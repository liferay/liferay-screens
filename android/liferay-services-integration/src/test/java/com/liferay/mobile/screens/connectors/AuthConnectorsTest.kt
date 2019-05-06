package com.liferay.mobile.screens.connectors

import org.junit.Test

class AuthInteractorsTest : BaseTest() {

	@Test
	fun loginConnectorServicesShouldExist() {
		val userConnector = serviceProvider.getUserConnector(session)

		assertThatServiceExist { userConnector.getUserByEmailAddress(0, "") }
		assertThatServiceExist { userConnector.getUserByScreenName(0, "") }
		assertThatServiceExist { userConnector.getUserById(0) }
	}

	@Test
	fun signUpConnectorServicesShouldExist() {
		val userConnector = serviceProvider.getUserConnector(session)

		assertThatServiceExist {
			userConnector.addUser(0, true, "", "",
				true, "", "", 0, "",
				"", "", "", "", 0, 0, true,
				1, 1, 1, "", null,
				null, null, null, true, null)
		}
	}

	@Test
	fun forgotPasswordConnectorServicesShouldExist() {
		assertThatServiceExist { serviceProvider.getForgotPasswordConnector(session).sendPasswordByEmailAddress(0, "") }
		assertThatServiceExist { serviceProvider.getForgotPasswordConnector(session).sendPasswordByUserId(0) }
		assertThatServiceExist { serviceProvider.getForgotPasswordConnector(session).sendPasswordByScreenName(0, "") }
	}
}

