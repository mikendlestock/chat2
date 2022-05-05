package com.amason.chat2.client;


import com.amason.chat2.message.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Client {
    //add message

    private static Long lastUpdate = 0L;

    static List<String> messages = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        updateMessages();
        var scannerName = new Scanner(System.in);

        System.out.println("Введите имя");

        var name = scannerName.next();

        while (true) {
            var scanner = new Scanner(System.in);
            var text = scanner.nextLine();


            var clientSocket = new Socket("127.0.0.1", 8000);
            try (
                    var writer = new BufferedWriter(
                            new OutputStreamWriter(
                                    clientSocket.getOutputStream(), Charset.forName("UTF-8")));

                    var reader = new BufferedReader(
                            new InputStreamReader(
                                    clientSocket.getInputStream(), Charset.forName("UTF-8")));
            ) {

                var localDateTime = LocalDateTime.now();

                var message = new Message();
                message.setName(name);
                message.setText(text);
                message.setType("SEND");

                var instant = localDateTime.atZone(ZoneId.of("Europe/Paris")).toInstant();
                message.setDateAndTimeMilliSec(instant.toEpochMilli());

                var mapper = new ObjectMapper();

                var messageJson = mapper.writeValueAsString(message);

                writer.write(messageJson);
                writer.newLine();
                writer.flush();

                clientSocket.close();
            }
            updateMessages();
        }
    }

    static void updateMessages() throws IOException {
        var clientSocket = new Socket("127.0.0.1", 8000);
        try (
                var writer = new BufferedWriter(
                        new OutputStreamWriter(
                                clientSocket.getOutputStream(), Charset.forName("UTF-8")));

                var reader = new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream(), Charset.forName("UTF-8")));
        ) {

            var localDateTime = LocalDateTime.now();

            var message = new Message();
            message.setType("GET");

            var instant = localDateTime.atZone(ZoneId.of("Europe/Paris")).toInstant();

            var updateTime = instant.toEpochMilli();

            final Long updated = updateTime;

            message.setDateAndTimeMilliSec(lastUpdate);

            var mapper = new ObjectMapper();

            var messageJson = mapper.writeValueAsString(message);
            writer.write(messageJson);

            lastUpdate = updated;
            writer.newLine();
            writer.flush();
            var response = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            System.out.println(response);

            clientSocket.close();
        }
    }
}

//class Thread1 extends Thread {
//    @Override
//    public void run() {
//        int x = 0;
//        while (x < 20) {
//            try {
//                var clientSocket = new Socket("127.0.0.1", 8001);
//                var reader = new BufferedReader(
//                        new InputStreamReader(
//                                clientSocket.getInputStream()));
//                var response = reader.lines().collect(Collectors.toList());
//                System.out.println(response);
//
//                Thread.sleep(3599L);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            x++;
//            System.out.println("thread1 is working");
//        }
//    }
//}
