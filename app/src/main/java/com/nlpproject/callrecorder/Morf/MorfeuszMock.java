package com.nlpproject.callrecorder.Morf;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Piotrek on 06.01.2017.
 */

 class MorfeuszMock implements OwnMorfeusz {
    @Override
    public List<String> getBase(String input) {
        return Arrays.asList(input);
    }

    @Override
    public Map<String, List<String>> getBase(List<String> inputList) {
        HashMap<String, List<String>> result = new HashMap<>();
        for (String input : inputList){
            result.put(input,Arrays.asList(input));
        }
        return result;
    }
    @Override
    public Map<String, List<String>> getBase(String[] inputTab){
        return getBase(Arrays.asList(inputTab));
    }
}
