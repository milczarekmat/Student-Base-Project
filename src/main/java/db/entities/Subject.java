package db.entities;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "subjects", schema = "bazazpo")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "subject_manager", nullable = false)
    private String subjectManager;

    @OneToMany(mappedBy = "idSubject")
    private Set<StudentGrade> studentGrades = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjectManager() {
        return subjectManager;
    }

    public void setSubjectManager(String subjectManager) {
        this.subjectManager = subjectManager;
    }

    public Set<StudentGrade> getStudentGrades() {
        return studentGrades;
    }

    public void setStudentGrades(Set<StudentGrade> studentGrades) {
        this.studentGrades = studentGrades;
    }

}