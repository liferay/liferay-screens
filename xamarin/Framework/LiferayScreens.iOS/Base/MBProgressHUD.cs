using System;
using CoreFoundation;
using CoreGraphics;
using Foundation;
using ObjCRuntime;
using UIKit;

namespace LiferayScreens
{
    delegate void NSDispatchHandlerT();

    // typedef void (^MBProgressHUDCompletionBlock)();
    delegate void MBProgressHUDCompletionBlock();

    // @interface MBProgressHUD : UIView
    [BaseType(typeof(UIView))]
    interface MBProgressHUD
    {
        // +(instancetype)showHUDAddedTo:(UIView *)view animated:(BOOL)animated;
        [Static]
        [Export("showHUDAddedTo:animated:")]
        MBProgressHUD ShowHUDAddedTo(UIView view, bool animated);

        // +(BOOL)hideHUDForView:(UIView *)view animated:(BOOL)animated;
        [Static]
        [Export("hideHUDForView:animated:")]
        bool HideHUDForView(UIView view, bool animated);

        // +(NSUInteger)hideAllHUDsForView:(UIView *)view animated:(BOOL)animated;
        [Static]
        [Export("hideAllHUDsForView:animated:")]
        nuint HideAllHUDsForView(UIView view, bool animated);

        // +(instancetype)HUDForView:(UIView *)view;
        [Static]
        [Export("HUDForView:")]
        MBProgressHUD HUDForView(UIView view);

        // +(NSArray *)allHUDsForView:(UIView *)view;
        [Static]
        [Export("allHUDsForView:")]
        //[Verify(StronglyTypedNSArray)]
        NSObject[] AllHUDsForView(UIView view);

        // -(id)initWithWindow:(UIWindow *)window;
        [Export("initWithWindow:")]
        IntPtr Constructor(UIWindow window);

        // -(id)initWithView:(UIView *)view;
        [Export("initWithView:")]
        IntPtr Constructor(UIView view);

        // -(void)show:(BOOL)animated;
        [Export("show:")]
        void Show(bool animated);

        // -(void)hide:(BOOL)animated;
        [Export("hide:")]
        void Hide(bool animated);

        // -(void)hide:(BOOL)animated afterDelay:(NSTimeInterval)delay;
        [Export("hide:afterDelay:")]
        void Hide(bool animated, double delay);

        // -(void)showWhileExecuting:(SEL)method onTarget:(id)target withObject:(id)object animated:(BOOL)animated;
        [Export("showWhileExecuting:onTarget:withObject:animated:")]
        void ShowWhileExecuting(Selector method, NSObject target, NSObject @object, bool animated);

        // -(void)showAnimated:(BOOL)animated whileExecutingBlock:(dispatch_block_t)block;
        [Export("showAnimated:whileExecutingBlock:")]
        void ShowAnimated(bool animated, NSDispatchHandlerT block);

        // -(void)showAnimated:(BOOL)animated whileExecutingBlock:(dispatch_block_t)block completionBlock:(MBProgressHUDCompletionBlock)completion;
        [Export("showAnimated:whileExecutingBlock:completionBlock:")]
        void ShowAnimated(bool animated, NSDispatchHandlerT block, MBProgressHUDCompletionBlock completion);

        // -(void)showAnimated:(BOOL)animated whileExecutingBlock:(dispatch_block_t)block onQueue:(dispatch_queue_t)queue;
        [Export("showAnimated:whileExecutingBlock:onQueue:")]
        void ShowAnimated(bool animated, NSDispatchHandlerT block, DispatchQueue queue);

        // -(void)showAnimated:(BOOL)animated whileExecutingBlock:(dispatch_block_t)block onQueue:(dispatch_queue_t)queue completionBlock:(MBProgressHUDCompletionBlock)completion;
        [Export("showAnimated:whileExecutingBlock:onQueue:completionBlock:")]
        void ShowAnimated(bool animated, NSDispatchHandlerT block, DispatchQueue queue, MBProgressHUDCompletionBlock completion);

        // @property (copy) MBProgressHUDCompletionBlock completionBlock;
        [Export("completionBlock", ArgumentSemantic.Copy)]
        MBProgressHUDCompletionBlock CompletionBlock { get; set; }

        // @property (assign) MBProgressHUDMode mode;
        [Export("mode", ArgumentSemantic.Assign)]
        MBProgressHUDMode Mode { get; set; }

        // @property (assign) MBProgressHUDAnimation animationType;
        [Export("animationType", ArgumentSemantic.Assign)]
        MBProgressHUDAnimation AnimationType { get; set; }

        // @property (retain) UIView * customView;
        [Export("customView", ArgumentSemantic.Retain)]
        UIView CustomView { get; set; }

        [Wrap("WeakDelegate")]
        MBProgressHUDDelegate Delegate { get; set; }

        // @property (assign) id<MBProgressHUDDelegate> delegate;
        [NullAllowed, Export("delegate", ArgumentSemantic.Assign)]
        NSObject WeakDelegate { get; set; }

