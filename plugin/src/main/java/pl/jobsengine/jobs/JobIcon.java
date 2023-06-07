package pl.jobsengine.jobs;

import lombok.Getter;
import org.bukkit.Material;

import java.util.List;

@Getter
public class JobIcon {

    private final Material material;
    private final String name;
    private final List<String> lore;

    public JobIcon(Material material, String name, List<String> lore) {
        this.material = material;
        this.name = name;
        this.lore = lore;
    }

}
