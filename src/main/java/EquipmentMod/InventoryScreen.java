package EquipmentMod;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.buttons.ConfirmButton;
import com.megacrit.cardcrawl.ui.buttons.DynamicButton;
import com.megacrit.cardcrawl.ui.panels.AbstractPanel;

import java.util.ArrayList;

public class InventoryScreen {

    private static float drawStartX;
    private static float drawStartY = Settings.HEIGHT * 0.75F;
    private static float padX;
    private static float padY;

    private static final int RELICS_PER_LINE = 10;
    private Equipment hoveredRelic;
    private Equipment clickedStartedRelic;

    private Inventory inventory;

    private ConfirmButton equipButton;
    private static final float buttonX = (Settings.WIDTH * 0.8F);
    private static final float buttonY = (Settings.HEIGHT * 0.66F);

    public boolean isOpen;

    public InventoryScreen(Inventory inventory){
        drawStartX = Settings.WIDTH * 0.1F;

        padX = ((Settings.WIDTH * 0.8F) / 9.0F);
        // padX = AbstractRelic.RAW_W;
        padY = AbstractRelic.RAW_W;

        this.inventory = inventory;
        equipButton = new ConfirmButton("Equip");


        isOpen = false;
    }

    public void update() {
        updatePositions();
        updateClicking();

        equipButton.update();
    }

    public void open() {
        AbstractDungeon.player.releaseCard();
        CardCrawlGame.sound.play("DECK_OPEN");
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = AbstractDungeon.CurrentScreen.NONE;
        initializePositions();
        AbstractDungeon.overlayMenu.proceedButton.hide();
        AbstractDungeon.overlayMenu.hideCombatPanels();
        AbstractDungeon.overlayMenu.showBlackScreen();
        AbstractDungeon.overlayMenu.cancelButton.show("Return");

        equipButton.show();

        isOpen = true;
    }

    private void updateClicking() {
        if (hoveredRelic != null && hoveredRelic.hb.clickStarted) {
            if (clickedStartedRelic != null) {
                clickedStartedRelic.stopPulse();
            }
            clickedStartedRelic = hoveredRelic;
            clickedStartedRelic.beginLongPulse();
            equipButton.isDisabled = false;
        }

        if (!equipButton.isDisabled && equipButton.isHovered && equipButton.hb.clicked) {
            inventory.equip(clickedStartedRelic);
            close();
        }

    }

    private void initializePositions() {
        int linenum = 0;
        ArrayList<Equipment> items = inventory.getInventory();

        for (int i = 0; i < items.size(); ++i) {
            int mod = i % RELICS_PER_LINE;
            if (mod == 0 && i != 0)
                ++linenum;

            Equipment curr = items.get(i);
            curr.targetX = drawStartX + mod*padX;
            curr.targetY = drawStartY - linenum*padY;
        }
    }

    private void updatePositions() {
        int linenum = 0;
        ArrayList<Equipment> items = inventory.getInventory();

        for (int i = 0; i < items.size(); ++i) {
            int mod = i % RELICS_PER_LINE;
            if (mod == 0 && i != 0)
                ++linenum;

            Equipment curr = items.get(i);
            curr.targetX = drawStartX + mod*padX;
            curr.targetY = drawStartY - linenum*padY;
            curr.update();
            if (curr.hb.hovered)
                this.hoveredRelic = curr;
        }

    }

    public void render(SpriteBatch sb) {
        equipButton.render(sb);
        inventory.render(sb);
    }

    public void close() {
        isOpen = false;
        hoveredRelic = null;
        if (clickedStartedRelic != null) {
            clickedStartedRelic.stopPulse();
            equipButton.isDisabled = true;
        }
        equipButton.hb.clicked = false;
        clickedStartedRelic = null;
        AbstractDungeon.overlayMenu.cancelButton.hide();
        equipButton.hide();
        if (AbstractDungeon.previousScreen == null) {
            AbstractDungeon.overlayMenu.hideBlackScreen();
            AbstractDungeon.isScreenUp = false;
        }

        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.player.isDead) {
            AbstractDungeon.overlayMenu.showCombatPanels();
        }
    }
}

