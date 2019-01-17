package EquipmentMod;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;

public abstract class Equipment extends CustomRelic {
    public Equipment(String id, Texture texture, RelicTier tier, LandingSound sfx) {
        super(id, texture, tier, sfx);
    }
}
