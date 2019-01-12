package GUI;

import java.awt.FileDialog;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Stam {
	public static void main(String[] args)
	{
		exploitData();
	}
	
	public static String exploitData() {
		
		String jdbcUrl="jdbc:mysql://ariel-oop.xyz:3306/oop"; //?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";
		String jdbcUser="student";
		String jdbcPassword="student";
		String resultsToWrite="";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = 
					DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
			
			
			Statement statement = connection.createStatement();
			
			String s="Ex4_OOP_example1";
			double d=s.hashCode();
			//select data
			//String allCustomersQuery = "SELECT point FROM logs WHERE SomeDouble=" + d;
			String allCustomersQuery = "SELECT Points FROM logs WHERE FirstID=" + 111;
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);
			System.out.println("FirstID\t\tSecondID\tThirdID\t\tLogTime\t\t\t\tPoint\t\tSomeDouble");
			//write titles 
			resultsToWrite = "FirstID,SecondID,ThirdID,LogTime,Point,SomeDouble"+"\n";	
			while(resultSet.next())
			{
				//write content
				resultsToWrite+=resultSet.getInt("FirstID")+"," +resultSet.getInt("SecondID")+"," +
						resultSet.getInt("ThirdID")+"," +
						resultSet.getTimestamp("LogTime") +"," +
						resultSet.getDouble("Point") +"," +
						resultSet.getDouble("SomeDouble")+",";
				
				resultsToWrite+="\n";
				
				 	System.out.println(resultSet.getInt("FirstID")+"\t\t" +
						resultSet.getInt("SecondID")+"\t\t" +
						resultSet.getInt("ThirdID")+"\t\t" +
						resultSet.getTimestamp("LogTime") +"\t\t\t\t" +
						resultSet.getDouble("Point") +"\t\t" +
						resultSet.getDouble("SomeDouble"));
			}
			guiGame demo =new guiGame();
			demo.writeResults(resultsToWrite);
		}catch(Exception e) {
			
		}
		
		return resultsToWrite;
	}
}
			
			//call to function Write with resultToWrite
		


