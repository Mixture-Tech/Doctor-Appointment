package mixture.hutech.backend.service.impl;

import lombok.RequiredArgsConstructor;
import mixture.hutech.backend.dto.request.AuthenticationRequest;
import mixture.hutech.backend.dto.request.RegisterRequest;
import mixture.hutech.backend.dto.response.AuthenticationResponse;
import mixture.hutech.backend.dto.response.MessageResponse;
import mixture.hutech.backend.entity.CustomUserDetail;
import mixture.hutech.backend.entity.PasswordResetToken;
import mixture.hutech.backend.entity.Role;
import mixture.hutech.backend.entity.User;
import mixture.hutech.backend.enums.ErrorCodeEnum;
import mixture.hutech.backend.enums.PasswordResetTokenEnum;
import mixture.hutech.backend.exceptions.ApiException;
import mixture.hutech.backend.exceptions.ConflictException;
import mixture.hutech.backend.repository.PasswordResetTokenRepository;
import mixture.hutech.backend.repository.RoleRepository;
import mixture.hutech.backend.repository.UserRepository;
import mixture.hutech.backend.service.AuthenticationService;
import mixture.hutech.backend.service.EmailService;
import mixture.hutech.backend.service.JwtService;
import mixture.hutech.backend.service.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final EmailService emailService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private PasswordResetToken createAndSavePasswordResetToken(User user, PasswordResetTokenEnum tokenType) {
        String token = "MIXTURE_" + UUID.randomUUID().toString() + System.currentTimeMillis();
        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .tokenType(tokenType)
                .expiryDate(Timestamp.valueOf(LocalDateTime.now().plusHours(24)))
                .build();
        passwordResetTokenRepository.save(passwordResetToken);
        return passwordResetToken;
    }

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new ApiException(ErrorCodeEnum.EMAIL_ALREADY_EXISTS);
        }

        Role role = roleRepository.findById(request.getRole())
                .orElse(roleRepository.findFirstByOrderByIdAsc());

        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .username(request.getUsername())
                .role(role)
                .isDoctorInfoCompleted(false)
                .build();
        userRepository.save(user);

        PasswordResetToken verificationToken = createAndSavePasswordResetToken(user, PasswordResetTokenEnum.EMAIL_VERIFICATION_TOKEN);

        try{
            emailService.sendMailWithTokenRegister(user.getEmail(), user.getUsername(), verificationToken.getToken());
        }catch (Exception e){
            System.out.println("Mail error: " + e.getMessage());
        }

        return AuthenticationResponse.builder()
                .errorCode(ErrorCodeEnum.OK)
                .message("Registration Successful")
                .accessToken(null)
                .refreshToken(null)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new ApiException(ErrorCodeEnum.USER_NOT_FOUND)
        );

        CustomUserDetail userDetails = new CustomUserDetail(user);
        if(!userDetails.isEnabled()){
            throw new ApiException(ErrorCodeEnum.ACCOUNT_NOT_ACTIVATED);
        }

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException exception){
            throw new ApiException(ErrorCodeEnum.INVALID_CREDENTIALS);
        }

        var jwtToken = jwtService.generateToken(userDetails);
        var refreshToken = jwtService.generateRefreshToken(userDetails);
        tokenService.revokeAllUserTokens(user);
        tokenService.saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .name(user.getUsername())
                .role(user.getRole().getName())
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .errorCode(ErrorCodeEnum.OK)
                .message("Login Successful")
                .build();
    }

    @Override
    public MessageResponse verifyTokenRegister(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token).orElseThrow();
        if(passwordResetToken.getExpiryDate().before(new Date())){
            return MessageResponse.builder()
                    .errorCode(ErrorCodeEnum.TOKEN_EXPIRED)
                    .message(ErrorCodeEnum.TOKEN_EXPIRED.getMessage())
                    .build();
        }

        passwordResetToken.setActivated(true);
        passwordResetToken.getUser().setActive(true);
        passwordResetTokenRepository.save(passwordResetToken);

        return MessageResponse.builder()
                .errorCode(ErrorCodeEnum.OK)
                .message("Verification Successful")
                .build();
    }

    @Override
    public MessageResponse forgotPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ApiException(ErrorCodeEnum.USER_NOT_FOUND)
        );

        PasswordResetToken passwordResetToken = createAndSavePasswordResetToken(user, PasswordResetTokenEnum.PASSWORD_RESET_TOKEN);


        try{
            emailService.sendMailWithTokenResetPassword(user.getEmail(), user.getUsername(), passwordResetToken.getToken());
        }catch (Exception e){
            System.out.println("Mail error: " + e.getMessage());
        }

        return MessageResponse.builder()
                .errorCode(ErrorCodeEnum.OK)
                .message("Email sent successfully")
                .build();
    }

    @Override
    public MessageResponse changePassword(String token, String newPassword) {
        if(token == null || token.isEmpty() || newPassword == null || newPassword.isEmpty()){
            throw new ApiException(ErrorCodeEnum.INVALID_TOKEN);
        }

        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token).orElseThrow(null);
        if(passwordResetToken == null || passwordResetToken.getExpiryDate().before(new Date()) || passwordResetToken.isActivated()){
            throw new ApiException(ErrorCodeEnum.INVALID_TOKEN);
        }

        User user = passwordResetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        passwordResetToken.setActivated(true);
        passwordResetTokenRepository.save(passwordResetToken);

        return MessageResponse.builder()
                .errorCode(ErrorCodeEnum.OK)
                .message("Password changed successfully")
                .build();
    }
}
