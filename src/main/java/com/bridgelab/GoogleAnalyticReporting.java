package com.bridgelab;

import java.util.Scanner;

public class GoogleAnalyticReporting {

	public static void main(String[] args) {
		// creating object of InputJsonReader
		InputJsonReader inputjsonreaderobject = new InputJsonReader();
		System.out.println("enter the Json file path");
		Scanner sc = new Scanner(System.in);
		// Taking path of input JSON
		String jsonfilepath = sc.next();
		// calling gaReportSummary method of GaReportSummaryReader class and
		// passing JSON path location
		inputjsonreaderobject.readInputJsonFile(jsonfilepath);

	}

}