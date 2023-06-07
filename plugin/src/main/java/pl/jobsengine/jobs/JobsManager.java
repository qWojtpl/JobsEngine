package pl.jobsengine.jobs;

import lombok.Getter;
import pl.jobsengine.JobsEngine;

import javax.annotation.Nullable;
import java.util.HashMap;

@Getter
public class JobsManager {

    private final JobsEngine plugin = JobsEngine.getInstance();
    private final HashMap<String, Job> jobs = new HashMap<>();
    private final HashMap<String, PlayerProfile> playerProfiles = new HashMap<>();

    public void createJob(Job job) {
        jobs.put(job.getName(), job);
    }

    public void assignJob(String nickname, Job job) {
        PlayerProfile profile = createPlayerProfile(nickname);
        profile.setCurrentJob(job);
        profile.createStats(job);
    }

    public PlayerProfile createPlayerProfile(String nickname) {
        if(playerProfiles.containsKey(nickname)) {
            return playerProfiles.get(nickname);
        }
        return new PlayerProfile(nickname);
    }

    public PlayerProfile getPlayerProfile(String nickname) {
        return createPlayerProfile(nickname);
    }

    @Nullable
    public Job getJobByName(String name) {
        for(String key : jobs.keySet()) {
            if(jobs.get(key).getName().equals(name)) {
                return jobs.get(key);
            }
        }
        return null;
    }

    @Nullable
    public Job getPlayersJob(String nickname) {
        return getPlayerProfile(nickname).getCurrentJob();
    }

}
