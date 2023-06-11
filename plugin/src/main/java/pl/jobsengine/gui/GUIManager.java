package pl.jobsengine.gui;

import lombok.Getter;
import org.bukkit.inventory.Inventory;
import pl.jobsengine.JobsEngine;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class GUIManager {

    private final JobsEngine plugin = JobsEngine.getInstance();
    private final List<GUIMethods> inventories = new ArrayList<>();

    public void registerInventory(GUIMethods gui) {
        gui.onOpen();
        inventories.add(gui);
    }

    public void removeInventory(GUIMethods gui) {
        gui.onClose();
        inventories.remove(gui);
    }

    public void onClick(Inventory inventory, int slot) {
        GUIMethods gui = getGUIByInventory(inventory);
        if(gui == null) {
            return;
        }
        gui.onClickSlot(slot);
    }

    @Nullable
    public GUIMethods getGUIByInventory(Inventory inventory) {
        for(GUIMethods gui : inventories) {
            if(gui.getInventory().equals(inventory)) {
                return gui;
            }
        }
        return null;
    }

    public void closeAllInventories() {
        for(GUIMethods gui : getInventories()) {
            gui.closeInventory();
        }
    }

}
