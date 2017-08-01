// WARNING
//
// This file has been generated automatically by Visual Studio from the outlets and
// actions declared in your storyboard file.
// Manual changes to this file will not be maintained.
//
using Foundation;
using System;
using System.CodeDom.Compiler;

namespace ShowcaseiOS.ViewController
{
    [Register ("ViewController")]
    partial class ViewController
    {
        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton forgotPasswordButton { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        LiferayScreens.LoginScreenlet loginScreenlet { get; set; }

        [Action ("ForgotPasswordButton_TouchUpInside:")]
        [GeneratedCode ("iOS Designer", "1.0")]
        partial void ForgotPasswordButton_TouchUpInside (UIKit.UIButton sender);

        void ReleaseDesignerOutlets ()
        {
            if (forgotPasswordButton != null) {
                forgotPasswordButton.Dispose ();
                forgotPasswordButton = null;
            }

            if (loginScreenlet != null) {
                loginScreenlet.Dispose ();
                loginScreenlet = null;
            }
        }
    }
}