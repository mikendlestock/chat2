package com.amason.chat2.client;


import com.amason.chat2.message.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Client {

    private final static String sendType = "SEND";
    private final static String getType = "GET";

    private static final String host = "127.0.0.1";

//    private static final String host = "45.90.33.48";
    private static final int port = 8000;
    private static final int sleepSeconds = 2000;
    private static Long lastUpdate = 0L;

    public static void main(String[] args) throws Exception {
        System.out.println("Введите имя");
        var scannerName = new Scanner(System.in);
        var name = scannerName.nextLine();
        var thread = new Thread(
                () -> {
                    try {
                        updateMessages();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        thread.start();
        while (true) {
            var scanner = new Scanner(System.in);
            var text = scanner.nextLine();
            var clientSocket = new Socket(host, port);
            try (
                    var writer = new BufferedWriter(
                            new OutputStreamWriter(
                                    clientSocket.getOutputStream(), Charset.forName("UTF-8")));

            ) {
                var message = new Message();
                message.setName(name);
                message.setText(text);
                message.setType(sendType);
                message.setDateAndTimeMilliSec(Instant.now().toEpochMilli());

                var mapper = new ObjectMapper();
                var messageJson = mapper.writeValueAsString(message);

                writer.write(messageJson);
                writer.newLine();
                writer.flush();

                clientSocket.close();
            }
        }
    }

    static void updateMessages() throws IOException {
        while (true) {
            var clientSocket = new Socket(host, port);
            try (
                    var writer = new BufferedWriter(
                            new OutputStreamWriter(
                                    clientSocket.getOutputStream(), StandardCharsets.UTF_8));

                    var reader = new BufferedReader(
                            new InputStreamReader(
                                    clientSocket.getInputStream(), StandardCharsets.UTF_8));
            ) {

                var message = new Message();
                message.setType(getType);

                var updateTime = Instant.now().toEpochMilli();

                final Long updated = updateTime;

                message.setDateAndTimeMilliSec(lastUpdate);

                var mapper = new ObjectMapper();

                var messageJson = mapper.writeValueAsString(message);
                writer.write(messageJson);

                lastUpdate = updated;

                writer.newLine();
                writer.flush();
                var response = reader.lines().collect(Collectors.joining(System.lineSeparator()));
                if (!response.isEmpty()) {
                    System.out.print(response);
                }
                clientSocket.close();
                sleep();
            }
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(sleepSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
