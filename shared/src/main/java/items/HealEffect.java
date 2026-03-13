package items;

import reuse.Character;

public class HealEffect implements ItemEffect{
    private final double percentage;

    public HealEffect(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public void apply(Character target) {
        int healAmount = (int) (target.getMaxHP() * this.percentage);
        target.modifyCurrentHP(healAmount);
        System.out.println(target.getName() + " restored " + healAmount + " HP!");
    }
}
