package org.spongepowered.asm.mixin; import java.lang.annotation.*;
@Retention(RetentionPolicy.CLASS) @Target(ElementType.TYPE) public @interface Mixin { Class<?>[] value(); int priority() default 1000; }
