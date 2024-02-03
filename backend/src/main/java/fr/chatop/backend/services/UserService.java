package fr.chatop.backend.services;

import fr.chatop.backend.dto.UserDto;
import fr.chatop.backend.models.User;
import fr.chatop.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDetailsService userDetailsService() {
        return username -> userRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public User save(User newUser) {
        if (newUser.getId() == null) {
            newUser.setCreatedAt(LocalDateTime.now());
        }

        newUser.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(newUser);
    }

    public UserDto getUser(UserDetails userDetails) {
        UserDto me = new UserDto();
        me.setEmail(userDetails.getUsername());
        User user = userRepository.findByEmail(me.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        me.setId(user.getId());
        me.setName(user.getName());
        me.setCreated_at(user.getCreatedAt());
        me.setUpdated_at(user.getUpdatedAt());
        return me;
    }

    public Long count() {
        return userRepository.count();
    }
}
