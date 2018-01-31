using System;
using AndorraTelecomiOS.Util;
using Foundation;
using ObjCRuntime;
using UIKit;

namespace AndorraTelecomiOS
{
    public partial class CallMeBackView : UIView
    {
        public CallMeBackView(IntPtr handle) : base(handle) { }

        public static ICallMeBackDelegate Delegate;

        public static CallMeBackView Create()
        {
            var CallMeBackViewArr = NSBundle.MainBundle.LoadNib("CallMeBackView", null, null);
            var View = Runtime.GetNSObject<CallMeBackView>(CallMeBackViewArr.ValueAt(0));

            /* Set text outlets */

            var LanguageBundle = RetrieveLanguageBundle(LanguageHelper.Language);

            View.HelpQuestion.Text = LanguageBundle.LocalizedString("Help?", null);

            View.PhoneLabel.Text = LanguageBundle.LocalizedString("Phone", null);

            var PhoneImage = new UIImage("img/phone-input.png");
            View.PhoneInput.LeftView = new UIImageView(PhoneImage);
            View.PhoneInput.LeftViewMode = UITextFieldViewMode.Always;

            var Attributes = new UIStringAttributes();
            Attributes.UnderlineStyle = NSUnderlineStyle.Single;
            Attributes.ForegroundColor = Colors.DarkPurple;

            var AcceptLegalLocalized = LanguageBundle.LocalizedString("Accept-legal", null);
            var MutableString = new NSMutableAttributedString(AcceptLegalLocalized);
            var Range = RangeLastTwoWords(AcceptLegalLocalized);
            MutableString.AddAttributes(Attributes, Range);
            View.LegalLabel.AttributedText = MutableString;

            View.CallMeNowButton.SetTitle(LanguageBundle.LocalizedString("Call-me-now", null), UIControlState.Normal);
            View.OrLabel.Text = LanguageBundle.LocalizedString("Or", null);
            View.ICallButton.SetTitle(LanguageBundle.LocalizedString("I-call", null), UIControlState.Normal);

            /* Add gesture recognizer to legal conditions link */

            Action LegalTouchPopOver = () =>
            {
                ShowLegalConditions(View);
            };

            var TapGesture = new UITapGestureRecognizer(LegalTouchPopOver);
            View.LegalLabel.UserInteractionEnabled = true;
            View.LegalLabel.AddGestureRecognizer(TapGesture);

            return View;
        }

        /* Actions */

        partial void CallMeNowButton_TouchUpInside(UIButton sender)
        {
            var LanguageBundle = RetrieveLanguageBundle(LanguageHelper.Language);

            var PhoneCount = PhoneInput.Text.Length;
            if(PhoneCount != 6)
            {
                ShowAlertLegalNotAccepted(this, LanguageBundle.LocalizedString("Title-invalid-number", null), LanguageBundle.LocalizedString("Phone-not-valid", null));
            }
            else if(LegalSwitch.On)
            {
                ShowAlertLegalNotAccepted(this, LanguageBundle.LocalizedString("Title-call", null), LanguageBundle.LocalizedString("Call", null));
            }
            else
            {
                ShowAlertLegalNotAccepted(this, LanguageBundle.LocalizedString("Title-legal", null), LanguageBundle.LocalizedString("Legal-not-accepted", null));
            }
        }

        partial void ICallButton_TouchUpInside(UIButton sender)
        {
            var Url = new NSUrl("tel://100900900");
            if (UIApplication.SharedApplication.CanOpenUrl(Url))
            {
                UIApplication.SharedApplication.OpenUrl(Url);
            }
            else
            {
                Console.WriteLine("Can't open Url: " + Url);
            }
        }

        /* Private methods */

        static NSBundle RetrieveLanguageBundle(string Language)
        {
            var Path = NSBundle.MainBundle.PathForResource(Language, "lproj");
            return NSBundle.FromPath(Path);
        }

        static NSRange RangeLastTwoWords(string Message)
        {
            var CountCharacters = Message.Length;
            var ArrayWords = Message.Split(' ');
            var LastPosition = ArrayWords.Length - 1;

            var CountLastTwoWords = ArrayWords[LastPosition].Length + ArrayWords[LastPosition - 1].Length + 1;

            return new NSRange(CountCharacters - CountLastTwoWords, CountLastTwoWords);
        }

        static void ShowLegalConditions(CallMeBackView View)
        {
            Delegate.ShowLegalConditions(View);
        }

        static void ShowAlertLegalNotAccepted(CallMeBackView View, string Title, string Message)
        {
            Delegate.ShowAlertLegalNotAccepted(View, Title, Message);
        }

        internal void LegalConditionsChange(CallMeBackView View, bool IsAccepted)
        {
            View.LegalSwitch.On = IsAccepted;
        }
    }
}