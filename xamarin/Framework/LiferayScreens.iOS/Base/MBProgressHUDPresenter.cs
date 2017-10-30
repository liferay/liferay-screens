using Foundation;
using ObjCRuntime;
using UIKit;

namespace LiferayScreens
{
    // @interface MBProgressHUDPresenter : NSObject <ProgressPresenter>
    [BaseType(typeof(NSObject))]
    interface MBProgressHUDPresenter : IProgressPresenter
    {
        // @property (nonatomic, strong) MBProgressHUD * _Nullable instance;
        [NullAllowed, Export("instance", ArgumentSemantic.Strong)]
        MBProgressHUD Instance { get; set; }

        // @property (nonatomic, strong) UIView * _Nullable customView;
        [NullAllowed, Export("customView", ArgumentSemantic.Strong)]
        UIView CustomView { get; set; }

        // @property (nonatomic, strong) UIColor * _Nullable customColor;
        [NullAllowed, Export("customColor", ArgumentSemantic.Strong)]
        UIColor CustomColor { get; set; }

        // @property (nonatomic) float customOpacity;
        [Export("customOpacity")]
        float CustomOpacity { get; set; }

        // -(void)hideHUDFromView:(UIView * _Nullable)view message:(NSString * _Nullable)message forInteractor:(Interactor * _Nonnull)interactor withError:(NSError * _Nullable)error;
        [Export("hideHUDFromView:message:forInteractor:withError:")]
        void HideHUDFromView([NullAllowed] UIView view, [NullAllowed] string message, Interactor interactor, [NullAllowed] NSError error);

        // -(void)showHUDInView:(UIView * _Nonnull)view message:(NSString * _Nullable)message forInteractor:(Interactor * _Nonnull)interactor;
        [Export("showHUDInView:message:forInteractor:")]
        void ShowHUDInView(UIView view, [NullAllowed] string message, Interactor interactor);

        // -(void)hideHud;
        [Export("hideHud")]
        void HideHud();

        // -(void)configureAndShowHUD:(MBProgressHUD * _Nonnull)hud message:(NSString * _Nullable)message closeMode:(enum ProgressCloseMode)closeMode spinnerMode:(enum ProgressSpinnerMode)spinnerMode;
        [Export("configureAndShowHUD:message:closeMode:spinnerMode:")]
        void ConfigureAndShowHUD(MBProgressHUD hud, [NullAllowed] string message, ProgressCloseMode closeMode, ProgressSpinnerMode spinnerMode);

        // -(UIView * _Nonnull)rootView:(UIView * _Nonnull)currentView __attribute__((warn_unused_result));
        [Export("rootView:")]
        UIView RootView(UIView currentView);

        // -(id)spinnerModeToProgressModeHUD:(enum ProgressSpinnerMode)spinnerMode __attribute__((warn_unused_result));
        [Export("spinnerModeToProgressModeHUD:")]
        NSObject SpinnerModeToProgressModeHUD(ProgressSpinnerMode spinnerMode);
    }
}
