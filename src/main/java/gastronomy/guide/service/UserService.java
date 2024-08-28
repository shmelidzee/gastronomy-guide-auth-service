package gastronomy.guide.service;

import gastronomy.guide.model.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    User create(User user);

    UserDetailsService userDetailsService();

    User userByRefreshToken(String refreshToken);

    void updateRefreshToken(UserDetails user, String refreshToken);
}
