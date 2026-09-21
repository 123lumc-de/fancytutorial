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
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class FancyTutorial extends JavaPlugin implements Listener {

    private String GUI_TITLE;
    private String LANG;

    @Override
    public void onEnable() {
        // Config speichern & laden
        saveDefaultConfig();
        LANG = getConfig().getString("language", "de").toLowerCase();
        
        // bStats initialisieren (Plugin ID: 34189)
        int pluginId = 34189;
        Metrics metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new SimplePie("server_type", () -> "SMP"));

        // Event-Listener registrieren
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("FancyTutorial von lmcstudio wurde erfolgreich aktiviert! (Sprache: " + LANG + ")");
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
        GUI_TITLE = translateHex(getConfig().getString("gui-title", "&8&lServer Info | Menü"));
        Inventory inv = Bukkit.createInventory(null, 54, GUI_TITLE);

        // Slots aus Config laden
        int slotStart = getConfig().getInt("slots.start", 10);
        int slotShards = getConfig().getInt("slots.shards", 11);
        int slotMoney = getConfig().getInt("slots.money", 12);
        int slotEggs = getConfig().getInt("slots.eggs", 13);
        int slotCommands = getConfig().getInt("slots.commands", 14);
        int slotRanks = getConfig().getInt("slots.ranks", 15);
        int slotTags = getConfig().getInt("slots.tags", 19);
        int slotSpawner = getConfig().getInt("slots.spawner", 20);

        // --- ITEMS ERSTELLEN ---
        
        // 1. Start (Oak Log statt Sapling im Text? Nein, Sapling bleibt, Text wird angepasst)
        inv.setItem(slotStart, createItem(Material.OAK_SAPLING, 
                getMsg("start.title", "&a&lWIE KANNST DU GUT STARTEN?"),
                getMsgList("start.lore", 
                        "&7Beschreibung",
                        "",
                        "&a➜ Fange mit /rtp an und baue",
                        "&ajeden OAK_LOG Block ab und verkaufe ihn",
                        "&amit /sell!",
                        "",
                        "&a&lInformation:",
                        "&fDein Ziel auf dem Server ist es,",
                        "&fam meisten Geld zu haben.",
                        "",
                        "&a&lTipp:",
                        "&fMache /worth <item> um zu gucken,",
                        "&fwie viel die verschiedenen Items kosten."),
                getMsg("start.click", "&a&l➜ KLICKE zum Öffnen"), true));

        // 2. Shards (Amethyst, Lila/Pink)
        inv.setItem(slotShards, createItem(Material.AMETHYST_SHARD, 
                getMsg("shards.title", "&#9400FF&lSHARDS"), // Lila
                getMsgList("shards.lore", 
                        "&7Beschreibung",
                        "",
                        "&#9400FF➜ So kannst du gut Shards sammeln:",
                        "",
                        "&#9400FF&lMethoden:",
                        "&f- Du kannst Shards durch's AFK-stehen in /afk bekommen",
                        "&f- Events und Giveaways im Discord (/discord)",
                        "&f- Crates, in einigen Crates sind Shards!",
                        "&f- Durch Hills kannst du Shards bekommen",
                        "",
                        "&#9400FF&lInformation:",
                        "&fNutze Shards, um",
                        "&fsie im /shardshop auszugeben"),
                getMsg("shards.click", "&#9400FF&l➜ KLICKE zum Öffnen"), true));

        // 3. Geld (Emerald, Grün)
        inv.setItem(slotMoney, createItem(Material.EMERALD, 
                getMsg("money.title", "&#25FF95&lGELD BEKOMMEN"), // Grün
                getMsgList("money.lore", 
                        "&7Beschreibung",
                        "",
                        "&#25FF95➜ Verdiene Geld indem du Items",
                        "&#25FF95mit /sell verkaufst.",
                        "",
                        "&#25FF95&lBeispiel Methoden:",
                        "&f- Zuckerrohr verkaufen (Farmen)",
                        "&f- Bambus Farmen",
                        "&f- Sea Pickle Farmen",
                        "&f- Spawner AFK'n",
                        "&f- an Events/Giveaways teilnehmen (/discord)"),
                getMsg("money.click", "&#25FF95&l➜ KLICKE zum Öffnen"), false));

        // 4. Eggs (Sniffer Egg, Gold/Gelb)
        inv.setItem(slotEggs, createItem(Material.SNIFFER_EGG, 
                getMsg("eggs.title", "&6&lEGGS"),
                getMsgList("eggs.lore", 
                        "&7Beschreibung",
                        "",
                        "&6➜ Du kannst Eggs benutzen, damit sie",
                        "&6dir Geld generieren. Mit Upgrades, machen sie noch mehr",
                        "",
                        "&6&lInformation:",
                        "&fDu kannst Eggs durch Crates",
                        "&fOder im Shards-Shop bekommen (/shardshop)"),
                getMsg("eggs.click", "&6&l➜ KLICKE zum Öffnen"), true));

        // 5. Commands (Gold Block, Gelb/Gold)
        inv.setItem(slotCommands, createItem(Material.GOLD_BLOCK, 
                getMsg("commands.title", "&e&lWICHTIGE COMMANDS"),
                getMsgList("commands.lore", 
                        "&7Beschreibung",
                        "",
                        "&fFighte gegen Spieler mit /rtpqueue oder /duel",
                        "",
                        "&e- /shop",
                        "&e- /ah",
                        "&e- /home",
                        "&e- /spawn",
                        "&e- /rtp"),
                getMsg("commands.click", "&e&l➜ KLICKE zum Öffnen"), false));

        // 6. Ranks (Totem, Aqua)
        inv.setItem(slotRanks, createItem(Material.TOTEM_OF_UNDYING, 
                getMsg("ranks.title", "&b&lRANKS"),
                getMsgList("ranks.lore", 
                        "&7Beschreibung",
                        "",
                        "&b&lInformation:",
                        "&fSiehe alle Ränge und wie",
                        "&fdu sie bekommst.",
                        "",
                        "&b&l➜ KLICKE zum Öffnen"),
                getMsg("ranks.click", "&b&l➜ KLICKE zum Öffnen"), true));

        // 7. Tags (Name Tag, Rot)
        inv.setItem(slotTags, createItem(Material.NAME_TAG, 
                getMsg("tags.title", "&c&lTAGS"),
                getMsgList("tags.lore", 
                        "&7Beschreibung",
                        "",
                        "&c&lInformation:",
                        "&fSiehe dir alle deine Tags an",
                        "",
                        "&c&l➜ KLICKE zum Öffnen"),
                getMsg("tags.click", "&c&l➜ KLICKE zum Öffnen"), true));

        // 8. Spawner (Vault, Gold/Orange)
        inv.setItem(slotSpawner, createItem(Material.VAULT, 
                getMsg("spawner.title", "&6&lSPAWNER"),
                getMsgList("spawner.lore", 
                        "&7Beschreibung",
                        "",
                        "&6➜ Du kannst Spawner platzieren",
                        "&6so generieren sie dir automatisch Geld und Items!",
                        "&6Um alles einzusammeln: Den Spawner Rechts Klicken.",
                        "",
                        "&6&lInformation:",
                        "&fDu kannst Spawner durch Crates",
                        "&fOder im Shards-Shop bekommen (/shardshop)"),
                getMsg("spawner.click", "&6&l➜ KLICKE zum Öffnen"), true));

        player.openInventory(inv);
    }

    // --- HILFSMETHODEN ---

    private String getMsg(String path, String defaultText) {
        if (LANG.equals("en")) {
            // Englische Übersetzung (kann in config.yml überschrieben werden)
            switch (path) {
                case "start.title": return "&a&lHOW TO START WELL?";
                case "shards.title": return "&#9400FF&lSHARDS";
                case "money.title": return "&#25FF95&lGET MONEY";
                case "eggs.title": return "&6&lEGGS";
                case "commands.title": return "&e&lIMPORTANT COMMANDS";
                case "ranks.title": return "&b&lRANKS";
                case "tags.title": return "&c&lTAGS";
                case "spawner.title": return "&6&lSPAWNER";
                case "start.click": return "&a&l➜ CLICK to open";
                case "shards.click": return "&#9400FF&l➜ CLICK to open";
                case "money.click": return "&#25FF95&l➜ CLICK to open";
                case "eggs.click": return "&6&l➜ CLICK to open";
                case "commands.click": return "&e&l➜ CLICK to open";
                case "ranks.click": return "&b&l➜ CLICK to open";
                case "tags.click": return "&c&l➜ CLICK to open";
                case "spawner.click": return "&6&l➜ CLICK to open";
            }
        }
        return defaultText;
    }

    private List<String> getMsgList(String path, String... defaultLines) {
        List<String> lines = new ArrayList<>();
        for (String line : defaultLines) {
            lines.add(line);
        }
        return lines;
    }

    private ItemStack createItem(Material material, String name, List<String> lore, String clickText, boolean hideFlags) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(translateHex(name));
            List<String> coloredLore = new ArrayList<>();
            for (String line : lore) {
                coloredLore.add(translateHex(line));
            }
            // Fügt den "Klicke zum Öffnen" Text hinzu, wenn es einen gibt
            if (clickText != null && !clickText.isEmpty()) {
                coloredLore.add("");
                coloredLore.add(translateHex(clickText));
            }
            meta.setLore(coloredLore);
            
            // Versteckt die standardmäßigen Minecraft-Beschreibungen (wie "Minecraft" oder "Interact with Spawn Egg")
            if (hideFlags) {
                // WICHTIG: HIDE_POTION_EFFECTS heißt jetzt HIDE_ADDITIONAL_TOOLTIP
                meta.addItemFlags(
                    ItemFlag.HIDE_ATTRIBUTES, 
                    ItemFlag.HIDE_UNBREAKABLE, 
                    ItemFlag.HIDE_ENCHANTS, 
                    ItemFlag.HIDE_DYE, 
                    ItemFlag.HIDE_ADDITIONAL_TOOLTIP
                );
            }
            
            item.setItemMeta(meta);
        }
        return item;
    }

    // Übersetzt Hex-Codes (z.B. #A9FF00) in Minecraft-Farbcodes
    private String translateHex(String message) {
        if (message == null) return "";
        // Ersetzt & durch § für Standard-Farben
        message = ChatColor.translateAlternateColorCodes('&', message);
        
        // Sucht nach #RRGGBB und wandelt es in §x§R§R§G§G§B§B um
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("#[a-fA-F0-9]{6}");
        java.util.regex.Matcher matcher = pattern.matcher(message);
        StringBuffer buffer = new StringBuffer();
        
        while (matcher.find()) {
            String hex = matcher.group();
            StringBuilder replacement = new StringBuilder("§x");
            for (char c : hex.substring(1).toCharArray()) {
                replacement.append("§").append(c);
            }
            matcher.appendReplacement(buffer, replacement.toString());
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    // --- EVENT HANDLER ---

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
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
                } else if (clickedItem.getType() == Material.SNIFFER_EGG) {
                    player.closeInventory();
                    player.performCommand("eggs");
                }
                // Weitere Klick-Aktionen können hier hinzugefügt werden (z.B. für Commands, Shards, etc.)
            }
        }
    }
}
