package xyz.lennyesquivel.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientSession {
    public String ClientSessionId;

    public ClientSession(String id) {
        this.ClientSessionId = id;
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
