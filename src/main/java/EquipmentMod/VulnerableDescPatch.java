package EquipmentMod;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;

@SpirePatch(
        clz = VulnerablePower.class,
        method = "updateDescription"
)

public class VulnerableDescPatch
{

    public static void Postfix(VulnerablePower __instance) {
        if (AbstractDungeon.player.hasRelic("equipmentmod:LongBlade") && __instance.owner != null && !__instance.owner.isPlayer ){
            int base = (AbstractDungeon.player.hasRelic("Paper Frog")) ? 75 : 50;
            String desc = (__instance.amount == 1) ? VulnerablePower.DESCRIPTIONS[2] : VulnerablePower.DESCRIPTIONS[3];

            LongBlade blade = (LongBlade) AbstractDungeon.player.getRelic("equipmentmod:LongBlade");

            __instance.description = VulnerablePower.DESCRIPTIONS[0] + (base + blade.getVulnImprove())
                    + VulnerablePower.DESCRIPTIONS[1] + __instance.amount + desc;
        }
    }
}

