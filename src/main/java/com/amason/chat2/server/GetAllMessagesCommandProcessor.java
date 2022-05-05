package com.amason.chat2.server;

import com.amason.chat2.message.Message;
import org.w3c.dom.ls.LSOutput;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

public class GetAllMessagesCommandProcessor implements CommandProcessor {
    @Override
    public String process(BufferedWriter writer, List<Message> messagesList, Message message) {
        try {
            var listWithNewMessagesForSend = messagesList.stream()
                    .filter(mes->mes.getDateAndTimeMilliSec() > message.getDateAndTimeMilliSec())
                    .collect(Collectors.toList());

            System.out.println("listWith new Messages " + listWithNewMessagesForSend);

            var messagesSB = new StringBuilder();

            for (Message m : listWithNewMessagesForSend) {
                messagesSB.append(m.getName() + ": " + m.getText() + "\n");
            }
            writer.write(messagesSB.toString());
            writer.newLine();
            writer.flush();
            writer.close();

            return "ok";
        } catch (IOException e) {
            throw new RuntimeException("error during process GetAllMessagesCommandProcessor");
        }
    }
}
