package pl.jobsengine.data;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.jobsengine.JobsEngine;
import pl.jobsengine.jobs.*;

import java.io.File;
import java.io.IOException;

@Getter
@Setter
public class DataHandler {

    private final JobsEngine plugin = JobsEngine.getInstance();
    private final JobsManager jobsManager = plugin.getJobsManager();
    private double expMultipler;
    private int toolbarExpLength;
    private String toolbarSign;
    private YamlConfiguration data;

    public void loadAll() {
        loadConfig();
        loadJobs();
        loadData();
    }

    public void loadConfig() {
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(getConfigFile());
        expMultipler = yml.getDouble("config.expMultipler", 1);
        toolbarExpLength = yml.getInt("config.toolbarExpLength", 30);
        toolbarSign = yml.getString("config.toolbarSign", "|");
    }

    public void loadJobs() {
        jobsManager.getJobs().clear();
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(getJobsFile());
        ConfigurationSection section = yml.getConfigurationSection("jobs");
        if(section == null) {
            return;
        }
        for(String name : section.getKeys(false)) {
            String path = "jobs." + name + ".";
            /* =========== [ LOADING JOB ICON ] =========== */
            String itemName = yml.getString(path + "icon.item", "BEDROCK").toUpperCase();
            Material m = Material.getMaterial(itemName);
            if(m == null) {
                m = Material.BEDROCK;
            }
            JobIcon icon = new JobIcon(
                    m,
                    yml.getString(path + "icon.name", "JobName"),
                    yml.getStringList(path + "icon.lore"));
            Job job = new Job(name, icon);
            /* =========== [ LOADING JOB INFO ] =========== */
            job.setMaxLevel(yml.getInt(path + "maxLevel", 100));
            job.setRequiredExp(yml.getDouble(path + "requiredExp", 100));
            job.setPaydayInterval(yml.getInt(path + "paydayInterval", 3600));
            /* =========== [ LOADING EXP INFO ] =========== */
            ExpInfo expInfo = new ExpInfo();
            String[] sections = new String[]{"break", "kill", "fish", "build", "eat"};
            for(String sectionName : sections) {
                ConfigurationSection expSection = yml.getConfigurationSection(path + "exp." + sectionName);
                if(expSection != null) {
                    for(String trigger : expSection.getKeys(false)) {
                        double exp = yml.getDouble(path + "exp." + sectionName + "." + trigger, 1);
                        if(sectionName.equals("break")) {
                            expInfo.getBlockBreaks().put(trigger, exp);
                        } else if(sectionName.equals("kill")) {
                            expInfo.getMobKills().put(trigger, exp);
                        } else if(sectionName.equals("fish")) {
                            expInfo.getFishItems().put(trigger, exp);
                        } else if(sectionName.equals("build")) {
                            expInfo.getBuildBlocks().put(trigger, exp);
                        } else if(sectionName.equals("eat")) {
                            expInfo.getEatItems().put(trigger, exp);
                        }
                    }
                }
            }
            job.setExpInfo(expInfo);
            jobsManager.createJob(job);
        }
    }

    public void loadData() {
        jobsManager.getPlayerProfiles().clear();
        data = YamlConfiguration.loadConfiguration(getDataFile());
        ConfigurationSection section = data.getConfigurationSection("data");
        if(section == null) {
            return;
        }
        for(String player : section.getKeys(false)) {
            String path = "data." + player + ".";
            PlayerProfile profile = jobsManager.getPlayerProfile(player);
            profile.setCurrentJob(jobsManager.getJobByName(data.getString(path + "current")));
        }
    }

    public void assignJob(String player, Job job) {
        data.set("data." + player + ".current", job.getName());
    }

    public void saveStats(String player, Job job) {
        String path = "data." + player + ".stats." + job.getName() + ".";
        JobStats jobStats = jobsManager.getPlayerProfile(player).getJobStats(job);
        data.set(path + "level", jobStats.getLevel());
        data.set(path + "exp", jobStats.getExp());
    }

    public void saveData() {
        try {
            data.save(getDataFile());
        } catch(IOException e) {
            plugin.getLogger().severe("Can't save data.yml, " + e.getMessage());
        }
    }

    public File getConfigFile() {
        return getFile("config.yml");
    }

    public File getJobsFile() {
        return getFile("jobs.yml");
    }

    public File getDataFile() {
        return getFile("data.yml");
    }

    public File getFile(String resourceName) {
        File file = new File(plugin.getDataFolder(), resourceName);
        if(!file.exists()) {
            plugin.saveResource(resourceName, false);
        }
        return file;
    }

}
