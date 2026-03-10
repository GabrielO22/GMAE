package items;

import reuse.Item;
import reuse.ItemType;

public class ItemFactory {
    public static Item createPotion() {
        return new Item(
                "Health Potion",
                ItemType.CONSUMABLE,
                new HealEffect(0.30)
        );
    }

    public static Item createSpear() {
        Buff spearBuff = new Buff("Spear Focus", 3,
                c -> c.modifyCritRate(0.25),
                c -> c.modifyCritRate(-0.25)
        );

        return new Item(
                "Spear",
                ItemType.BUFF,
                new AddBuffEffect(spearBuff)
        );
    }

    public static Item createAssassinBlade() {
        Buff bladeBuff = new Buff("Armor Piercer", 1,
                c -> c.setAttribute("EFFECT_IGNORES_DEFENSE", true),
                c -> c.removeAttribute("EFFECT_IGNORES_DEFENSE")
        );

        return new Item(
                "Assassin Blade",
                ItemType.BUFF,
                new AddBuffEffect(bladeBuff)
        );
    }
}
