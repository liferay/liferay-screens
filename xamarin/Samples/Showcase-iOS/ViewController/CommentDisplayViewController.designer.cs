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
    [Register ("CommentDisplayViewController")]
    partial class CommentDisplayViewController
    {
        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        LiferayScreens.CommentDisplayScreenlet commentDisplayScreenlet { get; set; }

        void ReleaseDesignerOutlets ()
        {
            if (commentDisplayScreenlet != null) {
                commentDisplayScreenlet.Dispose ();
                commentDisplayScreenlet = null;
            }
        }
    }
}