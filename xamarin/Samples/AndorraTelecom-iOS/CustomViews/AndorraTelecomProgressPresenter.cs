using AndorraTelecomiOS.Util;
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
                                "Loading...",
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
                                    "Loading...",
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
