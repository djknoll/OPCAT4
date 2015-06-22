package extensionTools.Documents.Doc;

import gui.Opcat2;
import gui.controls.FileControl;
import gui.controls.GuiControl;
import gui.opdGraphics.opdBaseComponents.OpdThing;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.projectStructure.Entry;
import gui.projectStructure.Instance;
import gui.projectStructure.ProcessEntry;
import gui.util.OpcatLogger;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

class PrintCSV extends Thread {
	private final static String fileSeparator = System
			.getProperty("file.separator");

	File csv_file;

	public PrintCSV(File file) {
		this.csv_file = file;
	}

	public void run() {
		GuiControl gui = GuiControl.getInstance();
		gui.startWaitingMode("Saving CSV File...", true);
		try {
			this.doCSV(this.csv_file, new File("docsv.xml"));
		} catch (Exception e) {
			gui.stopWaitingMode();
			OpcatLogger.logError(e);
			FileControl file = FileControl.getInstance();
			JOptionPane.showMessageDialog(file.getCurrentProject()
					.getMainFrame(),
					"Save had failed becasue of the following error:\n"
							+ e.getMessage(), "Message",
					JOptionPane.ERROR_MESSAGE);
		} finally {
			gui.stopWaitingMode();
		}
	}

private void doCSV(File file, File inFile) {
		SAXBuilder builder = new SAXBuilder(true) ;
		try {
		builder.build(inFile) ;
		}     // indicates a well-formedness error
	    catch (JDOMException e) { 
	        OpcatLogger.logError(e) ;
	        JOptionPane.showMessageDialog(Opcat2.getFrame(), "Error loading XML file", "OPCAT2 Error", JOptionPane.ERROR_MESSAGE);
	        return ; 
	      }  
	      catch (IOException e) { 
		    OpcatLogger.logError(e) ;	    	  
	        JOptionPane.showMessageDialog(Opcat2.getFrame(), "Error loading XML file", "OPCAT2 Error", JOptionPane.ERROR_MESSAGE);	        
	        return ; 
	      }  
	    
		
		OpdProject pr = FileControl.getInstance().getCurrentProject();
		try {
			FileWriter fw = new FileWriter(file);
			Document doc = new Document(pr);
			Iterator entries = Collections.list(
					pr.getSystemStructure().getElements()).iterator();
	        StringTokenizer st = new StringTokenizer(file.getPath(), ".", false);
	        String dir_name  = st.nextToken();			
			while (entries.hasNext()) {
				Entry entry = (Entry) entries.next();
				if (entry instanceof ProcessEntry) {
					Iterator instances = Collections.list(entry.getInstances())
							.iterator();
					while (instances.hasNext()) {
						Instance ins = (Instance) instances.next() ;
						OpdThing parant =  ins.getParent() ;
						String parantName ; 
						if (parant == null) {
							parantName = "none" ; 
						} else {
							parantName = parant.getName() ; 
						}
						String oplName = dir_name + fileSeparator ; 
						fw.write("SystemProcess" + "," 
								+ ins.getEntry().getName().replace("\n", " ") + ","
								+ parantName.replace("\n", " ") + "," 
								+ ins.getEntry().getDescription().replace("\n", " ") + "," 
								+ ins.getOpd().getName().replace("\n", " ") + "," 
								+ oplName + ins.getOpd().getName().replace("\n", " ") +  "_" + "" + "" + ins.getOpd().getOpdId() + ".jpeg" + "," 								
								+ oplName + ins.getOpd().getName().replace("\n", " ") +  "_" + "" + "" + ins.getOpd().getOpdId() + "_opl.html" + "," 
								+ parantName.replace("\n", " ") + "," 
								+ "SONS here ....."  + ","
								+ "tested by"  + "," 
								+ "verifies (Constraint)"  + "," 
								+ "verifies (PerformanceIndex)"  
								+"\n");			
					}
				}
			}
			fw.flush();
			fw.close();

			// print the OPD's
			Iterator opds = Collections.list(pr.getSystemStructure().getOpds())
					.iterator();
			while (opds.hasNext()) {
				Opd opd = (Opd) opds.next();
				doc.PrintOpd(opd, null, file.getPath());
			}
		} catch (IOException e) {
			OpcatLogger.debug("Error Creating CSV FileWriter");
			OpcatLogger.logError(e);
			JOptionPane.showMessageDialog(Opcat2.getFrame(),
					"Error Saving CSV file", "OPCAT II", JOptionPane.OK_OPTION);
			return;
		}

	}}