package com.NExT.Projeto.TranscricaoDeAudio.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.NExT.Projeto.TranscricaoDeAudio.service.Audiotranslate;
import com.NExT.Projeto.TranscricaoDeAudio.service.Traduzir;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/upload/arquivo", produces = {"application/json"})
@Slf4j
@CrossOrigin("*")
public class UploadArquivoController {

    private final String pathArquivos = "upload/audio/";
    Audiotranslate audioTranscricao = new Audiotranslate();
    Traduzir tradutor = new Traduzir();
    
    @PostMapping("/{target}")
    public ResponseEntity<String> salvarArquivo(@RequestParam("file") MultipartFile file, @PathVariable String target) throws IOException, InterruptedException, ExecutionException {
        log.info("Recebendo o arquivo: ", file.getOriginalFilename());
        String filename = new Date().getTime() + "-file." + extrairExtensao(file.getOriginalFilename());
        String path = pathArquivos + filename;
        File delArquivo = new File(path);
        
        log.info("Nome do arquivo: " + path);

        try {
            Files.copy(file.getInputStream(), Path.of(path), StandardCopyOption.REPLACE_EXISTING);
            String transcricao = audioTranscricao.audioToEnglish(filename);
            String translateTranscricao = tradutor.Translation(transcricao, target);
            delArquivo.delete();
            return new ResponseEntity<String>("{ \"mensagem\": \"Arquivo carregado com sucesso!\", \"traduction\": \""+ translateTranscricao +"\"}", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Erro ao processar arquivo", e);
            delArquivo.delete();
            return new ResponseEntity<String>("{ \"mensagem\": \"Erro ao carregar o arquivo!\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String extrairExtensao(String nomeArquivo) {
        int i = nomeArquivo.lastIndexOf(".");
        return nomeArquivo.substring(i + 1);
    }

}