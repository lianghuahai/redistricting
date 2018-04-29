package utils;

import java.util.HashMap;
import java.util.Map;

public class ReportJob {  
    private ReportType reportType;
    private Map map = new HashMap<ReportType, Integer>();
    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }  
    
}