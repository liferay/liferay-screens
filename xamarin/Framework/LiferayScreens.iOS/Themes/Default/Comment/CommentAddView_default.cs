using CoreGraphics;
using Foundation;
using System;
using UIKit;

namespace LiferayScreens
{
    // @interface CommentAddView_default : BaseScreenletView <CommentAddViewModel>
    [BaseType(typeof(BaseScreenletView))]
    interface CommentAddView_default : ICommentAddViewModel
    {
        // @property (copy, nonatomic) NSString * _Nonnull body;
        [Export("body")]
        string Body { get; set; }

        // -(void)updateButton;
        [Export("updateButton")]
        void UpdateButton();

        // -(void)onShow;
        [Export("onShow")]
        void OnShow();

        // -(void)onSetTranslations;
        [Export("onSetTranslations")]
        void OnSetTranslations();

        // -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
        [Export("createProgressPresenter")]
        ProgressPresenter CreateProgressPresenter();

        // -(BOOL)textFieldShouldReturn:(UITextField * _Nonnull)textField __attribute__((warn_unused_result));
        [Export("textFieldShouldReturn:")]
        bool TextFieldShouldReturn(UITextField textField);

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
