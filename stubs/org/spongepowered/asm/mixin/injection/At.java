package org.spongepowered.asm.mixin.injection; import java.lang.annotation.*;
@Retention(RetentionPolicy.RUNTIME) public @interface At { String value(); String target() default ""; boolean remap() default true; int ordinal() default -1; int opcode() default -1; }
