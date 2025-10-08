package com.github.yo.chat;

import com.github.yo.chat.guardrail.SafeInputGuardrail;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.guardrail.InputGuardrails;

@InputGuardrails({SafeInputGuardrail.class})
public interface CustomChatModelAiServiceApi {
    @SystemMessage(fromResource = "system-prompt.txt")
    Result<String> chat(UserMessage userMessage);
}
