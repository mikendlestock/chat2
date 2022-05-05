package com.amason.chat2.server;

import com.amason.chat2.message.Message;

import java.io.BufferedWriter;
import java.util.List;

public class SaveMessagesCommandProcessor implements CommandProcessor {
    @Override
    public String process(BufferedWriter writer, List<Message> messages, Message message) {
        try {
            messages.add(message);

            var sb = new StringBuilder();

            for (Message m : messages) {
                sb.append(m.getName() + ": " + m.getText() + "\n");
            }

            writer.write(sb.toString());
            writer.newLine();
            writer.flush();
            writer.close();

            return "ok";
        } catch (Exception e) {
            throw new RuntimeException("exception during process SaveMessagesCommandProcessor");
        }
    }
}
