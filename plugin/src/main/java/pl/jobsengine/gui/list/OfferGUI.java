package pl.jobsengine.gui.list;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.jobsengine.gui.GUIMethods;
import pl.jobsengine.jobs.JobIcon;
import pl.jobsengine.jobs.JobsManager;

public class OfferGUI extends GUIMethods {

    public OfferGUI(Player owner, String inventoryName, int inventorySize) {
        super(owner, inventoryName, inventorySize);
    }

    @Override
    public void onOpen() {
        JobsManager jobsManager = getPlugin().getJobsManager();
        setGUIProtected(true);
        fillWith(Material.BLACK_STAINED_GLASS_PANE);
        int i = 0;
        for(String name : jobsManager.getJobs().keySet()) {
            JobIcon icon = jobsManager.getJobs().get(name).getIcon();
            setSlot(i, icon.getMaterial(), icon.getName(), icon.getLore());
            i++;
        }
    }

    @Override
    public void onClickSlot(int slot) {
        JobsManager jobsManager = getPlugin().getJobsManager();
        int i = 0;
        for(String name : jobsManager.getJobs().keySet()) {
            if(slot == i) {
                getPlugin().getJobsManager().assignJob(getOwner().getName(), jobsManager.getJobByName(name));
                getOwner().sendMessage("Assigned job: " + name);
                closeInventory();
            }
            i++;
        }
    }

}
