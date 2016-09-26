package com.bridgelab;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;

public class Csvfilecreator {
	static int i = 0, j = 0, k = 0;

	public void csvfilecreate(String response) {
		int temp1 = 0, temp2 = 0, temp3 = 0;
		// making object of Csvfilecreator
		Csvfilecreator js = new Csvfilecreator();
		// creating values ArrayList
		ArrayList<String> values = new ArrayList<String>();
		// creating values1 ArrayList
		ArrayList<String> values1 = new ArrayList<String>();
		JSONParser parser = new JSONParser();
		try {

			Object obj = parser.parse(response);
			// converting object into JSONObject
			JSONObject jsonObject = (JSONObject) obj;
			// covering report array into JSONArray
			JSONArray reportarray = (JSONArray) jsonObject.get("reports");
			// reading report JSONArray
			for (int j = 0; j < reportarray.size(); j++) {
				// getting first object and converting into JSONObject
				JSONObject obj3 = (JSONObject) reportarray.get(j);
				// making JSONObject of data
				JSONObject dataobject = (JSONObject) obj3.get("data");
				// making JSONArray of rows
				JSONArray rowarray = (JSONArray) dataobject.get("rows");
				// storing row JSONArray size into temp1
				temp1 = rowarray.size();
				//System.out.println(temp1);
				// reading rows JSONArray
				for (int i = 0; i < rowarray.size(); i++) {
					// getting first object and converting into JSONObject
					JSONObject rowobject = (JSONObject) rowarray.get(i);
					// making metrics JSONArray
					JSONArray metricarray = (JSONArray) rowobject.get("metrics");
					// storing metric JSONArray size into temp2
					temp2 = metricarray.size();
					//System.out.println(temp2);
					// iterating metric JSONArray
					for (int k = 0; k < metricarray.size(); k++) {
						// getting first object and converting into JSONObject
						JSONObject metricobject = (JSONObject) metricarray.get(k);
						// making values JSONArray
						JSONArray valuesarray = (JSONArray) metricobject.get("values");
						temp2 = valuesarray.size();
						if (temp2 == 1) {
							// converting JSONArray into JSONString
							String valuestring = JSONArray.toJSONString(valuesarray);
							// making subString
							valuestring = valuestring.substring(valuestring.indexOf("[") + 2,
									valuestring.indexOf("]") - 1);
							// adding into value1 ArrayList
							values1.add(valuestring);
						}

						else {
							for (int l1 = 0; l1 < valuesarray.size(); l1++) {
								// System.out.println( valuesarray.get(l1));
								values1.add((String) valuesarray.get(l1));
							}
						}
					}
					JSONArray dimensionsarray = (JSONArray) rowobject.get("dimensions");

					temp3 = dimensionsarray.size();
					for (int l = 0; l < dimensionsarray.size(); l++) {
						// adding into value ArrayList
						values.add((String) dimensionsarray.get(l));
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		js.createCsv(values, values1, temp1, temp2, temp3);

	}

	public void createCsv(ArrayList<String> list, ArrayList<String> list1, int temp1, int temp2, int temp3) {
		int k = 0;
		int p = 0;
		try {

			// if metric have 4 element
			if (temp2 == 4) {
				// initializing the boolean value
				boolean b = false;
				// creating the new csv file
				File file = new File("/home/bridgeit/Music/GAdiscription.csv");
				// checking whether file already existing or not
				if (!file.exists()) {
					b = true;
				}
				
				FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
				BufferedWriter bw11 = new BufferedWriter(fw);
				// if file doesn't exists, then create it and appending values
				if (b) {
					file.createNewFile();
					// appending columnames

					bw11.append("Androidid");
					bw11.append("^");
					bw11.append("Date");
					bw11.append("^");
					bw11.append("Sessions");
					bw11.append("^");
					bw11.append("Screenviews");
					bw11.append("^");
					bw11.append("ExitRate");
					bw11.append("^");
					bw11.append("Exit");
					bw11.append("^");
					bw11.append("EventCategory");
					bw11.append("^");
					bw11.append("Totalevents");
					bw11.append("^");
					bw11.newLine();
				}

				for (i = 0; i < temp1; i++) {

					for (j = 0; j < temp3; j++) {
						k++;
						System.out.println(list.get(k));
						bw11.append(list.get(k));
						bw11.append("^");
					}

					for (int m = 0; m < temp2; m++) {
						p++;
						System.out.println(list1.get(p));
						bw11.append(list1.get(p));
						bw11.append("^");
					}

					bw11.append("null");
					bw11.append("^");
					bw11.append("null");
					bw11.append("^");
					bw11.newLine();
				}
			}
			// if metric have 2 values
			else {

				// initializing the boolean value
				boolean b = false;
				// creating the new csv file
				File file = new File("/home/bridgeit/Music/GAdiscription.csv");
				// checking whether file already existing or not
				if (!file.exists()) {
					b = true;
				}
				System.out.println(file.getAbsoluteFile());
				FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
				BufferedWriter bw11 = new BufferedWriter(fw);
				// if file doesn't exists, then create it and appending values
				if (b) {
					file.createNewFile();
					// appending columnames
					bw11.append("AndoidId");
					bw11.append("^");
					bw11.append("EventCategory");
					bw11.append("^");
					bw11.append("Date");
					bw11.append("^");
					bw11.append("Totalevents");
					bw11.append("^");
					bw11.append("Sessions");
					bw11.append("^");
					bw11.append("Screenviews");
					bw11.append("^");
					bw11.append("ExitRate");
					bw11.append("^");
					bw11.append("Exit");
					bw11.append("^");
					bw11.newLine();
				}

				for (i = 0; i < temp1; i++) {

					for (j = 0; j < temp3; j++) {
						k++;
						System.out.println(list.get(k));
						bw11.append(list.get(k));
						bw11.append("^");
					}
					for (int m = 0; m < temp2; m++) {
						p++;

						bw11.append(list1.get(p));
						System.out.println(list1.get(p));
					}
					bw11.append("^");
					bw11.append("null");
					bw11.append("^");
					bw11.append("null");
					bw11.append("^");
					bw11.append("null");
					bw11.append("^");
					bw11.append("null");
					bw11.append("^");
					bw11.newLine();
				}

			}
		} catch (Exception e) {
		}

	}

}
