using LiferayScreens;
using System;

namespace ShowcaseiOS
{
    public partial class LoginView_demo : LoginView_default
    {
        public LoginView_demo (IntPtr handle) : base (handle) { }

        public override void OnCreated()
        {
            //You can change the login button color from code
            //this.LoginButton.BackgroundColor = UIKit.UIColor.DarkGray;
        }

        public override IProgressPresenter CreateProgressPresenter()
        {
            return new NoneProgressPresenter();
        }
    }
}
