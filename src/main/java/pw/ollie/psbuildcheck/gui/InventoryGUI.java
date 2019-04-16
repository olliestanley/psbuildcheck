package pw.ollie.psbuildcheck.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class InventoryGUI implements Listener {
    protected Plugin plugin;
    protected OptionClickHandler handler;

    private String name;
    private int size;
    private String[] optionNames;
    private ItemStack[] optionIcons;
    private Player player;

    private boolean recentClick;

    public InventoryGUI(Plugin plugin, String name, int size, OptionClickHandler handler) {
        this.plugin = plugin;
        this.name = name;
        this.size = size;
        this.handler = handler;
        this.optionNames = new String[size];
        this.optionIcons = new ItemStack[size];

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public String getTitle() {
        return this.name;
    }

    public int getSize() {
        return this.size;
    }

    public InventoryGUI setSlot(int position, ItemStack icon, String name, String... lore) {
        boolean hasLore = lore != null && lore.length > 0;

        optionNames[position] = name;
        optionIcons[position] = hasLore ? setDisplayNameAndLore(icon, name, lore) : setDisplayName(icon, name);

        return this;
    }

    public InventoryGUI setSlot(int position, ItemStack icon, String name, List<String> lore) {
        optionNames[position] = name;
        optionIcons[position] = setDisplayNameAndLore(icon, name, lore);
        return this;
    }

    public ItemStack getStackInSlot(int position) {
        return this.optionIcons[position];
    }

    public InventoryGUI open(Player player) {
        this.player = player;

        Inventory inventory = Bukkit.createInventory(new GUIInventoryHolder(this, Bukkit.createInventory(player, size)), size, name);
        for (int i = 0; i < optionIcons.length; i++) {
            if (optionIcons[i] != null) {
                inventory.setItem(i, optionIcons[i]);
            }
        }
        player.openInventory(inventory);

        return this;
    }

    private void destroy() {
        HandlerList.unregisterAll(this);

        player = null;
        handler = null;
        plugin = null;
        optionNames = null;
        optionIcons = null;
        size = -1;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals(this.name)) {
            event.getWhoClicked().setItemOnCursor(null);
            event.setCursor(null);
            event.setCancelled(true);
        } else {
            return;
        }

        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        final Player clicker = (Player) event.getWhoClicked();
        if (!clicker.getName().equals(this.player.getName())) {
            return;
        }

        if (this.recentClick) {
            event.setCancelled(true);
            return;
        }

        this.recentClick = true;
        this.plugin.getServer().getScheduler().runTaskLater(plugin, () -> InventoryGUI.this.recentClick = false, 2L);

        event.setCancelled(true);
        if (event.getClick() != ClickType.LEFT && event.getClick() != ClickType.RIGHT) {
            event.setCancelled(true);
            return;
        }

        int slot = event.getRawSlot();
        if (slot >= 0 && slot < this.size && this.optionNames[slot] != null) {
            OptionClick e = new OptionClick(plugin, clicker, slot, this.optionNames[slot], this.optionIcons[slot], OptionClickType.fromClickType(event.getClick()));
            this.handler.onOptionClick(e);
            clicker.updateInventory();

            if (e.shouldClose()) {
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, clicker::closeInventory);
            }
            if (e.shouldDestroy()) {
                destroy();
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getInventory().getTitle().equals(this.name) && this.player == event.getPlayer())) {
            return;
        }

        this.destroy();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (event.getPlayer() == this.player) {
            this.destroy();
        }
    }

    public interface OptionClickHandler {
        void onOptionClick(OptionClick event);
    }

    public class OptionClick {
        private final Plugin plugin;
        private final InventoryGUI gui;

        private Player player;
        private int slot;
        private String name;
        private boolean close;
        private boolean destroy;
        private ItemStack item;
        private OptionClickType type;

        public OptionClick(Plugin plugin, Player player, int slot, String name, ItemStack item, OptionClickType type) {
            this.plugin = plugin;
            this.gui = InventoryGUI.this;
            this.player = player;
            this.slot = slot;
            this.name = name;
            this.item = item;
            this.type = type;
        }

        public Plugin getPlugin() {
            return plugin;
        }

        public InventoryGUI getGUI() {
            return gui;
        }

        public Player getPlayer() {
            return player;
        }

        public int getSlot() {
            return slot;
        }

        public String getName() {
            return name;
        }

        public boolean shouldClose() {
            return close;
        }

        public boolean shouldDestroy() {
            return destroy;
        }

        public void setClose(boolean close) {
            this.close = close;
        }

        public void setDestroy(boolean destroy) {
            this.destroy = destroy;
        }

        public ItemStack getItem() {
            return item;
        }

        public OptionClickType getClickType() {
            return type;
        }
    }

    public enum OptionClickType {
        LEFT, RIGHT;

        public static OptionClickType fromClickType(ClickType clickType) {
            if (clickType.isRightClick()) {
                return RIGHT;
            } else if (clickType.isLeftClick()) {
                return LEFT;
            } else {
                return null;
            }
        }
    }

    public class GUIInventoryHolder implements InventoryHolder {
        private final InventoryGUI gui;
        private final Inventory inventory;

        public GUIInventoryHolder(InventoryGUI gui, Inventory inventory) {
            this.gui = gui;
            this.inventory = inventory;
        }

        public InventoryGUI getGUI() {
            return gui;
        }

        @Override
        public Inventory getInventory() {
            return this.inventory;
        }
    }

    private ItemStack setDisplayName(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(item.getType());
            if (meta == null) {
                return item;
            }
        }
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack setDisplayNameAndLore(ItemStack item, String name, String... lore) {
        item = setDisplayName(item, name);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack setDisplayNameAndLore(ItemStack item, String name, List<String> lore) {
        item = setDisplayName(item, name);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
