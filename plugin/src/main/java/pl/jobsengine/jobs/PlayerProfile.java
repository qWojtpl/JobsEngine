package pl.jobsengine.jobs;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import pl.jobsengine.JobsEngine;
import pl.jobsengine.util.PlayerUtil;

import java.util.HashMap;

@Getter
@Setter
public class PlayerProfile {

    private final String player;
    private Job currentJob = null;
    private final HashMap<String, JobStats> stats = new HashMap<>();

    public PlayerProfile(String player) {
        this.player = player;
        JobsEngine.getInstance().getJobsManager().getPlayerProfiles().put(player, this);
    }

    public JobStats getJobStats(Job job) {
        return createStats(job);
    }

    public JobStats createStats(Job job) {
        if(stats.containsKey(job.getName())) {
            return stats.get(job.getName());
        }
        JobStats jobStats = new JobStats(this, job);
        stats.put(job.getName(), jobStats);
        return jobStats;
    }

    public void levelUp(String jobName, int level) {
        Player p = PlayerUtil.getPlayer(player);
        if(p == null) {
            return;
        }
        p.sendMessage("You leveled up in job " + jobName + "! Your new level is " + level);
    }

}
