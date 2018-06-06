package ru.mail.maks825.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AlfaBankPage {
    public AlfaBankPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public WebDriver driver;

    @FindBy(xpath = "//a[contains(text(),'Вакансии')]")
    private WebElement vacanciesLink;

    public void goToVacanciesPage() {
        vacanciesLink.click();
    }
}
