using System;
using ObjCRuntime;
using Foundation;
using UIKit;
using CoreGraphics;

namespace LiferayScreens
{
    // @interface BaseScreenlet : UIView
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
        IBaseScreenletDelegate Delegate { get; set; }

        // @property (nonatomic, weak) id<BaseScreenletDelegate> _Nullable delegate __attribute__((iboutlet));
        [NullAllowed, Export("delegate", ArgumentSemantic.Weak)]
        IBaseScreenletDelegate WeakDelegate { get; set; }

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
}
