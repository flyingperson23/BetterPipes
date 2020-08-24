package flyingperson.BetterPipes.compat;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class CompatBase {
    /** @param te tileentity to check for connectability
     *  @param direction the direction in which to check connectability
     *  @return is there a connectable block in that direction? */
    public abstract boolean canConnect(TileEntity te, EnumFacing direction);
    /** @param te tileentity to get connections from
     *  @return ArrayList of active connections on tileentity te */
    public abstract ArrayList<EnumFacing> getConnections(TileEntity te);
    /** @param te tileentity to test
     *  @return can tileentity te connect to a tileentity of given compat type*/
    public abstract boolean isAcceptable(TileEntity te);
    /** @param te tileentity to connect
     *  @param direction direction in which to connect
     *  @param player the player connecting/disconnecting the pipe*/
    public abstract void connect(TileEntity te, EnumFacing direction, EntityPlayer player);
    /** @param te tileentity to disconnect
     *  @param direction the direction of the the tileentity to disconnect from te
     *  @param player the player connecting/disconnecting the pipe*/
    public abstract void disconnect(TileEntity te, EnumFacing direction, EntityPlayer player);
    /**@param te tileentity to get drops from
     * @param blockState state to get drops from
     * @return list of drops te should drop*/
    public abstract Collection<ItemStack> getDrops(TileEntity te, IBlockState blockState);
    /**@return list of blocks from this compat module that can be broken with a wrench*/
    public abstract List<Block> getAcceptedBlocks();
    /**@return harvest speed for blocks from this module*/
    public abstract float getBreakSpeed();
}
