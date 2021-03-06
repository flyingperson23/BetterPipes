package flyingperson.BetterPipes.compat;

import flyingperson.BetterPipes.util.BlockWrapper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface ICompatBase {
    /** @param block block to check for connectability
     *  @param direction the direction in which to check connectability
     *  @return is there a connectable block in that direction? */
    boolean canConnect(BlockWrapper block, EnumFacing direction);
    /** @param block block to get connections from
     *  @return ArrayList of active connections on block block */
    ArrayList<EnumFacing> getConnections(BlockWrapper block);
    /** @param block block to test
     *  @return can block te connect to a block of given compat type*/
    boolean isAcceptable(BlockWrapper block);
    /** @param block block to connect
     *  @param direction direction in which to connect
     *  @param player the player connecting/disconnecting the pipe*/
    void connect(BlockWrapper block, EnumFacing direction, EntityPlayer player);
    /** @param block block to disconnect
     *  @param direction the direction of the the block to disconnect from block
     *  @param player the player connecting/disconnecting the pipe*/
    void disconnect(BlockWrapper block, EnumFacing direction, EntityPlayer player);
    /**@param block block to get drops from
     * @param blockState state to get drops from
     * @return list of drops block should drop*/
    Collection<ItemStack> getDrops(BlockWrapper block, IBlockState blockState);
    /**@return list of blocks from this compat module that can be broken with a wrench*/
    List<Block> getAcceptedBlocks();
    /**@return harvest speed for blocks from this module*/
    float getBreakSpeed();
}
