package xyz.lennyesquivel.models.enums;

public class DriverEndpoints {
    // STATUS
    public static String Status = "/status";
    public static String ClientSessionId = Status + "/clientSessionId";
    public static String DestroyDriver = Status + "/selfDestruct";

    // ACTION
    public static String Action = "/action";
    public static String Element = Action + "/element";
    public static String Driver = Action + "/driver";
}
