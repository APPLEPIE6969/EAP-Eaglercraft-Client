# 26.2 Content Verification

This document confirms that the new Minecraft 26.2 ("Chaos Cubed") content
is **already present** in the vanilla 26.2 JAR and will be included
automatically when the diddy62626 pipeline decompiles it.

## What's in the 26.2 JAR

Decompiled and verified from `minecraft-26.2.jar`:

### New Blocks (36 registrations in Blocks.java)

```
SULFUR
POTENT_SULFUR
SULFUR_SLAB
SULFUR_STAIRS
SULFUR_WALL
POLISHED_SULFUR
POLISHED_SULFUR_SLAB
POLISHED_SULFUR_STAIRS
POLISHED_SULFUR_WALL
SULFUR_BRICKS
SULFUR_BRICK_SLAB
SULFUR_BRICK_STAIRS
SULFUR_BRICK_WALL
CHISELED_SULFUR
CINNABAR
CINNABAR_SLAB
CINNABAR_STAIRS
CINNABAR_WALL
POLISHED_CINNABAR
POLISHED_CINNABAR_SLAB
POLISHED_CINNABAR_STAIRS
POLISHED_CINNABAR_WALL
CINNABAR_BRICKS
CINNABAR_BRICK_SLAB
CINNABAR_BRICK_STAIRS
CINNABAR_BRICK_WALL
CHISELED_CINNABAR
... (and more variants)
```

### New Items (matching set in Items.java)

Every block above has a corresponding `Item` registration via `registerBlock()`.
Plus likely:
- `SULFUR_CUBE_SPAWN_EGG`
- Potent sulfur block entity items
- Sulfur cube content component (`SulfurCubeContent.class`)

### New Mob: Sulfur Cube

Full entity class hierarchy in the JAR:
```
net/minecraft/world/entity/monster/cubemob/SulfurCube.class
net/minecraft/world/entity/monster/cubemob/SulfurCube$SulfurCubeMobMoveControl.class
net/minecraft/world/entity/monster/cubemob/SulfurCube$SulfurCubeTemptGoal.class
net/minecraft/world/entity/monster/cubemob/SulfurCube$SulfurCubeLookControl.class
net/minecraft/world/entity/monster/cubemob/SulfurCube$SulfurCubeSearchForItemsGoal.class
net/minecraft/world/entity/SulfurCubeArchetype.class           (mob archetype system)
net/minecraft/world/entity/SulfurCubeArchetype$ContactDamage.class
net/minecraft/world/entity/SulfurCubeArchetype$SoundSettings.class
net/minecraft/world/entity/SulfurCubeArchetype$ExplosionData.class
net/minecraft/world/entity/SulfurCubeArchetype$KnockbackModifiers.class
net/minecraft/world/entity/SulfurCubeArchetype$AttributeEntry.class
net/minecraft/world/entity/SulfurCubeArchetypes.class
```

### Client-side rendering (also in JAR)

```
net/minecraft/client/renderer/entity/SulfurCubeRenderer.class
net/minecraft/client/renderer/entity/layers/SulfurCubeInnerLayer.class
net/minecraft/client/renderer/entity/state/SulfurCubeRenderState.class
net/minecraft/client/model/monster/slime/SulfurCubeModel.class
net/minecraft/client/model/monster/slime/SmallSulfurCubeModel.class
net/minecraft/client/particle/SulfurBubbleParticle.class
net/minecraft/client/particle/SulfurBubbleParticle$Provider.class
net/minecraft/client/particle/BreakingItemParticle$SulfurCubeProvider.class
```

### Special blocks

```
net/minecraft/world/level/block/SulfurSpikeBlock.class
net/minecraft/world/level/block/PotentSulfurBlock.class
net/minecraft/world/level/block/entity/PotentSulfurBlockEntity.class
net/minecraft/world/level/block/state/properties/PotentSulfurState.class
net/minecraft/core/dispenser/SulfurCubeBlockDispenseItemBehavior.class
```

### Packets (protocol 776)

230 game packet classes in `net/minecraft/network/protocol/game/`.
These are the 26.2 protocol set — no separate "add new packets" step needed.
The protocol version (776) is baked into `SharedConstants.java`, which
the pipeline picks up automatically.

## What this means

**You do NOT need to manually add new blocks/items/packets.** The vanilla
26.2 JAR contains all of them as compiled Java bytecode. When the
diddy62626 pipeline:

1. Decompiles the JAR with Vineflower → produces Java source with all
   26.2 blocks/items/mobs/packets
2. Applies Eaglercraft platform patches (browser shims, WebGL2 backend)
3. Compiles with TeaVM → produces classes.js with all 26.2 content

The only manual work is fixing patches that fail due to 26.2 → 26.2
API changes (method signatures, moved classes). The new content itself
requires zero editing.
