package EquipmentMod;

import java.util.ArrayList;

public class EquipmentData{

    int ironcladEquipped;
    int silentEquipped;
    int defectEquipped;

    ArrayList<String> ids;
    ArrayList<Integer> level;
    ArrayList<ArrayList<Integer>> attributesList;

    public EquipmentData(Equipment ironclad, Equipment silent, Equipment defect, ArrayList<Equipment> inventory) {
        ids = new ArrayList<>();
        level = new ArrayList<>();
        attributesList = new ArrayList<>();
        for (Equipment e : inventory) {
            ids.add(e.name);
            level.add(e.level);
            attributesList.add(e.attributes);
        }

        // set equipped items
    }
}
