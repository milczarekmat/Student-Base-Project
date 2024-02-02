package db.entities;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "grades", schema = "bazazpo")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "value", nullable = false)
    private Float value;

    @Lob
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "idGrade")
    private Set<StudentGrade> studentGrades = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<StudentGrade> getStudentGrades() {
        return studentGrades;
    }

    public void setStudentGrades(Set<StudentGrade> studentGrades) {
        this.studentGrades = studentGrades;
    }

}