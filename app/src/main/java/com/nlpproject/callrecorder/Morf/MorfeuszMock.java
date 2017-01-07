package com.nlpproject.callrecorder.Morf;

import com.google.api.client.util.Lists;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Piotrek on 06.01.2017.
 */

public class MorfeuszMock implements OwnMorfeusz {
    @Override
    public String getBase(String input) {
        return input;
    }

    @Override
    public List<String> getBase(List<String> inputList) {
        return inputList;
    }
    @Override
    public List<String> getBase(String[] inputTab) {
        return Arrays.asList(inputTab);
    }
}
