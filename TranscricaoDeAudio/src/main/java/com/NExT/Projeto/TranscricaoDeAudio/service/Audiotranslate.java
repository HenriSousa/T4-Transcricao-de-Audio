package com.NExT.Projeto.TranscricaoDeAudio.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.speech.v1.LongRunningRecognizeMetadata;
import com.google.cloud.speech.v1.LongRunningRecognizeResponse;
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.protobuf.ByteString;


public class Audiotranslate {
    
    public String audioToEnglish(String filename) throws IOException, InterruptedException, ExecutionException {
        AudioConvert audioConvert = new AudioConvert();
        SpeechClient speechClient = SpeechClient.create();        
        Path path = Paths.get("upload/audio/" + filename);
        audioConvert.convertAudio(path);                
        byte[] data = Files.readAllBytes(path);
        ByteString audioBytes = ByteString.copyFrom(data);
        
        RecognitionConfig config = RecognitionConfig.newBuilder()
                                    .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                                    .setLanguageCode("pt-BR")                                                              
                                    .setAudioChannelCount(2)
                                    .build();
                                    
        RecognitionAudio audio = RecognitionAudio.newBuilder().setContent(audioBytes).build();
        OperationFuture<LongRunningRecognizeResponse, LongRunningRecognizeMetadata> 
        response = speechClient.longRunningRecognizeAsync(config, audio);
        
        while (!response.isDone()) {
            System.out.println("Esperando responder");
            Thread.sleep(10000);
        }    
       
        List<SpeechRecognitionResult> results = response.get().getResultsList();

        for (SpeechRecognitionResult result : results) {
            SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
            return alternative.getTranscript();
            
        }
        return null;
    }
}
