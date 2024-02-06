package server;

import db.entities.Grade;
import db.entities.Operations;
import db.entities.Student;
import db.entities.Subject;
import db.helperClasses.ManageInfo;
import db.helperClasses.SubjectMeanInfo;
import db.repositories.StudentRepository;
import db.repositories.SubjectRepository;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
        switch (op) {
            case SHOW_STUDENTS:
                List<Student> allStudents = studentRepository.getAllStudents();
                allStudents.forEach(System.out::println);
                output.writeObject(allStudents);
                break;
            case ADD_STUDENT:
                Student newStudent = (Student) input.readObject();
                studentRepository.addStudent(newStudent);

                allStudents = studentRepository.getAllStudents();
                allStudents.forEach(System.out::println);
                break;
            case DELETE_STUDENT:
                int index = (int) input.readObject();
                studentRepository.removeStudent(index);

                allStudents = studentRepository.getAllStudents();
                allStudents.forEach(System.out::println);
                break;
            case SHOW_SUBJECTS:
                List<Subject> allSubjects = subjectRepository.getAllSubjects();
                allSubjects.forEach(System.out::println);
                output.writeObject(allSubjects);
                break;
            case ADD_SUBJECT:
                Subject newSubject = (Subject) input.readObject();
                subjectRepository.addSubject(newSubject);

                allSubjects = subjectRepository.getAllSubjects();
                allSubjects.forEach(System.out::println);
                break;
            case DELETE_SUBJECT:
                String name = (String) input.readObject();
                subjectRepository.removeSubject(name);

                allSubjects = subjectRepository.getAllSubjects();
                allSubjects.forEach(System.out::println);
                break;
            case GET_SUBJECTS_WITH_GRADES:
                List<Subject> subjects = subjectRepository.getSubjectsWithGrades();

                List<SubjectMeanInfo> subjectsWithMeans = new ArrayList<>();

                subjects.forEach(subject -> {
                    String subjectName = subject.getName();
                    final Float[] mean = {0f};
                    subject.getStudentGrades().forEach(studentGrade -> {
                        Grade grade = studentGrade.getGrade();
                        if (grade != null) {
                            mean[0] += grade.getValue();
                        }
                    });
                    mean[0] /= subject.getStudentGrades().size();

                    subjectsWithMeans.add(new SubjectMeanInfo(subjectName, mean[0]));
                });

                output.writeObject(subjectsWithMeans);
                break;
            case SHOW_STUDENTS_WITH_GRADES:
                List<Student> studentsWithGrades = studentRepository.getAllStudentsWithGrades();
                output.writeObject(studentsWithGrades);
                break;
            case EDIT_STUDENT_GRADE:
                ManageInfo gradeInfo = (ManageInfo) input.readObject();
                subjectRepository.updateGradeForStudent(gradeInfo.getStudentId(),
                        gradeInfo.getSubjectId(), gradeInfo.getGradeValue());

                List<Student> allStudentsWithGrades = studentRepository.getAllStudentsWithGrades();

                output.writeObject(allStudentsWithGrades);
                break;
            case REMOVE_SUBJECT_FOR_STUDENT:
                ManageInfo manageInfo = (ManageInfo) input.readObject();
                subjectRepository.removeSubjectForStudent(manageInfo.getStudentId(),
                        manageInfo.getSubjectId());

                List<Student> allStudentWithGrades = studentRepository.getAllStudentsWithGrades();

                output.writeObject(allStudentWithGrades);
                break;
            case ADD_SUBJECT_FOR_STUDENT:
                ManageInfo info = (ManageInfo) input.readObject();

                subjectRepository.addSubjectForStudent(info.getStudentId(), info.getSubjectId());

                List<Student> allStudentsWithGrades1 = studentRepository.getAllStudentsWithGrades();

                output.writeObject(allStudentsWithGrades1);
                break;
            case FIND_STUDENT:
                Integer idx = (Integer) input.readObject();
                Student student = studentRepository.getStudentByIdWithGrades(idx);

            default:
                System.out.println("Nieznana operacja");
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
