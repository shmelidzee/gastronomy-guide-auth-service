package gastronomy.guide.service.impl;

import gastronomy.guide.model.entities.User;
import gastronomy.guide.repository.UserRepository;
import gastronomy.guide.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        return save(user);
    }

    @Override
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    @Override
    public User userByRefreshToken (String refreshToken) {
        return userRepository.findByRefreshToken(refreshToken)
                .orElse(null);
    }

    @Override
    @Transactional
    public void updateRefreshToken(UserDetails user, String refreshToken) {
        userRepository.updateRefreshTokenByUsername(refreshToken, user.getUsername());
    }

    private User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }

    private User save(User user) {
        return userRepository.save(user);
    }
}
