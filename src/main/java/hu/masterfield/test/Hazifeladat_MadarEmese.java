package hu.masterfield.test;

import hu.masterfield.browser.WebBrowser;
import hu.masterfield.browser.WebBrowserSetting;
import hu.masterfield.browser.WebBrowserType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class Hazifeladat_MadarEmese {

    private WebDriver driver;
    private String baseURL;

    @BeforeEach
    public void setup() {
        driver = WebBrowser.createDriver(WebBrowserType.Chrome);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30000));
        baseURL = WebBrowserSetting.getBaseURL();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testHaziFeladat() throws InterruptedException, IOException {
        driver.get(baseURL);
        Thread.sleep(3000);

        driver.findElement(By.linkText("EXERCISE")).click();

        // Termékek kigyűjtése
        List<WebElement> products = driver.findElements(By.cssSelector("li._RPA_product_infos"));
        //By.xpath("//div/div/div/div/ul/li"));
        //By.xpath("//div/div/div/div/ul/li/a"));
        System.out.println("A termékek száma: " + products.size());

        // Ellenőrzés: 186 db termék
        if (products.size() != 186) {
            System.out.println("Hiba: Nem 186 db termék található az oldalon!");
        }
        double maxSaving = Double.MIN_VALUE;
        double minSaving = Double.MAX_VALUE;
        WebElement maxSavingProduct = null;
        WebElement minSavingProduct = null;

        // Termékek feldolgozása
        for (WebElement product : products) {

            WebElement productIdElement = product.findElement(By.cssSelector("li._RPA_product_infos a[rpa_product_id]"));
            //(By.xpath("//div/div/div/div/ul/li/a"));
            //(By.xpath("//li[contains(@class '_RPA_product_infos')]//a[@rpa_product_id]"));

            String productId = productIdElement.getAttribute("rpa_product_id");

            WebElement productNameElement = product.findElement(By.cssSelector("span._RPA_product_name"));
            String productName = productNameElement.getText();

            WebElement productOldPriceElement = product.findElement(By.cssSelector("span._RPA_product_old_price"));
            String oldPriceText = productOldPriceElement.getText();

            WebElement productActualPriceElement = product.findElement(By.cssSelector("span._RPA_product_actual_price"));
            String actualPriceText = productActualPriceElement.getText();

            // Tisztítás: csak a számok és a pont karakter megtartása
            oldPriceText = oldPriceText.replaceAll("[^\\d.]", "");
            actualPriceText = actualPriceText.replaceAll("[^\\d.]", "");

            // Tizedes vesszővel van elválasztva
            oldPriceText = oldPriceText.replace(",", "");
            actualPriceText = actualPriceText.replace(",", "");

            // Konvertálás double típusba
            double oldPrice = Double.parseDouble(oldPriceText);
            double actualPrice = Double.parseDouble(actualPriceText);

            // Árkülönbség számolása
            double saving = oldPrice - actualPrice;

            // Termék adatainak kiírása a konzolra
            System.out.println(productId + " : " + productName + " : " + oldPrice + " -> " + actualPrice + " ---> " + saving);

            // Legtöbbet és legkevesebbet spóroló termékek ellenőrzése
            if (saving > maxSaving) {
                maxSaving = saving;
                maxSavingProduct = product;
            }

            if (saving < minSaving) {
                minSaving = saving;
                minSavingProduct = product;
            }
        }
            // Legtöbbet és legkevesebbet spóroló termékek kiírása
            System.out.println("Legtöbbet spóroló termék: " + "id: " + maxSavingProduct.findElement(By.cssSelector("li._RPA_product_infos a[rpa_product_id]")).getAttribute("rpa_product_id") + " : " + maxSavingProduct.findElement(By.cssSelector("span._RPA_product_name")).getText() + " ---> " + maxSaving);
            System.out.println("Legkevesebbet spóroló termék: " + "id: " + minSavingProduct.findElement(By.cssSelector("li._RPA_product_infos a[rpa_product_id]")).getAttribute("rpa_product_id") + " : " + minSavingProduct.findElement(By.cssSelector("span._RPA_product_name")).getText() + " ---> " + minSaving);
    }
}