package edu.uci.gmae.group16.items;

import edu.uci.gmae.group16.reuse.Item;
import edu.uci.gmae.group16.reuse.ItemType;

public class ItemFactory {
    public static Item createPotion() {
        return new Item(
                "Health Potion",
                1,
                ItemType.CONSUMABLE,
                new HealEffect(0.30)
        );
    }

//    public static Item createSpear() {
//        Buff spearBuff = new Buff("Spear Focus", 3,
//                c -> c.modifyCritRate(0.25),
//                c -> c.modifyCritRate(-0.25)
//        );
//
//        return new Item(
//                "Spear",
//                1,
//                ItemType.BUFF,
//                new AddBuffEffect(spearBuff)
//        );
//    }
//
//    public static Item createAssassinBlade() {
//        Buff bladeBuff = new Buff("Armor Piercer", 1,
//                c -> c.setIgnoresDefense(true),
//                c -> c.setIgnoresDefense(false)
//        );
//
//        return new Item(
//                "Assassin Blade",
//                1,
//                ItemType.BUFF,
//                new AddBuffEffect(bladeBuff)
//        );
//    }
}
