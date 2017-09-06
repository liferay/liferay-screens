using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface CommentDisplayView_default : BaseScreenletView <CommentDisplayViewModel>
    [BaseType(typeof(BaseScreenletView))]
    interface CommentDisplayView_default : ICommentDisplayViewModel
    {
        // -(void)editComment;
        [Export("editComment")]
        void EditComment();

        // @property (nonatomic, strong) Comment * _Nullable comment;
        [NullAllowed, Export("comment", ArgumentSemantic.Strong)]
        Comment Comment { get; set; }

        // -(void)onSetTranslations;
        [Export("onSetTranslations")]
        void OnSetTranslations();

        // @property (nonatomic) BOOL editable;
        [Export("editable")]
        bool Editable { get; set; }

        // -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
        [Export("createProgressPresenter")]
        ProgressPresenter CreateProgressPresenter();

        // -(void)confirmBodyClosure:(NSString * _Nullable)body;
        [Export("confirmBodyClosure:")]
        void ConfirmBodyClosure([NullAllowed] string body);

        // +(CGFloat)heightForText:(NSString * _Nullable)text width:(CGFloat)width __attribute__((warn_unused_result));
        [Static]
        [Export("heightForText:width:")]
        nfloat HeightForText([NullAllowed] string text, nfloat width);

        // +(NSDictionary<NSString *,NSObject *> * _Nonnull)defaultAttributedTextAttributes __attribute__((warn_unused_result));
        [Static]
        [Export("defaultAttributedTextAttributes")]
        NSDictionary<NSString, NSObject> DefaultAttributedTextAttributes();

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
