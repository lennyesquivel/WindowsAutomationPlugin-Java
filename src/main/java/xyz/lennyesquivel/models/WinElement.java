package xyz.lennyesquivel.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.lennyesquivel.WinAPDriver;
import xyz.lennyesquivel.helpers.ConnectionEngine;
import xyz.lennyesquivel.models.enums.Actions;
import xyz.lennyesquivel.models.enums.By;
import xyz.lennyesquivel.models.enums.DriverEndpoints;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WinElement {

    public By byLocator;
    public String locatorValue;
    public Object nativeElement;
    private ConnectionEngine con;

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
            this.nativeElement = el.nativeElement;
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

    public void type() {
        ActionRequest actionRequest = new ActionRequest(Actions.TypeOnTextBox, null, byLocator, locatorValue);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    public void highlight() {
        ActionRequest actionRequest = new ActionRequest(Actions.Highlight, null, byLocator, locatorValue);
        con.post(DriverEndpoints.Action, actionRequest.toJsonString());
    }

    // TO-DO
    public String getText() {
        return "";
    }

}
