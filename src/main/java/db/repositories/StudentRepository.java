package db.repositories;

import db.entities.Student;
import db.entities.StudentGrade;
import jakarta.persistence.*;

import java.util.List;

public class StudentRepository {
    private final EntityManagerFactory entityManagerFactory;

    public StudentRepository() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("student_base");
    }

    public void addStudent(Student newStudent) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();

            entityManager.persist(newStudent);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Student getStudent(int ind) {
        Student student = null;
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();

            student = entityManager.find(Student.class, ind);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return student;
    }

    public List<Student> getAllStudents() {
        List<Student> students = null;

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();

            String jpql = "SELECT s FROM Student s";
            TypedQuery<Student> query = entityManager.createQuery(jpql, Student.class);
            students = query.getResultList();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }

    public void removeStudent(int ind) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();

            Student student = entityManager.find(Student.class, ind);

            if (student != null) {
                entityManager.remove(student);
            } else {
                System.out.println("Nie znaleziono studenta z podanym indeksem: " + ind);
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Student> getAllStudentsWithGrades() {
        List<Student> studentsWithGrades = null;
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();

            String jpql = "SELECT DISTINCT s FROM Student s LEFT JOIN FETCH s.studentGrades sg LEFT JOIN FETCH sg.idSubject LEFT JOIN FETCH sg.idGrade";
            TypedQuery<Student> query = entityManager.createQuery(jpql, Student.class);
            studentsWithGrades = query.getResultList();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        for (Student student : studentsWithGrades) {
//            System.out.println("Student: " + student.getName() + " " + student.getSurname());
//
//            for (StudentGrade studentGrade : student.getStudentGrades()) {
//                System.out.println("Subject: " + studentGrade.getIdSubject().getName());
//                System.out.println("Grade: " + studentGrade.getIdGrade().getValue());
//            }
//            System.out.println();
//        }
        return studentsWithGrades;
    }
}
