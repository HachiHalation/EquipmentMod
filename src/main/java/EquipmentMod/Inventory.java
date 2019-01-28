package EquipmentMod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.AsyncSaver;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.saveAndContinue.SaveFileObfuscator;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventory {


    public ArrayList<Equipment> inventory;
    public Equipment ironcladEquipped;
    public Equipment silentEquipped;
    public Equipment defectEquipped;


    public Inventory() {
        inventory = new ArrayList<>();
        loadInventory();
    }

    //TODO: obscure file data
    public void saveInventory() {
        EquipmentData data = new EquipmentData(ironcladEquipped, silentEquipped, defectEquipped, inventory);

        HashMap<String, Object> map = new HashMap<>();
        EquipmentMod.logger.info("saving ironclad equipped: " + ironcladEquipped);
        map.put("ironcladEquipped", data.ironcladEquipped);
//        EquipmentMod.logger.info("saving silent equipped: " + silentEquipped);
//        map.put("silent", silentEquipped);
//        EquipmentMod.logger.info("saving defect equipped: " + defectEquipped);
//        map.put("defect", defectEquipped);

        EquipmentMod.logger.info("saving ids: " + data.ids.toString());
        map.put("ids", data.ids);
        EquipmentMod.logger.info("saving levels: " + data.ids.toString());
        map.put("level", data.level);
        EquipmentMod.logger.info("saving attributes: ");
        for (ArrayList a : data.attributesList) {
            EquipmentMod.logger.info(a.toString());
        }
        map.put("attributesList", data.attributesList);

        Gson gson = (new GsonBuilder().setPrettyPrinting().create());
        String str = gson.toJson(map);
        String filepath = "equipmentmod/saves/inventory.data";
        AsyncSaver.save(filepath, str);


    }


    private void loadInventory() {
        EquipmentData data;
        Gson gson = new Gson();
        FileHandle file = Gdx.files.local("equipmentmod/saves/inventory.data");
        String str = file.readString();
        EquipmentMod.logger.info("file read string: " + str);

        EquipmentMod.logger.info("loading...");
        data = gson.fromJson(str, EquipmentData.class);
        if (data.ids != null) {
            EquipmentMod.logger.info("loaded ids: " + data.ids.toString());
        } else {
            EquipmentMod.logger.info("ids are null!");
        }

        if (data.level != null)
            EquipmentMod.logger.info("loaded level: " + data.level.toString());
        else
            EquipmentMod.logger.info("level are null!");

        if (data.attributesList != null) {
            EquipmentMod.logger.info("loaded attr: ");
            for (ArrayList a : data.attributesList) {
                EquipmentMod.logger.info(a.toString());
            }
        }
        else
            EquipmentMod.logger.info("attributes are null!");

        for (int i = 0; i < data.ids.size(); ++i) {
            if (i != data.ironcladEquipped) {
                LongBlade blade = new LongBlade(data.level.get(i), data.attributesList.get(i));
                blade.isObtained = true;
                addToInventory(blade);
            }

        }
        ironcladEquipped = new LongBlade(data.level.get(data.ironcladEquipped), data.attributesList.get(data.ironcladEquipped));
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
            AbstractDungeon.player.loseRelic(ironcladEquipped.relicId);
            ironcladEquipped.instantObtain();

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
