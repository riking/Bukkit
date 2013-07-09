package org.bukkit.inventory;

import org.bukkit.block.Beacon;

/**
 * Represents a Beacon's inventory.
 */
public interface BeaconInventory extends Inventory {
    /**
     * Set the item powering the beacon.
     *
     * @param item The new item
     */
    void setItem(ItemStack item);

    /**
     * Get the item powering the beacon.
     *
     * @return The current item.
     */
    ItemStack getItem();

    /**
     * Get the Beacon this BeaconInventory belongs to.
     *
     * @return the Beacon BlockState
     */
    @Override
    public Beacon getHolder();
}
