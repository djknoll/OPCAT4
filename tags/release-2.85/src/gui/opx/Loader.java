package gui.opx;

import gui.opdProject.OpdProject;
import gui.opx.ver2.LoaderVer2;
import gui.opx.ver3.LoaderVer3;
import gui.util.OpcatLogger;

import java.io.InputStream;

import javax.swing.JDesktopPane;
import javax.swing.JProgressBar;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class Loader {

	public OpdProject load(JDesktopPane parentDesktop, InputStream is,
			JProgressBar progressBar) throws LoadException {
		SAXBuilder builder = new SAXBuilder(
				"org.apache.xerces.parsers.SAXParser");
		Document testDoc = null;
		try {
			testDoc = builder.build(is);
		} catch (Exception ex) {
			OpcatLogger.logError(ex);
		}
		Element rootTag = null;
		try {
			rootTag = testDoc.getRootElement();
		} catch (NullPointerException nex) {
			throw new LoadException("Error loading file - OPX file is empty");
		} catch (Exception ex) {
			throw new LoadException("Error loading file");
		}
		String version = rootTag.getAttributeValue("version");
		LoaderI myLoader = this.chooseLoader(version);
		if (myLoader == null) {
			throw new LoadException("The version: \"" + version
					+ "\" is not supported");
		}
		return myLoader.load(parentDesktop, testDoc, progressBar);
	}

	private LoaderI chooseLoader(String version) {
		if (version.equals("") || version.equals("1") || version.equals("2")) {
			return new LoaderVer2();
		}
		if (version.equals("3")) {
			return new LoaderVer3();
		}
		return null;
	}
}