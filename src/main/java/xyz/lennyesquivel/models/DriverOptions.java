package xyz.lennyesquivel.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DriverOptions {

    private int ImplicitWaitTime;

    /**
     * Get implicit wait time in milliseconds.
     *
     * @return Implicit wait time in milliseconds
     * @author Lenny Esquivel
     */
    public int getImplicitWaitTime() {
        return ImplicitWaitTime;
    }

    /**
     * Set implicit wait time in milliseconds.
     *
     * @author Lenny Esquivel
     */
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
