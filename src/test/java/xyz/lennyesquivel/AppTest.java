package xyz.lennyesquivel;

import junit.framework.TestCase;
import xyz.lennyesquivel.models.WinElement;
import xyz.lennyesquivel.models.enums.By;

public class AppTest extends TestCase {

    public void testApp() {
        WinAPDriver driver = null;
        try {
            //driver = new WinAPDriver("C:\\WinAPDriver\\publish\\WindowsAutomationPlugin.exe", "http://localhost:5224");
            driver = new WinAPDriver("C:\\WinAPDriver\\publish\\WindowsAutomationPlugin.exe");
            driver.launchStoreApp("Microsoft.WindowsCalculator_8wekyb3d8bbwe!App");
            WinElement btn5 = driver.findElement(By.AutomationId, "num5Button");
            btn5.click();
            WinElement multBtn = driver.findElement(By.AutomationId, "multiplyButton");
            multBtn.click();
            btn5.click();
            WinElement equalsBtn = driver.findElement(By.AutomationId, "equalButton");
            equalsBtn.click();
            WinElement result = driver.findElement(By.AutomationId, "CalculatorResults");
            System.out.println("Result is: " + result.getText());
            driver.quit();
        } catch (Exception e) {
            if (driver != null)
                driver.quit();
            throw new RuntimeException(e);
        }
    }

}
