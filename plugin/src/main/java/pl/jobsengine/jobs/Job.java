package pl.jobsengine.jobs;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Job {

    private final String name;
    private JobIcon icon;
    private int maxLevel = 100;
    private double requiredExp = 100;
    private int paydayInterval = 3600;
    private double levelExpMultipler = 1.05;
    private ExpInfo expInfo;
    private List<Payout> payoutList = new ArrayList<>();

    public Job(String name, JobIcon icon) {
        this.name = name;
        this.icon = icon;
    }

}
