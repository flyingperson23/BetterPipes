package flyingperson.BetterPipes.network;

import flyingperson.BetterPipes.BetterPipes;
import flyingperson.BetterPipes.compat.CompatBase;
import flyingperson.BetterPipes.compat.CompatBaseNoTE;
import flyingperson.BetterPipes.util.BlockWrapper;
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
    private int id;
    public MessageGetConnections(BlockPos pos, int id) {
        this.pos = pos;
        this.id = id;
    }

    @Override public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeInt(id);
    }

    @Override public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        id = buf.readInt();
    }

    public static class MessageHandler implements IMessageHandler<MessageGetConnections, IMessage> {

        @Override public IMessage onMessage(MessageGetConnections message, MessageContext ctx) {
            EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
            BlockPos pos = message.pos;
            serverPlayer.getServerWorld().addScheduledTask(() -> {
                if (message.id % 2 == 0) {
                    if (serverPlayer.world.getTileEntity(pos) != null) {
                        CompatBase compat = BetterPipes.instance.COMPAT_LIST.get(message.id / 2);
                        TileEntity te = serverPlayer.world.getTileEntity(pos);
                        if (compat.isAcceptable(te)) {
                            ConnectionBlock connections = new ConnectionBlock(te.getPos(), compat.getConnections(te));
                            BetterPipes.INSTANCE.sendTo(new MessageReturnConnections(connections), serverPlayer);
                        }
                    }
                } else {
                    CompatBaseNoTE compat = BetterPipes.instance.COMPAT_LIST_NO_TE.get(((message.id-1)/2));
                    BlockWrapper block = new BlockWrapper(message.pos, serverPlayer);
                    if (compat.isAcceptable(block)) {
                        ConnectionBlock connections = new ConnectionBlock(block.pos, compat.getConnections(block));
                        BetterPipes.INSTANCE.sendTo(new MessageReturnConnections(connections), serverPlayer);
                    }
                }
                /*
                for (CompatBase compat : BetterPipes.instance.COMPAT_LIST) {
                    TileEntity te = serverPlayer.world.getTileEntity(pos);
                    if (compat.isAcceptable(te)) {
                        ConnectionBlock connections = new ConnectionBlock(te.getPos(), compat.getConnections(te));
                        BetterPipes.INSTANCE.sendTo(new MessageReturnConnections(connections), serverPlayer);
                    }
                }
                for (CompatBaseNoTE compat : BetterPipes.instance.COMPAT_LIST_NO_TE) {
                    BlockWrapper block = new BlockWrapper(message.pos, serverPlayer);
                    if (compat.isAcceptable(block)) {
                        ConnectionBlock connections = new ConnectionBlock(block.pos, compat.getConnections(block));
                        BetterPipes.INSTANCE.sendTo(new MessageReturnConnections(connections), serverPlayer);
                    }
                }*/
            });
            return null;
        }
    }
}