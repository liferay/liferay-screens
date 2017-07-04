using System;

using ObjCRuntime;
using Foundation;
using UIKit;

namespace BindingLibrary
{
	// @interface LoginScreenlet : BaseScreenlet <BasicAuthBasedType>
	[BaseType(typeof(BaseScreenlet))]
	interface LoginScreenlet //: IBasicAuthBasedType
	{
		// @property (copy, nonatomic) NSString * _Nullable basicAuthMethod;
		[NullAllowed, Export("basicAuthMethod")]
		string BasicAuthMethod { get; set; }

		// @property (nonatomic) BOOL saveCredentials;
		[Export("saveCredentials")]
		bool SaveCredentials { get; set; }

		// @property (nonatomic) int64_t companyId;
		[Export("companyId")]
		long CompanyId { get; set; }

		// @property (copy, nonatomic) NSString * _Nonnull OAuthConsumerKey;
		[Export("OAuthConsumerKey")]
		string OAuthConsumerKey { get; set; }

		// @property (copy, nonatomic) NSString * _Nonnull OAuthConsumerSecret;
		[Export("OAuthConsumerSecret")]
		string OAuthConsumerSecret { get; set; }

		// @property (copy, nonatomic) NSString * _Nonnull loginMode;
		[Export("loginMode")]
		string LoginMode { get; set; }

		// @property (copy, nonatomic) void (^ _Nullable)(NSURLAuthenticationChallenge * _Nonnull, void (^ _Nonnull)(NSURLSessionAuthChallengeDisposition, NSURLCredential * _Nonnull)) challengeResolver;
		//[NullAllowed, Export("challengeResolver", ArgumentSemantic.Copy)]
		//Action<NSURLAuthenticationChallenge, Action<NSURLSessionAuthChallengeDisposition, NSURLCredential>> ChallengeResolver { get; set; }

		[Wrap("WeakLoginDelegate")]
		[NullAllowed]
		LoginScreenletDelegate LoginDelegate { get; }

		// @property (readonly, nonatomic, strong) id<LoginScreenletDelegate> _Nullable loginDelegate;
		[NullAllowed, Export("loginDelegate", ArgumentSemantic.Strong)]
		NSObject WeakLoginDelegate { get; }

		// @property (readonly, nonatomic, strong) id<LoginViewModel> _Nonnull viewModel;
		//[Export("viewModel", ArgumentSemantic.Strong)]
		//LoginViewModel ViewModel { get; }

		// @property (nonatomic) enum AuthType authType;
		[Export("authType", ArgumentSemantic.Assign)]
		AuthType AuthType { get; set; }

		// -(void)onCreated;
		[Export("onCreated")]
		void OnCreated();

		// -(Interactor * _Nullable)createInteractorWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender __attribute__((warn_unused_result));
		[Export("createInteractorWithName:sender:")]
		[return: NullAllowed]
		Interactor CreateInteractorWithName(string name, [NullAllowed] NSObject sender);

		// -(BOOL)loadStoredCredentials __attribute__((warn_unused_result));
		[Export("loadStoredCredentials")]
		[Verify(MethodToProperty)]
		bool LoadStoredCredentials { get; }

		// -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
		[Export("initWithFrame:themeName:")]
		[DesignatedInitializer]
		IntPtr Constructor(CGRect frame, [NullAllowed] string themeName);

		// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
		[Export("initWithCoder:")]
		[DesignatedInitializer]
		IntPtr Constructor(NSCoder aDecoder);
	}

	// @protocol LoginScreenletDelegate <BaseScreenletDelegate>
	[Protocol, Model]
	interface LoginScreenletDelegate : IBaseScreenletDelegate
	{
		// @optional -(void)screenlet:(BaseScreenlet * _Nonnull)screenlet onLoginResponseUserAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes;
		[Export("screenlet:onLoginResponseUserAttributes:")]
		void OnLoginResponseUserAttributes(BaseScreenlet screenlet, NSDictionary<NSString, NSObject> attributes);

		// @optional -(void)screenlet:(BaseScreenlet * _Nonnull)screenlet onLoginError:(NSError * _Nonnull)error;
		[Export("screenlet:onLoginError:")]
		void OnLoginError(BaseScreenlet screenlet, NSError error);

		// @optional -(void)screenlet:(BaseScreenlet * _Nonnull)screenlet onCredentialsSavedUserAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes;
		[Export("screenlet:onCredentialsSavedUserAttributes:")]
		void OnCredentialsSavedUserAttributes(BaseScreenlet screenlet, NSDictionary<NSString, NSObject> attributes);

		// @optional -(void)screenlet:(LoginScreenlet * _Nonnull)screenlet onCredentialsLoadedUserAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes;
		[Export("screenlet:onCredentialsLoadedUserAttributes:")]
		void OnCredentialsLoadedUserAttributes(LoginScreenlet screenlet, NSDictionary<NSString, NSObject> attributes);
	}
}

