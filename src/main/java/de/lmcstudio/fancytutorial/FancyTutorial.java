package de.lmcstudio.fancytutorial;

import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class FancyTutorial extends JavaPlugin implements Listener {

    // Fette Schrift für den GUI-Titel
    private final String GUI_TITLE = ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Server Info | Menü";

    @Override
    public void onEnable() {
        // bStats initialisieren (Plugin ID: 34189)
        int pluginId = 34189;
        Metrics metrics = new Metrics(this, pluginId);
        
        // Optional: Ein Beispiel für einen Custom Chart
        metrics.addCustomChart(new SimplePie("server_type", () -> "SMP"));

        // Event-Listener registrieren
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("FancyTutorial von lmcstudio wurde erfolgreich aktiviert!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("tutorial")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                openTutorialGUI(player);
            } else {
                sender.sendMessage("Dieser Befehl kann nur von Spielern ausgeführt werden.");
            }
            return true;
        }
        return false;
    }

    private void openTutorialGUI(Player player) {
        // 6 Reihen = 54 Slots
        Inventory inv = Bukkit.createInventory(null, 54, GUI_TITLE);

        // --- ITEMS ERSTELLEN (Mit fetten Überschriften) ---

        // 1. Wie kann ich gut starten? (Setzling)
        inv.setItem(10, createItem(Material.OAK_SAPLING, ChatColor.GREEN + "" + ChatColor.BOLD + "WIE KANNST DU GUT STARTEN?", 
                ChatColor.GRAY + "Beschreibung",
                "",
                ChatColor.GREEN + "➜ Fange mit /rtp an und baue",
                ChatColor.GREEN + "jeden OAK_LOG Block ab und verkaufe ihn",
                ChatColor.GREEN + "mit /sell!",
                "",
                ChatColor.GREEN + "" + ChatColor.BOLD + "Information:",
                ChatColor.WHITE + "Dein Ziel auf dem Server ist es,",
                ChatColor.WHITE + "am meisten Geld zu haben.",
                "",
                ChatColor.GREEN + "" + ChatColor.BOLD + "Tipp:",
                ChatColor.WHITE + "Mache /worth <item> um zu gucken,",
                ChatColor.WHITE + "wie viel die verschiedenen Items kosten."));

        // 2. Shards (Amethyst Shard, Lila Text, Fett)
        inv.setItem(11, createItem(Material.AMETHYST_SHARD, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "SHARDS", 
                ChatColor.GRAY + "Beschreibung",
                "",
                ChatColor.LIGHT_PURPLE + "➜ So kannst du gut Shards sammeln:",
                "",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Methoden:",
                ChatColor.WHITE + "- Du kannst Shards durch's AFK-stehen in /afk bekommen",
                ChatColor.WHITE + "- Events und Giveaways im Discord (/discord)",
                ChatColor.WHITE + "- Crates, in einigen Crates sind Shards!",
                ChatColor.WHITE + "- Durch Hills kannst du Shards bekommen",
                "",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Information:",
                ChatColor.WHITE + "Nutze Shards, um",
                ChatColor.WHITE + "sie im /shardshop auszugeben"));

        // 3. Geld bekommen (Smaragd)
        inv.setItem(12, createItem(Material.EMERALD, ChatColor.GREEN + "" + ChatColor.BOLD + "GELD BEKOMMEN", 
                ChatColor.GRAY + "Beschreibung",
                "",
                ChatColor.GREEN + "➜ Verdiene Geld indem du Items",
                ChatColor.GREEN + "mit /sell verkaufst.",
                "",
                ChatColor.GREEN + "" + ChatColor.BOLD + "Beispiel Methoden:",
                ChatColor.WHITE + "- Zuckerrohr verkaufen (Farmen)",
                ChatColor.WHITE + "- Bambus Farmen",
                ChatColor.WHITE + "- Sea Pickle Farmen",
                ChatColor.WHITE + "- Spawner AFK'n",
                ChatColor.WHITE + "- an Events/Giveaways teilnehmen (/discord)"));

        // 4. Eggs (Dragon Egg)
        inv.setItem(13, createItem(Material.DRAGON_EGG, ChatColor.GOLD + "" + ChatColor.BOLD + "EGGS", 
                ChatColor.GRAY + "Beschreibung",
                "",
                ChatColor.GOLD + "➜ Du kannst Eggs benutzen, damit sie",
                ChatColor.GOLD + "dir Geld generieren. Mit Upgrades, machen sie noch mehr",
                "",
                ChatColor.GOLD + "" + ChatColor.BOLD + "Information:",
                ChatColor.WHITE + "Du kannst Eggs in durch Crates",
                ChatColor.WHITE + "Oder im Shards-Shop bekommen (/shardshop)"));

        // 5. Wichtige Commands (Eisenbarren)
        inv.setItem(14, createItem(Material.IRON_INGOT, ChatColor.YELLOW + "" + ChatColor.BOLD + "WICHTIGE COMMANDS", 
                ChatColor.GRAY + "Beschreibung",
                "",
                ChatColor.WHITE + "Fighte gegen Spieler mit /rtpqueue oder /duel",
                "",
                ChatColor.YELLOW + "- /shop",
                ChatColor.YELLOW + "- /ah",
                ChatColor.YELLOW + "- /home",
                ChatColor.YELLOW + "- /spawn",
                ChatColor.YELLOW + "- /rtp"));

        // 6. Ranks (Totem of Undying)
        inv.setItem(15, createItem(Material.TOTEM_OF_UNDYING, ChatColor.GOLD + "" + ChatColor.BOLD + "RANKS", 
                ChatColor.GRAY + "Beschreibung",
                "",
                ChatColor.AQUA + "" + ChatColor.BOLD + "Information:",
                ChatColor.WHITE + "Siehe alle Ränge und wie",
                ChatColor.WHITE + "du sie bekommst.",
                "",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "➜ KLICKE zum Öffnen"));

        // 7. Tags (Nametag)
        inv.setItem(19, createItem(Material.NAME_TAG, ChatColor.RED + "" + ChatColor.BOLD + "TAGS", 
                ChatColor.GRAY + "Beschreibung",
                "",
                ChatColor.RED + "" + ChatColor.BOLD + "Information:",
                ChatColor.WHITE + "Siehe dir alle deine Tags an",
                "",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "➜ KLICKE zum Öffnen"));

        // 8. Spawner (Spawner Block)
        inv.setItem(20, createItem(Material.SPAWNER, ChatColor.GOLD + "" + ChatColor.BOLD + "SPAWNER", 
                ChatColor.GRAY + "Beschreibung",
                "",
                ChatColor.GOLD + "➜ Du kannst Spawner platzieren",
                ChatColor.GOLD + "so generieren sie dir automatisch Geld und Items!",
                ChatColor.GOLD + "Um alles einzusammeln: Den Spawner Rechts Klicken.",
                "",
                ChatColor.GOLD + "" + ChatColor.BOLD + "Information:",
                ChatColor.WHITE + "Du kannst Spawner in durch Crates",
                ChatColor.WHITE + "Oder im Shards-Shop bekommen (/shardshop)"));

        player.openInventory(inv);
    }

    // Hilfsmethode zum Erstellen der Items mit Namen und Lore
    private ItemStack createItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            List<String> loreList = new ArrayList<>();
            for (String line : lore) {
                loreList.add(line);
            }
            meta.setLore(loreList);
            item.setItemMeta(meta);
        }
        return item;
    }

    // Event-Handler für Klicks im GUI
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // Prüfen, ob das richtige Inventory offen ist
        if (event.getView().getTitle().equals(GUI_TITLE)) {
            event.setCancelled(true); // Verhindern, dass Items aus dem GUI genommen werden

            if (event.getWhoClicked() instanceof Player) {
                Player player = (Player) event.getWhoClicked();
                ItemStack clickedItem = event.getCurrentItem();

                if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

                // Aktionen basierend auf dem Item
                if (clickedItem.getType() == Material.TOTEM_OF_UNDYING) {
                    player.closeInventory();
                    player.performCommand("ranks");
                } else if (clickedItem.getType() == Material.NAME_TAG) {
                    player.closeInventory();
                    player.performCommand("tags");
                } else if (clickedItem.getType() == Material.DRAGON_EGG) {
                    player.closeInventory();
                    player.performCommand("eggs");
                }
            }
        }
    }
}
