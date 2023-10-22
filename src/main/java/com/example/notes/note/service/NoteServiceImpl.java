package com.example.notes.note.service;

import com.example.notes.exception.NotFoundException;
import com.example.notes.note.dto.NoteDto;
import com.example.notes.note.dto.SendNoteDto;
import com.example.notes.note.dto.SendNoteFrom;
import com.example.notes.note.mapper.NoteMapper;
import com.example.notes.note.model.Note;
import com.example.notes.note.model.SendNote;
import com.example.notes.note.repository.NoteRepository;
import com.example.notes.note.repository.SendNoteRepository;
import com.example.notes.user.model.User;
import com.example.notes.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final SendNoteRepository sendNoteRepository;
    private final NoteMapper mapper;

    @Override
    public Note createNote(NoteDto noteDto, Long userId) {
        Note note = mapper.toNote(noteDto);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        note.setCreated(LocalDateTime.now());
        note.setUser(user);
        note.setName(note.getName());
        return noteRepository.save(note);
    }

    @Override
    public Note updateNote(NoteDto noteDto, Long userId, Long noteId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new NotFoundException("Заметка не найдена"));
        note.setChanged(LocalDateTime.now());
        Optional.ofNullable(noteDto.getName()).ifPresent(note::setName);
        Optional.ofNullable(noteDto.getDescription()).ifPresent(note::setDescription);
        return noteRepository.save(note);
    }

    @Override
    public List<Note> getNoteByUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        return noteRepository.findByUserId(userId);
    }

    @Override
    public void deleteNote(Long noteId, Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        noteRepository.findById(noteId).orElseThrow(() -> new NotFoundException("Заметка не найдена"));
        noteRepository.deleteById(noteId);
    }

    public List<Note> getAll() {
        return noteRepository.findAll();
    }

    @Override
    public SendNote sendNote(Long userIdFrom, Long userIdTo, Long noteId) {
        User userFrom = findUser(userIdFrom);
        User userTo = findUser(userIdTo);
        Note note = findNote(noteId);
        SendNoteDto sendNoteDto = new SendNoteDto(note, userFrom, userTo, false);
        return sendNoteRepository.save(mapper.toSendNote(sendNoteDto));
    }

    public List<SendNoteFrom> getSendNotesForUser(Long userId) {
        findUser(userId);
        return sendNoteRepository.findByUserId(userId);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    private Note findNote(Long noteId) {
        return noteRepository.findById(noteId)
                .orElseThrow(() -> new NotFoundException("Заметка не найдена"));
    }
}
