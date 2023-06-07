package pl.jobsengine.jobs;

import lombok.Getter;
import lombok.Setter;
import pl.jobsengine.JobsEngine;

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
        double expCount = count * JobsEngine.getInstance().getDataHandler().getExpMultipler();
        exp += expCount;
        checkLevel(expCount);
    }

    public void addLevel(double level) {
        this.level += level;
        profile.onLevelUp(job, this.level);
        checkLevel(0);
    }

    public void checkLevel(double count) {
        profile.onUpdateExp(job, count);
        if(exp >= job.getRequiredExp() * level) {
            exp -= job.getRequiredExp() * level;
            addLevel(1);
        }
    }

}
