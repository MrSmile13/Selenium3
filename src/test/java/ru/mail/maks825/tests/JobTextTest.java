package ru.mail.maks825.tests;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.mail.maks825.WebTextParser;
import ru.mail.maks825.pages.AlfaBankPage;
import ru.mail.maks825.pages.JobAlfaBankPage;
import ru.mail.maks825.pages.YandexPage;
import utils.ConfigProperties;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JobTextTest {
    private static WebDriver driver;
    private static YandexPage yandexPage;
    private static AlfaBankPage alfaBankPage;
    private static JobAlfaBankPage jobAlfaBankPage;

    private static String yandexWindow;
    private static String browserTitle;

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", ConfigProperties.getProperty("chromeDriver"));
        driver = new ChromeDriver();
        yandexPage = new YandexPage(driver);
        alfaBankPage = new AlfaBankPage(driver);
        jobAlfaBankPage = new JobAlfaBankPage(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void step1_openYandexPage() {
        driver.get(ConfigProperties.getProperty("URL"));
        Assert.assertEquals("https://www.yandex.ru/", driver.getCurrentUrl());

        yandexWindow = driver.getWindowHandle();
        browserTitle = driver.getTitle();
    }

    @Test
    public void step2_openAlfaBankSite() {
        yandexPage.searchField.sendKeys("Альфа банк");
        driver.findElement(By.xpath("//div[@class='popup__content']//li//span[text()='alfabank.ru']")).click();

        Set<String> handles = driver.getWindowHandles();
        for (String windowHandle : handles) {
            if (!windowHandle.equals(yandexWindow)) {
                driver.switchTo().window(windowHandle);
            }
        }

        Assert.assertEquals("https://alfabank.ru/", driver.getCurrentUrl());
    }

    @Test
    public void step3_goToVacanciesPage() {
        alfaBankPage.goToVacanciesPage();
        Assert.assertTrue(driver.getCurrentUrl().contains("http://job.alfabank.ru/"));
    }

    @Test
    public void step4_goToAboutWorkingInBank() {
        jobAlfaBankPage.goToAboutWorkingInBank();
        Assert.assertEquals("О работе в банке", jobAlfaBankPage.getAboutWorkingInBankTitle());
    }

    @Test
    public void step5_copyTextToFile() {
        Calendar time = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");

        Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();

        String outputFileName = sdf.format(time.getTime()) + "_" + cap.getBrowserName() + "_" + browserTitle + ".docx";

        WebTextParser.parseWebText(outputFileName, jobAlfaBankPage);
    }

    @AfterClass
    public static void tearDown() {
        driver.close();
        driver.switchTo().window(yandexWindow);
        driver.close();
    }


}