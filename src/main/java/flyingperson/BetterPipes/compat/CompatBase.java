package flyingperson.BetterPipes.compat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;

public abstract class CompatBase {
    /** @return is the needed mod loaded?*/
    public abstract boolean isModLoaded();
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
     *  @param player the player connecting/disconnecting the pipe
     *  @param hitX where should the mod think the pipe was hit on the x axis
     *  @param hitY where should the mod think the pipe was hit on the y axis
     *  @param hitZ where should the mod think the pipe was hit on the z axis*/
    public abstract void connect(TileEntity te, EnumFacing direction, EntityPlayer player);
    /** @param te tileentity to disconnect
     *  @param direction the direction of the the tileentity to disconnect from te
     *  @param player the player connecting/disconnecting the pipe
     *  @param hitX where should the mod think the pipe was hit on the x axis
     *  @param hitY where should the mod think the pipe was hit on the y axis
     *  @param hitZ where should the mod think the pipe was hit on the z axis*/
    public abstract void disconnect(TileEntity te, EnumFacing direction, EntityPlayer player);
}
