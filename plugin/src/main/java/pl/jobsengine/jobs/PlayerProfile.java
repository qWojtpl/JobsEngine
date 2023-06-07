package pl.jobsengine.jobs;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
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

    public void onLevelUp(Job job, int level) {
        Player p = getCastedPlayer();
        if(p == null) {
            return;
        }
        p.sendMessage("You leveled up in job " + job.getName() + "! Your new level is " + level);
    }

    public void onUpdateExp(Job job, double count) {
        Player p = getCastedPlayer();
        if(p == null) {
            return;
        }
        double times = getJobStats(job).getExp()/(job.getRequiredExp() * getJobStats(job).getLevel()) * 20;
        String green = "§a";
        String red = "§4";
        for(int i = 0; i < times; i++) {
            green += "|";
        }
        for(double i = times; i <= 20; i++) {
            red += "|";
        }
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                "§e" + job.getName() + " §7" + getJobStats(job).getLevel() + "    " + green + red + "   §2+ " + count + " xp"));
    }

    public Player getCastedPlayer() {
        return PlayerUtil.getPlayer(player);
    }

}
