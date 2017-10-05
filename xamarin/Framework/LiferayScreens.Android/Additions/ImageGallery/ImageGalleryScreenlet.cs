using Android.Runtime;
using System;

namespace Com.Liferay.Mobile.Screens.Imagegallery
{
    public partial class ImageGalleryScreenlet
    {
        protected override Java.Lang.Object CreateInteractor(string p0)
        {
            throw new NotImplementedException();
        }

        public virtual IImageGalleryListener Listener
        {
            [Register("getListener", "()Lcom/liferay/mobile/screens/imagegallery/ImageGalleryListener;", "GetGetListenerHandler")]
            get;
            [Register("setListener", "(Lcom/liferay/mobile/screens/imagegallery/ImageGalleryListener;)V", "GetSetListener_Lcom_liferay_mobile_screens_imagegallery_ImageGalleryListener_Handler")]
            set;
        }
    }
}
