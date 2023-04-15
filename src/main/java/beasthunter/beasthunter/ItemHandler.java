package beasthunter.beasthunter;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemHandler {

    public ItemStack quest;
    public ItemStack cancel;
    public ItemStack ıBet;

    public ItemStack turn_back;

    public ItemStack close;

    private Main main;

    public ItemHandler(Main main) {
        this.main = main;
    }

    public static ItemStack createCustomItem(Material material, String name, List<Component> lore, String renk) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (name != null) {
            itemMeta.displayName(MiniMessage.miniMessage().deserialize("<"+renk+">"  + name));
        }


        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public void createQuestButton() {
        List<Component> lore = new ArrayList<>();
        lore.add(MiniMessage.miniMessage().deserialize("<gold>Görev Almaya Ne Dersin"));
        ItemStack questButton = createCustomItem(Material.WRITABLE_BOOK, "Görev Al", lore, "yellow");
        this.quest = questButton;
    }

    public void createCancelQuestButton() {
        List<Component> lore = new ArrayList<>();
        lore.add(MiniMessage.miniMessage().deserialize("<yellow>Görevi İptal Et"));
        lore.add(MiniMessage.miniMessage().deserialize(""));
        lore.add(MiniMessage.miniMessage().deserialize("<red>-10 Dinar"));
        lore.add(MiniMessage.miniMessage().deserialize("<gray>İşlemi yaparsan Kalacak olan tutar: ")); //Burada tırnağın dışında oyuncunun var olan parasından eksilecek olanı yazdırırsın
        ItemStack cancelButton = createCustomItem(Material.BARRIER, "Görevi İptal et", lore, "red");
        this.cancel = cancelButton;
    }

    public void createIBetButton() {
        List<Component> lore = new ArrayList<>();
        lore.add(MiniMessage.miniMessage().deserialize("<yellow>Görevi İptal Etmek İçin Tıkla."));
        lore.add(MiniMessage.miniMessage().deserialize("<white>Bunun Geri Dönüşü Yoktur."));
        lore.add(MiniMessage.miniMessage().deserialize("<gray>İşlemi yaparsan Kalacak olan tutar: ")); //Burada tırnağın dışında oyuncunun var olan parasından eksilecek olanı yazdırırsın
        ItemStack iBetButton = createCustomItem(Material.RED_STAINED_GLASS_PANE, "Görevi İptal et", lore, "red");
        this.ıBet = iBetButton;
    }

    public void creatTurnBackButton() {
        List<Component> lore = new ArrayList<>();
        lore.add(MiniMessage.miniMessage().deserialize("<yellow>Geri Dönmek İçin Tıkla"));
        ItemStack geriDonusButton = createCustomItem(Material.GREEN_STAINED_GLASS_PANE, "Geri Dön", lore, "green");
        this.turn_back = geriDonusButton;
    }

    public void createCloseButton() {
        List<Component> lore = new ArrayList<>();
        lore.add(MiniMessage.miniMessage().deserialize("<gold>Menüyü Kapatmak İçin Tıkla"));
        ItemStack kapat = createCustomItem(Material.GREEN_STAINED_GLASS_PANE, "Menüyü Kapat", lore, "gold");
        this.close = kapat;
    }


    public void init() {
        this.createCloseButton();
        this.creatTurnBackButton();
        this.createIBetButton();
        this.createCancelQuestButton();
        this.createQuestButton();
    }
}
