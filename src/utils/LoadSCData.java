package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import pojo.CDistrict;
import pojo.Party;
import pojo.Precinct;
import pojo.State;
import pojo.mapJson.Feature;
import pojo.mapJson.PrecinctJson;

public class LoadSCData {
    String path ;
    public LoadSCData() throws URISyntaxException{
        this.path=this.getClass().getClassLoader().getResource("/").toURI().getPath();
    }
    @Test
    public void test() throws Exception{
        State workingState = getState();
    }
    @Test
    public ArrayList<ArrayList<String>> loadNeighbors() throws Exception{
        File file = new File(path+"/"+"NH_candidateNeighbors_v2(1).txt");
//        File file = new File("NH_candidateNeighbors_v2(1).txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        ArrayList<String> pList = new ArrayList<String>();
        ArrayList<String> neighborList = new ArrayList<String>();
        String st;
        int i=1;
        while ((st = br.readLine()) != null){
            if(i%2==1){
                pList.add(st);
            }else{
                neighborList.add(st);
            }
            i++;
        }
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        list.add(pList);
        list.add(neighborList);
        return list;
        
    }
    //cd2 647464   cd1 669006
    public State getState() throws Exception{
        State workingState = new State();
        workingState.initialStateByNumOfCDs(7);
        workingState.setPopulation(5088916);
        ArrayList<Precinct> precincts = getPrecincts();
        setupCD(precincts,workingState);
        setVotesForPrecincts(workingState);
        setRegister(workingState);
        setUpPopulationAndVotes(workingState);
        
        
//        Set<CDistrict> cds = workingState.getCongressionalDistricts();
//        long sum=0;
//        for (CDistrict cDistrict : cds) {
////            System.out.println(cDistrict.getName()+","+cDistrict.getVote().get("DEMOCRATIC")+","+cDistrict.getVote().get("REPUBLICAN"));
//            System.out.println(cDistrict.getName()+","+cDistrict.getPopulation()+","+cDistrict.getRegisterVoters());
//            sum = sum+cDistrict.getPopulation();
//        }
//        System.out.println(sum);
//        
        
//        FileOutputStream of = new FileOutputStream("d:NHcds.json"); // 输出文件路径
//        Gson gson = new Gson();
//        String json = gson.toJson(jsonData);
//        of.write(json.getBytes());
//        of.close();
        return workingState;
    }
    private void setupCD(ArrayList<Precinct> precincts,State workingState) throws Exception {
        test excelReader = new test(path+"/CD1/z_cd1_relation.xlsx");
        Map<Integer, Map<Integer, Object>> map = excelReader.readExcelContent();
        System.out.println("size:"+precincts.size());
        for (Precinct precinct : precincts) {
            for (int i = 1; i < map.size(); i++) {
                Map<Integer, Object> row = map.get(i);
                if(precinct.getName().equals(row.get(0).toString())){
//                    System.out.println(row.get(0).toString()+","+row.get(1).toString());
                    CDistrict cd = getCdByName(row.get(1).toString(), workingState.getCongressionalDistricts());
//                    char charAt = cd.getName().charAt(cd.getName().length()-1);
//                    String s = String.valueOf(charAt);
//                    cd.setCdCode(Integer.parseInt(s)+2);
                    precinct.setCdistrictId(cd.getCdCode());
                    precinct.setStateId(3);
                    cd.getPrecinct().add(precinct);
                    break;
                }
            }
        }
        
    }
    private ArrayList<Precinct> getPrecincts() throws IOException {
        File file = new File(path+"/"+"sc_st45_sc_vtd.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        ArrayList<String> list = new ArrayList<String>();
        String st;
        int i=1;
        while ((st = br.readLine()) != null){
            if(i%2==1){
                list.add(st);
            }
            i++;
        }
        Map<String,String> map = new HashMap<String,String>();
        ArrayList<String> listName = new ArrayList<String>();
        ArrayList<Precinct> precincts = new ArrayList<Precinct>();
        for (String str : list) {
            //1+2+4 = pCode   3 county ,   5 pName
            Precinct p = new Precinct();
            String[] strs=str.split(",");
            p.setName(strs[5].toString());
            p.setPrecinctCode(strs[1].toString()+strs[2].toString()+strs[4].toString());
            p.setCounty(strs[3].toString());
            //System.out.println(p.getName()+","+p.getCounty()+","+p.getPrecinctCode());
            precincts.add(p);
//            map.put(strs[5].toString(), strs[4].toString());
        }
        return precincts;
    }
    //my method
    
    
    private void setUpGeoCds(State workingState, PrecinctJson jsonData) {
        CDistrict cd1 = getCdByName("cd1", workingState.getCongressionalDistricts());
        CDistrict cd2 = getCdByName("cd2", workingState.getCongressionalDistricts());
        Set<Feature> features = jsonData.getFeatures();
        for (Feature feature : features) {
            if(feature.getProperties().getCD115FP().equals("01")){
                feature.getProperties().setPOPULATION(cd1.getPopulation());
                feature.getProperties().setRVOTES(cd1.getVotes().get(Party.REPUBLICAN));
                feature.getProperties().setDVOTES(cd1.getVotes().get(Party.DEMOCRATIC));
            }else{
                feature.getProperties().setPOPULATION(cd2.getPopulation());
                feature.getProperties().setRVOTES(cd2.getVotes().get(Party.REPUBLICAN));
                feature.getProperties().setDVOTES(cd2.getVotes().get(Party.DEMOCRATIC));
            }
        }
    }

    private void setUpGeoPrecincts(State workingState,PrecinctJson precinctJson) {
        Set<CDistrict> cds = workingState.getCongressionalDistricts();
        int count=0;
        int colorCount=1;
        for (CDistrict cd : cds) {
            Set<Precinct> ps = cd.getPrecinct();
            for (Precinct p : ps) {
                for (Feature f : precinctJson.getFeatures()) {
                    if(f.getProperties().getVTDST10().equals(p.getPrecinctCode())){
                        f.getProperties().setPOPULATION(p.getPopulation());
                        f.getProperties().setREGISTERVOTERS(p.getRegisteredVoters());
                        f.getProperties().setTOTALVOTERS(p.getTotalVoters());
                        f.getProperties().setRVOTES(p.getVotes().get(Party.REPUBLICAN));
                        f.getProperties().setDVOTES(p.getVotes().get(Party.DEMOCRATIC));
                        f.getProperties().setFill(Integer.toString(colorCount));
                        count++;
                        break;
                    }
                }
            }
            colorCount++;
        }
    }

    private void setUpPopulationAndVotes(State workingState) throws Exception {
        Set<CDistrict> cds = workingState.getCongressionalDistricts();
        //set up State votes
        int registerVote=0;
        for (CDistrict cDistrict : cds) {
            registerVote=registerVote+cDistrict.getRegisterVoters();
        }
        // set up population for all precincts
        for (CDistrict cDistrict : cds) {
            Set<Precinct> ps = cDistrict.getPrecinct();
            for (Precinct p : ps) {
                p.setPopulation((workingState.getPopulation())*p.getRegisteredVoters()/registerVote);
                cDistrict.setPopulation(cDistrict.getPopulation()+p.getPopulation());
            }
        }
    }

    public void setRegister(State workingState) throws Exception{
        String filepath = path+"/"+"/CD1/populationCD1.xlsx";
        test excelReader = new test(filepath);
        Map<Integer, Map<Integer, Object>> map = excelReader.readExcelContent();
        for (int i = 2; i <= map.size(); i++) {
            Map<Integer, Object> row = map.get(i); //election data row
            System.out.println(row);
            //0 pName 1registerVotes  2 totalVotes
            Precinct p = getPrecintByName((String)row.get(0),workingState);
            if(p!=null){
                p.setRegisteredVoters((int) (Double.parseDouble((String) row.get(1))));
                p.setTotalVoters((int) (Double.parseDouble((String) row.get(2))));
            }
        }
        Set<CDistrict> cds = workingState.getCongressionalDistricts();
        for (CDistrict cd : cds) {
            Set<Precinct> ps = cd.getPrecinct();
                for (Precinct p : ps) {
                    cd.setRegisterVoters(cd.getRegisterVoters()+p.getRegisteredVoters());
//                    cd.setTotalVoters(cd.getTotalVoters()+p.getTotalVoters());
                }
        }
    }
    
    public void setVotesForPrecincts(State workingState) throws Exception{
        String filepath = path+"/"+"/CD1/z_cd1_electionData.xlsx";
        test excelReader = new test(filepath);
        Map<Integer, Map<Integer, Object>> map = excelReader.readExcelContent();
        for (int i = 2; i <= map.size(); i++) {
            Map<Integer, Object> row = map.get(i); //election data row
            //0 pName 2 dVotes 4 rVotes
            Precinct precinct = getPrecinctByName((String)row.get(0),workingState.getCongressionalDistricts());
            if(precinct!=null){
                precinct.getVote().put("DEMOCRATIC", (int)Double.parseDouble((String) row.get(2)));
                precinct.getVote().put("REPUBLICAN", (int)Double.parseDouble((String) row.get(4)));
            }
        }
        Set<CDistrict> cds = workingState.getCongressionalDistricts();
        for (CDistrict cd : cds) {
            HashMap<String, Integer> vote = cd.getVote();
            vote.put("REPUBLICAN",0);
            vote.put("DEMOCRATIC",0);
            Set<Precinct> ps = cd.getPrecinct();
                for (Precinct p : ps) {
                    if(p.getVote().size()!=0){
                        vote.put("REPUBLICAN", vote.get("REPUBLICAN") + p.getVote().get("REPUBLICAN"));
                        vote.put("DEMOCRATIC", vote.get("DEMOCRATIC") + p.getVote().get("DEMOCRATIC"));
                    }
                }
        }
    }
    
    private Precinct getPrecinctByName(String string, Set<CDistrict> congressionalDistricts) {
        for (CDistrict cDistrict : congressionalDistricts) {
            Set<Precinct> precincts = cDistrict.getPrecinct();
            for (Precinct p : precincts) {
                if(p.getName().equals(string)){
                    return p;
                }
            }
        }
        return null;
        
    }
    private int getTotal(String a,String b,String c,String d,String e) {
        int a1=0;
        int b1=0;
        int c1=0;
        int d1=0;
        int e1=0;
        if(!a.equals("")){
            a1 = (int) (Double.parseDouble(a));
        }
        if(!b.equals("")){
            b1 = (int) (Double.parseDouble(b));    
                }
        if(!c.equals("")){
            c1 = (int) (Double.parseDouble(c));
        }
        if(!d.equals("")){
            d1 = (int) (Double.parseDouble(d));   
                }
        if(!e.equals("")){
            e1 = (int) (Double.parseDouble(e));
        }
        return a1+b1+c1+d1+e1;
    }


    private void setUpPrecinctCode(Set<CDistrict> cds) throws IOException{
        File file = new File(path+"/"+"new_hampshire_st33_nh_vtd.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        ArrayList<String> relationList = new ArrayList<String>();
        String st;
        int i=1;
        while ((st = br.readLine()) != null){
            if(i%2==1){
                relationList.add(st);
            }
            i++;
        }
        Map<String,String> map = new HashMap<String,String>();
        for (String str : relationList) {
            String[] strs=str.split(",");
            map.put(strs[5].toString(), strs[4].toString());
        }
        for (Map.Entry<String, String> entry : map.entrySet())
        {
            for (CDistrict cd : cds) {
                Set<Precinct> ps = cd.getPrecinct();
                for (Precinct precinct : ps) {
                        if(precinct.getName().equals(entry.getKey())){
                            precinct.setPrecinctCode(entry.getValue());
                        }
                }
            }
        }
    }
    
    
    private Precinct getPrecintByName(String string,State workingState) {
        Set<CDistrict> cds = workingState.getCongressionalDistricts();
        for (CDistrict cDistrict : cds) {
            Set<Precinct> ps = cDistrict.getPrecinct();
            for (Precinct precinct : ps) {
                if(precinct.getName().equals(string)){
                    return precinct;
                }
            }
        }
        return null;
    }
    private void loadPrecinctToCd(String filepath, CDistrict cd1,int numOfPrecints) throws Exception {
        test excelReader = new test(filepath);
        Map<Integer, Map<Integer, Object>> map = excelReader.readExcelContent();
        for (int i = 3; i < numOfPrecints; i++) {
            Precinct p = new Precinct();
            Map<Integer, Object> row = map.get(i); //election data row
            p.setName((String)row.get(0));
            p.setCDistrict(cd1);
            cd1.getPrecinct().add(p);
        }
    }
    private static CDistrict getCdByName(String name, Set<CDistrict> cds) {
        for (CDistrict cDistrict : cds) {
            if (cDistrict.getName().equals(name)) {
                return cDistrict;
            }
        }
        return null;
    }
    //xlsx methods
    private Object getCellFormatValue(Cell cell) {
        Object cellvalue = "";
        if (cell != null) {
            switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
            case Cell.CELL_TYPE_FORMULA: {
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    cellvalue = date;
                } else {
                    cellvalue = String.valueOf(cell.getNumericCellValue());
                }
                break;
            }
            case Cell.CELL_TYPE_STRING:
                cellvalue = cell.getRichStringCellValue().getString();
                break;
            default:
                cellvalue = "";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;
    }
    private Workbook wb;

    private Sheet sheet;

    private Row row;

    public int getState(int x, int y) {
        return x + y;
    }
    public String[] readExcelTitle() throws Exception {
        if (wb == null) {
            throw new Exception("Workbook is null！");
        }
        sheet = wb.getSheetAt(0);
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        System.out.println("colNum:" + colNum);
        String[] title = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            title[i] = row.getCell(i).getCellFormula();
        }
        return title;
    }

    public Map<Integer, Map<Integer, Object>> readExcelContent() throws Exception {
        if (wb == null) {
            throw new Exception("Workbook is null！");
        }
        Map<Integer, Map<Integer, Object>> content = new HashMap<Integer, Map<Integer, Object>>();

        sheet = wb.getSheetAt(0);
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            int j = 0;
            Map<Integer, Object> cellValue = new HashMap<Integer, Object>();
            while (j < colNum) {
                Object obj = getCellFormatValue(row.getCell(j));
                cellValue.put(j, obj);
                j++;
            }
            content.put(i, cellValue);
        }
        return content;
    }
}
