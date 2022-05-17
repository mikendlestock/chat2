package com.amason.chat2.server;

import com.amason.chat2.message.Message;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.stream.Collectors;

public class GetAllMessagesCommandProcessor implements CommandProcessor {
    private static final String SEPARATOR = ": ";

    @Override
    public String process(BufferedWriter writer, MessageDatabase messageDatabase, Message message) {

        try {
            var newMessagesAsString = findNewMessagesAsString(messageDatabase, message);
            writer.write(newMessagesAsString);
            return "ok";
        } catch (IOException e) {
            throw new RuntimeException("error during process GetAllMessagesCommandProcessor");
        }
    }

    private String findNewMessagesAsString(MessageDatabase messageDatabase, Message message) {
        var listWithNewMessagesForSend = messageDatabase.getMessageList().stream()
                .filter(mes->mes.getDateAndTimeMilliSec() > message.getDateAndTimeMilliSec())
                .collect(Collectors.toList());

        var messagesSB = new StringBuilder();

        for (Message m : listWithNewMessagesForSend) {
            messagesSB
                    .append(m.getName())
                    .append(SEPARATOR)
                    .append(m.getText())
                    .append(System.lineSeparator());
        }
        return messagesSB.toString();
    }
}
