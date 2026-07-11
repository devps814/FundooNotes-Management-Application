package com.fundoonotes.fundoo_notes.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollaboratorDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    // "READ" or "WRITE" — defaults to READ if not sent
    private String permission = "READ";
}
