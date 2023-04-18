package committee.nova.pkttwkr;

import committee.nova.pkttwkr.network.handler.NetworkHandler;
import committee.nova.pkttwkr.storage.NetworkStrategies;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = PktTweaker.MODID, useMetadata = true)
public class PktTweaker {
    public static final String MODID = "pkttwkr";
    private static Logger logger;

    public static Logger getLogger() {
        return logger;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        logger = e.getModLog();
        NetworkHandler.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        MinecraftForge.EVENT_BUS.post(new NetworkStrategies.StrategyRegisterEventInternal());
    }
}
