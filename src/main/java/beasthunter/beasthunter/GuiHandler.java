package beasthunter.beasthunter;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GuiHandler {

    public Main main;
    public Component gui_name;

    public Component cancelGui_name;
    private ItemHandler items;
    public Inventory npcGui;

    public Inventory cancelGui;

    public GuiHandler(Main main) {
        this.main = main;
        this.items = main.getItems();
        intilize();
    }

    public final int inv_rows1 = 6 * 9;


    public void intilize() {
        gui_name = MiniMessage.miniMessage().deserialize("<light_purple>Görev Ustası");
        npcGui = Bukkit.createInventory(null, inv_rows1);
        cancelGui_name = MiniMessage.miniMessage().deserialize("<dark_purple>Emin Misin?");
        cancelGui = Bukkit.createInventory(null, inv_rows1);
    }

    public Inventory npcGuiCall() {
        Inventory toReturn = Bukkit.createInventory(null, inv_rows1, gui_name);
        npcGui.setItem(20, items.quest);
        npcGui.setItem(24, items.cancel);
        npcGui.setItem(49, items.close);
        toReturn.setContents(npcGui.getContents());
        return toReturn;
    }

    public Inventory cancelGuiCall() {
        Inventory toReturn = Bukkit.createInventory(null, inv_rows1, cancelGui_name);
        cancelGui.setItem(20, items.turn_back);
        cancelGui.setItem(24, items.ıBet);
        toReturn.setContents(cancelGui.getContents());
        return toReturn;
    }

}
