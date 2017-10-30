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

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
