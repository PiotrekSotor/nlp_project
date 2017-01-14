package com.nlpproject.callrecorder.Morf;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Piotrek on 13.01.2017.
 */

class MorfeuszWebDemo implements OwnMorfeusz {
    @Override
    public List<String> getBase(String input) {
        return getBasesFromWeb(Arrays.asList(input)).get(input);
    }

    @Override
    public Map<String, List<String>> getBase(List<String> inputList) {
        return getBasesFromWeb(inputList);
    }

    @Override
    public Map<String, List<String>> getBase(String[] inputTab) {
        return getBase(Arrays.asList(inputTab));
    }

    private Map<String, List<String>> getBasesFromWeb(List<String> inputList) {

        String urlString = prepareHttpGetUrl(inputList);

        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HashMap<String, List<String>> result = new HashMap<>();
        HttpURLConnection urlConnection = null;
        StringBuilder sb = new StringBuilder();
        try {
            Log.i("Morfeusz HttpGet",urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader isr = new BufferedReader(
                    new InputStreamReader(in));

            String line;
            String currentKeyword = "";
            List<String> currentKeywordBasesList = new ArrayList<>();
            while ((line = isr.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
                line = line.trim();
                if (line.startsWith("<tr ")) {
                        //pierwszy wiersz danego slowa - zawiera powtorzenie szukanego slowa
                    List<String> tds = getTdFromLine(line);
                    if (tds.size()!=7)
                        continue;
                    if (line.startsWith("<tr class=\"seg")) {
                        currentKeyword = tds.get(2);
                        currentKeywordBasesList.clear();
                    }
                    String baseWord = clearAfterMorfeusz(tds.get(3));
                    baseWord = baseWord.toLowerCase();
                    if (!currentKeywordBasesList.contains(baseWord)){
                        if (result.containsKey(currentKeyword)){
                            result.get(currentKeyword).add(baseWord);
                        }
                        else{
                            List<String> list = new ArrayList<>();
                            list.add(baseWord);
                            result.put(currentKeyword,list);
                        }
                        currentKeywordBasesList.add(baseWord);
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();

        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        Log.i("Morfeusz HttpResponse",sb.toString());
        return result;
    }

    private String clearAfterMorfeusz(String s) {
        if (s.contains(":")){
            return s.substring(0,s.indexOf(":"));
        }
        return s;
    }

    private List<String> getTdFromLine(String line) {
        int lastIndexOf = 0;
        int index = 0;
        List<String> result = new ArrayList<>();
        while ((index = line.indexOf("<td", lastIndexOf)) != -1) {
            for (; index < line.length(); ++index) {
                if (line.charAt(index) == '>') {
                    index++;
                    break;
                }
            }
            lastIndexOf = line.indexOf("</td>", index);
            String content = line.substring(index, lastIndexOf);
            result.add(removeHTMLtags(content));
        }
        return result;
    }

    private String removeHTMLtags(String content) {
        StringBuilder sb = new StringBuilder();
        boolean ignore=false;
        for (int i=0;i<content.length();++i){
            if (content.charAt(i)=='<'){
                ignore=true;
            }
            if (!ignore){
                sb.append(content.charAt(i));
            }
            if (content.charAt(i)=='>'){
                ignore=false;
            }
        }
        return sb.toString();
    }

    private String prepareHttpGetUrl(List<String> inputList) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://sgjp.pl/morfeusz/demo/?text=");
        for (String input : inputList) {
            sb.append(input);
            sb.append("+");
        }
        sb.deleteCharAt(sb.lastIndexOf("+"));
        return sb.toString();

    }


}
