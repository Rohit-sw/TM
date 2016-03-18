package com.TrafficManager.test;
import static com.TrafficManager.test.DriverScript.APP_LOGS;
import static com.TrafficManager.test.DriverScript.CONFIG;
//import static com.easyro.test.mouseMovement.*;
import static com.TrafficManager.test.DriverScript.OR;
import static com.TrafficManager.test.DriverScript.currentTestCaseName;
import static com.TrafficManager.test.DriverScript.currentTestDataSetID;
import static com.TrafficManager.test.DriverScript.currentTestSuiteXLS;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;




//import cart.MyListener;
//import com.varstreet.test.MyListener;
public class Keywords {

	public WebDriver driver= null;

	//Open browser
	public String openBrowser(String object1, String object2,String value1, String value2) throws Exception{		
		APP_LOGS.debug("Opening browser");
		if(driver == null)
		{
			if(value1.equals("Mozilla"))
			{
				FirefoxProfile profile= new FirefoxProfile();
				profile.setEnableNativeEvents(true);	
				driver=new FirefoxDriver();
			}
			else if(value1.equals("IE"))
				driver=new InternetExplorerDriver();
			else if(value1.equals("Chrome")){
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//ChromeDriver//chromedriver.exe");
				//String downloadFilePath = System.getProperty("user.dir")+"\\src\\com\\broadcastreporter\\requirements\\downloadedFiles";
				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("profile.default_content_settings.popups",0);
				//chromePrefs.put("download.default_directory", downloadFilePath);
				ChromeOptions options = new ChromeOptions();
				HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
				options.setExperimentalOptions("prefs", chromePrefs);
				options.addArguments("--test-type");
				DesiredCapabilities cap = DesiredCapabilities.chrome();
				cap.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
				cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				cap.setCapability(ChromeOptions.CAPABILITY, options);
				driver=new ChromeDriver(cap);
				Robot robot = new Robot();
				robot.mouseMove(10,10);
			}
		}

		driver.manage().window().maximize();
		long implicitWaitTime=Long.parseLong(CONFIG.getProperty("implicitwait"));
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		return Constants.KEYWORD_PASS;

	}

	//navigate to a particular url
	public String navigate(String object1, String object2,String value1, String value2){		
		APP_LOGS.debug("Navigating to URL");
		try{
			driver.get(value1);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" -- Not able to navigate";
		}
		return Constants.KEYWORD_PASS;
	}

	//click on the link based on xpath
	public String clickLink(String object1, String object2,String value1,String value2){
		APP_LOGS.debug("Clicking on link ");

		//System.out.println("Clicking on link ");
		try{
			Thread.sleep(2000);
			driver.findElement(By.xpath(OR.getProperty(object1))).click();
			System.out.println("clicked on link successfully");
			Thread.sleep(2000);

		}catch(Exception e){
			APP_LOGS.error(e);
			return Constants.KEYWORD_FAIL+" -- Not able to click on link"+e.getMessage();
		}

		return Constants.KEYWORD_PASS;
	}

