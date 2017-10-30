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
    [Register ("RatingViewController")]
    partial class RatingViewController
    {
        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        LiferayScreens.RatingScreenlet ratingScreenletStars { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        LiferayScreens.RatingScreenlet ratingScreenletThumbs { get; set; }

        void ReleaseDesignerOutlets ()
        {
            if (ratingScreenletStars != null) {
                ratingScreenletStars.Dispose ();
                ratingScreenletStars = null;
            }

            if (ratingScreenletThumbs != null) {
                ratingScreenletThumbs.Dispose ();
                ratingScreenletThumbs = null;
            }
        }
    }
}