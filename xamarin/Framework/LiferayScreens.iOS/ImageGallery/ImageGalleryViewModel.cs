using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    interface IImageGalleryViewModel {}

    // @protocol ImageGalleryViewModel
    [BaseType(typeof(NSObject))]
    [Protocol, Model]
    interface ImageGalleryViewModel
    {
        // @required @property (readonly, nonatomic) NSInteger totalEntries;
        [Abstract]
        [Export("totalEntries")]
        nint TotalEntries { get; }

        // @optional -(void)onImageEntryDeleted:(ImageEntry * _Nonnull)imageEntry;
        [Export("onImageEntryDeleted:")]
        void OnImageEntryDeleted(ImageEntry imageEntry);

        // @optional -(void)onImageUploaded:(ImageEntry * _Nonnull)imageEntry;
        [Export("onImageUploaded:")]
        void OnImageUploaded(ImageEntry imageEntry);

        // @optional -(void)onImageUploadEnqueued:(ImageEntryUpload * _Nonnull)imageEntryUpload;
        [Export("onImageUploadEnqueued:")]
        void OnImageUploadEnqueued(ImageEntryUpload imageEntryUpload);

        // @optional -(void)onImageUploadProgress:(uint64_t)bytesSent bytesToSend:(uint64_t)bytesToSend imageEntryUpload:(ImageEntryUpload * _Nonnull)imageEntryUpload;
        [Export("onImageUploadProgress:bytesToSend:imageEntryUpload:")]
        void OnImageUploadProgress(ulong bytesSent, ulong bytesToSend, ImageEntryUpload imageEntryUpload);

        // @optional -(void)onImageUploadError:(ImageEntryUpload * _Nonnull)imageEntryUpload error:(NSError * _Nonnull)error;
        [Export("onImageUploadError:error:")]
        void OnImageUploadError(ImageEntryUpload imageEntryUpload, NSError error);

        // @optional -(NSInteger)indexOfImageEntry:(ImageEntry * _Nonnull)imageEntry __attribute__((warn_unused_result));
        [Export("indexOfImageEntry:")]
        nint IndexOfImageEntry(ImageEntry imageEntry);
    }
}
