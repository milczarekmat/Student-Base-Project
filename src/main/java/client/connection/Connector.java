package client.connection;

import db.entities.Operations;
import db.entities.Student;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Connector {

    private static boolean loading;

    private static boolean connected;

    private static Socket socket = null;
    private static DataInputStream input = null;
    private static ObjectOutputStream out = null;

    public static boolean isConnected() {
        return connected;
    }

    public static void connect() {
        try {
            loading = true;
            connected = false;
            socket = new Socket("localhost", 8080);
            out =  new ObjectOutputStream(socket.getOutputStream());
            input = new DataInputStream(System.in);
            System.out.println("Connected");
//
//
//
        }
        catch (UnknownHostException u) {
//            System.out.println(u);
            loading = false;
            return;
        }
        catch (IOException i) {
//            System.out.println(i);
            loading = false;
            return;
        }
//        catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
        loading = false;
        connected = true;
    }

    public void disconnect() {
        try {
            input.close();
            out.close();
            socket.close();
        }
        catch (IOException i) {
            loading = false;
            return;
        }
        loading = false;
        connected = false;
        socket = null;
        System.out.println("Disconnected");
    }

    public ArrayList<Student> getStudents() {
//        System.out.println("wyslij studentow");
        try {
            out.writeObject(Operations.SHOW_STUDENTS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ObjectInputStream in;
        ArrayList<Student> receivedStudenci;
        try {
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            receivedStudenci = (ArrayList<Student>) in.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        receivedStudenci.forEach(System.out::println);
        return receivedStudenci;
    }

    public void addStudent(Student student) {
        try {
            out.writeObject(Operations.ADD_STUDENT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            out.writeObject(student);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
