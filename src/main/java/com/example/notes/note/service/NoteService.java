package com.example.notes.note.service;

import com.example.notes.note.dto.NoteDto;
import com.example.notes.note.dto.SendNoteFrom;
import com.example.notes.note.model.Note;
import com.example.notes.note.model.SendNote;

import java.util.List;

public interface NoteService {
    Note createNote (NoteDto noteDto, Long userId);
    Note updateNote (NoteDto noteDto, Long userId, Long noteId);
    List<Note> getNoteByUserId(Long id);
    void deleteNote(Long id, Long userId);
    List<Note> getAll();
    SendNote sendNote(Long userIdFrom, Long userIdTo, Long noteId);
    List<SendNoteFrom> getSendNotesForUser(Long userId);
    Note acceptedSendNoteForUser(Long userId, Long noteId);
    Note addUserForNote(Long userId, Long noteId);
}
