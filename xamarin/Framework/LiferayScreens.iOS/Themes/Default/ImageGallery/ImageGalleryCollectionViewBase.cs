using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;
using UIKit;

namespace LiferayScreens
{
    // @interface ImageGalleryCollectionViewBase : BaseListCollectionView <ImageGalleryViewModel>
    [BaseType(typeof(BaseListCollectionView))]
    interface ImageGalleryCollectionViewBase : IImageGalleryViewModel
    {
        // @property (nonatomic, weak) UIView * _Nullable _uploadView;
        [NullAllowed, Export("_uploadView", ArgumentSemantic.Weak)]
        UIView _uploadView { get; set; }

        // @property (readonly, nonatomic) NSInteger totalEntries;
        [Export("totalEntries")]
        nint TotalEntries { get; }

        // -(void)onImageEntryDeleted:(ImageEntry * _Nonnull)imageEntry;
        [Export("onImageEntryDeleted:")]
        void OnImageEntryDeleted(ImageEntry imageEntry);

        // -(void)onImageUploaded:(ImageEntry * _Nonnull)imageEntry;
        [Export("onImageUploaded:")]
        void OnImageUploaded(ImageEntry imageEntry);

        // -(void)onImageUploadEnqueued:(ImageEntryUpload * _Nonnull)imageEntryUpload;
        [Export("onImageUploadEnqueued:")]
        void OnImageUploadEnqueued(ImageEntryUpload imageEntryUpload);

        // -(void)onImageUploadProgress:(uint64_t)bytesSent bytesToSend:(uint64_t)bytesToSend imageEntryUpload:(ImageEntryUpload * _Nonnull)imageEntryUpload;
        [Export("onImageUploadProgress:bytesToSend:imageEntryUpload:")]
        void OnImageUploadProgress(ulong bytesSent, ulong bytesToSend, ImageEntryUpload imageEntryUpload);

        // -(void)onImageUploadError:(ImageEntryUpload * _Nonnull)imageEntryUpload error:(NSError * _Nonnull)error;
        [Export("onImageUploadError:error:")]
        void OnImageUploadError(ImageEntryUpload imageEntryUpload, NSError error);

        // -(NSInteger)indexOfImageEntry:(ImageEntry * _Nonnull)imageEntry __attribute__((warn_unused_result));
        [Export("indexOfImageEntry:")]
        nint IndexOfImageEntry(ImageEntry imageEntry);

        // -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
        [Export("createProgressPresenter")]
        ProgressPresenter CreateProgressPresenter();

        // -(void)showUploadProgressView;
        [Export("showUploadProgressView")]
        void ShowUploadProgressView();

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
