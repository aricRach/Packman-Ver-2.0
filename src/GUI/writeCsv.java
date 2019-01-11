package GUI;

import java.io.IOException;
import java.util.ArrayList;
import GIS.fruit;
import GIS.packman;
import Map.converts;

/**
 * This class create String that will contains ArrayLists (Fruits and Packmans) data
 * @author Aric and Tal 
 */

public class writeCsv {
	
	/**
	 * This function receive 2 ArrayLists  
	 * @param ff ArrayList of fruits
	 * @param pp ArrayList of packmans
	 * @return String that contains all csv Data 
	 * @throws IOException
	 */
	public static String Write(String s) throws IOException {
	
		
		String str = "Type,id,Lat,Lon,Alt,Speed/Weight,Radius,"+"\n";
			
			str+="P,"+43+","+22+","+11+","+98+","+54+","+43+","+"\n";
		
		return str;
	}
}