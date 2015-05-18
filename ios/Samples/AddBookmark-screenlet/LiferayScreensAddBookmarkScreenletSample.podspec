Pod::Spec.new do |s|
	s.name         = 'LiferayScreensAddBookmarkScreenletSample'
	s.version      = '1.0'
	s.summary      = 'Your theme description'
	s.source = {
		:git => 'https://your_repository_url.git',
		:tag => 'v1.0'
	}

	s.platform = :ios, '8.0'
	s.requires_arc = true

	s.source_files = 'LiferayScreensAddBookmarkScreenletSample/**/*.{h,m,swift}'
	s.resources = 'LiferayScreensAddBookmarkScreenletSample/**/*.{xib,png,plist,lproj}'

	s.dependency 'LiferayScreens'
end