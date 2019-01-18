package EquipmentMod;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class Inventory {
    public ArrayList<Equipment> inventory;
    public Equipment ironcladEquipped;
    public Equipment silentEquipped;
    public Equipment defectEquipped;


    public Inventory() {
        inventory = new ArrayList<>();
        loadInventory();
    }

    private void loadInventory() {
        for (int i = 0; i <= 20; i++){
            LongBlade temp = EquipmentMod.generateLongBlade(i);
            temp.isObtained = true;
            inventory.add(temp);
        }

        if (ironcladEquipped == null)
            ironcladEquipped = (Equipment) inventory.get(0).makeCopy();
    }

    public void addToInventory(Equipment item) {
        inventory.add(item);
    }

    public void equip(Equipment item) {
        if (AbstractDungeon.player instanceof Ironclad) {
            unequip(ironcladEquipped);
            ironcladEquipped = (Equipment) item.makeCopy();
            ironcladEquipped.instantObtain();
            // TODO : Other things to do on equip
        }
        // TODO: other characters

    }

    public void reequip() {
        if (AbstractDungeon.player instanceof Ironclad) {
            AbstractDungeon.player.loseRelic("equipmentmod:LongBlade");
            if (ironcladEquipped != null) {
                ironcladEquipped.instantObtain();
            }
            else
                EquipmentMod.generateLongBlade(0).instantObtain();

        }
    }

    public void unequip(Equipment item) {
            Equipment relic =  (Equipment) AbstractDungeon.player.getRelic(item.relicId);
            if (relic == item)
                AbstractDungeon.player.loseRelic(item.relicId);
            // TODO: other things that must be done when unequipping
    }

    public ArrayList<Equipment> getInventory() {
        return inventory;
    }

    public void render(SpriteBatch sb) {
        for (Equipment e : inventory)
            e.render(sb);
    }
}
