package com.example.notes.note.service;

import com.example.notes.exception.NotFoundException;
import com.example.notes.exception.UnauthorizedException;
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
import java.util.*;
import java.util.stream.Collectors;

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
        note.setCreatedUser(user);
        note.setName(note.getName());
        note.setCommon(false);
        return noteRepository.save(note);
    }

    @Override
    public Note updateNote(NoteDto noteDto, Long userId, Long noteId) {
        User user = findUser(userId);

        Note note = findNote(noteId);

        if (!userHasNotePermission(user, note) && !note.getCreatedUser().equals(user)) {
            throw new UnauthorizedException("У вас нет разрешения на изменение этой заметки");
        }
        if (note.getCommon()) {
            note.setChangedUser(user);
        }
        Optional.ofNullable(noteDto.getName()).ifPresent(note::setName);
        Optional.ofNullable(noteDto.getDescription()).ifPresent(note::setDescription);

        note.setChanged(LocalDateTime.now());
        return noteRepository.save(note);
    }

    private boolean userHasNotePermission(User user, Note note) {
        return note.getUserSet().stream()
                .anyMatch(noteUser -> noteUser.equals(user));
    }

    @Override
    public List<Note> getNoteByUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        return noteRepository.findByCreatedUserIdOrUserSet_Id(userId, userId);
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
        SendNoteDto sendNoteDto = new SendNoteDto(null, note, userFrom, userTo, false);
        return sendNoteRepository.save(mapper.toSendNote(sendNoteDto));
    }


    // Просмотр запросов на передачу заметок для пользователя
    public List<SendNoteFrom> getSendNotesForUser(Long userId) {
        findUser(userId);
        return sendNoteRepository.findByAcceptedUserId(userId)
                .stream()
                .map(mapper::toSendNoteFrom)
                .collect(Collectors.toList());
    }

    public Note acceptedSendNoteForUser(Long userId, Long noteId) {
        User user = findUser(userId);
        Note saveNote = findNote(noteId);

        boolean exists = sendNoteRepository.existsByNoteIdAndAcceptedUserId(noteId, userId);

        if (exists) {
            Note note = new Note();
            note.setName(saveNote.getName());
            note.setDescription(saveNote.getDescription());
            note.setCreated(LocalDateTime.now());
            note.setCreatedUser(user);
            noteRepository.save(note);

            sendNoteRepository.removeByNoteIdAndAcceptedUserId(noteId, userId);

            return saveNote;
        } else {
            throw new NotFoundException("Заметки не существует в списке запросов.");
        }
    }

    // Добавление пользователя для заметки
    public Note addUserForNote(Long userId, Long noteId) {
        User user = findUser(userId);
        Note note = findNote(noteId);
        note.setCommon(true);
        note.getUserSet().add(user);
        return noteRepository.save(note);
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