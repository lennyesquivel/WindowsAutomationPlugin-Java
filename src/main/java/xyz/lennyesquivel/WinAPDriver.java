package xyz.lennyesquivel;

import xyz.lennyesquivel.helpers.ConnectionEngine;
import xyz.lennyesquivel.helpers.DriverManager;
import xyz.lennyesquivel.models.ActionRequest;
import xyz.lennyesquivel.models.WinElement;
import xyz.lennyesquivel.models.enums.Actions;
import xyz.lennyesquivel.models.enums.By;
import xyz.lennyesquivel.models.enums.DriverEndpoints;

import java.io.IOException;

public class WinAPDriver {

    private DriverManager manager;
    private ConnectionEngine con;

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

    }

    public void launchStoreApp(String AUMID) {
        ActionRequest actionRequest = new ActionRequest();
        actionRequest.setAction(Actions.LaunchStoreApp);
        actionRequest.setActionValue(AUMID);
        actionRequest.setBy(By.AutomationId);
        con.post(DriverEndpoints.Action, actionRequest.toJson());
    }

    public void attachToProgram() {

    }

    public void click() {

    }

    public void doubleClick() {

    }

    public void rightClick() {

    }

    public void type(String value) {

    }

    public void typeOnTextBox(String value, WinElement element) {

    }

    public void close() {

    }

    public void takeScreenshot() {

    }

    public void driverWait(int seconds) {

    }

    public void findElement(By by, String locatorValue) {

    }

    public void highlightElement(WinElement element) {

    }

    public void quit() {
        if (manager.isDriverProcessRunning()) {
            manager.stopDriverProcess();
        }
    }

}
