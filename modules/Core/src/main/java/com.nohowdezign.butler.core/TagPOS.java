package com.nohowdezign.butler.core;

import com.nohowdezign.butler.modules.annotations.Initialize;
import com.nohowdezign.butler.modules.annotations.ModuleLogic;
import com.nohowdezign.butler.processing.LanguageProcessor;

import java.io.IOException;

/**
 * Created by noah on 6/25/16.
 */
@ModuleLogic(subjectWord = "tag")
public class TagPOS {

    @Initialize
    public void run(String query) throws IOException {
        String response = query.replaceAll("tag ", "");
        LanguageProcessor languageProcessor = new LanguageProcessor();
        System.out.println(languageProcessor.tagPartsOfSpeech(response));
    }

}
