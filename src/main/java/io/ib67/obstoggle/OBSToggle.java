package io.ib67.obstoggle;

import io.ib67.obstoggle.config.MidnightConfig;
import io.ib67.obstoggle.config.OBSConfig;
import io.obswebsocket.community.client.OBSRemoteController;
import lombok.Getter;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class OBSToggle implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("OBSToggle");
    public static final String MOD_ID = "obstoggle";
    private static OBSToggle instance;

    public static OBSToggle getInstance() {
        return instance;
    }

    @Getter
    private OBSRemoteController obsRemoteController = null;

    @Override
    public void onInitialize() {
        MidnightConfig.init(MOD_ID, OBSConfig.class);
        instance = this;
        if (OBSConfig.enabled) {
            initializeController();
        }
    }

    public void updateConfig() {
        if (obsRemoteController == null && OBSConfig.enabled) {
            initializeController();
        }
    }

    private void initializeController() {
        obsRemoteController =
                OBSRemoteController.builder()
                        .host(OBSConfig.host)
                        .password(OBSConfig.password)
                        .port(OBSConfig.port)
                        .autoConnect(true)
                        .build();
    }
}
