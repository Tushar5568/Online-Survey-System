package com.jdbc.surveysystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CreateSurvey{
	private static Scanner scanner = new Scanner(System.in);
	static final String DB_URL = "jdbc:mysql://localhost/surveyjdbc";
	static final String USER = "root";
	static final String PASS = "rootp";

	public static void main(String[] args) {
      // Open a connection
	}
   
   
	public void createYourSurvey(String surveyId) throws SQLException
	{
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);)
		{		      
			Statement stmt = conn.createStatement();
			String sql = "CREATE TABLE "+surveyId+"(questionno INTEGER not NULL, " +" questions VARCHAR(255), " + 
        		  	" option1 VARCHAR(100), " + " option2 VARCHAR(100), " +" option3 VARCHAR(100), " +
        		  	" option4 VARCHAR(100), " +" option5 VARCHAR(100), " +" PRIMARY KEY ( questionno ))"; 
			stmt.executeUpdate(sql);
			System.out.println("                            CREATE YOUR SURVEY QUESTIONS                   \n");
			System.out.print("How many question do you want to create : ");
			int noOfQuestions = Integer.parseInt(scanner.nextLine());
			for(int i=1;i<=noOfQuestions;i++)
			{
				String sql1 ="insert into "+surveyId.toLowerCase()+" (questionno, questions, option1, option2, option3, option4, option5) values (?,?,?,?,?,?,?)";
				PreparedStatement preparedStatement = conn.prepareStatement(sql1);
				preparedStatement.setInt(1,i);
				System.out.print("\nEnter Question "+i+" : ");
				preparedStatement.setString(2,scanner.nextLine());
				System.out.print("Enter no. of Options : ");
				int noOfOptions = Integer.parseInt(scanner.nextLine());
				int m=0;
				for(int j=1; j<= noOfOptions;j++)
				{
					m=j+2;
					System.out.print("Enter your Option "+j+": ");
					preparedStatement.setString(m,scanner.nextLine());
				}
				for(int k=m+1;k<=7;k++)
				{
					preparedStatement.setString(m+1,null);
					m++;
				}
				preparedStatement.executeUpdate();
			}
			System.out.println("\n                               Your Survey is Created                        \n");
			System.out.println("______________________________________________________________________________________________\n");
		} catch (SQLException e) {
         e.printStackTrace();
		} 
		
      
	}
}
