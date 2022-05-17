package com.amason.chat2.server;

import com.amason.chat2.message.Message;

import java.io.BufferedWriter;

public interface CommandProcessor {
    String process(BufferedWriter writer, MessageDatabase messages, Message message);
}
