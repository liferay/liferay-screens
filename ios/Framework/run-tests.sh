#!/bin/sh

if [ -n "$1" ]; then
    sdk=${1}
else
    sdk=iphonesimulator
fi

xcodebuild clean test -workspace LiferayScreens.xcworkspace -scheme LiferayScreens -destination "platform=iOS Simulator,name=iPhone 6 Plus" | xcpretty
