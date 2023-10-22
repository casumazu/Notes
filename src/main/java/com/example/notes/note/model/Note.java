package com.example.notes.note.model;

import com.example.notes.user.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "note")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; // Название заметки
    private String description; // Описание заметки
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created; // создано
    private LocalDateTime changed; // изменено
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}