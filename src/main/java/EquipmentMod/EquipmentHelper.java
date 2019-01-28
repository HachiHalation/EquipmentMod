package EquipmentMod;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.HashMap;

public class EquipmentHelper {

    private static ArrayList<Equipment> base;
    private static ArrayList<String[]> categs;
    private static ArrayList<HashMap<String, Integer>> costs;
    private static ArrayList<Texture> textures;

    public static void initializeEquipment() {
        base = new ArrayList<>();

        EquipmentMod.logger.info("init: LongBlade");
        categs.add(EquipmentID.LONGBLADE.idx, LongBladeHelper.initializeCategories());
        costs.add(EquipmentID.LONGBLADE.idx, LongBladeHelper.initializeCosts());
        base.add(new LongBlade(0, allocatePoints(0, costs.get(EquipmentID.LONGBLADE.idx), categs.get(EquipmentID.LONGBLADE.idx))));


    }


    public static ArrayList<Integer> allocatePoints(int level, HashMap<String, Integer> costs, String[] cat) {
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
    LONGBLADE (0);

    int idx;

    EquipmentID(int i) {
        idx = i;
    }
}