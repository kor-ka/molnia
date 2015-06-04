//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/ex3ndr/Develop/actor-model/library/actor-cocoa-base/build/java/im/actor/model/api/updates/UpdateParameterChanged.java
//

#ifndef _APUpdateParameterChanged_H_
#define _APUpdateParameterChanged_H_

#include "J2ObjC_header.h"
#include "im/actor/model/network/parser/Update.h"

@class BSBserValues;
@class BSBserWriter;
@class IOSByteArray;

#define APUpdateParameterChanged_HEADER 131

@interface APUpdateParameterChanged : APUpdate

#pragma mark Public

- (instancetype)init;

- (instancetype)initWithNSString:(NSString *)key
                    withNSString:(NSString *)value;

+ (APUpdateParameterChanged *)fromBytesWithByteArray:(IOSByteArray *)data;

- (jint)getHeaderKey;

- (NSString *)getKey;

- (NSString *)getValue;

- (void)parseWithBSBserValues:(BSBserValues *)values;

- (void)serializeWithBSBserWriter:(BSBserWriter *)writer;

- (NSString *)description;

@end

J2OBJC_EMPTY_STATIC_INIT(APUpdateParameterChanged)

J2OBJC_STATIC_FIELD_GETTER(APUpdateParameterChanged, HEADER, jint)

FOUNDATION_EXPORT APUpdateParameterChanged *APUpdateParameterChanged_fromBytesWithByteArray_(IOSByteArray *data);

FOUNDATION_EXPORT void APUpdateParameterChanged_initWithNSString_withNSString_(APUpdateParameterChanged *self, NSString *key, NSString *value);

FOUNDATION_EXPORT APUpdateParameterChanged *new_APUpdateParameterChanged_initWithNSString_withNSString_(NSString *key, NSString *value) NS_RETURNS_RETAINED;

FOUNDATION_EXPORT void APUpdateParameterChanged_init(APUpdateParameterChanged *self);

FOUNDATION_EXPORT APUpdateParameterChanged *new_APUpdateParameterChanged_init() NS_RETURNS_RETAINED;

J2OBJC_TYPE_LITERAL_HEADER(APUpdateParameterChanged)

typedef APUpdateParameterChanged ImActorModelApiUpdatesUpdateParameterChanged;

#endif // _APUpdateParameterChanged_H_
