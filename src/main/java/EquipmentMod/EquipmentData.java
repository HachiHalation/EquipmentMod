package EquipmentMod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.megacrit.cardcrawl.helpers.AsyncSaver;
import com.megacrit.cardcrawl.saveAndContinue.SaveFileObfuscator;

import java.util.ArrayList;
import java.util.HashMap;

public class EquipmentData{

    int ironcladEquipped;
    int silentEquipped;
    int defectEquipped;

    ArrayList<EquipmentID> ids;
    ArrayList<Integer> level;
    ArrayList<ArrayList<Integer>> attributesList;


    public EquipmentData(Equipment ironclad, Equipment silent, Equipment defect, ArrayList<Equipment> inventory) {
        ids = new ArrayList<>();
        level = new ArrayList<>();
        attributesList = new ArrayList<>();
        for (Equipment e : inventory) {
            ids.add(e.equipID);
            level.add(e.level);
            attributesList.add(e.attributes);
        }

        ironcladEquipped = ids.size();
        ids.add(ironclad.equipID);
        level.add(ironclad.level);
        attributesList.add(ironclad.attributes);

//
//        silentEquipped = ids.size();
//        ids.add(silent.name);
//        level.add(silent.level);
//        attributesList.add(silent.attributes);


//        defectEquipped = ids.size();
//        ids.add(defect.name);
//        level.add(defect.level);
//        attributesList.add(defect.attributes);
    }

    public void save() {


    }


}
