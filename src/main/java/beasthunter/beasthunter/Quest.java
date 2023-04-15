package beasthunter.beasthunter;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Quest {
    private String name;
    private EntityType entityType;
    private int killCount;
    private boolean completed;
    private int currentKillCount;

    private boolean completedBoss;

    private boolean bossİsAlive;

    private Player owner;
    private long currentTime;

    public Quest(String name, EntityType entityType, int killCount, Player owner) {
        this.name = name;
        this.entityType = entityType;
        this.killCount = killCount;
        this.currentKillCount = 0;
        this.completed = false;
        this.completedBoss = false;
        this.owner = owner;
    }

    public Quest() {

    }

    public String getName() {
        return name;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public int getKillCount() {
        return killCount;
    }

    public void setCurrentKillCount(int currentKillCount) {
        this.currentKillCount = currentKillCount;
    }

    public int getCurrentKillCount() {
        return currentKillCount;
    }


    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Player getOwner() {
        return owner;
    }

    public boolean isBossİsAlive() {
        return bossİsAlive;
    }

    public void incrementCurrentKillCount() {

        if (!isBossİsAlive()) {
            currentKillCount++;

        }
    }


}
