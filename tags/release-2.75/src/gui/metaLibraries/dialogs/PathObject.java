/*
 * Created on 02/06/2004
 */
package gui.metaLibraries.dialogs;

/**
 * A representation of the path string - in a short form for the ontologies editing
 * window and in the full form for all other purposes.
 * @author Eran Toch
 * Created: 02/06/2004
 */
public class PathObject {
	private String fullPath = "";
	private String shortPath = "";
	private int threshold = 35;
	
	public PathObject(String _path)	{
		fullPath = _path;
		if (fullPath.length() > threshold)	{
			int firstSlash = Math.max(fullPath.indexOf("/"), fullPath.indexOf("\\"));
			int lastSlash = Math.max(fullPath.lastIndexOf("/"), fullPath.lastIndexOf("\\"));
			if ((lastSlash - firstSlash) < 10)	{
				shortPath = fullPath;
				return;
			}
			String finalPath = fullPath.substring(0, firstSlash+1);
			finalPath += " ... ";
			finalPath += fullPath.substring(lastSlash);
			shortPath = finalPath;
		}
		else	{
			shortPath = fullPath;
		}
	}
	
	public String getFullPath()	{
		return fullPath;
	}
	
	public String getShortPath()	{
		return shortPath;
	}
	
	public String toString()	{
		return getShortPath();
	}
}
