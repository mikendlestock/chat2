package com.amason.chat2.server;

import java.io.BufferedWriter;

public class SaveMessagesCommandProcessor implements CommandProcessor {
    @Override
    public String process(BufferedWriter writer, StringBuilder messages, String command) {
        try {
            messages.append(command.substring(5)).append(System.lineSeparator());
//            Thread.sleep(3000L);
            System.out.println("messages is ---- " + messages.toString());
            writer.write(messages.toString());
            writer.newLine();
            writer.flush();
            writer.close();
            return "ok";
        } catch (Exception e) {
            return "error";
        }
    }
}
