package com.amason.chat2.server;

import com.amason.chat2.message.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) throws Exception {
        System.out.println("------- Server started ---------");

        var mapper = new ObjectMapper();
        int count = 0;
        var serverSocket = new ServerSocket(8000);
        List<Message> messagesList = new ArrayList<>();

        while (true) {
            var clientSocket = serverSocket.accept();

            var writer = new BufferedWriter(
                    new OutputStreamWriter(
                            clientSocket.getOutputStream()));

            var reader = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));

            var request = reader.readLine();
            var message = mapper.readValue(request, Message.class);
            if (message.getType().equals("SEND")) {
                System.out.println("----- client with message accepted: " + (++count));
            }
            var dispatcher = new CommandProcessorDispatcher();

            dispatcher.processCommand(message, messagesList, writer);
            reader.close();
            clientSocket.close();
        }
    }

}
