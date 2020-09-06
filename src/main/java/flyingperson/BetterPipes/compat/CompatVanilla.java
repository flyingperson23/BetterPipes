package flyingperson.BetterPipes.compat;

import flyingperson.BetterPipes.util.BlockWrapper;
import flyingperson.BetterPipes.util.Utils;
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

        if (block.state.getBlock() == Blocks.PISTON) {
            connections.add(block.world.getBlockState(block.pos).getValue(BlockPistonBase.FACING));
        }

        if (block.state.getBlock() instanceof BlockTrapDoor) {
            connections.add(block.world.getBlockState(block.pos).getValue(BlockTrapDoor.HALF) == BlockTrapDoor.DoorHalf.BOTTOM ? EnumFacing.DOWN : EnumFacing.UP);
            connections.add(block.world.getBlockState(block.pos).getValue(BlockTrapDoor.FACING).getOpposite());
        }

        if (block.state.getBlock() instanceof BlockRedstoneRepeater) {
            connections.add(block.world.getBlockState(block.pos).getValue(BlockRedstoneRepeater.FACING));
        }

        if (block.state.getBlock() instanceof BlockRedstoneComparator) {
            connections.add(block.world.getBlockState(block.pos).getValue(BlockRedstoneComparator.FACING));
        }

        if (block.state.getBlock() instanceof BlockAnvil) {
            connections.add(block.world.getBlockState(block.pos).getValue(BlockAnvil.FACING));
            connections.add(block.world.getBlockState(block.pos).getValue(BlockAnvil.FACING).getOpposite());
        }

        if (block.state.getBlock() == Blocks.ENDER_CHEST) {
            connections.add(block.world.getBlockState(block.pos).getValue(BlockEnderChest.FACING));
        }

        //if (block.state.getBlock() == Blocks.CHEST) {
        //    connections.add(block.world.getBlockState(block.pos).getValue(BlockChest.FACING));
        //}

        return connections;
    }

    @Override
    public boolean isAcceptable(BlockWrapper block) {
        return block.state.getBlock() == Blocks.FURNACE
                || block.state.getBlock() == Blocks.DROPPER
                || block.state.getBlock() == Blocks.DISPENSER
                || block.state.getBlock() == Blocks.HOPPER
                || (block.state.getBlock() == Blocks.PISTON && !block.world.isBlockPowered(block.pos))
                || block.state.getBlock() instanceof BlockTrapDoor
                || block.state.getBlock() instanceof BlockRedstoneRepeater
                || block.state.getBlock() instanceof BlockRedstoneComparator
                || block.state.getBlock() == Blocks.ANVIL
                || block.state.getBlock() == Blocks.ENDER_CHEST;
                //|| block.state.getBlock() instanceof BlockChest;
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

            if (block.state.getBlock() == Blocks.PISTON) {
                block.world.setBlockState(block.pos, block.world.getBlockState(block.pos).withProperty(BlockPistonBase.FACING, direction));
            }

            if (block.state.getBlock() instanceof BlockTrapDoor) {
                if (Utils.isHorizontal(direction)) {
                    block.world.setBlockState(block.pos, block.world.getBlockState(block.pos).withProperty(BlockTrapDoor.FACING, direction.getOpposite()));
                } else {
                    if (direction == EnumFacing.UP) {
                        block.world.setBlockState(block.pos, block.world.getBlockState(block.pos).withProperty(BlockTrapDoor.HALF, BlockTrapDoor.DoorHalf.TOP));
                    }
                    if (direction == EnumFacing.DOWN) {
                        block.world.setBlockState(block.pos, block.world.getBlockState(block.pos).withProperty(BlockTrapDoor.HALF, BlockTrapDoor.DoorHalf.BOTTOM));
                    }
                }
            }

            if (block.state.getBlock() instanceof BlockRedstoneRepeater) {
                if (Utils.isHorizontal(direction)) {
                    block.world.setBlockState(block.pos, block.world.getBlockState(block.pos).withProperty(BlockRedstoneRepeater.FACING, direction));
                }
            }

            if (block.state.getBlock() instanceof BlockRedstoneComparator) {
                if (Utils.isHorizontal(direction)) {
                    block.world.setBlockState(block.pos, block.world.getBlockState(block.pos).withProperty(BlockRedstoneComparator.FACING, direction));
                }
            }

            if (block.state.getBlock() == Blocks.ANVIL) {
                if (Utils.isHorizontal(direction)) {
                    block.world.setBlockState(block.pos, block.world.getBlockState(block.pos).withProperty(BlockAnvil.FACING, direction));
                }
            }

            if (block.state.getBlock() == Blocks.ENDER_CHEST) {
                if (Utils.isHorizontal(direction)) {
                    block.world.setBlockState(block.pos, block.world.getBlockState(block.pos).withProperty(BlockEnderChest.FACING, direction));
                }
            }

            //if (block.state.getBlock() instanceof BlockChest) {
            //    if (Utils.isHorizontal(direction)) {
            //        block.world.setBlockState(block.pos, block.world.getBlockState(block.pos).withProperty(BlockChest.FACING, direction));
            //    }
            //}

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
        blocks.add(Blocks.PISTON);
        blocks.add(Blocks.TRAPDOOR);
        blocks.add(Blocks.IRON_TRAPDOOR);
        blocks.add(Blocks.POWERED_REPEATER);
        blocks.add(Blocks.UNPOWERED_REPEATER);
        blocks.add(Blocks.POWERED_COMPARATOR);
        blocks.add(Blocks.UNPOWERED_COMPARATOR);
        blocks.add(Blocks.ANVIL);
        blocks.add(Blocks.ENDER_CHEST);
        //blocks.add(Blocks.CHEST);
        return blocks;
    }

    @Override
    public float getBreakSpeed() {
        return 10f;
    }
}
