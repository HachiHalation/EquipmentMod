package EquipmentMod;

import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class StandardArmor extends Equipment{
    private static final String ID = "equipmentmod:StandardArmor";
    private static final int DEX_IDX = 0;
    private static final int PLATED_IDX = 1;
    private static final int MOD_IDX = 2;
    private static final int HP_IDX = 3;

    private int dex;
    private int plated;
    private int mod;
    private int hp;

    public StandardArmor(int level, ArrayList<Integer> attributes) {
        super(ID, EquipmentID.STANDARDARMOR, StdArmorHelper.getTexture(), RelicTier.STARTER, LandingSound.SOLID, level, attributes);
        dex = attributes.get(DEX_IDX);
        plated = attributes.get(PLATED_IDX);
        mod = attributes.get(MOD_IDX);
        hp = attributes.get(HP_IDX) * 5;

        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }



    @Override
    public AbstractRelic makeCopy() {
        return new StandardArmor(level, attributes);
    }

    @Override
    public Equipment makeType(int level, ArrayList<Integer> attributes) {
        return new StandardArmor(level, attributes);
    }
}
