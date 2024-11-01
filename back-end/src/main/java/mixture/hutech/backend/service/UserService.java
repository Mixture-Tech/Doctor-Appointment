package mixture.hutech.backend.service;

import mixture.hutech.backend.dto.response.UserResponse;

public interface UserService {
    UserResponse getCurrentUserByEmail(String email);
}
