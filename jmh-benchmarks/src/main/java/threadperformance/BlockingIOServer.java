package threadperformance;

//Blocking I/O (BIO) — Example
//Server using traditional Socket (blocking)
import java.io.*;
import java.net.*;

public class BlockingIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("BIO Server started on port 8080");

        while (true) {
            Socket clientSocket = serverSocket.accept(); // 🚫 blocking
            new Thread(() -> handleClient(clientSocket)).start();
        }
    }

    private static void handleClient(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String line;
            while ((line = in.readLine()) != null) { // 🚫 blocking read
                System.out.println("Received: " + line);
                out.println("Echo: " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//Client
/*
import java.io.*;
import java.net.*;

public class BlockingIOClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8080);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader response = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        System.out.println("Enter text:");
        String input;
        while ((input = reader.readLine()) != null) {
            writer.println(input);
            System.out.println("Server: " + response.readLine());
        }
    }
}
*/
/*Behavior:

Each connection → new thread.
Each thread blocks on read() until input arrives.
Fine for 10s or 100s of clients, bad for thousands (CPU wasted waiting).
*/
// Non-Blocking I/O (NIO) — Example
/*
NIO uses:
   Channels: Replaces streams
   Buffers: Data containers
   Selectors: Monitor multiple channels (clients) at once
*/
/*
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.*;
import java.nio.channels.*;
import java.util.Iterator;

public class NonBlockingServer {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(8080));
        server.configureBlocking(false); // non-blocking mode
        server.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("NIO Server started on port 8080...");

        ByteBuffer buffer = ByteBuffer.allocate(256);

        while (true) {
            selector.select(); // blocks until any channel is ready
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

            while (keys.hasNext()) {
                SelectionKey key = keys.next();
                keys.remove();

                if (key.isAcceptable()) {
                    SocketChannel client = server.accept();
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ);
                    System.out.println("Client connected: " + client.getRemoteAddress());
                } else if (key.isReadable()) {
                    SocketChannel client = (SocketChannel) key.channel();
                    buffer.clear();
                    int bytesRead = client.read(buffer);
                    if (bytesRead == -1) {
                        client.close();
                    } else {
                        buffer.flip();
                        client.write(buffer); // Echo back
                    }
                }
            }
        }
    }
}

Client (Same as BIO client works)
import java.io.*;
import java.net.*;

public class SimpleClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8080);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader response = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        System.out.println("Enter message:");
        String line;
        while ((line = reader.readLine()) != null) {
            writer.println(line);
            System.out.println("Server: " + response.readLine());
        }
    }
}

*/