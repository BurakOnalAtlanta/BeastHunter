package beasthunter.beasthunter;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class ClickEvent implements Listener {

    public Map<UUID, Quest> activeQuests = new HashMap<>();
    public Map<UUID, Double> playerDamage = new HashMap<>();

    private String[] questNames = {"Kararmış Madenci Öldür", "Zombileri Öldür", "Creeperları Temizle"};

    private Main main;
    private GuiHandler gui;
    private ItemHandler items;

    private BossBar aktifBar;

    private long lastCancelledQuestTime = 0;


    public ClickEvent(Main main) {
        this.gui = main.getGui();
        this.items = main.getItems();
        this.main = main;
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void tıklamaEvent(InventoryClickEvent event) {
        ItemStack esya = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        Quest quest = activeQuests.get(player.getUniqueId());
        UUID uuıd = player.getUniqueId();
        Quest playerMemory = PlayerUtility.getPlayerMemory(player);
        items.quest.setAmount(1);
        //int[] killArray = new int[] {16, 32, 48, 64};

        if (esya != null && esya.isSimilar(items.quest)) {
            if (!(activeQuests.containsKey(player.getUniqueId()))) {
                player.showTitle(Title.title(MiniMessage.miniMessage().deserialize("<light_purple>Görevin Başarıyla Verildi"), MiniMessage.miniMessage().deserialize("<gray>" + player.getName())));

            }
            if (activeQuests.containsKey(uuıd)) {
                if (quest.isCompleted()) {
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<aqua>Tebrikler! " + "<white>" + quest.getName() + "<aqua> görevini tamamladın!"));
                    player.closeInventory();
                    player.setLevel(player.getLevel() + 10); // Ödülü
                    activeQuests.remove(player.getUniqueId());
                    hideActiveBossBar(player);
                    //player.hideBossBar(fullBar);
                } else { // isComplated kontrolü yapcan UNUTMA
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<light_purple>Görevin Zaten var, bitirdikten sonra gelip ödevini alabilirsin."));
                }
            } else {
                createRandomQuest(player);

            }
        }
        if (esya != null && esya.isSimilar(items.cancel)) {
            if (!(activeQuests.containsKey(player.getUniqueId()))) {
                player.closeInventory();
                player.showTitle(Title.title(MiniMessage.miniMessage().deserialize("<dark_green>Herhangi Bir Görevi Yok"), MiniMessage.miniMessage().deserialize("<green>" + player.getName())));
            } else {
                player.openInventory(gui.cancelGuiCall());
            }
        }
        if (playerMemory.getCurrentTime() == 0) {
            if (esya != null &&  esya.isSimilar(items.ıBet)) {

                long coolDown = playerMemory.getCurrentTime() / 1000 + 10;
                playerMemory.setCurrentTime(coolDown * 1000);
                PlayerUtility.setPlayerMemory(player, playerMemory);
                long currentTime = System.currentTimeMillis();
                long elapsedTime = currentTime - playerMemory.getCurrentTime();
                int kalanSaniye = (int) ((10000 - elapsedTime) / 1000);
                if (coolDown >= quest.getCurrentTime()) {
                    player.showTitle(Title.title(MiniMessage.miniMessage().deserialize("<gold>Görevin Başarıyla iptal edildi"), MiniMessage.miniMessage().deserialize("<yellow>" + player.getName())));
                    player.closeInventory();
                    hideActiveBossBar(player);
                    // BU SATIRA OYUNCUNUN DİNARINDAN EKSİLTME EKLENECEK
                    activeQuests.remove(player.getUniqueId());
                } else {
                    if (playerMemory.getCurrentTime() != 0) {
                        if (playerMemory.getCurrentTime() <= currentTime) {
                            player.showTitle(Title.title(MiniMessage.miniMessage().deserialize("<aqua>Görevi Şuanlık İptal Edemezsin"), MiniMessage.miniMessage().deserialize("<gray>" + player.getName())));
                            player.sendMessage(MiniMessage.miniMessage().deserialize("<gold>Görevi iptal Etmen için en az 10 dakika geçmiş olması gerekmektedir. Kalan Süre: <yellow>" + kalanSaniye ));
                        }
                    }

                }

            }
        }


        if (esya != null &&  esya.isSimilar(items.turn_back)) {
            //player.closeInventory();
            player.openInventory(gui.npcGuiCall());
        }
        if (esya != null &&  esya.isSimilar(items.close)) {
            player.closeInventory();
        }
        if(player.getInventory().contains(items.quest)) {
            player.getInventory().removeItem(items.quest);
        }

    }

    private Quest createRandomQuest(Player player) {

        int[] killList = {16, 32, 48, 64};
        int killCountInt = killList[ThreadLocalRandom.current().nextInt(killList.length)];
        int questIndex = ThreadLocalRandom.current().nextInt(questNames.length);
        String questName = questNames[questIndex];
        EntityType entityType = null;
        if (questName.toLowerCase().contains("madenci")) {
            entityType = EntityType.SKELETON;
        } else if (questName.toLowerCase().contains("zombi")) {
            entityType = EntityType.ZOMBIE;
        } else if (questName.toLowerCase().contains("creeper")) {
            entityType = EntityType.CREEPER;
        }

        Quest quest = new Quest(questName, entityType, killCountInt, player);
        int remaining = quest.getKillCount() - 1 - quest.getCurrentKillCount();
        Component name = MiniMessage.miniMessage().deserialize("<dark_green>" + questName + " <green>" + remaining + "/" + quest.getKillCount() + " Kaldı"); // bald yapılacak
        BossBar fullBar = BossBar.bossBar(name, 1, BossBar.Color.PURPLE, BossBar.Overlay.NOTCHED_20);
        player.showBossBar(fullBar);
        player.sendMessage(MiniMessage.miniMessage().deserialize("<gold>" + player.getName() + " <white>" + questName + " <yellow>adlı görevi aldın. Yapman gereken (" + quest.getKillCount() + ")<yellow> tane " +   /*quest.getEntityType().toString()*/  " öldürmek"));
        player.closeInventory();
        this.aktifBar = fullBar;
        activeQuests.put(player.getUniqueId(), quest);
        return quest;
    }

    public void hideActiveBossBar(final @NonNull Audience target) {
        target.hideBossBar(this.aktifBar);
        this.aktifBar = null;
    }

    public boolean isTimePassed(long lastTime, int seconds) {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastTime;
        int elapsedSeconds = (int) (elapsedTime / 1000);

        if (elapsedSeconds >= seconds) {
            return true;
        } else {
            return false;
        }
    }

}
