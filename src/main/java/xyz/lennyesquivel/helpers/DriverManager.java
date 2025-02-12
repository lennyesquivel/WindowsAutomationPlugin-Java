package xyz.lennyesquivel.helpers;

import xyz.lennyesquivel.models.enums.DriverEndpoints;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class DriverManager {

    private String driverPathInUse = "";
    private Process driverProcess;
    private final ConnectionEngine con;
    private final String sessionId;
    // TO-DO: Check this later since we might need a token for the request
    private final String driverReleaseUrl = "https://github.com/lennyesquivel/WindowsAutomationPlugin-WindowsDriver/releases/latest";

    public DriverManager(ConnectionEngine connectionEngine) {
        this.sessionId = UUID.randomUUID().toString();
        this.con = connectionEngine;
        if (!checkReadyStatus(5)) {
            throw new RuntimeException("Driver session could not be reached.");
        }
        registerClientSession();
    }

    public DriverManager(ConnectionEngine connectionEngine, boolean silent) throws IOException, InterruptedException {
        this.sessionId = UUID.randomUUID().toString();
        this.con = connectionEngine;
        if (!checkReadyStatus(5)) {
            String defaultDriverPath = "";
            if (!driverExists(defaultDriverPath)) {
                fetchDriverAndSetup();
            }
            startDriverProcess(driverPathInUse, silent);
        }
        registerClientSession();
    }

    public DriverManager(ConnectionEngine connectionEngine, String driverPath, boolean silent) throws Exception {
        this.sessionId = UUID.randomUUID().toString();
        this.con = connectionEngine;
        if (!checkReadyStatus(5)) {
            if (!driverExists(driverPath)) {
                throw new Exception("Driver does not exist. Path:" + driverPath);
            }
            startDriverProcess(driverPath, silent);
        }
        registerClientSession();
    }

    public boolean isDriverProcessRunning() {
        return driverProcess != null && driverProcess.isAlive();
    }

    private boolean driverExists(String driverPath) {
        boolean exists = false;
        File driverFile = new File(driverPath);
        exists = driverFile.exists();
        if (exists) {
            driverPathInUse = driverPath;
        }
        String envVarName = "WAP_PATH";
        String envVarValue = System.getenv(envVarName);
        if (envVarValue != null && !envVarValue.isEmpty()) {
            String driverExeName = "WindowsAutomationPlugin.exe";
            File driverFileEnv = new File(envVarValue + "\\" + driverExeName);
            boolean fromEnvExists = driverFileEnv.exists();
            if (fromEnvExists) {
                driverPathInUse = envVarValue + "\\" + driverExeName;
            }
            exists = exists || driverFileEnv.exists();
        }
        return exists;
    }

    /**
     * TO-DO
     * Add check env variable and path for file, if none present then download driver and extract/copy to cache path
     */
    private void fetchDriverAndSetup() {

    }

    private void startDriverProcess(String driverPath, boolean silent) throws IOException, InterruptedException {
        String command = silent ? driverPath : "cmd /c start " + driverPath;
        driverProcess = Runtime.getRuntime().exec(command);
        driverProcess.waitFor();
    }

    public void stopDriverProcess() {
        driverProcess.destroyForcibly();
    }

    public boolean checkReadyStatus(int retries) {
        boolean ready = false;
        int retried = 0;
        while(!ready) {
            try {
                retried++;
                if (retried > retries)
                    break;
                Thread.sleep(200);
                ready = this.con.get(DriverEndpoints.Status).contains("Ready");
            } catch (Exception ignored) { }
        }
        return ready;
    }

    private void registerClientSession() {
        if (checkReadyStatus(10)) {
            this.con.post(DriverEndpoints.Status, this.sessionId);
        } else {
            throw new RuntimeException("Driver was unreachable");
        }
    }

    public OutputStream getDriverLogs() {
        return driverProcess.getOutputStream();
    }

}
