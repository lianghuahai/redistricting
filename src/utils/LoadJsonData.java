package utils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.net.URISyntaxException;

import org.junit.Test;

import pojo.mapJson.PrecinctJson;

import com.google.gson.Gson;

public class LoadJsonData {
    String path ;
    public LoadJsonData() throws URISyntaxException{
        this.path=this.getClass().getClassLoader().getResource("/").toURI().getPath();
    }
    @Test
    public void writeFile(){
        String jsonData = readFile(path+"/"+ "NewHampshire_precinct.json");
        PrecinctJson fromJson = new Gson().fromJson(jsonData, PrecinctJson.class);
        try {  
            FileOutputStream out = new FileOutputStream("d:/zzzzzz.json"); // 输出文件路径  
            out.write(new Gson().toJson(fromJson).getBytes());  
            out.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }
    public PrecinctJson getOhioJsonData(){
        String jsonData = readFile(path+"/"+"NewHampshire_precinct.json");
//        String jsonData = readFile("new_hampshire_congressional_district.json");
        PrecinctJson fromJson = new Gson().fromJson(jsonData, PrecinctJson.class);
        System.out.println(jsonData);
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
