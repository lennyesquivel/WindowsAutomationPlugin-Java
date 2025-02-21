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

    /**
     * WinAPDriver constructor using default driverPath and driverUrl. Launching driver instance not in silent mode.
     *
     * @author Lenny Esquivel
     * @throws IOException
     */
    public WinAPDriver() throws IOException {
        con = new ConnectionEngine();
        manager = new DriverManager(con, false);
        manager.checkReadyStatus(1);
        options = new DriverOptions();
    }

    /**
     * WinAPDriver constructor using default driverPath and driverUrl.
     *
     * @param silent When true driver window will not be shown.
     * @author Lenny Esquivel
     * @throws IOException
     */
    public WinAPDriver(boolean silent) throws IOException {
        con = new ConnectionEngine();
        manager = new DriverManager(con, silent);
        manager.checkReadyStatus(1);
        options = new DriverOptions();
    }

    /**
     * WinAPDriver constructor using default driverUrl. Receives driverPath to executable file.
     *
     * @param driverPath Path to driver executable file including file name.
     * @param silent When true driver window will not be shown.
     * @author Lenny Esquivel
     * @throws IOException
     */
    public WinAPDriver(String driverPath, boolean silent) throws Exception {
        con = new ConnectionEngine();
        manager = new DriverManager(con, driverPath, silent);
        manager.checkReadyStatus(1);
        options = new DriverOptions();
    }

    /**
     * WinAPDriver constructor with URL to connect to remote or active driver session.
     *
     * @param url URL to remote or active driver session.
     * @author Lenny Esquivel
     * @throws IOException
     */
    public WinAPDriver(URL url) throws Exception {
        con = new ConnectionEngine(url);
        manager = new DriverManager(con);
        manager.checkReadyStatus(1);
        options = new DriverOptions();
    }

    /**
     * Used set amount of milliseconds to wait for an element before throwing an exception.
     * Must call .build() after to register in driver.
     *
     * @author Lenny Esquivel
     * @param millis Milliseconds to wait.
     * @return WinAPDriver
     */
    public WinAPDriver implicitlyWait(int millis) {
        this.options.setImplicitWaitTime(millis);
        return this;
    }

    /**
     * Call to build driver with set options.
     *
     * @return WinAPDriver
     * @author Lenny Esquivel
     */
    public WinAPDriver build() {
        con.post(DriverEndpoints.Driver, this.options.toJsonString());
        return this;
    }

    /**
     * Launch and attach to an executable process.
     *
     * @param appPath Path to application executable file with file name.
     * @author Lenny Esquivel
     */
    public void launch(String appPath) {
        ActionRequest actionRequest = new ActionRequest(Actions.Launch, appPath,
                By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    /**
     * Launch and attach to Windows 11 store application.
     *
     * @param AUMID AUMID for Windows 11 store apps.
     * @author Lenny Esquivel
     */
    public void launchStoreApp(String AUMID) {
        ActionRequest actionRequest = new ActionRequest(Actions.LaunchStoreApp, AUMID,
                By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    /**
     * Attach to a running executable program.
     *
     * @param path Path to application executable file with file name.
     * @author Lenny Esquivel
     */
    public void attachToProgram(String path) {
        ActionRequest actionRequest = new ActionRequest(Actions.AttachToProgram, path,
                By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    /**
     * Perform click where cursor is currently at.
     *
     * @author Lenny Esquivel
     */
    public void click() {
        ActionRequest actionRequest = new ActionRequest(Actions.Click, null,
                By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    /**
     * Perform double click where cursor is currently at.
     *
     * @author Lenny Esquivel
     */
    public void doubleClick() {
        ActionRequest actionRequest = new ActionRequest(Actions.DoubleClick, null,
                By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    /**
     * Perform right click where cursor is currently at.
     *
     * @author Lenny Esquivel
     */
    public void rightClick() {
        ActionRequest actionRequest = new ActionRequest(Actions.RightClick, null,
                By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    /**
     * Type value with keyboard.
     *
     * @author Lenny Esquivel
     */
    public void type(String value) {
        ActionRequest actionRequest = new ActionRequest(Actions.Type, value, By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    /**
     * Must be performed on an element of type TextBox. Parses the element to a TextBox element and performs element native type.
     *
     * @param value Value to by typed.
     * @param element WinElement element.
     * @author Lenny Esquivel
     */
    public void typeOnTextBox(String value, WinElement element) {
        ActionRequest actionRequest = new ActionRequest(Actions.TypeOnTextBox, value, element.byLocator,
                element.locatorValue);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    /**
     * Presses keyboard key by keycode.
     *
     * @param key VirtualKeyShort key to be pressed.
     * @author Lenny Esquivel
     */
    public void keyDown(VirtualKeyShort key) {
        ActionRequest actionRequest = new ActionRequest(Actions.KeyDown, key.toString(), null, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    /**
     * Releases keyboard key by keycode.
     *
     * @param key VirtualKeyShort key to be released.
     * @author Lenny Esquivel
     */
    public void keyUp(VirtualKeyShort key) {
        ActionRequest actionRequest = new ActionRequest(Actions.KeyUp, key.toString(), null, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    /**
     * Close attached application.
     *
     * @author Lenny Esquivel
     */
    public void close() {
        ActionRequest actionRequest = new ActionRequest(Actions.Close, null,
                By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    /**
     * Takes screenshot of the whole screen.
     *
     * @author Lenny Esquivel
     */
    public void takeScreenshot() {
        ActionRequest actionRequest = new ActionRequest(Actions.TakeScreenshot, null,
                By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    /**
     * Waits for the amount of seconds.
     *
     * @param seconds Seconds to wait
     * @author Lenny Esquivel
     */
    public void driverWait(int seconds) {
        ActionRequest actionRequest = new ActionRequest(Actions.Wait, "" + seconds,
                By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    /**
     * Find an element with the provided locator type and locator value.
     *
     * @param by By locator type
     * @param locatorValue Locator value
     * @return WinElement
     * @author Lenny Esquivel
     */
    public WinElement findElement(By by, String locatorValue) {
        Map<String, String> params = new HashMap<>();
        params.put("locatorType", by.name());
        params.put("locatorValue", locatorValue);
        String response = con.get(DriverEndpoints.Element, params);
        return new WinElement(response);
    }

    /**
     * Highlights in a red contour provided element.
     *
     * @param element WinElement to be highlighted
     * @author Lenny Esquivel
     */
    public void highlightElement(WinElement element) {
        ActionRequest actionRequest = new ActionRequest(Actions.Highlight, null,
                element.byLocator, element.locatorValue);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    /**
     * Move cursor to provided position of X,Y pixels.
     *
     * @param X Position in X axis
     * @param Y Position in Y axis
     * @author Lenny Esquivel
     */
    public void moveMouseToPosition(int X, int Y) {
        ActionRequest actionRequest = new ActionRequest(Actions.MoveMouseToPosition,
                String.format("(%s,%s)", X, Y), null, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    /**
     * Press cursor down and drag cursor to provided position of X,Y pixels.
     *
     * @param X Position in X axis
     * @param Y Position in Y axis
     * @author Lenny Esquivel
     */
    public void clickAndDragToCoordinates(int X, int Y) {
        ActionRequest actionRequest = new ActionRequest(Actions.ClickAndDragToCoordinates,
                String.format("(%s,%s)", X, Y), By.AutomationId, null);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    /**
     * Press cursor down snd drag cursor to provided position of WinElement's location.
     *
     * @param element WinElement to where the cursor will be dragged to
     * @author Lenny Esquivel
     */
    public void clickAndDragToElement(WinElement element) {
        ActionRequest actionRequest = new ActionRequest(Actions.ClickAndDragToElement, null,
                element.byLocator, element.locatorValue);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    /**
     * Stop driver's process.
     *
     * @author Lenny Esquivel
     */
    public void quit() {
        if (manager.isDriverProcessRunning()) {
            manager.stopDriverProcess();
        }
    }

    public static ConnectionEngine getConnection() {
        return con;
    }

}
