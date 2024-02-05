package db.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "student_grades", schema = "bazazpo", uniqueConstraints={@UniqueConstraint(name = "STUDENT_SUBJECT_UNIQUE", columnNames = {"id_subject" , "id_student"})})
public class StudentGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_subject", nullable = false, foreignKey = @ForeignKey(name="FK_SUBJECT"))
    private Subject idSubject;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_student", nullable = false, foreignKey = @ForeignKey(name="FK_STUDENT"))
    private Student idStudent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_grade", nullable = true, foreignKey = @ForeignKey(name="FK_GRADE"))
    private Grade idGrade;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Subject getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(Subject idSubject) {
        this.idSubject = idSubject;
    }

    public Student getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(Student idStudent) {
        this.idStudent = idStudent;
    }

    public Grade getIdGrade() {
        return idGrade;
    }

    public void setIdGrade(Grade idGrade) {
        this.idGrade = idGrade;
    }

}