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
import com.liferay.mobile.screens.sites.SiteConnector;
import com.liferay.mobile.screens.webcontentdisplay.connector.JournalContentConnector;
import com.liferay.mobile.screens.webcontentdisplay.connector.ScreensJournalContentConnector;

/**
 * @author Javier Gamarra
 */
public interface ServiceVersionFactory {

	ForgotPasswordConnector getForgotPasswordConnector(Session session);

	UserConnector getUserConnector(Session session);

	CurrentUserConnector getCurrentUserConnector(Session session);

	ScreensJournalContentConnector getScreensJournalContentConnector(Session session);

	DDLRecordConnector getDDLRecordConnector(Session session);

	JournalContentConnector getJournalContentConnector(Session session);

	DDMStructureConnector getDDMStructureConnector(Session session);

	ScreensDDLRecordConnector getScreensDDLRecordConnector(Session session);

	ScreensAssetEntryConnector getScreensAssetEntryConnector(Session session);

	DLAppConnector getDLAppConnector(Session session);

	DDLRecordSetConnector getDDLRecordSetConnector(Session session);

	AssetEntryConnector getAssetEntryConnector(Session session);

	SiteConnector getSiteConnector(Session session);
}
