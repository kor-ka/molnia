//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/ex3ndr/Develop/actor-model/library/actor-cocoa-base/build/java/im/actor/model/entity/compat/content/ObsoleteFastThumb.java
//

#ifndef _ImActorModelEntityCompatContentObsoleteFastThumb_H_
#define _ImActorModelEntityCompatContentObsoleteFastThumb_H_

#include "J2ObjC_header.h"
#include "im/actor/model/droidkit/bser/BserObject.h"

@class APFastThumb;
@class BSBserValues;
@class BSBserWriter;
@class IOSByteArray;
@class ImActorModelEntityContentInternalLocalFastThumb;

@interface ImActorModelEntityCompatContentObsoleteFastThumb : BSBserObject

#pragma mark Public

- (instancetype)initWithByteArray:(IOSByteArray *)data;

- (jint)getH;

- (IOSByteArray *)getImage;

- (jint)getW;

- (void)parseWithBSBserValues:(BSBserValues *)values;

- (void)serializeWithBSBserWriter:(BSBserWriter *)writer;

- (APFastThumb *)toApiFastThumb;

- (ImActorModelEntityContentInternalLocalFastThumb *)toFastThumb;

@end

J2OBJC_EMPTY_STATIC_INIT(ImActorModelEntityCompatContentObsoleteFastThumb)

FOUNDATION_EXPORT void ImActorModelEntityCompatContentObsoleteFastThumb_initWithByteArray_(ImActorModelEntityCompatContentObsoleteFastThumb *self, IOSByteArray *data);

FOUNDATION_EXPORT ImActorModelEntityCompatContentObsoleteFastThumb *new_ImActorModelEntityCompatContentObsoleteFastThumb_initWithByteArray_(IOSByteArray *data) NS_RETURNS_RETAINED;

J2OBJC_TYPE_LITERAL_HEADER(ImActorModelEntityCompatContentObsoleteFastThumb)

#endif // _ImActorModelEntityCompatContentObsoleteFastThumb_H_
