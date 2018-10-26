package com.liferay.mobile.screens.connectors

import org.junit.Test

class WebContentConnectorsTest : BaseTest() {

	@Test
	fun webContentConnectorsShouldExist() {
		val journalContentConnector = serviceProvider.getJournalContentConnector(session)

		assertThatServiceExist { journalContentConnector.getArticle(0, "") }
		assertThatServiceExist { journalContentConnector.getArticleContent(0, "", "", null) }

		assertThatServiceExist {
			serviceProvider.getScreensJournalContentConnector(session).getJournalArticleContent(0, "", 0, "")
		}
	}

	@Test
	fun webContentListConnectorsShouldExist() {
		assertThatServiceExist {
			serviceProvider.getJournalContentConnector(session).getJournalArticles(0, 0, 0, 0, null)
		}
		assertThatServiceExist { serviceProvider.getJournalContentConnector(session).getJournalArticlesCount(0, 0) }
	}
}