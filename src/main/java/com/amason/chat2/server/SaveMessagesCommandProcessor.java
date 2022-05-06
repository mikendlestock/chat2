package com.amason.chat2.server;

import com.amason.chat2.message.Message;

import java.io.BufferedWriter;
import java.util.List;

public class SaveMessagesCommandProcessor implements CommandProcessor {

    @Override
    public String process(BufferedWriter writer, List<Message> messages, Message message) {
        messages.add(message);
        return "ok";
    }
}
