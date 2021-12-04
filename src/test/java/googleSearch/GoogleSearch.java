package googleSearch;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;
import static variables.Variables.*;

public class GoogleSearch
{
    @Test
    void googleSearch()
    {
        //Open Google and try to write in search bar

        driver.get(url);
        driver.manage().window().maximize();
        WebElement searchField = driver.findElement(By.name("q"));
        searchField.sendKeys(searchTerm);

        // Verify that the suggestions appears related to search term

        List<WebElement> suggestions = new WebDriverWait(driver, 1).until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//ul[@role='listbox']//li/descendant::span"), 0));
        for (int i = 0; i < suggestions.size(); i = i + 2)
        {
            Assert.assertTrue(suggestions.get(i).getText().contains(searchTerm));
        }

        //Submit the search term and verify that the url contains the search term

        searchField.submit();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains(searchTerm));

        // Verify that the results related to the search term

        List<WebElement> results = driver.findElements(By.cssSelector(".r"));
        for (int i = 0; i < results.size(); i++)
        {
            Assert.assertTrue(results.get(i).getText().contains(searchTerm), "Search result validation failed at instance [ + i + ].");
        }
    }
}
