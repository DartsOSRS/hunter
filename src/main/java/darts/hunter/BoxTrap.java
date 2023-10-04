package darts.hunter;

import lombok.Getter;
import net.runelite.api.GameObject;
import net.runelite.api.coords.WorldPoint;

public class BoxTrap {
    private static final int BOX_TRAP_DURATION = 18;

    @Getter
    private final WorldPoint location;

    @Getter
    private String ticksLeftDisplay;

    @Getter
    private int ticksLeft;

    @Getter
    private GameObject object;

    BoxTrap(WorldPoint location, GameObject object) {
        this.ticksLeft = BOX_TRAP_DURATION;
        this.ticksLeftDisplay = "" + this.ticksLeft;
        this.location = location;
        this.object = object;
    }

    public void decrement() {
        this.ticksLeftDisplay = "" + --this.ticksLeft;
    }
}
