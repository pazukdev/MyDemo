package com.generation_p.hotel_demo.services;

import com.generation_p.hotel_demo.HotelUI;
//import io.github.bonigarcia.wdm.WebDriverManager;
import com.generation_p.hotel_demo.entity.Hotel;
import com.generation_p.hotel_demo.entity.HotelTagLink;
import com.generation_p.hotel_demo.entity.Tag;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// To run test run class
// By default test adds 3 new hotel categories and 4 new hotels
// To change number of new items edit invocation of addCategory(int quantityOfNewCategories)
// and addHotel(int quantityOfNewHotels) methods (change arguments)

public class DemoService {
    private static WebDriver driver;
    WebDriverWait wait;

    static Thread demoThread;

    private TagService tagDAO = ServiceProvider.getTagService();
    private HotelService hotelDAO = ServiceProvider.getHotelService();
    private HotelTagLinkService hotelTagLinkService = ServiceProvider.getHotelTagLinkTagService();


    public void runDemo(String browser) {
        try {
            demoThread = new Thread(new Runnable() {
                public void run() {
                    try {
                        System.out.println("Demo thread runned");
                        executeTasks(browser);
                        System.out.println("Demo thread run() finished");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println("Demo thread was interrupted");
                    }
                }
            });
            demoThread.start();
        } catch (Exception e) {
            e.printStackTrace();
            if(demoThread != null) {
                demoThread.interrupt();
                System.out.println("Demo thread was interrupted");
            }
        }
    }

    public static void stopDemo() {
        demoThread.interrupt();
    }


    private void executeTasks(String browser) throws InterruptedException {
        prepareBrowser(browser);

        String baseUrl = "http://localhost:8080/demo-1.0-SNAPSHOT/#!hotels";

        driver.get(baseUrl); // launch browser and open address page
        driver.manage().window().maximize();

        Thread.sleep(2000);

        System.out.println("Test started");

        addFacilitiesScenario();

        System.out.println("Test finished");

        //close browser //driver.close();
    }

    private void prepareBrowser(String browser) {
        /*if(browser.equals("Chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }

        if(browser.equals("FireFox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }

        if(browser.equals("IE")) {
            WebDriverManager.iedriver().setup();
            driver = new InternetExplorerDriver();
        }*/

        // Chrome
        System.setProperty("webdriver.chrome.driver","C:\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }


    // add facilities
    private void addFacilitiesScenario() throws InterruptedException {
        System.out.println("Add facilities scenario started");

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName("a")));

        List<WebElement> allLinks = driver.findElements(By.tagName("a"));
        int i = 0;
        for (WebElement element : allLinks){
            element.click();
            Thread.sleep(2000);
            ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
            driver.switchTo().window(tabs2.get(1));
            Thread.sleep(2000);

            List<Hotel> hotelsList = hotelDAO.findAll();
            Hotel hotel = hotelsList.get(i);

            Tag tag = null;
            List<WebElement> allHotelFacilities = driver.findElements(By.className("important_facility"));
            for(WebElement facility : allHotelFacilities) {
                String facilityName= facility.getText();

                // check if we met this facility for the first time
                // if yes - we add it to TAG table
                List tagsActualList = tagDAO.findAll();
                if(!tagsActualList.contains(facilityName)) {
                    tag = new Tag();
                    tag.setTagName(facilityName);
                    tagDAO.save(tag);
                }

                HotelTagLink hotelTagLink = new HotelTagLink();
                // все дальше не успеваю!!!!!!!!!!!!!!!!!!!!!!!
                /*hotelTagLink.setHotelId((Long.valueOf(tag.getId().toString()));
                hotelTagLink.setTagId(Long.valueOf(tag.getId().toString()));*/



            }

            driver.close();
            driver.switchTo().window(tabs2.get(0));

            i++;
        }

        System.out.println("Add facilities scenario finished");
    }


    private void addCategory(int quantityOfNewCategories) throws InterruptedException {
        for(int i = 1; i <= quantityOfNewCategories; i++) {

            Thread.sleep(2000);

            WebElement addCategoryButton = driver.findElement(By.id("addCategoryButton"));

            Thread.sleep(2000);

            addCategoryButton.click();

            Thread.sleep(2000);

            WebElement categoryNameField = driver.findElement(By.id("categoryNameTextField"));

            Thread.sleep(2000);

            categoryNameField.sendKeys("0 New test category " + i);

            Thread.sleep(2000);

            WebElement updateButton = driver.findElement(By.id("updateButton"));

            Thread.sleep(2000);

            updateButton.click();

            Thread.sleep(2000);
        }
    }




}