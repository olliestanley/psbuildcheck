package pw.ollie.psbuildcheck.util;

import com.github.intellectualsites.plotsquared.plot.object.Location;

public final class PlotSquaredUtil {
    public static Location toPSLocation(org.bukkit.Location bukLoc) {
        return new Location(bukLoc.getWorld().getName(), bukLoc.getBlockX(), bukLoc.getBlockY(), bukLoc.getBlockZ(), bukLoc.getYaw(), bukLoc.getPitch());
    }

    private PlotSquaredUtil() {
        throw new UnsupportedOperationException();
    }
}
