package pojo;

public enum ObjectElement {
    COMPACTNESSWEIGHT(25),
    PARTISANFAIRNESSWEIGHT(25),
    POPULATIONVARIANCEWEIGHT(25),
    RACIALFAIRNESSWEIGHT(25);
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
