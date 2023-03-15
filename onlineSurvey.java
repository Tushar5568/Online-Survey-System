package com.jdbc.surveysystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TakeSurvey {
	private static Connection connection = null;
	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void show(String surveyid, String surveyname)
	{
		String SID = "";
		String OriSurveyIdforCount=surveyid;
		SID = surveyid.toLowerCase();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String dbURL = "jdbc:mysql://localhost:3306/surveyjdbc";
			String username ="root";
			String password = "rootp";
			connection = DriverManager.getConnection(dbURL,username,password);
			TakeSurvey takesurvey = new TakeSurvey();
			takesurvey.surveyQA(SID,surveyname,OriSurveyIdforCount);
			}
		catch(Exception e){
			throw new RuntimeException("Something went wrong");
		}
	}
	
	public void surveyQA(String surveyId,String yoursurveyname,String OriginalSurveyId) throws SQLException
	{
		char choice = ' ';
		System.out.println("                           "+yoursurveyname.toUpperCase()+"                        \n");
		String sql = "select * from "+ surveyId;
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(sql);
		
		while(result.next())
		{
			System.out.println(result.getInt("questionno")+". "+result.getString("questions"));
			
			for(int i=1;i<=5;i++)
			{
				String option = "option"+i;
				
				if(result.getString(option)!=null)
				{
					char c=(char)(96+i);
					System.out.println(c+") "+result.getString(option));
				}
			}	
			System.out.print("Enter your Choice : ");
			choice = scanner.next().charAt(0);
			System.out.println();
		}
		int count;
		String sql1 = "Select surveytakencount from surveyresult where surveyid = '"+OriginalSurveyId+"'";
		Statement statement1 = connection.createStatement();
		ResultSet result1 = statement1.executeQuery(sql1);
		if(result1.next()) {
				count = result1.getInt("surveytakencount");
				count=count+1;
				System.out.println("\n          YOUR SURVEY HAS BEEN SUBMITTED\n");
				System.out.println("        Total Number of Surveys Submitted : "+count);
				String sql2 ="update surveyresult set surveytakencount = (?) WHERE surveyid = '"+OriginalSurveyId+"'";
				PreparedStatement preparedStatement = connection.prepareStatement(sql2);
				preparedStatement.setInt(1,count);
				preparedStatement.executeUpdate();
		}
		else {
			System.out.println("No Records Found....");
		}
		System.out.println("______________________________________________________________________________________________\n");			
	}
	
}
