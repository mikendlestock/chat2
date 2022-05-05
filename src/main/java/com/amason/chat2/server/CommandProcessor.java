package com.amason.chat2.server;

import java.io.BufferedWriter;

public interface CommandProcessor {
    String process(BufferedWriter writer, StringBuilder messages, String command);
}
