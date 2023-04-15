package beasthunter.beasthunter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GuiCommand implements CommandExecutor {

    private Main main;
    private GuiHandler gui;
    public GuiCommand(Main main) {
        this.gui = main.getGui();
        this.main = main;
        main.getCommand("beast").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            player.openInventory(gui.npcGuiCall());

        }

        return false;
    }
}
