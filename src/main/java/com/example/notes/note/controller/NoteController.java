package com.example.notes.note.controller;

import com.example.notes.note.dto.NoteDto;
import com.example.notes.note.dto.SendNoteFrom;
import com.example.notes.note.model.Note;
import com.example.notes.note.model.SendNote;
import com.example.notes.note.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/note")
@RequiredArgsConstructor
@Validated
@Slf4j
public class NoteController {
    private final NoteService noteService;

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Note createNote(@Valid @RequestBody NoteDto noteDto, @PathVariable Long userId) {
        return noteService.createNote(noteDto, userId);
    }

    @GetMapping("/{userId}")
    List<Note> getNoteByUserId(@PathVariable Long userId) {
        log.info("Получен GET-запрос на получение всех заметок пользователя {}", userId);
        return noteService.getNoteByUserId(userId);
    }

    @GetMapping("/all")
    List<Note> getAll() {
        return noteService.getAll();
    }

    @PostMapping("/sendNote/{userIdFrom}/{userIdTo}/{noteId}")
    @ResponseStatus(HttpStatus.CREATED)
    public SendNote sendNote(@PathVariable Long userIdFrom, @PathVariable Long userIdTo, @PathVariable Long noteId) {
        return noteService.sendNote(userIdFrom, userIdTo, noteId);
    }

    @GetMapping("/sendNote/{userId}")
    List<SendNoteFrom> getSendNotesForUser(@PathVariable Long userId) {
        return noteService.getSendNotesForUser(userId);
    }
}