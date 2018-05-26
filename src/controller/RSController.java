package controller;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.net.aso.f;

import org.apache.poi.hssf.util.HSSFColor.GOLD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pojo.CDistrict;
import pojo.Neighbors;
import pojo.ObjectElement;
import pojo.Precinct;
import pojo.PrecinctProperty;
import pojo.Preference;
import pojo.Representive;
import pojo.Reserver;
import pojo.State;
import pojo.User;
import pojo.stateInfo;
import pojo.mapJson.Feature;
import pojo.mapJson.Geometry;
import pojo.mapJson.PrecinctJson;
import service.RSService;
import utils.LoadCOData;
import utils.LoadJsonData;
import utils.LoadNHData;
import utils.LoadSCData;
import utils.PropertyManager;
import utils.test;

import com.google.gson.Gson;

@Controller
public class RSController {
    @Autowired
    private RSService rsService;

    @RequestMapping("login")
    public void login(User user, HttpServletRequest req, HttpServletResponse res) throws IOException {
        User existedUser = rsService.login(user);
        Gson gson = new Gson();
        if (null == existedUser) {
            res.getWriter().print(gson.toJson("null"));
        } else {
            res.getWriter().print(gson.toJson(existedUser.getRole()));
        }
    }

    @RequestMapping("register")
    public void register(User user, HttpServletRequest req, HttpServletResponse res) throws IOException {
        boolean createdUser = rsService.register(user);
        Gson gson = new Gson();
        res.getWriter().print(gson.toJson(createdUser));
    }
    
    @RequestMapping("validateEmail")
    public void validateEmail(String email,HttpServletRequest req, HttpServletResponse res) throws IOException{
        boolean qualifiedEmail = rsService.validateEmail(email);
        res.getWriter().print(new Gson().toJson(qualifiedEmail));
    }
    @RequestMapping("getUsers")
    public void getUsers(HttpServletRequest req, HttpServletResponse res) throws IOException{
        List<User> users = rsService.getUsers();
        res.getWriter().print(new Gson().toJson(users));
    }
    @RequestMapping("deleteUserByEmail")
    public void deleteUserByEmail(String email,HttpServletRequest req, HttpServletResponse res) throws IOException{
        List<User> qualifiedEmail = rsService.getUsers();
        res.getWriter().print(new Gson().toJson(qualifiedEmail));
    }
    
    @RequestMapping("displayState")
    public void displayState(String stateName,String dLevel,String userEmail,HttpServletRequest req, HttpServletResponse res) throws IOException, Exception{
        State originalState = rsService.initializeState(stateName);
        originalState.setsName(stateName);
        PrecinctJson mapJson = new LoadJsonData().getJsonData(stateName,dLevel);
        if(stateName.equals("NH")){
            if(dLevel.equals("PD")){
                int colorCount=1;
                for (CDistrict cd : originalState.getCongressionalDistricts()) {
                    cd.setUpPrecinctMapJson(mapJson.getFeatures(),colorCount);
                    colorCount++;
                }
            }else{
                originalState.setUpCdMapJson(mapJson.getFeatures());
            }
        }else if(stateName.equals("CO")){
            if(dLevel.equals("PD")){
                int colorCount=1;
                for (CDistrict cd : originalState.getCongressionalDistricts()) {
                    cd.setUpPrecinctMapJson(mapJson.getFeatures(),colorCount);
                    colorCount++;
                }
            }else{
                originalState.setUpCoroladoCdMapJson(mapJson.getFeatures());
            }
        }else if(stateName.equals("SC")){
            if(dLevel.equals("PD")){
                int colorCount=1;
                for (CDistrict cd : originalState.getCongressionalDistricts()) {
                    cd.setUpSCPrecinctMapJson(mapJson.getFeatures(),colorCount);
                    colorCount++;
                }
            }else{
                originalState.setUpSCCdMapJson(mapJson.getFeatures());
            }
        }
        String email ="haha";
        String filePath = this.getClass().getClassLoader().getResource("/").toURI().getPath()+"/"+email+"/"+"nihao";
        File file = new File(this.getClass().getClassLoader().getResource("/").toURI().getPath()+"/"+email);
        if (!file.exists()) {
            file.mkdirs();
        }
        FileOutputStream of = new FileOutputStream(filePath); // 输出文件路径
        of.write(new Gson().toJson(mapJson).getBytes());
        of.close();
        req.getSession().setAttribute(PropertyManager.getInstance().getValue("originalState"),originalState);
        res.getWriter().print(new Gson().toJson(mapJson));
    }
    
