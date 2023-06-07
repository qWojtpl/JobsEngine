package pl.jobsengine.commands;

import org.bukkit.command.CommandSender;

public interface Subcommand {

    void begin(CommandSender sender, String[] args);

}
