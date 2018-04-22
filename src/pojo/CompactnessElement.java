package pojo;

import utils.PropertyManager;

public enum CompactnessElement {
	Reock(Integer.parseInt((PropertyManager.getInstance().getValue("compactnessElement.Reock")))),
	ConvexHull(Integer.parseInt((PropertyManager.getInstance().getValue("compactnessElement.ConvexHull")))),
	PolsbyPopper(Integer.parseInt((PropertyManager.getInstance().getValue("compactnessElement.PolsbyPopper"))));
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
