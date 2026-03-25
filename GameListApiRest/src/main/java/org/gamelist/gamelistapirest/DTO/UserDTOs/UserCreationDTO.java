package org.gamelist.gamelistapirest.DTO.UserDTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

//Nos genera setters,getters,equals...
@Data
public class UserCreationDTO {
    @NotBlank
    private String nickname;
    @NotBlank
    @Email
    private String email;
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._-])[A-Za-z\\d@$!%*?&._-]{8,}$",
            message = "La contraseña debe tener mayúsculas, minúsculas, números y un carácter especial"
    )
    private String password;
    private String imageUrl;
}
