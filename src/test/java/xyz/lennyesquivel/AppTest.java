package xyz.lennyesquivel;

import junit.framework.TestCase;

public class AppTest extends TestCase {

    public void testApp() {
        WinAPDriver driver = null;
        try {
            driver = new WinAPDriver("C:\\WinAPDriver\\publish\\WindowsAutomationPlugin.exe");
            driver.launchStoreApp("Microsoft.WindowsCalculator_8wekyb3d8bbwe!App");
            driver.quit();
        } catch (Exception e) {
            if (driver != null)
                driver.quit();
            throw new RuntimeException(e);
        }
    }

}
