package com.newlinegaming.Runix.api;

import com.newlinegaming.Runix.api.tier.ITier;
import net.minecraft.block.Block;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
public @interface BindRunixAPI { }
