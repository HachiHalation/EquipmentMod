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
    public static Random stat_random;

    private static BuffHelper bhelper;

    public static InventoryScreen inventoryScreen;
    public static Inventory inventory;

    public static ArmoryRewardScreen armoryRewardScreen;

    public EquipmentMod() {
        BaseMod.subscribe(this);
        stat_random = new Random();

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
        logger.info("init equipment");
        EquipmentHelper.initializeEquipment();

        logger.info("Initializing inventory...");
        inventory = new Inventory();

        logger.info("Loading equipment...");
        // dummy relic
        BaseMod.addRelic(EquipmentHelper.generate(EquipmentID.LONGBLADE, 0), RelicType.RED);
        logger.info("saving...");
        inventory.saveInventory();
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
