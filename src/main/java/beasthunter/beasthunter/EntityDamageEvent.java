package beasthunter.beasthunter;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Boss;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EntityDamageEvent implements Listener {

    private Main main;
    private ClickEvent click;



    public EntityDamageEvent(Main main) {
        this.click = main.getClick();
        this.main = main;
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity entity = event.getEntity();


        if (entity instanceof Boss && damager instanceof Player) {
            Player player = (Player) damager;
            double damage = event.getFinalDamage();
            if (!(click.playerDamage.containsKey(player.getUniqueId()))) {
                click.playerDamage.put(player.getUniqueId(), 0.0);
            }
            double toplamHasar = click.playerDamage.get(player.getUniqueId()) + damage;
            click.playerDamage.put(player.getUniqueId(), toplamHasar);
            Quest quest = click.activeQuests.get(player.getUniqueId());
            if (quest != null && player.equals(quest.getOwner())) {
                event.setCancelled(true); // false yapman lazım
            } else {
                event.setCancelled(false);
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Bu boss'a sadece görevin sahibi vurabilir."));
            }
        }
    }
}
