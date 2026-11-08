# NeoOrigins Glide Compat (NeoForge 1.21.1)

Fixes NeoOrigins natural gliding (Elytrian, Phantom, Hiveling, Windwalker...) when another mod,
such as Clockwork, also redirects `ItemStack#canElytraFly` in `LocalPlayer#aiStep`.

Mixin can only apply one `@Redirect` per call, so NeoOrigins' redirect gets skipped and natural
glide never starts on the client. This mod adds a MixinExtras `@ModifyExpressionValue` on the same
call (priority 500). It chains on top of whichever redirect won and also allows gliding when the
player has the NeoOrigins `natural_glide` capability.

- Client-side only (harmless on servers).
- Requires NeoOrigins. Does not modify NeoOrigins or Clockwork.
- License: MIT

## Build
`./build.sh` (needs JDK 21). Output: `build/glidecompat-<version>-neoforge-1.21.1.jar`

The `stubs` folder only contains tiny empty placeholder classes with the right names so `javac`
can compile the mixin. The real classes come from Minecraft / NeoForge / NeoOrigins at runtime.
