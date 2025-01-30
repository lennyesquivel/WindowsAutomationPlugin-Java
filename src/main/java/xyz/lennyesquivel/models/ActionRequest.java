package xyz.lennyesquivel.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.lennyesquivel.models.enums.Actions;
import xyz.lennyesquivel.models.enums.By;

public class ActionRequest {

    private Actions Action;
    private String ActionValue;
    private By By;
    private String LocatorValue;

    public ActionRequest() {

    }

    public ActionRequest(Actions action, String actionValue, By by, String locatorValue) {
        this.Action = action;
        this.ActionValue = actionValue;
        this.By = by;
        this.LocatorValue = locatorValue;
    }

    public void setAction(Actions action) {
        this.Action = action;
    }

    public Actions getAction() {
        return Action;
    }

    public String getActionValue() {
        return ActionValue;
    }

    public void setActionValue(String actionValue) {
        ActionValue = actionValue;
    }

    public By getBy() {
        return By;
    }

    public void setBy(By by) {
        By = by;
    }

    public String getLocatorValue() {
        return LocatorValue;
    }

    public void setLocatorValue(String locatorValue) {
        LocatorValue = locatorValue;
    }

    public String toJson() {
        try {
            ObjectMapper objMapper = new ObjectMapper();
            return objMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            System.err.println("Error mapping ActionRequest object to json string");
            return null;
        }
    }

}
