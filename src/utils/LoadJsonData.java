package utils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.net.URISyntaxException;

import org.junit.Test;

import pojo.mapJson.PrecinctJson;

import com.google.gson.Gson;

public class LoadJsonData {
    String serverPath ;
    public LoadJsonData() throws URISyntaxException{
        this.serverPath=this.getClass().getClassLoader().getResource("/").toURI().getPath();
    }
    
    public PrecinctJson getJsonData(String stateName ,String dLevel){
        System.out.println("stateName,dLevel:"+stateName+","+dLevel);
        String jsonData = readFile(serverPath+"/"+PropertyManager.getInstance().getValue(stateName+dLevel));
        PrecinctJson fromJson = new Gson().fromJson(jsonData, PrecinctJson.class);
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
