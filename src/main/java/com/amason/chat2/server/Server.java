package com.amason.chat2.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;

public class Server {
    public static void main(String[] args) throws Exception {
        int count = 0;
        var serverSocket = new ServerSocket(8000);

        System.out.println("------- Server started ---------");

        StringBuilder messages = new StringBuilder();

        while (true) {
            var clientSocket = serverSocket.accept();

            System.out.println("----- client accepted: " + (++count));

            var writer = new BufferedWriter(
                    new OutputStreamWriter(
                            clientSocket.getOutputStream()));

            var reader = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));

            System.out.println("Server step 0 ---- ");
            var request = reader.readLine();

            var dispatcher = new CommandProcessorDispatcher();
            dispatcher.processCommand(request, messages, writer);
//
//            System.out.println("Server step 1---");
//            messages.append(request).append(System.lineSeparator());
////            Thread.sleep(3000L);
//            System.out.println("messages is ---- " + messages.toString());
//            writer.write(messages.toString());
//            writer.newLine();
//            writer.flush();
//
//            writer.close();
            reader.close();
            clientSocket.close();
        }

    }

    private static boolean isGet(String request) {
        var fourLetters = request.substring(0,3);
        if (fourLetters.contains("get")) {
            return true;
        } else {
            return false;
        }
    }

}
