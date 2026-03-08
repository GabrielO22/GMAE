package edu.uci.gmae.group16.items;

import edu.uci.gmae.group16.reuse.Character;

public class AddBuffEffect implements ItemEffect{
    private final Buff buff;

    public AddBuffEffect(Buff buff) {
        this.buff = buff;
    }

    @Override
    public void apply(Character target) {
        //target.addBuff(this.buff);
        System.out.println(target.getName() + " gained the effect of " + buff.getName() + "!");
    }
}
