package extensionTools.MsProject;
import javax.swing.*;

import java.awt.Color;
import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import exportedAPI.opcatAPIx.*;
import extensionTools.MsProject.MsProjectDB.MsAssignment;
import extensionTools.MsProject.MsProjectDB.MsProject;
import extensionTools.MsProject.MsProjectDB.MsResource;
import extensionTools.MsProject.MsProjectDB.MsTask;
public class ProjectLoader {
	//private JProgressBar pBar;
	private Document doc;
	DocumentBuilder db;
	IXSystem opcatSystem;

	public ProjectLoader()
	{
	}

	public void load( IXSystem opcatSystem, InputStream is, JProgressBar progressBar)
	{
		try{
			this.opcatSystem = opcatSystem;
			MsProject.getInstance();
			MsProject.setOPMProject(opcatSystem);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
			db = dbf.newDocumentBuilder();
			loadDataFromInputStream(is);
			NodeList titles = this.doc.getElementsByTagName("Name");
			if(titles.getLength()!=0){
				MsProject.title = titles.item(0).getTextContent();
				if(MsProject.title.endsWith(".xml")){
					MsProject.title = MsProject.title.replace(".xml", " ");
				}
			}
			buildTasksHierarchy();
			OPDGenerator gen = new OPDGenerator(OPDGenerator.BASIC_HIERARCHY);
			gen.generateOPDTree();
			loadOPMPredecessors();
			drawMilestonesCritical();
		}catch(Exception a){a.printStackTrace();}
	}
	
	public void buildTasksHierarchy()throws Exception{
		loadTasks();
		loadResources();
		loadAssignments();
	}
	
	public void loadAssignments()throws Exception{
		NodeList resourcesList = doc.getElementsByTagName("Assignment");
	 	for(int i=0; i < resourcesList.getLength();i++){
			new MsAssignment(resourcesList.item(i));
		}		
	}
	
	private void loadTasks()throws Exception{
		NodeList wbsList = doc.getElementsByTagName("WBS");
	 	//First - find all the WBS elements with the same prefix
		for(int i=0; i < wbsList.getLength();i++){
			Node wbsNode = wbsList.item(i);
			String wbsCode = ((Element)wbsNode).getTextContent();
			if(wbsCode.equals("0"))MsProject.firstTask = new MsTask(wbsNode.getParentNode());
			if(wbsCode.matches("[1-9][0-9]*")){
				//System.out.println("First child task: " + wbsCode);
				MsProject.firstTasks.add(new MsTask(wbsNode.getParentNode()));
			}
		}
	}
	
	private void loadResources()throws Exception{
		NodeList resourcesList = doc.getElementsByTagName("Resource");
	 	for(int i=0; i < resourcesList.getLength();i++){
			new MsResource(resourcesList.item(i));
		}
	}

	public void loadDataFromInputStream(InputStream is) throws Exception{
		try {
			doc = db.parse(is);
		}
		catch (Exception io) {
			io.printStackTrace();
		}
	}
	
	public void loadOPMPredecessors(){
		java.util.Enumeration<MsTask> tasks = MsProject.tasks.elements();
		MsTask currTask = null;
		IXProcessEntry procEntry;
		while(tasks.hasMoreElements()){
			currTask = tasks.nextElement();
			procEntry = (IXProcessEntry)currTask.getProcOPMInstance().getIXEntry(); 
			java.util.ArrayList<MsTask.Predecessor>preds = currTask.getPredessesors();
			for(int i=0; i<preds.size();i++){
				MsTask.Predecessor pred = preds.get(i);
				long predID = MsProject.tasks.get(pred.getUID()).getProcOPMInstance().getIXEntry().getId();
				procEntry.addPredecessor(predID, pred.getType());
			}
		}
	}
	
	public void drawMilestonesCritical(){
		java.util.Enumeration<MsTask> tasks = MsProject.tasks.elements();
		MsTask currTask = null;
		IXProcessInstance procInstance;
		while(tasks.hasMoreElements()){
			currTask = tasks.nextElement();
			procInstance = (IXProcessInstance)currTask.getProcOPMInstance(); 
			if(currTask.isMilestone())procInstance.setBorderColor(Color.MAGENTA);
		}
	}
	
}
