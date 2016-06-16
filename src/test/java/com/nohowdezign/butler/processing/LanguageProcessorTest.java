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

        assertEquals(result, "The_DT quick_JJ brown_JJ fox_NN jumps_NNS over_IN the_DT lazy_JJ Butler._.");
    }

    @Test
    public void testGetVerbsFromSentance() {
        String result = "";
        try {
            result = languageProcessor.getVerbsFromSentence("The quick brown fox kills the lazy Butler.");
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(result, "kills");
    }

}