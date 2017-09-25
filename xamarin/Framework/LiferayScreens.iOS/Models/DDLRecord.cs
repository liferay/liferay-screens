using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface DDLRecord : NSObject <NSCoding>
    [BaseType(typeof(NSObject))]
    [DisableDefaultCtor]
    interface DDLRecord : INSCoding
    {
        // @property (nonatomic, strong) DDMStructure * _Nullable structure;
        [NullAllowed, Export("structure", ArgumentSemantic.Strong)]
        DDMStructure Structure { get; set; }

        // @property (readonly, copy, nonatomic) NSArray<DDMField *> * _Nullable untypedValues;
        [NullAllowed, Export("untypedValues", ArgumentSemantic.Copy)]
        DDMField[] UntypedValues { get; }

        // @property (copy, nonatomic) NSDictionary<NSString *,id> * _Nonnull attributes;
        [Export("attributes", ArgumentSemantic.Copy)]
        NSDictionary<NSString, NSObject> Attributes { get; set; }

        // @property (readonly, copy, nonatomic) NSArray<DDMField *> * _Nonnull fields;
        [Export("fields", ArgumentSemantic.Copy)]
        DDMField[] Fields { get; }

        // -(DDMField * _Nullable)objectForKeyedSubscript:(NSString * _Nonnull)fieldName __attribute__((warn_unused_result));
        [Export("objectForKeyedSubscript:")]
        [return: NullAllowed]
        DDMField ObjectForKeyedSubscript(string fieldName);

        // @property (readonly, copy, nonatomic) NSDictionary<NSString *,id> * _Nonnull values;
        [Export("values", ArgumentSemantic.Copy)]
        NSDictionary<NSString, NSObject> Values { get; }

        // -(instancetype _Nonnull)initWithStructure:(DDMStructure * _Nonnull)structure __attribute__((objc_designated_initializer));
        [Export("initWithStructure:")]
        [DesignatedInitializer]
        IntPtr Constructor(DDMStructure structure);

        // -(instancetype _Nonnull)initWithXsd:(NSString * _Nonnull)xsd locale:(NSLocale * _Nonnull)locale __attribute__((objc_designated_initializer));
        [Export("initWithXsd:locale:")]
        [DesignatedInitializer]
        IntPtr Constructor(string xsd, NSLocale locale);

        // -(instancetype _Nonnull)initWithJson:(NSString * _Nonnull)json locale:(NSLocale * _Nonnull)locale __attribute__((objc_designated_initializer));
        [Export("initWithJson:locale:")]
        [DesignatedInitializer]
        IntPtr ConstructorJson(string json, NSLocale locale);

        // -(instancetype _Nonnull)initWithData:(NSDictionary<NSString *,id> * _Nonnull)data attributes:(NSDictionary<NSString *,id> * _Nonnull)attributes __attribute__((objc_designated_initializer));
        [Export("initWithData:attributes:")]
        [DesignatedInitializer]
        IntPtr Constructor(NSDictionary<NSString, NSObject> data, NSDictionary<NSString, NSObject> attributes);

        // -(instancetype _Nonnull)initWithDataAndAttributes:(NSDictionary<NSString *,id> * _Nonnull)dataAndAttributes __attribute__((objc_designated_initializer));
        [Export("initWithDataAndAttributes:")]
        [DesignatedInitializer]
        IntPtr Constructor(NSDictionary<NSString, NSObject> dataAndAttributes);

        // -(DDMField * _Nullable)fieldByName:(NSString * _Nonnull)name __attribute__((warn_unused_result));
        [Export("fieldByName:")]
        [return: NullAllowed]
        DDMField FieldByName(string name);

        // -(NSArray<DDMField *> * _Nonnull)fieldsByType:(Class _Nonnull)type __attribute__((warn_unused_result));
        [Export("fieldsByType:")]
        DDMField[] FieldsByType(Class type);

        // -(void)updateCurrentValuesWithValues:(NSDictionary<NSString *,id> * _Nonnull)values;
        [Export("updateCurrentValuesWithValues:")]
        void UpdateCurrentValuesWithValues(NSDictionary<NSString, NSObject> values);

        // -(NSInteger)updateCurrentValuesWithXmlValues:(NSString * _Nonnull)xmlValues;
        [Export("updateCurrentValuesWithXmlValues:")]
        nint UpdateCurrentValuesWithXmlValues(string xmlValues);

        // -(void)clearValues;
        [Export("clearValues")]
        void ClearValues();
    }
}
