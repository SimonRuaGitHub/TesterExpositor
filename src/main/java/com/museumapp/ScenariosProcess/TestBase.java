/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.museumapp.ScenariosProcess;

import java.util.logging.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author Simon Rua
 */
public class TestBase 
{
    protected WebDriver driver;
    private static final Logger LOGGER  = Logger.getLogger(TestBase.class.getName());
    private static final String PATH_CHROME_DRIVER = "S:\\SEMESTRE 12\\Proyecto integrador\\chromeDriver\\chromedriver.exe";
    private static final String CHROME_DRIVER_TYPE = "webdriver.chrome.driver";
    private static final Dimension DIMENSION_SCREEN = new Dimension(1024, 1024);

    public void settingWebDriver()
    {
	System.setProperty(CHROME_DRIVER_TYPE, PATH_CHROME_DRIVER);
	driver = new ChromeDriver();
	driver.manage().window().maximize();
    }
    
    public void closingWebDriver()
    {
        driver.close();
    }
}
