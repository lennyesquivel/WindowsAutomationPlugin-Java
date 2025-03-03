package xyz.lennyesquivel;

import junit.framework.Assert;
import junit.framework.TestCase;
import xyz.lennyesquivel.models.WinElement;
import xyz.lennyesquivel.models.enums.By;

public class AppTest extends TestCase {

    public void testApp() {
        WinAPDriver driver = null;
        try {
            driver = new WinAPDriver(false).implicitlyWait(1000).build();
            driver.launchStoreApp("Microsoft.WindowsCalculator_8wekyb3d8bbwe!App");
            Thread.sleep(1000);
            WinElement btn5 = driver.findElement(By.AutomationId, "num5Button");
            btn5.click();
            WinElement multBtn = driver.findElement(By.AutomationId, "multiplyButton");
            multBtn.click();
            btn5.click();
            WinElement equalsBtn = driver.findElement(By.AutomationId, "equalButton");
            equalsBtn.click();
            WinElement result = driver.findElement(By.AutomationId, "CalculatorResults");
            System.out.println("Result is: " + result.getName());
            Assert.assertTrue(result.getName().contains("25"));
            driver.quit();
        } catch (Exception e) {
            if (driver != null)
                driver.quit();
            throw new RuntimeException(e);
        }
    }

}
