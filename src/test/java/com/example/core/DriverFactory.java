package com.example.core;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

  private static WebDriver driver;

  private DriverFactory() {
  }

  public static WebDriver getDriver() {
    if (driver == null) {
      switch (Propriedades.browser) {
        case FIREFOX:
          driver = new FirefoxDriver();
          break;
        case MSEDGE:
          System.setProperty("webdriver.edge.driver",
              "C:\\Users\\guss_\\scoop\\apps\\edgedriver\\current\\msedgedriver.exe");
          driver = new EdgeDriver();
          break;
        case CHROME:
          driver = new ChromeDriver();
          break;
      }
      driver.manage().window().setSize(new Dimension(860, 1032));
      driver.manage().window().setPosition(new Point(1700, 0));
    }
    return driver;
  }

  public static void killDriver() {
    if (driver != null) {
      driver.quit();
      driver = null;
    }
  }
}
