package com.example.notes.note.model;

import com.example.notes.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "send_note")
public class SendNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "note_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Note note;
    @JoinColumn(name = "sender_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User senderUser;
    @JoinColumn(name = "accepted_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User acceptedUser;
    private Boolean accepted;
}