package FrameworkClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;

public class Utilities extends driverSetup{
	public void takeSnapShot(String fileWithPath) throws Exception{

        //Convert web driver object to TakeScreenshot

        TakesScreenshot scrShot =((TakesScreenshot)driver);

        //Call getScreenshotAs method to create image file
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
        
        
       

       //Move image file to new destination
       File DestFile=new File("target//"+"surefire-reports-" + getAppConfigProperties("build.timestamp") +"//images//"+ fileWithPath);

       //Copy file at destination
       FileUtils.copyFile(SrcFile, DestFile);
                
       // Update the pdf report
       Reporter.log("<a href='"+ DestFile.getAbsolutePath() + "'> <img src='"+ DestFile.getAbsolutePath() + 
    		   "' height='200' width='200'/> </a>");              
    }
	
	public String timereturn() {
		
	    LocalDateTime now = LocalDateTime.now(); 
	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmmss");  
	    
	    return dtf.format(now);
	    		//System.out.println(dtf.format(now));  
	}
	
	public String getAppConfigProperties(String propertyName) {
		// Properties setup
		Properties p = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream(".\\target\\app.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			p.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	return p.getProperty(propertyName);
	}
}
