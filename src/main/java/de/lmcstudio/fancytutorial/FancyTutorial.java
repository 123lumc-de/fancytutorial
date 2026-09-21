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

    // Deine Hex-Farben
    private final String HEX_GREEN = "#A9FF00";
    private final String HEX_MINT = "#25FF95";
    private final String HEX_PURPLE = "#9400FF";

    @Override
    public void onEnable() {
        saveDefaultConfig();
        LANG = getConfig().getString("language", "de").toLowerCase();
        
        int pluginId = 34189;
        Metrics metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new SimplePie("server_type", () -> "SMP"));

        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("FancyTutorial von lmcstudio wurde erfolgreich aktiviert! (Sprache: " + LANG + ")");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("tutorial")) {
            if (sender instanceof Player) {
                openTutorialGUI((Player) sender);
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

        int slotStart = getConfig().getInt("slots.start", 10);
        int slotShards = getConfig().getInt("slots.shards", 11);
        int slotMoney = getConfig().getInt("slots.money", 12);
        int slotEggs = getConfig().getInt("slots.eggs", 13);
        int slotCommands = getConfig().getInt("slots.commands", 14);
        int slotRanks = getConfig().getInt("slots.ranks", 15);
        int slotTags = getConfig().getInt("slots.tags", 19);
        int slotSpawner = getConfig().getInt("slots.spawner", 20);

        // --- ITEMS ERSTELLEN ---
        
        // 1. Start
        inv.setItem(slotStart, createItem(Material.OAK_SAPLING, 
                getMsg("start.title", HEX_GREEN + "&lWIE KANNST DU GUT STARTEN?"),
                getMsgList("start.lore", 
                        "&7Beschreibung",
                        "",
                        HEX_GREEN + "➜ Fange mit /rtp an und baue",
                        HEX_GREEN + "jeden OAK_LOG Block ab und verkaufe ihn",
                        HEX_GREEN + "mit /sell!",
                        "",
                        HEX_GREEN + "&lInformation:",
                        "&fDein Ziel auf dem Server ist es,",
                        "&fam meisten Geld zu haben.",
                        "",
                        HEX_GREEN + "&lTipp:",
                        "&fMache /worth <item> um zu gucken,",
                        "&fwie viel die verschiedenen Items kosten."),
                HEX_GREEN + "&l➜ KLICKE zum Öffnen", true));

        // 2. Shards
        inv.setItem(slotShards, createItem(Material.AMETHYST_SHARD, 
                getMsg("shards.title", HEX_PURPLE + "&lSHARDS"),
                getMsgList("shards.lore", 
                        "&7Beschreibung",
                        "",
                        HEX_PURPLE + "➜ So kannst du gut Shards sammeln:",
                        "",
                        HEX_PURPLE + "&lMethoden:",
                        "&f- Du kannst Shards durch's AFK-stehen in /afk bekommen",
                        "&f- Events und Giveaways im Discord (/discord)",
                        "&f- Crates, in einigen Crates sind Shards!",
                        "&f- Durch Hills kannst du Shards bekommen",
                        "",
                        HEX_PURPLE + "&lInformation:",
                        "&fNutze Shards, um",
                        "&fsie im /shardshop auszugeben"),
                HEX_PURPLE + "&l➜ KLICKE zum Öffnen", true));

        // 3. Geld
        inv.setItem(slotMoney, createItem(Material.EMERALD, 
                getMsg("money.title", HEX_MINT + "&lGELD BEKOMMEN"),
                getMsgList("money.lore", 
                        "&7Beschreibung",
                        "",
                        HEX_MINT + "➜ Verdiene Geld indem du Items",
                        HEX_MINT + "mit /sell verkaufst.",
                        "",
                        HEX_MINT + "&lBeispiel Methoden:",
                        "&f- Zuckerrohr verkaufen (Farmen)",
                        "&f- Bambus Farmen",
                        "&f- Sea Pickle Farmen",
                        "&f- Spawner AFK'n",
                        "&f- an Events/Giveaways teilnehmen (/discord)"),
                HEX_MINT + "&l➜ KLICKE zum Öffnen", true));

        // 4. Eggs
        inv.setItem(slotEggs, createItem(Material.SNIFFER_EGG, 
                getMsg("eggs.title", HEX_MINT + "&lEGGS"),
                getMsgList("eggs.lore", 
                        "&7Beschreibung",
                        "",
                        HEX_MINT + "➜ Du kannst Eggs benutzen, damit sie",
                        HEX_MINT + "dir Geld generieren. Mit Upgrades, machen sie noch mehr",
                        "",
                        HEX_MINT + "&lInformation:",
                        "&fDu kannst Eggs durch Crates",
                        "&fOder im Shards-Shop bekommen (/shardshop)"),
                HEX_MINT + "&l➜ KLICKE zum Öffnen", true));

        // 5. Commands
        inv.setItem(slotCommands, createItem(Material.GOLD_BLOCK, 
                getMsg("commands.title", HEX_GREEN + "&lWICHTIGE COMMANDS"),
                getMsgList("commands.lore", 
                        "&7Beschreibung",
                        "",
                        "&fFighte gegen Spieler mit /rtpqueue oder /duel",
                        "",
                        HEX_GREEN + "- /shop",
                        HEX_GREEN + "- /ah",
                        HEX_GREEN + "- /home",
                        HEX_GREEN + "- /spawn",
                        HEX_GREEN + "- /rtp"),
                HEX_GREEN + "&l➜ KLICKE zum Öffnen", true));

        // 6. Ranks
        inv.setItem(slotRanks, createItem(Material.TOTEM_OF_UNDYING, 
                getMsg("ranks.title", HEX_PURPLE + "&lRANKS"),
                getMsgList("ranks.lore", 
                        "&7Beschreibung",
                        "",
                        HEX_PURPLE + "&lInformation:",
                        "&fSiehe alle Ränge und wie",
                        "&fdu sie bekommst."),
                HEX_PURPLE + "&l➜ KLICKE zum Öffnen", true));

        // 7. Tags
        inv.setItem(slotTags, createItem(Material.NAME_TAG, 
                getMsg("tags.title", HEX_PURPLE + "&lTAGS"),
                getMsgList("tags.lore", 
                        "&7Beschreibung",
                        "",
                        HEX_PURPLE + "&lInformation:",
                        "&fSiehe dir alle deine Tags an"),
                HEX_PURPLE + "&l➜ KLICKE zum Öffnen", true));

        // 8. Spawner
        inv.setItem(slotSpawner, createItem(Material.VAULT, 
                getMsg("spawner.title", HEX_GREEN + "&lSPAWNER"),
                getMsgList("spawner.lore", 
                        "&7Beschreibung",
                        "",
                        HEX_GREEN + "➜ Du kannst Spawner platzieren",
                        HEX_GREEN + "so generieren sie dir automatisch Geld und Items!",
                        HEX_GREEN + "Um alles einzusammeln: Den Spawner Rechts Klicken.",
                        "",
                        HEX_GREEN + "&lInformation:",
                        "&fDu kannst Spawner durch Crates",
                        "&fOder im Shards-Shop bekommen (/shardshop)"),
                HEX_GREEN + "&l➜ KLICKE zum Öffnen", true));

        player.openInventory(inv);
    }

    // --- HILFSMETHODEN ---

    private String getMsg(String path, String defaultText) {
        // Wenn die Config den Pfad hat, nutze sie, sonst den Standardtext
        return getConfig().getString("messages." + path, defaultText);
    }

    private List<String> getMsgList(String path, String... defaultLines) {
        List<String> configList = getConfig().getStringList("messages." + path);
        if (configList != null && !configList.isEmpty()) {
            return configList;
        }
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
            // Fügt den "Klicke zum Öffnen" Text hinzu
            if (clickText != null && !clickText.isEmpty()) {
                coloredLore.add("");
                coloredLore.add(translateHex(clickText));
            }
            meta.setLore(coloredLore);
            
            // Versteckt die standardmäßigen Minecraft-Beschreibungen
            if (hideFlags) {
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

    // Übersetzt Hex-Codes und &-Codes
    private String translateHex(String message) {
        if (message == null) return "";
        
        // 1. Ersetzt & durch § für Standard-Farben
        message = ChatColor.translateAlternateColorCodes('&', message);
        
        // 2. Sucht nach #RRGGBB und wandelt es in §x§R§R§G§G§B§B um
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
            event.setCancelled(true);

            if (event.getWhoClicked() instanceof Player) {
                Player player = (Player) event.getWhoClicked();
                ItemStack clickedItem = event.getCurrentItem();

                if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

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
            }
        }
    }
}