    @RequestMapping("reservePrecinct")
    public void reservePrecinct( String VTDST10,boolean select ,HttpServletRequest req, HttpServletResponse res) throws IOException{
        State originalState= (State) req.getSession().getAttribute(PropertyManager.getInstance().getValue("originalState"));
        Set<CDistrict> cds = originalState.getCongressionalDistricts();
        for (CDistrict cDistrict : cds) {
            Set<Precinct> precincts = cDistrict.getPrecinct();
            boolean flag = false;
            for (Precinct precinct : precincts) {
               if(precinct.getPrecinctCode().equals(VTDST10)){
                   precinct.setIsFixed(select);
                   flag= true;
                   break;
               }
            }
            if(flag)
            {
                break;
            }
        }
    }
        
        @RequestMapping("redistrict")
    public void redistrict(String userEmail,Preference preference,HttpServletRequest req, HttpServletResponse res) throws IOException, URISyntaxException{
        State originalState= (State) req.getSession().getAttribute(PropertyManager.getInstance().getValue("originalState"));
        originalState.setPreference(preference);
        originalState.setupGoodness();
        rsService.increaseRunningTimes(originalState.getRunningTimes(),originalState.getsName());
        State workingState= originalState.clone(preference);
        String email ="haha";
        String filePath = this.getClass().getClassLoader().getResource("/").toURI().getPath()+"/"+email+"/"+"nihao";
        String json = new Gson().toJson(workingState);
        FileOutputStream of = new FileOutputStream(filePath); // 输出文件路径
        of.write(json.getBytes());
        of.close();
        rsService.initializeNeighbors(workingState);
        Precinct movedPrecinct = workingState.startAlgorithm();
        if(!workingState.checkTermination()){
            while(movedPrecinct==null){
                movedPrecinct = workingState.startAlgorithm();
                if(workingState.checkTermination()){
                    PrecinctProperty precinctProperty =  new PrecinctProperty();
                    precinctProperty.setTerminated(true);
                    req.getSession().setAttribute(PropertyManager.getInstance().getValue("workingState"),workingState);
                    res.getWriter().print(new Gson().toJson(precinctProperty));
                    return ;
                }
            }
            PrecinctProperty precinctProperty =  new PrecinctProperty();
            //old goodness
            precinctProperty.setOriginalGoodness(originalState.getCurrentGoodness());
            precinctProperty.setOriginalCompactness(originalState.getCompactness());
            precinctProperty.setOriginalRacialFairness(originalState.getRacialFairness());
            precinctProperty.setOriginalPartisanFairness(originalState.getPatisanFairness());
            precinctProperty.setOriginalPopulationVariance(originalState.getPopulationVariance());
            //new goodness
            precinctProperty.setGoodness(workingState.getCurrentGoodness());
            precinctProperty.setCompactness(workingState.getCompactness());
            precinctProperty.setRacialFairness(workingState.getRacialFairness());
            precinctProperty.setPartisanFairness(workingState.getPatisanFairness());
            precinctProperty.setPopulationVariance(workingState.getPopulationVariance());
            precinctProperty.setFill(movedPrecinct.getFeature().getProperties().getFill());
            precinctProperty.setVTDST10(movedPrecinct.getPrecinctCode());
            req.getSession().setAttribute(PropertyManager.getInstance().getValue("workingState"),workingState);
            res.getWriter().print(new Gson().toJson(precinctProperty));
        }else{
            PrecinctProperty precinctProperty =  new PrecinctProperty();
            precinctProperty.setTerminated(true);
            res.getWriter().print(new Gson().toJson(precinctProperty));
            return ;
        }
    }
    
