using System;
using AVFoundation;
using AVKit;
using CoreGraphics;
using Foundation;
using LiferayScreens;
using ObjCRuntime;
using ObjectiveC;
using UIKit;
using WebKit;

// @protocol AnonymousBasicAuthType
[Protocol, Model]
interface AnonymousBasicAuthType
{
	// @required @property (copy, nonatomic) NSString * _Nullable anonymousApiUserName;
	[Abstract]
	[NullAllowed, Export ("anonymousApiUserName")]
	string AnonymousApiUserName { get; set; }

	// @required @property (copy, nonatomic) NSString * _Nullable anonymousApiPassword;
	[Abstract]
	[NullAllowed, Export ("anonymousApiPassword")]
	string AnonymousApiPassword { get; set; }
}

// @interface Asset : NSObject <NSCoding>
[BaseType (typeof(NSObject))]
[DisableDefaultCtor]
interface Asset : INSCoding
{
	// @property (readonly, copy, nonatomic) NSDictionary<NSString *,id> * _Nonnull attributes;
	[Export ("attributes", ArgumentSemantic.Copy)]
	NSDictionary<NSString, NSObject> Attributes { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull title;
	[Export ("title")]
	string Title { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nullable mimeType;
	[NullAllowed, Export ("mimeType")]
	string MimeType { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull description;
	[Export ("description")]
	string Description { get; }

	// @property (readonly, nonatomic) int64_t classNameId;
	[Export ("classNameId")]
	long ClassNameId { get; }

	// @property (readonly, nonatomic) int64_t classPK;
	[Export ("classPK")]
	long ClassPK { get; }

	// @property (readonly, nonatomic) int64_t groupId;
	[Export ("groupId")]
	long GroupId { get; }

	// @property (readonly, nonatomic) int64_t companyId;
	[Export ("companyId")]
	long CompanyId { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull url;
	[Export ("url")]
	string Url { get; }

	// @property (readonly, nonatomic) int64_t entryId;
	[Export ("entryId")]
	long EntryId { get; }

	// @property (readonly, copy, nonatomic) NSDate * _Nonnull createDate;
	[Export ("createDate", ArgumentSemantic.Copy)]
	NSDate CreateDate { get; }

	// @property (readonly, copy, nonatomic) NSDate * _Nonnull modifiedDate;
	[Export ("modifiedDate", ArgumentSemantic.Copy)]
	NSDate ModifiedDate { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull debugDescription;
	[Export ("debugDescription")]
	string DebugDescription { get; }

	// -(instancetype _Nonnull)initWithAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes __attribute__((objc_designated_initializer));
	[Export ("initWithAttributes:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSDictionary<NSString, NSObject> attributes);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);

	// -(void)encodeWithCoder:(NSCoder * _Nonnull)aCoder;
	[Export ("encodeWithCoder:")]
	void EncodeWithCoder (NSCoder aCoder);
}

// @interface AssetClassEntry : NSObject
[BaseType (typeof(NSObject))]
[DisableDefaultCtor]
interface AssetClassEntry
{
	// @property (readonly, nonatomic) int64_t classNameId;
	[Export ("classNameId")]
	long ClassNameId { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull className;
	[Export ("className")]
	string ClassName { get; }

	// -(instancetype _Nonnull)init:(int64_t)classNameId :(NSString * _Nonnull)className __attribute__((objc_designated_initializer));
	[Export ("init::")]
	[DesignatedInitializer]
	IntPtr Constructor (long classNameId, string className);
}

// @interface AssetClasses : NSObject
[BaseType (typeof(NSObject))]
interface AssetClasses
{
	// +(NSString * _Nullable)getClassName:(NSString * _Nonnull)key __attribute__((warn_unused_result));
	[Static]
	[Export ("getClassName:")]
	[return: NullAllowed]
	string GetClassName (string key);

	// +(NSString * _Nullable)getClassNameFromId:(int64_t)classNameId __attribute__((warn_unused_result));
	[Static]
	[Export ("getClassNameFromId:")]
	[return: NullAllowed]
	string GetClassNameFromId (long classNameId);

	// +(void)set:(NSString * _Nonnull)key newId:(int64_t)newId;
	[Static]
	[Export ("set:newId:")]
	void Set (string key, long newId);

	// +(void)set:(NSString * _Nonnull)key newClassName:(NSString * _Nonnull)newClassName;
	[Static]
	[Export ("set:newClassName:")]
	void Set (string key, string newClassName);
}

// @interface AssetDisplayBuilder : NSObject
[BaseType (typeof(NSObject))]
interface AssetDisplayBuilder
{
	// +(BaseScreenlet * _Nullable)createScreenlet:(CGRect)frame asset:(Asset * _Nonnull)asset themeName:(NSString * _Nullable)themeName __attribute__((warn_unused_result));
	[Static]
	[Export ("createScreenlet:asset:themeName:")]
	[return: NullAllowed]
	BaseScreenlet CreateScreenlet (CGRect frame, Asset asset, [NullAllowed] string themeName);
}

// @interface BaseScreenlet : UIView
[BaseType (typeof(UIView))]
interface BaseScreenlet
{
	// @property (readonly, copy, nonatomic, class) NSString * _Nonnull DefaultAction;
	[Static]
	[Export ("DefaultAction")]
	string DefaultAction { get; }

	// @property (readonly, copy, nonatomic, class) NSString * _Nonnull DefaultThemeName;
	[Static]
	[Export ("DefaultThemeName")]
	string DefaultThemeName { get; }

	[Wrap ("WeakDelegate")]
	[NullAllowed]
	BaseScreenletDelegate Delegate { get; set; }

	// @property (nonatomic, weak) id<BaseScreenletDelegate> _Nullable delegate __attribute__((iboutlet));
	[NullAllowed, Export ("delegate", ArgumentSemantic.Weak)]
	NSObject WeakDelegate { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable themeName;
	[NullAllowed, Export ("themeName")]
	string ThemeName { get; set; }

	// @property (nonatomic, weak) BaseScreenletView * _Nullable screenletView;
	[NullAllowed, Export ("screenletView", ArgumentSemantic.Weak)]
	BaseScreenletView ScreenletView { get; set; }

	// @property (nonatomic, weak) UIViewController * _Nullable presentingViewController;
	[NullAllowed, Export ("presentingViewController", ArgumentSemantic.Weak)]
	UIViewController PresentingViewController { get; set; }

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:themeName:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame, [NullAllowed] string themeName);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);

	// -(void)awakeFromNib __attribute__((objc_requires_super));
	[Export ("awakeFromNib")]
	[RequiresSuper]
	void AwakeFromNib ();

	// -(BOOL)becomeFirstResponder __attribute__((warn_unused_result));
	[Export ("becomeFirstResponder")]
	[Verify (MethodToProperty)]
	bool BecomeFirstResponder { get; }

	// -(void)didMoveToWindow;
	[Export ("didMoveToWindow")]
	void DidMoveToWindow ();

	// -(void)prepareForInterfaceBuilder;
	[Export ("prepareForInterfaceBuilder")]
	void PrepareForInterfaceBuilder ();

	// -(void)onCreated;
	[Export ("onCreated")]
	void OnCreated ();

	// -(void)onPreCreate;
	[Export ("onPreCreate")]
	void OnPreCreate ();

	// -(void)onHide;
	[Export ("onHide")]
	void OnHide ();

	// -(void)onShow;
	[Export ("onShow")]
	void OnShow ();

	// -(BOOL)performActionWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender;
	[Export ("performActionWithName:sender:")]
	bool PerformActionWithName (string name, [NullAllowed] NSObject sender);

	// -(BOOL)performDefaultAction;
	[Export ("performDefaultAction")]
	[Verify (MethodToProperty)]
	bool PerformDefaultAction { get; }

	// -(BOOL)onActionWithName:(NSString * _Nonnull)name interactor:(Interactor * _Nonnull)interactor sender:(id _Nullable)sender;
	[Export ("onActionWithName:interactor:sender:")]
	bool OnActionWithName (string name, Interactor interactor, [NullAllowed] NSObject sender);

	// -(BOOL)isActionRunning:(NSString * _Nonnull)name __attribute__((warn_unused_result));
	[Export ("isActionRunning:")]
	bool IsActionRunning (string name);

	// -(void)cancelInteractorsForAction:(NSString * _Nonnull)name;
	[Export ("cancelInteractorsForAction:")]
	void CancelInteractorsForAction (string name);

	// -(Interactor * _Nullable)createInteractorWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender __attribute__((warn_unused_result));
	[Export ("createInteractorWithName:sender:")]
	[return: NullAllowed]
	Interactor CreateInteractorWithName (string name, [NullAllowed] NSObject sender);

	// -(void)endInteractor:(Interactor * _Nonnull)interactor error:(NSError * _Nullable)error;
	[Export ("endInteractor:error:")]
	void EndInteractor (Interactor interactor, [NullAllowed] NSError error);

	// -(void)onStartInteraction;
	[Export ("onStartInteraction")]
	void OnStartInteraction ();

	// -(void)onFinishInteraction:(id _Nullable)result error:(NSError * _Nullable)error;
	[Export ("onFinishInteraction:error:")]
	void OnFinishInteraction ([NullAllowed] NSObject result, [NullAllowed] NSError error);

	// -(void)showHUDWithMessage:(NSString * _Nullable)message forInteractor:(Interactor * _Nonnull)interactor;
	[Export ("showHUDWithMessage:forInteractor:")]
	void ShowHUDWithMessage ([NullAllowed] string message, Interactor interactor);

	// -(void)hideHUDWithMessage:(NSString * _Nullable)message forInteractor:(Interactor * _Nonnull)interactor withError:(NSError * _Nullable)error;
	[Export ("hideHUDWithMessage:forInteractor:withError:")]
	void HideHUDWithMessage ([NullAllowed] string message, Interactor interactor, [NullAllowed] NSError error);

	// -(void)refreshTranslations;
	[Export ("refreshTranslations")]
	void RefreshTranslations ();
}

// @interface AssetDisplayScreenlet : BaseScreenlet
[BaseType (typeof(BaseScreenlet))]
interface AssetDisplayScreenlet
{
	// @property (nonatomic) int64_t assetEntryId;
	[Export ("assetEntryId")]
	long AssetEntryId { get; set; }

	// @property (copy, nonatomic) NSString * _Nonnull className;
	[Export ("className")]
	string ClassName { get; set; }

	// @property (nonatomic) int64_t classPK;
	[Export ("classPK")]
	long ClassPK { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable portletItemName;
	[NullAllowed, Export ("portletItemName")]
	string PortletItemName { get; set; }

	// @property (nonatomic) BOOL autoLoad;
	[Export ("autoLoad")]
	bool AutoLoad { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable offlinePolicy;
	[NullAllowed, Export ("offlinePolicy")]
	string OfflinePolicy { get; set; }

	[Wrap ("WeakAssetDisplayDelegate")]
	[NullAllowed]
	AssetDisplayScreenletDelegate AssetDisplayDelegate { get; }

	// @property (readonly, nonatomic, strong) id<AssetDisplayScreenletDelegate> _Nullable assetDisplayDelegate;
	[NullAllowed, Export ("assetDisplayDelegate", ArgumentSemantic.Strong)]
	NSObject WeakAssetDisplayDelegate { get; }

	// @property (readonly, nonatomic, strong) id<AssetDisplayViewModel> _Nullable assetDisplayViewModel;
	[NullAllowed, Export ("assetDisplayViewModel", ArgumentSemantic.Strong)]
	AssetDisplayViewModel AssetDisplayViewModel { get; }

	// @property (nonatomic, strong) Asset * _Nullable assetEntry;
	[NullAllowed, Export ("assetEntry", ArgumentSemantic.Strong)]
	Asset AssetEntry { get; set; }

	// -(void)onShow;
	[Export ("onShow")]
	void OnShow ();

	// -(Interactor * _Nullable)createInteractorWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender __attribute__((warn_unused_result));
	[Export ("createInteractorWithName:sender:")]
	[return: NullAllowed]
	Interactor CreateInteractorWithName (string name, [NullAllowed] NSObject sender);

	// -(BOOL)load;
	[Export ("load")]
	[Verify (MethodToProperty)]
	bool Load { get; }

	// -(UIView * _Nullable)createInnerScreenlet:(Asset * _Nonnull)asset __attribute__((warn_unused_result));
	[Export ("createInnerScreenlet:")]
	[return: NullAllowed]
	UIView CreateInnerScreenlet (Asset asset);

	// -(void)configureInnerScreenlet:(BaseScreenlet * _Nonnull)innerScreenlet asset:(Asset * _Nonnull)asset;
	[Export ("configureInnerScreenlet:asset:")]
	void ConfigureInnerScreenlet (BaseScreenlet innerScreenlet, Asset asset);

	// -(void)removeInnerScreenlet;
	[Export ("removeInnerScreenlet")]
	void RemoveInnerScreenlet ();

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:themeName:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame, [NullAllowed] string themeName);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @protocol BaseScreenletDelegate <NSObject>
[Protocol, Model]
[BaseType (typeof(NSObject))]
interface BaseScreenletDelegate
{
	// @optional -(Interactor * _Nullable)screenlet:(BaseScreenlet * _Nonnull)screenlet customInteractorForAction:(NSString * _Nonnull)customInteractorForAction withSender:(id _Nullable)withSender __attribute__((warn_unused_result));
	[Export ("screenlet:customInteractorForAction:withSender:")]
	[return: NullAllowed]
	Interactor CustomInteractorForAction (BaseScreenlet screenlet, string customInteractorForAction, [NullAllowed] NSObject withSender);
}

// @protocol AssetDisplayScreenletDelegate <BaseScreenletDelegate>
[Protocol, Model]
interface AssetDisplayScreenletDelegate : IBaseScreenletDelegate
{
	// @optional -(void)screenlet:(AssetDisplayScreenlet * _Nonnull)screenlet onAssetResponse:(Asset * _Nonnull)asset;
	[Export ("screenlet:onAssetResponse:")]
	void Screenlet (AssetDisplayScreenlet screenlet, Asset asset);

	// @optional -(void)screenlet:(AssetDisplayScreenlet * _Nonnull)screenlet onAssetError:(NSError * _Nonnull)error;
	[Export ("screenlet:onAssetError:")]
	void Screenlet (AssetDisplayScreenlet screenlet, NSError error);

	// @optional -(void)screenlet:(AssetDisplayScreenlet * _Nonnull)screenlet onConfigureScreenlet:(BaseScreenlet * _Nullable)childScreenlet onAsset:(Asset * _Nonnull)asset;
	[Export ("screenlet:onConfigureScreenlet:onAsset:")]
	void Screenlet (AssetDisplayScreenlet screenlet, [NullAllowed] BaseScreenlet childScreenlet, Asset asset);

	// @optional -(UIView * _Nullable)screenlet:(AssetDisplayScreenlet * _Nonnull)screenlet onAsset:(Asset * _Nonnull)asset __attribute__((warn_unused_result));
	[Export ("screenlet:onAsset:")]
	[return: NullAllowed]
	UIView Screenlet (AssetDisplayScreenlet screenlet, Asset asset);
}

// @protocol AssetDisplayViewModel
[Protocol, Model]
interface AssetDisplayViewModel
{
	// @required @property (nonatomic, strong) Asset * _Nullable asset;
	[Abstract]
	[NullAllowed, Export ("asset", ArgumentSemantic.Strong)]
	Asset Asset { get; set; }

	// @required @property (nonatomic, strong) UIView * _Nullable innerScreenlet;
	[Abstract]
	[NullAllowed, Export ("innerScreenlet", ArgumentSemantic.Strong)]
	UIView InnerScreenlet { get; set; }
}

// @interface BaseScreenletView : UIView <UITextFieldDelegate>
[BaseType (typeof(UIView))]
interface BaseScreenletView : IUITextFieldDelegate
{
	// @property (nonatomic, weak) BaseScreenlet * _Nullable screenlet;
	[NullAllowed, Export ("screenlet", ArgumentSemantic.Weak)]
	BaseScreenlet Screenlet { get; set; }

	// @property (nonatomic, weak) UIViewController * _Nullable presentingViewController;
	[NullAllowed, Export ("presentingViewController", ArgumentSemantic.Weak)]
	UIViewController PresentingViewController { get; set; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull NoProgressMessage;
	[Export ("NoProgressMessage")]
	string NoProgressMessage { get; }

	// @property (nonatomic) BOOL editable;
	[Export ("editable")]
	bool Editable { get; set; }

	// @property (copy, nonatomic) NSString * _Nonnull themeName;
	[Export ("themeName")]
	string ThemeName { get; set; }

	// -(void)awakeFromNib __attribute__((objc_requires_super));
	[Export ("awakeFromNib")]
	[RequiresSuper]
	void AwakeFromNib ();

	// -(BOOL)becomeFirstResponder __attribute__((warn_unused_result));
	[Export ("becomeFirstResponder")]
	[Verify (MethodToProperty)]
	bool BecomeFirstResponder { get; }

	// -(void)didMoveToWindow;
	[Export ("didMoveToWindow")]
	void DidMoveToWindow ();

	// -(BOOL)textFieldShouldReturn:(UITextField * _Nonnull)textField __attribute__((warn_unused_result));
	[Export ("textFieldShouldReturn:")]
	bool TextFieldShouldReturn (UITextField textField);

	// -(void)onCreated;
	[Export ("onCreated")]
	void OnCreated ();

	// -(void)onDestroy;
	[Export ("onDestroy")]
	void OnDestroy ();

	// -(void)onPreCreate;
	[Export ("onPreCreate")]
	void OnPreCreate ();

	// -(void)onHide;
	[Export ("onHide")]
	void OnHide ();

	// -(void)onShow;
	[Export ("onShow")]
	void OnShow ();

	// -(BOOL)onSetUserActionForControl:(UIControl * _Nonnull)control __attribute__((warn_unused_result));
	[Export ("onSetUserActionForControl:")]
	bool OnSetUserActionForControl (UIControl control);

	// -(BOOL)onPreActionWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender __attribute__((warn_unused_result));
	[Export ("onPreActionWithName:sender:")]
	bool OnPreActionWithName (string name, [NullAllowed] NSObject sender);

	// -(BOOL)onSetDefaultDelegate:(id _Nonnull)delegate view:(UIView * _Nonnull)view __attribute__((warn_unused_result));
	[Export ("onSetDefaultDelegate:view:")]
	bool OnSetDefaultDelegate (NSObject @delegate, UIView view);

	// -(void)onSetTranslations;
	[Export ("onSetTranslations")]
	void OnSetTranslations ();

	// -(void)onStartInteraction;
	[Export ("onStartInteraction")]
	void OnStartInteraction ();

	// -(void)onFinishInteraction:(id _Nullable)result error:(NSError * _Nullable)error;
	[Export ("onFinishInteraction:error:")]
	void OnFinishInteraction ([NullAllowed] NSObject result, [NullAllowed] NSError error);

	// -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
	[Export ("createProgressPresenter")]
	[Verify (MethodToProperty)]
	ProgressPresenter CreateProgressPresenter { get; }

	// -(NSString * _Nullable)progressMessageForAction:(NSString * _Nonnull)actionName messageType:(enum ProgressMessageType)messageType __attribute__((warn_unused_result));
	[Export ("progressMessageForAction:messageType:")]
	[return: NullAllowed]
	string ProgressMessageForAction (string actionName, ProgressMessageType messageType);

	// -(void)userActionWithSender:(id _Nullable)sender;
	[Export ("userActionWithSender:")]
	void UserActionWithSender ([NullAllowed] NSObject sender);

	// -(void)userActionWithName:(NSString * _Nullable)name;
	[Export ("userActionWithName:")]
	void UserActionWithName ([NullAllowed] string name);

	// -(void)userActionWithName:(NSString * _Nullable)name sender:(id _Nullable)sender;
	[Export ("userActionWithName:sender:")]
	void UserActionWithName ([NullAllowed] string name, [NullAllowed] NSObject sender);

	// -(void)changeEditable:(BOOL)editable;
	[Export ("changeEditable:")]
	void ChangeEditable (bool editable);

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface AssetDisplayView_default : BaseScreenletView <AssetDisplayViewModel>
[BaseType (typeof(BaseScreenletView))]
interface AssetDisplayView_default : IAssetDisplayViewModel
{
	// @property (nonatomic, strong) Asset * _Nullable asset;
	[NullAllowed, Export ("asset", ArgumentSemantic.Strong)]
	Asset Asset { get; set; }

	// @property (nonatomic, strong) UIView * _Nullable innerScreenlet;
	[NullAllowed, Export ("innerScreenlet", ArgumentSemantic.Strong)]
	UIView InnerScreenlet { get; set; }

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface ServerConnector : NSOperation
[BaseType (typeof(NSOperation))]
interface ServerConnector
{
	// @property (nonatomic, strong) NSError * _Nullable lastError;
	[NullAllowed, Export ("lastError", ArgumentSemantic.Strong)]
	NSError LastError { get; set; }

	// -(void)main;
	[Export ("main")]
	void Main ();

	// -(ValidationError * _Nullable)validateAndEnqueue:(void (^ _Nullable)(ServerConnector * _Nonnull))onComplete;
	[Export ("validateAndEnqueue:")]
	[return: NullAllowed]
	ValidationError ValidateAndEnqueue ([NullAllowed] Action<ServerConnector> onComplete);

	// -(void)enqueue:(void (^ _Nullable)(ServerConnector * _Nonnull))onComplete;
	[Export ("enqueue:")]
	void Enqueue ([NullAllowed] Action<ServerConnector> onComplete);

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }

	// -(BOOL)preRun __attribute__((warn_unused_result));
	[Export ("preRun")]
	[Verify (MethodToProperty)]
	bool PreRun { get; }

	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(void)postRun;
	[Export ("postRun")]
	void PostRun ();

	// -(LRSession * _Nullable)createSession __attribute__((warn_unused_result));
	[NullAllowed, Export ("createSession")]
	[Verify (MethodToProperty)]
	LRSession CreateSession { get; }

	// -(void)callOnComplete;
	[Export ("callOnComplete")]
	void CallOnComplete ();
}

// @interface PaginationLiferayConnector : ServerConnector
[BaseType (typeof(ServerConnector))]
[DisableDefaultCtor]
interface PaginationLiferayConnector
{
	// @property (readonly, nonatomic) NSInteger startRow;
	[Export ("startRow")]
	nint StartRow { get; }

	// @property (readonly, nonatomic) NSInteger endRow;
	[Export ("endRow")]
	nint EndRow { get; }

	// @property (readonly, nonatomic) BOOL computeRowCount;
	[Export ("computeRowCount")]
	bool ComputeRowCount { get; }

	// @property (copy, nonatomic) NSString * _Nullable obcClassName;
	[NullAllowed, Export ("obcClassName")]
	string ObcClassName { get; set; }

	// @property (copy, nonatomic) NSArray<NSDictionary<NSString *,id> *> * _Nullable resultPageContent;
	[NullAllowed, Export ("resultPageContent", ArgumentSemantic.Copy)]
	NSDictionary<NSString, NSObject>[] ResultPageContent { get; set; }

	// -(instancetype _Nonnull)initWithStartRow:(NSInteger)startRow endRow:(NSInteger)endRow computeRowCount:(BOOL)computeRowCount __attribute__((objc_designated_initializer));
	[Export ("initWithStartRow:endRow:computeRowCount:")]
	[DesignatedInitializer]
	IntPtr Constructor (nint startRow, nint endRow, bool computeRowCount);

	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(void)doAddPageRowsServiceCallWithSession:(LRBatchSession * _Nonnull)session startRow:(NSInteger)startRow endRow:(NSInteger)endRow obc:(LRJSONObjectWrapper * _Nullable)obc;
	[Export ("doAddPageRowsServiceCallWithSession:startRow:endRow:obc:")]
	void DoAddPageRowsServiceCallWithSession (LRBatchSession session, nint startRow, nint endRow, [NullAllowed] LRJSONObjectWrapper obc);

	// -(void)doAddRowCountServiceCallWithSession:(LRBatchSession * _Nonnull)session;
	[Export ("doAddRowCountServiceCallWithSession:")]
	void DoAddRowCountServiceCallWithSession (LRBatchSession session);
}

// @interface AssetListPageLiferayConnector : PaginationLiferayConnector
[BaseType (typeof(PaginationLiferayConnector))]
interface AssetListPageLiferayConnector
{
	// @property (copy, nonatomic) NSString * _Nullable portletItemName;
	[NullAllowed, Export ("portletItemName")]
	string PortletItemName { get; set; }

	// @property (copy, nonatomic) NSDictionary<NSString *,id> * _Nullable customEntryQuery;
	[NullAllowed, Export ("customEntryQuery", ArgumentSemantic.Copy)]
	NSDictionary<NSString, NSObject> CustomEntryQuery { get; set; }

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }

	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(void)doAddPageRowsServiceCallWithSession:(LRBatchSession * _Nonnull)session startRow:(NSInteger)startRow endRow:(NSInteger)endRow obc:(LRJSONObjectWrapper * _Nullable)obc;
	[Export ("doAddPageRowsServiceCallWithSession:startRow:endRow:obc:")]
	void DoAddPageRowsServiceCallWithSession (LRBatchSession session, nint startRow, nint endRow, [NullAllowed] LRJSONObjectWrapper obc);

	// -(void)doAddRowCountServiceCallWithSession:(LRBatchSession * _Nonnull)session;
	[Export ("doAddRowCountServiceCallWithSession:")]
	void DoAddRowCountServiceCallWithSession (LRBatchSession session);

	// -(void)doGetPageRowsWithSession:(LRBatchSession * _Nonnull)session entryQuery:(LRJSONObjectWrapper * _Nonnull)entryQuery obc:(LRJSONObjectWrapper * _Nullable)obc;
	[Export ("doGetPageRowsWithSession:entryQuery:obc:")]
	void DoGetPageRowsWithSession (LRBatchSession session, LRJSONObjectWrapper entryQuery, [NullAllowed] LRJSONObjectWrapper obc);

	// -(void)doGetRowCountWithSession:(LRBatchSession * _Nonnull)session entryQuery:(LRJSONObjectWrapper * _Nonnull)entryQuery;
	[Export ("doGetRowCountWithSession:entryQuery:")]
	void DoGetRowCountWithSession (LRBatchSession session, LRJSONObjectWrapper entryQuery);

	// -(NSDictionary<NSString *,id> * _Nonnull)configureEntryQuery __attribute__((warn_unused_result));
	[Export ("configureEntryQuery")]
	[Verify (MethodToProperty)]
	NSDictionary<NSString, NSObject> ConfigureEntryQuery { get; }

	// -(instancetype _Nonnull)initWithStartRow:(NSInteger)startRow endRow:(NSInteger)endRow computeRowCount:(BOOL)computeRowCount __attribute__((objc_designated_initializer));
	[Export ("initWithStartRow:endRow:computeRowCount:")]
	[DesignatedInitializer]
	IntPtr Constructor (nint startRow, nint endRow, bool computeRowCount);
}

// @interface Interactor : NSObject
[BaseType (typeof(NSObject))]
interface Interactor
{
	// @property (copy, nonatomic) NSString * _Nullable actionName;
	[NullAllowed, Export ("actionName")]
	string ActionName { get; set; }

	// @property (copy, nonatomic) void (^ _Nullable)(void) onSuccess;
	[NullAllowed, Export ("onSuccess", ArgumentSemantic.Copy)]
	Action OnSuccess { get; set; }

	// @property (copy, nonatomic) void (^ _Nullable)(NSError * _Nonnull) onFailure;
	[NullAllowed, Export ("onFailure", ArgumentSemantic.Copy)]
	Action<NSError> OnFailure { get; set; }

	// @property (nonatomic, strong) NSError * _Nullable lastError;
	[NullAllowed, Export ("lastError", ArgumentSemantic.Strong)]
	NSError LastError { get; set; }

	// @property (readonly, nonatomic, strong) BaseScreenlet * _Nullable screenlet;
	[NullAllowed, Export ("screenlet", ArgumentSemantic.Strong)]
	BaseScreenlet Screenlet { get; }

	// -(instancetype _Nonnull)initWithScreenlet:(BaseScreenlet * _Nullable)screenlet __attribute__((objc_designated_initializer));
	[Export ("initWithScreenlet:")]
	[DesignatedInitializer]
	IntPtr Constructor ([NullAllowed] BaseScreenlet screenlet);

	// -(void)callOnSuccess;
	[Export ("callOnSuccess")]
	void CallOnSuccess ();

	// -(void)callOnFailure:(NSError * _Nonnull)error;
	[Export ("callOnFailure:")]
	void CallOnFailure (NSError error);

	// -(BOOL)start __attribute__((warn_unused_result));
	[Export ("start")]
	[Verify (MethodToProperty)]
	bool Start { get; }

	// -(void)cancel;
	[Export ("cancel")]
	void Cancel ();

	// -(id _Nullable)interactionResult __attribute__((warn_unused_result));
	[NullAllowed, Export ("interactionResult")]
	[Verify (MethodToProperty)]
	NSObject InteractionResult { get; }
}

// @interface ServerConnectorInteractor : Interactor
[BaseType (typeof(Interactor))]
interface ServerConnectorInteractor
{
	// @property (nonatomic, strong) ServerConnector * _Nullable currentConnector;
	[NullAllowed, Export ("currentConnector", ArgumentSemantic.Strong)]
	ServerConnector CurrentConnector { get; set; }

	// -(BOOL)start __attribute__((warn_unused_result));
	[Export ("start")]
	[Verify (MethodToProperty)]
	bool Start { get; }

	// -(void)cancel;
	[Export ("cancel")]
	void Cancel ();

	// -(void)callOnSuccess;
	[Export ("callOnSuccess")]
	void CallOnSuccess ();

	// -(void)callOnFailure:(NSError * _Nonnull)error;
	[Export ("callOnFailure:")]
	void CallOnFailure (NSError error);

	// -(ServerConnector * _Nullable)createConnector __attribute__((warn_unused_result));
	[NullAllowed, Export ("createConnector")]
	[Verify (MethodToProperty)]
	ServerConnector CreateConnector { get; }

	// -(void)completedConnector:(ServerConnector * _Nonnull)c;
	[Export ("completedConnector:")]
	void CompletedConnector (ServerConnector c);

	// -(void)readFromCache:(ServerConnector * _Nonnull)c result:(void (^ _Nonnull)(id _Nullable))result;
	[Export ("readFromCache:result:")]
	void ReadFromCache (ServerConnector c, Action<NSObject> result);

	// -(void)writeToCache:(ServerConnector * _Nonnull)c;
	[Export ("writeToCache:")]
	void WriteToCache (ServerConnector c);

	// -(void)defaultStrategyRemote:(ServerConnector * _Nonnull)connector whenSuccess:(void (^ _Nonnull)(void))whenSuccess whenFailure:(void (^ _Nonnull)(NSError * _Nonnull))whenFailure;
	[Export ("defaultStrategyRemote:whenSuccess:whenFailure:")]
	void DefaultStrategyRemote (ServerConnector connector, Action whenSuccess, Action<NSError> whenFailure);

	// -(void)defaultStrategyReadFromCache:(ServerConnector * _Nonnull)connector whenSuccess:(void (^ _Nonnull)(void))whenSuccess whenFailure:(void (^ _Nonnull)(NSError * _Nonnull))whenFailure;
	[Export ("defaultStrategyReadFromCache:whenSuccess:whenFailure:")]
	void DefaultStrategyReadFromCache (ServerConnector connector, Action whenSuccess, Action<NSError> whenFailure);

	// -(void)defaultStrategyWriteToCache:(ServerConnector * _Nonnull)connector whenSuccess:(void (^ _Nonnull)(void))whenSuccess whenFailure:(void (^ _Nonnull)(NSError * _Nonnull))whenFailure;
	[Export ("defaultStrategyWriteToCache:whenSuccess:whenFailure:")]
	void DefaultStrategyWriteToCache (ServerConnector connector, Action whenSuccess, Action<NSError> whenFailure);

	// -(void (^ _Nonnull)(ServerConnector * _Nonnull, void (^ _Nonnull)(void), void (^ _Nonnull)(NSError * _Nonnull)))createStrategyWhenFails:(void (^ _Nonnull)(ServerConnector * _Nonnull, void (^ _Nonnull)(void), void (^ _Nonnull)(NSError * _Nonnull)))mainStrategy then:(void (^ _Nonnull)(ServerConnector * _Nonnull, void (^ _Nonnull)(void), void (^ _Nonnull)(NSError * _Nonnull)))onFailStrategy __attribute__((warn_unused_result));
	[Export ("createStrategyWhenFails:then:")]
	Action<ServerConnector, Action, Action<NSError>> CreateStrategyWhenFails (Action<ServerConnector, Action, Action<NSError>> mainStrategy, Action<ServerConnector, Action, Action<NSError>> onFailStrategy);

	// -(void (^ _Nonnull)(ServerConnector * _Nonnull, void (^ _Nonnull)(void), void (^ _Nonnull)(NSError * _Nonnull)))createStrategyWhenSucceeds:(void (^ _Nonnull)(ServerConnector * _Nonnull, void (^ _Nonnull)(void), void (^ _Nonnull)(NSError * _Nonnull)))mainStrategy then:(void (^ _Nonnull)(ServerConnector * _Nonnull, void (^ _Nonnull)(void), void (^ _Nonnull)(NSError * _Nonnull)))onSuccessStrategy __attribute__((warn_unused_result));
	[Export ("createStrategyWhenSucceeds:then:")]
	Action<ServerConnector, Action, Action<NSError>> CreateStrategyWhenSucceeds (Action<ServerConnector, Action, Action<NSError>> mainStrategy, Action<ServerConnector, Action, Action<NSError>> onSuccessStrategy);

	// -(void (^ _Nonnull)(ServerConnector * _Nonnull, void (^ _Nonnull)(void), void (^ _Nonnull)(NSError * _Nonnull)))createStrategy:(void (^ _Nonnull)(ServerConnector * _Nonnull, void (^ _Nonnull)(void), void (^ _Nonnull)(NSError * _Nonnull)))firstStrategy andThen:(void (^ _Nonnull)(ServerConnector * _Nonnull, void (^ _Nonnull)(void), void (^ _Nonnull)(NSError * _Nonnull)))secondStrategy __attribute__((warn_unused_result));
	[Export ("createStrategy:andThen:")]
	Action<ServerConnector, Action, Action<NSError>> CreateStrategy (Action<ServerConnector, Action, Action<NSError>> firstStrategy, Action<ServerConnector, Action, Action<NSError>> secondStrategy);

	// -(instancetype _Nonnull)initWithScreenlet:(BaseScreenlet * _Nullable)screenlet __attribute__((objc_designated_initializer));
	[Export ("initWithScreenlet:")]
	[DesignatedInitializer]
	IntPtr Constructor ([NullAllowed] BaseScreenlet screenlet);
}

// @interface ServerReadConnectorInteractor : ServerConnectorInteractor
[BaseType (typeof(ServerConnectorInteractor))]
interface ServerReadConnectorInteractor
{
	// -(instancetype _Nonnull)initWithScreenlet:(BaseScreenlet * _Nullable)screenlet __attribute__((objc_designated_initializer));
	[Export ("initWithScreenlet:")]
	[DesignatedInitializer]
	IntPtr Constructor ([NullAllowed] BaseScreenlet screenlet);
}

// @interface BaseListPageLoadInteractor : ServerReadConnectorInteractor
[BaseType (typeof(ServerReadConnectorInteractor))]
interface BaseListPageLoadInteractor
{
	// @property (readonly, nonatomic) NSInteger page;
	[Export ("page")]
	nint Page { get; }

	// @property (readonly, nonatomic) BOOL computeRowCount;
	[Export ("computeRowCount")]
	bool ComputeRowCount { get; }

	// @property (copy, nonatomic) NSString * _Nullable obcClassName;
	[NullAllowed, Export ("obcClassName")]
	string ObcClassName { get; set; }

	// @property (copy, nonatomic) NSDictionary<NSString *,NSArray *> * _Nullable resultPageContent;
	[NullAllowed, Export ("resultPageContent", ArgumentSemantic.Copy)]
	NSDictionary<NSString, NSArray> ResultPageContent { get; set; }

	// @property (copy, nonatomic) NSArray<NSString *> * _Nullable sections;
	[NullAllowed, Export ("sections", ArgumentSemantic.Copy)]
	string[] Sections { get; set; }

	// -(instancetype _Nonnull)initWithScreenlet:(BaseListScreenlet * _Nonnull)screenlet page:(NSInteger)page computeRowCount:(BOOL)computeRowCount __attribute__((objc_designated_initializer));
	[Export ("initWithScreenlet:page:computeRowCount:")]
	[DesignatedInitializer]
	IntPtr Constructor (BaseListScreenlet screenlet, nint page, bool computeRowCount);

	// -(PaginationLiferayConnector * _Nonnull)createConnector __attribute__((warn_unused_result));
	[Export ("createConnector")]
	[Verify (MethodToProperty)]
	PaginationLiferayConnector CreateConnector { get; }

	// -(void)completedConnector:(ServerConnector * _Nonnull)c;
	[Export ("completedConnector:")]
	void CompletedConnector (ServerConnector c);

	// -(PaginationLiferayConnector * _Nonnull)createListPageConnector __attribute__((warn_unused_result));
	[Export ("createListPageConnector")]
	[Verify (MethodToProperty)]
	PaginationLiferayConnector CreateListPageConnector { get; }

	// -(id _Nonnull)convertResult:(NSDictionary<NSString *,id> * _Nonnull)serverResult __attribute__((warn_unused_result));
	[Export ("convertResult:")]
	NSObject ConvertResult (NSDictionary<NSString, NSObject> serverResult);

	// -(NSString * _Nullable)sectionForRowObject:(id _Nonnull)object __attribute__((warn_unused_result));
	[Export ("sectionForRowObject:")]
	[return: NullAllowed]
	string SectionForRowObject (NSObject @object);

	// -(NSString * _Nonnull)cacheKey:(PaginationLiferayConnector * _Nonnull)c __attribute__((warn_unused_result));
	[Export ("cacheKey:")]
	string CacheKey (PaginationLiferayConnector c);

	// -(void)readFromCache:(ServerConnector * _Nonnull)c result:(void (^ _Nonnull)(id _Nullable))result;
	[Export ("readFromCache:result:")]
	void ReadFromCache (ServerConnector c, Action<NSObject> result);

	// -(void)writeToCache:(ServerConnector * _Nonnull)c;
	[Export ("writeToCache:")]
	void WriteToCache (ServerConnector c);
}

// @interface AssetListPageLoadInteractor : BaseListPageLoadInteractor
[BaseType (typeof(BaseListPageLoadInteractor))]
interface AssetListPageLoadInteractor
{
	// @property (copy, nonatomic) NSDictionary<NSString *,id> * _Nullable customEntryQuery;
	[NullAllowed, Export ("customEntryQuery", ArgumentSemantic.Copy)]
	NSDictionary<NSString, NSObject> CustomEntryQuery { get; set; }

	// -(PaginationLiferayConnector * _Nonnull)createListPageConnector __attribute__((warn_unused_result));
	[Export ("createListPageConnector")]
	[Verify (MethodToProperty)]
	PaginationLiferayConnector CreateListPageConnector { get; }

	// -(id _Nonnull)convertResult:(NSDictionary<NSString *,id> * _Nonnull)serverResult __attribute__((warn_unused_result));
	[Export ("convertResult:")]
	NSObject ConvertResult (NSDictionary<NSString, NSObject> serverResult);

	// -(NSString * _Nonnull)cacheKey:(PaginationLiferayConnector * _Nonnull)c __attribute__((warn_unused_result));
	[Export ("cacheKey:")]
	string CacheKey (PaginationLiferayConnector c);
}

// @interface BaseListScreenlet : BaseScreenlet
[BaseType (typeof(BaseScreenlet))]
interface BaseListScreenlet
{
	// @property (readonly, copy, nonatomic, class) NSString * _Nonnull LoadInitialPageAction;
	[Static]
	[Export ("LoadInitialPageAction")]
	string LoadInitialPageAction { get; }

	// @property (readonly, copy, nonatomic, class) NSString * _Nonnull LoadPageAction;
	[Static]
	[Export ("LoadPageAction")]
	string LoadPageAction { get; }

	// @property (nonatomic) BOOL autoLoad;
	[Export ("autoLoad")]
	bool AutoLoad { get; set; }

	// @property (nonatomic) BOOL refreshControl;
	[Export ("refreshControl")]
	bool RefreshControl { get; set; }

	// @property (nonatomic) NSInteger firstPageSize;
	[Export ("firstPageSize")]
	nint FirstPageSize { get; set; }

	// @property (nonatomic) NSInteger pageSize;
	[Export ("pageSize")]
	nint PageSize { get; set; }

	// @property (copy, nonatomic) NSString * _Nonnull obcClassName;
	[Export ("obcClassName")]
	string ObcClassName { get; set; }

	// @property (readonly, nonatomic, strong) BaseListView * _Nonnull baseListView;
	[Export ("baseListView", ArgumentSemantic.Strong)]
	BaseListView BaseListView { get; }

	// -(void)onCreated;
	[Export ("onCreated")]
	void OnCreated ();

	// -(void)onShow;
	[Export ("onShow")]
	void OnShow ();

	// -(Interactor * _Nullable)createInteractorWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender __attribute__((warn_unused_result));
	[Export ("createInteractorWithName:sender:")]
	[return: NullAllowed]
	Interactor CreateInteractorWithName (string name, [NullAllowed] NSObject sender);

	// -(BOOL)onActionWithName:(NSString * _Nonnull)name interactor:(Interactor * _Nonnull)interactor sender:(id _Nullable)sender __attribute__((warn_unused_result));
	[Export ("onActionWithName:interactor:sender:")]
	bool OnActionWithName (string name, Interactor interactor, [NullAllowed] NSObject sender);

	// -(BOOL)loadList;
	[Export ("loadList")]
	[Verify (MethodToProperty)]
	bool LoadList { get; }

	// -(void)loadPageForRow:(NSInteger)row;
	[Export ("loadPageForRow:")]
	void LoadPageForRow (nint row);

	// -(NSInteger)pageFromRow:(NSInteger)row __attribute__((warn_unused_result));
	[Export ("pageFromRow:")]
	nint PageFromRow (nint row);

	// -(NSInteger)firstRowForPage:(NSInteger)page __attribute__((warn_unused_result));
	[Export ("firstRowForPage:")]
	nint FirstRowForPage (nint page);

	// -(BaseListPageLoadInteractor * _Nonnull)createPageLoadInteractorWithPage:(NSInteger)page computeRowCount:(BOOL)computeRowCount __attribute__((warn_unused_result));
	[Export ("createPageLoadInteractorWithPage:computeRowCount:")]
	BaseListPageLoadInteractor CreatePageLoadInteractorWithPage (nint page, bool computeRowCount);

	// -(void)onLoadPageErrorWithPage:(NSInteger)page error:(NSError * _Nonnull)error;
	[Export ("onLoadPageErrorWithPage:error:")]
	void OnLoadPageErrorWithPage (nint page, NSError error);

	// -(void)onLoadPageResultWithPage:(NSInteger)page rows:(NSArray * _Nonnull)rows rowCount:(NSInteger)rowCount;
	[Export ("onLoadPageResultWithPage:rows:rowCount:")]
	[Verify (StronglyTypedNSArray)]
	void OnLoadPageResultWithPage (nint page, NSObject[] rows, nint rowCount);

	// -(void)onSelectedRow:(id _Nonnull)row;
	[Export ("onSelectedRow:")]
	void OnSelectedRow (NSObject row);

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:themeName:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame, [NullAllowed] string themeName);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface AssetListScreenlet : BaseListScreenlet
[BaseType (typeof(BaseListScreenlet))]
interface AssetListScreenlet
{
	// @property (nonatomic) int64_t groupId;
	[Export ("groupId")]
	long GroupId { get; set; }

	// @property (nonatomic) int64_t classNameId;
	[Export ("classNameId")]
	long ClassNameId { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable portletItemName;
	[NullAllowed, Export ("portletItemName")]
	string PortletItemName { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable offlinePolicy;
	[NullAllowed, Export ("offlinePolicy")]
	string OfflinePolicy { get; set; }

	[Wrap ("WeakAssetListDelegate")]
	[NullAllowed]
	AssetListScreenletDelegate AssetListDelegate { get; }

	// @property (readonly, nonatomic, strong) id<AssetListScreenletDelegate> _Nullable assetListDelegate;
	[NullAllowed, Export ("assetListDelegate", ArgumentSemantic.Strong)]
	NSObject WeakAssetListDelegate { get; }

	// @property (copy, nonatomic) NSDictionary<NSString *,id> * _Nullable customEntryQuery;
	[NullAllowed, Export ("customEntryQuery", ArgumentSemantic.Copy)]
	NSDictionary<NSString, NSObject> CustomEntryQuery { get; set; }

	// -(BaseListPageLoadInteractor * _Nonnull)createPageLoadInteractorWithPage:(NSInteger)page computeRowCount:(BOOL)computeRowCount __attribute__((warn_unused_result));
	[Export ("createPageLoadInteractorWithPage:computeRowCount:")]
	BaseListPageLoadInteractor CreatePageLoadInteractorWithPage (nint page, bool computeRowCount);

	// -(void)onLoadPageErrorWithPage:(NSInteger)page error:(NSError * _Nonnull)error;
	[Export ("onLoadPageErrorWithPage:error:")]
	void OnLoadPageErrorWithPage (nint page, NSError error);

	// -(void)onLoadPageResultWithPage:(NSInteger)page rows:(NSArray * _Nonnull)rows rowCount:(NSInteger)rowCount;
	[Export ("onLoadPageResultWithPage:rows:rowCount:")]
	[Verify (StronglyTypedNSArray)]
	void OnLoadPageResultWithPage (nint page, NSObject[] rows, nint rowCount);

	// -(void)onSelectedRow:(id _Nonnull)row;
	[Export ("onSelectedRow:")]
	void OnSelectedRow (NSObject row);

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:themeName:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame, [NullAllowed] string themeName);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @protocol AssetListScreenletDelegate <BaseScreenletDelegate>
[Protocol, Model]
interface AssetListScreenletDelegate : IBaseScreenletDelegate
{
	// @optional -(void)screenlet:(AssetListScreenlet * _Nonnull)screenlet onAssetListResponse:(NSArray<Asset *> * _Nonnull)assets;
	[Export ("screenlet:onAssetListResponse:")]
	void OnAssetListResponse (AssetListScreenlet screenlet, Asset[] assets);

	// @optional -(void)screenlet:(AssetListScreenlet * _Nonnull)screenlet onAssetListError:(NSError * _Nonnull)error;
	[Export ("screenlet:onAssetListError:")]
	void OnAssetListError (AssetListScreenlet screenlet, NSError error);

	// @optional -(void)screenlet:(AssetListScreenlet * _Nonnull)screenlet onAssetSelected:(Asset * _Nonnull)asset;
	[Export ("screenlet:onAssetSelected:")]
	void OnAssetSelected (AssetListScreenlet screenlet, Asset asset);
}

// @interface BaseListView : BaseScreenletView
[BaseType (typeof(BaseScreenletView))]
interface BaseListView
{
	// @property (readonly, copy, nonatomic, class) NSString * _Nonnull DefaultSection;
	[Static]
	[Export ("DefaultSection")]
	string DefaultSection { get; }

	// @property (readonly, nonatomic) NSInteger rowCount;
	[Export ("rowCount")]
	nint RowCount { get; }

	// @property (readonly, copy, nonatomic) NSArray<NSString *> * _Nonnull sections;
	[Export ("sections", ArgumentSemantic.Copy)]
	string[] Sections { get; }

	// @property (copy, nonatomic) void (^ _Nullable)(id _Nonnull) onSelectedRowClosure;
	[NullAllowed, Export ("onSelectedRowClosure", ArgumentSemantic.Copy)]
	Action<NSObject> OnSelectedRowClosure { get; set; }

	// @property (copy, nonatomic) void (^ _Nullable)(NSInteger) fetchPageForRow;
	[NullAllowed, Export ("fetchPageForRow", ArgumentSemantic.Copy)]
	Action<nint> FetchPageForRow { get; set; }

	// @property (nonatomic) BOOL loadingRows;
	[Export ("loadingRows")]
	bool LoadingRows { get; set; }

	// @property (nonatomic) BOOL moreRows;
	[Export ("moreRows")]
	bool MoreRows { get; set; }

	// -(void)clearRows;
	[Export ("clearRows")]
	void ClearRows ();

	// -(void)deleteRow:(NSString * _Nonnull)section row:(NSInteger)row;
	[Export ("deleteRow:row:")]
	void DeleteRow (string section, nint row);

	// -(void)addRow:(NSString * _Nonnull)section element:(id _Nonnull)element;
	[Export ("addRow:element:")]
	void AddRow (string section, NSObject element);

	// -(void)updateRow:(NSString * _Nonnull)section row:(NSInteger)row element:(id _Nonnull)element;
	[Export ("updateRow:row:element:")]
	void UpdateRow (string section, nint row, NSObject element);

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface BaseListTableView : BaseListView <UIScrollViewDelegate, UITableViewDelegate, UITableViewDataSource>
[BaseType (typeof(BaseListView))]
interface BaseListTableView : IUIScrollViewDelegate, IUITableViewDelegate, IUITableViewDataSource
{
	// @property (nonatomic, strong) UITableView * _Nullable tableView __attribute__((iboutlet));
	[NullAllowed, Export ("tableView", ArgumentSemantic.Strong)]
	UITableView TableView { get; set; }

	// -(void)onCreated;
	[Export ("onCreated")]
	void OnCreated ();

	// -(void)onFinishInteraction:(id _Nullable)result error:(NSError * _Nullable)error;
	[Export ("onFinishInteraction:error:")]
	void OnFinishInteraction ([NullAllowed] NSObject result, [NullAllowed] NSError error);

	// -(NSInteger)tableView:(UITableView * _Nonnull)tableView numberOfRowsInSection:(NSInteger)section __attribute__((warn_unused_result));
	[Export ("tableView:numberOfRowsInSection:")]
	nint TableView (UITableView tableView, nint section);

	// -(NSInteger)numberOfSectionsInTableView:(UITableView * _Nonnull)tableView __attribute__((warn_unused_result));
	[Export ("numberOfSectionsInTableView:")]
	nint NumberOfSectionsInTableView (UITableView tableView);

	// -(NSString * _Nullable)tableView:(UITableView * _Nonnull)tableView titleForHeaderInSection:(NSInteger)section __attribute__((warn_unused_result));
	[Export ("tableView:titleForHeaderInSection:")]
	[return: NullAllowed]
	string TableView (UITableView tableView, nint section);

	// -(UITableViewCell * _Nonnull)tableView:(UITableView * _Nonnull)tableView cellForRowAtIndexPath:(NSIndexPath * _Nonnull)indexPath __attribute__((warn_unused_result));
	[Export ("tableView:cellForRowAtIndexPath:")]
	UITableViewCell TableView (UITableView tableView, NSIndexPath indexPath);

	// -(void)tableView:(UITableView * _Nonnull)tableView didSelectRowAtIndexPath:(NSIndexPath * _Nonnull)indexPath;
	[Export ("tableView:didSelectRowAtIndexPath:")]
	void TableView (UITableView tableView, NSIndexPath indexPath);

	// -(void)tableView:(UITableView * _Nonnull)tableView willDisplayCell:(UITableViewCell * _Nonnull)cell forRowAtIndexPath:(NSIndexPath * _Nonnull)indexPath;
	[Export ("tableView:willDisplayCell:forRowAtIndexPath:")]
	void TableView (UITableView tableView, UITableViewCell cell, NSIndexPath indexPath);

	// -(UITableViewCell * _Nonnull)doDequeueReusableCellWithRow:(NSInteger)row object:(id _Nullable)object __attribute__((warn_unused_result));
	[Export ("doDequeueReusableCellWithRow:object:")]
	UITableViewCell DoDequeueReusableCellWithRow (nint row, [NullAllowed] NSObject @object);

	// -(void)doFillLoadedCellWithRow:(NSInteger)row cell:(UITableViewCell * _Nonnull)cell object:(id _Nonnull)object;
	[Export ("doFillLoadedCellWithRow:cell:object:")]
	void DoFillLoadedCellWithRow (nint row, UITableViewCell cell, NSObject @object);

	// -(void)doFillInProgressCellWithRow:(NSInteger)row cell:(UITableViewCell * _Nonnull)cell;
	[Export ("doFillInProgressCellWithRow:cell:")]
	void DoFillInProgressCellWithRow (nint row, UITableViewCell cell);

	// -(void)doRegisterCellNibs;
	[Export ("doRegisterCellNibs")]
	void DoRegisterCellNibs ();

	// -(NSString * _Nonnull)doGetCellIdWithRow:(NSInteger)row object:(id _Nullable)object __attribute__((warn_unused_result));
	[Export ("doGetCellIdWithRow:object:")]
	string DoGetCellIdWithRow (nint row, [NullAllowed] NSObject @object);

	// -(UITableViewCell * _Nonnull)doCreateCell:(NSString * _Nonnull)cellId __attribute__((warn_unused_result));
	[Export ("doCreateCell:")]
	UITableViewCell DoCreateCell (string cellId);

	// -(UIView * _Nullable)createLoadingMoreView __attribute__((warn_unused_result));
	[NullAllowed, Export ("createLoadingMoreView")]
	[Verify (MethodToProperty)]
	UIView CreateLoadingMoreView { get; }

	// -(void)changeEditable:(BOOL)editable;
	[Export ("changeEditable:")]
	void ChangeEditable (bool editable);

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface AssetListTableView : BaseListTableView
[BaseType (typeof(BaseListTableView))]
interface AssetListTableView
{
	// -(void)doFillLoadedCellWithRow:(NSInteger)row cell:(UITableViewCell * _Nonnull)cell object:(id _Nonnull)object;
	[Export ("doFillLoadedCellWithRow:cell:object:")]
	void DoFillLoadedCellWithRow (nint row, UITableViewCell cell, NSObject @object);

	// -(void)doFillInProgressCellWithRow:(NSInteger)row cell:(UITableViewCell * _Nonnull)cell;
	[Export ("doFillInProgressCellWithRow:cell:")]
	void DoFillInProgressCellWithRow (nint row, UITableViewCell cell);

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface AssetListView_default : AssetListTableView
[BaseType (typeof(AssetListTableView))]
interface AssetListView_default
{
	// -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
	[Export ("createProgressPresenter")]
	[Verify (MethodToProperty)]
	ProgressPresenter CreateProgressPresenter { get; }

	// -(void)doFillLoadedCellWithRow:(NSInteger)row cell:(UITableViewCell * _Nonnull)cell object:(id _Nonnull)object;
	[Export ("doFillLoadedCellWithRow:cell:object:")]
	void DoFillLoadedCellWithRow (nint row, UITableViewCell cell, NSObject @object);

	// -(void)doFillInProgressCellWithRow:(NSInteger)row cell:(UITableViewCell * _Nonnull)cell;
	[Export ("doFillInProgressCellWithRow:cell:")]
	void DoFillInProgressCellWithRow (nint row, UITableViewCell cell);

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface AssetLoadByClassPKLiferayConnector : ServerConnector
[BaseType (typeof(ServerConnector))]
[DisableDefaultCtor]
interface AssetLoadByClassPKLiferayConnector
{
	// @property (readonly, copy, nonatomic) NSString * _Nonnull className;
	[Export ("className")]
	string ClassName { get; }

	// @property (readonly, nonatomic) int64_t classPK;
	[Export ("classPK")]
	long ClassPK { get; }

	// @property (nonatomic, strong) Asset * _Nullable resultAsset;
	[NullAllowed, Export ("resultAsset", ArgumentSemantic.Strong)]
	Asset ResultAsset { get; set; }

	// -(instancetype _Nonnull)initWithClassName:(NSString * _Nonnull)className classPK:(int64_t)classPK __attribute__((objc_designated_initializer));
	[Export ("initWithClassName:classPK:")]
	[DesignatedInitializer]
	IntPtr Constructor (string className, long classPK);

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }
}

// @interface AssetLoadByEntryIdLiferayConnector : ServerConnector
[BaseType (typeof(ServerConnector))]
[DisableDefaultCtor]
interface AssetLoadByEntryIdLiferayConnector
{
	// @property (readonly, nonatomic) int64_t entryId;
	[Export ("entryId")]
	long EntryId { get; }

	// @property (nonatomic, strong) Asset * _Nullable resultAsset;
	[NullAllowed, Export ("resultAsset", ArgumentSemantic.Strong)]
	Asset ResultAsset { get; set; }

	// -(instancetype _Nonnull)initWithEntryId:(int64_t)entryId __attribute__((objc_designated_initializer));
	[Export ("initWithEntryId:")]
	[DesignatedInitializer]
	IntPtr Constructor (long entryId);

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }
}

// @interface AssetLoadByPortletItemNameLiferayConnector : ServerConnector
[BaseType (typeof(ServerConnector))]
[DisableDefaultCtor]
interface AssetLoadByPortletItemNameLiferayConnector
{
	// @property (readonly, copy, nonatomic) NSString * _Nullable portletItemName;
	[NullAllowed, Export ("portletItemName")]
	string PortletItemName { get; }

	// @property (nonatomic, strong) Asset * _Nullable resultAsset;
	[NullAllowed, Export ("resultAsset", ArgumentSemantic.Strong)]
	Asset ResultAsset { get; set; }

	// -(instancetype _Nonnull)initWithPortletItemName:(NSString * _Nonnull)portletItemName __attribute__((objc_designated_initializer));
	[Export ("initWithPortletItemName:")]
	[DesignatedInitializer]
	IntPtr Constructor (string portletItemName);

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }
}

// @interface FileDisplayScreenlet : BaseScreenlet
[BaseType (typeof(BaseScreenlet))]
interface FileDisplayScreenlet
{
	// @property (readonly, copy, nonatomic, class) NSString * _Nonnull LoadFileAction;
	[Static]
	[Export ("LoadFileAction")]
	string LoadFileAction { get; }

	// @property (nonatomic) int64_t assetEntryId;
	[Export ("assetEntryId")]
	long AssetEntryId { get; set; }

	// @property (copy, nonatomic) NSString * _Nonnull className;
	[Export ("className")]
	string ClassName { get; set; }

	// @property (nonatomic) int64_t classPK;
	[Export ("classPK")]
	long ClassPK { get; set; }

	// @property (nonatomic) BOOL autoLoad;
	[Export ("autoLoad")]
	bool AutoLoad { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable offlinePolicy;
	[NullAllowed, Export ("offlinePolicy")]
	string OfflinePolicy { get; set; }

	[Wrap ("WeakFileDisplayDelegate")]
	[NullAllowed]
	FileDisplayScreenletDelegate FileDisplayDelegate { get; }

	// @property (readonly, nonatomic, strong) id<FileDisplayScreenletDelegate> _Nullable fileDisplayDelegate;
	[NullAllowed, Export ("fileDisplayDelegate", ArgumentSemantic.Strong)]
	NSObject WeakFileDisplayDelegate { get; }

	// @property (readonly, nonatomic, strong) id<FileDisplayViewModel> _Nullable fileDisplayViewModel;
	[NullAllowed, Export ("fileDisplayViewModel", ArgumentSemantic.Strong)]
	FileDisplayViewModel FileDisplayViewModel { get; }

	// @property (readonly, copy, nonatomic) NSArray<NSString *> * _Nonnull supportedMimeTypes;
	[Export ("supportedMimeTypes", ArgumentSemantic.Copy)]
	string[] SupportedMimeTypes { get; }

	// @property (nonatomic, strong) FileEntry * _Nullable fileEntry;
	[NullAllowed, Export ("fileEntry", ArgumentSemantic.Strong)]
	FileEntry FileEntry { get; set; }

	// -(void)onShow;
	[Export ("onShow")]
	void OnShow ();

	// -(Interactor * _Nullable)createInteractorWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender __attribute__((warn_unused_result));
	[Export ("createInteractorWithName:sender:")]
	[return: NullAllowed]
	Interactor CreateInteractorWithName (string name, [NullAllowed] NSObject sender);

	// -(BOOL)load;
	[Export ("load")]
	[Verify (MethodToProperty)]
	bool Load { get; }

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:themeName:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame, [NullAllowed] string themeName);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface AudioDisplayScreenlet : FileDisplayScreenlet
[BaseType (typeof(FileDisplayScreenlet))]
interface AudioDisplayScreenlet
{
	// @property (copy, nonatomic) NSString * _Nonnull mimeTypes;
	[Export ("mimeTypes")]
	string MimeTypes { get; set; }

	// @property (readonly, copy, nonatomic) NSArray<NSString *> * _Nonnull supportedMimeTypes;
	[Export ("supportedMimeTypes", ArgumentSemantic.Copy)]
	string[] SupportedMimeTypes { get; }

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:themeName:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame, [NullAllowed] string themeName);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @protocol FileDisplayViewModel
[Protocol, Model]
interface FileDisplayViewModel
{
	// @required @property (copy, nonatomic) NSURL * _Nullable url;
	[Abstract]
	[NullAllowed, Export ("url", ArgumentSemantic.Copy)]
	NSUrl Url { get; set; }

	// @required @property (copy, nonatomic) NSString * _Nullable title;
	[Abstract]
	[NullAllowed, Export ("title")]
	string Title { get; set; }
}

// @interface AudioDisplayView_default : BaseScreenletView <FileDisplayViewModel>
[BaseType (typeof(BaseScreenletView))]
interface AudioDisplayView_default : IFileDisplayViewModel
{
	// @property (nonatomic) float volume;
	[Export ("volume")]
	float Volume { get; set; }

	// @property (nonatomic) NSInteger numberOfLoops;
	[Export ("numberOfLoops")]
	nint NumberOfLoops { get; set; }

	// @property (copy, nonatomic) NSURL * _Nullable url;
	[NullAllowed, Export ("url", ArgumentSemantic.Copy)]
	NSUrl Url { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable title;
	[NullAllowed, Export ("title")]
	string Title { get; set; }

	// @property (nonatomic, strong) AVAudioPlayer * _Nullable audio;
	[NullAllowed, Export ("audio", ArgumentSemantic.Strong)]
	AVAudioPlayer Audio { get; set; }

	// @property (nonatomic, strong) NSTimer * _Nullable timer;
	[NullAllowed, Export ("timer", ArgumentSemantic.Strong)]
	NSTimer Timer { get; set; }

	// @property (nonatomic, strong) NSTimer * _Nullable duration;
	[NullAllowed, Export ("duration", ArgumentSemantic.Strong)]
	NSTimer Duration { get; set; }

	// -(void)onHide;
	[Export ("onHide")]
	void OnHide ();

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @protocol CredentialsStore
[Protocol, Model]
interface CredentialsStore
{
	// @required @property (readonly, nonatomic, strong) id<LRAuthentication> _Nullable authentication;
	[Abstract]
	[NullAllowed, Export ("authentication", ArgumentSemantic.Strong)]
	LRAuthentication Authentication { get; }

	// @required @property (readonly, copy, nonatomic) NSDictionary<NSString *,id> * _Nullable userAttributes;
	[Abstract]
	[NullAllowed, Export ("userAttributes", ArgumentSemantic.Copy)]
	NSDictionary<NSString, NSObject> UserAttributes { get; }

	// @required -(BOOL)storeCredentials:(LRSession * _Nullable)session userAttributes:(NSDictionary<NSString *,id> * _Nullable)userAttributes __attribute__((warn_unused_result));
	[Abstract]
	[Export ("storeCredentials:userAttributes:")]
	bool UserAttributes ([NullAllowed] LRSession session, [NullAllowed] NSDictionary<NSString, NSObject> userAttributes);

	// @required -(BOOL)removeStoredCredentials __attribute__((warn_unused_result));
	[Abstract]
	[Export ("removeStoredCredentials")]
	[Verify (MethodToProperty)]
	bool RemoveStoredCredentials { get; }

	// @required -(BOOL)loadStoredCredentials __attribute__((warn_unused_result));
	[Abstract]
	[Export ("loadStoredCredentials")]
	[Verify (MethodToProperty)]
	bool LoadStoredCredentials { get; }
}

// @interface BaseCredentialsStoreKeyChain : NSObject <CredentialsStore>
[BaseType (typeof(NSObject))]
interface BaseCredentialsStoreKeyChain : ICredentialsStore
{
	// @property (nonatomic, strong) id<LRAuthentication> _Nullable authentication;
	[NullAllowed, Export ("authentication", ArgumentSemantic.Strong)]
	LRAuthentication Authentication { get; set; }

	// @property (copy, nonatomic) NSDictionary<NSString *,id> * _Nullable userAttributes;
	[NullAllowed, Export ("userAttributes", ArgumentSemantic.Copy)]
	NSDictionary<NSString, NSObject> UserAttributes { get; set; }

	// -(BOOL)storeCredentials:(LRSession * _Nullable)session userAttributes:(NSDictionary<NSString *,id> * _Nullable)userAttributes __attribute__((warn_unused_result));
	[Export ("storeCredentials:userAttributes:")]
	bool StoreCredentials ([NullAllowed] LRSession session, [NullAllowed] NSDictionary<NSString, NSObject> userAttributes);

	// -(BOOL)removeStoredCredentials __attribute__((warn_unused_result));
	[Export ("removeStoredCredentials")]
	[Verify (MethodToProperty)]
	bool RemoveStoredCredentials { get; }

	// -(BOOL)loadStoredCredentials __attribute__((warn_unused_result));
	[Export ("loadStoredCredentials")]
	[Verify (MethodToProperty)]
	bool LoadStoredCredentials { get; }
}

// @interface BaseListCollectionView : BaseListView <UICollectionViewDataSource, UICollectionViewDelegate, UIScrollViewDelegate>
[BaseType (typeof(BaseListView))]
interface BaseListCollectionView : IUICollectionViewDataSource, IUICollectionViewDelegate, IUIScrollViewDelegate
{
	// @property (nonatomic, strong) UICollectionView * _Nullable collectionView __attribute__((iboutlet));
	[NullAllowed, Export ("collectionView", ArgumentSemantic.Strong)]
	UICollectionView CollectionView { get; set; }

	// -(void)onCreated;
	[Export ("onCreated")]
	void OnCreated ();

	// -(void)onShow;
	[Export ("onShow")]
	void OnShow ();

	// -(void)onFinishInteraction:(id _Nullable)result error:(NSError * _Nullable)error;
	[Export ("onFinishInteraction:error:")]
	void OnFinishInteraction ([NullAllowed] NSObject result, [NullAllowed] NSError error);

	// -(NSInteger)collectionView:(UICollectionView * _Nonnull)collectionView numberOfItemsInSection:(NSInteger)section __attribute__((warn_unused_result));
	[Export ("collectionView:numberOfItemsInSection:")]
	nint CollectionView (UICollectionView collectionView, nint section);

	// -(NSInteger)numberOfSectionsInCollectionView:(UICollectionView * _Nonnull)collectionView __attribute__((warn_unused_result));
	[Export ("numberOfSectionsInCollectionView:")]
	nint NumberOfSectionsInCollectionView (UICollectionView collectionView);

	// -(UICollectionViewCell * _Nonnull)collectionView:(UICollectionView * _Nonnull)collectionView cellForItemAtIndexPath:(NSIndexPath * _Nonnull)indexPath __attribute__((warn_unused_result));
	[Export ("collectionView:cellForItemAtIndexPath:")]
	UICollectionViewCell CollectionView (UICollectionView collectionView, NSIndexPath indexPath);

	// -(void)collectionView:(UICollectionView * _Nonnull)collectionView didSelectItemAtIndexPath:(NSIndexPath * _Nonnull)indexPath;
	[Export ("collectionView:didSelectItemAtIndexPath:")]
	void CollectionView (UICollectionView collectionView, NSIndexPath indexPath);

	// -(void)collectionView:(UICollectionView * _Nonnull)collectionView willDisplayCell:(UICollectionViewCell * _Nonnull)cell forItemAtIndexPath:(NSIndexPath * _Nonnull)indexPath;
	[Export ("collectionView:willDisplayCell:forItemAtIndexPath:")]
	void CollectionView (UICollectionView collectionView, UICollectionViewCell cell, NSIndexPath indexPath);

	// -(CGSize)collectionView:(UICollectionView * _Nonnull)collectionView layout:(UICollectionViewLayout * _Nonnull)collectionViewLayout sizeForItemAtIndexPath:(NSIndexPath * _Nonnull)indexPath __attribute__((warn_unused_result));
	[Export ("collectionView:layout:sizeForItemAtIndexPath:")]
	CGSize CollectionView (UICollectionView collectionView, UICollectionViewLayout collectionViewLayout, NSIndexPath indexPath);

	// -(void)doConfigureCollectionView:(UICollectionView * _Nonnull)collectionView;
	[Export ("doConfigureCollectionView:")]
	void DoConfigureCollectionView (UICollectionView collectionView);

	// -(UICollectionViewLayout * _Nonnull)doCreateLayout __attribute__((warn_unused_result));
	[Export ("doCreateLayout")]
	[Verify (MethodToProperty)]
	UICollectionViewLayout DoCreateLayout { get; }

	// -(UICollectionViewCell * _Nonnull)doDequeueReusableCell:(NSIndexPath * _Nonnull)indexPath object:(id _Nullable)object __attribute__((warn_unused_result));
	[Export ("doDequeueReusableCell:object:")]
	UICollectionViewCell DoDequeueReusableCell (NSIndexPath indexPath, [NullAllowed] NSObject @object);

	// -(void)doFillLoadedCellWithIndexPath:(NSIndexPath * _Nonnull)indexPath cell:(UICollectionViewCell * _Nonnull)cell object:(id _Nonnull)object;
	[Export ("doFillLoadedCellWithIndexPath:cell:object:")]
	void DoFillLoadedCellWithIndexPath (NSIndexPath indexPath, UICollectionViewCell cell, NSObject @object);

	// -(void)doFillInProgressCellWithIndexPath:(NSIndexPath * _Nonnull)indexPath cell:(UICollectionViewCell * _Nonnull)cell;
	[Export ("doFillInProgressCellWithIndexPath:cell:")]
	void DoFillInProgressCellWithIndexPath (NSIndexPath indexPath, UICollectionViewCell cell);

	// -(void)doRegisterCellNibs;
	[Export ("doRegisterCellNibs")]
	void DoRegisterCellNibs ();

	// -(void)doRegisterLoadMoreCell;
	[Export ("doRegisterLoadMoreCell")]
	void DoRegisterLoadMoreCell ();

	// -(NSString * _Nonnull)doGetCellIdWithIndexPath:(NSIndexPath * _Nonnull)indexPath object:(id _Nullable)object __attribute__((warn_unused_result));
	[Export ("doGetCellIdWithIndexPath:object:")]
	string DoGetCellIdWithIndexPath (NSIndexPath indexPath, [NullAllowed] NSObject @object);

	// -(NSString * _Nonnull)doGetLoadMoreCellId __attribute__((warn_unused_result));
	[Export ("doGetLoadMoreCellId")]
	[Verify (MethodToProperty)]
	string DoGetLoadMoreCellId { get; }

	// -(UICollectionViewCell * _Nonnull)doCreateCell:(NSString * _Nonnull)cellId __attribute__((warn_unused_result));
	[Export ("doCreateCell:")]
	UICollectionViewCell DoCreateCell (string cellId);

	// -(void)doFillLoadMoreCell:(UICollectionViewCell * _Nonnull)cell;
	[Export ("doFillLoadMoreCell:")]
	void DoFillLoadMoreCell (UICollectionViewCell cell);

	// -(CGSize)doGetLoadMoreViewSize:(UICollectionViewFlowLayout * _Nonnull)layout __attribute__((warn_unused_result));
	[Export ("doGetLoadMoreViewSize:")]
	CGSize DoGetLoadMoreViewSize (UICollectionViewFlowLayout layout);

	// -(void)updateRefreshControl;
	[Export ("updateRefreshControl")]
	void UpdateRefreshControl ();

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @protocol BasicAuthBasedType
[Protocol, Model]
interface BasicAuthBasedType
{
	// @required @property (copy, nonatomic) NSString * _Nullable basicAuthMethod;
	[Abstract]
	[NullAllowed, Export ("basicAuthMethod")]
	string BasicAuthMethod { get; set; }
}

// @interface BasicCredentialsStoreKeyChain : BaseCredentialsStoreKeyChain
[BaseType (typeof(BaseCredentialsStoreKeyChain))]
interface BasicCredentialsStoreKeyChain
{
}

// @protocol BlogsDisplayViewModel
[Protocol, Model]
interface BlogsDisplayViewModel
{
	// @required @property (nonatomic, strong) BlogsEntry * _Nullable blogsEntry;
	[Abstract]
	[NullAllowed, Export ("blogsEntry", ArgumentSemantic.Strong)]
	BlogsEntry BlogsEntry { get; set; }
}

// @interface BlogsEntry : Asset
[BaseType (typeof(Asset))]
interface BlogsEntry
{
	// @property (readonly, copy, nonatomic) NSDictionary<NSString *,id> * _Nullable blogsEntry;
	[NullAllowed, Export ("blogsEntry", ArgumentSemantic.Copy)]
	NSDictionary<NSString, NSObject> BlogsEntry { get; }

	// @property (readonly, nonatomic) int64_t blogId;
	[Export ("blogId")]
	long BlogId { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull subtitle;
	[Export ("subtitle")]
	string Subtitle { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull userName;
	[Export ("userName")]
	string UserName { get; }

	// @property (readonly, copy, nonatomic) NSDate * _Nullable displayDate;
	[NullAllowed, Export ("displayDate", ArgumentSemantic.Copy)]
	NSDate DisplayDate { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull content;
	[Export ("content")]
	string Content { get; }

	// @property (readonly, nonatomic) int64_t userId;
	[Export ("userId")]
	long UserId { get; }

	// @property (readonly, nonatomic) int64_t coverImageFileEntryId;
	[Export ("coverImageFileEntryId")]
	long CoverImageFileEntryId { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nullable mimeType;
	[NullAllowed, Export ("mimeType")]
	string MimeType { get; }

	// -(instancetype _Nonnull)initWithAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes __attribute__((objc_designated_initializer));
	[Export ("initWithAttributes:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSDictionary<NSString, NSObject> attributes);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface BlogsEntryDisplayScreenlet : BaseScreenlet
[BaseType (typeof(BaseScreenlet))]
interface BlogsEntryDisplayScreenlet
{
	// @property (nonatomic) int64_t assetEntryId;
	[Export ("assetEntryId")]
	long AssetEntryId { get; set; }

	// @property (nonatomic) int64_t classPK;
	[Export ("classPK")]
	long ClassPK { get; set; }

	// @property (nonatomic) BOOL autoLoad;
	[Export ("autoLoad")]
	bool AutoLoad { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable offlinePolicy;
	[NullAllowed, Export ("offlinePolicy")]
	string OfflinePolicy { get; set; }

	[Wrap ("WeakBlogsEntryDisplayDelegate")]
	[NullAllowed]
	BlogsEntryDisplayScreenletDelegate BlogsEntryDisplayDelegate { get; }

	// @property (readonly, nonatomic, strong) id<BlogsEntryDisplayScreenletDelegate> _Nullable blogsEntryDisplayDelegate;
	[NullAllowed, Export ("blogsEntryDisplayDelegate", ArgumentSemantic.Strong)]
	NSObject WeakBlogsEntryDisplayDelegate { get; }

	// @property (readonly, nonatomic, strong) id<BlogsDisplayViewModel> _Nullable blogsEntryViewModel;
	[NullAllowed, Export ("blogsEntryViewModel", ArgumentSemantic.Strong)]
	BlogsDisplayViewModel BlogsEntryViewModel { get; }

	// @property (nonatomic, strong) BlogsEntry * _Nullable blogsEntry;
	[NullAllowed, Export ("blogsEntry", ArgumentSemantic.Strong)]
	BlogsEntry BlogsEntry { get; set; }

	// -(void)onShow;
	[Export ("onShow")]
	void OnShow ();

	// -(Interactor * _Nullable)createInteractorWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender __attribute__((warn_unused_result));
	[Export ("createInteractorWithName:sender:")]
	[return: NullAllowed]
	Interactor CreateInteractorWithName (string name, [NullAllowed] NSObject sender);

	// -(BOOL)load;
	[Export ("load")]
	[Verify (MethodToProperty)]
	bool Load { get; }

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:themeName:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame, [NullAllowed] string themeName);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @protocol BlogsEntryDisplayScreenletDelegate <BaseScreenletDelegate>
[Protocol, Model]
interface BlogsEntryDisplayScreenletDelegate : IBaseScreenletDelegate
{
	// @optional -(void)screenlet:(BlogsEntryDisplayScreenlet * _Nonnull)screenlet onBlogEntryResponse:(BlogsEntry * _Nonnull)blogEntry;
	[Export ("screenlet:onBlogEntryResponse:")]
	void OnBlogEntryResponse (BlogsEntryDisplayScreenlet screenlet, BlogsEntry blogEntry);

	// @optional -(void)screenlet:(BlogsEntryDisplayScreenlet * _Nonnull)screenlet onBlogEntryError:(NSError * _Nonnull)error;
	[Export ("screenlet:onBlogEntryError:")]
	void OnBlogEntryError (BlogsEntryDisplayScreenlet screenlet, NSError error);
}

// @interface BlogsEntryDisplayView_default : BaseScreenletView <BlogsDisplayViewModel>
[BaseType (typeof(BaseScreenletView))]
interface BlogsEntryDisplayView_default : IBlogsDisplayViewModel
{
	// @property (nonatomic) CGFloat headerImageHeight;
	[Export ("headerImageHeight")]
	nfloat HeaderImageHeight { get; set; }

	// @property (readonly, nonatomic, strong) NSDateFormatter * _Nonnull dateFormatter;
	[Export ("dateFormatter", ArgumentSemantic.Strong)]
	NSDateFormatter DateFormatter { get; }

	// @property (nonatomic, strong) BlogsEntry * _Nullable blogsEntry;
	[NullAllowed, Export ("blogsEntry", ArgumentSemantic.Strong)]
	BlogsEntry BlogsEntry { get; set; }

	// -(void)onSetTranslations;
	[Export ("onSetTranslations")]
	void OnSetTranslations ();

	// -(void)loadBlog;
	[Export ("loadBlog")]
	void LoadBlog ();

	// -(void)loadImage;
	[Export ("loadImage")]
	void LoadImage ();

	// -(void)loadUserInfo;
	[Export ("loadUserInfo")]
	void LoadUserInfo ();

	// -(void)loadDate;
	[Export ("loadDate")]
	void LoadDate ();

	// -(void)loadTitleSubtitle;
	[Export ("loadTitleSubtitle")]
	void LoadTitleSubtitle ();

	// -(void)loadContent;
	[Export ("loadContent")]
	void LoadContent ();

	// +(NSDictionary<NSString *,NSObject *> * _Nonnull)defaultAttributedTextAttributes __attribute__((warn_unused_result));
	[Static]
	[Export ("defaultAttributedTextAttributes")]
	[Verify (MethodToProperty)]
	NSDictionary<NSString, NSObject> DefaultAttributedTextAttributes { get; }

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface LiferayScreens_Swift_1117 (NSBundle)
[Category]
[BaseType (typeof(NSBundle))]
interface NSBundle_LiferayScreens_Swift_1117
{
	// +(NSArray<NSBundle *> * _Nonnull)allBundles:(Class _Nonnull)currentClass __attribute__((warn_unused_result));
	[Static]
	[Export ("allBundles:")]
	NSBundle[] AllBundles (Class currentClass);

	// +(NSArray<NSBundle *> * _Nonnull)discoverBundles __attribute__((warn_unused_result));
	[Static]
	[Export ("discoverBundles")]
	[Verify (MethodToProperty)]
	NSBundle[] DiscoverBundles { get; }

	// +(NSArray<NSBundle *> * _Nonnull)bundlesForDefaultTheme __attribute__((warn_unused_result));
	[Static]
	[Export ("bundlesForDefaultTheme")]
	[Verify (MethodToProperty)]
	NSBundle[] BundlesForDefaultTheme { get; }

	// +(NSArray<NSBundle *> * _Nonnull)bundlesForCore __attribute__((warn_unused_result));
	[Static]
	[Export ("bundlesForCore")]
	[Verify (MethodToProperty)]
	NSBundle[] BundlesForCore { get; }

	// +(NSBundle * _Nonnull)bundleForName:(NSString * _Nonnull)name __attribute__((warn_unused_result));
	[Static]
	[Export ("bundleForName:")]
	NSBundle BundleForName (string name);

	// +(NSArray<NSBundle *> * _Nonnull)bundlesForApp __attribute__((warn_unused_result));
	[Static]
	[Export ("bundlesForApp")]
	[Verify (MethodToProperty)]
	NSBundle[] BundlesForApp { get; }

	// +(NSBundle * _Nullable)bundleForNibName:(NSString * _Nonnull)name currentClass:(Class _Nonnull)currentClass __attribute__((warn_unused_result));
	[Static]
	[Export ("bundleForNibName:currentClass:")]
	[return: NullAllowed]
	NSBundle BundleForNibName (string name, Class currentClass);

	// +(UIImage * _Nullable)imageInBundlesWithName:(NSString * _Nonnull)name currentClass:(Class _Nonnull)currentClass __attribute__((warn_unused_result));
	[Static]
	[Export ("imageInBundlesWithName:currentClass:")]
	[return: NullAllowed]
	UIImage ImageInBundlesWithName (string name, Class currentClass);

	// +(UINib * _Nullable)nibInBundlesWithName:(NSString * _Nonnull)name currentClass:(Class _Nonnull)currentClass __attribute__((warn_unused_result));
	[Static]
	[Export ("nibInBundlesWithName:currentClass:")]
	[return: NullAllowed]
	UINib NibInBundlesWithName (string name, Class currentClass);

	// +(UIView * _Nullable)viewForThemeOrDefaultWithName:(NSString * _Nonnull)name themeName:(NSString * _Nonnull)themeName currentClass:(Class _Nonnull)currentClass __attribute__((warn_unused_result));
	[Static]
	[Export ("viewForThemeOrDefaultWithName:themeName:currentClass:")]
	[return: NullAllowed]
	UIView ViewForThemeOrDefaultWithName (string name, string themeName, Class currentClass);

	// +(UIView * _Nullable)viewForThemeWithName:(NSString * _Nonnull)name themeName:(NSString * _Nonnull)themeName currentClass:(Class _Nonnull)currentClass __attribute__((warn_unused_result));
	[Static]
	[Export ("viewForThemeWithName:themeName:currentClass:")]
	[return: NullAllowed]
	UIView ViewForThemeWithName (string name, string themeName, Class currentClass);

	// +(id _Nullable)rootNibObjectForThemeOrDefaultWithName:(NSString * _Nonnull)name themeName:(NSString * _Nonnull)themeName currentClass:(Class _Nonnull)currentClass __attribute__((warn_unused_result));
	[Static]
	[Export ("rootNibObjectForThemeOrDefaultWithName:themeName:currentClass:")]
	[return: NullAllowed]
	NSObject RootNibObjectForThemeOrDefaultWithName (string name, string themeName, Class currentClass);

	// +(id _Nullable)rootNibObjectForThemeWithName:(NSString * _Nonnull)name themeName:(NSString * _Nonnull)themeName currentClass:(Class _Nonnull)currentClass __attribute__((warn_unused_result));
	[Static]
	[Export ("rootNibObjectForThemeWithName:themeName:currentClass:")]
	[return: NullAllowed]
	NSObject RootNibObjectForThemeWithName (string name, string themeName, Class currentClass);
}

// @interface CacheManager : NSObject
[BaseType (typeof(NSObject))]
[DisableDefaultCtor]
interface CacheManager
{
	// @property (readonly, nonatomic, strong) YapDatabase * _Nonnull database;
	[Export ("database", ArgumentSemantic.Strong)]
	YapDatabase Database { get; }

	// @property (readonly, nonatomic, strong) YapDatabaseConnection * _Nonnull readConnection;
	[Export ("readConnection", ArgumentSemantic.Strong)]
	YapDatabaseConnection ReadConnection { get; }

	// @property (readonly, nonatomic, strong) YapDatabaseConnection * _Nonnull writeConnection;
	[Export ("writeConnection", ArgumentSemantic.Strong)]
	YapDatabaseConnection WriteConnection { get; }

	// -(instancetype _Nonnull)initWithDatabase:(YapDatabase * _Nonnull)database __attribute__((objc_designated_initializer));
	[Export ("initWithDatabase:")]
	[DesignatedInitializer]
	IntPtr Constructor (YapDatabase database);

	// -(instancetype _Nonnull)initWithName:(NSString * _Nonnull)name;
	[Export ("initWithName:")]
	IntPtr Constructor (string name);

	// -(instancetype _Nonnull)initWithSession:(LRSession * _Nonnull)session userId:(int64_t)userId;
	[Export ("initWithSession:userId:")]
	IntPtr Constructor (LRSession session, long userId);

	// -(void)getStringWithCollection:(NSString * _Nonnull)collection key:(NSString * _Nonnull)key result:(void (^ _Nonnull)(NSString * _Nullable))result;
	[Export ("getStringWithCollection:key:result:")]
	void GetStringWithCollection (string collection, string key, Action<NSString> result);

	// -(void)getLocalFileURLWithCollection:(NSString * _Nonnull)collection key:(NSString * _Nonnull)key result:(void (^ _Nonnull)(NSURL * _Nullable))result;
	[Export ("getLocalFileURLWithCollection:key:result:")]
	void GetLocalFileURLWithCollection (string collection, string key, Action<NSURL> result);

	// -(void)getImageWithCollection:(NSString * _Nonnull)collection key:(NSString * _Nonnull)key result:(void (^ _Nonnull)(UIImage * _Nullable))result;
	[Export ("getImageWithCollection:key:result:")]
	void GetImageWithCollection (string collection, string key, Action<UIImage> result);

	// -(void)getAnyWithCollection:(NSString * _Nonnull)collection key:(NSString * _Nonnull)key result:(void (^ _Nonnull)(id _Nullable))result;
	[Export ("getAnyWithCollection:key:result:")]
	void GetAnyWithCollection (string collection, string key, Action<NSObject> result);

	// -(void)getAnyWithAttributesWithCollection:(NSString * _Nonnull)collection key:(NSString * _Nonnull)key result:(void (^ _Nonnull)(id _Nullable, NSDictionary<NSString *,id> * _Nullable))result;
	[Export ("getAnyWithAttributesWithCollection:key:result:")]
	void GetAnyWithAttributesWithCollection (string collection, string key, Action<NSObject, NSDictionary<NSString, NSObject>> result);

	// -(void)getMetadataWithCollection:(NSString * _Nonnull)collection key:(NSString * _Nonnull)key result:(void (^ _Nonnull)(CacheMetadata * _Nullable))result;
	[Export ("getMetadataWithCollection:key:result:")]
	void GetMetadataWithCollection (string collection, string key, Action<CacheMetadata> result);

	// -(void)setCleanWithCollection:(NSString * _Nonnull)collection key:(NSString * _Nonnull)key value:(id<NSCoding> _Nonnull)value attributes:(NSDictionary<NSString *,id> * _Nonnull)attributes onCompletion:(void (^ _Nullable)(void))onCompletion;
	[Export ("setCleanWithCollection:key:value:attributes:onCompletion:")]
	void SetCleanWithCollection (string collection, string key, NSCoding value, NSDictionary<NSString, NSObject> attributes, [NullAllowed] Action onCompletion);

	// -(void)setCleanWithCollection:(NSString * _Nonnull)collection key:(NSString * _Nonnull)key localFileURL:(NSURL * _Nonnull)localFileURL attributes:(NSDictionary<NSString *,id> * _Nonnull)attributes onCompletion:(void (^ _Nullable)(void))onCompletion;
	[Export ("setCleanWithCollection:key:localFileURL:attributes:onCompletion:")]
	void SetCleanWithCollection (string collection, string key, NSUrl localFileURL, NSDictionary<NSString, NSObject> attributes, [NullAllowed] Action onCompletion);

	// -(void)setCleanWithCollection:(NSString * _Nonnull)collection keys:(NSArray<NSString *> * _Nonnull)keys values:(NSArray<id<NSCoding>> * _Nonnull)values attributes:(NSDictionary<NSString *,id> * _Nonnull)attributes onCompletion:(void (^ _Nullable)(void))onCompletion;
	[Export ("setCleanWithCollection:keys:values:attributes:onCompletion:")]
	void SetCleanWithCollection (string collection, string[] keys, NSCoding[] values, NSDictionary<NSString, NSObject> attributes, [NullAllowed] Action onCompletion);

	// -(void)setDirtyWithCollection:(NSString * _Nonnull)collection key:(NSString * _Nonnull)key value:(id<NSCoding> _Nonnull)value attributes:(NSDictionary<NSString *,id> * _Nonnull)attributes onCompletion:(void (^ _Nullable)(void))onCompletion;
	[Export ("setDirtyWithCollection:key:value:attributes:onCompletion:")]
	void SetDirtyWithCollection (string collection, string key, NSCoding value, NSDictionary<NSString, NSObject> attributes, [NullAllowed] Action onCompletion);

	// -(void)setCleanWithCollection:(NSString * _Nonnull)collection key:(NSString * _Nonnull)key attributes:(NSDictionary<NSString *,id> * _Nonnull)attributes onCompletion:(void (^ _Nullable)(void))onCompletion;
	[Export ("setCleanWithCollection:key:attributes:onCompletion:")]
	void SetCleanWithCollection (string collection, string key, NSDictionary<NSString, NSObject> attributes, [NullAllowed] Action onCompletion);

	// -(void)removeWithCollection:(NSString * _Nonnull)collection key:(NSString * _Nonnull)key onCompletion:(void (^ _Nullable)(void))onCompletion;
	[Export ("removeWithCollection:key:onCompletion:")]
	void RemoveWithCollection (string collection, string key, [NullAllowed] Action onCompletion);

	// -(void)removeWithCollection:(NSString * _Nonnull)collection onCompletion:(void (^ _Nullable)(void))onCompletion;
	[Export ("removeWithCollection:onCompletion:")]
	void RemoveWithCollection (string collection, [NullAllowed] Action onCompletion);

	// -(void)removeAll:(void (^ _Nullable)(void))onCompletion;
	[Export ("removeAll:")]
	void RemoveAll ([NullAllowed] Action onCompletion);

	// -(void)countPendingToSync:(void (^ _Nonnull)(NSUInteger))result;
	[Export ("countPendingToSync:")]
	void CountPendingToSync (Action<nuint> result);

	// -(void)pendingToSync:(BOOL (^ _Nonnull)(NSString * _Nonnull, NSString * _Nonnull, NSDictionary<NSString *,id> * _Nonnull))result onCompletion:(void (^ _Nullable)(void))onCompletion;
	[Export ("pendingToSync:onCompletion:")]
	void PendingToSync (Func<NSString, NSString, NSDictionary<NSString, NSObject>, bool> result, [NullAllowed] Action onCompletion);

	// +(NSString * _Nonnull)databasePath:(NSString * _Nonnull)name __attribute__((warn_unused_result));
	[Static]
	[Export ("databasePath:")]
	string DatabasePath (string name);

	// -(void)registerPendingToSyncView:(void (^ _Nullable)(BOOL))result;
	[Export ("registerPendingToSyncView:")]
	void RegisterPendingToSyncView ([NullAllowed] Action<bool> result);
}

// @interface CacheMetadata : NSObject <NSCoding>
[BaseType (typeof(NSObject))]
[DisableDefaultCtor]
interface CacheMetadata : INSCoding
{
	// @property (readonly, copy, nonatomic) NSDate * _Nullable synchronized;
	[NullAllowed, Export ("synchronized", ArgumentSemantic.Copy)]
	NSDate Synchronized { get; }

	// @property (readonly, copy, nonatomic) NSDictionary<NSString *,id> * _Nonnull attributes;
	[Export ("attributes", ArgumentSemantic.Copy)]
	NSDictionary<NSString, NSObject> Attributes { get; }

	// -(instancetype _Nonnull)initWithSynchronized:(NSDate * _Nullable)synchronized attributes:(NSDictionary<NSString *,id> * _Nonnull)attributes __attribute__((objc_designated_initializer));
	[Export ("initWithSynchronized:attributes:")]
	[DesignatedInitializer]
	IntPtr Constructor ([NullAllowed] NSDate synchronized, NSDictionary<NSString, NSObject> attributes);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder;
	[Export ("initWithCoder:")]
	IntPtr Constructor (NSCoder aDecoder);

	// -(void)encodeWithCoder:(NSCoder * _Nonnull)aCoder;
	[Export ("encodeWithCoder:")]
	void EncodeWithCoder (NSCoder aCoder);
}

// @interface Comment : NSObject <NSCoding>
[BaseType (typeof(NSObject))]
[DisableDefaultCtor]
interface Comment : INSCoding
{
	// +(NSString * _Nonnull)plainBodyToHtml:(NSString * _Nonnull)plainBody __attribute__((warn_unused_result));
	[Static]
	[Export ("plainBodyToHtml:")]
	string PlainBodyToHtml (string plainBody);

	// @property (readonly, copy, nonatomic) NSDictionary<NSString *,id> * _Nonnull attributes;
	[Export ("attributes", ArgumentSemantic.Copy)]
	NSDictionary<NSString, NSObject> Attributes { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull originalBody;
	[Export ("originalBody")]
	string OriginalBody { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull plainBody;
	[Export ("plainBody")]
	string PlainBody { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull htmlBody;
	[Export ("htmlBody")]
	string HtmlBody { get; }

	// @property (readonly, nonatomic) BOOL isStyled;
	[Export ("isStyled")]
	bool IsStyled { get; }

	// @property (readonly, nonatomic) int64_t commentId;
	[Export ("commentId")]
	long CommentId { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull userName;
	[Export ("userName")]
	string UserName { get; }

	// @property (readonly, nonatomic) int64_t userId;
	[Export ("userId")]
	long UserId { get; }

	// @property (readonly, copy, nonatomic) NSDate * _Nonnull createDate;
	[Export ("createDate", ArgumentSemantic.Copy)]
	NSDate CreateDate { get; }

	// @property (readonly, copy, nonatomic) NSDate * _Nonnull modifiedDate;
	[Export ("modifiedDate", ArgumentSemantic.Copy)]
	NSDate ModifiedDate { get; }

	// @property (readonly, nonatomic) BOOL canDelete;
	[Export ("canDelete")]
	bool CanDelete { get; }

	// @property (readonly, nonatomic) BOOL canEdit;
	[Export ("canEdit")]
	bool CanEdit { get; }

	// -(void)encodeWithCoder:(NSCoder * _Nonnull)aCoder;
	[Export ("encodeWithCoder:")]
	void EncodeWithCoder (NSCoder aCoder);

	// -(instancetype _Nonnull)initWithAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes __attribute__((objc_designated_initializer));
	[Export ("initWithAttributes:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSDictionary<NSString, NSObject> attributes);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface ServerWriteConnectorInteractor : ServerConnectorInteractor
[BaseType (typeof(ServerConnectorInteractor))]
interface ServerWriteConnectorInteractor
{
	// -(instancetype _Nonnull)initWithScreenlet:(BaseScreenlet * _Nullable)screenlet __attribute__((objc_designated_initializer));
	[Export ("initWithScreenlet:")]
	[DesignatedInitializer]
	IntPtr Constructor ([NullAllowed] BaseScreenlet screenlet);
}

// @interface CommentAddInteractor : ServerWriteConnectorInteractor
[BaseType (typeof(ServerWriteConnectorInteractor))]
interface CommentAddInteractor
{
	// @property (nonatomic, strong) Comment * _Nullable resultComment;
	[NullAllowed, Export ("resultComment", ArgumentSemantic.Strong)]
	Comment ResultComment { get; set; }

	// -(instancetype _Nonnull)initWithScreenlet:(CommentAddScreenlet * _Nonnull)screenlet body:(NSString * _Nonnull)body __attribute__((objc_designated_initializer));
	[Export ("initWithScreenlet:body:")]
	[DesignatedInitializer]
	IntPtr Constructor (CommentAddScreenlet screenlet, string body);

	// -(instancetype _Nonnull)initWithClassName:(NSString * _Nonnull)className classPK:(int64_t)classPK body:(NSString * _Nonnull)body __attribute__((objc_designated_initializer));
	[Export ("initWithClassName:classPK:body:")]
	[DesignatedInitializer]
	IntPtr Constructor (string className, long classPK, string body);

	// -(instancetype _Nonnull)initWithClassName:(NSString * _Nonnull)className classPK:(int64_t)classPK body:(NSString * _Nonnull)body cacheKeyUsed:(NSString * _Nonnull)cacheKeyUsed;
	[Export ("initWithClassName:classPK:body:cacheKeyUsed:")]
	IntPtr Constructor (string className, long classPK, string body, string cacheKeyUsed);

	// -(CommentAddLiferayConnector * _Nullable)createConnector __attribute__((warn_unused_result));
	[NullAllowed, Export ("createConnector")]
	[Verify (MethodToProperty)]
	CommentAddLiferayConnector CreateConnector { get; }

	// -(void)completedConnector:(ServerConnector * _Nonnull)c;
	[Export ("completedConnector:")]
	void CompletedConnector (ServerConnector c);

	// -(void)writeToCache:(ServerConnector * _Nonnull)c;
	[Export ("writeToCache:")]
	void WriteToCache (ServerConnector c);

	// -(void)callOnSuccess;
	[Export ("callOnSuccess")]
	void CallOnSuccess ();
}

// @interface CommentAddLiferayConnector : ServerConnector
[BaseType (typeof(ServerConnector))]
[DisableDefaultCtor]
interface CommentAddLiferayConnector
{
	// @property (readonly, copy, nonatomic) NSString * _Nonnull className;
	[Export ("className")]
	string ClassName { get; }

	// @property (readonly, nonatomic) int64_t classPK;
	[Export ("classPK")]
	long ClassPK { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull body;
	[Export ("body")]
	string Body { get; }

	// @property (nonatomic, strong) Comment * _Nullable resultComment;
	[NullAllowed, Export ("resultComment", ArgumentSemantic.Strong)]
	Comment ResultComment { get; set; }

	// -(instancetype _Nonnull)initWithClassName:(NSString * _Nonnull)className classPK:(int64_t)classPK body:(NSString * _Nonnull)body __attribute__((objc_designated_initializer));
	[Export ("initWithClassName:classPK:body:")]
	[DesignatedInitializer]
	IntPtr Constructor (string className, long classPK, string body);

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }
}

// @interface CommentAddScreenlet : BaseScreenlet
[BaseType (typeof(BaseScreenlet))]
interface CommentAddScreenlet
{
	// @property (copy, nonatomic) NSString * _Nonnull className;
	[Export ("className")]
	string ClassName { get; set; }

	// @property (nonatomic) int64_t classPK;
	[Export ("classPK")]
	long ClassPK { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable offlinePolicy;
	[NullAllowed, Export ("offlinePolicy")]
	string OfflinePolicy { get; set; }

	[Wrap ("WeakCommentAddDelegate")]
	[NullAllowed]
	CommentAddScreenletDelegate CommentAddDelegate { get; }

	// @property (readonly, nonatomic, strong) id<CommentAddScreenletDelegate> _Nullable commentAddDelegate;
	[NullAllowed, Export ("commentAddDelegate", ArgumentSemantic.Strong)]
	NSObject WeakCommentAddDelegate { get; }

	// @property (readonly, nonatomic, strong) id<CommentAddViewModel> _Nonnull viewModel;
	[Export ("viewModel", ArgumentSemantic.Strong)]
	CommentAddViewModel ViewModel { get; }

	// @property (nonatomic, strong) Comment * _Nullable comment;
	[NullAllowed, Export ("comment", ArgumentSemantic.Strong)]
	Comment Comment { get; set; }

	// -(Interactor * _Nullable)createInteractorWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender __attribute__((warn_unused_result));
	[Export ("createInteractorWithName:sender:")]
	[return: NullAllowed]
	Interactor CreateInteractorWithName (string name, [NullAllowed] NSObject sender);

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:themeName:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame, [NullAllowed] string themeName);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @protocol CommentAddScreenletDelegate <BaseScreenletDelegate>
[Protocol, Model]
interface CommentAddScreenletDelegate : IBaseScreenletDelegate
{
	// @optional -(void)screenlet:(CommentAddScreenlet * _Nonnull)screenlet onCommentAdded:(Comment * _Nonnull)comment;
	[Export ("screenlet:onCommentAdded:")]
	void OnCommentAdded (CommentAddScreenlet screenlet, Comment comment);

	// @optional -(void)screenlet:(CommentAddScreenlet * _Nonnull)screenlet onAddCommentError:(NSError * _Nonnull)error;
	[Export ("screenlet:onAddCommentError:")]
	void OnAddCommentError (CommentAddScreenlet screenlet, NSError error);

	// @optional -(void)screenlet:(CommentAddScreenlet * _Nonnull)screenlet onCommentUpdated:(Comment * _Nonnull)comment;
	[Export ("screenlet:onCommentUpdated:")]
	void OnCommentUpdated (CommentAddScreenlet screenlet, Comment comment);

	// @optional -(void)screenlet:(CommentAddScreenlet * _Nonnull)screenlet onUpdateCommentError:(NSError * _Nonnull)error;
	[Export ("screenlet:onUpdateCommentError:")]
	void OnUpdateCommentError (CommentAddScreenlet screenlet, NSError error);
}

// @protocol CommentAddViewModel
[Protocol, Model]
interface CommentAddViewModel
{
	// @required @property (copy, nonatomic) NSString * _Nonnull body;
	[Abstract]
	[Export ("body")]
	string Body { get; set; }
}

// @interface CommentAddView_default : BaseScreenletView <CommentAddViewModel>
[BaseType (typeof(BaseScreenletView))]
interface CommentAddView_default : ICommentAddViewModel
{
	// @property (copy, nonatomic) NSString * _Nonnull body;
	[Export ("body")]
	string Body { get; set; }

	// -(void)updateButton;
	[Export ("updateButton")]
	void UpdateButton ();

	// -(void)onShow;
	[Export ("onShow")]
	void OnShow ();

	// -(void)onSetTranslations;
	[Export ("onSetTranslations")]
	void OnSetTranslations ();

	// -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
	[Export ("createProgressPresenter")]
	[Verify (MethodToProperty)]
	ProgressPresenter CreateProgressPresenter { get; }

	// -(BOOL)textFieldShouldReturn:(UITextField * _Nonnull)textField __attribute__((warn_unused_result));
	[Export ("textFieldShouldReturn:")]
	bool TextFieldShouldReturn (UITextField textField);

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface CommentDeleteInteractor : ServerWriteConnectorInteractor
[BaseType (typeof(ServerWriteConnectorInteractor))]
interface CommentDeleteInteractor
{
	// -(instancetype _Nonnull)initWithScreenlet:(BaseScreenlet * _Nullable)screenlet __attribute__((objc_designated_initializer));
	[Export ("initWithScreenlet:")]
	[DesignatedInitializer]
	IntPtr Constructor ([NullAllowed] BaseScreenlet screenlet);

	// -(instancetype _Nonnull)initWithCommentId:(int64_t)commentId __attribute__((objc_designated_initializer));
	[Export ("initWithCommentId:")]
	[DesignatedInitializer]
	IntPtr Constructor (long commentId);

	// -(CommentDeleteLiferayConnector * _Nullable)createConnector __attribute__((warn_unused_result));
	[NullAllowed, Export ("createConnector")]
	[Verify (MethodToProperty)]
	CommentDeleteLiferayConnector CreateConnector { get; }

	// -(void)writeToCache:(ServerConnector * _Nonnull)c;
	[Export ("writeToCache:")]
	void WriteToCache (ServerConnector c);

	// -(void)callOnSuccess;
	[Export ("callOnSuccess")]
	void CallOnSuccess ();
}

// @interface CommentDeleteLiferayConnector : ServerConnector
[BaseType (typeof(ServerConnector))]
[DisableDefaultCtor]
interface CommentDeleteLiferayConnector
{
	// @property (readonly, nonatomic) int64_t commentId;
	[Export ("commentId")]
	long CommentId { get; }

	// -(instancetype _Nonnull)initWithCommentId:(int64_t)commentId __attribute__((objc_designated_initializer));
	[Export ("initWithCommentId:")]
	[DesignatedInitializer]
	IntPtr Constructor (long commentId);

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }
}

// @interface CommentDisplayScreenlet : BaseScreenlet
[BaseType (typeof(BaseScreenlet))]
interface CommentDisplayScreenlet
{
	// @property (readonly, copy, nonatomic, class) NSString * _Nonnull DeleteAction;
	[Static]
	[Export ("DeleteAction")]
	string DeleteAction { get; }

	// @property (readonly, copy, nonatomic, class) NSString * _Nonnull UpdateAction;
	[Static]
	[Export ("UpdateAction")]
	string UpdateAction { get; }

	// @property (nonatomic) int64_t commentId;
	[Export ("commentId")]
	long CommentId { get; set; }

	// @property (nonatomic) BOOL autoLoad;
	[Export ("autoLoad")]
	bool AutoLoad { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable offlinePolicy;
	[NullAllowed, Export ("offlinePolicy")]
	string OfflinePolicy { get; set; }

	// @property (nonatomic) BOOL editable;
	[Export ("editable")]
	bool Editable { get; set; }

	[Wrap ("WeakCommentDisplayDelegate")]
	[NullAllowed]
	CommentDisplayScreenletDelegate CommentDisplayDelegate { get; }

	// @property (readonly, nonatomic, strong) id<CommentDisplayScreenletDelegate> _Nullable commentDisplayDelegate;
	[NullAllowed, Export ("commentDisplayDelegate", ArgumentSemantic.Strong)]
	NSObject WeakCommentDisplayDelegate { get; }

	// @property (readonly, nonatomic, strong) id<CommentDisplayViewModel> _Nonnull viewModel;
	[Export ("viewModel", ArgumentSemantic.Strong)]
	CommentDisplayViewModel ViewModel { get; }

	// @property (nonatomic, strong) Comment * _Nullable comment;
	[NullAllowed, Export ("comment", ArgumentSemantic.Strong)]
	Comment Comment { get; set; }

	// -(void)load;
	[Export ("load")]
	void Load ();

	// -(void)deleteComment;
	[Export ("deleteComment")]
	void DeleteComment ();

	// -(void)editComment;
	[Export ("editComment")]
	void EditComment ();

	// -(void)onCreated;
	[Export ("onCreated")]
	void OnCreated ();

	// -(void)onShow;
	[Export ("onShow")]
	void OnShow ();

	// -(Interactor * _Nullable)createInteractorWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender __attribute__((warn_unused_result));
	[Export ("createInteractorWithName:sender:")]
	[return: NullAllowed]
	Interactor CreateInteractorWithName (string name, [NullAllowed] NSObject sender);

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:themeName:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame, [NullAllowed] string themeName);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @protocol CommentDisplayScreenletDelegate <BaseScreenletDelegate>
[Protocol, Model]
interface CommentDisplayScreenletDelegate : IBaseScreenletDelegate
{
	// @optional -(void)screenlet:(CommentDisplayScreenlet * _Nonnull)screenlet onCommentLoaded:(Comment * _Nonnull)comment;
	[Export ("screenlet:onCommentLoaded:")]
	void OnCommentLoaded (CommentDisplayScreenlet screenlet, Comment comment);

	// @optional -(void)screenlet:(CommentDisplayScreenlet * _Nonnull)screenlet onLoadCommentError:(NSError * _Nonnull)error;
	[Export ("screenlet:onLoadCommentError:")]
	void OnLoadCommentError (CommentDisplayScreenlet screenlet, NSError error);

	// @optional -(void)screenlet:(CommentDisplayScreenlet * _Nonnull)screenlet onCommentDeleted:(Comment * _Nonnull)comment;
	[Export ("screenlet:onCommentDeleted:")]
	void OnCommentDeleted (CommentDisplayScreenlet screenlet, Comment comment);

	// @optional -(void)screenlet:(CommentDisplayScreenlet * _Nonnull)screenlet onDeleteComment:(Comment * _Nonnull)comment onError:(NSError * _Nonnull)error;
	[Export ("screenlet:onDeleteComment:onError:")]
	void OnDeleteComment (CommentDisplayScreenlet screenlet, Comment comment, NSError error);

	// @optional -(void)screenlet:(CommentDisplayScreenlet * _Nonnull)screenlet onCommentUpdated:(Comment * _Nonnull)comment;
	[Export ("screenlet:onCommentUpdated:")]
	void OnCommentUpdated (CommentDisplayScreenlet screenlet, Comment comment);

	// @optional -(void)screenlet:(CommentDisplayScreenlet * _Nonnull)screenlet onUpdateComment:(Comment * _Nonnull)comment onError:(NSError * _Nonnull)error;
	[Export ("screenlet:onUpdateComment:onError:")]
	void OnUpdateComment (CommentDisplayScreenlet screenlet, Comment comment, NSError error);
}

// @protocol CommentDisplayViewModel
[Protocol, Model]
interface CommentDisplayViewModel
{
	// @required @property (nonatomic, strong) Comment * _Nullable comment;
	[Abstract]
	[NullAllowed, Export ("comment", ArgumentSemantic.Strong)]
	Comment Comment { get; set; }

	// @required -(void)editComment;
	[Abstract]
	[Export ("editComment")]
	void EditComment ();
}

// @interface CommentDisplayView_default : BaseScreenletView <CommentDisplayViewModel>
[BaseType (typeof(BaseScreenletView))]
interface CommentDisplayView_default : ICommentDisplayViewModel
{
	// -(void)editComment;
	[Export ("editComment")]
	void EditComment ();

	// @property (nonatomic, strong) Comment * _Nullable comment;
	[NullAllowed, Export ("comment", ArgumentSemantic.Strong)]
	Comment Comment { get; set; }

	// -(void)onSetTranslations;
	[Export ("onSetTranslations")]
	void OnSetTranslations ();

	// @property (nonatomic) BOOL editable;
	[Export ("editable")]
	bool Editable { get; set; }

	// -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
	[Export ("createProgressPresenter")]
	[Verify (MethodToProperty)]
	ProgressPresenter CreateProgressPresenter { get; }

	// -(void)confirmBodyClosure:(NSString * _Nullable)body;
	[Export ("confirmBodyClosure:")]
	void ConfirmBodyClosure ([NullAllowed] string body);

	// +(CGFloat)heightForText:(NSString * _Nullable)text width:(CGFloat)width __attribute__((warn_unused_result));
	[Static]
	[Export ("heightForText:width:")]
	nfloat HeightForText ([NullAllowed] string text, nfloat width);

	// +(NSDictionary<NSString *,NSObject *> * _Nonnull)defaultAttributedTextAttributes __attribute__((warn_unused_result));
	[Static]
	[Export ("defaultAttributedTextAttributes")]
	[Verify (MethodToProperty)]
	NSDictionary<NSString, NSObject> DefaultAttributedTextAttributes { get; }

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface CommentEditViewController_default : UIViewController <UIScrollViewDelegate, UITextViewDelegate>
[BaseType (typeof(UIViewController))]
interface CommentEditViewController_default : IUIScrollViewDelegate, IUITextViewDelegate
{
	// @property (nonatomic, strong) UITextView * _Nullable bodyTextView __attribute__((iboutlet));
	[NullAllowed, Export ("bodyTextView", ArgumentSemantic.Strong)]
	UITextView BodyTextView { get; set; }

	// @property (nonatomic, strong) UIButton * _Nullable confirmButton __attribute__((iboutlet));
	[NullAllowed, Export ("confirmButton", ArgumentSemantic.Strong)]
	UIButton ConfirmButton { get; set; }

	// @property (nonatomic, strong) UIButton * _Nullable cancelButton __attribute__((iboutlet));
	[NullAllowed, Export ("cancelButton", ArgumentSemantic.Strong)]
	UIButton CancelButton { get; set; }

	// @property (nonatomic, strong) UIScrollView * _Nullable scrollView __attribute__((iboutlet));
	[NullAllowed, Export ("scrollView", ArgumentSemantic.Strong)]
	UIScrollView ScrollView { get; set; }

	// @property (copy, nonatomic) void (^ _Nullable)(NSString * _Nullable) confirmBodyClosure;
	[NullAllowed, Export ("confirmBodyClosure", ArgumentSemantic.Copy)]
	Action<NSString> ConfirmBodyClosure { get; set; }

	// -(instancetype _Nonnull)initWithNibName:(NSString * _Nullable)nibNameOrNil bundle:(NSBundle * _Nullable)nibBundleOrNil __attribute__((objc_designated_initializer));
	[Export ("initWithNibName:bundle:")]
	[DesignatedInitializer]
	IntPtr Constructor ([NullAllowed] string nibNameOrNil, [NullAllowed] NSBundle nibBundleOrNil);

	// -(instancetype _Nonnull)initWithBody:(NSString * _Nullable)body;
	[Export ("initWithBody:")]
	IntPtr Constructor ([NullAllowed] string body);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);

	// -(void)viewDidLoad;
	[Export ("viewDidLoad")]
	void ViewDidLoad ();

	// -(void)textViewDidChange:(UITextView * _Nonnull)textView;
	[Export ("textViewDidChange:")]
	void TextViewDidChange (UITextView textView);

	// -(void)cancelButtonAction __attribute__((ibaction));
	[Export ("cancelButtonAction")]
	void CancelButtonAction ();

	// -(void)confirmButtonAction __attribute__((ibaction));
	[Export ("confirmButtonAction")]
	void ConfirmButtonAction ();
}

// @interface CommentListPageLiferayConnector : PaginationLiferayConnector
[BaseType (typeof(PaginationLiferayConnector))]
interface CommentListPageLiferayConnector
{
	// @property (readonly, copy, nonatomic) NSString * _Nonnull className;
	[Export ("className")]
	string ClassName { get; }

	// @property (readonly, nonatomic) int64_t classPK;
	[Export ("classPK")]
	long ClassPK { get; }

	// -(instancetype _Nonnull)initWithClassName:(NSString * _Nonnull)className classPK:(int64_t)classPK startRow:(NSInteger)startRow endRow:(NSInteger)endRow computeRowCount:(BOOL)computeRowCount __attribute__((objc_designated_initializer));
	[Export ("initWithClassName:classPK:startRow:endRow:computeRowCount:")]
	[DesignatedInitializer]
	IntPtr Constructor (string className, long classPK, nint startRow, nint endRow, bool computeRowCount);

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }
}

// @interface CommentListPageLoadInteractor : BaseListPageLoadInteractor
[BaseType (typeof(BaseListPageLoadInteractor))]
interface CommentListPageLoadInteractor
{
	// -(instancetype _Nonnull)initWithScreenlet:(BaseListScreenlet * _Nonnull)screenlet page:(NSInteger)page computeRowCount:(BOOL)computeRowCount className:(NSString * _Nonnull)className classPK:(int64_t)classPK __attribute__((objc_designated_initializer));
	[Export ("initWithScreenlet:page:computeRowCount:className:classPK:")]
	[DesignatedInitializer]
	IntPtr Constructor (BaseListScreenlet screenlet, nint page, bool computeRowCount, string className, long classPK);

	// -(PaginationLiferayConnector * _Nonnull)createListPageConnector __attribute__((warn_unused_result));
	[Export ("createListPageConnector")]
	[Verify (MethodToProperty)]
	PaginationLiferayConnector CreateListPageConnector { get; }

	// -(id _Nonnull)convertResult:(NSDictionary<NSString *,id> * _Nonnull)serverResult __attribute__((warn_unused_result));
	[Export ("convertResult:")]
	NSObject ConvertResult (NSDictionary<NSString, NSObject> serverResult);

	// -(NSString * _Nonnull)cacheKey:(PaginationLiferayConnector * _Nonnull)op __attribute__((warn_unused_result));
	[Export ("cacheKey:")]
	string CacheKey (PaginationLiferayConnector op);
}

// @interface CommentListScreenlet : BaseListScreenlet <CommentDisplayScreenletDelegate, BaseScreenletDelegate>
[BaseType (typeof(BaseListScreenlet))]
interface CommentListScreenlet : ICommentDisplayScreenletDelegate, IBaseScreenletDelegate
{
	// @property (copy, nonatomic) NSString * _Nonnull className;
	[Export ("className")]
	string ClassName { get; set; }

	// @property (nonatomic) int64_t classPK;
	[Export ("classPK")]
	long ClassPK { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable offlinePolicy;
	[NullAllowed, Export ("offlinePolicy")]
	string OfflinePolicy { get; set; }

	// @property (nonatomic) BOOL editable;
	[Export ("editable")]
	bool Editable { get; set; }

	// @property (readonly, nonatomic, strong) id<CommentListViewModel> _Nullable viewModel;
	[NullAllowed, Export ("viewModel", ArgumentSemantic.Strong)]
	CommentListViewModel ViewModel { get; }

	[Wrap ("WeakCommentListDelegate")]
	[NullAllowed]
	CommentListScreenletDelegate CommentListDelegate { get; }

	// @property (readonly, nonatomic, strong) id<CommentListScreenletDelegate> _Nullable commentListDelegate;
	[NullAllowed, Export ("commentListDelegate", ArgumentSemantic.Strong)]
	NSObject WeakCommentListDelegate { get; }

	// -(void)addComment:(Comment * _Nonnull)comment;
	[Export ("addComment:")]
	void AddComment (Comment comment);

	// -(void)deleteComment:(Comment * _Nonnull)comment;
	[Export ("deleteComment:")]
	void DeleteComment (Comment comment);

	// -(void)updateComment:(Comment * _Nonnull)comment;
	[Export ("updateComment:")]
	void UpdateComment (Comment comment);

	// -(void)onCreated;
	[Export ("onCreated")]
	void OnCreated ();

	// -(BaseListPageLoadInteractor * _Nonnull)createPageLoadInteractorWithPage:(NSInteger)page computeRowCount:(BOOL)computeRowCount __attribute__((warn_unused_result));
	[Export ("createPageLoadInteractorWithPage:computeRowCount:")]
	BaseListPageLoadInteractor CreatePageLoadInteractorWithPage (nint page, bool computeRowCount);

	// -(void)onLoadPageErrorWithPage:(NSInteger)page error:(NSError * _Nonnull)error;
	[Export ("onLoadPageErrorWithPage:error:")]
	void OnLoadPageErrorWithPage (nint page, NSError error);

	// -(void)onLoadPageResultWithPage:(NSInteger)page rows:(NSArray * _Nonnull)rows rowCount:(NSInteger)rowCount;
	[Export ("onLoadPageResultWithPage:rows:rowCount:")]
	[Verify (StronglyTypedNSArray)]
	void OnLoadPageResultWithPage (nint page, NSObject[] rows, nint rowCount);

	// -(void)onSelectedRow:(id _Nonnull)row;
	[Export ("onSelectedRow:")]
	void OnSelectedRow (NSObject row);

	// -(void)screenlet:(CommentDisplayScreenlet * _Nonnull)screenlet onCommentDeleted:(Comment * _Nonnull)comment;
	[Export ("screenlet:onCommentDeleted:")]
	void Screenlet (CommentDisplayScreenlet screenlet, Comment comment);

	// -(void)screenlet:(CommentDisplayScreenlet * _Nonnull)screenlet onDeleteComment:(Comment * _Nonnull)comment onError:(NSError * _Nonnull)error;
	[Export ("screenlet:onDeleteComment:onError:")]
	void Screenlet (CommentDisplayScreenlet screenlet, Comment comment, NSError error);

	// -(void)screenlet:(CommentDisplayScreenlet * _Nonnull)screenlet onCommentUpdated:(Comment * _Nonnull)comment;
	[Export ("screenlet:onCommentUpdated:")]
	void Screenlet (CommentDisplayScreenlet screenlet, Comment comment);

	// -(void)screenlet:(CommentDisplayScreenlet * _Nonnull)screenlet onUpdateComment:(Comment * _Nonnull)comment onError:(NSError * _Nonnull)error;
	[Export ("screenlet:onUpdateComment:onError:")]
	void Screenlet (CommentDisplayScreenlet screenlet, Comment comment, NSError error);

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:themeName:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame, [NullAllowed] string themeName);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @protocol CommentListScreenletDelegate <BaseScreenletDelegate>
[Protocol, Model]
interface CommentListScreenletDelegate : IBaseScreenletDelegate
{
	// @optional -(void)screenlet:(CommentListScreenlet * _Nonnull)screenlet onListResponseComments:(NSArray<Comment *> * _Nonnull)comments;
	[Export ("screenlet:onListResponseComments:")]
	void OnListResponseComments (CommentListScreenlet screenlet, Comment[] comments);

	// @optional -(void)screenlet:(CommentListScreenlet * _Nonnull)screenlet onCommentListError:(NSError * _Nonnull)error;
	[Export ("screenlet:onCommentListError:")]
	void OnCommentListError (CommentListScreenlet screenlet, NSError error);

	// @optional -(void)screenlet:(CommentListScreenlet * _Nonnull)screenlet onSelectedComment:(Comment * _Nonnull)comment;
	[Export ("screenlet:onSelectedComment:")]
	void OnSelectedComment (CommentListScreenlet screenlet, Comment comment);

	// @optional -(void)screenlet:(CommentListScreenlet * _Nonnull)screenlet onDeletedComment:(Comment * _Nonnull)comment;
	[Export ("screenlet:onDeletedComment:")]
	void OnDeletedComment (CommentListScreenlet screenlet, Comment comment);

	// @optional -(void)screenlet:(CommentListScreenlet * _Nonnull)screenlet onCommentDelete:(Comment * _Nonnull)comment onError:(NSError * _Nonnull)error;
	[Export ("screenlet:onCommentDelete:onError:")]
	void OnCommentDelete (CommentListScreenlet screenlet, Comment comment, NSError error);

	// @optional -(void)screenlet:(CommentListScreenlet * _Nonnull)screenlet onUpdatedComment:(Comment * _Nonnull)comment;
	[Export ("screenlet:onUpdatedComment:")]
	void OnUpdatedComment (CommentListScreenlet screenlet, Comment comment);

	// @optional -(void)screenlet:(CommentListScreenlet * _Nonnull)screenlet onCommentUpdate:(Comment * _Nonnull)comment onError:(NSError * _Nonnull)error;
	[Export ("screenlet:onCommentUpdate:onError:")]
	void OnCommentUpdate (CommentListScreenlet screenlet, Comment comment, NSError error);
}

// @protocol CommentListViewModel
[Protocol, Model]
interface CommentListViewModel
{
	// @required -(void)addComment:(Comment * _Nonnull)comment;
	[Abstract]
	[Export ("addComment:")]
	void AddComment (Comment comment);

	// @required -(void)deleteComment:(Comment * _Nonnull)comment;
	[Abstract]
	[Export ("deleteComment:")]
	void DeleteComment (Comment comment);

	// @required -(void)updateComment:(Comment * _Nonnull)comment;
	[Abstract]
	[Export ("updateComment:")]
	void UpdateComment (Comment comment);
}

// @interface CommentListView_default : BaseListTableView <CommentListViewModel>
[BaseType (typeof(BaseListTableView))]
interface CommentListView_default : ICommentListViewModel
{
	// -(void)onShow;
	[Export ("onShow")]
	void OnShow ();

	// -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
	[Export ("createProgressPresenter")]
	[Verify (MethodToProperty)]
	ProgressPresenter CreateProgressPresenter { get; }

	// -(void)addComment:(Comment * _Nonnull)comment;
	[Export ("addComment:")]
	void AddComment (Comment comment);

	// -(void)deleteComment:(Comment * _Nonnull)comment;
	[Export ("deleteComment:")]
	void DeleteComment (Comment comment);

	// -(void)updateComment:(Comment * _Nonnull)comment;
	[Export ("updateComment:")]
	void UpdateComment (Comment comment);

	// -(void)doRegisterCellNibs;
	[Export ("doRegisterCellNibs")]
	void DoRegisterCellNibs ();

	// -(UITableViewCell * _Nonnull)doDequeueReusableCellWithRow:(NSInteger)row object:(id _Nullable)object __attribute__((warn_unused_result));
	[Export ("doDequeueReusableCellWithRow:object:")]
	UITableViewCell DoDequeueReusableCellWithRow (nint row, [NullAllowed] NSObject @object);

	// -(NSString * _Nonnull)doGetCellIdWithRow:(NSInteger)row object:(id _Nullable)object __attribute__((warn_unused_result));
	[Export ("doGetCellIdWithRow:object:")]
	string DoGetCellIdWithRow (nint row, [NullAllowed] NSObject @object);

	// -(void)doFillLoadedCellWithRow:(NSInteger)row cell:(UITableViewCell * _Nonnull)cell object:(id _Nonnull)object;
	[Export ("doFillLoadedCellWithRow:cell:object:")]
	void DoFillLoadedCellWithRow (nint row, UITableViewCell cell, NSObject @object);

	// -(void)doFillInProgressCellWithRow:(NSInteger)row cell:(UITableViewCell * _Nonnull)cell;
	[Export ("doFillInProgressCellWithRow:cell:")]
	void DoFillInProgressCellWithRow (nint row, UITableViewCell cell);

	// -(CGFloat)tableView:(UITableView * _Nonnull)tableView heightForRowAtIndexPath:(NSIndexPath * _Nonnull)indexPath __attribute__((warn_unused_result));
	[Export ("tableView:heightForRowAtIndexPath:")]
	nfloat TableView (UITableView tableView, NSIndexPath indexPath);

	// -(BOOL)tableView:(UITableView * _Nonnull)tableView canEditRowAtIndexPath:(NSIndexPath * _Nonnull)indexPath __attribute__((warn_unused_result));
	[Export ("tableView:canEditRowAtIndexPath:")]
	bool TableView (UITableView tableView, NSIndexPath indexPath);

	// -(NSArray * _Nullable)tableView:(UITableView * _Nonnull)tableView editActionsForRowAtIndexPath:(NSIndexPath * _Nonnull)indexPath __attribute__((warn_unused_result));
	[Export ("tableView:editActionsForRowAtIndexPath:")]
	[Verify (StronglyTypedNSArray)]
	[return: NullAllowed]
	NSObject[] TableView (UITableView tableView, NSIndexPath indexPath);

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface CommentLoadInteractor : ServerReadConnectorInteractor
[BaseType (typeof(ServerReadConnectorInteractor))]
interface CommentLoadInteractor
{
	// @property (nonatomic, strong) Comment * _Nullable resultComment;
	[NullAllowed, Export ("resultComment", ArgumentSemantic.Strong)]
	Comment ResultComment { get; set; }

	// -(CommentLoadLiferayConnector * _Nullable)createConnector __attribute__((warn_unused_result));
	[NullAllowed, Export ("createConnector")]
	[Verify (MethodToProperty)]
	CommentLoadLiferayConnector CreateConnector { get; }

	// -(void)completedConnector:(ServerConnector * _Nonnull)c;
	[Export ("completedConnector:")]
	void CompletedConnector (ServerConnector c);

	// -(void)readFromCache:(ServerConnector * _Nonnull)c result:(void (^ _Nonnull)(id _Nullable))result;
	[Export ("readFromCache:result:")]
	void ReadFromCache (ServerConnector c, Action<NSObject> result);

	// -(void)writeToCache:(ServerConnector * _Nonnull)c;
	[Export ("writeToCache:")]
	void WriteToCache (ServerConnector c);
}

// @interface CommentLoadLiferayConnector : ServerConnector
[BaseType (typeof(ServerConnector))]
[DisableDefaultCtor]
interface CommentLoadLiferayConnector
{
	// @property (readonly, nonatomic) int64_t commentId;
	[Export ("commentId")]
	long CommentId { get; }

	// @property (nonatomic, strong) Comment * _Nullable resultComment;
	[NullAllowed, Export ("resultComment", ArgumentSemantic.Strong)]
	Comment ResultComment { get; set; }

	// -(instancetype _Nonnull)initWithCommentId:(int64_t)commentId __attribute__((objc_designated_initializer));
	[Export ("initWithCommentId:")]
	[DesignatedInitializer]
	IntPtr Constructor (long commentId);

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }
}

// @interface CommentTableViewCell_default : UITableViewCell
[BaseType (typeof(UITableViewCell))]
interface CommentTableViewCell_default
{
	// @property (nonatomic, weak) CommentDisplayScreenlet * _Nullable commentDisplayScreenlet __attribute__((iboutlet));
	[NullAllowed, Export ("commentDisplayScreenlet", ArgumentSemantic.Weak)]
	CommentDisplayScreenlet CommentDisplayScreenlet { get; set; }

	// -(instancetype _Nonnull)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString * _Nullable)reuseIdentifier __attribute__((availability(ios, introduced=3.0))) __attribute__((objc_designated_initializer));
	[iOS (3,0)]
	[Export ("initWithStyle:reuseIdentifier:")]
	[DesignatedInitializer]
	IntPtr Constructor (UITableViewCellStyle style, [NullAllowed] string reuseIdentifier);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface CommentUpdateInteractor : ServerWriteConnectorInteractor
[BaseType (typeof(ServerWriteConnectorInteractor))]
interface CommentUpdateInteractor
{
	// @property (nonatomic, strong) Comment * _Nullable resultComment;
	[NullAllowed, Export ("resultComment", ArgumentSemantic.Strong)]
	Comment ResultComment { get; set; }

	// -(CommentUpdateLiferayConnector * _Nullable)createConnector __attribute__((warn_unused_result));
	[NullAllowed, Export ("createConnector")]
	[Verify (MethodToProperty)]
	CommentUpdateLiferayConnector CreateConnector { get; }

	// -(void)completedConnector:(ServerConnector * _Nonnull)c;
	[Export ("completedConnector:")]
	void CompletedConnector (ServerConnector c);

	// -(void)writeToCache:(ServerConnector * _Nonnull)c;
	[Export ("writeToCache:")]
	void WriteToCache (ServerConnector c);

	// -(void)callOnSuccess;
	[Export ("callOnSuccess")]
	void CallOnSuccess ();
}

// @interface CommentUpdateLiferayConnector : ServerConnector
[BaseType (typeof(ServerConnector))]
[DisableDefaultCtor]
interface CommentUpdateLiferayConnector
{
	// @property (readonly, nonatomic) int64_t commentId;
	[Export ("commentId")]
	long CommentId { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull body;
	[Export ("body")]
	string Body { get; }

	// @property (nonatomic, strong) Comment * _Nullable resultComment;
	[NullAllowed, Export ("resultComment", ArgumentSemantic.Strong)]
	Comment ResultComment { get; set; }

	// -(instancetype _Nonnull)initWithCommentId:(int64_t)commentId body:(NSString * _Nonnull)body __attribute__((objc_designated_initializer));
	[Export ("initWithCommentId:body:")]
	[DesignatedInitializer]
	IntPtr Constructor (long commentId, string body);

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }
}

// @interface CookieCredentialsStoreKeyChain : BaseCredentialsStoreKeyChain
[BaseType (typeof(BaseCredentialsStoreKeyChain))]
interface CookieCredentialsStoreKeyChain
{
}

// @interface CredentialsStorage : NSObject
[BaseType (typeof(NSObject))]
[DisableDefaultCtor]
interface CredentialsStorage
{
	// @property (readonly, nonatomic, strong) id<CredentialsStore> _Nonnull credentialsStore;
	[Export ("credentialsStore", ArgumentSemantic.Strong)]
	CredentialsStore CredentialsStore { get; }

	// -(instancetype _Nonnull)initWithStore:(id<CredentialsStore> _Nonnull)store __attribute__((objc_designated_initializer));
	[Export ("initWithStore:")]
	[DesignatedInitializer]
	IntPtr Constructor (CredentialsStore store);

	// +(CredentialsStorage * _Nullable)createFromStoredAuthType __attribute__((warn_unused_result));
	[Static]
	[NullAllowed, Export ("createFromStoredAuthType")]
	[Verify (MethodToProperty)]
	CredentialsStorage CreateFromStoredAuthType { get; }

	// -(BOOL)storeWithSession:(LRSession * _Nullable)session userAttributes:(NSDictionary<NSString *,id> * _Nonnull)userAttributes __attribute__((warn_unused_result));
	[Export ("storeWithSession:userAttributes:")]
	bool StoreWithSession ([NullAllowed] LRSession session, NSDictionary<NSString, NSObject> userAttributes);

	// -(BOOL)remove __attribute__((warn_unused_result));
	[Export ("remove")]
	[Verify (MethodToProperty)]
	bool Remove { get; }
}

// @interface DDMFieldTableCell : UITableViewCell
[BaseType (typeof(UITableViewCell))]
interface DDMFieldTableCell
{
	// +(DDMFieldTableCell * _Nullable)viewAsFieldCell:(UIView * _Nullable)view __attribute__((warn_unused_result));
	[Static]
	[Export ("viewAsFieldCell:")]
	[return: NullAllowed]
	DDMFieldTableCell ViewAsFieldCell ([NullAllowed] UIView view);

	// @property (nonatomic, strong) UITableView * _Nullable tableView;
	[NullAllowed, Export ("tableView", ArgumentSemantic.Strong)]
	UITableView TableView { get; set; }

	// @property (copy, nonatomic) NSIndexPath * _Nullable indexPath;
	[NullAllowed, Export ("indexPath", ArgumentSemantic.Copy)]
	NSIndexPath IndexPath { get; set; }

	// @property (nonatomic, strong) DDLFormTableView * _Nullable formView;
	[NullAllowed, Export ("formView", ArgumentSemantic.Strong)]
	DDLFormTableView FormView { get; set; }

	// @property (nonatomic, strong) DDMField * _Nullable field;
	[NullAllowed, Export ("field", ArgumentSemantic.Strong)]
	DDMField Field { get; set; }

	// @property (readonly, nonatomic) BOOL isLastCell;
	[Export ("isLastCell")]
	bool IsLastCell { get; }

	// @property (readonly, nonatomic) BOOL isFullyVisible;
	[Export ("isFullyVisible")]
	bool IsFullyVisible { get; }

	// -(void)awakeFromNib __attribute__((objc_requires_super));
	[Export ("awakeFromNib")]
	[RequiresSuper]
	void AwakeFromNib ();

	// -(void)onChangedField;
	[Export ("onChangedField")]
	void OnChangedField ();

	// -(void)onPostValidation:(BOOL)valid;
	[Export ("onPostValidation:")]
	void OnPostValidation (bool valid);

	// -(void)setCellHeight:(CGFloat)height;
	[Export ("setCellHeight:")]
	void SetCellHeight (nfloat height);

	// -(CGFloat)resetCellHeight __attribute__((warn_unused_result));
	[Export ("resetCellHeight")]
	[Verify (MethodToProperty)]
	nfloat ResetCellHeight { get; }

	// -(instancetype _Nonnull)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString * _Nullable)reuseIdentifier __attribute__((availability(ios, introduced=3.0))) __attribute__((objc_designated_initializer));
	[iOS (3,0)]
	[Export ("initWithStyle:reuseIdentifier:")]
	[DesignatedInitializer]
	IntPtr Constructor (UITableViewCellStyle style, [NullAllowed] string reuseIdentifier);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface DDLBaseFieldTextboxTableCell_default : DDMFieldTableCell <UITextFieldDelegate>
[BaseType (typeof(DDMFieldTableCell))]
interface DDLBaseFieldTextboxTableCell_default : IUITextFieldDelegate
{
	// @property (nonatomic, strong) UITextField * _Nullable textField __attribute__((iboutlet));
	[NullAllowed, Export ("textField", ArgumentSemantic.Strong)]
	UITextField TextField { get; set; }

	// @property (nonatomic, strong) UILabel * _Nullable label __attribute__((iboutlet));
	[NullAllowed, Export ("label", ArgumentSemantic.Strong)]
	UILabel Label { get; set; }

	// @property (readonly, nonatomic, strong) DefaultTextField * _Nullable defaultTextField;
	[NullAllowed, Export ("defaultTextField", ArgumentSemantic.Strong)]
	DefaultTextField DefaultTextField { get; }

	// -(void)onChangedField;
	[Export ("onChangedField")]
	void OnChangedField ();

	// -(void)onPostValidation:(BOOL)valid;
	[Export ("onPostValidation:")]
	void OnPostValidation (bool valid);

	// -(BOOL)textField:(UITextField * _Nonnull)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString * _Nonnull)string __attribute__((warn_unused_result));
	[Export ("textField:shouldChangeCharactersInRange:replacementString:")]
	bool TextField (UITextField textField, NSRange range, string @string);

	// -(instancetype _Nonnull)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString * _Nullable)reuseIdentifier __attribute__((availability(ios, introduced=3.0))) __attribute__((objc_designated_initializer));
	[iOS (3,0)]
	[Export ("initWithStyle:reuseIdentifier:")]
	[DesignatedInitializer]
	IntPtr Constructor (UITableViewCellStyle style, [NullAllowed] string reuseIdentifier);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface DDLFieldCheckboxTableCell_default : DDMFieldTableCell
[BaseType (typeof(DDMFieldTableCell))]
interface DDLFieldCheckboxTableCell_default
{
	// @property (nonatomic, strong) UISwitch * _Nullable switchView __attribute__((iboutlet));
	[NullAllowed, Export ("switchView", ArgumentSemantic.Strong)]
	UISwitch SwitchView { get; set; }

	// @property (nonatomic, strong) UILabel * _Nullable label __attribute__((iboutlet));
	[NullAllowed, Export ("label", ArgumentSemantic.Strong)]
	UILabel Label { get; set; }

	// @property (readonly, nonatomic) BOOL canBecomeFirstResponder;
	[Export ("canBecomeFirstResponder")]
	bool CanBecomeFirstResponder { get; }

	// -(void)onChangedField;
	[Export ("onChangedField")]
	void OnChangedField ();

	// -(void)onPostValidation:(BOOL)valid;
	[Export ("onPostValidation:")]
	void OnPostValidation (bool valid);

	// -(instancetype _Nonnull)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString * _Nullable)reuseIdentifier __attribute__((availability(ios, introduced=3.0))) __attribute__((objc_designated_initializer));
	[iOS (3,0)]
	[Export ("initWithStyle:reuseIdentifier:")]
	[DesignatedInitializer]
	IntPtr Constructor (UITableViewCellStyle style, [NullAllowed] string reuseIdentifier);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface DDLFieldDateTableCell_default : DDLBaseFieldTextboxTableCell_default
[BaseType (typeof(DDLBaseFieldTextboxTableCell_default))]
interface DDLFieldDateTableCell_default
{
	// -(void)awakeFromNib __attribute__((objc_requires_super));
	[Export ("awakeFromNib")]
	[RequiresSuper]
	void AwakeFromNib ();

	// -(void)onChangedField;
	[Export ("onChangedField")]
	void OnChangedField ();

	// -(instancetype _Nonnull)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString * _Nullable)reuseIdentifier __attribute__((availability(ios, introduced=3.0))) __attribute__((objc_designated_initializer));
	[iOS (3,0)]
	[Export ("initWithStyle:reuseIdentifier:")]
	[DesignatedInitializer]
	IntPtr Constructor (UITableViewCellStyle style, [NullAllowed] string reuseIdentifier);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface DDLFieldDocumentlibraryTableCell_default : DDLBaseFieldTextboxTableCell_default
[BaseType (typeof(DDLBaseFieldTextboxTableCell_default))]
interface DDLFieldDocumentlibraryTableCell_default
{
	// @property (nonatomic, strong) MDRadialProgressView * _Nullable progress __attribute__((iboutlet));
	[NullAllowed, Export ("progress", ArgumentSemantic.Strong)]
	MDRadialProgressView Progress { get; set; }

	// -(void)onChangedField;
	[Export ("onChangedField")]
	void OnChangedField ();

	// -(instancetype _Nonnull)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString * _Nullable)reuseIdentifier __attribute__((availability(ios, introduced=3.0))) __attribute__((objc_designated_initializer));
	[iOS (3,0)]
	[Export ("initWithStyle:reuseIdentifier:")]
	[DesignatedInitializer]
	IntPtr Constructor (UITableViewCellStyle style, [NullAllowed] string reuseIdentifier);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface DDLFieldNumberTableCell_default : DDLBaseFieldTextboxTableCell_default
[BaseType (typeof(DDLBaseFieldTextboxTableCell_default))]
interface DDLFieldNumberTableCell_default
{
	// @property (nonatomic, strong) UIStepper * _Nullable stepper __attribute__((iboutlet));
	[NullAllowed, Export ("stepper", ArgumentSemantic.Strong)]
	UIStepper Stepper { get; set; }

	// -(void)awakeFromNib __attribute__((objc_requires_super));
	[Export ("awakeFromNib")]
	[RequiresSuper]
	void AwakeFromNib ();

	// -(void)onChangedField;
	[Export ("onChangedField")]
	void OnChangedField ();

	// -(BOOL)textField:(UITextField * _Nonnull)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString * _Nonnull)string __attribute__((warn_unused_result));
	[Export ("textField:shouldChangeCharactersInRange:replacementString:")]
	bool TextField (UITextField textField, NSRange range, string @string);

	// -(BOOL)textFieldShouldReturn:(UITextField * _Nonnull)textField __attribute__((warn_unused_result));
	[Export ("textFieldShouldReturn:")]
	bool TextFieldShouldReturn (UITextField textField);

	// -(instancetype _Nonnull)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString * _Nullable)reuseIdentifier __attribute__((availability(ios, introduced=3.0))) __attribute__((objc_designated_initializer));
	[iOS (3,0)]
	[Export ("initWithStyle:reuseIdentifier:")]
	[DesignatedInitializer]
	IntPtr Constructor (UITableViewCellStyle style, [NullAllowed] string reuseIdentifier);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface DDLFieldRadioTableCell_default : DDMFieldTableCell
[BaseType (typeof(DDMFieldTableCell))]
interface DDLFieldRadioTableCell_default
{
	// @property (nonatomic, strong) UILabel * _Nullable label __attribute__((iboutlet));
	[NullAllowed, Export ("label", ArgumentSemantic.Strong)]
	UILabel Label { get; set; }

	// @property (nonatomic, strong) UILabel * _Nullable radioReferenceLabel __attribute__((iboutlet));
	[NullAllowed, Export ("radioReferenceLabel", ArgumentSemantic.Strong)]
	UILabel RadioReferenceLabel { get; set; }

	// @property (nonatomic, strong) UIView * _Nullable separator __attribute__((iboutlet));
	[NullAllowed, Export ("separator", ArgumentSemantic.Strong)]
	UIView Separator { get; set; }

	// @property (nonatomic, strong) TNRadioButtonGroup * _Nullable radioGroup;
	[NullAllowed, Export ("radioGroup", ArgumentSemantic.Strong)]
	TNRadioButtonGroup RadioGroup { get; set; }

	// @property (readonly, nonatomic, strong) UIColor * _Nonnull radioColor;
	[Export ("radioColor", ArgumentSemantic.Strong)]
	UIColor RadioColor { get; }

	// @property (readonly, nonatomic, strong) UIColor * _Nonnull radioTextColor;
	[Export ("radioTextColor", ArgumentSemantic.Strong)]
	UIColor RadioTextColor { get; }

	// @property (readonly, nonatomic, strong) UIColor * _Nonnull invalidRadioColor;
	[Export ("invalidRadioColor", ArgumentSemantic.Strong)]
	UIColor InvalidRadioColor { get; }

	// @property (readonly, nonatomic, strong) UIColor * _Nonnull invalidRadioTextColor;
	[Export ("invalidRadioTextColor", ArgumentSemantic.Strong)]
	UIColor InvalidRadioTextColor { get; }

	// @property (readonly, nonatomic) NSInteger radioButtonWidth;
	[Export ("radioButtonWidth")]
	nint RadioButtonWidth { get; }

	// @property (readonly, nonatomic) BOOL canBecomeFirstResponder;
	[Export ("canBecomeFirstResponder")]
	bool CanBecomeFirstResponder { get; }

	// -(void)onChangedField;
	[Export ("onChangedField")]
	void OnChangedField ();

	// -(void)onPostValidation:(BOOL)valid;
	[Export ("onPostValidation:")]
	void OnPostValidation (bool valid);

	// -(void)createRadioButtons:(DDMFieldStringWithOptions * _Nonnull)field;
	[Export ("createRadioButtons:")]
	void CreateRadioButtons (DDMFieldStringWithOptions field);

	// -(TNRectangularRadioButtonData * _Nonnull)createRadioButtonData:(DDMFieldStringWithOptions * _Nonnull)field option:(Option * _Nonnull)option __attribute__((warn_unused_result));
	[Export ("createRadioButtonData:option:")]
	TNRectangularRadioButtonData CreateRadioButtonData (DDMFieldStringWithOptions field, Option option);

	// -(void)radioButtonSelected:(NSNotification * _Nonnull)notification;
	[Export ("radioButtonSelected:")]
	void RadioButtonSelected (NSNotification notification);

	// -(void)clearObserver;
	[Export ("clearObserver")]
	void ClearObserver ();

	// -(instancetype _Nonnull)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString * _Nullable)reuseIdentifier __attribute__((availability(ios, introduced=3.0))) __attribute__((objc_designated_initializer));
	[iOS (3,0)]
	[Export ("initWithStyle:reuseIdentifier:")]
	[DesignatedInitializer]
	IntPtr Constructor (UITableViewCellStyle style, [NullAllowed] string reuseIdentifier);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface DDLFieldSelectTableCell_default : DDLBaseFieldTextboxTableCell_default <UIScrollViewDelegate, UITableViewDelegate, UITableViewDataSource>
[BaseType (typeof(DDLBaseFieldTextboxTableCell_default))]
interface DDLFieldSelectTableCell_default : IUIScrollViewDelegate, IUITableViewDelegate, IUITableViewDataSource
{
	// @property (nonatomic, strong) UIColor * _Nonnull cellBackgroundColor;
	[Export ("cellBackgroundColor", ArgumentSemantic.Strong)]
	UIColor CellBackgroundColor { get; set; }

	// @property (copy, nonatomic) NSArray<Option *> * _Nonnull options;
	[Export ("options", ArgumentSemantic.Copy)]
	Option[] Options { get; set; }

	// @property (readonly, copy, nonatomic) NSArray<Option *> * _Nonnull selectedOptions;
	[Export ("selectedOptions", ArgumentSemantic.Copy)]
	Option[] SelectedOptions { get; }

	// -(void)awakeFromNib __attribute__((objc_requires_super));
	[Export ("awakeFromNib")]
	[RequiresSuper]
	void AwakeFromNib ();

	// -(void)onChangedField;
	[Export ("onChangedField")]
	void OnChangedField ();

	// -(UITableView * _Nonnull)createPresenterTableViewWithMultipleSelection:(BOOL)multiple __attribute__((warn_unused_result));
	[Export ("createPresenterTableViewWithMultipleSelection:")]
	UITableView CreatePresenterTableViewWithMultipleSelection (bool multiple);

	// -(NSInteger)tableView:(UITableView * _Nonnull)tableView numberOfRowsInSection:(NSInteger)section __attribute__((warn_unused_result));
	[Export ("tableView:numberOfRowsInSection:")]
	nint TableView (UITableView tableView, nint section);

	// -(UITableViewCell * _Nonnull)tableView:(UITableView * _Nonnull)tableView cellForRowAtIndexPath:(NSIndexPath * _Nonnull)indexPath __attribute__((warn_unused_result));
	[Export ("tableView:cellForRowAtIndexPath:")]
	UITableViewCell TableView (UITableView tableView, NSIndexPath indexPath);

	// -(void)tableView:(UITableView * _Nonnull)tableView didSelectRowAtIndexPath:(NSIndexPath * _Nonnull)indexPath;
	[Export ("tableView:didSelectRowAtIndexPath:")]
	void TableView (UITableView tableView, NSIndexPath indexPath);

	// -(void)tableView:(UITableView * _Nonnull)tableView didDeselectRowAtIndexPath:(NSIndexPath * _Nonnull)indexPath;
	[Export ("tableView:didDeselectRowAtIndexPath:")]
	void TableView (UITableView tableView, NSIndexPath indexPath);

	// -(void)updateFieldCurrentValue:(NSArray<NSIndexPath *> * _Nullable)selectedIndexPaths;
	[Export ("updateFieldCurrentValue:")]
	void UpdateFieldCurrentValue ([NullAllowed] NSIndexPath[] selectedIndexPaths);

	// -(instancetype _Nonnull)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString * _Nullable)reuseIdentifier __attribute__((availability(ios, introduced=3.0))) __attribute__((objc_designated_initializer));
	[iOS (3,0)]
	[Export ("initWithStyle:reuseIdentifier:")]
	[DesignatedInitializer]
	IntPtr Constructor (UITableViewCellStyle style, [NullAllowed] string reuseIdentifier);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface DDLFieldTextTableCell_default : DDLBaseFieldTextboxTableCell_default
[BaseType (typeof(DDLBaseFieldTextboxTableCell_default))]
interface DDLFieldTextTableCell_default
{
	// -(BOOL)textField:(UITextField * _Nonnull)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString * _Nonnull)string __attribute__((warn_unused_result));
	[Export ("textField:shouldChangeCharactersInRange:replacementString:")]
	bool TextField (UITextField textField, NSRange range, string @string);

	// -(BOOL)textFieldShouldReturn:(UITextField * _Nonnull)textField __attribute__((warn_unused_result));
	[Export ("textFieldShouldReturn:")]
	bool TextFieldShouldReturn (UITextField textField);

	// -(instancetype _Nonnull)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString * _Nullable)reuseIdentifier __attribute__((availability(ios, introduced=3.0))) __attribute__((objc_designated_initializer));
	[iOS (3,0)]
	[Export ("initWithStyle:reuseIdentifier:")]
	[DesignatedInitializer]
	IntPtr Constructor (UITableViewCellStyle style, [NullAllowed] string reuseIdentifier);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface DDLFieldTextareaTableCell_default : DDMFieldTableCell <UIScrollViewDelegate, UITextViewDelegate>
[BaseType (typeof(DDMFieldTableCell))]
interface DDLFieldTextareaTableCell_default : IUIScrollViewDelegate, IUITextViewDelegate
{
	// @property (nonatomic, strong) UITextView * _Nullable textView __attribute__((iboutlet));
	[NullAllowed, Export ("textView", ArgumentSemantic.Strong)]
	UITextView TextView { get; set; }

	// @property (nonatomic, strong) UILabel * _Nullable placeholder __attribute__((iboutlet));
	[NullAllowed, Export ("placeholder", ArgumentSemantic.Strong)]
	UILabel Placeholder { get; set; }

	// @property (nonatomic, strong) UILabel * _Nullable label __attribute__((iboutlet));
	[NullAllowed, Export ("label", ArgumentSemantic.Strong)]
	UILabel Label { get; set; }

	// @property (nonatomic, strong) UIView * _Nullable separator __attribute__((iboutlet));
	[NullAllowed, Export ("separator", ArgumentSemantic.Strong)]
	UIView Separator { get; set; }

	// @property (readonly, nonatomic) BOOL canBecomeFirstResponder;
	[Export ("canBecomeFirstResponder")]
	bool CanBecomeFirstResponder { get; }

	// -(BOOL)becomeFirstResponder __attribute__((warn_unused_result));
	[Export ("becomeFirstResponder")]
	[Verify (MethodToProperty)]
	bool BecomeFirstResponder { get; }

	// -(void)onChangedField;
	[Export ("onChangedField")]
	void OnChangedField ();

	// -(void)onPostValidation:(BOOL)valid;
	[Export ("onPostValidation:")]
	void OnPostValidation (bool valid);

	// -(BOOL)textViewShouldBeginEditing:(UITextView * _Nonnull)textView __attribute__((warn_unused_result));
	[Export ("textViewShouldBeginEditing:")]
	bool TextViewShouldBeginEditing (UITextView textView);

	// -(void)textViewDidEndEditing:(UITextView * _Nonnull)textView;
	[Export ("textViewDidEndEditing:")]
	void TextViewDidEndEditing (UITextView textView);

	// -(BOOL)textView:(UITextView * _Nonnull)textView shouldChangeTextInRange:(NSRange)range replacementText:(NSString * _Nonnull)text __attribute__((warn_unused_result));
	[Export ("textView:shouldChangeTextInRange:replacementText:")]
	bool TextView (UITextView textView, NSRange range, string text);

	// -(instancetype _Nonnull)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString * _Nullable)reuseIdentifier __attribute__((availability(ios, introduced=3.0))) __attribute__((objc_designated_initializer));
	[iOS (3,0)]
	[Export ("initWithStyle:reuseIdentifier:")]
	[DesignatedInitializer]
	IntPtr Constructor (UITableViewCellStyle style, [NullAllowed] string reuseIdentifier);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface DDLFormLoadLiferayConnector : ServerConnector
[BaseType (typeof(ServerConnector))]
[DisableDefaultCtor]
interface DDLFormLoadLiferayConnector
{
	// @property (readonly, nonatomic) int64_t structureId;
	[Export ("structureId")]
	long StructureId { get; }

	// @property (nonatomic, strong) DDLRecord * _Nullable resultRecord;
	[NullAllowed, Export ("resultRecord", ArgumentSemantic.Strong)]
	DDLRecord ResultRecord { get; set; }

	// -(instancetype _Nonnull)initWithStructureId:(int64_t)structureId __attribute__((objc_designated_initializer));
	[Export ("initWithStructureId:")]
	[DesignatedInitializer]
	IntPtr Constructor (long structureId);
}

// @interface DDLFormRecordLoadLiferayConnector : ServerConnector
[BaseType (typeof(ServerConnector))]
[DisableDefaultCtor]
interface DDLFormRecordLoadLiferayConnector
{
	// @property (readonly, nonatomic) int64_t recordId;
	[Export ("recordId")]
	long RecordId { get; }

	// @property (copy, nonatomic) NSDictionary<NSString *,id> * _Nullable resultRecordData;
	[NullAllowed, Export ("resultRecordData", ArgumentSemantic.Copy)]
	NSDictionary<NSString, NSObject> ResultRecordData { get; set; }

	// @property (copy, nonatomic) NSDictionary<NSString *,id> * _Nullable resultRecordAttributes;
	[NullAllowed, Export ("resultRecordAttributes", ArgumentSemantic.Copy)]
	NSDictionary<NSString, NSObject> ResultRecordAttributes { get; set; }

	// -(instancetype _Nonnull)initWithRecordId:(int64_t)recordId __attribute__((objc_designated_initializer));
	[Export ("initWithRecordId:")]
	[DesignatedInitializer]
	IntPtr Constructor (long recordId);

	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(NSDictionary * _Nullable)getRecord:(LRSession * _Nonnull)session recordId:(int64_t)recordId locale:(NSString * _Nonnull)locale error:(NSError * _Nullable * _Nullable)error __attribute__((warn_unused_result));
	[Export ("getRecord:recordId:locale:error:")]
	[return: NullAllowed]
	NSDictionary GetRecord (LRSession session, long recordId, string locale, [NullAllowed] out NSError error);
}

// @interface DDLFormScreenlet : BaseScreenlet
[BaseType (typeof(BaseScreenlet))]
interface DDLFormScreenlet
{
	// @property (readonly, copy, nonatomic, class) NSString * _Nonnull LoadFormAction;
	[Static]
	[Export ("LoadFormAction")]
	string LoadFormAction { get; }

	// @property (readonly, copy, nonatomic, class) NSString * _Nonnull LoadRecordAction;
	[Static]
	[Export ("LoadRecordAction")]
	string LoadRecordAction { get; }

	// @property (readonly, copy, nonatomic, class) NSString * _Nonnull SubmitFormAction;
	[Static]
	[Export ("SubmitFormAction")]
	string SubmitFormAction { get; }

	// @property (readonly, copy, nonatomic, class) NSString * _Nonnull UploadDocumentAction;
	[Static]
	[Export ("UploadDocumentAction")]
	string UploadDocumentAction { get; }

	// @property (nonatomic) int64_t structureId;
	[Export ("structureId")]
	long StructureId { get; set; }

	// @property (nonatomic) int64_t groupId;
	[Export ("groupId")]
	long GroupId { get; set; }

	// @property (nonatomic) int64_t recordSetId;
	[Export ("recordSetId")]
	long RecordSetId { get; set; }

	// @property (nonatomic) int64_t recordId;
	[Export ("recordId")]
	long RecordId { get; set; }

	// @property (nonatomic) int64_t userId;
	[Export ("userId")]
	long UserId { get; set; }

	// @property (nonatomic) int64_t repositoryId;
	[Export ("repositoryId")]
	long RepositoryId { get; set; }

	// @property (nonatomic) int64_t folderId;
	[Export ("folderId")]
	long FolderId { get; set; }

	// @property (copy, nonatomic) NSString * _Nonnull filePrefix;
	[Export ("filePrefix")]
	string FilePrefix { get; set; }

	// @property (nonatomic) BOOL autoLoad;
	[Export ("autoLoad")]
	bool AutoLoad { get; set; }

	// @property (nonatomic) BOOL autoscrollOnValidation;
	[Export ("autoscrollOnValidation")]
	bool AutoscrollOnValidation { get; set; }

	// @property (nonatomic) BOOL showSubmitButton;
	[Export ("showSubmitButton")]
	bool ShowSubmitButton { get; set; }

	// @property (nonatomic) BOOL editable;
	[Export ("editable")]
	bool Editable { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable offlinePolicy;
	[NullAllowed, Export ("offlinePolicy")]
	string OfflinePolicy { get; set; }

	[Wrap ("WeakDdlFormDelegate")]
	[NullAllowed]
	DDLFormScreenletDelegate DdlFormDelegate { get; }

	// @property (readonly, nonatomic, strong) id<DDLFormScreenletDelegate> _Nullable ddlFormDelegate;
	[NullAllowed, Export ("ddlFormDelegate", ArgumentSemantic.Strong)]
	NSObject WeakDdlFormDelegate { get; }

	// @property (readonly, nonatomic, strong) id<DDLFormViewModel> _Nonnull viewModel;
	[Export ("viewModel", ArgumentSemantic.Strong)]
	DDLFormViewModel ViewModel { get; }

	// @property (readonly, nonatomic) BOOL isFormLoaded;
	[Export ("isFormLoaded")]
	bool IsFormLoaded { get; }

	// -(void)onCreated;
	[Export ("onCreated")]
	void OnCreated ();

	// -(void)onShow;
	[Export ("onShow")]
	void OnShow ();

	// -(Interactor * _Nullable)createInteractorWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender __attribute__((warn_unused_result));
	[Export ("createInteractorWithName:sender:")]
	[return: NullAllowed]
	Interactor CreateInteractorWithName (string name, [NullAllowed] NSObject sender);

	// -(BOOL)onActionWithName:(NSString * _Nonnull)name interactor:(Interactor * _Nonnull)interactor sender:(id _Nullable)sender __attribute__((warn_unused_result));
	[Export ("onActionWithName:interactor:sender:")]
	bool OnActionWithName (string name, Interactor interactor, [NullAllowed] NSObject sender);

	// -(BOOL)loadForm;
	[Export ("loadForm")]
	[Verify (MethodToProperty)]
	bool LoadForm { get; }

	// -(void)clearForm;
	[Export ("clearForm")]
	void ClearForm ();

	// -(BOOL)loadRecord;
	[Export ("loadRecord")]
	[Verify (MethodToProperty)]
	bool LoadRecord { get; }

	// -(BOOL)submitForm;
	[Export ("submitForm")]
	[Verify (MethodToProperty)]
	bool SubmitForm { get; }

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:themeName:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame, [NullAllowed] string themeName);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @protocol DDLFormScreenletDelegate <BaseScreenletDelegate>
[Protocol, Model]
interface DDLFormScreenletDelegate : IBaseScreenletDelegate
{
	// @optional -(void)screenlet:(DDLFormScreenlet * _Nonnull)screenlet onFormLoaded:(DDLRecord * _Nonnull)record;
	[Export ("screenlet:onFormLoaded:")]
	void OnFormLoaded (DDLFormScreenlet screenlet, DDLRecord record);

	// @optional -(void)screenlet:(DDLFormScreenlet * _Nonnull)screenlet onFormLoadError:(NSError * _Nonnull)error;
	[Export ("screenlet:onFormLoadError:")]
	void OnFormLoadError (DDLFormScreenlet screenlet, NSError error);

	// @optional -(void)screenlet:(DDLFormScreenlet * _Nonnull)screenlet onRecordLoaded:(DDLRecord * _Nonnull)record;
	[Export ("screenlet:onRecordLoaded:")]
	void OnRecordLoaded (DDLFormScreenlet screenlet, DDLRecord record);

	// @optional -(void)screenlet:(DDLFormScreenlet * _Nonnull)screenlet onRecordLoadError:(NSError * _Nonnull)error;
	[Export ("screenlet:onRecordLoadError:")]
	void OnRecordLoadError (DDLFormScreenlet screenlet, NSError error);

	// @optional -(void)screenlet:(DDLFormScreenlet * _Nonnull)screenlet onFormSubmitted:(DDLRecord * _Nonnull)record;
	[Export ("screenlet:onFormSubmitted:")]
	void OnFormSubmitted (DDLFormScreenlet screenlet, DDLRecord record);

	// @optional -(void)screenlet:(DDLFormScreenlet * _Nonnull)screenlet onFormSubmitError:(NSError * _Nonnull)error;
	[Export ("screenlet:onFormSubmitError:")]
	void OnFormSubmitError (DDLFormScreenlet screenlet, NSError error);

	// @optional -(void)screenlet:(DDLFormScreenlet * _Nonnull)screenlet onDocumentFieldUploadStarted:(DDMFieldDocument * _Nonnull)field;
	[Export ("screenlet:onDocumentFieldUploadStarted:")]
	void OnDocumentFieldUploadStarted (DDLFormScreenlet screenlet, DDMFieldDocument field);

	// @optional -(void)screenlet:(DDLFormScreenlet * _Nonnull)screenlet onDocumentField:(DDMFieldDocument * _Nonnull)field uploadedBytes:(uint64_t)bytes totalBytes:(uint64_t)total;
	[Export ("screenlet:onDocumentField:uploadedBytes:totalBytes:")]
	void OnDocumentField (DDLFormScreenlet screenlet, DDMFieldDocument field, ulong bytes, ulong total);

	// @optional -(void)screenlet:(DDLFormScreenlet * _Nonnull)screenlet onDocumentField:(DDMFieldDocument * _Nonnull)field uploadResult:(NSDictionary<NSString *,id> * _Nonnull)result;
	[Export ("screenlet:onDocumentField:uploadResult:")]
	void OnDocumentField (DDLFormScreenlet screenlet, DDMFieldDocument field, NSDictionary<NSString, NSObject> result);

	// @optional -(void)screenlet:(DDLFormScreenlet * _Nonnull)screenlet onDocumentField:(DDMFieldDocument * _Nonnull)field uploadError:(NSError * _Nonnull)error;
	[Export ("screenlet:onDocumentField:uploadError:")]
	void OnDocumentField (DDLFormScreenlet screenlet, DDMFieldDocument field, NSError error);
}

// @interface DDLFormSubmitLiferayConnector : ServerConnector
[BaseType (typeof(ServerConnector))]
[DisableDefaultCtor]
interface DDLFormSubmitLiferayConnector
{
	// @property (nonatomic) BOOL autoscrollOnValidation;
	[Export ("autoscrollOnValidation")]
	bool AutoscrollOnValidation { get; set; }

	// @property (nonatomic, strong) NSDictionary * _Nullable resultAttributes;
	[NullAllowed, Export ("resultAttributes", ArgumentSemantic.Strong)]
	NSDictionary ResultAttributes { get; set; }

	// -(instancetype _Nonnull)initWithValues:(NSDictionary<NSString *,id> * _Nonnull)values viewModel:(id<DDLFormViewModel> _Nullable)viewModel __attribute__((objc_designated_initializer));
	[Export ("initWithValues:viewModel:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSDictionary<NSString, NSObject> values, [NullAllowed] DDLFormViewModel viewModel);

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }
}

// @protocol DDLFormViewModel
[Protocol, Model]
interface DDLFormViewModel
{
	// @required @property (nonatomic) BOOL showSubmitButton;
	[Abstract]
	[Export ("showSubmitButton")]
	bool ShowSubmitButton { get; set; }

	// @required @property (nonatomic, strong) DDLRecord * _Nullable record;
	[Abstract]
	[NullAllowed, Export ("record", ArgumentSemantic.Strong)]
	DDLRecord Record { get; set; }

	// @required @property (readonly, nonatomic) BOOL isRecordEmpty;
	[Abstract]
	[Export ("isRecordEmpty")]
	bool IsRecordEmpty { get; }

	// @required -(ValidationError * _Nullable)validateFormWithAutoscroll:(BOOL)autoscroll __attribute__((warn_unused_result));
	[Abstract]
	[Export ("validateFormWithAutoscroll:")]
	[return: NullAllowed]
	ValidationError ValidateFormWithAutoscroll (bool autoscroll);
}

// @interface DDLFormView : BaseScreenletView <DDLFormViewModel>
[BaseType (typeof(BaseScreenletView))]
interface DDLFormView : IDDLFormViewModel
{
	// @property (nonatomic) BOOL showSubmitButton;
	[Export ("showSubmitButton")]
	bool ShowSubmitButton { get; set; }

	// @property (nonatomic, strong) DDLRecord * _Nullable record;
	[NullAllowed, Export ("record", ArgumentSemantic.Strong)]
	DDLRecord Record { get; set; }

	// @property (readonly, nonatomic) BOOL isRecordEmpty;
	[Export ("isRecordEmpty")]
	bool IsRecordEmpty { get; }

	// -(void)refresh;
	[Export ("refresh")]
	void Refresh ();

	// -(ValidationError * _Nullable)validateFormWithAutoscroll:(BOOL)autoscroll __attribute__((warn_unused_result));
	[Export ("validateFormWithAutoscroll:")]
	[return: NullAllowed]
	ValidationError ValidateFormWithAutoscroll (bool autoscroll);

	// -(DDMField * _Nullable)getField:(NSInteger)index __attribute__((warn_unused_result));
	[Export ("getField:")]
	[return: NullAllowed]
	DDMField GetField (nint index);

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface DDLFormTableView : DDLFormView <UIScrollViewDelegate, UITableViewDelegate, UITableViewDataSource>
[BaseType (typeof(DDLFormView))]
interface DDLFormTableView : IUIScrollViewDelegate, IUITableViewDelegate, IUITableViewDataSource
{
	// @property (nonatomic, strong) UITableView * _Nullable tableView __attribute__((iboutlet));
	[NullAllowed, Export ("tableView", ArgumentSemantic.Strong)]
	UITableView TableView { get; set; }

	// @property (nonatomic, strong) DDLRecord * _Nullable record;
	[NullAllowed, Export ("record", ArgumentSemantic.Strong)]
	DDLRecord Record { get; set; }

	// @property (copy, nonatomic) NSString * _Nonnull themeName;
	[Export ("themeName")]
	string ThemeName { get; set; }

	// -(void)refresh;
	[Export ("refresh")]
	void Refresh ();

	// -(BOOL)resignFirstResponder __attribute__((warn_unused_result));
	[Export ("resignFirstResponder")]
	[Verify (MethodToProperty)]
	bool ResignFirstResponder { get; }

	// -(BOOL)becomeFirstResponder __attribute__((warn_unused_result));
	[Export ("becomeFirstResponder")]
	[Verify (MethodToProperty)]
	bool BecomeFirstResponder { get; }

	// -(void)onShow;
	[Export ("onShow")]
	void OnShow ();

	// -(void)onHide;
	[Export ("onHide")]
	void OnHide ();

	// -(void)layoutWhenKeyboardHidden;
	[Export ("layoutWhenKeyboardHidden")]
	void LayoutWhenKeyboardHidden ();

	// -(NSInteger)tableView:(UITableView * _Nonnull)tableView numberOfRowsInSection:(NSInteger)section __attribute__((warn_unused_result));
	[Export ("tableView:numberOfRowsInSection:")]
	nint TableView (UITableView tableView, nint section);

	// -(UITableViewCell * _Nonnull)tableView:(UITableView * _Nonnull)tableView cellForRowAtIndexPath:(NSIndexPath * _Nonnull)indexPath __attribute__((warn_unused_result));
	[Export ("tableView:cellForRowAtIndexPath:")]
	UITableViewCell TableView (UITableView tableView, NSIndexPath indexPath);

	// -(CGFloat)tableView:(UITableView * _Nonnull)tableView heightForRowAtIndexPath:(NSIndexPath * _Nonnull)indexPath __attribute__((warn_unused_result));
	[Export ("tableView:heightForRowAtIndexPath:")]
	nfloat TableView (UITableView tableView, NSIndexPath indexPath);

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface DDLFormUploadLiferayConnector : ServerConnector
[BaseType (typeof(ServerConnector))]
[DisableDefaultCtor]
interface DDLFormUploadLiferayConnector
{
	// -(instancetype _Nonnull)initWithDocument:(DDMFieldDocument * _Nonnull)document filePrefix:(NSString * _Nonnull)filePrefix repositoryId:(int64_t)repositoryId folderId:(int64_t)folderId onProgress:(void (^ _Nullable)(DDMFieldDocument * _Nonnull, uint64_t, uint64_t))onProgress __attribute__((objc_designated_initializer));
	[Export ("initWithDocument:filePrefix:repositoryId:folderId:onProgress:")]
	[DesignatedInitializer]
	IntPtr Constructor (DDMFieldDocument document, string filePrefix, long repositoryId, long folderId, [NullAllowed] Action<DDMFieldDocument, ulong, ulong> onProgress);

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }

	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(BOOL)doSendFile:(LRSession * _Nonnull)session name:(NSString * _Nonnull)name data:(LRUploadData * _Nonnull)data error:(NSError * _Nullable * _Nullable)error;
	[Export ("doSendFile:name:data:error:")]
	bool DoSendFile (LRSession session, string name, LRUploadData data, [NullAllowed] out NSError error);

	// -(void)onProgress:(NSData * _Null_unspecified)data totalBytes:(int64_t)totalBytes;
	[Export ("onProgress:totalBytes:")]
	void OnProgress (NSData data, long totalBytes);

	// -(void)onFailure:(NSError * _Null_unspecified)error;
	[Export ("onFailure:")]
	void OnFailure (NSError error);

	// -(void)onSuccess:(id _Null_unspecified)result;
	[Export ("onSuccess:")]
	void OnSuccess (NSObject result);
}

// @interface DDLFormView_default : DDLFormTableView
[BaseType (typeof(DDLFormTableView))]
interface DDLFormView_default
{
	// -(void)onCreated;
	[Export ("onCreated")]
	void OnCreated ();

	// -(void)onFinishInteraction:(id _Nullable)result error:(NSError * _Nullable)error;
	[Export ("onFinishInteraction:error:")]
	void OnFinishInteraction ([NullAllowed] NSObject result, [NullAllowed] NSError error);

	// -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
	[Export ("createProgressPresenter")]
	[Verify (MethodToProperty)]
	ProgressPresenter CreateProgressPresenter { get; }

	// -(void)changeEditable:(BOOL)editable;
	[Export ("changeEditable:")]
	void ChangeEditable (bool editable);

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface DDLListPageLiferayConnector : PaginationLiferayConnector
[BaseType (typeof(PaginationLiferayConnector))]
interface DDLListPageLiferayConnector
{
	// -(instancetype _Nonnull)initWithViewModel:(id<DDLListViewModel> _Nonnull)viewModel startRow:(NSInteger)startRow endRow:(NSInteger)endRow computeRowCount:(BOOL)computeRowCount __attribute__((objc_designated_initializer));
	[Export ("initWithViewModel:startRow:endRow:computeRowCount:")]
	[DesignatedInitializer]
	IntPtr Constructor (DDLListViewModel viewModel, nint startRow, nint endRow, bool computeRowCount);

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }
}

// @interface DDLListPageLoadInteractor : BaseListPageLoadInteractor
[BaseType (typeof(BaseListPageLoadInteractor))]
interface DDLListPageLoadInteractor
{
	// @property (readonly, nonatomic) int64_t userId;
	[Export ("userId")]
	long UserId { get; }

	// @property (readonly, nonatomic) int64_t recordSetId;
	[Export ("recordSetId")]
	long RecordSetId { get; }

	// -(instancetype _Nonnull)initWithScreenlet:(BaseListScreenlet * _Nonnull)screenlet page:(NSInteger)page computeRowCount:(BOOL)computeRowCount userId:(int64_t)userId recordSetId:(int64_t)recordSetId __attribute__((objc_designated_initializer));
	[Export ("initWithScreenlet:page:computeRowCount:userId:recordSetId:")]
	[DesignatedInitializer]
	IntPtr Constructor (BaseListScreenlet screenlet, nint page, bool computeRowCount, long userId, long recordSetId);

	// -(PaginationLiferayConnector * _Nonnull)createListPageConnector __attribute__((warn_unused_result));
	[Export ("createListPageConnector")]
	[Verify (MethodToProperty)]
	PaginationLiferayConnector CreateListPageConnector { get; }

	// -(id _Nonnull)convertResult:(NSDictionary<NSString *,id> * _Nonnull)serverResult __attribute__((warn_unused_result));
	[Export ("convertResult:")]
	NSObject ConvertResult (NSDictionary<NSString, NSObject> serverResult);

	// -(NSString * _Nonnull)cacheKey:(PaginationLiferayConnector * _Nonnull)c __attribute__((warn_unused_result));
	[Export ("cacheKey:")]
	string CacheKey (PaginationLiferayConnector c);
}

// @interface DDLListScreenlet : BaseListScreenlet
[BaseType (typeof(BaseListScreenlet))]
interface DDLListScreenlet
{
	// @property (nonatomic) int64_t userId;
	[Export ("userId")]
	long UserId { get; set; }

	// @property (nonatomic) int64_t recordSetId;
	[Export ("recordSetId")]
	long RecordSetId { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable labelFields;
	[NullAllowed, Export ("labelFields")]
	string LabelFields { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable offlinePolicy;
	[NullAllowed, Export ("offlinePolicy")]
	string OfflinePolicy { get; set; }

	[Wrap ("WeakDdlListDelegate")]
	[NullAllowed]
	DDLListScreenletDelegate DdlListDelegate { get; }

	// @property (readonly, nonatomic, strong) id<DDLListScreenletDelegate> _Nullable ddlListDelegate;
	[NullAllowed, Export ("ddlListDelegate", ArgumentSemantic.Strong)]
	NSObject WeakDdlListDelegate { get; }

	// @property (readonly, nonatomic, strong) id<DDLListViewModel> _Nonnull viewModel;
	[Export ("viewModel", ArgumentSemantic.Strong)]
	DDLListViewModel ViewModel { get; }

	// -(void)onCreated;
	[Export ("onCreated")]
	void OnCreated ();

	// -(BaseListPageLoadInteractor * _Nonnull)createPageLoadInteractorWithPage:(NSInteger)page computeRowCount:(BOOL)computeRowCount __attribute__((warn_unused_result));
	[Export ("createPageLoadInteractorWithPage:computeRowCount:")]
	BaseListPageLoadInteractor CreatePageLoadInteractorWithPage (nint page, bool computeRowCount);

	// -(void)onLoadPageErrorWithPage:(NSInteger)page error:(NSError * _Nonnull)error;
	[Export ("onLoadPageErrorWithPage:error:")]
	void OnLoadPageErrorWithPage (nint page, NSError error);

	// -(void)onLoadPageResultWithPage:(NSInteger)page rows:(NSArray * _Nonnull)rows rowCount:(NSInteger)rowCount;
	[Export ("onLoadPageResultWithPage:rows:rowCount:")]
	[Verify (StronglyTypedNSArray)]
	void OnLoadPageResultWithPage (nint page, NSObject[] rows, nint rowCount);

	// -(void)onSelectedRow:(id _Nonnull)row;
	[Export ("onSelectedRow:")]
	void OnSelectedRow (NSObject row);

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:themeName:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame, [NullAllowed] string themeName);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @protocol DDLListScreenletDelegate <BaseScreenletDelegate>
[Protocol, Model]
interface DDLListScreenletDelegate : IBaseScreenletDelegate
{
	// @optional -(void)screenlet:(DDLListScreenlet * _Nonnull)screenlet onDDLListResponseRecords:(NSArray<DDLRecord *> * _Nonnull)records;
	[Export ("screenlet:onDDLListResponseRecords:")]
	void OnDDLListResponseRecords (DDLListScreenlet screenlet, DDLRecord[] records);

	// @optional -(void)screenlet:(DDLListScreenlet * _Nonnull)screenlet onDDLListError:(NSError * _Nonnull)error;
	[Export ("screenlet:onDDLListError:")]
	void OnDDLListError (DDLListScreenlet screenlet, NSError error);

	// @optional -(void)screenlet:(DDLListScreenlet * _Nonnull)screenlet onDDLSelectedRecord:(DDLRecord * _Nonnull)record;
	[Export ("screenlet:onDDLSelectedRecord:")]
	void OnDDLSelectedRecord (DDLListScreenlet screenlet, DDLRecord record);
}

// @protocol DDLListViewModel
[Protocol, Model]
interface DDLListViewModel
{
	// @required @property (copy, nonatomic) NSArray<NSString *> * _Nonnull labelFields;
	[Abstract]
	[Export ("labelFields", ArgumentSemantic.Copy)]
	string[] LabelFields { get; set; }
}

// @interface DDLListView_default : BaseListTableView <DDLListViewModel>
[BaseType (typeof(BaseListTableView))]
interface DDLListView_default : IDDLListViewModel
{
	// @property (copy, nonatomic) NSArray<NSString *> * _Nonnull labelFields;
	[Export ("labelFields", ArgumentSemantic.Copy)]
	string[] LabelFields { get; set; }

	// -(void)doFillLoadedCellWithRow:(NSInteger)row cell:(UITableViewCell * _Nonnull)cell object:(id _Nonnull)object;
	[Export ("doFillLoadedCellWithRow:cell:object:")]
	void DoFillLoadedCellWithRow (nint row, UITableViewCell cell, NSObject @object);

	// -(void)doFillInProgressCellWithRow:(NSInteger)row cell:(UITableViewCell * _Nonnull)cell;
	[Export ("doFillInProgressCellWithRow:cell:")]
	void DoFillInProgressCellWithRow (nint row, UITableViewCell cell);

	// -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
	[Export ("createProgressPresenter")]
	[Verify (MethodToProperty)]
	ProgressPresenter CreateProgressPresenter { get; }

	// -(NSString * _Nonnull)composeLabel:(DDLRecord * _Nonnull)record __attribute__((warn_unused_result));
	[Export ("composeLabel:")]
	string ComposeLabel (DDLRecord record);

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface DDLRecord : NSObject <NSCoding>
[BaseType (typeof(NSObject))]
[DisableDefaultCtor]
interface DDLRecord : INSCoding
{
	// @property (nonatomic, strong) DDMStructure * _Nullable structure;
	[NullAllowed, Export ("structure", ArgumentSemantic.Strong)]
	DDMStructure Structure { get; set; }

	// @property (readonly, copy, nonatomic) NSArray<DDMField *> * _Nullable untypedValues;
	[NullAllowed, Export ("untypedValues", ArgumentSemantic.Copy)]
	DDMField[] UntypedValues { get; }

	// @property (copy, nonatomic) NSDictionary<NSString *,id> * _Nonnull attributes;
	[Export ("attributes", ArgumentSemantic.Copy)]
	NSDictionary<NSString, NSObject> Attributes { get; set; }

	// @property (readonly, copy, nonatomic) NSArray<DDMField *> * _Nonnull fields;
	[Export ("fields", ArgumentSemantic.Copy)]
	DDMField[] Fields { get; }

	// -(DDMField * _Nullable)objectForKeyedSubscript:(NSString * _Nonnull)fieldName __attribute__((warn_unused_result));
	[Export ("objectForKeyedSubscript:")]
	[return: NullAllowed]
	DDMField ObjectForKeyedSubscript (string fieldName);

	// @property (readonly, copy, nonatomic) NSDictionary<NSString *,id> * _Nonnull values;
	[Export ("values", ArgumentSemantic.Copy)]
	NSDictionary<NSString, NSObject> Values { get; }

	// -(instancetype _Nonnull)initWithStructure:(DDMStructure * _Nonnull)structure __attribute__((objc_designated_initializer));
	[Export ("initWithStructure:")]
	[DesignatedInitializer]
	IntPtr Constructor (DDMStructure structure);

	// -(instancetype _Nonnull)initWithXsd:(NSString * _Nonnull)xsd locale:(NSLocale * _Nonnull)locale __attribute__((objc_designated_initializer));
	[Export ("initWithXsd:locale:")]
	[DesignatedInitializer]
	IntPtr Constructor (string xsd, NSLocale locale);

	// -(instancetype _Nonnull)initWithJson:(NSString * _Nonnull)json locale:(NSLocale * _Nonnull)locale __attribute__((objc_designated_initializer));
	[Export ("initWithJson:locale:")]
	[DesignatedInitializer]
	IntPtr Constructor (string json, NSLocale locale);

	// -(instancetype _Nonnull)initWithData:(NSDictionary<NSString *,id> * _Nonnull)data attributes:(NSDictionary<NSString *,id> * _Nonnull)attributes __attribute__((objc_designated_initializer));
	[Export ("initWithData:attributes:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSDictionary<NSString, NSObject> data, NSDictionary<NSString, NSObject> attributes);

	// -(instancetype _Nonnull)initWithDataAndAttributes:(NSDictionary<NSString *,id> * _Nonnull)dataAndAttributes __attribute__((objc_designated_initializer));
	[Export ("initWithDataAndAttributes:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSDictionary<NSString, NSObject> dataAndAttributes);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);

	// -(void)encodeWithCoder:(NSCoder * _Nonnull)aCoder;
	[Export ("encodeWithCoder:")]
	void EncodeWithCoder (NSCoder aCoder);

	// -(DDMField * _Nullable)fieldByName:(NSString * _Nonnull)name __attribute__((warn_unused_result));
	[Export ("fieldByName:")]
	[return: NullAllowed]
	DDMField FieldByName (string name);

	// -(NSArray<DDMField *> * _Nonnull)fieldsByType:(Class _Nonnull)type __attribute__((warn_unused_result));
	[Export ("fieldsByType:")]
	DDMField[] FieldsByType (Class type);

	// -(void)updateCurrentValuesWithValues:(NSDictionary<NSString *,id> * _Nonnull)values;
	[Export ("updateCurrentValuesWithValues:")]
	void UpdateCurrentValuesWithValues (NSDictionary<NSString, NSObject> values);

	// -(NSInteger)updateCurrentValuesWithXmlValues:(NSString * _Nonnull)xmlValues;
	[Export ("updateCurrentValuesWithXmlValues:")]
	nint UpdateCurrentValuesWithXmlValues (string xmlValues);

	// -(void)clearValues;
	[Export ("clearValues")]
	void ClearValues ();
}

// @interface DDLSubmitButtonTableCell_default : DDMFieldTableCell
[BaseType (typeof(DDMFieldTableCell))]
interface DDLSubmitButtonTableCell_default
{
	// @property (nonatomic, strong) UIButton * _Nullable submitButton __attribute__((iboutlet));
	[NullAllowed, Export ("submitButton", ArgumentSemantic.Strong)]
	UIButton SubmitButton { get; set; }

	// -(void)awakeFromNib __attribute__((objc_requires_super));
	[Export ("awakeFromNib")]
	[RequiresSuper]
	void AwakeFromNib ();

	// @property (readonly, nonatomic) BOOL canBecomeFirstResponder;
	[Export ("canBecomeFirstResponder")]
	bool CanBecomeFirstResponder { get; }

	// -(instancetype _Nonnull)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString * _Nullable)reuseIdentifier __attribute__((availability(ios, introduced=3.0))) __attribute__((objc_designated_initializer));
	[iOS (3,0)]
	[Export ("initWithStyle:reuseIdentifier:")]
	[DesignatedInitializer]
	IntPtr Constructor (UITableViewCellStyle style, [NullAllowed] string reuseIdentifier);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface DDMField : NSObject <NSCoding>
[BaseType (typeof(NSObject))]
[DisableDefaultCtor]
interface DDMField : INSCoding
{
	// @property (copy, nonatomic) void (^ _Nullable)(BOOL) onPostValidation;
	[NullAllowed, Export ("onPostValidation", ArgumentSemantic.Copy)]
	Action<bool> OnPostValidation { get; set; }

	// @property (nonatomic, strong) id _Nullable currentValue;
	[NullAllowed, Export ("currentValue", ArgumentSemantic.Strong)]
	NSObject CurrentValue { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable currentValueAsString;
	[NullAllowed, Export ("currentValueAsString")]
	string CurrentValueAsString { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable currentValueAsLabel;
	[NullAllowed, Export ("currentValueAsLabel")]
	string CurrentValueAsLabel { get; set; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull debugDescription;
	[Export ("debugDescription")]
	string DebugDescription { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull description;
	[Export ("description")]
	string Description { get; }

	// @property (copy, nonatomic) NSLocale * _Nonnull currentLocale;
	[Export ("currentLocale", ArgumentSemantic.Copy)]
	NSLocale CurrentLocale { get; set; }

	// -(instancetype _Nonnull)initWithAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes locale:(NSLocale * _Nonnull)locale __attribute__((objc_designated_initializer));
	[Export ("initWithAttributes:locale:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSDictionary<NSString, NSObject> attributes, NSLocale locale);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);

	// -(void)encodeWithCoder:(NSCoder * _Nonnull)aCoder;
	[Export ("encodeWithCoder:")]
	void EncodeWithCoder (NSCoder aCoder);

	// -(BOOL)validate __attribute__((warn_unused_result));
	[Export ("validate")]
	[Verify (MethodToProperty)]
	bool Validate { get; }
}

// @interface LiferayScreens_Swift_2284 (DDMField)
[Category]
[BaseType (typeof(DDMField))]
interface DDMField_LiferayScreens_Swift_2284
{
}

// @interface LiferayScreens_Swift_2288 (DDMField)
[Category]
[BaseType (typeof(DDMField))]
interface DDMField_LiferayScreens_Swift_2288
{
}

// @interface DDMFieldBoolean : DDMField
[BaseType (typeof(DDMField))]
interface DDMFieldBoolean
{
	// -(instancetype _Nonnull)initWithAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes locale:(NSLocale * _Nonnull)locale __attribute__((objc_designated_initializer));
	[Export ("initWithAttributes:locale:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSDictionary<NSString, NSObject> attributes, NSLocale locale);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface DDMFieldDate : DDMField
[BaseType (typeof(DDMField))]
interface DDMFieldDate
{
	// @property (readonly, nonatomic, strong) NSDateFormatter * _Nonnull clientDateFormatter;
	[Export ("clientDateFormatter", ArgumentSemantic.Strong)]
	NSDateFormatter ClientDateFormatter { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull serverDateFormat;
	[Export ("serverDateFormat")]
	string ServerDateFormat { get; }

	// -(NSDateFormatter * _Nonnull)formatterWithFormat:(NSString * _Nonnull)format __attribute__((warn_unused_result));
	[Export ("formatterWithFormat:")]
	NSDateFormatter FormatterWithFormat (string format);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface DDMFieldDate_v62 : DDMFieldDate
[BaseType (typeof(DDMFieldDate))]
interface DDMFieldDate_v62
{
	// -(instancetype _Nonnull)initWithAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes locale:(NSLocale * _Nonnull)locale __attribute__((objc_designated_initializer));
	[Export ("initWithAttributes:locale:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSDictionary<NSString, NSObject> attributes, NSLocale locale);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface DDMFieldDate_v70 : DDMFieldDate
[BaseType (typeof(DDMFieldDate))]
interface DDMFieldDate_v70
{
	// -(instancetype _Nonnull)initWithAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes locale:(NSLocale * _Nonnull)locale __attribute__((objc_designated_initializer));
	[Export ("initWithAttributes:locale:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSDictionary<NSString, NSObject> attributes, NSLocale locale);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);

	// @property (readonly, copy, nonatomic) NSString * _Nonnull serverDateFormat;
	[Export ("serverDateFormat")]
	string ServerDateFormat { get; }
}

// @interface DDMFieldDocument : DDMField
[BaseType (typeof(DDMField))]
interface DDMFieldDocument
{
	// @property (readonly, copy, nonatomic) NSString * _Nullable mimeType;
	[NullAllowed, Export ("mimeType")]
	string MimeType { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nullable cachedKey;
	[NullAllowed, Export ("cachedKey")]
	string CachedKey { get; }

	// -(instancetype _Nonnull)initWithAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes locale:(NSLocale * _Nonnull)locale __attribute__((objc_designated_initializer));
	[Export ("initWithAttributes:locale:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSDictionary<NSString, NSObject> attributes, NSLocale locale);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);

	// -(void)encodeWithCoder:(NSCoder * _Nonnull)aCoder;
	[Export ("encodeWithCoder:")]
	void EncodeWithCoder (NSCoder aCoder);
}

// @interface DDMFieldDocumentlibraryPresenterViewController_default : UIViewController <UIImagePickerControllerDelegate, UINavigationControllerDelegate>
[BaseType (typeof(UIViewController))]
interface DDMFieldDocumentlibraryPresenterViewController_default : IUIImagePickerControllerDelegate, IUINavigationControllerDelegate
{
	// @property (nonatomic, strong) UIButton * _Nullable takeNewButton __attribute__((iboutlet));
	[NullAllowed, Export ("takeNewButton", ArgumentSemantic.Strong)]
	UIButton TakeNewButton { get; set; }

	// @property (nonatomic, strong) UIButton * _Nullable selectPhotoButton __attribute__((iboutlet));
	[NullAllowed, Export ("selectPhotoButton", ArgumentSemantic.Strong)]
	UIButton SelectPhotoButton { get; set; }

	// @property (nonatomic, strong) UIButton * _Nullable selectVideoButton __attribute__((iboutlet));
	[NullAllowed, Export ("selectVideoButton", ArgumentSemantic.Strong)]
	UIButton SelectVideoButton { get; set; }

	// @property (nonatomic, strong) UIButton * _Nullable cancelButton __attribute__((iboutlet));
	[NullAllowed, Export ("cancelButton", ArgumentSemantic.Strong)]
	UIButton CancelButton { get; set; }

	// @property (copy, nonatomic) void (^ _Nullable)(UIImage * _Nullable, NSURL * _Nullable) selectedDocumentClosure;
	[NullAllowed, Export ("selectedDocumentClosure", ArgumentSemantic.Copy)]
	Action<UIImage, NSURL> SelectedDocumentClosure { get; set; }

	// -(instancetype _Nonnull)initWithNibName:(NSString * _Nullable)nibNameOrNil bundle:(NSBundle * _Nullable)nibBundleOrNil __attribute__((objc_designated_initializer));
	[Export ("initWithNibName:bundle:")]
	[DesignatedInitializer]
	IntPtr Constructor ([NullAllowed] string nibNameOrNil, [NullAllowed] NSBundle nibBundleOrNil);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);

	// -(void)imagePickerController:(UIImagePickerController * _Nonnull)picker didFinishPickingMediaWithInfo:(NSDictionary<NSString *,id> * _Nonnull)info;
	[Export ("imagePickerController:didFinishPickingMediaWithInfo:")]
	void ImagePickerController (UIImagePickerController picker, NSDictionary<NSString, NSObject> info);

	// -(void)imagePickerControllerDidCancel:(UIImagePickerController * _Nonnull)picker;
	[Export ("imagePickerControllerDidCancel:")]
	void ImagePickerControllerDidCancel (UIImagePickerController picker);
}

// @interface DDMFieldImage : DDMField
[BaseType (typeof(DDMField))]
interface DDMFieldImage
{
	// -(instancetype _Nonnull)initWithAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes locale:(NSLocale * _Nonnull)locale __attribute__((objc_designated_initializer));
	[Export ("initWithAttributes:locale:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSDictionary<NSString, NSObject> attributes, NSLocale locale);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);

	// -(void)encodeWithCoder:(NSCoder * _Nonnull)aCoder;
	[Export ("encodeWithCoder:")]
	void EncodeWithCoder (NSCoder aCoder);
}

// @interface DDMFieldNumber : DDMField
[BaseType (typeof(DDMField))]
interface DDMFieldNumber
{
	// @property (nonatomic) NSInteger maximumDecimalDigits;
	[Export ("maximumDecimalDigits")]
	nint MaximumDecimalDigits { get; set; }

	// @property (nonatomic) NSInteger minimumDecimalDigits;
	[Export ("minimumDecimalDigits")]
	nint MinimumDecimalDigits { get; set; }

	// @property (readonly, nonatomic) BOOL isDecimal;
	[Export ("isDecimal")]
	bool IsDecimal { get; }

	// -(instancetype _Nonnull)initWithAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes locale:(NSLocale * _Nonnull)locale __attribute__((objc_designated_initializer));
	[Export ("initWithAttributes:locale:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSDictionary<NSString, NSObject> attributes, NSLocale locale);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface DDMFieldString : DDMField
[BaseType (typeof(DDMField))]
interface DDMFieldString
{
	// -(instancetype _Nonnull)initWithAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes locale:(NSLocale * _Nonnull)locale __attribute__((objc_designated_initializer));
	[Export ("initWithAttributes:locale:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSDictionary<NSString, NSObject> attributes, NSLocale locale);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface DDMFieldStringWithOptions : DDMField
[BaseType (typeof(DDMField))]
interface DDMFieldStringWithOptions
{
	// -(instancetype _Nonnull)initWithAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes locale:(NSLocale * _Nonnull)locale __attribute__((objc_designated_initializer));
	[Export ("initWithAttributes:locale:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSDictionary<NSString, NSObject> attributes, NSLocale locale);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);

	// -(void)encodeWithCoder:(NSCoder * _Nonnull)aCoder;
	[Export ("encodeWithCoder:")]
	void EncodeWithCoder (NSCoder aCoder);
}

// @interface DDMFieldUntyped : DDMField
[BaseType (typeof(DDMField))]
interface DDMFieldUntyped
{
	// -(instancetype _Nonnull)initWithName:(NSString * _Nonnull)name value:(id _Nonnull)value locale:(NSLocale * _Nonnull)locale __attribute__((objc_designated_initializer));
	[Export ("initWithName:value:locale:")]
	[DesignatedInitializer]
	IntPtr Constructor (string name, NSObject value, NSLocale locale);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface DDMStructure : NSObject <NSCoding>
[BaseType (typeof(NSObject))]
[DisableDefaultCtor]
interface DDMStructure : INSCoding
{
	// @property (copy, nonatomic) NSArray<DDMField *> * _Nonnull fields;
	[Export ("fields", ArgumentSemantic.Copy)]
	DDMField[] Fields { get; set; }

	// @property (readonly, copy, nonatomic) NSDictionary<NSString *,id> * _Nonnull attributes;
	[Export ("attributes", ArgumentSemantic.Copy)]
	NSDictionary<NSString, NSObject> Attributes { get; }

	// @property (readonly, copy, nonatomic) NSLocale * _Nonnull locale;
	[Export ("locale", ArgumentSemantic.Copy)]
	NSLocale Locale { get; }

	// -(DDMField * _Nullable)objectForKeyedSubscript:(NSString * _Nonnull)fieldName __attribute__((warn_unused_result));
	[Export ("objectForKeyedSubscript:")]
	[return: NullAllowed]
	DDMField ObjectForKeyedSubscript (string fieldName);

	// @property (readonly, copy, nonatomic) NSString * _Nonnull debugDescription;
	[Export ("debugDescription")]
	string DebugDescription { get; }

	// -(instancetype _Nonnull)initWithFields:(NSArray<DDMField *> * _Nonnull)fields locale:(NSLocale * _Nonnull)locale attributes:(NSDictionary<NSString *,id> * _Nonnull)attributes __attribute__((objc_designated_initializer));
	[Export ("initWithFields:locale:attributes:")]
	[DesignatedInitializer]
	IntPtr Constructor (DDMField[] fields, NSLocale locale, NSDictionary<NSString, NSObject> attributes);

	// -(instancetype _Nullable)initWithXsd:(NSString * _Nonnull)xsd locale:(NSLocale * _Nonnull)locale attributes:(NSDictionary<NSString *,id> * _Nonnull)attributes;
	[Export ("initWithXsd:locale:attributes:")]
	IntPtr Constructor (string xsd, NSLocale locale, NSDictionary<NSString, NSObject> attributes);

	// -(instancetype _Nullable)initWithJson:(NSString * _Nonnull)json locale:(NSLocale * _Nonnull)locale attributes:(NSDictionary<NSString *,id> * _Nonnull)attributes;
	[Export ("initWithJson:locale:attributes:")]
	IntPtr Constructor (string json, NSLocale locale, NSDictionary<NSString, NSObject> attributes);

	// -(instancetype _Nullable)initWithStructureData:(NSDictionary<NSString *,id> * _Nonnull)structureData locale:(NSLocale * _Nonnull)locale;
	[Export ("initWithStructureData:locale:")]
	IntPtr Constructor (NSDictionary<NSString, NSObject> structureData, NSLocale locale);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);

	// -(void)encodeWithCoder:(NSCoder * _Nonnull)aCoder;
	[Export ("encodeWithCoder:")]
	void EncodeWithCoder (NSCoder aCoder);

	// -(DDMField * _Nullable)fieldByName:(NSString * _Nonnull)name __attribute__((warn_unused_result));
	[Export ("fieldByName:")]
	[return: NullAllowed]
	DDMField FieldByName (string name);

	// -(NSArray<DDMField *> * _Nonnull)fieldsByType:(Class _Nonnull)type __attribute__((warn_unused_result));
	[Export ("fieldsByType:")]
	DDMField[] FieldsByType (Class type);
}

// @protocol ProgressPresenter
[Protocol, Model]
interface ProgressPresenter
{
	// @required -(void)showHUDInView:(UIView * _Nonnull)view message:(NSString * _Nullable)message forInteractor:(Interactor * _Nonnull)interactor;
	[Abstract]
	[Export ("showHUDInView:message:forInteractor:")]
	void ShowHUDInView (UIView view, [NullAllowed] string message, Interactor interactor);

	// @required -(void)hideHUDFromView:(UIView * _Nullable)view message:(NSString * _Nullable)message forInteractor:(Interactor * _Nonnull)interactor withError:(NSError * _Nullable)error;
	[Abstract]
	[Export ("hideHUDFromView:message:forInteractor:withError:")]
	void HideHUDFromView ([NullAllowed] UIView view, [NullAllowed] string message, Interactor interactor, [NullAllowed] NSError error);
}

// @interface MBProgressHUDPresenter : NSObject <ProgressPresenter>
[BaseType (typeof(NSObject))]
interface MBProgressHUDPresenter : IProgressPresenter
{
	// @property (nonatomic, strong) MBProgressHUD * _Nullable instance;
	[NullAllowed, Export ("instance", ArgumentSemantic.Strong)]
	MBProgressHUD Instance { get; set; }

	// @property (nonatomic, strong) UIView * _Nullable customView;
	[NullAllowed, Export ("customView", ArgumentSemantic.Strong)]
	UIView CustomView { get; set; }

	// @property (nonatomic, strong) UIColor * _Nullable customColor;
	[NullAllowed, Export ("customColor", ArgumentSemantic.Strong)]
	UIColor CustomColor { get; set; }

	// @property (nonatomic) float customOpacity;
	[Export ("customOpacity")]
	float CustomOpacity { get; set; }

	// -(void)hideHUDFromView:(UIView * _Nullable)view message:(NSString * _Nullable)message forInteractor:(Interactor * _Nonnull)interactor withError:(NSError * _Nullable)error;
	[Export ("hideHUDFromView:message:forInteractor:withError:")]
	void HideHUDFromView ([NullAllowed] UIView view, [NullAllowed] string message, Interactor interactor, [NullAllowed] NSError error);

	// -(void)showHUDInView:(UIView * _Nonnull)view message:(NSString * _Nullable)message forInteractor:(Interactor * _Nonnull)interactor;
	[Export ("showHUDInView:message:forInteractor:")]
	void ShowHUDInView (UIView view, [NullAllowed] string message, Interactor interactor);

	// -(void)hideHud;
	[Export ("hideHud")]
	void HideHud ();

	// -(void)configureAndShowHUD:(MBProgressHUD * _Nonnull)hud message:(NSString * _Nullable)message closeMode:(enum ProgressCloseMode)closeMode spinnerMode:(enum ProgressSpinnerMode)spinnerMode;
	[Export ("configureAndShowHUD:message:closeMode:spinnerMode:")]
	void ConfigureAndShowHUD (MBProgressHUD hud, [NullAllowed] string message, ProgressCloseMode closeMode, ProgressSpinnerMode spinnerMode);

	// -(UIView * _Nonnull)rootView:(UIView * _Nonnull)currentView __attribute__((warn_unused_result));
	[Export ("rootView:")]
	UIView RootView (UIView currentView);

	// -(id)spinnerModeToProgressModeHUD:(enum ProgressSpinnerMode)spinnerMode __attribute__((warn_unused_result));
	[Export ("spinnerModeToProgressModeHUD:")]
	NSObject SpinnerModeToProgressModeHUD (ProgressSpinnerMode spinnerMode);
}

// @interface DefaultProgressPresenter : MBProgressHUDPresenter
[BaseType (typeof(MBProgressHUDPresenter))]
interface DefaultProgressPresenter
{
}

// @interface DefaultTextField : UITextField
[BaseType (typeof(UITextField))]
interface DefaultTextField
{
	// @property (nonatomic) CGFloat buttonMargin;
	[Export ("buttonMargin")]
	nfloat ButtonMargin { get; set; }

	// @property (copy, nonatomic) void (^ _Nullable)(void) onRightButtonClick;
	[NullAllowed, Export ("onRightButtonClick", ArgumentSemantic.Copy)]
	Action OnRightButtonClick { get; set; }

	// @property (nonatomic, strong) UIColor * _Nonnull defaultColor;
	[Export ("defaultColor", ArgumentSemantic.Strong)]
	UIColor DefaultColor { get; set; }

	// @property (nonatomic, strong) UIColor * _Nonnull highlightColor;
	[Export ("highlightColor", ArgumentSemantic.Strong)]
	UIColor HighlightColor { get; set; }

	// @property (nonatomic, strong) UIColor * _Nonnull errorColor;
	[Export ("errorColor", ArgumentSemantic.Strong)]
	UIColor ErrorColor { get; set; }

	// @property (nonatomic) CGFloat paddingLeft;
	[Export ("paddingLeft")]
	nfloat PaddingLeft { get; set; }

	// @property (nonatomic) CGFloat paddingRight;
	[Export ("paddingRight")]
	nfloat PaddingRight { get; set; }

	// @property (nonatomic, strong) UIImage * _Nullable leftImage;
	[NullAllowed, Export ("leftImage", ArgumentSemantic.Strong)]
	UIImage LeftImage { get; set; }

	// @property (nonatomic, strong) UIImage * _Nullable rightButtonImage;
	[NullAllowed, Export ("rightButtonImage", ArgumentSemantic.Strong)]
	UIImage RightButtonImage { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable rightButtonTitle;
	[NullAllowed, Export ("rightButtonTitle")]
	string RightButtonTitle { get; set; }

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);

	// -(void)prepareForInterfaceBuilder;
	[Export ("prepareForInterfaceBuilder")]
	void PrepareForInterfaceBuilder ();

	// -(void)setDefaultState;
	[Export ("setDefaultState")]
	void SetDefaultState ();

	// -(void)setErrorState;
	[Export ("setErrorState")]
	void SetErrorState ();

	// -(BOOL)resignFirstResponder __attribute__((warn_unused_result));
	[Export ("resignFirstResponder")]
	[Verify (MethodToProperty)]
	bool ResignFirstResponder { get; }

	// -(BOOL)becomeFirstResponder __attribute__((warn_unused_result));
	[Export ("becomeFirstResponder")]
	[Verify (MethodToProperty)]
	bool BecomeFirstResponder { get; }

	// -(CGRect)textRectForBounds:(CGRect)bounds __attribute__((warn_unused_result));
	[Export ("textRectForBounds:")]
	CGRect TextRectForBounds (CGRect bounds);

	// -(CGRect)editingRectForBounds:(CGRect)bounds __attribute__((warn_unused_result));
	[Export ("editingRectForBounds:")]
	CGRect EditingRectForBounds (CGRect bounds);

	// -(CGRect)leftViewRectForBounds:(CGRect)bounds __attribute__((warn_unused_result));
	[Export ("leftViewRectForBounds:")]
	CGRect LeftViewRectForBounds (CGRect bounds);

	// -(CGRect)rightViewRectForBounds:(CGRect)bounds __attribute__((warn_unused_result));
	[Export ("rightViewRectForBounds:")]
	CGRect RightViewRectForBounds (CGRect bounds);
}

// @interface DeleteRatingInteractor : ServerWriteConnectorInteractor
[BaseType (typeof(ServerWriteConnectorInteractor))]
interface DeleteRatingInteractor
{
	// -(ServerConnector * _Nullable)createConnector __attribute__((warn_unused_result));
	[NullAllowed, Export ("createConnector")]
	[Verify (MethodToProperty)]
	ServerConnector CreateConnector { get; }

	// -(void)completedConnector:(ServerConnector * _Nonnull)c;
	[Export ("completedConnector:")]
	void CompletedConnector (ServerConnector c);

	// -(void)writeToCache:(ServerConnector * _Nonnull)c;
	[Export ("writeToCache:")]
	void WriteToCache (ServerConnector c);

	// -(void)callOnSuccess;
	[Export ("callOnSuccess")]
	void CallOnSuccess ();
}

// @protocol FileDisplayScreenletDelegate <BaseScreenletDelegate>
[Protocol, Model]
interface FileDisplayScreenletDelegate : IBaseScreenletDelegate
{
	// @optional -(void)screenlet:(FileDisplayScreenlet * _Nonnull)screenlet onFileAssetResponse:(NSURL * _Nonnull)url;
	[Export ("screenlet:onFileAssetResponse:")]
	void OnFileAssetResponse (FileDisplayScreenlet screenlet, NSUrl url);

	// @optional -(void)screenlet:(FileDisplayScreenlet * _Nonnull)screenlet onFileAssetError:(NSError * _Nonnull)error;
	[Export ("screenlet:onFileAssetError:")]
	void OnFileAssetError (FileDisplayScreenlet screenlet, NSError error);
}

// @interface FileDisplayView_default : BaseScreenletView <FileDisplayViewModel>
[BaseType (typeof(BaseScreenletView))]
interface FileDisplayView_default : IFileDisplayViewModel
{
	// @property (nonatomic, strong) WKWebView * _Nullable webView;
	[NullAllowed, Export ("webView", ArgumentSemantic.Strong)]
	WKWebView WebView { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable title;
	[NullAllowed, Export ("title")]
	string Title { get; set; }

	// @property (copy, nonatomic) NSURL * _Nullable url;
	[NullAllowed, Export ("url", ArgumentSemantic.Copy)]
	NSUrl Url { get; set; }

	// -(void)onCreated;
	[Export ("onCreated")]
	void OnCreated ();

	// -(void)addWebView;
	[Export ("addWebView")]
	void AddWebView ();

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface FileEntry : Asset
[BaseType (typeof(Asset))]
interface FileEntry
{
	// @property (readonly, copy, nonatomic) NSString * _Nonnull url;
	[Export ("url")]
	string Url { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nullable fileExtension;
	[NullAllowed, Export ("fileExtension")]
	string FileExtension { get; }

	// @property (readonly, copy, nonatomic) NSDictionary<NSString *,id> * _Nonnull fileEntry;
	[Export ("fileEntry", ArgumentSemantic.Copy)]
	NSDictionary<NSString, NSObject> FileEntry { get; }

	// @property (readonly, nonatomic) int64_t fileEntryId;
	[Export ("fileEntryId")]
	long FileEntryId { get; }

	// -(instancetype _Nonnull)initWithAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes __attribute__((objc_designated_initializer));
	[Export ("initWithAttributes:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSDictionary<NSString, NSObject> attributes);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface ForgotPasswordBaseLiferayConnector : ServerConnector
[BaseType (typeof(ServerConnector))]
[DisableDefaultCtor]
interface ForgotPasswordBaseLiferayConnector
{
	// @property (nonatomic) int64_t companyId;
	[Export ("companyId")]
	long CompanyId { get; set; }

	// -(instancetype _Nonnull)initWithViewModel:(id<ForgotPasswordViewModel> _Nonnull)viewModel anonymousUsername:(NSString * _Nonnull)anonymousUsername anonymousPassword:(NSString * _Nonnull)anonymousPassword __attribute__((objc_designated_initializer));
	[Export ("initWithViewModel:anonymousUsername:anonymousPassword:")]
	[DesignatedInitializer]
	IntPtr Constructor (ForgotPasswordViewModel viewModel, string anonymousUsername, string anonymousPassword);

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }

	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(LRSession * _Nullable)createSession __attribute__((warn_unused_result));
	[NullAllowed, Export ("createSession")]
	[Verify (MethodToProperty)]
	LRSession CreateSession { get; }
}

// @interface ForgotPasswordEmailLiferay62Connector : ForgotPasswordBaseLiferayConnector
[BaseType (typeof(ForgotPasswordBaseLiferayConnector))]
interface ForgotPasswordEmailLiferay62Connector
{
	// -(instancetype _Nonnull)initWithViewModel:(id<ForgotPasswordViewModel> _Nonnull)viewModel anonymousUsername:(NSString * _Nonnull)anonymousUsername anonymousPassword:(NSString * _Nonnull)anonymousPassword __attribute__((objc_designated_initializer));
	[Export ("initWithViewModel:anonymousUsername:anonymousPassword:")]
	[DesignatedInitializer]
	IntPtr Constructor (ForgotPasswordViewModel viewModel, string anonymousUsername, string anonymousPassword);
}

// @interface ForgotPasswordEmailLiferay70Connector : ForgotPasswordBaseLiferayConnector
[BaseType (typeof(ForgotPasswordBaseLiferayConnector))]
interface ForgotPasswordEmailLiferay70Connector
{
	// -(instancetype _Nonnull)initWithViewModel:(id<ForgotPasswordViewModel> _Nonnull)viewModel anonymousUsername:(NSString * _Nonnull)anonymousUsername anonymousPassword:(NSString * _Nonnull)anonymousPassword __attribute__((objc_designated_initializer));
	[Export ("initWithViewModel:anonymousUsername:anonymousPassword:")]
	[DesignatedInitializer]
	IntPtr Constructor (ForgotPasswordViewModel viewModel, string anonymousUsername, string anonymousPassword);
}

// @interface ForgotPasswordScreenNameLiferay62Connector : ForgotPasswordBaseLiferayConnector
[BaseType (typeof(ForgotPasswordBaseLiferayConnector))]
interface ForgotPasswordScreenNameLiferay62Connector
{
	// -(instancetype _Nonnull)initWithViewModel:(id<ForgotPasswordViewModel> _Nonnull)viewModel anonymousUsername:(NSString * _Nonnull)anonymousUsername anonymousPassword:(NSString * _Nonnull)anonymousPassword __attribute__((objc_designated_initializer));
	[Export ("initWithViewModel:anonymousUsername:anonymousPassword:")]
	[DesignatedInitializer]
	IntPtr Constructor (ForgotPasswordViewModel viewModel, string anonymousUsername, string anonymousPassword);
}

// @interface ForgotPasswordScreenNameLiferay70Connector : ForgotPasswordBaseLiferayConnector
[BaseType (typeof(ForgotPasswordBaseLiferayConnector))]
interface ForgotPasswordScreenNameLiferay70Connector
{
	// -(instancetype _Nonnull)initWithViewModel:(id<ForgotPasswordViewModel> _Nonnull)viewModel anonymousUsername:(NSString * _Nonnull)anonymousUsername anonymousPassword:(NSString * _Nonnull)anonymousPassword __attribute__((objc_designated_initializer));
	[Export ("initWithViewModel:anonymousUsername:anonymousPassword:")]
	[DesignatedInitializer]
	IntPtr Constructor (ForgotPasswordViewModel viewModel, string anonymousUsername, string anonymousPassword);
}

// @interface ForgotPasswordScreenlet : BaseScreenlet <AnonymousBasicAuthType, BasicAuthBasedType>
[BaseType (typeof(BaseScreenlet))]
interface ForgotPasswordScreenlet : IAnonymousBasicAuthType, IBasicAuthBasedType
{
	// @property (copy, nonatomic) NSString * _Nullable anonymousApiUserName;
	[NullAllowed, Export ("anonymousApiUserName")]
	string AnonymousApiUserName { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable anonymousApiPassword;
	[NullAllowed, Export ("anonymousApiPassword")]
	string AnonymousApiPassword { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable basicAuthMethod;
	[NullAllowed, Export ("basicAuthMethod")]
	string BasicAuthMethod { get; set; }

	[Wrap ("WeakForgotPasswordDelegate")]
	[NullAllowed]
	ForgotPasswordScreenletDelegate ForgotPasswordDelegate { get; }

	// @property (readonly, nonatomic, strong) id<ForgotPasswordScreenletDelegate> _Nullable forgotPasswordDelegate;
	[NullAllowed, Export ("forgotPasswordDelegate", ArgumentSemantic.Strong)]
	NSObject WeakForgotPasswordDelegate { get; }

	// @property (readonly, nonatomic, strong) id<ForgotPasswordViewModel> _Nonnull viewModel;
	[Export ("viewModel", ArgumentSemantic.Strong)]
	ForgotPasswordViewModel ViewModel { get; }

	// @property (nonatomic) BOOL saveCredentials;
	[Export ("saveCredentials")]
	bool SaveCredentials { get; set; }

	// -(void)onCreated;
	[Export ("onCreated")]
	void OnCreated ();

	// -(Interactor * _Nullable)createInteractorWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender __attribute__((warn_unused_result));
	[Export ("createInteractorWithName:sender:")]
	[return: NullAllowed]
	Interactor CreateInteractorWithName (string name, [NullAllowed] NSObject sender);

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:themeName:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame, [NullAllowed] string themeName);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @protocol ForgotPasswordScreenletDelegate <BaseScreenletDelegate>
[Protocol, Model]
interface ForgotPasswordScreenletDelegate : IBaseScreenletDelegate
{
	// @optional -(void)screenlet:(ForgotPasswordScreenlet * _Nonnull)screenlet onForgotPasswordSent:(BOOL)passwordSent;
	[Export ("screenlet:onForgotPasswordSent:")]
	void OnForgotPasswordSent (ForgotPasswordScreenlet screenlet, bool passwordSent);

	// @optional -(void)screenlet:(ForgotPasswordScreenlet * _Nonnull)screenlet onForgotPasswordError:(NSError * _Nonnull)error;
	[Export ("screenlet:onForgotPasswordError:")]
	void OnForgotPasswordError (ForgotPasswordScreenlet screenlet, NSError error);
}

// @interface ForgotPasswordUserIdLiferay62Connector : ForgotPasswordBaseLiferayConnector
[BaseType (typeof(ForgotPasswordBaseLiferayConnector))]
interface ForgotPasswordUserIdLiferay62Connector
{
	// -(instancetype _Nonnull)initWithViewModel:(id<ForgotPasswordViewModel> _Nonnull)viewModel anonymousUsername:(NSString * _Nonnull)anonymousUsername anonymousPassword:(NSString * _Nonnull)anonymousPassword __attribute__((objc_designated_initializer));
	[Export ("initWithViewModel:anonymousUsername:anonymousPassword:")]
	[DesignatedInitializer]
	IntPtr Constructor (ForgotPasswordViewModel viewModel, string anonymousUsername, string anonymousPassword);
}

// @interface ForgotPasswordUserIdLiferay70Connector : ForgotPasswordBaseLiferayConnector
[BaseType (typeof(ForgotPasswordBaseLiferayConnector))]
interface ForgotPasswordUserIdLiferay70Connector
{
	// -(instancetype _Nonnull)initWithViewModel:(id<ForgotPasswordViewModel> _Nonnull)viewModel anonymousUsername:(NSString * _Nonnull)anonymousUsername anonymousPassword:(NSString * _Nonnull)anonymousPassword __attribute__((objc_designated_initializer));
	[Export ("initWithViewModel:anonymousUsername:anonymousPassword:")]
	[DesignatedInitializer]
	IntPtr Constructor (ForgotPasswordViewModel viewModel, string anonymousUsername, string anonymousPassword);
}

// @protocol ForgotPasswordViewModel <BasicAuthBasedType>
[Protocol, Model]
interface ForgotPasswordViewModel : IBasicAuthBasedType
{
	// @required @property (copy, nonatomic) NSString * _Nullable userName;
	[Abstract]
	[NullAllowed, Export ("userName")]
	string UserName { get; set; }
}

// @interface ForgotPasswordView_default : BaseScreenletView <ForgotPasswordViewModel, BasicAuthBasedType>
[BaseType (typeof(BaseScreenletView))]
interface ForgotPasswordView_default : IForgotPasswordViewModel, IBasicAuthBasedType
{
	// @property (nonatomic, strong) UITextField * _Nullable userNameField __attribute__((iboutlet));
	[NullAllowed, Export ("userNameField", ArgumentSemantic.Strong)]
	UITextField UserNameField { get; set; }

	// @property (nonatomic, strong) UIButton * _Nullable requestPasswordButton __attribute__((iboutlet));
	[NullAllowed, Export ("requestPasswordButton", ArgumentSemantic.Strong)]
	UIButton RequestPasswordButton { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable userName;
	[NullAllowed, Export ("userName")]
	string UserName { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable basicAuthMethod;
	[NullAllowed, Export ("basicAuthMethod")]
	string BasicAuthMethod { get; set; }

	// @property (nonatomic) BOOL saveCredentials;
	[Export ("saveCredentials")]
	bool SaveCredentials { get; set; }

	// -(void)onCreated;
	[Export ("onCreated")]
	void OnCreated ();

	// -(void)onSetTranslations;
	[Export ("onSetTranslations")]
	void OnSetTranslations ();

	// -(void)onStartInteraction;
	[Export ("onStartInteraction")]
	void OnStartInteraction ();

	// -(void)onFinishInteraction:(id _Nullable)result error:(NSError * _Nullable)error;
	[Export ("onFinishInteraction:error:")]
	void OnFinishInteraction ([NullAllowed] NSObject result, [NullAllowed] NSError error);

	// -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
	[Export ("createProgressPresenter")]
	[Verify (MethodToProperty)]
	ProgressPresenter CreateProgressPresenter { get; }

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface GetUserBaseLiferayConnector : ServerConnector
[BaseType (typeof(ServerConnector))]
interface GetUserBaseLiferayConnector
{
	// @property (copy, nonatomic) NSDictionary<NSString *,id> * _Nullable resultUserAttributes;
	[NullAllowed, Export ("resultUserAttributes", ArgumentSemantic.Copy)]
	NSDictionary<NSString, NSObject> ResultUserAttributes { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable userName;
	[NullAllowed, Export ("userName")]
	string UserName { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable password;
	[NullAllowed, Export ("password")]
	string Password { get; set; }

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }

	// -(LRSession * _Nullable)createSession __attribute__((warn_unused_result));
	[NullAllowed, Export ("createSession")]
	[Verify (MethodToProperty)]
	LRSession CreateSession { get; }

	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(NSDictionary * _Nullable)sendGetUserRequest:(LRSession * _Nonnull)session error:(NSError * _Nullable * _Nullable)error __attribute__((warn_unused_result));
	[Export ("sendGetUserRequest:error:")]
	[return: NullAllowed]
	NSDictionary SendGetUserRequest (LRSession session, [NullAllowed] out NSError error);
}

// @interface GetUserByEmailLiferayConnector : GetUserBaseLiferayConnector
[BaseType (typeof(GetUserBaseLiferayConnector))]
[DisableDefaultCtor]
interface GetUserByEmailLiferayConnector
{
	// @property (readonly, nonatomic) int64_t companyId;
	[Export ("companyId")]
	long CompanyId { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull emailAddress;
	[Export ("emailAddress")]
	string EmailAddress { get; }

	// -(instancetype _Nonnull)initWithCompanyId:(int64_t)companyId emailAddress:(NSString * _Nonnull)emailAddress __attribute__((objc_designated_initializer));
	[Export ("initWithCompanyId:emailAddress:")]
	[DesignatedInitializer]
	IntPtr Constructor (long companyId, string emailAddress);

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }
}

// @interface GetUserByEmailLiferay62Connector : GetUserByEmailLiferayConnector
[BaseType (typeof(GetUserByEmailLiferayConnector))]
interface GetUserByEmailLiferay62Connector
{
	// -(instancetype _Nonnull)initWithCompanyId:(int64_t)companyId emailAddress:(NSString * _Nonnull)emailAddress __attribute__((objc_designated_initializer));
	[Export ("initWithCompanyId:emailAddress:")]
	[DesignatedInitializer]
	IntPtr Constructor (long companyId, string emailAddress);

	// -(NSDictionary * _Nullable)sendGetUserRequest:(LRSession * _Nonnull)session error:(NSError * _Nullable * _Nullable)error __attribute__((warn_unused_result));
	[Export ("sendGetUserRequest:error:")]
	[return: NullAllowed]
	NSDictionary SendGetUserRequest (LRSession session, [NullAllowed] out NSError error);
}

// @interface GetUserByEmailLiferay70Connector : GetUserByEmailLiferayConnector
[BaseType (typeof(GetUserByEmailLiferayConnector))]
interface GetUserByEmailLiferay70Connector
{
	// -(instancetype _Nonnull)initWithCompanyId:(int64_t)companyId emailAddress:(NSString * _Nonnull)emailAddress __attribute__((objc_designated_initializer));
	[Export ("initWithCompanyId:emailAddress:")]
	[DesignatedInitializer]
	IntPtr Constructor (long companyId, string emailAddress);

	// -(NSDictionary * _Nullable)sendGetUserRequest:(LRSession * _Nonnull)session error:(NSError * _Nullable * _Nullable)error __attribute__((warn_unused_result));
	[Export ("sendGetUserRequest:error:")]
	[return: NullAllowed]
	NSDictionary SendGetUserRequest (LRSession session, [NullAllowed] out NSError error);
}

// @interface GetUserByScreenNameLiferayConnector : GetUserBaseLiferayConnector
[BaseType (typeof(GetUserBaseLiferayConnector))]
[DisableDefaultCtor]
interface GetUserByScreenNameLiferayConnector
{
	// @property (readonly, nonatomic) int64_t companyId;
	[Export ("companyId")]
	long CompanyId { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull screenName;
	[Export ("screenName")]
	string ScreenName { get; }

	// -(instancetype _Nonnull)initWithCompanyId:(int64_t)companyId screenName:(NSString * _Nonnull)screenName __attribute__((objc_designated_initializer));
	[Export ("initWithCompanyId:screenName:")]
	[DesignatedInitializer]
	IntPtr Constructor (long companyId, string screenName);

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }
}

// @interface GetUserByScreenNameLiferay62Connector : GetUserByScreenNameLiferayConnector
[BaseType (typeof(GetUserByScreenNameLiferayConnector))]
interface GetUserByScreenNameLiferay62Connector
{
	// -(instancetype _Nonnull)initWithCompanyId:(int64_t)companyId screenName:(NSString * _Nonnull)screenName __attribute__((objc_designated_initializer));
	[Export ("initWithCompanyId:screenName:")]
	[DesignatedInitializer]
	IntPtr Constructor (long companyId, string screenName);

	// -(NSDictionary * _Nullable)sendGetUserRequest:(LRSession * _Nonnull)session error:(NSError * _Nullable * _Nullable)error __attribute__((warn_unused_result));
	[Export ("sendGetUserRequest:error:")]
	[return: NullAllowed]
	NSDictionary SendGetUserRequest (LRSession session, [NullAllowed] out NSError error);
}

// @interface GetUserByScreenNameLiferay70Connector : GetUserByScreenNameLiferayConnector
[BaseType (typeof(GetUserByScreenNameLiferayConnector))]
interface GetUserByScreenNameLiferay70Connector
{
	// -(instancetype _Nonnull)initWithCompanyId:(int64_t)companyId screenName:(NSString * _Nonnull)screenName __attribute__((objc_designated_initializer));
	[Export ("initWithCompanyId:screenName:")]
	[DesignatedInitializer]
	IntPtr Constructor (long companyId, string screenName);

	// -(NSDictionary * _Nullable)sendGetUserRequest:(LRSession * _Nonnull)session error:(NSError * _Nullable * _Nullable)error __attribute__((warn_unused_result));
	[Export ("sendGetUserRequest:error:")]
	[return: NullAllowed]
	NSDictionary SendGetUserRequest (LRSession session, [NullAllowed] out NSError error);
}

// @interface GetUserByUserIdLiferayConnector : GetUserBaseLiferayConnector
[BaseType (typeof(GetUserBaseLiferayConnector))]
[DisableDefaultCtor]
interface GetUserByUserIdLiferayConnector
{
	// @property (readonly, nonatomic) int64_t userId;
	[Export ("userId")]
	long UserId { get; }

	// -(instancetype _Nonnull)initWithUserId:(int64_t)userId __attribute__((objc_designated_initializer));
	[Export ("initWithUserId:")]
	[DesignatedInitializer]
	IntPtr Constructor (long userId);

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }
}

// @interface GetUserByUserIdLiferay62Connector : GetUserByUserIdLiferayConnector
[BaseType (typeof(GetUserByUserIdLiferayConnector))]
interface GetUserByUserIdLiferay62Connector
{
	// -(instancetype _Nonnull)initWithUserId:(int64_t)userId __attribute__((objc_designated_initializer));
	[Export ("initWithUserId:")]
	[DesignatedInitializer]
	IntPtr Constructor (long userId);

	// -(NSDictionary * _Nullable)sendGetUserRequest:(LRSession * _Nonnull)session error:(NSError * _Nullable * _Nullable)error __attribute__((warn_unused_result));
	[Export ("sendGetUserRequest:error:")]
	[return: NullAllowed]
	NSDictionary SendGetUserRequest (LRSession session, [NullAllowed] out NSError error);
}

// @interface GetUserByUserIdLiferay70Connector : GetUserByUserIdLiferayConnector
[BaseType (typeof(GetUserByUserIdLiferayConnector))]
interface GetUserByUserIdLiferay70Connector
{
	// -(instancetype _Nonnull)initWithUserId:(int64_t)userId __attribute__((objc_designated_initializer));
	[Export ("initWithUserId:")]
	[DesignatedInitializer]
	IntPtr Constructor (long userId);

	// -(NSDictionary * _Nullable)sendGetUserRequest:(LRSession * _Nonnull)session error:(NSError * _Nullable * _Nullable)error __attribute__((warn_unused_result));
	[Export ("sendGetUserRequest:error:")]
	[return: NullAllowed]
	NSDictionary SendGetUserRequest (LRSession session, [NullAllowed] out NSError error);
}

// @interface HttpConnector : ServerConnector
[BaseType (typeof(ServerConnector))]
[DisableDefaultCtor]
interface HttpConnector
{
	// @property (copy, nonatomic) NSURL * _Nonnull url;
	[Export ("url", ArgumentSemantic.Copy)]
	NSUrl Url { get; set; }

	// @property (copy, nonatomic) NSData * _Nullable resultData;
	[NullAllowed, Export ("resultData", ArgumentSemantic.Copy)]
	NSData ResultData { get; set; }

	// -(instancetype _Nonnull)initWithUrl:(NSURL * _Nonnull)url __attribute__((objc_designated_initializer));
	[Export ("initWithUrl:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSUrl url);

	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(LRSession * _Nullable)createSession __attribute__((warn_unused_result));
	[NullAllowed, Export ("createSession")]
	[Verify (MethodToProperty)]
	LRSession CreateSession { get; }
}

// @interface HttpDownloadConnector : ServerConnector
[BaseType (typeof(ServerConnector))]
[DisableDefaultCtor]
interface HttpDownloadConnector
{
	// @property (copy, nonatomic) NSURL * _Nonnull url;
	[Export ("url", ArgumentSemantic.Copy)]
	NSUrl Url { get; set; }

	// @property (copy, nonatomic) NSURL * _Nullable resultUrl;
	[NullAllowed, Export ("resultUrl", ArgumentSemantic.Copy)]
	NSUrl ResultUrl { get; set; }

	// -(instancetype _Nonnull)initWithUrl:(NSURL * _Nonnull)url __attribute__((objc_designated_initializer));
	[Export ("initWithUrl:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSUrl url);

	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);
}

// @interface ImageDisplayScreenlet : FileDisplayScreenlet
[BaseType (typeof(FileDisplayScreenlet))]
interface ImageDisplayScreenlet
{
	// @property (nonatomic, strong) UIImage * _Nullable placeholder;
	[NullAllowed, Export ("placeholder", ArgumentSemantic.Strong)]
	UIImage Placeholder { get; set; }

	// @property (copy, nonatomic) NSString * _Nonnull mimeTypes;
	[Export ("mimeTypes")]
	string MimeTypes { get; set; }

	// @property (readonly, nonatomic, strong) id<ImageDisplayViewModel> _Nullable imageDisplayViewModel;
	[NullAllowed, Export ("imageDisplayViewModel", ArgumentSemantic.Strong)]
	ImageDisplayViewModel ImageDisplayViewModel { get; }

	// @property (nonatomic) UIViewContentMode imageMode;
	[Export ("imageMode", ArgumentSemantic.Assign)]
	UIViewContentMode ImageMode { get; set; }

	// @property (nonatomic) UIViewContentMode placeholderImageMode;
	[Export ("placeholderImageMode", ArgumentSemantic.Assign)]
	UIViewContentMode PlaceholderImageMode { get; set; }

	// @property (readonly, copy, nonatomic) NSArray<NSString *> * _Nonnull supportedMimeTypes;
	[Export ("supportedMimeTypes", ArgumentSemantic.Copy)]
	string[] SupportedMimeTypes { get; }

	// -(void)onCreated;
	[Export ("onCreated")]
	void OnCreated ();

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:themeName:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame, [NullAllowed] string themeName);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @protocol ImageDisplayViewModel <FileDisplayViewModel>
[Protocol, Model]
interface ImageDisplayViewModel : IFileDisplayViewModel
{
	// @required @property (nonatomic) UIViewContentMode imageMode;
	[Abstract]
	[Export ("imageMode", ArgumentSemantic.Assign)]
	UIViewContentMode ImageMode { get; set; }

	// @required @property (nonatomic, strong) UIImage * _Nullable placeholder;
	[Abstract]
	[NullAllowed, Export ("placeholder", ArgumentSemantic.Strong)]
	UIImage Placeholder { get; set; }

	// @required @property (nonatomic) UIViewContentMode placeholderImageMode;
	[Abstract]
	[Export ("placeholderImageMode", ArgumentSemantic.Assign)]
	UIViewContentMode PlaceholderImageMode { get; set; }
}

// @interface ImageDisplayView_default : BaseScreenletView <ImageDisplayViewModel, FileDisplayViewModel>
[BaseType (typeof(BaseScreenletView))]
interface ImageDisplayView_default : IImageDisplayViewModel, IFileDisplayViewModel
{
	// @property (nonatomic, weak) UIImageView * _Nullable imageView __attribute__((iboutlet));
	[NullAllowed, Export ("imageView", ArgumentSemantic.Weak)]
	UIImageView ImageView { get; set; }

	// @property (nonatomic) UIViewContentMode imageMode;
	[Export ("imageMode", ArgumentSemantic.Assign)]
	UIViewContentMode ImageMode { get; set; }

	// @property (nonatomic) UIViewContentMode placeholderImageMode;
	[Export ("placeholderImageMode", ArgumentSemantic.Assign)]
	UIViewContentMode PlaceholderImageMode { get; set; }

	// @property (nonatomic, strong) UIImage * _Nullable placeholder;
	[NullAllowed, Export ("placeholder", ArgumentSemantic.Strong)]
	UIImage Placeholder { get; set; }

	// @property (copy, nonatomic) NSURL * _Nullable url;
	[NullAllowed, Export ("url", ArgumentSemantic.Copy)]
	NSUrl Url { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable title;
	[NullAllowed, Export ("title")]
	string Title { get; set; }

	// -(void)onStartInteraction;
	[Export ("onStartInteraction")]
	void OnStartInteraction ();

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface ImageEntry : Asset
[BaseType (typeof(Asset))]
interface ImageEntry
{
	// @property (nonatomic, strong) UIImage * _Nullable image;
	[NullAllowed, Export ("image", ArgumentSemantic.Strong)]
	UIImage Image { get; set; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull thumbnailUrl;
	[Export ("thumbnailUrl")]
	string ThumbnailUrl { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull imageUrl;
	[Export ("imageUrl")]
	string ImageUrl { get; }

	// @property (readonly, nonatomic) int64_t imageEntryId;
	[Export ("imageEntryId")]
	long ImageEntryId { get; }

	// -(instancetype _Nonnull)initWithAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes __attribute__((objc_designated_initializer));
	[Export ("initWithAttributes:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSDictionary<NSString, NSObject> attributes);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface ImageEntryUpload : NSObject <NSCoding>
[BaseType (typeof(NSObject))]
[DisableDefaultCtor]
interface ImageEntryUpload : INSCoding
{
	// @property (readonly, nonatomic, strong) UIImage * _Nonnull image;
	[Export ("image", ArgumentSemantic.Strong)]
	UIImage Image { get; }

	// @property (readonly, nonatomic, strong) UIImage * _Nullable thumbnail;
	[NullAllowed, Export ("thumbnail", ArgumentSemantic.Strong)]
	UIImage Thumbnail { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull title;
	[Export ("title")]
	string Title { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull notes;
	[Export ("notes")]
	string Notes { get; }

	// -(instancetype _Nonnull)initWithImage:(UIImage * _Nonnull)image thumbnail:(UIImage * _Nullable)thumbnail title:(NSString * _Nonnull)title notes:(NSString * _Nonnull)notes __attribute__((objc_designated_initializer));
	[Export ("initWithImage:thumbnail:title:notes:")]
	[DesignatedInitializer]
	IntPtr Constructor (UIImage image, [NullAllowed] UIImage thumbnail, string title, string notes);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);

	// -(void)encodeWithCoder:(NSCoder * _Nonnull)aCoder;
	[Export ("encodeWithCoder:")]
	void EncodeWithCoder (NSCoder aCoder);
}

// @interface ImageGalleryCell : UITableViewCell
[BaseType (typeof(UITableViewCell))]
interface ImageGalleryCell
{
	// @property (copy, nonatomic) NSString * _Nullable title;
	[NullAllowed, Export ("title")]
	string Title { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable imageUrl;
	[NullAllowed, Export ("imageUrl")]
	string ImageUrl { get; set; }

	// @property (nonatomic, strong) UIImage * _Nullable img;
	[NullAllowed, Export ("img", ArgumentSemantic.Strong)]
	UIImage Img { get; set; }

	// -(void)awakeFromNib __attribute__((objc_requires_super));
	[Export ("awakeFromNib")]
	[RequiresSuper]
	void AwakeFromNib ();

	// -(void)prepareForReuse;
	[Export ("prepareForReuse")]
	void PrepareForReuse ();

	// -(instancetype _Nonnull)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString * _Nullable)reuseIdentifier __attribute__((availability(ios, introduced=3.0))) __attribute__((objc_designated_initializer));
	[iOS (3,0)]
	[Export ("initWithStyle:reuseIdentifier:")]
	[DesignatedInitializer]
	IntPtr Constructor (UITableViewCellStyle style, [NullAllowed] string reuseIdentifier);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @protocol ImageGalleryViewModel
[Protocol, Model]
interface ImageGalleryViewModel
{
	// @required @property (readonly, nonatomic) NSInteger totalEntries;
	[Abstract]
	[Export ("totalEntries")]
	nint TotalEntries { get; }

	// @optional -(void)onImageEntryDeleted:(ImageEntry * _Nonnull)imageEntry;
	[Export ("onImageEntryDeleted:")]
	void OnImageEntryDeleted (ImageEntry imageEntry);

	// @optional -(void)onImageUploaded:(ImageEntry * _Nonnull)imageEntry;
	[Export ("onImageUploaded:")]
	void OnImageUploaded (ImageEntry imageEntry);

	// @optional -(void)onImageUploadEnqueued:(ImageEntryUpload * _Nonnull)imageEntryUpload;
	[Export ("onImageUploadEnqueued:")]
	void OnImageUploadEnqueued (ImageEntryUpload imageEntryUpload);

	// @optional -(void)onImageUploadProgress:(uint64_t)bytesSent bytesToSend:(uint64_t)bytesToSend imageEntryUpload:(ImageEntryUpload * _Nonnull)imageEntryUpload;
	[Export ("onImageUploadProgress:bytesToSend:imageEntryUpload:")]
	void OnImageUploadProgress (ulong bytesSent, ulong bytesToSend, ImageEntryUpload imageEntryUpload);

	// @optional -(void)onImageUploadError:(ImageEntryUpload * _Nonnull)imageEntryUpload error:(NSError * _Nonnull)error;
	[Export ("onImageUploadError:error:")]
	void OnImageUploadError (ImageEntryUpload imageEntryUpload, NSError error);

	// @optional -(NSInteger)indexOfImageEntry:(ImageEntry * _Nonnull)imageEntry __attribute__((warn_unused_result));
	[Export ("indexOfImageEntry:")]
	nint IndexOfImageEntry (ImageEntry imageEntry);
}

// @interface ImageGalleryCollectionViewBase : BaseListCollectionView <ImageGalleryViewModel>
[BaseType (typeof(BaseListCollectionView))]
interface ImageGalleryCollectionViewBase : IImageGalleryViewModel
{
	// @property (nonatomic, weak) UIView * _Nullable _uploadView;
	[NullAllowed, Export ("_uploadView", ArgumentSemantic.Weak)]
	UIView _uploadView { get; set; }

	// @property (readonly, nonatomic) NSInteger totalEntries;
	[Export ("totalEntries")]
	nint TotalEntries { get; }

	// -(void)onImageEntryDeleted:(ImageEntry * _Nonnull)imageEntry;
	[Export ("onImageEntryDeleted:")]
	void OnImageEntryDeleted (ImageEntry imageEntry);

	// -(void)onImageUploaded:(ImageEntry * _Nonnull)imageEntry;
	[Export ("onImageUploaded:")]
	void OnImageUploaded (ImageEntry imageEntry);

	// -(void)onImageUploadEnqueued:(ImageEntryUpload * _Nonnull)imageEntryUpload;
	[Export ("onImageUploadEnqueued:")]
	void OnImageUploadEnqueued (ImageEntryUpload imageEntryUpload);

	// -(void)onImageUploadProgress:(uint64_t)bytesSent bytesToSend:(uint64_t)bytesToSend imageEntryUpload:(ImageEntryUpload * _Nonnull)imageEntryUpload;
	[Export ("onImageUploadProgress:bytesToSend:imageEntryUpload:")]
	void OnImageUploadProgress (ulong bytesSent, ulong bytesToSend, ImageEntryUpload imageEntryUpload);

	// -(void)onImageUploadError:(ImageEntryUpload * _Nonnull)imageEntryUpload error:(NSError * _Nonnull)error;
	[Export ("onImageUploadError:error:")]
	void OnImageUploadError (ImageEntryUpload imageEntryUpload, NSError error);

	// -(NSInteger)indexOfImageEntry:(ImageEntry * _Nonnull)imageEntry __attribute__((warn_unused_result));
	[Export ("indexOfImageEntry:")]
	nint IndexOfImageEntry (ImageEntry imageEntry);

	// -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
	[Export ("createProgressPresenter")]
	[Verify (MethodToProperty)]
	ProgressPresenter CreateProgressPresenter { get; }

	// -(void)showUploadProgressView;
	[Export ("showUploadProgressView")]
	void ShowUploadProgressView ();

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface ImageGalleryDeleteConnector : ServerConnector
[BaseType (typeof(ServerConnector))]
[DisableDefaultCtor]
interface ImageGalleryDeleteConnector
{
	// @property (readonly, nonatomic) int64_t imageEntryId;
	[Export ("imageEntryId")]
	long ImageEntryId { get; }

	// -(instancetype _Nonnull)initWithImageEntryId:(int64_t)imageEntryId __attribute__((objc_designated_initializer));
	[Export ("initWithImageEntryId:")]
	[DesignatedInitializer]
	IntPtr Constructor (long imageEntryId);

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }
}

// @interface ImageGalleryDeleteInteractor : ServerWriteConnectorInteractor
[BaseType (typeof(ServerWriteConnectorInteractor))]
interface ImageGalleryDeleteInteractor
{
	// -(instancetype _Nonnull)initWithScreenlet:(BaseScreenlet * _Nullable)screenlet imageEntryId:(int64_t)imageEntryId repositoryId:(int64_t)repositoryId folderId:(int64_t)folderId page:(NSInteger)page __attribute__((objc_designated_initializer));
	[Export ("initWithScreenlet:imageEntryId:repositoryId:folderId:page:")]
	[DesignatedInitializer]
	IntPtr Constructor ([NullAllowed] BaseScreenlet screenlet, long imageEntryId, long repositoryId, long folderId, nint page);

	// -(ServerConnector * _Nullable)createConnector __attribute__((warn_unused_result));
	[NullAllowed, Export ("createConnector")]
	[Verify (MethodToProperty)]
	ServerConnector CreateConnector { get; }

	// -(void)writeToCache:(ServerConnector * _Nonnull)c;
	[Export ("writeToCache:")]
	void WriteToCache (ServerConnector c);
}

// @interface ImageGalleryGridCell : UICollectionViewCell
[BaseType (typeof(UICollectionViewCell))]
interface ImageGalleryGridCell
{
	// @property (copy, nonatomic) NSString * _Nonnull imageUrl;
	[Export ("imageUrl")]
	string ImageUrl { get; set; }

	// @property (nonatomic, strong) UIImage * _Nonnull image;
	[Export ("image", ArgumentSemantic.Strong)]
	UIImage Image { get; set; }

	// -(void)awakeFromNib __attribute__((objc_requires_super));
	[Export ("awakeFromNib")]
	[RequiresSuper]
	void AwakeFromNib ();

	// -(void)prepareForReuse;
	[Export ("prepareForReuse")]
	void PrepareForReuse ();

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface ImageGalleryLoadInteractor : BaseListPageLoadInteractor
[BaseType (typeof(BaseListPageLoadInteractor))]
interface ImageGalleryLoadInteractor
{
	// @property (readonly, copy, nonatomic, class) NSString * _Nonnull CacheKey;
	[Static]
	[Export ("CacheKey")]
	string CacheKey { get; }

	// @property (readonly, nonatomic) int64_t repositoryId;
	[Export ("repositoryId")]
	long RepositoryId { get; }

	// @property (readonly, nonatomic) int64_t folderId;
	[Export ("folderId")]
	long FolderId { get; }

	// @property (readonly, copy, nonatomic) NSArray<NSString *> * _Nonnull mimeTypes;
	[Export ("mimeTypes", ArgumentSemantic.Copy)]
	string[] MimeTypes { get; }

	// -(instancetype _Nonnull)initWithScreenlet:(BaseListScreenlet * _Nonnull)screenlet page:(NSInteger)page computeRowCount:(BOOL)computeRowCount repositoryId:(int64_t)repositoryId folderId:(int64_t)folderId mimeTypes:(NSArray<NSString *> * _Nonnull)mimeTypes __attribute__((objc_designated_initializer));
	[Export ("initWithScreenlet:page:computeRowCount:repositoryId:folderId:mimeTypes:")]
	[DesignatedInitializer]
	IntPtr Constructor (BaseListScreenlet screenlet, nint page, bool computeRowCount, long repositoryId, long folderId, string[] mimeTypes);

	// -(PaginationLiferayConnector * _Nonnull)createConnector __attribute__((warn_unused_result));
	[Export ("createConnector")]
	[Verify (MethodToProperty)]
	PaginationLiferayConnector CreateConnector { get; }

	// -(id _Nonnull)convertResult:(NSDictionary<NSString *,id> * _Nonnull)serverResult __attribute__((warn_unused_result));
	[Export ("convertResult:")]
	NSObject ConvertResult (NSDictionary<NSString, NSObject> serverResult);

	// -(NSString * _Nonnull)cacheKey:(PaginationLiferayConnector * _Nonnull)c __attribute__((warn_unused_result));
	[Export ("cacheKey:")]
	string CacheKey (PaginationLiferayConnector c);
}

// @interface ImageGalleryPageLiferayConnector : PaginationLiferayConnector
[BaseType (typeof(PaginationLiferayConnector))]
interface ImageGalleryPageLiferayConnector
{
	// @property (readonly, nonatomic) int64_t repositoryId;
	[Export ("repositoryId")]
	long RepositoryId { get; }

	// @property (readonly, nonatomic) int64_t folderId;
	[Export ("folderId")]
	long FolderId { get; }

	// @property (readonly, copy, nonatomic) NSArray<NSString *> * _Nonnull mimeTypes;
	[Export ("mimeTypes", ArgumentSemantic.Copy)]
	string[] MimeTypes { get; }

	// -(instancetype _Nonnull)initWithStartRow:(NSInteger)startRow endRow:(NSInteger)endRow computeRowCount:(BOOL)computeRowCount repositoryId:(int64_t)repositoryId folderId:(int64_t)folderId mimeTypes:(NSArray<NSString *> * _Nonnull)mimeTypes __attribute__((objc_designated_initializer));
	[Export ("initWithStartRow:endRow:computeRowCount:repositoryId:folderId:mimeTypes:")]
	[DesignatedInitializer]
	IntPtr Constructor (nint startRow, nint endRow, bool computeRowCount, long repositoryId, long folderId, string[] mimeTypes);

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }
}

// @interface ImageGalleryScreenlet : BaseListScreenlet
[BaseType (typeof(BaseListScreenlet))]
interface ImageGalleryScreenlet
{
	// @property (readonly, copy, nonatomic, class) NSString * _Nonnull DeleteImageAction;
	[Static]
	[Export ("DeleteImageAction")]
	string DeleteImageAction { get; }

	// @property (readonly, copy, nonatomic, class) NSString * _Nonnull UploadImageAction;
	[Static]
	[Export ("UploadImageAction")]
	string UploadImageAction { get; }

	// @property (readonly, copy, nonatomic, class) NSString * _Nonnull EnqueueUploadAction;
	[Static]
	[Export ("EnqueueUploadAction")]
	string EnqueueUploadAction { get; }

	// @property (nonatomic) int64_t repositoryId;
	[Export ("repositoryId")]
	long RepositoryId { get; set; }

	// @property (nonatomic) int64_t folderId;
	[Export ("folderId")]
	long FolderId { get; set; }

	// @property (copy, nonatomic) NSString * _Nonnull mimeTypes;
	[Export ("mimeTypes")]
	string MimeTypes { get; set; }

	// @property (copy, nonatomic) NSString * _Nonnull filePrefix;
	[Export ("filePrefix")]
	string FilePrefix { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable offlinePolicy;
	[NullAllowed, Export ("offlinePolicy")]
	string OfflinePolicy { get; set; }

	[Wrap ("WeakImageGalleryDelegate")]
	[NullAllowed]
	ImageGalleryScreenletDelegate ImageGalleryDelegate { get; }

	// @property (readonly, nonatomic, strong) id<ImageGalleryScreenletDelegate> _Nullable imageGalleryDelegate;
	[NullAllowed, Export ("imageGalleryDelegate", ArgumentSemantic.Strong)]
	NSObject WeakImageGalleryDelegate { get; }

	// @property (readonly, nonatomic, strong) id<ImageGalleryViewModel> _Nonnull viewModel;
	[Export ("viewModel", ArgumentSemantic.Strong)]
	ImageGalleryViewModel ViewModel { get; }

	// @property (copy, nonatomic) NSString * _Nonnull uploadDetailViewName;
	[Export ("uploadDetailViewName")]
	string UploadDetailViewName { get; set; }

	// @property (readonly, copy, nonatomic) NSArray<NSString *> * _Nonnull DefaultMimeTypes;
	[Export ("DefaultMimeTypes", ArgumentSemantic.Copy)]
	string[] DefaultMimeTypes { get; }

	// -(void)cancelUploads;
	[Export ("cancelUploads")]
	void CancelUploads ();

	// -(void)deleteImageCache;
	[Export ("deleteImageCache")]
	void DeleteImageCache ();

	// -(void)startMediaSelectorAndUpload;
	[Export ("startMediaSelectorAndUpload")]
	void StartMediaSelectorAndUpload ();

	// -(void)showDetailUploadView:(ImageEntryUpload * _Nonnull)imageUpload;
	[Export ("showDetailUploadView:")]
	void ShowDetailUploadView (ImageEntryUpload imageUpload);

	// -(void)deleteImageEntry:(ImageEntry * _Nonnull)imageEntry;
	[Export ("deleteImageEntry:")]
	void DeleteImageEntry (ImageEntry imageEntry);

	// -(void)onCreated;
	[Export ("onCreated")]
	void OnCreated ();

	// -(void)onShow;
	[Export ("onShow")]
	void OnShow ();

	// -(BOOL)performActionWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender;
	[Export ("performActionWithName:sender:")]
	bool PerformActionWithName (string name, [NullAllowed] NSObject sender);

	// -(Interactor * _Nullable)createInteractorWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender __attribute__((warn_unused_result));
	[Export ("createInteractorWithName:sender:")]
	[return: NullAllowed]
	Interactor CreateInteractorWithName (string name, [NullAllowed] NSObject sender);

	// -(void)onLoadPageErrorWithPage:(NSInteger)page error:(NSError * _Nonnull)error;
	[Export ("onLoadPageErrorWithPage:error:")]
	void OnLoadPageErrorWithPage (nint page, NSError error);

	// -(void)onLoadPageResultWithPage:(NSInteger)page rows:(NSArray * _Nonnull)rows rowCount:(NSInteger)rowCount;
	[Export ("onLoadPageResultWithPage:rows:rowCount:")]
	[Verify (StronglyTypedNSArray)]
	void OnLoadPageResultWithPage (nint page, NSObject[] rows, nint rowCount);

	// -(void)onSelectedRow:(id _Nonnull)row;
	[Export ("onSelectedRow:")]
	void OnSelectedRow (NSObject row);

	// -(BaseListPageLoadInteractor * _Nonnull)createPageLoadInteractorWithPage:(NSInteger)page computeRowCount:(BOOL)computeRowCount __attribute__((warn_unused_result));
	[Export ("createPageLoadInteractorWithPage:computeRowCount:")]
	BaseListPageLoadInteractor CreatePageLoadInteractorWithPage (nint page, bool computeRowCount);

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:themeName:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame, [NullAllowed] string themeName);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @protocol ImageGalleryScreenletDelegate <BaseScreenletDelegate>
[Protocol, Model]
interface ImageGalleryScreenletDelegate : IBaseScreenletDelegate
{
	// @optional -(void)screenlet:(ImageGalleryScreenlet * _Nonnull)screenlet onImageEntriesResponse:(NSArray<ImageEntry *> * _Nonnull)imageEntries;
	[Export ("screenlet:onImageEntriesResponse:")]
	void Screenlet (ImageGalleryScreenlet screenlet, ImageEntry[] imageEntries);

	// @optional -(void)screenlet:(ImageGalleryScreenlet * _Nonnull)screenlet onImageEntriesError:(NSError * _Nonnull)error;
	[Export ("screenlet:onImageEntriesError:")]
	void Screenlet (ImageGalleryScreenlet screenlet, NSError error);

	// @optional -(void)screenlet:(ImageGalleryScreenlet * _Nonnull)screenlet onImageEntrySelected:(ImageEntry * _Nonnull)imageEntry;
	[Export ("screenlet:onImageEntrySelected:")]
	void Screenlet (ImageGalleryScreenlet screenlet, ImageEntry imageEntry);

	// @optional -(void)screenlet:(ImageGalleryScreenlet * _Nonnull)screenlet onImageEntryDeleted:(ImageEntry * _Nonnull)imageEntry;
	[Export ("screenlet:onImageEntryDeleted:")]
	void Screenlet (ImageGalleryScreenlet screenlet, ImageEntry imageEntry);

	// @optional -(void)screenlet:(ImageGalleryScreenlet * _Nonnull)screenlet onImageEntryDeleteError:(NSError * _Nonnull)error;
	[Export ("screenlet:onImageEntryDeleteError:")]
	void Screenlet (ImageGalleryScreenlet screenlet, NSError error);

	// @optional -(void)screenlet:(ImageGalleryScreenlet * _Nonnull)screenlet onImageUploadStart:(ImageEntryUpload * _Nonnull)imageEntryUpload;
	[Export ("screenlet:onImageUploadStart:")]
	void Screenlet (ImageGalleryScreenlet screenlet, ImageEntryUpload imageEntryUpload);

	// @optional -(void)screenlet:(ImageGalleryScreenlet * _Nonnull)screenlet onImageUploadProgress:(ImageEntryUpload * _Nonnull)imageEntryUpload totalBytesSent:(uint64_t)totalBytesSent totalBytesToSend:(uint64_t)totalBytesToSend;
	[Export ("screenlet:onImageUploadProgress:totalBytesSent:totalBytesToSend:")]
	void Screenlet (ImageGalleryScreenlet screenlet, ImageEntryUpload imageEntryUpload, ulong totalBytesSent, ulong totalBytesToSend);

	// @optional -(void)screenlet:(ImageGalleryScreenlet * _Nonnull)screenlet onImageUploadError:(NSError * _Nonnull)error;
	[Export ("screenlet:onImageUploadError:")]
	void Screenlet (ImageGalleryScreenlet screenlet, NSError error);

	// @optional -(void)screenlet:(ImageGalleryScreenlet * _Nonnull)screenlet onImageUploaded:(ImageEntry * _Nonnull)image;
	[Export ("screenlet:onImageUploaded:")]
	void Screenlet (ImageGalleryScreenlet screenlet, ImageEntry image);

	// @optional -(BOOL)screenlet:(ImageGalleryScreenlet * _Nonnull)screenlet onImageUploadDetailViewCreated:(ImageUploadDetailViewBase * _Nonnull)view __attribute__((warn_unused_result));
	[Export ("screenlet:onImageUploadDetailViewCreated:")]
	bool Screenlet (ImageGalleryScreenlet screenlet, ImageUploadDetailViewBase view);
}

// @interface ImageGallerySlideshowCell : UICollectionViewCell
[BaseType (typeof(UICollectionViewCell))]
interface ImageGallerySlideshowCell
{
	// @property (copy, nonatomic) NSString * _Nonnull imageUrl;
	[Export ("imageUrl")]
	string ImageUrl { get; set; }

	// @property (nonatomic, strong) UIImage * _Nonnull image;
	[Export ("image", ArgumentSemantic.Strong)]
	UIImage Image { get; set; }

	// -(void)awakeFromNib __attribute__((objc_requires_super));
	[Export ("awakeFromNib")]
	[RequiresSuper]
	void AwakeFromNib ();

	// -(void)prepareForReuse;
	[Export ("prepareForReuse")]
	void PrepareForReuse ();

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface UploadFileConnector : ServerConnector
[BaseType (typeof(ServerConnector))]
[DisableDefaultCtor]
interface UploadFileConnector
{
	// @property (readonly, copy, nonatomic) NSString * _Nonnull fileName;
	[Export ("fileName")]
	string FileName { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull mimeType;
	[Export ("mimeType")]
	string MimeType { get; }

	// -(instancetype _Nonnull)initWithInputStream:(NSInputStream * _Nonnull)inputStream bytesToSend:(int64_t)bytesToSend fileName:(NSString * _Nonnull)fileName mimeType:(NSString * _Nonnull)mimeType parameter:(id _Nullable)parameter onUploadedBytes:(void (^ _Nullable)(id _Nullable, uint64_t, uint64_t))onUploadedBytes __attribute__((objc_designated_initializer));
	[Export ("initWithInputStream:bytesToSend:fileName:mimeType:parameter:onUploadedBytes:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSInputStream inputStream, long bytesToSend, string fileName, string mimeType, [NullAllowed] NSObject parameter, [NullAllowed] Action<NSObject, ulong, ulong> onUploadedBytes);

	// -(instancetype _Nonnull)initWithImage:(UIImage * _Nonnull)image fileName:(NSString * _Nonnull)fileName mimeType:(NSString * _Nonnull)mimeType parameter:(id _Nullable)parameter onUploadedBytes:(void (^ _Nullable)(id _Nullable, uint64_t, uint64_t))onUploadedBytes __attribute__((objc_designated_initializer));
	[Export ("initWithImage:fileName:mimeType:parameter:onUploadedBytes:")]
	[DesignatedInitializer]
	IntPtr Constructor (UIImage image, string fileName, string mimeType, [NullAllowed] NSObject parameter, [NullAllowed] Action<NSObject, ulong, ulong> onUploadedBytes);

	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(void)onProgress:(NSData * _Null_unspecified)data totalBytes:(int64_t)totalBytes;
	[Export ("onProgress:totalBytes:")]
	void OnProgress (NSData data, long totalBytes);

	// -(void)onFailure:(NSError * _Null_unspecified)error;
	[Export ("onFailure:")]
	void OnFailure (NSError error);

	// -(BOOL)doSendFile:(LRSession * _Nonnull)session data:(LRUploadData * _Nonnull)data error:(NSError * _Nullable * _Nullable)error;
	[Export ("doSendFile:data:error:")]
	bool DoSendFile (LRSession session, LRUploadData data, [NullAllowed] out NSError error);

	// -(void)onSuccess:(id _Null_unspecified)result;
	[Export ("onSuccess:")]
	void OnSuccess (NSObject result);
}

// @interface ImageGalleryUploadConnector : UploadFileConnector
[BaseType (typeof(UploadFileConnector))]
interface ImageGalleryUploadConnector
{
	// @property (readonly, nonatomic) int64_t repositoryId;
	[Export ("repositoryId")]
	long RepositoryId { get; }

	// @property (readonly, nonatomic) int64_t folderId;
	[Export ("folderId")]
	long FolderId { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull sourceFileName;
	[Export ("sourceFileName")]
	string SourceFileName { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull title;
	[Export ("title")]
	string Title { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull descrip;
	[Export ("descrip")]
	string Descrip { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull changeLog;
	[Export ("changeLog")]
	string ChangeLog { get; }

	// -(instancetype _Nonnull)initWithRepositoryId:(int64_t)repositoryId folderId:(int64_t)folderId sourceFileName:(NSString * _Nonnull)sourceFileName mimeType:(NSString * _Nonnull)mimeType title:(NSString * _Nonnull)title descrip:(NSString * _Nonnull)descrip changeLog:(NSString * _Nonnull)changeLog image:(UIImage * _Nonnull)image onUploadBytes:(void (^ _Nullable)(id _Nullable, uint64_t, uint64_t))onUploadBytes __attribute__((objc_designated_initializer));
	[Export ("initWithRepositoryId:folderId:sourceFileName:mimeType:title:descrip:changeLog:image:onUploadBytes:")]
	[DesignatedInitializer]
	IntPtr Constructor (long repositoryId, long folderId, string sourceFileName, string mimeType, string title, string descrip, string changeLog, UIImage image, [NullAllowed] Action<NSObject, ulong, ulong> onUploadBytes);

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }
}

// @interface ImageGalleryUploadInteractor : ServerWriteConnectorInteractor
[BaseType (typeof(ServerWriteConnectorInteractor))]
interface ImageGalleryUploadInteractor
{
	// -(instancetype _Nonnull)initWithScreenlet:(ImageGalleryScreenlet * _Nullable)screenlet imageUpload:(ImageEntryUpload * _Nonnull)imageUpload repositoryId:(int64_t)repositoryId folderId:(int64_t)folderId page:(NSInteger)page onUploadedBytes:(void (^ _Nullable)(id _Nullable, uint64_t, uint64_t))onUploadedBytes __attribute__((objc_designated_initializer));
	[Export ("initWithScreenlet:imageUpload:repositoryId:folderId:page:onUploadedBytes:")]
	[DesignatedInitializer]
	IntPtr Constructor ([NullAllowed] ImageGalleryScreenlet screenlet, ImageEntryUpload imageUpload, long repositoryId, long folderId, nint page, [NullAllowed] Action<NSObject, ulong, ulong> onUploadedBytes);

	// -(instancetype _Nonnull)initWithScreenlet:(ImageGalleryScreenlet * _Nullable)screenlet imageUpload:(ImageEntryUpload * _Nonnull)imageUpload repositoryId:(int64_t)repositoryId folderId:(int64_t)folderId page:(NSInteger)page onUploadedBytes:(void (^ _Nullable)(id _Nullable, uint64_t, uint64_t))onUploadedBytes cacheKeyUsed:(NSString * _Nonnull)cacheKeyUsed;
	[Export ("initWithScreenlet:imageUpload:repositoryId:folderId:page:onUploadedBytes:cacheKeyUsed:")]
	IntPtr Constructor ([NullAllowed] ImageGalleryScreenlet screenlet, ImageEntryUpload imageUpload, long repositoryId, long folderId, nint page, [NullAllowed] Action<NSObject, ulong, ulong> onUploadedBytes, string cacheKeyUsed);

	// -(ServerConnector * _Nullable)createConnector __attribute__((warn_unused_result));
	[NullAllowed, Export ("createConnector")]
	[Verify (MethodToProperty)]
	ServerConnector CreateConnector { get; }

	// -(void)completedConnector:(ServerConnector * _Nonnull)c;
	[Export ("completedConnector:")]
	void CompletedConnector (ServerConnector c);

	// -(void)writeToCache:(ServerConnector * _Nonnull)c;
	[Export ("writeToCache:")]
	void WriteToCache (ServerConnector c);

	// -(void)callOnSuccess;
	[Export ("callOnSuccess")]
	void CallOnSuccess ();
}

// @interface ImageGalleryView_default : ImageGalleryCollectionViewBase
[BaseType (typeof(ImageGalleryCollectionViewBase))]
interface ImageGalleryView_default
{
	// @property (readonly, nonatomic, class) NSInteger DefaultColumns;
	[Static]
	[Export ("DefaultColumns")]
	nint DefaultColumns { get; }

	// @property (nonatomic) CGFloat spacing;
	[Export ("spacing")]
	nfloat Spacing { get; set; }

	// @property (nonatomic) NSInteger columnNumber;
	[Export ("columnNumber")]
	nint ColumnNumber { get; set; }

	// -(void)layoutSubviews;
	[Export ("layoutSubviews")]
	void LayoutSubviews ();

	// -(void)onShow;
	[Export ("onShow")]
	void OnShow ();

	// -(void)onHide;
	[Export ("onHide")]
	void OnHide ();

	// -(void)doConfigureCollectionView:(UICollectionView * _Nonnull)collectionView;
	[Export ("doConfigureCollectionView:")]
	void DoConfigureCollectionView (UICollectionView collectionView);

	// -(void)doRegisterCellNibs;
	[Export ("doRegisterCellNibs")]
	void DoRegisterCellNibs ();

	// -(UICollectionViewLayout * _Nonnull)doCreateLayout __attribute__((warn_unused_result));
	[Export ("doCreateLayout")]
	[Verify (MethodToProperty)]
	UICollectionViewLayout DoCreateLayout { get; }

	// -(void)doFillLoadedCellWithIndexPath:(NSIndexPath * _Nonnull)indexPath cell:(UICollectionViewCell * _Nonnull)cell object:(id _Nonnull)object;
	[Export ("doFillLoadedCellWithIndexPath:cell:object:")]
	void DoFillLoadedCellWithIndexPath (NSIndexPath indexPath, UICollectionViewCell cell, NSObject @object);

	// -(void)doFillInProgressCellWithIndexPath:(NSIndexPath * _Nonnull)indexPath cell:(UICollectionViewCell * _Nonnull)cell;
	[Export ("doFillInProgressCellWithIndexPath:cell:")]
	void DoFillInProgressCellWithIndexPath (NSIndexPath indexPath, UICollectionViewCell cell);

	// -(NSString * _Nonnull)doGetCellIdWithIndexPath:(NSIndexPath * _Nonnull)indexPath object:(id _Nullable)object __attribute__((warn_unused_result));
	[Export ("doGetCellIdWithIndexPath:object:")]
	string DoGetCellIdWithIndexPath (NSIndexPath indexPath, [NullAllowed] NSObject @object);

	// -(void)changeLayout;
	[Export ("changeLayout")]
	void ChangeLayout ();

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface ImageGalleryView_default_list : BaseListTableView <ImageGalleryViewModel>
[BaseType (typeof(BaseListTableView))]
interface ImageGalleryView_default_list : IImageGalleryViewModel
{
	// @property (nonatomic, weak) UIView * _Nullable _uploadView;
	[NullAllowed, Export ("_uploadView", ArgumentSemantic.Weak)]
	UIView _uploadView { get; set; }

	// @property (readonly, nonatomic) NSInteger totalEntries;
	[Export ("totalEntries")]
	nint TotalEntries { get; }

	// -(void)onImageEntryDeleted:(ImageEntry * _Nonnull)imageEntry;
	[Export ("onImageEntryDeleted:")]
	void OnImageEntryDeleted (ImageEntry imageEntry);

	// -(void)onImageUploaded:(ImageEntry * _Nonnull)imageEntry;
	[Export ("onImageUploaded:")]
	void OnImageUploaded (ImageEntry imageEntry);

	// -(void)onImageUploadEnqueued:(ImageEntryUpload * _Nonnull)imageEntry;
	[Export ("onImageUploadEnqueued:")]
	void OnImageUploadEnqueued (ImageEntryUpload imageEntry);

	// -(void)showUploadProgressView;
	[Export ("showUploadProgressView")]
	void ShowUploadProgressView ();

	// -(void)onImageUploadProgress:(uint64_t)bytesSent bytesToSend:(uint64_t)bytesToSend imageEntryUpload:(ImageEntryUpload * _Nonnull)imageEntryUpload;
	[Export ("onImageUploadProgress:bytesToSend:imageEntryUpload:")]
	void OnImageUploadProgress (ulong bytesSent, ulong bytesToSend, ImageEntryUpload imageEntryUpload);

	// -(void)onImageUploadError:(ImageEntryUpload * _Nonnull)imageEntryUpload error:(NSError * _Nonnull)error;
	[Export ("onImageUploadError:error:")]
	void OnImageUploadError (ImageEntryUpload imageEntryUpload, NSError error);

	// -(NSInteger)indexOfImageEntry:(ImageEntry * _Nonnull)imageEntry __attribute__((warn_unused_result));
	[Export ("indexOfImageEntry:")]
	nint IndexOfImageEntry (ImageEntry imageEntry);

	// -(void)onCreated;
	[Export ("onCreated")]
	void OnCreated ();

	// -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
	[Export ("createProgressPresenter")]
	[Verify (MethodToProperty)]
	ProgressPresenter CreateProgressPresenter { get; }

	// -(void)doFillLoadedCellWithRow:(NSInteger)row cell:(UITableViewCell * _Nonnull)cell object:(id _Nonnull)object;
	[Export ("doFillLoadedCellWithRow:cell:object:")]
	void DoFillLoadedCellWithRow (nint row, UITableViewCell cell, NSObject @object);

	// -(void)doFillInProgressCellWithRow:(NSInteger)row cell:(UITableViewCell * _Nonnull)cell;
	[Export ("doFillInProgressCellWithRow:cell:")]
	void DoFillInProgressCellWithRow (nint row, UITableViewCell cell);

	// -(NSString * _Nonnull)doGetCellIdWithRow:(NSInteger)row object:(id _Nullable)object __attribute__((warn_unused_result));
	[Export ("doGetCellIdWithRow:object:")]
	string DoGetCellIdWithRow (nint row, [NullAllowed] NSObject @object);

	// -(void)doRegisterCellNibs;
	[Export ("doRegisterCellNibs")]
	void DoRegisterCellNibs ();

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface ImageGalleryView_default_slideshow : ImageGalleryCollectionViewBase
[BaseType (typeof(ImageGalleryCollectionViewBase))]
interface ImageGalleryView_default_slideshow
{
	// -(void)doConfigureCollectionView:(UICollectionView * _Nonnull)collectionView;
	[Export ("doConfigureCollectionView:")]
	void DoConfigureCollectionView (UICollectionView collectionView);

	// -(void)doRegisterCellNibs;
	[Export ("doRegisterCellNibs")]
	void DoRegisterCellNibs ();

	// -(UICollectionViewLayout * _Nonnull)doCreateLayout __attribute__((warn_unused_result));
	[Export ("doCreateLayout")]
	[Verify (MethodToProperty)]
	UICollectionViewLayout DoCreateLayout { get; }

	// -(void)updateRefreshControl;
	[Export ("updateRefreshControl")]
	void UpdateRefreshControl ();

	// -(void)doFillLoadedCellWithIndexPath:(NSIndexPath * _Nonnull)indexPath cell:(UICollectionViewCell * _Nonnull)cell object:(id _Nonnull)object;
	[Export ("doFillLoadedCellWithIndexPath:cell:object:")]
	void DoFillLoadedCellWithIndexPath (NSIndexPath indexPath, UICollectionViewCell cell, NSObject @object);

	// -(void)doFillInProgressCellWithIndexPath:(NSIndexPath * _Nonnull)indexPath cell:(UICollectionViewCell * _Nonnull)cell;
	[Export ("doFillInProgressCellWithIndexPath:cell:")]
	void DoFillInProgressCellWithIndexPath (NSIndexPath indexPath, UICollectionViewCell cell);

	// -(NSString * _Nonnull)doGetCellIdWithIndexPath:(NSIndexPath * _Nonnull)indexPath object:(id _Nullable)object __attribute__((warn_unused_result));
	[Export ("doGetCellIdWithIndexPath:object:")]
	string DoGetCellIdWithIndexPath (NSIndexPath indexPath, [NullAllowed] NSObject @object);

	// -(UIEdgeInsets)collectionView:(UICollectionView * _Nonnull)collectionView layout:(UICollectionViewLayout * _Nonnull)collectionViewLayout insetForSectionAtIndex:(NSInteger)section __attribute__((warn_unused_result));
	[Export ("collectionView:layout:insetForSectionAtIndex:")]
	UIEdgeInsets CollectionView (UICollectionView collectionView, UICollectionViewLayout collectionViewLayout, nint section);

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface ImageUploadDetailViewBase : UIView
[BaseType (typeof(UIView))]
interface ImageUploadDetailViewBase
{
	// @property (nonatomic, strong) UIImage * _Nullable image;
	[NullAllowed, Export ("image", ArgumentSemantic.Strong)]
	UIImage Image { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable imageTitle;
	[NullAllowed, Export ("imageTitle")]
	string ImageTitle { get; set; }

	// @property (nonatomic, weak) ImageGalleryScreenlet * _Nullable screenlet;
	[NullAllowed, Export ("screenlet", ArgumentSemantic.Weak)]
	ImageGalleryScreenlet Screenlet { get; set; }

	// @property (nonatomic, weak) UIImageView * _Nullable imagePreview __attribute__((iboutlet));
	[NullAllowed, Export ("imagePreview", ArgumentSemantic.Weak)]
	UIImageView ImagePreview { get; set; }

	// @property (nonatomic, weak) UITextField * _Nullable titleText __attribute__((iboutlet));
	[NullAllowed, Export ("titleText", ArgumentSemantic.Weak)]
	UITextField TitleText { get; set; }

	// @property (nonatomic, weak) UITextView * _Nullable descripText __attribute__((iboutlet));
	[NullAllowed, Export ("descripText", ArgumentSemantic.Weak)]
	UITextView DescripText { get; set; }

	// -(void)startUpload;
	[Export ("startUpload")]
	void StartUpload ();

	// -(void)startUpload:(NSString * _Nonnull)title notes:(NSString * _Nonnull)notes thumbnail:(UIImage * _Nullable)thumbnail;
	[Export ("startUpload:notes:thumbnail:")]
	void StartUpload (string title, string notes, [NullAllowed] UIImage thumbnail);

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface ImageUploadDetailViewController_default : UIViewController
[BaseType (typeof(UIViewController))]
interface ImageUploadDetailViewController_default
{
	// @property (nonatomic, strong) ImageUploadDetailViewBase * _Nullable imageUploadDetailview;
	[NullAllowed, Export ("imageUploadDetailview", ArgumentSemantic.Strong)]
	ImageUploadDetailViewBase ImageUploadDetailview { get; set; }

	// -(instancetype _Nonnull)initWithImageUploadDetailview:(ImageUploadDetailViewBase * _Nonnull)imageUploadDetailview __attribute__((objc_designated_initializer));
	[Export ("initWithImageUploadDetailview:")]
	[DesignatedInitializer]
	IntPtr Constructor (ImageUploadDetailViewBase imageUploadDetailview);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);

	// -(void)viewDidLoad;
	[Export ("viewDidLoad")]
	void ViewDidLoad ();

	// -(void)addNavBarButtons;
	[Export ("addNavBarButtons")]
	void AddNavBarButtons ();

	// -(void)addImageUploadView;
	[Export ("addImageUploadView")]
	void AddImageUploadView ();

	// -(void)startUploadClick;
	[Export ("startUploadClick")]
	void StartUploadClick ();

	// -(void)cancelClick;
	[Export ("cancelClick")]
	void CancelClick ();
}

// @interface ImageUploadDetailView_default : ImageUploadDetailViewBase <UIScrollViewDelegate, UITextViewDelegate>
[BaseType (typeof(ImageUploadDetailViewBase))]
interface ImageUploadDetailView_default : IUIScrollViewDelegate, IUITextViewDelegate
{
	// @property (nonatomic, strong) UIImage * _Nullable image;
	[NullAllowed, Export ("image", ArgumentSemantic.Strong)]
	UIImage Image { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable imageTitle;
	[NullAllowed, Export ("imageTitle")]
	string ImageTitle { get; set; }

	// -(void)awakeFromNib __attribute__((objc_requires_super));
	[Export ("awakeFromNib")]
	[RequiresSuper]
	void AwakeFromNib ();

	// -(void)initialize;
	[Export ("initialize")]
	void Initialize ();

	// -(void)didMoveToWindow;
	[Export ("didMoveToWindow")]
	void DidMoveToWindow ();

	// -(void)willMoveToWindow:(UIWindow * _Nullable)newWindow;
	[Export ("willMoveToWindow:")]
	void WillMoveToWindow ([NullAllowed] UIWindow newWindow);

	// -(void)textViewDidBeginEditing:(UITextView * _Nonnull)textView;
	[Export ("textViewDidBeginEditing:")]
	void TextViewDidBeginEditing (UITextView textView);

	// -(void)textViewDidEndEditing:(UITextView * _Nonnull)textView;
	[Export ("textViewDidEndEditing:")]
	void TextViewDidEndEditing (UITextView textView);

	// -(void)dismissKeyboard;
	[Export ("dismissKeyboard")]
	void DismissKeyboard ();

	// -(void)keyboardWillShow:(NSNotification * _Nonnull)notification;
	[Export ("keyboardWillShow:")]
	void KeyboardWillShow (NSNotification notification);

	// -(void)keyboardWillHide:(NSNotification * _Nonnull)notification;
	[Export ("keyboardWillHide:")]
	void KeyboardWillHide (NSNotification notification);

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface LRCookieBlockCallback : NSObject
[BaseType (typeof(NSObject))]
[DisableDefaultCtor]
interface LRCookieBlockCallback
{
	// -(void)onSuccess:(LRSession * _Null_unspecified)session;
	[Export ("onSuccess:")]
	void OnSuccess (LRSession session);

	// -(void)onFailure:(NSError * _Null_unspecified)error;
	[Export ("onFailure:")]
	void OnFailure (NSError error);
}

// @interface LiferayScreens_Swift_3270 (LRSession)
[Category]
[BaseType (typeof(LRSession))]
interface LRSession_LiferayScreens_Swift_3270
{
	// @property (readonly, copy, nonatomic) NSString * _Nullable serverName;
	[NullAllowed, Export ("serverName")]
	string ServerName { }
}

// @interface Liferay62AssetListPageConnector : AssetListPageLiferayConnector
[BaseType (typeof(AssetListPageLiferayConnector))]
interface Liferay62AssetListPageConnector
{
	// -(void)doGetPageRowsWithSession:(LRBatchSession * _Nonnull)session entryQuery:(LRJSONObjectWrapper * _Nonnull)entryQuery obc:(LRJSONObjectWrapper * _Nullable)obc;
	[Export ("doGetPageRowsWithSession:entryQuery:obc:")]
	void DoGetPageRowsWithSession (LRBatchSession session, LRJSONObjectWrapper entryQuery, [NullAllowed] LRJSONObjectWrapper obc);

	// -(void)doGetRowCountWithSession:(LRBatchSession * _Nonnull)session entryQuery:(LRJSONObjectWrapper * _Nonnull)entryQuery;
	[Export ("doGetRowCountWithSession:entryQuery:")]
	void DoGetRowCountWithSession (LRBatchSession session, LRJSONObjectWrapper entryQuery);

	// -(instancetype _Nonnull)initWithStartRow:(NSInteger)startRow endRow:(NSInteger)endRow computeRowCount:(BOOL)computeRowCount __attribute__((objc_designated_initializer));
	[Export ("initWithStartRow:endRow:computeRowCount:")]
	[DesignatedInitializer]
	IntPtr Constructor (nint startRow, nint endRow, bool computeRowCount);
}

// @protocol LiferayConnectorFactory
[Protocol, Model]
interface LiferayConnectorFactory
{
	// @required -(GetUserByEmailLiferayConnector * _Nonnull)createGetUserByEmailConnectorWithCompanyId:(int64_t)companyId emailAddress:(NSString * _Nonnull)emailAddress __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createGetUserByEmailConnectorWithCompanyId:emailAddress:")]
	GetUserByEmailLiferayConnector CreateGetUserByEmailConnectorWithCompanyId (long companyId, string emailAddress);

	// @required -(GetUserByScreenNameLiferayConnector * _Nonnull)createGetUserByScreenNameConnectorWithCompanyId:(int64_t)companyId screenName:(NSString * _Nonnull)screenName __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createGetUserByScreenNameConnectorWithCompanyId:screenName:")]
	GetUserByScreenNameLiferayConnector CreateGetUserByScreenNameConnectorWithCompanyId (long companyId, string screenName);

	// @required -(GetUserByUserIdLiferayConnector * _Nonnull)createGetUserByUserIdConnectorWithUserId:(int64_t)userId __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createGetUserByUserIdConnectorWithUserId:")]
	GetUserByUserIdLiferayConnector CreateGetUserByUserIdConnectorWithUserId (long userId);

	// @required -(GetUserByEmailLiferayConnector * _Nonnull)createLoginByEmailConnectorWithCompanyId:(int64_t)companyId emailAddress:(NSString * _Nonnull)emailAddress password:(NSString * _Nonnull)password __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createLoginByEmailConnectorWithCompanyId:emailAddress:password:")]
	GetUserByEmailLiferayConnector CreateLoginByEmailConnectorWithCompanyId (long companyId, string emailAddress, string password);

	// @required -(GetUserByScreenNameLiferayConnector * _Nonnull)createLoginByScreenNameConnectorWithCompanyId:(int64_t)companyId screenName:(NSString * _Nonnull)screenName password:(NSString * _Nonnull)password __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createLoginByScreenNameConnectorWithCompanyId:screenName:password:")]
	GetUserByScreenNameLiferayConnector CreateLoginByScreenNameConnectorWithCompanyId (long companyId, string screenName, string password);

	// @required -(GetUserByUserIdLiferayConnector * _Nonnull)createLoginByUserIdConnectorWithUserId:(int64_t)userId password:(NSString * _Nonnull)password __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createLoginByUserIdConnectorWithUserId:password:")]
	GetUserByUserIdLiferayConnector CreateLoginByUserIdConnectorWithUserId (long userId, string password);

	// @required -(ForgotPasswordBaseLiferayConnector * _Nonnull)createForgotPasswordByEmailConnectorWithViewModel:(id<ForgotPasswordViewModel> _Nonnull)viewModel anonymousUsername:(NSString * _Nonnull)anonymousUsername anonymousPassword:(NSString * _Nonnull)anonymousPassword __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createForgotPasswordByEmailConnectorWithViewModel:anonymousUsername:anonymousPassword:")]
	ForgotPasswordBaseLiferayConnector CreateForgotPasswordByEmailConnectorWithViewModel (ForgotPasswordViewModel viewModel, string anonymousUsername, string anonymousPassword);

	// @required -(ForgotPasswordBaseLiferayConnector * _Nonnull)createForgotPasswordByScreenNameConnectorWithViewModel:(id<ForgotPasswordViewModel> _Nonnull)viewModel anonymousUsername:(NSString * _Nonnull)anonymousUsername anonymousPassword:(NSString * _Nonnull)anonymousPassword __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createForgotPasswordByScreenNameConnectorWithViewModel:anonymousUsername:anonymousPassword:")]
	ForgotPasswordBaseLiferayConnector CreateForgotPasswordByScreenNameConnectorWithViewModel (ForgotPasswordViewModel viewModel, string anonymousUsername, string anonymousPassword);

	// @required -(ForgotPasswordBaseLiferayConnector * _Nonnull)createForgotPasswordByUserIdConnectorWithViewModel:(id<ForgotPasswordViewModel> _Nonnull)viewModel anonymousUsername:(NSString * _Nonnull)anonymousUsername anonymousPassword:(NSString * _Nonnull)anonymousPassword __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createForgotPasswordByUserIdConnectorWithViewModel:anonymousUsername:anonymousPassword:")]
	ForgotPasswordBaseLiferayConnector CreateForgotPasswordByUserIdConnectorWithViewModel (ForgotPasswordViewModel viewModel, string anonymousUsername, string anonymousPassword);

	// @required -(SignUpLiferayConnector * _Nonnull)createSignUpConnectorWithViewModel:(id<SignUpViewModel> _Nonnull)viewModel anonymousUsername:(NSString * _Nonnull)anonymousUsername anonymousPassword:(NSString * _Nonnull)anonymousPassword __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createSignUpConnectorWithViewModel:anonymousUsername:anonymousPassword:")]
	SignUpLiferayConnector CreateSignUpConnectorWithViewModel (SignUpViewModel viewModel, string anonymousUsername, string anonymousPassword);

	// @required -(UpdateCurrentUserLiferayConnector * _Nonnull)createUpdateCurrentUserConnectorWithViewModel:(id<SignUpViewModel> _Nonnull)viewModel __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createUpdateCurrentUserConnectorWithViewModel:")]
	UpdateCurrentUserLiferayConnector CreateUpdateCurrentUserConnectorWithViewModel (SignUpViewModel viewModel);

	// @required -(UploadUserPortraitLiferayConnector * _Nonnull)createUploadUserPortraitConnectorWithUserId:(int64_t)userId image:(UIImage * _Nonnull)image __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createUploadUserPortraitConnectorWithUserId:image:")]
	UploadUserPortraitLiferayConnector CreateUploadUserPortraitConnectorWithUserId (long userId, UIImage image);

	// @required -(AssetListPageLiferayConnector * _Nonnull)createAssetListPageConnectorWithStartRow:(NSInteger)startRow endRow:(NSInteger)endRow computeRowCount:(BOOL)computeRowCount __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createAssetListPageConnectorWithStartRow:endRow:computeRowCount:")]
	AssetListPageLiferayConnector CreateAssetListPageConnectorWithStartRow (nint startRow, nint endRow, bool computeRowCount);

	// @required -(WebContentListPageLiferayConnector * _Nonnull)createWebContentListPageConnectorWithGroupId:(int64_t)groupId folderId:(int64_t)folderId startRow:(NSInteger)startRow endRow:(NSInteger)endRow computeRowCount:(BOOL)computeRowCount __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createWebContentListPageConnectorWithGroupId:folderId:startRow:endRow:computeRowCount:")]
	WebContentListPageLiferayConnector CreateWebContentListPageConnectorWithGroupId (long groupId, long folderId, nint startRow, nint endRow, bool computeRowCount);

	// @required -(DDLListPageLiferayConnector * _Nonnull)createDDLListPageConnectorWithViewModel:(id<DDLListViewModel> _Nonnull)viewModel startRow:(NSInteger)startRow endRow:(NSInteger)endRow computeRowCount:(BOOL)computeRowCount __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createDDLListPageConnectorWithViewModel:startRow:endRow:computeRowCount:")]
	DDLListPageLiferayConnector CreateDDLListPageConnectorWithViewModel (DDLListViewModel viewModel, nint startRow, nint endRow, bool computeRowCount);

	// @required -(WebContentLoadHtmlLiferayConnector * _Nonnull)createWebContentLoadHtmlConnectorWithGroupId:(int64_t)groupId articleId:(NSString * _Nonnull)articleId __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createWebContentLoadHtmlConnectorWithGroupId:articleId:")]
	WebContentLoadHtmlLiferayConnector CreateWebContentLoadHtmlConnectorWithGroupId (long groupId, string articleId);

	// @required -(WebContentLoadStructuredLiferayConnector * _Nonnull)createWebContentLoadStructuredConnectorWithGroupId:(int64_t)groupId articleId:(NSString * _Nonnull)articleId structureId:(int64_t)structureId __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createWebContentLoadStructuredConnectorWithGroupId:articleId:structureId:")]
	WebContentLoadStructuredLiferayConnector CreateWebContentLoadStructuredConnectorWithGroupId (long groupId, string articleId, long structureId);

	// @required -(DDLFormLoadLiferayConnector * _Nonnull)createDDLFormLoadConnector:(int64_t)structureId __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createDDLFormLoadConnector:")]
	DDLFormLoadLiferayConnector CreateDDLFormLoadConnector (long structureId);

	// @required -(DDLFormRecordLoadLiferayConnector * _Nonnull)createDDLFormRecordLoadConnector:(int64_t)recordId __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createDDLFormRecordLoadConnector:")]
	DDLFormRecordLoadLiferayConnector CreateDDLFormRecordLoadConnector (long recordId);

	// @required -(DDLFormSubmitLiferayConnector * _Nonnull)createDDLFormSubmitConnectorWithValues:(NSDictionary<NSString *,id> * _Nonnull)values viewModel:(id<DDLFormViewModel> _Nullable)viewModel __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createDDLFormSubmitConnectorWithValues:viewModel:")]
	DDLFormSubmitLiferayConnector CreateDDLFormSubmitConnectorWithValues (NSDictionary<NSString, NSObject> values, [NullAllowed] DDLFormViewModel viewModel);

	// @required -(DDLFormUploadLiferayConnector * _Nonnull)createDDLFormUploadConnectorWithDocument:(DDMFieldDocument * _Nonnull)document filePrefix:(NSString * _Nonnull)filePrefix repositoryId:(int64_t)repositoryId folderId:(int64_t)folderId onProgress:(void (^ _Nullable)(DDMFieldDocument * _Nonnull, uint64_t, uint64_t))onProgress __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createDDLFormUploadConnectorWithDocument:filePrefix:repositoryId:folderId:onProgress:")]
	DDLFormUploadLiferayConnector CreateDDLFormUploadConnectorWithDocument (DDMFieldDocument document, string filePrefix, long repositoryId, long folderId, [NullAllowed] Action<DDMFieldDocument, ulong, ulong> onProgress);

	// @required -(AssetLoadByEntryIdLiferayConnector * _Nullable)createAssetLoadByEntryIdConnector:(int64_t)entryId __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createAssetLoadByEntryIdConnector:")]
	[return: NullAllowed]
	AssetLoadByEntryIdLiferayConnector CreateAssetLoadByEntryIdConnector (long entryId);

	// @required -(AssetLoadByClassPKLiferayConnector * _Nullable)createAssetLoadByClassPKConnector:(NSString * _Nonnull)className classPK:(int64_t)classPK __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createAssetLoadByClassPKConnector:classPK:")]
	[return: NullAllowed]
	AssetLoadByClassPKLiferayConnector CreateAssetLoadByClassPKConnector (string className, long classPK);

	// @required -(AssetLoadByPortletItemNameLiferayConnector * _Nullable)createAssetLoadByPortletItemNameConnectorWithPortletItemName:(NSString * _Nonnull)portletItemName __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createAssetLoadByPortletItemNameConnectorWithPortletItemName:")]
	[return: NullAllowed]
	AssetLoadByPortletItemNameLiferayConnector CreateAssetLoadByPortletItemNameConnectorWithPortletItemName (string portletItemName);

	// @required -(RatingLoadByEntryIdLiferayConnector * _Nullable)createRatingLoadByEntryIdConnectorWithEntryId:(int64_t)entryId ratingsGroupCount:(int32_t)ratingsGroupCount __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createRatingLoadByEntryIdConnectorWithEntryId:ratingsGroupCount:")]
	[return: NullAllowed]
	RatingLoadByEntryIdLiferayConnector CreateRatingLoadByEntryIdConnectorWithEntryId (long entryId, int ratingsGroupCount);

	// @required -(RatingLoadByClassPKLiferayConnector * _Nullable)createRatingLoadByClassPKConnector:(int64_t)classPK className:(NSString * _Nonnull)className ratingsGroupCount:(int32_t)ratingsGroupCount __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createRatingLoadByClassPKConnector:className:ratingsGroupCount:")]
	[return: NullAllowed]
	RatingLoadByClassPKLiferayConnector CreateRatingLoadByClassPKConnector (long classPK, string className, int ratingsGroupCount);

	// @required -(RatingUpdateLiferayConnector * _Nullable)createRatingUpdateConnectorWithClassPK:(int64_t)classPK className:(NSString * _Nonnull)className score:(double)score ratingsGroupCount:(int32_t)ratingsGroupCount __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createRatingUpdateConnectorWithClassPK:className:score:ratingsGroupCount:")]
	[return: NullAllowed]
	RatingUpdateLiferayConnector CreateRatingUpdateConnectorWithClassPK (long classPK, string className, double score, int ratingsGroupCount);

	// @required -(RatingDeleteLiferayConnector * _Nullable)createRatingDeleteConnectorWithClassPK:(int64_t)classPK className:(NSString * _Nonnull)className ratingsGroupCount:(int32_t)ratingsGroupCount __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createRatingDeleteConnectorWithClassPK:className:ratingsGroupCount:")]
	[return: NullAllowed]
	RatingDeleteLiferayConnector CreateRatingDeleteConnectorWithClassPK (long classPK, string className, int ratingsGroupCount);

	// @required -(ImageGalleryDeleteConnector * _Nullable)createImageGalleryDeleteConnector:(int64_t)imageEntryId __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createImageGalleryDeleteConnector:")]
	[return: NullAllowed]
	ImageGalleryDeleteConnector CreateImageGalleryDeleteConnector (long imageEntryId);

	// @required -(ImageGalleryPageLiferayConnector * _Nullable)createImageGalleryPageConnectorWithStartRow:(NSInteger)startRow endRow:(NSInteger)endRow computeRowCount:(BOOL)computeRowCount repositoryId:(int64_t)repositoryId folderId:(int64_t)folderId mimeTypes:(NSArray<NSString *> * _Nonnull)mimeTypes __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createImageGalleryPageConnectorWithStartRow:endRow:computeRowCount:repositoryId:folderId:mimeTypes:")]
	[return: NullAllowed]
	ImageGalleryPageLiferayConnector CreateImageGalleryPageConnectorWithStartRow (nint startRow, nint endRow, bool computeRowCount, long repositoryId, long folderId, string[] mimeTypes);

	// @required -(ImageGalleryUploadConnector * _Nullable)createImageGalleryUploadConnectorWithRepositoryId:(int64_t)repositoryId folderId:(int64_t)folderId sourceFileName:(NSString * _Nonnull)sourceFileName mimeType:(NSString * _Nonnull)mimeType title:(NSString * _Nonnull)title descrip:(NSString * _Nonnull)descrip changeLog:(NSString * _Nonnull)changeLog image:(UIImage * _Nonnull)image onUploadBytes:(void (^ _Nullable)(id _Nullable, uint64_t, uint64_t))onUploadBytes __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createImageGalleryUploadConnectorWithRepositoryId:folderId:sourceFileName:mimeType:title:descrip:changeLog:image:onUploadBytes:")]
	[return: NullAllowed]
	ImageGalleryUploadConnector CreateImageGalleryUploadConnectorWithRepositoryId (long repositoryId, long folderId, string sourceFileName, string mimeType, string title, string descrip, string changeLog, UIImage image, [NullAllowed] Action<NSObject, ulong, ulong> onUploadBytes);

	// @required -(CommentListPageLiferayConnector * _Nullable)createCommentListPageConnectorWithClassName:(NSString * _Nonnull)className classPK:(int64_t)classPK startRow:(NSInteger)startRow endRow:(NSInteger)endRow computeRowCount:(BOOL)computeRowCount __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createCommentListPageConnectorWithClassName:classPK:startRow:endRow:computeRowCount:")]
	[return: NullAllowed]
	CommentListPageLiferayConnector CreateCommentListPageConnectorWithClassName (string className, long classPK, nint startRow, nint endRow, bool computeRowCount);

	// @required -(CommentAddLiferayConnector * _Nullable)createCommentAddConnectorWithClassName:(NSString * _Nonnull)className classPK:(int64_t)classPK body:(NSString * _Nonnull)body __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createCommentAddConnectorWithClassName:classPK:body:")]
	[return: NullAllowed]
	CommentAddLiferayConnector CreateCommentAddConnectorWithClassName (string className, long classPK, string body);

	// @required -(CommentLoadLiferayConnector * _Nullable)createCommentLoadConnectorWithCommentId:(int64_t)commentId __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createCommentLoadConnectorWithCommentId:")]
	[return: NullAllowed]
	CommentLoadLiferayConnector CreateCommentLoadConnectorWithCommentId (long commentId);

	// @required -(CommentDeleteLiferayConnector * _Nullable)createCommentDeleteConnectorWithCommentId:(int64_t)commentId __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createCommentDeleteConnectorWithCommentId:")]
	[return: NullAllowed]
	CommentDeleteLiferayConnector CreateCommentDeleteConnectorWithCommentId (long commentId);

	// @required -(CommentUpdateLiferayConnector * _Nullable)createCommentUpdateConnectorWithCommentId:(int64_t)commentId body:(NSString * _Nonnull)body __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createCommentUpdateConnectorWithCommentId:body:")]
	[return: NullAllowed]
	CommentUpdateLiferayConnector CreateCommentUpdateConnectorWithCommentId (long commentId, string body);
}

// @interface Liferay62ConnectorFactory : NSObject <LiferayConnectorFactory>
[BaseType (typeof(NSObject))]
interface Liferay62ConnectorFactory : ILiferayConnectorFactory
{
	// -(GetUserByEmailLiferayConnector * _Nonnull)createGetUserByEmailConnectorWithCompanyId:(int64_t)companyId emailAddress:(NSString * _Nonnull)emailAddress __attribute__((warn_unused_result));
	[Export ("createGetUserByEmailConnectorWithCompanyId:emailAddress:")]
	GetUserByEmailLiferayConnector CreateGetUserByEmailConnectorWithCompanyId (long companyId, string emailAddress);

	// -(GetUserByScreenNameLiferayConnector * _Nonnull)createGetUserByScreenNameConnectorWithCompanyId:(int64_t)companyId screenName:(NSString * _Nonnull)screenName __attribute__((warn_unused_result));
	[Export ("createGetUserByScreenNameConnectorWithCompanyId:screenName:")]
	GetUserByScreenNameLiferayConnector CreateGetUserByScreenNameConnectorWithCompanyId (long companyId, string screenName);

	// -(GetUserByUserIdLiferayConnector * _Nonnull)createGetUserByUserIdConnectorWithUserId:(int64_t)userId __attribute__((warn_unused_result));
	[Export ("createGetUserByUserIdConnectorWithUserId:")]
	GetUserByUserIdLiferayConnector CreateGetUserByUserIdConnectorWithUserId (long userId);

	// -(GetUserByEmailLiferayConnector * _Nonnull)createLoginByEmailConnectorWithCompanyId:(int64_t)companyId emailAddress:(NSString * _Nonnull)emailAddress password:(NSString * _Nonnull)password __attribute__((warn_unused_result));
	[Export ("createLoginByEmailConnectorWithCompanyId:emailAddress:password:")]
	GetUserByEmailLiferayConnector CreateLoginByEmailConnectorWithCompanyId (long companyId, string emailAddress, string password);

	// -(GetUserByScreenNameLiferayConnector * _Nonnull)createLoginByScreenNameConnectorWithCompanyId:(int64_t)companyId screenName:(NSString * _Nonnull)screenName password:(NSString * _Nonnull)password __attribute__((warn_unused_result));
	[Export ("createLoginByScreenNameConnectorWithCompanyId:screenName:password:")]
	GetUserByScreenNameLiferayConnector CreateLoginByScreenNameConnectorWithCompanyId (long companyId, string screenName, string password);

	// -(GetUserByUserIdLiferayConnector * _Nonnull)createLoginByUserIdConnectorWithUserId:(int64_t)userId password:(NSString * _Nonnull)password __attribute__((warn_unused_result));
	[Export ("createLoginByUserIdConnectorWithUserId:password:")]
	GetUserByUserIdLiferayConnector CreateLoginByUserIdConnectorWithUserId (long userId, string password);

	// -(ForgotPasswordBaseLiferayConnector * _Nonnull)createForgotPasswordByEmailConnectorWithViewModel:(id<ForgotPasswordViewModel> _Nonnull)viewModel anonymousUsername:(NSString * _Nonnull)anonymousUsername anonymousPassword:(NSString * _Nonnull)anonymousPassword __attribute__((warn_unused_result));
	[Export ("createForgotPasswordByEmailConnectorWithViewModel:anonymousUsername:anonymousPassword:")]
	ForgotPasswordBaseLiferayConnector CreateForgotPasswordByEmailConnectorWithViewModel (ForgotPasswordViewModel viewModel, string anonymousUsername, string anonymousPassword);

	// -(ForgotPasswordBaseLiferayConnector * _Nonnull)createForgotPasswordByScreenNameConnectorWithViewModel:(id<ForgotPasswordViewModel> _Nonnull)viewModel anonymousUsername:(NSString * _Nonnull)anonymousUsername anonymousPassword:(NSString * _Nonnull)anonymousPassword __attribute__((warn_unused_result));
	[Export ("createForgotPasswordByScreenNameConnectorWithViewModel:anonymousUsername:anonymousPassword:")]
	ForgotPasswordBaseLiferayConnector CreateForgotPasswordByScreenNameConnectorWithViewModel (ForgotPasswordViewModel viewModel, string anonymousUsername, string anonymousPassword);

	// -(ForgotPasswordBaseLiferayConnector * _Nonnull)createForgotPasswordByUserIdConnectorWithViewModel:(id<ForgotPasswordViewModel> _Nonnull)viewModel anonymousUsername:(NSString * _Nonnull)anonymousUsername anonymousPassword:(NSString * _Nonnull)anonymousPassword __attribute__((warn_unused_result));
	[Export ("createForgotPasswordByUserIdConnectorWithViewModel:anonymousUsername:anonymousPassword:")]
	ForgotPasswordBaseLiferayConnector CreateForgotPasswordByUserIdConnectorWithViewModel (ForgotPasswordViewModel viewModel, string anonymousUsername, string anonymousPassword);

	// -(SignUpLiferayConnector * _Nonnull)createSignUpConnectorWithViewModel:(id<SignUpViewModel> _Nonnull)viewModel anonymousUsername:(NSString * _Nonnull)anonymousUsername anonymousPassword:(NSString * _Nonnull)anonymousPassword __attribute__((warn_unused_result));
	[Export ("createSignUpConnectorWithViewModel:anonymousUsername:anonymousPassword:")]
	SignUpLiferayConnector CreateSignUpConnectorWithViewModel (SignUpViewModel viewModel, string anonymousUsername, string anonymousPassword);

	// -(UpdateCurrentUserLiferayConnector * _Nonnull)createUpdateCurrentUserConnectorWithViewModel:(id<SignUpViewModel> _Nonnull)viewModel __attribute__((warn_unused_result));
	[Export ("createUpdateCurrentUserConnectorWithViewModel:")]
	UpdateCurrentUserLiferayConnector CreateUpdateCurrentUserConnectorWithViewModel (SignUpViewModel viewModel);

	// -(UploadUserPortraitLiferayConnector * _Nonnull)createUploadUserPortraitConnectorWithUserId:(int64_t)userId image:(UIImage * _Nonnull)image __attribute__((warn_unused_result));
	[Export ("createUploadUserPortraitConnectorWithUserId:image:")]
	UploadUserPortraitLiferayConnector CreateUploadUserPortraitConnectorWithUserId (long userId, UIImage image);

	// -(AssetListPageLiferayConnector * _Nonnull)createAssetListPageConnectorWithStartRow:(NSInteger)startRow endRow:(NSInteger)endRow computeRowCount:(BOOL)computeRowCount __attribute__((warn_unused_result));
	[Export ("createAssetListPageConnectorWithStartRow:endRow:computeRowCount:")]
	AssetListPageLiferayConnector CreateAssetListPageConnectorWithStartRow (nint startRow, nint endRow, bool computeRowCount);

	// -(WebContentListPageLiferayConnector * _Nonnull)createWebContentListPageConnectorWithGroupId:(int64_t)groupId folderId:(int64_t)folderId startRow:(NSInteger)startRow endRow:(NSInteger)endRow computeRowCount:(BOOL)computeRowCount __attribute__((warn_unused_result));
	[Export ("createWebContentListPageConnectorWithGroupId:folderId:startRow:endRow:computeRowCount:")]
	WebContentListPageLiferayConnector CreateWebContentListPageConnectorWithGroupId (long groupId, long folderId, nint startRow, nint endRow, bool computeRowCount);

	// -(DDLListPageLiferayConnector * _Nonnull)createDDLListPageConnectorWithViewModel:(id<DDLListViewModel> _Nonnull)viewModel startRow:(NSInteger)startRow endRow:(NSInteger)endRow computeRowCount:(BOOL)computeRowCount __attribute__((warn_unused_result));
	[Export ("createDDLListPageConnectorWithViewModel:startRow:endRow:computeRowCount:")]
	DDLListPageLiferayConnector CreateDDLListPageConnectorWithViewModel (DDLListViewModel viewModel, nint startRow, nint endRow, bool computeRowCount);

	// -(WebContentLoadHtmlLiferayConnector * _Nonnull)createWebContentLoadHtmlConnectorWithGroupId:(int64_t)groupId articleId:(NSString * _Nonnull)articleId __attribute__((warn_unused_result));
	[Export ("createWebContentLoadHtmlConnectorWithGroupId:articleId:")]
	WebContentLoadHtmlLiferayConnector CreateWebContentLoadHtmlConnectorWithGroupId (long groupId, string articleId);

	// -(WebContentLoadStructuredLiferayConnector * _Nonnull)createWebContentLoadStructuredConnectorWithGroupId:(int64_t)groupId articleId:(NSString * _Nonnull)articleId structureId:(int64_t)structureId __attribute__((warn_unused_result));
	[Export ("createWebContentLoadStructuredConnectorWithGroupId:articleId:structureId:")]
	WebContentLoadStructuredLiferayConnector CreateWebContentLoadStructuredConnectorWithGroupId (long groupId, string articleId, long structureId);

	// -(DDLFormLoadLiferayConnector * _Nonnull)createDDLFormLoadConnector:(int64_t)structureId __attribute__((warn_unused_result));
	[Export ("createDDLFormLoadConnector:")]
	DDLFormLoadLiferayConnector CreateDDLFormLoadConnector (long structureId);

	// -(DDLFormRecordLoadLiferayConnector * _Nonnull)createDDLFormRecordLoadConnector:(int64_t)recordId __attribute__((warn_unused_result));
	[Export ("createDDLFormRecordLoadConnector:")]
	DDLFormRecordLoadLiferayConnector CreateDDLFormRecordLoadConnector (long recordId);

	// -(DDLFormSubmitLiferayConnector * _Nonnull)createDDLFormSubmitConnectorWithValues:(NSDictionary<NSString *,id> * _Nonnull)values viewModel:(id<DDLFormViewModel> _Nullable)viewModel __attribute__((warn_unused_result));
	[Export ("createDDLFormSubmitConnectorWithValues:viewModel:")]
	DDLFormSubmitLiferayConnector CreateDDLFormSubmitConnectorWithValues (NSDictionary<NSString, NSObject> values, [NullAllowed] DDLFormViewModel viewModel);

	// -(DDLFormUploadLiferayConnector * _Nonnull)createDDLFormUploadConnectorWithDocument:(DDMFieldDocument * _Nonnull)document filePrefix:(NSString * _Nonnull)filePrefix repositoryId:(int64_t)repositoryId folderId:(int64_t)folderId onProgress:(void (^ _Nullable)(DDMFieldDocument * _Nonnull, uint64_t, uint64_t))onProgress __attribute__((warn_unused_result));
	[Export ("createDDLFormUploadConnectorWithDocument:filePrefix:repositoryId:folderId:onProgress:")]
	DDLFormUploadLiferayConnector CreateDDLFormUploadConnectorWithDocument (DDMFieldDocument document, string filePrefix, long repositoryId, long folderId, [NullAllowed] Action<DDMFieldDocument, ulong, ulong> onProgress);

	// -(AssetLoadByEntryIdLiferayConnector * _Nullable)createAssetLoadByEntryIdConnector:(int64_t)entryId __attribute__((warn_unused_result));
	[Export ("createAssetLoadByEntryIdConnector:")]
	[return: NullAllowed]
	AssetLoadByEntryIdLiferayConnector CreateAssetLoadByEntryIdConnector (long entryId);

	// -(AssetLoadByClassPKLiferayConnector * _Nullable)createAssetLoadByClassPKConnector:(NSString * _Nonnull)className classPK:(int64_t)classPK __attribute__((warn_unused_result));
	[Export ("createAssetLoadByClassPKConnector:classPK:")]
	[return: NullAllowed]
	AssetLoadByClassPKLiferayConnector CreateAssetLoadByClassPKConnector (string className, long classPK);

	// -(AssetLoadByPortletItemNameLiferayConnector * _Nullable)createAssetLoadByPortletItemNameConnectorWithPortletItemName:(NSString * _Nonnull)portletItemName __attribute__((warn_unused_result));
	[Export ("createAssetLoadByPortletItemNameConnectorWithPortletItemName:")]
	[return: NullAllowed]
	AssetLoadByPortletItemNameLiferayConnector CreateAssetLoadByPortletItemNameConnectorWithPortletItemName (string portletItemName);

	// -(RatingLoadByEntryIdLiferayConnector * _Nullable)createRatingLoadByEntryIdConnectorWithEntryId:(int64_t)entryId ratingsGroupCount:(int32_t)ratingsGroupCount __attribute__((warn_unused_result));
	[Export ("createRatingLoadByEntryIdConnectorWithEntryId:ratingsGroupCount:")]
	[return: NullAllowed]
	RatingLoadByEntryIdLiferayConnector CreateRatingLoadByEntryIdConnectorWithEntryId (long entryId, int ratingsGroupCount);

	// -(RatingLoadByClassPKLiferayConnector * _Nullable)createRatingLoadByClassPKConnector:(int64_t)classPK className:(NSString * _Nonnull)className ratingsGroupCount:(int32_t)ratingsGroupCount __attribute__((warn_unused_result));
	[Export ("createRatingLoadByClassPKConnector:className:ratingsGroupCount:")]
	[return: NullAllowed]
	RatingLoadByClassPKLiferayConnector CreateRatingLoadByClassPKConnector (long classPK, string className, int ratingsGroupCount);

	// -(RatingUpdateLiferayConnector * _Nullable)createRatingUpdateConnectorWithClassPK:(int64_t)classPK className:(NSString * _Nonnull)className score:(double)score ratingsGroupCount:(int32_t)ratingsGroupCount __attribute__((warn_unused_result));
	[Export ("createRatingUpdateConnectorWithClassPK:className:score:ratingsGroupCount:")]
	[return: NullAllowed]
	RatingUpdateLiferayConnector CreateRatingUpdateConnectorWithClassPK (long classPK, string className, double score, int ratingsGroupCount);

	// -(RatingDeleteLiferayConnector * _Nullable)createRatingDeleteConnectorWithClassPK:(int64_t)classPK className:(NSString * _Nonnull)className ratingsGroupCount:(int32_t)ratingsGroupCount __attribute__((warn_unused_result));
	[Export ("createRatingDeleteConnectorWithClassPK:className:ratingsGroupCount:")]
	[return: NullAllowed]
	RatingDeleteLiferayConnector CreateRatingDeleteConnectorWithClassPK (long classPK, string className, int ratingsGroupCount);

	// -(ImageGalleryDeleteConnector * _Nullable)createImageGalleryDeleteConnector:(int64_t)imageEntryId __attribute__((warn_unused_result));
	[Export ("createImageGalleryDeleteConnector:")]
	[return: NullAllowed]
	ImageGalleryDeleteConnector CreateImageGalleryDeleteConnector (long imageEntryId);

	// -(ImageGalleryPageLiferayConnector * _Nullable)createImageGalleryPageConnectorWithStartRow:(NSInteger)startRow endRow:(NSInteger)endRow computeRowCount:(BOOL)computeRowCount repositoryId:(int64_t)repositoryId folderId:(int64_t)folderId mimeTypes:(NSArray<NSString *> * _Nonnull)mimeTypes __attribute__((warn_unused_result));
	[Export ("createImageGalleryPageConnectorWithStartRow:endRow:computeRowCount:repositoryId:folderId:mimeTypes:")]
	[return: NullAllowed]
	ImageGalleryPageLiferayConnector CreateImageGalleryPageConnectorWithStartRow (nint startRow, nint endRow, bool computeRowCount, long repositoryId, long folderId, string[] mimeTypes);

	// -(ImageGalleryUploadConnector * _Nullable)createImageGalleryUploadConnectorWithRepositoryId:(int64_t)repositoryId folderId:(int64_t)folderId sourceFileName:(NSString * _Nonnull)sourceFileName mimeType:(NSString * _Nonnull)mimeType title:(NSString * _Nonnull)title descrip:(NSString * _Nonnull)descrip changeLog:(NSString * _Nonnull)changeLog image:(UIImage * _Nonnull)image onUploadBytes:(void (^ _Nullable)(id _Nullable, uint64_t, uint64_t))onUploadBytes __attribute__((warn_unused_result));
	[Export ("createImageGalleryUploadConnectorWithRepositoryId:folderId:sourceFileName:mimeType:title:descrip:changeLog:image:onUploadBytes:")]
	[return: NullAllowed]
	ImageGalleryUploadConnector CreateImageGalleryUploadConnectorWithRepositoryId (long repositoryId, long folderId, string sourceFileName, string mimeType, string title, string descrip, string changeLog, UIImage image, [NullAllowed] Action<NSObject, ulong, ulong> onUploadBytes);

	// -(CommentListPageLiferayConnector * _Nullable)createCommentListPageConnectorWithClassName:(NSString * _Nonnull)className classPK:(int64_t)classPK startRow:(NSInteger)startRow endRow:(NSInteger)endRow computeRowCount:(BOOL)computeRowCount __attribute__((warn_unused_result));
	[Export ("createCommentListPageConnectorWithClassName:classPK:startRow:endRow:computeRowCount:")]
	[return: NullAllowed]
	CommentListPageLiferayConnector CreateCommentListPageConnectorWithClassName (string className, long classPK, nint startRow, nint endRow, bool computeRowCount);

	// -(CommentAddLiferayConnector * _Nullable)createCommentAddConnectorWithClassName:(NSString * _Nonnull)className classPK:(int64_t)classPK body:(NSString * _Nonnull)body __attribute__((warn_unused_result));
	[Export ("createCommentAddConnectorWithClassName:classPK:body:")]
	[return: NullAllowed]
	CommentAddLiferayConnector CreateCommentAddConnectorWithClassName (string className, long classPK, string body);

	// -(CommentLoadLiferayConnector * _Nullable)createCommentLoadConnectorWithCommentId:(int64_t)commentId __attribute__((warn_unused_result));
	[Export ("createCommentLoadConnectorWithCommentId:")]
	[return: NullAllowed]
	CommentLoadLiferayConnector CreateCommentLoadConnectorWithCommentId (long commentId);

	// -(CommentDeleteLiferayConnector * _Nullable)createCommentDeleteConnectorWithCommentId:(int64_t)commentId __attribute__((warn_unused_result));
	[Export ("createCommentDeleteConnectorWithCommentId:")]
	[return: NullAllowed]
	CommentDeleteLiferayConnector CreateCommentDeleteConnectorWithCommentId (long commentId);

	// -(CommentUpdateLiferayConnector * _Nullable)createCommentUpdateConnectorWithCommentId:(int64_t)commentId body:(NSString * _Nonnull)body __attribute__((warn_unused_result));
	[Export ("createCommentUpdateConnectorWithCommentId:body:")]
	[return: NullAllowed]
	CommentUpdateLiferayConnector CreateCommentUpdateConnectorWithCommentId (long commentId, string body);
}

// @interface Liferay62DDLFormLoadConnector : DDLFormLoadLiferayConnector
[BaseType (typeof(DDLFormLoadLiferayConnector))]
interface Liferay62DDLFormLoadConnector
{
	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(instancetype _Nonnull)initWithStructureId:(int64_t)structureId __attribute__((objc_designated_initializer));
	[Export ("initWithStructureId:")]
	[DesignatedInitializer]
	IntPtr Constructor (long structureId);
}

// @interface Liferay62DDLFormRecordLoadConnector : DDLFormRecordLoadLiferayConnector
[BaseType (typeof(DDLFormRecordLoadLiferayConnector))]
interface Liferay62DDLFormRecordLoadConnector
{
	// -(NSDictionary * _Nullable)getRecord:(LRSession * _Nonnull)session recordId:(int64_t)recordId locale:(NSString * _Nonnull)locale error:(NSError * _Nullable * _Nullable)error __attribute__((warn_unused_result));
	[Export ("getRecord:recordId:locale:error:")]
	[return: NullAllowed]
	NSDictionary GetRecord (LRSession session, long recordId, string locale, [NullAllowed] out NSError error);

	// -(instancetype _Nonnull)initWithRecordId:(int64_t)recordId __attribute__((objc_designated_initializer));
	[Export ("initWithRecordId:")]
	[DesignatedInitializer]
	IntPtr Constructor (long recordId);
}

// @interface Liferay62DDLFormSubmitConnector : DDLFormSubmitLiferayConnector
[BaseType (typeof(DDLFormSubmitLiferayConnector))]
interface Liferay62DDLFormSubmitConnector
{
	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(instancetype _Nonnull)initWithValues:(NSDictionary<NSString *,id> * _Nonnull)values viewModel:(id<DDLFormViewModel> _Nullable)viewModel __attribute__((objc_designated_initializer));
	[Export ("initWithValues:viewModel:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSDictionary<NSString, NSObject> values, [NullAllowed] DDLFormViewModel viewModel);
}

// @interface Liferay62DDLFormUploadConnector : DDLFormUploadLiferayConnector
[BaseType (typeof(DDLFormUploadLiferayConnector))]
interface Liferay62DDLFormUploadConnector
{
	// -(BOOL)doSendFile:(LRSession * _Nonnull)session name:(NSString * _Nonnull)name data:(LRUploadData * _Nonnull)data error:(NSError * _Nullable * _Nullable)error;
	[Export ("doSendFile:name:data:error:")]
	bool DoSendFile (LRSession session, string name, LRUploadData data, [NullAllowed] out NSError error);

	// -(instancetype _Nonnull)initWithDocument:(DDMFieldDocument * _Nonnull)document filePrefix:(NSString * _Nonnull)filePrefix repositoryId:(int64_t)repositoryId folderId:(int64_t)folderId onProgress:(void (^ _Nullable)(DDMFieldDocument * _Nonnull, uint64_t, uint64_t))onProgress __attribute__((objc_designated_initializer));
	[Export ("initWithDocument:filePrefix:repositoryId:folderId:onProgress:")]
	[DesignatedInitializer]
	IntPtr Constructor (DDMFieldDocument document, string filePrefix, long repositoryId, long folderId, [NullAllowed] Action<DDMFieldDocument, ulong, ulong> onProgress);
}

// @interface Liferay62DDLListPageConnector : DDLListPageLiferayConnector
[BaseType (typeof(DDLListPageLiferayConnector))]
interface Liferay62DDLListPageConnector
{
	// -(void)doAddPageRowsServiceCallWithSession:(LRBatchSession * _Nonnull)session startRow:(NSInteger)startRow endRow:(NSInteger)endRow obc:(LRJSONObjectWrapper * _Nullable)obc;
	[Export ("doAddPageRowsServiceCallWithSession:startRow:endRow:obc:")]
	void DoAddPageRowsServiceCallWithSession (LRBatchSession session, nint startRow, nint endRow, [NullAllowed] LRJSONObjectWrapper obc);

	// -(void)doAddRowCountServiceCallWithSession:(LRBatchSession * _Nonnull)session;
	[Export ("doAddRowCountServiceCallWithSession:")]
	void DoAddRowCountServiceCallWithSession (LRBatchSession session);

	// -(instancetype _Nonnull)initWithViewModel:(id<DDLListViewModel> _Nonnull)viewModel startRow:(NSInteger)startRow endRow:(NSInteger)endRow computeRowCount:(BOOL)computeRowCount __attribute__((objc_designated_initializer));
	[Export ("initWithViewModel:startRow:endRow:computeRowCount:")]
	[DesignatedInitializer]
	IntPtr Constructor (DDLListViewModel viewModel, nint startRow, nint endRow, bool computeRowCount);
}

// @interface SignUpLiferayConnector : ServerConnector
[BaseType (typeof(ServerConnector))]
[DisableDefaultCtor]
interface SignUpLiferayConnector
{
	// @property (nonatomic) int64_t companyId;
	[Export ("companyId")]
	long CompanyId { get; set; }

	// @property (copy, nonatomic) NSDictionary<NSString *,id> * _Nullable resultUserAttributes;
	[NullAllowed, Export ("resultUserAttributes", ArgumentSemantic.Copy)]
	NSDictionary<NSString, NSObject> ResultUserAttributes { get; set; }

	// @property (readonly, nonatomic, strong) id<SignUpViewModel> _Nonnull viewModel;
	[Export ("viewModel", ArgumentSemantic.Strong)]
	SignUpViewModel ViewModel { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull anonymousUsername;
	[Export ("anonymousUsername")]
	string AnonymousUsername { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull anonymousPassword;
	[Export ("anonymousPassword")]
	string AnonymousPassword { get; }

	// -(instancetype _Nonnull)initWithViewModel:(id<SignUpViewModel> _Nonnull)viewModel anonymousUsername:(NSString * _Nonnull)anonymousUsername anonymousPassword:(NSString * _Nonnull)anonymousPassword __attribute__((objc_designated_initializer));
	[Export ("initWithViewModel:anonymousUsername:anonymousPassword:")]
	[DesignatedInitializer]
	IntPtr Constructor (SignUpViewModel viewModel, string anonymousUsername, string anonymousPassword);

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }

	// -(LRSession * _Nullable)createSession __attribute__((warn_unused_result));
	[NullAllowed, Export ("createSession")]
	[Verify (MethodToProperty)]
	LRSession CreateSession { get; }
}

// @interface Liferay62SignUpConnector : SignUpLiferayConnector
[BaseType (typeof(SignUpLiferayConnector))]
interface Liferay62SignUpConnector
{
	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(instancetype _Nonnull)initWithViewModel:(id<SignUpViewModel> _Nonnull)viewModel anonymousUsername:(NSString * _Nonnull)anonymousUsername anonymousPassword:(NSString * _Nonnull)anonymousPassword __attribute__((objc_designated_initializer));
	[Export ("initWithViewModel:anonymousUsername:anonymousPassword:")]
	[DesignatedInitializer]
	IntPtr Constructor (SignUpViewModel viewModel, string anonymousUsername, string anonymousPassword);
}

// @interface UpdateCurrentUserLiferayConnector : ServerConnector
[BaseType (typeof(ServerConnector))]
[DisableDefaultCtor]
interface UpdateCurrentUserLiferayConnector
{
	// @property (copy, nonatomic) NSDictionary<NSString *,id> * _Nullable resultUserAttributes;
	[NullAllowed, Export ("resultUserAttributes", ArgumentSemantic.Copy)]
	NSDictionary<NSString, NSObject> ResultUserAttributes { get; set; }

	// -(instancetype _Nonnull)initWithViewModel:(id<SignUpViewModel> _Nonnull)viewModel __attribute__((objc_designated_initializer));
	[Export ("initWithViewModel:")]
	[DesignatedInitializer]
	IntPtr Constructor (SignUpViewModel viewModel);

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }

	// -(NSString * _Nonnull)attributeAsString:(NSString * _Nonnull)key __attribute__((warn_unused_result));
	[Export ("attributeAsString:")]
	string AttributeAsString (string key);

	// -(int64_t)attributeAsId:(NSString * _Nonnull)key __attribute__((warn_unused_result));
	[Export ("attributeAsId:")]
	long AttributeAsId (string key);
}

// @interface Liferay62UpdateCurrentUserConnector : UpdateCurrentUserLiferayConnector
[BaseType (typeof(UpdateCurrentUserLiferayConnector))]
interface Liferay62UpdateCurrentUserConnector
{
	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(instancetype _Nonnull)initWithViewModel:(id<SignUpViewModel> _Nonnull)viewModel __attribute__((objc_designated_initializer));
	[Export ("initWithViewModel:")]
	[DesignatedInitializer]
	IntPtr Constructor (SignUpViewModel viewModel);
}

// @interface UploadUserPortraitLiferayConnector : ServerConnector
[BaseType (typeof(ServerConnector))]
[DisableDefaultCtor]
interface UploadUserPortraitLiferayConnector
{
	// -(instancetype _Nonnull)initWithUserId:(int64_t)userId image:(UIImage * _Nonnull)image __attribute__((objc_designated_initializer));
	[Export ("initWithUserId:image:")]
	[DesignatedInitializer]
	IntPtr Constructor (long userId, UIImage image);

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }

	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(void)uploadBytes:(NSData * _Nonnull)imageBytes withSession:(LRSession * _Nonnull)session;
	[Export ("uploadBytes:withSession:")]
	void UploadBytes (NSData imageBytes, LRSession session);
}

// @interface Liferay62UploadUserPortraitConnector : UploadUserPortraitLiferayConnector
[BaseType (typeof(UploadUserPortraitLiferayConnector))]
interface Liferay62UploadUserPortraitConnector
{
	// -(void)uploadBytes:(NSData * _Nonnull)imageBytes withSession:(LRSession * _Nonnull)session;
	[Export ("uploadBytes:withSession:")]
	void UploadBytes (NSData imageBytes, LRSession session);

	// -(instancetype _Nonnull)initWithUserId:(int64_t)userId image:(UIImage * _Nonnull)image __attribute__((objc_designated_initializer));
	[Export ("initWithUserId:image:")]
	[DesignatedInitializer]
	IntPtr Constructor (long userId, UIImage image);
}

// @interface WebContentListPageLiferayConnector : PaginationLiferayConnector
[BaseType (typeof(PaginationLiferayConnector))]
interface WebContentListPageLiferayConnector
{
	// @property (readonly, nonatomic) int64_t groupId;
	[Export ("groupId")]
	long GroupId { get; }

	// @property (readonly, nonatomic) int64_t folderId;
	[Export ("folderId")]
	long FolderId { get; }
}

// @interface Liferay62WebContentListPageConnector : WebContentListPageLiferayConnector
[BaseType (typeof(WebContentListPageLiferayConnector))]
interface Liferay62WebContentListPageConnector
{
	// -(void)doAddPageRowsServiceCallWithSession:(LRBatchSession * _Nonnull)session startRow:(NSInteger)startRow endRow:(NSInteger)endRow obc:(LRJSONObjectWrapper * _Nullable)obc;
	[Export ("doAddPageRowsServiceCallWithSession:startRow:endRow:obc:")]
	void DoAddPageRowsServiceCallWithSession (LRBatchSession session, nint startRow, nint endRow, [NullAllowed] LRJSONObjectWrapper obc);

	// -(void)doAddRowCountServiceCallWithSession:(LRBatchSession * _Nonnull)session;
	[Export ("doAddRowCountServiceCallWithSession:")]
	void DoAddRowCountServiceCallWithSession (LRBatchSession session);
}

// @interface WebContentLoadBaseLiferayConnector : ServerConnector
[BaseType (typeof(ServerConnector))]
[DisableDefaultCtor]
interface WebContentLoadBaseLiferayConnector
{
	// @property (readonly, nonatomic) int64_t groupId;
	[Export ("groupId")]
	long GroupId { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull articleId;
	[Export ("articleId")]
	string ArticleId { get; }

	// -(instancetype _Nonnull)initWithGroupId:(int64_t)groupId articleId:(NSString * _Nonnull)articleId __attribute__((objc_designated_initializer));
	[Export ("initWithGroupId:articleId:")]
	[DesignatedInitializer]
	IntPtr Constructor (long groupId, string articleId);

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }
}

// @interface WebContentLoadHtmlLiferayConnector : WebContentLoadBaseLiferayConnector
[BaseType (typeof(WebContentLoadBaseLiferayConnector))]
interface WebContentLoadHtmlLiferayConnector
{
	// @property (copy, nonatomic) NSString * _Nullable resultHTML;
	[NullAllowed, Export ("resultHTML")]
	string ResultHTML { get; set; }

	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }

	// -(instancetype _Nonnull)initWithGroupId:(int64_t)groupId articleId:(NSString * _Nonnull)articleId __attribute__((objc_designated_initializer));
	[Export ("initWithGroupId:articleId:")]
	[DesignatedInitializer]
	IntPtr Constructor (long groupId, string articleId);
}

// @interface Liferay62WebContentLoadHtmlConnector : WebContentLoadHtmlLiferayConnector
[BaseType (typeof(WebContentLoadHtmlLiferayConnector))]
interface Liferay62WebContentLoadHtmlConnector
{
	// -(instancetype _Nonnull)initWithGroupId:(int64_t)groupId articleId:(NSString * _Nonnull)articleId __attribute__((objc_designated_initializer));
	[Export ("initWithGroupId:articleId:")]
	[DesignatedInitializer]
	IntPtr Constructor (long groupId, string articleId);
}

// @interface WebContentLoadStructuredLiferayConnector : WebContentLoadBaseLiferayConnector
[BaseType (typeof(WebContentLoadBaseLiferayConnector))]
interface WebContentLoadStructuredLiferayConnector
{
	// @property (readonly, nonatomic) int64_t structureId;
	[Export ("structureId")]
	long StructureId { get; }

	// @property (nonatomic, strong) DDLRecord * _Nullable resultRecord;
	[NullAllowed, Export ("resultRecord", ArgumentSemantic.Strong)]
	DDLRecord ResultRecord { get; set; }

	// -(instancetype _Nonnull)initWithGroupId:(int64_t)groupId articleId:(NSString * _Nonnull)articleId structureId:(int64_t)structureId __attribute__((objc_designated_initializer));
	[Export ("initWithGroupId:articleId:structureId:")]
	[DesignatedInitializer]
	IntPtr Constructor (long groupId, string articleId, long structureId);

	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);
}

// @interface Liferay62WebContentLoadStructuredConnector : WebContentLoadStructuredLiferayConnector
[BaseType (typeof(WebContentLoadStructuredLiferayConnector))]
interface Liferay62WebContentLoadStructuredConnector
{
	// -(instancetype _Nonnull)initWithGroupId:(int64_t)groupId articleId:(NSString * _Nonnull)articleId structureId:(int64_t)structureId __attribute__((objc_designated_initializer));
	[Export ("initWithGroupId:articleId:structureId:")]
	[DesignatedInitializer]
	IntPtr Constructor (long groupId, string articleId, long structureId);
}

// @interface Liferay70AssetListPageConnector : AssetListPageLiferayConnector
[BaseType (typeof(AssetListPageLiferayConnector))]
interface Liferay70AssetListPageConnector
{
	// -(void)doGetPageRowsWithSession:(LRBatchSession * _Nonnull)session entryQuery:(LRJSONObjectWrapper * _Nonnull)entryQuery obc:(LRJSONObjectWrapper * _Nullable)obc;
	[Export ("doGetPageRowsWithSession:entryQuery:obc:")]
	void DoGetPageRowsWithSession (LRBatchSession session, LRJSONObjectWrapper entryQuery, [NullAllowed] LRJSONObjectWrapper obc);

	// -(void)doGetRowCountWithSession:(LRBatchSession * _Nonnull)session entryQuery:(LRJSONObjectWrapper * _Nonnull)entryQuery;
	[Export ("doGetRowCountWithSession:entryQuery:")]
	void DoGetRowCountWithSession (LRBatchSession session, LRJSONObjectWrapper entryQuery);

	// -(instancetype _Nonnull)initWithStartRow:(NSInteger)startRow endRow:(NSInteger)endRow computeRowCount:(BOOL)computeRowCount __attribute__((objc_designated_initializer));
	[Export ("initWithStartRow:endRow:computeRowCount:")]
	[DesignatedInitializer]
	IntPtr Constructor (nint startRow, nint endRow, bool computeRowCount);
}

// @interface Liferay70AssetLoadByClassPKConnector : AssetLoadByClassPKLiferayConnector
[BaseType (typeof(AssetLoadByClassPKLiferayConnector))]
interface Liferay70AssetLoadByClassPKConnector
{
	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(instancetype _Nonnull)initWithClassName:(NSString * _Nonnull)className classPK:(int64_t)classPK __attribute__((objc_designated_initializer));
	[Export ("initWithClassName:classPK:")]
	[DesignatedInitializer]
	IntPtr Constructor (string className, long classPK);
}

// @interface Liferay70AssetLoadByEntryIdConnector : AssetLoadByEntryIdLiferayConnector
[BaseType (typeof(AssetLoadByEntryIdLiferayConnector))]
interface Liferay70AssetLoadByEntryIdConnector
{
	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(instancetype _Nonnull)initWithEntryId:(int64_t)entryId __attribute__((objc_designated_initializer));
	[Export ("initWithEntryId:")]
	[DesignatedInitializer]
	IntPtr Constructor (long entryId);
}

// @interface Liferay70AssetLoadByPortletItemNameConnector : AssetLoadByPortletItemNameLiferayConnector
[BaseType (typeof(AssetLoadByPortletItemNameLiferayConnector))]
interface Liferay70AssetLoadByPortletItemNameConnector
{
	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(instancetype _Nonnull)initWithPortletItemName:(NSString * _Nonnull)portletItemName __attribute__((objc_designated_initializer));
	[Export ("initWithPortletItemName:")]
	[DesignatedInitializer]
	IntPtr Constructor (string portletItemName);
}

// @interface Liferay70CommentAddConnector : CommentAddLiferayConnector
[BaseType (typeof(CommentAddLiferayConnector))]
interface Liferay70CommentAddConnector
{
	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(instancetype _Nonnull)initWithClassName:(NSString * _Nonnull)className classPK:(int64_t)classPK body:(NSString * _Nonnull)body __attribute__((objc_designated_initializer));
	[Export ("initWithClassName:classPK:body:")]
	[DesignatedInitializer]
	IntPtr Constructor (string className, long classPK, string body);
}

// @interface Liferay70CommentDeleteConnector : CommentDeleteLiferayConnector
[BaseType (typeof(CommentDeleteLiferayConnector))]
interface Liferay70CommentDeleteConnector
{
	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(instancetype _Nonnull)initWithCommentId:(int64_t)commentId __attribute__((objc_designated_initializer));
	[Export ("initWithCommentId:")]
	[DesignatedInitializer]
	IntPtr Constructor (long commentId);
}

// @interface Liferay70CommentListPageConnector : CommentListPageLiferayConnector
[BaseType (typeof(CommentListPageLiferayConnector))]
interface Liferay70CommentListPageConnector
{
	// -(void)doAddPageRowsServiceCallWithSession:(LRBatchSession * _Nonnull)session startRow:(NSInteger)startRow endRow:(NSInteger)endRow obc:(LRJSONObjectWrapper * _Nullable)obc;
	[Export ("doAddPageRowsServiceCallWithSession:startRow:endRow:obc:")]
	void DoAddPageRowsServiceCallWithSession (LRBatchSession session, nint startRow, nint endRow, [NullAllowed] LRJSONObjectWrapper obc);

	// -(void)doAddRowCountServiceCallWithSession:(LRBatchSession * _Nonnull)session;
	[Export ("doAddRowCountServiceCallWithSession:")]
	void DoAddRowCountServiceCallWithSession (LRBatchSession session);

	// -(instancetype _Nonnull)initWithClassName:(NSString * _Nonnull)className classPK:(int64_t)classPK startRow:(NSInteger)startRow endRow:(NSInteger)endRow computeRowCount:(BOOL)computeRowCount __attribute__((objc_designated_initializer));
	[Export ("initWithClassName:classPK:startRow:endRow:computeRowCount:")]
	[DesignatedInitializer]
	IntPtr Constructor (string className, long classPK, nint startRow, nint endRow, bool computeRowCount);
}

// @interface Liferay70CommentLoadConnector : CommentLoadLiferayConnector
[BaseType (typeof(CommentLoadLiferayConnector))]
interface Liferay70CommentLoadConnector
{
	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(instancetype _Nonnull)initWithCommentId:(int64_t)commentId __attribute__((objc_designated_initializer));
	[Export ("initWithCommentId:")]
	[DesignatedInitializer]
	IntPtr Constructor (long commentId);
}

// @interface Liferay70CommentUpdateConnector : CommentUpdateLiferayConnector
[BaseType (typeof(CommentUpdateLiferayConnector))]
interface Liferay70CommentUpdateConnector
{
	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(instancetype _Nonnull)initWithCommentId:(int64_t)commentId body:(NSString * _Nonnull)body __attribute__((objc_designated_initializer));
	[Export ("initWithCommentId:body:")]
	[DesignatedInitializer]
	IntPtr Constructor (long commentId, string body);
}

// @interface Liferay70ConnectorFactory : NSObject <LiferayConnectorFactory>
[BaseType (typeof(NSObject))]
interface Liferay70ConnectorFactory : ILiferayConnectorFactory
{
	// -(GetUserByEmailLiferayConnector * _Nonnull)createGetUserByEmailConnectorWithCompanyId:(int64_t)companyId emailAddress:(NSString * _Nonnull)emailAddress __attribute__((warn_unused_result));
	[Export ("createGetUserByEmailConnectorWithCompanyId:emailAddress:")]
	GetUserByEmailLiferayConnector CreateGetUserByEmailConnectorWithCompanyId (long companyId, string emailAddress);

	// -(GetUserByScreenNameLiferayConnector * _Nonnull)createGetUserByScreenNameConnectorWithCompanyId:(int64_t)companyId screenName:(NSString * _Nonnull)screenName __attribute__((warn_unused_result));
	[Export ("createGetUserByScreenNameConnectorWithCompanyId:screenName:")]
	GetUserByScreenNameLiferayConnector CreateGetUserByScreenNameConnectorWithCompanyId (long companyId, string screenName);

	// -(GetUserByUserIdLiferayConnector * _Nonnull)createGetUserByUserIdConnectorWithUserId:(int64_t)userId __attribute__((warn_unused_result));
	[Export ("createGetUserByUserIdConnectorWithUserId:")]
	GetUserByUserIdLiferayConnector CreateGetUserByUserIdConnectorWithUserId (long userId);

	// -(GetUserByEmailLiferayConnector * _Nonnull)createLoginByEmailConnectorWithCompanyId:(int64_t)companyId emailAddress:(NSString * _Nonnull)emailAddress password:(NSString * _Nonnull)password __attribute__((warn_unused_result));
	[Export ("createLoginByEmailConnectorWithCompanyId:emailAddress:password:")]
	GetUserByEmailLiferayConnector CreateLoginByEmailConnectorWithCompanyId (long companyId, string emailAddress, string password);

	// -(GetUserByScreenNameLiferayConnector * _Nonnull)createLoginByScreenNameConnectorWithCompanyId:(int64_t)companyId screenName:(NSString * _Nonnull)screenName password:(NSString * _Nonnull)password __attribute__((warn_unused_result));
	[Export ("createLoginByScreenNameConnectorWithCompanyId:screenName:password:")]
	GetUserByScreenNameLiferayConnector CreateLoginByScreenNameConnectorWithCompanyId (long companyId, string screenName, string password);

	// -(GetUserByUserIdLiferayConnector * _Nonnull)createLoginByUserIdConnectorWithUserId:(int64_t)userId password:(NSString * _Nonnull)password __attribute__((warn_unused_result));
	[Export ("createLoginByUserIdConnectorWithUserId:password:")]
	GetUserByUserIdLiferayConnector CreateLoginByUserIdConnectorWithUserId (long userId, string password);

	// -(ForgotPasswordBaseLiferayConnector * _Nonnull)createForgotPasswordByEmailConnectorWithViewModel:(id<ForgotPasswordViewModel> _Nonnull)viewModel anonymousUsername:(NSString * _Nonnull)anonymousUsername anonymousPassword:(NSString * _Nonnull)anonymousPassword __attribute__((warn_unused_result));
	[Export ("createForgotPasswordByEmailConnectorWithViewModel:anonymousUsername:anonymousPassword:")]
	ForgotPasswordBaseLiferayConnector CreateForgotPasswordByEmailConnectorWithViewModel (ForgotPasswordViewModel viewModel, string anonymousUsername, string anonymousPassword);

	// -(ForgotPasswordBaseLiferayConnector * _Nonnull)createForgotPasswordByScreenNameConnectorWithViewModel:(id<ForgotPasswordViewModel> _Nonnull)viewModel anonymousUsername:(NSString * _Nonnull)anonymousUsername anonymousPassword:(NSString * _Nonnull)anonymousPassword __attribute__((warn_unused_result));
	[Export ("createForgotPasswordByScreenNameConnectorWithViewModel:anonymousUsername:anonymousPassword:")]
	ForgotPasswordBaseLiferayConnector CreateForgotPasswordByScreenNameConnectorWithViewModel (ForgotPasswordViewModel viewModel, string anonymousUsername, string anonymousPassword);

	// -(ForgotPasswordBaseLiferayConnector * _Nonnull)createForgotPasswordByUserIdConnectorWithViewModel:(id<ForgotPasswordViewModel> _Nonnull)viewModel anonymousUsername:(NSString * _Nonnull)anonymousUsername anonymousPassword:(NSString * _Nonnull)anonymousPassword __attribute__((warn_unused_result));
	[Export ("createForgotPasswordByUserIdConnectorWithViewModel:anonymousUsername:anonymousPassword:")]
	ForgotPasswordBaseLiferayConnector CreateForgotPasswordByUserIdConnectorWithViewModel (ForgotPasswordViewModel viewModel, string anonymousUsername, string anonymousPassword);

	// -(SignUpLiferayConnector * _Nonnull)createSignUpConnectorWithViewModel:(id<SignUpViewModel> _Nonnull)viewModel anonymousUsername:(NSString * _Nonnull)anonymousUsername anonymousPassword:(NSString * _Nonnull)anonymousPassword __attribute__((warn_unused_result));
	[Export ("createSignUpConnectorWithViewModel:anonymousUsername:anonymousPassword:")]
	SignUpLiferayConnector CreateSignUpConnectorWithViewModel (SignUpViewModel viewModel, string anonymousUsername, string anonymousPassword);

	// -(UpdateCurrentUserLiferayConnector * _Nonnull)createUpdateCurrentUserConnectorWithViewModel:(id<SignUpViewModel> _Nonnull)viewModel __attribute__((warn_unused_result));
	[Export ("createUpdateCurrentUserConnectorWithViewModel:")]
	UpdateCurrentUserLiferayConnector CreateUpdateCurrentUserConnectorWithViewModel (SignUpViewModel viewModel);

	// -(UploadUserPortraitLiferayConnector * _Nonnull)createUploadUserPortraitConnectorWithUserId:(int64_t)userId image:(UIImage * _Nonnull)image __attribute__((warn_unused_result));
	[Export ("createUploadUserPortraitConnectorWithUserId:image:")]
	UploadUserPortraitLiferayConnector CreateUploadUserPortraitConnectorWithUserId (long userId, UIImage image);

	// -(AssetListPageLiferayConnector * _Nonnull)createAssetListPageConnectorWithStartRow:(NSInteger)startRow endRow:(NSInteger)endRow computeRowCount:(BOOL)computeRowCount __attribute__((warn_unused_result));
	[Export ("createAssetListPageConnectorWithStartRow:endRow:computeRowCount:")]
	AssetListPageLiferayConnector CreateAssetListPageConnectorWithStartRow (nint startRow, nint endRow, bool computeRowCount);

	// -(WebContentListPageLiferayConnector * _Nonnull)createWebContentListPageConnectorWithGroupId:(int64_t)groupId folderId:(int64_t)folderId startRow:(NSInteger)startRow endRow:(NSInteger)endRow computeRowCount:(BOOL)computeRowCount __attribute__((warn_unused_result));
	[Export ("createWebContentListPageConnectorWithGroupId:folderId:startRow:endRow:computeRowCount:")]
	WebContentListPageLiferayConnector CreateWebContentListPageConnectorWithGroupId (long groupId, long folderId, nint startRow, nint endRow, bool computeRowCount);

	// -(DDLListPageLiferayConnector * _Nonnull)createDDLListPageConnectorWithViewModel:(id<DDLListViewModel> _Nonnull)viewModel startRow:(NSInteger)startRow endRow:(NSInteger)endRow computeRowCount:(BOOL)computeRowCount __attribute__((warn_unused_result));
	[Export ("createDDLListPageConnectorWithViewModel:startRow:endRow:computeRowCount:")]
	DDLListPageLiferayConnector CreateDDLListPageConnectorWithViewModel (DDLListViewModel viewModel, nint startRow, nint endRow, bool computeRowCount);

	// -(WebContentLoadHtmlLiferayConnector * _Nonnull)createWebContentLoadHtmlConnectorWithGroupId:(int64_t)groupId articleId:(NSString * _Nonnull)articleId __attribute__((warn_unused_result));
	[Export ("createWebContentLoadHtmlConnectorWithGroupId:articleId:")]
	WebContentLoadHtmlLiferayConnector CreateWebContentLoadHtmlConnectorWithGroupId (long groupId, string articleId);

	// -(WebContentLoadStructuredLiferayConnector * _Nonnull)createWebContentLoadStructuredConnectorWithGroupId:(int64_t)groupId articleId:(NSString * _Nonnull)articleId structureId:(int64_t)structureId __attribute__((warn_unused_result));
	[Export ("createWebContentLoadStructuredConnectorWithGroupId:articleId:structureId:")]
	WebContentLoadStructuredLiferayConnector CreateWebContentLoadStructuredConnectorWithGroupId (long groupId, string articleId, long structureId);

	// -(DDLFormLoadLiferayConnector * _Nonnull)createDDLFormLoadConnector:(int64_t)structureId __attribute__((warn_unused_result));
	[Export ("createDDLFormLoadConnector:")]
	DDLFormLoadLiferayConnector CreateDDLFormLoadConnector (long structureId);

	// -(DDLFormRecordLoadLiferayConnector * _Nonnull)createDDLFormRecordLoadConnector:(int64_t)recordId __attribute__((warn_unused_result));
	[Export ("createDDLFormRecordLoadConnector:")]
	DDLFormRecordLoadLiferayConnector CreateDDLFormRecordLoadConnector (long recordId);

	// -(DDLFormSubmitLiferayConnector * _Nonnull)createDDLFormSubmitConnectorWithValues:(NSDictionary<NSString *,id> * _Nonnull)values viewModel:(id<DDLFormViewModel> _Nullable)viewModel __attribute__((warn_unused_result));
	[Export ("createDDLFormSubmitConnectorWithValues:viewModel:")]
	DDLFormSubmitLiferayConnector CreateDDLFormSubmitConnectorWithValues (NSDictionary<NSString, NSObject> values, [NullAllowed] DDLFormViewModel viewModel);

	// -(DDLFormUploadLiferayConnector * _Nonnull)createDDLFormUploadConnectorWithDocument:(DDMFieldDocument * _Nonnull)document filePrefix:(NSString * _Nonnull)filePrefix repositoryId:(int64_t)repositoryId folderId:(int64_t)folderId onProgress:(void (^ _Nullable)(DDMFieldDocument * _Nonnull, uint64_t, uint64_t))onProgress __attribute__((warn_unused_result));
	[Export ("createDDLFormUploadConnectorWithDocument:filePrefix:repositoryId:folderId:onProgress:")]
	DDLFormUploadLiferayConnector CreateDDLFormUploadConnectorWithDocument (DDMFieldDocument document, string filePrefix, long repositoryId, long folderId, [NullAllowed] Action<DDMFieldDocument, ulong, ulong> onProgress);

	// -(RatingLoadByEntryIdLiferayConnector * _Nullable)createRatingLoadByEntryIdConnectorWithEntryId:(int64_t)entryId ratingsGroupCount:(int32_t)ratingsGroupCount __attribute__((warn_unused_result));
	[Export ("createRatingLoadByEntryIdConnectorWithEntryId:ratingsGroupCount:")]
	[return: NullAllowed]
	RatingLoadByEntryIdLiferayConnector CreateRatingLoadByEntryIdConnectorWithEntryId (long entryId, int ratingsGroupCount);

	// -(RatingLoadByClassPKLiferayConnector * _Nullable)createRatingLoadByClassPKConnector:(int64_t)classPK className:(NSString * _Nonnull)className ratingsGroupCount:(int32_t)ratingsGroupCount __attribute__((warn_unused_result));
	[Export ("createRatingLoadByClassPKConnector:className:ratingsGroupCount:")]
	[return: NullAllowed]
	RatingLoadByClassPKLiferayConnector CreateRatingLoadByClassPKConnector (long classPK, string className, int ratingsGroupCount);

	// -(RatingUpdateLiferayConnector * _Nullable)createRatingUpdateConnectorWithClassPK:(int64_t)classPK className:(NSString * _Nonnull)className score:(double)score ratingsGroupCount:(int32_t)ratingsGroupCount __attribute__((warn_unused_result));
	[Export ("createRatingUpdateConnectorWithClassPK:className:score:ratingsGroupCount:")]
	[return: NullAllowed]
	RatingUpdateLiferayConnector CreateRatingUpdateConnectorWithClassPK (long classPK, string className, double score, int ratingsGroupCount);

	// -(RatingDeleteLiferayConnector * _Nullable)createRatingDeleteConnectorWithClassPK:(int64_t)classPK className:(NSString * _Nonnull)className ratingsGroupCount:(int32_t)ratingsGroupCount __attribute__((warn_unused_result));
	[Export ("createRatingDeleteConnectorWithClassPK:className:ratingsGroupCount:")]
	[return: NullAllowed]
	RatingDeleteLiferayConnector CreateRatingDeleteConnectorWithClassPK (long classPK, string className, int ratingsGroupCount);

	// -(ImageGalleryDeleteConnector * _Nullable)createImageGalleryDeleteConnector:(int64_t)imageEntryId __attribute__((warn_unused_result));
	[Export ("createImageGalleryDeleteConnector:")]
	[return: NullAllowed]
	ImageGalleryDeleteConnector CreateImageGalleryDeleteConnector (long imageEntryId);

	// -(ImageGalleryPageLiferayConnector * _Nonnull)createImageGalleryPageConnectorWithStartRow:(NSInteger)startRow endRow:(NSInteger)endRow computeRowCount:(BOOL)computeRowCount repositoryId:(int64_t)repositoryId folderId:(int64_t)folderId mimeTypes:(NSArray<NSString *> * _Nonnull)mimeTypes __attribute__((warn_unused_result));
	[Export ("createImageGalleryPageConnectorWithStartRow:endRow:computeRowCount:repositoryId:folderId:mimeTypes:")]
	ImageGalleryPageLiferayConnector CreateImageGalleryPageConnectorWithStartRow (nint startRow, nint endRow, bool computeRowCount, long repositoryId, long folderId, string[] mimeTypes);

	// -(ImageGalleryUploadConnector * _Nonnull)createImageGalleryUploadConnectorWithRepositoryId:(int64_t)repositoryId folderId:(int64_t)folderId sourceFileName:(NSString * _Nonnull)sourceFileName mimeType:(NSString * _Nonnull)mimeType title:(NSString * _Nonnull)title descrip:(NSString * _Nonnull)descrip changeLog:(NSString * _Nonnull)changeLog image:(UIImage * _Nonnull)image onUploadBytes:(void (^ _Nullable)(id _Nullable, uint64_t, uint64_t))onUploadBytes __attribute__((warn_unused_result));
	[Export ("createImageGalleryUploadConnectorWithRepositoryId:folderId:sourceFileName:mimeType:title:descrip:changeLog:image:onUploadBytes:")]
	ImageGalleryUploadConnector CreateImageGalleryUploadConnectorWithRepositoryId (long repositoryId, long folderId, string sourceFileName, string mimeType, string title, string descrip, string changeLog, UIImage image, [NullAllowed] Action<NSObject, ulong, ulong> onUploadBytes);

	// -(CommentListPageLiferayConnector * _Nullable)createCommentListPageConnectorWithClassName:(NSString * _Nonnull)className classPK:(int64_t)classPK startRow:(NSInteger)startRow endRow:(NSInteger)endRow computeRowCount:(BOOL)computeRowCount __attribute__((warn_unused_result));
	[Export ("createCommentListPageConnectorWithClassName:classPK:startRow:endRow:computeRowCount:")]
	[return: NullAllowed]
	CommentListPageLiferayConnector CreateCommentListPageConnectorWithClassName (string className, long classPK, nint startRow, nint endRow, bool computeRowCount);

	// -(CommentAddLiferayConnector * _Nullable)createCommentAddConnectorWithClassName:(NSString * _Nonnull)className classPK:(int64_t)classPK body:(NSString * _Nonnull)body __attribute__((warn_unused_result));
	[Export ("createCommentAddConnectorWithClassName:classPK:body:")]
	[return: NullAllowed]
	CommentAddLiferayConnector CreateCommentAddConnectorWithClassName (string className, long classPK, string body);

	// -(CommentLoadLiferayConnector * _Nullable)createCommentLoadConnectorWithCommentId:(int64_t)commentId __attribute__((warn_unused_result));
	[Export ("createCommentLoadConnectorWithCommentId:")]
	[return: NullAllowed]
	CommentLoadLiferayConnector CreateCommentLoadConnectorWithCommentId (long commentId);

	// -(CommentDeleteLiferayConnector * _Nullable)createCommentDeleteConnectorWithCommentId:(int64_t)commentId __attribute__((warn_unused_result));
	[Export ("createCommentDeleteConnectorWithCommentId:")]
	[return: NullAllowed]
	CommentDeleteLiferayConnector CreateCommentDeleteConnectorWithCommentId (long commentId);

	// -(CommentUpdateLiferayConnector * _Nullable)createCommentUpdateConnectorWithCommentId:(int64_t)commentId body:(NSString * _Nonnull)body __attribute__((warn_unused_result));
	[Export ("createCommentUpdateConnectorWithCommentId:body:")]
	[return: NullAllowed]
	CommentUpdateLiferayConnector CreateCommentUpdateConnectorWithCommentId (long commentId, string body);

	// -(AssetLoadByEntryIdLiferayConnector * _Nullable)createAssetLoadByEntryIdConnector:(int64_t)entryId __attribute__((warn_unused_result));
	[Export ("createAssetLoadByEntryIdConnector:")]
	[return: NullAllowed]
	AssetLoadByEntryIdLiferayConnector CreateAssetLoadByEntryIdConnector (long entryId);

	// -(AssetLoadByClassPKLiferayConnector * _Nullable)createAssetLoadByClassPKConnector:(NSString * _Nonnull)className classPK:(int64_t)classPK __attribute__((warn_unused_result));
	[Export ("createAssetLoadByClassPKConnector:classPK:")]
	[return: NullAllowed]
	AssetLoadByClassPKLiferayConnector CreateAssetLoadByClassPKConnector (string className, long classPK);

	// -(AssetLoadByPortletItemNameLiferayConnector * _Nullable)createAssetLoadByPortletItemNameConnectorWithPortletItemName:(NSString * _Nonnull)portletItemName __attribute__((warn_unused_result));
	[Export ("createAssetLoadByPortletItemNameConnectorWithPortletItemName:")]
	[return: NullAllowed]
	AssetLoadByPortletItemNameLiferayConnector CreateAssetLoadByPortletItemNameConnectorWithPortletItemName (string portletItemName);
}

// @interface Liferay70DDLFormLoadConnector : DDLFormLoadLiferayConnector
[BaseType (typeof(DDLFormLoadLiferayConnector))]
interface Liferay70DDLFormLoadConnector
{
	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(instancetype _Nonnull)initWithStructureId:(int64_t)structureId __attribute__((objc_designated_initializer));
	[Export ("initWithStructureId:")]
	[DesignatedInitializer]
	IntPtr Constructor (long structureId);
}

// @interface Liferay70DDLFormRecordLoadConnector : DDLFormRecordLoadLiferayConnector
[BaseType (typeof(DDLFormRecordLoadLiferayConnector))]
interface Liferay70DDLFormRecordLoadConnector
{
	// -(NSDictionary * _Nullable)getRecord:(LRSession * _Nonnull)session recordId:(int64_t)recordId locale:(NSString * _Nonnull)locale error:(NSError * _Nullable * _Nullable)error __attribute__((warn_unused_result));
	[Export ("getRecord:recordId:locale:error:")]
	[return: NullAllowed]
	NSDictionary GetRecord (LRSession session, long recordId, string locale, [NullAllowed] out NSError error);

	// -(instancetype _Nonnull)initWithRecordId:(int64_t)recordId __attribute__((objc_designated_initializer));
	[Export ("initWithRecordId:")]
	[DesignatedInitializer]
	IntPtr Constructor (long recordId);
}

// @interface Liferay70DDLFormSubmitConnector : DDLFormSubmitLiferayConnector
[BaseType (typeof(DDLFormSubmitLiferayConnector))]
interface Liferay70DDLFormSubmitConnector
{
	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(instancetype _Nonnull)initWithValues:(NSDictionary<NSString *,id> * _Nonnull)values viewModel:(id<DDLFormViewModel> _Nullable)viewModel __attribute__((objc_designated_initializer));
	[Export ("initWithValues:viewModel:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSDictionary<NSString, NSObject> values, [NullAllowed] DDLFormViewModel viewModel);
}

// @interface Liferay70DDLFormUploadConnector : DDLFormUploadLiferayConnector
[BaseType (typeof(DDLFormUploadLiferayConnector))]
interface Liferay70DDLFormUploadConnector
{
	// -(BOOL)doSendFile:(LRSession * _Nonnull)session name:(NSString * _Nonnull)name data:(LRUploadData * _Nonnull)data error:(NSError * _Nullable * _Nullable)error;
	[Export ("doSendFile:name:data:error:")]
	bool DoSendFile (LRSession session, string name, LRUploadData data, [NullAllowed] out NSError error);

	// -(instancetype _Nonnull)initWithDocument:(DDMFieldDocument * _Nonnull)document filePrefix:(NSString * _Nonnull)filePrefix repositoryId:(int64_t)repositoryId folderId:(int64_t)folderId onProgress:(void (^ _Nullable)(DDMFieldDocument * _Nonnull, uint64_t, uint64_t))onProgress __attribute__((objc_designated_initializer));
	[Export ("initWithDocument:filePrefix:repositoryId:folderId:onProgress:")]
	[DesignatedInitializer]
	IntPtr Constructor (DDMFieldDocument document, string filePrefix, long repositoryId, long folderId, [NullAllowed] Action<DDMFieldDocument, ulong, ulong> onProgress);
}

// @interface Liferay70DDLListPageConnector : DDLListPageLiferayConnector
[BaseType (typeof(DDLListPageLiferayConnector))]
interface Liferay70DDLListPageConnector
{
	// -(void)doAddPageRowsServiceCallWithSession:(LRBatchSession * _Nonnull)session startRow:(NSInteger)startRow endRow:(NSInteger)endRow obc:(LRJSONObjectWrapper * _Nullable)obc;
	[Export ("doAddPageRowsServiceCallWithSession:startRow:endRow:obc:")]
	void DoAddPageRowsServiceCallWithSession (LRBatchSession session, nint startRow, nint endRow, [NullAllowed] LRJSONObjectWrapper obc);

	// -(void)doAddRowCountServiceCallWithSession:(LRBatchSession * _Nonnull)session;
	[Export ("doAddRowCountServiceCallWithSession:")]
	void DoAddRowCountServiceCallWithSession (LRBatchSession session);

	// -(instancetype _Nonnull)initWithViewModel:(id<DDLListViewModel> _Nonnull)viewModel startRow:(NSInteger)startRow endRow:(NSInteger)endRow computeRowCount:(BOOL)computeRowCount __attribute__((objc_designated_initializer));
	[Export ("initWithViewModel:startRow:endRow:computeRowCount:")]
	[DesignatedInitializer]
	IntPtr Constructor (DDLListViewModel viewModel, nint startRow, nint endRow, bool computeRowCount);
}

// @interface Liferay70ImageGalleryDeleteConnector : ImageGalleryDeleteConnector
[BaseType (typeof(ImageGalleryDeleteConnector))]
interface Liferay70ImageGalleryDeleteConnector
{
	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(instancetype _Nonnull)initWithImageEntryId:(int64_t)imageEntryId __attribute__((objc_designated_initializer));
	[Export ("initWithImageEntryId:")]
	[DesignatedInitializer]
	IntPtr Constructor (long imageEntryId);
}

// @interface Liferay70ImageGalleryPageLiferayConnector : ImageGalleryPageLiferayConnector
[BaseType (typeof(ImageGalleryPageLiferayConnector))]
interface Liferay70ImageGalleryPageLiferayConnector
{
	// -(void)doAddPageRowsServiceCallWithSession:(LRBatchSession * _Nonnull)session startRow:(NSInteger)startRow endRow:(NSInteger)endRow obc:(LRJSONObjectWrapper * _Nullable)obc;
	[Export ("doAddPageRowsServiceCallWithSession:startRow:endRow:obc:")]
	void DoAddPageRowsServiceCallWithSession (LRBatchSession session, nint startRow, nint endRow, [NullAllowed] LRJSONObjectWrapper obc);

	// -(void)doAddRowCountServiceCallWithSession:(LRBatchSession * _Nonnull)session;
	[Export ("doAddRowCountServiceCallWithSession:")]
	void DoAddRowCountServiceCallWithSession (LRBatchSession session);

	// -(instancetype _Nonnull)initWithStartRow:(NSInteger)startRow endRow:(NSInteger)endRow computeRowCount:(BOOL)computeRowCount repositoryId:(int64_t)repositoryId folderId:(int64_t)folderId mimeTypes:(NSArray<NSString *> * _Nonnull)mimeTypes __attribute__((objc_designated_initializer));
	[Export ("initWithStartRow:endRow:computeRowCount:repositoryId:folderId:mimeTypes:")]
	[DesignatedInitializer]
	IntPtr Constructor (nint startRow, nint endRow, bool computeRowCount, long repositoryId, long folderId, string[] mimeTypes);
}

// @interface Liferay70ImageGalleryUploadConnector : ImageGalleryUploadConnector
[BaseType (typeof(ImageGalleryUploadConnector))]
interface Liferay70ImageGalleryUploadConnector
{
	// -(BOOL)doSendFile:(LRSession * _Nonnull)session data:(LRUploadData * _Nonnull)data error:(NSError * _Nullable * _Nullable)error;
	[Export ("doSendFile:data:error:")]
	bool DoSendFile (LRSession session, LRUploadData data, [NullAllowed] out NSError error);

	// -(instancetype _Nonnull)initWithRepositoryId:(int64_t)repositoryId folderId:(int64_t)folderId sourceFileName:(NSString * _Nonnull)sourceFileName mimeType:(NSString * _Nonnull)mimeType title:(NSString * _Nonnull)title descrip:(NSString * _Nonnull)descrip changeLog:(NSString * _Nonnull)changeLog image:(UIImage * _Nonnull)image onUploadBytes:(void (^ _Nullable)(id _Nullable, uint64_t, uint64_t))onUploadBytes __attribute__((objc_designated_initializer));
	[Export ("initWithRepositoryId:folderId:sourceFileName:mimeType:title:descrip:changeLog:image:onUploadBytes:")]
	[DesignatedInitializer]
	IntPtr Constructor (long repositoryId, long folderId, string sourceFileName, string mimeType, string title, string descrip, string changeLog, UIImage image, [NullAllowed] Action<NSObject, ulong, ulong> onUploadBytes);
}

// @interface RatingDeleteLiferayConnector : ServerConnector
[BaseType (typeof(ServerConnector))]
[DisableDefaultCtor]
interface RatingDeleteLiferayConnector
{
	// @property (readonly, nonatomic) int64_t classPK;
	[Export ("classPK")]
	long ClassPK { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull className;
	[Export ("className")]
	string ClassName { get; }

	// @property (readonly, nonatomic) int32_t ratingsGroupCount;
	[Export ("ratingsGroupCount")]
	int RatingsGroupCount { get; }

	// @property (nonatomic, strong) RatingEntry * _Nullable resultRating;
	[NullAllowed, Export ("resultRating", ArgumentSemantic.Strong)]
	RatingEntry ResultRating { get; set; }

	// -(instancetype _Nonnull)initWithClassPK:(int64_t)classPK className:(NSString * _Nonnull)className ratingsGroupCount:(int32_t)ratingsGroupCount __attribute__((objc_designated_initializer));
	[Export ("initWithClassPK:className:ratingsGroupCount:")]
	[DesignatedInitializer]
	IntPtr Constructor (long classPK, string className, int ratingsGroupCount);

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }
}

// @interface Liferay70RatingDeleteConnector : RatingDeleteLiferayConnector
[BaseType (typeof(RatingDeleteLiferayConnector))]
interface Liferay70RatingDeleteConnector
{
	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(instancetype _Nonnull)initWithClassPK:(int64_t)classPK className:(NSString * _Nonnull)className ratingsGroupCount:(int32_t)ratingsGroupCount __attribute__((objc_designated_initializer));
	[Export ("initWithClassPK:className:ratingsGroupCount:")]
	[DesignatedInitializer]
	IntPtr Constructor (long classPK, string className, int ratingsGroupCount);
}

// @interface RatingLoadByClassPKLiferayConnector : ServerConnector
[BaseType (typeof(ServerConnector))]
[DisableDefaultCtor]
interface RatingLoadByClassPKLiferayConnector
{
	// @property (readonly, nonatomic) int64_t classPK;
	[Export ("classPK")]
	long ClassPK { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull className;
	[Export ("className")]
	string ClassName { get; }

	// @property (readonly, nonatomic) int32_t ratingsGroupCount;
	[Export ("ratingsGroupCount")]
	int RatingsGroupCount { get; }

	// @property (nonatomic, strong) RatingEntry * _Nullable resultRating;
	[NullAllowed, Export ("resultRating", ArgumentSemantic.Strong)]
	RatingEntry ResultRating { get; set; }

	// -(instancetype _Nonnull)initWithClassPK:(int64_t)classPK className:(NSString * _Nonnull)className ratingsGroupCount:(int32_t)ratingsGroupCount __attribute__((objc_designated_initializer));
	[Export ("initWithClassPK:className:ratingsGroupCount:")]
	[DesignatedInitializer]
	IntPtr Constructor (long classPK, string className, int ratingsGroupCount);

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }
}

// @interface Liferay70RatingLoadByClassPKConnector : RatingLoadByClassPKLiferayConnector
[BaseType (typeof(RatingLoadByClassPKLiferayConnector))]
interface Liferay70RatingLoadByClassPKConnector
{
	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(instancetype _Nonnull)initWithClassPK:(int64_t)classPK className:(NSString * _Nonnull)className ratingsGroupCount:(int32_t)ratingsGroupCount __attribute__((objc_designated_initializer));
	[Export ("initWithClassPK:className:ratingsGroupCount:")]
	[DesignatedInitializer]
	IntPtr Constructor (long classPK, string className, int ratingsGroupCount);
}

// @interface RatingLoadByEntryIdLiferayConnector : ServerConnector
[BaseType (typeof(ServerConnector))]
[DisableDefaultCtor]
interface RatingLoadByEntryIdLiferayConnector
{
	// @property (readonly, nonatomic) int64_t entryId;
	[Export ("entryId")]
	long EntryId { get; }

	// @property (readonly, nonatomic) int32_t ratingsGroupCount;
	[Export ("ratingsGroupCount")]
	int RatingsGroupCount { get; }

	// @property (nonatomic, strong) RatingEntry * _Nullable resultRating;
	[NullAllowed, Export ("resultRating", ArgumentSemantic.Strong)]
	RatingEntry ResultRating { get; set; }

	// -(instancetype _Nonnull)initWithEntryId:(int64_t)entryId ratingsGroupCount:(int32_t)ratingsGroupCount __attribute__((objc_designated_initializer));
	[Export ("initWithEntryId:ratingsGroupCount:")]
	[DesignatedInitializer]
	IntPtr Constructor (long entryId, int ratingsGroupCount);

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }
}

// @interface Liferay70RatingLoadByEntryIdConnector : RatingLoadByEntryIdLiferayConnector
[BaseType (typeof(RatingLoadByEntryIdLiferayConnector))]
interface Liferay70RatingLoadByEntryIdConnector
{
	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(instancetype _Nonnull)initWithEntryId:(int64_t)entryId ratingsGroupCount:(int32_t)ratingsGroupCount __attribute__((objc_designated_initializer));
	[Export ("initWithEntryId:ratingsGroupCount:")]
	[DesignatedInitializer]
	IntPtr Constructor (long entryId, int ratingsGroupCount);
}

// @interface RatingUpdateLiferayConnector : ServerConnector
[BaseType (typeof(ServerConnector))]
[DisableDefaultCtor]
interface RatingUpdateLiferayConnector
{
	// @property (readonly, nonatomic) int64_t classPK;
	[Export ("classPK")]
	long ClassPK { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull className;
	[Export ("className")]
	string ClassName { get; }

	// @property (readonly, nonatomic) double score;
	[Export ("score")]
	double Score { get; }

	// @property (readonly, nonatomic) int32_t ratingsGroupCount;
	[Export ("ratingsGroupCount")]
	int RatingsGroupCount { get; }

	// @property (nonatomic, strong) RatingEntry * _Nullable resultRating;
	[NullAllowed, Export ("resultRating", ArgumentSemantic.Strong)]
	RatingEntry ResultRating { get; set; }

	// -(instancetype _Nonnull)initWithClassPK:(int64_t)classPK className:(NSString * _Nonnull)className score:(double)score ratingsGroupCount:(int32_t)ratingsGroupCount __attribute__((objc_designated_initializer));
	[Export ("initWithClassPK:className:score:ratingsGroupCount:")]
	[DesignatedInitializer]
	IntPtr Constructor (long classPK, string className, double score, int ratingsGroupCount);

	// -(ValidationError * _Nullable)validateData __attribute__((warn_unused_result));
	[NullAllowed, Export ("validateData")]
	[Verify (MethodToProperty)]
	ValidationError ValidateData { get; }
}

// @interface Liferay70RatingUpdateConnector : RatingUpdateLiferayConnector
[BaseType (typeof(RatingUpdateLiferayConnector))]
interface Liferay70RatingUpdateConnector
{
	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(instancetype _Nonnull)initWithClassPK:(int64_t)classPK className:(NSString * _Nonnull)className score:(double)score ratingsGroupCount:(int32_t)ratingsGroupCount __attribute__((objc_designated_initializer));
	[Export ("initWithClassPK:className:score:ratingsGroupCount:")]
	[DesignatedInitializer]
	IntPtr Constructor (long classPK, string className, double score, int ratingsGroupCount);
}

// @interface Liferay70SignUpConnector : SignUpLiferayConnector
[BaseType (typeof(SignUpLiferayConnector))]
interface Liferay70SignUpConnector
{
	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(instancetype _Nonnull)initWithViewModel:(id<SignUpViewModel> _Nonnull)viewModel anonymousUsername:(NSString * _Nonnull)anonymousUsername anonymousPassword:(NSString * _Nonnull)anonymousPassword __attribute__((objc_designated_initializer));
	[Export ("initWithViewModel:anonymousUsername:anonymousPassword:")]
	[DesignatedInitializer]
	IntPtr Constructor (SignUpViewModel viewModel, string anonymousUsername, string anonymousPassword);
}

// @interface Liferay70UpdateCurrentUserConnector : UpdateCurrentUserLiferayConnector
[BaseType (typeof(UpdateCurrentUserLiferayConnector))]
interface Liferay70UpdateCurrentUserConnector
{
	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(instancetype _Nonnull)initWithViewModel:(id<SignUpViewModel> _Nonnull)viewModel __attribute__((objc_designated_initializer));
	[Export ("initWithViewModel:")]
	[DesignatedInitializer]
	IntPtr Constructor (SignUpViewModel viewModel);
}

// @interface Liferay70UploadUserPortraitConnector : UploadUserPortraitLiferayConnector
[BaseType (typeof(UploadUserPortraitLiferayConnector))]
interface Liferay70UploadUserPortraitConnector
{
	// -(void)uploadBytes:(NSData * _Nonnull)imageBytes withSession:(LRSession * _Nonnull)session;
	[Export ("uploadBytes:withSession:")]
	void UploadBytes (NSData imageBytes, LRSession session);

	// -(instancetype _Nonnull)initWithUserId:(int64_t)userId image:(UIImage * _Nonnull)image __attribute__((objc_designated_initializer));
	[Export ("initWithUserId:image:")]
	[DesignatedInitializer]
	IntPtr Constructor (long userId, UIImage image);
}

// @interface Liferay70WebContentListPageConnector : WebContentListPageLiferayConnector
[BaseType (typeof(WebContentListPageLiferayConnector))]
interface Liferay70WebContentListPageConnector
{
	// -(void)doAddPageRowsServiceCallWithSession:(LRBatchSession * _Nonnull)session startRow:(NSInteger)startRow endRow:(NSInteger)endRow obc:(LRJSONObjectWrapper * _Nullable)obc;
	[Export ("doAddPageRowsServiceCallWithSession:startRow:endRow:obc:")]
	void DoAddPageRowsServiceCallWithSession (LRBatchSession session, nint startRow, nint endRow, [NullAllowed] LRJSONObjectWrapper obc);

	// -(void)doAddRowCountServiceCallWithSession:(LRBatchSession * _Nonnull)session;
	[Export ("doAddRowCountServiceCallWithSession:")]
	void DoAddRowCountServiceCallWithSession (LRBatchSession session);
}

// @interface Liferay70WebContentLoadHtmlConnector : WebContentLoadHtmlLiferayConnector
[BaseType (typeof(WebContentLoadHtmlLiferayConnector))]
interface Liferay70WebContentLoadHtmlConnector
{
	// -(instancetype _Nonnull)initWithGroupId:(int64_t)groupId articleId:(NSString * _Nonnull)articleId __attribute__((objc_designated_initializer));
	[Export ("initWithGroupId:articleId:")]
	[DesignatedInitializer]
	IntPtr Constructor (long groupId, string articleId);
}

// @interface Liferay70WebContentLoadStructuredConnector : WebContentLoadStructuredLiferayConnector
[BaseType (typeof(WebContentLoadStructuredLiferayConnector))]
interface Liferay70WebContentLoadStructuredConnector
{
	// -(instancetype _Nonnull)initWithGroupId:(int64_t)groupId articleId:(NSString * _Nonnull)articleId structureId:(int64_t)structureId __attribute__((objc_designated_initializer));
	[Export ("initWithGroupId:articleId:structureId:")]
	[DesignatedInitializer]
	IntPtr Constructor (long groupId, string articleId, long structureId);
}

// @interface LiferayServerContext : NSObject
[BaseType (typeof(NSObject))]
interface LiferayServerContext
{
	// @property (copy, nonatomic, class) NSString * _Nonnull server;
	[Static]
	[Export ("server")]
	string Server { get; set; }

	// @property (nonatomic, class) enum LiferayServerVersion serverVersion;
	[Static]
	[Export ("serverVersion", ArgumentSemantic.Assign)]
	LiferayServerVersion ServerVersion { get; set; }

	// @property (nonatomic, class) int64_t companyId;
	[Static]
	[Export ("companyId")]
	long CompanyId { get; set; }

	// @property (nonatomic, class) int64_t groupId;
	[Static]
	[Export ("groupId")]
	long GroupId { get; set; }

	// @property (nonatomic, strong, class) id<ScreensFactory> _Nonnull factory;
	[Static]
	[Export ("factory", ArgumentSemantic.Strong)]
	ScreensFactory Factory { get; set; }

	// @property (nonatomic, strong, class) id<LiferayConnectorFactory> _Nonnull connectorFactory;
	[Static]
	[Export ("connectorFactory", ArgumentSemantic.Strong)]
	LiferayConnectorFactory ConnectorFactory { get; set; }

	// +(void)setPropertyValue:(id _Nonnull)value forKey:(NSString * _Nonnull)key;
	[Static]
	[Export ("setPropertyValue:forKey:")]
	void SetPropertyValue (NSObject value, string key);

	// +(id _Nonnull)propertyForKey:(NSString * _Nonnull)key __attribute__((warn_unused_result));
	[Static]
	[Export ("propertyForKey:")]
	NSObject PropertyForKey (string key);

	// +(NSNumber * _Nonnull)numberPropertyForKey:(NSString * _Nonnull)key __attribute__((warn_unused_result));
	[Static]
	[Export ("numberPropertyForKey:")]
	NSNumber NumberPropertyForKey (string key);

	// +(int64_t)longPropertyForKey:(NSString * _Nonnull)key __attribute__((warn_unused_result));
	[Static]
	[Export ("longPropertyForKey:")]
	long LongPropertyForKey (string key);

	// +(NSInteger)intPropertyForKey:(NSString * _Nonnull)key __attribute__((warn_unused_result));
	[Static]
	[Export ("intPropertyForKey:")]
	nint IntPropertyForKey (string key);

	// +(BOOL)booleanPropertyForKey:(NSString * _Nonnull)key __attribute__((warn_unused_result));
	[Static]
	[Export ("booleanPropertyForKey:")]
	bool BooleanPropertyForKey (string key);

	// +(NSDate * _Nonnull)datePropertyForKey:(NSString * _Nonnull)key __attribute__((warn_unused_result));
	[Static]
	[Export ("datePropertyForKey:")]
	NSDate DatePropertyForKey (string key);

	// +(NSString * _Nonnull)stringPropertyForKey:(NSString * _Nonnull)key __attribute__((warn_unused_result));
	[Static]
	[Export ("stringPropertyForKey:")]
	string StringPropertyForKey (string key);
}

// @interface LoadAssetInteractor : ServerReadConnectorInteractor
[BaseType (typeof(ServerReadConnectorInteractor))]
interface LoadAssetInteractor
{
	// @property (readonly, copy, nonatomic) NSString * _Nullable className;
	[NullAllowed, Export ("className")]
	string ClassName { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nullable portletItemName;
	[NullAllowed, Export ("portletItemName")]
	string PortletItemName { get; }

	// @property (nonatomic, strong) Asset * _Nullable asset;
	[NullAllowed, Export ("asset", ArgumentSemantic.Strong)]
	Asset Asset { get; set; }

	// -(instancetype _Nonnull)initWithScreenlet:(BaseScreenlet * _Nonnull)screenlet assetEntryId:(int64_t)assetEntryId;
	[Export ("initWithScreenlet:assetEntryId:")]
	IntPtr Constructor (BaseScreenlet screenlet, long assetEntryId);

	// -(instancetype _Nonnull)initWithScreenlet:(BaseScreenlet * _Nonnull)screenlet className:(NSString * _Nonnull)className classPK:(int64_t)classPK;
	[Export ("initWithScreenlet:className:classPK:")]
	IntPtr Constructor (BaseScreenlet screenlet, string className, long classPK);

	// -(instancetype _Nonnull)initWithScreenlet:(BaseScreenlet * _Nonnull)screenlet portletItemName:(NSString * _Nonnull)portletItemName;
	[Export ("initWithScreenlet:portletItemName:")]
	IntPtr Constructor (BaseScreenlet screenlet, string portletItemName);

	// -(ServerConnector * _Nullable)createConnector __attribute__((warn_unused_result));
	[NullAllowed, Export ("createConnector")]
	[Verify (MethodToProperty)]
	ServerConnector CreateConnector { get; }

	// -(void)completedConnector:(ServerConnector * _Nonnull)c;
	[Export ("completedConnector:")]
	void CompletedConnector (ServerConnector c);

	// -(void)readFromCache:(ServerConnector * _Nonnull)c result:(void (^ _Nonnull)(id _Nullable))result;
	[Export ("readFromCache:result:")]
	void ReadFromCache (ServerConnector c, Action<NSObject> result);

	// -(void)writeToCache:(ServerConnector * _Nonnull)c;
	[Export ("writeToCache:")]
	void WriteToCache (ServerConnector c);
}

// @interface LoadRatingsInteractor : ServerReadConnectorInteractor
[BaseType (typeof(ServerReadConnectorInteractor))]
interface LoadRatingsInteractor
{
	// -(ServerConnector * _Nullable)createConnector __attribute__((warn_unused_result));
	[NullAllowed, Export ("createConnector")]
	[Verify (MethodToProperty)]
	ServerConnector CreateConnector { get; }

	// -(void)completedConnector:(ServerConnector * _Nonnull)c;
	[Export ("completedConnector:")]
	void CompletedConnector (ServerConnector c);

	// -(void)readFromCache:(ServerConnector * _Nonnull)c result:(void (^ _Nonnull)(id _Nullable))result;
	[Export ("readFromCache:result:")]
	void ReadFromCache (ServerConnector c, Action<NSObject> result);

	// -(void)writeToCache:(ServerConnector * _Nonnull)c;
	[Export ("writeToCache:")]
	void WriteToCache (ServerConnector c);
}

// @interface LoginBasicInteractor : ServerConnectorInteractor
[BaseType (typeof(ServerConnectorInteractor))]
interface LoginBasicInteractor
{
	// @property (readonly, nonatomic) int64_t companyId;
	[Export ("companyId")]
	long CompanyId { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nullable screenName;
	[NullAllowed, Export ("screenName")]
	string ScreenName { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nullable emailAddress;
	[NullAllowed, Export ("emailAddress")]
	string EmailAddress { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull password;
	[Export ("password")]
	string Password { get; }

	// @property (copy, nonatomic) NSDictionary<NSString *,id> * _Nullable resultUserAttributes;
	[NullAllowed, Export ("resultUserAttributes", ArgumentSemantic.Copy)]
	NSDictionary<NSString, NSObject> ResultUserAttributes { get; set; }

	// -(instancetype _Nonnull)initWithLoginScreenlet:(LoginScreenlet * _Nonnull)loginScreenlet __attribute__((objc_designated_initializer));
	[Export ("initWithLoginScreenlet:")]
	[DesignatedInitializer]
	IntPtr Constructor (LoginScreenlet loginScreenlet);

	// -(instancetype _Nonnull)initWithCompanyId:(int64_t)companyId screenName:(NSString * _Nonnull)screenName password:(NSString * _Nonnull)password __attribute__((objc_designated_initializer));
	[Export ("initWithCompanyId:screenName:password:")]
	[DesignatedInitializer]
	IntPtr Constructor (long companyId, string screenName, string password);

	// -(instancetype _Nonnull)initWithCompanyId:(int64_t)companyId emailAddress:(NSString * _Nonnull)emailAddress password:(NSString * _Nonnull)password __attribute__((objc_designated_initializer));
	[Export ("initWithCompanyId:emailAddress:password:")]
	[DesignatedInitializer]
	IntPtr Constructor (long companyId, string emailAddress, string password);

	// -(instancetype _Nonnull)initWithUserId:(int64_t)userId password:(NSString * _Nonnull)password __attribute__((objc_designated_initializer));
	[Export ("initWithUserId:password:")]
	[DesignatedInitializer]
	IntPtr Constructor (long userId, string password);

	// -(GetUserBaseLiferayConnector * _Nullable)createConnector __attribute__((warn_unused_result));
	[NullAllowed, Export ("createConnector")]
	[Verify (MethodToProperty)]
	GetUserBaseLiferayConnector CreateConnector { get; }

	// -(void)completedConnector:(ServerConnector * _Nonnull)c;
	[Export ("completedConnector:")]
	void CompletedConnector (ServerConnector c);
}

// @interface LoginByEmailLiferay62Connector : GetUserByEmailLiferay62Connector
[BaseType (typeof(GetUserByEmailLiferay62Connector))]
interface LoginByEmailLiferay62Connector
{
	// -(instancetype _Nonnull)initWithCompanyId:(int64_t)companyId emailAddress:(NSString * _Nonnull)emailAddress password:(NSString * _Nonnull)password __attribute__((objc_designated_initializer));
	[Export ("initWithCompanyId:emailAddress:password:")]
	[DesignatedInitializer]
	IntPtr Constructor (long companyId, string emailAddress, string password);

	// -(void)postRun;
	[Export ("postRun")]
	void PostRun ();

	// -(LRSession * _Nullable)createSession __attribute__((warn_unused_result));
	[NullAllowed, Export ("createSession")]
	[Verify (MethodToProperty)]
	LRSession CreateSession { get; }
}

// @interface LoginByEmailLiferay70Connector : GetUserByEmailLiferay70Connector
[BaseType (typeof(GetUserByEmailLiferay70Connector))]
interface LoginByEmailLiferay70Connector
{
	// -(instancetype _Nonnull)initWithCompanyId:(int64_t)companyId emailAddress:(NSString * _Nonnull)emailAddress password:(NSString * _Nonnull)password __attribute__((objc_designated_initializer));
	[Export ("initWithCompanyId:emailAddress:password:")]
	[DesignatedInitializer]
	IntPtr Constructor (long companyId, string emailAddress, string password);

	// -(void)postRun;
	[Export ("postRun")]
	void PostRun ();

	// -(LRSession * _Nullable)createSession __attribute__((warn_unused_result));
	[NullAllowed, Export ("createSession")]
	[Verify (MethodToProperty)]
	LRSession CreateSession { get; }
}

// @interface LoginByScreenNameLiferay62Connector : GetUserByScreenNameLiferay62Connector
[BaseType (typeof(GetUserByScreenNameLiferay62Connector))]
interface LoginByScreenNameLiferay62Connector
{
	// -(instancetype _Nonnull)initWithCompanyId:(int64_t)companyId screenName:(NSString * _Nonnull)screenName password:(NSString * _Nonnull)password __attribute__((objc_designated_initializer));
	[Export ("initWithCompanyId:screenName:password:")]
	[DesignatedInitializer]
	IntPtr Constructor (long companyId, string screenName, string password);

	// -(void)postRun;
	[Export ("postRun")]
	void PostRun ();

	// -(LRSession * _Nullable)createSession __attribute__((warn_unused_result));
	[NullAllowed, Export ("createSession")]
	[Verify (MethodToProperty)]
	LRSession CreateSession { get; }
}

// @interface LoginByScreenNameLiferay70Connector : GetUserByScreenNameLiferay70Connector
[BaseType (typeof(GetUserByScreenNameLiferay70Connector))]
interface LoginByScreenNameLiferay70Connector
{
	// -(instancetype _Nonnull)initWithCompanyId:(int64_t)companyId screenName:(NSString * _Nonnull)screenName password:(NSString * _Nonnull)password __attribute__((objc_designated_initializer));
	[Export ("initWithCompanyId:screenName:password:")]
	[DesignatedInitializer]
	IntPtr Constructor (long companyId, string screenName, string password);

	// -(void)postRun;
	[Export ("postRun")]
	void PostRun ();

	// -(LRSession * _Nullable)createSession __attribute__((warn_unused_result));
	[NullAllowed, Export ("createSession")]
	[Verify (MethodToProperty)]
	LRSession CreateSession { get; }
}

// @interface LoginByUserIdLiferay62Connector : GetUserByUserIdLiferay62Connector
[BaseType (typeof(GetUserByUserIdLiferay62Connector))]
interface LoginByUserIdLiferay62Connector
{
	// -(instancetype _Nonnull)initWithUserId:(int64_t)userId password:(NSString * _Nonnull)password __attribute__((objc_designated_initializer));
	[Export ("initWithUserId:password:")]
	[DesignatedInitializer]
	IntPtr Constructor (long userId, string password);

	// -(void)postRun;
	[Export ("postRun")]
	void PostRun ();

	// -(LRSession * _Nullable)createSession __attribute__((warn_unused_result));
	[NullAllowed, Export ("createSession")]
	[Verify (MethodToProperty)]
	LRSession CreateSession { get; }
}

// @interface LoginByUserIdLiferay70Connector : GetUserByUserIdLiferay70Connector
[BaseType (typeof(GetUserByUserIdLiferay70Connector))]
interface LoginByUserIdLiferay70Connector
{
	// -(instancetype _Nonnull)initWithUserId:(int64_t)userId password:(NSString * _Nonnull)password __attribute__((objc_designated_initializer));
	[Export ("initWithUserId:password:")]
	[DesignatedInitializer]
	IntPtr Constructor (long userId, string password);

	// -(void)postRun;
	[Export ("postRun")]
	void PostRun ();

	// -(LRSession * _Nullable)createSession __attribute__((warn_unused_result));
	[NullAllowed, Export ("createSession")]
	[Verify (MethodToProperty)]
	LRSession CreateSession { get; }
}

// @interface LoginCookieInteractor : Interactor
[BaseType (typeof(Interactor))]
interface LoginCookieInteractor
{
	// @property (readonly, copy, nonatomic) NSString * _Nonnull emailAddress;
	[Export ("emailAddress")]
	string EmailAddress { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull password;
	[Export ("password")]
	string Password { get; }

	// @property (readonly, nonatomic) int64_t companyId;
	[Export ("companyId")]
	long CompanyId { get; }

	// @property (readonly, copy, nonatomic) void (^ _Nullable)(NSURLAuthenticationChallenge * _Nonnull, void (^ _Nonnull)(NSURLSessionAuthChallengeDisposition, NSURLCredential * _Nonnull)) challengeResolver;
	[NullAllowed, Export ("challengeResolver", ArgumentSemantic.Copy)]
	Action<NSURLAuthenticationChallenge, Action<NSURLSessionAuthChallengeDisposition, NSURLCredential>> ChallengeResolver { get; }

	// @property (nonatomic, strong) LRSession * _Nullable cookieSession;
	[NullAllowed, Export ("cookieSession", ArgumentSemantic.Strong)]
	LRSession CookieSession { get; set; }

	// @property (copy, nonatomic) NSDictionary<NSString *,id> * _Nullable resultUserAttributes;
	[NullAllowed, Export ("resultUserAttributes", ArgumentSemantic.Copy)]
	NSDictionary<NSString, NSObject> ResultUserAttributes { get; set; }

	// -(instancetype _Nonnull)initWithScreenlet:(BaseScreenlet * _Nullable)screenlet companyId:(int64_t)companyId emailAddress:(NSString * _Nonnull)emailAddress password:(NSString * _Nonnull)password challengeResolver:(void (^ _Nullable)(NSURLAuthenticationChallenge * _Nonnull, void (^ _Nonnull)(NSURLSessionAuthChallengeDisposition, NSURLCredential * _Nonnull)))challengeResolver __attribute__((objc_designated_initializer));
	[Export ("initWithScreenlet:companyId:emailAddress:password:challengeResolver:")]
	[DesignatedInitializer]
	IntPtr Constructor ([NullAllowed] BaseScreenlet screenlet, long companyId, string emailAddress, string password, [NullAllowed] Action<NSURLAuthenticationChallenge, Action<NSURLSessionAuthChallengeDisposition, NSURLCredential>> challengeResolver);

	// -(BOOL)start __attribute__((warn_unused_result));
	[Export ("start")]
	[Verify (MethodToProperty)]
	bool Start { get; }

	// -(void)onCookieFailure:(NSError * _Null_unspecified)error;
	[Export ("onCookieFailure:")]
	void OnCookieFailure (NSError error);

	// -(void)onSuccess:(id _Null_unspecified)result;
	[Export ("onSuccess:")]
	void OnSuccess (NSObject result);

	// -(void)onFailure:(NSError * _Null_unspecified)error;
	[Export ("onFailure:")]
	void OnFailure (NSError error);

	// -(void)onCookieSuccess:(LRSession * _Null_unspecified)session;
	[Export ("onCookieSuccess:")]
	void OnCookieSuccess (LRSession session);
}

// @interface LoginScreenlet : BaseScreenlet <BasicAuthBasedType>
[BaseType (typeof(BaseScreenlet))]
interface LoginScreenlet : IBasicAuthBasedType
{
	// @property (copy, nonatomic) NSString * _Nullable basicAuthMethod;
	[NullAllowed, Export ("basicAuthMethod")]
	string BasicAuthMethod { get; set; }

	// @property (nonatomic) BOOL saveCredentials;
	[Export ("saveCredentials")]
	bool SaveCredentials { get; set; }

	// @property (nonatomic) int64_t companyId;
	[Export ("companyId")]
	long CompanyId { get; set; }

	// @property (copy, nonatomic) NSString * _Nonnull OAuthConsumerKey;
	[Export ("OAuthConsumerKey")]
	string OAuthConsumerKey { get; set; }

	// @property (copy, nonatomic) NSString * _Nonnull OAuthConsumerSecret;
	[Export ("OAuthConsumerSecret")]
	string OAuthConsumerSecret { get; set; }

	// @property (copy, nonatomic) NSString * _Nonnull loginMode;
	[Export ("loginMode")]
	string LoginMode { get; set; }

	// @property (copy, nonatomic) void (^ _Nullable)(NSURLAuthenticationChallenge * _Nonnull, void (^ _Nonnull)(NSURLSessionAuthChallengeDisposition, NSURLCredential * _Nonnull)) challengeResolver;
	[NullAllowed, Export ("challengeResolver", ArgumentSemantic.Copy)]
	Action<NSURLAuthenticationChallenge, Action<NSURLSessionAuthChallengeDisposition, NSURLCredential>> ChallengeResolver { get; set; }

	[Wrap ("WeakLoginDelegate")]
	[NullAllowed]
	LoginScreenletDelegate LoginDelegate { get; }

	// @property (readonly, nonatomic, strong) id<LoginScreenletDelegate> _Nullable loginDelegate;
	[NullAllowed, Export ("loginDelegate", ArgumentSemantic.Strong)]
	NSObject WeakLoginDelegate { get; }

	// @property (readonly, nonatomic, strong) id<LoginViewModel> _Nonnull viewModel;
	[Export ("viewModel", ArgumentSemantic.Strong)]
	LoginViewModel ViewModel { get; }

	// @property (nonatomic) enum AuthType authType;
	[Export ("authType", ArgumentSemantic.Assign)]
	AuthType AuthType { get; set; }

	// -(void)onCreated;
	[Export ("onCreated")]
	void OnCreated ();

	// -(Interactor * _Nullable)createInteractorWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender __attribute__((warn_unused_result));
	[Export ("createInteractorWithName:sender:")]
	[return: NullAllowed]
	Interactor CreateInteractorWithName (string name, [NullAllowed] NSObject sender);

	// -(BOOL)loadStoredCredentials __attribute__((warn_unused_result));
	[Export ("loadStoredCredentials")]
	[Verify (MethodToProperty)]
	bool LoadStoredCredentials { get; }

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:themeName:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame, [NullAllowed] string themeName);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @protocol LoginScreenletDelegate <BaseScreenletDelegate>
[Protocol, Model]
interface LoginScreenletDelegate : IBaseScreenletDelegate
{
	// @optional -(void)screenlet:(BaseScreenlet * _Nonnull)screenlet onLoginResponseUserAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes;
	[Export ("screenlet:onLoginResponseUserAttributes:")]
	void OnLoginResponseUserAttributes (BaseScreenlet screenlet, NSDictionary<NSString, NSObject> attributes);

	// @optional -(void)screenlet:(BaseScreenlet * _Nonnull)screenlet onLoginError:(NSError * _Nonnull)error;
	[Export ("screenlet:onLoginError:")]
	void OnLoginError (BaseScreenlet screenlet, NSError error);

	// @optional -(void)screenlet:(BaseScreenlet * _Nonnull)screenlet onCredentialsSavedUserAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes;
	[Export ("screenlet:onCredentialsSavedUserAttributes:")]
	void OnCredentialsSavedUserAttributes (BaseScreenlet screenlet, NSDictionary<NSString, NSObject> attributes);

	// @optional -(void)screenlet:(LoginScreenlet * _Nonnull)screenlet onCredentialsLoadedUserAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes;
	[Export ("screenlet:onCredentialsLoadedUserAttributes:")]
	void OnCredentialsLoadedUserAttributes (LoginScreenlet screenlet, NSDictionary<NSString, NSObject> attributes);
}

// @protocol LoginViewModel <BasicAuthBasedType>
[Protocol, Model]
interface LoginViewModel : IBasicAuthBasedType
{
	// @required @property (copy, nonatomic) NSString * _Nullable userName;
	[Abstract]
	[NullAllowed, Export ("userName")]
	string UserName { get; set; }

	// @required @property (copy, nonatomic) NSString * _Nullable password;
	[Abstract]
	[NullAllowed, Export ("password")]
	string Password { get; set; }

	// @required @property (copy, nonatomic) NSString * _Nullable authType;
	[Abstract]
	[NullAllowed, Export ("authType")]
	string AuthType { get; set; }
}

// @interface LoginView_default : BaseScreenletView <LoginViewModel, BasicAuthBasedType>
[BaseType (typeof(BaseScreenletView))]
interface LoginView_default : ILoginViewModel, IBasicAuthBasedType
{
	// @property (nonatomic, weak) UITextField * _Nullable userNameField __attribute__((iboutlet));
	[NullAllowed, Export ("userNameField", ArgumentSemantic.Weak)]
	UITextField UserNameField { get; set; }

	// @property (nonatomic, weak) UITextField * _Nullable passwordField __attribute__((iboutlet));
	[NullAllowed, Export ("passwordField", ArgumentSemantic.Weak)]
	UITextField PasswordField { get; set; }

	// @property (nonatomic, weak) UIButton * _Nullable loginButton __attribute__((iboutlet));
	[NullAllowed, Export ("loginButton", ArgumentSemantic.Weak)]
	UIButton LoginButton { get; set; }

	// @property (nonatomic, weak) UIButton * _Nullable authorizeButton __attribute__((iboutlet));
	[NullAllowed, Export ("authorizeButton", ArgumentSemantic.Weak)]
	UIButton AuthorizeButton { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable basicAuthMethod;
	[NullAllowed, Export ("basicAuthMethod")]
	string BasicAuthMethod { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable authType;
	[NullAllowed, Export ("authType")]
	string AuthType { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable userName;
	[NullAllowed, Export ("userName")]
	string UserName { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable password;
	[NullAllowed, Export ("password")]
	string Password { get; set; }

	// -(void)onCreated;
	[Export ("onCreated")]
	void OnCreated ();

	// -(void)onSetTranslations;
	[Export ("onSetTranslations")]
	void OnSetTranslations ();

	// -(void)onStartInteraction;
	[Export ("onStartInteraction")]
	void OnStartInteraction ();

	// -(void)onFinishInteraction:(id _Nullable)result error:(NSError * _Nullable)error;
	[Export ("onFinishInteraction:error:")]
	void OnFinishInteraction ([NullAllowed] NSObject result, [NullAllowed] NSError error);

	// -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
	[Export ("createProgressPresenter")]
	[Verify (MethodToProperty)]
	ProgressPresenter CreateProgressPresenter { get; }

	// -(void)configureAuthType;
	[Export ("configureAuthType")]
	void ConfigureAuthType ();

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface MediaSelector : NSObject <UIImagePickerControllerDelegate, UINavigationControllerDelegate>
[BaseType (typeof(NSObject))]
[DisableDefaultCtor]
interface MediaSelector : IUIImagePickerControllerDelegate, IUINavigationControllerDelegate
{
	// -(void)show;
	[Export ("show")]
	void Show ();

	// -(void)imagePickerController:(UIImagePickerController * _Nonnull)picker didFinishPickingMediaWithInfo:(NSDictionary<NSString *,id> * _Nonnull)info;
	[Export ("imagePickerController:didFinishPickingMediaWithInfo:")]
	void ImagePickerController (UIImagePickerController picker, NSDictionary<NSString, NSObject> info);

	// -(void)imagePickerControllerDidCancel:(UIImagePickerController * _Nonnull)picker;
	[Export ("imagePickerControllerDidCancel:")]
	void ImagePickerControllerDidCancel (UIImagePickerController picker);
}

// @interface LiferayScreens_Swift_4096 (NSError)
[Category]
[BaseType (typeof(NSError))]
interface NSError_LiferayScreens_Swift_4096
{
}

// @interface LiferayScreens_Swift_4100 (NSLocale)
[Category]
[BaseType (typeof(NSLocale))]
interface NSLocale_LiferayScreens_Swift_4100
{
	// @property (copy, nonatomic, class) NSString * _Nonnull currentLanguageString;
	[Static]
	[Export ("currentLanguageString")]
	string CurrentLanguageString { get; set; }

	// @property (readonly, copy, nonatomic, class) NSString * _Nonnull currentLocaleString;
	[Static]
	[Export ("currentLocaleString")]
	string CurrentLocaleString { get; }

	// +(NSBundle * _Nullable)bundleForLanguage:(NSString * _Nonnull)language bundle:(NSBundle * _Nonnull)bundle __attribute__((warn_unused_result));
	[Static]
	[Export ("bundleForLanguage:bundle:")]
	[return: NullAllowed]
	NSBundle BundleForLanguage (string language, NSBundle bundle);
}

// @interface NetworkActivityIndicatorPresenter : NSObject <ProgressPresenter>
[BaseType (typeof(NSObject))]
interface NetworkActivityIndicatorPresenter : IProgressPresenter
{
	// -(void)showHUDInView:(UIView * _Nonnull)view message:(NSString * _Nullable)message forInteractor:(Interactor * _Nonnull)interactor;
	[Export ("showHUDInView:message:forInteractor:")]
	void ShowHUDInView (UIView view, [NullAllowed] string message, Interactor interactor);

	// -(void)hideHUDFromView:(UIView * _Nullable)view message:(NSString * _Nullable)message forInteractor:(Interactor * _Nonnull)interactor withError:(NSError * _Nullable)error;
	[Export ("hideHUDFromView:message:forInteractor:withError:")]
	void HideHUDFromView ([NullAllowed] UIView view, [NullAllowed] string message, Interactor interactor, [NullAllowed] NSError error);
}

// @interface OAuthCredentialsStoreKeyChain : BaseCredentialsStoreKeyChain
[BaseType (typeof(BaseCredentialsStoreKeyChain))]
interface OAuthCredentialsStoreKeyChain
{
}

// @interface PdfDisplayScreenlet : FileDisplayScreenlet
[BaseType (typeof(FileDisplayScreenlet))]
interface PdfDisplayScreenlet
{
	// @property (copy, nonatomic) NSString * _Nonnull mimeTypes;
	[Export ("mimeTypes")]
	string MimeTypes { get; set; }

	// @property (readonly, copy, nonatomic) NSArray<NSString *> * _Nonnull supportedMimeTypes;
	[Export ("supportedMimeTypes", ArgumentSemantic.Copy)]
	string[] SupportedMimeTypes { get; }

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:themeName:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame, [NullAllowed] string themeName);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface PdfDisplayView_default : BaseScreenletView <FileDisplayViewModel>
[BaseType (typeof(BaseScreenletView))]
interface PdfDisplayView_default : IFileDisplayViewModel
{
	// @property (nonatomic, strong) WKWebView * _Nullable webView;
	[NullAllowed, Export ("webView", ArgumentSemantic.Strong)]
	WKWebView WebView { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable title;
	[NullAllowed, Export ("title")]
	string Title { get; set; }

	// @property (copy, nonatomic) NSURL * _Nullable url;
	[NullAllowed, Export ("url", ArgumentSemantic.Copy)]
	NSUrl Url { get; set; }

	// -(void)onCreated;
	[Export ("onCreated")]
	void OnCreated ();

	// -(void)addWebView;
	[Export ("addWebView")]
	void AddWebView ();

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface RatingEntry : NSObject <NSCoding>
[BaseType (typeof(NSObject))]
[DisableDefaultCtor]
interface RatingEntry : INSCoding
{
	// @property (readonly, copy, nonatomic) NSDictionary<NSString *,id> * _Nonnull attributes;
	[Export ("attributes", ArgumentSemantic.Copy)]
	NSDictionary<NSString, NSObject> Attributes { get; }

	// @property (readonly, nonatomic) NSInteger totalCount;
	[Export ("totalCount")]
	nint TotalCount { get; }

	// @property (readonly, nonatomic) double average;
	[Export ("average")]
	double Average { get; }

	// @property (readonly, nonatomic) double userScore;
	[Export ("userScore")]
	double UserScore { get; }

	// @property (readonly, nonatomic) int64_t classPK;
	[Export ("classPK")]
	long ClassPK { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull className;
	[Export ("className")]
	string ClassName { get; }

	// @property (readonly, copy, nonatomic) NSArray<NSNumber *> * _Nonnull ratings;
	[Export ("ratings", ArgumentSemantic.Copy)]
	NSNumber[] Ratings { get; }

	// -(void)encodeWithCoder:(NSCoder * _Nonnull)aCoder;
	[Export ("encodeWithCoder:")]
	void EncodeWithCoder (NSCoder aCoder);

	// -(instancetype _Nonnull)initWithAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes __attribute__((objc_designated_initializer));
	[Export ("initWithAttributes:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSDictionary<NSString, NSObject> attributes);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface RatingScreenlet : BaseScreenlet
[BaseType (typeof(BaseScreenlet))]
interface RatingScreenlet
{
	// @property (readonly, copy, nonatomic, class) NSString * _Nonnull DeleteRatingAction;
	[Static]
	[Export ("DeleteRatingAction")]
	string DeleteRatingAction { get; }

	// @property (readonly, copy, nonatomic, class) NSString * _Nonnull UpdateRatingAction;
	[Static]
	[Export ("UpdateRatingAction")]
	string UpdateRatingAction { get; }

	// @property (readonly, copy, nonatomic, class) NSString * _Nonnull LoadRatingsAction;
	[Static]
	[Export ("LoadRatingsAction")]
	string LoadRatingsAction { get; }

	// @property (nonatomic) int64_t entryId;
	[Export ("entryId")]
	long EntryId { get; set; }

	// @property (copy, nonatomic) NSString * _Nonnull className;
	[Export ("className")]
	string ClassName { get; set; }

	// @property (nonatomic) int64_t classPK;
	[Export ("classPK")]
	long ClassPK { get; set; }

	// @property (nonatomic) int32_t ratingsGroupCount;
	[Export ("ratingsGroupCount")]
	int RatingsGroupCount { get; set; }

	// @property (nonatomic) BOOL autoLoad;
	[Export ("autoLoad")]
	bool AutoLoad { get; set; }

	// @property (nonatomic) BOOL editable;
	[Export ("editable")]
	bool Editable { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable offlinePolicy;
	[NullAllowed, Export ("offlinePolicy")]
	string OfflinePolicy { get; set; }

	[Wrap ("WeakRatingDisplayDelegate")]
	[NullAllowed]
	RatingScreenletDelegate RatingDisplayDelegate { get; }

	// @property (readonly, nonatomic, strong) id<RatingScreenletDelegate> _Nullable ratingDisplayDelegate;
	[NullAllowed, Export ("ratingDisplayDelegate", ArgumentSemantic.Strong)]
	NSObject WeakRatingDisplayDelegate { get; }

	// @property (readonly, nonatomic, strong) id<RatingViewModel> _Nullable viewModel;
	[NullAllowed, Export ("viewModel", ArgumentSemantic.Strong)]
	RatingViewModel ViewModel { get; }

	// -(void)prepareForInterfaceBuilder;
	[Export ("prepareForInterfaceBuilder")]
	void PrepareForInterfaceBuilder ();

	// -(void)onPreCreate;
	[Export ("onPreCreate")]
	void OnPreCreate ();

	// -(void)onCreated;
	[Export ("onCreated")]
	void OnCreated ();

	// -(void)onShow;
	[Export ("onShow")]
	void OnShow ();

	// -(Interactor * _Nullable)createInteractorWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender __attribute__((warn_unused_result));
	[Export ("createInteractorWithName:sender:")]
	[return: NullAllowed]
	Interactor CreateInteractorWithName (string name, [NullAllowed] NSObject sender);

	// -(BOOL)performDefaultAction __attribute__((warn_unused_result));
	[Export ("performDefaultAction")]
	[Verify (MethodToProperty)]
	bool PerformDefaultAction { get; }

	// -(BOOL)loadRatings;
	[Export ("loadRatings")]
	[Verify (MethodToProperty)]
	bool LoadRatings { get; }

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:themeName:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame, [NullAllowed] string themeName);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @protocol RatingScreenletDelegate <BaseScreenletDelegate>
[Protocol, Model]
interface RatingScreenletDelegate : IBaseScreenletDelegate
{
	// @optional -(void)screenlet:(RatingScreenlet * _Nonnull)screenlet onRatingRetrieve:(RatingEntry * _Nonnull)rating;
	[Export ("screenlet:onRatingRetrieve:")]
	void OnRatingRetrieve (RatingScreenlet screenlet, RatingEntry rating);

	// @optional -(void)screenlet:(RatingScreenlet * _Nonnull)screenlet onRatingDeleted:(RatingEntry * _Nonnull)rating;
	[Export ("screenlet:onRatingDeleted:")]
	void OnRatingDeleted (RatingScreenlet screenlet, RatingEntry rating);

	// @optional -(void)screenlet:(RatingScreenlet * _Nonnull)screenlet onRatingUpdated:(RatingEntry * _Nonnull)rating;
	[Export ("screenlet:onRatingUpdated:")]
	void OnRatingUpdated (RatingScreenlet screenlet, RatingEntry rating);

	// @optional -(void)screenlet:(RatingScreenlet * _Nonnull)screenlet onRatingError:(NSError * _Nonnull)error;
	[Export ("screenlet:onRatingError:")]
	void OnRatingError (RatingScreenlet screenlet, NSError error);
}

// @protocol RatingViewModel
[Protocol, Model]
interface RatingViewModel
{
	// @required @property (readonly, nonatomic) int32_t defaultRatingsGroupCount;
	[Abstract]
	[Export ("defaultRatingsGroupCount")]
	int DefaultRatingsGroupCount { get; }

	// @required @property (nonatomic, strong) RatingEntry * _Nullable ratingEntry;
	[Abstract]
	[NullAllowed, Export ("ratingEntry", ArgumentSemantic.Strong)]
	RatingEntry RatingEntry { get; set; }
}

// @interface RatingView_default_emojis : BaseScreenletView <RatingViewModel>
[BaseType (typeof(BaseScreenletView))]
interface RatingView_default_emojis : IRatingViewModel
{
	// @property (readonly, nonatomic) int32_t defaultRatingsGroupCount;
	[Export ("defaultRatingsGroupCount")]
	int DefaultRatingsGroupCount { get; }

	// -(void)onCreated;
	[Export ("onCreated")]
	void OnCreated ();

	// -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
	[Export ("createProgressPresenter")]
	[Verify (MethodToProperty)]
	ProgressPresenter CreateProgressPresenter { get; }

	// @property (nonatomic, strong) RatingEntry * _Nullable ratingEntry;
	[NullAllowed, Export ("ratingEntry", ArgumentSemantic.Strong)]
	RatingEntry RatingEntry { get; set; }

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface RatingView_default_like : BaseScreenletView <RatingViewModel>
[BaseType (typeof(BaseScreenletView))]
interface RatingView_default_like : IRatingViewModel
{
	// @property (nonatomic) int32_t defaultRatingsGroupCount;
	[Export ("defaultRatingsGroupCount")]
	int DefaultRatingsGroupCount { get; set; }

	// -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
	[Export ("createProgressPresenter")]
	[Verify (MethodToProperty)]
	ProgressPresenter CreateProgressPresenter { get; }

	// @property (nonatomic, strong) RatingEntry * _Nullable ratingEntry;
	[NullAllowed, Export ("ratingEntry", ArgumentSemantic.Strong)]
	RatingEntry RatingEntry { get; set; }

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface RatingView_default_stars : BaseScreenletView <RatingViewModel>
[BaseType (typeof(BaseScreenletView))]
interface RatingView_default_stars : IRatingViewModel
{
	// @property (nonatomic) int32_t defaultRatingsGroupCount;
	[Export ("defaultRatingsGroupCount")]
	int DefaultRatingsGroupCount { get; set; }

	// -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
	[Export ("createProgressPresenter")]
	[Verify (MethodToProperty)]
	ProgressPresenter CreateProgressPresenter { get; }

	// @property (nonatomic, strong) RatingEntry * _Nullable ratingEntry;
	[NullAllowed, Export ("ratingEntry", ArgumentSemantic.Strong)]
	RatingEntry RatingEntry { get; set; }

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface RatingView_default_thumbs : BaseScreenletView <RatingViewModel>
[BaseType (typeof(BaseScreenletView))]
interface RatingView_default_thumbs : IRatingViewModel
{
	// @property (nonatomic) int32_t defaultRatingsGroupCount;
	[Export ("defaultRatingsGroupCount")]
	int DefaultRatingsGroupCount { get; set; }

	// -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
	[Export ("createProgressPresenter")]
	[Verify (MethodToProperty)]
	ProgressPresenter CreateProgressPresenter { get; }

	// @property (nonatomic, strong) RatingEntry * _Nullable ratingEntry;
	[NullAllowed, Export ("ratingEntry", ArgumentSemantic.Strong)]
	RatingEntry RatingEntry { get; set; }

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface LiferayScreens_Swift_4320
interface LiferayScreens_Swift_4320
{
	// -(SMXMLElement * _Nullable)deepChildWithAttribute:(NSString * _Nonnull)attributeName value:(NSString * _Nonnull)attributeValue __attribute__((warn_unused_result));
	[Export ("deepChildWithAttribute:value:")]
	[return: NullAllowed]
	SMXMLElement DeepChildWithAttribute (string attributeName, string attributeValue);
}

// @interface LiferayScreens_Swift_4325 (SMXMLElement)
[Category]
[BaseType (typeof(SMXMLElement))]
interface SMXMLElement_LiferayScreens_Swift_4325
{
	// -(SMXMLElement * _Nullable)deepChildWithAttribute:(NSString * _Nonnull)attributeName value:(NSString * _Nonnull)attributeValue __attribute__((warn_unused_result));
	[Export ("deepChildWithAttribute:value:")]
	[return: NullAllowed]
	SMXMLElement DeepChildWithAttribute (string attributeName, string attributeValue);
}

// @interface SaveUserInteractor : ServerConnectorInteractor
[BaseType (typeof(ServerConnectorInteractor))]
interface SaveUserInteractor
{
	// @property (copy, nonatomic) NSDictionary<NSString *,id> * _Nullable resultUserAttributes;
	[NullAllowed, Export ("resultUserAttributes", ArgumentSemantic.Copy)]
	NSDictionary<NSString, NSObject> ResultUserAttributes { get; set; }

	// -(UpdateCurrentUserLiferayConnector * _Nonnull)createConnector __attribute__((warn_unused_result));
	[Export ("createConnector")]
	[Verify (MethodToProperty)]
	UpdateCurrentUserLiferayConnector CreateConnector { get; }

	// -(void)completedConnector:(ServerConnector * _Nonnull)c;
	[Export ("completedConnector:")]
	void CompletedConnector (ServerConnector c);

	// -(instancetype _Nonnull)initWithScreenlet:(BaseScreenlet * _Nullable)screenlet __attribute__((objc_designated_initializer));
	[Export ("initWithScreenlet:")]
	[DesignatedInitializer]
	IntPtr Constructor ([NullAllowed] BaseScreenlet screenlet);
}

// @protocol ScreensFactory
[Protocol, Model]
interface ScreensFactory
{
	// @required -(SessionContext * _Nonnull)createSessionContextWithSession:(LRSession * _Nonnull)session attributes:(NSDictionary<NSString *,id> * _Nonnull)attributes store:(id<CredentialsStore> _Nonnull)store __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createSessionContextWithSession:attributes:store:")]
	SessionContext CreateSessionContextWithSession (LRSession session, NSDictionary<NSString, NSObject> attributes, CredentialsStore store);

	// @required -(CacheManager * _Nonnull)createCacheManagerWithSession:(LRSession * _Nonnull)session userId:(int64_t)userId __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createCacheManagerWithSession:userId:")]
	CacheManager CreateCacheManagerWithSession (LRSession session, long userId);

	// @required -(id<CredentialsStore> _Nonnull)createCredentialsStore:(enum AuthType)authType __attribute__((warn_unused_result));
	[Abstract]
	[Export ("createCredentialsStore:")]
	CredentialsStore CreateCredentialsStore (AuthType authType);
}

// @interface ScreensFactoryImpl : NSObject <ScreensFactory>
[BaseType (typeof(NSObject))]
interface ScreensFactoryImpl : IScreensFactory
{
	// -(SessionContext * _Nonnull)createSessionContextWithSession:(LRSession * _Nonnull)session attributes:(NSDictionary<NSString *,id> * _Nonnull)attributes store:(id<CredentialsStore> _Nonnull)store __attribute__((warn_unused_result));
	[Export ("createSessionContextWithSession:attributes:store:")]
	SessionContext CreateSessionContextWithSession (LRSession session, NSDictionary<NSString, NSObject> attributes, CredentialsStore store);

	// -(CacheManager * _Nonnull)createCacheManagerWithSession:(LRSession * _Nonnull)session userId:(int64_t)userId __attribute__((warn_unused_result));
	[Export ("createCacheManagerWithSession:userId:")]
	CacheManager CreateCacheManagerWithSession (LRSession session, long userId);

	// -(id<CredentialsStore> _Nonnull)createCredentialsStore:(enum AuthType)authType __attribute__((warn_unused_result));
	[Export ("createCredentialsStore:")]
	CredentialsStore CreateCredentialsStore (AuthType authType);
}

// @interface ServerConnectorChain : ServerConnector
[BaseType (typeof(ServerConnector))]
[DisableDefaultCtor]
interface ServerConnectorChain
{
	// @property (copy, nonatomic) ServerConnector * _Nullable (^ _Nullable)(ServerConnector * _Nonnull, NSInteger) onNextStep;
	[NullAllowed, Export ("onNextStep", ArgumentSemantic.Copy)]
	Func<ServerConnector, nint, ServerConnector> OnNextStep { get; set; }

	// @property (readonly, nonatomic, strong) ServerConnector * _Nonnull headConnector;
	[Export ("headConnector", ArgumentSemantic.Strong)]
	ServerConnector HeadConnector { get; }

	// @property (nonatomic, strong) ServerConnector * _Nonnull currentConnector;
	[Export ("currentConnector", ArgumentSemantic.Strong)]
	ServerConnector CurrentConnector { get; set; }

	// -(instancetype _Nonnull)initWithHead:(ServerConnector * _Nonnull)head __attribute__((objc_designated_initializer));
	[Export ("initWithHead:")]
	[DesignatedInitializer]
	IntPtr Constructor (ServerConnector head);

	// -(LRSession * _Nullable)createSession __attribute__((warn_unused_result));
	[NullAllowed, Export ("createSession")]
	[Verify (MethodToProperty)]
	LRSession CreateSession { get; }

	// -(void)enqueue:(void (^ _Nullable)(ServerConnector * _Nonnull))onComplete;
	[Export ("enqueue:")]
	void Enqueue ([NullAllowed] Action<ServerConnector> onComplete);

	// -(void)doRunWithSession:(LRSession * _Nonnull)session;
	[Export ("doRunWithSession:")]
	void DoRunWithSession (LRSession session);

	// -(void)callOnComplete;
	[Export ("callOnComplete")]
	void CallOnComplete ();
}

// @interface SessionContext : NSObject
[BaseType (typeof(NSObject))]
[DisableDefaultCtor]
interface SessionContext
{
	// @property (nonatomic, strong, class) SessionContext * _Nullable currentContext;
	[Static]
	[NullAllowed, Export ("currentContext", ArgumentSemantic.Strong)]
	SessionContext CurrentContext { get; set; }

	// @property (readonly, nonatomic, strong) LRSession * _Nonnull session;
	[Export ("session", ArgumentSemantic.Strong)]
	LRSession Session { get; }

	// @property (readonly, nonatomic, strong) User * _Nonnull user;
	[Export ("user", ArgumentSemantic.Strong)]
	User User { get; }

	// @property (readonly, nonatomic, strong) CacheManager * _Nonnull cacheManager;
	[Export ("cacheManager", ArgumentSemantic.Strong)]
	CacheManager CacheManager { get; }

	// @property (nonatomic, strong) CredentialsStorage * _Nonnull credentialsStorage;
	[Export ("credentialsStorage", ArgumentSemantic.Strong)]
	CredentialsStorage CredentialsStorage { get; set; }

	// @property (readonly, nonatomic) int64_t userId;
	[Export ("userId")]
	long UserId { get; }

	// @property (readonly, copy, nonatomic) NSDictionary<NSString *,id> * _Nonnull userAttributes;
	[Export ("userAttributes", ArgumentSemantic.Copy)]
	NSDictionary<NSString, NSObject> UserAttributes { get; }

	// -(instancetype _Nonnull)initWithSession:(LRSession * _Nonnull)session attributes:(NSDictionary<NSString *,id> * _Nonnull)attributes store:(id<CredentialsStore> _Nonnull)store __attribute__((objc_designated_initializer));
	[Export ("initWithSession:attributes:store:")]
	[DesignatedInitializer]
	IntPtr Constructor (LRSession session, NSDictionary<NSString, NSObject> attributes, CredentialsStore store);

	// @property (readonly, nonatomic, class) BOOL isLoggedIn;
	[Static]
	[Export ("isLoggedIn")]
	bool IsLoggedIn { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nullable basicAuthUsername;
	[NullAllowed, Export ("basicAuthUsername")]
	string BasicAuthUsername { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nullable basicAuthPassword;
	[NullAllowed, Export ("basicAuthPassword")]
	string BasicAuthPassword { get; }

	// +(LRSession * _Nonnull)createEphemeralBasicSession:(NSString * _Nonnull)userName :(NSString * _Nonnull)password __attribute__((warn_unused_result));
	[Static]
	[Export ("createEphemeralBasicSession::")]
	LRSession CreateEphemeralBasicSession (string userName, string password);

	// +(LRSession * _Nonnull)loginWithBasicWithUsername:(NSString * _Nonnull)username password:(NSString * _Nonnull)password userAttributes:(NSDictionary<NSString *,id> * _Nonnull)userAttributes;
	[Static]
	[Export ("loginWithBasicWithUsername:password:userAttributes:")]
	LRSession LoginWithBasicWithUsername (string username, string password, NSDictionary<NSString, NSObject> userAttributes);

	// +(LRSession * _Nonnull)loginWithOAuthWithAuthentication:(LROAuth * _Nonnull)authentication userAttributes:(NSDictionary<NSString *,id> * _Nonnull)userAttributes;
	[Static]
	[Export ("loginWithOAuthWithAuthentication:userAttributes:")]
	LRSession LoginWithOAuthWithAuthentication (LROAuth authentication, NSDictionary<NSString, NSObject> userAttributes);

	// +(LRSession * _Nonnull)loginWithCookieWithAuthentication:(LRCookieAuthentication * _Nonnull)authentication userAttributes:(NSDictionary<NSString *,id> * _Nonnull)userAttributes;
	[Static]
	[Export ("loginWithCookieWithAuthentication:userAttributes:")]
	LRSession LoginWithCookieWithAuthentication (LRCookieAuthentication authentication, NSDictionary<NSString, NSObject> userAttributes);

	// +(void)reloadCookieAuthWithSession:(LRSession * _Nullable)session callback:(LRCookieBlockCallback * _Nonnull)callback;
	[Static]
	[Export ("reloadCookieAuthWithSession:callback:")]
	void ReloadCookieAuthWithSession ([NullAllowed] LRSession session, LRCookieBlockCallback callback);

	// -(LRSession * _Nonnull)createRequestSession __attribute__((warn_unused_result));
	[Export ("createRequestSession")]
	[Verify (MethodToProperty)]
	LRSession CreateRequestSession { get; }

	// -(BOOL)relogin:(void (^ _Nullable)(NSDictionary<NSString *,id> * _Nullable))completed __attribute__((warn_unused_result));
	[Export ("relogin:")]
	bool Relogin ([NullAllowed] Action<NSDictionary<NSString, NSObject>> completed);

	// -(BOOL)reloginBasic:(void (^ _Nullable)(NSDictionary<NSString *,id> * _Nullable))completed __attribute__((warn_unused_result));
	[Export ("reloginBasic:")]
	bool ReloginBasic ([NullAllowed] Action<NSDictionary<NSString, NSObject>> completed);

	// -(BOOL)reloginOAuth:(void (^ _Nullable)(NSDictionary<NSString *,id> * _Nullable))completed __attribute__((warn_unused_result));
	[Export ("reloginOAuth:")]
	bool ReloginOAuth ([NullAllowed] Action<NSDictionary<NSString, NSObject>> completed);

	// -(BOOL)refreshUserAttributes:(void (^ _Nullable)(NSDictionary<NSString *,id> * _Nullable))completed __attribute__((warn_unused_result));
	[Export ("refreshUserAttributes:")]
	bool RefreshUserAttributes ([NullAllowed] Action<NSDictionary<NSString, NSObject>> completed);

	// +(void)logout;
	[Static]
	[Export ("logout")]
	void Logout ();

	// -(BOOL)storeCredentials;
	[Export ("storeCredentials")]
	[Verify (MethodToProperty)]
	bool StoreCredentials { get; }

	// -(BOOL)removeStoredCredentials;
	[Export ("removeStoredCredentials")]
	[Verify (MethodToProperty)]
	bool RemoveStoredCredentials { get; }

	// +(BOOL)loadStoredCredentials;
	[Static]
	[Export ("loadStoredCredentials")]
	[Verify (MethodToProperty)]
	bool LoadStoredCredentials { get; }

	// +(BOOL)loadStoredCredentials:(CredentialsStorage * _Nonnull)storage __attribute__((warn_unused_result));
	[Static]
	[Export ("loadStoredCredentials:")]
	bool LoadStoredCredentials (CredentialsStorage storage);

	// +(LRSession * _Nullable)createSessionFromCurrentSession __attribute__((warn_unused_result));
	[Static]
	[NullAllowed, Export ("createSessionFromCurrentSession")]
	[Verify (MethodToProperty)]
	LRSession CreateSessionFromCurrentSession { get; }
}

// @interface SignUpScreenlet : BaseScreenlet <AnonymousBasicAuthType>
[BaseType (typeof(BaseScreenlet))]
interface SignUpScreenlet : IAnonymousBasicAuthType
{
	// @property (copy, nonatomic) NSString * _Nullable anonymousApiUserName;
	[NullAllowed, Export ("anonymousApiUserName")]
	string AnonymousApiUserName { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable anonymousApiPassword;
	[NullAllowed, Export ("anonymousApiPassword")]
	string AnonymousApiPassword { get; set; }

	// @property (nonatomic) BOOL autoLogin;
	[Export ("autoLogin")]
	bool AutoLogin { get; set; }

	// @property (nonatomic) BOOL saveCredentials;
	[Export ("saveCredentials")]
	bool SaveCredentials { get; set; }

	// @property (nonatomic) int64_t companyId;
	[Export ("companyId")]
	long CompanyId { get; set; }

	[Wrap ("WeakAutoLoginDelegate")]
	[NullAllowed]
	LoginScreenletDelegate AutoLoginDelegate { get; set; }

	// @property (nonatomic, weak) id<LoginScreenletDelegate> _Nullable autoLoginDelegate __attribute__((iboutlet));
	[NullAllowed, Export ("autoLoginDelegate", ArgumentSemantic.Weak)]
	NSObject WeakAutoLoginDelegate { get; set; }

	[Wrap ("WeakSignUpDelegate")]
	[NullAllowed]
	SignUpScreenletDelegate SignUpDelegate { get; }

	// @property (readonly, nonatomic, strong) id<SignUpScreenletDelegate> _Nullable signUpDelegate;
	[NullAllowed, Export ("signUpDelegate", ArgumentSemantic.Strong)]
	NSObject WeakSignUpDelegate { get; }

	// @property (readonly, nonatomic, strong) id<SignUpViewModel> _Nonnull viewModel;
	[Export ("viewModel", ArgumentSemantic.Strong)]
	SignUpViewModel ViewModel { get; }

	// -(Interactor * _Nullable)createInteractorWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender __attribute__((warn_unused_result));
	[Export ("createInteractorWithName:sender:")]
	[return: NullAllowed]
	Interactor CreateInteractorWithName (string name, [NullAllowed] NSObject sender);

	// -(BOOL)loadCurrentUser __attribute__((warn_unused_result));
	[Export ("loadCurrentUser")]
	[Verify (MethodToProperty)]
	bool LoadCurrentUser { get; }

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:themeName:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame, [NullAllowed] string themeName);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @protocol SignUpScreenletDelegate <BaseScreenletDelegate>
[Protocol, Model]
interface SignUpScreenletDelegate : IBaseScreenletDelegate
{
	// @optional -(void)screenlet:(SignUpScreenlet * _Nonnull)screenlet onSignUpResponseUserAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes;
	[Export ("screenlet:onSignUpResponseUserAttributes:")]
	void OnSignUpResponseUserAttributes (SignUpScreenlet screenlet, NSDictionary<NSString, NSObject> attributes);

	// @optional -(void)screenlet:(SignUpScreenlet * _Nonnull)screenlet onSignUpError:(NSError * _Nonnull)error;
	[Export ("screenlet:onSignUpError:")]
	void OnSignUpError (SignUpScreenlet screenlet, NSError error);
}

// @protocol SignUpViewModel
[Protocol, Model]
interface SignUpViewModel
{
	// @required @property (copy, nonatomic) NSString * _Nullable emailAddress;
	[Abstract]
	[NullAllowed, Export ("emailAddress")]
	string EmailAddress { get; set; }

	// @required @property (copy, nonatomic) NSString * _Nullable screenName;
	[Abstract]
	[NullAllowed, Export ("screenName")]
	string ScreenName { get; set; }

	// @required @property (copy, nonatomic) NSString * _Nullable password;
	[Abstract]
	[NullAllowed, Export ("password")]
	string Password { get; set; }

	// @required @property (copy, nonatomic) NSString * _Nullable firstName;
	[Abstract]
	[NullAllowed, Export ("firstName")]
	string FirstName { get; set; }

	// @required @property (copy, nonatomic) NSString * _Nullable middleName;
	[Abstract]
	[NullAllowed, Export ("middleName")]
	string MiddleName { get; set; }

	// @required @property (copy, nonatomic) NSString * _Nullable lastName;
	[Abstract]
	[NullAllowed, Export ("lastName")]
	string LastName { get; set; }

	// @required @property (copy, nonatomic) NSString * _Nullable jobTitle;
	[Abstract]
	[NullAllowed, Export ("jobTitle")]
	string JobTitle { get; set; }

	// @required @property (nonatomic) BOOL editCurrentUser;
	[Abstract]
	[Export ("editCurrentUser")]
	bool EditCurrentUser { get; set; }
}

// @interface SignUpView_default : BaseScreenletView <SignUpViewModel>
[BaseType (typeof(BaseScreenletView))]
interface SignUpView_default : ISignUpViewModel
{
	// @property (nonatomic, strong) UITextField * _Nullable emailAddressField __attribute__((iboutlet));
	[NullAllowed, Export ("emailAddressField", ArgumentSemantic.Strong)]
	UITextField EmailAddressField { get; set; }

	// @property (nonatomic, strong) UITextField * _Nullable passwordField __attribute__((iboutlet));
	[NullAllowed, Export ("passwordField", ArgumentSemantic.Strong)]
	UITextField PasswordField { get; set; }

	// @property (nonatomic, strong) UITextField * _Nullable firstNameField __attribute__((iboutlet));
	[NullAllowed, Export ("firstNameField", ArgumentSemantic.Strong)]
	UITextField FirstNameField { get; set; }

	// @property (nonatomic, strong) UITextField * _Nullable lastNameField __attribute__((iboutlet));
	[NullAllowed, Export ("lastNameField", ArgumentSemantic.Strong)]
	UITextField LastNameField { get; set; }

	// @property (nonatomic, strong) UIButton * _Nullable signUpButton __attribute__((iboutlet));
	[NullAllowed, Export ("signUpButton", ArgumentSemantic.Strong)]
	UIButton SignUpButton { get; set; }

	// @property (nonatomic, strong) UIScrollView * _Nullable scrollView __attribute__((iboutlet));
	[NullAllowed, Export ("scrollView", ArgumentSemantic.Strong)]
	UIScrollView ScrollView { get; set; }

	// -(void)onStartInteraction;
	[Export ("onStartInteraction")]
	void OnStartInteraction ();

	// -(void)onFinishInteraction:(id _Nullable)result error:(NSError * _Nullable)error;
	[Export ("onFinishInteraction:error:")]
	void OnFinishInteraction ([NullAllowed] NSObject result, [NullAllowed] NSError error);

	// -(void)onCreated;
	[Export ("onCreated")]
	void OnCreated ();

	// -(void)onSetTranslations;
	[Export ("onSetTranslations")]
	void OnSetTranslations ();

	// -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
	[Export ("createProgressPresenter")]
	[Verify (MethodToProperty)]
	ProgressPresenter CreateProgressPresenter { get; }

	// @property (copy, nonatomic) NSString * _Nullable emailAddress;
	[NullAllowed, Export ("emailAddress")]
	string EmailAddress { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable password;
	[NullAllowed, Export ("password")]
	string Password { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable firstName;
	[NullAllowed, Export ("firstName")]
	string FirstName { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable lastName;
	[NullAllowed, Export ("lastName")]
	string LastName { get; set; }

	// @property (nonatomic) BOOL editCurrentUser;
	[Export ("editCurrentUser")]
	bool EditCurrentUser { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable screenName;
	[NullAllowed, Export ("screenName")]
	string ScreenName { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable middleName;
	[NullAllowed, Export ("middleName")]
	string MiddleName { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable jobTitle;
	[NullAllowed, Export ("jobTitle")]
	string JobTitle { get; set; }

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface SlideShowLayout : UICollectionViewFlowLayout
[BaseType (typeof(UICollectionViewFlowLayout))]
[DisableDefaultCtor]
interface SlideShowLayout
{
	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);

	// -(NSArray<UICollectionViewLayoutAttributes *> * _Nullable)layoutAttributesForElementsInRect:(CGRect)rect __attribute__((warn_unused_result));
	[Export ("layoutAttributesForElementsInRect:")]
	[return: NullAllowed]
	UICollectionViewLayoutAttributes[] LayoutAttributesForElementsInRect (CGRect rect);

	// -(BOOL)shouldInvalidateLayoutForBoundsChange:(CGRect)newBounds __attribute__((warn_unused_result));
	[Export ("shouldInvalidateLayoutForBoundsChange:")]
	bool ShouldInvalidateLayoutForBoundsChange (CGRect newBounds);

	// -(UICollectionViewLayoutAttributes * _Nullable)layoutAttributesForItemAtIndexPath:(NSIndexPath * _Nonnull)indexPath __attribute__((warn_unused_result));
	[Export ("layoutAttributesForItemAtIndexPath:")]
	[return: NullAllowed]
	UICollectionViewLayoutAttributes LayoutAttributesForItemAtIndexPath (NSIndexPath indexPath);

	// -(CGPoint)targetContentOffsetForProposedContentOffset:(CGPoint)proposedContentOffset withScrollingVelocity:(CGPoint)velocity __attribute__((warn_unused_result));
	[Export ("targetContentOffsetForProposedContentOffset:withScrollingVelocity:")]
	CGPoint TargetContentOffsetForProposedContentOffset (CGPoint proposedContentOffset, CGPoint velocity);
}

// @interface SyncManager : NSObject
[BaseType (typeof(NSObject))]
[DisableDefaultCtor]
interface SyncManager
{
	[Wrap ("WeakDelegate")]
	[NullAllowed]
	SyncManagerDelegate Delegate { get; set; }

	// @property (nonatomic, weak) id<SyncManagerDelegate> _Nullable delegate;
	[NullAllowed, Export ("delegate", ArgumentSemantic.Weak)]
	NSObject WeakDelegate { get; set; }

	// @property (readonly, nonatomic, strong) CacheManager * _Nonnull cacheManager;
	[Export ("cacheManager", ArgumentSemantic.Strong)]
	CacheManager CacheManager { get; }

	// -(instancetype _Nonnull)initWithCacheManager:(CacheManager * _Nonnull)cacheManager __attribute__((objc_designated_initializer));
	[Export ("initWithCacheManager:")]
	[DesignatedInitializer]
	IntPtr Constructor (CacheManager cacheManager);

	// -(void)addSynchronizer:(Class _Nonnull)screenletClass synchronizer:(void (^ _Nonnull (^ _Nonnull)(NSString * _Nonnull, NSDictionary<NSString *,id> * _Nonnull))(void (^ _Nonnull)(void)))synchronizer;
	[Export ("addSynchronizer:synchronizer:")]
	void AddSynchronizer (Class screenletClass, Func<NSString, NSDictionary<NSString, NSObject>, Action<Action>> synchronizer);

	// -(void)addSynchronizerWithName:(NSString * _Nonnull)screenletClassName synchronizer:(void (^ _Nonnull (^ _Nonnull)(NSString * _Nonnull, NSDictionary<NSString *,id> * _Nonnull))(void (^ _Nonnull)(void)))synchronizer;
	[Export ("addSynchronizerWithName:synchronizer:")]
	void AddSynchronizerWithName (string screenletClassName, Func<NSString, NSDictionary<NSString, NSObject>, Action<Action>> synchronizer);

	// -(void)clear;
	[Export ("clear")]
	void Clear ();

	// -(void)startSync;
	[Export ("startSync")]
	void StartSync ();

	// -(void)prepareInteractorForSync:(ServerConnectorInteractor * _Nonnull)interactor key:(NSString * _Nonnull)key attributes:(NSDictionary<NSString *,id> * _Nonnull)attributes signal:(void (^ _Nonnull)(void))signal screenletClassName:(NSString * _Nonnull)screenletClassName;
	[Export ("prepareInteractorForSync:key:attributes:signal:screenletClassName:")]
	void PrepareInteractorForSync (ServerConnectorInteractor interactor, string key, NSDictionary<NSString, NSObject> attributes, Action signal, string screenletClassName);
}

// @interface LiferayScreens_Swift_4552 (SyncManager)
[Category]
[BaseType (typeof(SyncManager))]
interface SyncManager_LiferayScreens_Swift_4552
{
}

// @interface LiferayScreens_Swift_4556 (SyncManager)
[Category]
[BaseType (typeof(SyncManager))]
interface SyncManager_LiferayScreens_Swift_4556
{
}

// @interface LiferayScreens_Swift_4560 (SyncManager)
[Category]
[BaseType (typeof(SyncManager))]
interface SyncManager_LiferayScreens_Swift_4560
{
}

// @interface LiferayScreens_Swift_4564 (SyncManager)
[Category]
[BaseType (typeof(SyncManager))]
interface SyncManager_LiferayScreens_Swift_4564
{
}

// @interface LiferayScreens_Swift_4568 (SyncManager)
[Category]
[BaseType (typeof(SyncManager))]
interface SyncManager_LiferayScreens_Swift_4568
{
}

// @protocol SyncManagerDelegate
[Protocol, Model]
interface SyncManagerDelegate
{
	// @optional -(void)syncManager:(SyncManager * _Nonnull)manager itemsCount:(NSUInteger)itemsCount;
	[Export ("syncManager:itemsCount:")]
	void ItemsCount (SyncManager manager, nuint itemsCount);

	// @optional -(void)syncManager:(SyncManager * _Nonnull)manager onItemSyncScreenlet:(NSString * _Nonnull)screenlet startKey:(NSString * _Nonnull)startKey attributes:(NSDictionary<NSString *,id> * _Nonnull)attributes;
	[Export ("syncManager:onItemSyncScreenlet:startKey:attributes:")]
	void OnItemSyncScreenlet (SyncManager manager, string screenlet, string startKey, NSDictionary<NSString, NSObject> attributes);

	// @optional -(void)syncManager:(SyncManager * _Nonnull)manager onItemSyncScreenlet:(NSString * _Nonnull)screenlet completedKey:(NSString * _Nonnull)completedKey attributes:(NSDictionary<NSString *,id> * _Nonnull)attributes;
	[Export ("syncManager:onItemSyncScreenlet:completedKey:attributes:")]
	void OnItemSyncScreenlet (SyncManager manager, string screenlet, string completedKey, NSDictionary<NSString, NSObject> attributes);

	// @optional -(void)syncManager:(SyncManager * _Nonnull)manager onItemSyncScreenlet:(NSString * _Nonnull)screenlet failedKey:(NSString * _Nonnull)failedKey attributes:(NSDictionary<NSString *,id> * _Nonnull)attributes error:(NSError * _Nonnull)error;
	[Export ("syncManager:onItemSyncScreenlet:failedKey:attributes:error:")]
	void OnItemSyncScreenlet (SyncManager manager, string screenlet, string failedKey, NSDictionary<NSString, NSObject> attributes, NSError error);

	// @optional -(void)syncManager:(SyncManager * _Nonnull)manager onItemSyncScreenlet:(NSString * _Nonnull)screenlet conflictedKey:(NSString * _Nonnull)conflictedKey remoteValue:(id _Nonnull)remoteValue localValue:(id _Nonnull)localValue resolve:(void (^ _Nonnull)(enum SyncConflictResolution))resolve;
	[Export ("syncManager:onItemSyncScreenlet:conflictedKey:remoteValue:localValue:resolve:")]
	void OnItemSyncScreenlet (SyncManager manager, string screenlet, string conflictedKey, NSObject remoteValue, NSObject localValue, Action<SyncConflictResolution> resolve);
}

// @interface LiferayScreens_Swift_4583 (UIButton)
[Category]
[BaseType (typeof(UIButton))]
interface UIButton_LiferayScreens_Swift_4583
{
	// -(void)replaceAttributedTitle:(NSString * _Nonnull)title forState:(UIControlState)state;
	[Export ("replaceAttributedTitle:forState:")]
	void ReplaceAttributedTitle (string title, UIControlState state);
}

// @interface LiferayScreens_Swift_4588 (UIImage)
[Category]
[BaseType (typeof(UIImage))]
interface UIImage_LiferayScreens_Swift_4588
{
	// -(void)resizeImageToWidth:(NSInteger)width completion:(void (^ _Nonnull)(UIImage * _Nullable))completion;
	[Export ("resizeImageToWidth:completion:")]
	void ResizeImageToWidth (nint width, Action<UIImage> completion);

	// -(UIImage * _Nullable)resizeImageToWidth:(NSInteger)width __attribute__((warn_unused_result));
	[Export ("resizeImageToWidth:")]
	[return: NullAllowed]
	UIImage ResizeImageToWidth (nint width);
}

// @interface LiferayScreens_Swift_4594 (UIImageView)
[Category]
[BaseType (typeof(UIImageView))]
interface UIImageView_LiferayScreens_Swift_4594
{
	// @property (readonly, copy, nonatomic) NSURL * _Nullable lr_webURL;
	[NullAllowed, Export ("lr_webURL", ArgumentSemantic.Copy)]
	NSUrl Lr_webURL { get; }
}

// @interface LiferayScreens_Swift_4599 (UIView)
[Category]
[BaseType (typeof(UIView))]
interface UIView_LiferayScreens_Swift_4599
{
	// -(void)changeVisibilityWithVisible:(BOOL)visible;
	[Export ("changeVisibilityWithVisible:")]
	void ChangeVisibilityWithVisible (bool visible);

	// -(void)changeVisibilityWithVisible:(BOOL)visible delay:(double)delay;
	[Export ("changeVisibilityWithVisible:delay:")]
	void ChangeVisibilityWithVisible (bool visible, double delay);
}

// @interface UpdateRatingInteractor : ServerWriteConnectorInteractor
[BaseType (typeof(ServerWriteConnectorInteractor))]
interface UpdateRatingInteractor
{
	// -(ServerConnector * _Nullable)createConnector __attribute__((warn_unused_result));
	[NullAllowed, Export ("createConnector")]
	[Verify (MethodToProperty)]
	ServerConnector CreateConnector { get; }

	// -(void)completedConnector:(ServerConnector * _Nonnull)c;
	[Export ("completedConnector:")]
	void CompletedConnector (ServerConnector c);

	// -(void)writeToCache:(ServerConnector * _Nonnull)c;
	[Export ("writeToCache:")]
	void WriteToCache (ServerConnector c);

	// -(void)callOnSuccess;
	[Export ("callOnSuccess")]
	void CallOnSuccess ();
}

// @interface UploadProgressView_default : UIView
[BaseType (typeof(UIView))]
interface UploadProgressView_default
{
	// @property (copy, nonatomic) void (^ _Nullable)(void) cancelClosure;
	[NullAllowed, Export ("cancelClosure", ArgumentSemantic.Copy)]
	Action CancelClosure { get; set; }

	// -(void)awakeFromNib __attribute__((objc_requires_super));
	[Export ("awakeFromNib")]
	[RequiresSuper]
	void AwakeFromNib ();

	// -(void)addShadow;
	[Export ("addShadow")]
	void AddShadow ();

	// -(void)setProgress:(float)progress;
	[Export ("setProgress:")]
	void SetProgress (float progress);

	// -(void)addUpload:(UIImage * _Nullable)thumbnail;
	[Export ("addUpload:")]
	void AddUpload ([NullAllowed] UIImage thumbnail);

	// -(void)uploadComplete;
	[Export ("uploadComplete")]
	void UploadComplete ();

	// -(void)uploadError;
	[Export ("uploadError")]
	void UploadError ();

	// -(void)hide;
	[Export ("hide")]
	void Hide ();

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface User : NSObject <NSCoding>
[BaseType (typeof(NSObject))]
[DisableDefaultCtor]
interface User : INSCoding
{
	// @property (readonly, copy, nonatomic) NSDictionary<NSString *,id> * _Nonnull attributes;
	[Export ("attributes", ArgumentSemantic.Copy)]
	NSDictionary<NSString, NSObject> Attributes { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull firstName;
	[Export ("firstName")]
	string FirstName { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull lastName;
	[Export ("lastName")]
	string LastName { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull middleName;
	[Export ("middleName")]
	string MiddleName { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull screenName;
	[Export ("screenName")]
	string ScreenName { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull greeting;
	[Export ("greeting")]
	string Greeting { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull jobTitle;
	[Export ("jobTitle")]
	string JobTitle { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull email;
	[Export ("email")]
	string Email { get; }

	// @property (readonly, nonatomic) int64_t userId;
	[Export ("userId")]
	long UserId { get; }

	// -(instancetype _Nonnull)initWithAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes __attribute__((objc_designated_initializer));
	[Export ("initWithAttributes:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSDictionary<NSString, NSObject> attributes);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);

	// -(int64_t)int64Attribute:(NSString * _Nonnull)key __attribute__((warn_unused_result));
	[Export ("int64Attribute:")]
	long Int64Attribute (string key);

	// -(NSString * _Nonnull)stringAttribute:(NSString * _Nonnull)key __attribute__((warn_unused_result));
	[Export ("stringAttribute:")]
	string StringAttribute (string key);

	// -(void)encodeWithCoder:(NSCoder * _Nonnull)aCoder;
	[Export ("encodeWithCoder:")]
	void EncodeWithCoder (NSCoder aCoder);
}

// @interface UserPortraitDefaultProgressPresenter : DefaultProgressPresenter
[BaseType (typeof(DefaultProgressPresenter))]
[DisableDefaultCtor]
interface UserPortraitDefaultProgressPresenter
{
	// -(instancetype _Nonnull)initWithSpinner:(UIActivityIndicatorView * _Nonnull)spinner __attribute__((objc_designated_initializer));
	[Export ("initWithSpinner:")]
	[DesignatedInitializer]
	IntPtr Constructor (UIActivityIndicatorView spinner);

	// -(void)hideHUDFromView:(UIView * _Nullable)view message:(NSString * _Nullable)message forInteractor:(Interactor * _Nonnull)interactor withError:(NSError * _Nullable)error;
	[Export ("hideHUDFromView:message:forInteractor:withError:")]
	void HideHUDFromView ([NullAllowed] UIView view, [NullAllowed] string message, Interactor interactor, [NullAllowed] NSError error);

	// -(void)showHUDInView:(UIView * _Nonnull)view message:(NSString * _Nullable)message forInteractor:(Interactor * _Nonnull)interactor;
	[Export ("showHUDInView:message:forInteractor:")]
	void ShowHUDInView (UIView view, [NullAllowed] string message, Interactor interactor);
}

// @interface UserPortraitScreenlet : BaseScreenlet
[BaseType (typeof(BaseScreenlet))]
interface UserPortraitScreenlet
{
	// @property (readonly, copy, nonatomic, class) NSString * _Nonnull LoadPortrait;
	[Static]
	[Export ("LoadPortrait")]
	string LoadPortrait { get; }

	// @property (readonly, copy, nonatomic, class) NSString * _Nonnull UploadPortrait;
	[Static]
	[Export ("UploadPortrait")]
	string UploadPortrait { get; }

	// @property (nonatomic) CGFloat borderWidth;
	[Export ("borderWidth")]
	nfloat BorderWidth { get; set; }

	// @property (nonatomic, strong) UIColor * _Nullable borderColor;
	[NullAllowed, Export ("borderColor", ArgumentSemantic.Strong)]
	UIColor BorderColor { get; set; }

	// @property (nonatomic) BOOL editable;
	[Export ("editable")]
	bool Editable { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable offlinePolicy;
	[NullAllowed, Export ("offlinePolicy")]
	string OfflinePolicy { get; set; }

	[Wrap ("WeakUserPortraitDelegate")]
	[NullAllowed]
	UserPortraitScreenletDelegate UserPortraitDelegate { get; }

	// @property (readonly, nonatomic, strong) id<UserPortraitScreenletDelegate> _Nullable userPortraitDelegate;
	[NullAllowed, Export ("userPortraitDelegate", ArgumentSemantic.Strong)]
	NSObject WeakUserPortraitDelegate { get; }

	// @property (readonly, nonatomic, strong) id<UserPortraitViewModel> _Nonnull viewModel;
	[Export ("viewModel", ArgumentSemantic.Strong)]
	UserPortraitViewModel ViewModel { get; }

	// -(void)onCreated;
	[Export ("onCreated")]
	void OnCreated ();

	// -(Interactor * _Nullable)createInteractorWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender __attribute__((warn_unused_result));
	[Export ("createInteractorWithName:sender:")]
	[return: NullAllowed]
	Interactor CreateInteractorWithName (string name, [NullAllowed] NSObject sender);

	// -(BOOL)loadLoggedUserPortrait __attribute__((warn_unused_result));
	[Export ("loadLoggedUserPortrait")]
	[Verify (MethodToProperty)]
	bool LoadLoggedUserPortrait { get; }

	// -(BOOL)loadWithPortraitId:(int64_t)portraitId uuid:(NSString * _Nonnull)uuid male:(BOOL)male;
	[Export ("loadWithPortraitId:uuid:male:")]
	bool LoadWithPortraitId (long portraitId, string uuid, bool male);

	// -(BOOL)loadWithUserId:(int64_t)userId;
	[Export ("loadWithUserId:")]
	bool LoadWithUserId (long userId);

	// -(BOOL)loadWithCompanyId:(int64_t)companyId emailAddress:(NSString * _Nonnull)emailAddress;
	[Export ("loadWithCompanyId:emailAddress:")]
	bool LoadWithCompanyId (long companyId, string emailAddress);

	// -(BOOL)loadWithCompanyId:(int64_t)companyId screenName:(NSString * _Nonnull)screenName;
	[Export ("loadWithCompanyId:screenName:")]
	bool LoadWithCompanyId (long companyId, string screenName);

	// -(void)loadPlaceholder;
	[Export ("loadPlaceholder")]
	void LoadPlaceholder ();

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:themeName:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame, [NullAllowed] string themeName);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @protocol UserPortraitScreenletDelegate <BaseScreenletDelegate>
[Protocol, Model]
interface UserPortraitScreenletDelegate : IBaseScreenletDelegate
{
	// @optional -(UIImage * _Nonnull)screenlet:(UserPortraitScreenlet * _Nonnull)screenlet onUserPortraitResponseImage:(UIImage * _Nonnull)image __attribute__((warn_unused_result));
	[Export ("screenlet:onUserPortraitResponseImage:")]
	UIImage Screenlet (UserPortraitScreenlet screenlet, UIImage image);

	// @optional -(void)screenlet:(UserPortraitScreenlet * _Nonnull)screenlet onUserPortraitError:(NSError * _Nonnull)error;
	[Export ("screenlet:onUserPortraitError:")]
	void Screenlet (UserPortraitScreenlet screenlet, NSError error);

	// @optional -(void)screenlet:(UserPortraitScreenlet * _Nonnull)screenlet onUserPortraitUploaded:(NSDictionary<NSString *,id> * _Nonnull)attributes;
	[Export ("screenlet:onUserPortraitUploaded:")]
	void Screenlet (UserPortraitScreenlet screenlet, NSDictionary<NSString, NSObject> attributes);

	// @optional -(void)screenlet:(UserPortraitScreenlet * _Nonnull)screenlet onUserPortraitUploadError:(NSError * _Nonnull)error;
	[Export ("screenlet:onUserPortraitUploadError:")]
	void Screenlet (UserPortraitScreenlet screenlet, NSError error);
}

// @protocol UserPortraitViewModel
[Protocol, Model]
interface UserPortraitViewModel
{
	// @required @property (nonatomic, strong) UIImage * _Nullable image;
	[Abstract]
	[NullAllowed, Export ("image", ArgumentSemantic.Strong)]
	UIImage Image { get; set; }

	// @required @property (nonatomic) CGFloat borderWidth;
	[Abstract]
	[Export ("borderWidth")]
	nfloat BorderWidth { get; set; }

	// @required @property (nonatomic, strong) UIColor * _Nullable borderColor;
	[Abstract]
	[NullAllowed, Export ("borderColor", ArgumentSemantic.Strong)]
	UIColor BorderColor { get; set; }

	// @required -(void)loadPlaceholderFor:(User * _Nonnull)user;
	[Abstract]
	[Export ("loadPlaceholderFor:")]
	void LoadPlaceholderFor (User user);
}

// @interface UserPortraitView_default : BaseScreenletView <UserPortraitViewModel, UIActionSheetDelegate, UINavigationControllerDelegate, UIImagePickerControllerDelegate>
[BaseType (typeof(BaseScreenletView))]
interface UserPortraitView_default : IUserPortraitViewModel, IUIActionSheetDelegate, IUINavigationControllerDelegate, IUIImagePickerControllerDelegate
{
	// @property (nonatomic, strong, class) UIImage * _Nullable defaultPlaceholder;
	[Static]
	[NullAllowed, Export ("defaultPlaceholder", ArgumentSemantic.Strong)]
	UIImage DefaultPlaceholder { get; set; }

	// @property (nonatomic, weak) UIActivityIndicatorView * _Nullable activityIndicator __attribute__((iboutlet));
	[NullAllowed, Export ("activityIndicator", ArgumentSemantic.Weak)]
	UIActivityIndicatorView ActivityIndicator { get; set; }

	// @property (nonatomic, weak) UIImageView * _Nullable portraitImage __attribute__((iboutlet));
	[NullAllowed, Export ("portraitImage", ArgumentSemantic.Weak)]
	UIImageView PortraitImage { get; set; }

	// @property (nonatomic) BOOL editable;
	[Export ("editable")]
	bool Editable { get; set; }

	// @property (nonatomic, strong) UIImage * _Nullable image;
	[NullAllowed, Export ("image", ArgumentSemantic.Strong)]
	UIImage Image { get; set; }

	// @property (nonatomic) CGFloat borderWidth;
	[Export ("borderWidth")]
	nfloat BorderWidth { get; set; }

	// @property (nonatomic, strong) UIColor * _Nullable borderColor;
	[NullAllowed, Export ("borderColor", ArgumentSemantic.Strong)]
	UIColor BorderColor { get; set; }

	// -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
	[Export ("createProgressPresenter")]
	[Verify (MethodToProperty)]
	ProgressPresenter CreateProgressPresenter { get; }

	// -(void)onCreated;
	[Export ("onCreated")]
	void OnCreated ();

	// -(void)onShow;
	[Export ("onShow")]
	void OnShow ();

	// -(BOOL)onPreActionWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender __attribute__((warn_unused_result));
	[Export ("onPreActionWithName:sender:")]
	bool OnPreActionWithName (string name, [NullAllowed] NSObject sender);

	// -(void)loadPlaceholderFor:(User * _Nonnull)user;
	[Export ("loadPlaceholderFor:")]
	void LoadPlaceholderFor (User user);

	// -(void)loadDefaultPlaceholder;
	[Export ("loadDefaultPlaceholder")]
	void LoadDefaultPlaceholder ();

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface ValidationError : NSError
[BaseType (typeof(NSError))]
interface ValidationError
{
	// -(instancetype _Nonnull)init:(NSString * _Nonnull)message __attribute__((objc_designated_initializer));
	[Export ("init:")]
	[DesignatedInitializer]
	IntPtr Constructor (string message);

	// -(instancetype _Nonnull)init:(NSString * _Nonnull)key :(NSString * _Nonnull)message;
	[Export ("init::")]
	IntPtr Constructor (string key, string message);

	// -(instancetype _Nonnull)init:(NSString * _Nonnull)key :(NSString * _Nonnull)message :(id _Nonnull)bundleObject;
	[Export ("init:::")]
	IntPtr Constructor (string key, string message, NSObject bundleObject);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface VideoDisplayScreenlet : FileDisplayScreenlet
[BaseType (typeof(FileDisplayScreenlet))]
interface VideoDisplayScreenlet
{
	// @property (copy, nonatomic) NSString * _Nonnull mimeTypes;
	[Export ("mimeTypes")]
	string MimeTypes { get; set; }

	// @property (readonly, copy, nonatomic) NSArray<NSString *> * _Nonnull supportedMimeTypes;
	[Export ("supportedMimeTypes", ArgumentSemantic.Copy)]
	string[] SupportedMimeTypes { get; }

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:themeName:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame, [NullAllowed] string themeName);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface VideoDisplayView_default : BaseScreenletView <FileDisplayViewModel>
[BaseType (typeof(BaseScreenletView))]
interface VideoDisplayView_default : IFileDisplayViewModel
{
	// @property (nonatomic, strong) AVPlayerViewController * _Nullable playerController;
	[NullAllowed, Export ("playerController", ArgumentSemantic.Strong)]
	AVPlayerViewController PlayerController { get; set; }

	// @property (copy, nonatomic) NSURL * _Nullable url;
	[NullAllowed, Export ("url", ArgumentSemantic.Copy)]
	NSUrl Url { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable title;
	[NullAllowed, Export ("title")]
	string Title { get; set; }

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface LiferayScreens_Swift_4846 (WKWebView)
[Category]
[BaseType (typeof(WKWebView))]
interface WKWebView_LiferayScreens_Swift_4846
{
	// -(void)injectCookies;
	[Export ("injectCookies")]
	void InjectCookies ();

	// -(void)injectViewportMetaTag;
	[Export ("injectViewportMetaTag")]
	void InjectViewportMetaTag ();
}

// @interface LiferayScreens_Swift_4852 (WKWebViewConfiguration)
[Category]
[BaseType (typeof(WKWebViewConfiguration))]
interface WKWebViewConfiguration_LiferayScreens_Swift_4852
{
	// @property (readonly, nonatomic, strong, class) WKWebViewConfiguration * _Nonnull noCacheConfiguration;
	[Static]
	[Export ("noCacheConfiguration", ArgumentSemantic.Strong)]
	WKWebViewConfiguration NoCacheConfiguration { get; }
}

// @interface WebContent : Asset
[BaseType (typeof(Asset))]
interface WebContent
{
	// @property (readonly, nonatomic, strong) DDMStructure * _Nullable structure;
	[NullAllowed, Export ("structure", ArgumentSemantic.Strong)]
	DDMStructure Structure { get; }

	// @property (readonly, nonatomic, strong) DDLRecord * _Nullable structuredRecord;
	[NullAllowed, Export ("structuredRecord", ArgumentSemantic.Strong)]
	DDLRecord StructuredRecord { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nullable html;
	[NullAllowed, Export ("html")]
	string Html { get; }

	// @property (readonly, copy, nonatomic) NSString * _Nonnull debugDescription;
	[Export ("debugDescription")]
	string DebugDescription { get; }

	// +(BOOL)isWebContentClassName:(NSString * _Nonnull)className __attribute__((warn_unused_result));
	[Static]
	[Export ("isWebContentClassName:")]
	bool IsWebContentClassName (string className);

	// -(instancetype _Nonnull)initWithAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes __attribute__((objc_designated_initializer));
	[Export ("initWithAttributes:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSDictionary<NSString, NSObject> attributes);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);

	// -(void)encodeWithCoder:(NSCoder * _Nonnull)aCoder;
	[Export ("encodeWithCoder:")]
	void EncodeWithCoder (NSCoder aCoder);
}

// @interface WebContentDisplayScreenlet : BaseScreenlet
[BaseType (typeof(BaseScreenlet))]
interface WebContentDisplayScreenlet
{
	// @property (nonatomic) int64_t groupId;
	[Export ("groupId")]
	long GroupId { get; set; }

	// @property (copy, nonatomic) NSString * _Nonnull articleId;
	[Export ("articleId")]
	string ArticleId { get; set; }

	// @property (nonatomic) int64_t templateId;
	[Export ("templateId")]
	long TemplateId { get; set; }

	// @property (nonatomic) int64_t structureId;
	[Export ("structureId")]
	long StructureId { get; set; }

	// @property (nonatomic) BOOL autoLoad;
	[Export ("autoLoad")]
	bool AutoLoad { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable offlinePolicy;
	[NullAllowed, Export ("offlinePolicy")]
	string OfflinePolicy { get; set; }

	[Wrap ("WeakWebContentDisplayDelegate")]
	[NullAllowed]
	WebContentDisplayScreenletDelegate WebContentDisplayDelegate { get; }

	// @property (readonly, nonatomic, strong) id<WebContentDisplayScreenletDelegate> _Nullable webContentDisplayDelegate;
	[NullAllowed, Export ("webContentDisplayDelegate", ArgumentSemantic.Strong)]
	NSObject WeakWebContentDisplayDelegate { get; }

	// -(void)onShow;
	[Export ("onShow")]
	void OnShow ();

	// -(Interactor * _Nullable)createInteractorWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender __attribute__((warn_unused_result));
	[Export ("createInteractorWithName:sender:")]
	[return: NullAllowed]
	Interactor CreateInteractorWithName (string name, [NullAllowed] NSObject sender);

	// -(BOOL)loadWebContent;
	[Export ("loadWebContent")]
	[Verify (MethodToProperty)]
	bool LoadWebContent { get; }

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:themeName:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame, [NullAllowed] string themeName);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @protocol WebContentDisplayScreenletDelegate <BaseScreenletDelegate>
[Protocol, Model]
interface WebContentDisplayScreenletDelegate : IBaseScreenletDelegate
{
	// @optional -(NSString * _Nullable)screenlet:(WebContentDisplayScreenlet * _Nonnull)screenlet onWebContentResponse:(NSString * _Nonnull)html __attribute__((warn_unused_result));
	[Export ("screenlet:onWebContentResponse:")]
	[return: NullAllowed]
	string Screenlet (WebContentDisplayScreenlet screenlet, string html);

	// @optional -(void)screenlet:(WebContentDisplayScreenlet * _Nonnull)screenlet onRecordContentResponse:(DDLRecord * _Nonnull)record;
	[Export ("screenlet:onRecordContentResponse:")]
	void Screenlet (WebContentDisplayScreenlet screenlet, DDLRecord record);

	// @optional -(void)screenlet:(WebContentDisplayScreenlet * _Nonnull)screenlet onWebContentError:(NSError * _Nonnull)error;
	[Export ("screenlet:onWebContentError:")]
	void Screenlet (WebContentDisplayScreenlet screenlet, NSError error);
}

// @protocol WebContentDisplayViewModel
[Protocol, Model]
interface WebContentDisplayViewModel
{
	// @required @property (copy, nonatomic) NSString * _Nullable htmlContent;
	[Abstract]
	[NullAllowed, Export ("htmlContent")]
	string HtmlContent { get; set; }

	// @required @property (nonatomic, strong) DDLRecord * _Nullable recordContent;
	[Abstract]
	[NullAllowed, Export ("recordContent", ArgumentSemantic.Strong)]
	DDLRecord RecordContent { get; set; }
}

// @interface WebContentDisplayView_default : BaseScreenletView <WebContentDisplayViewModel>
[BaseType (typeof(BaseScreenletView))]
interface WebContentDisplayView_default : IWebContentDisplayViewModel
{
	// @property (nonatomic, strong) WKWebView * _Nullable webView;
	[NullAllowed, Export ("webView", ArgumentSemantic.Strong)]
	WKWebView WebView { get; set; }

	// -(void)onCreated;
	[Export ("onCreated")]
	void OnCreated ();

	// -(void)addWebView;
	[Export ("addWebView")]
	void AddWebView ();

	// -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
	[Export ("createProgressPresenter")]
	[Verify (MethodToProperty)]
	ProgressPresenter CreateProgressPresenter { get; }

	// @property (copy, nonatomic) NSString * _Nullable htmlContent;
	[NullAllowed, Export ("htmlContent")]
	string HtmlContent { get; set; }

	// @property (nonatomic, strong) DDLRecord * _Nullable recordContent;
	[NullAllowed, Export ("recordContent", ArgumentSemantic.Strong)]
	DDLRecord RecordContent { get; set; }

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface WebContentListPageLoadInteractor : BaseListPageLoadInteractor
[BaseType (typeof(BaseListPageLoadInteractor))]
interface WebContentListPageLoadInteractor
{
	// -(PaginationLiferayConnector * _Nonnull)createListPageConnector __attribute__((warn_unused_result));
	[Export ("createListPageConnector")]
	[Verify (MethodToProperty)]
	PaginationLiferayConnector CreateListPageConnector { get; }

	// -(id _Nonnull)convertResult:(NSDictionary<NSString *,id> * _Nonnull)serverResult __attribute__((warn_unused_result));
	[Export ("convertResult:")]
	NSObject ConvertResult (NSDictionary<NSString, NSObject> serverResult);

	// -(NSString * _Nonnull)cacheKey:(PaginationLiferayConnector * _Nonnull)c __attribute__((warn_unused_result));
	[Export ("cacheKey:")]
	string CacheKey (PaginationLiferayConnector c);
}

// @interface WebContentListScreenlet : BaseListScreenlet
[BaseType (typeof(BaseListScreenlet))]
interface WebContentListScreenlet
{
	// @property (nonatomic) int64_t groupId;
	[Export ("groupId")]
	long GroupId { get; set; }

	// @property (nonatomic) int64_t folderId;
	[Export ("folderId")]
	long FolderId { get; set; }

	// @property (copy, nonatomic) NSString * _Nullable offlinePolicy;
	[NullAllowed, Export ("offlinePolicy")]
	string OfflinePolicy { get; set; }

	[Wrap ("WeakWebContentListDelegate")]
	[NullAllowed]
	WebContentListScreenletDelegate WebContentListDelegate { get; }

	// @property (readonly, nonatomic, strong) id<WebContentListScreenletDelegate> _Nullable webContentListDelegate;
	[NullAllowed, Export ("webContentListDelegate", ArgumentSemantic.Strong)]
	NSObject WeakWebContentListDelegate { get; }

	// -(BaseListPageLoadInteractor * _Nonnull)createPageLoadInteractorWithPage:(NSInteger)page computeRowCount:(BOOL)computeRowCount __attribute__((warn_unused_result));
	[Export ("createPageLoadInteractorWithPage:computeRowCount:")]
	BaseListPageLoadInteractor CreatePageLoadInteractorWithPage (nint page, bool computeRowCount);

	// -(void)onLoadPageErrorWithPage:(NSInteger)page error:(NSError * _Nonnull)error;
	[Export ("onLoadPageErrorWithPage:error:")]
	void OnLoadPageErrorWithPage (nint page, NSError error);

	// -(void)onLoadPageResultWithPage:(NSInteger)page rows:(NSArray * _Nonnull)rows rowCount:(NSInteger)rowCount;
	[Export ("onLoadPageResultWithPage:rows:rowCount:")]
	[Verify (StronglyTypedNSArray)]
	void OnLoadPageResultWithPage (nint page, NSObject[] rows, nint rowCount);

	// -(void)onSelectedRow:(id _Nonnull)row;
	[Export ("onSelectedRow:")]
	void OnSelectedRow (NSObject row);

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:themeName:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame, [NullAllowed] string themeName);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @protocol WebContentListScreenletDelegate <BaseScreenletDelegate>
[Protocol, Model]
interface WebContentListScreenletDelegate : IBaseScreenletDelegate
{
	// @optional -(void)screenlet:(WebContentListScreenlet * _Nonnull)screenlet onWebContentListResponse:(NSArray<WebContent *> * _Nonnull)contents;
	[Export ("screenlet:onWebContentListResponse:")]
	void OnWebContentListResponse (WebContentListScreenlet screenlet, WebContent[] contents);

	// @optional -(void)screenlet:(WebContentListScreenlet * _Nonnull)screenlet onWebContentListError:(NSError * _Nonnull)error;
	[Export ("screenlet:onWebContentListError:")]
	void OnWebContentListError (WebContentListScreenlet screenlet, NSError error);

	// @optional -(void)screenlet:(WebContentListScreenlet * _Nonnull)screenlet onWebContentSelected:(WebContent * _Nonnull)content;
	[Export ("screenlet:onWebContentSelected:")]
	void OnWebContentSelected (WebContentListScreenlet screenlet, WebContent content);
}

// @interface WebContentListTableView : BaseListTableView
[BaseType (typeof(BaseListTableView))]
interface WebContentListTableView
{
	// -(void)doFillLoadedCellWithRow:(NSInteger)row cell:(UITableViewCell * _Nonnull)cell object:(id _Nonnull)object;
	[Export ("doFillLoadedCellWithRow:cell:object:")]
	void DoFillLoadedCellWithRow (nint row, UITableViewCell cell, NSObject @object);

	// -(void)doFillInProgressCellWithRow:(NSInteger)row cell:(UITableViewCell * _Nonnull)cell;
	[Export ("doFillInProgressCellWithRow:cell:")]
	void DoFillInProgressCellWithRow (nint row, UITableViewCell cell);

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface WebContentListView_default : WebContentListTableView
[BaseType (typeof(WebContentListTableView))]
interface WebContentListView_default
{
	// -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
	[Export ("createProgressPresenter")]
	[Verify (MethodToProperty)]
	ProgressPresenter CreateProgressPresenter { get; }

	// -(void)doFillLoadedCellWithRow:(NSInteger)row cell:(UITableViewCell * _Nonnull)cell object:(id _Nonnull)object;
	[Export ("doFillLoadedCellWithRow:cell:object:")]
	void DoFillLoadedCellWithRow (nint row, UITableViewCell cell, NSObject @object);

	// -(void)doFillInProgressCellWithRow:(NSInteger)row cell:(UITableViewCell * _Nonnull)cell;
	[Export ("doFillInProgressCellWithRow:cell:")]
	void DoFillInProgressCellWithRow (nint row, UITableViewCell cell);

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface WebContentListView_default_collection : BaseListCollectionView
[BaseType (typeof(BaseListCollectionView))]
interface WebContentListView_default_collection
{
	// -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
	[Export ("createProgressPresenter")]
	[Verify (MethodToProperty)]
	ProgressPresenter CreateProgressPresenter { get; }

	// -(void)doConfigureCollectionView:(UICollectionView * _Nonnull)collectionView;
	[Export ("doConfigureCollectionView:")]
	void DoConfigureCollectionView (UICollectionView collectionView);

	// -(UICollectionViewLayout * _Nonnull)doCreateLayout __attribute__((warn_unused_result));
	[Export ("doCreateLayout")]
	[Verify (MethodToProperty)]
	UICollectionViewLayout DoCreateLayout { get; }

	// -(void)doRegisterCellNibs;
	[Export ("doRegisterCellNibs")]
	void DoRegisterCellNibs ();

	// -(void)doFillLoadedCellWithIndexPath:(NSIndexPath * _Nonnull)indexPath cell:(UICollectionViewCell * _Nonnull)cell object:(id _Nonnull)object;
	[Export ("doFillLoadedCellWithIndexPath:cell:object:")]
	void DoFillLoadedCellWithIndexPath (NSIndexPath indexPath, UICollectionViewCell cell, NSObject @object);

	// -(void)doFillInProgressCellWithIndexPath:(NSIndexPath * _Nonnull)indexPath cell:(UICollectionViewCell * _Nonnull)cell;
	[Export ("doFillInProgressCellWithIndexPath:cell:")]
	void DoFillInProgressCellWithIndexPath (NSIndexPath indexPath, UICollectionViewCell cell);

	// -(NSString * _Nonnull)doGetCellIdWithIndexPath:(NSIndexPath * _Nonnull)indexPath object:(id _Nullable)object __attribute__((warn_unused_result));
	[Export ("doGetCellIdWithIndexPath:object:")]
	string DoGetCellIdWithIndexPath (NSIndexPath indexPath, [NullAllowed] NSObject @object);

	// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
	[Export ("initWithFrame:")]
	[DesignatedInitializer]
	IntPtr Constructor (CGRect frame);

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}

// @interface WebViewCell : UICollectionViewCell
[BaseType (typeof(UICollectionViewCell))]
interface WebViewCell
{
	// @property (copy, nonatomic) NSString * _Nonnull html;
	[Export ("html")]
	string Html { get; set; }

	// -(instancetype _Nullable)initWithCoder:(NSCoder * _Nonnull)aDecoder __attribute__((objc_designated_initializer));
	[Export ("initWithCoder:")]
	[DesignatedInitializer]
	IntPtr Constructor (NSCoder aDecoder);
}
