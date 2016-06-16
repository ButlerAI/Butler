package com.nohowdezign.butler.processing;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
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
            result = languageProcessor.getVerbsFromSentence("The quick brown fox kills the lazy Butler.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("kills ", result);
    }

    @Test
    public void testGetMultipleVerbsFromSentence() {
        String result = "";
        try {
            result = languageProcessor.getVerbsFromSentence("When you kill someone, have you done the killing?");
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("kill have done ", result);
    }

    @Test
    public void testNormalizeSentance() {
        String result = "";
        try {
            result = languageProcessor.normalizeSentance("We did the thing");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assertEquals("thing", result);
    }

}