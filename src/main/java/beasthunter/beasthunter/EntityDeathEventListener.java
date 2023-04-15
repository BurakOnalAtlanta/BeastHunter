package beasthunter.beasthunter;


import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.ThreadLocalRandom;

public class EntityDeathEventListener implements Listener {

    private Main main;

    private ClickEvent click;

    private Location publicLastLocation;



    public EntityDeathEventListener(Main main) {
        this.main = main;
        this.click = main.getClick();
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onDeathEvent(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        Player killer = event.getEntity().getKiller();
        //if (killer instanceof Player player) {
        if (killer != null && click.activeQuests.containsKey(killer.getUniqueId())) {
            Quest quest = click.activeQuests.get(killer.getUniqueId());
            if (entity.getName().contains("☬")) {
                quest.setCompleted(true);
                killer.showTitle(Title.title(MiniMessage.miniMessage().deserialize("<light_purple>Görevin Tamamlandı"), MiniMessage.miniMessage().deserialize("<gray>" + killer.getName())));
            }
            EntityType entityType = quest.getEntityType();
            //int remaining = quest.getKillCount() - 1 - quest.getCurrentKillCount();
            if (entity.getType() == entityType) {
                quest.incrementCurrentKillCount();
                if (quest.getCurrentKillCount() == quest.getKillCount() -1) {
                    killer.playSound(killer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1f, 1f);
                    killer.sendMessage(MiniMessage.miniMessage().deserialize("<green>Sırada Boss Var"));
                    if (killer.equals(click.activeQuests.get(killer.getUniqueId()).getOwner())) {
                        entity.getWorld().spawnParticle(Particle.FLAME, entity.getLocation(), 10, 0.2, 0.2, 0.2, 0.1);
                        BukkitTask task = new BukkitRunnable() {
                            @Override
                            public void run() {
                                spawnRandomBoss(entity.getLocation(), killer);
                            }
                        }.runTaskLater(main, 60);

                        //killer.showTitle(Title.title(MiniMessage.miniMessage().deserialize("<gold>Sırada Boss Var!"), MiniMessage.miniMessage().deserialize("<yellow>" + killer.getName())));
                        //spawnRandomBoss(lastEntityLocation, 1, killer);


                    /*if (quest.isCompleted()) {
                        killer.playSound(killer.getLocation(), Sound.ENTITY_WITHER_DEATH, 1f, 1f);
                        //killer.getWorld().spawnEntity(lastEntityLocation, EntityType.WITHER);
                        killer.sendMessage(MiniMessage.miniMessage().deserialize("<green>Tebrikler Görevi Tammaladın"));

                    }*/
                    }
                }
            }
        }
    }

    private EntityType spawnRandomBoss(Location location, Player player) {
        EntityType entityType = null;
        String questName = click.activeQuests.get(player.getUniqueId()).getName();
        if (questName.toLowerCase().contains("madenci")) {
            entityType = EntityType.SKELETON;
        } else if (questName.toLowerCase().contains("zombi")) {
            entityType = EntityType.ZOMBIE;
        } else if (questName.toLowerCase().contains("creeper")) {
            entityType = EntityType.CREEPER;
        }
        assert entityType != null;
        spawnBoss(location, entityType);

        return entityType;
    }

    public void spawnBoss(Location loc, EntityType entityType) {

        switch (entityType) {
            case SKELETON -> loc.getWorld().spawn(loc, Skeleton.class, skeleton-> {
                skeleton.setCustomNameVisible(true);
                skeleton.customName(MiniMessage.miniMessage().deserialize("<red>☬<gold>✯✯<gray>[<white>Svy. 62<gray>] <dark_gray>Kararmış Güç"));
            });

            case ZOMBIE -> loc.getWorld().spawn(loc, Zombie.class, zombie-> {
                zombie.setCustomNameVisible(true);
                zombie.customName(MiniMessage.miniMessage().deserialize("<red>☬<gold>✯✯<gray>[<white>Svy. 69<gray>] <dark_gray>Evrimleşmiş Yaratık"));
            });

            case CREEPER -> loc.getWorld().spawn(loc, Creeper.class, creeper-> {
                creeper.setCustomNameVisible(true);
                creeper.customName(MiniMessage.miniMessage().deserialize("<red>☬<gold>✯✯<gray>[<white>Svy. 31<gray>] <dark_gray>Yüklenmiş Patlayıcı"));
            });

        }
    }

}




