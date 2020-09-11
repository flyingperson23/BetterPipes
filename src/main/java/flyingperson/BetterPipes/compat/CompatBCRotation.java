package flyingperson.BetterPipes.compat;

import buildcraft.transport.pipe.Pipe;
import buildcraft.transport.pipe.behaviour.BCPipeUtils;
import buildcraft.transport.pipe.behaviour.PipeBehaviourDirectional;
import buildcraft.transport.tile.TilePipeHolder;
import flyingperson.BetterPipes.util.BlockWrapper;
import flyingperson.BetterPipes.util.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;

public class CompatBCRotation extends CompatBaseRotation{
    @Override
    public boolean canBeRotatedTo(BlockWrapper block, EnumFacing direction) {
        if (isAcceptable(block)) {
            PipeBehaviourDirectional p = (PipeBehaviourDirectional) ((TilePipeHolder) Utils.getTE(block)).getPipe().behaviour;
            return BCPipeUtils.canConnect(p, direction);
        }
        return false;
    }

    @Override
    public ArrayList<EnumFacing> getRotation(BlockWrapper block) {
        ArrayList<EnumFacing> c = new ArrayList<>();
        if (isAcceptable(block)) {
            Pipe p = ((TilePipeHolder) Utils.getTE(block)).getPipe();
            c.add(BCPipeUtils.getConnection((PipeBehaviourDirectional) p.behaviour));
        }
        return c;
    }

    @Override
    public boolean isAcceptable(BlockWrapper block) {
        if (Utils.getTE(block) instanceof TilePipeHolder) {
            TilePipeHolder p = (TilePipeHolder) Utils.getTE(block);
            if (p != null) {
                if (p.getPipe() != null) return p.getPipe().behaviour instanceof PipeBehaviourDirectional;
            }
        }
        return false;
    }

    @Override
    public void rotateTo(BlockWrapper block, EnumFacing direction, EntityPlayer player) {
        if (isAcceptable(block)) {
            PipeBehaviourDirectional p = ((PipeBehaviourDirectional) ((TilePipeHolder) Utils.getTE(block)).getPipe().behaviour);
            if (BCPipeUtils.canConnect(p, direction)) BCPipeUtils.setConnection(p, direction);
        }
    }

    @Override
    public Class<? extends ICompatBase> getMainCompatClass() {
        return CompatBC.class;
    }
}
