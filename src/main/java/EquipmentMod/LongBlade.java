package EquipmentMod;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class LongBlade extends Equipment{
    private static final String ID = "equipmentmod:LongBlade";
    private static final int STRENGTH_IDX = 0;
    private static final int STRENGTHMOD_IDX = 1;
    private static final int VULN_BUFF_IDX = 2;

    private boolean triggerBuff;
    private boolean triggerBuffDown;

    private int str;
    private int strmod;
    private int vulnbuff;

    public LongBlade(int level, ArrayList<Integer> attributes) {
        super (ID, EquipmentID.LONGBLADE, LongBladeHelper.getTexture(), RelicTier.STARTER, LandingSound.SOLID, level, attributes);

        str = attributes.get(STRENGTH_IDX);
        strmod = attributes.get(STRENGTHMOD_IDX);
        vulnbuff = attributes.get(VULN_BUFF_IDX) * 5;

        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();

    }

    public int getVulnImprove() {
        return VULN_BUFF_IDX;
    }

    public void applyStrMod() {
        if (triggerBuff){
            triggerBuff= false;
            this.flash();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, strmod), strmod));
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        } else
            triggerBuff= true;
    }

    public void applyStrModDown() {
        if (triggerBuffDown){
            triggerBuffDown = false;
            this.flash();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoseStrengthPower(AbstractDungeon.player, strmod), strmod));
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        } else
            triggerBuffDown = true;
    }



    @Override
    public void atBattleStart() {
        triggerBuff = false;
        triggerBuffDown = true;
        if ( str > 0) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, str), str));
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new LongBlade(level, attributes);
    }

    @Override
    public Equipment makeType(int level, ArrayList<Integer> attributes) {
        return new LongBlade(level, attributes);
    }
}
