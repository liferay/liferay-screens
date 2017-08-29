using System;
using ObjCRuntime;
using Foundation;
using UIKit;
using CoreGraphics;

namespace LiferayScreens
{
    // @interface BaseScreenletView : UIView <UITextFieldDelegate>
    [BaseType(typeof(UIView))]
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
        [Export("createProgressPresenter")]
        ProgressPresenter CreateProgressPresenter();

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
    }
}

