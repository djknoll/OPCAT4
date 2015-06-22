package extensionTools.MsProject;
import gui.opdProject.StateMachine;
//import gui.opx.ver2.LoaderVer2;

import javax.swing.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.JFileChooser;

public class MsProjectImportDialog {
	
	
	JFileChooser fileChooser = new JFileChooser();
	File myFile = null;
	{
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		javax.swing.filechooser.FileFilter filter = new XMLFileFilter(); 
		fileChooser.setFileFilter(filter);
	}
	public MsProjectImportDialog(){}
	
	public void init(){
		fileChooser.setVisible(true);
		int retval = fileChooser.showOpenDialog(null);
		if (retval == JFileChooser.APPROVE_OPTION) {
			myFile = fileChooser.getSelectedFile();
			//if(myFile.exists()){System.out.println("Opened File!!");}
		}
	}
	
	public File getImportFile(){
		return myFile;
	}
	
	class XMLFileFilter extends javax.swing.filechooser.FileFilter {
		public boolean accept(File f) {
			return f.isDirectory() || f.getName().toLowerCase().endsWith(".xml");
		}

		public String getDescription() {
			return ".xml files";
		}
	};

}
