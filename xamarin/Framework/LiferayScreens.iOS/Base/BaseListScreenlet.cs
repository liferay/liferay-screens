using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface BaseListScreenlet : BaseScreenlet
    [BaseType(typeof(BaseScreenlet))]
    interface BaseListScreenlet
    {
        // @property (readonly, copy, nonatomic, class) NSString * _Nonnull LoadInitialPageAction;
        [Static]
        [Export("LoadInitialPageAction")]
        string LoadInitialPageAction { get; }

        // @property (readonly, copy, nonatomic, class) NSString * _Nonnull LoadPageAction;
        [Static]
        [Export("LoadPageAction")]
        string LoadPageAction { get; }

        // @property (nonatomic) BOOL autoLoad;
        [Export("autoLoad")]
        bool AutoLoad { get; set; }

        // @property (nonatomic) BOOL refreshControl;
        [Export("refreshControl")]
        bool RefreshControl { get; set; }

        // @property (nonatomic) NSInteger firstPageSize;
        [Export("firstPageSize")]
        nint FirstPageSize { get; set; }

        // @property (nonatomic) NSInteger pageSize;
        [Export("pageSize")]
        nint PageSize { get; set; }

        // @property (copy, nonatomic) NSString * _Nonnull obcClassName;
        [Export("obcClassName")]
        string ObcClassName { get; set; }

        // @property (readonly, nonatomic, strong) BaseListView * _Nonnull baseListView;
        [Export("baseListView", ArgumentSemantic.Strong)]
        BaseListView BaseListView { get; }

        // -(void)onCreated;
        [Export("onCreated")]
        void OnCreated();

        // -(void)onShow;
        [Export("onShow")]
        void OnShow();

        // -(Interactor * _Nullable)createInteractorWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender __attribute__((warn_unused_result));
        [Export("createInteractorWithName:sender:")]
        [return: NullAllowed]
        Interactor CreateInteractorWithName(string name, [NullAllowed] NSObject sender);

        // -(BOOL)onActionWithName:(NSString * _Nonnull)name interactor:(Interactor * _Nonnull)interactor sender:(id _Nullable)sender __attribute__((warn_unused_result));
        [Export("onActionWithName:interactor:sender:")]
        bool OnActionWithName(string name, Interactor interactor, [NullAllowed] NSObject sender);

        // -(BOOL)loadList;
        [Export("loadList")]
        bool LoadList();

        // -(void)loadPageForRow:(NSInteger)row;
        [Export("loadPageForRow:")]
        void LoadPageForRow(nint row);

        // -(NSInteger)pageFromRow:(NSInteger)row __attribute__((warn_unused_result));
        [Export("pageFromRow:")]
        nint PageFromRow(nint row);

        // -(NSInteger)firstRowForPage:(NSInteger)page __attribute__((warn_unused_result));
        [Export("firstRowForPage:")]
        nint FirstRowForPage(nint page);

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
