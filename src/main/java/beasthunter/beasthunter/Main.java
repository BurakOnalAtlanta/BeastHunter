package beasthunter.beasthunter;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public final class Main extends JavaPlugin {

    public Set<UUID> getBeklemeListesi() {
        return beklemeListesi;
    }

    Set<UUID> beklemeListesi = new HashSet<>();



    public ItemHandler getItems() {
        return items;
    }
    public GuiHandler getGui() {
        return gui;
    }
    public ClickEvent getClick() {
        return click;
    }
    public ItemHandler items;
    public GuiHandler gui;

    public ClickEvent click;

    @Override
    public void onEnable() {
        items = new ItemHandler(this);
        items.init();
        gui = new GuiHandler(this);
        click = new ClickEvent(this);
        new EntityDamageEvent(this);
        new GuiCommand(this);
        new EntityDeathEventListener(this);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!(beklemeListesi.isEmpty())) {
                    for () {

                    }
                }
            }
        }.runTaskTimer(this, 0, 20);
        send("Beast Hunter Çalışıyor");
    }

    @Override
    public void onDisable() {
        send("Beast Hunter Çalışmıyor");
    }
    public void send(String string) {
        getServer().getConsoleSender().sendMessage(MiniMessage.miniMessage().deserialize("<red>" + string));
    }

}
