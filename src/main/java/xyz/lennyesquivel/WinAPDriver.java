package xyz.lennyesquivel;

import xyz.lennyesquivel.helpers.ConnectionEngine;
import xyz.lennyesquivel.helpers.DriverManager;
import xyz.lennyesquivel.models.ActionRequest;
import xyz.lennyesquivel.models.DriverOptions;
import xyz.lennyesquivel.models.WinElement;
import xyz.lennyesquivel.models.enums.Actions;
import xyz.lennyesquivel.models.enums.By;
import xyz.lennyesquivel.models.enums.DriverEndpoints;
import xyz.lennyesquivel.models.enums.VirtualKeyShort;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class WinAPDriver {

    private final DriverManager manager;
    private static ConnectionEngine con;
    private final DriverOptions options;

    public WinAPDriver() throws IOException {
        con = new ConnectionEngine();
        manager = new DriverManager(con, false);
        manager.checkReadyStatus(1);
        options = new DriverOptions();
    }

    public WinAPDriver(boolean silent) throws IOException {
        con = new ConnectionEngine();
        manager = new DriverManager(con, silent);
        manager.checkReadyStatus(1);
        options = new DriverOptions();
    }

    public WinAPDriver(String driverPath, boolean silent) throws Exception {
        con = new ConnectionEngine();
        manager = new DriverManager(con, driverPath, silent);
        manager.checkReadyStatus(1);
        options = new DriverOptions();
    }

    public WinAPDriver(URL url) throws Exception {
        con = new ConnectionEngine(url);
        manager = new DriverManager(con);
        manager.checkReadyStatus(1);
        options = new DriverOptions();
    }

    public WinAPDriver implicitlyWait(int millis) {
        this.options.setImplicitWaitTime(millis);
        return this;
    }

    public WinAPDriver withUIA2() {
        this.options.setUIAVersion(2);
        return this;
    }

    public WinAPDriver withUIA3() {
        this.options.setUIAVersion(3);
        return this;
    }

    public WinAPDriver build() {
        con.post(DriverEndpoints.Driver, this.options.toJsonString());
        return this;
    }

    public void launch(String appPath) {
        ActionRequest actionRequest = new ActionRequest(Actions.Launch, appPath,
                By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void launchStoreApp(String AUMID) {
        ActionRequest actionRequest = new ActionRequest(Actions.LaunchStoreApp, AUMID,
                By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void attachToProgram(String path) {
        ActionRequest actionRequest = new ActionRequest(Actions.AttachToProgram, path,
                By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void click() {
        ActionRequest actionRequest = new ActionRequest(Actions.Click, null,
                By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void doubleClick() {
        ActionRequest actionRequest = new ActionRequest(Actions.DoubleClick, null,
                By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void rightClick() {
        ActionRequest actionRequest = new ActionRequest(Actions.RightClick, null,
                By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void type(String value) {
        ActionRequest actionRequest = new ActionRequest(Actions.Type, value, By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void typeOnTextBox(String value, WinElement element) {
        ActionRequest actionRequest = new ActionRequest(Actions.TypeOnTextBox, value, element.byLocator,
                element.locatorValue);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void keyDown(VirtualKeyShort key) {
        ActionRequest actionRequest = new ActionRequest(Actions.KeyDown, key.toString(), null, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void keyUp(VirtualKeyShort key) {
        ActionRequest actionRequest = new ActionRequest(Actions.KeyUp, key.toString(), null, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void close() {
        ActionRequest actionRequest = new ActionRequest(Actions.Close, null,
                By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void takeScreenshot() {
        ActionRequest actionRequest = new ActionRequest(Actions.TakeScreenshot, null,
                By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void driverWait(int seconds) {
        ActionRequest actionRequest = new ActionRequest(Actions.Wait, "" + seconds,
                By.AutomationId, null);
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
        ActionRequest actionRequest = new ActionRequest(Actions.Highlight, null,
                element.byLocator, element.locatorValue);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void moveMouseToPosition(int X, int Y) {
        ActionRequest actionRequest = new ActionRequest(Actions.MoveMouseToPosition,
                String.format("(%s,%s)", X, Y), null, null);
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
