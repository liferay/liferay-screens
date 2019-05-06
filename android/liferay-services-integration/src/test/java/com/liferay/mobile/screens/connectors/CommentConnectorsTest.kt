package com.liferay.mobile.screens.connectors

import org.junit.Test

class CommentConnectorsTest : BaseTest() {

	@Test
	fun commentConnectorsShouldExist() {
		val commentService = serviceProvider.getScreensCommentConnector(session)

		assertThatServiceExist { commentService.getComment(0) }
		assertThatServiceExist { commentService.getComments("", 0, 0, 0) }
		assertThatServiceExist { commentService.getCommentsCount("", 0) }
		assertThatServiceExist { commentService.updateComment(0, "") }
		assertThatServiceExist { commentService.addComment("", 0, "") }

		assertThatServiceExist { serviceProvider.getCommentConnector(session).deleteComment(0) }
	}
}