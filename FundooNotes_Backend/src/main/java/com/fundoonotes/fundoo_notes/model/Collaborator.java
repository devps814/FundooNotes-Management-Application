package com.fundoonotes.fundoo_notes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "collaborators",
        uniqueConstraints = @UniqueConstraint(columnNames = {"note_id", "user_id"}))
public class Collaborator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The note being shared
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id")
    private Note note;

    // The user the note is shared WITH
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // READ or WRITE
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Permission permission = Permission.READ;

    @CreationTimestamp
    private LocalDateTime addedAt;

    public enum Permission {
        READ, WRITE
    }


}
