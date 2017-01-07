package com.nlpproject.callrecorder.Morf;

import java.util.List;

/**
 * Created by Piotrek on 06.01.2017.
 */

public interface OwnMorfeusz {
    public String getBase(String input);
    public List<String> getBase(List<String> inputList);
    public List<String> getBase(String[] inputTab); // return  bases in lowercase

}
