package com.mplata.task_management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    @NotBlank(message = "The firstName cannot be blank.")
    @NotNull(message = "The firstName cannot be null.")
    private String firstName;

    @NotBlank(message = "The lastName cannot be blank.")
    @NotNull(message = "The lastName cannot be null.")
    private String lastName;

    @NotBlank(message = "The email cannot be blank.")
    @NotNull(message = "The email cannot be null.")
    @Email(message = "format: example@mail.com")
    private String email;

    @NotBlank(message = "The password cannot be blank.")
    @NotNull(message = "The password cannot be null.")
    private String password;
}
