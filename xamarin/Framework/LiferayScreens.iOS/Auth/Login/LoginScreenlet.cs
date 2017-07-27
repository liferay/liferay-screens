using System;
using ObjCRuntime;
using Foundation;
using CoreGraphics;

namespace LiferayScreens
{
    // @interface LoginScreenlet : BaseScreenlet <BasicAuthBasedType>
    [BaseType(typeof(BaseScreenlet))]
    interface LoginScreenlet : IBasicAuthBasedType
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
        [Export("viewModel", ArgumentSemantic.Strong)]
        ILoginViewModel ViewModel { get; }

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
        bool LoadStoredCredentials();

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
        [Export("initWithFrame:themeName:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame, [NullAllowed] string themeName);
    }
}
