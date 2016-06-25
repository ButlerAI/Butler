package com.nohowdezign.butler.processing;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author Noah Howard
 */
public class LanguageProcessorTest {
    private LanguageProcessor languageProcessor;

    @Before
    public void setUp() throws Exception {
        languageProcessor = new LanguageProcessor();
    }

    @Test
    public void testTagPartsOfSpeech() {
        String result = "";
        try {
            result = languageProcessor.tagPartsOfSpeech("The quick brown fox jumps over the lazy Butler.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("The_DT quick_JJ brown_JJ fox_NN jumps_NNS over_IN the_DT lazy_JJ Butler._.", result);
    }

    @Test
    public void testGetVerbsFromSentence() {
        String result = "";
        try {
            result = languageProcessor.getPartOfSpeechFromSentence("The quick brown fox kills the lazy Butler.", "V");
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("kills", result);
    }

    @Test
    public void testGetMultipleVerbsFromSentence() {
        String result = "";
        try {
            result = languageProcessor.getPartOfSpeechFromSentence("When you kill someone, have you done the killing?", "V");
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("kill have done", result);
    }

    @Test
    public void testNormalizeSentence() {
        String result = "";
        try {
            result = languageProcessor.normalizeSentence("What is the weather like today?");
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("weather like today?", result);
    }

    @Test
    public void testGetSentenceSubject() {
        String result = "";
        try {
            result = languageProcessor.normalizeSentence("Where is the nearest tennis court?");
            result = languageProcessor.getPartOfSpeechFromSentence(result, "NN");
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("weather", result);
    }

}