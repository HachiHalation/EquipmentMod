import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class LongBlade extends CustomRelic{
    private static final String id = "LongBlade";
    private int level;
    private int str;
    private int strMod;
    private int vulnTurns;
    private int vulnImprove;

    private boolean triggerBuff;
    private boolean triggerBuffDown;

    public LongBlade(int level, int[] attributes) {
        super (id, LongBladeHelper.getTexture(), RelicTier.STARTER, LandingSound.SOLID);

        this.level = level;
        str = attributes[0];
        strMod = attributes[1];
        vulnImprove = attributes[2]*5;

        triggerBuff = true;
        triggerBuffDown = true;

        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    public int getVulnImprove() {
        return vulnImprove;
    }

    public void applyStrMod() {
        if (triggerBuffDown){
            triggerBuffDown = false;
            this.flash();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, strMod), strMod));
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        } else
            triggerBuffDown = true;
    }

    public void applyStrModDown() {
        if (triggerBuff){
            triggerBuff = false;
            this.flash();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoseStrengthPower(AbstractDungeon.player, strMod), strMod));
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        } else
            triggerBuff = true;
    }

    public LongBlade(int level, int strengthUp, int strengthMod, int vulnerableTurns, int vulnerableImprovement) {
        super(id, LongBladeHelper.getTexture(), RelicTier.STARTER, LandingSound.SOLID);

        this.level = level;
        this.str = strengthUp;
        this.strMod = strengthMod;
        this.vulnTurns = vulnerableTurns;
        this.vulnImprove = vulnerableImprovement;

        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }


    @Override
    public String getUpdatedDescription() {
        StringBuilder desc = new StringBuilder();
        desc.append(DESCRIPTIONS[1]).append(level).append(DESCRIPTIONS[2]);
        if (str != 0)
            desc.append(DESCRIPTIONS[3]).append(str).append(DESCRIPTIONS[4]);
        if (strMod != 0)
            desc.append(DESCRIPTIONS[5]).append(strMod).append(DESCRIPTIONS[6]);
        if (vulnImprove != 0)
            desc.append(DESCRIPTIONS[7]).append(vulnImprove).append(DESCRIPTIONS[8]);

        return desc.toString();
    }



    @Override
    public void atBattleStart() {
        triggerBuff = false;
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, str), str));
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new LongBlade(level, str, strMod, vulnTurns, vulnImprove);
    }



}
