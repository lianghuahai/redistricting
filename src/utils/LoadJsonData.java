package utils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.HashMap;

import org.junit.Test;

import pojo.ObjectElement;
import pojo.State;
import pojo.mapJson.Feature;
import pojo.mapJson.precinctJson;

import com.google.gson.Gson;

public class LoadJsonData {
    @Test
    public void writeFile(){
        String jsonData = readFile("NewHampshire_precinct.json");
        precinctJson fromJson = new Gson().fromJson(jsonData, precinctJson.class);
        try {  
            FileOutputStream out = new FileOutputStream("d:/zzzzzz.json"); // 输出文件路径  
            out.write(new Gson().toJson(fromJson).getBytes());  
            out.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }
    public precinctJson getOhioJsonData(){
        String jsonData = readFile("ohio_precints.json");
        precinctJson fromJson = new Gson().fromJson(jsonData, precinctJson.class);
        return fromJson;
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