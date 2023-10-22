package com.example.notes.note.dto;

import com.example.notes.user.model.User;
import jakarta.validation.Valid;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Valid
public class NoteDto {
    private Long id;
    private String name;
    private String description;
    private User user;
}