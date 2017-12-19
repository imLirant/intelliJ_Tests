package Test;

import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class FirstTest {

    private static WebDriver driver;

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "chromedriver//chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("http://193.189.127.179:5010/timeTable/group");
    }
    @Test
    public void Test_1()//gotoStudent
    {
        String studentXpath = "/html/body/div[3]/div[1]/ul/li/ul/li[4]/a";
        System.out.append("Запущен тест 1\n");
        WebElement student = driver.findElement(By.xpath(studentXpath));
        String name;
        if (student.isDisplayed())
        {
            name = student.getText();
            student.click();
        }
        else
        {
            WebElement menu = driver.findElement(By.xpath("/html/body/div[3]/div[1]/ul/li/a"));
            menu.click();
            student = driver.findElement(By.xpath(studentXpath));
            name = student.getText();
            student.click();
        }
        System.out.append("Перешел на страницу '" + name + "'\n");
    }

    @Test
    public void Test_2() //selectEN
    {
        System.out.append("Запущен тест 2\n");
        String Lang = "EN";
        if (!ChangeLang(Lang))
            Assert.fail("Не удалось изменить язык");
        else System.out.append("Язык страницы установлен на '" + Lang + "'\n");
    }

    @Test
    public void Test_3()
    {
        System.out.append("Запущен тест 3\n");
        Assert.assertEquals("http://193.189.127.179:5010/timeTable/student", driver.getCurrentUrl());

        String Faculty  = "Факультет електроніки та комп'ютерної інженерії";
        String Course = "4";
        String Group = "КІ-14-1";
        String Student = "Дудник С. С.";

        if(select("faculty", Faculty)) System.out.append("Был выбран Факультет: " + Faculty + "\n");
        else Assert.fail("Ошибка при выполнении выбора факультета");

        if (select("course", Course)) System.out.append("Был выбран курс: " + Course + "\n");
        else Assert.fail("Ошибка при выполнении выбора курса");

        if (select("group", Group)) System.out.append("Была выбрана группа: " + Group + "\n");
        else Assert.fail("Ошибка при выполнении выбора группы");

        if (select("student", Student)) System.out.append("Был выбран Студент: " + Student + "\n");
        else Assert.fail("Ошибка при выполнении выбора студента");

    }

    @Test
    public void Test_4()
    {
        System.out.append("Запущен тест 4\n");
        Assert.assertEquals("http://193.189.127.179:5010/timeTable/student", driver.getCurrentUrl());
        String weekButtonPath = "/html/body/div[3]/div[2]/div[2]/div[2]/div/div/div[2]/table/tbody/tr/td[3]/span[2]";
        WebElement weekButton;

        try
        {
            weekButton = driver.findElement(By.xpath(weekButtonPath));
            weekButton.click();
        }
        catch (org.openqa.selenium.NoSuchElementException e)
        {
            WebElement checkBox;
            checkBox = driver.findElement(By.id("checkbox-timeTable"));
            checkBox.click();
            weekButton = driver.findElement(By.xpath(weekButtonPath));
            weekButton.click();
        }
    }
    @AfterClass
    public static void tearDown()
    {
        driver.close();
    }
    public boolean ChangeLang(String langCode)
    {
        boolean result = false;
        WebElement LangGroup = driver.findElement(By.id("languagePickerContainer"));
        WebElement LangButtons = LangGroup.findElement(By.className("btn-group"));

        List<WebElement> Buttons = LangButtons.findElements(By.name("languagePicker"));
        for(int i = 0; i < Buttons.size(); ++i)
        {
            if (Buttons.get(i).getText().equals(langCode))
            {
                Buttons.get(i).click();
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean select(String Type, String Name)
    {
        boolean result = false;

        WebElement Chose = driver.findElement(By.id("TimeTableForm_" + Type + "_chosen"));
        try
        {
            Chose.click();
        }
        catch (org.openqa.selenium.StaleElementReferenceException e)
        {
            Chose = driver.findElement(By.id("TimeTableForm_" + Type + "_chosen"));
            Chose.click();
        }
        List<WebElement> choseList;
        try
        {
            choseList = Chose.findElements(By.className("active-result"));
        }
        catch (org.openqa.selenium.StaleElementReferenceException e)
        {
            Chose = driver.findElement(By.id("TimeTableForm_" + Type + "_chosen"));
            choseList = Chose.findElements(By.className("active-result"));
        }
        if (choseList == null) return false;

        for (int i = 0; i < choseList.size(); ++i)
        {
            if (choseList.get(i).getText().equals(Name))
            {
                choseList.get(i).click();
                result = true;
                break;
            }
        }
        return result;
    }
}