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

namespace ShowcaseiOS.ViewController
{
    [Register ("SignUpViewController")]
    partial class SignUpViewController
    {
        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        LiferayScreens.SignUpScreenlet signUpScreenlet { get; set; }

        void ReleaseDesignerOutlets ()
        {
            if (signUpScreenlet != null) {
                signUpScreenlet.Dispose ();
                signUpScreenlet = null;
            }
        }
    }
}