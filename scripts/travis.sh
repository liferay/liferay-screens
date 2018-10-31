set -e

if [ "$PLATFORM" = Android ]; then
	cd android
	sh gradlew clean
	sh gradlew assemble
	sh gradlew liferay-screens:testDebug
	sh gradlew detekt
elif [ "$PLATFORM" = iOS ]; then
	cd ios/Framework
	pod install --repo-update
	set -o pipefail
	xcodebuild clean build build-for-testing -workspace LiferayScreens.xcworkspace -scheme LiferayScreens -destination "platform=iOS Simulator,name=iPhone 7" | xcpretty
	xcodebuild test-without-building -workspace LiferayScreens.xcworkspace -scheme LiferayScreens -destination "platform=iOS Simulator,name=iPhone 7" | xcpretty
elif [ "${DANGER}" -eq 1 ]; then
	brew install swiftlint
	gem install danger 
	gem install danger-swiftlint
	danger
fi

if [ "$TRAVIS_EVENT_TYPE" = cron ]; then
	cd android
	sh gradlew liferay-services-integration:testDebug
fi