package org.bukkit.event.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;

public class InventoryClickEvent extends InventoryEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private SlotType slot_type;
    private MouseButton button;
    private Result result;
    private int whichSlot;
    private int rawSlot;
    private ItemStack current = null;

    /**
     * Represents a mouse click action
     */
    public enum MouseButton {
        /**
         * Left mouse button
         */
        LEFT,
        /**
         * Shift key plus left mouse button
         */
        SHIFT_LEFT,
        /**
         * Middle mouse button or wheel click
         */
        MIDDLE,
        /**
         * Right mouse button
         */
        RIGHT,
        /**
         * Shift key plus right mouse button
         */
        SHIFT_RIGHT,
        /**
         * Number key 1-9
         */
        NUMBER,
        /**
         * Left click dragging
         */
        DRAG_LEFT,
        /**
         * Right click dragging
         */
        DRAG_RIGHT,
        ;
    }

    @Deprecated
    public InventoryClickEvent(InventoryView what, SlotType type, int slot, boolean right, boolean shift) {
        this(what, type, slot, (right ? (shift ? MouseButton.SHIFT_RIGHT : MouseButton.RIGHT) : (shift ? MouseButton.SHIFT_LEFT : MouseButton.LEFT)));
    }

    public InventoryClickEvent(InventoryView what, SlotType type, int slot, MouseButton button) {
        super(what);
        this.slot_type = type;
        this.button = button;
        this.result = Result.DEFAULT;
        this.rawSlot = slot;
        this.whichSlot = what.convertSlot(slot);
    }

    /**
     * Get the type of slot that was clicked.
     * @return The slot type.
     */
    public SlotType getSlotType() {
        return slot_type;
    }

    /**
     * Get the current item on the cursor.
     * @return The cursor item
     */
    public ItemStack getCursor() {
        return getView().getCursor();
    }

    /**
     * Get the current item in the clicked slot.
     * @return The slot item.
     */
    public ItemStack getCurrentItem() {
        if(slot_type == SlotType.OUTSIDE) return current;
        return getView().getItem(rawSlot);
    }

    /**
     * @deprecated in favor of {@link #getButton()}
     * @return True if the click is a right-click.
     */
    @Deprecated
    public boolean isRightClick() {
        return button == MouseButton.RIGHT || button == MouseButton.SHIFT_RIGHT || button == MouseButton.DRAG_RIGHT;
    }

    /**
     * @deprecated in favor of {@link #getButton()}
     * @return True if the click is a left-click.
     */
    @Deprecated
    public boolean isLeftClick() {
        return button == MouseButton.LEFT || button == MouseButton.SHIFT_LEFT || button == MouseButton.DRAG_LEFT;
    }

    /**
     * @deprecated in favor of {@link #getButton()}
     * @return True if the click is a shift-click.
     */
    @Deprecated
    public boolean isShiftClick() {
        return button == MouseButton.SHIFT_LEFT || button == MouseButton.SHIFT_RIGHT;
    }

    /**
     * @return The mouse button used in this click event
     */
    public MouseButton getButton() {
        return button;
    }

    public void setResult(Result newResult) {
        result = newResult;
    }

    public Result getResult() {
        return result;
    }

    /**
     * Get the player who performed the click.
     * @return The clicking player.
     */
    public HumanEntity getWhoClicked() {
        return getView().getPlayer();
    }

    /**
     * Set the item on the cursor.
     * @param what The new cursor item.
     */
    public void setCursor(ItemStack what) {
        getView().setCursor(what);
    }

    /**
     * Set the current item in the slot.
     * @param what The new slot item.
     */
    public void setCurrentItem(ItemStack what) {
        if(slot_type == SlotType.OUTSIDE) current = what;
        else getView().setItem(rawSlot, what);
    }

    public boolean isCancelled() {
        return result == Result.DENY;
    }

    public void setCancelled(boolean toCancel) {
        result = toCancel ? Result.DENY : Result.ALLOW;
    }

    /**
     * The slot number that was clicked, ready for passing to {@link Inventory#getItem(int)}. Note
     * that there may be two slots with the same slot number, since a view links two different inventories.
     * @return The slot number.
     */
    public int getSlot() {
        return whichSlot;
    }

    /**
     * The raw slot number, which is unique for the view.
     * @return The slot number.
     */
    public int getRawSlot() {
        return rawSlot;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
