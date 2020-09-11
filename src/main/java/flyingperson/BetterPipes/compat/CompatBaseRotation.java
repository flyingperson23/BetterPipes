package flyingperson.BetterPipes.compat;

import flyingperson.BetterPipes.util.BlockWrapper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class CompatBaseRotation implements ICompatBase {

    @Override
    public Collection<ItemStack> getDrops(BlockWrapper block, IBlockState blockState) {
        try {
            return getMainCompatClass().newInstance().getDrops(block, blockState);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<Block> getAcceptedBlocks() {
        try {
            return getMainCompatClass().newInstance().getAcceptedBlocks();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public float getBreakSpeed() {
        try {
            return getMainCompatClass().newInstance().getBreakSpeed();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return 0f;
    }

    @Override
    public void disconnect(BlockWrapper block, EnumFacing direction, EntityPlayer player) {
    }


    @Override
    public boolean canConnect(BlockWrapper block, EnumFacing direction) {
        return canBeRotatedTo(block, direction);
    }

    public abstract boolean canBeRotatedTo(BlockWrapper block, EnumFacing direction);

    @Override
    public ArrayList<EnumFacing> getConnections(BlockWrapper block) {
        return getRotation(block);
    }

    public abstract ArrayList<EnumFacing> getRotation(BlockWrapper block);

    @Override
    public void connect(BlockWrapper block, EnumFacing direction, EntityPlayer player) {
        rotateTo(block, direction, player);
    }

    public abstract void rotateTo(BlockWrapper block, EnumFacing direction, EntityPlayer player);

    public abstract Class<? extends ICompatBase> getMainCompatClass();

}
