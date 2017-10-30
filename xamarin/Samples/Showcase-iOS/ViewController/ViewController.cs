using LiferayScreens;
using Foundation;
using System;
using UIKit;

namespace ShowcaseiOS.ViewController
{
    public partial class ViewController : UIViewController, ILoginScreenletDelegate
    {
        protected ViewController(IntPtr handle) : base(handle) {}

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            //Remove the comment for demo theme without ProgressPresenter
            //this.loginScreenlet.ThemeName = "demo";
            this.loginScreenlet.Delegate = this;

            SetDefaultValues();
        }

        /* ILoginScreenletDelegate */

        [Export("screenlet:onLoginError:")]
        public virtual void OnLoginError(BaseScreenlet screenlet, NSError error)
        {
            Console.WriteLine($"Login failed: {error.Description}");
        }

        [Export("screenlet:onLoginResponseUserAttributes:")]
        public virtual void OnLoginResponseUserAttributes(BaseScreenlet screenlet, NSDictionary<NSString, NSObject> attributes)
        {
            Console.WriteLine($"Login successful: {attributes}");

            Console.WriteLine("Navigate to SelectScreenletViewController");
            UIStoryboard board = UIStoryboard.FromName("SelectScreenlet", null);
            SelectScreenletViewController vc = (SelectScreenletViewController)
                board.InstantiateViewController("SelectScreenletViewController");
            this.NavigationController.PushViewController(vc, true);
        }

        /* Private methods */

        void SetDefaultValues()
        {
            this.loginScreenlet.ViewModel.UserName = "demo@liferay.com";
            this.loginScreenlet.ViewModel.Password = "demo";
        }

        /* Event methods */

        partial void ForgotPasswordButton_TouchUpInside(UIButton sender)
        {
            Console.WriteLine("Navigate to ForgotPasswordScreenlet");
            UIStoryboard board = UIStoryboard.FromName("ForgotPassword", null);
            ForgotPasswordViewController vc = (ForgotPasswordViewController) 
                board.InstantiateViewController("ForgotPasswordViewController");
            this.NavigationController.PushViewController(vc, true);
        }

        partial void SignUpButton_TouchUpInside(UIButton sender)
        {
            Console.WriteLine("Navigate to SignUpScreenlet");
            UIStoryboard board = UIStoryboard.FromName("SignUp", null);
            SignUpViewController vc = (SignUpViewController) 
                board.InstantiateViewController("SignUpViewController");
            this.NavigationController.PushViewController(vc, true);
        }

        partial void ChangeThemeButton_TouchUpInside(UIButton sender)
        {
            if (this.loginScreenlet.ThemeName == "default")
            {
                this.loginScreenlet.ThemeName = "demo";
            }
            else
            {
                this.loginScreenlet.ThemeName = "default";
            }
        }
    }
}
