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

        Student student2 = new Student();

        student2.setId(240125);
        student2.setName("Zygmunt");
        student2.setSurname("Nowacki");
        student2.setDepartment("Mechaniczny");

        Subject subject1 = new Subject();
        subject1.setName("Przyroda");
        subject1.setSubjectManager("Janosz");

        Subject subject2 = new Subject();
        subject2.setName("Matematyka");
        subject2.setSubjectManager("Welfle");


        Grade gradeTwo = new Grade();
        gradeTwo.setName("Niedostateczny");
        gradeTwo.setValue(2f);

        Grade gradeThree = new Grade();
        gradeThree.setName("Dostateczny");
        gradeThree.setValue(3f);

        Grade gradeThreePlus = new Grade();
        gradeThreePlus.setName("Dostateczny plus");
        gradeThreePlus.setValue(3.5F);

        Grade gradeFour = new Grade();
        gradeFour.setName("Dobry");
        gradeFour.setValue(4f);

        Grade gradeFourPlus = new Grade();
        gradeFourPlus.setName("Dobry plus");
        gradeFourPlus.setValue(4.5F);

        Grade gradeFive = new Grade();
        gradeFive.setName("Bardzo dobry");
        gradeFive.setValue(5f);

        StudentGrade firstStudentGrade = new StudentGrade();
        firstStudentGrade.setGrade(gradeThree);
        firstStudentGrade.setStudent(student);
        firstStudentGrade.setSubject(subject1);

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
        session.persist(student2);

        session.persist(gradeTwo);
        session.persist(gradeThree);
        session.persist(gradeThreePlus);
        session.persist(gradeFour);
        session.persist(gradeFourPlus);
        session.persist(gradeFive);

        session.persist(firstStudentGrade);

//        session.persist(studentGrade);
//        session.remove(student);

        session.getTransaction().commit();

    }
}

