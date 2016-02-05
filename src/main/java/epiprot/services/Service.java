package epiprot.services;

import java.util.TimerTask;

public abstract class Service extends TimerTask {
	
	// Directory of the project: Users/Patrick/Documents/workspace/epiprot/
	public final static String SERVICEFILEPATH = System.getProperty("user.dir").toString() + "/";
	
	public Service () {}
}
