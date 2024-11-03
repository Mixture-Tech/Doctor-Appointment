package mixture.hutech.backend.service.impl;

import lombok.RequiredArgsConstructor;
import mixture.hutech.backend.dto.response.UserResponse;
import mixture.hutech.backend.repository.UserRepository;
import mixture.hutech.backend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public Optional<UserResponse> getUserProfile(String userId) {
        return userRepository.findById(userId).map(user -> new UserResponse(
                null,
                null,
                user.getUsername(),
                null,
                user.getAddress(),
                user.getDateOfBirth(),
                user.getGender()
        ));
    }
}
