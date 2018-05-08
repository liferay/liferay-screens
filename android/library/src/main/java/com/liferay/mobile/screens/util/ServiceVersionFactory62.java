package com.liferay.mobile.screens.util;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.asset.list.connector.AssetEntryConnector;
import com.liferay.mobile.screens.asset.list.connector.AssetEntryConnector62;
import com.liferay.mobile.screens.asset.list.connector.ScreensAssetEntryConnector;
import com.liferay.mobile.screens.asset.list.connector.ScreensAssetEntryConnector62;
import com.liferay.mobile.screens.auth.forgotpassword.connector.ForgotPasswordConnector;
import com.liferay.mobile.screens.auth.login.connector.CurrentUserConnector;
import com.liferay.mobile.screens.auth.login.connector.ScreensUserConnector62;
import com.liferay.mobile.screens.auth.login.connector.UserConnector;
import com.liferay.mobile.screens.auth.login.connector.UserConnector62;
import com.liferay.mobile.screens.comment.connector.ScreensCommentConnector;
import com.liferay.mobile.screens.comment.display.interactor.delete.CommentConnector;
import com.liferay.mobile.screens.ddl.form.connector.DDLRecordConnector;
import com.liferay.mobile.screens.ddl.form.connector.DDLRecordConnector62;
import com.liferay.mobile.screens.ddl.form.connector.DDLRecordSetConnector;
import com.liferay.mobile.screens.ddl.form.connector.DDLRecordSetConnector62;
import com.liferay.mobile.screens.ddl.form.connector.DDMStructureConnector;
import com.liferay.mobile.screens.ddl.form.connector.DDMStructureConnector62;
import com.liferay.mobile.screens.ddl.form.connector.DLAppConnector;
import com.liferay.mobile.screens.ddl.form.connector.DLAppConnector62;
import com.liferay.mobile.screens.ddl.form.connector.ScreensDDLRecordConnector;
import com.liferay.mobile.screens.ddl.form.connector.ScreensDDLRecordConnector62;
import com.liferay.mobile.screens.rating.connector.ScreensRatingsConnector;
import com.liferay.mobile.screens.webcontent.display.connector.JournalContentConnector;
import com.liferay.mobile.screens.webcontent.display.connector.JournalContentConnector62;
import com.liferay.mobile.screens.webcontent.display.connector.ScreensJournalContentConnector;
import com.liferay.mobile.screens.webcontent.display.connector.ScreensJournalContentConnector62;

/**
 * @author Javier Gamarra
 */
public class ServiceVersionFactory62 implements ServiceVersionFactory {

	public ForgotPasswordConnector getForgotPasswordConnector(Session session) {
		return new ScreensUserConnector62(session);
	}

	public UserConnector getUserConnector(Session session) {
		return new UserConnector62(session);
	}

	public CurrentUserConnector getCurrentUserConnector(Session session) {
		return new ScreensUserConnector62(session);
	}

	public DLAppConnector getDLAppConnector(Session session) {
		return new DLAppConnector62(session);
	}

	public DDLRecordConnector getDDLRecordConnector(Session session) {
		return new DDLRecordConnector62(session);
	}

	public ScreensDDLRecordConnector getScreensDDLRecordConnector(Session session) {
		return new ScreensDDLRecordConnector62(session);
	}

	public DDLRecordSetConnector getDDLRecordSetConnector(Session session) {
		return new DDLRecordSetConnector62(session);
	}

	public DDMStructureConnector getDDMStructureConnector(Session session) {
		return new DDMStructureConnector62(session);
	}

	public AssetEntryConnector getAssetEntryConnector(Session session) {
		return new AssetEntryConnector62(session);
	}

	public ScreensAssetEntryConnector getScreensAssetEntryConnector(Session session) {
		return new ScreensAssetEntryConnector62(session);
	}

	public JournalContentConnector getJournalContentConnector(Session session) {
		return new JournalContentConnector62(session);
	}

	public ScreensJournalContentConnector getScreensJournalContentConnector(Session session) {
		return new ScreensJournalContentConnector62(session);
	}

	@Override
	public ScreensCommentConnector getScreensCommentConnector(Session session) {
		return null;
	}

	@Override
	public ScreensRatingsConnector getScreensRatingsConnector(Session session) {
		return null;
	}

	@Override
	public CommentConnector getCommentConnector(Session session) {
		return null;
	}

}
