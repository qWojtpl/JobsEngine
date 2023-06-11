package pl.jobsengine.gui.list;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import pl.jobsengine.JobsEngine;
import pl.jobsengine.gui.GUIMethods;
import pl.jobsengine.jobs.Job;
import pl.jobsengine.jobs.JobsManager;
import pl.jobsengine.jobs.PlayerProfile;

public class ProfileGUI extends GUIMethods {

    private JobsManager jobsManager;

    public ProfileGUI(Player owner, String inventoryName, int inventorySize) {
        super(owner, inventoryName, inventorySize);
    }

    @Override
    public void onOpen() {
        jobsManager = JobsEngine.getInstance().getJobsManager();
        setGUIProtected(true);
        fillWith(Material.BLACK_STAINED_GLASS_PANE);
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        if(meta != null) {
            meta.setDisplayName("§e" + getOwner().getName());
            meta.setOwnerProfile(getOwner().getPlayerProfile());
            head.setItemMeta(meta);
        }
        setSlot(12, head);
        PlayerProfile profile = jobsManager.getPlayerProfile(getOwner().getName());
        Job job = profile.getCurrentJob();
        if(job == null) {
            setSlot(14, Material.BARRIER, "§4You don't have any job", getLore("§cCheck out job offers to get a job!"));
        } else {
            int level = profile.getJobStats(job).getLevel();
            setSlot(14, job.getIcon().getMaterial(),
                    "§eCurrent job: " + job.getName(),
                    getLore(
                            "§eLevel: " + level,
                            "§eExp: " + Math.round(profile.getJobStats(job).getExp()) + "/" + job.getRequiredExp() * level));
            setSlot(40, Material.BARRIER, "§4Quit current job", getLore("§cGo find something better"));
        }
        setSlot(29, Material.OAK_SIGN, "§2Job stats", getLore("§aSee your jobs stats"));
        setSlot(33, Material.BOOK, "§2Job offers", getLore("§aBrowse job offers"));
    }

    @Override
    public void onClickSlot(int slot) {
        if(slot == 33) {
            closeInventory();
            new OfferGUI(getOwner(), "Offers", 9);
        } else if(slot == 14) {
            if(jobsManager.getPlayersJob(getOwner().getName()) == null) {
                return;
            }
            closeInventory();
            new ExperienceGUI(getOwner(), "Experience", 54);
        }
    }

}
