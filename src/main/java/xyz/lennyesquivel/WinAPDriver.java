package xyz.lennyesquivel;

import xyz.lennyesquivel.helpers.ConnectionEngine;
import xyz.lennyesquivel.helpers.DriverManager;
import xyz.lennyesquivel.models.ActionRequest;
import xyz.lennyesquivel.models.WinElement;
import xyz.lennyesquivel.models.enums.Actions;
import xyz.lennyesquivel.models.enums.By;
import xyz.lennyesquivel.models.enums.DriverEndpoints;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WinAPDriver {

    private DriverManager manager;
    private static ConnectionEngine con;

    /**
     * TO-DO
     * Change to driver builder so we can set options before starting the driver process
     * @throws IOException
     * @throws InterruptedException
     */
    public WinAPDriver() throws IOException, InterruptedException {
        con = new ConnectionEngine();
        manager = new DriverManager(con);
        manager.checkReadyStatus(con);
    }

    public WinAPDriver(String driverPath) throws Exception {
        con = new ConnectionEngine();
        manager = new DriverManager(con, driverPath);
        manager.checkReadyStatus(con);
    }

    public WinAPDriver(String driverPath, String url) throws Exception {
        con = new ConnectionEngine(url);
        manager = new DriverManager(con, driverPath);
        manager.checkReadyStatus(con);
    }

    public void launch(String appPath) {
        ActionRequest actionRequest = new ActionRequest(Actions.Launch, appPath, By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void launchStoreApp(String AUMID) {
        ActionRequest actionRequest = new ActionRequest(Actions.LaunchStoreApp, AUMID, By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void attachToProgram(String path) {
        ActionRequest actionRequest = new ActionRequest(Actions.AttachToProgram, path, By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void click() {
        ActionRequest actionRequest = new ActionRequest(Actions.Click, null, By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void doubleClick() {
        ActionRequest actionRequest = new ActionRequest(Actions.DoubleClick, null, By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void rightClick() {
        ActionRequest actionRequest = new ActionRequest(Actions.RightClick, null, By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void type(String value) {
        ActionRequest actionRequest = new ActionRequest(Actions.Type, value, By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void typeOnTextBox(String value, WinElement element) {
        ActionRequest actionRequest = new ActionRequest(Actions.TypeOnTextBox, value, element.byLocator, element.locatorValue);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void close() {
        ActionRequest actionRequest = new ActionRequest(Actions.Close, null, By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void takeScreenshot() {
        ActionRequest actionRequest = new ActionRequest(Actions.TakeScreenshot, null, By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void driverWait(int seconds) {
        ActionRequest actionRequest = new ActionRequest(Actions.Wait, "" + seconds, By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public WinElement findElement(By by, String locatorValue) {
        Map<String, String> params = new HashMap<>();
        params.put("locatorType", by.name());
        params.put("locatorValue", locatorValue);
        String response = con.get(DriverEndpoints.Element, params);
        return new WinElement(response);
    }

    public void highlightElement(WinElement element) {
        ActionRequest actionRequest = new ActionRequest(Actions.Highlight, null, element.byLocator, element.locatorValue);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void quit() {
        if (manager.isDriverProcessRunning()) {
            manager.stopDriverProcess();
        }
    }

    public static ConnectionEngine getConnection() {
        return con;
    }

}
