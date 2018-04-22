package pojo;

import utils.PropertyManager;

public enum ObjectElement {
    COMPACTNESSWEIGHT(Integer.parseInt((PropertyManager.getInstance().getValue("objElement.COMPACTNESSWEIGHT")))),
    PARTISANFAIRNESSWEIGHT(Integer.parseInt((PropertyManager.getInstance().getValue("objElement.PARTISANFAIRNESSWEIGHT")))),
    POPULATIONVARIANCEWEIGHT(Integer.parseInt((PropertyManager.getInstance().getValue("objElement.POPULATIONVARIANCEWEIGHT")))),
    RACIALFAIRNESSWEIGHT(Integer.parseInt((PropertyManager.getInstance().getValue("objElement.RACIALFAIRNESSWEIGHT"))));
    private int weight;
    
    ObjectElement(int weight){
        this.weight=weight;
    }
    
    public int getWeight(){
    	return weight;
    }
    
    public float calculate(){
		return 0;
    }
}
