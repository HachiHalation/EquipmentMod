package EquipmentMod;

import java.util.ArrayList;
import java.util.HashMap;

public class EquipmentHelper {

    private static ArrayList<Integer> allocatePoints(int level, HashMap<String, Integer> costs, String[] cat) {
        logger.info("allocating points");
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
}
