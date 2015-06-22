/**
 * $Id: mxCellPath.java,v 1.1 2007/01/23 11:34:10 gaudenz Exp $
 * Copyright (c) 2007, Gaudenz Alder
 */
package mxgraph.model;

public class mxCellPath
{

	/**
	 *
	 */
	public static String PATH_SEPARATOR = ".";

	/**
	 *
	 */
	public static String create(mxICell cell)
	{
		String result = "";
		mxICell parent = cell.getParent();
		while (parent != null)
		{
			int index = parent.getIndex(cell);
			result = index + mxCellPath.PATH_SEPARATOR + result;
			cell = parent;
			parent = cell.getParent();
		}
		return (result.length() > 1) ? result.substring(0, result.length() - 1)
				: "";
	}

	/**
	 *
	 */
	public static mxICell resolve(mxICell root, String path)
	{
		mxICell parent = root;
		String[] tokens = path.split(PATH_SEPARATOR);
		for (int i = 0; i < tokens.length; i++)
		{
			parent = parent.getChildAt(Integer.parseInt(tokens[i]));
		}
		return parent;
	}

}
