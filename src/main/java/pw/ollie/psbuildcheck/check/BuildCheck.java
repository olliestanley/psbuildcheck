package pw.ollie.psbuildcheck.check;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public final class BuildCheck {
    private final String world;
    private final int x;
    private final int y;
    private final int z;
    private final String plotName;
    private final String submitter;

    public BuildCheck(String world, int x, int y, int z, String plotName, String submitter) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.plotName = plotName;
        this.submitter = submitter;
    }

    public Location getLocation() {
        return new Location(Bukkit.getWorld(world), x, y, z);
    }

    public String getWorldName() {
        return world;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public String getPlotName() {
        return plotName;
    }

    public String getSubmitter() {
        return submitter;
    }
}
