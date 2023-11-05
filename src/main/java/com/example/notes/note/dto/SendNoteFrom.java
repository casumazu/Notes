package com.example.notes.note.dto;

import com.example.notes.note.model.Note;
import com.example.notes.user.model.User;
import jakarta.validation.Valid;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Valid
public class SendNoteFrom {
    private Long id;
    private Note note;
    private User senderUser;
}