package mixture.hutech.backend.service.impl;

import lombok.RequiredArgsConstructor;
import mixture.hutech.backend.dto.request.UserRequest;
import mixture.hutech.backend.dto.response.UserResponse;
import mixture.hutech.backend.entity.User;
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

    @Override
    public Optional<UserResponse> updateProfile(String userId, UserRequest request) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.get();
        user.setUsername(request.getUsername());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setGender(request.getGender());
        user.setAddress(request.getAddress());
        userRepository.save(user); // Lưu thay đổi
        return Optional.of(new UserResponse(
                user.getId(),
                null, // email
                user.getUsername(),
                null, // phone
                user.getAddress(),
                user.getDateOfBirth(),
                user.getGender()
        ));
    }
}
