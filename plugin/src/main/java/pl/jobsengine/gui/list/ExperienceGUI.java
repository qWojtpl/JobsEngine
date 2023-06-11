package pl.jobsengine.gui.list;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.jobsengine.JobsEngine;
import pl.jobsengine.gui.GUIMethods;
import pl.jobsengine.jobs.ExpInfo;
import pl.jobsengine.jobs.Job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExperienceGUI extends GUIMethods {

    private HashMap<String, HashMap<String, Double>> pages;

    public ExperienceGUI(Player owner, String inventoryName, int inventorySize) {
        super(owner, inventoryName, inventorySize);
    }

    @Override
    public void onOpen() {
        setGUIProtected(true);
        Job job = JobsEngine.getInstance().getJobsManager().getPlayersJob(getOwner().getName());
        if(job == null) {
            return;
        }
        ExpInfo expInfo = job.getExpInfo();
        pages = new HashMap<>();
        pages.put("mining", expInfo.getBlockBreaks());
        pages.put("killing", expInfo.getMobKills());
        loadPage("mining");
    }

    @Override
    public void onClickSlot(int slot) {
        if(slot == 47) {
            loadPage("mining");
        } else if(slot == 48) {
            loadPage("killing");
        } else if(slot == 49) {
            loadPage("fishing");
        } else if(slot == 50) {
            loadPage("building");
        } else if(slot == 51) {
            loadPage("eating");
        }
    }

    public void loadPage(String pageName) {
        fillWith(Material.BLACK_STAINED_GLASS_PANE);
        HashMap<String, Double> pageInfo = pages.getOrDefault(pageName, new HashMap<>());
        setSlot(47, Material.DIAMOND_PICKAXE, "§2Mining", getLore("§aSwitch to mining section"));
        setSlot(48, Material.WOODEN_SWORD, "§2Killing", getLore("§aSwitch to killing section"));
        setSlot(49, Material.FISHING_ROD, "§2Fishing", getLore("§aSwitch to fishing section"));
        setSlot(50, Material.GOLDEN_HELMET, "§2Building", getLore("§aSwitch to building section"));
        setSlot(51, Material.COOKED_BEEF, "§2Eating", getLore("§aSwitch to eating section"));
        if(pageName.equalsIgnoreCase("mining")) {
            setSlotEnchanted(47, true);
        } else if(pageName.equalsIgnoreCase("killing")) {
            setSlotEnchanted(48, true);
        } else if(pageName.equalsIgnoreCase("fishing")) {
            setSlotEnchanted(49, true);
        } else if(pageName.equalsIgnoreCase("building")) {
            setSlotEnchanted(50, true);
        } else if(pageName.equalsIgnoreCase("eating")) {
            setSlotEnchanted(51, true);
        }
        List<String> extendedLore = new ArrayList<>();
        int i = 0;
        for(String trigger : pageInfo.keySet()) {
            Material m = Material.getMaterial(trigger.toUpperCase());
            if(m == null) {
                m = Material.BEDROCK;
            }
            String name = trigger;
            if(trigger.equals("ANY")) {
                name = "Any block";
            }
            name = name.replace("_", " ");
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            double exp = pageInfo.get(trigger);
            if(i >= 35) {
                if(pageInfo.size() > i) {
                    extendedLore.add("§a" + name + ": §2" + exp + " exp");
                    i++;
                    continue;
                }
            }
            setSlot(i, m, "§2" + name, getLore("§aExperience for " + pageName + ": §2"
                    + exp + " exp"));
            i++;
        }
        if(extendedLore.size() > 0) {
            setSlot(35, Material.BEDROCK, "§aAnd (" + (pageInfo.size() - 35) + ") more...", extendedLore);
        }
    }

}
