package org.bukkit.event.inventory;

import org.bukkit.event.inventory.InventoryClickEvent.ClickAction;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.Recipe;

public class CraftItemEvent extends InventoryClickEvent {
    private Recipe recipe;

    @Deprecated
    public CraftItemEvent(Recipe recipe, InventoryView what, SlotType type, int slot, boolean right, boolean shift) {
        this(recipe, what, type, slot, (right ? (shift ? ClickAction.SHIFT_RIGHT : ClickAction.RIGHT) : (shift ? ClickAction.SHIFT_LEFT : ClickAction.LEFT)));
    }

    @Deprecated
    public CraftItemEvent(Recipe recipe, InventoryView what, SlotType type, int slot, ClickAction button, boolean shift) {
        this(recipe, what, type, slot, (shift && button == ClickAction.LEFT ? ClickAction.SHIFT_LEFT : (shift && button == ClickAction.RIGHT ? ClickAction.SHIFT_RIGHT : button)));
    }

    public CraftItemEvent(Recipe recipe, InventoryView what, SlotType type, int slot, ClickAction button) {
        super(what, type, slot, button);
        this.recipe = recipe;
    }

    /**
     * @return A copy of the current recipe on the crafting matrix.
     */
    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public CraftingInventory getInventory() {
        return (CraftingInventory) super.getInventory();
    }
}
