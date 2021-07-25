package TestCases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

import org.testng.annotations.Test;

import FrameworkClasses.Utilities;
import MyPages.geewizFunctions;

public class geewizTestCase{
	// Instantiate class objects
	geewizFunctions clGeewizFunctions = new geewizFunctions();
	Utilities clUtilities = new Utilities();
	
	@Test
	public void test1() throws Exception {
		//instantiate variables
		String sURL = "https://www.geewiz.co.za";
		String sSearchItem = "microwave";
		String sSortBy = "Price, low to high";
		String sQuantity = "5";
		
		//creates a csv file and adds data to it
		FileWriter myWriter = new FileWriter("MyVariables.csv");
        myWriter.close();
        
		clGeewizFunctions.createCSVFile(sURL, sSearchItem, sSortBy, sQuantity);
		clGeewizFunctions.createCSVFile(sURL, "charger", "Name, Z to A", "2");
		clGeewizFunctions.createCSVFile(sURL, "mirror", "Name, A to Z", "9999");
		
		//while the csv file has data, run these test using the data
		Scanner scanner = new Scanner(new File("MyVariables.csv"));
		
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			
			//if the line is empty exit loop
			if (line.length() < 1) {
				return;
			}
			
		   	//read data from file
			String[] MyVariablesString = line.split(";");
			//set variables
			String sURLValue = MyVariablesString[0];
			String sSearchItemValue = MyVariablesString[1];
			String sSortByValue = MyVariablesString[2];
			String sQuantityValue = MyVariablesString[3];
					
			//the Test
			clGeewizFunctions.automationTestForGeewiz(sURLValue, sSearchItemValue, sSortByValue, sQuantityValue);
			//checks the cart amount
			clGeewizFunctions.checkCartAmount();
		}	
		//closes the scanner
		scanner.close();
	}
	}