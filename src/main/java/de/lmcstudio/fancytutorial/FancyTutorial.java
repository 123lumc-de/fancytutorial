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

    // --- FARBEN (Hex) ---
    private final String C_START    = "#A9FF00"; // Hellgrün
    private final String C_SHARDS   = "#9400FF"; // Lila/Pink (wie gewünscht)
    private final String C_MONEY    = "#25FF95"; // Mint
    private final String C_EGGS     = "#FFD700"; // Gold/Gelb
    private final String C_COMMANDS = "#00BFFF"; // Himmelblau
    private final String C_RANKS    = "#FF4500"; // Orangerot
    private final String C_TAGS     = "#FF69B4"; // Pink
    private final String C_SPAWNER  = "#00FFFF"; // Cyan/Türkis

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
        
        // 1. Start (Hellgrün)
        inv.setItem(slotStart, createItem(Material.OAK_SAPLING, 
                getMsg("start.title", C_START + "&lWIE KANNST DU GUT STARTEN?"),
                getMsgList("start.lore", 
                        "&7Beschreibung",
                        "",
                        C_START + "➜ Fange mit /rtp an und baue",
                        C_START + "jeden OAK_LOG Block ab und verkaufe ihn",
                        C_START + "mit /sell!",
                        "",
                        C_START + "&lInformation:",
                        "&fDein Ziel auf dem Server ist es,",
                        "&fam meisten Geld zu haben.",
                        "",
                        C_START + "&lTipp:",
                        "&fMache /worth <item> um zu gucken,",
                        "&fwie viel die verschiedenen Items kosten."),
                C_START + "&l➜ KLICKE zum Öffnen", true));

        // 2. Shards (Lila/Pink)
        inv.setItem(slotShards, createItem(Material.AMETHYST_SHARD, 
                getMsg("shards.title", C_SHARDS + "&lSHARDS"),
                getMsgList("shards.lore", 
                        "&7Beschreibung",
                        "",
                        C_SHARDS + "➜ So kannst du gut Shards sammeln:",
                        "",
                        C_SHARDS + "&lMethoden:",
                        "&f- Du kannst Shards durch's AFK-stehen in /afk bekommen",
                        "&f- Events und Giveaways im Discord (/discord)",
                        "&f- Crates, in einigen Crates sind Shards!",
                        "&f- Durch Hills kannst du Shards bekommen",
                        "",
                        C_SHARDS + "&lInformation:",
                        "&fNutze Shards, um",
                        "&fsie im /shardshop auszugeben"),
                C_SHARDS + "&l➜ KLICKE zum Öffnen", true));

        // 3. Geld (Mint)
        inv.setItem(slotMoney, createItem(Material.EMERALD, 
                getMsg("money.title", C_MONEY + "&lGELD BEKOMMEN"),
                getMsgList("money.lore", 
                        "&7Beschreibung",
                        "",
                        C_MONEY + "➜ Verdiene Geld indem du Items",
                        C_MONEY + "mit /sell verkaufst.",
                        "",
                        C_MONEY + "&lBeispiel Methoden:",
                        "&f- Zuckerrohr verkaufen (Farmen)",
                        "&f- Bambus Farmen",
                        "&f- Sea Pickle Farmen",
                        "&f- Spawner AFK'n",
                        "&f- an Events/Giveaways teilnehmen (/discord)"),
                C_MONEY + "&l➜ KLICKE zum Öffnen", true));

        // 4. Eggs (Gold/Gelb)
        inv.setItem(slotEggs, createItem(Material.SNIFFER_EGG, 
                getMsg("eggs.title", C_EGGS + "&lEGGS"),
                getMsgList("eggs.lore", 
                        "&7Beschreibung",
                        "",
                        C_EGGS + "➜ Du kannst Eggs benutzen, damit sie",
                        C_EGGS + "dir Geld generieren. Mit Upgrades, machen sie noch mehr",
                        "",
                        C_EGGS + "&lInformation:",
                        "&fDu kannst Eggs durch Crates",
                        "&fOder im Shards-Shop bekommen (/shardshop)"),
                C_EGGS + "&l➜ KLICKE zum Öffnen", true));

        // 5. Commands (Himmelblau)
        inv.setItem(slotCommands, createItem(Material.GOLD_BLOCK, 
                getMsg("commands.title", C_COMMANDS + "&lWICHTIGE COMMANDS"),
                getMsgList("commands.lore", 
                        "&7Beschreibung",
                        "",
                        "&fFighte gegen Spieler mit /rtpqueue oder /duel",
                        "",
                        C_COMMANDS + "- /shop",
                        C_COMMANDS + "- /ah",
                        C_COMMANDS + "- /home",
                        C_COMMANDS + "- /spawn",
                        C_COMMANDS + "- /rtp"),
                C_COMMANDS + "&l➜ KLICKE zum Öffnen", true));

        // 6. Ranks (Orangerot)
        inv.setItem(slotRanks, createItem(Material.TOTEM_OF_UNDYING, 
                getMsg("ranks.title", C_RANKS + "&lRANKS"),
                getMsgList("ranks.lore", 
                        "&7Beschreibung",
                        "",
                        C_RANKS + "&lInformation:",
                        "&fSiehe alle Ränge und wie",
                        "&fdu sie bekommst."),
                C_RANKS + "&l➜ KLICKE zum Öffnen", true));

        // 7. Tags (Pink)
        inv.setItem(slotTags, createItem(Material.NAME_TAG, 
                getMsg("tags.title", C_TAGS + "&lTAGS"),
                getMsgList("tags.lore", 
                        "&7Beschreibung",
                        "",
                        C_TAGS + "&lInformation:",
                        "&fSiehe dir alle deine Tags an"),
                C_TAGS + "&l➜ KLICKE zum Öffnen", true));

        // 8. Spawner (Cyan/Türkis)
        inv.setItem(slotSpawner, createItem(Material.VAULT, 
                getMsg("spawner.title", C_SPAWNER + "&lSPAWNER"),
                getMsgList("spawner.lore", 
                        "&7Beschreibung",
                        "",
                        C_SPAWNER + "➜ Du kannst Spawner platzieren",
                        C_SPAWNER + "so generieren sie dir automatisch Geld und Items!",
                        C_SPAWNER + "Um alles einzusammeln: Den Spawner Rechts Klicken.",
                        "",
                        C_SPAWNER + "&lInformation:",
                        "&fDu kannst Spawner durch Crates",
                        "&fOder im Shards-Shop bekommen (/shardshop)"),
                C_SPAWNER + "&l➜ KLICKE zum Öffnen", true));

        player.openInventory(inv);
    }

    // --- HILFSMETHODEN ---

    private String getMsg(String path, String defaultText) {
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
            if (clickText != null && !clickText.isEmpty()) {
                coloredLore.add("");
                coloredLore.add(translateHex(clickText));
            }
            meta.setLore(coloredLore);
            
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

    private String translateHex(String message) {
        if (message == null) return "";
        message = ChatColor.translateAlternateColorCodes('&', message);
        
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
