package com.nlpproject.callrecorder.Morf;

import java.util.List;
import java.util.Map;

/**
 * Created by Piotrek on 06.01.2017.
 */

public interface OwnMorfeusz {
    public List<String> getBase(String input);
    public Map<String, List<String>> getBase(List<String> inputList);
    public Map<String, List<String>> getBase(String[] inputTab); // return  bases in lowercase

}
