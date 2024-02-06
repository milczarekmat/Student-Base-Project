package db.repositories;

import db.entities.Grade;
import db.entities.Student;
import db.entities.StudentGrade;
import db.entities.Subject;
import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

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

    public void removeSubjectForStudent(int studentId, int subjectId) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();

            String jpql = "DELETE FROM StudentGrade sg WHERE sg.student.id = :ind AND sg.subject.id = :subjectId";

            Query query = entityManager.createQuery(jpql);
            query.setParameter("ind", studentId);
            query.setParameter("subjectId", subjectId);

            int deletedCount = query.executeUpdate();

            entityManager.getTransaction().commit();

            if (deletedCount > 0) {
                System.out.println("Usunięto przedmiot o id: " + subjectId + " dla studenta o indeksie: " + studentId);
            } else {
                System.out.println("Nie znaleziono rekordów z student_grades dla studenta o indeksie: " + studentId + " i przedmiotu o id: " + subjectId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSubjectForStudent(int studentId, int subjectId) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();

            Student student = entityManager.find(Student.class, studentId);
            Subject subject = entityManager.find(Subject.class, subjectId);

            if (student != null && subject != null) {
                StudentGrade studentGrade = new StudentGrade();
                studentGrade.setStudent(student);
                studentGrade.setSubject(subject);
                studentGrade.setGrade(null);

                entityManager.persist(studentGrade);

                entityManager.getTransaction().commit();
                System.out.println("Dodano przedmiot o id: " + subjectId + " dla studenta o indeksie: " + studentId);
            } else {
                System.out.println("Nie znaleziono studenta o indeksie: " + studentId + " lub przedmiotu o id: " + subjectId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateGradeForStudent(int studentId, int subjectId, Float newGradeValue) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();

            String jpql = "SELECT sg FROM StudentGrade sg WHERE sg.student.id = :studentId AND sg.subject.id = :subjectId";
            TypedQuery<StudentGrade> query = entityManager.createQuery(jpql, StudentGrade.class);
            query.setParameter("studentId", studentId);
            query.setParameter("subjectId", subjectId);

            try {
                StudentGrade studentGrade = query.getSingleResult();

                if (newGradeValue == 0f) {
                    studentGrade.setGrade(null);
                } else {
                    String gradeJpql = "SELECT g FROM Grade g WHERE g.value = :gradeValue";
                    TypedQuery<Grade> gradeQuery = entityManager.createQuery(gradeJpql, Grade.class);
                    gradeQuery.setParameter("gradeValue", newGradeValue);

                    Optional<Grade> grade = gradeQuery.getResultList().stream().findFirst();

                    if (grade.isEmpty()) {
                        System.out.println("Podana ocena nie jest możliwa do wpisania do systemu.");
                        return;
                    }

                    studentGrade.setGrade(grade.get());
                }

                entityManager.getTransaction().commit();
                System.out.println("Zaktualizowano ocenę dla przedmiotu o id: " + subjectId + " dla studenta o indeksie: " + studentId + " na ocene: " + newGradeValue);
            } catch (NoResultException e) {
                System.out.println("Nie znaleziono oceny do zaktualizowania dla studenta o indeksie: " + studentId + " i przedmiotu o id: " + subjectId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
