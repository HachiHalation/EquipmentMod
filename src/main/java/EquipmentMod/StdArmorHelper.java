package EquipmentMod;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class StdArmorHelper {

    public static Texture getTexture() {
        return new Texture("EquipmentModAssets/relics/armorTemp.png");
    }

    public static String[] initializeCategories() {
        return new String[] {
                "Dexterity",
                "Plated Armor",
                "P/T Mod",
                "HP"
        };
    }

    public static HashMap<String, Integer> initializeCosts() {
        HashMap<String, Integer> costs = new HashMap<>();

        costs.put("Dexterity", 2);
        costs.put("Plated Armor", 1);
        costs.put("P/T Mod", 2);
        costs.put("HP", 1);

        return costs;
    }

}
