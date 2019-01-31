package EquipmentMod;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class EquipmentHelper {

    private static ArrayList<Equipment> base;
    private static ArrayList<String[]> categs;
    private static ArrayList<HashMap<String, Integer>> costs;
    private static ArrayList<Texture> textures;

    public static void initializeEquipment() {
        base = new ArrayList<>();
        categs = new ArrayList<>();
        costs = new ArrayList<>();

        Equipment temp;

        int idx = EquipmentID.LONGBLADE.idx;
        EquipmentMod.logger.info("init: LongBlade");
        categs.add(idx, LongBladeHelper.initializeCategories());
        EquipmentMod.logger.info("categories: " + Arrays.toString(categs.get(idx)));
        costs.add(idx, LongBladeHelper.initializeCosts());
        EquipmentMod.logger.info("costs: " + costs.get(idx).toString());
        temp = new LongBlade(0, allocatePoints(0, costs.get(idx), categs.get(idx)));

        base.add(temp);

        idx = EquipmentID.STANDARDARMOR.idx;
        EquipmentMod.logger.info("init: StandardArmor");
        categs.add(idx, StdArmorHelper.initializeCategories());
        costs.add(idx, StdArmorHelper.initializeCosts());
        temp = new StandardArmor(0, allocatePoints(0, costs.get(idx), categs.get(idx)));
        base.add(temp);

    }


    public static ArrayList<Integer> allocatePoints(int level, HashMap<String, Integer> costs, String[] cat) {
        EquipmentMod.logger.info("allocating points...");

        int numCategories = cat.length;
        int points = (int) Math.floor(Math.pow(level, 1.5));
        int[] result = new int[numCategories];
        while (points > 0) {
            int idx = EquipmentMod.stat_random.nextInt(numCategories);
            int cost = costs.get(cat[idx]);
            if (points - cost >= 0) {
                points -= cost;
                result[idx]++;
            }
        }

        ArrayList<Integer> pts = new ArrayList<>();
        for (int i : result) {
            pts.add(i);
        }

        EquipmentMod.logger.info("points allocated");
        return pts;
    }

    public static Equipment generate(EquipmentID id, int level) {
        return base.get(id.idx).makeType(level, allocatePoints(level, costs.get(id.idx), categs.get(id.idx)));
    }

    public static Equipment createFromStats(EquipmentID id, int level, ArrayList<Integer> attributes) {
        return base.get(id.idx).makeType(level, attributes);
    }
}

enum EquipmentID {
    LONGBLADE (0),
    STANDARDARMOR(1);

    int idx;

    EquipmentID(int i) {
        idx = i;
    }
}