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
        System.out.println(user.getParty());
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
//        State workingState = (State) req.getSession().getAttribute(PropertyManager.getInstance().getValue("workingState"));
//        State workingState = new State();
        System.out.println("这里开始gson");
        Gson gson = new Gson();
        String json = gson.toJson(originalState);
        System.out.println("这里结束gson");
        FileOutputStream of = new FileOutputStream(filePath); // 输出文件路径
        of.write(json.getBytes());
        of.close();
        req.getSession().setAttribute(PropertyManager.getInstance().getValue("originalState"),originalState);
        req.getSession().setAttribute("nihao","nadaole");
        res.getWriter().print(new Gson().toJson(mapJson));
    }
    
    @RequestMapping("reservePrecinct")
    public void reservePrecinct( String VTDST10,boolean select ,HttpServletRequest req, HttpServletResponse res) throws IOException{
        System.out.println(VTDST10);
        System.out.println(select);
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
        System.out.println(originalState.getsName());
        
    }
        
        @RequestMapping("redistrict")
    public void redistrict(String stateName,String userEmail, List<String> reservedList,Preference preference,HttpServletRequest req, HttpServletResponse res) throws IOException{
        System.out.println("11111");
        System.out.println(reservedList.size());
        State originalState= (State) req.getSession().getAttribute(PropertyManager.getInstance().getValue("originalState"));
        originalState.setPreference(preference);
        originalState.setupGoodness();
        System.out.println(originalState.getsName());
        rsService.increaseRunningTimes(originalState.getRunningTimes(),originalState.getsName());
//        Set<CDistrict> cds = originalState.getCongressionalDistricts();
//        for (CDistrict cDistrict : cds) {
//            System.out.println(cDistrict.getName()+":"+cDistrict.calculateObjectiveFunction()+","+cDistrict.getCurrentGoodness());
//        }
        System.out.println(originalState.getsName());
        System.out.print("redistrict>>>oriGoodness:" + originalState.getCurrentGoodness()+"compactness"+originalState.getCompactness()+"pv:"+originalState.getPopulationVariance()+"racialF:"+originalState.getRacialFairness()+"partyF:"+originalState.getPatisanFairness());
        State workingState= originalState.clone(preference);
        System.out.println(workingState.getsName());
        System.out.println("clone");
        rsService.initializeNeighbors(workingState);
        System.out.println("initializeNeighbors");
        Precinct movedPrecinct = workingState.startAlgorithm();
        if(!workingState.checkTermination()){
            while(movedPrecinct==null){
                movedPrecinct = workingState.startAlgorithm();
                if(workingState.checkTermination()){
                    PrecinctProperty precinctProperty =  new PrecinctProperty();
                    precinctProperty.setTerminated(true);
                    System.out.println("redistrict loop terminate");
                    req.getSession().setAttribute(PropertyManager.getInstance().getValue("workingState"),workingState);
                    res.getWriter().print(new Gson().toJson(precinctProperty));
                    return ;
                }
            }
            System.out.print("redistrict>>>newGoodness:" + workingState.getCurrentGoodness()+" comnpactness: "+workingState.getCompactness()+" pv:"+workingState.getPopulationVariance()+"racialF:"+workingState.getRacialFairness()+"partyF:"+workingState.getPatisanFairness());
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
            System.out.println("redistrict:Terminated");
            PrecinctProperty precinctProperty =  new PrecinctProperty();
            precinctProperty.setTerminated(true);
            res.getWriter().print(new Gson().toJson(precinctProperty));
            return ;
        }
    }
    
    @RequestMapping("process")
    public void process(String userEmail,String fileName,HttpServletRequest req, HttpServletResponse res) throws IOException, URISyntaxException{
        State workingState = (State) req.getSession().getAttribute(PropertyManager.getInstance().getValue("workingState"));
        System.out.print("processs>>>originalGoodness : " + workingState.getCurrentGoodness());
//        if(fileName!=null){
//            System.out.println("save file");
//            System.out.println(workingState.getCongressionalDistricts().size());
//            System.out.println(workingState.getsName());
//           String email ="haha";
//            String filePath = this.getClass().getClassLoader().getResource("/").toURI().getPath()+"/"+email+"/"+fileName;
//            File file = new File(this.getClass().getClassLoader().getResource("/").toURI().getPath()+"/"+email);
//            if (!file.exists()) {
//                file.mkdirs();
//            }
////            State workingState = new State();
////            if(workingState==null){System.out.println("null");}
//            System.out.println("这里开始gson");
//            Gson gson = new Gson();
//            String json = gson.toJson(workingState);
////            String json = gson.toJson("a");
////            System.out.println("这里结束gson");
//            FileOutputStream of = new FileOutputStream(filePath); // 输出文件路径
//            of.write(json.getBytes());
////            of.write("a".getBytes());
//            of.close();
//            res.getWriter().print(new Gson().toJson("ok"));
//        }else{
            
        
        
        Precinct movedPrecinct = workingState.startAlgorithm();
        if(!workingState.checkTermination()){
            while(movedPrecinct==null){
                movedPrecinct = workingState.startAlgorithm();
                if(workingState.checkTermination()){
//                    res.getWriter().print(new Gson().toJson("{terminated:true}"));
                    PrecinctProperty precinctProperty =  new PrecinctProperty();
                    precinctProperty.setTerminated(true);
                    System.out.println("process loop terminate");
                    req.getSession().setAttribute(PropertyManager.getInstance().getValue("workingState"),workingState);
                    res.getWriter().print(new Gson().toJson(precinctProperty));
                    return ;
                }
            }
            System.out.println("processs>>>newGoodness:" + workingState.getCurrentGoodness()+"compactness"+workingState.getCompactness()+" pv:"+workingState.getPopulationVariance()+" racialF:"+workingState.getRacialFairness()+" partyF:"+workingState.getPatisanFairness());
            Set<CDistrict> cds = workingState.getCongressionalDistricts();
            for (CDistrict cDistrict : cds) {
                System.out.println("move!"+cDistrict.getName()+","+cDistrict.getPopulation());
            }
            PrecinctProperty precinctProperty =  new PrecinctProperty();
            precinctProperty.setupGoodness(workingState);
            precinctProperty.setFill(movedPrecinct.getFeature().getProperties().getFill());
            precinctProperty.setVTDST10(movedPrecinct.getPrecinctCode());
            req.getSession().setAttribute(PropertyManager.getInstance().getValue("workingState"),workingState);
            res.getWriter().print(new Gson().toJson(precinctProperty));
        }else{
            req.getSession().setAttribute(PropertyManager.getInstance().getValue("workingState"),workingState);
            System.out.println("terminated");
//            res.getWriter().print(new Gson().toJson("{terminated:true}"));
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
        Preference preference= new Preference();
        preference.setCOMPACTNESSWEIGHT(25);
        preference.setPOPULATIONVARIANCEWEIGHT(25);
        preference.setPARTISANFAIRNESSWEIGHT(25);
        preference.setRACIALFAIRNESSWEIGHT(25);
        originalState.setPreference(preference);
        originalState.setupGoodness();
        System.out.println(originalState.getCongressionalDistricts().size());
        stateInfo si = new stateInfo();
        si.setupGoodness(originalState);
        si.setArea(originalState.getArea());
        si.setAveIncome(originalState.getAveIncome());
        si.setNumOfCds(originalState.getCongressionalDistricts().size());
        si.setPopulation(originalState.getPopulation());
        si.setCompactness(originalState.getCompactness());
        si.setPatisanFairness(originalState.getPatisanFairness());
        si.setPopulationVariance(originalState.getPopulationVariance());
        si.setRacialFairness(originalState.getRacialFairness());
        si.setGoodness(originalState.getCurrentGoodness());
        System.out.println("details"+si.getDetails().size());
        int count = 0;
        Set<CDistrict> cds = originalState.getCongressionalDistricts();
        for (CDistrict cDistrict : cds) {
            Set<Precinct> precinct = cDistrict.getPrecinct();
            for (Precinct precinct2 : precinct) {
                count++;
            }
        }
        //compactness, goodness, fairness , effeciency gap
        si.setNumOfPds(count);
        si.setArea(originalState.getArea());
        res.getWriter().print(new Gson().toJson(si));
    }
    
    @RequestMapping("editProperties")
    public void editProperties(HttpServletRequest req, HttpServletResponse res) throws IOException, URISyntaxException{
        Desktop.getDesktop().open(new File(this.getClass().getClassLoader().getResource("/").toURI().getPath()+"/constants.properties"));
        res.getWriter().print(new Gson().toJson(1));
    }
    
    @RequestMapping("getCompareState")
    public void getCompareState(String stateName,int year,HttpServletRequest req, HttpServletResponse res) throws IOException, URISyntaxException{
//        State originalState = rsService.getStateForCompare(stateName);
        State originalState= (State) req.getSession().getAttribute(PropertyManager.getInstance().getValue("originalState"));
        Preference preference= new Preference();
        preference.setCOMPACTNESSWEIGHT(25);
        preference.setPOPULATIONVARIANCEWEIGHT(25);
        preference.setPARTISANFAIRNESSWEIGHT(25);
        preference.setRACIALFAIRNESSWEIGHT(25);
        originalState.setPreference(preference);
        originalState.setupGoodness();
        System.out.println(originalState.getCongressionalDistricts().size());
        stateInfo si = new stateInfo();
        si.setupGoodness(originalState);
        si.setArea(originalState.getArea());
        si.setAveIncome(originalState.getAveIncome());
        si.setNumOfCds(originalState.getCongressionalDistricts().size());
        si.setPopulation(originalState.getPopulation());
        si.setCompactness(originalState.getCompactness());
        si.setPatisanFairness(originalState.getPatisanFairness());
        si.setPopulationVariance(originalState.getPopulationVariance());
        si.setRacialFairness(originalState.getRacialFairness());
        si.setGoodness(originalState.getCurrentGoodness());
        List<Representive> repres= rsService.getRepresents(stateName,year);
        si.setupRepre(repres);
        System.out.println("details"+si.getDetails().size());
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
        si.setArea(originalState.getArea());
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
        String filePath = this.getClass().getClassLoader().getResource("/").toURI().getPath()+"/"+email+"/"+fileName;
        String stateInput = readToString(filePath);
        Gson gson = new Gson();
        State state = gson.fromJson(stateInput, State.class);
        state.setRedistrictTimes(0);
        PrecinctJson precinctJson = new PrecinctJson();
        precinctJson.setsName(state.getsName());
        precinctJson.setCOMPACTNESSWEIGHT(state.getPreference().getCOMPACTNESSWEIGHT());
        precinctJson.setPARTISANFAIRNESSWEIGHT(state.getPreference().getPARTISANFAIRNESSWEIGHT());
        precinctJson.setPOPULATIONVARIANCEWEIGHT(state.getPreference().getPOPULATIONVARIANCEWEIGHT());
        precinctJson.setRACIALFAIRNESSWEIGHT(state.getPreference().getRACIALFAIRNESSWEIGHT());
        precinctJson.setContiguity(state.getPreference().getIsContiguity());
        precinctJson.setNaturalBoundary(state.getPreference().getIsNaturalBoundary());
        Set<Feature> features = precinctJson.getFeatures();
        Set<CDistrict> cds = state.getCongressionalDistricts();
        for (CDistrict cDistrict : cds) {
            Set<Precinct> precincts = cDistrict.getPrecinct();
            for (Precinct precinct : precincts) {
                features.add(precinct.getFeature());
            }
        }
        String json = gson.toJson(precinctJson);
        res.getWriter().print(json);
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
        if(originalState==null){
            System.out.println("original null");
        }
//        State workingState = new State();
        System.out.println(fileName);
        Object attribute = req.getSession().getAttribute("nihao");
        if(attribute==null){
            System.out.println("attrictu null!!!");
        }
        System.out.println(attribute.toString());
        if(workingState==null){System.out.println("null");}
        System.out.println("这里开始gson");
        Gson gson = new Gson();
        String json = gson.toJson(workingState);
        System.out.println("这里结束gson");
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
            System.out.println(string);
        }
        res.getWriter().print(new Gson().toJson(str));
    }
    @RequestMapping("removeFile")
    public void removeFile( String email,String fileName,HttpServletRequest req, HttpServletResponse res) throws Exception {
        String filePath = this.getClass().getClassLoader().getResource("/").toURI().getPath()+"/"+"haha"+"/"+fileName;
        System.out.println(filePath);
        File file = new File(filePath);
        if (file.exists()) {
            System.out.println("jinlai");
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
    //Todo
    @RequestMapping("exportTest")
    public void exportTest( String email,String fileName,HttpServletRequest req, HttpServletResponse res) throws Exception {
        System.out.println("exportState");
        email ="haha";
        String filePath = this.getClass().getClassLoader().getResource("/").toURI().getPath()+"/"+email+"/"+fileName;
        File file = new File(this.getClass().getClassLoader().getResource("/").toURI().getPath()+"/"+email);
        if (!file.exists()) {
            file.mkdirs();
        }
//        State workingState = (State) req.getSession().getAttribute(PropertyManager.getInstance().getValue("workingState"));
        State workingState = new State();
        Set<CDistrict> congressionalDistricts = workingState.getCongressionalDistricts();
        CDistrict ciCDistrict= new CDistrict();
        congressionalDistricts.add(ciCDistrict);
        Precinct precinct= new Precinct();
        ciCDistrict.getPrecinct().add(precinct);
        
        String attribute = (String) req.getSession().getAttribute("nihao");
        System.out.println(attribute);
        Gson gson = new Gson();
        String json = gson.toJson(workingState);
        FileOutputStream of = new FileOutputStream(filePath); // 输出文件路径
        of.write(json.getBytes());
        of.close();
        res.getWriter().print(json);
    }
    public boolean getMouthlyReport (String mouth){return true;}
    public boolean addNewState (String stateName){return true;}
    public boolean getState (String stateName){return true;}
    public boolean deleteState (String stateName){return true;}
    public boolean findState (String stateID){return true;}
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
                System.out.println(codinates.get(0));
                System.out.println(codinates.get(1));
                break;
            }
            System.out.println(list.get(1));
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
    @RequestMapping("saveData")
    public void a( HttpServletRequest req, HttpServletResponse res) throws Exception {
        System.out.println("aaa");
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
