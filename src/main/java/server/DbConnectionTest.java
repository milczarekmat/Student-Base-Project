package server;

import db.entities.Grade;
import db.entities.Student;
import db.entities.StudentGrade;
import db.entities.Subject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DbConnectionTest {
    public static void main(String[] args) {


        // Ustawienie polaczenia
        EntityManagerFactory sessionFactory
                = Persistence.createEntityManagerFactory("student_base");


        // start sesji
        EntityManager session = sessionFactory.createEntityManager();

        Student student = new Student();

        student.setId(240885);
        student.setName("Jan");
        student.setSurname("Kowalski");
        student.setDepartment("Weeia");

        Subject subject1 = new Subject();
        subject1.setName("Przyroda");
        subject1.setSubjectManager("Janosz");

        Subject subject2 = new Subject();
        subject2.setName("Matematyka");
        subject2.setSubjectManager("Welfle");

        Grade grade = new Grade();
        grade.setName("Dostateczny");
        grade.setValue(3f);

//        Grade grade = session.find(Grade.class, 1);
//        Student student = session.find(Student.class, 240885);
//        Subject subject = session.find(Subject.class, 2);
//
//        StudentGrade studentGrade = new StudentGrade();
//        studentGrade.setIdStudent(student);
//        studentGrade.setIdSubject(subject);
//        studentGrade.setIdGrade(grade);

//        Subject subject = session.find(Subject.class, 1);

        session.getTransaction().begin();

        session.persist(subject1);
        session.persist(subject2);
        session.persist(student);
        session.persist(grade);

//        session.persist(studentGrade);
//        session.remove(student);

        session.getTransaction().commit();

    }
}

