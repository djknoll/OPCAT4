package gui.util;

import exportedAPI.OpcatExtensionToolBase;

import java.io.File;
import java.io.FileFilter;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

public class ExtensionToolsInstaller
{
	private static final String toolsDirPath = System.getProperty("file.separator")+"extensionTools";
	private ExtensionToolsInstaller()
	{
	}

	public static Enumeration getExtensionTools()
	{
		Object obj;
		String className;
		ExtensionToolInfo extInfo;

		Vector retVector = new Vector();
        Vector classesVector = new Vector();
		File[] filesList;


        Vector tDirs = new Vector();

        StringTokenizer st = new StringTokenizer(System.getProperty("java.class.path"), ";");

        for (;st.hasMoreTokens();)
        {
            String currPath = st.nextToken();
            File[] subDirs = _getToolsDirs(new File(currPath+toolsDirPath));

            if (subDirs != null)
            {
                for (int i=0;i<subDirs.length;i++)
                {
                    filesList = subDirs[i].listFiles(new ClassFilter());
                    for (int j=0; j<filesList.length; j++)
                    {
                        className = filesList[j].getAbsolutePath().substring(currPath.length()+1);
                        className = className.replace(System.getProperty("file.separator").charAt(0), '.');
                        className = className.substring(0, className.length() - ".class".length());
                        classesVector.add(className);
                    }
                }
            }
        }

		if (classesVector.size() == 0) return retVector.elements();

		for(int i = 0; i < classesVector.size(); i++)
		{
			// iterate over all files in directory
				try
				{
					obj = Class.forName((String)classesVector.get(i)).newInstance();
					if(obj instanceof OpcatExtensionToolBase)
					{
						retVector.add(new ExtensionToolInfo((OpcatExtensionToolBase)obj ));
					}
				}
				catch(InstantiationException ie)
				{
				}
				catch(ClassNotFoundException e)
				{
				}
                catch(IllegalAccessException e3)
                {
                }
		}
		return retVector.elements();
	}

	private static File[] _getToolsDirs(File rootPath)
	{
		return rootPath.listFiles(new DirFilter());
	}
}

class DirFilter implements FileFilter
{
	public boolean accept(File  pathname)
	{
		return pathname.isDirectory();
	}
}

class ClassFilter implements FileFilter
{
	public boolean accept(File pathname)
	{
		return pathname.getName().endsWith(".class");
	}
}