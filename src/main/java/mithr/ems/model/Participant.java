package mithr.ems.model;

public class Participant {
	private String name;
	
	public Participant(final String name){
		this.name=name;
	}
	
	public String getName(){
		return this.name;
	}
	
	@Override
	public String toString(){
		return this.name;
	}
	
	/*@Override
	public boolean equals(Object object){
		if(object instanceOf Participant )
	}*/
	

}
