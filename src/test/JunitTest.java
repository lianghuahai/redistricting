package test;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.junit.Test;

import pojo.ObjectElement;
import pojo.State;

public class JunitTest {
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
