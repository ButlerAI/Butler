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
        result = languageProcessor.tagPartsOfSpeech("The quick brown fox jumps over the lazy Butler.");

        assertEquals("The_DT quick_JJ brown_JJ fox_NN jumps_VBZ over_IN the_DT lazy_JJ Butler_NNP ._. ", result);
    }

    @Test
    public void testGetVerbsFromSentence() {
        String result = "";
        result = languageProcessor.getPartOfSpeechFromSentence("The quick brown fox kills the lazy Butler.", "V");

        assertEquals("kills ", result);
    }

    @Test
    public void testGetMultipleVerbsFromSentence() {
        String result = "";
        result = languageProcessor.getPartOfSpeechFromSentence("When you kill someone, have you done the killing?", "V");

        assertEquals("kill have done ", result);
    }

    @Test
    public void testGetSentenceSubject() {
        String result = "";
        result = languageProcessor.getSubject("What is the weather in Bar Harbor?");

        assertEquals("weather", result);
    }

}