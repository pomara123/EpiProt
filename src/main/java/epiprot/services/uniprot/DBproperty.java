package epiprot.services.uniprot;

public class DBproperty {
	private String type;
	private String value;
	
	public DBproperty (String type, String value) {
		this.type = type;
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
