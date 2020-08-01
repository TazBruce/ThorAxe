package io.github.tazbruce.thoraxe;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;

public class ThorAxeEvent implements Listener {
    HashMap<String, Integer> stormCount = new HashMap<>();
    HashMap<String, Long> playerTime = new HashMap<>();

    /*
        Checks for player use event and if they are holding Thor Axe
     */
    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String localizedName = player.getInventory().getItemInMainHand().getItemMeta().getLocalizedName();
        
        if (!playerTime.containsKey(player.getName())) {
            playerTime.put(player.getName(), System.currentTimeMillis() / 1000);
        }

        if (((System.currentTimeMillis() / 1000) - playerTime.get(player.getName())) > 0.5) {
            playerTime.replace(player.getName(), System.currentTimeMillis() / 1000);
            if (localizedName.equals("thor_axe")) {
                Integer numStorms;

                // Checks if player is trying to charge axe
                if (player.getWorld().hasStorm() && player.isSneaking()) {
                    if (stormCount.containsKey(player.getName())) {
                        numStorms = stormCount.get(player.getName());
                        if (numStorms == 0) {
                            player.getInventory().getItemInMainHand().addEnchantment(Enchantment.DURABILITY, 1);
                        }
                        numStorms++;
                        stormCount.replace(player.getName(), numStorms);
                        player.getWorld().strikeLightningEffect(player.getLocation());
                    } else {
                        stormCount.put(player.getName(), 1);
                        player.getInventory().getItemInMainHand().addEnchantment(Enchantment.DURABILITY, 1);
                    }

                    // Else player must be trying to use axe
                } else if (!player.isSneaking() && stormCount.containsKey(player.getName())) {
                    numStorms = stormCount.get(player.getName());

                    if (numStorms > 0) {
                        Block targetBlock = player.getTargetBlock(null, 100);
                        Location blockLoc = targetBlock.getLocation();

                        player.getWorld().strikeLightning(blockLoc);
                        numStorms--;
                        stormCount.replace(player.getName(), numStorms);

                        if (numStorms == 0) {
                            player.getInventory().getItemInMainHand().removeEnchantment(Enchantment.DURABILITY);
                        }
                    }
                }
            }
        }
    }
}
