package com.amason.chat2.server;

import java.io.BufferedWriter;
import java.io.IOException;

public class GetAllMessagesCommandProcessor implements CommandProcessor {
    @Override
    public String process(BufferedWriter writer, StringBuilder messages, String command) {
        try {
            writer.write(messages.toString());
            writer.newLine();
            writer.flush();
            writer.close();
            return "ok";
        } catch (IOException e) {
            throw new RuntimeException("error during process GetAllMessagesCommandProcessor");
        }
    }
}
