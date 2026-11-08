package com.llamalad7.mixinextras.injector; import java.lang.annotation.*; import org.spongepowered.asm.mixin.injection.At;
@Retention(RetentionPolicy.RUNTIME) @Target(ElementType.METHOD) public @interface ModifyExpressionValue { String[] method(); At[] at(); boolean remap() default true; int require() default -1; }
