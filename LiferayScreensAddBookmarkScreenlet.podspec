Pod::Spec.new do |s|
	s.name         = 'LiferayScreensAddBookmarkScreenlet'
	s.version      = '1.0.0'
	s.summary      = 'A sample project to demonstrate how to create your own screenlet from scratch'
	s.homepage     = 'https://www.liferay.com/screens'
	s.license = {
		:type => 'LGPL 2.1',
		:file => 'LICENSE.md'
	}
	s.source = {
		:git => 'https://github.com/liferay/liferay-screens.git',
		:tag => s.version.to_s
	}
	s.authors = {
		'Jose Manuel Navarro' => 'jose.navarro@liferay.com'
	}

	s.platform = :ios, '8.0'
	s.requires_arc = true

	s.source_files = 'ios/Samples/AddBookmark-screenlet/LiferayScreensAddBookmarkScreenletSample/**/*.{h,m,swift}'
	s.resources = 'ios/Samples/AddBookmark-screenlet/LiferayScreensAddBookmarkScreenletSample/**/*.{xib,png,plist,lproj}'

	s.dependency 'LiferayScreens'
end