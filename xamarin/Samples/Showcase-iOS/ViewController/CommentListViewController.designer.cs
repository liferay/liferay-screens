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
    [Register ("CommentListViewController")]
    partial class CommentListViewController
    {
        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        LiferayScreens.CommentListScreenlet commentListScreenlet { get; set; }

        void ReleaseDesignerOutlets ()
        {
            if (commentListScreenlet != null) {
                commentListScreenlet.Dispose ();
                commentListScreenlet = null;
            }
        }
    }
}