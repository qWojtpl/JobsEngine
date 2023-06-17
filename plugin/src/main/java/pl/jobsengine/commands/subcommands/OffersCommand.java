package pl.jobsengine.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.jobsengine.commands.Subcommand;
import pl.jobsengine.gui.list.OfferGUI;

public class OffersCommand implements Subcommand {

    @Override
    public void begin(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            new OfferGUI((Player) sender, "Job offers", 9);
        }
    }

}
