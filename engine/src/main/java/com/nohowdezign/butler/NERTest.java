package com.nohowdezign.butler;

import edu.stanford.nlp.simple.Sentence;

/**
 * Created by noah on 7/27/16.
 */
public class NERTest {

    public static void main(String[] args) {
        Sentence sentence = new Sentence("Set an alarm for August tenth at five o'clock");
        System.out.println(sentence.nerTags());
    }

}
