package committee.nova.pkttwkr.util;

import committee.nova.pkttwkr.network.handler.NetworkHandler;
import committee.nova.pkttwkr.network.msg.C2SMessage;
import committee.nova.pkttwkr.network.msg.S2CMessage;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.IData;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("pkttwkr.util.PacketUtil")
public class PacketUtil {
    @ZenMethod
    public static void sendToServer(String id, IData data) {
        final C2SMessage msg = new C2SMessage();
        wrap(msg.getTag(), id, data);
        NetworkHandler.instance.sendToServer(msg);
    }

    @ZenMethod
    public static void sendToClientPlayer(String id, IData data, IPlayer player) {
        final S2CMessage msg = new S2CMessage();
        wrap(msg.getTag(), id, data);
        NetworkHandler.instance.sendTo(msg, (EntityPlayerMP) CraftTweakerMC.getPlayer(player));
    }

    @ZenMethod
    public static void sendToAllClientPlayer(String id, IData data) {
        final S2CMessage msg = new S2CMessage();
        wrap(msg.getTag(), id, data);
        NetworkHandler.instance.sendToAll(msg);
    }

    @ZenMethod
    public static void sendToDimension(String id, IData data, int dim) {
        final S2CMessage msg = new S2CMessage();
        wrap(msg.getTag(), id, data);
        NetworkHandler.instance.sendToDimension(msg, dim);
    }

    private static void wrap(NBTTagCompound tag, String id, IData msgTag) {
        tag.setString("id", StringUtil.getFormatted(id));
        tag.setTag("msgTag", CraftTweakerMC.getNBT(msgTag));
    }
}
