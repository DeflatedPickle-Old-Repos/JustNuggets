package com.deflatedpickle.justnuggets.proxy;

import com.deflatedpickle.justnuggets.items.ItemNugget;

public interface CommonProxy {
    public void init();

    public void registerNuggetModel(ItemNugget nugget);
}
