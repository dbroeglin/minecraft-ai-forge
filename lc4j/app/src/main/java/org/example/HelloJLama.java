package org.example;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.jlama.JlamaChatModel;

public class HelloJLama {
    public static void main(String[] args) {
        ChatLanguageModel model = JlamaChatModel.builder()
                .modelName("tjake/Mistral-7B-Instruct-v0.3-JQ4")
                .build();

        String response = model.generate("How do I get grilled chicken in Minecraft?");
        System.out.println(response);
    }
}
