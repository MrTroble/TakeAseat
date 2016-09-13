package de.mrtroble.sitzplan;

public class ClassM8 {
	
	private String name;
	private short place;

	public ClassM8(String name,short place) {
		this.name = name;
		this.place = place;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getPlace() {
		return place;
	}

	public void setPlace(short place) {
		this.place = place;
	}
	
	@Override
	public String toString() {
		return this.name + ":" + this.place;
	}	
	
}
