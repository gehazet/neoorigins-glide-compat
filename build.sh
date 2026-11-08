#!/usr/bin/env bash
# Builds glidecompat without Gradle. The "stubs" folder only contains tiny empty
# placeholder classes with the right names so javac can compile the mixins;
# the real classes come from Minecraft/NeoForge/NeoOrigins at runtime.
set -e
cd "$(dirname "$0")"
VERSION=$(grep -m1 '^version' src/main/resources/META-INF/neoforge.mods.toml | cut -d'"' -f2)
rm -rf build && mkdir -p build/classes build/jar
javac --release 21 -cp stubs -sourcepath stubs -d build/classes $(find stubs -name "*.java") $(find src/main/java -name "*.java")
mkdir -p build/jar/dev/gehazet/glidecompat/mixin
cp build/classes/dev/gehazet/glidecompat/mixin/*.class build/jar/dev/gehazet/glidecompat/mixin/
cp -r src/main/resources/* build/jar/
echo '{"pack":{"description":"NeoOrigins Glide Compat","pack_format":34}}' > build/jar/pack.mcmeta
(cd build/jar && jar cfm "../glidecompat-$VERSION-neoforge-1.21.1.jar" ../../MANIFEST.MF .)
echo "Built build/glidecompat-$VERSION-neoforge-1.21.1.jar"
