package com.bridgelab;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.util.SystemPropertyUtils;

import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;

public class Csvfilecreator {
	static int i = 0, j = 0, k = 0;
	IdandDiscReader id = new IdandDiscReader();

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
				// System.out.println(temp1);
				// reading rows JSONArray
				for (int i = 0; i < rowarray.size(); i++) {
					// getting first object and converting into JSONObject
					JSONObject rowobject = (JSONObject) rowarray.get(i);
					// making metrics JSONArray
					JSONArray metricarray = (JSONArray) rowobject.get("metrics");
					// storing metric JSONArray size into temp2
					temp2 = metricarray.size();
					// System.out.println(temp2);
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
		int c = 0;
		try {
			System.out.println("hello");
			ArrayList<String> s1 = id.gaid();

			// initializing the boolean value
			boolean b1 = false;

			// creating the new csv file

			File file1 = new File("/home/bridgeit/Music/CSVfile3.csv");
			// checking whether file already existing or not

			// if metric have 4 element
			
			
			
			//File file = new File("/home/bridgeit/Music/CSVfile4.csv");
			/*if (temp2 == 4) {
				boolean b = false;

				if (!file.exists()) {
					b = true;
				}

				FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
				BufferedWriter bw11 = new BufferedWriter(fw);

				// if file doesn't exists, then create it and appending values
				if (b) {
					file.createNewFile();
					// appending column names
					bw11.append("gaid");
					bw11.append("^");
					bw11.append("gadiscription");
					bw11.append("^");
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

					bw11.newLine();
				}

				for (i = 0; i < temp1; i++) {
					bw11.append(s1.get(0));
					bw11.append("^");
					bw11.append(s1.get(1));
					bw11.append("^");
					// appending dimension output
					for (j = 0; j < temp3; j++) {

						System.out.println(list.get(k));

						bw11.append(list.get(k));
						k++;
						bw11.append("^");
					}
					// appending metric value
					for (int m = 0; m < temp2; m++) {

						System.out.println(list1.get(p));
						bw11.append(list1.get(p));
						p++;
						bw11.append("^");
					}
					
					bw11.newLine();

				}
				bw11.close();
			}

			// if metric have 1 values
			else {
*/
				if (!file1.exists()) {
					b1 = true;
				}

				FileWriter fw1 = new FileWriter(file1.getAbsoluteFile(), true);
				BufferedWriter bw1 = new BufferedWriter(fw1);

				if (b1) {
					file1.createNewFile();
					// appending column names
					bw1.append("gaid");
					bw1.append("^");
					bw1.append("gadiscription");
					bw1.append("^");
					bw1.append("Date");
					bw1.append("^");
					bw1.append("AndroisId");
					bw1.append("^");
					bw1.append("Eventcategory/connectiontype");
					bw1.append("^");
					bw1.append("Totalevents");
					bw1.append("^");
					bw1.append("Sessions");
					bw1.append("^");
					bw1.append("Screenviews");
					bw1.append("^");
					bw1.append("Exit");
					bw1.append("^");
					bw1.append("ExitRate");
					bw1.append("^");
					bw1.newLine();
				}
				// id dimension have 3 element
				if (temp3 == 3) {
					for (i = 0; i < temp1; i++) {
						c++;
						bw1.append(s1.get(0));
						bw1.append("^");
						bw1.append(s1.get(1));
						bw1.append("^");
						// appending dimension output
						for (j = 0; j < temp3; j++) {

							System.out.println(list.get(k));

							bw1.append(list.get(k));
							k++;
							bw1.append("^");
						}
						// appending metric value
						for (int m = 0; m < temp2; m++) {

							System.out.println(list1.get(p));
							bw1.append(list1.get(p));
							p++;
							bw1.append("^");
						}
						bw1.append(" ");
						bw1.append("^");
						bw1.append(" ");
						bw1.append("^");
						bw1.append(" ");
						bw1.append("^");
						bw1.append(" ");
						bw1.append("^");
						bw1.newLine();

					}
				} 
				// if dimension having 2 element 
				else {

					for (i = 0; i < temp1; i++) {

						bw1.append(s1.get(0));
						bw1.append("^");
						bw1.append(s1.get(1));
						bw1.append("^");
						// appending dimension output
						for (j = 0; j < temp3; j++) {

							System.out.println(list.get(k));

							bw1.append(list.get(k));
							k++;
							bw1.append("^");
						}
						bw1.append(" ");
						bw1.append("^");
						bw1.append(" ");
						bw1.append("^");
						// appending metric value
						for (int m = 0; m < temp2; m++) {

							System.out.println(list1.get(p));
							bw1.append(list1.get(p));
							p++;
							bw1.append("^");
						}
						bw1.newLine();

					}
				}

				bw1.close();
			//}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
