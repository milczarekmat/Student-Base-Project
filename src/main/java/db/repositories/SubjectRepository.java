package db.repositories;

import db.entities.Student;
import db.entities.Subject;
import jakarta.persistence.*;

import java.util.List;

public class SubjectRepository {
    private final EntityManagerFactory entityManagerFactory;

    public SubjectRepository() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("student_base");
    }

    public void addSubject(Subject newSubject) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();

            entityManager.persist(newSubject);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeSubject(String subjectName) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();

            String jpql = "DELETE FROM Subject s WHERE s.name = :subjectName";
            Query query = entityManager.createQuery(jpql);
            query.setParameter("subjectName", subjectName);

            int deletedCount = query.executeUpdate();

            entityManager.getTransaction().commit();

            if (deletedCount > 0) {
                System.out.println("Usunięto przedmiot o nazwie: " + subjectName);
            } else {
                System.out.println("Nie znaleziono przedmiotu o nazwie: " + subjectName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Subject> getAllSubjects() {
        List<Subject> subjects = null;

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();

            String jpql = "SELECT s FROM Subject s";
            TypedQuery<Subject> query = entityManager.createQuery(jpql, Subject.class);
            subjects = query.getResultList();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return subjects;
    }

    public List<Subject> getSubjectsWithGrades() {
        List<Subject> subjects = null;

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();

            // Znajdź przedmioty, które mają chociaż jedną ocenę w tabeli student_grades
            String jpql = "SELECT DISTINCT s FROM Subject s " +
                    "JOIN FETCH s.studentGrades g " +
                    "WHERE SIZE(s.studentGrades) > 0";

            TypedQuery<Subject> query = entityManager.createQuery(jpql, Subject.class);
            subjects = query.getResultList();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return subjects;
    }
}
