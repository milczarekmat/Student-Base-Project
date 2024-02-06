package db.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "subjects", schema = "bazazpo", uniqueConstraints = @UniqueConstraint(name = "UNIQUE_SUBJECT_NAME", columnNames = {"name"}))
public class Subject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "name", nullable = false)

    private String name;

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subjectManager='" + subjectManager + '\'' +
                '}';
    }

    @Lob
    @Column(name = "subject_manager", nullable = false)
    private String subjectManager;

    @OneToMany(mappedBy = "subject")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<StudentGrade> studentGrades = new LinkedHashSet<>();

    @Lob
    @Column(name = "mean", nullable = false)
    private Float mean;

    public Subject(Integer id, String name, String subjectManager) {
        this.id = id;
        this.name = name;
        this.subjectManager = subjectManager;
    }

    public Subject() {
    }

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

    public void countMean() {
        Float sum = (float) 0;
        int count = 0;
        for(StudentGrade grade : studentGrades) {
            sum += grade.getGrade().getValue();
            count++;
        }
        mean = sum / count;
    }

    public Float getMean() {
        return mean;
    }

}