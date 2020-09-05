package flyingperson.BetterPipes.compat;

import flyingperson.BetterPipes.util.BlockWrapper;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CompatVanilla implements ICompatBase {

    @Override
    public boolean canConnect(BlockWrapper block, EnumFacing direction) {
        return !getConnections(block).contains(direction);
    }

    @Override
    public ArrayList<EnumFacing> getConnections(BlockWrapper block) {
        ArrayList<EnumFacing> connections = new ArrayList<>();
        if (block.state.getBlock() == Blocks.FURNACE) {
            connections.add(block.world.getBlockState(block.pos).getValue(BlockFurnace.FACING));
        }
        if (block.state.getBlock() == Blocks.DROPPER) {
            connections.add(block.world.getBlockState(block.pos).getValue(BlockDropper.FACING));
        }
        if (block.state.getBlock() == Blocks.DISPENSER) {
            connections.add(block.world.getBlockState(block.pos).getValue(BlockDispenser.FACING));
        }
        if (block.state.getBlock() == Blocks.HOPPER) {
            connections.add(block.world.getBlockState(block.pos).getValue(BlockHopper.FACING));
        }
        return connections;
    }

    @Override
    public boolean isAcceptable(BlockWrapper block) {
        return block.state.getBlock() == Blocks.FURNACE
                || block.state.getBlock() == Blocks.DROPPER
                || block.state.getBlock() == Blocks.DISPENSER
                || block.state.getBlock() == Blocks.HOPPER;
    }

    @Override
    public void connect(BlockWrapper block, EnumFacing direction, EntityPlayer player) {
        if (!block.world.isRemote) {
            if (block.state.getBlock() == Blocks.FURNACE) {
                if (direction != EnumFacing.UP && direction != EnumFacing.DOWN) {
                    block.world.setBlockState(block.pos, block.world.getBlockState(block.pos).withProperty(BlockFurnace.FACING, direction));
                }
            }

            if (block.state.getBlock() == Blocks.DROPPER) {
                block.world.setBlockState(block.pos, block.world.getBlockState(block.pos).withProperty(BlockDropper.FACING, direction));
            }

            if (block.state.getBlock() == Blocks.DISPENSER) {
                block.world.setBlockState(block.pos, block.world.getBlockState(block.pos).withProperty(BlockDispenser.FACING, direction));
            }

            if (block.state.getBlock() == Blocks.HOPPER) {
                if (direction != EnumFacing.UP) {
                    block.world.setBlockState(block.pos, block.world.getBlockState(block.pos).withProperty(BlockHopper.FACING, direction));
                }
            }

            block.world.notifyBlockUpdate(block.pos, block.state, block.state, 3);
        }
    }

    @Override
    public void disconnect(BlockWrapper block, EnumFacing direction, EntityPlayer player) {

    }

    @Override
    public Collection<ItemStack> getDrops(BlockWrapper block, IBlockState blockState) {
        return block.state.getBlock().getDrops(block.world, block.pos, block.state, 0);
    }

    @Override
    public List<Block> getAcceptedBlocks() {
        ArrayList<Block> blocks = new ArrayList<>();
        blocks.add(Blocks.FURNACE);
        blocks.add(Blocks.DROPPER);
        blocks.add(Blocks.DISPENSER);
        blocks.add(Blocks.HOPPER);
        return blocks;
    }

    @Override
    public float getBreakSpeed() {
        return 10f;
    }
}
