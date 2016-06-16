package com.liferay.mobile.screens.base.interactor;

/**
 * @author Javier Gamarra
 */
public interface CustomInteractorListener<I extends Interactor> {

	I createInteractor(String actionName);
}
