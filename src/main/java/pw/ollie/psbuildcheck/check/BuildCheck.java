package pw.ollie.psbuildcheck.check;

import org.bson.BasicBSONObject;

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

    public BasicBSONObject toBSONObject() {
        BasicBSONObject result = new BasicBSONObject();
        result.put("world", world);
        result.put("x", x);
        result.put("y", y);
        result.put("z", z);
        if (plotName != null) {
            result.put("plot", plotName);
        }
        result.put("submitter", submitter);
        return result;
    }

    public static BuildCheck fromBSONObject(BasicBSONObject bObj) {
        return new BuildCheck(bObj.getString("world"), bObj.getInt("x"), bObj.getInt("y"), bObj.getInt("z"),
                bObj.containsField("plot") ? bObj.getString("plot") : null, bObj.getString("submitter"));
    }
}
