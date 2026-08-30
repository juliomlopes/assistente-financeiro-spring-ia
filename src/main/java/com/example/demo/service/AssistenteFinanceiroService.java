package com.example.demo.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class AssistenteFinanceiroService {

    private final ChatClient chatClient;
    private final OpenAiAudioTranscriptionModel transcriptionModel;

    public AssistenteFinanceiroService(ChatClient.Builder builder, OpenAiAudioTranscriptionModel transcriptionModel) {
        this.transcriptionModel = transcriptionModel;
        this.chatClient = builder
                .defaultSystem("Você é um assistente financeiro direto e eficiente. Use as ferramentas para registrar transações ou consultar saldo. Não invente dados.")
                .defaultFunctions("registrarTransacao", "consultarSaldo")
                .build();
    }

    public String processarTexto(String comandoUsuario) {
        return chatClient.prompt()
                .user(comandoUsuario)
                .call()
                .content();
    }

    public String processarAudio(Resource arquivoAudio) {
        String textoTranscrito = transcriptionModel.call(arquivoAudio);

        System.out.println("🔊 TEXTO EXTRAÍDO DO ÁUDIO: " + textoTranscrito);

        return processarTexto(textoTranscrito);
    }
}