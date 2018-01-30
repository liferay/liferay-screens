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

        internal void LegalConditionsChange(CallMeBackView View, bool IsAccepted)
        {
            View.LegalSwitch.On = IsAccepted;
        }
    }
}