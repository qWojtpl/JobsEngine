package pl.jobsengine.jobs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobStats {

    private final PlayerProfile profile;
    private final Job job;
    private int exp = 0;
    private int level = 1;

    public JobStats(PlayerProfile profile, Job job) {
        this.profile = profile;
        this.job = job;
    }

    public void addExp(double count) {
        this.exp += count;
        if(this.exp >= job.getRequiredExp() * level) {
            this.exp -= job.getRequiredExp() * level;
            addLevel(1);
        }
        profile.levelUp(job.getName(), level);
    }

    public void addLevel(double level) {
        this.level += level;
    }

}
