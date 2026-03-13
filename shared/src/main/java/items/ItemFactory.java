package items;

import reuse.Item;
import reuse.ItemType;

public class ItemFactory {
    public static Item createHelmet(){
        Buff helmetBuff = new Buff("Helmet buff", -1,
                c -> c.setAttribute("EFFECT_REDUCE_DMG", true),
                c -> c.removeAttribute("EFFECT_REDUCE_DMG")
        );

        return new Item(
                "Helmet",
                ItemType.BUFF,
                new AddBuffEffect(helmetBuff)
        );
    }

    public static Item createArmor(){
        Buff armorBuff = new Buff("Armor buff", 3,
                c -> c.modifyCurrentHP((int) (c.getMaxHP()*0.5)),
                c -> c.modifyDefence((int) (c.getDefence()*0.5))
        );

        return new Item(
                "Armor",
                ItemType.BUFF,
                new AddBuffEffect(armorBuff)
        );
    }

    public static Item createShield() {
        Buff shieldBuff = new Buff("Shield buff", 1,
                c -> c.setAttribute("EFFECT_IGNORES_ATTK", true),
                c -> c.removeAttribute("EFFECT_IGNORES_ATTK")
        );

        return new Item(
                "Shield",
                ItemType.BUFF,
                new AddBuffEffect(shieldBuff)
        );
    }

    public static Item createSword() {
        Buff swordBuff = new Buff("Sword buff", 1,
                c -> c.modifyAttack((int) (c.getAttack()*0.25)),
                c -> c.modifyCritRate(c.getCritRate()*0.25)
        );

        return new Item(
                "Sword",
                ItemType.WEAPON,
                new AddBuffEffect(swordBuff)
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

    public static Item createPotion() {
        return new Item(
                "Health Potion",
                ItemType.CONSUMABLE,
                new HealEffect(0.30)
        );
    }

    public static Item createAssassinBlade() {
        Buff bladeBuff = new Buff("Armor Piercer", 1,
                c -> c.setAttribute("EFFECT_IGNORES_DEFENSE", true),
                c -> c.removeAttribute("EFFECT_IGNORES_DEFENSE")
        );

        return new Item(
                "Assassin Blade",
                ItemType.WEAPON,
                new AddBuffEffect(bladeBuff)
        );
    }
}
