package pl.jobsengine.jobs;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import pl.jobsengine.JobsEngine;
import pl.jobsengine.util.PlayerUtil;

import javax.annotation.Nullable;
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
        if(count == 0) {
            return;
        }
        Player p = getCastedPlayer();
        if(p == null) {
            return;
        }
        int length = JobsEngine.getInstance().getDataHandler().getToolbarExpLength();
        double exp = getJobStats(job).getExp();
        if(exp < 0) {
            exp = 0;
        }
        double times = exp/(job.getRequiredExp() * getJobStats(job).getLevel()) * length;
        String green = "§a";
        String red = "§4";
        String sign = JobsEngine.getInstance().getDataHandler().getToolbarSign();
        for(int i = 1; i <= times; i++) {
            if(i > length) {
                break;
            }
            green += sign;
        }
        for(int i = (int) times + 1; i <= length; i++) {
            red += sign;
        }
        String expSign = "§c-";
        if(count > 0) {
            expSign = "§2+";
        }
        count = Math.abs(count);
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                "§e" + job.getName() + " §7" + getJobStats(job).getLevel() + "    " + green + red + "   " + expSign + " " + count + " xp"));
    }

    @Nullable
    public Player getCastedPlayer() {
        return PlayerUtil.getPlayer(player);
    }

}
