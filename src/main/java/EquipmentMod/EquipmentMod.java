package EquipmentMod;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.localization.RelicStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

@SpireInitializer
public class EquipmentMod implements
        PostInitializeSubscriber,
        StartGameSubscriber,
        StartActSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        PostUpdateSubscriber,
        RenderSubscriber{
    public static final String MOD_NAME = "Equipment Mod";
    public static final String AUTHOR = "HachiHalation";
    public static final String DESCRIPTION = "Adds equipment EquipmentModAssets.relics to the game.";
    public static final Logger logger = LogManager.getLogger(EquipmentMod.class.getName());
    private static Random stat_random;

    private static BuffHelper bhelper;
    private static String[] longBladeCateg;
    private static HashMap<String, Integer> longBladeCosts;

    public static InventoryScreen inventoryScreen;
    public static Inventory inventory;

    public static ArmoryRewardScreen armoryRewardScreen;

    public EquipmentMod() {
        BaseMod.subscribe(this);
        stat_random = new Random();

        logger.info("generating LongBlade categories and costs");
        longBladeCateg = LongBladeHelper.initializeCategories();
        longBladeCosts = LongBladeHelper.initializeCosts();
        logger.info("LongBlade done!" + longBladeCateg.length);

        logger.info("starting buff helper");
        bhelper = new BuffHelper();


    }

    public static void initialize(){
        @SuppressWarnings("unused")
        EquipmentMod mod = new EquipmentMod();
    }

    public static boolean closeModScreens() {
        if (inventoryScreen.isOpen) {
            inventoryScreen.close();
            return true;
        }
        return false;
    }

    @Override
    public void receiveEditRelics() {
        logger.info("Initializing inventory...");
        inventory = new Inventory();

        logger.info("Loading equipment...");
        // dummy relic
        BaseMod.addRelic(generateLongBlade(0), RelicType.RED);
        logger.info("saving...");
        inventory.saveInventory();
    }


//    public static LongBlade generateLongBlade(int level){
//        logger.info("Generating new LongBlade");
//        return new LongBlade(level, allocatePoints(level, longBladeCosts, longBladeCateg));
//    }

//    private static ArrayList<Integer> allocatePoints(int level, HashMap<String, Integer> costs, String[] cat) {
//        logger.info("allocating points");
//        int numCategories = cat.length;
//        int points = (int) Math.floor(Math.pow(level, 1.5));
//        int[] result = new int[numCategories];
//        while (points > 0) {
//            int idx = stat_random.nextInt(numCategories);
//            int cost = costs.get(cat[idx]);
//            if (points - cost >= 0) {
//                points -= cost;
//                result[idx]++;
//            }
//        }

        ArrayList<Integer> attr = new ArrayList<>(numCategories);
        for (int i : result) {
            attr.add(i);
        }

        return attr;
    }

    @Override
    public void receiveStartGame() {
        inventory.reequip();
    }

    @Override
    public void receiveEditStrings() {
        logger.info("loading relic strings");
        BaseMod.loadCustomStringsFile(RelicStrings.class, "EquipmentModAssets/localization/Equipment-RelicStrings.json");
    }

    @Override
    public void receivePostInitialize() {
        // TODO: Mod badge
        Texture badgeTexture = new Texture("EquipmentModAssets/relics/ggg.png");
        ModPanel settingsPanel = new ModPanel();
        BaseMod.registerModBadge(badgeTexture, MOD_NAME, AUTHOR, DESCRIPTION, settingsPanel);

        inventoryScreen = new InventoryScreen(inventory);
        BaseMod.addTopPanelItem(new InventoryTopBar(inventoryScreen));

        armoryRewardScreen = new ArmoryRewardScreen();
    }

    @Override
    public void receivePostUpdate() {
        if (inventoryScreen.isOpen) {
            inventoryScreen.update();
        }

    }

    @Override
    public void receiveStartAct() {
        inventory.saveInventory();
    }

    @Override
    public void receiveRender(SpriteBatch spriteBatch) {
        if (inventoryScreen.isOpen)
            inventoryScreen.render(spriteBatch);
    }
}
