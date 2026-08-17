package br.com.alura.screenmatch.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaChatGPT {
    public static String obterTraducao(String texto) {
            OpenAiService service = new OpenAiService("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");

            CompletionRequest requisicao = CompletionRequest.builder()
                    .model("gpt-3.5-turbo-instruct")
                    .prompt("traduza para o português o texto: " + texto)
                    .maxTokens(1000)
                    .temperature(0.7)
                    .build();

            var resposta = service.createCompletion(requisicao);
            return resposta.getChoices().get(0).getText();
    }
}

