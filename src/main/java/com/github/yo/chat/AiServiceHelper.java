package com.github.yo.chat;

import com.github.yo.chat.guardrail.SafeInputGuardrail;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.guardrail.InputGuardrails;

import java.util.List;

@InputGuardrails({SafeInputGuardrail.class})
public interface AiServiceHelper {

    /**
     * 简单的文本对话
     *
     */
    @SystemMessage(fromResource = "system-prompt.txt")
    String simpleTextChat(String userMessage);

    /**
     * 结构化返回值的文本对话
     *
     */
    @SystemMessage(fromResource = "system-prompt.txt")
    JsonResp jsonChat(UserMessage userMessage);

    /**
     * 结构化返回值
     */
    record JsonResp(String name, List<String> suggestions) {
    }

    @SystemMessage(fromResource = "system-prompt.txt")
    Result<String> ragChat(UserMessage userMessage);
}
