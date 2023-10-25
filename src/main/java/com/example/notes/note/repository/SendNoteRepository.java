package com.example.notes.note.repository;

import com.example.notes.note.dto.SendNoteDto;
import com.example.notes.note.dto.SendNoteFrom;
import com.example.notes.note.model.SendNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SendNoteRepository extends JpaRepository<SendNote, Long> {
    List<SendNote> findByAcceptedUserId(Long userId);
    void removeByNoteIdAndAcceptedUserId(Long noteId, Long acceptedUserId);
    Boolean existsByNoteIdAndAcceptedUserId(Long noteId, Long acceptedUserId);
}
