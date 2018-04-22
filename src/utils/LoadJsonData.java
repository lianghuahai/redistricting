package utils;

import java.io.BufferedReader;
import java.io.FileReader;

import org.junit.Test;

import pojo.mapJson.Feature;
import pojo.mapJson.precinctJson;

import com.google.gson.Gson;

public class LoadJsonData {
    @Test
    public void ts(){
        System.out.println("test:");
        String jsonData = readFile("ohiogood.json");
        //System.out.println(jsonData);
        precinctJson fromJson = new Gson().fromJson(jsonData, precinctJson.class);
        //System.out.println(new Gson().toJson(fromJson));
        int count=0;
        for (Feature f : fromJson.getFeatures()) {
            //System.out.println(new Gson().toJson(f));
            count++;
        }
        System.out.println(count);
    }
    public  String readFile(String filename) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
