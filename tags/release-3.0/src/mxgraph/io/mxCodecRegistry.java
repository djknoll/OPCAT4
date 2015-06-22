/**
 * $Id: mxCodecRegistry.java,v 1.4 2007/01/25 21:13:55 gaudenz Exp $
 * Copyright (c) 2007, Gaudenz Alder
 */
package mxgraph.io;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/*
 * Class: mxCodecRegistry
 * 
 * Singleton class that acts as a global registry for codecs. See <mxCodec> for
 * an example of using this class.
 */
public class mxCodecRegistry
{

	/**
	 * Variable: codecs
	 * 
	 * Maps from constructor names to codecs.
	 */
	static Hashtable<String, mxObjectCodec> codecs = new Hashtable<String, mxObjectCodec>();

	/**
	 * Variable: instances
	 * 
	 * Maps from names to instances.
	 */
	static List<String> packages = new ArrayList<String>();

	// Registers the known codecs and packagenames for dynamic
	// codec generation
	static
	{
		addPackage("com.mxgraph");
		addPackage("com.mxgraph.util");
		addPackage("com.mxgraph.model");
		addPackage("java.lang");
		addPackage("java.util");
		register(new mxObjectCodec(new ArrayList()));
		register(new mxModelCodec());
		register(new mxCellCodec());
	}

	/**
	 * Function: register
	 * 
	 * Registers a new codec and associates the name of the template constructor
	 * in the codec with the codec object.
	 * 
	 * Parameters:
	 * 
	 * codec - <mxObjectCodec> to be registered.
	 */
	public static mxObjectCodec register(mxObjectCodec codec)
	{
		String name = getName(codec.getTemplate().getClass());
		codecs.put(name, codec);
		return codec;
	}

	/**
	 * Function: getCodec
	 * 
	 * Returns a codec that handles the given object, which can be an object
	 * instance or an XML node.
	 * 
	 * Parameters:
	 * 
	 * ctor - A JavaScript constructor function.
	 */
	public static mxObjectCodec getCodec(String name)
	{
		mxObjectCodec codec = codecs.get(name);

		// Registers a new default codec for the given constructor
		// if no codec has been previously defined.
		if (codec == null)
		{
			Object instance = getInstanceForName(name);
			if (instance != null)
			{
				codec = new mxObjectCodec(instance);
				register(codec);
			}
		}
		return codec;
	}

	/**
	 * Returns the name that identifies the codec associated
	 * with the given instance.
	 *
	 * The I/O system uses unqualified classnames, eg. for
	 * <code>com.mxgraph.model.mxCell</code> this returns
	 * <code>mxCell</code>.
	 * 
	 * @param instance The instance to return the name of.
	 * @return The string that identifies the codec.
	 */
	public static void addPackage(String packagename)
	{
		packages.add(packagename);
	}

	/**
	 *
	 */
	public static Object getInstanceForName(String name)
	{
		for (String s : packages)
		{
			try
			{
				return Class.forName(s + "." + name).newInstance();
			}
			catch (Exception e)
			{
				// ignore
			}
		}
		return null;
	}

	/**
	 * Function: getName
	 * 
	 * Returns the name that identifies the codec associated
	 * with the given instance.
	 *
	 * The I/O system uses unqualified classnames, eg. for
	 * <code>com.mxgraph.model.mxCell</code> this returns
	 * <code>mxCell</code>.
	 * 
	 * @param instance The instance to return the name of.
	 * @return The string that identifies the codec.
	 */
	public static String getName(Class type)
	{
		if (type.isArray() || Collection.class.isAssignableFrom(type)
				|| Map.class.isAssignableFrom(type))
		{
			return "Array";
		}
		else
		{
			return type.getSimpleName();
		}
	}

}
