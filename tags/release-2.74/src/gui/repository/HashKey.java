package gui.repository;

import gui.opdProject.Opd;
import gui.opdProject.OpdProject;

class HashKey
{
	String key;
	HashKey(Object o)
	{
		//this.key = new String(Long.toString(key));
		if(o instanceof OpdProject)
		{
			key = "Project" +((OpdProject)o).getProjectId();
			return;
		}
		if(o instanceof Opd)
		{
			key = "Opd" +((Opd)o).getOpdId();
			return;
		}
		if(o instanceof String)
		{
			key = new String(o.toString());
			return;
		}
	}

	public int hashCode()
	{
		return key.hashCode();
	}

	public boolean equals(Object o)
	{
		if(!(o instanceof HashKey))
		{
			return false;
		}

		return key.equals(((HashKey)o).toString());
	}

	public String toString()
	{
		return key;
	}
}