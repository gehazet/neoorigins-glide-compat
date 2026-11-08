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

## Versions
- 1.0.0: Clockwork / NeoOrigins natural glide fix
- 1.1.0: jump mid-air always starts gliding even with Create Jetpack's "Active Elytra" keybind set
- 1.2.0: the jump fix only applies to NeoOrigins natural gliders (Human + elytra keeps default behaviour)

## Build
`./build.sh` (needs JDK 21). Output: `build/glidecompat-<version>-neoforge-1.21.1.jar`


CurseForge: https://www.curseforge.com/minecraft/mc-mods (project ID 1788351)

License: MIT
