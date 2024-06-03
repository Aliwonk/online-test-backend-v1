package ru.test_app.backend.controllers.test.database.entity;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Entity(name = "tests")
public class TestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column()
    private int expires;

    @Column()
    private int attempt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date close;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "test")
    private List<TestSectionsEntity> sections;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    /* CONSTRUCTORS */

    public TestEntity() {}

    public TestEntity(String title, int expires) {
        this.title = title;
        this.expires = expires;
    }

    /* GETTERS */

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getExpires() {
        return expires;
    }

    public int getAttempt() {
        return attempt;
    }

    public Date getClose() {
        return close;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    /* SETTERS */

    public void setTitle(String title) {
        this.title = title;
    }

    public void setExpires(int expires) {
        this.expires = expires;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    public void setClose(Date end) {
        this.close = end;
    }

    public void setSections(List<TestSectionsEntity> sections) {
        this.sections = sections;
    }

    public void addSection(TestSectionsEntity section) {
        this.sections.add(section);
    }

}
