package pl.jobsengine.commands.subcommands;

import org.bukkit.command.CommandSender;
import pl.jobsengine.JobsEngine;
import pl.jobsengine.commands.Subcommand;

public class reload implements Subcommand {

    @Override
    public void begin(CommandSender sender, String[] args) {
        JobsEngine.getInstance().getDataHandler().saveData();
        JobsEngine.getInstance().getDataHandler().loadAll();
        sender.sendMessage("§aReloaded!");
    }

}
