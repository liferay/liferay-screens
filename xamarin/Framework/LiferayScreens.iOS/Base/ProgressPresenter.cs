using Foundation;
using ObjCRuntime;
using UIKit;

namespace LiferayScreens
{
    // @protocol ProgressPresenter
    [Protocol, Model]
    [BaseType(typeof(NSObject))]
    interface ProgressPresenter
    {
        // @required -(void)showHUDInView:(UIView * _Nonnull)view message:(NSString * _Nullable)message forInteractor:(Interactor * _Nonnull)interactor;
        [Abstract]
        [Export("showHUDInView:message:forInteractor:")]
        void ShowHUDInView(UIView view, [NullAllowed] string message, Interactor interactor);

        // @required -(void)hideHUDFromView:(UIView * _Nullable)view message:(NSString * _Nullable)message forInteractor:(Interactor * _Nonnull)interactor withError:(NSError * _Nullable)error;
        [Abstract]
        [Export("hideHUDFromView:message:forInteractor:withError:")]
        void HideHUDFromView([NullAllowed] UIView view, [NullAllowed] string message, Interactor interactor, [NullAllowed] NSError error);
    }
}