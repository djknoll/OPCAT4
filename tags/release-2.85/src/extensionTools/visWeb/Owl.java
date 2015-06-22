package extensionTools.visWeb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.Bag;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import exportedAPI.OpcatConstants;
import exportedAPI.opcatAPI.IEntry;
import exportedAPI.opcatAPI.ILinkEntry;
import exportedAPI.opcatAPI.IObjectEntry;
import exportedAPI.opcatAPI.IProcessEntry;
import exportedAPI.opcatAPI.IRelationEntry;
import exportedAPI.opcatAPI.IRole;
import exportedAPI.opcatAPI.IStateEntry;
import exportedAPI.opcatAPI.ISystemStructure;
import gui.metaLibraries.logic.Types;
import gui.opdProject.OpdProject;


/**
 * @author VisWeb team
 */
public class Owl
{
	private OntModel model;
	private String Lang;
	private OpdProject currentProject;
	private boolean Detailed;
	private String URL;
	private boolean ToOwl;
	private boolean IsObject;  /* true - object , false - process */
	
	public Owl(OpdProject currentProject_ ,boolean Detailed_, String URL_ , boolean ToOwl_)
	{
		//initialization 
	    this.model = ModelFactory.createOntologyModel();	
	    OntDocumentManager manager = this.model.getDocumentManager();
	    manager.loadImport(this.model,"http://t2.technion.ac.il/~sokohavi/opm.owl");
	    this.currentProject=currentProject_;
	    this.Detailed=Detailed_;
	    this.URL=URL_;
	    this.ToOwl=ToOwl_;
	    this.Lang = "RDF/XML-ABBREV";
	    this.IsObject=true;
	    // end initalization    
		this.model.setNsPrefix("Opm",this.URL);
		this.model.setNsPrefix("VisWebOntology","http://www.ie.technion.ac.il/OPM/opm.owl#");
		if (this.ToOwl == false) {
			this.Lang = "RDF/XML" ;
		}
	}
	
	 /**
	 * 
	 */
	public void CreateModel()
	 {
	 	this.CreateThings();
	 	this.CreateInstances();
	 	this.CreateStructuralRelations();
	 	this.CreateProcessLinks( );	
		
	 }
	   
	
	private void CreateThings()
	{
		Enumeration Elementiter = this.currentProject.getISystemStructure().getElements();
	    while (Elementiter.hasMoreElements())
		{
	    	IEntry entry = (IEntry)Elementiter.nextElement() ;
	    	
	    	if (entry instanceof IObjectEntry) 
	    	{
	   	    	OntClass OWLres = this.model.createClass(this.URL+((IObjectEntry)entry).getName());
	   	    	OWLres.addSuperClass(this.model.getOntClass("http://www.ie.technion.ac.il/OPM/opm.owl#OPMEntity"));
		    	this.BasicObject(entry,OWLres,this.URL,true);
			    if (this.Detailed == true) {
					this.DetailedObject(entry,OWLres,this.URL);
				}
			    		
	    	}
	    		
	    	if (entry instanceof IProcessEntry) 
		    {
	    		OntClass OWLres ;
		        OWLres = this.model.createClass(this.URL+((IProcessEntry)entry).getName());
		        OWLres.addSuperClass(this.model.getOntClass("http://www.ie.technion.ac.il/OPM/opm.owl#OPMProcess"));
		    	this.BasicProcess(entry,OWLres,this.URL,true);
		    	if (this.Detailed == true) {
					this.DetailedProcess(entry,OWLres,this.URL);
				}
		   
		    }	
	 	
	    }
	    
	}
	    
