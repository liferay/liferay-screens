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

        public static CallMeBackView Create()
        {
            var CallMeBackViewArr = NSBundle.MainBundle.LoadNib("CallMeBackView", null, null);
            var View = Runtime.GetNSObject<CallMeBackView>(CallMeBackViewArr.ValueAt(0));

            return View;
        }
    }
}