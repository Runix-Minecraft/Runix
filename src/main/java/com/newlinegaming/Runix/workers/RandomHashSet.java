package com.newlinegaming.Runix.workers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/** From http://stackoverflow.com/a/12386664/3067894 
 * If we use this inside a worker it will give us truly random block iteration.*/
class RandomHashSet<A> {
    private final ArrayList<A> contents = new ArrayList<>();
    private final HashMap<A,Integer> indices = new HashMap<>();
    private final Random R = new Random();

    //selects random element in constant time
    A randomKey() {
        return contents.get(R.nextInt(contents.size()));
    }

    //adds new element in constant time
    void add(A a) {
        indices.put(a,contents.size());
        contents.add(a);
    }

    //removes element in constant time
    void remove(A a) {
        int index = indices.get(a);
        contents.set(index,contents.get(contents.size()-1));
        contents.remove(contents.size()-1);
        indices.put(contents.get(contents.size()-1), index);
        indices.remove(a);
    }
}
