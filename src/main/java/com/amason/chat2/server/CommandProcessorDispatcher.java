package com.amason.chat2.server;

import java.io.BufferedWriter;
import java.net.Socket;
import java.util.Map;

public class CommandProcessorDispatcher {
    private static final String GET = "GET";
    private static final String SEND = "SEND";

    Map<String, CommandProcessor> commandProcessorMap = Map.of(
            GET, new GetAllMessagesCommandProcessor(),
            SEND, new SaveMessagesCommandProcessor()
    );

    public String processCommand(String command, StringBuilder messages, BufferedWriter writer) {
        for (String s : commandProcessorMap.keySet()) {
            if (command.startsWith(s)) {
                var commandProcessor = commandProcessorMap.get(s);
                return commandProcessor.process(writer, messages, command);
            }
        }
        throw new RuntimeException("Command not found");
    }
}
