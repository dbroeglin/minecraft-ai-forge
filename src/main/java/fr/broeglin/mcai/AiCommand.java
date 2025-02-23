package fr.broeglin.mcai;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.logging.LogUtils;
import dev.langchain4j.model.azure.AzureOpenAiChatModel;
import net.minecraft.ChatFormatting;
import dev.langchain4j.model.chat.ChatLanguageModel;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringUtil;

import org.slf4j.Logger;

public class AiCommand {

    private static final Logger LOGGER = LogUtils.getLogger();

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(Commands.literal("ai")
                .then(Commands.argument("question", StringArgumentType.string())
                        .executes(context -> {
                            String question = StringArgumentType.getString(context, "question");

                            LOGGER.info("AI: {}", question);

                            String key = System.getenv("AZURE_OPENAI_KEY");
                            String endpoint = System.getenv("AZURE_OPENAI_ENDPOINT");
                            String deploymentName = System.getenv("AZURE_OPENAI_DEPLOYMENT_NAME");

                            if (StringUtil.isBlank(key) || StringUtil.isBlank(endpoint) || StringUtil.isBlank(deploymentName)) {
                                context.getSource().sendFailure(
                                        Component.literal("Azure OpenAI credentials are not set. Please set the following environment variables: AZURE_OPENAI_KEY, AZURE_OPENAI_ENDPOINT, AZURE_OPENAI_DEPLOYMENT_NAME"));
                                return 0; // Command failed
                            }

                            ChatLanguageModel model = AzureOpenAiChatModel.builder()
                                    .apiKey(key)
                                    .deploymentName(deploymentName)
                                    .endpoint(endpoint)
                                    .temperature(0.3)
                                    .logRequestsAndResponses(true)
                                    .build();
                            String response = model
                                    .generate("Provide a short answer (max 250 characters) to the following question: "
                                            + question);

                            context.getSource().sendSuccess(
                                    () -> Component.literal("AI: " + response)
                                            .withStyle(ChatFormatting.ITALIC),
                                    true);
                            return 1; // Command was executed successfully
                        })));
    }
}
