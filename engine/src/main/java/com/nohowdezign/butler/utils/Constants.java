package com.nohowdezign.butler.utils;

import com.nohowdezign.butler.responder.Responder;
import com.nohowdezign.butler.responder.VoiceResponder;

import java.io.File;

/**
 * @author Noah Howard
 */
public class Constants {
    public static double VERSION = 0.01;
    public static Responder DEFAULT_RESPONDER = new VoiceResponder();
    public static File GRAMMAR_FILE = null;
}
