package flyingperson.BetterPipes.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

public class BlockWrapper {
    public BlockPos pos;
    public IBlockState state;
    public World world;
    public BlockWrapper(BlockPos pos, IBlockState state, World world) {
        this.pos = pos;
        this.state = state;
        this.world = world;
    }
    public BlockWrapper(BlockPos pos, EntityPlayer player) {
        this(pos, player.world.getBlockState(pos), player.world);
    }
    public BlockWrapper(BlockEvent e) {
        this(e.getPos(), e.getState(), e.getWorld());
    }
    public BlockWrapper offset(EnumFacing direction) {
        BlockPos offsetBlock = pos.offset(direction);
        return new BlockWrapper(offsetBlock, world.getBlockState(offsetBlock), world);
    }
}
