package flyingperson.BetterPipes.compat;

import crazypants.enderio.base.machine.base.te.AbstractMachineEntity;
import flyingperson.BetterPipes.util.BlockWrapper;
import flyingperson.BetterPipes.util.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;

public class CompatEnderIORotation extends CompatBaseRotation {
    @Override
    public boolean canBeRotatedTo(BlockWrapper block, EnumFacing direction) {
        return Utils.isHorizontal(direction);
    }

    @Override
    public ArrayList<EnumFacing> getRotation(BlockWrapper block) {
        if (isAcceptable(block)) return Utils.getArrayList(((AbstractMachineEntity) Utils.getTE(block)).getFacing());
        return new ArrayList<>();
    }

    @Override
    public void rotateTo(BlockWrapper block, EnumFacing direction, EntityPlayer player) {
        if (isAcceptable(block) && Utils.isHorizontal(direction)) ((AbstractMachineEntity) Utils.getTE(block)).setFacing(direction);
    }

    @Override
    public Class<? extends ICompatBase> getMainCompatClass() {
        return CompatEnderIO.class;
    }

    @Override
    public boolean isAcceptable(BlockWrapper block) {
        return Utils.getTE(block) instanceof AbstractMachineEntity;
    }
}
