package com.NExT.Projeto.TranscricaoDeAudio.service;

import javax.sound.sampled.*;

import java.io.*;
import java.nio.file.Path;

public class AudioConvert {
    public static void convertToWav(File audioFile) {
        try {
            // Obtém os formatos de entrada e saída
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat inputFormat = inputStream.getFormat();
            AudioFormat outputFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 
                                                      inputFormat.getSampleRate(), 
                                                      16, 
                                                      inputFormat.getChannels(), 
                                                      inputFormat.getChannels() * 2, 
                                                      inputFormat.getSampleRate(), 
                                                      false);
            // Converte o arquivo de entrada para o formato de saída
            AudioInputStream outputStream = AudioSystem.getAudioInputStream(outputFormat, inputStream);
            // Salva o arquivo de saída no formato .wav
            String file = audioFile.toString();
            File wavFile = new File(audioFile.getParent(), file);
            AudioSystem.write(outputStream, AudioFileFormat.Type.WAVE, wavFile);
        } catch (IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public void convertAudio(Path filename) {
        String file = filename.toString();
        File audioFile = new File(file);
        System.out.println("Passei aqui !!!" + file);
        convertToWav(audioFile);
        System.out.println("Agora aqui !!!" + audioFile + "e o tipo é: " + audioFile.getClass());
    }
}
