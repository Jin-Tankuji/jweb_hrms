package com.nhom2.hrms.service;

import com.nhom2.hrms.dto.request.AuthRequest;
import com.nhom2.hrms.dto.request.IntrospectRequest;
import com.nhom2.hrms.dto.request.LogoutRequest;
import com.nhom2.hrms.dto.request.RefreshRequest;
import com.nhom2.hrms.dto.response.ApiResponse;
import com.nhom2.hrms.dto.response.AuthResponse;
import com.nhom2.hrms.dto.response.IntrospectResponse;

import com.nhom2.hrms.entity.InvalidatedToken;
import com.nhom2.hrms.entity.User;
import com.nhom2.hrms.repository.InvalidatedTokenRepository;
import com.nhom2.hrms.repository.UserRepository;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {
    UserRepository userRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;

    // Encryption key
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGN_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    public ApiResponse<IntrospectResponse> introspect(IntrospectRequest req) throws JOSEException, ParseException {
        // Extract the token from the request
        var token = req.getToken();
        boolean isValid = true;

        // Verify the token and check its validity
        try {
            verifyToken(token, false);
        } catch (JOSEException | ParseException e) {
            // Build and return a failed response indicating the token is invalid
            isValid = false;
        }

        // Build and return a successful response indicating the token is valid
        return ApiResponse.<IntrospectResponse>builder()
                .result(IntrospectResponse.builder()
                        .valid(isValid)
                        .build())
                .build();
    }

    // Validate login
    public AuthResponse authenticate(AuthRequest req) {
        // Search user by username
        var user = userRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tên người dùng"));

        // Check encrypt password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated =  passwordEncoder.matches(req.getPassword(), user.getPassword());

        if (!authenticated)
            throw new RuntimeException("Sai tài khoản hoặc mật khẩu");

        // Generate authentication token for login session
        var token = generateToken(user);
        return AuthResponse.builder()
            .token(token)
            .authenticated(true)
            .build();


    }

    public void logout(LogoutRequest req)
            throws ParseException, JOSEException {
        try {
            var signToken = verifyToken(req.getToken(), true);

            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jit)
                    .expiryTime(expiryTime)
                    .build();

            invalidatedTokenRepository.save(invalidatedToken);
        } catch (Exception e) {
            log.info("Token already expired.");
        }
    }

    public AuthResponse refreshToken(RefreshRequest req)
            throws ParseException, JOSEException {
        var signedJWT = verifyToken(req.getToken(), true);

        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();

        invalidatedTokenRepository.save(invalidatedToken);

        var username = signedJWT.getJWTClaimsSet().getSubject();

        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        var token = generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    private SignedJWT verifyToken(String token, boolean isRefresh)
            throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGN_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiryTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime()
                    .toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) {
            throw new RuntimeException("Token không hợp lệ hoặc hết hạn.");
        }

        System.out.println("Token hợp lệ: " + signedJWT.getJWTClaimsSet().getSubject());

        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new RuntimeException("Token này đã bị vô hiệu hóa.");

        return signedJWT;
    }

    // Generate token with username
    private String generateToken(User user) {
        // Set value header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // Set value Payload
        // - subject: Định danh người dùng (username)
        // - issuer: Người phát hành token (hrms.com)
        // - issueTime: Thời điểm phát hành token
        // - expirationTime: Thời điểm hết hạn (sau 2 giờ kể từ lúc tạo)
        // - claim: Thêm thông tin tùy chỉnh, ở đây là userId = "Custom"
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("hrms.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();

        // Convert JWTClaimsSet to payload (content of JWT)
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        // Config json web token
        JWSObject jwsObject = new JWSObject(header, payload);

        // Sign token by secret key use MACSigner
        try {
            jwsObject.sign(new MACSigner(SIGN_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(stringJoiner::add);

        return stringJoiner.toString();
    }
}
