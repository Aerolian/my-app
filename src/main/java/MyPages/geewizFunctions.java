package MyPages;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import FrameworkClasses.ReusableFunctions;
import FrameworkClasses.Utilities;
import FrameworkClasses.driverSetup;

public class geewizFunctions extends driverSetup{
		//Instantiate Selenium Functions and Utilities
		ReusableFunctions sfSelenium = new ReusableFunctions();
		Utilities uUtilities = new Utilities();
		//global variables
		
		//navigate to URL 
		public void navigateToURL(String pURL) throws Exception {
			//Navigate to URL
			driver.get(pURL);		 
		}
		
		public void createCSVFile(String pURL, String pSearchItem, String pSortBy, String pQuantity) throws IOException {		    
		    try
		    {
		    	//creates a file if it doesn't exist and appends the data
		        BufferedWriter myWriter = new BufferedWriter(new FileWriter("MyVariables.csv", true));
		        //appends the string to the file
		        myWriter.write(pURL + ";" + pSearchItem + ";" + pSortBy + ";" + pQuantity + ";");
		        myWriter.newLine();
		        myWriter.close();
		    }
		    catch(IOException ioe)
		    {
		        System.err.println("IOException: " + ioe.getMessage());
		    }
		}
		
		public void automationTestForGeewiz(String pURL, String pSearchItem, String pSortBy, String pQuantity) throws Exception {
			
		    //navigate to URL
			navigateToURL(pURL);
			//enter data in search box
			sfSelenium.populateInputField(By.xpath("/html[1]/body[1]/main[1]/header[1]/div[1]/div[2]/div[1]/div[1]/div[3]/div[1]/form[1]/input[2]"), pSearchItem);
			//clicks search
			driver.findElement(By.xpath("/html[1]/body[1]/main[1]/header[1]/div[1]/div[2]/div[1]/div[1]/div[3]/div[1]/form[1]/button[1]/i[1]")).click();
			//click on dropdown to expand options
			driver.findElement(By.xpath("/html[1]/body[1]/main[1]/section[1]/div[1]/div[2]/section[1]/section[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/button[1]")).click();
			//choose option
			driver.findElement(By.xpath("//a[contains(text(),'" + pSortBy + "')]")).click();
			
			//NOTE: Look for a way to use a wait instead of sleep
			
			Thread.sleep(5000);
			//click first item in product list
			driver.findElement(By.xpath("//body/main[@id='page']/section[@id='wrapper']/div[1]/div[2]/section[1]/section[1]/div[3]/div[1]/div[1]/div[1]/div[1]/div[1]/article[1]/div[1]/div[2]/h3[1]")).click();
			//wait for the quantity_wanted element to be visible
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#quantity_wanted")));
			//input quantity
			sfSelenium.populateInputField(By.cssSelector("#quantity_wanted"), pQuantity);
			driver.findElement(By.cssSelector("#quantity_wanted")).sendKeys(Keys.RETURN);
			Thread.sleep(1000);
			
			//check if low Stock pop-up is displayed
			boolean bStock;
			try {
				bStock = driver.findElement(By.xpath("//p[contains(text(),'Please note the current stock status is:')]")).isDisplayed();
			}
			catch(Exception e){
				bStock = false;
			}
			
			if (bStock) {
				//navigate to Home Page
				driver.get("https://www.geewiz.co.za/");
			}
			else {
				//click the checkout button
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html[1]/body[1]/main[1]/section[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[1]/div[2]/form[1]/div[3]/div[1]/div[2]/button[1]")));
				driver.findElement(By.xpath("/html[1]/body[1]/main[1]/section[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[1]/div[2]/form[1]/div[3]/div[1]/div[2]/button[1]")).click();
				
				//NOTE: Look for a way to use a wait instead of sleep

				//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html[1]/body[1]/div[19]/div[1]/div[1]/div[1]/h4[1]")));
				Thread.sleep(5000);
				
				//click Continue shopping button
				driver.findElement(By.xpath("//button[contains(text(),'Continue shopping')]")).click();
			}	
		}
		
		public void checkCartAmount() throws InterruptedException {
			//instantiate variables
			boolean bValueCheck;
			float iTotal;
			
			//creates the wait
			WebDriverWait wait = new WebDriverWait(driver, 20);
			
			//navigate to Cart
			driver.get("https://www.geewiz.co.za/cart?action=show");
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html[1]/body[1]/main[1]/section[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/span[2]")));
			
			//gets Total value
			String sCartValue = driver.findElement(By.xpath("/html[1]/body[1]/main[1]/section[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/span[2]")).getText();
			
			//remove non int values
			iTotal = Integer.parseInt(sCartValue.replaceAll("[\\D]", ""));
			
			//divide by 100 to get actual value
			iTotal = iTotal / 100;
			
			//check if value is greater than 100
			if(iTotal > 100) {
				bValueCheck = true;
			}
			else {
				bValueCheck = false;
			}
			
			Reporter.log("expected ------------------" + "Greater than 100");
		    Reporter.log("actual --------------------"+ iTotal);
			SoftAssert softassert = new SoftAssert();
			softassert.assertEquals(bValueCheck, true, "Value is less than 100");
			System.out.println(iTotal);
		}
}
