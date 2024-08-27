package gastronomy.guide.service.impl;

import gastronomy.guide.model.dto.JWTAuthenticationResponse;
import gastronomy.guide.model.dto.SignInRequest;
import gastronomy.guide.model.dto.SignUpRequest;
import gastronomy.guide.model.entities.User;
import gastronomy.guide.model.enums.Role;
import gastronomy.guide.service.AuthenticationService;
import gastronomy.guide.service.JwtService;
import gastronomy.guide.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public JWTAuthenticationResponse signUp(SignUpRequest request) {

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userService.create(user);

        var jwt = jwtService.generateToken(user);
        return new JWTAuthenticationResponse(jwt);
    }

    @Override
    public JWTAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new JWTAuthenticationResponse(jwt);
    }
}