// WARNING
//
// This file has been generated automatically by Visual Studio from the outlets and
// actions declared in your storyboard file.
// Manual changes to this file will not be maintained.
//
using Foundation;
using System;
using System.CodeDom.Compiler;
using UIKit;

namespace ShowcaseiOS
{
    [Register ("LoginView_demo")]
    partial class LoginView_demo
    {
        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton authorizeButton { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton loginButton { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        LiferayScreens.DefaultTextField passwordField { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        LiferayScreens.DefaultTextField userNameField { get; set; }

        void ReleaseDesignerOutlets ()
        {
            if (authorizeButton != null) {
                authorizeButton.Dispose ();
                authorizeButton = null;
            }

            if (loginButton != null) {
                loginButton.Dispose ();
                loginButton = null;
            }

            if (passwordField != null) {
                passwordField.Dispose ();
                passwordField = null;
            }

            if (userNameField != null) {
                userNameField.Dispose ();
                userNameField = null;
            }
        }
    }
}