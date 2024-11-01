package mixture.hutech.backend.controller;

import lombok.RequiredArgsConstructor;
import mixture.hutech.backend.dto.response.MessageResponse;
import mixture.hutech.backend.dto.response.UserResponse;
import mixture.hutech.backend.enums.ErrorCodeEnum;
import mixture.hutech.backend.exceptions.ApiException;
import mixture.hutech.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/getUser/{email}")
    public ResponseEntity<MessageResponse> getCurrentUserByEmail(@PathVariable String email){
        try {
            UserResponse userResponse = userService.getCurrentUserByEmail(email);
            return ResponseEntity.ok(MessageResponse.builder()
                    .errorCode(ErrorCodeEnum.OK)
                    .message(ErrorCodeEnum.OK.getMessage())
                    .data(userResponse)
                    .build());
        } catch (ApiException ex){
            return ResponseEntity
                    .status(ex.getErrorCodeEnum().getHttpStatus())
                    .body(MessageResponse.builder()
                            .errorCode(ex.getErrorCodeEnum())
                            .message(ex.getMessage())
                            .build());
        }
    }
}
