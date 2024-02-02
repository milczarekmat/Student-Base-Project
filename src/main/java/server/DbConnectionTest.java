package server;

import db.entities.Student;
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

        Student student1 = new Student();

        student1.setId(124744327);
        student1.setName("Adam");
        student1.setSurname("Nowak");
        student1.setDepartment("Weeia");

        session.getTransaction().begin();

        session.persist(student1);

        session.getTransaction().commit();

    }
}

