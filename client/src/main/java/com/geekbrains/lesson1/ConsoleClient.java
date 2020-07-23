package com.geekbrains.lesson1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ConsoleClient {

    public static void main(String[] args) throws IOException {

        try (Socket s = new Socket("127.0.0.1", 8189)) {
            Connection connection = new Connection(s);
            System.out.println("Успешное подключение к серверу");
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("Введите имя файла");
                connection.sendFile(new File(sc.nextLine()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
