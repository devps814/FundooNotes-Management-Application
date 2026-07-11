package com.fundoonotes.fundoo_notes.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabelResponseDTO {

    private Long id;
    private String name;

    public LabelResponseDTO() {}

    public LabelResponseDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}