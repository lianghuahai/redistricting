package test;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.google.gson.Gson;

import pojo.CDistrict;
import pojo.ObjectElement;
import pojo.State;
import utils.test;

public class JunitTest {
    
    
    
    
    @Test
    public void test21() throws Exception{
        State state = new State();
        Gson gson = new Gson();
        gson.toJson(state);
    }
    @Test
    public void test2() throws Exception{
        State workingState = new State();
        workingState.initialStateByNumOfCDs(7);
        workingState.setPopulation(1316470);
//        test excelReader = new test(path+"/"+"Representatives/Congressional District 1-excek.xlsx");
        test excelReader = new test("2016GeneralResultsPrecinctLevel.xlsx");
        Map<Integer, Map<Integer, Object>> map = excelReader.readExcelContent();
        int count =0;
        for (int i = 0; i < map.size(); i++) {
            count++;
//            Precinct p = new Precinct();
//            Map<Integer, Object> row = map.get(i); //election data row
//            p.setName((String)row.get(0));
//            p.setCDistrict(cd1);
//            cd1.getPrecinct().add(p);
        }
        System.out.println(count);
    }
    @Test  //Testing PropertyManager
    public void juniTest3() throws IOException, URISyntaxException{
        String string = "4253001245";
        String substring = string.substring(5, string.length());
        System.out.println(substring);
        System.out.println(string);
        
    }
    @Test  //Testing PropertyManager
    public void juniTest2() throws IOException, URISyntaxException{
        Desktop.getDesktop().open(new File(this.getClass().getClassLoader().getResource("/").toURI().getPath()+"/constants.properties"));
        
    }
    @Test  //Testing PropertyManager
    public void juniTest(){
//        BasicConfiguration configuration = BasicConfiguration.getInstance(); 
//        String a =configuration.getValue("state.maxTime");
        State state = new State();
        System.out.println(state.getMAXRUNTIME());
        System.out.println(ObjectElement.COMPACTNESSWEIGHT.getWeight());
        System.out.println(ObjectElement.PARTISANFAIRNESSWEIGHT.getWeight());
        System.out.println(ObjectElement.POPULATIONVARIANCEWEIGHT.getWeight());
        System.out.println(ObjectElement.RACIALFAIRNESSWEIGHT.getWeight());
    }
}
