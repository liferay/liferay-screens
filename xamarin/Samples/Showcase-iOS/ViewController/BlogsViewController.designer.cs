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
    [Register ("BlogsViewController")]
    partial class BlogsViewController
    {
        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        LiferayScreens.BlogsEntryDisplayScreenlet blogsDisplayScreenlet { get; set; }

        void ReleaseDesignerOutlets ()
        {
            if (blogsDisplayScreenlet != null) {
                blogsDisplayScreenlet.Dispose ();
                blogsDisplayScreenlet = null;
            }
        }
    }
}