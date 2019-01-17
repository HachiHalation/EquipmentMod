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
        for (int i = 1; i <= 20; i++){
            LongBlade temp = EquipmentMod.generateLongBlade(i);
            temp.isObtained = true;
            inventory.add(temp);
        }
    }

    public void addToInventory(Equipment item) {
        inventory.add(item);
    }

    public void equip(Equipment item) {
        if (AbstractDungeon.player instanceof Ironclad) {
            if (item instanceof LongBlade)
                ironcladEquipped = item;
        }
    }

    public ArrayList<Equipment> getInventory() {
        return inventory;
    }

    public void render(SpriteBatch sb) {
        for (Equipment e : inventory)
            e.render(sb);
    }
}
