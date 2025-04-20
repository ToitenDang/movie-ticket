package com.dang.Movie_Ticket.service.impl;

import com.dang.Movie_Ticket.dto.request.AuthenticationRequest;
import com.dang.Movie_Ticket.dto.request.IntrospectRequest;
import com.dang.Movie_Ticket.dto.request.LogoutRequest;
import com.dang.Movie_Ticket.dto.request.RefreshRequest;
import com.dang.Movie_Ticket.dto.response.AuthenticationResponse;
import com.dang.Movie_Ticket.dto.response.IntrospectResponse;
import com.dang.Movie_Ticket.entity.InvalidatedToken;
import com.dang.Movie_Ticket.entity.User;
import com.dang.Movie_Ticket.exception.AppException;
import com.dang.Movie_Ticket.exception.ErrorCode;
import com.dang.Movie_Ticket.repository.InvalidatedTokenRepository;
import com.dang.Movie_Ticket.repository.UserRepository;
import com.dang.Movie_Ticket.service.AuthenticationService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private InvalidatedTokenRepository invalidatedTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;

        try {
            // truyền token và thông báo không refresh token vào để xác minh token
            verifyToken(token, false);
        } catch (AppException e){
            // nếu có exception thì sẽ trả về false
            isValid = false;
        }
        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    @Override
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try {
            // Tạo biến lưu kết quả sau khi xác thực token cần logout
            var signToken = verifyToken(request.getToken(), true);

            // Lấy 2 thông tin cần lưu vào database khi logout
            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            // Tạo đối tượng để lưu vào database
            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jit)
                    .expiryTime(expiryTime)
                    .build();
            // Lưu vào database để quản lý các token không còn giá trị sử dụng nhưng còn thời gian hiệu lực
            invalidatedTokenRepository.save(invalidatedToken);
        } catch (AppException exception){
            // Nếu trả về exception chứng tỏ token đã hết hạn
            log.info("Token already expired");
        }
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        // Kiểm tra hiệu lực token
        var signJWT = verifyToken(request.getToken(), true);

        // Lấy 2 thông tin cần lưu vào database khi refresh
        var jit = signJWT.getJWTClaimsSet().getJWTID();
        Date expiryTime = signJWT.getJWTClaimsSet().getExpirationTime();

        // Tạo đối tượng để lưu vào database
        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();
        // Lưu vào database để quản lý các token không còn giá trị sử dụng nhưng còn thời gian hiệu lực
        invalidatedTokenRepository.save(invalidatedToken);

        // Lấy ra user sở hữu token cần refresh
        var email = signJWT.getJWTClaimsSet().getSubject();
        var user = userRepository.findUserByEmail(email).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Tạo token mới cho user
        var token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }


    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        // Tạo đối tượng verifier với đối số là mã khóa của token
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        // Chuyển token từ String sang đối tượng SignedJWT
        SignedJWT signedJWT = SignedJWT.parse(token);

        // Lấy ra ngày hết hạn trong claim(payload). Nếu isRefresh == true thì lấy thời gian refresh
        // nếu false thì thời gian tồn tại của token
        Date expiryTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant()
                .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        // Xác thực token bằng đối tượng verifier chứa mã khóa token
        var verified = signedJWT.verify(verifier);

        if (!verified && expiryTime.after(new Date()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        log.info("user{}", request.getEmail());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if(!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }



    private String generateToken(User user){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("dang.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .claim("scope", user.getRole())
                .jwtID(UUID.randomUUID().toString())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        log.info("payload: {}", payload);
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }
}
