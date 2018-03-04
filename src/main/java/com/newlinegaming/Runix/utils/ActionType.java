package com.newlinegaming.Runix.utils;


import java.util.HashMap;
import java.util.Map;

public enum ActionType {
    // named constants for 'tp', type of player action
    TP_RIGHTCLICK(0), // right-clicked block
    TP_SWING(1), // left-clicked with item in hand in air
    TP_DIGGING(2), // left-clicked a block / digging a block
    TP_BROKEN(3), // finished digging a block
    TP_PLACED(4), // placed a block
    TP_TELEPORT(5), // artificial TP sent by the new teleport method
    TP_INV_HOLDING(6), // for runes that have an effect
    TP_ARROWLAUNCH(7), // for runes that trigger on arrow launch, like multishot
    TP_RIGHTCLICKAIR(8),
    TP_ARROWHIT(9), // most bow runes don't trigger til they hit
    TP_CATCHALL(-2), // used by things like Zchest, when different actions cause
    // different effects
    TP_ALL(-1), // apply to all runes/enchants, regardless of RuneID
    TP_SPEAK(-3), // artificial TP for spoken effects like the babel curse
    TP_PASSIVE(-4); // for buffs like Flight, Fairy boots, and similar.
    private final int code;
    private static final Map<Integer, ActionType> table = new HashMap<Integer, ActionType>();

    static {
        for (ActionType a : values()) {
            table.put(a.code, a);
        }
    }

    public static ActionType fromCode(int i) {
        return table.get(i);
    }

    ActionType(int i) {
        code = i;
    }

    public final int getCode() {
        return code;
    }

    public static final ActionType[] primaryActions = {TP_RIGHTCLICK, TP_SWING, TP_DIGGING, TP_BROKEN, TP_PLACED, TP_TELEPORT, TP_INV_HOLDING, TP_ARROWLAUNCH};
}
