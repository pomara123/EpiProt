package epiprot.services.uniprot;

import java.util.ArrayList;

public class DBentry {
	private String id;
	private String type;
	private ArrayList<DBproperty> dbPropertiesList;
	
	public DBentry (String id, String type) {
		this.id = id;
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ArrayList<DBproperty> getDbPropertiesList() {
		return dbPropertiesList;
	}
	public void setDbPropertiesList(ArrayList<DBproperty> dbPropertiesList) {
		this.dbPropertiesList = dbPropertiesList;
	}
	public void addDbProperty(DBproperty dbProperty) {
		this.dbPropertiesList.add(dbProperty);
	}
}
