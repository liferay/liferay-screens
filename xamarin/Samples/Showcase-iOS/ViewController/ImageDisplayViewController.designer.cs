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
    [Register ("ImageDisplayViewController")]
    partial class ImageDisplayViewController
    {
        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        LiferayScreens.ImageDisplayScreenlet imageDisplayScreenlet { get; set; }

        void ReleaseDesignerOutlets ()
        {
            if (imageDisplayScreenlet != null) {
                imageDisplayScreenlet.Dispose ();
                imageDisplayScreenlet = null;
            }
        }
    }
}