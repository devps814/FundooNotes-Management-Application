package com.fundoonotes.fundoo_notes.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReminderDTO {

    private LocalDateTime reminderTime;
}
