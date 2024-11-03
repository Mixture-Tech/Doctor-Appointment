package mixture.hutech.backend.service;

import mixture.hutech.backend.dto.response.UserResponse;

import java.util.Optional;

public interface UserService {
    Optional<UserResponse> getUserProfile(String userId);
}
