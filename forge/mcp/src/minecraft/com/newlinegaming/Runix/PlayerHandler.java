package com.newlinegaming.Runix;

import java.util.ArrayList;

public class PlayerHandler extends PersistentRune {
private static ArrayList<PersistentRune> serverPlayers = new ArrayList<PersistentRune>();

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
