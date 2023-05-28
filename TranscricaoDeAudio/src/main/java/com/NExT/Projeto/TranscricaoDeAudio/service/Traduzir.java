package com.NExT.Projeto.TranscricaoDeAudio.service;

import com.google.cloud.translate.Detection;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.cloud.translate.Translate.TranslateOption;

public class Traduzir  {

    public String Translation (String text, String target) {
        Translate translate = TranslateOptions.getDefaultInstance().getService();
        Detection detection = translate.detect(text);
        String detectedIdioma = detection.getLanguage();
        Translation translation = 
        translate.translate(text, 
        TranslateOption.sourceLanguage(detectedIdioma),
        TranslateOption.targetLanguage(target));
        return translation.getTranslatedText().replace("&#39;", "'");        
    }
    
}
