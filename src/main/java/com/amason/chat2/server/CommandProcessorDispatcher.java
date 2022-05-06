package com.amason.chat2.server;

import com.amason.chat2.message.Message;

import java.io.BufferedWriter;
import java.util.List;
import java.util.Map;

public class CommandProcessorDispatcher {
    private static final String GET = "GET";
    private static final String SEND = "SEND";

    Map<String, CommandProcessor> commandProcessorMap = Map.of(
            GET, new GetAllMessagesCommandProcessor(),
            SEND, new SaveMessagesCommandProcessor()
    );

    public String processCommand(Message message, List<Message> messages, BufferedWriter writer) {
        for (String s : commandProcessorMap.keySet()) {
            if (message.getType().equals(s)) {
                var commandProcessor = commandProcessorMap.get(s);
                return commandProcessor.process(writer, messages, message);
            }
        }
        throw new RuntimeException("Command not found");
    }
}
