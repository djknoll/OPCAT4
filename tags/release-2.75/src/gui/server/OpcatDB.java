package gui.server;
import exportedAPI.OpcatConstants;
import exportedAPI.OpdKey;
import exportedAPI.RelativeConnectionPoint;
import gui.Opcat2;
import gui.opdGraphics.DraggedPoint;
import gui.opdGraphics.draggers.AroundDragger;
import gui.opdGraphics.draggers.OpdRelationDragger;
import gui.opdGraphics.draggers.TextDragger;
import gui.opdGraphics.lines.StyledLine;
import gui.opdGraphics.opdBaseComponents.OpdBaseComponent;
import gui.opdGraphics.opdBaseComponents.OpdConnectionEdge;
import gui.opdGraphics.opdBaseComponents.OpdFundamentalRelation;
import gui.opdGraphics.opdBaseComponents.OpdObject;
import gui.opdGraphics.opdBaseComponents.OpdState;
import gui.opdGraphics.opdBaseComponents.OpdThing;
import gui.opdProject.GenericTable;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.opdProject.StateMachine;
import gui.opmEntities.Constants;
import gui.opmEntities.OpmAgent;
import gui.opmEntities.OpmAggregation;
import gui.opmEntities.OpmBiDirectionalRelation;
import gui.opmEntities.OpmConditionLink;
import gui.opmEntities.OpmConnectionEdge;
import gui.opmEntities.OpmConsumptionEventLink;
import gui.opmEntities.OpmConsumptionLink;
import gui.opmEntities.OpmEffectLink;
import gui.opmEntities.OpmEntity;
import gui.opmEntities.OpmExceptionLink;
import gui.opmEntities.OpmExhibition;
import gui.opmEntities.OpmFundamentalRelation;
import gui.opmEntities.OpmGeneralRelation;
import gui.opmEntities.OpmInstantination;
import gui.opmEntities.OpmInstrument;
import gui.opmEntities.OpmInstrumentEventLink;
import gui.opmEntities.OpmInvocationLink;
import gui.opmEntities.OpmObject;
import gui.opmEntities.OpmProceduralLink;
import gui.opmEntities.OpmProcess;
import gui.opmEntities.OpmResultLink;
import gui.opmEntities.OpmSpecialization;
import gui.opmEntities.OpmState;
import gui.opmEntities.OpmStructuralRelation;
import gui.opmEntities.OpmThing;
import gui.opmEntities.OpmUniDirectionalRelation;
import gui.projectStructure.ConnectionEdgeEntry;
import gui.projectStructure.ConnectionEdgeInstance;
import gui.projectStructure.Entry;
import gui.projectStructure.FundamentalRelationEntry;
import gui.projectStructure.FundamentalRelationInstance;
import gui.projectStructure.GeneralRelationEntry;
import gui.projectStructure.GeneralRelationInstance;
import gui.projectStructure.GraphicalRelationRepresentation;
import gui.projectStructure.LinkEntry;
import gui.projectStructure.LinkInstance;
import gui.projectStructure.MainStructure;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ObjectInstance;
import gui.projectStructure.ProcessEntry;
import gui.projectStructure.StateEntry;
import gui.projectStructure.StateInstance;
import gui.projectStructure.ThingEntry;
import gui.projectStructure.ThingInstance;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParsePosition;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JDesktopPane;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;


/**
 * This Class is responsible for the connection to the database,
 * loading and saving projects.
 */

public class OpcatDB {

	//private final String dbURL = "jdbc:odbc:Opcat";
//    static private final String defaultSourceFile = "Examples\\ATMsystem.mdb";
	static private final String defaultSourceFile = "opcatii.mdb";
	static private final String dbURL = "jdbc:odbc:;Driver={Microsoft Access Driver (*.mdb)}; DBQ=";
	static private final String user  = "user";
	static private final String passwd = "password";
	private Connection connection;
	private MainStructure projectStructure;
	private OpdProject myProject;

	final static private String PROCESS = "PROCESS";
	final static private String OBJECT = "OBJECT";
	final static private String RELATION = "RELATION";
	final static private String STATE = "STATE";
	final static private String LINK = "LINK";
	private String currentDB = defaultSourceFile;

/**
* Creates an instance of OpcatDB.
*
*/

	public OpcatDB()
	{
	}

/**
* Returns next free project entry in database.
* In the database there are many different projects and each
* has unique id. For new project we will have to allocate new id
* and this method looks for this number
*
* @return next free project entry in database.
*/

