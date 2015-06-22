package gui.server;
import java.util.Date;
import java.util.Vector;

/**
 * This Class contains parameters for Load Project Dialog.
 * Parameters are project id, project name, author's name, date of creation.
 */

public class LoadParameters
{
	private Vector loadParam;
	private long returnedPId;


/**
* Creates an empty instance of LoadParameters.
*
*/
	public LoadParameters()
	{
		loadParam = new Vector();
		returnedPId = -1;
	}

/**
* Returns number of entries in LoadParameters.
*
*/

	public int length()
	{
		return loadParam.size();
	}

/**
* Returns projectId of selected entry in LoadParameters.
* -1 is returned if no project was selected.
*
*/
	public long getReturnedPId()
	{
		return returnedPId;
	}


/**
* Sets selected entry.
*
*/
	public void setReturnedPId(int entryNum)
	{

		if ( loadParam.size() >= entryNum )
		{
			ParametersEntry pEntry;
			pEntry = (ParametersEntry)loadParam.get(entryNum);
			returnedPId = pEntry.getProjectId();
		}
		else returnedPId = -1;
	}

/**
* Adds entry to LoadParameters.
*
* @param pId project id.
* @param pName project name.
* @param pCreator project creator.
* @param cDate project creation date.
*/

	public void addParametersEntry(long pId, String pName, String pCreator, Date cDate)
	{
		loadParam.addElement( new ParametersEntry(pId, pName, pCreator, cDate));
	}

/**
* Returns project name from specified entry.
* Returns null if there is no such entry in LoadParam
*/
	public String getProjectName(int entryNum)
	{
		if ( loadParam.size() > entryNum )
		{
			ParametersEntry pEntry;
			pEntry = (ParametersEntry)loadParam.get(entryNum);
			return pEntry.getProjectName();
		}
		else
		{
			System.out.println("Problem! Empty entry "+entryNum+"! Can't getProgectName!");
			return " ";
		}
	}

/**
* Returns project creator name from specified entry.
* Returns null if there is no such entry in LoadParam
*/

	public String getProjectCreator(int entryNum)
	{
		if ( loadParam.size() > entryNum )
		{
			ParametersEntry pEntry;
			pEntry = (ParametersEntry)loadParam.get(entryNum);
			return pEntry.getProjectCreator();
		}
		else
		{
			System.out.println("Problem! Empty entry "+entryNum+"! Can't getProgectName!");
			return " ";
		}

	}

/**
* Returns creation date from specified entry.
* Returns null if there is no such entry in LoadParam
*/

	public Date getCreationDate(int entryNum)
	{
		if ( loadParam.size() > entryNum )
		{
			ParametersEntry pEntry;
			pEntry = (ParametersEntry)loadParam.get(entryNum);
			return pEntry.getCreationDate();
		}
		else
		{
			System.out.println("Problem! Empty entry "+entryNum+"! Can't getProgectName!");
			return new Date(0);
		}
	}


/**
 * This Class is inner class in LoadParameters class, and
 * is an entry in LoadParameters's data structure. It contains
 * project id, project name, author's name, date of creation.
 */

	public class ParametersEntry
	{
		private long projectId;
		private String projectName;
		private String projectCreator;
		private Date creationDate;

/**
* Creates entry with specified parameters.
*
* @param pId project id.
* @param pName project name.
* @param pCreator project creator.
* @param cDate project creation date.
*/

		public ParametersEntry(long pId, String pName, String pCreator, Date cDate)
		{
			projectId = pId;
			projectName = pName;
			projectCreator = pCreator;
			creationDate = cDate;
		}

/**
* Returns creation date.
*/
		public Date getCreationDate()
		{
			return creationDate;
		}


/**
* Returns project creator.
*/
		public String getProjectCreator()
		{
			return projectCreator;
		}


/**
* Returns project id.
*/
		public long getProjectId()
		{
			return projectId;
		}

/**
* Returns project name.
*/
		public String getProjectName()
		{
			return projectName;
		}

	}

}