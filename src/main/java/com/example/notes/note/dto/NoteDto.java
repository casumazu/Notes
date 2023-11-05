package com.example.notes.note.dto;

import com.example.notes.user.model.User;
import jakarta.validation.Valid;
import lombok.*;

import java.util.Set;

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
    private Set<User> userSet;
}