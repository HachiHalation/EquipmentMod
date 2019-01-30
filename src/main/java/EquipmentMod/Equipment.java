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
    EquipmentID equipID;
    int level;


    public Equipment(String id, EquipmentID eid, Texture texture, RelicTier tier, LandingSound sfx, int level, ArrayList<Integer> attributes) {
        super(id, texture, tier, sfx);
        this.equipID = eid;
        this.level = level;
        this.attributes = attributes;

    }

    @Override
    public String getUpdatedDescription() {
        if (level == 0)
            return DESCRIPTIONS[0];

        StringBuilder desc = new StringBuilder();
        desc.append(DESCRIPTIONS[1]).append(level).append(DESCRIPTIONS[2]);

        int descNum = 2;
        for (int i : attributes) {
            if (i != 0)
                desc.append(descNum++).append(i).append(descNum++);
        }

        return desc.toString();
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

    public abstract Equipment makeType(int level, ArrayList<Integer> attributes);
}
