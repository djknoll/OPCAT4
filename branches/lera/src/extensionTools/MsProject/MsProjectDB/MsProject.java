package extensionTools.MsProject.MsProjectDB;
import java.util.*;

import exportedAPI.opcatAPIx.IXSystem;

public class MsProject {
	public static java.util.Hashtable<String, MsAssignment>assignments = new java.util.Hashtable<String, MsAssignment>();
	public static Vector<MsTask>firstTasks = new Vector<MsTask>();
	public static MsTask firstTask;
	public static String title = "Project";
	public static java.util.Hashtable<String, MsTask>tasks = new java.util.Hashtable<String, MsTask>();
	public static java.util.Hashtable<String, MsResource>resources = new java.util.Hashtable<String, MsResource>();
	public static IXSystem opcatSystem;
	private static MsProject inst = null;
	private MsProject(){inst = this;}
	public static MsProject getInstance(){
		if (inst == null){inst = new MsProject();}
		return inst;
	}
	public static void setOPMProject(IXSystem opm){
		opcatSystem = opm;
	}
}
