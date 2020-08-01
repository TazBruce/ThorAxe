package io.github.tazbruce.thoraxe;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class ThorAxe extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.addRecipe(addThorAxe());
        getServer().getPluginManager().registerEvents(new ThorAxeEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public ShapedRecipe addThorAxe() {
        ItemStack item = new ItemStack(Material.IRON_AXE);
        ItemMeta meta = item.getItemMeta();

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.DARK_BLUE+""+ChatColor.ITALIC+"Strike your foes with the power of thunder");

        meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Thor's Axe");
        meta.setLocalizedName("thor_axe");
        meta.setUnbreakable(true);
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);

        NamespacedKey key = new NamespacedKey(this, "thor_axe");
        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape(" T "," A ","DGR");
        recipe.setIngredient('T',Material.GHAST_TEAR);
        recipe.setIngredient('A',Material.IRON_AXE);
        recipe.setIngredient('D',Material.GLOWSTONE_DUST);
        recipe.setIngredient('G',Material.GUNPOWDER);
        recipe.setIngredient('R',Material.REDSTONE);

        return recipe;
    }
}
