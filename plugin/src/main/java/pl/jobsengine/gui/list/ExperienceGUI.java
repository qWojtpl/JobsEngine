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

    private HashMap<String, HashMap<String, Double>> pagesMaps;
    private HashMap<Integer, String> pagesSlots;

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
        pagesMaps = new HashMap<>();
        pagesSlots = new HashMap<>();
        pagesMaps.put("mining", expInfo.getBlockBreaks());
        pagesSlots.put(47, "mining");
        pagesMaps.put("killing", expInfo.getMobKills());
        pagesSlots.put(48, "killing");
        pagesMaps.put("fishing", expInfo.getFishItems());
        pagesSlots.put(49, "fishing");
        pagesMaps.put("building", expInfo.getBuildBlocks());
        pagesSlots.put(50, "building");
        pagesMaps.put("eating", expInfo.getEatItems());
        pagesSlots.put(51, "eating");
        loadPage("mining");
    }

    @Override
    public void onClickSlot(int slot) {
        if(pagesSlots.containsKey(slot)) {
            loadPage(pagesSlots.get(slot));
        }
    }

    public void loadPage(String pageName) {
        fillWith(Material.BLACK_STAINED_GLASS_PANE);
        HashMap<String, Double> pageInfo = pagesMaps.getOrDefault(pageName, new HashMap<>());
        setSlot(47, Material.DIAMOND_PICKAXE, "§2Mining", getLore("§aSwitch to mining section"));
        setSlot(48, Material.WOODEN_SWORD, "§2Killing", getLore("§aSwitch to killing section"));
        setSlot(49, Material.FISHING_ROD, "§2Fishing", getLore("§aSwitch to fishing section"));
        setSlot(50, Material.GOLDEN_HELMET, "§2Building", getLore("§aSwitch to building section"));
        setSlot(51, Material.COOKED_BEEF, "§2Eating", getLore("§aSwitch to eating section"));
        setSlotEnchanted(getSlotByName(pageName), true);
        List<String> extendedLore = new ArrayList<>();
        int i = 0;
        for(String trigger : pageInfo.keySet()) {
            Material m = Material.getMaterial(trigger.toUpperCase());
            if(m == null) {
                m = Material.getMaterial(trigger.toUpperCase() + "_SPAWN_EGG");
                if(m == null) {
                    m = Material.BEDROCK;
                }
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
        if(i == 0) {
            setSlot(22, Material.BARRIER, "§4No experience",
                    getLore("§cYou can't receive experience for " + pageName + "!"));
        } else {
            if(extendedLore.size() > 0) {
                setSlot(35, Material.BEDROCK, "§aAnd (" + (pageInfo.size() - 35) + ") more...", extendedLore);
            }
        }
    }

    private int getSlotByName(String name) {
        for(int slot : pagesSlots.keySet()) {
            if(pagesSlots.get(slot).equals(name)) {
                return slot;
            }
        }
        return 0;
    }

}
