package com.amason.chat2.server;

import com.amason.chat2.message.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;

public class Server {
    public static void main(String[] args) throws Exception {
        System.out.println("------- Server started ---------");

        var mapper = new ObjectMapper();
        int count = 0;
        var serverSocket = new ServerSocket(8000);

        var messageDatabase  = MessageDatabase.INSTANCE;

        while (true) {
            var clientSocket = serverSocket.accept();

            var writer = new BufferedWriter(
                    new OutputStreamWriter(
                            clientSocket.getOutputStream()));

            var reader = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));

            var messageJson = reader.readLine();
            var message = mapper.readValue(messageJson, Message.class);
            if (message.getType().equals("SEND")) {
                System.out.println(++ count + " -----accepted__ client: " + message.getName() + " with message: " + message.getText());
            }
            var dispatcher = new CommandProcessorDispatcher();

            dispatcher.processCommand(message, messageDatabase, writer);

            writer.newLine();
            writer.flush();
            writer.close();
            reader.close();
            clientSocket.close();
        }
    }

}
