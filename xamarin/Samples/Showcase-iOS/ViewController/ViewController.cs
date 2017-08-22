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

            this.loginScreenlet.Delegate = this;

            SetDefaultValues();
        }

        /* ILoginScreenletDelegate */

        [Export("screenlet:onLoginError:")]
        public virtual void OnLoginError(BaseScreenlet screenlet, NSError error)
        {
            System.Diagnostics.Debug.WriteLine($"Login failed: {error.Description}");
        }

        [Export("screenlet:onLoginResponseUserAttributes:")]
        public virtual void OnLoginResponseUserAttributes(BaseScreenlet screenlet, NSDictionary<NSString, NSObject> attributes)
        {
            System.Diagnostics.Debug.WriteLine($"Login successful: {attributes}");

            System.Diagnostics.Debug.WriteLine("Navigate to SelectScreenletViewController");
            UIStoryboard board = UIStoryboard.FromName("SelectScreenlet", null);
            SelectScreenletViewController vc = (SelectScreenletViewController)
                board.InstantiateViewController("SelectScreenletViewController");
            this.NavigationController.PushViewController(vc, true);
        }

        /* Private methods */

        void SetDefaultValues()
        {
            this.loginScreenlet.ViewModel.UserName = "test@liferay.com";
            this.loginScreenlet.ViewModel.Password = "test1";
        }

        /* Event methods */

        partial void ForgotPasswordButton_TouchUpInside(UIButton sender)
        {
            System.Diagnostics.Debug.WriteLine("Navigate to ForgotPasswordViewController");
            UIStoryboard board = UIStoryboard.FromName("ForgotPassword", null);
            ForgotPasswordViewController vc = (ForgotPasswordViewController) 
                board.InstantiateViewController("ForgotPasswordViewController");
            this.NavigationController.PushViewController(vc, true);
        }

        partial void SignUpButton_TouchUpInside(UIButton sender)
        {
            System.Diagnostics.Debug.WriteLine("Navigate to SignUpViewController");
            UIStoryboard board = UIStoryboard.FromName("SignUp", null);
            SignUpViewController vc = (SignUpViewController) 
                board.InstantiateViewController("SignUpViewController");
            this.NavigationController.PushViewController(vc, true);
        }
    }
}
