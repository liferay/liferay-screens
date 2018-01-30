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
    [Register ("CallMeBackView")]
    partial class CallMeBackView
    {
        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton CallMeNowButton { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UILabel HelpQuestion { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton ICallButton { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UILabel LegalLabel { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UISwitch LegalSwitch { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UILabel OrLabel { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UITextField PhoneInput { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UILabel PhoneLabel { get; set; }

        [Action ("CallMeNowButton_TouchUpInside:")]
        [GeneratedCode ("iOS Designer", "1.0")]
        partial void CallMeNowButton_TouchUpInside (UIKit.UIButton sender);

        [Action ("ICallButton_TouchUpInside:")]
        [GeneratedCode ("iOS Designer", "1.0")]
        partial void ICallButton_TouchUpInside (UIKit.UIButton sender);

        void ReleaseDesignerOutlets ()
        {
            if (CallMeNowButton != null) {
                CallMeNowButton.Dispose ();
                CallMeNowButton = null;
            }

            if (HelpQuestion != null) {
                HelpQuestion.Dispose ();
                HelpQuestion = null;
            }

            if (ICallButton != null) {
                ICallButton.Dispose ();
                ICallButton = null;
            }

            if (LegalLabel != null) {
                LegalLabel.Dispose ();
                LegalLabel = null;
            }

            if (LegalSwitch != null) {
                LegalSwitch.Dispose ();
                LegalSwitch = null;
            }

            if (OrLabel != null) {
                OrLabel.Dispose ();
                OrLabel = null;
            }

            if (PhoneInput != null) {
                PhoneInput.Dispose ();
                PhoneInput = null;
            }

            if (PhoneLabel != null) {
                PhoneLabel.Dispose ();
                PhoneLabel = null;
            }
        }
    }
}