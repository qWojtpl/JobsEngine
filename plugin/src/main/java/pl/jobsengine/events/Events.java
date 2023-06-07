package pl.jobsengine.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pl.jobsengine.JobsEngine;
import pl.jobsengine.jobs.Job;
import pl.jobsengine.jobs.JobsManager;

public class Events implements Listener {

    private final JobsEngine plugin = JobsEngine.getInstance();
    private final JobsManager jobsManager = plugin.getJobsManager();

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
        String blockName = event.getBlock().getType().name();
        if(!job.getExpInfo().getBlockBreaks().containsKey(blockName)) {
            if(job.getExpInfo().getBlockBreaks().containsKey("ANY")) {
                expToGive += job.getExpInfo().getBlockBreaks().get("ANY");
            }
        } else {
            expToGive += job.getExpInfo().getBlockBreaks().get(blockName);
        }
        jobsManager.createPlayerProfile(event.getPlayer().getName()).getJobStats(job).addExp(expToGive);
        event.getPlayer().sendMessage("Your exp is now: " + jobsManager.getPlayerProfile(
                event.getPlayer().getName()).getJobStats(job).getExp());
    }

}
