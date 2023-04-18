package committee.nova.pkttwkr.network.msg;

import committee.nova.pkttwkr.PktTweaker;
import committee.nova.pkttwkr.storage.NetworkStrategies;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.minecraft.CraftTweakerMC;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class C2SMessage implements IMessage {
    private NBTTagCompound tag = new NBTTagCompound();

    public NBTTagCompound getTag() {
        return tag;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        tag = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, tag);
    }

    public static class Handler implements IMessageHandler<C2SMessage, IMessage> {
        @Override
        public IMessage onMessage(C2SMessage message, MessageContext ctx) {
            if (ctx.side != Side.SERVER) return null;
            final NBTTagCompound tag = message.getTag();
            final String id = tag.getString("id");
            final NBTBase msgTag = tag.getTag("msgTag");
            final NetworkStrategies.C2SStrategy s = NetworkStrategies.getC2SStrategies().get(id);
            if (s == null) {
                PktTweaker.getLogger().error("No strategy matches <" + id + "> found!");
                return null;
            }
            final EntityPlayerMP mp = ctx.getServerHandler().player;
            mp.getServerWorld().addScheduledTask(() -> {
                try {
                    s.handle(CraftTweakerMC.getIPlayer(mp), CraftTweakerAPI.server, CraftTweakerMC.getIData(msgTag));
                } catch (Exception e) {
                    PktTweaker.getLogger().error("Exception caught in C2SMessage <" + id + ">...", e);
                }
            });
            return null;
        }
    }
}
