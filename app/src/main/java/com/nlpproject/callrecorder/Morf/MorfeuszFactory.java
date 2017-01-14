package com.nlpproject.callrecorder.Morf;

/**
 * Created by Piotrek on 13.01.2017.
 */

public class MorfeuszFactory {
    public static OwnMorfeusz getMorfeusz(){
        return new MorfeuszWebDemo();
    }
}
