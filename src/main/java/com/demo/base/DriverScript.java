package com.demo.base;

import java.io.File;
import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import com.demo.actions.TestEngine;
import com.demo.support.RemoteGridSupport;
import com.demo.support.WebDriverFactory;
import com.demo.support.Xls_Reader;
import com.demo.util.Util;

public class DriverScript {
	
	public static String SEPARATOR = File.separator;
	public static String TESTDATAPATH = System.getProperty("user.dir") + "/src/main/java/com/demo/data";
	public static Xls_Reader xls = new Xls_Reader (TESTDATAPATH + "/testdata.xlsx");
	public static WebDriver driver = null;
	public static String baseUrl = null;
	public static String BROWSER = null;
	public static String testCaseID = null;
	public static int rowNum = 2;
	
	public static boolean isRunnable (String tcId, int row) throws NumberFormatException, IOException {
		rowNum = row;
		String runmode = xls.getCellData("TestData","Runmode",rowNum);
		try {
			if (runmode.toUpperCase().equals("YES")) {
				BROWSER = Util.getProperty ("Browser");
				driver = null;
				if (driver == null) {
					baseUrl = Util.getProperty ("AppURL");
					System.out.println("App URL: " + baseUrl);
					String platform = Util.getProperty("RemoteExecution");
					if (platform.toUpperCase().equals("YES")) {
						RemoteGridSupport.setUp(BROWSER, tcId);
					} else {
						WebDriverFactory.initialize();
					}
					return true;
				}
			} else {
				System.out.println("Please check the runmode of your test case");
				return false;
			}
		} catch (Throwable t) {
			t.printStackTrace();
			return false;
		}
		return false;
	}
	
	@AfterMethod
	public void tearDown() {
		TestEngine.quit();
	}
	
}
