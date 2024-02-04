package connection;

import java.io.*;
import java.net.*;
public class Connector {

    private static boolean loading;

    private static boolean connected;

    private static Socket socket = null;
    private static DataInputStream input = null;
    private DataOutputStream out = null;

    public static void connect() {
        try {
            loading = true;
            connected = false;
            socket = new Socket("localhost", 8080);
            System.out.println("Connected");
        }
        catch (UnknownHostException u) {
            System.out.println(u);
            loading = false;
            return;
        }
        catch (IOException i) {
            System.out.println(i);
            loading = false;
            return;
        }
        loading = false;
        connected = true;
    }

}
