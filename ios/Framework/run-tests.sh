#!/bin/sh

if [ -n "$1" ]; then
    sdk=${1}
else
    sdk=iphonesimulator9.1
fi

xctool clean test -workspace LiferayScreens.xcworkspace -scheme LiferayScreens -sdk ${sdk} ONLY_ACTIVE_ARCH=NO