	public long getFreeProjectEntry()
	{

		String sqlQuery;
		ResultSet rst;
		Statement statement;
		long returnedId = 0;

		try
		{
			sqlQuery =  "SELECT MAX(PROJECT_ID) FROM PROJECT ";

			statement = connection.createStatement();
			rst = statement.executeQuery(sqlQuery);

			while(rst.next())
			{
				returnedId = (long)rst.getInt(1);
			}

			rst.close();
			statement.close();
			if (returnedId == 0)
			{
				return 1;
			}
			else
			{
				return returnedId+1;
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in  "+e);
			return 0;
		}

	}

/**
* Loads project from database. After calling this method user
* will see menu with names of all projects existing in database
* and selected project will be loaded into OPCAT system.
*
* @param parentDesktop JDesktopPane to which we put diagrams of this project.
* @param opcatTree tree to which we put repository manager of this project.
* @return object of OpdProject type that contains loaded project.
*/

	public OpdProject load(JDesktopPane parentDesktop)
	{
		long projectId;

		 projectId = _loadProjectsForLoad();
		 if (projectId == -1)
		 {
			return null;
		 }

		StateMachine.setWaiting(true);
		Opcat2.getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		myProject = new OpdProject(parentDesktop, projectId);

		projectStructure = myProject.getComponentsStructure();

		_loadConfigurationTable(myProject.getConfiguration());
		_loadConfigurationTable(myProject.getGeneralInfo());
		myProject.showRootOpd();

		_loadProjectTable();

		_loadLogicalStructure();

		_loadOpdTable(1, (OpdThing)null);

		_loadStateInOpdTable();

		_loadLinkInOpdTable();

		_loadRelationInOpdTable();

		_loadGeneralRelationInOpdTable();

		myProject.setCurrentOpd(projectStructure.getOpd(1));
		StateMachine.setWaiting(false);
		Opcat2.getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		return myProject;
	}

	public void deleteProject(OpdProject currentProject)
	{
		long projectId;

		 projectId = _loadProjectsForDelete();
		 if (projectId == -1)
		 {
			return;
		 }

		StateMachine.setWaiting(true);
		Opcat2.getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		//project deletion
		if(currentProject != null && currentProject.getProjectId() == projectId)
		{
			JOptionPane.showMessageDialog(Opcat2.getFrame(),
										  "You are tring to delete an opened project.\nClose it and try again.",
										  "Opcat2 - Error project deletion", JOptionPane.ERROR_MESSAGE);
			StateMachine.setWaiting(false);
			Opcat2.getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			return;
		}

		try
		{
			_deleteProject(projectId);
		}
		catch(Exception e1)
		{
			JOptionPane.showMessageDialog(Opcat2.getFrame(),
										  "Cannot delete project.",
										  "Opcat2 - Error project deletion", JOptionPane.ERROR_MESSAGE);
			StateMachine.setWaiting(false);
			Opcat2.getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}

		StateMachine.setWaiting(false);
		Opcat2.getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		return;
	}

	private long _loadProjectsForDelete()
	{
		String sqlQuery;
		ResultSet rst;
		Statement statement;
		int numOfProjects = 0;

		try
		{
			ParsePosition pos = new ParsePosition(0);
			java.util.Date creationDate;
			String cDate;

			LoadParameters parameters = new LoadParameters();


			sqlQuery =
			  "SELECT PROJECT_ID, PROJECT_NAME, CREATOR, CREATE_DATE FROM PROJECT ";


			statement = connection.createStatement();
			rst = statement.executeQuery(sqlQuery);

			while(rst.next())
			{

				pos.setIndex(0);

				parameters.addParametersEntry(rst.getInt("PROJECT_ID"), getString(rst, "PROJECT_NAME"),
				getString(rst, "CREATOR"), rst.getTimestamp("CREATE_DATE"));
				numOfProjects++;
			}

			rst.close();
			statement.close();

			if (numOfProjects == 0)
			{
				JOptionPane.showMessageDialog(null, " No projects to load in database", "No projects" ,JOptionPane.INFORMATION_MESSAGE);
				return -1;
			}
			else
			{
				new DeleteDialog(parameters);
				return parameters.getReturnedPId();
			}

		}
		catch(Exception e)
		{
			System.out.println("Exception in _loadProjectForLoad "+e);
			return -1;
		}


	}



/**
* Saves project to database. This method overwrites previuos version of this
* project in database.
*
*/

	public void save(OpdProject project)
	{
		myProject = project;
		projectStructure = myProject.getComponentsStructure();
		Opd tempOpd;

		Entry currentEntry;

		_deleteProject(myProject.getProjectId());

		_saveProjectTable(myProject);
		_saveConfigurationTable(myProject.getConfiguration());
		_saveConfigurationTable(myProject.getGeneralInfo());

		for(Enumeration e = projectStructure.getOpds(); e.hasMoreElements();)
		{
			tempOpd = (Opd)(e.nextElement());
			_saveOpdTable(tempOpd);
		}



		for(Enumeration e = projectStructure.getElements(); e.hasMoreElements();)
		{
			currentEntry = (Entry)(e.nextElement());


			if (currentEntry instanceof ThingEntry)
			{
				if ( ((ThingEntry)currentEntry).getLogicalEntity() instanceof OpmObject)
				{
					_addOpdObject((ThingEntry)currentEntry);
				}
				else
				{
					_addOpdProcess((ThingEntry)currentEntry);
				}
				continue;
			}

			if (currentEntry instanceof StateEntry)
			{
				_addOpdState((StateEntry)currentEntry);
				continue;
			}

			if (currentEntry instanceof LinkEntry)
			{
				_addOpdLink((LinkEntry)currentEntry);
				continue;
			}

			if (currentEntry instanceof GeneralRelationEntry)
			{
				_addOpdGeneralRelation((GeneralRelationEntry)currentEntry);
				continue;
			}

			if (currentEntry instanceof FundamentalRelationEntry)
			{
				_addOpdFundamentalRelation((FundamentalRelationEntry)currentEntry);
				continue;
			}


	  }

	GraphicalRelationRepresentation gEntry;
	FundamentalRelationInstance tempInstance;

		for(Enumeration e = projectStructure.getGraphicalRepresentations(); e.hasMoreElements();)
		{

			gEntry = (GraphicalRelationRepresentation)(e.nextElement());
						_saveFundamentalRelationRepresentation(gEntry);

			//insert into fundamental_relation_in_opd table
			for (Enumeration e2 = gEntry.getInstances(); e2.hasMoreElements();)
			{
				tempInstance = (FundamentalRelationInstance)(e2.nextElement());
				_saveFundamentalRelationInOpdTable(tempInstance, gEntry);
			}

		}

	}



/**
* Connects to database. If fails to establish connection with database
* returns false, otherwise true.
*
*/
	public boolean connectToDB()
	{
		String statusDB;
		try {
		   try {
			  Class.forName("com.ms.jdbc.odbc.JdbcOdbcDriver");
		   }
		  catch (Exception e) {
			   Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		  }

		  connection = DriverManager.getConnection(dbURL+currentDB, user, passwd);
		  return true;
	   }
	  catch (Exception e) {
		//System.out.println(e.getMessage()+" Error in connecting to DB");
		return false;
	}
 }





	/*****************************************************************/
	//add OPDEProcess to DB.
	//Insert records into follow tables:
	//ENTITY,THING_IN_OPD,PROCESS.
	/*****************************************************************/
	private void _addOpdProcess(ThingEntry myEntry)
	{
		OpmProcess logicalProcess = (OpmProcess)myEntry.getLogicalEntity();
		ThingInstance tempInstance;

		//insert into entity table
		_saveEntityTable(myEntry);

		//insert into process table
		_saveProcessTable(logicalProcess);

		//insert into thing_in_opd table
		for (Enumeration e = myEntry.getInstances(); e.hasMoreElements();)
		{
			tempInstance = (ThingInstance)(e.nextElement());
			_saveThingInOpdTable(tempInstance);
		}

	}






/***************************************************************/
//insert OPDObject into DB. If object already exist in DB
//exception will be raised.Insert into follow tables:
//ENTITY,THING_IN_OPD,OBJECT.
/***************************************************************/
private void _addOpdObject(ThingEntry myEntry)
{

		OpmObject logicalObject = (OpmObject)myEntry.getLogicalEntity();
		ThingInstance tempInstance;

		//insert into entity table
		_saveEntityTable(myEntry);

		//insert into object table
		_saveObjectTable(logicalObject);

		//insert into thing_in_opd table
		for (Enumeration e = myEntry.getInstances(); e.hasMoreElements();)
		{
			tempInstance = (ThingInstance)(e.nextElement());
			_saveThingInOpdTable(tempInstance);
		}

}



/***************************************************************/
//insert LinkEntry into DB.
//Insert into following tables:
/***************************************************************/
private void _addOpdLink(LinkEntry myEntry)
{

		OpmProceduralLink logicalLink = (OpmProceduralLink)myEntry.getLogicalEntity();
		LinkInstance tempInstance;

		//insert into entity table
		_saveEntityTable(myEntry);

		//insert into object table
		_saveLinkTable(logicalLink);

		//insert into link_in_opd table
		for (Enumeration e = myEntry.getInstances(); e.hasMoreElements();)
		{
			tempInstance = (LinkInstance)(e.nextElement());
			_saveLinkInOpdTable(tempInstance, myEntry);
		}

}

/***************************************************************/
//insert GeneralRelationEntry into DB.
//Insert into following tables:
/***************************************************************/
private void _addOpdGeneralRelation(GeneralRelationEntry myEntry)
{

		OpmGeneralRelation logicalRelation = (OpmGeneralRelation)myEntry.getLogicalEntity();
		GeneralRelationInstance tempInstance;

		//insert into entity table
		_saveEntityTable(myEntry);

		//insert into relation table
		_saveRelationTable(logicalRelation);

		//insert into general_relation_in_opd table
		for (Enumeration e = myEntry.getInstances(); e.hasMoreElements();)
		{
			tempInstance = (GeneralRelationInstance)(e.nextElement());
			_saveGeneralRelationInOpdTable(tempInstance, myEntry);
		}

}


/***************************************************************/
//insert StateEntry into DB.
/***************************************************************/
private void _addOpdState(StateEntry myEntry)
{

		OpmState logicalState = (OpmState)myEntry.getLogicalEntity();
		StateInstance tempInstance;

		//insert into entity table
		_saveEntityTable(myEntry);

		//insert into object table
		_saveStateTable(logicalState, myEntry);

		//insert into state_in_opd table
		for (Enumeration e = myEntry.getInstances(); e.hasMoreElements();)
		{
			tempInstance = (StateInstance)(e.nextElement());
			_saveStateInOpdTable(tempInstance, myEntry);
		}

}



/***************************************************************/
//insert FundamentalRelationEntry into DB. If object already exist in DB
//exception will be raised.Insert into follow tables:
//ENTITY,THING_IN_OPD,OBJECT.
/***************************************************************/
private void _addOpdFundamentalRelation(FundamentalRelationEntry myEntry)
{

		OpmFundamentalRelation logicalRelation = (OpmFundamentalRelation)myEntry.getLogicalEntity();
		FundamentalRelationInstance tempInstance;

		//insert into entity table
		_saveEntityTable(myEntry);

		//insert into object table
		_saveRelationTable(logicalRelation);

}


/*************************************************************/
// close connection to DB
/************************************************************/
public boolean closeConnection() {
	try {

		connection.close();
		return true;
	}
	catch (Exception e) {
		return false;
	}
}




	private void _deleteProject(long projectId)
	{
		Statement statement;
		PreparedStatement prepStmt;
		String sqlQuery;


		try
		{
			statement = connection.createStatement();

			sqlQuery =
				"DELETE * FROM PROJECT " +
				"WHERE ( PROJECT_ID = " + projectId + ");";
			statement.executeUpdate(sqlQuery);

			sqlQuery =
				"DELETE * FROM OPD " +
				"WHERE ( PROJECT_ID = " + projectId + ");";
			statement.executeUpdate(sqlQuery);

			sqlQuery =
				"DELETE * FROM ENTITY " +
				"WHERE ( PROJECT_ID = " + projectId + ");";
			statement.executeUpdate(sqlQuery);

			sqlQuery =
				"DELETE * FROM THING_IN_OPD " +
				"WHERE ( PROJECT_ID = " + projectId + ");";
			statement.executeUpdate(sqlQuery);

			sqlQuery =
				"DELETE * FROM OBJECT " +
				"WHERE ( PROJECT_ID = " + projectId + ");";
			statement.executeUpdate(sqlQuery);

			sqlQuery =
				"DELETE * FROM PROCESS " +
				"WHERE ( PROJECT_ID = " + projectId + ");";
			statement.executeUpdate(sqlQuery);

			sqlQuery =
				"DELETE * FROM STATE " +
				"WHERE ( PROJECT_ID = " + projectId + ");";
			statement.executeUpdate(sqlQuery);

			sqlQuery =
				"DELETE * FROM RELATION " +
				"WHERE ( PROJECT_ID = " + projectId + ");";
			statement.executeUpdate(sqlQuery);

			sqlQuery =
				"DELETE * FROM LINK " +
				"WHERE ( PROJECT_ID = " + projectId + ");";
			statement.executeUpdate(sqlQuery);

			sqlQuery =
				"DELETE * FROM LINK_CONNECTION " +
				"WHERE ( PROJECT_ID = " + projectId + ");";
			statement.executeUpdate(sqlQuery);

			sqlQuery =
				"DELETE * FROM GRAPHIC_OBJECT " +
				"WHERE ( PROJECT_ID = " + projectId + ");";
			statement.executeUpdate(sqlQuery);

			sqlQuery =
				"DELETE * FROM LINK_IN_OPD " +
				"WHERE ( PROJECT_ID = " + projectId + ");";
			statement.executeUpdate(sqlQuery);

			sqlQuery =
				"DELETE * FROM FUNDAMENTAL_RELATION_IN_OPD " +
				"WHERE ( PROJECT_ID = " + projectId + ");";
			statement.executeUpdate(sqlQuery);

						sqlQuery =
				"DELETE * FROM FUNDAMENTAL_RELATION_REPRESENTATION " +
				"WHERE ( PROJECT_ID = " + projectId + ");";
			statement.executeUpdate(sqlQuery);


			sqlQuery =
				"DELETE * FROM STATE_IN_OPD " +
				"WHERE ( PROJECT_ID = " + projectId + ");";
			statement.executeUpdate(sqlQuery);

			sqlQuery =
				"DELETE * FROM GENERAL_RELATION_IN_OPD " +
				"WHERE ( PROJECT_ID = " + projectId + ");";
			statement.executeUpdate(sqlQuery);

			sqlQuery =
				"DELETE * FROM CONFIGURATION " +
				"WHERE ( PROJECT_ID = " + projectId + ");";
			statement.executeUpdate(sqlQuery);

			sqlQuery =
				"DELETE * FROM LINE " +
				"WHERE ( PROJECT_ID = " + projectId + ");";
			statement.executeUpdate(sqlQuery);


			statement.close();

		}
		catch(Exception e)
		{
			System.out.println("Exception in deleteProject  "+e);
		}


	}


	private void _saveProjectTable(OpdProject myProject)
	{
		PreparedStatement prepStmt;
		String sqlQuery;

		try
		{
			sqlQuery =
				"INSERT INTO PROJECT " +
				"(PROJECT_ID, PROJECT_NAME, CREATOR, CREATE_DATE, ROOT_OPD_ID, MAX_ENTITY_ENTRY, MAX_OPD_ENTRY) " +
					"VALUES ( ?, ?, ?, ? , ?, ? , ?);";

			prepStmt = connection.prepareStatement(sqlQuery);

			prepStmt.setInt(1, (int)myProject.getProjectId());
			setString(prepStmt, 2, myProject.getName());
			setString(prepStmt, 3, myProject.getCreator());
			prepStmt.setTimestamp(4, new Timestamp(myProject.getCreationDate().getTime()));
			prepStmt.setInt(5, 1);
			prepStmt.setInt(6, (int)myProject.getEntityMaxEntry());
			prepStmt.setInt(7, (int)myProject.getOpdMaxEntry());
			prepStmt.executeUpdate();
			prepStmt.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception in _saveProjectTable "+e);
		}

	}

	private long _loadProjectsForLoad()
	{
		String sqlQuery;
		ResultSet rst;
		Statement statement;
		int numOfProjects = 0;

		try
		{
			ParsePosition pos = new ParsePosition(0);
			java.util.Date creationDate;
			String cDate;

			LoadParameters parameters = new LoadParameters();


			sqlQuery =
			  "SELECT PROJECT_ID, PROJECT_NAME, CREATOR, CREATE_DATE FROM PROJECT ";


			statement = connection.createStatement();
			rst = statement.executeQuery(sqlQuery);

			while(rst.next())
			{

				pos.setIndex(0);

				parameters.addParametersEntry(rst.getInt("PROJECT_ID"), getString(rst, "PROJECT_NAME"),
				getString(rst, "CREATOR"), rst.getTimestamp("CREATE_DATE"));
				numOfProjects++;
			}

			rst.close();
			statement.close();

			if (numOfProjects == 0)
			{
				JOptionPane.showMessageDialog(Opcat2.getFrame(), " No projects to load in database", "No projects" ,JOptionPane.INFORMATION_MESSAGE);
				return -1;
			}
			else
			{
				new LoadDialog(parameters);
				return parameters.getReturnedPId();
			}

		}
		catch(Exception e)
		{
			System.out.println("Exception in _loadProjectForLoad "+e);
			return -1;
		}


	}

	private void _loadProjectTable()
	{

		String sqlQuery;
		ResultSet projectRst;
		Statement statement;

		try
		{

			sqlQuery =
			  "SELECT PROJECT_NAME, CREATOR, CREATE_DATE, MAX_ENTITY_ENTRY, MAX_OPD_ENTRY "+
			  "FROM PROJECT " +
			  "WHERE PROJECT_ID = " + myProject.getProjectId() + ";";

			statement = connection.createStatement();
			projectRst = statement.executeQuery(sqlQuery);

			while(projectRst.next())
			{
			  myProject.setName(getString(projectRst, "PROJECT_NAME"));
			  myProject.setCreator(getString(projectRst, "CREATOR"));
			  myProject.setCreationDate(projectRst.getTimestamp("CREATE_DATE"));
			  myProject.setEntityMaxEntry((long)projectRst.getInt("MAX_ENTITY_ENTRY"));
			  myProject.setOpdMaxEntry((long)projectRst.getInt("MAX_OPD_ENTRY"));
			}

			projectRst.close();
			statement.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception in _loadProjectTable "+e);
		}

	}

	private void _saveOpdTable(Opd myOpd)
	{

		PreparedStatement prepStmt;
		String sqlQuery;

		try
		{
			sqlQuery =
				"INSERT INTO OPD " +
				"(OPD_ID, OPD_NAME, PROJECT_ID, MAX_ENTITY_IN_OPD_ENTRY, PARENT_OPD_ID, MAIN_ENTITY_ID, MAIN_ENTITY_IN_OPD_ID) " +
					"VALUES ( ?, ?, ?, ?, ?, ?, ?);";

			prepStmt = connection.prepareStatement(sqlQuery);

			prepStmt.setInt(1, (int)myOpd.getOpdId());
			setString(prepStmt, 2, myOpd.getName());
			prepStmt.setInt(3, (int)myProject.getProjectId());
			prepStmt.setInt(4, (int)myOpd.getEntityInOpdMaxEntry());
			if(myOpd.getParentOpd() == null)
			{
				prepStmt.setNull(5, Types.INTEGER);
			}
			else
			{
				prepStmt.setInt(5, (int)myOpd.getParentOpd().getOpdId());
			}
			if(myOpd.getMainEntry() == null)
			{
				prepStmt.setNull(6, Types.INTEGER);
			}
			else
			{
				prepStmt.setInt(6, (int)myOpd.getMainEntry().getId());
			}
			if(myOpd.getMainInstance() == null)
			{
				prepStmt.setNull(7, Types.INTEGER);
			}
			else
			{
				prepStmt.setInt(7, (int)myOpd.getMainInstance().getKey().getEntityInOpdId());
			}

			prepStmt.executeUpdate();
			prepStmt.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception in _saveOpdTable "+e);
		}
	}

	private Opd _loadOpdTable(long opdNum, OpdThing parentThing)
	{

		String sqlQuery;
		ResultSet tableRst;
		Statement statement;

		Opd tempOpd = null;
		Opd parentOpd;

		long mainEntitytInOpdId = -1;

		try
		{

			sqlQuery =
			  "SELECT OPD_NAME, MAX_ENTITY_IN_OPD_ENTRY, PARENT_OPD_ID, MAIN_ENTITY_ID, MAIN_ENTITY_IN_OPD_ID "+
			  "FROM OPD " +
			  "WHERE PROJECT_ID = " + myProject.getProjectId() +
			  "AND OPD_ID = " + opdNum + ";";

			statement = connection.createStatement();

			tableRst = statement.executeQuery(sqlQuery);
			while(tableRst.next())
			{
				if (opdNum == 1) //root OPD
				{
					tempOpd = projectStructure.getOpd(1);
					tempOpd.setEntityInOpdMaxEntry(tableRst.getInt("MAX_ENTITY_IN_OPD_ENTRY"));
				}
				else
				{
					//added Stas
//					if(parentThing.getOpdId() == (long)tableRst.getInt("PARENT_OPD_ID"))
//					{
					if(parentThing.getEntity().getId() == (long)tableRst.getInt("MAIN_ENTITY_ID"))
					{
					//end added Stas
						parentOpd = projectStructure.getOpd(parentThing.getOpdId());
						tempOpd = new Opd( myProject, getString(tableRst, "OPD_NAME"), opdNum, parentOpd);
						tempOpd.setEntityInOpdMaxEntry(tableRst.getInt("MAX_ENTITY_IN_OPD_ENTRY"));
						//added Stas
						tempOpd.setMainEntry((ThingEntry)projectStructure.getEntry(parentThing.getEntity().getId()));
						//end added Stas
						projectStructure.put(opdNum, tempOpd);
						mainEntitytInOpdId = (long)tableRst.getInt("MAIN_ENTITY_IN_OPD_ID");
					//added Stas
					}
					//end added Stas
				}
			}

			tableRst.close();
			statement.close();

			if (tempOpd != null)
			{
				_loadParentThingsForOpd(opdNum, mainEntitytInOpdId);
				_loadChildThingsForOpd(opdNum);
			}

		}
		catch(Exception e)
		{
			System.out.println("Exception in _loadOpdTable "+e);
		}

		return tempOpd;
	}

	private void _saveEntityTable(Entry pEntry)
	{
	PreparedStatement prepStmt;
	String sqlQuery;


	OpmEntity myEntity;
	//insert into entity table
	try
	{
		String tableName=null;

		sqlQuery =
			"INSERT INTO ENTITY " +
			"(ENTITY_ID, ENTITY_NAME, DESCRIPTION,TABLE_NAME, PROJECT_ID, ZOOM_IN_OPD, PARENT_THING_ID, IS_ENVIRONMENTAL) " +
				"VALUES ( ?, ?, ?, ? , ?, ?, ?, ?);";


			myEntity = pEntry.getLogicalEntity();
			prepStmt = connection.prepareStatement(sqlQuery);

			prepStmt.setInt(1, (int)myEntity.getId());
			setString(prepStmt, 2, myEntity.getName());
			setMemoString(prepStmt, 3, myEntity.getDescription());

			if (myEntity instanceof OpmProcess)
			{
				tableName = PROCESS;
			}

			if (myEntity instanceof OpmObject)
			{
				tableName = OBJECT;
			}


			if (myEntity instanceof OpmProceduralLink)
			{
				tableName = LINK;
			}

			if (myEntity instanceof OpmStructuralRelation)
			{
				tableName = RELATION;
			}

			if (myEntity instanceof OpmState)
			{
				tableName = STATE;
			}

			if (tableName == null)
			{
				JOptionPane.showMessageDialog(null, " Serious internal bug occured in _saveEntityTable function \n Please contact software developers.", "Error" ,JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}

			setString   (prepStmt,4, tableName);
			prepStmt.setInt   	(5, (int)myProject.getProjectId());
			if (pEntry instanceof ThingEntry)
			{
				Opd zoomInOpd = ((ThingEntry)pEntry).getZoomedInOpd();
				ThingEntry parentEntry = ((ThingEntry)pEntry).getParentThing();

				if (zoomInOpd == null)
				{
					prepStmt.setNull(6, Types.INTEGER);
				}
				else
				{
					prepStmt.setInt(6, (int)zoomInOpd.getOpdId());
				}

				if (parentEntry == null)
				{
					prepStmt.setNull(7, Types.INTEGER);
				}
				else
				{
					prepStmt.setInt(7, (int)parentEntry.getId());
				}

			}
			else
			{
				prepStmt.setNull(6, Types.INTEGER);
				prepStmt.setNull(7, Types.INTEGER);
			}

			prepStmt.setBoolean(8, myEntity.isEnviromental());
			prepStmt.executeUpdate();
			prepStmt.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception in _saveEntityTable "+e);
		}

	}


	private void _loadParentThings()
	{
		PreparedStatement prepStmt;
		String sqlQuery;
		ResultSet lRst;
		Entry myEntry;
		Statement statement;
		String table;

		try
		{

			sqlQuery =
				"SELECT ENTITY_ID, ENTITY_NAME, DESCRIPTION, TABLE_NAME " +
				"FROM ENTITY " +
				"WHERE PARENT_THING_ID IS NULL AND " +
				"PROJECT_ID = " + myProject.getProjectId();

			statement = connection.createStatement();
			lRst = statement.executeQuery(sqlQuery);

			while(lRst.next())
			{
				table = getString(lRst, "TABLE_NAME");
				if (table.equals(PROCESS))
				{
					OpmProcess myProcess;

					myProcess = new OpmProcess((long)lRst.getInt("ENTITY_ID"), " ");
					myProcess.setName(getString(lRst, "ENTITY_NAME"));
					myProcess.setDescription(getString(lRst, "DESCRIPTION"));
					_loadProcessTable(myProcess);

					myEntry = new ProcessEntry(myProcess, myProject);
					projectStructure.put(myProcess.getId(), myEntry);
					continue;
				}

				if (table.equals(OBJECT))
				{
					OpmObject myObject;

					myObject = new OpmObject((long)lRst.getInt("ENTITY_ID"), " ");
					myObject.setName(getString(lRst, "ENTITY_NAME"));
					myObject.setDescription(getString(lRst, "DESCRIPTION"));
					_loadObjectTable(myObject);

					myEntry = new ObjectEntry(myObject, myProject);
					projectStructure.put(myObject.getId(), myEntry);

					continue;
				}
			}
			lRst.close();
			statement.close();
		}
		catch(java.sql.SQLException e)
		{
			System.out.println("Exception in _loadParentThings "+e+e.getErrorCode());
		}

	}


	private boolean  _loadChildThings()
	{
		PreparedStatement prepStmt;
		String sqlQuery;
		ResultSet lRst;
		Entry myEntry;
		ThingEntry parentEntry;
		Statement statement;
		String table;
		long thingId;
		boolean moreThingsToLoad = false;


		try
		{

			sqlQuery =
				"SELECT ENTITY_ID, ENTITY_NAME, DESCRIPTION, TABLE_NAME, PARENT_THING_ID " +
				"FROM ENTITY " +
				"WHERE PARENT_THING_ID IS NOT NULL AND " +
				"PROJECT_ID = " + myProject.getProjectId();

			statement = connection.createStatement();
			lRst = statement.executeQuery(sqlQuery);

			while(lRst.next())
			{
				table = getString(lRst, "TABLE_NAME");
				parentEntry = (ThingEntry)projectStructure.getEntry((long)lRst.getInt("PARENT_THING_ID"));
				thingId = (long)lRst.getInt("ENTITY_ID");

				if (table.equals(PROCESS))
				{
					if (projectStructure.getEntry(thingId) != null)
					{
						continue;
					}
					OpmProcess myProcess;

					myProcess = new OpmProcess(thingId, " ");
					myProcess.setName(getString(lRst, "ENTITY_NAME"));
					myProcess.setDescription(getString(lRst, "DESCRIPTION"));
					_loadProcessTable(myProcess);

					myEntry = new ProcessEntry(myProcess, parentEntry, myProject);
					projectStructure.put(myProcess.getId(), myEntry);
					moreThingsToLoad = true;
					continue;
				}

				if (table.equals(OBJECT))
				{
					if (projectStructure.getEntry(thingId) != null)
					{
						continue;
					}

					OpmObject myObject;

					myObject = new OpmObject(thingId, " ");
					myObject.setName(getString(lRst, "ENTITY_NAME"));
					myObject.setDescription(getString(lRst, "DESCRIPTION"));
					_loadObjectTable(myObject);

					myEntry = new ObjectEntry(myObject, parentEntry, myProject);
					projectStructure.put(myObject.getId(), myEntry);
					moreThingsToLoad = true;;
					continue;
				}
			}
			lRst.close();
			statement.close();
			return moreThingsToLoad;

		}
		catch(java.sql.SQLException e)
		{
			System.out.println("Exception in _loadChildThings "+e+e.getErrorCode());
			return false;
		}

	}

	private void _loadLogicalStructure()
	{
		PreparedStatement prepStmt;
		String sqlQuery;
		ResultSet lRst;
		Entry myEntry;
		Statement statement;
		String table;

		try
		{
			_loadParentThings();
			boolean moreThingToLoad = true;

			while (moreThingToLoad)
			{
				moreThingToLoad = _loadChildThings();
			}

			sqlQuery =
			  "SELECT ENTITY_ID, ENTITY_NAME, DESCRIPTION, TABLE_NAME " +
			  "FROM ENTITY " +
			  "WHERE PROJECT_ID = " + myProject.getProjectId() + ";";

			statement = connection.createStatement();
			lRst = statement.executeQuery(sqlQuery);



			while(lRst.next())
			{
				table = getString(lRst, "TABLE_NAME");

				if (table.equals(STATE))
				{
					OpmState myState;
					OpmObject parent;

					myState = new OpmState((long)lRst.getInt("ENTITY_ID"), " ");
					parent = _loadStateTable(myState);
					ObjectEntry objectEntry = (ObjectEntry)(projectStructure.getEntry(parent.getId()));
					myState.setName(getString(lRst, "ENTITY_NAME"));
					myState.setDescription(getString(lRst, "DESCRIPTION"));
					myEntry = new StateEntry(myState, parent, myProject);
					projectStructure.put(myState.getId(), myEntry);
					objectEntry.addState(myState);

					continue;

				}

			}

			lRst.close();
			statement.close();
			sqlQuery =
			  "SELECT ENTITY_ID, ENTITY_NAME, DESCRIPTION, TABLE_NAME, IS_ENVIRONMENTAL " +
			  "FROM ENTITY " +
			  "WHERE PROJECT_ID = " + myProject.getProjectId() + ";";
			   //+
			  //" AND NOT ( TABLE = 'PROCESS' OR TABLE = 'OBJECT');";

			statement = connection.createStatement();
			lRst = statement.executeQuery(sqlQuery);

			while(lRst.next())
			{
				table = getString(lRst, "TABLE_NAME");

				if (table.equals(LINK))
				{
					OpmProceduralLink myLink;

					myLink = _loadLinkTable((long)lRst.getInt("ENTITY_ID"));
					myLink.setName(getString(lRst, "ENTITY_NAME"));
					myLink.setDescription(getString(lRst, "DESCRIPTION"));
					myLink.setEnviromental(lRst.getBoolean("IS_ENVIRONMENTAL"));

					myEntry = new LinkEntry(myLink, myProject);
					projectStructure.put(myLink.getId(), myEntry);

					continue;

				}

				if (table.equals(RELATION))
				{
					OpmStructuralRelation myRelation;
					long relationId;

					relationId = (long)lRst.getInt("ENTITY_ID");
					myRelation = _loadRelationTable(relationId);
					myRelation.setName(getString(lRst, "ENTITY_NAME"));
					myRelation.setDescription(getString(lRst, "DESCRIPTION"));
					myRelation.setEnviromental(lRst.getBoolean("IS_ENVIRONMENTAL"));

					if (myRelation instanceof OpmFundamentalRelation)
					{
						myEntry = new FundamentalRelationEntry((OpmFundamentalRelation)myRelation, myProject);
					}
					else
					{
						myEntry = new GeneralRelationEntry((OpmGeneralRelation)myRelation, myProject);
					}

					projectStructure.put(myRelation.getId(), myEntry);

					continue;

				}

			}
			lRst.close();
			statement.close();
		}
		catch(java.sql.SQLException e)
		{
			System.out.println("Exception in _loadlogicalStructure "+e+e.getErrorCode());
		}

	}


	private void _saveObjectTable(OpmObject myObject)
	{
		//insert into object table
		PreparedStatement prepStmt;
		String sqlQuery;

		try
		{

			sqlQuery =
			"INSERT INTO OBJECT (OBJECT_ID,PHYSICAL,ENVIRONMENTAL,PERSISTENT, TYPE,SCOPE,KEY,INDEX_NAME,INDEX_ORDER,INITIAL_VALUE, PROJECT_ID, TYPE_ORIGIN_ID, NO_OF_INSTANCES, ROLE) " +
			"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

			prepStmt = connection.prepareStatement(sqlQuery);

			prepStmt.setInt(1, (int)myObject.getId());
			prepStmt.setBoolean(2, myObject.isPhysical());
			prepStmt.setBoolean(3, myObject.isEnviromental());
			prepStmt.setBoolean(4, myObject.isPersistent());
			setString(prepStmt, 5, myObject.getType());
			setString(prepStmt,6, myObject.getScope());
			prepStmt.setBoolean(7, myObject.isKey());
			setString(prepStmt, 8, myObject.getIndexName());
			prepStmt.setInt(9,  myObject.getIndexOrder());
			setString(prepStmt, 10, myObject.getInitialValue());
			prepStmt.setInt(11, (int)myProject.getProjectId());
			prepStmt.setInt(12, (int)myObject.getTypeOriginId());
			prepStmt.setInt(13, myObject.getNumberOfInstances());
			setString(prepStmt, 14, myObject.getRole());

			prepStmt.executeUpdate();
			prepStmt.close();
		}
		catch(Exception e)
			{
				System.out.println("Exception in _saveObjectTable "+e);
			}

	}


	private void _loadObjectTable(OpmObject myObject)
	{
		String sqlQuery;
		ResultSet objectRst;
		Statement statement;

		try
		{

			sqlQuery =
			  "SELECT PHYSICAL,ENVIRONMENTAL,PERSISTENT, TYPE, SCOPE, KEY, " +
						"INDEX_NAME,INDEX_ORDER,INITIAL_VALUE, TYPE_ORIGIN_ID, NO_OF_INSTANCES, ROLE " +
			  "FROM OBJECT " +
			  "WHERE PROJECT_ID = " + myProject.getProjectId() +
			  " AND OBJECT_ID = " + myObject.getId() + ";";

			statement = connection.createStatement();
			objectRst = statement.executeQuery(sqlQuery);

			while(objectRst.next())
			{
				myObject.setPersistent(objectRst.getBoolean("PERSISTENT"));
				myObject.setKey(objectRst.getBoolean("KEY"));
				myObject.setEnviromental(objectRst.getBoolean("ENVIRONMENTAL"));
				myObject.setPhysical(objectRst.getBoolean("PHYSICAL"));
				myObject.setType(getString(objectRst, "TYPE"));
				myObject.setScope(getString(objectRst, "SCOPE"));
				myObject.setIndexName(getString(objectRst, "INDEX_NAME"));
				myObject.setIndexOrder(objectRst.getInt("INDEX_ORDER"));
				myObject.setInitialValue(getString(objectRst, "INITIAL_VALUE"));
				myObject.setTypeOriginId(objectRst.getInt("TYPE_ORIGIN_ID"));
				myObject.setNumberOfInstances(objectRst.getInt("NO_OF_INSTANCES"));
				myObject.setRole(getString(objectRst,"ROLE"));
			}

			objectRst.close();
			statement.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception in _loadObjectTable "+e);
		}

	}


	private void _saveProcessTable(OpmProcess myProcess)
	{
		//insert into process table

		PreparedStatement prepStmt;
		String sqlQuery;

		try
		{

			sqlQuery =
			"INSERT INTO PROCESS (PROCESS_ID,PHYSICAL,ENVIRONMENTAL,MAX_TIME_ACTIVATION, MIN_TIME_ACTIVATION,SCOPE,PROCESS_BODY, PROJECT_ID, NO_OF_INSTANCES, ROLE) " +
			"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

			prepStmt = connection.prepareStatement(sqlQuery);

			prepStmt.setInt(1, (int)myProcess.getId());
			prepStmt.setBoolean(2, myProcess.isPhysical());
			prepStmt.setBoolean(3, myProcess.isEnviromental());
			setString(prepStmt, 4, myProcess.getMaxTimeActivation());
			setString(prepStmt, 5, myProcess.getMinTimeActivation());

			setString(prepStmt, 6, myProcess.getScope());
			setMemoString(prepStmt, 7, myProcess.getProcessBody());
			prepStmt.setInt(8, (int)myProject.getProjectId());
			prepStmt.setInt(9, myProcess.getNumberOfInstances());
			setString(prepStmt, 10, myProcess.getRole());
			prepStmt.executeUpdate();
			prepStmt.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception in _saveProcessTable "+e);
		}

	}


	private void _loadProcessTable(OpmProcess myProcess)
	{
		String sqlQuery;
		ResultSet processRst;
		Statement statement;

		try
		{

			sqlQuery =
			  "SELECT PHYSICAL , ENVIRONMENTAL , MAX_TIME_ACTIVATION, MIN_TIME_ACTIVATION, NO_OF_INSTANCES, ROLE, " +
			  "SCOPE ,PROCESS_BODY " +
			  "FROM PROCESS " +
			  "WHERE PROJECT_ID = " + myProject.getProjectId() +
			  " AND PROCESS_ID = " + myProcess.getId() + ";";

			statement = connection.createStatement();
			processRst = statement.executeQuery(sqlQuery);

			while(processRst.next())
			{
				myProcess.setEnviromental(processRst.getBoolean("ENVIRONMENTAL"));
				myProcess.setPhysical(processRst.getBoolean("PHYSICAL"));
				myProcess.setScope(getString(processRst, "SCOPE"));
				myProcess.setMinTimeActivation(getString(processRst, "MIN_TIME_ACTIVATION"));
				myProcess.setMaxTimeActivation(getString(processRst, "MAX_TIME_ACTIVATION"));
				myProcess.setProcessBody(getString(processRst, "PROCESS_BODY"));
				myProcess.setNumberOfInstances(processRst.getInt("NO_OF_INSTANCES"));
				myProcess.setRole(getString(processRst, "ROLE"));
			}

			processRst.close();
			statement.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception in _loadProcessTable "+e);
		}

	}


	private void _loadParentThingsForOpd(long opdNum, long mainEntityInOpdId)
	{
		try
		{
			String sqlQuery;
			ResultSet lPTOrst;
			Statement statement;

			OpmThing logicalThing;
			OpdThing graphicalThing;
			ThingEntry myEntry;
			long entityId;
			ThingInstance myInstance;
			Opd currentOpd;

			sqlQuery =
			  "SELECT ENTITY_ID, ENTITY_IN_OPD_ID, X, Y, WIDTH, HEIGHT, " +
			  "TEXT_COLOR, BORDER_COLOR, BACKGROUND_COLOR, UNFOLDING_OPD, " +
			  "ZOOMED_IN, STATES_IS_AUTOARRANGED, TEXT_POSITION, LAYER " +
			  "FROM THING_IN_OPD " +
			  "WHERE PARENT_OBJECT_ID IS NULL AND " +
			  "PROJECT_ID = " + myProject.getProjectId() +
			  "AND OPD_ID = " + opdNum + ";";

			statement = connection.createStatement();
			lPTOrst = statement.executeQuery(sqlQuery);

			while(lPTOrst.next())
			{
				entityId = (long)lPTOrst.getInt("ENTITY_ID");
				myEntry = (ThingEntry)projectStructure.getEntry(entityId);

				logicalThing = (OpmThing)myEntry.getLogicalEntity();
				currentOpd = projectStructure.getOpd(opdNum);
				myProject.setCurrentOpd(currentOpd);
				long entityInOpdId = (long)lPTOrst.getInt("ENTITY_IN_OPD_ID");
				if (myEntry instanceof ProcessEntry)
				{
					myInstance = myProject.addProcess(0,0, currentOpd.getDrawingArea(), entityId, entityInOpdId);
				}
				else
				{
					myInstance = myProject.addObject(0,0, currentOpd.getDrawingArea(), entityId, entityInOpdId, false);
					((OpdObject)myInstance.getThing()).setStatesAutoarrange(lPTOrst.getBoolean("STATES_IS_AUTOARRANGED"));
				}

				//added Stas
				if(opdNum != 1 && entityInOpdId == mainEntityInOpdId)
				{
					currentOpd.setMainInstance(myInstance);
				}
				//end added Stas

				myInstance.setLocation(lPTOrst.getInt("X"), lPTOrst.getInt("Y"));
				myInstance.setSize(lPTOrst.getInt("WIDTH"), lPTOrst.getInt("HEIGHT"));

				myInstance.setTextColor(new Color(lPTOrst.getInt("TEXT_COLOR")));
				myInstance.setBorderColor(new Color(lPTOrst.getInt("BORDER_COLOR")));
				myInstance.setBackgroundColor(new Color(lPTOrst.getInt("BACKGROUND_COLOR")));
				myInstance.setTextPosition(getString(lPTOrst, "TEXT_POSITION"));
				myInstance.setLayer(lPTOrst.getInt("LAYER"));

				graphicalThing = myInstance.getThing();
				//graphicalThing.repaint();

				//myInstance = (ThingInstance)myEntry.getInstance(new OpdKey(opdNum, graphicalThing.getEntityInOpdId()));
				myInstance.setUnfoldingOpd(_loadOpdTable((long)lPTOrst.getInt("UNFOLDING_OPD"), graphicalThing));
				myInstance.setZoomedIn(lPTOrst.getBoolean("ZOOMED_IN"));
				myInstance.update();

//				if ((myEntry.getZoomedInOpd() == null) && (projectStructure.getOpd() == null) // && myInstance.isZoomedIn())
//				{
//					myEntry.setZoomedInOpd(_loadZoomedInOpd(graphicalThing));
//				}

				_loadZoomedInOpd(graphicalThing);
			}

			lPTOrst.close();
			statement.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception in _loadParentThingForOpd "+e);
		}


	}

	private Opd _loadZoomedInOpd(OpdThing parentThing)
	{
		String sqlQuery;
		ResultSet rst;
		ThingEntry myEntry;
		Statement statement;
		Opd tempOpd = null;
		String table;
		long zoomInOpdID;
		long parentOpdID;

		myEntry = (ThingEntry)projectStructure.getEntry(parentThing.getEntity().getId());

		try
		{

			sqlQuery =
			  " SELECT ZOOM_IN_OPD " +
			  " FROM ENTITY " +
			  " WHERE ENTITY_ID = " + parentThing.getEntity().getId() +
			  " AND ZOOM_IN_OPD IS NOT NULL " +
			  " AND PROJECT_ID = " + myProject.getProjectId() + ";";

			statement = connection.createStatement();
			rst = statement.executeQuery(sqlQuery);

			while(rst.next())
			{
				zoomInOpdID = (long)rst.getInt("ZOOM_IN_OPD");
				if ((myEntry.getZoomedInOpd() == null) && (projectStructure.getOpd(zoomInOpdID) == null)) // && myInstance.isZoomedIn())
				{
					//added Stas
//					parentOpdID = (long)rst.getInt()
//					if(
					//end added Stas
					tempOpd = _loadOpdTable(zoomInOpdID, parentThing);
					myEntry.setZoomedInOpd(tempOpd);
				}


			}

			rst.close();
			statement.close();
			return tempOpd;
		}
		catch(Exception e)
		{
			System.out.println("Exception in _loadZoomedInOpd "+e);
			return tempOpd;
		}


	}

	private void _loadChildThingsForOpd(long opdNum)
	{
		try
		{
			String sqlQuery;
			ResultSet rst;
			Statement statement;

			OpmThing logicalThing;
			OpdThing graphicalThing;
			ThingEntry myEntry;
			ThingInstance myInstance;
			Opd currentOpd;
			OpdThing parentThing;
			ThingEntry parentEntry;
			ThingInstance parentInstance;

			sqlQuery =
			  "SELECT ENTITY_ID, ENTITY_IN_OPD_ID, X, Y, WIDTH, HEIGHT, " +
			  "TEXT_COLOR, BORDER_COLOR, BACKGROUND_COLOR, UNFOLDING_OPD, ZOOMED_IN, " +
			  "PARENT_OBJECT_ID, PARENT_OBJECT_IN_OPD_ID, STATES_IS_AUTOARRANGED, TEXT_POSITION, LAYER " +
			  "FROM THING_IN_OPD " +
			  "WHERE PARENT_OBJECT_ID IS NOT NULL AND " +
			  "PROJECT_ID = " + myProject.getProjectId() + " AND " +
			  "OPD_ID = "+ opdNum;


			statement = connection.createStatement();
			rst = statement.executeQuery(sqlQuery);

			while(rst.next())
			{
				long entityId = (long)rst.getInt("ENTITY_ID");
				myEntry = (ThingEntry)projectStructure.getEntry(entityId);
				logicalThing = (OpmThing)myEntry.getLogicalEntity();
				currentOpd = projectStructure.getOpd(opdNum);

				parentEntry = (ThingEntry)projectStructure.getEntry((long)rst.getInt("PARENT_OBJECT_ID"));
				parentInstance = (ThingInstance)parentEntry.getInstance(new OpdKey(opdNum, (long)rst.getInt("PARENT_OBJECT_IN_OPD_ID")));
				myProject.setCurrentOpd(currentOpd);
				if (myEntry instanceof ProcessEntry)
				{
					myInstance = myProject.addProcess(0,0, parentInstance.getThing(), entityId, (long)rst.getInt("ENTITY_IN_OPD_ID"));
				}
				else
				{
					myInstance = myProject.addObject(0,0, parentInstance.getThing(), entityId, (long)rst.getInt("ENTITY_IN_OPD_ID"), false);
					((OpdObject)myInstance.getThing()).setStatesAutoarrange(rst.getBoolean("STATES_IS_AUTOARRANGED"));
				}

				myInstance.setLocation(rst.getInt("X"), rst.getInt("Y"));
				myInstance.setSize(rst.getInt("WIDTH"), rst.getInt("HEIGHT"));

				myInstance.setTextColor(new Color(rst.getInt("TEXT_COLOR")));
				myInstance.setBorderColor(new Color(rst.getInt("BORDER_COLOR")));
				myInstance.setBackgroundColor(new Color(rst.getInt("BACKGROUND_COLOR")));
				myInstance.setTextPosition(getString(rst, "TEXT_POSITION"));
				myInstance.setLayer(rst.getInt("LAYER"));
				graphicalThing = myInstance.getThing();
				//graphicalThing.repaint();

				//myInstance = (ThingInstance)myEntry.getInstance(new OpdKey(opdNum, graphicalThing.getEntityInOpdId()));
				myInstance.setUnfoldingOpd(_loadOpdTable((long)rst.getInt("UNFOLDING_OPD"), graphicalThing));

				myInstance.setZoomedIn(rst.getBoolean("ZOOMED_IN"));
				myInstance.update();

//				if ((myEntry.getZoomedInOpd() == null)) //&& myInstance.isZoomedIn())
//				{
//					myEntry.setZoomedInOpd(_loadZoomedInOpd(graphicalThing));
//				}

				_loadZoomedInOpd(graphicalThing);

			 }

			rst.close();
			statement.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception in _loadChildThings "+e);
		}


	}


	private void _saveStateTable(OpmState myState, StateEntry myEntry)
	{
		PreparedStatement prepStmt;
		String sqlQuery;


		try
		{
			//insert into state table

			sqlQuery =
				"INSERT INTO STATE (STATE_ID, OBJECT_ID,INITIAL,FINAL,MIN_TIME,MAX_TIME, PROJECT_ID) " +
				"VALUES (?, ?, ?, ?, ?, ?, ? );";

			prepStmt = connection.prepareStatement(sqlQuery);

			prepStmt.setInt(1, (int)myState.getId());
			prepStmt.setInt(2, (int)myEntry.getParentObjectId());
			prepStmt.setBoolean(3, myState.isInitial());
			prepStmt.setBoolean(4, myState.isFinal());
			setString(prepStmt, 5, myState.getMinTime());
			setString(prepStmt, 6, myState.getMaxTime());
			prepStmt.setInt(7, (int)myProject.getProjectId());

			prepStmt.executeUpdate();
			prepStmt.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception in _saveStateTable "+e);
		}

	}

	private void _saveStateInOpdTable(StateInstance myInstance, StateEntry myEntry)
	{
		try
		{
			PreparedStatement prepStmt;
			String sqlQuery;


			OpdState myState = myInstance.getState();

			sqlQuery =
					"INSERT INTO STATE_IN_OPD " +
					"(PROJECT_ID, ENTITY_ID, ENTITY_IN_OPD_ID, OPD_ID, " +
					"X , Y, WIDTH, HEIGHT, " +
					 "TEXT_COLOR, BORDER_COLOR, BACKGROUND_COLOR, PARENT_OBJECT_IN_OPD_ID, IS_VISIBLE ) "+
					"VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? );";

			prepStmt = connection.prepareStatement(sqlQuery);

			prepStmt.setInt(1,(int)myProject.getProjectId());
			prepStmt.setInt(2,(int)myEntry.getLogicalEntity().getId());
			prepStmt.setInt(3,(int)myState.getEntityInOpdId());
			prepStmt.setInt(4,(int)myState.getOpdId());

			prepStmt.setInt(5, myState.getX());
			prepStmt.setInt(6, myState.getY());
			prepStmt.setInt(7, myState.getWidth());
			prepStmt.setInt(8, myState.getHeight());
			prepStmt.setInt(9 , myState.getTextColor().getRGB());
			prepStmt.setInt(10 , myState.getBorderColor().getRGB());
			prepStmt.setInt(11 , myState.getBackgroundColor().getRGB());


			prepStmt.setInt(12, (int)myInstance.getParent().getEntityInOpdId());
						prepStmt.setBoolean(13, myState.isVisible());
			prepStmt.executeUpdate();
			prepStmt.close();
		}
		catch(Exception e)
			{
				System.out.println("Exception in _saveStateInOpdTable "+e);
			}

	}


	private void _saveRelationTable(OpmStructuralRelation myRelation)
	{
		PreparedStatement prepStmt;
		String sqlQuery;
		ResultSet rst;

		try
		{
			String relationType = Constants.getDBType4Relation(myRelation);

			//insert into relation table
			sqlQuery =
				"INSERT INTO RELATION (RELATION_ID, RELATION_TYPE, SOURCE_ID,DESTINATION_ID, " +
				 "SOURCE_CARDINALITY,DESTINATION_CARDINALITY,FORWARD_RELATION_MEANING,BACKWARD_RELATION_MEANING, PROJECT_ID) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?  );";

			prepStmt = connection.prepareStatement(sqlQuery);

			prepStmt.setInt(1,(int)myRelation.getId());
			setString(prepStmt, 2, relationType );
			prepStmt.setInt(3,(int)myRelation.getSourceId() );
			prepStmt.setInt(4,(int)myRelation.getDestinationId());
			setString(prepStmt, 5,myRelation.getSourceCardinality());
			setString(prepStmt, 6,myRelation.getDestinationCardinality());
			setString(prepStmt, 7, myRelation.getForwardRelationMeaning());
			setString(prepStmt, 8, myRelation.getBackwardRelationMeaning());
			prepStmt.setInt(9, (int)myProject.getProjectId());

			prepStmt.executeUpdate();
			prepStmt.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception in _saveRelationTable "+e);
		}

	}

	private void _saveThingInOpdTable(ThingInstance myInstance)
	{
		try
		{
			PreparedStatement prepStmt;
			String sqlQuery;


			OpdThing myThing = myInstance.getThing();

			sqlQuery =
					"INSERT INTO THING_IN_OPD " +
					"(PROJECT_ID, ENTITY_ID, ENTITY_IN_OPD_ID, OPD_ID ,X , Y, WIDTH, HEIGHT, " +
					"TEXT_COLOR, BORDER_COLOR, BACKGROUND_COLOR, PARENT_OBJECT_ID, " +
					"PARENT_OBJECT_IN_OPD_ID, UNFOLDING_OPD, ZOOMED_IN, STATES_IS_AUTOARRANGED, " +
					"TEXT_POSITION, LAYER ) "+
					"VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?, ?, ?, ?, ?);";

			prepStmt = connection.prepareStatement(sqlQuery);

			prepStmt.setInt(1,(int)myProject.getProjectId());
			prepStmt.setInt(2,(int)((ThingEntry)myThing.getEntry()).getLogicalEntity().getId());
			prepStmt.setInt(3,(int)myThing.getEntityInOpdId());
			prepStmt.setInt(4,(int)myThing.getOpdId());

			prepStmt.setInt(5, myThing.getX());
			prepStmt.setInt(6, myThing.getY());
			prepStmt.setInt(7, myThing.getWidth());
			prepStmt.setInt(8, myThing.getHeight());
			prepStmt.setInt(9 , myThing.getTextColor().getRGB());
			prepStmt.setInt(10 , myThing.getBorderColor().getRGB());
			prepStmt.setInt(11 , myThing.getBackgroundColor().getRGB());


			if (myInstance.getParent() != null)
			{
				prepStmt.setInt(12,(int)((ThingEntry)myInstance.getParent().getEntry()).getLogicalEntity().getId());
				prepStmt.setInt(13, (int)myInstance.getParent().getEntityInOpdId());
			}
			else
			{
				prepStmt.setNull(12, Types.INTEGER);
				prepStmt.setNull(13, Types.INTEGER);
			}


			if (myInstance.getUnfoldingOpd() != null)
			{
				prepStmt.setInt(14, (int)myInstance.getUnfoldingOpd().getOpdId());
			}
			else
			{
				prepStmt.setNull(14, Types.INTEGER);
			}

			prepStmt.setBoolean(15, myInstance.isZoomedIn());

			if (myThing instanceof OpdObject)
			{
				prepStmt.setBoolean(16, ((OpdObject)myThing).isStatesAutoarrange());
			}
			else
			{
//				prepStmt.setNull(16, Types.VARBINARY);
                prepStmt.setBoolean(16, false);
			}

			prepStmt.setString(17, myInstance.getTextPosition());
			prepStmt.setInt(18, myInstance.getLayer());

			prepStmt.executeUpdate();
			prepStmt.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception in _saveThingInOpdTable "+e);
            e.printStackTrace();
		}

	}


	private void _saveLinkTable(OpmProceduralLink myLink)
	{
		//insert into link table

		PreparedStatement prepStmt;
		String sqlQuery;
		String linkType = null;

		try
		{

			sqlQuery =
			"INSERT INTO LINK (PROJECT_ID, LINK_ID, LINK_TYPE, SOURCE_ID, DESTINATION_ID, " +
			"MIN_REACTION_TIME, MAX_REACTION_TIME, CONDITION , PATH ) " +
			"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?  );";

			linkType = Constants.getDBType4Link(myLink);

			if (linkType == null)
			{
				JOptionPane.showMessageDialog(null, " Serious internal bug occured in _saveLinkTable function \n Please contact software developers.", "Error" ,JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}

			prepStmt = connection.prepareStatement(sqlQuery);
			prepStmt.setInt(1, (int)myProject.getProjectId());
			prepStmt.setInt(2, (int)myLink.getId());
			setString(prepStmt, 3, linkType);
			prepStmt.setInt(4, (int)myLink.getSourceId());
			prepStmt.setInt(5, (int)myLink.getDestinationId());
			setString(prepStmt, 6, myLink.getMinReactionTime());
			setString(prepStmt, 7, myLink.getMaxReactionTime());

			setString(prepStmt, 8, myLink.getCondition());
			setString(prepStmt, 9, myLink.getPath());

			prepStmt.executeUpdate();
			prepStmt.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception in _saveLinkTable "+e);
		}

	}

	private void _saveLinkInOpdTable(LinkInstance myInstance, LinkEntry myEntry)
	{
		try
		{
			PreparedStatement prepStmt;
			String sqlQuery;
			OpmProceduralLink myLink = (OpmProceduralLink)myEntry.getLogicalEntity();

			AroundDragger sourceDragger = myInstance.getSourceDragger();
			AroundDragger destinationDragger = myInstance.getDestinationDragger();
			OpdConnectionEdge source = myInstance.getSource();
			OpdConnectionEdge destination= myInstance.getDestination();

			sqlQuery =
					"INSERT INTO LINK_IN_OPD " +
					"(PROJECT_ID, ENTITY_ID, ENTITY_IN_OPD_ID, OPD_ID , " +
					"SOURCE_ID, SOURCE_IN_OPD_ID, SOURCE_CONNECTION_SIDE, SOURCE_CONNECTION_PARAMETER, " +
					"DESTINATION_ID, DESTINATION_IN_OPD_ID, DESTINATION_CONNECTION_SIDE, DESTINATION_CONNECTION_PARAMETER, "+
					 "TEXT_COLOR, BORDER_COLOR, BACKGROUND_COLOR, IS_AUTOARRANGED ) " +
					 "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? );";

			prepStmt = connection.prepareStatement(sqlQuery);

			prepStmt.setInt(1,(int)myProject.getProjectId());
			prepStmt.setInt(2,(int)myLink.getId());
			prepStmt.setInt(3,(int)myInstance.getKey().getEntityInOpdId());
			prepStmt.setInt(4,(int)myInstance.getKey().getOpdId());
			prepStmt.setInt(5,(int)source.getEntity().getId());
			prepStmt.setInt(6,(int)source.getEntityInOpdId());
			prepStmt.setInt(7, sourceDragger.getSide());
			prepStmt.setDouble(8, sourceDragger.getParam());
			prepStmt.setInt(9,(int)destination.getEntity().getId());
			prepStmt.setInt(10,(int)destination.getEntityInOpdId());
			prepStmt.setInt(11, destinationDragger.getSide());
			prepStmt.setDouble(12, destinationDragger.getParam());

			prepStmt.setInt(13 , myInstance.getTextColor().getRGB());
			prepStmt.setInt(14 , myInstance.getBorderColor().getRGB());
			prepStmt.setInt(15 , myInstance.getBackgroundColor().getRGB());
			prepStmt.setBoolean(16, myInstance.isAutoArranged());
			prepStmt.executeUpdate();
			prepStmt.close();

			_saveLineTable(myInstance.getLine());
		}
		catch(Exception e)
		{
			System.out.println("Exception in _saveLinkInOpdTable "+e);
		}

	}

	private void _saveGeneralRelationInOpdTable(GeneralRelationInstance myInstance, GeneralRelationEntry myEntry)
	{
		try
		{
			PreparedStatement prepStmt;
			String sqlQuery;
			OpmGeneralRelation myRelation = (OpmGeneralRelation)myEntry.getLogicalEntity();

			OpdRelationDragger sourceDragger = (OpdRelationDragger)myInstance.getSourceDragger();
			OpdRelationDragger destinationDragger = (OpdRelationDragger)myInstance.getDestinationDragger();
			OpdConnectionEdge source = myInstance.getSource();
			OpdConnectionEdge destination= myInstance.getDestination();

			sqlQuery =
					"INSERT INTO GENERAL_RELATION_IN_OPD " +
					"(PROJECT_ID, ENTITY_ID, ENTITY_IN_OPD_ID, OPD_ID , " +
					"SOURCE_ID, SOURCE_IN_OPD_ID, SOURCE_CONNECTION_SIDE, SOURCE_CONNECTION_PARAMETER, " +
					"DESTINATION_ID, DESTINATION_IN_OPD_ID, DESTINATION_CONNECTION_SIDE, DESTINATION_CONNECTION_PARAMETER, "+
					 "TEXT_COLOR, BORDER_COLOR, BACKGROUND_COLOR, " +
					 "SOURCE_LABEL_X, SOURCE_LABEL_Y, DESTINATION_LABEL_X, DESTINATION_LABEL_Y, IS_AUTOARRANGED  ) " +
					 "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?, ?, ? );";

			prepStmt = connection.prepareStatement(sqlQuery);

			prepStmt.setInt(1,(int)myProject.getProjectId());
			prepStmt.setInt(2,(int)myRelation.getId());
			prepStmt.setInt(3,(int)myInstance.getKey().getEntityInOpdId());
			prepStmt.setInt(4,(int)myInstance.getKey().getOpdId());
			prepStmt.setInt(5,(int)source.getEntity().getId());
			prepStmt.setInt(6,(int)source.getEntityInOpdId());
			prepStmt.setInt(7, sourceDragger.getSide());
			prepStmt.setDouble(8, sourceDragger.getParam());
			prepStmt.setInt(9,(int)destination.getEntity().getId());
			prepStmt.setInt(10,(int)destination.getEntityInOpdId());
			prepStmt.setInt(11, destinationDragger.getSide());
			prepStmt.setDouble(12, destinationDragger.getParam());

			prepStmt.setInt(13 , myInstance.getTextColor().getRGB());
			prepStmt.setInt(14 , myInstance.getBorderColor().getRGB());
			prepStmt.setInt(15 , myInstance.getBackgroundColor().getRGB());

			prepStmt.setInt(16 , sourceDragger.getOpdCardinalityLabel().getX());
			prepStmt.setInt(17 , sourceDragger.getOpdCardinalityLabel().getY());
			prepStmt.setInt(18 , destinationDragger.getOpdCardinalityLabel().getX());
			prepStmt.setInt(19 , destinationDragger.getOpdCardinalityLabel().getY());
			prepStmt.setBoolean(20, myInstance.isAutoArranged());

			prepStmt.executeUpdate();
			prepStmt.close();

			_saveLineTable(myInstance.getLine());
		}
		catch(Exception e)
		{
			System.out.println("Exception in _saveGeneralRelationInOpdTable "+e);
		}

	}


		private void _saveFundamentalRelationRepresentation(GraphicalRelationRepresentation myEntry)
	{
		try
		{
			PreparedStatement prepStmt;
			String sqlQuery;

			AroundDragger sourceDragger = myEntry.getSourceDragger();
			OpdConnectionEdge source = myEntry.getSource();

			sqlQuery =
					"INSERT INTO FUNDAMENTAL_RELATION_REPRESENTATION " +
					"(PROJECT_ID, SOURCE_ID, SOURCE_IN_OPD_ID, OPD_ID , " +
					"RELATION_TYPE, SOURCE_CONNECTION_SIDE, SOURCE_CONNECTION_PARAMETER, " +
					"X, Y, WIDTH, HEIGHT, " +
										"TEXT_COLOR, BORDER_COLOR, BACKGROUND_COLOR, " +
										"POINT0_X, POINT0_Y, POINT1_X, POINT1_Y, " +
										"POINT2_X, POINT2_Y, POINT3_X, POINT3_Y  ) " +
					 "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ? , ? , ?, ? , ? , ? , ?);";

			prepStmt = connection.prepareStatement(sqlQuery);

			prepStmt.setInt(1,(int)myProject.getProjectId());
			prepStmt.setInt(2,(int)source.getEntity().getId());
			prepStmt.setInt(3,(int)source.getEntityInOpdId());
			prepStmt.setInt(4,(int)source.getOpdId());
			prepStmt.setInt(5, myEntry.getType());
			prepStmt.setInt(6, sourceDragger.getSide());
			prepStmt.setDouble(7, sourceDragger.getParam());
			prepStmt.setInt(8 , myEntry.getRelation().getX());
			prepStmt.setInt(9 , myEntry.getRelation().getY());
			prepStmt.setInt(10 , myEntry.getRelation().getWidth());
			prepStmt.setInt(11 , myEntry.getRelation().getHeight());
			prepStmt.setInt(12 , myEntry.getTextColor().getRGB());
			prepStmt.setInt(13 , myEntry.getBorderColor().getRGB());
			prepStmt.setInt(14 , myEntry.getBackgroundColor().getRGB());

			prepStmt.setInt(15 , myEntry.getPoint(0).getX());
			prepStmt.setInt(16 , myEntry.getPoint(0).getY());
			prepStmt.setInt(17 , myEntry.getPoint(1).getX());
			prepStmt.setInt(18 , myEntry.getPoint(1).getY());
			prepStmt.setInt(19 , myEntry.getPoint(2).getX());
			prepStmt.setInt(20 , myEntry.getPoint(2).getY());
			prepStmt.setInt(21 , myEntry.getPoint(3).getX());
			prepStmt.setInt(22 , myEntry.getPoint(3).getY());

			prepStmt.executeUpdate();
			prepStmt.close();

		}
		catch(Exception e)
		{
			System.out.println("Exception in _saveFundamentalRelationRepresentation "+e);
		}

	}


	private void _saveFundamentalRelationInOpdTable(FundamentalRelationInstance myInstance, GraphicalRelationRepresentation myEntry)
	{
		try
		{
			PreparedStatement prepStmt;
			String sqlQuery;
			OpmFundamentalRelation myRelation = myInstance.getLogicalRelation();

			TextDragger destinationDragger = myInstance.getDestinationDragger();
			OpdConnectionEdge destination = myInstance.getDestination();
						OpdConnectionEdge source = myEntry.getSource();

			sqlQuery =
					"INSERT INTO FUNDAMENTAL_RELATION_IN_OPD " +
					"(PROJECT_ID, ENTITY_ID, ENTITY_IN_OPD_ID, OPD_ID , " +
										"SOURCE_ID, SOURCE_IN_OPD_ID, " +
					"DESTINATION_ID, DESTINATION_IN_OPD_ID, DESTINATION_CONNECTION_SIDE, DESTINATION_CONNECTION_PARAMETER , " +
										"DESTINATION_LABEL_X, DESTINATION_LABEL_Y, " +
										"POINT0_X, POINT0_Y, POINT1_X, POINT1_Y, " +
										"POINT2_X, POINT2_Y, POINT3_X, POINT3_Y  ) " +
									 "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ? , ?, ?, ?, ?, ? );";

			prepStmt = connection.prepareStatement(sqlQuery);

			prepStmt.setInt(1,(int)myProject.getProjectId());
			prepStmt.setInt(2,(int)myRelation.getId());
			prepStmt.setInt(3,(int)myInstance.getKey().getEntityInOpdId());
			prepStmt.setInt(4,(int)myInstance.getKey().getOpdId());
			prepStmt.setInt(5,(int)source.getEntity().getId());
			prepStmt.setInt(6,(int)source.getEntityInOpdId());
			prepStmt.setInt(7,(int)destination.getEntity().getId());
			prepStmt.setInt(8,(int)destination.getEntityInOpdId());
			prepStmt.setInt(9, destinationDragger.getSide());
			prepStmt.setDouble(10, destinationDragger.getParam());
			prepStmt.setInt(11 , destinationDragger.getOpdCardinalityLabel().getX());
			prepStmt.setInt(12 , destinationDragger.getOpdCardinalityLabel().getY());
			prepStmt.setInt(13 , myInstance.getPoint(0).getX());
			prepStmt.setInt(14 , myInstance.getPoint(0).getY());
			prepStmt.setInt(15 , myInstance.getPoint(1).getX());
			prepStmt.setInt(16 , myInstance.getPoint(1).getY());
			prepStmt.setInt(17 , myInstance.getPoint(2).getX());
			prepStmt.setInt(18 , myInstance.getPoint(2).getY());
			prepStmt.setInt(19 , myInstance.getPoint(3).getX());
			prepStmt.setInt(20 , myInstance.getPoint(3).getY());

			prepStmt.executeUpdate();
			prepStmt.close();

		}
		catch(Exception e)
		{
			System.out.println("Exception in _saveFundamentalRelationInOpdTable "+e);
		}

	}

	private OpmProceduralLink _loadLinkTable(long linkId)
	{

		String sqlQuery;
		ResultSet rst;
		Statement statement;

		int linkType;
		long sourceId, destinationId;
		String sLinkType = null;
		OpmProceduralLink myLink = null;
		ConnectionEdgeEntry sourceEntry;
		ConnectionEdgeEntry destinationEntry;

		try
		{

			sqlQuery =
			  "SELECT LINK_TYPE, SOURCE_ID, DESTINATION_ID, " +
			  "MIN_REACTION_TIME, MAX_REACTION_TIME, CONDITION, PATH " +
			  "FROM LINK " +
			  "WHERE PROJECT_ID = " + myProject.getProjectId() +
			  " AND LINK_ID = "+ linkId;


			statement = connection.createStatement();
			rst = statement.executeQuery(sqlQuery);

			while(rst.next())
			{

				sLinkType = getString(rst, "LINK_TYPE");

				sourceId = rst.getInt("SOURCE_ID");
				destinationId = rst.getInt("DESTINATION_ID");

				if (sLinkType.equals("AG"))
				{
					myLink = new OpmAgent(linkId, " ", sourceId, destinationId);
				}


				if (sLinkType.equals("IN"))
				{
					myLink = new OpmInstrument(linkId, " ", sourceId, destinationId);
				}


				if (sLinkType.equals("RL"))
				{
					myLink = new OpmResultLink(linkId, " ", sourceId, destinationId);
				}

				if (sLinkType.equals("CL"))
				{
					myLink = new OpmConsumptionLink(linkId, " ", sourceId, destinationId);
				}

				if (sLinkType.equals("EL"))
				{
					myLink = new OpmEffectLink(linkId, " ", sourceId, destinationId);
				}

				if (sLinkType.equals("IL"))
				{
					myLink = new OpmInvocationLink(linkId, " ", sourceId, destinationId);
				}

				if (sLinkType.equals("CN"))
				{
					myLink = new OpmConditionLink(linkId, " ", sourceId, destinationId);
				}
				if (sLinkType.equals("EE"))
				{
					myLink = new OpmInstrumentEventLink(linkId, " ", sourceId, destinationId);
				}

				if (sLinkType.equals("IE"))
				{
					myLink = new OpmConsumptionEventLink(linkId, " ", sourceId, destinationId);
				}

				if (sLinkType.equals("EX"))
				{
					myLink = new OpmExceptionLink(linkId, " ", sourceId, destinationId);
				}

				if (myLink == null)
				{
					JOptionPane.showMessageDialog(null, " Serious internal bug occured in _loadLinkTable " +
													"function \n Please contact software developers.",
													 "Error" ,JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				}


				myLink.setMinReactionTime(getString(rst, "MIN_REACTION_TIME"));
				myLink.setMaxReactionTime(getString(rst, "MAX_REACTION_TIME"));
				myLink.setCondition(getString(rst, "CONDITION"));
				myLink.setPath(getString(rst, "PATH"));

				sourceEntry = (ConnectionEdgeEntry)projectStructure.getEntry(sourceId);
				destinationEntry = (ConnectionEdgeEntry)projectStructure.getEntry(destinationId);
				sourceEntry.addLinkSource(myLink);
				destinationEntry.addLinkDestination(myLink);
			}

			rst.close();
			statement.close();
			return myLink;
		}
		catch(Exception e)
		{
			System.out.println("Exception in _loadLinkTable "+e);
			return myLink;
		}


	}

	private void _loadLinkInOpdTable()
	{

		String sqlQuery;
		ResultSet rst;
		Statement statement;

		int linkType = -1;
		Opd currentOpd;
		long opdId;
		OpmProceduralLink myLink = null;
		LinkEntry myEntry;
		LinkInstance myInstance;
		ConnectionEdgeEntry sourceEntry;
		ConnectionEdgeEntry destinationEntry;
		ConnectionEdgeInstance sourceInstance;
		ConnectionEdgeInstance destinationInstance;

		OpdConnectionEdge source;
		OpdConnectionEdge destination;
		JLayeredPane tempContainer;

		try
		{

			sqlQuery =
				"SELECT ENTITY_ID, ENTITY_IN_OPD_ID, OPD_ID , " +
				"SOURCE_ID, SOURCE_IN_OPD_ID, SOURCE_CONNECTION_SIDE, SOURCE_CONNECTION_PARAMETER, " +
				"DESTINATION_ID, DESTINATION_IN_OPD_ID, DESTINATION_CONNECTION_SIDE, DESTINATION_CONNECTION_PARAMETER, "+
				"TEXT_COLOR, BORDER_COLOR, BACKGROUND_COLOR, IS_AUTOARRANGED  " +
				"FROM LINK_IN_OPD " +
				" WHERE PROJECT_ID = " + myProject.getProjectId();

			statement = connection.createStatement();
			rst = statement.executeQuery(sqlQuery);

			while(rst.next())
			{
				opdId = rst.getInt("OPD_ID");
				currentOpd = projectStructure.getOpd(opdId);
				myProject.setCurrentOpd(currentOpd);

				myEntry = (LinkEntry)projectStructure.getEntry(rst.getInt("ENTITY_ID"));
				myLink = (OpmProceduralLink)myEntry.getLogicalEntity();


				sourceEntry = (ConnectionEdgeEntry)projectStructure.getEntry(rst.getInt("SOURCE_ID"));
				destinationEntry = (ConnectionEdgeEntry)projectStructure.getEntry(rst.getInt("DESTINATION_ID"));

				sourceInstance = (ConnectionEdgeInstance)(sourceEntry.getInstance(new OpdKey(opdId, rst.getInt("SOURCE_IN_OPD_ID"))));
				source = sourceInstance.getConnectionEdge();

				destinationInstance = (ConnectionEdgeInstance)destinationEntry.getInstance(new OpdKey(opdId, rst.getInt("DESTINATION_IN_OPD_ID")));
				destination = destinationInstance.getConnectionEdge();

				if (myLink instanceof OpmConsumptionLink)
				{
					linkType = OpcatConstants.CONSUMPTION_LINK;
				}

				if (myLink instanceof OpmEffectLink)
				{
					linkType = OpcatConstants.EFFECT_LINK;
				}


				if (myLink instanceof OpmInstrument)
				{
					linkType = OpcatConstants.INSTRUMENT_LINK;
				}


				if (myLink instanceof OpmConditionLink)
				{
					linkType = OpcatConstants.CONDITION_LINK;
				}

				if (myLink instanceof OpmAgent)
				{
					linkType = OpcatConstants.AGENT_LINK;
				}

				if (myLink instanceof OpmResultLink)
				{
					linkType = OpcatConstants.RESULT_LINK;
				}

				if (myLink instanceof OpmInvocationLink)
				{
					linkType = OpcatConstants.INVOCATION_LINK;
				}

				if (myLink instanceof OpmInstrumentEventLink)
				{
					linkType = OpcatConstants.INSTRUMENT_EVENT_LINK;
				}

				if (myLink instanceof OpmExceptionLink)
				{
					linkType = OpcatConstants.EXCEPTION_LINK;
				}

				if (myLink instanceof OpmConsumptionEventLink)
				{
					linkType = OpcatConstants.CONSUMPTION_EVENT_LINK;
				}


				if (linkType == -1)
				{
					JOptionPane.showMessageDialog(null, " Serious internal bug occured in _loadLinkInOpdTable function \n Please contact software developers.", "Error" ,JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				}

				if (source.getParent() == destination.getParent())
				{
					tempContainer = (JLayeredPane)source.getParent();
				}
				else
				{
					tempContainer = (JLayeredPane)currentOpd.getDrawingArea();
				}

				RelativeConnectionPoint sourcePoint = new RelativeConnectionPoint(rst.getInt("SOURCE_CONNECTION_SIDE"), rst.getDouble("SOURCE_CONNECTION_PARAMETER"));
				RelativeConnectionPoint destinationPoint = new RelativeConnectionPoint(rst.getInt("DESTINATION_CONNECTION_SIDE"), rst.getDouble("DESTINATION_CONNECTION_PARAMETER"));

				myInstance = myProject.addLink(source, sourcePoint,
											  destination, destinationPoint,
												tempContainer, linkType, myEntry.getId(), rst.getInt("ENTITY_IN_OPD_ID"));

				_loadLineTable(myInstance.getLine());
				myInstance.setAutoArranged(rst.getBoolean("IS_AUTOARRANGED"));

				if (!myInstance.isAutoArranged())
				{
					myInstance.getSourceDragger().setRelativeConnectionPoint(sourcePoint);
					myInstance.getDestinationDragger().setRelativeConnectionPoint(destinationPoint);
				}

				myInstance.setBackgroundColor(new Color(rst.getInt("BACKGROUND_COLOR")));
				myInstance.setBorderColor(new Color(rst.getInt("BORDER_COLOR")));
				myInstance.setTextColor(new Color(rst.getInt("TEXT_COLOR")));
				myInstance.update();
			}

			rst.close();
			statement.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception in _loadLinkInOpdTable "+e);
		}


	}

	private void _loadGeneralRelationInOpdTable()
	{

		String sqlQuery;
		ResultSet rst;
		Statement statement;

		int relationType = -1;
		Opd currentOpd;
		long opdId;
		OpmGeneralRelation myRelation = null;
		GeneralRelationEntry myEntry;
		ConnectionEdgeEntry sourceEntry;
		ConnectionEdgeEntry destinationEntry;
		ConnectionEdgeInstance sourceInstance;
		ConnectionEdgeInstance destinationInstance;
		GeneralRelationInstance myInstance;

		OpdConnectionEdge source;
		OpdConnectionEdge destination;
		JLayeredPane tempContainer;


		try
		{

			sqlQuery =
				"SELECT ENTITY_ID, ENTITY_IN_OPD_ID, OPD_ID , " +
				"SOURCE_ID, SOURCE_IN_OPD_ID, SOURCE_CONNECTION_SIDE, SOURCE_CONNECTION_PARAMETER, " +
				"DESTINATION_ID, DESTINATION_IN_OPD_ID, DESTINATION_CONNECTION_SIDE, DESTINATION_CONNECTION_PARAMETER, "+
				"TEXT_COLOR, BORDER_COLOR, BACKGROUND_COLOR, " +
				"SOURCE_LABEL_X, SOURCE_LABEL_Y, DESTINATION_LABEL_X, DESTINATION_LABEL_Y, IS_AUTOARRANGED " +
				"FROM GENERAL_RELATION_IN_OPD " +
				" WHERE PROJECT_ID = " + myProject.getProjectId();

			statement = connection.createStatement();
			rst = statement.executeQuery(sqlQuery);

			while(rst.next())
			{
				opdId = rst.getInt("OPD_ID");
				currentOpd = projectStructure.getOpd(opdId);
				myProject.setCurrentOpd(currentOpd);

				myEntry = (GeneralRelationEntry)projectStructure.getEntry(rst.getInt("ENTITY_ID"));
				myRelation = (OpmGeneralRelation)myEntry.getLogicalEntity();


				sourceEntry = (ConnectionEdgeEntry)projectStructure.getEntry(rst.getInt("SOURCE_ID"));
				destinationEntry = (ConnectionEdgeEntry)projectStructure.getEntry(rst.getInt("DESTINATION_ID"));

				sourceInstance = (ConnectionEdgeInstance)(sourceEntry.getInstance(new OpdKey(opdId, rst.getInt("SOURCE_IN_OPD_ID"))));
				source = sourceInstance.getConnectionEdge();

				destinationInstance = (ConnectionEdgeInstance)destinationEntry.getInstance(new OpdKey(opdId, rst.getInt("DESTINATION_IN_OPD_ID")));
				destination = destinationInstance.getConnectionEdge();

				if (myRelation instanceof OpmUniDirectionalRelation)
				{
					relationType = OpcatConstants.UNI_DIRECTIONAL_RELATION;
				}

				if (myRelation instanceof OpmBiDirectionalRelation)
				{
					relationType = OpcatConstants.BI_DIRECTIONAL_RELATION;
				}


				if (relationType == -1)
				{
					JOptionPane.showMessageDialog(null, " Serious internal bug occured in _loadGeneralRelationInOpdTable function \n Please contact software developers.", "Error" ,JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				}

				if (source.getParent() == destination.getParent())
				{
                                  tempContainer = (JLayeredPane)source.getParent();
				}
				else
				{
                                  tempContainer = (JLayeredPane)currentOpd.getDrawingArea();
				}


				RelativeConnectionPoint sourcePoint = new RelativeConnectionPoint(rst.getInt("SOURCE_CONNECTION_SIDE"), rst.getDouble("SOURCE_CONNECTION_PARAMETER"));
				RelativeConnectionPoint destinationPoint = new RelativeConnectionPoint(rst.getInt("DESTINATION_CONNECTION_SIDE"), rst.getDouble("DESTINATION_CONNECTION_PARAMETER"));

				myInstance = myProject.addGeneralRelation(source, sourcePoint,
									destination, destinationPoint,
									tempContainer, relationType, myEntry.getId(), rst.getInt("ENTITY_IN_OPD_ID"));


				_loadLineTable(myInstance.getLine());

				myInstance.setAutoArranged(rst.getBoolean("IS_AUTOARRANGED"));

				if (!myInstance.isAutoArranged())
				{
					myInstance.getSourceDragger().setRelativeConnectionPoint(sourcePoint);
					myInstance.getDestinationDragger().setRelativeConnectionPoint(destinationPoint);
					myInstance.update();
				}


				((OpdRelationDragger)(myInstance.getSourceDragger())).getOpdCardinalityLabel().setLocation(rst.getInt("SOURCE_LABEL_X"), rst.getInt("SOURCE_LABEL_Y"));
				((OpdRelationDragger)(myInstance.getDestinationDragger())).getOpdCardinalityLabel().setLocation(rst.getInt("DESTINATION_LABEL_X"), rst.getInt("DESTINATION_LABEL_Y"));
				myInstance.setBackgroundColor(new Color(rst.getInt("BACKGROUND_COLOR")));
				myInstance.setBorderColor(new Color(rst.getInt("BORDER_COLOR")));
				myInstance.setTextColor(new Color(rst.getInt("TEXT_COLOR")));
				myInstance.update();

			}

			rst.close();
			statement.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception in _loadGeneralRelationInOpdTable "+e);
		}


	}


	private OpmStructuralRelation _loadRelationTable(long relationId)
	{

		String sqlQuery;
		ResultSet rst;
		Statement statement;

		int relationType;
		long sourceId , destinationId;
		String sRelType = null;
		OpmStructuralRelation myRelation = null;
		ConnectionEdgeEntry sourceEntry;
		OpmConnectionEdge sourceEdge;
		ConnectionEdgeEntry destinationEntry;
		OpmConnectionEdge destinationEdge;

		try
		{

			sqlQuery =
			  "SELECT RELATION_TYPE, SOURCE_ID, DESTINATION_ID, " +
			  "SOURCE_CARDINALITY, DESTINATION_CARDINALITY, " +
			  "FORWARD_RELATION_MEANING, BACKWARD_RELATION_MEANING "+
			  "FROM RELATION " +
			  "WHERE PROJECT_ID = " + myProject.getProjectId() +
									  " AND RELATION_ID = "+ relationId;


			statement = connection.createStatement();
			rst = statement.executeQuery(sqlQuery);

			while(rst.next())
			{

				sRelType = getString(rst, "RELATION_TYPE");


				sourceId = rst.getInt("SOURCE_ID");
				destinationId = rst.getInt("DESTINATION_ID");
				sourceEntry = (ConnectionEdgeEntry)projectStructure.getEntry(sourceId);
				sourceEdge = (OpmConnectionEdge)(sourceEntry.getLogicalEntity());
				destinationEntry = (ConnectionEdgeEntry)projectStructure.getEntry(destinationId);
				destinationEdge = (OpmConnectionEdge)(destinationEntry.getLogicalEntity());

				if (sRelType.equals("CH"))
				{
					myRelation = new OpmExhibition(relationId, " ", sourceEdge, destinationEdge );
				}

				if (sRelType.equals("AG"))
				{
					myRelation = new OpmAggregation(relationId, " " , sourceEdge, destinationEdge);
				}

				if (sRelType.equals("GN"))
				{
					myRelation = new OpmSpecialization(relationId, " " , sourceEdge, destinationEdge);
				}

				if (sRelType.equals("IN"))
				{
					myRelation = new OpmInstantination(relationId, " " , sourceEdge, destinationEdge);
				}

				if (sRelType.equals("G1"))
				{
					myRelation = new OpmUniDirectionalRelation(relationId, " " , sourceEdge, destinationEdge);
				}

				if (sRelType.equals("G2"))
				{
					myRelation = new OpmBiDirectionalRelation(relationId, " " , sourceEdge, destinationEdge);
				}

				if (myRelation == null)
				{
					JOptionPane.showMessageDialog(null, " Serious internal bug occured in _loadRelationTable " +
													"function \n Please contact software developers.",
													 "Error" ,JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				}

				myRelation.setSourceCardinality(getString(rst, "SOURCE_CARDINALITY"));
				myRelation.setDestinationCardinality(getString(rst, "DESTINATION_CARDINALITY"));
				myRelation.setForwardRelationMeaning(getString(rst, "FORWARD_RELATION_MEANING"));
				myRelation.setBackwardRelationMeaning(getString(rst, "BACKWARD_RELATION_MEANING"));
				if (myRelation instanceof OpmFundamentalRelation)
				{
					sourceEntry.addRelationSource((OpmFundamentalRelation)myRelation);
					destinationEntry.addRelationDestination((OpmFundamentalRelation)myRelation);
				}
				else
				{
					sourceEntry.addGeneralRelationSource((OpmGeneralRelation)myRelation);
					destinationEntry.addGeneralRelationDestination((OpmGeneralRelation)myRelation);
				}

			}

			rst.close();
			statement.close();
			return myRelation;
		}
		catch(Exception e)
		{
			System.out.println("Exception in _loadRelationTable "+e);
			return myRelation;
		}


	}

	private void _loadRelationInOpdTable()
	{

		String sqlQuery;
		ResultSet rst;
		Statement statement;

		int relationType = -1;
		Opd currentOpd;
		long opdId;
		OpmFundamentalRelation myRelation = null;
		OpdFundamentalRelation gRelation;
		FundamentalRelationEntry myEntry;
		FundamentalRelationInstance myInstance;
		ConnectionEdgeEntry sourceEntry;
		ConnectionEdgeEntry destinationEntry;
		ConnectionEdgeInstance sourceInstance;
		ConnectionEdgeInstance destinationInstance;
		GraphicalRelationRepresentation repr;

		OpdConnectionEdge source;
		OpdConnectionEdge destination;
		JLayeredPane tempContainer;

		try
		{

			sqlQuery =
				"SELECT ENTITY_ID, ENTITY_IN_OPD_ID, OPD_ID , " +
				"SOURCE_ID, SOURCE_IN_OPD_ID,  " +
				"DESTINATION_ID, DESTINATION_IN_OPD_ID, DESTINATION_CONNECTION_SIDE, DESTINATION_CONNECTION_PARAMETER, "+
				"DESTINATION_LABEL_X , DESTINATION_LABEL_Y, " +
				"POINT0_X, POINT0_Y, POINT1_X, POINT1_Y, " +
				"POINT2_X, POINT2_Y, POINT3_X, POINT3_Y  " +
				"FROM FUNDAMENTAL_RELATION_IN_OPD " +
				" WHERE PROJECT_ID = " + myProject.getProjectId();

			statement = connection.createStatement();
			rst = statement.executeQuery(sqlQuery);

			while(rst.next())
			{
				opdId = rst.getInt("OPD_ID");
				currentOpd = projectStructure.getOpd(opdId);
				myProject.setCurrentOpd(currentOpd);

				myEntry = (FundamentalRelationEntry)projectStructure.getEntry(rst.getInt("ENTITY_ID"));
				myRelation = (OpmFundamentalRelation)myEntry.getLogicalEntity();


				sourceEntry = (ConnectionEdgeEntry)projectStructure.getEntry(rst.getInt("SOURCE_ID"));
				destinationEntry = (ConnectionEdgeEntry)projectStructure.getEntry(rst.getInt("DESTINATION_ID"));

				sourceInstance = (ConnectionEdgeInstance)(sourceEntry.getInstance(new OpdKey(opdId, rst.getInt("SOURCE_IN_OPD_ID"))));
				source = sourceInstance.getConnectionEdge();

				destinationInstance = (ConnectionEdgeInstance)destinationEntry.getInstance(new OpdKey(opdId, rst.getInt("DESTINATION_IN_OPD_ID")));
				destination = destinationInstance.getConnectionEdge();

				if (myRelation instanceof OpmExhibition)
				{
					relationType = OpcatConstants.EXHIBITION_RELATION;
				}

				if (myRelation instanceof OpmAggregation)
				{
					relationType = OpcatConstants.AGGREGATION_RELATION;
				}

				if (myRelation instanceof OpmSpecialization)
				{
					relationType = OpcatConstants.SPECIALIZATION_RELATION;
				}

				if (myRelation instanceof OpmInstantination)
				{
					relationType = OpcatConstants.INSTANTINATION_RELATION;
				}


				if (relationType == -1)
				{
					JOptionPane.showMessageDialog(null, " Serious internal bug occured in _loadRelationInOpdTable function \n Please contact software developers.", "Error" ,JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				}

				if (source.getParent() == destination.getParent())
				{
					tempContainer = (JLayeredPane)source.getParent();
				}
				else
				{
					tempContainer = (JLayeredPane)currentOpd.getDrawingArea();
				}


				OpdKey instanceKey = new OpdKey(currentOpd.getOpdId(), (long)rst.getInt("ENTITY_IN_OPD_ID"));
				myInstance = myProject.addRelation(source, new RelativeConnectionPoint(OpdBaseComponent.CENTER, 0.0),
				destination, new RelativeConnectionPoint(rst.getInt("DESTINATION_CONNECTION_SIDE"), rst.getDouble("DESTINATION_CONNECTION_PARAMETER")),
				relationType, tempContainer, myEntry.getId() , instanceKey.getEntityInOpdId());

				repr = myInstance.getGraphicalRelationRepresentation();
				//myInstance = repr.getInstance(instanceKey);
				myInstance.getDestinationDragger().getOpdCardinalityLabel().setLocation(rst.getInt("DESTINATION_LABEL_X"), rst.getInt("DESTINATION_LABEL_Y"));
				myInstance.getPoint(0).setAbsolutesLocation(rst.getInt("POINT0_X"), rst.getInt("POINT0_Y"));
				myInstance.getPoint(1).setAbsolutesLocation(rst.getInt("POINT1_X"), rst.getInt("POINT1_Y"));
				myInstance.getPoint(2).setAbsolutesLocation(rst.getInt("POINT2_X"), rst.getInt("POINT2_Y"));
				myInstance.getPoint(3).setAbsolutesLocation(rst.getInt("POINT3_X"), rst.getInt("POINT3_Y"));


				_loadRelationRepresentation(repr);

			}


			rst.close();
			statement.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception in _loadRelationInOpdTable "+e);
			e.printStackTrace();
		}
	}

	private void _loadRelationRepresentation(GraphicalRelationRepresentation representation)
	{

		String sqlQuery;
		ResultSet rst;
		Statement statement;


		try
		{

			sqlQuery =
				"SELECT SOURCE_CONNECTION_SIDE, SOURCE_CONNECTION_PARAMETER, " +
				"X, Y, WIDTH, HEIGHT, " +
				"TEXT_COLOR, BORDER_COLOR, BACKGROUND_COLOR,  " +
				"POINT0_X, POINT0_Y, POINT1_X, POINT1_Y, " +
				"POINT2_X, POINT2_Y, POINT3_X, POINT3_Y  " +
				"FROM FUNDAMENTAL_RELATION_REPRESENTATION " +
				" WHERE PROJECT_ID = " + myProject.getProjectId() +
				" AND SOURCE_ID = " + representation.getSource().getEntity().getId() +
				" AND SOURCE_IN_OPD_ID = " + representation.getSource().getEntityInOpdId() +
				" AND OPD_ID = " + representation.getSource().getOpdId() +
				" AND RELATION_TYPE = " + representation.getType();

			statement = connection.createStatement();
			rst = statement.executeQuery(sqlQuery);

			while(rst.next())
			{
				representation.getSourceDragger().setRelativeConnectionPoint(new RelativeConnectionPoint(rst.getInt("SOURCE_CONNECTION_SIDE"), rst.getDouble("SOURCE_CONNECTION_PARAMETER")));
				representation.getSourceDragger().updateDragger();
				representation.getRelation().setLocation(rst.getInt("X"), rst.getInt("Y"));
				representation.getRelation().setSize(rst.getInt("WIDTH"), rst.getInt("HEIGHT"));
				representation.setTextColor(new Color(rst.getInt("TEXT_COLOR")));
				representation.setBorderColor(new Color(rst.getInt("BORDER_COLOR")));
				representation.setBackgroundColor(new Color(rst.getInt("BACKGROUND_COLOR")));
				representation.getPoint(0).setAbsolutesLocation(rst.getInt("POINT0_X"), rst.getInt("POINT0_Y"));
				representation.getPoint(1).setAbsolutesLocation(rst.getInt("POINT1_X"), rst.getInt("POINT1_Y"));
				representation.getPoint(2).setAbsolutesLocation(rst.getInt("POINT2_X"), rst.getInt("POINT2_Y"));
				representation.getPoint(3).setAbsolutesLocation(rst.getInt("POINT3_X"), rst.getInt("POINT3_Y"));

				representation.getRelation().repaintLines();
			}

			rst.close();
			statement.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception in _loadRepresentation "+e);
		}

	}

	private OpmObject _loadStateTable(OpmState myState)
	{
		String sqlQuery;
		ResultSet stateRst;
		Statement statement;
		ObjectEntry parentEntry;
		OpmObject parent = null;

		try
		{

			sqlQuery =
			  "SELECT OBJECT_ID , INITIAL, FINAL, MAX_TIME, MIN_TIME " +
			  "FROM STATE " +
			  "WHERE PROJECT_ID = " + myProject.getProjectId() +
			  " AND STATE_ID = " + myState.getId() + ";";

			statement = connection.createStatement();
			stateRst = statement.executeQuery(sqlQuery);


			while(stateRst.next())
			{
				myState.setFinal(stateRst.getBoolean("FINAL"));
				myState.setInitial(stateRst.getBoolean("INITIAL"));
				myState.setMinTime(getString(stateRst, "MIN_TIME"));
				myState.setMaxTime(getString(stateRst, "MAX_TIME"));
				parentEntry = (ObjectEntry)projectStructure.getEntry((long)stateRst.getInt("OBJECT_ID"));
				parent = (OpmObject)parentEntry.getLogicalEntity();
			}

			stateRst.close();
			statement.close();
			return parent;
		}
		catch(Exception e)
		{
			System.out.println("Exception in _loadStateTable "+e);
			return parent;
		}

	}

	private void _loadStateInOpdTable()
	{

		String sqlQuery;
		ResultSet rst;
		Statement statement;

		Opd currentOpd;
		long opdId;

		StateEntry myEntry;
		StateInstance myInstance;

		ObjectEntry parentEntry;
		ObjectInstance parentInstance;
		OpdObject parent;
		OpdState gState;



		try
		{

			sqlQuery =
				"SELECT ENTITY_ID, ENTITY_IN_OPD_ID, OPD_ID , " +
				"X, Y, WIDTH, HEIGHT, " +
				"TEXT_COLOR, BORDER_COLOR, BACKGROUND_COLOR,  " +
				"PARENT_OBJECT_IN_OPD_ID, IS_VISIBLE " +
				"FROM STATE_IN_OPD " +
				" WHERE PROJECT_ID = " + myProject.getProjectId();

			statement = connection.createStatement();
			rst = statement.executeQuery(sqlQuery);

			while(rst.next())
			{
				opdId = rst.getInt("OPD_ID");
				OpdKey myKey = new OpdKey(opdId, (long)rst.getInt("ENTITY_IN_OPD_ID"));
				currentOpd = projectStructure.getOpd(opdId);
				myProject.setCurrentOpd(currentOpd);
				myEntry = null;

				myEntry = (StateEntry)projectStructure.getEntry(rst.getInt("ENTITY_ID"));
				parentEntry = (ObjectEntry)projectStructure.getEntry(myEntry.getParentObjectId());
				OpdKey parentKey = new OpdKey(opdId, rst.getInt("PARENT_OBJECT_IN_OPD_ID"));
				parentInstance = (ObjectInstance)parentEntry.getInstance(parentKey);

				parent = (OpdObject)parentInstance.getThing();


				myInstance = new StateInstance(myEntry, myKey, parent, myProject);
				myEntry.addInstance(myKey, myInstance);

				myInstance.setLocation(rst.getInt("X"), rst.getInt("Y"));
				myInstance.setSize(rst.getInt("WIDTH"), rst.getInt("HEIGHT"));
				myInstance.setTextColor(new Color(rst.getInt("TEXT_COLOR")));
				myInstance.setBorderColor(new Color(rst.getInt("BORDER_COLOR")));
				myInstance.setBackgroundColor(new Color(rst.getInt("BACKGROUND_COLOR")));
				myInstance.setVisible(rst.getBoolean("IS_VISIBLE"));

			}

			rst.close();
			statement.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}


	}

	private void _loadConfigurationTable(GenericTable table)
	{

		String sqlQuery;
		ResultSet rst;
		Statement statement;

//		GenericTable config = myProject.getConfiguration();
		GenericTable config = table;

		try
		{

			sqlQuery =
				"SELECT PROPERTY_NAME, PROPERTY_VALUE " +
				"FROM " + table.getTableName() +
				" WHERE PROJECT_ID = " + myProject.getProjectId();

			statement = connection.createStatement();
			rst = statement.executeQuery(sqlQuery);

			while(rst.next())
			{
				config.setDBString(getString(rst, "PROPERTY_NAME"), getString(rst, "PROPERTY_VALUE"));
			}

			rst.close();
			statement.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception in _loadConfigurationTable "+e);
		}
	}


	private void _saveConfigurationTable(GenericTable config)
	{

		PreparedStatement prepStmt;
		String sqlQuery = "";
		String currentName, currentValue;
		for (Enumeration e = config.propertyNames();e.hasMoreElements();)
		{
			currentName = (String)e.nextElement();
			currentValue = config.getDBString(currentName);
			try
			{
				sqlQuery =
					"INSERT INTO " + config.getTableName() +
					" (PROJECT_ID, PROPERTY_NAME, PROPERTY_VALUE) " +
						"VALUES ( ?, ?, ?);";

				prepStmt = connection.prepareStatement(sqlQuery);
				prepStmt.setInt(1, (int)myProject.getProjectId());
				setString(prepStmt, 2, currentName);
				setString(prepStmt, 3, currentValue);

				if (currentValue != null && !currentValue.equals("")) {
					prepStmt.executeUpdate();
					prepStmt.close();
				}
			}
			catch(SQLException ex)
			{
				//Remarked by Eran Toch. Exceptions are forseen becuase of the fact that
				//we are saving the CONFIGURATION table twice.
				//System.out.println("Exception in _saveConfigurationTable "+ex);
			}
		}
	}


	private void _loadLineTable(StyledLine line)
	{

		String sqlQuery;
		ResultSet rst;
		Statement statement;


		try
		{

			sqlQuery =
				"SELECT X, Y " +
				"FROM LINE " +
				"WHERE PROJECT_ID = " + myProject.getProjectId()+
				" AND LINE_ID = " + line.getEntity().getId() +
				" AND OPD_ID = " + line.getKey().getOpdId() +
				" AND LINE_IN_OPD_ID = " + line.getKey().getEntityInOpdId() +
				" ORDER BY POINT_NUMBER;";

			statement = connection.createStatement();
			rst = statement.executeQuery(sqlQuery);

			int i = 0;
			while(rst.next())
			{
				line.addPoint(new Point(rst.getInt("X"), rst.getInt("Y")));
				i++;
			}


			rst.close();
			statement.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception in _loadLineTable "+e);
			e.printStackTrace();
		}
	}


	private void _saveLineTable(StyledLine line)
	{
		//insert into process table

		PreparedStatement prepStmt;
		String sqlQuery;

		Vector points = line.getPointsVector();

		for (int i=0; i<points.size(); i++)
		{
			try
			{

				sqlQuery =
				"INSERT INTO LINE (PROJECT_ID, LINE_ID, OPD_ID, LINE_IN_OPD_ID, POINT_NUMBER, X, Y) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?);";

				prepStmt = connection.prepareStatement(sqlQuery);
				DraggedPoint tPoint = (DraggedPoint)points.get(i);

				prepStmt.setInt(1, (int)myProject.getProjectId());
				prepStmt.setInt(2, (int)line.getEntity().getId());
				prepStmt.setInt(3, (int)line.getKey().getOpdId());
				prepStmt.setInt(4, (int)line.getKey().getEntityInOpdId());
				prepStmt.setInt(5, i);
				prepStmt.setInt(6, tPoint.getX());
				prepStmt.setInt(7, tPoint.getY());

				prepStmt.executeUpdate();
				prepStmt.close();
			}
			catch(Exception e)
			{
				System.out.println("Exception in _saveLineTable "+e);
				e.printStackTrace();
			}
		}

	}


	private void setString(PreparedStatement prepStmt, int parameterIndex, String myString) throws java.sql.SQLException
	{
		if (myString == null || myString.equals(""))
		{
			prepStmt.setNull(parameterIndex, Types.VARCHAR);
		}
		else
		{
			prepStmt.setString(parameterIndex, myString);
		}
	}

	private void setMemoString(PreparedStatement prepStmt, int parameterIndex, String myString) throws java.sql.SQLException
	{
		if (myString == null || myString.equals(""))
		{
			prepStmt.setString(parameterIndex, " ");
		}
		else
		{
			prepStmt.setString(parameterIndex, myString);
		}
	}


	private String getString(ResultSet rst, String columnName) throws java.sql.SQLException
	{
		String retString = rst.getString(columnName);
		if (retString == null)
		{
			retString = "";
		}
		return retString;
	}

	public boolean setCurrentDB(String dbFile)
	{
		closeConnection();
		currentDB = dbFile;
		return connectToDB();
	}

	public String getCurrentDB()
	{
		return currentDB;
	}


	public boolean validateCurrentDb()
	{
		String sqlQuery = null;
		try
		{
			sqlQuery =  "SELECT PROJECT_ID, PROJECT_NAME, CREATOR, COMPANY, VERSION, " +
						" DESCRIPTION, CREATE_DATE, MODIFIED_DATE, ROOT_OPD_ID, " +
						" MAX_ENTITY_ENTRY, MAX_OPD_ENTRY, TEMPLATE_ID, RULE_FILE_PATH FROM PROJECT;";
			connection.createStatement().executeQuery(sqlQuery);

			sqlQuery =  "SELECT PROJECT_ID, ENTITY_ID, ENTITY_NAME, PLURAL_FORM ,TABLE_NAME, " +
						" DESCRIPTION, ZOOM_IN_OPD, PARENT_THING_ID, IS_ENVIRONMENTAL, " +
						" IS_ACTIVE FROM ENTITY ;";
			connection.createStatement().executeQuery(sqlQuery);

			sqlQuery =  "SELECT PROJECT_ID, ENTITY_ID, ENTITY_IN_OPD_ID, OPD_ID, SOURCE_ID, " +
						" SOURCE_IN_OPD_ID, DESTINATION_ID, DESTINATION_IN_OPD_ID, "+
						" DESTINATION_CONNECTION_SIDE, DESTINATION_CONNECTION_PARAMETER, " +
						" DESTINATION_LABEL_X, DESTINATION_LABEL_Y, POINT0_X, POINT0_Y, POINT1_X, " +
						" POINT1_Y, POINT2_X, POINT2_Y, POINT3_X, POINT3_Y FROM FUNDAMENTAL_RELATION_IN_OPD;";
			connection.createStatement().executeQuery(sqlQuery);

			sqlQuery =  "SELECT PROJECT_ID, SOURCE_ID, SOURCE_IN_OPD_ID, OPD_ID, RELATION_TYPE, " +
						" SOURCE_CONNECTION_SIDE, SOURCE_CONNECTION_PARAMETER, X, Y, WIDTH, HEIGHT, " +
						" TEXT_COLOR, BORDER_COLOR, BACKGROUND_COLOR, POINT0_X, POINT0_Y, POINT1_X, " +
						" POINT1_Y, POINT2_X, POINT2_Y, POINT3_X, POINT3_Y "+
						" FROM FUNDAMENTAL_RELATION_REPRESENTATION;";
			connection.createStatement().executeQuery(sqlQuery);

			sqlQuery =  "SELECT PROJECT_ID, ENTITY_ID, ENTITY_IN_OPD_ID, OPD_ID, SOURCE_ID, " +
						" SOURCE_IN_OPD_ID, SOURCE_CONNECTION_SIDE, SOURCE_CONNECTION_PARAMETER, " +
						" DESTINATION_ID, DESTINATION_IN_OPD_ID, DESTINATION_CONNECTION_SIDE, "+
						" DESTINATION_CONNECTION_PARAMETER, TEXT_COLOR, BORDER_COLOR, " +
						" BACKGROUND_COLOR, SOURCE_LABEL_X, SOURCE_LABEL_Y, DESTINATION_LABEL_X, "+
						" DESTINATION_LABEL_Y, IS_AUTOARRANGED FROM GENERAL_RELATION_IN_OPD;";
			connection.createStatement().executeQuery(sqlQuery);

			sqlQuery = "SELECT PROJECT_ID, GRAPHIC_OBJECT_ID, TYPE, TEXT FROM GRAPHIC_OBJECT;";
			connection.createStatement().executeQuery(sqlQuery);

			sqlQuery =  "SELECT PROJECT_ID, LINK_ID, LINK_TYPE, SOURCE_ID, DESTINATION_ID, " +
						" MIN_REACTION_TIME, MAX_REACTION_TIME, CONDITION, PATH, INFLUENCE FROM LINK;";
			connection.createStatement().executeQuery(sqlQuery);

			sqlQuery = "SELECT PROJECT_ID, CONNECTION_ID, LINK_ID, TYPE FROM LINK_CONNECTION;";
			connection.createStatement().executeQuery(sqlQuery);

			sqlQuery =  "SELECT PROJECT_ID, ENTITY_ID, ENTITY_IN_OPD_ID, OPD_ID, SOURCE_ID, " +
						" SOURCE_IN_OPD_ID, SOURCE_CONNECTION_SIDE, SOURCE_CONNECTION_PARAMETER, " +
						" DESTINATION_ID, DESTINATION_IN_OPD_ID, DESTINATION_CONNECTION_SIDE, " +
						" DESTINATION_CONNECTION_PARAMETER, TEXT_COLOR, BORDER_COLOR, " +
						" BACKGROUND_COLOR, IS_AUTOARRANGED FROM LINK_IN_OPD;";
			connection.createStatement().executeQuery(sqlQuery);

			sqlQuery = "SELECT PROJECT_ID, OBJECT_ID, PHYSICAL, ENVIRONMENTAL, PERSISTENT, " +
						" TYPE_ORIGIN_ID, TYPE, SCOPE, KEY, INDEX_NAME, INDEX_ORDER, " +
						"INITIAL_VALUE, NO_OF_INSTANCES, ROLE FROM OBJECT;";
			connection.createStatement().executeQuery(sqlQuery);

			sqlQuery = "SELECT PROJECT_ID, OPD_ID, OPD_NAME, LAST_UPDATE, WINDOW_STATE, X, Y, " +
					   " WIDTH, HEIGHT, MAX_ENTITY_IN_OPD_ENTRY, PARENT_OPD_ID, " +
					   " MAIN_ENTITY_ID, MAIN_ENTITY_IN_OPD_ID FROM OPD;";
			connection.createStatement().executeQuery(sqlQuery);

			sqlQuery = "SELECT PROJECT_ID, SEGMENT_ID, PROCESS_ID FROM PARALLEL_EXECUTION;";
			connection.createStatement().executeQuery(sqlQuery);

			sqlQuery =  "SELECT PROJECT_ID, PROCESS_ID, PHYSICAL, ENVIRONMENTAL, MIN_TIME_ACTIVATION, " +
						" MAX_TIME_ACTIVATION, SCOPE, PROCESS_BODY,  NO_OF_INSTANCES, ROLE FROM PROCESS;";
			connection.createStatement().executeQuery(sqlQuery);

			sqlQuery =  "SELECT PROJECT_ID, RELATION_ID, RELATION_TYPE, SOURCE_ID, DESTINATION_ID, " +
						"SOURCE_CARDINALITY, DESTINATION_CARDINALITY, FORWARD_RELATION_MEANING, " +
						" BACKWARD_RELATION_MEANING FROM RELATION;";
			connection.createStatement().executeQuery(sqlQuery);

			sqlQuery =  "SELECT PROJECT_ID, STATE_ID, OBJECT_ID, INITIAL, FINAL, MIN_TIME, " +
						" MAX_TIME FROM STATE;";
			connection.createStatement().executeQuery(sqlQuery);

			sqlQuery =  "SELECT PROJECT_ID, ENTITY_ID, ENTITY_IN_OPD_ID, OPD_ID, X, Y, WIDTH, " +
						" HEIGHT, TEXT_COLOR, BORDER_COLOR, BACKGROUND_COLOR, PARENT_OBJECT_IN_OPD_ID, " +
						" IS_VISIBLE FROM STATE_IN_OPD;";
			connection.createStatement().executeQuery(sqlQuery);

			sqlQuery = "SELECT PROJECT_ID, ENTITY_ID, ENTITY_IN_OPD_ID, OPD_ID, X, Y, WIDTH, " +
						" HEIGHT, TEXT_COLOR, BORDER_COLOR, BACKGROUND_COLOR, PARENT_OBJECT_ID, " +
						" PARENT_OBJECT_IN_OPD_ID, UNFOLDING_OPD, ZOOMED_IN, " +
						" STATES_IS_AUTOARRANGED, TEXT_POSITION, " +
						" STATES_ARE_VERTICAL, LAYER FROM THING_IN_OPD;";
			connection.createStatement().executeQuery(sqlQuery);

			sqlQuery = "SELECT PROJECT_ID, PROPERTY_NAME, PROPERTY_VALUE  FROM CONFIGURATION;";
			connection.createStatement().executeQuery(sqlQuery);

		}
		catch(SQLException sqle)
		{
			return false;
		}

		return true;
	}

}//end of OpcatDB

