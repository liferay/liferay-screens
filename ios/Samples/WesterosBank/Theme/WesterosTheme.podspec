Pod::Spec.new do |s|
	s.name         = 'LiferayScreens-WesterosTheme'
	s.module_name  = 'WesterosTheme'
	s.version      = '0.1'
	s.summary      = 'A sample theme for Demo app Westeros Bank'
	s.homepage     = 'https://www.liferay.com/liferay-screens'
	s.documentation_url = 'https://github.com/liferay/liferay-screens'
	s.license = { 
		:type => 'LGPL 2.1', 
		:file => 'LICENSE.md'
	}
	s.source = {
		:git => 'https://github.com/liferay/liferay-screens.git',
		:tag => 'v1.0-beta-3'
	}
	s.authors = {
		'Jose Manuel Navarro' => 'jose.navarro@liferay.com'
	}
	s.social_media_url = 'http://twitter.com/jmnavarro'
	
	s.platform = :ios
	s.ios.deployment_target = '8.0'
	s.requires_arc = true

#	s.ios.frameworks = 'CoreGraphics', 'Foundation', 'MobileCoreServices', 'QuartzCore', 'Security', 'SystemConfiguration', 'UIKit'
	s.source_files = '**/*.{h,m,swift}'

	s.resource_bundle = {
		'LiferayScreens-westeros' => '**/*.{xib,png,plist,lproj}'
	}
	
#	s.xcconfig = {
#		'GCC_PREPROCESSOR_DEFINITIONS' => 'LIFERAY_SCREENS_FRAMEWORK=1',
#		'OTHER_SWIFT_FLAGS' => '"-D" "LIFERAY_SCREENS_FRAMEWORK"'
#	}

	s.dependency 'LiferayScreens'

end