package pl.jobsengine.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.jobsengine.commands.Subcommand;
import pl.jobsengine.gui.list.ProfileGUI;

public class ProfileCommand implements Subcommand {

    @Override
    public void begin(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            new ProfileGUI((Player) sender, "Profile", 54);
        }
    }

}
