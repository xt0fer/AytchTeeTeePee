package rocks.zipcode;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;


// Show how Sockets work. Lower level than Main class.
class SimpleHTTPServer {
    public static void main(String args[]) throws IOException {
        ServerSocket server = new ServerSocket(8001);
        System.out.println("Listening for connection on port 8080 ....");
        while (true) {
            try (Socket socket = server.accept()) {
                Date today = new Date();
                String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;
                socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
            }
        }
    }
}
