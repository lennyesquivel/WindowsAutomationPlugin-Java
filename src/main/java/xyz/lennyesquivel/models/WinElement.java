package xyz.lennyesquivel.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.lennyesquivel.WinAPDriver;
import xyz.lennyesquivel.helpers.ConnectionEngine;
import xyz.lennyesquivel.models.enums.Actions;
import xyz.lennyesquivel.models.enums.By;
import xyz.lennyesquivel.models.enums.DriverEndpoints;
import xyz.lennyesquivel.models.enums.VirtualKeyShort;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WinElement {

    public By byLocator;
    public String locatorValue;
    private ConnectionEngine con;
    public String name;
    public int positionX;
    public int positionY;
    public double height;
    public double width;
    public String className;
    public String controlType;
    public boolean isAvailable;
    public boolean isEnabled;
    public boolean isOffscreen;

    /**
     * WinElement constructor, no properties set.
     *
     * @author Lenny Esquivel
     */
    public WinElement() {
        con = WinAPDriver.getConnection();
    }

    /**
     * WinElement constructor receiving WinElement object as String with Json structure
     *
     * @param fromJsonString String with WinElement Json structure
     * @author Lenny Esquivel
     */
    public WinElement(String fromJsonString) {
        con = WinAPDriver.getConnection();
        ObjectMapper objMapper = new ObjectMapper();
        try {
            WinElement el = objMapper.readValue(fromJsonString, WinElement.class);
            this.byLocator = el.byLocator;
            this.locatorValue = el.locatorValue;
            this.name = el.name;
            this.positionX = el.positionX;
            this.positionY = el.positionY;
            this.height = el.height;
            this.width = el.width;
            this.className = el.className;
            this.controlType = el.controlType;
            this.isAvailable = el.isAvailable;
            this.isEnabled = el.isEnabled;
            this.isOffscreen = el.isOffscreen;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Click on element.
     * driver-attached-action: ClickOnElement
     *
     * @author Lenny Esquivel
     */
    public void click() {
        ActionRequest actionRequest = new ActionRequest(Actions.ClickOnElement, null,
                byLocator, locatorValue);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    /**
     * Double-click on element.
     * driver-attached-action: DoubleClickOnElement
     *
     * @author Lenny Esquivel
     */
    public void doubleClick() {
        ActionRequest actionRequest = new ActionRequest(Actions.DoubleClickOnElement, null,
                byLocator, locatorValue);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    /**
     * Right click on element.
     * driver-attached-action: RightClickOnElement
     *
     * @author Lenny Esquivel
     */
    public void rightClick() {
        ActionRequest actionRequest = new ActionRequest(Actions.RightClickOnElement, null,
                byLocator, locatorValue);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    /**
     * Right double-click on element.
     * driver-attached-action: RightDoubleClickOnElement
     *
     * @author Lenny Esquivel
     */
    public void rightDoubleClick() {
        ActionRequest actionRequest = new ActionRequest(Actions.RightDoubleClickOnElement, null,
                byLocator, locatorValue);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    /**
     * Element must be of type TextBox. Cast element to TextBox and type value on element.
     * driver-attached-action: TypeOnTextBox
     *
     * @param value String to be typed into element
     * @author Lenny Esquivel
     */
    public void type(String value) {
        ActionRequest actionRequest = new ActionRequest(Actions.TypeOnTextBox, value, byLocator, locatorValue);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    /**
     * Highlight element.
     * driver-attached-action: Highlight
     *
     * @author Lenny Esquivel
     */
    public void highlight() {
        ActionRequest actionRequest = new ActionRequest(Actions.Highlight, null, byLocator, locatorValue);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    /**
     * Performs two actions. Presses Ctrl + A, then Backspace.
     * driver-attached-action: TypeSimultaneously
     *
     * @author Lenny Esquivel
     */
    public void clear() {
        int[] keys = new int[]{VirtualKeyShort.CONTROL, VirtualKeyShort.KEY_A};
        ActionRequest actionRequest = new ActionRequest(Actions.TypeSimultaneously, null, byLocator,
                locatorValue, keys);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
        int[] bckKey = new int[]{VirtualKeyShort.BACK};
        ActionRequest actionRequest2 = new ActionRequest(Actions.TypeSimultaneously, null, byLocator,
                locatorValue, bckKey);
        con.post(DriverEndpoints.Action, actionRequest2.toJsonString());
    }

    /**
     * Get element Name.
     *
     * @return Name
     * @author Lenny Esquivel
     */
    public String getName() {
        return name;
    }

    /**
     * Get element coordinates [X][Y]
     *
     * @return Coordinates [X][Y]
     * @author Lenny Esquivel
     */
    public int[][] getCoordinates() {
        return new int[][] {{positionX}, {positionY}};
    }

}
