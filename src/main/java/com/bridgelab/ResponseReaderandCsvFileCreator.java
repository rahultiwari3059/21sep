package com.bridgelab;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.bridgelab.model.GaReportModel;

public class ResponseReaderandCsvFileCreator {
	
	static int i = 0, j = 0, k = 0;
	// creating object of GaReportModel class 
	GaReportModel modelobject = new GaReportModel();
	// method which read response and pass it to csvFileCreator method
	public void responseReader(String response,ArrayList<String> gaidanddiscriptionarlist,ArrayList<String> dimension8checker) {
		// initializing rowarraysize ,metricarraysize ,dimensionarraysize
		int rowarraysize = 0, metricarraysize = 0, dimensionarraysize = 0;
		
		// making object of ResponseReaderandCsvFileCreator class
		ResponseReaderandCsvFileCreator js = new ResponseReaderandCsvFileCreator();
		// creating dimensionresponsearray  ArrayList
		ArrayList<String> dimensionresponsearray = new ArrayList<String>();
		// creating metricresponsearray ArrayList
		ArrayList<String> metricresponsearray = new ArrayList<String>();
		JSONParser parser = new JSONParser();
		try {
			//parsing and placing in Object class  
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
				rowarraysize = rowarray.size();
				// System.out.println(temp1);
				// reading rows JSONArray
				for (int i = 0; i < rowarray.size(); i++) {
					// getting first object and converting into JSONObject
					JSONObject rowobject = (JSONObject) rowarray.get(i);
					
					// making metrics JSONArray
					JSONArray metricarray = (JSONArray) rowobject.get("metrics");
					// storing metric JSONArray size into temp2
					metricarraysize = metricarray.size();
					// System.out.println(temp2);
					// iterating metric JSONArray
					for (int k = 0; k < metricarray.size(); k++) {
						// getting first object and converting into JSONObject
						JSONObject metricobject = (JSONObject) metricarray.get(k);
						// making values JSONArray
						JSONArray valuesarray = (JSONArray) metricobject.get("values");
						metricarraysize = valuesarray.size();
						if (metricarraysize == 1) {
							// converting JSONArray into JSONString
							String valuestring = JSONArray.toJSONString(valuesarray);
							// making subString
							valuestring = valuestring.substring(valuestring.indexOf("[") + 2,
									valuestring.indexOf("]") - 1);
							// adding into value1 ArrayList
							metricresponsearray.add(valuestring);
						}
						else {
							for (int l1 = 0; l1 < valuesarray.size(); l1++) {
								// System.out.println( valuesarray.get(l1));
								metricresponsearray.add((String) valuesarray.get(l1));
							}
						}
					}
					// casting into dimensions JSONArray
					JSONArray dimensionsarray = (JSONArray) rowobject.get("dimensions");
					// taking size of dimension array
					dimensionarraysize = dimensionsarray.size();
					for (int l = 0; l < dimensionsarray.size(); l++) {
						// adding into ArrayList
						dimensionresponsearray.add((String) dimensionsarray.get(l));
					}
					
				}
			}

		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("no data found");
			try
			{
			File file1 = new File("/home/bridgeit/Music/allcsv.csv");
			FileWriter filewriterobject1 = new FileWriter(file1.getAbsoluteFile(), true);
			BufferedWriter bufferedwritterobject = new BufferedWriter(filewriterobject1);
			file1.createNewFile();
			bufferedwritterobject.append(gaidanddiscriptionarlist.get(0));
			bufferedwritterobject.append("^");
			bufferedwritterobject.append(gaidanddiscriptionarlist.get(1));
			bufferedwritterobject.append("^");
			bufferedwritterobject.append("0");
			bufferedwritterobject.append("^");
			bufferedwritterobject.append("0");
			bufferedwritterobject.append("^");
			bufferedwritterobject.append("0");
			bufferedwritterobject.append("^");
			bufferedwritterobject.append("0");
			bufferedwritterobject.append("^");
			bufferedwritterobject.newLine();
			bufferedwritterobject.close();
			}
			
			catch (Exception e1) 
			{
				
				e.printStackTrace();
			}
			
		}
		// calling createcsv method
		js.createCsv(dimensionresponsearray, metricresponsearray,gaidanddiscriptionarlist, rowarraysize, metricarraysize, dimensionarraysize,dimension8checker);

	}

	public void createCsv(ArrayList<String> dimensionresponsearray, ArrayList<String> metricresponsearray,ArrayList<String>gaidanddiscriptionarlist, int rowarraysize, int metricarraysize, int dimensionarraysize,ArrayList<String> dimension8checker) {
		int k = 0,p = 0,c = 0;
		String date = null;
		String uniqueuser = null;
		int totaluniqueandroidid = 0;
		Integer total = 0;
		// creating object of hashMap for date
		HashMap<String, Integer> datehashmap = new HashMap<String, Integer>();
		// creating object of hashMap androidId
		HashMap<String, Integer> androididhashmap = new HashMap<String, Integer>();
		// creating ArrayList object for storing dates
		ArrayList<String> datearray = new ArrayList<String>();
		// creating ArrayList object for storing uniqueAndroidId
		ArrayList<String> uniqueandroidId = new ArrayList<String>();
		// creating ArrayList object for storing totalarray
		ArrayList<Integer> totalarray = new ArrayList<Integer>();
		int datehashmapcount = 1;
		int androididhashmapcount = 0;
		try {
			// calling method of IdandDiscReader and storing into ArrayList
			//ArrayList<String> s1 = id.gaid();
			// taking first element and setting in GAreportmodel
			modelobject.setGAID(gaidanddiscriptionarlist.get(0));
			// taking second element and storing in GAreportmodel
			modelobject.setGaDiscription(gaidanddiscriptionarlist.get(1));
			// initializing the boolean value
			boolean b1 = false;
			boolean b = false;
			// creating the new csv file
			File file1 = new File("/home/bridgeit/Music/allcsv.csv");
			// if file is not created yet then set b as true
			if (!file1.exists()) {
				b1 = true;
			}

			FileWriter filewriterobject1 = new FileWriter(file1.getAbsoluteFile(), true);
			BufferedWriter bufferedwritterobject = new BufferedWriter(filewriterobject1);

			if (b1) {
				file1.createNewFile();
				// appending column names
				bufferedwritterobject.append("gaid");
				bufferedwritterobject.append("^");
				bufferedwritterobject.append("gadiscription");
				bufferedwritterobject.append("^");
				bufferedwritterobject.append("Date");
				bufferedwritterobject.append("^");
				bufferedwritterobject.append("AndroisId");
				bufferedwritterobject.append("^");
				bufferedwritterobject.append("Eventcategory");
				bufferedwritterobject.append("^");
				bufferedwritterobject.append("connectiontype");
				bufferedwritterobject.append("^");
				bufferedwritterobject.append("Totalevents");
				bufferedwritterobject.append("^");
				bufferedwritterobject.append("Sessions");
				bufferedwritterobject.append("^");
				bufferedwritterobject.append("Screenviews");
				bufferedwritterobject.append("^");
				bufferedwritterobject.append("Exit");
				bufferedwritterobject.append("^");
				bufferedwritterobject.append("ExitRate");
				bufferedwritterobject.append("^");
				bufferedwritterobject.newLine();
			}
			// id dimension have 3 element and to give choice between
			// eventCategory and connection type
			if (dimensionarraysize == 3) {
				for (i = 0; i < rowarraysize; i++) {
					// appending id and gadiscription
					bufferedwritterobject.append(gaidanddiscriptionarlist.get(0));
					bufferedwritterobject.append("^");
					bufferedwritterobject.append(gaidanddiscriptionarlist.get(1));
					bufferedwritterobject.append("^");
					// appending dimension output
					for (j = 0; j < dimensionarraysize; j++) {

						// condition for taking date and setting
						if (k % 3 == 0) {
							// setting date into model class
							modelobject.setDate(dimensionresponsearray.get(k));
							// adding into HashMap according to date
							datehashmap.put(modelobject.getDate(), datehashmapcount++);
						}
						if (k % 3 == 1) {
							// setting android id in model class
							modelobject.setAndroidId(dimensionresponsearray.get(k));
							// adding into HashMap according to android id to
							// fetch number of unique user
							androididhashmap.put(modelobject.getAndroidId(), androididhashmapcount++);

						}

						// System.out.println(list.get(k));
						bufferedwritterobject.append(dimensionresponsearray.get(k));
						bufferedwritterobject.append("^");
						// if dimension8 is there in DimensionList then put
						// space
						if (dimension8checker.get(2) == "true") {
							if (k % 3 == 1) {
								bufferedwritterobject.append(" ");
								bufferedwritterobject.append("^");
							}
						}
						k++;
					}
					// if dimension8 is not there then put space after
					if (!dimension8checker.get(2).equals("true")) {
						bufferedwritterobject.append(" ");
						bufferedwritterobject.append("^");
					}
					// appending metric value
					for (int m = 0; m < metricarraysize; m++) {

						bufferedwritterobject.append(metricresponsearray.get(p));
						p++;
						bufferedwritterobject.append("^");
					}
					bufferedwritterobject.append(" ");
					bufferedwritterobject.append("^");
					bufferedwritterobject.append(" ");
					bufferedwritterobject.append("^");
					bufferedwritterobject.append(" ");
					bufferedwritterobject.append("^");
					bufferedwritterobject.append(" ");
					bufferedwritterobject.append("^");
					bufferedwritterobject.newLine();

				}
				bufferedwritterobject.close();
				System.out.println(androididhashmap.size());
				// taking number of unique android id
				totaluniqueandroidid = androididhashmap.size();
				uniqueuser = String.valueOf(totaluniqueandroidid);
				for (Entry<String, Integer> m1 : androididhashmap.entrySet()) {
					// taking value
					total = m1.getValue();
					uniqueandroidId.add(m1.getKey());
				}
				for (Entry<String, Integer> m : datehashmap.entrySet()) {
					// putting key and value into date and total
					date = m.getKey();
					total = m.getValue();
					// adding into date ArrayList
					datearray.add(m.getKey());
					totalarray.add(m.getValue());
					// printing key value pair
					System.out.println(m.getKey() + " " + m.getValue());
				}
				
			}
			// if metric having 4 element and 2 dimension
			else {
				if (metricarraysize == 4 && dimensionarraysize == 2) {
					//r = 0;
					for (i = 0; i < rowarraysize; i++) {
						// appending id and gadiscription
						bufferedwritterobject.append(gaidanddiscriptionarlist.get(0));
						bufferedwritterobject.append("^");
						bufferedwritterobject.append(gaidanddiscriptionarlist.get(1));
						bufferedwritterobject.append("^");
						// appending dimension output
						for (j = 0; j < dimensionarraysize; j++) {
							if (k % 2 == 0) {
								// setting date into model class
								modelobject.setDate(dimensionresponsearray.get(k));
								// adding into HashMap according to date
								datehashmap.put(modelobject.getDate(), datehashmapcount++);
							}
							if (k % 2 == 1) {
								// setting android id in model class
								modelobject.setAndroidId(dimensionresponsearray.get(k));
								// adding into HashMap according to android id
								// to fetch number of unique user
								androididhashmap.put(modelobject.getAndroidId(), androididhashmapcount++);

							}
							// appending value
							bufferedwritterobject.append(dimensionresponsearray.get(k));
							bufferedwritterobject.append("^");
							k++;
						}
						bufferedwritterobject.append(" ");
						bufferedwritterobject.append("^");
						bufferedwritterobject.append(" ");
						bufferedwritterobject.append("^");
						bufferedwritterobject.append(" ");
						bufferedwritterobject.append("^");
						// appending metric value
						for (int m = 0; m < metricarraysize; m++) {

							// System.out.println(list1.get(p));
							bufferedwritterobject.append(metricresponsearray.get(p));
							p++;
							bufferedwritterobject.append("^");
						}
						bufferedwritterobject.newLine();
					}
					bufferedwritterobject.close();
					System.out.println(androididhashmap.size());
					// taking number of unique android id
					totaluniqueandroidid = androididhashmap.size();
					uniqueuser = String.valueOf(totaluniqueandroidid);
					for (Entry<String, Integer> m1 : androididhashmap.entrySet()) {
						// taking value
						total = m1.getValue();
						uniqueandroidId.add(m1.getKey());
					}
					for (Entry<String, Integer> m : datehashmap.entrySet()) {
						date = m.getKey();
						total = m.getValue();
						datearray.add(m.getKey());
						totalarray.add(m.getValue());
						// printing key value pair
						System.out.println(m.getKey() + " " + m.getValue());
					}

					for (int k1 = 0; k1 < uniqueandroidId.size(); k1++) {
						// System.out.println(uniqueandroidId.get(k1));

					}
				}

				else {

					if (dimensionarraysize == 2) {
						for (i = 0; i < rowarraysize; i++) {
							// appending id and gadiscription
							bufferedwritterobject.append(gaidanddiscriptionarlist.get(0));
							bufferedwritterobject.append("^");
							bufferedwritterobject.append(gaidanddiscriptionarlist.get(1));
							bufferedwritterobject.append("^");
							// appending dimension output
							for (j = 0; j < dimensionarraysize; j++) {
								if (k % 2 == 0) {
									modelobject.setDate(dimensionresponsearray.get(k));
									datehashmap.put(modelobject.getDate(), datehashmapcount++);
								}
								if (k % 2 == 1) {
									modelobject.setAndroidId(dimensionresponsearray.get(k));
									androididhashmap.put(modelobject.getAndroidId(), androididhashmapcount++);

								}
								// appending value
								bufferedwritterobject.append(dimensionresponsearray.get(k));
								k++;
								bufferedwritterobject.append("^");
							}
							bufferedwritterobject.append(" ");
							bufferedwritterobject.append("^");
							bufferedwritterobject.append(" ");
							bufferedwritterobject.append("^");
							// appending metric value
							for (int m = 0; m < metricarraysize; m++) {

								// System.out.println(list1.get(p));
								bufferedwritterobject.append(metricresponsearray.get(p));
								p++;
								bufferedwritterobject.append("^");
							}
							bufferedwritterobject.append(" ");
							bufferedwritterobject.append("^");
							bufferedwritterobject.append(" ");
							bufferedwritterobject.append("^");
							bufferedwritterobject.append(" ");
							bufferedwritterobject.append("^");
							bufferedwritterobject.append(" ");
							bufferedwritterobject.append("^");
							bufferedwritterobject.newLine();
						}
						bufferedwritterobject.close();
						System.out.println(androididhashmap.size());
						// taking number of unique android id
						totaluniqueandroidid = androididhashmap.size();
						uniqueuser = String.valueOf(totaluniqueandroidid);
						for (Entry<String, Integer> m1 : androididhashmap.entrySet()) {
							// taking value
							total = m1.getValue();
							uniqueandroidId.add(m1.getKey());
						}
						for (Entry<String, Integer> m : datehashmap.entrySet()) {
							date = m.getKey();
							total = m.getValue();
							datearray.add(m.getKey());
							totalarray.add(m.getValue());
							// printing key value pair
							System.out.println(m.getKey() + " " + m.getValue());
						}

						for (int k1 = 0; k1 < uniqueandroidId.size(); k1++) {
							// System.out.println(uniqueandroidId.get(k1));

						}
					}
				}

			}
			File file = new File("/home/bridgeit/Music/summaryreport.csv");
			if (!file.exists()) {
				b = true;
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			if (b) {
				file.createNewFile();
				// appending id and gadiscription
				bw.append("gaid");
				bw.append("^");
				bw.append("gadiscription");
				bw.append("^");
				bw.append("totaluniqueandroidid");
				bw.append("^");
				// appending date in summary response
				for (int j1 = 0; j1 < datearray.size(); j1++) {
					bw.append(datearray.get(j1));
					bw.append("^");
				}
				bw.newLine();
			}
			if (true) {
				bw.append(modelobject.getGAID());
				bw.append("^");
				bw.append(modelobject.getGaDiscription());
				bw.append("^");
				// appending unique user total value
				bw.append(uniqueuser);
				bw.append("^");
				// appending total values
				for (int j2 = 0; j2 < datearray.size(); j2++) {
					bw.append(totalarray.get(j2).toString());
					bw.append("^");
				}
				bw.newLine();

			}
			bw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
