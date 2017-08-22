using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface ImageGalleryScreenlet : BaseListScreenlet
    [BaseType(typeof(BaseListScreenlet))]
    interface ImageGalleryScreenlet
    {
        // @property (readonly, copy, nonatomic, class) NSString * _Nonnull DeleteImageAction;
        [Static]
        [Export("DeleteImageAction")]
        string DeleteImageAction { get; }

        // @property (readonly, copy, nonatomic, class) NSString * _Nonnull UploadImageAction;
        [Static]
        [Export("UploadImageAction")]
        string UploadImageAction { get; }

        // @property (readonly, copy, nonatomic, class) NSString * _Nonnull EnqueueUploadAction;
        [Static]
        [Export("EnqueueUploadAction")]
        string EnqueueUploadAction { get; }

        // @property (nonatomic) int64_t repositoryId;
        [Export("repositoryId")]
        long RepositoryId { get; set; }

        // @property (nonatomic) int64_t folderId;
        [Export("folderId")]
        long FolderId { get; set; }

        // @property (copy, nonatomic) NSString * _Nonnull mimeTypes;
        [Export("mimeTypes")]
        string MimeTypes { get; set; }

        // @property (copy, nonatomic) NSString * _Nonnull filePrefix;
        [Export("filePrefix")]
        string FilePrefix { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable offlinePolicy;
        [NullAllowed, Export("offlinePolicy")]
        string OfflinePolicy { get; set; }

        [Wrap("WeakImageGalleryDelegate")]
        [NullAllowed]
        ImageGalleryScreenletDelegate ImageGalleryDelegate { get; }

        // @property (readonly, nonatomic, strong) id<ImageGalleryScreenletDelegate> _Nullable imageGalleryDelegate;
        [NullAllowed, Export("imageGalleryDelegate", ArgumentSemantic.Strong)]
        NSObject WeakImageGalleryDelegate { get; }

        // @property (readonly, nonatomic, strong) id<ImageGalleryViewModel> _Nonnull viewModel;
        [Export("viewModel", ArgumentSemantic.Strong)]
        IImageGalleryViewModel ViewModel { get; }

        // @property (copy, nonatomic) NSString * _Nonnull uploadDetailViewName;
        [Export("uploadDetailViewName")]
        string UploadDetailViewName { get; set; }

        // @property (readonly, copy, nonatomic) NSArray<NSString *> * _Nonnull DefaultMimeTypes;
        [Export("DefaultMimeTypes", ArgumentSemantic.Copy)]
        string[] DefaultMimeTypes { get; }

        // -(void)cancelUploads;
        [Export("cancelUploads")]
        void CancelUploads();

        // -(void)deleteImageCache;
        [Export("deleteImageCache")]
        void DeleteImageCache();

        // -(void)startMediaSelectorAndUpload;
        [Export("startMediaSelectorAndUpload")]
        void StartMediaSelectorAndUpload();

        // -(void)showDetailUploadView:(ImageEntryUpload * _Nonnull)imageUpload;
        [Export("showDetailUploadView:")]
        void ShowDetailUploadView(ImageEntryUpload imageUpload);

        // -(void)deleteImageEntry:(ImageEntry * _Nonnull)imageEntry;
        [Export("deleteImageEntry:")]
        void DeleteImageEntry(ImageEntry imageEntry);

        // -(void)onCreated;
        [Export("onCreated")]
        void OnCreated();

        // -(void)onShow;
        [Export("onShow")]
        void OnShow();

        // -(BOOL)performActionWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender;
        [Export("performActionWithName:sender:")]
        bool PerformActionWithName(string name, [NullAllowed] NSObject sender);

        // -(Interactor * _Nullable)createInteractorWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender __attribute__((warn_unused_result));
        [Export("createInteractorWithName:sender:")]
        [return: NullAllowed]
        Interactor CreateInteractorWithName(string name, [NullAllowed] NSObject sender);

        // -(void)onLoadPageErrorWithPage:(NSInteger)page error:(NSError * _Nonnull)error;
        [Export("onLoadPageErrorWithPage:error:")]
        void OnLoadPageErrorWithPage(nint page, NSError error);

        // -(void)onLoadPageResultWithPage:(NSInteger)page rows:(NSArray * _Nonnull)rows rowCount:(NSInteger)rowCount;
        [Export("onLoadPageResultWithPage:rows:rowCount:")]
        //[Verify(StronglyTypedNSArray)]
        void OnLoadPageResultWithPage(nint page, NSObject[] rows, nint rowCount);

        // -(void)onSelectedRow:(id _Nonnull)row;
        [Export("onSelectedRow:")]
        void OnSelectedRow(NSObject row);

        // -(BaseListPageLoadInteractor * _Nonnull)createPageLoadInteractorWithPage:(NSInteger)page computeRowCount:(BOOL)computeRowCount __attribute__((warn_unused_result));
        //[Export("createPageLoadInteractorWithPage:computeRowCount:")]
        //BaseListPageLoadInteractor CreatePageLoadInteractorWithPage(nint page, bool computeRowCount);

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
        [Export("initWithFrame:themeName:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame, [NullAllowed] string themeName);
    }
}
