package flyingperson.BetterPipes.network;

import flyingperson.BetterPipes.BetterPipes;
import flyingperson.BetterPipes.util.Utils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;

public class MessageReturnConnections implements IMessage {
    public MessageReturnConnections(){}

    private ConnectionBlock connectionBlock;
    public MessageReturnConnections(ConnectionBlock connectionBlock) {
        this.connectionBlock = connectionBlock;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer buf2 = new PacketBuffer(buf);
        buf2.writeLong(connectionBlock.pos.toLong());
        buf2.writeVarIntArray(Utils.toIntArr(connectionBlock.connections));
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer buf2 = new PacketBuffer(buf);
        try {
            BlockPos pos = BlockPos.fromLong(buf2.readLong());
            ArrayList<EnumFacing> connections = Utils.toArrayList(buf2.readVarIntArray());
            this.connectionBlock = new ConnectionBlock(pos, connections);
        } catch (Exception e) {
            BetterPipes.logger.log(Level.ERROR, "Something went wrong trying to receive a packet");
        }
    }

    public static class MessageHandler implements IMessageHandler<MessageReturnConnections, IMessage> {

        @Override
        public IMessage onMessage(MessageReturnConnections message, MessageContext ctx) {
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            ConnectionBlock connectionBlock = message.connectionBlock;
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileEntity te = player.world.getTileEntity(connectionBlock.pos);
                player.world.notifyBlockUpdate(connectionBlock.pos, player.world.getBlockState(connectionBlock.pos), player.world.getBlockState(connectionBlock.pos), 3);
                if (te != null) {
                    te.markDirty();
                    for (EnumFacing e : EnumFacing.VALUES) {
                        te.getBlockType().onNeighborChange(te.getWorld(), te.getPos(), te.getPos().offset(e, 1));
                    }
                }
                ConnectionGrid.instance().add(connectionBlock);
            });
            return null;
        }
    }
}