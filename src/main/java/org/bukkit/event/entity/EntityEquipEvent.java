package org.bukkit.event.entity;

import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.HandlerList;

/**
 * This event is called when an entity that can use equipment attempts to pick
 * up an item with the intent of equipping it.
 * <p>
 * If this event is not cancelled, and there was already an item in the same
 * equipment slot (this can happen when the entity tries to 'upgrade'), the
 * old item may be dropped with a chance determined by the slot's dropChance,
 * as defined by {@link org.bukkit.inventory.EntityEquipment}. If this is
 * undesirable, set the dropChance of the item to 0 in tfhe EntityEquipment
 * class.
 * <p>
 * After this event, the Item will be removed.
 */
public class EntityEquipEvent extends EntityPickupItemEvent {
    private static final HandlerList handlers = new HandlerList();
    private final int equipSlot;

    public EntityEquipEvent(final LivingEntity entity, final Item item, final int equipSlot) {
        super(entity, item);
        this.equipSlot = equipSlot;
    }

    /**
     * Gets the LivingEntity involved in this event.
     *
     * @return LivingEntity
     */
    @Override
    public LivingEntity getEntity() {
        return (LivingEntity) entity;
    }

    /**
     * Gets the raw slot the item will be equipped to.
     * <p>
     * For the values 0-3, the raw slot is the same as the indices in
     * {@link org.bukkit.inventory.EntityEquipment#getArmorContents()}.
     * <p>
     * For the value 4, the raw slot is equivalent to
     * {@link org.bukkit.inventory.EntityEquipment#getItemInHand()}.
     *
     * @return raw slot to equip the item.
     */
    public int getEquipSlot() {
        return equipSlot;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
