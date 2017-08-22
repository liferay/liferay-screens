using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface BaseListView : BaseScreenletView
    [BaseType(typeof(BaseScreenletView))]
    interface BaseListView
    {
        // @property (readonly, copy, nonatomic, class) NSString * _Nonnull DefaultSection;
        [Static]
        [Export("DefaultSection")]
        string DefaultSection { get; }

        // @property (readonly, nonatomic) NSInteger rowCount;
        [Export("rowCount")]
        nint RowCount { get; }

        // @property (readonly, copy, nonatomic) NSArray<NSString *> * _Nonnull sections;
        [Export("sections", ArgumentSemantic.Copy)]
        string[] Sections { get; }

        // @property (copy, nonatomic) void (^ _Nullable)(id _Nonnull) onSelectedRowClosure;
        [NullAllowed, Export("onSelectedRowClosure", ArgumentSemantic.Copy)]
        Action<NSObject> OnSelectedRowClosure { get; set; }

        // @property (copy, nonatomic) void (^ _Nullable)(NSInteger) fetchPageForRow;
        [NullAllowed, Export("fetchPageForRow", ArgumentSemantic.Copy)]
        Action<nint> FetchPageForRow { get; set; }

        // @property (nonatomic) BOOL loadingRows;
        [Export("loadingRows")]
        bool LoadingRows { get; set; }

        // @property (nonatomic) BOOL moreRows;
        [Export("moreRows")]
        bool MoreRows { get; set; }

        // -(void)clearRows;
        [Export("clearRows")]
        void ClearRows();

        // -(void)deleteRow:(NSString * _Nonnull)section row:(NSInteger)row;
        [Export("deleteRow:row:")]
        void DeleteRow(string section, nint row);

        // -(void)addRow:(NSString * _Nonnull)section element:(id _Nonnull)element;
        [Export("addRow:element:")]
        void AddRow(string section, NSObject element);

        // -(void)updateRow:(NSString * _Nonnull)section row:(NSInteger)row element:(id _Nonnull)element;
        [Export("updateRow:row:element:")]
        void UpdateRow(string section, nint row, NSObject element);

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