	   private void CreateInstances()
	    {
	       Enumeration Elementiter = this.currentProject.getISystemStructure().getElements();
	       while (Elementiter.hasMoreElements())
		   {
	    	IEntry entry = (IEntry)Elementiter.nextElement() ;
	    	if (entry instanceof IObjectEntry) 
	    	{
	    		OntClass OWLClass;
	    		this.IsObject=true;
	    		OWLClass = this.GetClass(entry,this.currentProject.getISystemStructure(),this.URL);
	    		
	    		if (OWLClass != null)
	    		{
	    			OntClass SourceClass = this.model.getOntClass(this.URL+((IObjectEntry)entry).getName()) ;
					SourceClass.remove() ;
	    			
	    			Individual OWLres = this.model.createIndividual(this.URL+((IObjectEntry)entry).getName(),OWLClass);
	    			this.BasicObject(entry,OWLres,this.URL,true);
	    			if (this.Detailed == true) {
						this.DetailedObject(entry,OWLres,this.URL);
					}
	    		}
	    	}
	    	if (entry instanceof IProcessEntry) 
	    	{
	    		OntClass OWLClass;
	    		this.IsObject=false;
	    		OWLClass = this.GetClass(entry,this.currentProject.getISystemStructure(),this.URL);
	    		
	    		if (OWLClass != null)
	    		{
	    			OntClass SourceClass = this.model.getOntClass(this.URL+((IProcessEntry)entry).getName()) ;
					SourceClass.remove() ;
	    			
	    			Individual OWLres = this.model.createIndividual(this.URL+((IProcessEntry)entry).getName(),OWLClass);
	    			this.BasicProcess(entry,OWLres,this.URL,true);
	    			if (this.Detailed == true) {
						this.DetailedProcess(entry,OWLres,this.URL);
					}
	    		}
	    	}
		}
	    
	  }	    
	    
	    
	    private void CreateStructuralRelations()
	    {
	     OntProperty Exhibition_Relation = null;
	     OntProperty Aggregetion_Relation = null;
	     Enumeration Elementiter = this.currentProject.getISystemStructure().getElements();
	     while (Elementiter.hasMoreElements())
		 {
	    	IEntry entry = (IEntry)Elementiter.nextElement() ;	    	
	    	if (entry instanceof IRelationEntry)
	    	{
	        	int RelationType = ((IRelationEntry)entry).getRelationType() ;
	        	if (RelationType == OpcatConstants.SPECIALIZATION_RELATION) {
					this.BasicSpecialization(entry,this.URL,this.currentProject.getISystemStructure());
				}
	
	    		if ((RelationType == OpcatConstants.UNI_DIRECTIONAL_RELATION) || 
	    				(RelationType == OpcatConstants.BI_DIRECTIONAL_RELATION))
	    		{
	    			OntProperty res ;
	    		
	    			res = this.model.createOntProperty(this.URL+((IRelationEntry)entry).getForwardRelationMeaning());
	    			res.addProperty(this.model.createProperty(this.URL+"Type"),"General Relation");
	    	    
	    			this.BasicRelation(entry,res,this.URL,this.currentProject.getISystemStructure(),this.ToOwl);
	    		}
	    		
	    		if (RelationType == OpcatConstants.EXHIBITION_RELATION)
	    		{
	    			/*if (Exhibition_Flag == false)
	    			{	
	    				Exhibition_Relation = model.createOntProperty(URL+"EXHIBITION_RELATION");
	    				Exhibition_Flag = true	;
	    			}*/
	    			
	    			Exhibition_Relation = this.model.getOntProperty("http://www.ie.technion.ac.il/OPM/opm.owl#characterises");
		    		this.BasicExhibition(entry,Exhibition_Relation,this.URL,this.currentProject.getISystemStructure(),this.ToOwl);
	    		}
	    		

	    		if (RelationType == OpcatConstants.AGGREGATION_RELATION)
	    		{
	    			/*if (Aggregetion_Flag == false)
	    			{	
	    				Aggregetion_Relation = model.createOntProperty(URL+"AGGREGATION_RELATION");
	    				Aggregetion_Flag = true ;
	    			}*/
	    			
	    			Aggregetion_Relation = this.model.getOntProperty("http://www.ie.technion.ac.il/OPM/opm.owl#consist_of");
	    			this.BasicAggregetion(entry,Aggregetion_Relation,this.URL,this.currentProject.getISystemStructure(),this.ToOwl);
	    		}
	    	}	
		}	  
    }   
	     
	   
	
	
	   
