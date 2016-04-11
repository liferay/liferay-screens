package com.liferay.mobile.screens.util;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.assetlist.connector.AssetEntryConnector;
import com.liferay.mobile.screens.assetlist.connector.AssetEntryConnector70;
import com.liferay.mobile.screens.assetlist.connector.ScreensAssetEntryConnector;
import com.liferay.mobile.screens.assetlist.connector.ScreensAssetEntryConnector70;
import com.liferay.mobile.screens.auth.forgotpassword.connector.ForgotPasswordConnector;
import com.liferay.mobile.screens.auth.login.connector.CurrentUserConnector;
import com.liferay.mobile.screens.auth.login.connector.UserConnector;
import com.liferay.mobile.screens.auth.login.connector.UserConnector70;
import com.liferay.mobile.screens.ddl.form.connector.DDLRecordConnector;
import com.liferay.mobile.screens.ddl.form.connector.DDLRecordConnector70;
import com.liferay.mobile.screens.ddl.form.connector.DDLRecordSetConnector;
import com.liferay.mobile.screens.ddl.form.connector.DDLRecordSetConnector70;
import com.liferay.mobile.screens.ddl.form.connector.DDMStructureConnector;
import com.liferay.mobile.screens.ddl.form.connector.DDMStructureConnector70;
import com.liferay.mobile.screens.ddl.form.connector.DLAppConnector;
import com.liferay.mobile.screens.ddl.form.connector.DLAppConnector70;
import com.liferay.mobile.screens.ddl.form.connector.ScreensDDLRecordConnector;
import com.liferay.mobile.screens.ddl.form.connector.ScreensDDLRecordConnector70;
import com.liferay.mobile.screens.sites.SiteConnector;
import com.liferay.mobile.screens.webcontentdisplay.connector.JournalContentConnector;
import com.liferay.mobile.screens.webcontentdisplay.connector.JournalContentConnector70;
import com.liferay.mobile.screens.webcontentdisplay.connector.ScreensJournalContentConnector;
import com.liferay.mobile.screens.webcontentdisplay.connector.ScreensJournalContentConnector70;

/**
 * @author Javier Gamarra
 */
public class ServiceVersionFactory70 implements ServiceVersionFactory {

	public ForgotPasswordConnector getForgotPasswordConnector(Session session) {
		return new UserConnector70(session);
	}

	public UserConnector getUserConnector(Session session) {
		return new UserConnector70(session);
	}

	public CurrentUserConnector getCurrentUserConnector(Session session) {
		return new UserConnector70(session);
	}

	public ScreensJournalContentConnector getScreensJournalContentConnector(Session session) {
		return new ScreensJournalContentConnector70(session);
	}

	public DDLRecordConnector getDDLRecordConnector(Session session) {
		return new DDLRecordConnector70(session);
	}

	public JournalContentConnector getJournalContentConnector(Session session) {
		return new JournalContentConnector70(session);
	}

	public DDMStructureConnector getDDMStructureConnector(Session session) {
		return new DDMStructureConnector70(session);
	}

	public ScreensDDLRecordConnector getScreensDDLRecordConnector(Session session) {
		return new ScreensDDLRecordConnector70(session);
	}

	public ScreensAssetEntryConnector getScreensAssetEntryConnector(Session session) {
		return new ScreensAssetEntryConnector70(session);
	}

	public DLAppConnector getDLAppConnector(Session session) {
		return new DLAppConnector70(session);
	}

	public DDLRecordSetConnector getDDLRecordSetConnector(Session session) {
		return new DDLRecordSetConnector70(session);
	}

	public AssetEntryConnector getAssetEntryConnector(Session session) {
		return new AssetEntryConnector70(session);
	}

	@Override
	public SiteConnector getSiteConnector(Session session) {
		return new SiteConnector70(session);
	}
}
