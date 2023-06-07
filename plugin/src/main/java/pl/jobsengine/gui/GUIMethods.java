package pl.jobsengine.gui;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.jobsengine.JobsEngine;

import java.util.List;

@Getter
@Setter
public class GUIMethods {

    private final JobsEngine plugin = JobsEngine.getInstance();
    private Inventory inventory;
    private Player owner;
    private boolean guiProtected = false;

    public GUIMethods(Player owner, String inventoryName, int inventorySize) {
        this.owner = owner;
        this.inventory = plugin.getServer().createInventory(owner, inventorySize, inventoryName);
        plugin.getGuiManager().registerInventory(this);
        owner.openInventory(inventory);
    }

    public void setSlot(int slot, Material material, String name, List<String> lore) {
        ItemStack is = new ItemStack(material);
        ItemMeta im = is.getItemMeta();
        if(im != null) {
            im.setDisplayName(name);
            im.setLore(lore);
        }
        is.setItemMeta(im);
        setSlot(slot, is);
    }

    public void setSlot(int slot, int count, Material material, String name, List<String> lore) {
        ItemStack is = new ItemStack(material);
        is.setAmount(count);
        ItemMeta im = is.getItemMeta();
        if(im != null) {
            im.setDisplayName(name);
            im.setLore(lore);
        }
        is.setItemMeta(im);
        setSlot(slot, is);
    }

    public void setSlot(int slot, ItemStack is) {
        inventory.setItem(slot, is);
    }

    public void setGUIProtected(boolean protect) {
        guiProtected = protect;
    }

}
