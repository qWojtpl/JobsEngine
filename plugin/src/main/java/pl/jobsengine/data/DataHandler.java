package pl.jobsengine.data;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.jobsengine.JobsEngine;
import pl.jobsengine.jobs.ExpInfo;
import pl.jobsengine.jobs.Job;
import pl.jobsengine.jobs.JobIcon;
import pl.jobsengine.jobs.JobsManager;

import java.io.File;

@Getter
@Setter
public class DataHandler {

    private final JobsEngine plugin = JobsEngine.getInstance();
    private final JobsManager jobsManager = plugin.getJobsManager();
    private double expMultipler;
    private int toolbarExpLength;

    public void loadAll() {
        loadConfig();
        loadJobs();
        loadData();
    }

    public void loadConfig() {
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(getConfigFile());
        expMultipler = yml.getDouble("config.expMultipler", 1);
        toolbarExpLength = yml.getInt("config.toolbarExpLength", 30);
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
            String[] sections = new String[]{"break", "kill"};
            for(String sectionName : sections) {
                ConfigurationSection expSection = yml.getConfigurationSection(path + "exp." + sectionName);
                if(expSection != null) {
                    for(String trigger : expSection.getKeys(false)) {
                        double exp = yml.getDouble(path + "exp." + sectionName + "." + trigger, 1);
                        if(sectionName.equals("break")) {
                            expInfo.getBlockBreaks().put(trigger, exp);
                        } else {
                            expInfo.getMobKills().put(trigger, exp);
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
    }

    public File getConfigFile() {
        return getFile("config.yml");
    }

    public File getJobsFile() {
        return getFile("jobs.yml");
    }

    public File getFile(String resourceName) {
        File file = new File(plugin.getDataFolder(), resourceName);
        if(!file.exists()) {
            plugin.saveResource(resourceName, false);
        }
        return file;
    }

}
