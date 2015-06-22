package gui.checkModule;

import exportedAPI.OpcatConstants;
import exportedAPI.OpdKey;
import gui.opdProject.OpdProject;
import gui.projectStructure.ConnectionEdgeEntry;
import gui.projectStructure.ConnectionEdgeInstance;
import gui.projectStructure.MainStructure;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ProcessEntry;
import gui.projectStructure.StateEntry;

public class CommandWrapper
{
	private ConnectionEdgeEntry sEntry, dEntry;
	private ConnectionEdgeInstance sInstance, dInstance;
	private OpdProject prj;
	private int type, sType, dType;
	private boolean checkGraphic;


	public CommandWrapper(long sourceEntityId,
						  OpdKey sourceInstanceId,
						  long destinationEntityId,
						  OpdKey destinationInstanceId,
						  int linkOrRelationType,
						  OpdProject prj)
	{
		this.prj = prj;
		type = linkOrRelationType;
		MainStructure ms = prj.getComponentsStructure();

		sEntry = (ConnectionEdgeEntry)ms.getEntry(sourceEntityId);
		sInstance = (ConnectionEdgeInstance)sEntry.getInstance(sourceInstanceId);
		sType = _entry2Type(sEntry);

		dEntry = (ConnectionEdgeEntry)ms.getEntry(destinationEntityId);
		dInstance = (ConnectionEdgeInstance)dEntry.getInstance(destinationInstanceId);
		dType = _entry2Type(dEntry);
		checkGraphic = true;
	}

	public CommandWrapper(long sourceEntityId,
						  long destinationEntityId,
						  int linkOrRelationType,
						  OpdProject prj)
	{
		this.prj = prj;
		type = linkOrRelationType;
		MainStructure ms = prj.getComponentsStructure();

		sEntry = (ConnectionEdgeEntry)ms.getEntry(sourceEntityId);
		sInstance = null;
		sType = _entry2Type(sEntry);

		dEntry = (ConnectionEdgeEntry)ms.getEntry(destinationEntityId);
		dInstance = null;
		dType = _entry2Type(dEntry);
		checkGraphic = false;
	}

	public ConnectionEdgeEntry getSourceEntry()
	{
		return sEntry;
	}

	public ConnectionEdgeEntry getDestinationEntry()
	{
		return dEntry;
	}

	public ConnectionEdgeInstance getSourceInstance()
	{
		return sInstance;
	}

	public ConnectionEdgeInstance getDestinationInstance()
	{
		return dInstance;
	}

	public int getType()
	{
		return type;
	}

	public int getSourceType()
	{
		return sType;
	}

	private int _getConstantsType(int type)
	{
		switch(type)
		{
			case 0: return OpcatConstants.OBJECT;
			case 1: return OpcatConstants.PROCESS;
			case 2: return OpcatConstants.STATE;
			default: return 0;
		}
	}

	public int getConstantsSourceType()
	{
		return _getConstantsType(sType);
	}

	public int getConstantsDestinationType()
	{
		return _getConstantsType(dType);
	}



	public int getDestinationType()
	{
		return dType;
	}


	private int _entry2Type(ConnectionEdgeEntry e)
	{
		if(e instanceof ObjectEntry)
			 return 0;
		if(e instanceof ProcessEntry)
			 return 1;
		if(e instanceof StateEntry)
			 return 2;
		return -1;
	}

	public static String type2String(int tp)
	{
		switch (tp)
		{
			case 0: return "an object";
			case 1: return "a process";
			case 2: return "a state";
			default: return "";
		}
	}

	public OpdProject getProject()
	{
		return prj;
	}

	public boolean isCheckGraphic()
	{
		return checkGraphic;
	}

}