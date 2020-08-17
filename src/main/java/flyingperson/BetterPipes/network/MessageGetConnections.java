package flyingperson.BetterPipes.network;

import flyingperson.BetterPipes.BetterPipes;
import flyingperson.BetterPipes.compat.CompatBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageGetConnections implements IMessage {
    public MessageGetConnections(){}

    private BlockPos pos;
    public MessageGetConnections(BlockPos pos) {
        this.pos = pos;
    }

    @Override public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
    }

    @Override public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
    }

    public static class MyMessageHandler implements IMessageHandler<MessageGetConnections, IMessage> {

        @Override public IMessage onMessage(MessageGetConnections message, MessageContext ctx) {
            EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
            BlockPos pos = message.pos;
            serverPlayer.getServerWorld().addScheduledTask(() -> {
                if (serverPlayer.world.getTileEntity(pos) != null) {
                    for (CompatBase compat : BetterPipes.COMPAT_LIST) {
                        TileEntity te = serverPlayer.world.getTileEntity(pos);
                        if (compat.isAcceptable(te)) {
                            ConnectionBlock connections = new ConnectionBlock(te.getPos(), compat.getConnections(te));
                            BetterPipes.INSTANCE.sendTo(new MessageReturnConnections(connections), serverPlayer);
                        }
                    }
                }
            });
            return null;
        }
    }
}