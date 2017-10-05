using Android.Runtime;
using Com.Liferay.Mobile.Screens.Base.Interactor.Listener;
using Com.Liferay.Mobile.Screens.Imagegallery.Model;
using Com.Liferay.Mobile.Screens.Base.List;
using System;

namespace Com.Liferay.Mobile.Screens.Imagegallery
{
    [Register("com/liferay/mobile/screens/imagegallery/ImageGalleryListener", "", "Com.Liferay.Mobile.Screens.Imagegallery.IImageGalleryListenerInvoker")]
    public interface IImageGalleryListener : IBaseListListener, IBaseCacheListener, IJavaObject, IDisposable
    {
        //
        // Methods
        //
        [Register("onImageEntryDeleted", "(J)V", "GetOnImageEntryDeleted_JHandler:Com.Liferay.Mobile.Screens.Imagegallery.IImageGalleryListenerInvoker, LiferayScreens_Android")]
        void OnImageEntryDeleted(long p0);

        [Register("onImageUploadEnd", "(Lcom/liferay/mobile/screens/imagegallery/model/ImageEntry;)V", "GetOnImageUploadEnd_Lcom_liferay_mobile_screens_imagegallery_model_ImageEntry_Handler:Com.Liferay.Mobile.Screens.Imagegallery.IImageGalleryListenerInvoker, LiferayScreens_Android")]
        void OnImageUploadEnd(ImageEntry p0);

        [Register("onImageUploadProgress", "(II)V", "GetOnImageUploadProgress_IIHandler:Com.Liferay.Mobile.Screens.Imagegallery.IImageGalleryListenerInvoker, LiferayScreens_Android")]
        void OnImageUploadProgress(int p0, int p1);

        [Register("onImageUploadStarted", "(Landroid/net/Uri;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "GetOnImageUploadStarted_Landroid_net_Uri_Ljava_lang_String_Ljava_lang_String_Ljava_lang_String_Handler:Com.Liferay.Mobile.Screens.Imagegallery.IImageGalleryListenerInvoker, LiferayScreens_Android")]
        void OnImageUploadStarted(Java.Net.URI p0, string p1, string p2, string p3);

        [Register("provideImageUploadDetailView", "()I", "GetProvideImageUploadDetailViewHandler:Com.Liferay.Mobile.Screens.Imagegallery.IImageGalleryListenerInvoker, LiferayScreens_Android")]
        int ProvideImageUploadDetailView();

        [Register("showUploadImageView", "(Ljava/lang/String;Landroid/net/Uri;I)Z", "GetShowUploadImageView_Ljava_lang_String_Landroid_net_Uri_IHandler:Com.Liferay.Mobile.Screens.Imagegallery.IImageGalleryListenerInvoker, LiferayScreens_Android")]
        bool ShowUploadImageView(string p0, Java.Net.URI p1, int p2);
    }
}

namespace Com.Liferay.Mobile.Screens.Imagegallery.Model
{
    public partial class ImageEntry { }
}