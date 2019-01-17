package EquipmentMod;

import basemod.BaseMod;
import basemod.interfaces.PostPowerApplySubscriber;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BuffHelper implements PostPowerApplySubscriber {
    public BuffHelper() {
        BaseMod.subscribe(this);
    }

    @Override
    public void receivePostPowerApplySubscriber(AbstractPower abstractPower, AbstractCreature target, AbstractCreature source) {
        if (target != null && target.isPlayer) {
            if (AbstractDungeon.player.hasRelic("LongBlade") ) {
                if (abstractPower.ID.equals("Strength")){
                    LongBlade blade = (LongBlade) AbstractDungeon.player.getRelic("LongBlade");
                    blade.applyStrMod();
                } else if (abstractPower.ID.equals("Flex")) {
                    LongBlade blade = (LongBlade) AbstractDungeon.player.getRelic("LongBlade");
                    blade.applyStrModDown();
                }
            }
        }

    }
}
