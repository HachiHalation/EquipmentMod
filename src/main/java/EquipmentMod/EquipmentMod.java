package EquipmentMod;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

@SpireInitializer
public class EquipmentMod implements PostCreateStartingRelicsSubscriber,
        PostInitializeSubscriber,
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

    public EquipmentMod() {
        BaseMod.subscribe(this);
        stat_random = new Random();

        logger.info("generating LongBlade categories and costs");
        longBladeCateg = LongBladeHelper.initializeCategories();
        longBladeCosts = LongBladeHelper.initializeCosts();
        logger.info("LongBlade done!");

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
        //BaseMod.addRelic(generateLongBlade(2), RelicType.RED);
        //BaseMod.addRelic(generateLongBlade(5), RelicType.RED);
        BaseMod.addRelic(generateLongBlade(10), RelicType.RED);
    }


    public static LongBlade generateLongBlade(int level){
        logger.info("Generating new LongBlade");
        return new LongBlade(level, allocatePoints(level, longBladeCosts, longBladeCateg));
    }

    private static int[] allocatePoints(int level, HashMap<String, Integer> costs, String[] cat) {
        logger.info("allocating points");
        int numCategories = costs.size();
        int points = (int) Math.floor(Math.pow(level, 1.5));
        int[] result = new int[numCategories];

        while (points > 0) {
            int idx = stat_random.nextInt(numCategories);
            int cost = costs.get(cat[idx]);
            if (points - cost >= 0) {
                points -= cost;
                result[idx]++;
            }
        }

        return result;
    }



    @Override
    public void receivePostCreateStartingRelics(AbstractPlayer.PlayerClass playerClass, ArrayList<String> arrayList) {
        arrayList.add("equipmentmod:LongBlade");

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

        inventory = new Inventory();
        inventoryScreen = new InventoryScreen(inventory);
        BaseMod.addTopPanelItem(new InventoryTopBar(inventoryScreen));
    }

    @Override
    public void receivePostUpdate() {
        if (inventoryScreen.isOpen) {
            inventoryScreen.update();
        }

    }

    @Override
    public void receiveRender(SpriteBatch spriteBatch) {
        if (inventoryScreen.isOpen)
            inventoryScreen.render(spriteBatch);
    }
}
