package cz.czechitas.selenium;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestyPrihlasovaniNaKurz {

    private static final String URL_APLIKACE = "https://cz-test-dva.herokuapp.com/";

    WebDriver prohlizec;

    @BeforeEach
    public void setUp() {
//      System.setProperty("webdriver.gecko.driver", System.getProperty("user.home") + "/Java-Training/Selenium/geckodriver");
        System.setProperty("webdriver.gecko.driver", "C:\\Java-Training\\Selenium\\geckodriver.exe");
        prohlizec = new FirefoxDriver();
        prohlizec.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }


    @Test
    //Rodič s existujícím účtem se musí být schopen přihlásit do webové aplikace.
    public void rodicSeMuzePrihlasit() {
        prohlizec.navigate().to(URL_APLIKACE);
        WebElement prihlaseni = prohlizec.findElement(By.linkText("Přihlásit"));
        prihlaseni.click();
        WebElement polickoEmail = prohlizec.findElement(By.id("email"));
        polickoEmail.sendKeys("kocurkova.magda@gmail.com");
        WebElement polickoHeslo = prohlizec.findElement(By.id("password"));
        polickoHeslo.sendKeys("Heslo123.");
        WebElement tlacitkoPrihlasit = prohlizec.findElement(By.xpath("//form//button[contains(text(),'Přihlásit')]"));
        tlacitkoPrihlasit.click();
        WebElement potvrzeniOprihlaseni = prohlizec.findElement(By.className("dropdown-toggle"));
    }


    @Test
    //Rodič s existujícím účtem musí být schopen přihlásit svoje dítě na kurz.
    //Varianta, že rodič nejprve vybere kurz a potom se přihlásí ke svému účtu, vyplní přihlášku, odešle ji a nakonec ve svém seznamu přihlášek zkontroluje, že ji systém eviduje.
    public void rodicMuzePrihlasitDiteNaKurzI() {
        prohlizec.navigate().to(URL_APLIKACE);
        WebElement kurzyWebu = prohlizec.findElement(By.className("btn-primary"));
        kurzyWebu.click();
        WebElement vytvoritPrihlasku = prohlizec.findElement(By.linkText("Vytvořit přihlášku"));
        vytvoritPrihlasku.click();
        WebElement polickoEmail = prohlizec.findElement(By.id("email"));
        polickoEmail.sendKeys("kocurkova.magda@gmail.com");
        WebElement polickoHeslo = prohlizec.findElement(By.id("password"));
        polickoHeslo.sendKeys("Heslo123.");
        WebElement tlacitkoPrihlasit = prohlizec.findElement(By.xpath("//form//button[contains(text(),'Přihlásit')]"));
        tlacitkoPrihlasit.click();
        vytvoritPrihlasku();
    }

    public void vytvoritPrihlasku() {
        WebElement polickoVyberteTermin = prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div/form/table/tbody/tr[2]/td[2]/div/button/div/div/div"));
        polickoVyberteTermin.click();
        WebElement datumKurzu = prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div/form/table/tbody/tr[2]/td[2]/div/div/div[1]/input"));
        datumKurzu.sendKeys("21.06" + Keys.ENTER);
        WebElement polickoKrestniJmeno = prohlizec.findElement(By.xpath("//*[@id=\"forename\"]"));
        polickoKrestniJmeno.sendKeys("Pepa");
        WebElement polickoPrijmeni = prohlizec.findElement(By.xpath("//*[@id=\"surname\"]"));
        polickoPrijmeni.sendKeys("Novak");
        WebElement polickoDatumNarozeni = prohlizec.findElement(By.id("birthday"));
        polickoDatumNarozeni.sendKeys("01.01.2000");
        WebElement bankovniPrevod = prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div/form/table/tbody/tr[8]/td[2]/span[1]/label"));
        bankovniPrevod.click();
        WebElement polickoSouhlasSPodminkami = prohlizec.findElement(By.xpath("//label[@for='terms_conditions']"));
        polickoSouhlasSPodminkami.submit();
        WebElement polickoVytvoritPrihlasku = prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div/form/table/tbody/tr[11]/td[2]/input"));
        polickoVytvoritPrihlasku.click();
    }



    @Test
   // Rodič s existujícím účtem musí být schopen přihlásit svoje dítě na kurz.
    //Varianta, že se rodič nejprve přihlásí ke svému účtu a potom vybere kurz, vyplní, odešle, zkontroluje v seznamu.
    public void rodicMuzePrihlasitDiteNaKurzII() {
        rodicSeMuzePrihlasit();
        WebElement vytvoritNovouPrihlasku = prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div/div[1]/a"));
        vytvoritNovouPrihlasku.click();
        WebElement trimesicniKurz = prohlizec.findElement(By.xpath("/html/body/div/div/div[1]/div[4]/div/div[2]/a"));
        trimesicniKurz.click();
        WebElement vybratPrihlasku = prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div[2]/div/div/div[2]/a"));
        vybratPrihlasku.click();
        vytvoritPrihlasku();
    }


    @Test
    //Jeden další scénář dle své úvahy - predvyplněné jméno přihlášeného uživatele při vytvoření nové přihlášky
    public void predvyplneneJmenoPriZalozeniPrihlasky() {
        prohlizec.navigate().to(URL_APLIKACE);
        WebElement kurzyWebu = prohlizec.findElement(By.xpath("/html/body/div/div/div[1]/div[2]/div/div[2]/a"));
        kurzyWebu.click();
        WebElement vytvoritPrihlasku = prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div[2]/div/div/div[2]/a"));
        vytvoritPrihlasku.click();
        WebElement polickoEmail = prohlizec.findElement(By.id("email"));
        polickoEmail.sendKeys("kocurkova.magda@gmail.com");
        WebElement polickoHeslo = prohlizec.findElement(By.id("password"));
        polickoHeslo.sendKeys("Heslo123.");
        WebElement tlacitkoPrihlasit = prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div/div/form/div[3]/div/button"));
        tlacitkoPrihlasit.click();
        WebElement zakonyZastupce = prohlizec.findElement(By.id("parent_name"));
        Assertions.assertEquals("MagdalenaKocurkova", zakonyZastupce.getAttribute("value"));

    }


    @AfterEach
    public void tearDown() {
        prohlizec.close();
}}
