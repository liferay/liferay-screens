using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;
using UIKit;

namespace LiferayScreens
{
    // @interface DDLFormTableView : DDLFormView <UIScrollViewDelegate, UITableViewDelegate, UITableViewDataSource>
    [BaseType(typeof(DDLFormView))]
    interface DDLFormTableView : IUIScrollViewDelegate, IUITableViewDelegate, IUITableViewDataSource
    {
        // @property (nonatomic, strong) UITableView * _Nullable tableView __attribute__((iboutlet));
        [NullAllowed, Export("tableView", ArgumentSemantic.Strong)]
        UITableView TableView { get; set; }

        // @property (nonatomic, strong) DDLRecord * _Nullable record;
        [NullAllowed, Export("record", ArgumentSemantic.Strong)]
        DDLRecord Record { get; set; }

        // @property (copy, nonatomic) NSString * _Nonnull themeName;
        [Export("themeName")]
        string ThemeName { get; set; }

        // -(void)refresh;
        [Export("refresh")]
        void Refresh();

        // -(BOOL)resignFirstResponder __attribute__((warn_unused_result));
        [Export("resignFirstResponder")]
        bool ResignFirstResponder();

        // -(BOOL)becomeFirstResponder __attribute__((warn_unused_result));
        [Export("becomeFirstResponder")]
        bool BecomeFirstResponder();

        // -(void)onShow;
        [Export("onShow")]
        void OnShow();

        // -(void)onHide;
        [Export("onHide")]
        void OnHide();

        // -(void)layoutWhenKeyboardHidden;
        [Export("layoutWhenKeyboardHidden")]
        void LayoutWhenKeyboardHidden();

        // -(NSInteger)tableView:(UITableView * _Nonnull)tableView numberOfRowsInSection:(NSInteger)section __attribute__((warn_unused_result));
        [Export("ddlFormTableView:numberOfRowsInSection:")]
        nint TableViewNumberOfRowsInSection(UITableView tableView, nint section);

        // -(UITableViewCell * _Nonnull)tableView:(UITableView * _Nonnull)tableView cellForRowAtIndexPath:(NSIndexPath * _Nonnull)indexPath __attribute__((warn_unused_result));
        [Export("ddlFormTableView:cellForRowAtIndexPath:")]
        UITableViewCell TableViewCellForRowAtIndexPath(UITableView tableView, NSIndexPath indexPath);

        // -(CGFloat)tableView:(UITableView * _Nonnull)tableView heightForRowAtIndexPath:(NSIndexPath * _Nonnull)indexPath __attribute__((warn_unused_result));
        [Export("ddlFormTableView:heightForRowAtIndexPath:")]
        nfloat TableViewHeightForRowAtIndexPath(UITableView tableView, NSIndexPath indexPath);

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
