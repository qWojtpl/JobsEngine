package pl.jobsengine.util;

import org.bukkit.entity.Player;
import pl.jobsengine.JobsEngine;

import javax.annotation.Nullable;

public class PlayerUtil {

    @Nullable
    public static Player getPlayer(String nickname) {
        for(Player p : JobsEngine.getInstance().getServer().getOnlinePlayers()) {
            if(p.getName().equals(nickname)) {
                return p;
            }
        }
        return null;
    }

}
