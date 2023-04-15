package beasthunter.beasthunter;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

public class InventoryMoveEvent implements Listener {

    private Main main;

    private ClickEvent click;

    private GuiHandler gui;

    public InventoryMoveEvent(Main main) {
        this.main = main;
        this.click = main.getClick();
        this.gui = main.getGui();
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void inventoryMoveEvent(InventoryMoveItemEvent event) {
        Player player = (Player) event.getInitiator().getViewers();
        if (player.getOpenInventory() == gui.npcGui) {
            event.setCancelled(true);
        }
        if (player.getOpenInventory() == gui.cancelGui) {
            event.setCancelled(true);
        }

    }

}
