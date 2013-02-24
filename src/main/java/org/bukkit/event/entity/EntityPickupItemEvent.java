package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * This event is called when an entity attempts to pick up an Item.
 * <p>
 * If the event is cancelled, the entity will not pick up the Item, which will
 * remain where it was.
 * <p>
 * If the Item entity is removed, the event will be treated as cancelled.
 * <p>
 * If the ItemStack in the Item entity is modified, the new ItemStack will be
 * used.
 */
public class EntityPickupItemEvent extends EntityEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final Item item;
    private boolean cancel = false;

    public EntityPickupItemEvent(final Entity entity, final Item item) {
        super(entity);
        this.item = item;
    }

    /**
     * Gets the Item entity the entity is trying to pick up.
     *
     * @return Item
     */
    public Item getItem() {
        return item;
    }

    public boolean isCancelled() {
        if (item.isDead()) cancel = true;
        return cancel;
    }

    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
