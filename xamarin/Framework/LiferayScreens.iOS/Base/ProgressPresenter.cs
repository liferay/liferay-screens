using Foundation;
using ObjCRuntime;
using UIKit;

namespace LiferayScreens
{
    interface IProgressPresenter { }

    // @protocol ProgressPresenter
    [BaseType(typeof(NSObject))]
    [Protocol, Model]
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