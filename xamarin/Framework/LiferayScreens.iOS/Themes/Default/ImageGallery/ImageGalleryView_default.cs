using CoreGraphics;
using Foundation;
using System;
using UIKit;

namespace LiferayScreens
{
    // @interface ImageGalleryView_default : ImageGalleryCollectionViewBase
    [BaseType(typeof(ImageGalleryCollectionViewBase))]
    interface ImageGalleryView_default
    {
        // @property (readonly, nonatomic, class) NSInteger DefaultColumns;
        [Static]
        [Export("DefaultColumns")]
        nint DefaultColumns { get; }

        // @property (nonatomic) CGFloat spacing;
        [Export("spacing")]
        nfloat Spacing { get; set; }

        // @property (nonatomic) NSInteger columnNumber;
        [Export("columnNumber")]
        nint ColumnNumber { get; set; }

        // -(void)layoutSubviews;
        [Export("layoutSubviews")]
        void LayoutSubviews();

        // -(void)onShow;
        [Export("onShow")]
        void OnShow();

        // -(void)onHide;
        [Export("onHide")]
        void OnHide();

        // -(void)doConfigureCollectionView:(UICollectionView * _Nonnull)collectionView;
        [Export("doConfigureCollectionView:")]
        void DoConfigureCollectionView(UICollectionView collectionView);

        // -(void)doRegisterCellNibs;
        [Export("doRegisterCellNibs")]
        void DoRegisterCellNibs();

        // -(UICollectionViewLayout * _Nonnull)doCreateLayout __attribute__((warn_unused_result));
        [Export("doCreateLayout")]
        UICollectionViewLayout DoCreateLayout();

        // -(void)doFillLoadedCellWithIndexPath:(NSIndexPath * _Nonnull)indexPath cell:(UICollectionViewCell * _Nonnull)cell object:(id _Nonnull)object;
        [Export("doFillLoadedCellWithIndexPath:cell:object:")]
        void DoFillLoadedCellWithIndexPath(NSIndexPath indexPath, UICollectionViewCell cell, NSObject @object);

        // -(void)doFillInProgressCellWithIndexPath:(NSIndexPath * _Nonnull)indexPath cell:(UICollectionViewCell * _Nonnull)cell;
        [Export("doFillInProgressCellWithIndexPath:cell:")]
        void DoFillInProgressCellWithIndexPath(NSIndexPath indexPath, UICollectionViewCell cell);

        // -(NSString * _Nonnull)doGetCellIdWithIndexPath:(NSIndexPath * _Nonnull)indexPath object:(id _Nullable)object __attribute__((warn_unused_result));
        [Export("doGetCellIdWithIndexPath:object:")]
        string DoGetCellIdWithIndexPath(NSIndexPath indexPath, [NullAllowed] NSObject @object);

        // -(void)changeLayout;
        [Export("changeLayout")]
        void ChangeLayout();

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
