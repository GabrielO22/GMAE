package edu.uci.gmae.group16.items;

public class HealEffect implements ItemEffect{
    private final double percentage;

    public HealEffect(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public void apply(edu.uci.gmae.group16.reuse.Character target) {
        int healAmount = (int) (target.getMaxHP() * this.percentage);
        //target.takeDamage(-healAmount);
        System.out.println(target.getName() + " restored " + healAmount + " HP!");
    }
}
