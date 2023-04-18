package committee.nova.pkttwkr.network.handler;

import committee.nova.pkttwkr.PktTweaker;
import committee.nova.pkttwkr.network.msg.C2SMessage;
import committee.nova.pkttwkr.network.msg.S2CMessage;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {
    public static final SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel(PktTweaker.MODID);

    private static int nextId = 0;

    public static void init() {
        registerMessage(C2SMessage.Handler.class, C2SMessage.class, Side.SERVER);
        registerMessage(S2CMessage.Handler.class, S2CMessage.class, Side.CLIENT);
    }

    private static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side) {
        instance.registerMessage(messageHandler, requestMessageType, nextId++, side);
    }
}
