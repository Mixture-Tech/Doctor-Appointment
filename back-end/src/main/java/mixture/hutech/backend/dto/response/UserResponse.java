package mixture.hutech.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private String role;
    private String avatar;
    private String phone;
    private String address;
    private Set<AppointmentResponse> appointments;

    public UserResponse(String id, String email, String username, String phone) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.phone = phone;
    }
}
