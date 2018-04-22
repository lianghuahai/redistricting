package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.Gson;

import pojo.CDistrict;
import pojo.Party;
import pojo.Precinct;
import pojo.State;
import pojo.mapJson.Feature;
import pojo.mapJson.precinctJson;


public class test {
    /**
     * ohio.xlsx is the file that contains the relation between congressional districts and
     * precincts(determine that a precinct belongs to which CD) president.xlsx is the file that
     * contains the election data
     * 
     * @param args
     * @throws IOException
     */
    private final static int ohioNumOfCDs =16;
    private final static int NewHampshireNumOfCDs =2;
    private final static int coloradoNumOfCDs =7;
    public static void main(String[] args) throws IOException {
        try {
            String filepath2 = "ohio.xlsx";
            test excelReader2 = new test(filepath2);
            Map<Integer, Map<Integer, Object>> map2 = excelReader2.readExcelContent();
            // Initial State
            State workingState = new State();
            workingState.initialStateByNumOfCDs(ohioNumOfCDs);
            
            // load election data for every precinct
            String filepath = "president.xlsx";
            test excelReader = new test(filepath);
            Map<Integer, Map<Integer, Object>> map = excelReader.readExcelContent();
            System.out.println("Excel Data:");
            String temp ="Adams";
            int count = 1;
            for (int i = 4; i <= map.size(); i++) {
                    Map<Integer, Object> row = map.get(i); //election data row
                    Map<Integer, Object> row2 = map2.get(i); //relation row(cd and precinct)
                    Precinct workingP = new Precinct();
                    // set up precinct data
                    if(!temp.equals((String)row.get(0))){
                       temp = (String)row.get(0);
                       count+=2;
                    }
                    if(count<10){
                        workingP.setPrecinctCode("00"+count+(String)row.get(2));
                    }else if(count>=10 && count <100){
                        workingP.setPrecinctCode("0"+count+(String)row.get(2));
                        
                    }else{
                        workingP.setPrecinctCode(count+(String)row.get(2));
                    }
                    workingP.setName((String) row.get(1));
                    workingP.setRegisteredVoters(((int) (Double.parseDouble((String) row.get(5)))));
                    workingP.setTotalVoters(((int) (Double.parseDouble((String) row.get(6)))));
                    HashMap<Party, Integer> votes = workingP.getVotes();
                    votes.put(Party.DEMOCRATIC, ((int) (Double.parseDouble((String) row.get(11)))));
                    votes.put(Party.REPUBLICAN, ((int) (Double.parseDouble((String) row.get(30)))));
                    votes.put(Party.GREEN, ((int) (Double.parseDouble((String) row.get(27)))));
                    // Add the precinct to its congressional district
                    for (int j = 14; j < 59; j++) {
                        if (((int) (Double.parseDouble((String) row2.get(j)))) != 0) {
                            String cdName = getCdByIndex(j);
                            CDistrict cd = getCdByName(cdName, workingState.getCongressionalDistricts());
                            workingP.setCDistrict(cd);
                            Set<Precinct> precincts = cd.getPrecinct();
                            precincts.add(workingP);
                            break;
                        }
                    }
//                }
            }
            //load geo json
            LoadJsonData ld = new LoadJsonData();
            precinctJson ohioJsonData = ld.getOhioJsonData();
            // set up each color of congressional districts  
            int count1 = 1;
            Set<CDistrict> cdss = workingState.getCongressionalDistricts();
            for (CDistrict cd : cdss) {
                Set<Precinct> precincts = cd.getPrecinct();
                for (Precinct p : precincts) {
                    for (Feature ff : ohioJsonData.getFeatures()) {
                        if(p.getPrecinctCode().equals(ff.getProperties().getVTDST10())){
                            ff.getProperties().setFill(String.valueOf(count1));
                            break;
                        }
                    }
                }
                count1++;
            }
            String outputJson = new Gson().toJson(ohioJsonData);
            try {  
                FileOutputStream out = new FileOutputStream("d:/wendeyipi.json"); // 输出文件路径  
                out.write(outputJson.getBytes());  
                out.close();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  

        } catch (FileNotFoundException e) {
            System.out.println("Not file existed!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
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

    private static String getCdByIndex(int num) {
        if (num >= 14 & num <= 17) {
            return "cd1";
        } else if (num >= 18 & num <= 20) {
            return "cd2";
        } else if (num >= 21 & num <= 22) {
            return "cd3";
        } else if (num >= 23 & num <= 24) {
            return "cd4";
        } else if (num >= 25 & num <= 26) {
            return "cd5";
        } else if (num >= 27 & num <= 28) {
            return "cd6";
        } else if (num >= 29 & num <= 31) {
            return "cd7";
        } else if (num >= 32 & num <= 34) {
            return "cd8";
        } else if (num >= 35 & num <= 37) {
            return "cd9";
        } else if (num >= 38 & num <= 41) {
            return "cd10";
        } else if (num >= 42 & num <= 43) {
            return "cd11";
        } else if (num >= 44 & num <= 47) {
            return "cd12";
        } else if (num >= 48 & num <= 50) {
            return "cd13";
        } else if (num >= 51 & num <= 53) {
            return "cd14";
        } else if (num >= 54 & num <= 55) {
            return "cd15";
        } else if (num >= 56 & num <= 57) {
            return "cd16";
        } else {
            return "";
        }
    }

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

    public test(String filepath) throws IOException {
        if (filepath == null) {
            return;
        }
        String ext = filepath.substring(filepath.lastIndexOf("."));

        InputStream is = new FileInputStream(filepath);
        if (".xls".equals(ext)) {
            wb = new HSSFWorkbook(is);
        } else if (".xlsx".equals(ext)) {
            wb = new XSSFWorkbook(is);
        } else {
            wb = null;
        }
    }

    public test() {
        // TODO Auto-generated constructor stub
    }

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
