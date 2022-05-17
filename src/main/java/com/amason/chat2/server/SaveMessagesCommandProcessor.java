package com.amason.chat2.server;

import com.amason.chat2.message.Message;
import java.io.BufferedWriter;

public class SaveMessagesCommandProcessor implements CommandProcessor {

    @Override
    public String process(BufferedWriter writer, MessageDatabase messages, Message message) {
        messages.addMessage(message);
        return "ok";
    }
}
