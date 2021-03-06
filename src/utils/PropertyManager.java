package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

public class PropertyManager {  
    
    private static PropertyManager configuration = null;  
    private Properties pros = null;  
    public static synchronized PropertyManager getInstance(){  
        if(configuration == null){  
            configuration = new PropertyManager();  
        }  
        return configuration;  
    }  
    public String getValue(String key){  
        return pros.getProperty(key);  
    }  
      
    private PropertyManager(){  
        readConfig();  
    }  
    private void readConfig() {  
        pros = new Properties();   
        String path="";
        InputStream in = null;  
        try {  
            try {
                 path = this.getClass().getClassLoader().getResource("/").toURI().getPath();
            } catch (URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            in = new FileInputStream(path+"/constants.properties");  
//            in = new FileInputStream("constants.properties");  
            pros.load(in);  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        }catch (IOException e) {  
            e.printStackTrace();  
        }finally{  
            try {  
                in.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
}