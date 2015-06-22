package gui.reqProject;

import gui.util.OpcatLogger;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

public class ReqProject {

	private String path;

	private String name;

	private String globalID = "";

	InputStream fileStream;

	HashMap hm = null;

	public ReqProject(InputStream is, String file) {
		// globalID = generateGlobalID() ;
		this.globalID = file;
		this.fileStream = is;
		this.Init("NoName", file, this.globalID);
	}

	public ReqProject(String file) {
		// globalID = generateGlobalID() ;
		try {
			this.fileStream = new BufferedInputStream(new FileInputStream(new File(
					file)), 4096);
			this.globalID = file;
			this.Init("NoName", file, this.globalID);
		} catch (Exception e) {
			OpcatLogger.logError(e);
		}
	}

	public ReqProject(String projectName, String file, String globalID) {
		try {
			this.fileStream = new BufferedInputStream(new FileInputStream(new File(
					file)), 4096);
			this.Init(projectName, file, globalID);
		} catch (Exception e) {
			OpcatLogger.logError(e);
		}
	}

	public ReqProject(String file, String globalID) {
		try {
			this.fileStream = new BufferedInputStream(new FileInputStream(new File(
					file)), 4096);
			this.Init("NoName", file, globalID);
		} catch (Exception e) {
			OpcatLogger.logError(e);
		}
	}

	private void Init(String projectName, String file, String globalID) {
		this.name = projectName;
		this.path = file;
		this.hm = new HashMap();
		this.globalID = globalID;

		// load the file here.
		/** 
		 * TODO change this to a generic loader.
		 */
		CSVLoad loader = new CSVLoad(new File(file));
		/***************************************/		
		
		Iterator rows = loader.getRowsIterator();
		while (rows.hasNext()) {
			Vector row = (Vector) rows.next();
			Req req = new Req((String) row.elementAt(3));
			req.setReqIndentNumber((String) row.elementAt(0));
			req.setName((String) row.elementAt(1));
			req.setDescription((String) row.elementAt(2));
			this.hm.put(new Long(req.getId()), req);
			try {
				req.setRisk((String) row.elementAt(4));
			} catch (Exception e) {
				req.setRisk("");
			}
		}
	}

	public void loaddummy() {
		// for now just load some dummy data
		Random generator = new Random();
		Req req;
		int endLoop;
		endLoop = 50;
		int i = 1;
		for (; i < endLoop; i++) {
			String id = Integer.toString(i);
			req = new Req(id);
			req.setDescription("a demo master requirment " + i);
			req.setName("a compound req" + i);
			req.setRisk("0.32");
			req.setReqIndentNumber(id);
			this.hm.put(id, req);
			int rand = generator.nextInt(10);
			if (rand < 0) {
				rand = 10;
			}
			for (int j = 0; j < rand; j++) {
				req = new Req(Integer.toString(j) + "." + id);
				req.setDescription("a demo requirment " + i + "." + j);
				req.setName("a sub req " + i + "." + j);
				req.setReqIndentNumber(i + "." + j);
				req.setRisk("0.32");
				this.hm.put(req.getName(), req);
			}
		}
		// /
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGlobalID() {
		return this.globalID;
	}

	public Collection values() {
		return this.hm.values();
	}

	protected String generateGlobalID() {
		Calendar rightNow = Calendar.getInstance();
		long nowMillis = rightNow.getTimeInMillis();
		Random generator = new Random(nowMillis);
		long randomLong = generator.nextLong();
		if (randomLong < 0) {
			randomLong *= -1;
		}
		String globalID = String.valueOf(randomLong) + "."
				+ String.valueOf(nowMillis);
		return globalID;
	}

	public Req getReq(long reqID) {
		return (Req) this.hm.get(new Long(reqID));
	}

	public String getPath() {
		return this.path;
	}

}
