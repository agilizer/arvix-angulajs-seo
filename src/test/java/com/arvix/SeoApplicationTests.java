package com.arvix;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.SessionNotFoundException;

import com.gargoylesoftware.htmlunit.StringWebResponse;
import com.gargoylesoftware.htmlunit.UnexpectedPage;
import com.paulhammant.ngwebdriver.NgWebDriver;

public class SeoApplicationTests {

	public static void main(String args[]) {
		HtmlUnitDriver driver = new HtmlUnitDriver(true);
		try {
			
			// This will block for the page load and any
			// associated AJAX requests
			// PhantomJSDriver
			driver.get("http://127.0.0.1:3000");
			NgWebDriver ngWebDriver = new NgWebDriver(driver);
			System.out.println("wiat forrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr-->");
			ngWebDriver.waitForAngular2RequestsToFinish();
			// You can get status code unlike other Selenium drivers.
			// It blocks for AJAX requests and page loads after clicks
			// and keyboard events.
			// Returns the page source in its current state, including
			// any DOM updates that occurred after page load
			System.out.println("donnnnnnnnnnnnnnnnnnnnnnnnnnnn-->"+driver.getPageSource());
			
			// Close the browser. Allows this thread to terminate.
			
		} catch (Exception e) {
			throw new WebDriverException(e);
		}finally{
			System.out.println("finally0------>"+driver.getPageSource());
			driver.quit();
		}

	}

}
