package de.mrtroble.sitzplan;

public class BanndPaare {
	
	private String m81,m82;
	
    public BanndPaare(String str,String str2) {
    	m81 = str;
    	m82 = str2;
    }

	public String getM81() {
		return m81;
	}

	public void setM81(String m81) {
		this.m81 = m81;
	}

	public String getM82() {
		return m82;
	}

	public void setM82(String m82) {
		this.m82 = m82;
	}
	
	public boolean isBanned(String str1,String str2){
		str1 = str1.replace(" ", "");
		str2 = str2.replace(" ", "");
		m81 = m81.replace(" ", "");
		m82 = m82.replace(" ", "");
		if(str1.equals(str2))return true;
		if(str1.contentEquals(m81) || str1.contentEquals(m82)){
			if(str2.contentEquals(m81) || str2.contentEquals(m82)){
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return m81 + ":" + m82;
	}
}
