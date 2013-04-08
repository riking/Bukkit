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
    private ClickAction button;
    private Result result;
    private int whichSlot;
    private int rawSlot;
    private ItemStack current = null;

    /**
     * Represents a Inventory click action
     */
    public enum ClickAction {
        /**
         * Left mouse button without shift and not dragging inside the
         * inventory.
         * <p>
         * DEFAULT behavior:
         * <ul>
         * <li>If the cursor is air, and there is a item in the clicked slot, the whole itemstack is picked up.</li>
         * <li>If the cursor is air, and there is no item in the clicked slot, this does nothing.</li>
         * <li>If the cursor has an item, and there is no item in the clicked slot, this deposits the whole itemstack.</li>
         * <li>If the cursor has an item, and the same item is in the clicked slot, as much as possible (up to {@link Inventory#getMaxStackSize()}) of the itemstack is deposited.</li>
         * <li>If the cursor and the clicked slot have different materials, they are exchanged.</li>
         * </ul>
         */
        LEFT,
        /**
         * Shift key plus left mouse button.
         * <p>
         * DEFAULT behavior:
         * <ul>
         * <li>If the player's inventory is open:
         *  <ul>
         *  <li>If the clicked item is a wearable piece of armor, it is equipped.</li>
         *  <li>If the clicked slot is not on the player's hotbar, and there is room on the player's hotbar, it is moved to the rightmost slot on the hotbar.</li>
         *  <li>If there is no room on the player's hotbar, and the clicked slot is within the player's inventory, this does nothing, excluding the armor behavior mentioned above.</li>
         *  <li>If there is no room on the player's hotbar, and the clicked slot is in the player's crafting grid, the clicked item is moved to the first available slot in the player's inventory, starting on the hotbar from the right to the left, then starting from the top-left and going across.</li>
         *  <li>If the clicked slot is on the hotbar, the item is moved to the first available slot in the player's inventory, starting on the top-left and going across.</li>
         *  </ul>
         * </li>
         * <li>If a different inventory is open:
         *  <ul>
         *  <li>If the clicked item is in the player's inventory, it is moved to the top inventory if possible, stacking first with other items of the same type, then filling empty slots; if no slots in the top inventory will accept the item, this does nothing.</li>
         *  <li>If the clicked item is in the top inventory, the item will be moved to the first slot in the player's inventory, starting with the right side of the hotbar, then starting in the bottom-right of the player's inventory and going across.</li>
         *  </ul>
         * </ul>
         */
        SHIFT_LEFT,
        /**
         * Left mouse button outside the inventory holding nothing.
         * <p>
         * DEFAULT behavior:
         * <ul>
         * <li>This does nothing.</li>
         * </ul>
         */
        EMPTY_LEFT,
        /**
         * Middle mouse button (or wheel click).
         * <p>
         * DEFAULT behavior:
         * <ul>
         * <li>If the player is in {@link org.bukkit.GameMode#CREATIVE}, the cursor is filled with a ItemStack of the material of the clicked item and an amount of {@link org.bukkit.Material#getMaxStackSize()}.
         * <li>If the player is not in Creative, this does nothing.</li>
         * </ul>
         */
        MIDDLE,
        /**
         * Right mouse button without shift and not dragging inside the
         * inventory.
         * <p>
         * DEFAULT behavior:
         * <ul>
         * <li>If the cursor is air, and there is an item in the clicked slot, half of the itemstack is picked up, with the odd item going to the cursor.</li>
         * <li>If the cursor is air, and there is no item in the clicked slot, this does nothing.</li>
         * <li>If the cursor has an item, and the same item is in the clicked slot, a single item is deposited in the clicked slot, unless that would exceed {@link org.bukkit.Material#getMaxStackSize()}.</li>
         * <li>If the cursor has an item, and a different item is in the clicked slot, the two items are exchanged.</li>
         * </ul>
         */
        RIGHT,
        /**
         * Shift key plus right mouse button.
         * <p>
         * DEFAULT behavior:
         * @see {@link #SHIFT_LEFT}
         */
        SHIFT_RIGHT,
        /**
         * Right mouse button outside the inventory holding nothing.
         * <p>
         * DEFAULT behavior:
         * <ul>
         * <li>This does nothing.</li>
         * </ul>
         */
        EMPTY_RIGHT,
        /**
         * Number key 1.
         * <p>
         * DEFAULT behavior:
         * For the purposes of this description, the "hotbar slot" will be the slot on the player's hotbar indicated by the pressed key; e.g. for key 1, the left most slot, and the rightmost slot for key 9.
         * <ul>
         * <li>If the clicked slot is in the player's inventory:
         *  <ul>
         *  <li>If the clicked slot is the hotbar slot, this does nothing.</li>
         *  <li>The items in the clicked slot and the hotbar slot are exchanged.</li>
         *  </ul>
         * </li>
         * <li>If the clicked slot is in a different inventory:
         *  <ul>
         *  <li>If the clicked slot has an item, and the hotbar slot has the same item, the clicked slot will be deposited in the hotbar slot up to {@link org.bukkit.Material#getMaxStackSize()}, then in the first available slot, starting from the left side of the hotbar, then starting from the upper-left of the inventory and going across.</li>
         *  <li>If the clicked slot has an item, and the hotbar slot is empty, the clicked slot will be placed in the hotbar slot.</li>
         *  <li>If the clicked slot has an item, and the hotbar slot has a different item, the clicked slot will be placed in the hotbar slot, and the hotbar slot item will be moved to a different slot
         */
        NUMBER_1,djd
        NUMBER_2,
        NUMBER_3,
        NUMBER_4,
        NUMBER_5,
        NUMBER_6,
        NUMBER_7,
        NUMBER_8,
        NUMBER_9,
        /**
         * Double left click (Collect all of type)
         */
        DOUBLE_CLICK,
        /**
         * Left click drag start
         * @see {@link InventoryPaintEvent}
         */
        DRAG_START_LEFT,
        /**
         * Right click drag start
         * @see {@link InventoryPaintEvent}
         */
        DRAG_START_RIGHT,
        /**
         * Left click dragging
         * @see {@link InventoryPaintEvent}
         */
        DRAG_LEFT,
        /**
         * Right click dragging
         * @see {@link InventoryPaintEvent}
         */
        DRAG_RIGHT,
        /**
         * Left click drag complete
         * @see {@link InventoryPaintEvent}
         */
        DRAG_FINISH_LEFT,
        /**
         * Right click drag complete
         * @see {@link InventoryPaintEvent}
         */
        DRAG_FINISH_RIGHT,
        ;
    }

    @Deprecated
    public InventoryClickEvent(InventoryView what, SlotType type, int slot, boolean right, boolean shift) {
        this(what, type, slot, (right ? (shift ? ClickAction.SHIFT_RIGHT : ClickAction.RIGHT) : (shift ? ClickAction.SHIFT_LEFT : ClickAction.LEFT)));
    }

    public InventoryClickEvent(InventoryView what, SlotType type, int slot, ClickAction button) {
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
     * @deprecated in favor of {@link #getAction()}
     * @return True if the click is a right-click.
     */
    @Deprecated
    public boolean isRightClick() {
        return button == ClickAction.RIGHT || button == ClickAction.SHIFT_RIGHT || button == ClickAction.DRAG_RIGHT;
    }

    /**
     * @deprecated in favor of {@link #getAction()}
     * @return True if the click is a left-click.
     */
    @Deprecated
    public boolean isLeftClick() {
        return button == ClickAction.LEFT || button == ClickAction.SHIFT_LEFT || button == ClickAction.DRAG_LEFT;
    }

    /**
     * @deprecated in favor of {@link #getAction()}
     * @return True if the click is a shift-click.
     */
    @Deprecated
    public boolean isShiftClick() {
        return button == ClickAction.SHIFT_LEFT || button == ClickAction.SHIFT_RIGHT;
    }

    /**
     * @return The mouse button used in this click event
     */
    public ClickAction getAction() {
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