	    private void CreateProcessLinks()
	    {
	    	
	    	ISystemStructure SystemStr = this.currentProject.getISystemStructure();
	    	Enumeration Elementiter = SystemStr.getElements();
	    	
	    	
		    while (Elementiter.hasMoreElements())
			{
		    	IEntry linkEntry = (IEntry)Elementiter.nextElement() ;
		    	
		    	if (linkEntry instanceof ILinkEntry)
		    	{
		    		
		    		long destID=((ILinkEntry) linkEntry).getDestinationId();
		    		long sourceID = ((ILinkEntry) linkEntry).getSourceId();
		    		
		    		IEntry destEntry = SystemStr.getIEntry(destID);
		    		IEntry sourceEntry = SystemStr.getIEntry(sourceID);
		    		Resource sourceClass = (this.model).getOntClass(this.URL+sourceEntry.getName()) ;;
		    		Resource destClass = (this.model).getOntClass(this.URL+destEntry.getName()) ;
		    		
		    		if (sourceEntry instanceof IStateEntry)
		    		{
		    			IEntry sourceParentEntry =	((IStateEntry)sourceEntry).getParentIObjectEntry() ;
		    		    sourceClass = (this.model).getOntClass(this.URL+sourceParentEntry.getName()) ;
		    		}
		
		    		if( ((ILinkEntry) linkEntry).getLinkType()==exportedAPI.OpcatConstants.INSTRUMENT_LINK )
		    		{
		    		    //Property hasInput = model.createProperty(URL+"hasInput");
		    			Property hasInput = this.model.getOntProperty("http://www.ie.technion.ac.il/OPM/opm.owl#requires");
		    			destClass.addProperty(hasInput,sourceClass);
		    		   // ((OntModel)model).getOntClass(URL+destEntry.getName()).addProperty(hasInput,"sourceClass");
		    			 	
		    		}
		    		
		    		if( ((ILinkEntry) linkEntry).getLinkType()==exportedAPI.OpcatConstants.RESULT_LINK )
		    		{
			    		//Property hasOutput = model.createProperty(URL+"/"+"hasOutput");	
			    	    Property hasOutput = this.model.getOntProperty("http://www.ie.technion.ac.il/OPM/opm.owl#yields");
	    				sourceClass.addProperty(hasOutput,destClass);
	    		   }
		    		
		    		if( ((ILinkEntry) linkEntry).getLinkType()==exportedAPI.OpcatConstants.EFFECT_LINK )
		    		{
		    			//Property hasEffect = model.createProperty(URL+"/"+"hasEffect");
			         	Property hasEffect = this.model.getOntProperty("http://www.ie.technion.ac.il/OPM/opm.owl#affects");
			         	destClass.addProperty(hasEffect,sourceClass);
		    			 
		    		}
		    		
		    		if( ((ILinkEntry) linkEntry).getLinkType()==exportedAPI.OpcatConstants.AGENT_LINK)
		    		{
		    			//Property hasEffect = model.createProperty(URL+"/"+"hasEffect");
			         	Property hasAgent = this.model.getOntProperty("http://www.ie.technion.ac.il/OPM/opm.owl#handles");
			         	destClass.addProperty(hasAgent,sourceClass);
		    			 
		    		}
		    		
		    		if( ((ILinkEntry) linkEntry).getLinkType()==exportedAPI.OpcatConstants.INVOCATION_LINK)
		    		{
		    			//Property hasEffect = model.createProperty(URL+"/"+"hasEffect");
			         	Property hasInvocation = this.model.getOntProperty("http://www.ie.technion.ac.il/OPM/opm.owl#invokes");
			         	destClass.addProperty(hasInvocation,sourceClass);
		    			 
		    		}
		
		    		if( ((ILinkEntry) linkEntry).getLinkType()==exportedAPI.OpcatConstants.CONDITION_LINK)
		    		{
		    			//Property hasEffect = model.createProperty(URL+"/"+"hasEffect");
			         	Property hasCondition = this.model.getOntProperty("http://www.ie.technion.ac.il/OPM/opm.owl#occures_if");
			         	(destClass.addProperty(hasCondition,sourceClass)).addProperty(hasCondition,((IStateEntry)sourceEntry).getName()) ;
		    		}
		    		
		    		
	    	  }
	    	}
	    } 
	    
	    
	    
	    
	
