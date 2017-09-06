using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface WebContentListScreenlet : BaseListScreenlet
    [BaseType(typeof(BaseListScreenlet))]
    interface WebContentListScreenlet
    {
        // @property (nonatomic) int64_t groupId;
        [Export("groupId")]
        long GroupId { get; set; }

        // @property (nonatomic) int64_t folderId;
        [Export("folderId")]
        long FolderId { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable offlinePolicy;
        [NullAllowed, Export("offlinePolicy")]
        string OfflinePolicy { get; set; }

        [Wrap("WeakWebContentListDelegate")]
        [NullAllowed]
        WebContentListScreenletDelegate WebContentListDelegate { get; }

        // @property (readonly, nonatomic, strong) id<WebContentListScreenletDelegate> _Nullable webContentListDelegate;
        [NullAllowed, Export("webContentListDelegate", ArgumentSemantic.Strong)]
        NSObject WeakWebContentListDelegate { get; }

        // -(BaseListPageLoadInteractor * _Nonnull)createPageLoadInteractorWithPage:(NSInteger)page computeRowCount:(BOOL)computeRowCount __attribute__((warn_unused_result));
        //[Export("createPageLoadInteractorWithPage:computeRowCount:")]
        //BaseListPageLoadInteractor CreatePageLoadInteractorWithPage(nint page, bool computeRowCount);

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

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
        [Export("initWithFrame:themeName:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame, [NullAllowed] string themeName);
    }
}
