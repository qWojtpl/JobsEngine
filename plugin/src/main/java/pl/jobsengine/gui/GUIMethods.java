package pl.jobsengine.gui;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.jobsengine.JobsEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class GUIMethods {

    private final JobsEngine plugin = JobsEngine.getInstance();
    private final Player owner;
    private final Inventory inventory;
    private final String inventoryName;
    private final int inventorySize;
    private boolean guiProtected = false;

    public GUIMethods(Player owner, String inventoryName, int inventorySize) {
        this.owner = owner;
        this.inventoryName = inventoryName;
        this.inventorySize = inventorySize;
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

    public List<String> getLore(String... loreLine) {
        return new ArrayList<>(Arrays.asList(loreLine));
    }

    public void setSlotEnchanted(int slot, boolean enchanted) {
        ItemStack is = inventory.getItem(slot);
        if(is == null) {
            return;
        }
        ItemMeta im = is.getItemMeta();
        if(im == null) {
            return;
        }
        if(enchanted) {
            im.addEnchant(Enchantment.DURABILITY, 1, true);
            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            im.removeEnchant(Enchantment.DURABILITY);
            im.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        is.setItemMeta(im);
        setSlot(slot, is);
    }

    public void setGUIProtected(boolean protect) {
        guiProtected = protect;
    }

    public void closeInventory() {
        owner.closeInventory();
    }

    public void fillWith(Material material) {
        for(int i = 0; i < inventorySize; i++) {
            setSlot(i, material, " ", new ArrayList<>());
        }
    }

    public void onOpen() {

    }

    public void onClose() {

    }

    public void onClickSlot(int slot) {

    }

}
