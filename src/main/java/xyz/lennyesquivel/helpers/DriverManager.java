package xyz.lennyesquivel.helpers;

import xyz.lennyesquivel.models.enums.DriverEndpoints;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public class DriverManager {

    private String driverPathInUse = "";
    private Process driverProcess;
    private final ConnectionEngine con;
    private final String sessionId;
    private final String driverName = "WindowsAutomationPlugin.exe";
    private final String userDirectory = System.getProperty("user.home");

    public DriverManager(ConnectionEngine connectionEngine) {
        this.sessionId = UUID.randomUUID().toString();
        this.con = connectionEngine;
        if (!checkReadyStatus(5)) {
            throw new RuntimeException("Driver session could not be reached.");
        }
        registerClientSession();
    }

    public DriverManager(ConnectionEngine connectionEngine, boolean silent) throws IOException {
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
        File driverFile = new File(driverPath);
        boolean exists = driverFile.exists();
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

    private void fetchDriverAndSetup() {
        File cacheDir = new File(userDirectory + "\\.cache");
        File driverDir = new File(userDirectory + "\\.cache\\winapdriver");
        File driverFile = new File(userDirectory + "\\.cache\\winapdriver\\" + driverName);
        if (!cacheDir.exists()) {
            System.out.println("Driver path doesn't exist, creating directory...");
            assert cacheDir.mkdir();
            assert driverDir.mkdir();
        } else if (!driverDir.exists()) {
            System.out.println("Driver path doesn't exist, creating directory...");
            assert driverDir.mkdir();
        } else if (driverFile.exists()) {
            driverPathInUse = driverFile.getAbsolutePath();
            return;
        }
        String driverReleaseUrl = "https://github.com/lennyesquivel/WindowsAutomationPlugin-WindowsDriver/releases/latest/download/" + driverName;
        System.out.println("Fetching latest driver from: " + driverReleaseUrl);
        try (BufferedInputStream inputStream = new BufferedInputStream(new URL(driverReleaseUrl).openStream())) {
            String driverPath = driverDir.getAbsolutePath().concat("\\" + driverName);
            FileOutputStream fileOutputStream = new FileOutputStream(driverPath);
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            driverPathInUse = driverPath;
            fileOutputStream.close();
            inputStream.close();
            System.out.println("Done.");
        } catch (IOException e) {
            throw new RuntimeException("There was an error trying to download the latest driver.\n" + e);
        }
    }

    private void startDriverProcess(String driverPath, boolean silent) throws IOException {
        String command = silent ? driverPath : "cmd /c start " + driverPath;
        driverProcess = Runtime.getRuntime().exec(command);
    }

    public void stopDriverProcess() {
        driverProcess.destroy();
        try {
            driverProcess.waitFor();
        } catch (Exception ex) {
            throw new RuntimeException("There was an error while terminating the driver process.\n" + ex.getMessage());
        }
    }

    public boolean checkReadyStatus(int retries) {
        boolean ready = false;
        int retried = 0;
        while(!ready) {
            try {
                retried++;
                if (retried > retries)
                    break;
                Thread.sleep(250);
                ready = this.con.get(DriverEndpoints.Status).contains("Ready");
            } catch (Exception ignored) { }
        }
        return ready;
    }

    private void registerClientSession() {
        if (checkReadyStatus(30)) {
            this.con.post(DriverEndpoints.Status, this.sessionId);
        } else {
            throw new RuntimeException("Driver was unreachable");
        }
    }

    public OutputStream getDriverLogs() {
        return driverProcess.getOutputStream();
    }

}
