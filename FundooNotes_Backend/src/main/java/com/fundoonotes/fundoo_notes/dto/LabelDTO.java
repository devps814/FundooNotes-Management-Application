package com.fundoonotes.fundoo_notes.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabelDTO {

    @NotBlank(message = "Label name cannot be empty")
    private String name;
}