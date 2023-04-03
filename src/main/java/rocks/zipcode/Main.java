package rocks.zipcode;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Main {
    static final int port = 8000;
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(Main.port), 0);
        server.createContext("/", new RootHandler());
        server.createContext("/test", new TestHandler());
        server.createContext("/foo", new FooHandler());
        server.createContext("/bar", new BarHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "Zip Code Rocks!";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    static class TestHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "Testing! Testing! Zip Code Rocks!";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    static class FooHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "<HTML><BODY><h1>HTML</h1><p>This is a proper paragraph of text.</p></BODY></HTML>";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class BarHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = MakeBody();
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static String MakeBody() {
        StringBuilder sb = new StringBuilder();
        String css = "body { font-family: Arial, sans-serif; line-height: 1.6; color: #222; " +
                "max-width: 40rem; padding: 2rem; margin: auto; background: #fafafa; }" +
                "a { color: #2ECC40; } h1, h2, strong { color: #111; }";

        sb.append("<HTML>");
        sb.append("<style>");
        sb.append(css);
        sb.append("</style>");
        sb.append("<BODY><h1>HTML Title</h1>");
        sb.append("<p>");
        for(int i = 0; i<10; i++) {
            sb.append("This is a proper paragraph of text. ");
        }
        sb.append("</p>");
        sb.append("</BODY></HTML>");

        return sb.toString();
    }

}

