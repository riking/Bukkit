package org.bukkit.event.block;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.block.Beacon;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.potion.PotionEffect;

import com.google.common.collect.ImmutableList;

/**
 * This event is called whenever a beacon "pulses" (attempts to refresh the
 * effects on the players within its effective radius).
 * <p>
 * This event will not be called for beacons that are inactive. However, it
 * will still be called when they have no nearby players. See
 * {@link Beacon#isActive()} for details about the definition of inactive.
 * <p>
 * Any changes to the beacon state during this event may not be used until the
 * next pulse.
 */
public class BeaconPulseEvent extends BlockEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private final Beacon beacon;
    private List<HumanEntity> players;
    private List<PotionEffect> effects = null;

    public BeaconPulseEvent(final Beacon beacon, List<HumanEntity> players) {
        super(beacon.getBlock());
        this.beacon = beacon;
        this.players = players;
        cancelled = players.isEmpty();
    }

    /**
     * The beacon that is pulsing.
     *
     * @return pulsing beacon
     */
    public Beacon getBeacon() {
        return beacon;
    }

    /**
     * A mutable collection of the players within the beacon's radius.
     *
     * @return all affected players
     */
    public List<HumanEntity> getPlayers() {
        return players;
    }

    /**
     * Change the set of players affected by the beacon in this pulse.
     *
     * @param newPlayers new list of affected players.
     */
    public void setPlayers(Collection<HumanEntity> newPlayers) {
        // note that contract states that getPlayers() is mutable, so recreate the collection
        players = new ArrayList<HumanEntity>(newPlayers);
    }

    /**
     * Get a copy of the PotionEffect list the beacon is providing to the
     * affected players in this pulse. <b>This method accounts for the
     * one-time modifications performed by changePulseEffects().</b>
     * <p>
     * This method should be favored over {@link Beacon#getEffects()} because
     * it respects the one-time modifications to the pulse.
     *
     * @return an immutable list of PotionEffects to apply to players
     */
    public List<PotionEffect> getEffects() {
        // Lazy
        if (effects == null) {
            effects = beacon.getEffects();
        }
        return ImmutableList.copyOf(effects);
    }

    /**
     * Perform a <b>one-time modification</b> of the effects used in this
     * pulse.
     * <p>
     * <b>{@link Beacon#setEffects(Collection)} is preferred</b> when you want
     * to make the change permanent.
     * <p>
     * The duration and ambient settings of the provided effects may be
     * ignored.
     *
     * @param modifiedEffects new effects for this pulse only
     */
    public void changePulseEffects(Collection<PotionEffect> modifiedEffects) {
        if (modifiedEffects instanceof List) {
            this.effects = (List<PotionEffect>) modifiedEffects;
        } else {
            this.effects = new ArrayList<PotionEffect>(modifiedEffects);
        }
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
