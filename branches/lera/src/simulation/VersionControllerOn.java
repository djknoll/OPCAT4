package simulation;

import exportedAPI.opcatAPIx.*;
import gui.opx.LoadException;

import java.io.*;

public class VersionControllerOn extends VersionController{
	private static VersionControllerOn myCtrlOn = null;
	protected String projectFileName = null;
	protected static final String projectTmpFileName = "TmpXXX111.opz";
	protected IXFileControl fileControl = null;
	
	protected VersionControllerOn(IXSystem opmSystem_, IXFileControl fileControl){
		super(opmSystem_);
		this.fileControl = fileControl;
		projectFileName = fileControl.getSystemFileName();
	}
	public static VersionControllerOn getInstance(IXSystem opmSystem_, IXFileControl fileControl){
		if(myCtrlOn != null){
				myCtrlOn.projectFileName = fileControl.getSystemFileName();
			return myCtrlOn;
			}
		return (myCtrlOn = new VersionControllerOn(opmSystem_, fileControl));
	}
	
	public IXSystem getSystem(){
		if(fileControl.isProjectOpened()){
			fileControl.closeSystem(false);
		}
		try{
			simulation.util.FileUtility.copy(projectFileName, projectTmpFileName);
			opmSystem = fileControl.openSystem(projectTmpFileName, new javax.swing.JProgressBar());	
		}catch(IOException e){
			e.printStackTrace();
		}catch(LoadException e){
			e.printStackTrace();
		}
		return opmSystem;
	}


	/*
	 *Should be called only at the exit from animation
	 * -Releases the actual project and removes the 
	 *  temporary project file, that was modified through 
	 *  animation/execution
	 * */
	public void releaseSystem(){
		if(fileControl.getSystemFileName().equals(projectTmpFileName)&&
				fileControl.isProjectOpened()){
			fileControl.closeSystem(false);
			//simulation.util.FileUtility.delete(projectTmpFileName);
			File f = new File(projectTmpFileName);
			if(f.exists())
				f.delete();
			try{
				fileControl.openSystem(projectFileName, new javax.swing.JProgressBar());
			}catch(Exception e){e.printStackTrace();}
		}
	}
}
