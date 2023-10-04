package darts.hunter;

import com.google.inject.Provides;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import net.runelite.api.GameObject;

@Slf4j
@PluginDescriptor(
		name = "Box Trap Utilities",
		description = "A RuneLite plugin for Hunter.",
		tags = {"hunter", "box", "trap", "utilities", "timer"}
)
public class BoxTrapPlugin extends Plugin {
	private static final int SET_BOX_TRAP = 9380;

	@Getter
	private final List<BoxTrap> freshBoxTrapList = new LinkedList<>();

	@Getter
	private final List<BoxTrap> liveBoxTrapList = new LinkedList<>();

	@Inject
	private Client client;

	@Inject
	private BoxTrapOverlay overlay;

	@Inject
	private OverlayManager overlayManager;

	@Provides
	BoxTrapConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(BoxTrapConfig.class);
	}

	@Override
	protected void startUp() {
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() {
		overlayManager.remove(overlay);
		this.freshBoxTrapList.clear();
	}

	@Subscribe
	public void onGameObjectSpawned(GameObjectSpawned event) {
		GameObject go = event.getGameObject();
		if (go.getId() == SET_BOX_TRAP) {
			freshBoxTrapList.add(new BoxTrap(go.getWorldLocation(), go));
		}
	}

	@Subscribe
	public void onGameObjectDespawned(GameObjectDespawned event) {
		GameObject go = event.getGameObject();
		if (go.getId() == SET_BOX_TRAP){
			freshBoxTrapList.removeIf(boxTrap -> boxTrap.getObject() == go);
			liveBoxTrapList.removeIf(boxTrap -> boxTrap.getObject() == go);
		}
	}

	@Subscribe
	public void onGameTick(GameTick event) {
//		this.freshBoxTrapList.forEach(BoxTrap::decrement);
		this.freshBoxTrapList.removeIf(boxTrap -> {
			boxTrap.decrement();
			return boxTrap.getTicksLeft() < 0 && liveBoxTrapList.add(new BoxTrap(boxTrap.getLocation(), boxTrap.getObject()));
		});
	}
}