    @RequestMapping("process")
    public void process(String userEmail,String fileName,HttpServletRequest req, HttpServletResponse res) throws IOException, URISyntaxException{
        State workingState = (State) req.getSession().getAttribute(PropertyManager.getInstance().getValue("workingState"));
        Precinct movedPrecinct = workingState.startAlgorithm();
        if(!workingState.checkTermination()){
            while(movedPrecinct==null){
                movedPrecinct = workingState.startAlgorithm();
                if(workingState.checkTermination()){
                    PrecinctProperty precinctProperty =  new PrecinctProperty();
                    precinctProperty.setTerminated(true);
                    req.getSession().setAttribute(PropertyManager.getInstance().getValue("workingState"),workingState);
                    res.getWriter().print(new Gson().toJson(precinctProperty));
                    return ;
                }
            }
            PrecinctProperty precinctProperty =  new PrecinctProperty();
            precinctProperty.setupGoodness(workingState);
            precinctProperty.setFill(movedPrecinct.getFeature().getProperties().getFill());
            precinctProperty.setVTDST10(movedPrecinct.getPrecinctCode());
            req.getSession().setAttribute(PropertyManager.getInstance().getValue("workingState"),workingState);
            res.getWriter().print(new Gson().toJson(precinctProperty));
        }else{
            req.getSession().setAttribute(PropertyManager.getInstance().getValue("workingState"),workingState);
            PrecinctProperty precinctProperty =  new PrecinctProperty();
            precinctProperty.setTerminated(true);
            res.getWriter().print(new Gson().toJson(precinctProperty));
            return ;
        }
//        }
   }
    
    @RequestMapping("getStateInfo")
    public void originalStateData(String userEmail,HttpServletRequest req, HttpServletResponse res) throws IOException{
        State originalState= (State) req.getSession().getAttribute(PropertyManager.getInstance().getValue("originalState"));
        stateInfo si = new stateInfo();
        String stateName = originalState.getsName();
        if(stateName.equals("NH")){
            si.setStateproperty(0.27286730582052965 ,0.24058867394182346,0.9996979458721359 ,0.06512390228811901 ,0.39456945698065205);
            si.setupNHProperty(2);
        }else if(stateName.equals("CO")){
            si.setStateproperty(0.27931594716675207,0.16752750658519622 ,0.9992986870914463,0.16752750658519622,0.435060409784365);
            si.setupCOProperty(7);
        }else{
            si.setStateproperty(0.28843552956512 ,0.13892058059200102,0.9990938912611522 ,0.32590240953115807 ,0.4380881027373579);
            si.setupSCProperty(7);
        }
        si.setPopulation(originalState.getPopulation());
        si.setArea(originalState.getArea());
        si.setAveIncome(originalState.getAveIncome());
        si.setNumOfCds(originalState.getCongressionalDistricts().size());
        si.setCompactness(originalState.getCompactness());
        si.setPatisanFairness(originalState.getPatisanFairness());
        si.setPopulationVariance(originalState.getPopulationVariance());
        si.setRacialFairness(originalState.getRacialFairness());
        si.setGoodness(originalState.getCurrentGoodness());
        int count = 0;
        Set<CDistrict> cds = originalState.getCongressionalDistricts();
        for (CDistrict cDistrict : cds) {
            Set<Precinct> precinct = cDistrict.getPrecinct();
            for (Precinct precinct2 : precinct) {
                count++;
            }
        }
        si.setNumOfPds(count);
        si.setArea(originalState.getArea());
        System.out.println(si);
        res.getWriter().print(new Gson().toJson(si));
    }
    
    @RequestMapping("editProperties")
    public void editProperties(HttpServletRequest req, HttpServletResponse res) throws IOException, URISyntaxException{
        Desktop.getDesktop().open(new File(this.getClass().getClassLoader().getResource("/").toURI().getPath()+"/constants.properties"));
        res.getWriter().print(new Gson().toJson(1));
    }
    
