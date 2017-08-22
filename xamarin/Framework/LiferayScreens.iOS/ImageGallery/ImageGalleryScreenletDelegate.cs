using Foundation;
using System;

namespace LiferayScreens
{
    // @protocol ImageGalleryScreenletDelegate <BaseScreenletDelegate>
    [BaseType(typeof(BaseScreenletDelegate))]
    [Protocol, Model]
    interface ImageGalleryScreenletDelegate
    {
        // @optional -(void)screenlet:(ImageGalleryScreenlet * _Nonnull)screenlet onImageEntriesResponse:(NSArray<ImageEntry *> * _Nonnull)imageEntries;
        [Export("screenlet:onImageEntriesResponse:")]
        void Screenlet(ImageGalleryScreenlet screenlet, ImageEntry[] imageEntries);

        // @optional -(void)screenlet:(ImageGalleryScreenlet * _Nonnull)screenlet onImageEntriesError:(NSError * _Nonnull)error;
        [Export("screenlet:onImageEntriesError:")]
        void ScreenletImageEntriesError(ImageGalleryScreenlet screenlet, NSError error);

        // @optional -(void)screenlet:(ImageGalleryScreenlet * _Nonnull)screenlet onImageEntrySelected:(ImageEntry * _Nonnull)imageEntry;
        [Export("screenlet:onImageEntrySelected:")]
        void ScreenletImageSelected(ImageGalleryScreenlet screenlet, ImageEntry imageEntry);

        // @optional -(void)screenlet:(ImageGalleryScreenlet * _Nonnull)screenlet onImageEntryDeleted:(ImageEntry * _Nonnull)imageEntry;
        [Export("screenlet:onImageEntryDeleted:")]
        void ScreenletImageDeleted(ImageGalleryScreenlet screenlet, ImageEntry imageEntry);

        // @optional -(void)screenlet:(ImageGalleryScreenlet * _Nonnull)screenlet onImageEntryDeleteError:(NSError * _Nonnull)error;
        [Export("screenlet:onImageEntryDeleteError:")]
        void ScreenletImageDeleteError(ImageGalleryScreenlet screenlet, NSError error);

        // @optional -(void)screenlet:(ImageGalleryScreenlet * _Nonnull)screenlet onImageUploadStart:(ImageEntryUpload * _Nonnull)imageEntryUpload;
        [Export("screenlet:onImageUploadStart:")]
        void ScreenletImageUploadStart(ImageGalleryScreenlet screenlet, ImageEntryUpload imageEntryUpload);

        // @optional -(void)screenlet:(ImageGalleryScreenlet * _Nonnull)screenlet onImageUploadProgress:(ImageEntryUpload * _Nonnull)imageEntryUpload totalBytesSent:(uint64_t)totalBytesSent totalBytesToSend:(uint64_t)totalBytesToSend;
        [Export("screenlet:onImageUploadProgress:totalBytesSent:totalBytesToSend:")]
        void ScreenletImageUploadProgress(ImageGalleryScreenlet screenlet, ImageEntryUpload imageEntryUpload, ulong totalBytesSent, ulong totalBytesToSend);

        // @optional -(void)screenlet:(ImageGalleryScreenlet * _Nonnull)screenlet onImageUploadError:(NSError * _Nonnull)error;
        [Export("screenlet:onImageUploadError:")]
        void ScreenletImageUploadError(ImageGalleryScreenlet screenlet, NSError error);

        // @optional -(void)screenlet:(ImageGalleryScreenlet * _Nonnull)screenlet onImageUploaded:(ImageEntry * _Nonnull)image;
        [Export("screenlet:onImageUploaded:")]
        void ScreenletImageUploaded(ImageGalleryScreenlet screenlet, ImageEntry image);

        // @optional -(BOOL)screenlet:(ImageGalleryScreenlet * _Nonnull)screenlet onImageUploadDetailViewCreated:(ImageUploadDetailViewBase * _Nonnull)view __attribute__((warn_unused_result));
        //[Export("screenlet:onImageUploadDetailViewCreated:")]
        //bool ScreenletImageUploadDetailViewCreated(ImageGalleryScreenlet screenlet, ImageUploadDetailViewBase view);
    }
}
