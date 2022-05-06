package com.amason.chat2.server;

import com.amason.chat2.message.Message;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

public class GetAllMessagesCommandProcessor implements CommandProcessor {
    private static final String SEPARATOR = ": ";

    @Override
    public String process(BufferedWriter writer, List<Message> messagesList, Message message) {

        try {
            var newMessagesAsString = findNewMessagesAsString(messagesList, message);
            System.out.println("meesages from server: " + newMessagesAsString);
            writer.write(newMessagesAsString);
            return "ok";
        } catch (IOException e) {
            throw new RuntimeException("error during process GetAllMessagesCommandProcessor");
        }
    }

    private String findNewMessagesAsString(List<Message> messagesList, Message message) {
        var listWithNewMessagesForSend = messagesList.stream()
                .peek(x-> System.out.println("mes sek: "+ x.getDateAndTimeMilliSec() + "\nmessage sek: " + message.getDateAndTimeMilliSec()))
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
