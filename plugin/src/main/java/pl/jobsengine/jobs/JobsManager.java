package pl.jobsengine.jobs;

import lombok.Getter;
import org.bukkit.entity.Player;
import pl.jobsengine.JobsEngine;

import javax.annotation.Nullable;
import java.util.HashMap;

@Getter
public class JobsManager {

    private final JobsEngine plugin = JobsEngine.getInstance();
    private final HashMap<String, Job> jobs = new HashMap<>();
    private final HashMap<Player, Job> playerJobs = new HashMap<>();

    public void createJob(Job job) {
        jobs.put(job.getName(), job);
    }

    @Nullable
    public Job getByName(String name) {
        for(String key : jobs.keySet()) {
            if(jobs.get(key).getName().equals(name)) {
                return jobs.get(key);
            }
        }
        return null;
    }

}
