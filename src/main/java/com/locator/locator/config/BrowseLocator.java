package com.locator.locator.config;

import com.locator.locator.common.Locator_Model;
import com.locator.locator.repo.Locator_Repo;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class BrowseLocator implements InitializingBean {
    private FirefoxDriver driver = null;
    private String url[] = {"https://www.betium.it/Holder.aspx?page=bet"};
    private String codes[] = {"CALCIO"};
    private HashMap<String, String> handlers = new HashMap<>();

    @Autowired
    private Locator_Repo locatorRepo;

    public void initialise() throws Exception {
        System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
        System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);

        driver = new FirefoxDriver();

        for (int i = 0; i < url.length - 1; i++) {
            driver.executeScript("window.open()");
        }

        ArrayList<String> windowsHandles = new ArrayList<>(driver.getWindowHandles());

        for (int i = 0; i < url.length; i++) {
            handlers.put(codes[i], windowsHandles.get(i));
        }

        scrape("http://www.aalnc.org/page/search");
    }

    public void scrape(String link) throws InterruptedException, IOException {
        driver.get(link);
        Locator_Model locatorModel = null;
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        WebElement selectInput = driver.findElementByXPath("/html/body/div[4]/div/div/div/div/div[2]/div[2]/div/div[1]/form/div[6]/div/select");

        Thread.sleep(5000);
        WebElement searchButton = driver.findElementByXPath("/html/body/div[4]/div/div/div/div/div[2]/div[2]/div/div[1]/form/p/input");
        jse.executeScript("arguments[0].scrollIntoView(true);", searchButton);
        jse.executeScript("arguments[0].click();", searchButton);
        Thread.sleep(2000);
//        WebElement elementByXPath1 = driver.findElementByXPath("/html/body/div[4]/div/div/div/div/div[2]/div[2]/div");
//        WebElement webElement = elementByXPath1.findElements(By.xpath("./*")).get(5);
//        List<WebElement> elements = webElement.findElements(By.xpath("./*"));
//        WebElement webElement1 = elements.get(2);
//        WebElement webElement2 = elements.get(3);
////        WebElement tbody1 = webElement1.findElement(By.tagName("tbody"));
        boolean b = true;
        int count = 0;
        while (true) {
            WebElement elementByXPath1 = driver.findElementByXPath("/html/body/div[4]/div/div/div/div/div[2]/div[2]/div");
            WebElement webElement = elementByXPath1.findElements(By.xpath("./*")).get(5);
            List<WebElement> elements = webElement.findElements(By.xpath("./*"));
            WebElement webElement1 = elements.get(2);
            WebElement webElement2 = elements.get(3);
            WebElement tbody1 = webElement1.findElement(By.tagName("tbody"));


            for (WebElement element : tbody1.findElements(By.xpath("./*"))) {
                Thread.sleep(5000);
                WebElement cilckLink = element.findElements(By.tagName("td")).get(0).findElement(By.tagName("a"));
                jse.executeScript("arguments[0].scrollIntoView(true);", cilckLink);
                jse.executeScript("arguments[0].click();", cilckLink);
                String cuName = cilckLink.getAttribute("innerText");

                Thread.sleep(2000);
                WebElement elementByXPath = driver.findElementByXPath("/html/body/div[8]/div/div/div[2]/table");
                for (WebElement tbody : elementByXPath.findElements(By.xpath("./*"))) {
                    String cuEmail = tbody.findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).get(1).findElement(By.tagName("a")).getAttribute("href");
                    String cuPhone = tbody.findElements(By.tagName("tr")).get(2).findElements(By.tagName("td")).get(1).getAttribute("innerText");
                    String cuAddress = tbody.findElements(By.tagName("tr")).get(3).findElements(By.tagName("td")).get(1).getAttribute("innerText");
                    String emailCus = cuEmail.split(":")[1];
                    System.out.println("Customer Name :" + cuName);
                    System.out.println("Customer Email :" + emailCus);
                    System.out.println("Customer Phone :" + cuPhone);
                    System.out.println("Customer Address :" + cuAddress);
                    locatorModel = new Locator_Model();
                    locatorModel.setCustName(cuName);
                    locatorModel.setCustEmail(cuEmail);
                    locatorModel.setCustPhone(cuPhone);
                    locatorModel.setCustAddress(cuAddress);
                    locatorRepo.save(locatorModel);
                    System.out.println("============================================");

                    WebElement cloceBut = driver.findElementByCssSelector("#popclick > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > button:nth-child(1)");
                    cloceBut.click();
                    continue;
                }
                if (count == 24) {
                    System.err.println("++++++++=========================");
                    if (b) {
                        WebElement next = webElement2.findElement(By.tagName("ul")).findElements(By.xpath("./*")).get(5).findElement(By.tagName("a"));
                        jse.executeScript("arguments[0].scrollIntoView(true);", next);
                        jse.executeScript("arguments[0].click();", next);
                        System.err.println("++++++++=========================");
                        count = 0;
                        Thread.sleep(5000);
                        b = false;
                    } else {
                        WebElement next = webElement2.findElement(By.tagName("ul")).findElements(By.xpath("./*")).get(7).findElement(By.tagName("a"));
                        jse.executeScript("arguments[0].scrollIntoView(true);", next);
                        jse.executeScript("arguments[0].click();", next);
                        System.err.println("++++++++=========================");
                        count = 0;
                        Thread.sleep(5000);
                    }
                }
                count++;
            }
        }
//        WebElement allCustomer = driver.findElementByXPath("/html/body/div[4]/div/div/div/div/div[2]/div[2]/div/div[2]/table/tbody");
//        for (WebElement element : allCustomer.findElements(By.xpath("./*"))) {
//            WebElement cilckLink = element.findElements(By.tagName("td")).get(0).findElement(By.tagName("a"));
//            String cuName = cilckLink.getAttribute("innerText");
//            jse.executeScript("arguments[0].scrollIntoView(true);", cilckLink);
//            jse.executeScript("arguments[0].click();", cilckLink);
//
//            Thread.sleep(1000);
//            WebElement elementByXPath = driver.findElementByXPath("/html/body/div[8]/div/div/div[2]/table");
//            for (WebElement tbody : elementByXPath.findElements(By.xpath("./*"))) {
//                String cuEmail = tbody.findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).get(1).findElement(By.tagName("a")).getAttribute("href");
//                String cuPhone = tbody.findElements(By.tagName("tr")).get(2).findElements(By.tagName("td")).get(1).getAttribute("innerText");
//                String cuAddress = tbody.findElements(By.tagName("tr")).get(3).findElements(By.tagName("td")).get(1).getAttribute("innerText");
//                System.out.println("Customer Name :" + cuName);
//                System.out.println("Customer Email :" + cuEmail);
//                System.out.println("Customer Phone :" + cuPhone);
//                System.out.println("Customer Address :" + cuAddress);
//                System.out.println("============================================");
//
//                locatorModel = new Locator_Model();
//                locatorModel.setCustName(cuName);
//                locatorModel.setCustEmail(cuEmail);
//                locatorModel.setCustPhone(cuPhone);
//                locatorModel.setCustAddress(cuAddress);
//                locatorRepo.save(locatorModel);
//
//                WebElement cloceBut = driver.findElementByCssSelector("#popclick > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > button:nth-child(1)");
//                cloceBut.click();
//                continue;
//            }
//        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.initialise();
    }
}
