package com.bridgelab;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class IdandDiscReader {
	String flag;
	String dimension8var = "ga:dimension8";

	public ArrayList<String> gaid() {
		JSONParser parser = new JSONParser();
		ArrayList<String> s = new ArrayList<String>();
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
				JSONArray dimensionsarray = (JSONArray) GAReportInfoobject.get("dimension");

				// reading the dimension array
				for (int j = 0; j < dimensionsarray.size(); j++) {
					if ("ga:dimension8".equals((String) dimensionsarray.get(j))) {
						flag = "true";
						//System.out.println("true");
						s.add(flag);
					}
					/*else
					{
						flag = "false";
						s.add(flag);
					}*/
					// adding into DimensionFilter ArrayList

				}
				{
					flag = "false";
					s.add(flag);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		return s;
	}

}
