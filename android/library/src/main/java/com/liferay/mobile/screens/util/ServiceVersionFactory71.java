package com.liferay.mobile.screens.util;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.asset.list.connector.AssetEntryConnector;
import com.liferay.mobile.screens.asset.list.connector.AssetEntryConnector70;
import com.liferay.mobile.screens.asset.list.connector.ScreensAssetEntryConnector;
import com.liferay.mobile.screens.asset.list.connector.ScreensAssetEntryConnector70;
import com.liferay.mobile.screens.auth.forgotpassword.connector.ForgotPasswordConnector;
import com.liferay.mobile.screens.auth.login.connector.CurrentUserConnector;
import com.liferay.mobile.screens.auth.login.connector.UserConnector;
import com.liferay.mobile.screens.auth.login.connector.UserConnector70;
import com.liferay.mobile.screens.comment.connector.CommentConnector70;
import com.liferay.mobile.screens.comment.connector.ScreensCommentConnector;
import com.liferay.mobile.screens.comment.connector.ScreensCommentConnector70;
import com.liferay.mobile.screens.comment.display.interactor.delete.CommentConnector;
import com.liferay.mobile.screens.ddl.form.connector.DDLRecordConnector;
import com.liferay.mobile.screens.ddl.form.connector.DDLRecordConnector70;
import com.liferay.mobile.screens.ddl.form.connector.DDLRecordSetConnector;
import com.liferay.mobile.screens.ddl.form.connector.DDLRecordSetConnector70;
import com.liferay.mobile.screens.ddl.form.connector.DDMStructureConnector;
import com.liferay.mobile.screens.ddl.form.connector.DLAppConnector;
import com.liferay.mobile.screens.ddl.form.connector.DLAppConnector70;
import com.liferay.mobile.screens.ddl.form.connector.ScreensDDLRecordConnector;
import com.liferay.mobile.screens.ddl.form.connector.ScreensDDLRecordConnector70;
import com.liferay.mobile.screens.ddl.form.connector.ScreensDDMStructureConnector70;
import com.liferay.mobile.screens.ddm.form.connector.FormInstanceConnector;
import com.liferay.mobile.screens.ddm.form.connector.FormInstanceConnector71;
import com.liferay.mobile.screens.ddm.form.connector.FormInstanceRecordConnector;
import com.liferay.mobile.screens.ddm.form.connector.FormInstanceRecordConnector71;
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord;
import com.liferay.mobile.screens.rating.connector.ScreensRatingsConnector;
import com.liferay.mobile.screens.rating.connector.ScreensRatingsConnector70;
import com.liferay.mobile.screens.webcontent.display.connector.JournalContentConnector;
import com.liferay.mobile.screens.webcontent.display.connector.JournalContentConnector70;
import com.liferay.mobile.screens.webcontent.display.connector.ScreensJournalContentConnector;
import com.liferay.mobile.screens.webcontent.display.connector.ScreensJournalContentConnector70;

/**
 * @author Javier Gamarra
 */
public class ServiceVersionFactory71 implements ServiceVersionFactory {

	@Override
	public FormInstanceConnector getFormInstanceConnector(Session session) {
		return new FormInstanceConnector71(session);
	}

	@Override
	public FormInstanceRecordConnector getFormInstanceRecordConnector(Session session) {
		return new FormInstanceRecordConnector71(session);
	}

	@Override
	public UserConnector getUserConnector(Session session) {
		return null;
	}

	@Override
	public CurrentUserConnector getCurrentUserConnector(Session session) {
		return null;
	}

	@Override
	public ForgotPasswordConnector getForgotPasswordConnector(Session session) {
		return null;
	}

	@Override
	public DLAppConnector getDLAppConnector(Session session) {
		return null;
	}

	@Override
	public DDLRecordConnector getDDLRecordConnector(Session session) {
		return null;
	}

	@Override
	public ScreensDDLRecordConnector getScreensDDLRecordConnector(Session session) {
		return null;
	}

	@Override
	public DDLRecordSetConnector getDDLRecordSetConnector(Session session) {
		return null;
	}

	@Override
	public DDMStructureConnector getDDMStructureConnector(Session session) {
		return null;
	}

	@Override
	public AssetEntryConnector getAssetEntryConnector(Session session) {
		return null;
	}

	@Override
	public ScreensAssetEntryConnector getScreensAssetEntryConnector(Session session) {
		return null;
	}

	@Override
	public JournalContentConnector getJournalContentConnector(Session session) {
		return null;
	}

	@Override
	public ScreensJournalContentConnector getScreensJournalContentConnector(Session session) {
		return null;
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
