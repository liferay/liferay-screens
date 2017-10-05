using Android.Runtime;
using Java.Interop;
using System;
using System.Collections;
using Com.Liferay.Mobile.Screens.Base.Interactor.Listener;

namespace Com.Liferay.Mobile.Screens.Base.List
{
    [Register("com/liferay/mobile/screens/base/list/BaseListListener", "", "Com.Liferay.Mobile.Screens.Base.List.IBaseListListenerInvoker"), JavaTypeParameters(new string[] {
        "E"
    })]
    public interface IBaseListListener : IBaseCacheListener, IJavaObject, IDisposable
    {
        //
        // Methods
        //
        [Register("onListItemSelected", "(Ljava/lang/Object;Landroid/view/View;)V", "GetOnListItemSelected_Ljava_lang_Object_Landroid_view_View_Handler:Com.Liferay.Mobile.Screens.Base.List.IBaseListListenerInvoker, LiferayScreens_Android")]
        void OnListItemSelected(Java.Lang.Object p0, Java.Lang.Object p1);

        [Register("onListPageFailed", "(ILjava/lang/Exception;)V", "GetOnListPageFailed_ILjava_lang_Exception_Handler:Com.Liferay.Mobile.Screens.Base.List.IBaseListListenerInvoker, LiferayScreens_Android")]
        void OnListPageFailed(int p0, Java.Lang.Exception p1);

        [Register("onListPageReceived", "(IILjava/util/List;I)V", "GetOnListPageReceived_IILjava_util_List_IHandler:Com.Liferay.Mobile.Screens.Base.List.IBaseListListenerInvoker, LiferayScreens_Android")]
        void OnListPageReceived(int p0, int p1, IList p2, int p3);
    }
}

namespace Com.Liferay.Mobile.Screens.Base.Interactor.Listener
{
    public partial interface IBaseCacheListener : IJavaObject, IDisposable { }
}

namespace Com.Liferay.Mobile.Screens.Base.List
{
    [Register("com/liferay/mobile/screens/base/list/BaseListListener", DoNotGenerateAcw = true)]
    internal partial class IBaseListListenerInvoker : Java.Lang.Object, IBaseListListener, IBaseCacheListener, IJavaObject, IDisposable
    {
        //
        // Static Fields
        //
        private static IntPtr java_class_ref;

        private static Delegate cb_onListItemSelected_Ljava_lang_Object_Landroid_view_View_;

        private static Delegate cb_onListPageFailed_ILjava_lang_Exception_;

        private static Delegate cb_onListPageReceived_IILjava_util_List_I;

        private static Delegate cb_error_Ljava_lang_Exception_Ljava_lang_String_;

        //
        // Fields
        //
        private IntPtr class_ref;

        private IntPtr id_onListItemSelected_Ljava_lang_Object_Landroid_view_View_;

        private IntPtr id_onListPageFailed_ILjava_lang_Exception_;

        private IntPtr id_onListPageReceived_IILjava_util_List_I;

        private IntPtr id_error_Ljava_lang_Exception_Ljava_lang_String_;

        //
        // Properties
        //
        protected override IntPtr ThresholdClass
        {
            get;
        }

        protected override Type ThresholdType
        {
            get;
        }

        //
        // Constructors
        //
        public IBaseListListenerInvoker(IntPtr handle, JniHandleOwnership transfer) { }

        //
        // Static Methods
        //
        private static Delegate GetError_Ljava_lang_Exception_Ljava_lang_String_Handler() 
        {
            return null;
        }

        public static IBaseListListener GetObject(IntPtr handle, JniHandleOwnership transfer) 
        {
            return null;
        }

        private static Delegate GetOnListItemSelected_Ljava_lang_Object_Landroid_view_View_Handler()
        {
            return null;
        }

        private static Delegate GetOnListPageFailed_ILjava_lang_Exception_Handler()
        {
            return null;
        }

        private static Delegate GetOnListPageReceived_IILjava_util_List_IHandler()
        {
            return null;
        }

        private static void n_Error_Ljava_lang_Exception_Ljava_lang_String_(IntPtr jnienv, IntPtr native__this, IntPtr native_p0, IntPtr native_p1) { }

        private static void n_OnListItemSelected_Ljava_lang_Object_Landroid_view_View_(IntPtr jnienv, IntPtr native__this, IntPtr native_p0, IntPtr native_p1) { }

        private static void n_OnListPageFailed_ILjava_lang_Exception_(IntPtr jnienv, IntPtr native__this, int p0, IntPtr native_p1) { }

        private static void n_OnListPageReceived_IILjava_util_List_I(IntPtr jnienv, IntPtr native__this, int p0, int p1, IntPtr native_p2, int p3) { }

        private static IntPtr Validate(IntPtr handle)
        {
            return handle;
        }

        //
        // Methods
        //
        protected override void Dispose(bool disposing) { }

        public void Error(Java.Lang.Exception p0, string p1) { }

        public void OnListItemSelected(Java.Lang.Object p0, Java.Lang.Object p1) { }

        public void OnListPageFailed(int p0, Java.Lang.Exception p1) { }

        public void OnListPageReceived(int p0, int p1, IList p2, int p3) { }
    }
}
