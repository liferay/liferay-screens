package com.liferay.mobile.screens.connectors

import org.junit.Test

class RatingConnectorsTest : BaseTest() {

	@Test
	fun ratingConnectorsShouldExist() {
		val ratingService = serviceProvider.getScreensRatingsConnector(session)

		assertThatServiceExist { ratingService.getRatingsEntries(0, 0) }
		assertThatServiceExist { ratingService.getRatingsEntries(0, "", 0) }
		assertThatServiceExist { ratingService.updateRatingsEntry(0, "", 0.0, 0) }
		assertThatServiceExist { ratingService.deleteRatingsEntry(0, "", 0) }
	}
}