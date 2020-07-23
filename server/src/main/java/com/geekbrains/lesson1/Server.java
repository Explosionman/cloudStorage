package com.geekbrains.lesson1;

import java.io.*;
import java.net.Socket;

public class Server implements Runnable {

    private static Socket clientSocket;

    public Server(Socket client) {
        this.clientSocket = client;
    }

    @Override
    public void run() {
        try {
            DataOutputStream os = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream is = new DataInputStream(clientSocket.getInputStream());

            while (!clientSocket.isClosed()) {
                String fileName = is.readUTF();
                System.out.println("fileName: " + fileName);
                File file = new File("./common/server/" + fileName);
                file.createNewFile();
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    byte[] buffer = new byte[8192];
                    while (true) {
                        int r = is.read(buffer);
                        if (r == -1) break;
                        fos.write(buffer, 0, r);
                    }
                }
                System.out.println("File uploaded!");
                os.flush();
            }
            System.out.println("Client disconnected");
            is.close();
            os.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


