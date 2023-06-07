package pl.jobsengine.commands.subcommands;

import org.bukkit.command.CommandSender;
import pl.jobsengine.commands.Subcommand;

public class help implements Subcommand {

    help() {

    }

    @Override
    public void begin(CommandSender sender, String[] args) {
        sender.sendMessage("help page");
    }

}
