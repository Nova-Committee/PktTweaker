package committee.nova.pkttwkr.event.handler;

import committee.nova.pkttwkr.event.impl.StrategyRegisterEvent;
import committee.nova.pkttwkr.event.manager.EventManager;
import committee.nova.pkttwkr.storage.NetworkStrategies;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ForgeEventHandler {
    @SubscribeEvent
    public static void onPacketStrategyRegister(NetworkStrategies.StrategyRegisterEventInternal event) {
        final EventManager instance = EventManager.getInstance();
        if (instance.hasRegister()) instance.publishRegister(new StrategyRegisterEvent(event));
    }
}
