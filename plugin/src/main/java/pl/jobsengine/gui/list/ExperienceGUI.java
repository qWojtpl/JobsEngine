package pl.jobsengine.gui.list;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.jobsengine.JobsEngine;
import pl.jobsengine.gui.GUIMethods;
import pl.jobsengine.jobs.ExpInfo;
import pl.jobsengine.jobs.Job;

import java.util.ArrayList;
import java.util.List;

public class ExperienceGUI extends GUIMethods {

    public ExperienceGUI(Player owner, String inventoryName, int inventorySize) {
        super(owner, inventoryName, inventorySize);
    }

    @Override
    public void onOpen() {
        setGUIProtected(true);
        fillWith(Material.BLACK_STAINED_GLASS_PANE);
        Job job = JobsEngine.getInstance().getJobsManager().getPlayersJob(getOwner().getName());
        if(job == null) {
            return;
        }
        setSlot(47, Material.DIAMOND_PICKAXE, "§2Mining", getLore("§aSwitch to mining section"));
        setSlotEnchanted(47, true);
        setSlot(48, Material.WOODEN_SWORD, "§2Killing", getLore("§aSwitch to killing section"));
        setSlot(49, Material.FISHING_ROD, "§2Fishing", getLore("§aSwitch to fishing section"));
        setSlot(50, Material.GOLDEN_HELMET, "§2Building", getLore("§aSwitch to building section"));
        setSlot(51, Material.COOKED_BEEF, "§2Eating", getLore("§aSwitch to eating section"));
        ExpInfo expInfo = job.getExpInfo();
        List<String> extendedLore = new ArrayList<>();
        int i = 0;
        for(String block : expInfo.getBlockBreaks().keySet()) {
            Material m = Material.getMaterial(block.toUpperCase());
            if(m == null) {
                m = Material.BEDROCK;
            }
            String name = block;
            if(block.equals("ANY")) {
                name = "Any block";
            }
            name = name.replace("_", " ");
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            double exp = expInfo.getBlockBreaks().get(block);
            if(i >= 35) {
                if(expInfo.getBlockBreaks().size() > i) {
                    extendedLore.add("§a" + name + ": §2" + exp + " exp");
                    i++;
                    continue;
                }
            }
            setSlot(i, m, "§2" + name, getLore("§aExperience for mining: §2"
                    + exp + " exp"));
            i++;
        }
        if(extendedLore.size() > 0) {
            setSlot(35, Material.BEDROCK, "§aAnd (" + (expInfo.getBlockBreaks().size() - 35) + ") more...", extendedLore);
        }
    }

    @Override
    public void onClickSlot(int slot) {

    }

}
