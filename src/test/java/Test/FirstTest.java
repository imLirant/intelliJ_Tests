package Test;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.max.CouldNotReadCoreException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class FirstTest {

    private static WebDriver driver;

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "E://chromedriver//chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://193.189.127.179:5010/timeTable/group");
    }
    @Test
    public void Test_1()//gotoStudent
    {
        WebElement student = null;
        try
        {
            student = driver.findElement(By.linkText("Студента"));
        }
        catch (org.openqa.selenium.NoSuchElementException e)
        {
            try
            {
                student = driver.findElement(By.linkText("Student`s"));
            }
            catch (org.openqa.selenium.NoSuchElementException ex)
            {
                Assert.fail("Не найдена ссылка на расписание студента");
            }
        }
        if (student != null)
        {
            student.click();
            Assert.assertEquals("http://193.189.127.179:5010/timeTable/student", driver.getCurrentUrl());
        }
    }

    @Test
    public void Test_2() //selectEN
    {
        if (!ChangeLang("EN"))
            Assert.fail("Не удалось изменить язык");
    }

    @Test
    public void Test_3()
    {
        Assert.assertEquals("http://193.189.127.179:5010/timeTable/student", driver.getCurrentUrl());

        String Faculty  = "Факультет електроніки та комп'ютерної інженерії";
        String Course = "4";
        String Group = "КІ-14-1";
        String Student = "Дудник С. С.";

        if(select("faculty", Faculty)) System.out.append("Был выбран Факультет: " + Faculty + "\n");
        else Assert.fail("Ошибка при выполнении выбора факультета");

        if (select("course",Course)) System.out.append("Был выбран курс: " + Course + "\n");
        else Assert.fail("Ошибка при выполнении выбора курса");

        if (select("group",Group)) System.out.append("Была выбрана группа: " + Group + "\n");
        else Assert.fail("Ошибка при выполнении выбора группы");

        if (select("student",Student)) System.out.append("Был выбран Студент: " + Student + "\n");
        else Assert.fail("Ошибка при выполнении выбора студента");
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
        List<WebElement> choseList = null;
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
    public void FindMe()
    {
        WebElement text;
        try
        {
            text = driver.findElement(By.id("St_st2"));
        }
        catch (org.openqa.selenium.NoSuchElementException e)
        {
            return;
        }

        text.sendKeys("Дудник");
        WebElement button = driver.findElement(By.name("yt0"));
        button.click();
    }
    @AfterClass
    public static void tearDown()
    {
        driver.close();
    }
}