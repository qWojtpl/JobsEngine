package pl.jobsengine.gui.list;

import org.bukkit.entity.Player;
import pl.jobsengine.gui.GUIMethods;
import pl.jobsengine.gui.PluginGUI;
import pl.jobsengine.jobs.JobIcon;
import pl.jobsengine.jobs.JobsManager;

public class OfferGUI extends GUIMethods implements PluginGUI {

    public OfferGUI(Player owner, String inventoryName, int inventorySize) {
        super(owner, inventoryName, inventorySize);
    }

    @Override
    public void onOpen() {
        setGUIProtected(true);
        JobsManager jobsManager = getPlugin().getJobsManager();
        int i = 0;
        for(String name : jobsManager.getJobs().keySet()) {
            JobIcon icon = jobsManager.getJobs().get(name).getIcon();
            setSlot(i, icon.getMaterial(), icon.getName(), icon.getLore());
            i++;
        }
    }

    @Override
    public void onClose() {

    }

    @Override
    public void onClickSlot(int slot) {
        if(slot == 0) {
            getPlugin().getJobsManager().assignJob(getOwner().getName(), getPlugin().getJobsManager().getJobByName("miner"));
            closeInventory();
        }
    }

}
