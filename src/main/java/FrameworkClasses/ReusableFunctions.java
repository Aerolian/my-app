package FrameworkClasses;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.By;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;
import org.testng.Assert;

//This class manages all Selenium Functions and the Driver
public class ReusableFunctions extends driverSetup{
	//ExtentReportClass extReports = new ExtentReportClass();
	
		// Generate random number
		public int generateRandomData(int pMin, int pMax) {		
			int randomNum = ThreadLocalRandom.current().nextInt(pMin, pMax + 1);
			
			return randomNum;
		}
		
		// function to populate an input field using multiple By clauses
		public void populateInputField(By byClause, String inputValue) {
			driver.findElement(byClause).sendKeys(inputValue);
		}
		
		// function to select a radio button option
		public void selectRadioOption(String pValue) {
			driver.findElement(By.xpath("//input[@type='radio'][@value='" + pValue + "']")).click();
		}
		
		// function to click on a hyperlink
		public void clickLink(String pLinkText) {
			driver.findElement(By.linkText(pLinkText)).click();
		}
		
		public void CloseSelenium() {
			driver.quit();
		}
		
		// Switch between tabs
		public void switchTab(int pTagIndex) {
			//Hold all the window handles in an array list
			ArrayList<String> newTb = new ArrayList<String>(driver.getWindowHandles());
			if (pTagIndex < 0) {
				
				pTagIndex = newTb.size() - 1;
			}
			driver.switchTo().window(newTb.get(pTagIndex));
		}
		
		// Get last Digits
		public String getLastDigits(int iLastDigits, String pStringName) {
			String sLastdigits = pStringName.substring(pStringName.length() - iLastDigits);
		    System.out.println("sLastdigits" + sLastdigits);
		    return sLastdigits;
		}
		
		// Get first Digits
		public String getFirstDigits(int iFirstDigits, String pStringName) {
			String sFirstdigits = pStringName.substring(0,iFirstDigits);
		    System.out.println("sFirstdigits" + sFirstdigits);
		    return sFirstdigits;
		}
		
		// Maximise Browser Window
		public void maximiseBrowserWindow() {
			driver.manage().window().maximize();
		}
		
		// Get Detail
		public String getDetail(String sCssOfField) throws IOException {
			// get field text
			String sFieldText = this.driver.findElement(By.cssSelector(sCssOfField)).getText();
			// print but remove this later
		    System.out.println("getDetail: " + sFieldText);
		    // return
			return sFieldText;
		}
		
	    public String readPDFContent(String appUrl, int expectedNoPages) throws Exception {

	        URL url = new URL(appUrl);
	        InputStream input = url.openStream();
	        BufferedInputStream fileToParse = new BufferedInputStream(input);
	        PDDocument document = null;
	        String output = null;

	        try {
	            document = PDDocument.load(fileToParse);
	            output = new PDFTextStripper().getText(document);
	            // ensure the number of pages is correct
	            int numberOfPages = getPageCount(document);
	            Assert.assertEquals(numberOfPages,expectedNoPages);
	     
	            
	        } finally {
	            if (document != null) {
	                document.close();
	            }
	            fileToParse.close();
	            input.close();
	        }
	        return output;
	    }
	    
	    public static int getPageCount(PDDocument doc) {
			//get the total number of pages in the pdf document
			int pageCount = doc.getNumberOfPages();
			return pageCount;
		}
}
