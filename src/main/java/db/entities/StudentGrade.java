package db.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "student_grades", schema = "bazazpo", uniqueConstraints={@UniqueConstraint(name = "STUDENT_SUBJECT_UNIQUE", columnNames = {"id_subject" , "id_student"})})
public class StudentGrade implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "id_subject", nullable = false, foreignKey = @ForeignKey(name="FK_SUBJECT"))
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_student", nullable = false, foreignKey = @ForeignKey(name="FK_STUDENT"))
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_grade", nullable = true, foreignKey = @ForeignKey(name="FK_GRADE"))
    private Grade grade;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "StudentGrade{" +
                "id=" + id +
                ", subject=" + subject +
                ", student=" + student +
                ", grade=" + grade +
                '}';
    }

}