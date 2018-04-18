package pojo;

public enum CompactnessElement {
	Reock(1),
	ConvexHull(1),
	PolsbyPopper(1);
	private int weight;
	
	CompactnessElement(int weight){
        this.weight=weight;
    }
	
    public int getWeight(){
    	return weight;
    }
    
    public float calculate(){
		return 0;
    	
    }

}
