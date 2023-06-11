package pl.jobsengine.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import pl.jobsengine.JobsEngine;
import pl.jobsengine.gui.GUIManager;
import pl.jobsengine.gui.GUIMethods;
import pl.jobsengine.jobs.Job;
import pl.jobsengine.jobs.JobsManager;

public class Events implements Listener {

    private final JobsEngine plugin = JobsEngine.getInstance();
    private final JobsManager jobsManager = plugin.getJobsManager();
    private final GUIManager guiManager = plugin.getGuiManager();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent event) {
        if(event.isCancelled()) {
            return;
        }
        Job job = jobsManager.getPlayersJob(event.getPlayer().getName());
        if(job == null) {
            return;
        }
        double expToGive = 0;
        String blockName = event.getBlock().getType().name().toLowerCase();
        if(!job.getExpInfo().getBlockBreaks().containsKey(blockName)) {
            if(job.getExpInfo().getBlockBreaks().containsKey("ANY")) {
                expToGive += job.getExpInfo().getBlockBreaks().get("ANY");
            }
        } else {
            expToGive += job.getExpInfo().getBlockBreaks().get(blockName);
        }
        jobsManager.createPlayerProfile(event.getPlayer().getName()).getJobStats(job).addExp(expToGive);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onKill(EntityDeathEvent event) {
        Player p = event.getEntity().getKiller();
        if(p == null) {
            return;
        }
        Job job = jobsManager.getPlayersJob(p.getName());
        if(job == null) {
            return;
        }
        double expToGive = 0;
        String mobName = event.getEntity().getType().name().toLowerCase();
        if(!job.getExpInfo().getMobKills().containsKey(mobName)) {
            if(job.getExpInfo().getMobKills().containsKey("ANY")) {
                expToGive += job.getExpInfo().getMobKills().get("ANY");
            }
        } else {
            expToGive += job.getExpInfo().getMobKills().get(mobName);
        }
        jobsManager.createPlayerProfile(p.getName()).getJobStats(job).addExp(expToGive);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        GUIMethods gui = guiManager.getGUIByInventory(event.getInventory());
        if(gui != null) {
            if(gui.isGuiProtected()) {
                event.setCancelled(true);
            }
            guiManager.onClick(event.getClickedInventory(), event.getSlot());
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        GUIMethods gui = guiManager.getGUIByInventory(event.getInventory());
        if(gui != null) {
            if(gui.isGuiProtected()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        GUIMethods gui = guiManager.getGUIByInventory(event.getInventory());
        if(gui != null) {
            guiManager.removeInventory(gui);
        }
    }

}
