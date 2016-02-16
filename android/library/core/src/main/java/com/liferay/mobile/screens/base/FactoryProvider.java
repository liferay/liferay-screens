package com.liferay.mobile.screens.base;

import android.util.Log;

import com.liferay.mobile.screens.context.LiferayServerContext;

/**
 * @author Javier Gamarra
 */
public class FactoryProvider {

	public static AbstractFactory getInstance() {
		if (_abstractFactory == null) {
			synchronized (AbstractFactory.class) {
				_abstractFactory = createFactory();
			}
		}
		return _abstractFactory;
	}

	private static AbstractFactory createFactory() {
		try {
			return (AbstractFactory) Class.forName(LiferayServerContext.getFactoryClass()).newInstance();
		}
		catch (Exception e) {
			Log.e("LiferayScreens", "Error creating the instance class, " +
				"there isn't an attribute called *factory_class* that can be instantiated. " +
				"Are you sure that your class and package exists and it has a public empty constructor?");
			return new FactoryCE();
		}
	}

	private static AbstractFactory _abstractFactory;
}
