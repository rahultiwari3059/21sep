package com.bridgelab;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Csvfilecreator1 {
	static int i = 0, j = 0, k = 0;

	public static void main(String[] args) {
		int temp1 = 0, temp2 = 0, temp3 = 0;
		Csvfilecreator1 js = new Csvfilecreator1();
		ArrayList<String> values = new ArrayList<String>();
		ArrayList<String> values1 = new ArrayList<String>();
		JSONParser parser = new JSONParser();
		try {

			Object obj = parser
					.parse(new FileReader("/home/bridgeit/Desktop/springexp/HelloAnalytics/subsciptionpage.JSON"));
			// converting object into JSONObject
			JSONObject jsonObject = (JSONObject) obj;
			// covering report array into JSONArray
			JSONArray reportarray = (JSONArray) jsonObject.get("reports");
			// System.out.println(reportarray);
			// System.out.println(reportarray.size());
			for (int j = 0; j < reportarray.size(); j++) {
				JSONObject obj3 = (JSONObject) reportarray.get(j);
				// System.out.println(obj3);
				JSONObject dataobject = (JSONObject) obj3.get("data");
				// System.out.println(dataobject);
				JSONArray rowarray = (JSONArray) dataobject.get("rows");
				// System.out.println(rowarray);
				// System.out.println(rowarray.size());
				temp1 = rowarray.size();
				// System.out.println(temp1);
				for (int i = 0; i < rowarray.size(); i++) {

					JSONObject rowobject = (JSONObject) rowarray.get(i);
					// System.out.println(rowobject);
					JSONArray metricarray = (JSONArray) rowobject.get("metrics");
					// System.out.println(metricarray);
					for (int k = 0; k < metricarray.size(); k++) {
						JSONObject metricobject = (JSONObject) metricarray.get(k);
						// System.out.println(metricobject);
						JSONArray valuesarray = (JSONArray) metricobject.get("values");
						temp2 = valuesarray.size();
						// System.out.println(temp2);
						for (int l1 = 0; l1 < valuesarray.size(); l1++) {
							// System.out.println( valuesarray.get(l1));
							values1.add((String) valuesarray.get(l1));
						}
					}
					JSONArray dimensionsarray = (JSONArray) rowobject.get("dimensions");
					// System.out.println("dimension="+dimensionsarray.size());
					temp3 = dimensionsarray.size();
					// System.out.println(temp3);
					for (int l = 0; l < dimensionsarray.size(); l++) {
						// System.out.println(dimensionsarray.get(l));
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
			// initializing the boolean value
			boolean b = false;
			// creating the new csv file
			File file = new File("/home/bridgeit/Music/subsciptionpage.csv");
			// checking whether file already existing or not
			if (!file.exists()) {
				b = true;
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			// if file doesn't exists, then create it and appending values
			if (b) {
				file.createNewFile();
				bw.append("Androidid");
				bw.append("^");
				bw.append("Date");
				bw.append("^");
				bw.append("sessions");
				bw.append("^");
				bw.append("screenviews");
				bw.append("^");
				bw.append("exitRate");
				bw.append("^");
				bw.append("exit");
				bw.append("^");
				bw.newLine();
			}
			for (i = 0; i < temp1; i++) {

				for (j = 0; j < temp3; j++) {
					k++;
					// appending dimention in csv file
					bw.append(list.get(k));
					bw.append("^");
					System.out.println(list.get(k));

				}
				for (int m = 0; m < temp2; m++) {
					p++;
					// appending metrics in csv file
					bw.append(list1.get(p));
					bw.append("^");
					System.out.println(list1.get(p));

				}
				bw.newLine();
			}

		} catch (Exception e) {
		}
		// if operation is completed then printing done
		System.out.println("Done");
	}
}