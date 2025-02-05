package xyz.lennyesquivel.helpers;

import xyz.lennyesquivel.models.enums.DriverEndpoints;

import java.io.File;
import java.io.IOException;

public class DriverManager {

    private String defaultDriverPath = "";
    private Process driverProcess;
    private ConnectionEngine con;

    public DriverManager(ConnectionEngine connectionEngine, boolean silent) throws IOException, InterruptedException {
        this.con = connectionEngine;
        if (!checkReadyStatus(con, 5)) {
            if (!driverExists(defaultDriverPath)) {
                fetchDriverAndSetup();
            }
            startDriverProcess(defaultDriverPath, silent);
        }
    }

    public DriverManager(ConnectionEngine connectionEngine, String driverPath, boolean silent) throws Exception {
        this.con = connectionEngine;
        if (!checkReadyStatus(con, 5)) {
            if (!driverExists(driverPath)) {
                throw new Exception("Driver does not exist. Path:" + driverPath);
            }
            startDriverProcess(driverPath, silent);
        }
    }

    public boolean isDriverProcessRunning() {
        return driverProcess != null && driverProcess.isAlive();
    }

    private boolean driverExists(String driverPath) {
        File driverFile = new File(driverPath);
        return driverFile.exists();
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
        driverProcess.destroy();
    }

    public void checkReadyStatus(ConnectionEngine connectionEngine) {
        boolean ready = false;
        while(!ready) {
            try {
                Thread.sleep(100);
                ready = connectionEngine.get(DriverEndpoints.Status).contains("Ready");

            } catch (Exception ignored) { }
        }
    }

    public boolean checkReadyStatus(ConnectionEngine connectionEngine, int retries) {
        boolean ready = false;
        int retried = 0;
        while(!ready) {
            try {
                retried++;
                if (retried > retries)
                    break;
                System.out.printf("Checking if driver is up %s times.\n", retried);
                Thread.sleep(100);
                ready = connectionEngine.get(DriverEndpoints.Status).contains("Ready");
            } catch (Exception ignored) { }
        }
        return ready;
    }

    public String getDriverLogsAsString() {
        // TO-DO
        return "";
    }

}
