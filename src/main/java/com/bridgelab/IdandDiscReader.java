package com.bridgelab;



import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class IdandDiscReader {
	public ArrayList<String> gaid() {
		JSONParser parser = new JSONParser();
		ArrayList<String> s= new ArrayList<String>();
		ArrayList<String> dimentionarraylist = new ArrayList<String>();
		ArrayList<String> metricarraylist = new ArrayList<String>();
		ArrayList<String> dimensionfilterarraylist = new ArrayList<String>();
		try {
			Object obj = parser
					.parse(new FileReader("/home/bridgeit/Desktop/springexp/HelloAnalytics/GAreportsummary1.JSON"));
			// converting object into JSONObject
			JSONObject jsonObject = (JSONObject) obj;

			// converting into JSONObject
			JSONArray GAReportInfoarray = (JSONArray) jsonObject.get("GAReportInfo");
			// reading GAReportInfoarray
			for (int i = 0; i < GAReportInfoarray.size(); i++) {
				JSONObject GAReportInfoobject = (JSONObject) GAReportInfoarray.get(i);
				// converting GAID into string and printing same
				String gaid = (String) GAReportInfoobject.get("GAID");
				s.add(gaid);
				System.out.println("gaid=" + gaid);
				// converting GAdiscription into string and printing same
				String GAdiscription = (String) GAReportInfoobject.get("GAdiscription");
				s.add(GAdiscription);
				System.out.println("GAdiscription=" + GAdiscription);
			} 
		
			
			}
		catch(Exception e)
		{
			e.printStackTrace();
		
		}
		
		return s;
		}

}
