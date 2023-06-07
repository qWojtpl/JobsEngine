package pl.jobsengine.jobs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobStats {

    private final PlayerProfile profile;
    private final Job job;
    private double exp = 0;
    private int level = 1;

    public JobStats(PlayerProfile profile, Job job) {
        this.profile = profile;
        this.job = job;
    }

    public void addExp(double count) {
        exp += count;
        if(exp >= job.getRequiredExp() * level) {
            exp -= job.getRequiredExp() * level;
            addLevel(1);
        } else {
            profile.onUpdateExp(job, count);
        }
    }

    public void addLevel(double level) {
        this.level += level;
        profile.onLevelUp(job, this.level);
    }

}