	/**
	 * @param directoryName the name of the directory the file will be stored in
	 * @param fileName the name of the file 
	 * @effects save the file "fileName" in directory "directoryName"
	 */
	public void saveFileInDirectory (String directoryName, String fileName)
    { 
    	OutputStream OutFile=null;
		try {
			File outputFile = new File(directoryName+fileName);
			OutFile = new FileOutputStream(outputFile);
		} catch (FileNotFoundException ee) {
			// TODO Auto-generated catch block
			ee.printStackTrace();
		}
		
		this.model.write(new PrintWriter(OutFile),this.Lang);
		try {
			OutFile.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    
    }
	
	
	/**
	 * @param entry contains the reference to the opm object
	 * @param SystemStr
	 * @param URL the URL of OwlModel
	 * @return
	 */ 
	private OntClass GetClass(IEntry entry,ISystemStructure SystemStr,String URL)
	{
		Enumeration Relationiter = null ;
		if (this.IsObject == true) {
			Relationiter = ((IObjectEntry)entry).getRelationByDestination() ;
		} else {
			Relationiter = ((IProcessEntry)entry).getRelationByDestination() ;
		}	
		if (Relationiter.hasMoreElements())
    	{
			IRelationEntry RelationEntry = (IRelationEntry)Relationiter.nextElement() ;  
			if (RelationEntry.getRelationType() == OpcatConstants.INSTANTINATION_RELATION) 
			{
				long SourceID = (RelationEntry).getSourceId() ;
				IEntry SourceEntry = SystemStr.getIEntry(SourceID) ;
				OntClass SourceClass = this.model.getOntClass(URL+SourceEntry.getName()) ;
				return SourceClass ;
			}
    	}
		return null ;
	}
	
	
	
    /**
     * @param entry contains the reference to the opm object
     * @param res jena object
     * @param URL the URL of OwlModel
     * @param ToOwl if ToOwl==true the translation is to Owl
     * @effects  this function adds to res all basic data (Roles, Description, States) from the entry 
     */
    private void BasicObject(IEntry entry,/*OntClass*/Resource res,String URL,boolean ToOwl)
    {
    	res.addProperty(this.model.createProperty(URL+"Thing"),"Object");
    	
    	Enumeration Stateiter = ((IObjectEntry)entry).getStates();
		
    	if (Stateiter.hasMoreElements())
    	{
    		Bag StatesBag = this.model.createBag(URL+res.getLocalName());
		
			while (Stateiter.hasMoreElements())
			{
				IStateEntry StateEntry = (IStateEntry)Stateiter.nextElement() ;  
				StatesBag.add(StateEntry.getName());
	    	}
			res.addProperty(this.model.createProperty(URL+"state"),StatesBag);
    	}
    	
		if (ToOwl == true)
		{
			Enumeration Roleiter = ((IObjectEntry)entry).getRoles(Types.MetaLibrary);
		 	int RoleNum = 0 ;
			while (Roleiter.hasMoreElements())
			{
				RoleNum++ ;
				IRole Role = (IRole)Roleiter.nextElement() ;
			 	//Role.getLibraryName();
				((OntClass)res).addSuperClass(this.model.createResource("http://"+Role.getThingName()));
			}
		
			if ( ((IObjectEntry)entry).getDescription().compareTo("none") != 0 ) {
				((OntClass)res).addComment(this.model.createLiteral(((IObjectEntry)entry).getDescription()));
			} 
		}
    }
    
    /**
     * @param entry contains the reference to the opm process
     * @param res jena object
     * @param URL the URL of OwlModel
     * @param ToOwl if ToOwl==true the translation is to Owl
     * @effects  this function adds to res all basic data (Roles, Description) from the entry 
     */
    private void BasicProcess(IEntry entry,/*OntClass*/Resource res,String URL,boolean ToOwl)
    {  
    	res.addProperty(this.model.createProperty(URL+"Thing"),"Process");
    	
		if (ToOwl == true)
		{
			Enumeration Roleiter = ((IProcessEntry)entry).getRoles(Types.MetaLibrary);
		 	int RoleNum = 0 ;
			while (Roleiter.hasMoreElements())
			{
				RoleNum++ ;
				IRole Role = (IRole)Roleiter.nextElement() ;
			 	//Role.getLibraryName();
				((OntClass)res).addSuperClass(this.model.createResource("http://"+Role.getThingName()));
			}
		
			if ( ((IProcessEntry)entry).getDescription().compareTo("none") != 0 ) {
				((OntClass)res).addComment(this.model.createLiteral(((IProcessEntry)entry).getDescription()));
			} 
		}
    }
    
    
    /**
     * @param entry contains the reference to the opm object
     * @param res jena object
     * @param URL the URL of OwlModel
     * @effects this function adds to res all detailed data (Initial Value, Scope, Type, Essence, Origin) from the entry 
     */
    private void DetailedObject(IEntry entry,/*OntClass*/ Resource res,String URL)
    {
    	
    	Property InitialValue = this.model.createProperty(URL+"Initvalue");
		Property Scope = this.model.createProperty(URL+"Scope");
		Property Type = this.model.createProperty(URL+"Type");
		Property Essence = this.model.createProperty(URL+"Essence");
		Property Origin = this.model.createProperty(URL+"Origin");
		
		if ( ((IObjectEntry)entry).getInitialValue().compareTo("") != 0 ) {
			res.addProperty(InitialValue,((IObjectEntry)entry).getInitialValue());
		}
		
		String ScopeState = ((IObjectEntry)entry).getScope() ;
		if (ScopeState.compareTo("0") == 0) {
			res.addProperty(Scope,"Public");
		}
		if (ScopeState.compareTo("1") == 0) {
			res.addProperty(Scope,"Protected");
		}
		if (ScopeState.compareTo("2") == 0) {
			res.addProperty(Scope,"Private");
		}
		
	    if ( ((IObjectEntry)entry).getType().compareTo("") == 0 ) {
			res.addProperty(Type,"Compound Object");
		} else {
			res.addProperty(Type,((IObjectEntry)entry).getType());
		}
		
		if ( ((IObjectEntry)entry).isPhysical() == true ) {
			res.addProperty(Essence,"Physical");
		} else {
			res.addProperty(Essence,"Informatical");
		}
		
		if ( ((IObjectEntry)entry).isEnvironmental() == true ) {
			res.addProperty(Origin,"Environmental");
		} else {
			res.addProperty(Origin,"Systemic");
		}	
    }   
    
    
    /**
     * @param entry contains the reference to the opm process
     * @param res jena object
     * @param URL the URL of OwlModel
     * @effects this function adds to res all detailed data (Scope, Essence, Origin) from the entry 
     */
    private void DetailedProcess(IEntry entry,/*OntClass*/ Resource res,String URL)
    {
		Property Scope = this.model.createProperty(URL+"Scope");
		Property Essence = this.model.createProperty(URL+"Essence");
		Property Origin = this.model.createProperty(URL+"Origin");
		
		String ScopeState = ((IProcessEntry)entry).getScope() ;
		if (ScopeState.compareTo("0") == 0) {
			res.addProperty(Scope,"Public");
		}
		if (ScopeState.compareTo("1") == 0) {
			res.addProperty(Scope,"Protected");
		}
		if (ScopeState.compareTo("2") == 0) {
			res.addProperty(Scope,"Private");
		}
		
		if ( ((IProcessEntry)entry).isPhysical() == true ) {
			res.addProperty(Essence,"Physical");
		} else {
			res.addProperty(Essence,"Informatical");
		}
		
		if ( ((IProcessEntry)entry).isEnvironmental() == true ) {
			res.addProperty(Origin,"Environmental");
		} else {
			res.addProperty(Origin,"Systemic");
		}	
    }   
    
    /**
     * @param entry contains the reference to the opm process    
     * @param res jena object
     * @param URL the URL of OwlModel
     * @param SystemStr opm project Interface of type ISystemStructure
     * @param ToOwl if ToOwl==true the translation is to Owl
     * @effects creates basic general Relation (from the specific entry) which will be subclass of res
     */
    private void BasicRelation(IEntry entry,Property res,String URL,ISystemStructure SystemStr,boolean ToOwl)
    {   
    	long SourceID = ((IRelationEntry)entry).getSourceId() ;
    	long DestinationID = ((IRelationEntry)entry).getDestinationId() ;
		
    	int RelationType = ((IRelationEntry)entry).getRelationType() ;
    	
			IEntry SourceEntry = SystemStr.getIEntry(SourceID) ;
			Resource SourceClass = this.model.getOntClass(URL+SourceEntry.getName()) ;
			IEntry RangeEntry = SystemStr.getIEntry(DestinationID) ;
			Resource RangeClass = this.model.getOntClass(URL+RangeEntry.getName()) ;
			
			String SandR_Names = "_" + SourceClass.getLocalName() + "_"+ RangeClass.getLocalName() ;
			
			OntProperty SubOfRes ;
			
			if ( ( ((IRelationEntry)entry).getForwardRelationMeaning() == null ) ||
				     ( ((IRelationEntry)entry).getForwardRelationMeaning().compareTo("") == 0) ) {
				SubOfRes = this.model.createOntProperty(URL+"UNI_DIRECTIONAL_RELATION"+SandR_Names);
			} else {
				SubOfRes = this.model.createOntProperty(URL+((IRelationEntry)entry).getForwardRelationMeaning()+SandR_Names);
			}
				
			SubOfRes.addProperty(this.model.createProperty(URL+"Type"),"UniGeneral Relation");
			SubOfRes.addSuperProperty(res);
			SubOfRes.addDomain(SourceClass);
			SubOfRes.addRange(RangeClass);		
			this.CheckCardinalityString(((IRelationEntry)entry).getDestinationCardinality(),SubOfRes,URL,ToOwl);
			
			if (ToOwl== true) {
				this.DetailedRelation(entry,SubOfRes,URL);
			}
			
			
		if ( RelationType == OpcatConstants.BI_DIRECTIONAL_RELATION ) 
		{
			Resource tmpClass = RangeClass ;
			RangeClass =  SourceClass ;
			SourceClass = tmpClass ;
			
			SandR_Names = "_" + SourceClass.getLocalName() + "_"+ RangeClass.getLocalName() ;
    		
			if ( ( ((IRelationEntry)entry).getBackwardRelationMeaning() == null ) ||
			     ( ((IRelationEntry)entry).getBackwardRelationMeaning().compareTo("") == 0) ) {
				SubOfRes = this.model.createOntProperty(URL+"UNI_DIRECTIONAL_RELATION"+SandR_Names);
			} else {
				SubOfRes = this.model.createOntProperty(URL+((IRelationEntry)entry).getBackwardRelationMeaning()+SandR_Names);
			}
			
			SubOfRes.addProperty(this.model.createProperty(URL+"Type"),"UniGeneral Relation");
			SubOfRes.addSuperProperty(res);
			SubOfRes.addDomain(SourceClass);
			SubOfRes.addRange(RangeClass);
			this.CheckCardinalityString(((IRelationEntry)entry).getSourceCardinality(),SubOfRes,URL,ToOwl);
			
			if (ToOwl== true) {
				this.DetailedRelation(entry,SubOfRes,URL);
			}
			
		}		
    }
    
    
   
    /**
     * @param entry contains the reference to the opm process    
     * @param res jena object
     * @param URL the URL of OwlModel
     * @effects adds information (origin) to the res (which is a general relation)
     */
    private void DetailedRelation(IEntry entry,Property res,String URL)
    {
    	Property Origin = this.model.createProperty(URL+"Origin");
		
    	if ( ((IRelationEntry)entry).isEnvironmental() == true ) {
			res.addProperty(Origin,"Environmental");
		} else {
			res.addProperty(Origin,"Systemic");
		}	
    }
    
    
    /**
     * @param entry contains the reference to the opm process
     * @param URL the URL of OwlModel
     * @param SystemStr opm project Interface of type ISystemStructure
     * @effects gets an opm specialization relation (from the entry) and adds the range object of the relation as a subclass of the source class
     */
    private void BasicSpecialization(IEntry entry,String URL,ISystemStructure SystemStr)
    {  
    	long SourceID = ((IRelationEntry)entry).getSourceId() ;
    	long DestinationID = ((IRelationEntry)entry).getDestinationId() ;
    	
    	IEntry SourceEntry = SystemStr.getIEntry(SourceID) ;
		OntClass SourceClass = this.model.getOntClass(URL+SourceEntry.getName()) ;
		IEntry RangeEntry = SystemStr.getIEntry(DestinationID) ;
		OntClass RangeClass = this.model.getOntClass(URL+RangeEntry.getName()) ;
		
		SourceClass.addSubClass(RangeClass);
    }
      
    /**
     * @param entry contains the reference to the opm Exhibition Relation
     * @param Exhibition_Relation jena property (relation) object
     * @param URL the URL of OwlModel
     * @param SystemStr opm project Interface of type ISystemStructure
     * @param ToOwl if ToOwl==true the translation is to Owl
     * @effects creates Exhibition Relation (from the specific entry)which will be subclass of Exhibition_Relation
     */
    private void BasicExhibition(IEntry entry,Property Exhibition_Relation,String URL,ISystemStructure SystemStr,boolean ToOwl)
    {
    	long SourceID = ((IRelationEntry)entry).getSourceId() ;
    	long DestinationID = ((IRelationEntry)entry).getDestinationId() ;
    	
    	IEntry SourceEntry = SystemStr.getIEntry(SourceID) ;
		Resource SourceClass = this.model.getOntClass(URL+SourceEntry.getName()) ;
		IEntry RangeEntry = SystemStr.getIEntry(DestinationID) ;
		Resource RangeClass = this.model.getOntClass(URL+RangeEntry.getName()) ;
		
		String SandR_Names = "EXHIBITION_RELATION_" + SourceClass.getLocalName() + "_"+ RangeClass.getLocalName() ;
		
		OntProperty SubOfRes = this.model.createOntProperty(URL+SandR_Names);
    	
		SubOfRes.addProperty(this.model.createProperty(URL+"Type"),"Exhibition Relation");
		SubOfRes.addSuperProperty(Exhibition_Relation);
		SubOfRes.addDomain(SourceClass);
		SubOfRes.addRange(RangeClass);		
		this.CheckCardinalityString(((IRelationEntry)entry).getDestinationCardinality(),SubOfRes,URL,ToOwl);   	  	
    }
    
    /**
     * creates Aggregetion Relation (from the specific entry) which will be subclass of Aggregetion_Relation
     * @param entry contains the reference to the opm Aggregetion Relation
     * @param Aggregetion_Relation jena property (relation) object
     * @param URL the URL of OwlModel
     * @param SystemStr opm project Interface of type ISystemStructure
     * @param ToOwl if ToOwl==true the translation is to Owl
     * @effects 
     */
    
    private void BasicAggregetion(IEntry entry, Property Aggregetion_Relation,String URL,ISystemStructure SystemStr,boolean ToOwl)
    {
    	long SourceID = ((IRelationEntry)entry).getSourceId() ;
    	long DestinationID = ((IRelationEntry)entry).getDestinationId() ;
    	
    	IEntry SourceEntry = SystemStr.getIEntry(SourceID) ;
		Resource SourceClass = this.model.getOntClass(URL+SourceEntry.getName()) ;
		IEntry RangeEntry = SystemStr.getIEntry(DestinationID) ;
		Resource RangeClass = this.model.getOntClass(URL+RangeEntry.getName()) ;		
		String SandR_Names = "Aggregetion_RELATION_" + SourceClass.getLocalName() + "_"+ RangeClass.getLocalName() ;
		OntProperty SubOfRes = this.model.createOntProperty(URL+SandR_Names);	
		
		SubOfRes.addProperty(this.model.createProperty(URL+"Type"),"Aggregetion Relation");
		SubOfRes.addSuperProperty(Aggregetion_Relation);
		SubOfRes.addDomain(SourceClass);
		SubOfRes.addRange(RangeClass);		
		this.CheckCardinalityString(((IRelationEntry)entry).getDestinationCardinality(),SubOfRes,URL,ToOwl);   	  	
    }
           
    /**
     * @param Cardinality a string indicates the cardinality type (min/min-max/fixed)
     * @param SubOfRes the property which the cardinality refers to
     * @param URL the URL of OwlModel
     * @param ToOwl if ToOwl==true the translation is to Owl
     * @return true if the Cardinality string is valid (so we succeeded creating 
     * the cardinality restriction) and false if not
     * @effects this function gets cardinality type (min/min-max/fixed) and creates cardinality restriction for a given property 
     */
    boolean CheckCardinalityString(String Cardinality,OntProperty SubOfRes,String URL,boolean ToOwl)
	{
    	Property TmpCardinality ;
    	
    	if (Cardinality.compareTo("m") == 0 )
    	{
    		
    		if (ToOwl== true) {
				this.model.createMinCardinalityRestriction(null,SubOfRes,0);
			} else
    		{
    			TmpCardinality = this.model.createProperty(URL+"MinCardinality");
    			SubOfRes.addProperty(TmpCardinality,0);
    		}
    			
		    return true ;
    	}
		    
    	int TwoPointsPos = Cardinality.lastIndexOf("..") ;
    	if (TwoPointsPos != -1)
    	{
    		if (ToOwl== true){
    			this.model.createMinCardinalityRestriction(null,SubOfRes,Integer.parseInt(Cardinality.substring(0,TwoPointsPos)));
        		this.model.createMaxCardinalityRestriction(null,SubOfRes,Integer.parseInt(Cardinality.substring(TwoPointsPos+2,Cardinality.length())));			
    		}
    		else
    		{
    			TmpCardinality = this.model.createProperty(URL+"MinCardinality");
    			SubOfRes.addProperty(TmpCardinality,Integer.parseInt(Cardinality.substring(0,TwoPointsPos)));
    			TmpCardinality = this.model.createProperty(URL+"MaxCardinality");
    			SubOfRes.addProperty(TmpCardinality,Integer.parseInt(Cardinality.substring(TwoPointsPos+2,Cardinality.length())));
    		}
    	    return true;
    	}
    	
    	if (TwoPointsPos == -1)
    	{
    		if (ToOwl== true) {
				this.model.createCardinalityRestriction(null,SubOfRes,Integer.parseInt(Cardinality));
			} else
    		{
    			TmpCardinality = this.model.createProperty(URL+"Cardinality");
    			SubOfRes.addProperty(TmpCardinality,Integer.parseInt(Cardinality));
    		}
    	    return true;
    	}
		
	    return false;
	}
   }
