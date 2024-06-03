package ru.test_app.backend.controllers.test.database.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity(name = "test_sections")
public class TestSectionsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column(length = 50, nullable = false)
    public String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    public TestEntity test;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "section")
    public List<SectionQuestionsEntity> questions;

}
