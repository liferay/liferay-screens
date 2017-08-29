using CoreGraphics;
using Foundation;
using System;
using ObjCRuntime;
using UIKit;

namespace LiferayScreens
{
    // @interface BaseListCollectionView : BaseListView <UICollectionViewDataSource, UICollectionViewDelegate, UIScrollViewDelegate>
    [BaseType(typeof(BaseListView))]
    interface BaseListCollectionView : IUICollectionViewDataSource, IUICollectionViewDelegate, IUIScrollViewDelegate
    {
        // @property (nonatomic, strong) UICollectionView * _Nullable collectionView __attribute__((iboutlet));
        [NullAllowed, Export("collectionView", ArgumentSemantic.Strong)]
        UICollectionView CollectionView { get; set; }

        // -(void)onCreated;
        [Export("onCreated")]
        void OnCreated();

        // -(void)onShow;
        [Export("onShow")]
        void OnShow();

        // -(void)onFinishInteraction:(id _Nullable)result error:(NSError * _Nullable)error;
        [Export("onFinishInteraction:error:")]
        void OnFinishInteraction([NullAllowed] NSObject result, [NullAllowed] NSError error);

        // -(NSInteger)collectionView:(UICollectionView * _Nonnull)collectionView numberOfItemsInSection:(NSInteger)section __attribute__((warn_unused_result));
        [Export("collectionView:numberOfItemsInSection:")]
        new nint GetItemsCount(UICollectionView collectionView, nint section);

        // -(NSInteger)numberOfSectionsInCollectionView:(UICollectionView * _Nonnull)collectionView __attribute__((warn_unused_result));
        [Export("numberOfSectionsInCollectionView:")]
        nint NumberOfSectionsInCollectionView(UICollectionView collectionView);

        // -(UICollectionViewCell * _Nonnull)collectionView:(UICollectionView * _Nonnull)collectionView cellForItemAtIndexPath:(NSIndexPath * _Nonnull)indexPath __attribute__((warn_unused_result));
        [Export("collectionView:cellForItemAtIndexPath:")]
        new UICollectionViewCell GetCell(UICollectionView collectionView, NSIndexPath indexPath);

        // -(void)collectionView:(UICollectionView * _Nonnull)collectionView didSelectItemAtIndexPath:(NSIndexPath * _Nonnull)indexPath;
        [Export("collectionView:didSelectItemAtIndexPath:")]
        void CollectionViewDidSelectItemAtIndexPath(UICollectionView collectionView, NSIndexPath indexPath);

        // -(void)collectionView:(UICollectionView * _Nonnull)collectionView willDisplayCell:(UICollectionViewCell * _Nonnull)cell forItemAtIndexPath:(NSIndexPath * _Nonnull)indexPath;
        [Export("collectionView:willDisplayCell:forItemAtIndexPath:")]
        void CollectionViewWillDisplayCellForItemAtIndexPath(UICollectionView collectionView, UICollectionViewCell cell, NSIndexPath indexPath);

        // -(CGSize)collectionView:(UICollectionView * _Nonnull)collectionView layout:(UICollectionViewLayout * _Nonnull)collectionViewLayout sizeForItemAtIndexPath:(NSIndexPath * _Nonnull)indexPath __attribute__((warn_unused_result));
        [Export("collectionView:layout:sizeForItemAtIndexPath:")]
        CGSize CollectionViewLayoutSizeForItemAtIndexPath(UICollectionView collectionView, UICollectionViewLayout collectionViewLayout, NSIndexPath indexPath);

        // -(void)doConfigureCollectionView:(UICollectionView * _Nonnull)collectionView;
        [Export("doConfigureCollectionView:")]
        void DoConfigureCollectionView(UICollectionView collectionView);

        // -(UICollectionViewLayout * _Nonnull)doCreateLayout __attribute__((warn_unused_result));
        [Export("doCreateLayout")]
        UICollectionViewLayout DoCreateLayout();

        // -(UICollectionViewCell * _Nonnull)doDequeueReusableCell:(NSIndexPath * _Nonnull)indexPath object:(id _Nullable)object __attribute__((warn_unused_result));
        [Export("doDequeueReusableCell:object:")]
        UICollectionViewCell DoDequeueReusableCell(NSIndexPath indexPath, [NullAllowed] NSObject @object);

        // -(void)doFillLoadedCellWithIndexPath:(NSIndexPath * _Nonnull)indexPath cell:(UICollectionViewCell * _Nonnull)cell object:(id _Nonnull)object;
        [Export("doFillLoadedCellWithIndexPath:cell:object:")]
        void DoFillLoadedCellWithIndexPath(NSIndexPath indexPath, UICollectionViewCell cell, NSObject @object);

        // -(void)doFillInProgressCellWithIndexPath:(NSIndexPath * _Nonnull)indexPath cell:(UICollectionViewCell * _Nonnull)cell;
        [Export("doFillInProgressCellWithIndexPath:cell:")]
        void DoFillInProgressCellWithIndexPath(NSIndexPath indexPath, UICollectionViewCell cell);

        // -(void)doRegisterCellNibs;
        [Export("doRegisterCellNibs")]
        void DoRegisterCellNibs();

        // -(void)doRegisterLoadMoreCell;
        [Export("doRegisterLoadMoreCell")]
        void DoRegisterLoadMoreCell();

        // -(NSString * _Nonnull)doGetCellIdWithIndexPath:(NSIndexPath * _Nonnull)indexPath object:(id _Nullable)object __attribute__((warn_unused_result));
        [Export("doGetCellIdWithIndexPath:object:")]
        string DoGetCellIdWithIndexPath(NSIndexPath indexPath, [NullAllowed] NSObject @object);

        // -(NSString * _Nonnull)doGetLoadMoreCellId __attribute__((warn_unused_result));
        [Export("doGetLoadMoreCellId")]
        string DoGetLoadMoreCellId();

        // -(UICollectionViewCell * _Nonnull)doCreateCell:(NSString * _Nonnull)cellId __attribute__((warn_unused_result));
        [Export("doCreateCell:")]
        UICollectionViewCell DoCreateCell(string cellId);

        // -(void)doFillLoadMoreCell:(UICollectionViewCell * _Nonnull)cell;
        [Export("doFillLoadMoreCell:")]
        void DoFillLoadMoreCell(UICollectionViewCell cell);

        // -(CGSize)doGetLoadMoreViewSize:(UICollectionViewFlowLayout * _Nonnull)layout __attribute__((warn_unused_result));
        [Export("doGetLoadMoreViewSize:")]
        CGSize DoGetLoadMoreViewSize(UICollectionViewFlowLayout layout);

        // -(void)updateRefreshControl;
        [Export("updateRefreshControl")]
        void UpdateRefreshControl();

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
