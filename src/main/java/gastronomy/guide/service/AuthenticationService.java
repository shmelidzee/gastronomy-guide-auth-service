package gastronomy.guide.service;

import gastronomy.guide.model.dto.JWTAuthenticationResponse;
import gastronomy.guide.model.dto.SignInRequest;
import gastronomy.guide.model.dto.SignUpRequest;

public interface AuthenticationService {

    JWTAuthenticationResponse signUp(SignUpRequest request);

    JWTAuthenticationResponse signIn(SignInRequest request);
}
