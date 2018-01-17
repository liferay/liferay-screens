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

namespace AndorraTelecomiOS
{
    [Register ("MenuViewController")]
    partial class MenuViewController
    {
        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        LiferayScreens.WebScreenlet webScreenlet { get; set; }

        void ReleaseDesignerOutlets ()
        {
            if (webScreenlet != null) {
                webScreenlet.Dispose ();
                webScreenlet = null;
            }
        }
    }
}