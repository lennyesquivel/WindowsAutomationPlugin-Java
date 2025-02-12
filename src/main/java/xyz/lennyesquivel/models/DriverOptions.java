package xyz.lennyesquivel.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DriverOptions {

    private int UIAVersion;
    private int ImplicitWaitTime;

    public int getUIAVersion() {
        return UIAVersion;
    }

    public void setUIAVersion(int UIAVersion) {
        this.UIAVersion = UIAVersion;
    }

    public int getImplicitWaitTime() {
        return ImplicitWaitTime;
    }

    public void setImplicitWaitTime(int implicitWaitTime) {
        ImplicitWaitTime = implicitWaitTime;
    }

    public String toJsonString() {
        try {
            ObjectMapper objMapper = new ObjectMapper();
            return objMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            System.err.println("Error mapping ActionRequest object to json string");
            return null;
        }
    }

}
