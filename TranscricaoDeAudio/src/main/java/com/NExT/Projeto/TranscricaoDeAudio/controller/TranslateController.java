package com.NExT.Projeto.TranscricaoDeAudio.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.NExT.Projeto.TranscricaoDeAudio.service.Traduzir;

@RestController
@CrossOrigin("*")
public class TranslateController {
    
    Traduzir phrase = new Traduzir();

    @RequestMapping(value = "/toenglish/{text}", method = RequestMethod.GET)
    public String ptToEn(@PathVariable String text) {
        return phrase.Translation(text, "en");
    }
        
    @RequestMapping(value = "/toportuguese/{text}", method = RequestMethod.GET)
    public String EnToPt(@PathVariable String text) {
        return phrase.Translation(text, "pt");
    }

    @RequestMapping(value = "/idioma/{text}/{target}", method = RequestMethod.GET)
    public String translateText(@PathVariable String text, @PathVariable String target) {
        return phrase.Translation(text, target);
    }
}
