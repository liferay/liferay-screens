package com.liferay.mobile.screens.util;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.assetlist.connector.AssetEntryConnector;
import com.liferay.mobile.screens.assetlist.connector.ScreensAssetEntryConnector;
import com.liferay.mobile.screens.auth.forgotpassword.connector.ForgotPasswordConnector;
import com.liferay.mobile.screens.auth.login.connector.CurrentUserConnector;
import com.liferay.mobile.screens.auth.login.connector.UserConnector;
import com.liferay.mobile.screens.ddl.form.connector.DDLRecordConnector;
import com.liferay.mobile.screens.ddl.form.connector.DDLRecordSetConnector;
import com.liferay.mobile.screens.ddl.form.connector.DDMStructureConnector;
import com.liferay.mobile.screens.ddl.form.connector.DLAppConnector;
import com.liferay.mobile.screens.ddl.form.connector.ScreensDDLRecordConnector;
import com.liferay.mobile.screens.webcontent.display.connector.JournalContentConnector;
import com.liferay.mobile.screens.webcontent.display.connector.ScreensJournalContentConnector;

/**
 * @author Javier Gamarra
 */
public interface ServiceVersionFactory {

	UserConnector getUserConnector(Session session);

	CurrentUserConnector getCurrentUserConnector(Session session);

	ForgotPasswordConnector getForgotPasswordConnector(Session session);

	DLAppConnector getDLAppConnector(Session session);

	DDLRecordConnector getDDLRecordConnector(Session session);

	ScreensDDLRecordConnector getScreensDDLRecordConnector(Session session);

	DDLRecordSetConnector getDDLRecordSetConnector(Session session);

	DDMStructureConnector getDDMStructureConnector(Session session);

	AssetEntryConnector getAssetEntryConnector(Session session);

	ScreensAssetEntryConnector getScreensAssetEntryConnector(Session session);

	JournalContentConnector getJournalContentConnector(Session session);

	ScreensJournalContentConnector getScreensJournalContentConnector(Session session);

}
