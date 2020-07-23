package com.geekbrains.lesson1;


import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Connection {

    private final Socket socket;
    private final Thread thread;
    private final DataInputStream in;
    private final DataOutputStream out;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream((socket.getOutputStream()));
        thread = new Thread(new Runnable() {
            public void run() {
                try {
                    while (!thread.isAlive()) {
                        Scanner sc = new Scanner(System.in);
                        sendFile(new File("C:\\Courses\\cloud\\hw.bmp"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public synchronized void sendFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        long size = file.length();
        int count = (int) (size / 8192) / 10, readBuckets = 0;
        // /==========/
        try (DataOutputStream os = new DataOutputStream(socket.getOutputStream())) {
            byte[] buffer = new byte[8192];
            os.writeUTF(file.getName());
            System.out.print("/");
            while (is.available() > 0) {
                int readBytes = is.read(buffer);
                readBuckets++;
                if (readBuckets % count == 0) {
                    System.out.print("=");
                }
                os.write(buffer, 0, readBytes);
            }
            System.out.println("/");
        }
    }
}
