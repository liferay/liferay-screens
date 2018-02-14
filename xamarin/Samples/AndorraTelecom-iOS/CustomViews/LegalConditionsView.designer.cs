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
    [Register ("LegalConditionsView")]
    partial class LegalConditionsView
    {
        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        LiferayScreens.WebScreenlet WebScreenlet { get; set; }

        void ReleaseDesignerOutlets ()
        {
            if (WebScreenlet != null) {
                WebScreenlet.Dispose ();
                WebScreenlet = null;
            }
        }
    }
}