package com.bridgelab;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.bridgelab.model.GaReportModel;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.AnalyticsReportingScopes;
import com.google.api.services.analyticsreporting.v4.model.DateRange;
import com.google.api.services.analyticsreporting.v4.model.Dimension;
import com.google.api.services.analyticsreporting.v4.model.DimensionFilter;
import com.google.api.services.analyticsreporting.v4.model.DimensionFilterClause;
import com.google.api.services.analyticsreporting.v4.model.GetReportsRequest;
import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;
import com.google.api.services.analyticsreporting.v4.model.Metric;
import com.google.api.services.analyticsreporting.v4.model.ReportRequest;

public class InitializeAnalyticsReporting {
	ResponseReaderandCsvFileCreator cf = new ResponseReaderandCsvFileCreator();
	@SuppressWarnings("rawtypes")
	List[] summaryresponse = null;
	@SuppressWarnings("rawtypes")
	ArrayList dimension = null;
	@SuppressWarnings("rawtypes")
	ArrayList metric = null;
	@SuppressWarnings("rawtypes")
	ArrayList dimensionfilter1 = null;
	GaReportModel ga = new GaReportModel();
	private static final String APPLICATION_NAME = "Appystore test app";
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final String KEY_FILE_LOCATION = "/home/bridgeit/Desktop/springexp/HelloAnalytics/AppyGAReports-35a6c523765c.p12";
	private static final String SERVICE_ACCOUNT_EMAIL = "appystorereport@appygareports.iam.gserviceaccount.com";
	private static final String VIEW_ID = "ga:111820853";
	@SuppressWarnings("unused")
	private static final Object POST_INDEX_PATH = null;

	public AnalyticsReporting initializeAnalyticsReporting() throws GeneralSecurityException, IOException {

		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
				.setJsonFactory(JSON_FACTORY).setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
				.setServiceAccountPrivateKeyFromP12File(new File(KEY_FILE_LOCATION))
				.setServiceAccountScopes(AnalyticsReportingScopes.all()).build();
		// getting access token
		String refreshToken = null;
		credential.setRefreshToken(refreshToken);
		credential.refreshToken();
		if (!credential.refreshToken()) {
			throw new RuntimeException("Failed OAuth to refresh the token");
		}
		// Construct the Analytics Reporting service object.
		return new AnalyticsReporting.Builder(httpTransport, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME).build();
	}

	// reading dimension,metric and dimension filter
	public void dimensionmetricfilterList(List[] summary) {
		summaryresponse = summary;
		// retrieving the metric ArrayList
		metric = (ArrayList) summaryresponse[0];
		// retrieving the dimension ArrayList
		dimension = (ArrayList) summaryresponse[1];
		
		if(summaryresponse.length==3)
		{
			// retrieving the dimensionfilter ArrayList
				dimensionfilter1 = (ArrayList) summaryresponse[2];
				ga.setMetric(metric);
				ga.setDimension(dimension);
				ga.setDimensionfilter1(dimensionfilter1);
		}
		else
		if(summaryresponse.length==2)
		{
			ga.setMetric(metric);
			ga.setDimension(dimension);
		}
	}

	// method which give response after setting dimension metric and filter 
	public GetReportsResponse getReport(AnalyticsReporting service) throws IOException {

		// Creating the DateRange object.
		DateRange dateRange = new DateRange();
		dateRange.setStartDate("2016-08-26");
		dateRange.setEndDate("2016-08-26");

		// getting metricarry from model class 
		ArrayList metric = ga.getMetric();
		// creating object of metric ArrayList
		ArrayList<Metric> metriclist = new ArrayList<Metric>();
		metriclist.clear();
		for (int j = 0; j < metric.size(); j++) {
			// Creating the Metrics object.
			Metric metric3 = new Metric();
			// adding metric into metric ArrayList
			metriclist.add(metric3.setExpression((String) metric.get(j)));
		}

		
		// getting dimensionarray from model class 
		dimension = ga.getDimension();
		Dimension dimens;
		// Creating the Dimensions ArrayList.
		ArrayList<Dimension> dimensList = new ArrayList<Dimension>();
		dimensList.clear();
		for (int i = 0; i < dimension.size(); i++) {
			// Creating the Dimensions object.
			dimens = new Dimension();
			// adding dimension after setting name into the dimension ArrayList
			dimensList.add(dimens.setName((String) dimension.get(i)));
		}

		
		// getting dimenstionfilter from model class
		dimensionfilter1 = ga.getDimensionfilter1();
		// creating object of DimensionFilter arrayList
		ArrayList<DimensionFilter> dimensfilterList = new ArrayList<DimensionFilter>();
		dimensfilterList.clear();
		if(dimensionfilter1.size()>=1){
		for (int k = 0; k < dimensionfilter1.size(); k++) {
			// created DimensionFilter object
			DimensionFilter dimensionFilter = new DimensionFilter();
			// taking DimensionFilter and converting into String
			String dimensionfilter = (String) dimensionfilter1.get(k);
			String s = "==";
			String s1 = "=@:";
			// checking whether inside DimensionFilter exact or partial operator
			// Available
			if (dimensionfilter.contains(s)) {
				// Splitting the DimensionFilter
				String[] words = dimensionfilter.split("==");
				// adding into dimensfilterList after setting the parameter
				dimensfilterList.add(dimensionFilter.setDimensionName(words[0]).setOperator("EXACT")
						.setExpressions(Arrays.asList(words[1])));
				System.out.println("equals");
			} else if (dimensionfilter.contains(s1))

			{
				String[] words = dimensionfilter.split("=@:");
				dimensfilterList.add(dimensionFilter.setDimensionName(words[0]).setOperator("PARTIAL")
						.setExpressions(Arrays.asList(words[1])));
				System.out.println("at the rate");
			} else {
				String[] words = dimensionfilter.split("=@");
				dimensfilterList.add(dimensionFilter.setDimensionName(words[0]).setOperator("PARTIAL")
						.setExpressions(Arrays.asList(words[1])));
				System.out.println("at the rate");

			}

		}
		}
		
		
		// creating DimensionFilterClause object
		DimensionFilterClause dimensionFilterPathClause = new DimensionFilterClause();
		// making ArrayList of DimensionFilterClause
		ArrayList<DimensionFilterClause> dmfilterclauselist = new ArrayList<DimensionFilterClause>();
		// adding dimFilters to it
		dmfilterclauselist.add(dimensionFilterPathClause.setFilters(dimensfilterList).setOperator("AND"));
		

		// Creating the ReportRequest object.
		ReportRequest request = new ReportRequest()
				.setViewId(VIEW_ID)
				.setDateRanges(Arrays.asList(dateRange))
				.setMetrics(metriclist)
				.setDimensions(dimensList);
		// if dimensionfilter is available then only set it
		if(dimensionfilter1.size()>=1)
		{
				request.setDimensionFilterClauses(dmfilterclauselist);
		}
		
		// making ReportRequest ArrayList
		ArrayList<ReportRequest> requests = new ArrayList<ReportRequest>();
		requests.add(request);
		// Creating the GetReportsRequest object.
		GetReportsRequest getReport = new GetReportsRequest().setReportRequests(requests);
		// Calling the batchGet method.
		GetReportsResponse response = service.reports().batchGet(getReport).execute();
		// Returning the response.
		return response;
	}

}
