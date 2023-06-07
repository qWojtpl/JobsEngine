package pl.jobsengine;

import lombok.Getter;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import pl.jobsengine.commands.CommandHelper;
import pl.jobsengine.commands.Commands;
import pl.jobsengine.data.DataHandler;
import pl.jobsengine.events.Events;
import pl.jobsengine.jobs.JobsManager;

@Getter
public final class JobsEngine extends JavaPlugin {

    private static JobsEngine main;
    private DataHandler dataHandler;
    private JobsManager jobsManager;
    private Commands commands;
    private CommandHelper commandHelper;
    private Events events;

    @Override
    public void onEnable() {
        main = this;
        this.dataHandler = new DataHandler();
        this.jobsManager = new JobsManager();
        this.commands = new Commands();
        this.commandHelper = new CommandHelper();
        this.events = new Events();
        PluginCommand cmd = getCommand("jobs");
        if(cmd != null) {
            cmd.setExecutor(commands);
            cmd.setTabCompleter(commandHelper);
        }
        getServer().getPluginManager().registerEvents(events, this);
        dataHandler.loadAll();
        getLogger().info("Enabled JobsEngine!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled JobsEngine!");
    }

    public static JobsEngine getInstance() {
        return main;
    }

}
