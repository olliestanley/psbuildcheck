package pw.ollie.psbuildcheck.check;

import pw.ollie.psbuildcheck.PSBCPlugin;

import org.bson.BSONDecoder;
import org.bson.BSONEncoder;
import org.bson.BSONObject;
import org.bson.BasicBSONDecoder;
import org.bson.BasicBSONEncoder;
import org.bson.BasicBSONObject;
import org.bson.types.BasicBSONList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public final class CheckManager {
    private final PSBCPlugin plugin;
    private final List<BuildCheck> queuedChecks;

    public CheckManager(PSBCPlugin plugin) {
        this.plugin = plugin;
        this.queuedChecks = new ArrayList<>();
    }

    public PSBCPlugin getPlugin() {
        return plugin;
    }

    public List<BuildCheck> getQueuedChecks() {
        return new ArrayList<>(queuedChecks);
    }

    public void addCheck(BuildCheck check) {
        queuedChecks.add(check);
    }

    public void removeCheck(BuildCheck check) {
        queuedChecks.remove(check);
    }

    public void loadData() {
        File storageFolder = new File(plugin.getDataFolder(), "data");
        File checksFile = new File(storageFolder, "checks.bson");
        if (!checksFile.exists()) {
            return;
        }

        try {
            byte[] bytes = Files.readAllBytes(checksFile.toPath());
            BSONDecoder decoder = new BasicBSONDecoder();
            BSONObject bObj = decoder.readObject(bytes);

            if (!(bObj instanceof BasicBSONObject)) {
                plugin.getLogger().log(Level.SEVERE, "Invalid data stored... Cannot load checks data!");
                return;
            }

            BasicBSONObject bson = (BasicBSONObject) bObj;
            BasicBSONList list = (BasicBSONList) bson.get("checks");
            for (Object object : list) {
                BasicBSONObject bsonObj = (BasicBSONObject) object;
                queuedChecks.add(BuildCheck.fromBSONObject(bsonObj));
            }
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not load checks data...", e);
        }
    }

    public void saveData() {
        File storageFolder = new File(plugin.getDataFolder(), "data");
        File checksFile = new File(storageFolder, "checks.bson");
        File backupFile = new File(storageFolder, "checks.bson.bck");

        if (checksFile.exists()) {
            try {
                Files.copy(checksFile.toPath(), backupFile.toPath());
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Could not backup checks data, not saving...", e);
                return;
            }

            checksFile.delete();
        }

        if (!queuedChecks.isEmpty()) {
            BasicBSONObject storageObj = new BasicBSONObject();
            BasicBSONList listObj = new BasicBSONList();
            for (BuildCheck check : queuedChecks) {
                listObj.add(check.toBSONObject());
            }
            storageObj.put("checks", listObj);

            BSONEncoder encoder = new BasicBSONEncoder();
            byte[] data = encoder.encode(storageObj);

            try {
                checksFile.createNewFile();
                Files.write(checksFile.toPath(), data);
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Could not write checks data, attempting to restore backup...", e);

                try {
                    Files.copy(backupFile.toPath(), checksFile.toPath());
                } catch (IOException e1) {
                    plugin.getLogger().log(Level.SEVERE, "Could not restore backup, restore manually...", e1);
                    return;
                }
            }
        }
    }
}
