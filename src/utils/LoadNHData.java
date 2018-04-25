package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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

import com.google.gson.Gson;

public class LoadNHData {
    @Test
    public void test() throws Exception{
        getState();
    }
    
    public State getState() throws Exception{
        State workingState = new State();
        workingState.initialStateByNumOfCDs(2);
        workingState.setPopulation(1316470);
        //load precincts to cds
        CDistrict cd1 = getCdByName("cd1", workingState.getCongressionalDistricts());
        CDistrict cd2 = getCdByName("cd2", workingState.getCongressionalDistricts());
        int numOfPrecinctCd1=117;
        int numOfPrecinctCd2=209;
        loadPrecinctToCd("Representatives in Congress Results  By CD By Precinct/Congressional District 1-excek.xlsx",cd1,numOfPrecinctCd1);
        loadPrecinctToCd("Representatives in Congress Results  By CD By Precinct/Congressional District 2-excel.xlsx",cd2,numOfPrecinctCd2);
        //read relation file
        setUpPrecinctCode(workingState.getCongressionalDistricts());
        //read president votes
        setVotesForPrecincts(workingState);
        //set up register voters and total voters
        setRegister(workingState);
        //set up population
        setUpPopulationAndVotes(workingState);
        
        //set up geo precincts
        LoadJsonData ld = new LoadJsonData();
        PrecinctJson jsonData = ld.getOhioJsonData();
        setUpGeoPrecincts(workingState,jsonData);
        
        FileOutputStream of = new FileOutputStream("d:/NHprecincts.json"); // 输出文件路径
        Gson gson = new Gson();
        String json = gson.toJson(jsonData);
        of.write(json.getBytes());
        of.close();
        return workingState;
    }
    //my method
    
    
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
                        f.getProperties().setColor(colorCount);
                        count++;
                        break;
                    }
                }
            }
            colorCount++;
        }
        System.out.println(count);
        
    }

    private void setUpPopulationAndVotes(State workingState) throws Exception {
        Set<CDistrict> cds = workingState.getCongressionalDistricts();
        HashMap<Party, Integer> voteState = workingState.getVotes();
        //set up State votes
        for (CDistrict cDistrict : cds) {
            voteState.put(Party.REPUBLICAN, cDistrict.getVotes().get(Party.REPUBLICAN)+voteState.get(Party.REPUBLICAN));
            voteState.put(Party.DEMOCRATIC, cDistrict.getVotes().get(Party.DEMOCRATIC)+voteState.get(Party.DEMOCRATIC));
        }
        // set up population for all precincts
        for (CDistrict cDistrict : cds) {
            Set<Precinct> ps = cDistrict.getPrecinct();
            for (Precinct p : ps) {
                p.setPopulation((workingState.getPopulation())*p.getRegisteredVoters()/(voteState.get(Party.REPUBLICAN)+voteState.get(Party.DEMOCRATIC)));
                cDistrict.setPopulation(cDistrict.getPopulation()+p.getPopulation());
            }
        }
    }

    public void setRegister(State workingState) throws Exception{
        String filepath = "registerAndTotalVoters.xlsx";
        test excelReader = new test(filepath);
        Map<Integer, Map<Integer, Object>> map = excelReader.readExcelContent();
        for (int i = 2; i <= map.size(); i++) {
            Map<Integer, Object> row = map.get(i); //election data row
            Precinct p = getPrecintByName((String)row.get(0),workingState);
            if(p!=null){
                p.setRegisteredVoters((int) (Double.parseDouble((String) row.get(4))));
            }
        }
    }
    
    public void setVotesForPrecincts(State workingState) throws Exception{
        String filepath = "President Election Results By County By Precinct/President ALL Counties.xlsx";
        test excelReader = new test(filepath);
        Map<Integer, Map<Integer, Object>> map = excelReader.readExcelContent();
        for (int i = 1; i <= map.size(); i++) {
            Map<Integer, Object> row = map.get(i); //election data row
            Precinct p = getPrecintByName((String)row.get(0),workingState);
            if(p!=null){
                HashMap<Party, Integer> votes = p.getVotes();
                int a =getTotal((String)row.get(1),(String)row.get(2),(String)row.get(3),(String)row.get(4),(String)row.get(5));
                p.setTotalVoters(a);
                votes.put(Party.REPUBLICAN, (int) (Double.parseDouble((String) row.get(1))));
                votes.put(Party.DEMOCRATIC, (int) (Double.parseDouble((String) row.get(2))));
            }
        }
        Set<CDistrict> cds = workingState.getCongressionalDistricts();
        for (CDistrict cd : cds) {
            HashMap<Party, Integer> votes = cd.getVotes();
            Set<Precinct> ps = cd.getPrecinct();
                for (Precinct p : ps) {
                    votes.put(Party.REPUBLICAN, votes.get(Party.REPUBLICAN) + p.getVotes().get(Party.REPUBLICAN));
                    votes.put(Party.DEMOCRATIC, votes.get(Party.DEMOCRATIC) + p.getVotes().get(Party.DEMOCRATIC));
                }
        }
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
        File file = new File("new_hampshire_st33_nh_vtd.txt");
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
