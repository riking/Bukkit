package org.bukkit.event.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.bukkit.block.Beacon;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.potion.PotionEffect;

/**
 * This event is called when a player pays to change a beacon. The payment
 * will only be removed after the event if it is not cancelled, so it will
 * still be in the {@link org.bukkit.inventory.BeaconInventory} and
 * retrievable via Beacon.getInventory().getItem().
 */
public class PlayerPayBeaconEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Beacon state;
    private boolean cancelled = false;
    private List<PotionEffect> effects;

    public PlayerPayBeaconEvent(final Beacon beacon, Player player, Collection<PotionEffect> newEffects) {
        super(player);
        state = beacon;
        effects = new ArrayList<PotionEffect>(newEffects);
    }

    /**
     * Get the Beacon involved in this event.
     *
     * @return the Beacon block state
     */
    public Beacon getBeacon() {
        return state;
    }

    /**
     * Get the new PotionEffects to put on the beacon.
     *
     * @return the PotionEffects to apply to the beacon
     */
    public List<PotionEffect> getNewEffects() {
        return effects;
    }

    /**
     * Set the new PotionEffects to put on the beacon.
     * <p>
     * Calling this method will result in {@link Beacon#hasCustomEffects()}
     * returning true after the event.
     *
     * @param newEffects new PotionEffects to apply to the beacon
     */
    public void setNewEffects(List<PotionEffect> newEffects) {
        Validate.noNullElements(newEffects, "Cannot provide null PotionEffects");
        effects = newEffects;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
