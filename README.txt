Makes mods from other pipes work like GT6 pipes
Mods supported:
Vanilla
Gregtech Community Edition
AE2 (dense only)
Buildcraft
Cyclic
EnderIO
Immersive Engineering (WIP)
Logistics Pipes (WIP)
Mekanism
PneumaticCraft: Repressurized
Thermal Dynamics

Adding support for your mod:

To add compatibility for your pipes:

- Create a class extending CompatBaseTE if your pipes are tileentities, or implement ICompatBase if they're blocks
- Check the javadoc descriptions or look at my implementations to see how to implement the required methods
- If all requied mods are loaded and you want compatibility to be enabled, add a new instance of your compat class to BetterPipes.instance.COMPAT_LIST in PostInit

To add compatibility for your wrench:

- Create a class implementing IWrenchProvider
- Check the javadoc descriptions or look at my implementation to see how to implement the required methods
- If you want to enable compatibility, add a new instance of your wrench provider to BetterPipes.instance.WRENCH_PROVIDERS in PostInit