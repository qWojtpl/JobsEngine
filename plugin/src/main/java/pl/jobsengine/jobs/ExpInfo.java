package pl.jobsengine.jobs;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class ExpInfo {

    private final HashMap<String, Double> blockBreaks = new HashMap<>();
    private final HashMap<String, Double> mobKills = new HashMap<>();

    public void clearAll() {
        blockBreaks.clear();
        mobKills.clear();
    }

}
