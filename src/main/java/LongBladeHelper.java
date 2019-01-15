import basemod.interfaces.PostPowerApplySubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.HashMap;

public class LongBladeHelper{

    public static Texture getTexture(){
        return new Texture("relics/ggg.png");
    }

    public static String[] initializeCategories() {
        return new String[] {
                "Strength",
                "Strength Modifier",
                "Vuln Modifier"
        };
    }

    public static HashMap<String, Integer> initializeCosts() {
        HashMap<String, Integer> costs = new HashMap<>();

        costs.put("Strength", 3);
        costs.put("Strength Modifier", 2);
        costs.put("Vuln Modifier", 1);

        return costs;
    }

}
