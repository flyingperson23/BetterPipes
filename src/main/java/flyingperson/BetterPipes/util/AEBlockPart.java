package flyingperson.BetterPipes.util;

import appeng.api.networking.IGridNode;
import appeng.api.parts.*;
import appeng.api.util.AECableType;
import appeng.api.util.AEPartLocation;
import appeng.items.parts.PartModels;
import appeng.parts.PartModel;
import flyingperson.BetterPipes.BetterPipes;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class AEBlockPart implements IPart {

    @PartModels
    public static final PartModel DEFAULT_MODELS = new PartModel(false, new ResourceLocation(BetterPipes.MODID, "ae2part/blockpart" ));

    public AEBlockPart() {

    }


    @Override
    public void getBoxes( final IPartCollisionHelper bch ) {
        bch.addBox(8.0D, 8.0D, 16.0D, 8.0D, 8.0D, 16.0D);
    }

    @Override
    public ItemStack getItemStack( final PartItemStack wrenched ) {
        return new ItemStack(RegisterAEStuff.aepart, 1);
    }

    @Override
    public boolean requireDynamicRender() {
        return false;
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public boolean canConnectRedstone() {
        return false;
    }

    @Override
    public void writeToNBT( final NBTTagCompound data ) {

    }

    @Override
    public void readFromNBT( final NBTTagCompound data ) {

    }

    @Override
    public int getLightLevel() {
        return 0;
    }

    @Override
    public boolean isLadder( final EntityLivingBase entity ) {
        return false;
    }

    @Override
    public void onNeighborChanged(IBlockAccess w, BlockPos pos, BlockPos neighbor ) {

    }

    @Override
    public int isProvidingStrongPower() {
        return 0;
    }

    @Override
    public int isProvidingWeakPower() {
        return 0;
    }

    @Override
    public void writeToStream( final ByteBuf data ) throws IOException {

    }

    @Override
    public boolean readFromStream( final ByteBuf data ) throws IOException {
        return false;
    }

    @Override
    public IGridNode getGridNode() {
        return null;
    }

    @Override
    public void onEntityCollision( final Entity entity ) {
    }

    @Override
    public void removeFromWorld() {

    }

    @Override
    public void addToWorld() {
    }

    @Override
    public IGridNode getExternalFacingNode() {
        return null;
    }

    @Override
    public void setPartHostInfo(final AEPartLocation side, final IPartHost host, final TileEntity tile ) {

    }

    @Override
    public boolean onActivate(final EntityPlayer player, final EnumHand hand, final Vec3d pos ) {
        return false;
    }

    @Override
    public boolean onShiftActivate( final EntityPlayer player, final EnumHand hand, final Vec3d pos ) {
        return false;
    }

    @Override
    public void getDrops(final List<ItemStack> drops, final boolean wrenched ) {
        drops.removeIf(i -> i.getItem() == RegisterAEStuff.aepart);
    }

    @Override
    public float getCableConnectionLength( AECableType cable ) {
        return 0;
    }

    @Override
    public void randomDisplayTick(final World world, final BlockPos pos, final Random r ) {

    }

    @Override
    public void onPlacement( final EntityPlayer player, final EnumHand hand, final ItemStack held, final AEPartLocation side ) {

    }

    @Override
    public boolean canBePlacedOn( final BusSupport what ) {
        return what == BusSupport.DENSE_CABLE || what == BusSupport.CABLE;
    }

    @Override
    public IPartModel getStaticModels() {
        return DEFAULT_MODELS;
    }

}