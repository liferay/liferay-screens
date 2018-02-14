using AndorraTelecomiOS.Util;
using Foundation;
using LiferayScreens;

namespace AndorraTelecomiOS.CustomViews
{
    public class AndorraTelecomProgressPresenter : MBProgressHUDPresenter, IProgressPresenter
    {
        public AndorraTelecomProgressPresenter()
        {
            CustomColor = Colors.DarkPurple;
            CustomOpacity = 0.6f;
        }

        public override void ShowHUDInView(UIKit.UIView view, string message, Interactor interactor)
        {
            if (Instance == null) {
                Instance = MBProgressHUD.ShowHUDAddedTo(view, true);
            }

            ConfigureAndShowHUD(Instance,
                                NSBundle.FromPath(NSBundle.MainBundle.PathForResource(LanguageHelper.Language, "lproj")).LocalizedString("Loading...", null),
                                ProgressCloseMode.ManualClose,
                                ProgressSpinnerMode.IndeterminateSpinner);
        }

        public override void HideHUDFromView(UIKit.UIView view, string message, Interactor interactor, Foundation.NSError error)
        {
            if (message != null)
            {
                if (Instance == null)
                {
                    Instance = MBProgressHUD.ShowHUDAddedTo(view, true);
                }

                ConfigureAndShowHUD(Instance,
                                    NSBundle.MainBundle.LocalizedString("Loading...", null),
                                    (error == null ? ProgressCloseMode.Autoclose_TouchClosable : ProgressCloseMode.ManualClose_TouchClosable),
                                    ProgressSpinnerMode.IndeterminateSpinner);
            }
            else 
            {
                HideHud();
            }
                
        }
    }
}
