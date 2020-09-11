package buildcraft.transport.pipe.behaviour;

import net.minecraft.util.EnumFacing;

public class BCPipeUtils {
    public static boolean canConnect(PipeBehaviourDirectional p, EnumFacing f) {
        return p.canFaceDirection(f);
    }
    public static void setConnection(PipeBehaviourDirectional p, EnumFacing f) {
        if (canConnect(p, f)) p.setCurrentDir(f);
    }
    public static EnumFacing getConnection(PipeBehaviourDirectional p) {
        return p.getCurrentDir();
    }
}
