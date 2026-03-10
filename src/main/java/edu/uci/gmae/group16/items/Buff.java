package edu.uci.gmae.group16.items;

import java.util.function.Consumer;
import edu.uci.gmae.group16.reuse.Character;

public class Buff {
    private String name;
    private int duration;
    private Consumer<Character> onApply;
    private Consumer<Character> onRemove;

    public Buff(String name, int duration, Consumer<Character> onApply, Consumer<Character> onRemove) {
        this.name = name;
        this.duration = duration;
        this.onApply = onApply;
        this.onRemove = onRemove;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public void apply(Character target){
        onApply.accept(target);
    }

    public void remove(Character target){
        onRemove.accept(target);
    }

    public void update(){
        if (duration > 0)
            --duration;
    }
}
