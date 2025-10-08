package com.github.yo.chat;

import dev.langchain4j.service.SystemMessage;

public interface AiServiceHelper {

    @SystemMessage(fromResource = "system-prompt.txt")
    String simpleTextChat(String userMessage);
}
