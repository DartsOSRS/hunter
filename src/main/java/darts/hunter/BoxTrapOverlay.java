package darts.hunter;

import com.google.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;
import java.awt.Polygon;
import java.awt.Shape;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import java.awt.geom.Rectangle2D;

public class BoxTrapOverlay extends Overlay {
    private final BoxTrapPlugin plugin;
    private final BoxTrapConfig config;
    private final Client client;
    private final ModelOutlineRenderer modelOutlineRenderer;

    @Inject
    BoxTrapOverlay(BoxTrapPlugin plugin, BoxTrapConfig config, Client client, ModelOutlineRenderer modelOutlineRenderer) {
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
        this.plugin = plugin;
        this.config = config;
        this.client = client;
        this.modelOutlineRenderer = modelOutlineRenderer;
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        final Color colorText = this.config.getTextColor();
        final boolean shouldOutline = this.config.getEnableTextOutline();

        final Color colorBackground = this.config.getBackgroundColor();
        final int backgroundSize = this.config.getBackgroundSize();

        final boolean timerEnabled = this.config.getEnableTimer();

        final Color freshTrap = this.config.getFreshTrapColor();
        final Color liveTrap = this.config.getLiveTrapColor();
        final Color deadTrap = this.config.getDeadTrapColor();

        this.plugin.getFreshBoxTrapList().forEach(trap -> {
            final String text = trap.getTicksLeftDisplay();
            final LocalPoint localPoint = LocalPoint.fromWorld(this.client, trap.getLocation());
            if (localPoint != null) {
                final Color color;
                final int counter = trap.getTicksLeft();
                color = colorText;

                final Point point = Perspective.getCanvasTextLocation(this.client, graphics, localPoint, text, 0);
                Rectangle2D textBounds = graphics.getFontMetrics().getStringBounds(text, graphics);

                if (timerEnabled) {
                    this.drawTextBackground(graphics, point, colorBackground, textBounds, backgroundSize);
                    this.drawText(graphics, point, color, shouldOutline, text);
                }

                modelOutlineRenderer.drawOutline(trap.getObject(), 2, freshTrap, 0);
            }
        });

        this.plugin.getLiveBoxTrapList().forEach(trap -> {
            final LocalPoint localPoint = LocalPoint.fromWorld(this.client, trap.getLocation());
            if (localPoint != null) {
                modelOutlineRenderer.drawOutline(trap.getObject(), 2, liveTrap, 0);
            }
        });
        return null;
    }

    private void drawTextBackground(Graphics2D graphics, Point point, Color color, Rectangle2D textBounds, int size) {
        graphics.setColor(color);

        final int x = (int) (point.getX() - (size / 2) + (textBounds.getWidth() / 2));
        final int y = (int) (point.getY() - (size / 2) - (textBounds.getHeight() / 2));

        graphics.fillRect(x, y, size, size);
    }

    private void drawText(Graphics2D graphics, Point point, Color color, boolean shouldOutline, String text) {
        final int x = point.getX();
        final int y = point.getY();

        if (shouldOutline) {
            graphics.setColor(Color.BLACK);
            graphics.drawString(text, x + 1, y + 1);
        }

        graphics.setColor(color);
        graphics.drawString(text, x, y);
    }
}