    @RequestMapping("getCompareState")
    public void getCompareState(String stateName,int year,HttpServletRequest req, HttpServletResponse res) throws IOException, URISyntaxException{
        stateInfo si = new stateInfo();
        if(stateName.equals("NH")){
            //1 Compactness 2 patisanFairness 3PopulationVariance 4RacialFairness 5Goodness 
            si.setStateproperty(0.27286730582052965 ,0.24058867394182346,0.9996979458721359 ,0.06512390228811901 ,0.39456945698065205);
            si.setupNHProperty(2);
            si.setArea(9349);
            si.setAveIncome(70936);
            si.setPopulation(1334168);
            si.setNumOfPds(328);
            si.setCompactness(0.27286730582052965);
            si.setPatisanFairness(0.24058867394182346);
            si.setPopulationVariance(0.9996979458721359 );
            si.setRacialFairness(0.06512390228811901);
            si.setGoodness(0.39456945698065205);
        }else if(stateName.equals("CO")){
            si.setStateproperty(0.27931594716675207,0.16752750658519622 ,0.9992986870914463,0.16752750658519622,0.435060409784365);
            si.setupCOProperty(7);
            si.setArea(104185);
            si.setAveIncome(52334);
            si.setPopulation(5684203);
            si.setNumOfPds(3039);
            
            si.setCompactness(0.27286730582052976);
            si.setPatisanFairness(0.2940994982940655);
            si.setPopulationVariance(0.9992986870914463 );
            si.setRacialFairness(0.16752750658519622);
            si.setGoodness(0.4201788177579298);
        }else{
            si.setStateproperty(0.28843552956512 ,0.13892058059200102,0.9990938912611522 ,0.32590240953115807 ,0.4380881027373579);
            si.setupSCProperty(7);
            si.setArea(32020);
            si.setAveIncome(65685);
            si.setPopulation(5088916);
            si.setNumOfPds(2122);
            si.setCompactness(0.28843552956512);
            si.setPatisanFairness(0.13892058059200102);
            si.setPopulationVariance(0.9990938912611522);
            si.setRacialFairness(0.32590240953115807);
            si.setGoodness(0.4380881027373579);
        }
        //si.setupGoodness(originalState);
        
        List<Representive> repres= rsService.getRepresents(stateName,year);
        si.setupRepre(repres);
        if(stateName.equals("NH")){
            si.setNumOfCds(2);
            si.setNumOfPds(328);
        }else if(stateName.equals("CO")){
            si.setNumOfCds(7);
            si.setNumOfPds(3039);
        }else{
            si.setNumOfCds(7);
            si.setNumOfPds(2122);
        }
        //compactness, goodness, fairness , effeciency gap
        res.getWriter().print(new Gson().toJson(si));
        
        
        
    }
    
    @RequestMapping("updateUser")
    public void updateUser(User user,HttpServletRequest req, HttpServletResponse res) throws IOException, URISyntaxException{
        System.out.println(user);
        if(user.getParty().equals("undefined")){
            user.setParty("REPUBLICAN");
        }
        rsService.updateUser(user);
    }
    @RequestMapping("addUser")
    public void addUser(User user,HttpServletRequest req, HttpServletResponse res) throws IOException, URISyntaxException{
        System.out.println(user);
    }
    @RequestMapping("deleteUser")
    public void deleteUser(User user,HttpServletRequest req, HttpServletResponse res) throws IOException, URISyntaxException{
        System.out.println(user);
        rsService.deleteUser(user.getEmail());
    }
    
    
    @RequestMapping("importState")
    public void importState(String email,String fileName, HttpServletRequest req, HttpServletResponse res) throws Exception {
        email = "haha";
        String filePath = this.getClass().getClassLoader().getResource("/").toURI().getPath()+"/"+email+"/"+fileName;
        String stateName="NH";
        String dLevel = "PD";
        State originalState = rsService.initializeState(stateName);
        originalState.setsName(stateName);
        PrecinctJson mapJson = new LoadJsonData().getJsonData(stateName,dLevel);
        mapJson.setsName("NH");
        if(stateName.equals("NH")){
            if(dLevel.equals("PD")){
                int colorCount=1;
                for (CDistrict cd : originalState.getCongressionalDistricts()) {
                    cd.setUpPrecinctMapJson(mapJson.getFeatures(),colorCount);
                    colorCount++;
                }
            }else{
                originalState.setUpCdMapJson(mapJson.getFeatures());
            }
        }else if(stateName.equals("CO")){
            if(dLevel.equals("PD")){
                int colorCount=1;
                for (CDistrict cd : originalState.getCongressionalDistricts()) {
                    cd.setUpPrecinctMapJson(mapJson.getFeatures(),colorCount);
                    colorCount++;
                }
            }else{
                originalState.setUpCoroladoCdMapJson(mapJson.getFeatures());
            }
        }else if(stateName.equals("SC")){
            if(dLevel.equals("PD")){
                int colorCount=1;
                for (CDistrict cd : originalState.getCongressionalDistricts()) {
                    cd.setUpSCPrecinctMapJson(mapJson.getFeatures(),colorCount);
                    colorCount++;
                }
            }else{
                originalState.setUpSCCdMapJson(mapJson.getFeatures());
            }
        }
        res.getWriter().print(mapJson);
    }
    @RequestMapping("exportState")
    public void exportState( String email,String fileName,HttpServletRequest req, HttpServletResponse res) throws Exception {
        System.out.println("exportState");
        email ="haha";
        String filePath = this.getClass().getClassLoader().getResource("/").toURI().getPath()+"/"+email+"/"+fileName;
        File file = new File(this.getClass().getClassLoader().getResource("/").toURI().getPath()+"/"+email);
        if (!file.exists()) {
            file.mkdirs();
        }
        State workingState = (State) req.getSession().getAttribute(PropertyManager.getInstance().getValue("workingState"));
         State originalState= (State) req.getSession().getAttribute(PropertyManager.getInstance().getValue("originalState"));
        if(workingState==null){System.out.println("null");}
        Gson gson = new Gson();
        String json = gson.toJson(workingState);
        FileOutputStream of = new FileOutputStream(filePath); // 输出文件路径
        of.write(json.getBytes());
        of.close();
    }
    
