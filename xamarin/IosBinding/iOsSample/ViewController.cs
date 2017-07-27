﻿using System;
using BindingLibrary;
using Foundation;
using UIKit;

namespace iOsSample
{
    public partial class ViewController : UIViewController, ILoginScreenletDelegate
    {
        protected ViewController(IntPtr handle) : base(handle)
        {
                // Note: this .ctor should not contain any initialization logic.
        }

        public override void ViewDidLoad()
        {
            
            base.ViewDidLoad();
            // Perform any additional setup after loading the  = this;view, typically from a nib.
            this.loginscrlet.PresentingViewController = this;
            this.loginscrlet.Delegate = this;
        }

        public override void DidReceiveMemoryWarning()
        {
            base.DidReceiveMemoryWarning();
            // Release any cached data, images, etc that aren't in use.
        }

        [Export("screenlet:onLoginError:")]
        public void OnLoginError(BaseScreenlet screenlet, NSError error)
        {
            System.Diagnostics.Debug.WriteLine($"Login error {error.Description}");
        }
    }
}
