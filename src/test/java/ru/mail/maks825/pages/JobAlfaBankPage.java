package ru.mail.maks825.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class JobAlfaBankPage {
    public JobAlfaBankPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public WebDriver driver;

    @FindBy(xpath = "//span[contains(text(),'О работе в банке')]")
    private WebElement aboutWorkingInBankLink;

    @FindBy(xpath = "//div[@class='message']")
    public WebElement infoTitle;

    @FindBy(xpath = "//h1[@class='_underlined']")
    private WebElement aboutWorkingInBankTitle;

    public void goToAboutWorkingInBank() {
        aboutWorkingInBankLink.click();
    }

    public List<WebElement> getInfoParagraphesText() {
        return driver.findElements(By.xpath("//div[@class='info']//*"));
    }

    public String getAboutWorkingInBankTitle() {
        return aboutWorkingInBankTitle.getAttribute("textContent");
    }
}
