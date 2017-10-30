using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface DDLListScreenlet : BaseListScreenlet
    [BaseType(typeof(BaseListScreenlet))]
    interface DDLListScreenlet
    {
        // @property (nonatomic) int64_t userId;
        [Export("userId")]
        long UserId { get; set; }

        // @property (nonatomic) int64_t recordSetId;
        [Export("recordSetId")]
        long RecordSetId { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable labelFields;
        [NullAllowed, Export("labelFields")]
        string LabelFields { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable offlinePolicy;
        [NullAllowed, Export("offlinePolicy")]
        string OfflinePolicy { get; set; }

        [Wrap("WeakDdlListDelegate")]
        [NullAllowed]
        DDLListScreenletDelegate DdlListDelegate { get; }

        // @property (readonly, nonatomic, strong) id<DDLListScreenletDelegate> _Nullable ddlListDelegate;
        [NullAllowed, Export("ddlListDelegate", ArgumentSemantic.Strong)]
        NSObject WeakDdlListDelegate { get; }

        // @property (readonly, nonatomic, strong) id<DDLListViewModel> _Nonnull viewModel;
        [Export("viewModel", ArgumentSemantic.Strong)]
        IDDLListViewModel ViewModel { get; }

        // -(BaseListPageLoadInteractor * _Nonnull)createPageLoadInteractorWithPage:(NSInteger)page computeRowCount:(BOOL)computeRowCount __attribute__((warn_unused_result));
        //[Export("createPageLoadInteractorWithPage:computeRowCount:")]
        //BaseListPageLoadInteractor CreatePageLoadInteractorWithPage(nint page, bool computeRowCount);

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
        [Export("initWithFrame:themeName:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame, [NullAllowed] string themeName);
    }
}
