import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.chests.AbstractChest;

import java.util.Iterator;

public class ArmoryChest extends AbstractChest {
    public ArmoryChest() {
        this.img = ImageMaster.loadImage("armoryChest.png");
        this.openedImg = ImageMaster.loadImage("armoryChestOpened.png");
        this.hb = new Hitbox(256.0F * Settings.scale, 200.0F * Settings.scale);
        this.hb.move(CHEST_LOC_X, CHEST_LOC_Y - 100.0F * Settings.scale);
    }

    @Override
    public void open(boolean bossChest) {
        AbstractDungeon.overlayMenu.proceedButton.setLabel(TEXT[0]);
        Iterator var2 = AbstractDungeon.player.relics.iterator();

        AbstractRelic r;
        while(var2.hasNext()) {
            r = (AbstractRelic)var2.next();
            r.onChestOpen(bossChest);
        }

        CardCrawlGame.sound.play("CHEST_OPEN");

        // TODO: Remove an option on pickup
        AbstractDungeon.getCurrRoom().addRelicToRewards(EquipmentMod.generateLongBlade(AbstractDungeon.ascensionLevel));
        AbstractDungeon.getCurrRoom().addRelicToRewards(EquipmentMod.generateLongBlade(AbstractDungeon.ascensionLevel));

        while(var2.hasNext()) {
            r = (AbstractRelic)var2.next();
            r.onChestOpenAfter(bossChest);
        }

        AbstractDungeon.combatRewardScreen.open();
    }

    @Override
    public void close() {
        CardCrawlGame.sound.play("CHEST_OPEN");
        this.isOpen = false;
    }
}


