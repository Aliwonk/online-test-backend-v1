package ru.test_app.backend.controllers.test.database.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity(name = "question_options")
public class QuestionOptionsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column(length = 100, nullable = false)
    public String text;

    @Column(nullable = false)
    public boolean isRight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    public SectionQuestionsEntity question;
}
