package committee.nova.pkttwkr.network.msg;

import committee.nova.pkttwkr.PktTweaker;
import committee.nova.pkttwkr.storage.NetworkStrategies;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.minecraft.CraftTweakerMC;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Collection;

public class S2CMessage implements IMessage {
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

    public static class Handler implements IMessageHandler<S2CMessage, IMessage> {
        @Override
        public IMessage onMessage(S2CMessage message, MessageContext ctx) {
            if (ctx.side != Side.CLIENT) return null;
            final NBTTagCompound tag = message.getTag();
            final String id = tag.getString("id");
            final NBTBase msgTag = tag.getTag("msgTag");
            final Collection<NetworkStrategies.S2CStrategy> s = NetworkStrategies.getS2CStrategies().get(id);
            if (s.isEmpty()) {
                PktTweaker.getLogger().error("No strategy matches <" + id + "> found!");
                return null;
            }
            final Minecraft mc = Minecraft.getMinecraft();
            mc.addScheduledTask(() -> {
                for (final NetworkStrategies.S2CStrategy y : s) {
                    try {
                        y.handle(CraftTweakerMC.getIPlayer(mc.player), CraftTweakerAPI.client, CraftTweakerMC.getIData(msgTag));
                    } catch (Exception e) {
                        PktTweaker.getLogger().error("Exception caught in S2CMessage <" + id + ">...", e);
                    }
                }
            });
            return null;
        }
    }
}
