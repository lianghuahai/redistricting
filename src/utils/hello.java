package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Set;

import org.junit.Test;

import pojo.User;
import pojo.mapJson.Coordinate;
import pojo.mapJson.Feature;
import pojo.mapJson.Geometry;
import pojo.mapJson.geoData;
import pojo.mapJson.precinctJson;

import com.google.gson.Gson;

public class hello {
    @Test
    public void ts(){
        System.out.println("test:");
        String jsonData = readFile("test.json");
//        Coordinate c = new Coordinate();
//        Set<geoData> position = c.getPosition();
//        geoData g = new geoData();
//        g.getCoordinates().add(100.12);
//        g.getCoordinates().add(200.53);
//        position.add(g);
//        System.out.println(c);
//        String json = new Gson().toJson(c);
//        System.out.println(json);
//        Coordinate fromJson = new Gson().fromJson(json, Coordinate.class);
//        System.out.println(fromJson);
        precinctJson fromJson = new Gson().fromJson(jsonData, precinctJson.class);
        System.out.println(fromJson);
        System.out.println(jsonData);
        System.out.println(fromJson.getType());
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
