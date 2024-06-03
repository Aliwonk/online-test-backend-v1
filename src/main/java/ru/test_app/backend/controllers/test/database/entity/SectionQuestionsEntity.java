package ru.test_app.backend.controllers.test.database.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity(name = "section_questions")
public class SectionQuestionsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column(length = 100, nullable = false)
    public String text;

    @Column(nullable = false)
    public int scope;

    public String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    public TestSectionsEntity section;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "question")
    public List<QuestionOptionsEntity> options;

}
