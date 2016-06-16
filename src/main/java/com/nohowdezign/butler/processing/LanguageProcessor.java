package com.nohowdezign.butler.processing;

import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

/**
 * @author Noah Howard
 */
public class LanguageProcessor {

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
                    toReturn += whitespaceTokenizerLine[i];
                }
            }
        }

        return toReturn;
    }

    public String tagPartsOfSpeech(String sentanceToTag) throws IOException {
        String toReturn = "";

        POSModel model = new POSModelLoader().load(new File("resources/models/en-pos-maxent.bin"));
        POSTaggerME tagger = new POSTaggerME(model);
        ObjectStream<String> lineStream = new PlainTextByLineStream(new StringReader(sentanceToTag));

        String line;
        while ((line = lineStream.read()) != null) {
            String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE.tokenize(line);
            String[] tags = tagger.tag(whitespaceTokenizerLine);

            POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
            toReturn += sample.toString();
        }

        return toReturn;
    }

}
