package com.fundoonotes.fundoo_notes.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollaboratorResponseDTO {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String permission;

    public CollaboratorResponseDTO() {
    }

    public CollaboratorResponseDTO(Long id, String email, String firstName,
                                    String lastName, String permission) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.permission = permission;
    }

}
