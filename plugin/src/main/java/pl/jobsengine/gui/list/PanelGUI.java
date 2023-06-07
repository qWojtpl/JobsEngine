package pl.jobsengine.gui.list;

import org.bukkit.entity.Player;
import pl.jobsengine.gui.GUIMethods;
import pl.jobsengine.gui.PluginGUI;

public class PanelGUI extends GUIMethods implements PluginGUI {

    public PanelGUI(Player owner, String inventoryName, int inventorySize) {
        super(owner, inventoryName, inventorySize);
    }

    @Override
    public void onOpen() {
        setGUIProtected(true);
    }

    @Override
    public void onClose() {

    }

    @Override
    public void onClickSlot(int slot) {

    }

}