    @RequestMapping("getFileList")
    public void filetest( String email,HttpServletRequest req, HttpServletResponse res) throws Exception {
        System.out.println(email);
        String filePath = this.getClass().getClassLoader().getResource("/").toURI().getPath()+"/"+"haha";
        File file = new File(filePath);
        String [] fileName = file.list();
        List<String> str = new ArrayList<String>();
        for (String string : fileName) {
            if(string!=null){
                str.add(string);
            }
        }
        res.getWriter().print(new Gson().toJson(str));
    }
    @RequestMapping("removeFile")
    public void removeFile( String email,String fileName,HttpServletRequest req, HttpServletResponse res) throws Exception {
        String filePath = this.getClass().getClassLoader().getResource("/").toURI().getPath()+"/"+"haha"+"/"+fileName;
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        res.getWriter().print(new Gson().toJson("ok"));
    }
    //helper
    public String readToString(String fileName) {  
        String encoding = "UTF-8";  
        File file = new File(fileName);  
        Long filelength = file.length();  
        byte[] filecontent = new byte[filelength.intValue()];  
        try {  
            FileInputStream in = new FileInputStream(file);  
            in.read(filecontent);  
            in.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        try {  
            return new String(filecontent, encoding);  
        } catch (UnsupportedEncodingException e) {  
            System.err.println("The OS does not support " + encoding);  
            e.printStackTrace();  
            return null;  
        }  
    }

    @RequestMapping("exportTest")
    public void exportTest( String email,String fileName,HttpServletRequest req, HttpServletResponse res) throws Exception {
        System.out.println("exportState");
        email ="haha";
        String filePath = this.getClass().getClassLoader().getResource("/").toURI().getPath()+"/"+email+"/"+fileName;
        File file = new File(this.getClass().getClassLoader().getResource("/").toURI().getPath()+"/"+email);
        if (!file.exists()) {
            file.mkdirs();
        }
        State workingState = new State();
        Set<CDistrict> congressionalDistricts = workingState.getCongressionalDistricts();
        CDistrict ciCDistrict= new CDistrict();
        congressionalDistricts.add(ciCDistrict);
        Precinct precinct= new Precinct();
        ciCDistrict.getPrecinct().add(precinct);
        
        String attribute = (String) req.getSession().getAttribute("nihao");
        Gson gson = new Gson();
        String json = gson.toJson(workingState);
        FileOutputStream of = new FileOutputStream(filePath); // 输出文件路径
        of.write(json.getBytes());
        of.close();
        res.getWriter().print(json);
    }
    
    
  // Load state information and store into DB 
    
    @RequestMapping("gsonT")
    public void gsonT( HttpServletRequest req, HttpServletResponse res) throws Exception {
        State state = new State();
        Set<CDistrict> congressionalDistricts = state.getCongressionalDistricts();
        CDistrict ciCDistrict= new CDistrict();
        congressionalDistricts.add(ciCDistrict);
        Precinct precinct= new Precinct();
        ciCDistrict.getPrecinct().add(precinct);
        Gson gson = new Gson();
        res.getWriter().print(gson.toJson(state));
    }
    
    @RequestMapping("saveR")
    public void saveR( HttpServletRequest req, HttpServletResponse res) throws Exception {
        String filePath = this.getClass().getClassLoader().getResource("/").toURI().getPath();
//        test excelReader = new test(filePath+"/"+"sc_r.xlsx");
//        test excelReader = new test(filePath+"/"+"nh_r.xlsx");
        test excelReader = new test(filePath+"/"+"co_r.xlsx");
        Map<Integer, Map<Integer, Object>> map = excelReader.readExcelContent();
        for (int i = 1; i < map.size(); i++) {
            Map<Integer, Object> row = map.get(i); //election data row
            int year =(int)(Double.parseDouble((String)row.get(0)));
            String sName = (String)row.get(1);
            int cdistrictId = (int)(Double.parseDouble((String)row.get(2)));
            String name = (String)row.get(3);
            String party = (String)row.get(4);
            rsService.saveRR(sName,cdistrictId,name,party,year);
        }
    }
    @RequestMapping("loadtest")
    public void loadtest( HttpServletRequest req, HttpServletResponse res) throws Exception {
        PrecinctJson mapJson = new LoadJsonData().getJsonData("NH","PD");
        Set<Feature> features = mapJson.getFeatures();
        for (Feature feature : features) {
            Geometry geometry = feature.getGeometry();
            List<List<List<Double>>> coordinates = geometry.getCoordinates();
            List<List<Double>> list = coordinates.get(0);
            for (List<Double> codinates : list) {
                break;
            }
            break;
        }
    }
    
    
    @RequestMapping("testSC")
    public void testSC( HttpServletRequest req, HttpServletResponse res) throws Exception {
//        rsService.updatePCode();
        LoadSCData loadSCData = new LoadSCData();
        State workingState = loadSCData.getState();
        Set<CDistrict> cds = workingState.getCongressionalDistricts();
        for (CDistrict cDistrict : cds) {
            rsService.saveCds(cDistrict);
            for (Precinct p : cDistrict.getPrecinct()) {
                rsService.savePrecincts(p);
                rsService.updatePrecinctVotes(p);
            }
        }
    }
    @RequestMapping("updatePCode")
    public void updatePCode( HttpServletRequest req, HttpServletResponse res) throws Exception {
//        rsService.updatePCode();
        rsService.createVotes();
    }
    @RequestMapping("getCDsByStateId")
    public void getCDsByStateId( HttpServletRequest req, HttpServletResponse res) throws Exception {
//        List<String> relationList = new LoadNHData().load();
//        for (String str : relationList) {
//            String[] strs=str.split(",");
//            System.out.println(strs[3].toString());
//            System.out.println(strs[4].toString());
//            rsService.updatePrecinctCounty(strs[4].toString(),strs[3].toString());
//        }
    }
    @RequestMapping("test")
    public void test( HttpServletRequest req, HttpServletResponse res) throws Exception {
        LoadCOData load= new LoadCOData();
        State workingState = load.getState();
      Set<CDistrict> cds = workingState.getCongressionalDistricts();
      for (CDistrict cDistrict : cds) {
          rsService.saveCds(cDistrict);
          for (Precinct p : cDistrict.getPrecinct()) {
              //System.out.println(p);
              rsService.savePrecincts(p);
              rsService.updatePrecinctVotes(p);
          }
      }
    }
    @RequestMapping("neighbor")
    public void neighbor( HttpServletRequest req, HttpServletResponse res) throws Exception {
        ArrayList<ArrayList<String>> list = new LoadNHData().loadNeighbors();
        ArrayList<String> pList = list.get(0);
        ArrayList<String> nList = list.get(1);
        
//        rsService.saveNeighbors(pCode,neighborCode);
//        State originalState = rsService.initializeState("SC");
      
        //State originalState = rsService.initializeState("CO");
        //State originalState = rsService.initializeState("SC");
        Map<String,String> map = new HashMap<String,String>();
        for (int i = 0; i < pList.size(); i++) {
                String str = nList.get(i);
                String[] strs=str.split(",");
                for (String string : strs) {
//                    rsService.saveNeighbors(pList.get(i),string);
//                    rsService.saveToNewNeighbors(pList.get(i),string,"NH");
                    rsService.saveToNewNeighbors(pList.get(i),string,"CO");
//                    rsService.saveToNewNeighbors(pList.get(i),string,"SC");
                }
        }
        res.getWriter().print(new Gson().toJson("ok"));
        
    }
    
    
    @RequestMapping("setPrecintArea")
    public void setPrecintArea( HttpServletRequest req, HttpServletResponse res) throws Exception {
        String path  = this.getClass().getClassLoader().getResource("/").toURI().getPath();
//        File file = new File(path+"/"+"NH_PD_area.txt");
        File file = new File(path+"/"+"SC_PD_area.txt");
//        File file = new File(path+"/"+"NH_PD_area.txt");
      BufferedReader br = new BufferedReader(new FileReader(file));
      ArrayList<String> pList = new ArrayList<String>();
      String st;
      while ((st = br.readLine()) != null){
              pList.add(st);
          }
      for (int i = 0; i < pList.size(); i++) {
          String str = pList.get(i);
          String[] strs=str.split(",");
          String code = strs[0];
          if(code.length()==7){
              code = code +"0";
          }
          if(code.length()==6){
              code = code +"00";
          }
          rsService.updateArea(code,Double.parseDouble(strs[1]));
      }
      System.out.println(pList.size());
//      for (String string : pList) {
//        System.out.println(string);
//    }
    }
    @RequestMapping("getNeighborToSet")
    public void getNeighborToSet( HttpServletRequest req, HttpServletResponse res) throws Exception {
        List<Neighbors> neighs= rsService.getneighbors();
        System.out.println("guolai");
        for (Neighbors neighbors : neighs) {
            boolean flag=false;
            if(neighbors.getpCode().length()==7){
                neighbors.setpCode(neighbors.getpCode()+"0");
                flag=true;
            }
            if(neighbors.getpCode().length()==6){
                neighbors.setpCode(neighbors.getpCode()+"00");
                flag=true;
            }
            if(neighbors.getNeighborCode().length()==7){
                neighbors.setNeighborCode(neighbors.getNeighborCode()+"0");
                flag=true;
            }
            if(neighbors.getNeighborCode().length()==6){
                neighbors.setNeighborCode(neighbors.getNeighborCode()+"00");
                flag=true;
            }
            if(flag){
                rsService.updateNeiCode(neighbors);
            }
        }
    }
    @RequestMapping("ddddd")
    public void ddddd( HttpServletRequest req, HttpServletResponse res) throws Exception {
        String path  = this.getClass().getClassLoader().getResource("/").toURI().getPath();
//      File file = new File(path+"/"+"NH_PD_area.txt");
        File file = new File(path+"/"+"CO_undefinedP(1).txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        ArrayList<String> pList = new ArrayList<String>();
        String st;
        while ((st = br.readLine()) != null){
            pList.add(st);
        }
        for (int i = 0; i < pList.size(); i++) {
            String str = pList.get(i);
            String[] strs=str.split(",");
            String code = strs[0];
            String cdid = strs[1];
            System.out.println("1");
            int int1 = Integer.parseInt(cdid);
            System.out.println("1");
            System.out.println(code);
            int1=int1+2;
            String valueOf = String.valueOf(int1);
            rsService.savePrecinctsone(code,valueOf,2);
        }
    }
        @RequestMapping("saveData")
        public void a( HttpServletRequest req, HttpServletResponse res) throws Exception {
        LoadNHData a = new LoadNHData();
        int abc=0;
        State workingState = a.getState();
//        Set<CDistrict> cds = workingState.getCongressionalDistricts();
//        for (CDistrict cDistrict : cds) {
//            System.out.println(cDistrict.getPrecinct().size());
//            System.out.println(cDistrict.getName()+cDistrict.getPopulation()+cDistrict.getStateId());
//            rsService.saveCds(cDistrict);
//            for (Precinct p : cDistrict.getPrecinct()) {
//                rsService.savePrecincts(p);
//            }
//        }
        
        //update population or votes
        Set<CDistrict> cds = workingState.getCongressionalDistricts();
        
        for (CDistrict cDistrict : cds) {
            Set<Precinct> ps = cDistrict.getPrecinct();
            for (Precinct precinct : ps) {
//                rsService.updatePrecinctField(precinct);
//                rsService.updatePrecinctPopulation(precinct);
                rsService.updatePrecinctVotes(precinct);
            }
        }
        
    }
    
}
