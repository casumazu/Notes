package com.example.notes.note.dto;

import com.example.notes.note.model.Note;
import com.example.notes.user.model.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class SendNoteFrom {
    private Note noteId;
    private User senderUser;
}