	//click on the link based on linktext
	public String clickLink_linkText(String object1, String object2,String value1,String value2){
		APP_LOGS.debug("Clicking on link ");
		try{
			Thread.sleep(3000);
			driver.findElement(By.linkText(OR.getProperty(object1))).click();

		}catch(Exception e){
			return Constants.KEYWORD_FAIL +e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	//click link based on partial link text
	public String clickPartial_LinkText(String object1, String object2,String value1,String value2){
		APP_LOGS.debug("Clicking on partial link ");
		try{
			driver.findElement(By.partialLinkText((object1))).click();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}


	//Verify link text
	public  String verifyLinkText(String object1, String object2,String value1,String value2){
		APP_LOGS.debug("Verifying link Text");
		try{
			String actual=driver.findElement(By.xpath(OR.getProperty(object1))).getText();
			String expected=value1;

			if(actual.equals(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+" -- Link text not verified";

		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" -- Link text not verified"+e.getMessage();

		}

	}

	//Click on a button
	public  String clickButton(String object1, String object2,String value1,String value2){

		APP_LOGS.debug("Clicking on Button");
		try{
			Thread.sleep(2000);
			WebElement button = driver.findElement(By.cssSelector(OR.getProperty(object1)));
			if(button.isEnabled()){
				button.click();
			}
				

			//System.out.println("clicked on button successfully");
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" -- Not able to click on Button"+e.getMessage();
		}


		return Constants.KEYWORD_PASS;
	}

	//Verify button text
	public  String verifyButtonText(String object1, String object2,String value1,String value2){
		APP_LOGS.debug("Verifying the button text");
		try{
			String actual=driver.findElement(By.cssSelector(OR.getProperty(object1))).getText();
			String expected=value1;

			if(actual.equals(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+" -- Button text not verified "+actual+" -- "+expected;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}

	}


	//Select element from dropdown
	public  String selectList(String object1, String object2, String value1, String value2){
		APP_LOGS.debug("Selecting from list");
		try{
			if(value1.equals(" ")){
				return Constants.KEYWORD_PASS;
			}
			else if(!value1.equals(Constants.RANDOM_VALUE)){
				new Select(driver.findElement(By.xpath(OR.getProperty(object1)))).selectByVisibleText(value1);
				Thread.sleep(2000);
			}else{
				// logic to find a random value in list
				WebElement droplist= driver.findElement(By.xpath(OR.getProperty(object1))); 
				List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));
				Random num = new Random();
				int index=num.nextInt(droplist_cotents.size());
				String selectedVal=droplist_cotents.get(index).getText();

				driver.findElement(By.xpath(OR.getProperty(object1))).sendKeys(selectedVal);
				Thread.sleep(1000);
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +" - Could not select from list. "+ e.getMessage();	
		}
		return Constants.KEYWORD_PASS;	
	}

	//Verify elements in the dropdown
	public String verifyElementsInDropdown(String object1, String object2, String value1, String value2){
		APP_LOGS.debug("Verify elements in the dropdown");
		try{
			WebElement dropdown = driver.findElement(By.xpath(OR.getProperty(object1)));
			List<WebElement> allItems = dropdown.findElements(By.tagName("option"));
			for(WebElement singleItem:allItems){
				String itemName = singleItem.getText();
				if(itemName.contains(value1)){
					return Constants.KEYWORD_PASS;
				}
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +"Unable to verify items in dropdown" +e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}



	//Verify all the elements in a list on mouse hover
	public String verifyAllListElements(String object1, String object2, String value1, String value2){
		APP_LOGS.debug("Verifying the selection of the list");
		try{	
			WebElement droplist= driver.findElement(By.xpath(OR.getProperty(object1))); 
			List<WebElement> droplist_cotents = droplist.findElements(By.tagName("a"));
			// extract the expected values from OR. properties
			String temp=value1;
			String allElements[]=temp.split(",");
			// check if size of array == size if list
			if(allElements.length != droplist_cotents.size())
				return Constants.KEYWORD_FAIL +"- size of lists do not match";	

			for(int i=0;i<droplist_cotents.size();i++){
				if(!allElements[i].equals(droplist_cotents.get(i).getText())){
					return Constants.KEYWORD_FAIL +"- Element not found - "+allElements[i];
				}
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +" - Could not select from list. "+ e.getMessage();	

		}


		return Constants.KEYWORD_PASS;	
	}

	//Verify the selected element in a dropdown
	public  String verifyListSelection(String object1, String object2,String value1, String value2){
		APP_LOGS.debug("Verifying List selection");
		try{
			String expectedVal=value1;
			String actualVal = new Select(driver.findElement(By.xpath(OR.getProperty(object1)))).getFirstSelectedOption().getText();

			if(!actualVal.equals(expectedVal))
				return Constants.KEYWORD_FAIL + "Value not in list - "+expectedVal;

		}catch(Exception e){
			return Constants.KEYWORD_FAIL +" - Could not find list. "+ e.getMessage();	

		}
		return Constants.KEYWORD_PASS;	

	}

	//Select element in the dropdown based on <li> tag
	public String selectListByLi(String object1, String object2, String value1, String value2){
		APP_LOGS.debug("selecting element in the list");
		try{
			driver.findElement(By.xpath(OR.getProperty(object1))).click();
			WebElement allListItems = driver.findElement(By.xpath(OR.getProperty(object2)));
			List<WebElement> listItems = allListItems.findElements(By.tagName("li"));
			for(WebElement item:listItems){
				if(item.equals(value1)){
					item.click();
					return Constants.KEYWORD_PASS;
				}
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	//Select radio button
	public  String selectRadio(String object1, String object2, String value1, String value2){
		APP_LOGS.debug("Selecting a radio button");
		try{
			String temp[]=object1.split(Constants.DATA_SPLIT);
			driver.findElement(By.xpath(OR.getProperty(temp[0])+value1+OR.getProperty(temp[1]))).click();
			//driver.findElement(By.xpath(OR.getProperty(object1)+value1+OR.getProperty(object2))).click();
			//driver.findElement(By.xpath(data)).click();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +"- Not able to find radio button";	

		}

		return Constants.KEYWORD_PASS;	

	}

	//Verify selected radio button
	public  String verifyRadioSelected(String object1, String object2, String value1, String value2){
		APP_LOGS.debug("Verify Radio Selected");
		try{
			String temp[]=object1.split(Constants.DATA_SPLIT);
			String checked=driver.findElement(By.xpath(OR.getProperty(temp[0])+value1+OR.getProperty(temp[1]))).getAttribute("checked");
			if(checked==null)
				return Constants.KEYWORD_FAIL+"- Radio not selected";	


		}catch(Exception e){
			return Constants.KEYWORD_FAIL +"- Not able to find radio button";	

		}

		return Constants.KEYWORD_PASS;	

	}


	//Select check box
	public  String checkCheckBox(String object1, String object2,String value1, String value2){
		APP_LOGS.debug("Checking checkbox");
		try{
			WebElement checkbox = driver.findElement(By.xpath(OR.getProperty(object1)));
			if(value1.equals("YES")){
				checkbox.click();
			}
			else if(checkbox.isSelected() && value1.equals("NO")){
				checkbox.click();
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" - Could not find checkbox";
		}
		return Constants.KEYWORD_PASS;

	}

	//Uncheck checkbox
	public String unCheckCheckBox(String object1, String object2,String value1, String value2){
		APP_LOGS.debug("Unchecking checkBox");
		try{
			String checked=driver.findElement(By.xpath(OR.getProperty(object1))).getAttribute("checked");
			if(checked!=null)
				driver.findElement(By.xpath(OR.getProperty(object1))).click();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" - Could not find checkbox";
		}
		return Constants.KEYWORD_PASS;

	}

	//Uncheck remember login option in gmail
	public String unChkRememberLoginGmail(String object1, String object2, String value1, String value2){
		APP_LOGS.debug("Unchecking the Remember Login option in Gmail");
		try{
			WebElement rememberLoginChkbox = driver.findElement(By.xpath(OR.getProperty(object1)));
			if(rememberLoginChkbox.isSelected()){
				rememberLoginChkbox.click();
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +"Could not uncheck checkbox";
		}
		return Constants.KEYWORD_PASS;
	}


	//Verify selected checkbox
	public  String verifyCheckBoxSelected(String object1, String object2,String value1, String value2){
		APP_LOGS.debug("Verifying checkbox selected");
		try{
			String checked=driver.findElement(By.xpath(OR.getProperty(object1))).getAttribute("checked");
			if(checked!=null)
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " - Not selected";

		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" - Could not find checkbox";

		}
	}

	//Verify text based on xpath
	public String verifyText(String object1, String object2, String value1, String value2){
		APP_LOGS.debug("Verifying the text");
		//String data_Flag = currentTestSuiteXLS.getCellData(currentTestCaseName, Constants.DATA_CORRECTNESS, currentTestDataSetID);
		try{

			String actual=driver.findElement(By.xpath(OR.getProperty(object1))).getText();
			String expected=value1;

			if(actual.equals(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+" -- text not verified "+actual+" -- "+expected;

		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}


	}

	//Extract link from the text and click on it
	public String extractLinkFromText(String object1, String object2, String value1, String value2){
		APP_LOGS.debug("Extracting a link from the text");
		try{
			WebElement allLinks = driver.findElement(By.xpath(OR.getProperty(object1)));
			List<WebElement> linkName = allLinks.findElements(By.tagName("a"));
			for(WebElement singleLink:linkName){
				if(singleLink.getText().contains("click here")){
					singleLink.click();
					return Constants.KEYWORD_PASS;
				}
			}	
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +e.getMessage();
		}

		return Constants.KEYWORD_PASS;
	}


	//Verify External RO text on the page
	public String verifyTextOnPage(String object1, String object2, String value1, String value2){
		try{	
			APP_LOGS.debug("Verifying text on the page");
			String data_Flag = currentTestSuiteXLS.getCellData(currentTestCaseName, Constants.DATA_CORRECTNESS, currentTestDataSetID);
			List<WebElement> allElements = driver.findElements(By.xpath(OR.getProperty(object1)));
			String temp=null;
			for(WebElement ele:allElements){
				temp = ele.getText();

			}
			if(temp.contains(value1)){
				if(data_Flag.equals(Constants.POSITIVE_DATA)) {
					return Constants.KEYWORD_PASS;
				}else{
					return Constants.KEYWORD_FAIL;
				}
			} else if(!temp.contains(value1))
			{
				if(data_Flag.equals(Constants.POSITIVE_DATA)) {
					return Constants.KEYWORD_FAIL;
				}else{
					return Constants.KEYWORD_PASS;
				}
			}
		} catch(Exception e){
			return Constants.KEYWORD_FAIL +e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	//Verify text based on getpagesource
	public String verifyTextByName(String object1, String object2, String value1, String value2){
		APP_LOGS.debug("Verifying the text");
		String data_Flag = currentTestSuiteXLS.getCellData(currentTestCaseName, Constants.DATA_CORRECTNESS, currentTestDataSetID);
		try{
			Boolean result = driver.getPageSource().contains(OR.getProperty(object1));
			String actual = Boolean.toString(result);
			String expected = value1;
			if(!data_Flag.equals(Constants.POSITIVE_DATA))
			{
				if(actual.equals(expected))
					return Constants.KEYWORD_PASS;
				else
					return Constants.KEYWORD_FAIL;
			}
			return Constants.KEYWORD_PASS;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
	}

	//Write in a text box
	public  String writeInInput(String object1, String object2,String value1, String value2){
		APP_LOGS.debug("Writing in text box");
		try{
			if(!value1.equals("")){
				driver.findElement(By.cssSelector(OR.getProperty(object1))).clear();
				driver.findElement(By.cssSelector(OR.getProperty(object1))).sendKeys(value1);
			}else{
				driver.findElement(By.cssSelector(OR.getProperty(object1))).clear();
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

		}
		return Constants.KEYWORD_PASS;

	}

	//Verify value in text boxtext box
	public  String verifyTextinInput(String object1, String object2,String value1, String value2){
		APP_LOGS.debug("Verifying the text in input box");
		String actual = null;
		try{
			if(object1.contains(Constants.DATA_SPLIT)){
				String temp[] = object1.split(Constants.DATA_SPLIT);
				actual  = driver.findElement(By.xpath(OR.getProperty(temp[0])+value2+OR.getProperty(temp[1]))).getAttribute("value");
			} else{
				actual = driver.findElement(By.xpath(OR.getProperty(object1))).getAttribute("value");
			}
			String expected=value1;

			if(actual.equals(expected)){
				return Constants.KEYWORD_PASS;
			}else{
				return Constants.KEYWORD_FAIL+" Not matching ";
			}

		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to find input box "+e.getMessage();

		}
	}

	//Verify if the input box is disabled
	public String verifyInputDisabled(String object1, String object2, String value1, String value2){
		try{
			APP_LOGS.debug("Vrifying if the input box is disabled");
			boolean actualResult = driver.findElement(By.xpath(OR.getProperty(object1))).isEnabled();
			if(actualResult){
				return Constants.KEYWORD_FAIL;
			}else{
				return Constants.KEYWORD_PASS;
			}

		}catch(Exception e){
			return Constants.KEYWORD_FAIL +e.getMessage();
		}
	}

	//Verify title of the page
	public  String verifyTitle(String object1, String object2, String value1, String value2){
		APP_LOGS.debug("Verifying title");
		try{
			String actualTitle= driver.getTitle();
			String expectedTitle=value1;
			if(actualTitle.equals(expectedTitle))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+" -- Title not verified "+expectedTitle+" -- "+actualTitle;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Error in retrieving title";
		}		
	}


	public String exist(String object1, String object2,String value1, String value2){
		APP_LOGS.debug("Checking existance of element");
		try{
			driver.findElement(By.xpath(OR.getProperty(object1)));
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object doest not exist";
		}


		return Constants.KEYWORD_PASS;
	}

	public  String click(String object1, String object2,String value1, String value2){
		APP_LOGS.debug("Clicking on any element");
		String data_Flag = currentTestSuiteXLS.getCellData(currentTestCaseName, Constants.DATA_CORRECTNESS, currentTestDataSetID);
		try{
			if(data_Flag.equals(Constants.POSITIVE_DATA)){
				driver.findElement(By.xpath(OR.getProperty(object1))).click();
			}else{
				return Constants.KEYWORD_PASS;
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Not able to click";
		}
		return Constants.KEYWORD_PASS;
	}

	//Close browser
	public  String closeBroswer(String object1, String object2, String value1, String value2){
		APP_LOGS.debug("Closing the browser");
		try{
			driver.quit();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+"Unable to close browser. Check if its open"+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}


	//Switch to Colorbox Frame to read elements on the Form
	public String switchToFrame(String object1, String object2, String value1, String value2){
		try{
			APP_LOGS.debug("Switching to a frame");
			Thread.sleep(20000);
			driver.switchTo().frame(0);
			return Constants.KEYWORD_PASS;
		} catch(Exception e){
			APP_LOGS.error(e);
			return Constants.KEYWORD_FAIL +"Not able to find the frame";
		}
	}

	public String waitForPage(String object1, String object2, String value1, String value2) throws Exception, InterruptedException{
		Thread.sleep(Integer.parseInt(value1));
		return Constants.KEYWORD_PASS;
	}


	// not a keyword

	public void captureScreenshot(String filename, String keyword_execution_result) throws IOException{
		// take screen shots
		if(CONFIG.getProperty("screenshot_everystep").equals("Y")){
			// capturescreen
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") +"//screenshots//"+filename+".jpg"));

		}else if (keyword_execution_result.startsWith(Constants.KEYWORD_FAIL) && CONFIG.getProperty("screenshot_error").equals("Y") ){
			// capture screenshot
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") +"//screenshots//"+filename+".jpg"));
		}
	}

	public String mouseHover(String object1, String object2, String value1, String value2) throws InterruptedException
	{
		////system.out.println(object1)
		WebElement MainMenuItemLink = driver.findElement(By.linkText(OR.getProperty(object1)));
		Actions builder = new Actions (driver);
		builder.moveToElement(MainMenuItemLink).build().perform();
		Thread.sleep(5000);

		return Constants.KEYWORD_PASS;

	}

	//Mouse hover on the username if the object contains col|(split)
	public String mouseHoverOnUsername(String object1, String object2, String value1, String value2) throws InterruptedException{

		WebElement MainMenuItemLink = driver.findElement(By.linkText(object1));
		Actions builder = new Actions (driver);
		builder.moveToElement(MainMenuItemLink).build().perform();
		Thread.sleep(5000);

		return Constants.KEYWORD_PASS;
	}

	public String pause(String object1, String object2, String value1, String value2) throws NumberFormatException, InterruptedException{

		if(object1.equals("")){
			Thread.sleep(10000);
			return Constants.KEYWORD_PASS;
		}else{
			long time = (long)Double.parseDouble(object1);
			Thread.sleep(time*1000L);
			return Constants.KEYWORD_PASS;
		}
	}

	//execute this keyword only when the data flag is negative 
	public String executeOnlyWhenNegative(String object1, String object2, String value1, String value2){
		try{
			APP_LOGS.debug("Execute the keywords only when the data flag is negative");
			String data_Flag = currentTestSuiteXLS.getCellData(currentTestCaseName, Constants.DATA_CORRECTNESS, currentTestDataSetID);
			if(!data_Flag.equals(Constants.POSITIVE_DATA)){
				WebElement MainMenuItemLink = driver.findElement(By.linkText(OR.getProperty(object1)));
				Actions builder = new Actions (driver);
				builder.moveToElement(MainMenuItemLink).build().perform();
				driver.findElement(By.linkText(OR.getProperty(object2))).click();

			}else {
				Thread.sleep(2000);
				return Constants.KEYWORD_PASS;
			}

		}catch(Exception e){
			return Constants.KEYWORD_FAIL +e.getMessage();
		}

		return Constants.KEYWORD_PASS;
	}




	/************************APPLICATION SPECIFIC KEYWORDS ********************************/


	// API to delete already created program   
	public String deleteMediaGridProgramsApi(String object1, String object2, String value1, String value2 ) throws IOException
	{
		APP_LOGS.debug("API to delete already created program");
		//String customerID = "728";
		//String channelId = "895";
		//driver.get("http://test.tv.surewaves.com/surewaves/apis/TrafficManager/deleteAllPrograms.php?customerId="+customerID+"&channelId="+channelId);
		try{
			URL url = new URL("http://test.tv.surewaves.com/surewaves/apis/TrafficManager/deleteAllPrograms.php?customerId="+value1+"&channelId="+value2);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			String strTemp = "";
			while (null != (strTemp = br.readLine())) 
			{
				//System.out.println(strTemp);
				return Constants.KEYWORD_PASS+strTemp;
			}
		}
		catch(Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	//Validate traffic manager Login
	public String validateTMLogin(String object1, String object2, String value1, String value2){
		APP_LOGS.debug("Validating traffic manager Login");
		String data_Flag = currentTestSuiteXLS.getCellData(currentTestCaseName, Constants.DATA_CORRECTNESS, currentTestDataSetID);
		try{
			Thread.sleep(3000);
			if(driver.getPageSource().contains("LOG IN")){
				//There is an error message on the page
				String actualErrMsg = driver.findElement(By.xpath(OR.getProperty(object1))).getText();
				String expectedErrMsg = OR.getProperty(value1);
				if(actualErrMsg.equals(expectedErrMsg) && (!data_Flag.equals(Constants.POSITIVE_DATA))){
					return Constants.KEYWORD_PASS;
				}else{
					return Constants.KEYWORD_FAIL;
				}
			}else{
				driver.findElement(By.linkText(OR.getProperty("Logout_LinkText"))).click();
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +e.getMessage();
		}
		if(data_Flag.equals(Constants.POSITIVE_DATA)){
			return Constants.KEYWORD_PASS;
		}else{
			return Constants.KEYWORD_FAIL;
		}
	}

	//Click on the FPC to create a program based on the date and time provided in the data
	public String clickOnFPCToCreateProgram(String object1, String object2, String value1, String value2){
		APP_LOGS.debug("Clicking on the FPC to create new program");
		String data_Flag = currentTestSuiteXLS.getCellData(currentTestCaseName, Constants.DATA_CORRECTNESS, currentTestDataSetID);
		try{
			Date date = parseDate(value1);
			Date time = parseTime(value2);
			//driver.findElement(By.xpath(".//*[@id='directives-calendar']/div[1]/div/button[2]")).click();
			Thread.sleep(3000);
			int  rowOfTheTimeInFPC = getRowOfTheTimeInFPC(time);
			ArrayList xyValues = clickOnFpcControl(date,rowOfTheTimeInFPC);
			Robot fpcRobot = new Robot();

			//Getting the color of the cell		
			fpcRobot.mouseMove((int)xyValues.get(1)+10, (int)xyValues.get(0));
			fpcRobot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			fpcRobot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

			if(object1.contains("VerifyColor"))
			{
				Thread.sleep(3000);
				Color color = fpcRobot.getPixelColor((int)xyValues.get(1)+10, (int)xyValues.get(0));
				if(data_Flag.equals(Constants.POSITIVE_DATA) && (color.equals("java.awt.Color[r=0,g=118,b=255]") || color.equals("java.awt.Color[r=187,g=217,b=251]")))
				{
					return Constants.KEYWORD_PASS +"Mouse hover is happening on the selected date and time";
				}else if(data_Flag.equals(Constants.POSITIVE_DATA) && (color.equals("java.awt.Color[r=248,g=249,b=249]") ||color.equals("java.awt.Color[r=240,g=242,b=243]")))
				{
					return Constants.KEYWORD_PASS +"Mouse hover is not happening at the provided date and time";
				}
				else if((!data_Flag.equals(Constants.POSITIVE_DATA)) && (color.equals("java.awt.Color[r=0,g=118,b=255]") || color.equals("java.awt.Color[r=187,g=217,b=251]")))
				{
					return Constants.KEYWORD_FAIL;
				}
			}

		}catch(Exception e){
			return Constants.KEYWORD_FAIL +e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	//Verify the context menu
	public String verifyContextMenuOnFPC(String object1, String object2, String value1, String value2){
		APP_LOGS.debug("Verifying the context menu on click");
		String data_Flag = currentTestSuiteXLS.getCellData(currentTestCaseName, Constants.DATA_CORRECTNESS, currentTestDataSetID);
		try{
			List<WebElement> contextMenu = driver.findElements(By.xpath(OR.getProperty(object1)));
			if(contextMenu.size()>0 && data_Flag.equals(Constants.POSITIVE_DATA))
			{
				WebElement contextMenuElements = driver.findElement(By.xpath(OR.getProperty(object1)));
				List<WebElement> allContextMenuElements = contextMenuElements.findElements(By.tagName("ul"));
				ArrayList<String> alist = new ArrayList<String>(Arrays.asList(value1.split(",")));
				for(int i=0;i<=alist.size()-1;i++){
					String elementToVerify = alist.get(i).toString();
					for(WebElement contextMenuElement:allContextMenuElements){
						String contextMenuText = contextMenuElement.getAttribute("textContent");
						String expectedElementOnContextMenu = elementToVerify;
						if(elementToVerify.equals("")){
							break;
						}
						else if(contextMenuText.equalsIgnoreCase(expectedElementOnContextMenu)){
							break;
						}

					}
				}
			}
			else if((contextMenu.size()==0) && (data_Flag.equals(Constants.POSITIVE_DATA)))
			{
				return Constants.KEYWORD_PASS;
			}
			else if((contextMenu.size()>0) && (!data_Flag.equals(Constants.POSITIVE_DATA)))
			{
				return Constants.KEYWORD_FAIL;
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	//Calculate the dates listed as per the selected week
	public String checkDatesOnHeader(String object1, String object2, String value1, String value2){
		try{
			Boolean dateMatches = false;
			//Get the week number to verify the weeknumber visible on the header of FPC
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = format.parse(value1);
			Calendar now = Calendar.getInstance();
			now.setTime(date);
			String weeknumber = "WEEK"+Integer.toString(now.get(Calendar.WEEK_OF_YEAR));

			//Get the format of date in d/M/yy EEE i.e 12/2/16 Fri		
			format = new SimpleDateFormat("EEE, dd MMM yyyy");
			//now.set(expYear, expMonth, Integer.parseInt(expDate));
			String[] days = new String[7];
			int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 1; //add 2 if your week start on monday
			now.add(Calendar.DAY_OF_MONTH, delta );
			for (int i = 0; i < 7; i++)
			{
				days[i] = format.format(now.getTime());
				now.add(Calendar.DAY_OF_MONTH, 1);
				WebElement datesOnFPC = driver.findElement(By.className("fc-row"));
				List<WebElement> allDates = datesOnFPC.findElements(By.tagName("th"));
				for(WebElement dateOnHeader:allDates){
					String textOnHeader = dateOnHeader.getText();
					if(textOnHeader.equals(weeknumber)){
						APP_LOGS.info("Week number matches");
					}
					if(textOnHeader.equals(days[i])){
						APP_LOGS.info(textOnHeader+"matches with" +days[i] );
						dateMatches=true;
					}
				}
			}
				if(!dateMatches){
					return Constants.KEYWORD_FAIL;
				}


				/*
				//Getting the date from the FPC header
				WebElement datesOnFPC = driver.findElement(By.className("fc-row"));
				List<WebElement> allDates = datesOnFPC.findElements(By.tagName("th"));

				for(int j=0;j<allDates.size();j++){
					String weekOnHeader = allDates.get(0).getText();
					if(weekOnHeader.equals(weeknumber)){
						break;
					}
					String actualDate = allDates.get(j).getText();
					String expectedDate = days[i];
					if(actualDate.equals(expectedDate)){
						break;
					}
				}*/  
				

		}catch(Exception e){
			return Constants.KEYWORD_FAIL +e.getMessage();	
		}
		return Constants.KEYWORD_PASS;
	}

	//selecting the program genre and program type, choosen dropdown
	public String selectElementInchosenDropdown(String object1, String object2, String value1, String value2){
		APP_LOGS.debug("selecting element in the list");
		try{
			//click on the dropdown
			driver.findElement(By.cssSelector(OR.getProperty(object1))).click();
			List<WebElement> programType = driver.findElements(By.className("ui-select-choices-row-inner"));
			for(WebElement individualProgramType:programType){
				String programTypetoBeSelected = individualProgramType.getText();
				if(programTypetoBeSelected.contains(value1)){
					individualProgramType.click();
					break;
				}
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	//Select time for a program
	public String selectTimeForProgram(String object1, String object2, String value1, String value2){
		APP_LOGS.debug("Selecting time");
		String data_Flag = currentTestSuiteXLS.getCellData(currentTestCaseName, Constants.DATA_CORRECTNESS, currentTestDataSetID);
		try{
			Thread.sleep(3000);
			//String timeDurations[] = (object2.split(" ")[0]).split(",");
			String time = value1;
			String time_HH_MM_AMPM[] = (time.split(" ")[0]).split(":");
			String AMPM = time.split(" ")[1];
			String hours = time_HH_MM_AMPM[0];
			String minutes = time_HH_MM_AMPM[1];
			String gridColor = null;
			WebElement timeGrid = driver.findElement(By.cssSelector(OR.getProperty(object1)));
			List<WebElement> allRows = timeGrid.findElements(By.tagName("tr"));
			List<WebElement> allcolumns = allRows.get(1).findElements(By.tagName("td"));
			for(WebElement columnElement:allcolumns){
				String className = columnElement.getAttribute("class");
				if(className.equalsIgnoreCase(OR.getProperty("Hours_Section_TimeGrid"))){
					columnElement.findElement(By.tagName("input")).clear();
					columnElement.findElement(By.tagName("input")).sendKeys(hours);
					//Thread.sleep(2000);
					gridColor = columnElement.findElement(By.tagName("input")).getCssValue("border-color");

				}else if(className.equalsIgnoreCase(OR.getProperty("Minutes_Section_TimeGrid"))){
					columnElement.findElement(By.tagName("input")).clear();
					columnElement.findElement(By.tagName("input")).sendKeys(minutes);
					//Thread.sleep(2000);
					gridColor = columnElement.findElement(By.tagName("input")).getCssValue("border-color");
				}else if(className.equalsIgnoreCase(OR.getProperty("AM_PM_Section_TimeGrid"))){
					String temp = columnElement.findElement(By.tagName("button")).getText();
					if(temp.equals(AMPM)){
						break;
					}else{
						columnElement.findElement(By.tagName("button")).click();
					}
				}
			}	
			Thread.sleep(2000);
			if(gridColor.equals("rgb(132, 53, 52)") && (!data_Flag.equals(Constants.POSITIVE_DATA)))
			{
				String actualErrMsg = driver.findElement(By.xpath(OR.getProperty("Incorrect_StartTime_EndTime"))).getText();
				String expectedErrMsg = value2;
				if(actualErrMsg.equalsIgnoreCase(expectedErrMsg)){
					return Constants.KEYWORD_PASS;
				}else{
					return Constants.KEYWORD_FAIL;
				}
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	//Select program duration for template
	//Select time for a program
	public String selectTimeForTemplate(String object1, String object2, String value1, String value2){
		APP_LOGS.debug("Selecting time");
		String data_Flag = currentTestSuiteXLS.getCellData(currentTestCaseName, Constants.DATA_CORRECTNESS, currentTestDataSetID);
		try{
			Thread.sleep(3000);
			String timeDurations[] = (object2.split(" ")[0]).split(",");
			String time = value1;
			String time_HH_MM_AMPM[] = (time.split(" ")[0]).split(":");
			String hours = time_HH_MM_AMPM[0];
			String minutes = time_HH_MM_AMPM[1];
			String seconds = time_HH_MM_AMPM[2];
			String gridColor = null;
			WebElement timeGrid = driver.findElement(By.cssSelector(OR.getProperty(object1)));
			List<WebElement> allRows = timeGrid.findElements(By.tagName("tr"));
			List<WebElement> allcolumns = allRows.get(1).findElements(By.tagName("td"));
			for(WebElement columnElement:allcolumns){
				String className = columnElement.getAttribute("class");
				if(className.equalsIgnoreCase(OR.getProperty(timeDurations[0]))){
					columnElement.findElement(By.tagName("input")).clear();
					columnElement.findElement(By.tagName("input")).sendKeys(hours);
					gridColor = columnElement.findElement(By.tagName("input")).getCssValue("border-color");

				}else if(className.equalsIgnoreCase(OR.getProperty(timeDurations[1]))){
					columnElement.findElement(By.tagName("input")).clear();
					columnElement.findElement(By.tagName("input")).sendKeys(minutes);
					gridColor = columnElement.findElement(By.tagName("input")).getCssValue("border-color");
				}else if(className.equalsIgnoreCase(OR.getProperty(timeDurations[2]))){
					columnElement.findElement(By.tagName("input")).clear();
					columnElement.findElement(By.tagName("input")).sendKeys(seconds);
					gridColor = columnElement.findElement(By.tagName("input")).getCssValue("border-color");
				}
			}	
			Thread.sleep(2000);
			if(gridColor.equals("rgb(132, 53, 52)") && (!data_Flag.equals(Constants.POSITIVE_DATA)))
			{
				String actualErrMsg = driver.findElement(By.xpath(OR.getProperty("Incorrect_Time_Template"))).getText();
				String expectedErrMsg = value2;
				if(actualErrMsg.equalsIgnoreCase(expectedErrMsg)){
					return Constants.KEYWORD_PASS;
				}else{
					return Constants.KEYWORD_FAIL;
				}
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	//Display week based on the date selected by the user on FPC
	public String selectDateInCalendar(String object1, String object2, String value1, String value2){
		APP_LOGS.debug("Selecting date");
		String data_Flag = currentTestSuiteXLS.getCellData(currentTestCaseName, Constants.DATA_CORRECTNESS, currentTestDataSetID);
		try{
			driver.findElement(By.cssSelector(OR.getProperty(object1))).click();
			selectdateInAnyCalendar(value1);
			//If the data flag is negative then close the calendar as the date is disabled
			/*if(!data_Flag.equals(Constants.POSITIVE_DATA)){
				driver.findElement(By.cssSelector(OR.getProperty(object1))).click();
			}*/
			/*
			List<String> monthList = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
			//Select start date and end date
			String date = value1;      
			String date_dd_MM_YYYY[] = (date.split(" ")[0]).split("-");
			int expMonth = Integer.parseInt(date_dd_MM_YYYY[1]);
			String expDate = date_dd_MM_YYYY[0];
			int expYear = Integer.parseInt(date_dd_MM_YYYY[2]);
			boolean dateNotFound=true;
			//This loop will be executed till date not found
			while(dateNotFound){
				WebElement calendar = driver.findElement(By.cssSelector(OR.getProperty("Calendar_UI_Css")));
				List<WebElement> allRows1 = calendar.findElements(By.tagName("tr"));
				List<WebElement> allheaders=null;
				allheaders = allRows1.get(0).findElements(By.tagName("th"));
				String selectedCalendarmonth = null;
				selectedCalendarmonth = allRows1.get(0).getText();
				String month_Year[] = selectedCalendarmonth.split(" ");
				String month = month_Year[0];
				String year = month_Year[1];

				//If current year and month are same as expected month and year then go inside this condition
				if(monthList.indexOf(month)+1 == expMonth && (expYear == Integer.parseInt(year))){
					//Call select date function and set the datenotfound flag to false
					List<WebElement> noOfColumns=calendar.findElements(By.tagName("td"));
					for(WebElement calendarCell:noOfColumns){
						//System.out.println(calendarCell.getText());
						if(calendarCell.getText().equals(expDate)){
							calendarCell.findElement(By.tagName("button")).click();
							break;
						}
					}
					dateNotFound = false;
				} 
				else if(monthList.indexOf(month)+1 < expMonth && (expYear == Integer.parseInt(year)) || expYear > Integer.parseInt(year)){

					//Click on the next link of the date picker
					allheaders.get(2).click();
				}
				else if(monthList.indexOf(month)+1 > expMonth && (expYear == Integer.parseInt(year)) || expYear < Integer.parseInt(year)){

					//Click on the previous link of the date picker
					allheaders.get(0).click();
				}
			}
			 */
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	//Verify what days are selected based on the repeat status selected
	public String verifySelectedDay(String object1, String object2, String value1, String value2){
		APP_LOGS.debug("Verify what day is selected based on the repeat status selected");
		try{
			ArrayList<String> days = new ArrayList<String>(Arrays.asList(value2.split(",")));
			for(int i=0;i<days.size();i++){
				WebElement daystable = driver.findElement(By.xpath(OR.getProperty(object1)));
				List<WebElement> alldivs = daystable.findElements(By.tagName("div"));
				//int index=1;
				for(WebElement div:alldivs){
					String classname = div.getAttribute("class");
					//Select repeat status
					if(classname.equals("btn-group")){
						new Select(div.findElement(By.cssSelector("select[ng-model='data.occurence']"))).selectByVisibleText(value1);
					}
					if(classname.equals("btn-group weekly-check ng-scope")){
						String temp = div.findElement(By.tagName("label")).getText();
						//WebElement dayCheckbox = div.findElement(By.tagName("span"));
						if(temp.equals(days.get(i)) && (div!=null)){
							div.click(); //unselect the checkbox
							break;
						}
					}
				}
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	//Select days based on the repeat type selected by the usr
	public String selectDaysForProgram(String object1, String object2, String value1, String value2){
		APP_LOGS.debug("Selecting days");
		try{
			//String days[] = {"su","tu","we","th","fr","sa"};
			ArrayList days = new ArrayList(Arrays.asList(value2.split(",")));
			for(int i=0;i<days.size();i++){
				WebElement daystable = driver.findElement(By.xpath(OR.getProperty(object1)));
				List<WebElement> alldivs = daystable.findElements(By.tagName("div"));
				int index=1;
				for(WebElement div:alldivs){
					String classname = div.getAttribute("class");
					//Select repeat status
					if(index==1 && classname.equals("btn-group")){
						new Select(div.findElement(By.cssSelector("select[ng-model='data.occurence']"))).selectByVisibleText(value1);
					}
					if(classname.equals("btn-group weekly-check ng-scope")){
						String temp = div.findElement(By.tagName("label")).getText();

						if(temp.equals(days.get(i))){
							div.click();
							break;
						}
					}
					index++;
				}
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	//Add number of breaks for a program
	public String addBreaksToProgram(String object1, String object2, String value1, String value2){
		APP_LOGS.debug("Adding breaks in the program");
		String data_Flag = currentTestSuiteXLS.getCellData(currentTestCaseName, Constants.DATA_CORRECTNESS, currentTestDataSetID);
		try{
			//Get the number of breaks to be added in the program
			String numberOfBreaksToBeAdded = value1;
			String colorOfGrid = null;
			String actualErrMsg = null;
			if(!numberOfBreaksToBeAdded.equals("0") || (!numberOfBreaksToBeAdded.equals(""))){
				//Get the break duration to be provided
				String breaksDuration[] = value2.split(",");
				for(int i=0;i<=breaksDuration.length-1;i++){
					String oneBreakDuration = breaksDuration[i].toString();
					String breakduration_HH_MM_AMPM[] = (oneBreakDuration.split(" ")[0]).split(":");
					String breakhours = breakduration_HH_MM_AMPM[0];
					String breakminutes = breakduration_HH_MM_AMPM[1];
					String breakseconds = breakduration_HH_MM_AMPM[2];
					mainloop:
					for(int j=i+1; j<=Integer.parseInt(numberOfBreaksToBeAdded);j++){
						WebElement breakTable = driver.findElement(By.cssSelector(OR.getProperty(object1)));
						List<WebElement> breakTableAllRows = breakTable.findElements(By.tagName("tr").className("ng-scope"));
						List<WebElement> breakAllColumns = breakTableAllRows.get(i).findElements(By.tagName("td"));	
						WebElement breakdurationTable = breakAllColumns.get(1).findElement(By.tagName("table"));
						List<WebElement> a = breakdurationTable.findElements(By.tagName("tr"));
						List<WebElement> breakdurationAllColumns = a.get(1).findElements(By.tagName("td"));
						for(WebElement col:breakdurationAllColumns)
						{
							String classes = col.getAttribute("class");
							if(classes.equalsIgnoreCase(OR.getProperty("Hours_Section_TimeGrid"))){
								col.findElement(By.tagName("input")).clear();
								col.findElement(By.tagName("input")).sendKeys(breakhours);
								colorOfGrid = col.findElement(By.tagName("input")).getCssValue("border-color");
							}else if(classes.equalsIgnoreCase(OR.getProperty("Minutes_Section_TimeGrid"))){
								col.findElement(By.tagName("input")).clear();
								col.findElement(By.tagName("input")).sendKeys(breakminutes);
								colorOfGrid = col.findElement(By.tagName("input")).getCssValue("border-color");
							}else if(classes.equalsIgnoreCase(OR.getProperty("Seconds_Section_TimeGrid"))){
								col.findElement(By.tagName("input")).clear();
								col.findElement(By.tagName("input")).sendKeys(breakseconds);
								colorOfGrid = col.findElement(By.tagName("input")).getCssValue("border-color");
							}
							
						}
						//Click 				
						if(j<Integer.parseInt(numberOfBreaksToBeAdded)){
							driver.findElement(By.linkText(OR.getProperty("Add_Break_LinkText"))).click();
							break mainloop;
						}
						WebElement lastrow=null;
						//Verify the duration of the break
						if(j==Integer.parseInt(numberOfBreaksToBeAdded))
						{
							lastrow = breakTable.findElement(By.tagName("tr"));
							String lastrowCol = lastrow.findElement(By.tagName("td").className("ng-binding")).getText();

							System.out.println(lastrowCol);
						}
						if((j==Integer.parseInt(numberOfBreaksToBeAdded)) && (colorOfGrid.equals("gb(132, 53, 52)")) && (!data_Flag.equals(Constants.POSITIVE_DATA))){
							actualErrMsg = lastrow.findElement(By.tagName("td").className("form-group has-error")).getText();
							String expectedErrMsg = OR.getProperty("Invalid_Break_Duration_ErrMsg");
							if(actualErrMsg.equalsIgnoreCase(expectedErrMsg)){
								return Constants.KEYWORD_PASS;
							}else{
								return Constants.KEYWORD_FAIL;
							}

						}
					}
				}

			}else
			{
				driver.findElement(By.linkText(OR.getProperty("Remove_Break_LinkText"))).click();
			}

		}catch(Exception e){
			return Constants.KEYWORD_FAIL +e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	//Validate program creation
	public String validateFormCreation(String object1, String object2, String value1, String value2){
		APP_LOGS.debug("Validating Program creation");
		String data_Flag = currentTestSuiteXLS.getCellData(currentTestCaseName, Constants.DATA_CORRECTNESS, currentTestDataSetID);
		try{
			
			Boolean errorMsgsMatches=false;
			String allExpectedErrMsgs[] = value2.split(",");
			//Get the availability of the form on the UI
			List<WebElement> form = driver.findElements(By.className(OR.getProperty(object1)));
			if(form.size()>0 && (!data_Flag.equals(Constants.POSITIVE_DATA))){
				Thread.sleep(3000);
				for(int arrayIndex=0;arrayIndex<allExpectedErrMsgs.length;arrayIndex++){
				//Form is visible on the screen and there is a error message on the form
				//Capture the error message from the field
					List<WebElement> allActualErrMsgs = driver.findElements(By.className(OR.getProperty(value1)));
					for(WebElement actualErrMsg:allActualErrMsgs){	
						String expectedErrMsg = allExpectedErrMsgs[arrayIndex];
						if(actualErrMsg.getText().equalsIgnoreCase(expectedErrMsg))
						{
							errorMsgsMatches = true;
						}
					}
					if(arrayIndex==allExpectedErrMsgs.length-1){
						//click on close button
						driver.findElement(By.cssSelector(OR.getProperty(object2))).click();
					}
				}
				
				if(!errorMsgsMatches){
					return Constants.KEYWORD_FAIL +"Actual error message did not match expected error message.";
				}

			}
			else if(form.size()>0 && (data_Flag.equals(Constants.POSITIVE_DATA))){
				//click on close button
				driver.findElement(By.cssSelector(OR.getProperty(object2))).click();
				return Constants.KEYWORD_FAIL +"There is an unexpected error on the form";
			}
			else if(form.size()==0 && (!data_Flag.equals(Constants.POSITIVE_DATA))){
				return Constants.KEYWORD_FAIL +"Form should not have been submitted in case of negative data.";
			}
		
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}


	//Verify created program on the FPC page
	public String verifyCreatedProgramOnFPC(String object1, String object2, String value1, String value2){
		APP_LOGS.debug("Verifying the created program on the FPC");
		String data_Flag = currentTestSuiteXLS.getCellData(currentTestCaseName, Constants.DATA_CORRECTNESS, currentTestDataSetID);
		try{
			//Verify the FPC only if the data_flag is positive
			if(data_Flag.equals(Constants.POSITIVE_DATA)){
			//Get the days for which the program is created
			String days = value1;			
			String dates[] = (object1.split(" ")[0]).split(",");
			//Calculate the dates between start date and end date
			String startDate = dates[0];
			String endDate = dates[1];
			LocalDate start = LocalDate.parse(startDate);
			LocalDate end = LocalDate.parse(endDate);
			List<LocalDate> totalDates = new ArrayList<>();
			while (!start.isAfter(end)) {
				DayOfWeek DayForTheConsideredDate = start.getDayOfWeek();
				if (days.contains(DayForTheConsideredDate.toString())){
					totalDates.add(start);
				}

				start = start.plusDays(1);
			}

			System.out.println(totalDates);
			String programDetails[] = value2.split(",");
			for (int index=0; index < totalDates.size(); index++){
				String date = totalDates.get(index).toString();
				driver.findElement(By.cssSelector(OR.getProperty("SelectWeek_Button"))).click();
				selectdateInAnyCalendar(date);	
				Thread.sleep(2000);
				Date dateToBeSelected = parseDate(date);
				Date time = parseTime(object2);
				Thread.sleep(3000);
				int  rowOfTheTimeInFPC = getRowOfTheTimeInFPC(time);
				ArrayList xyValues = clickOnFpcControl(dateToBeSelected,rowOfTheTimeInFPC);
				Boolean programExists = getProgramValue((int)xyValues.get(0), (int)xyValues.get(1), programDetails[0],programDetails[1]);
				if (!programExists){
					return Constants.KEYWORD_FAIL+"Program does not exist for " + date;
				}

			}
		}
		}


		catch(Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	//Verify created templates on the grid
	public String verifyTemplates(String object1, String object2, String value1, String value2){
		APP_LOGS.debug("Verifying the template");
		String data_Flag = currentTestSuiteXLS.getCellData(currentTestCaseName, Constants.DATA_CORRECTNESS, currentTestDataSetID);
		try{
			driver.navigate().refresh();
			Thread.sleep(2000);
			//Verify the template only if the data flag is positive
			if(data_Flag.equals(Constants.POSITIVE_DATA))
			{
				WebElement templateGrid = driver.findElement(By.xpath("//*[@id='program_templates']/div[2]"));
				List<WebElement> allTemplates = templateGrid.findElements(By.tagName("div"));
				for(WebElement templateElement:allTemplates){
					String templateName = templateElement.getText();
					if(templateName.equalsIgnoreCase(value1)){
						return Constants.KEYWORD_PASS;
					}
				}
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}


	//Verifying the populated program duration from template

	public String verifyTemplateDuration(String object1, String object2,String value1, String value2)
	{
		APP_LOGS.debug("Verifying the populated program duration from template");
		try{
			int startTime = gettingTheStarttimeAndEndtime(OR.getProperty(object1));
			int endTime = gettingTheStarttimeAndEndtime(OR.getProperty(object2));
			int differenceBetweenStartTimeAndEndTime = endTime-startTime;
			String actualTemplateDuration = value1;
			if(Integer.toString(differenceBetweenStartTimeAndEndTime).equals(actualTemplateDuration))
			{
				return Constants.KEYWORD_PASS;
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	//Verifying the populated breaks from template for a program and total duration
	public String verifyPopulatedBreakDurationFromTemplate(String object1, String object2,String value1, String value2)
	{
		APP_LOGS.debug("Verifying the Numbeer of Breaks and their total duration ");
		try{
			String breakNumber = null;
			for(int j=0; j<=Integer.parseInt(value1);j++){
				WebElement breakTable = driver.findElement(By.cssSelector(OR.getProperty(object1)));
				List<WebElement> breakTableAllRows = breakTable.findElements(By.tagName("tr").className("ng-scope"));
				List<WebElement> breakAllColumns = breakTableAllRows.get(j).findElements(By.tagName("td"));	
				WebElement breakdurationTable = breakAllColumns.get(1).findElement(By.tagName("table"));
				List<WebElement> a = breakdurationTable.findElements(By.tagName("tr"));
				int i_rowNum=1;
				for (WebElement random:a)
				{
					List<WebElement> breakdurationAllColumns = random.findElements(By.tagName("td"));
					int i_colNum=1;
					for(WebElement b:breakdurationAllColumns)
					{

						if(i_colNum==1 && i_rowNum==Integer.parseInt(value1))
						{
							breakNumber = b.getText();
						}
						i_colNum++;

					}
					i_rowNum++;
				}

				int calculatedBreakDuration=0;
				if (breakNumber.equals(value1))
				{
					WebElement lastrow = breakTable.findElement(By.tagName("tr").className("break_summary"));
					String lastrowCol = lastrow.findElement(By.tagName("td").className("ng-binding")).getText();
					calculatedBreakDuration = Integer.parseInt(lastrowCol);
				}
				if(calculatedBreakDuration<=Integer.parseInt(value2)){
					return Constants.KEYWORD_PASS;
				}

			}
		}
		catch(Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	//Verify the default break duration 
	public String verifyDefaultBreakDuration(String object1, String object2,String value1, String value2)
	{
		APP_LOGS.debug("Verifying the default duration of the break");
		try{

			WebElement breakTable = driver.findElement(By.cssSelector(OR.getProperty(object1)));
			List<WebElement> breakTableAllRows = breakTable.findElements(By.tagName("tr").className("ng-scope"));
			List<WebElement> breakAllColumns = breakTableAllRows.get(0).findElements(By.tagName("td"));	
			WebElement breakdurationTable = breakAllColumns.get(1).findElement(By.tagName("table"));
			List<WebElement> a = breakdurationTable.findElements(By.tagName("tr"));
			List<WebElement> breakdurationAllColumns = a.get(1).findElements(By.tagName("td"));
			String actualHour = null;
			String actualMinutes = null;
			String actualSeconds = null;
			for(WebElement col:breakdurationAllColumns)
			{
				String classes = col.getAttribute("class");
				if(classes.equalsIgnoreCase(OR.getProperty("Hours_Section_TimeGrid"))){
					actualHour = col.findElement(By.tagName("input")).getAttribute("value");
				}else if(classes.equalsIgnoreCase(OR.getProperty("Minutes_Section_TimeGrid"))){
					actualMinutes = col.findElement(By.tagName("input")).getAttribute("value");
				}else if(classes.equalsIgnoreCase(OR.getProperty("Seconds_Section_TimeGrid"))){
					actualSeconds = col.findElement(By.tagName("input")).getAttribute("value");
				}
			}
			String expectedHour = "00";
			String expectedMinutes = "04";
			String expectedSeconds = "00";

			if(actualHour.equals(expectedHour) && actualMinutes.equals(expectedMinutes) && actualSeconds.equals(expectedSeconds)){
				return Constants.KEYWORD_PASS;
			}else{
				return Constants.KEYWORD_FAIL;
			}
		}
		catch(Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}

	}





	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~LOGICS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	//parse date which needs to be selected for the program creation
	public Date parseDate(String date) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd").parse(date);
	}

	//Parse the time for which the program needs to be created
	public Date parseTime(String time) throws ParseException {
		return new SimpleDateFormat("HH:mm:ss").parse(time);
	}

	//Get the row based on the time and date on the FPC
	public int getRowOfTheTimeInFPC(Date timeToClick) throws ParseException
	{
		//Verify the startTime and endTime of the FPC
		List<WebElement> allElements = driver.findElements(By.className("fc-time"));
		System.out.println(allElements.size());
		String fpcStartTime = allElements.get(0).getText();
		String fpcEndTime = allElements.get(allElements.size()-2).getText();
		if(fpcStartTime.equals("6am") && fpcEndTime.equals("5am"))
			APP_LOGS.info("FPC start time starts with 6am and FPC end time ends with 5am");

		String strFPCStartTime =  driver.findElement(By.className("fc-time")).getText();
		strFPCStartTime = strFPCStartTime.substring(0, strFPCStartTime.length() - 2);

		Calendar timeToClickOnFPC = Calendar.getInstance();
		timeToClickOnFPC.setTime(timeToClick); 

		Date dtFPCStartTime = parseTime(strFPCStartTime.concat(":00:00"));			
		Calendar FPCstartTime = Calendar.getInstance();
		FPCstartTime.setTime(dtFPCStartTime);

		int comparison = FPCstartTime.compareTo(timeToClickOnFPC);
		if (comparison > 0)
			timeToClickOnFPC.add(Calendar.DATE, 1);

		int rowNumber = 1;
		boolean DoWhile = true;

		FPCstartTime.add(Calendar.MINUTE, 30);
		comparison = FPCstartTime.compareTo(timeToClickOnFPC);

		if (comparison <= 0)
			DoWhile = true;
		else
			DoWhile = false;


		while (DoWhile)
		{
			rowNumber = rowNumber + 1;

			FPCstartTime.add(Calendar.MINUTE, 30);
			comparison = FPCstartTime.compareTo(timeToClickOnFPC);
			if (comparison <= 0)
				DoWhile = true;
			else
				DoWhile = false;
		}


		return rowNumber;

	}


	public ArrayList clickOnFpcControl(Date date,int rowOfTheFPC) throws InterruptedException, AWTException
	{
		ArrayList xy = new ArrayList();
		//Robot bot = new Robot();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);	
		String dayofweek = getDay(dayOfWeek);

		WebElement we = driver.findElement(By.xpath(".//*[@id='weekly_calender']/div[2]/div/table/tbody/tr/td/div/div/div[3]/table[2]/tbody/" + "tr[" + rowOfTheFPC + "]" + "/td[4]/div"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", we);

		Point location = we.getLocation();
		int y = location.y;
		xy.add(y);

		WebElement we2 = driver.findElement(By.className(dayofweek));
		Point location2 = we2.getLocation();
		int x = location2.x;
		xy.add(x);

		Thread.sleep(3000);
		/*
			bot.mouseMove(x+10, y);
			bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		 */
		return xy;

	}

	public String getDay(int dayOfWeek)
	{
		String weekday = "null";
		switch(dayOfWeek)
		{
		case 1:
			weekday = "fc-sun";
			return weekday;
		case 2:
			weekday = "fc-mon";
			return weekday;
		case 3:
			weekday = "fc-tue";
			return weekday;

		case 4:
			weekday = "fc-wed";
			return weekday;

		case 5:
			weekday = "fc-thu";
			return weekday;

		case 6:
			weekday = "fc-fri";
			return weekday;

		case 7:
			weekday = "fc-sat";
			return weekday;

		default:
			weekday = "test";
			return weekday;


		}
	}

	//Select the date
	public void selectdateInAnyCalendar(String date) throws InterruptedException
	{
		Boolean startOfMonth=false;
		String firstDateOfMonth = "01";
		List<String> monthList = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
		//Select start date and end date
		//String date = value1;      
		String date_dd_MM_YYYY[] = (date.split(" ")[0]).split("-");
		int expMonth = Integer.parseInt(date_dd_MM_YYYY[1]);
		String expDate = date_dd_MM_YYYY[2];
		int expYear = Integer.parseInt(date_dd_MM_YYYY[0]);
		boolean dateNotFound=true;
		//This loop will be executed till date not found
		while(dateNotFound){
			WebElement calendar = driver.findElement(By.cssSelector(OR.getProperty("Calendar_UI_Css")));
			List<WebElement> allRows1 = calendar.findElements(By.tagName("tr"));
			List<WebElement> allheaders=null;
			allheaders = allRows1.get(0).findElements(By.tagName("th"));
			String selectedCalendarmonth = null;
			selectedCalendarmonth = allRows1.get(0).getText();
			String month_Year[] = selectedCalendarmonth.split(" ");
			String month = month_Year[0];
			String year = month_Year[1];

			//If current year and month are same as expected month and year then go inside this condition
			if(monthList.indexOf(month)+1 == expMonth && (expYear == Integer.parseInt(year))){
				//Call select date function and set the datenotfound flag to false
				List<WebElement> noOfColumns=calendar.findElements(By.tagName("td"));
				for(WebElement calendarCell:noOfColumns){
					System.out.println(calendarCell.getText());
					if (startOfMonth){
						if(calendarCell.isEnabled() && calendarCell.getText().equals(expDate)){
							calendarCell.findElement(By.tagName("button")).click();
							Thread.sleep(2000);
							break;
						}
					}
					else{
						if(calendarCell.isEnabled() && calendarCell.getText().equals(firstDateOfMonth)){
							startOfMonth = true;
							if(calendarCell.isEnabled() && calendarCell.getText().equals(expDate)){
								calendarCell.findElement(By.tagName("button")).click();
								Thread.sleep(2000);
								break;
						}
					}
				}
				}
				dateNotFound = false;
			} 
			else if(monthList.indexOf(month)+1 < expMonth && (expYear == Integer.parseInt(year)) || expYear > Integer.parseInt(year)){

				//Click on the next link of the date picker
				allheaders.get(2).click();
			}
			else if(monthList.indexOf(month)+1 > expMonth && (expYear == Integer.parseInt(year)) || expYear < Integer.parseInt(year)){

				//Click on the previous link of the date picker
				allheaders.get(0).click();
			}
		}
	}

	//not a keyword
	public Boolean getProgramValue(int y, int x, String programName, String expectedNumberOfBreaks)
	{
		Boolean programFound = false;
		
		WebElement table3 = driver.findElement(By.cssSelector(".fc-content-skeleton"));
		List<WebElement> allProgramDetails = table3.findElements(By.className("fc-time-grid-event"));
		for(WebElement programDetail:allProgramDetails){
			if(programDetail.getText().contains(programName)){
				Point xyOfProgram  = programDetail.getLocation();
				int yOfProgram = xyOfProgram.y;
				int xOfProgram = xyOfProgram.x;	
				if(xOfProgram-2==x && yOfProgram+1==y){
					String actualNumberOfBreaks = programDetail.findElement(By.className("fc-breaks")).getText();
					if(actualNumberOfBreaks.equals(expectedNumberOfBreaks)){
						programFound = true;
						break;	
					}
				}
			}
		}
		return (programFound);
		/*
		WebElement hoverTable = driver.findElement(By.xpath("//*[@id='weekly_calender']/div[2]/div/table/tbody/tr/td/div/div/div[3]/table[1]/tbody"));
		List<WebElement> allRows = hoverTable.findElements(By.tagName("tr"));
		for(WebElement rowElement:allRows){
			System.out.println("ROW:" + rowElement.getText());
			List<WebElement> allcolumns = rowElement.findElements(By.tagName("td"));
			for(WebElement colElement:allcolumns){

				List<WebElement> createdProgramDiv = colElement.findElements(By.tagName("div").tagName("div").className("fc-event-container").tagName("a"));
				for(WebElement programNameLocation:createdProgramDiv){
					String extractedProgramDetails = programNameLocation.getText();		
					//System.out.println("COL:" + colElement.getText());
					if(extractedProgramDetails.contains(programName)){

						Point xyOfProgram  = programNameLocation.getLocation();
						int yOfProgram = xyOfProgram.y;
						int xOfProgram = xyOfProgram.x;	
						if(xOfProgram+2==x && yOfProgram-1==y){
							String actualNumberOfBreaks = programNameLocation.findElement(By.className("fc-breaks")).getText();
							if(actualNumberOfBreaks.equals(expectedNumberOfBreaks)){
								programFound = true;
								break;	
							}
						}
					}
				}
			}			
		}*/

		
	}


	/*Not a keyword
	 * Getting the startTime and endTime from the populated template for a program
	 */
	public int gettingTheStarttimeAndEndtime(String tableName)
	{	
		String startHour = null;
		String startMin  = null;
		String startAMPM = null;
		int hoursToMinutesTime = 0;
		WebElement startTimeGrid = driver.findElement(By.cssSelector(tableName));
		List<WebElement> startTimeRows = startTimeGrid.findElements(By.tagName("tr"));
		List<WebElement> startTimecolumns = startTimeRows.get(1).findElements(By.tagName("td"));
		for(WebElement columnElement:startTimecolumns){
			String className = columnElement.getAttribute("class");
			if(className.equalsIgnoreCase("form-group uib-time hours"))
			{
				startHour = columnElement.findElement(By.tagName("input")).getAttribute("value");
				hoursToMinutesTime = Integer.parseInt(startHour)*60;
			}
			else if(className.equalsIgnoreCase("form-group uib-time minutes"))
			{
				startMin = columnElement.findElement(By.tagName("input")).getAttribute("value");
			}
			else if(className.equalsIgnoreCase("uib-time am-pm"))
			{
				startAMPM = columnElement.findElement(By.tagName("button")).getText();
			}

		}

		int time24hourFormat = 0;
		if (startAMPM.equals("PM") )
		{
			time24hourFormat = hoursToMinutesTime+720;
		}
		int Time =time24hourFormat+Integer.parseInt(startMin);
		return Time;
	}
	
	
}







