package gastronomy.guide.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {
    @Size(min = 5, max = 70, message = "Имя пользователя должно содержать от 5 до 50 символов")
    private String username;

    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;

    @Size(max = 255, message = "Длина пароля должна быть не более 255 символов")
    private String password;
}
