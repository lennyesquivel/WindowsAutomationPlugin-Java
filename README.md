# WindowsAutomationPlugin
This is documentation for the WindowsAutomationPlugin client for Java. For Driver documentation please visit the [respective repository](https://github.com/lennyesquivel/WindowsAutomationPlugin-Java/tree/master).
## Maven dependency
Add to the ```pom.xml``` file in your project.
```
<dependency>
  <groupId>xyz.lennyesquivel</groupId>
  <artifactId>windowsautomationplugin-javaclient</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Download WAP driver
The WAP java project has an integrated driver manager that checks for an existing version of the driver in the device, if none found, it will attempt to fetch the driver files from the release page on GitHub.
Installation path for the automatically downloaded driver is "C:\\Users\\YOUR_USER\\.cache\\winapdriver".

For manual downloads and other versions, visit our [Releases](https://github.com/lennyesquivel/WindowsAutomationPlugin-WindowsDriver/releases) page on GitHub.

## Usage
### Starting the server
There are 2 ways to start the server.
1. Launching the server executable file
	- The client will attempt to connect to the localhost port 5000 so if the driver is already running it'll ping the server to see the status.
2. Letting the client start the driver process
	- You can specify the path to the driver executable file as a parameter for the WinAPDriver constructor.
	- There are several constructors that receive the URL of the driver as another parameter for remote connections (this functionality hasn't been tested yet but technically should work).
    - Setting an environment variable (WAP_PATH) with the path to the driver's executable file.

Being the default values for:
- Driver URL: http://localhost:5000
- Driver executable path: C:/Users/YOUR_USER/.cache/winapdriver/WindowsAutomationPlugin.exe
The constructors for instancing the WinAPDriver are as follows:

```
// Connects to default URL and launches executable in default location
WinAPDriver();

// When set to true the driver command line window will not be lauched, only the process
WinAPDriver(boolean silent);

// Looks for driver in set location and launches it depending on the silent variable
WinAPDriver(String driverPath, boolean silent);

// Will ping directly the URL and try to connect to that session
WinAPDriver(String url);
```
### Starting with options
#### Implicit Wait
For setting the amount of milliseconds for implicitly waiting on elements you can use the driver builder.
```
driver = new WinAPDriver().implicitlyWait(1000).build();
```

### Launching an application
Windows applications can be launched several ways, depending on the type of application.
 
For Windows 11 store applications use:
```
driver.launchStoreApp(String AUMID);
```
For the rest of Windows executables use:
```
driver.launch(String appPath);
```
can also attach the driver session to a running application using:
```
driver.attachToProgram(appPath);
```

### Pointer/mouse actions
Straight actions with the pointer/mouse can be done through the driver class wherever the pointer is currently located.

Examples:
```
driver.click();
driver.doubleClick();
driver.rightClick();
driver.moveMouseToPosition(int X, int Y);
```

### Keyboard actions
```
driver.type(String text);
// For pressing specific keys use typeSimultaneously
driver.typeSimultaneously(VirtualKeyShort[] keys);
```

### Driver Miscelaneous
```
driver.takeScreenshot();
// Close will end the running application
driver.close();
// Quit will attempt to end the running application and terminate the driver process
driver.quit();
```

### WinElement actions
To fetch a WinElement object use:
```
driver.findElement(By by, String locatorValue);
```

WinElement click actions:
```
element.click();
element.doubleClick();
element.rightClick();
element.rightDoubleClick();
```

WinElement type actions:
```
element.type(String text);
element.clear();
```

WinElement property methods:
```
element.getName();
element.getCoordinates();
```

### WinElement locator types (By)
```
By.Value
By.Text
By.ProcessId
By.Name
By.LocalizedControlType
By.HelpText
By.FrameworkType
By.FrameworkId
By.ControlType
By.ClassName
By.AutomationId
By.Xpath
```

## Scope
The actions implemented on the Java client are directly related to the actions the driver supports. Currently the driver uses FlaUI to interact with the Windows elements and the scope for this version of the driver is to extend as much functionality from FlaUI to the client as possible. Future versions will aim to remove FlaUI and implement a direct approach similar to what FlaUI does but without a middle man. This will allow us to try to provide more functionality and better performance.

This is a project done by one person in the span of about two weeks, it is very rough around the edges but I hopefully think this is the worst it'll be, there are several enhancements on the works and since it's an open source project I fully encourage anyone who finds this useful to contribute to it any way they can.
## Known Issues
None for now...

## Roadmap
Features in progress or planned to be implemented.
- [x] Actions 
  - [x] KeyUp
  - [x] KeyDown
  - [x] Drag/Drop
- [x] DriverManager automatic driver download and check
	- [x] Implement environment variables for driver path
- [x] Package .NET project executable into one file
- [x] Implement driver options to build driver with
	- [x] Implicit wait time
	- [ ] UIA version (deemed as not necessary at the moment)
- [x] Add By locators to readme
- [x] Explore and implement more WinElement properties
- [ ] Test remote connection
	- [ ] Https connection (deferred since having problems with https certificates)
- [x] Implement session ID to deny connections outside registered session
- [x] Write javadocs

## Raising issues and feature requests
See below for now.

## Contributing to the project
Please raise a GitHub issue requesting any features and issues, and I'll get to them ASAP. Any code changes and PR's will be reviewed by me (@lennyesquivel) in the foreseeable future until code guidelines and project good practices are in place for anyone else to implement (yes, I know and am fully aware I'm not following best practices but the project needed to be working fast).
