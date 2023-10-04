package darts.hunter;

import net.runelite.client.config.*;

import java.awt.*;


@ConfigGroup("hunter")
public interface BoxTrapConfig extends Config {
	@ConfigSection(
			name = "Timer Settings",
			description = "All settings relating to the timer",
			position = 100
	)
	String timerSettings = "timerSettings";

	@Alpha
	@ConfigItem(
			keyName = "enableTimer",
			name = "Enable Timer",
			description = "If the timer background should be enabled",
			position = 0,
			section = timerSettings
	)
	default boolean getEnableTimer() {
		return true;
	}

	@ConfigItem(
			keyName = "textColor",
			name = "Text Color",
			description = "Color of normal timer text",
			position = 1,
			section = timerSettings
	)
	default Color getTextColor() {
		return Constants.COLOR_TEXT;
	}

	@ConfigItem(
			keyName = "enableTextOutline",
			name = "Outline Text",
			description = "If the timer text should have an outline",
			position = 2,
			section = timerSettings
	)
	default boolean getEnableTextOutline() {
		return false;
	}

	@Range(
			min = 16,
			max = 30
	)
	@ConfigItem(
			keyName = "backgroundSize",
			name = "Background Size",
			description = "How large the timer background should be",
			position = 3,
			section = timerSettings
	)
	default int getBackgroundSize() {
		return Constants.BACKGROUND_SIZE;
	}

	@Alpha
	@ConfigItem(
			keyName = "backgroundColor",
			name = "Background Color",
			description = "Color of background box surrounding the timer",
			position = 4,
			section = timerSettings
	)
	default Color getBackgroundColor() {
		return Constants.COLOR_BACKGROUND;
	}

	@ConfigSection(
			name = "Box Trap Settings",
			description = "All settings relating to box traps",
			position = 101
	)
	String boxTrapSettings = "boxTrapSettings";

	@Alpha
	@ConfigItem(
			position = 0,
			keyName = "hexColorFreshTrap",
			name = "Fresh trap",
			description = "Color of fresh trap timer",
			section = boxTrapSettings
	)
	default Color getFreshTrapColor()
	{
		return Color.GREEN;
	}

	@Alpha
	@ConfigItem(
			position = 1,
			keyName = "hexColorLiveTrap",
			name = "Live trap",
			description = "Color of live trap timer",
			section = boxTrapSettings
	)
	default Color getLiveTrapColor()
	{
		return Color.RED;
	}

	@Alpha
	@ConfigItem(
			position = 2,
			keyName = "hexColorDeadTrap",
			name = "Dead trap",
			description = "Color of dead trap timer",
			section = boxTrapSettings
	)
	default Color getDeadTrapColor()
	{
		return Color.BLACK;
	}
}