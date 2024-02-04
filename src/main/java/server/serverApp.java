package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import db.entities.Student;

public class serverApp {
    private static final int MAX_CLIENTS = 3;

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

    private static void handleClient(Socket socket) {
        ArrayList<Student> testStudenci = new ArrayList<>();
        while(true){
            try {
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                Student obj = new Student();
                testStudenci.add(obj);

                out.writeObject(testStudenci);
                out.close();
                socket.close();
            } catch(IOException e ){
                System.out.println(e);
            }
        }
    }
}
