using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;
using UIKit;

namespace LiferayScreens
{
    // @interface DefaultTextField : UITextField
    [BaseType(typeof(UITextField))]
    interface DefaultTextField
    {
        // @property (nonatomic) CGFloat buttonMargin;
        [Export("buttonMargin")]
        nfloat ButtonMargin { get; set; }

        // @property (copy, nonatomic) void (^ _Nullable)(void) onRightButtonClick;
        [NullAllowed, Export("onRightButtonClick", ArgumentSemantic.Copy)]
        Action OnRightButtonClick { get; set; }

        // @property (nonatomic, strong) UIColor * _Nonnull defaultColor;
        [Export("defaultColor", ArgumentSemantic.Strong)]
        UIColor DefaultColor { get; set; }

        // @property (nonatomic, strong) UIColor * _Nonnull highlightColor;
        [Export("highlightColor", ArgumentSemantic.Strong)]
        UIColor HighlightColor { get; set; }

        // @property (nonatomic, strong) UIColor * _Nonnull errorColor;
        [Export("errorColor", ArgumentSemantic.Strong)]
        UIColor ErrorColor { get; set; }

        // @property (nonatomic) CGFloat paddingLeft;
        [Export("paddingLeft")]
        nfloat PaddingLeft { get; set; }

        // @property (nonatomic) CGFloat paddingRight;
        [Export("paddingRight")]
        nfloat PaddingRight { get; set; }

        // @property (nonatomic, strong) UIImage * _Nullable leftImage;
        [NullAllowed, Export("leftImage", ArgumentSemantic.Strong)]
        UIImage LeftImage { get; set; }

        // @property (nonatomic, strong) UIImage * _Nullable rightButtonImage;
        [NullAllowed, Export("rightButtonImage", ArgumentSemantic.Strong)]
        UIImage RightButtonImage { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable rightButtonTitle;
        [NullAllowed, Export("rightButtonTitle")]
        string RightButtonTitle { get; set; }

        // -(void)prepareForInterfaceBuilder;
        [Export("prepareForInterfaceBuilder")]
        void PrepareForInterfaceBuilder();

        // -(void)setDefaultState;
        [Export("setDefaultState")]
        void SetDefaultState();

        // -(void)setErrorState;
        [Export("setErrorState")]
        void SetErrorState();

        // -(BOOL)resignFirstResponder __attribute__((warn_unused_result));
        [Export("resignFirstResponder")]
        bool ResignFirstResponder();

        // -(BOOL)becomeFirstResponder __attribute__((warn_unused_result));
        [Export("becomeFirstResponder")]
        bool BecomeFirstResponder();

        // -(CGRect)textRectForBounds:(CGRect)bounds __attribute__((warn_unused_result));
        [Export("textRectForBounds:")]
        CGRect TextRectForBounds(CGRect bounds);

        // -(CGRect)editingRectForBounds:(CGRect)bounds __attribute__((warn_unused_result));
        [Export("editingRectForBounds:")]
        CGRect EditingRectForBounds(CGRect bounds);

        // -(CGRect)leftViewRectForBounds:(CGRect)bounds __attribute__((warn_unused_result));
        [Export("leftViewRectForBounds:")]
        CGRect LeftViewRectForBounds(CGRect bounds);

        // -(CGRect)rightViewRectForBounds:(CGRect)bounds __attribute__((warn_unused_result));
        [Export("rightViewRectForBounds:")]
        CGRect RightViewRectForBounds(CGRect bounds);
    }    
}
