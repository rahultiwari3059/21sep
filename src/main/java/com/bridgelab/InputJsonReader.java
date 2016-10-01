package com.bridgelab;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;

public class InputJsonReader {
	// creating object of InitializeAnalyticsReporting class
	InitializeAnalyticsReporting initializeobject = new InitializeAnalyticsReporting();
	// creating object of ResponseReaderandCsvFileCreator class
	ResponseReaderandCsvFileCreator responsereadercsvfilecreatorobject = new ResponseReaderandCsvFileCreator();
	// initializing dimension8available
	String dimension8available = null;

	// creating method to read metric dimension and filter from input JSONfile
	// and to create CSv
	// file
	public void readInputJsonFile(String jsonfilepath) {
		// creating object of JSONParser to parse the input JSON file
		JSONParser parser = new JSONParser();
		// creating ArrayList object to save dimension element
		ArrayList<String> dimentionArrayList = new ArrayList<String>();
		// creating ArrayList object to save metric element
		ArrayList<String> metricarraylist = new ArrayList<String>();
		// creating ArrayList object to save DimensionFilter element
		ArrayList<String> dimensionfilterarraylist = new ArrayList<String>();
		// creating ArrayList object to save Gaid and Gadiscription element
		ArrayList<String> gaidanddiscriptionarlist = new ArrayList<String>();
		// // creating ArrayList object to check whether Dimension8 is available
		// or not
		ArrayList<String> dimension8checker = new ArrayList<String>();

		try {
			// parsing and casting to Object
			Object obj = parser.parse(new FileReader(jsonfilepath));
			// casting object into JSONObject
			JSONObject jsonObject = (JSONObject) obj;
			// casting into JSONObject
			JSONArray gareportinfoarray = (JSONArray) jsonObject.get("GAReportInfo");

			// reading GAReportInfoarray
			for (int i = 0; i < gareportinfoarray.size(); i++) {
				// clearing all ArrayList so that every time it can read JSON
				dimentionArrayList.clear();
				metricarraylist.clear();
				dimensionfilterarraylist.clear();
				gaidanddiscriptionarlist.clear();
				dimension8checker.clear();
				
				JSONObject gareportinfoobject = (JSONObject) gareportinfoarray.get(i);
				// converting GAID into string and printing same
				String gaid = (String) gareportinfoobject.get("GAID");
				// printing gaid
				System.out.println("gaid=" + gaid);
				// adding into gaidanddiscription ArrayList
				gaidanddiscriptionarlist.add(gaid);
				// converting GAdiscription into string and printing same
				String gadiscription = (String) gareportinfoobject.get("GAdiscription");
				System.out.println("GAdiscription=" + gadiscription);
				gaidanddiscriptionarlist.add(gadiscription);
				
				// making metric array
				JSONArray metricarray = (JSONArray) gareportinfoobject.get("metric");
				// reading the metric array
				for (int k = 0; k < metricarray.size(); k++) {
					// adding into metric ArrayList
					metricarraylist.add((String) metricarray.get(k));
				}
				
				// making dimension JSONArray
				JSONArray dimensionsarray = (JSONArray) gareportinfoobject.get("dimension");
				// reading the dimension array
				for (int j = 0; j < dimensionsarray.size(); j++) {
					dimentionArrayList.add((String) dimensionsarray.get(j));
					// It is just to create space in CSv file if dimension8 is
					// available
					if ("ga:dimension8".equals((String) dimensionsarray.get(j))) {
						// if dimension8 is available then assign true
						dimension8available = "true";
						// adding into dimension8checker ArrayList
						dimension8checker.add(dimension8available);
					}
					{
						// else adding false into dimension8checker ArrayList
						dimension8available = "false";
						dimension8checker.add(dimension8available);
					}
				}

				
				// Casting DimensionFilter into JSONArray
				JSONArray dimensionfilterarray = (JSONArray) gareportinfoobject.get("dimensionfilter");
				// checking if dimensionfilter is not there then send only
				// metric and dimensionlist
				if (dimensionfilterarray == null) {
					initializeobject.dimensionmetricfilterList(new List[] { metricarraylist, dimentionArrayList });
				}
				//
				else {
					for (int l = 0; l < dimensionfilterarray.size(); l++) {
						// adding into DimensionFilter ArrayList
						dimensionfilterarraylist.add((String) dimensionfilterarray.get(l));
					}
					// calling dimensionmetricfilterList method of
					// InitializeAnalyticsReporting class by passing all
					// ArrayList of dimension metric and filter
					initializeobject.dimensionmetricfilterList(
							new List[] { metricarraylist, dimentionArrayList, dimensionfilterarraylist });
				}

				// 
				try {
					// calling initializeAnalyticsReporting method of
					// InitializeAnalyticsReporting class to initialize all
					// credential
					AnalyticsReporting service = initializeobject.initializeAnalyticsReporting();
					// calling getReport method to get response
					GetReportsResponse response = initializeobject.getReport(service);
					// printing the response
					System.out.println(response);
					// assigning response into variable responsejson of
					// GetReportsResponse type
					GetReportsResponse responsejson = response;

					// calling csvFileCreator to create CSv file by passing
					// response and id and dimension
					responsereadercsvfilecreatorobject.responseReader(responsejson.toString(), gaidanddiscriptionarlist, dimension8checker);
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			
			
		}

	}

}
