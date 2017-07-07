using Foundation;
using System;

namespace iOsSample
{
    public partial class LoginView_test : BindingLibrary.LoginView_default
    {
        public LoginView_test (IntPtr handle) : base (handle)
        {
        }

        public override void MovedToSuperview()
        {
            base.MovedToSuperview();

            this.UserNameField.TouchDown += touched;
        }

        public override void OnCreated()
        {
            base.OnCreated();

        }

        private void touched(object sender, EventArgs e)
        {
            this.UserNameField.Text = "patata";
        }
    }
}