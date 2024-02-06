package server;

import client.controllers.StudentListController;
import db.entities.Operations;
import db.entities.Student;
import db.entities.Subject;
import db.repositories.StudentRepository;
import db.repositories.SubjectRepository;
import db.entities.StudentGrade;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class serverApp {
    private static final int MAX_CLIENTS = 3;
    private static final StudentRepository studentRepository = new StudentRepository();
    private static final SubjectRepository subjectRepository = new SubjectRepository();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_CLIENTS);

        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Serwer uruchomiony, oczekiwanie na połączenia...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Nowe połączenie od klienta: " + socket.getInetAddress());

                executorService.execute(() -> handleClient(socket));
            }
        } catch (IOException e) {
            System.out.println("Błąd serwera: " + e.getMessage());
        }
    }

    public static void handleOperation(Operations op, ObjectInputStream input, ObjectOutputStream output) throws IOException, ClassNotFoundException {
        if(op == Operations.SHOW_STUDENTS){
            List<Student> allStudents = studentRepository.getAllStudents();
            allStudents.forEach(System.out::println);
            output.writeObject(allStudents);
        }
        else if (op == Operations.ADD_STUDENT){
            Student newStudent = (Student) input.readObject();
            studentRepository.addStudent(newStudent);

            List<Student> allStudents = studentRepository.getAllStudents();
            allStudents.forEach(System.out::println);
//            output.writeObject(allStudents);
        }
        else if (op == Operations.DELETE_STUDENT) {
            int index = (int) input.readObject();
            studentRepository.removeStudent(index);

            List<Student> allStudents = studentRepository.getAllStudents();
            allStudents.forEach(System.out::println);
//            output.writeObject(allStudents);
        }
        else if(op == Operations.SHOW_SUBJECTS) {
            List<Subject> allSubjects = subjectRepository.getAllSubjects();
            allSubjects.forEach(System.out::println);
            output.writeObject(allSubjects);
        }
        else if(op == Operations.ADD_SUBJECT) {
            Subject newSubject = (Subject) input.readObject();
            subjectRepository.addSubject(newSubject);

            List<Subject> allSubjects = subjectRepository.getAllSubjects();
            allSubjects.forEach(System.out::println);
//            output.writeObject(allSubjects);
        }
        else if (op == Operations.DELETE_SUBJECT) {
            String name = (String) input.readObject();
            subjectRepository.removeSubject(name);

            List<Subject> allSubjects = subjectRepository.getAllSubjects();
            allSubjects.forEach(System.out::println);
//            output.writeObject(allSubjects);
        }
    }

    private static void handleClient(Socket socket) {
        ObjectInputStream in;
        ObjectOutputStream out;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e);
            return;
        }

        while (true) {
            try {
                Operations op = (Operations) in.readObject();
                System.out.println(op);
                handleOperation(op, in, out);

//                in.close();
//                out.close();
//                socket.close();
            } catch(IOException e ){
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
