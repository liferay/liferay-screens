using System;
using ObjCRuntime;
using Foundation;
using UIKit;
using CoreGraphics;

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
        bool LoadStoredCredentials();

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
        [Export("initWithFrame:themeName:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame, [NullAllowed] string themeName);

        // -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
        //[Export("initWithCoder:")]
        //[DesignatedInitializer]
        //IntPtr Constructor(NSCoder aDecoder);
    }

    // @protocol LoginScreenletDelegate <BaseScreenletDelegate>
    [BaseType(typeof(NSObject))]
    [Protocol, Model]
    interface LoginScreenletDelegate
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

    // @interface Interactor : NSObject
    [BaseType(typeof(NSObject), Name = "_TtC14LiferayScreens10Interactor")]
    interface Interactor
    {
        // @property (copy, nonatomic) NSString * _Nullable actionName;
        [NullAllowed, Export("actionName")]
        string ActionName { get; set; }

        // @property (copy, nonatomic) void (^ _Nullable)(void) onSuccess;
        [NullAllowed, Export("onSuccess", ArgumentSemantic.Copy)]
        Action OnSuccess { get; set; }

        // @property (copy, nonatomic) void (^ _Nullable)(NSError * _Nonnull) onFailure;
        [NullAllowed, Export("onFailure", ArgumentSemantic.Copy)]
        Action<NSError> OnFailure { get; set; }

        // @property (nonatomic, strong) NSError * _Nullable lastError;
        [NullAllowed, Export("lastError", ArgumentSemantic.Strong)]
        NSError LastError { get; set; }

        // @property (readonly, nonatomic, strong) BaseScreenlet * _Nullable screenlet;
        [NullAllowed, Export("screenlet", ArgumentSemantic.Strong)]
        BaseScreenlet Screenlet { get; }

        // -(instancetype _Nonnull)initWithScreenlet:(BaseScreenlet * _Nullable)screenlet __attribute__((objc_designated_initializer));
        [Export("initWithScreenlet:")]
        [DesignatedInitializer]
        IntPtr Constructor([NullAllowed] BaseScreenlet screenlet);

        // -(void)callOnSuccess;
        [Export("callOnSuccess")]
        void CallOnSuccess();

        // -(void)callOnFailure:(NSError * _Nonnull)error;
        [Export("callOnFailure:")]
        void CallOnFailure(NSError error);

        // -(BOOL)start __attribute__((warn_unused_result));
        [Export("start")]
        bool Start();

        // -(void)cancel;
        [Export("cancel")]
        void Cancel();

        // -(id _Nullable)interactionResult __attribute__((warn_unused_result));
        [NullAllowed, Export("interactionResult")]
        NSObject InteractionResult();
    }

	[BaseType(typeof(UIView))]
	interface BaseScreenlet
	{
		// @property (readonly, copy, nonatomic, class) NSString * _Nonnull DefaultAction;
		[Static]
		[Export("DefaultAction")]
		string DefaultAction { get; }

		// @property (readonly, copy, nonatomic, class) NSString * _Nonnull DefaultThemeName;
		[Static]
		[Export("DefaultThemeName")]
		string DefaultThemeName { get; }

		[Wrap("WeakDelegate")]
		[NullAllowed]
		BaseScreenletDelegate Delegate { get; set; }

		// @property (nonatomic, weak) id<BaseScreenletDelegate> _Nullable delegate __attribute__((iboutlet));
		[NullAllowed, Export("delegate", ArgumentSemantic.Weak)]
		NSObject WeakDelegate { get; set; }

		// @property (copy, nonatomic) NSString * _Nullable themeName;
		[NullAllowed, Export("themeName")]
		string ThemeName { get; set; }

		// @property (nonatomic, weak) BaseScreenletView * _Nullable screenletView;
		[NullAllowed, Export("screenletView", ArgumentSemantic.Weak)]
		BaseScreenletView ScreenletView { get; set; }

		// @property (nonatomic, weak) UIViewController * _Nullable presentingViewController;
		[NullAllowed, Export("presentingViewController", ArgumentSemantic.Weak)]
		UIViewController PresentingViewController { get; set; }

		// -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
		[Export("initWithFrame:themeName:")]
		[DesignatedInitializer]
		IntPtr Constructor(CGRect frame, [NullAllowed] string themeName);

		// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
		//[Export("initWithCoder:")]
		//[DesignatedInitializer]
		//IntPtr Constructor(NSCoder aDecoder);

        // -(void)awakeFromNib __attribute__((objc_requires_super));
        [Export("awakeFromNib")]
        //[RequiresSuper]
        void AwakeFromNib();

        // -(BOOL)becomeFirstResponder __attribute__((warn_unused_result));
        [Export("becomeFirstResponder")]
        bool BecomeFirstResponder();

		// -(void)didMoveToWindow;
		[Export("didMoveToWindow")]
		void DidMoveToWindow();

		// -(void)prepareForInterfaceBuilder;
		[Export("prepareForInterfaceBuilder")]
		void PrepareForInterfaceBuilder();

		// -(void)onCreated;
		[Export("onCreated")]
		void OnCreated();

		// -(void)onPreCreate;
		[Export("onPreCreate")]
		void OnPreCreate();

		// -(void)onHide;
		[Export("onHide")]
		void OnHide();

		// -(void)onShow;
		[Export("onShow")]
		void OnShow();

		// -(BOOL)performActionWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender;
		[Export("performActionWithName:sender:")]
		bool PerformActionWithName(string name, [NullAllowed] NSObject sender);

        // -(BOOL)performDefaultAction;
        [Export("performDefaultAction")]
        bool PerformDefaultAction();

		// -(BOOL)onActionWithName:(NSString * _Nonnull)name interactor:(Interactor * _Nonnull)interactor sender:(id _Nullable)sender;
		[Export("onActionWithName:interactor:sender:")]
		bool OnActionWithName(string name, Interactor interactor, [NullAllowed] NSObject sender);

		// -(BOOL)isActionRunning:(NSString * _Nonnull)name __attribute__((warn_unused_result));
		[Export("isActionRunning:")]
		bool IsActionRunning(string name);

		// -(void)cancelInteractorsForAction:(NSString * _Nonnull)name;
		[Export("cancelInteractorsForAction:")]
		void CancelInteractorsForAction(string name);

		// -(Interactor * _Nullable)createInteractorWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender __attribute__((warn_unused_result));
		[Export("createInteractorWithName:sender:")]
		[return: NullAllowed]
		Interactor CreateInteractorWithName(string name, [NullAllowed] NSObject sender);

		// -(void)endInteractor:(Interactor * _Nonnull)interactor error:(NSError * _Nullable)error;
		[Export("endInteractor:error:")]
		void EndInteractor(Interactor interactor, [NullAllowed] NSError error);

		// -(void)onStartInteraction;
		[Export("onStartInteraction")]
		void OnStartInteraction();

		// -(void)onFinishInteraction:(id _Nullable)result error:(NSError * _Nullable)error;
		[Export("onFinishInteraction:error:")]
		void OnFinishInteraction([NullAllowed] NSObject result, [NullAllowed] NSError error);

		// -(void)showHUDWithMessage:(NSString * _Nullable)message forInteractor:(Interactor * _Nonnull)interactor;
		[Export("showHUDWithMessage:forInteractor:")]
		void ShowHUDWithMessage([NullAllowed] string message, Interactor interactor);

		// -(void)hideHUDWithMessage:(NSString * _Nullable)message forInteractor:(Interactor * _Nonnull)interactor withError:(NSError * _Nullable)error;
		[Export("hideHUDWithMessage:forInteractor:withError:")]
		void HideHUDWithMessage([NullAllowed] string message, Interactor interactor, [NullAllowed] NSError error);

		// -(void)refreshTranslations;
		[Export("refreshTranslations")]
		void RefreshTranslations();
	}

	// @protocol BaseScreenletDelegate <NSObject>
	[Protocol, Model]
	[BaseType(typeof(NSObject), Name = "_TtP14LiferayScreens21BaseScreenletDelegate_")]
	interface BaseScreenletDelegate
	{
		// @optional -(Interactor * _Nullable)screenlet:(BaseScreenlet * _Nonnull)screenlet customInteractorForAction:(NSString * _Nonnull)customInteractorForAction withSender:(id _Nullable)withSender __attribute__((warn_unused_result));
		[Export("screenlet:customInteractorForAction:withSender:")]
		[return: NullAllowed]
		Interactor CustomInteractorForAction(BaseScreenlet screenlet, string customInteractorForAction, [NullAllowed] NSObject withSender);
	}

	// @interface BaseScreenletView : UIView <UITextFieldDelegate>
	[BaseType(typeof(UIView), Name = "_TtC14LiferayScreens17BaseScreenletView")]
	interface BaseScreenletView : IUITextFieldDelegate
	{
		// @property (nonatomic, weak) BaseScreenlet * _Nullable screenlet;
		[NullAllowed, Export("screenlet", ArgumentSemantic.Weak)]
		BaseScreenlet Screenlet { get; set; }

		// @property (nonatomic, weak) UIViewController * _Nullable presentingViewController;
		[NullAllowed, Export("presentingViewController", ArgumentSemantic.Weak)]
		UIViewController PresentingViewController { get; set; }

		// @property (readonly, copy, nonatomic) NSString * _Nonnull NoProgressMessage;
		[Export("NoProgressMessage")]
		string NoProgressMessage { get; }

		// @property (nonatomic) BOOL editable;
		[Export("editable")]
		bool Editable { get; set; }

		// @property (copy, nonatomic) NSString * _Nonnull themeName;
		[Export("themeName")]
		string ThemeName { get; set; }

		// -(void)awakeFromNib __attribute__((objc_requires_super));
		[Export("awakeFromNib")]
		//[RequiresSuper]
		void AwakeFromNib();

        // -(BOOL)becomeFirstResponder __attribute__((warn_unused_result));
        [Export("becomeFirstResponder")]
        bool BecomeFirstResponder();

		// -(void)didMoveToWindow;
		[Export("didMoveToWindow")]
		void DidMoveToWindow();

		// -(BOOL)textFieldShouldReturn:(UITextField * _Nonnull)textField __attribute__((warn_unused_result));
		[Export("textFieldShouldReturn:")]
		bool TextFieldShouldReturn(UITextField textField);

		// -(void)onCreated;
		[Export("onCreated")]
		void OnCreated();

		// -(void)onDestroy;
		[Export("onDestroy")]
		void OnDestroy();

		// -(void)onPreCreate;
		[Export("onPreCreate")]
		void OnPreCreate();

		// -(void)onHide;
		[Export("onHide")]
		void OnHide();

		// -(void)onShow;
		[Export("onShow")]
		void OnShow();

		// -(BOOL)onSetUserActionForControl:(UIControl * _Nonnull)control __attribute__((warn_unused_result));
		[Export("onSetUserActionForControl:")]
		bool OnSetUserActionForControl(UIControl control);

		// -(BOOL)onPreActionWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender __attribute__((warn_unused_result));
		[Export("onPreActionWithName:sender:")]
		bool OnPreActionWithName(string name, [NullAllowed] NSObject sender);

		// -(BOOL)onSetDefaultDelegate:(id _Nonnull)delegate view:(UIView * _Nonnull)view __attribute__((warn_unused_result));
		[Export("onSetDefaultDelegate:view:")]
		bool OnSetDefaultDelegate(NSObject @delegate, UIView view);

		// -(void)onSetTranslations;
		[Export("onSetTranslations")]
		void OnSetTranslations();

		// -(void)onStartInteraction;
		[Export("onStartInteraction")]
		void OnStartInteraction();

		// -(void)onFinishInteraction:(id _Nullable)result error:(NSError * _Nullable)error;
		[Export("onFinishInteraction:error:")]
		void OnFinishInteraction([NullAllowed] NSObject result, [NullAllowed] NSError error);

		// -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
		//[Export("createProgressPresenter")]
		//[Verify(MethodToProperty)]
		//ProgressPresenter CreateProgressPresenter { get; }

		// -(NSString * _Nullable)progressMessageForAction:(NSString * _Nonnull)actionName messageType:(enum ProgressMessageType)messageType __attribute__((warn_unused_result));
		[Export("progressMessageForAction:messageType:")]
		[return: NullAllowed]
		string ProgressMessageForAction(string actionName, ProgressMessageType messageType);

		// -(void)userActionWithSender:(id _Nullable)sender;
		[Export("userActionWithSender:")]
		void UserActionWithSender([NullAllowed] NSObject sender);

		// -(void)userActionWithName:(NSString * _Nullable)name;
		[Export("userActionWithName:")]
		void UserActionWithName([NullAllowed] string name);

		// -(void)userActionWithName:(NSString * _Nullable)name sender:(id _Nullable)sender;
		[Export("userActionWithName:sender:")]
		void UserActionWithName([NullAllowed] string name, [NullAllowed] NSObject sender);

		// -(void)changeEditable:(BOOL)editable;
		[Export("changeEditable:")]
		void ChangeEditable(bool editable);

		// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
		[Export("initWithFrame:")]
		[DesignatedInitializer]
		IntPtr Constructor(CGRect frame);

		// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
		//[Export("initWithCoder:")]
		//[DesignatedInitializer]
		//IntPtr Constructor(NSCoder aDecoder);
	}
}

