package com.nohowdezign.butler.processing;

import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Noah Howard
 */
public class LanguageProcessor {
    private List<String> stopWords = new ArrayList<String>();

    public LanguageProcessor() {
        // Load stop words into local variable for normalization
        File stopWords = new File("resources/stopwords/en.txt");
        Scanner s = null;
        try {
            s = new Scanner(stopWords);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(s.hasNext()) {
            this.stopWords.add(s.next());
        }
    }

    public String getVerbsFromSentence(String sentence) throws IOException {
        String toReturn = "";

        POSModel model = new POSModelLoader().load(new File("resources/models/en-pos-maxent.bin"));
        POSTaggerME tagger = new POSTaggerME(model);
        ObjectStream<String> lineStream = new PlainTextByLineStream(new StringReader(sentence));

        String line;
        while ((line = lineStream.read()) != null) {
            String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE.tokenize(line);
            String[] tags = tagger.tag(whitespaceTokenizerLine);
            for(int i = 0; i < tags.length; i++) {
                if(tags[i].contains("V")) {
                    toReturn += whitespaceTokenizerLine[i] + " ";
                }
            }
        }

        return toReturn.trim().replaceAll("(\\s)+", "$1");
    }

    public String tagPartsOfSpeech(String sentenceToTag) throws IOException {
        String toReturn = "";

        POSModel model = new POSModelLoader().load(new File("resources/models/en-pos-maxent.bin"));
        POSTaggerME tagger = new POSTaggerME(model);
        ObjectStream<String> lineStream = new PlainTextByLineStream(new StringReader(sentenceToTag));

        String line;
        while ((line = lineStream.read()) != null) {
            String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE.tokenize(line);
            String[] tags = tagger.tag(whitespaceTokenizerLine);

            POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
            toReturn += sample.toString();
        }

        return toReturn.trim().replaceAll("(\\s)+", "$1");
    }

    public String normalizeSentence(String sentence) throws FileNotFoundException {
        String toReturn = sentence;
        for(String s : stopWords) {
            for(String word : sentence.split("\\s")) {
                if(word.toLowerCase().equals(s)) {
                    toReturn = toReturn.replaceAll("\\b" + word + "\\b", "");
                }
            }
        }
        return toReturn.trim().replaceAll("(\\s)+", "$1"); // Return normalized string with correct spacing
    }

}
