package com.newlinegaming.Runix.Runes;

import java.util.ArrayList;

import com.newlinegaming.Runix.PersistentRune;

public class DomainRune extends PersistentRune {
    
//TODO Use BlockRecord.java for Creeper Heal
    
    private static ArrayList<PersistentRune> activeDomains= new ArrayList<PersistentRune>();
    public DomainRune() {
	runeName = ("Domain");
    }

    public ArrayList<PersistentRune> getActiveMagic() {
	return activeDomains;
    }

    @Override
    public boolean oneRunePerPerson() {
	return true;
    }

    @Override
    protected int[][][] runicTemplateOriginal() {
	return new int [][][]
		{{{0,53,0},
		    {53,20,53},
		    {0,53,0}},
		    {{0,53,0},
		     {53,TIER,53},
		     {0,53,0}}};
    }

    @Override
    public boolean isFlatRuneOnly() {
	return true;
    }


};
