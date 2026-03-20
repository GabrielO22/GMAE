package items;

public class ItemFactory {

    // Consumable
    public static Item createHealthPotion() {
        return new Item("Health Potion", ItemType.CONSUMABLE, new HealEffect(0.30));
    }

    //
    public static Item createSword() {
        Buff swordBuff = new Buff("Sword buff", 1,
                c -> c.modifyAttack((int) (c.getAttack() * 0.25)),
                c -> c.modifyCritRate(c.getCritRate() * 0.25)
        );
        return new Item("Sword", ItemType.WEAPON, new AddBuffEffect(swordBuff),
                Item.DEFAULT_WEAPON_DURABILITY);
    }

    public static Item createAssassinBlade() {
        Buff bladeBuff = new Buff("Armor Piercer", 1,
                c -> c.setAttribute("EFFECT_IGNORES_DEFENSE", true),
                c -> c.removeAttribute("EFFECT_IGNORES_DEFENSE")
        );
        return new Item("Assassin Blade", ItemType.WEAPON, new AddBuffEffect(bladeBuff),
                Item.DEFAULT_WEAPON_DURABILITY);
    }


    // Buffs
    public static Item createHelmet() {
        Buff helmetBuff = new Buff("Helmet buff", -1,
                c -> c.setAttribute("EFFECT_REDUCE_DMG", true),
                c -> c.removeAttribute("EFFECT_REDUCE_DMG")
        );
        return new Item("Helmet", ItemType.BUFF, new AddBuffEffect(helmetBuff));
    }

    public static Item createArmor() {
        Buff armorBuff = new Buff("Armor buff", 3,
                c -> c.modifyCurrentHP((int) (c.getMaxHP() * 0.5)),
                c -> c.modifyDefence((int) (c.getDefence() * 0.5))
        );
        return new Item("Armor", ItemType.BUFF, new AddBuffEffect(armorBuff));
    }

    public static Item createShield() {
        Buff shieldBuff = new Buff("Shield buff", 1,
                c -> c.setAttribute("EFFECT_IGNORES_ATTK", true),
                c -> c.removeAttribute("EFFECT_IGNORES_ATTK")
        );
        return new Item("Shield", ItemType.BUFF, new AddBuffEffect(shieldBuff));
    }



    public static Item createSpear() {
        Buff spearBuff = new Buff("Spear Focus", 3,
                c -> c.modifyCritRate(0.25),
                c -> c.modifyCritRate(-0.25)
        );
        return new Item("Spear", ItemType.BUFF, new AddBuffEffect(spearBuff));
    }


    public static Item createHermesBoots() {
        Buff speedBuff = new Buff("Hermes Swiftness", 3,
                c -> c.modifySpeed(2),
                c -> c.modifySpeed(-2)
        );
        return new Item("Hermes Boots", ItemType.BUFF, new AddBuffEffect(speedBuff));
    }



    // Debuffs
    public static Item createPoisonVial() {
        Buff poisonBuff = new Buff("Poison", 3,
                c -> c.setAttribute("POISONED_DAMAGE", (int)(c.getMaxHP() * 0.08)),
                c -> c.removeAttribute("POISONED_DAMAGE")
        );
        return new Item("Poison Vial", ItemType.DEBUFF, new AddBuffEffect(poisonBuff));
    }

    public static Item createCursedRune() {
        Buff curseBuff = new Buff("Cursed", 3,
                c -> c.modifyAttack(-(int)(c.getAttack() * 0.30)),
                c -> c.modifyAttack( (int)(c.getAttack() * 0.30))
        );
        return new Item("Cursed Rune", ItemType.DEBUFF, new AddBuffEffect(curseBuff));
    }


    public static Item createDebuffScroll() {
        Buff shredBuff = new Buff("Defence Shred", 2,
                c -> c.modifyDefence(-(int)(c.getDefence() * 0.40)),
                c -> c.modifyDefence( (int)(c.getDefence() * 0.40))
        );
        return new Item("Debuff Scroll", ItemType.DEBUFF, new AddBuffEffect(shredBuff));
    }

    public static Item createLeadenBoots() {
        Buff slowBuff = new Buff("Slowed", 3,
                c -> {
                    c.setAttribute("ORIGINAL_SPEED", c.getSpeed());
                    c.modifySpeed(1 - c.getSpeed());
                },
                c -> {
                    Object orig = c.getAttribute("ORIGINAL_SPEED");
                    if (orig instanceof Integer) {
                        c.modifySpeed((int) orig - c.getSpeed());
                    }
                    c.removeAttribute("ORIGINAL_SPEED");
                }
        );
        return new Item("Leaden Boots", ItemType.DEBUFF, new AddBuffEffect(slowBuff));
    }


    // Relics
    public static Item createAncientSeed() {
        return new Item("Ancient Seed", ItemType.BUFF, null);
    }

    public static Item createObsidianSkull() {
        return new Item("Obsidian Skull", ItemType.BUFF, null);
    }

    public static Item createFrozenTear() {
        return new Item("Frozen Tear", ItemType.BUFF, null);
    }

    public static Item createGoldenScarab() {
        return new Item("Golden Scarab", ItemType.BUFF, null);
    }

    public static Item createDinosaurEgg() {
        return new Item("Dinosaur Egg", ItemType.BUFF, null);
    }

    public static Item createMysteryRelic() {
        return new Item("Mystery Relic", ItemType.BUFF, null);
    }
}