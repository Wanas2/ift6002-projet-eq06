package ca.ulaval.glo4002.game;

import ca.ulaval.glo4002.game.interfaces.rest.heartbeat.HeartbeatResource;
import org.glassfish.jersey.server.ResourceConfig;

public class ProjectConfig extends ResourceConfig {

    public ProjectConfig() {
        registerResources();
    }

    private void registerResources() {
        HeartbeatResource heartbeatResource = new HeartbeatResource();

        register(heartbeatResource);
    }
}
