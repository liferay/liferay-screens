using Foundation;
using ObjCRuntime;

namespace LiferayScreens
{
    // @protocol DDLListScreenletDelegate <BaseScreenletDelegate>
    [BaseType(typeof(BaseScreenletDelegate))]
    [Protocol, Model]
    interface DDLListScreenletDelegate
    {
        // @optional -(void)screenlet:(DDLListScreenlet * _Nonnull)screenlet onDDLListResponseRecords:(NSArray<DDLRecord *> * _Nonnull)records;
        [Export("screenlet:onDDLListResponseRecords:")]
        void OnDDLListResponseRecords(DDLListScreenlet screenlet, DDLRecord[] records);

        // @optional -(void)screenlet:(DDLListScreenlet * _Nonnull)screenlet onDDLListError:(NSError * _Nonnull)error;
        [Export("screenlet:onDDLListError:")]
        void OnDDLListError(DDLListScreenlet screenlet, NSError error);

        // @optional -(void)screenlet:(DDLListScreenlet * _Nonnull)screenlet onDDLSelectedRecord:(DDLRecord * _Nonnull)record;
        [Export("screenlet:onDDLSelectedRecord:")]
        void OnDDLSelectedRecord(DDLListScreenlet screenlet, DDLRecord record);
    }
}
