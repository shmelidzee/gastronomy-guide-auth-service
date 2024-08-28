package gastronomy.guide.controller;

import gastronomy.guide.model.dto.JWTAuthenticationResponse;
import gastronomy.guide.model.dto.SignInRequest;
import gastronomy.guide.model.dto.SignUpRequest;
import gastronomy.guide.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Операции для регистрации и авторизации пользователей")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Регистрация пользователя", description = "Создание нового пользователя и генерация JWT токена")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрирован",
                    content = @Content(schema = @Schema(implementation = JWTAuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные для регистрации", content = @Content)
    })
    @PostMapping("/sign-up")
    public JWTAuthenticationResponse signUp(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Информация о новом пользователе для регистрации", required = true)
            @Valid @RequestBody SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @Operation(summary = "Авторизация пользователя", description = "Авторизация существующего пользователя и генерация JWT токена")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно авторизован",
                    content = @Content(schema = @Schema(implementation = JWTAuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные для авторизации", content = @Content)
    })
    @PostMapping("/sign-in")
    public JWTAuthenticationResponse signIn(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Данные для авторизации пользователя", required = true)
            @Valid @RequestBody SignInRequest request) {
        return authenticationService.signIn(request);
    }

    @Operation(summary = "Рефреш токена", description = "Обновляет JWT access token с использованием refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Токен успешно обновлён",
                    content = @Content(schema = @Schema(implementation = JWTAuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректный refresh token", content = @Content),
            @ApiResponse(responseCode = "401", description = "Неавторизованный доступ или истёкший refresh token", content = @Content)
    })
    @PostMapping("/refresh")
    public JWTAuthenticationResponse refresh(
            @Parameter(description = "Refresh token для обновления JWT access token", required = true)
            @RequestBody String refreshToken) {
        return authenticationService.refresh(refreshToken);
    }
}
