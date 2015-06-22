package gui.testingScenarios;

import gui.metaLibraries.logic.MetaManager;

import java.util.HashMap;

public class Scenario {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name = "";

	private String id;

	private HashMap initialObjects;

	private HashMap finalObjects;

	private MetaManager metalibreries = new MetaManager();

	public Scenario(String name) {
		this.name = name;
		initialObjects = new HashMap();
		finalObjects = new HashMap();
		id = name;
	}

	public HashMap getFinalObjectsHash() {
		return finalObjects;
	}

	public void setFinalObjectsHash(HashMap finalObjects) {
		this.finalObjects = finalObjects;
	}

	public HashMap getInitialObjectsHash() {
		return initialObjects;
	}

	public void setInitialObjectsHash(HashMap initialObjects) {
		this.initialObjects = initialObjects;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.id = name;
	}

	public String getId() {
		return id;
	}

	public String toString() {
		return getName();
	}

	/**
	 * metalibs arraylist
	 * 
	 * @return
	 */
	public MetaManager getSettings() {
		return metalibreries;
	}

}
