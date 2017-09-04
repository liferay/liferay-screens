using LiferayScreens;
using Foundation;
using System;
using UIKit;
using System.Diagnostics;

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
            Debug.WriteLine($"Login failed: {error.Description}");
        }

        [Export("screenlet:onLoginResponseUserAttributes:")]
        public virtual void OnLoginResponseUserAttributes(BaseScreenlet screenlet, NSDictionary<NSString, NSObject> attributes)
        {
            Debug.WriteLine($"Login successful: {attributes}");

            Debug.WriteLine("Navigate to SelectScreenletViewController");
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
            Debug.WriteLine("Navigate to ForgotPasswordScreenlet");
            UIStoryboard board = UIStoryboard.FromName("ForgotPassword", null);
            ForgotPasswordViewController vc = (ForgotPasswordViewController) 
                board.InstantiateViewController("ForgotPasswordViewController");
            this.NavigationController.PushViewController(vc, true);
        }

        partial void SignUpButton_TouchUpInside(UIButton sender)
        {
            Debug.WriteLine("Navigate to SignUpScreenlet");
            UIStoryboard board = UIStoryboard.FromName("SignUp", null);
            SignUpViewController vc = (SignUpViewController) 
                board.InstantiateViewController("SignUpViewController");
            this.NavigationController.PushViewController(vc, true);
        }
    }
}
