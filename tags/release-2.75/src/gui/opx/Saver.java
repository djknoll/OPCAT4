package gui.opx;

import gui.opdProject.OpdProject;
import gui.opx.ver2.SaverVer2;
import gui.opx.ver3.SaverVer3;
import gui.util.OpcatLogger;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

/**
 * Performs saving processes. 
 * @author Eran Toch
 */
public class Saver {
	/**
	 * Holds the current version (highest) of the OPX schema.
	 */
	public static final String currentVersion = "3";

	private String usedVersion = "";
	
	private JFrame parentFrame = null;

	public void save(OpdProject project, JProgressBar progressBar, JFrame parent) {
		boolean saveOk = false;
		boolean firstSave = true;
		String fileName = project.getFileName();
		File newFile = new File(fileName);
		File backupFile = new File(fileName + ".bak");
		try {
			//Creating bakcup, if it's the file was already saved
			if (newFile.exists())	{
				firstSave = false;
				backupFile.createNewFile();
				copyFile(newFile, backupFile);
			}
			newFile.createNewFile();
			OutputStream fw;
			
			if (fileName.endsWith(".opz")) {
				fw = new GZIPOutputStream(new FileOutputStream(newFile), 4096);
			} else {
				fw = new BufferedOutputStream(new FileOutputStream(newFile),
						4096);
			}
			parentFrame = parent;
			String version = project.getVersionString();
			usedVersion = version;
			SaverI mySaver = chooseSaver(version);
			if (mySaver == null) {
				return;
				/*
				 * TODO: if we get here we should notify as this should not happen
				 */
			}
			mySaver.save(project, fw, progressBar);
			fw.close();
			saveOk = true;
		} 
		catch (SaveException sex)	{
			String msg = "Saving error occured on file "+ fileName +" on saver version "+ usedVersion +"\n Error text: "+ sex.getMessage();
			SaveException sexFull = new SaveException(msg);
			OpcatLogger.logError(sexFull);
			showErrorMessage();
		}
		catch (IOException ioe) {
			OpcatLogger.logError(ioe);
			showErrorMessage();
		}
		catch (Exception ex)	{
			OpcatLogger.logError(ex);
			showErrorMessage();
		}
		finally	{
			try	{
				if (!firstSave)	{
					if (!saveOk)	{
						//restore from backupfile
						newFile.createNewFile();
						copyFile(backupFile, newFile);
					}
					else	{
						//If save went okay - delete backup file
						backupFile.delete();
					}
				}
			}
			catch (Exception ex)	{
				OpcatLogger.logError(ex);
			}
		}
	}
	
	public void copyFile(File in, File out) throws Exception {
		FileInputStream fis = new FileInputStream(in);
		FileOutputStream fos = new FileOutputStream(out);
		byte[] buf = new byte[1024];
		int i = 0;
		while ((i = fis.read(buf)) != -1) {
			fos.write(buf, 0, i);
		}
		fis.close();
		fos.close();
	}

	private void showErrorMessage()	{
		String errorMessage = "An error occured while saving your file. The file was not saved. " +
				"\nCheck the file read only attribute, if changing this attribute does not help." + 
				"\n\"error.log\" file was created in Opcat2 directory. Please send this file to the Opcat development team.\n" +
				"The team's address can be found under the \"Contact Us\" section of www.objectprocess.org";
		JOptionPane.showMessageDialog(parentFrame, errorMessage, "Saving Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * The method decides of the version of the saver should be used. If the version
	 * of the system is the latest version then <code>SaverVer3</code> is used. 
	 * Otherwise, the method asks the user if to convert to the up-to-date version 
	 * or to save in the old verison. 
	 * @param version	The version of the current project.
	 * @return	The appropiriate <code>SaverI</code> implementation, or null
	 * if the user had decided to cancel the save operation.
	 */
	private SaverI chooseSaver(String version) {
		if (version.equals(currentVersion)) {
			return new SaverVer3();
		} else {
			Object[] options = { "Convert to up-to-date",
					"Save in old version", "Cancel" };
			int retValue = JOptionPane
					.showOptionDialog(
							parentFrame,
							"The system uses an old version of Opcat file format. \n"
									+ "Do you want to convert the system to the most up-to-date version of OPX?",
							"Save System", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, options,
							options[0]);
			if (retValue == JOptionPane.YES_OPTION) {
				return new SaverVer3();
			} else if (retValue == JOptionPane.NO_OPTION) {
				if (version.equals("") || version.equals("1")
						|| version.equals("2")) {
					return new SaverVer2();
				}
			} else if (retValue == JOptionPane.CANCEL_OPTION) {
				return null;
			}
		}
		return null;
	}

	/**
	 * Gets the current version number of the OPX schema.
	 * @return	The <code>String</code> representation of the OPX version.
	 */
	public String getCurrentVersion() {
		return currentVersion;
	}

}