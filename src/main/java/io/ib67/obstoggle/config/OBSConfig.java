package io.ib67.obstoggle.config;

import io.ib67.obstoggle.OBSToggle;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class OBSConfig extends MidnightConfig {
    @Entry(name = "Enable")
    public static boolean enabled = false;
    @Entry(name = "Host")
    public static String host = "localhost";
    @Entry(name = "Password")
    public static String password = "";
    @Entry(min = 0, max = 65535, name = "Port")
    public static int port = 4455;

    @Entry(name = "Timeout (Seconds)", min = 0)
    public static int timeout=3;

    public static void onSave() {
        if (enabled) {
            OBSToggle.getInstance().updateConfig();
        }
    }
}
