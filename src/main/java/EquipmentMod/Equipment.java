package EquipmentMod;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Equipment extends CustomRelic {
    ArrayList<Integer> attributes;
    int level;


    public Equipment(String id, Texture texture, RelicTier tier, LandingSound sfx, int level, ArrayList<Integer> attributes) {
        super(id, texture, tier, sfx);
        this.level = level;
        this.attributes = attributes;

    }

    @Override
    public void obtain() {
        toInventory();
    }

    public void toInventory() {
        EquipmentMod.inventory.addToInventory(this);
        this.relicTip();
        UnlockTracker.markRelicAsSeen(this.relicId);
    }

    public abstract LongBlade makeType(int level, ArrayList<Integer> attributes);
}
