/*
 * GUILinks: creates a dictionary that relates GUI elements to switchtable order and data table file/DSS location
 * 
 * Version 1.0
 * 
 * 2010-12-02
 *  
 */

package gov.ca.water.calgui.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class GUILinks {

	private Map<String, String> mapCtrlToTable;
	private Map<String, String> mapCtrlToTID;
	private Map<String, String> mapCtrlToswitchID;
	private Map<String, String> mapTIDToCtrl;
	private Map<String, String> mapCtrlToDReg;
	private static Logger log = Logger.getLogger(GUILinks.class.getName());

	/*
	 * GUILinks.tableNameForCtrl: Method to look up table name to use for a given control
	 * 
	 * 12/3/2010
	 */

	public String tableNameForCtrl(String ctrlID) {
		return mapCtrlToTable.get(ctrlID);
	}

	public String tableIDForCtrl(String ctrlID) {
		return mapCtrlToTID.get(ctrlID);
	}

	public String switchIDForCtrl(String ctrlID) {
		if (mapCtrlToswitchID.get(ctrlID) == null)
			System.out.println("Couldn't match control '" + ctrlID + "' in GUILinks.java");
		return mapCtrlToswitchID.get(ctrlID);
	}

	public String ctrlFortableID(String tID) {
		return mapTIDToCtrl.get(tID);
	}

	public String dRegForCtrl(String ctrlID) {
		return mapCtrlToDReg.get(ctrlID);
	}

	public int readIn(String inputFileName) {

		// Open input file

		Scanner input;
		try {
			input = new Scanner(new FileReader(inputFileName));

		} catch (FileNotFoundException e) {
			log.debug(e.getMessage());
			return -1;
		}

		mapCtrlToTable = new HashMap<String, String>();
		mapCtrlToTID = new HashMap<String, String>();
		mapCtrlToswitchID = new HashMap<String, String>();
		mapTIDToCtrl = new HashMap<String, String>();
		mapCtrlToDReg = new HashMap<String, String>();

		int lineCount = 0;
		while (input.hasNextLine()) {
			String line = input.nextLine();
			lineCount++;
			if (lineCount > 1) {
				// Parse, assuming space and/or tab-delimited
				String[] parts = line.split("[\t]+");
				if (parts.length > 0) {

					String ctrlName = parts[0].trim();
					String switchID = "";
					String TableID = "";
					String tableName = "";
					String sDReg = "";
					if (parts.length > 6) {
						tableName = parts[6].trim();
						if (parts.length > 7) {
							switchID = parts[7].trim();
						}
						if (parts.length > 8) {
							TableID = parts[8].trim();
						}
						if (parts.length > 9) {
							sDReg = parts[9].trim();
						}
					}
					mapCtrlToTable.put(ctrlName, tableName);
					if (switchID != "") {
						mapCtrlToswitchID.put(ctrlName, switchID);
					}
					if (TableID != "") {
						mapCtrlToTID.put(ctrlName, TableID);
						mapTIDToCtrl.put(TableID, ctrlName);
					}
					if (sDReg != "") {
						mapCtrlToDReg.put(ctrlName, sDReg);
					}
				}
			}
		}

		input.close();

		/*
		 * Set<Map.Entry<String,String>> s = mapCtrlToTable.entrySet();
		 * 
		 * //Move next key and value of Map by iterator Iterator<Map.Entry<String,String>> it=s.iterator();
		 * 
		 * while(it.hasNext()) { // key=value separator this by Map.Entry to get key and value Map.Entry<String,String> m
		 * =(Map.Entry<String,String>)it.next();
		 * 
		 * // getKey is used to get key of Map String key = (String) m.getKey();
		 * 
		 * System.out.println("Key :"+key+"  Value : " + mapCtrlToTable.get(key)); }
		 * 
		 * for (integer i = 1; i < 15; i++) { System.out.println("Switch: " + Integer.toString(i) + " - " +
		 * mapSwitchToCtrl.get(Integer.toString(i))); }
		 */
		return 0;
	}

}
