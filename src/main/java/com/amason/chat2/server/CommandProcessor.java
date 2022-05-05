package com.amason.chat2.server;

import com.amason.chat2.message.Message;

import java.io.BufferedWriter;
import java.util.List;

public interface CommandProcessor {
    String process(BufferedWriter writer, List<Message> messages, Message message);
}
