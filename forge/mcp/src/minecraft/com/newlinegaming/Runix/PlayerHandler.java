package com.newlinegaming.Runix;

import java.util.ArrayList;

/**
 * PlayerHandler is a persistent rune because we would like to use the same saving and loading functionality.
 * It overrides checkRunePattern to ensure there is no way to create this rune in-game.
 * Instead a new PlayerHandler instance is created each time a new player enters the server for the first time.
 */
public class PlayerHandler extends PersistentRune {
    private static ArrayList<PersistentRune> serverPlayers = new ArrayList<PersistentRune>();

    @Override
    public boolean checkRunePattern(WorldXYZ coords) {
        return false;
    }

    @Override
    public ArrayList<PersistentRune> getActiveMagic() {
        return serverPlayers;
    }

    @Override
    public boolean oneRunePerPerson() {
        return true;
    }

    @Override
    protected int[][][] runicTemplateOriginal() {
        return new int[][][] {
        };
    }

    @Override
    public boolean isFlatRuneOnly() {
        return false;
    }

}