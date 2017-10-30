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
    [Register ("CommentAddViewController")]
    partial class CommentAddViewController
    {
        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        LiferayScreens.CommentAddScreenlet commentAddScreenlet { get; set; }

        void ReleaseDesignerOutlets ()
        {
            if (commentAddScreenlet != null) {
                commentAddScreenlet.Dispose ();
                commentAddScreenlet = null;
            }
        }
    }
}