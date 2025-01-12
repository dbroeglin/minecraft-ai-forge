/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example;

import dev.langchain4j.model.azure.AzureOpenAiChatModel;
import dev.langchain4j.model.chat.ChatLanguageModel;
import com.azure.identity.DefaultAzureCredentialBuilder;

public class HelloAzureOpenAi {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println(new HelloAzureOpenAi().getGreeting());

        ChatLanguageModel model = AzureOpenAiChatModel.builder()
                .deploymentName(System.getenv("AZURE_OPENAI_DEPLOYMENT_NAME"))
                .endpoint(System.getenv("AZURE_OPENAI_ENDPOINT"))
                .tokenCredential(new DefaultAzureCredentialBuilder().build())
                .build();

        System.out.println(model.chat("Hello, how are you?"));

    }
}
