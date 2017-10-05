using Android.Runtime;
using System;

namespace Com.Liferay.Mobile.Screens.Base.List
{
    public partial class BaseListScreenlet
    {
        protected override Java.Lang.Object CreateInteractor(string p0)
        {
            throw new NotImplementedException();
        }

        protected override void OnUserAction(string p0, Java.Lang.Object p1, params Java.Lang.Object[] p2)
        {
            throw new NotImplementedException();
        }

        public virtual IBaseListListener Listener
        {
            [Register("getListener", "()Lcom/liferay/mobile/screens/base/list/BaseListListener;", "GetGetListenerHandler")]
            get;
            [Register("setListener", "(Lcom/liferay/mobile/screens/base/list/BaseListListener;)V", "GetSetListener_Lcom_liferay_mobile_screens_base_list_BaseListListener_Handler")]
            set;
        }
    }
}
