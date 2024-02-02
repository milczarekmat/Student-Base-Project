package db.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "student_grades", schema = "bazazpo")
public class StudentGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_subject", nullable = false)
    private Subject idSubject;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_student", nullable = false)
    private Student idStudent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_grade")
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