        // @property (copy) NSString * labelText;
        [Export("labelText")]
        string LabelText { get; set; }

        // @property (copy) NSString * detailsLabelText;
        [Export("detailsLabelText")]
        string DetailsLabelText { get; set; }

        // @property (assign) float opacity;
        [Export("opacity")]
        float Opacity { get; set; }

        // @property (retain) UIColor * color;
        [Export("color", ArgumentSemantic.Retain)]
        UIColor Color { get; set; }

        // @property (assign) float xOffset;
        [Export("xOffset")]
        float XOffset { get; set; }

        // @property (assign) float yOffset;
        [Export("yOffset")]
        float YOffset { get; set; }

        // @property (assign) float margin;
        [Export("margin")]
        float Margin { get; set; }

        // @property (assign) float cornerRadius;
        [Export("cornerRadius")]
        float CornerRadius { get; set; }

        // @property (assign) BOOL dimBackground;
        [Export("dimBackground")]
        bool DimBackground { get; set; }

        // @property (assign) float graceTime;
        [Export("graceTime")]
        float GraceTime { get; set; }

        // @property (assign) float minShowTime;
        [Export("minShowTime")]
        float MinShowTime { get; set; }

        // @property (assign) BOOL taskInProgress;
        [Export("taskInProgress")]
        bool TaskInProgress { get; set; }

        // @property (assign) BOOL removeFromSuperViewOnHide;
        [Export("removeFromSuperViewOnHide")]
        bool RemoveFromSuperViewOnHide { get; set; }

        // @property (retain) UIFont * labelFont;
        [Export("labelFont", ArgumentSemantic.Retain)]
        UIFont LabelFont { get; set; }

        // @property (retain) UIColor * labelColor;
        [Export("labelColor", ArgumentSemantic.Retain)]
        UIColor LabelColor { get; set; }

        // @property (retain) UIFont * detailsLabelFont;
        [Export("detailsLabelFont", ArgumentSemantic.Retain)]
        UIFont DetailsLabelFont { get; set; }

        // @property (retain) UIColor * detailsLabelColor;
        [Export("detailsLabelColor", ArgumentSemantic.Retain)]
        UIColor DetailsLabelColor { get; set; }

        // @property (retain) UIColor * activityIndicatorColor;
        [Export("activityIndicatorColor", ArgumentSemantic.Retain)]
        UIColor ActivityIndicatorColor { get; set; }

        // @property (assign) float progress;
        [Export("progress")]
        float Progress { get; set; }

        // @property (assign) CGSize minSize;
        [Export("minSize", ArgumentSemantic.Assign)]
        CGSize MinSize { get; set; }

        // @property (readonly, assign, atomic) CGSize size;
        [Export("size", ArgumentSemantic.Assign)]
        CGSize Size { get; }

        // @property (getter = isSquare, assign) BOOL square;
        [Export("square")]
        bool Square { [Bind("isSquare")] get; set; }
    }

    // @protocol MBProgressHUDDelegate <NSObject>
    [Protocol, Model]
    [BaseType(typeof(NSObject))]
    interface MBProgressHUDDelegate
    {
        // @optional -(void)hudWasHidden:(MBProgressHUD *)hud;
        [Export("hudWasHidden:")]
        void HudWasHidden(MBProgressHUD hud);
    }

    // @interface MBRoundProgressView : UIView
    [BaseType(typeof(UIView))]
    interface MBRoundProgressView
    {
        // @property (assign, nonatomic) float progress;
        [Export("progress")]
        float Progress { get; set; }

        // @property (retain, nonatomic) UIColor * progressTintColor;
        [Export("progressTintColor", ArgumentSemantic.Retain)]
        UIColor ProgressTintColor { get; set; }

        // @property (retain, nonatomic) UIColor * backgroundTintColor;
        [Export("backgroundTintColor", ArgumentSemantic.Retain)]
        UIColor BackgroundTintColor { get; set; }

        // @property (getter = isAnnular, assign, nonatomic) BOOL annular;
        [Export("annular")]
        bool Annular { [Bind("isAnnular")] get; set; }
    }

    // @interface MBBarProgressView : UIView
    [BaseType(typeof(UIView))]
    interface MBBarProgressView
    {
        // @property (assign, nonatomic) float progress;
        [Export("progress")]
        float Progress { get; set; }

        // @property (retain, nonatomic) UIColor * lineColor;
        [Export("lineColor", ArgumentSemantic.Retain)]
        UIColor LineColor { get; set; }

        // @property (retain, nonatomic) UIColor * progressRemainingColor;
        [Export("progressRemainingColor", ArgumentSemantic.Retain)]
        UIColor ProgressRemainingColor { get; set; }

        // @property (retain, nonatomic) UIColor * progressColor;
        [Export("progressColor", ArgumentSemantic.Retain)]
        UIColor ProgressColor { get; set; }
    }
}
