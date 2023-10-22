package com.example.notes.note.repository;

import com.example.notes.note.dto.SendNoteFrom;
import com.example.notes.note.model.SendNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SendNoteRepository extends JpaRepository<SendNote, Long> {
    List<SendNoteFrom> findByUserId(Long userId);
}
