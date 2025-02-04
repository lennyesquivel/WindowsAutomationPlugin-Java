package xyz.lennyesquivel.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.lennyesquivel.WinAPDriver;
import xyz.lennyesquivel.helpers.ConnectionEngine;
import xyz.lennyesquivel.models.enums.Actions;
import xyz.lennyesquivel.models.enums.By;
import xyz.lennyesquivel.models.enums.DriverEndpoints;
import xyz.lennyesquivel.models.enums.VirtualKeyShort;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WinElement {

    public By byLocator;
    public String locatorValue;
    private ConnectionEngine con;
    private String name;

    public WinElement() {
        con = WinAPDriver.getConnection();
    }

    public WinElement(String fromJsonString) {
        con = WinAPDriver.getConnection();
        ObjectMapper objMapper = new ObjectMapper();
        try {
            WinElement el = objMapper.readValue(fromJsonString, WinElement.class);
            this.byLocator = el.byLocator;
            this.locatorValue = el.locatorValue;
            this.name = el.name;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Click on Windows element.
     * driver-attached-action: ClickOnElement
     * @author Lenny Esquivel
     */
    public void click() {
        ActionRequest actionRequest = new ActionRequest(Actions.ClickOnElement, null, byLocator, locatorValue);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void doubleClick() {
        ActionRequest actionRequest = new ActionRequest(Actions.DoubleClickOnElement, null, byLocator, locatorValue);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void rightClick() {
        ActionRequest actionRequest = new ActionRequest(Actions.RightClickOnElement, null, byLocator, locatorValue);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void rightDoubleClick() {
        ActionRequest actionRequest = new ActionRequest(Actions.RightDoubleClickOnElement, null, byLocator, locatorValue);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void type(String value) {
        ActionRequest actionRequest = new ActionRequest(Actions.TypeOnTextBox, value, byLocator, locatorValue);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void highlight() {
        ActionRequest actionRequest = new ActionRequest(Actions.Highlight, null, byLocator, locatorValue);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void clear() {
        int[] keys = new int[]{VirtualKeyShort.CONTROL, VirtualKeyShort.KEY_A};
        ActionRequest actionRequest = new ActionRequest(Actions.TypeSimultaneously, null, byLocator, locatorValue, keys);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
        int[] bckKey = new int[]{VirtualKeyShort.BACK};
        ActionRequest actionRequest2 = new ActionRequest(Actions.TypeSimultaneously, null, byLocator, locatorValue, bckKey);
        con.post(DriverEndpoints.Action, actionRequest2.toJsonString());
    }

    public String getName() {
        return name;
    }

}
