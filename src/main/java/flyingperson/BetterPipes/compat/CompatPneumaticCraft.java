package flyingperson.BetterPipes.compat;

import flyingperson.BetterPipes.BPConfig;
import flyingperson.BetterPipes.compat.wrench.IWrenchProvider;
import flyingperson.BetterPipes.util.Utils;
import me.desht.pneumaticcraft.api.tileentity.IAirListener;
import me.desht.pneumaticcraft.common.item.ItemPneumaticWrench;
import me.desht.pneumaticcraft.common.tileentity.TileEntityPressureTube;
import me.desht.pneumaticcraft.lib.PneumaticValues;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CompatPneumaticCraft extends CompatBaseTE {

    @Override
    public boolean canConnect(TileEntity te, EnumFacing direction) {
        if (isAcceptable(te)) {
            TileEntityPressureTube tile = (TileEntityPressureTube) te;
            return tile.getWorld().getTileEntity(te.getPos().offset(direction, 1)) instanceof IAirListener;
        }
        return false;
    }

    @Override
    public ArrayList<EnumFacing> getConnections(TileEntity te) {
        ArrayList<EnumFacing> connections = new ArrayList<>();
        if (isAcceptable(te)) {
            TileEntityPressureTube tile = (TileEntityPressureTube) te;
            for (EnumFacing e : EnumFacing.values()) {
                if (tile.sidesConnected[e.getIndex()]) connections.add(e);
            }
        }
        return connections;
    }

    @Override
    public boolean isAcceptable(TileEntity te) {
        return te instanceof TileEntityPressureTube;
    }

    @Override
    public void connect(TileEntity te, EnumFacing direction, EntityPlayer player) {
        if (isAcceptable(te)) {

            TileEntityPressureTube tile = (TileEntityPressureTube) te;
            tile.sidesClosed[direction.getIndex()] = false;
            Utils.update(te);
        }
    }

    @Override
    public void disconnect(TileEntity te, EnumFacing direction, EntityPlayer player) {
        if (isAcceptable(te)) {
            TileEntityPressureTube tile = (TileEntityPressureTube) te;
            tile.sidesClosed[direction.getIndex()] = true;
            Utils.update(te);
        }
    }

    @Override
    public Collection<ItemStack> getDrops(TileEntity te, IBlockState blockState) {
        Collection<ItemStack> drops = new ArrayList<>();
        if (isAcceptable(te)) {
            TileEntityPressureTube tile = (TileEntityPressureTube) te;
            drops.addAll(tile.getBlockType().getDrops(tile.getWorld(), tile.getPos(), te.getWorld().getBlockState(te.getPos()), 0));
        }
        return drops;
    }

    @Override
    public List<Block> getAcceptedBlocks() {
        ArrayList<Block> blocks = new ArrayList<>();
        blocks.add(Block.REGISTRY.getObject(new ResourceLocation("pneumaticcraft", "pressure_tube")));
        blocks.add(Block.REGISTRY.getObject(new ResourceLocation("pneumaticcraft", "advanced_pressure_tube")));
        return blocks;
    }

    @Override
    public float getBreakSpeed() {
        return 100f;
    }

}
