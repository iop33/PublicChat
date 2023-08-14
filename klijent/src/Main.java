import java.io.*;
import java.net.Socket;

public class Main {

    public static final int PORT = 8999;


    public static void main(String[] args) {

        Socket socket=null;

        try {
            socket = new Socket("127.0.0.1", PORT);
            Thread thread=new Thread(new KlijentSlanjeThread(socket));
            Thread thread2=new Thread(new KlijentCetSobaThread(socket));
            thread.start();
            thread2.start();
            thread.join();
            thread2.join();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
