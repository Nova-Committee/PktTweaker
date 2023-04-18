package committee.nova.pkttwkr.storage;

import com.google.common.collect.ImmutableMap;
import committee.nova.pkttwkr.PktTweaker;
import committee.nova.pkttwkr.util.StringUtil;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.client.IClient;
import crafttweaker.api.data.IData;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.server.IServer;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.apache.logging.log4j.Logger;
import stanhebben.zenscript.annotations.ZenClass;

import java.util.HashMap;
import java.util.Map;

public class NetworkStrategies {
    public static class StrategyRegisterEventInternal extends Event {
        public boolean addC2SStrategy(String id, C2SStrategy strategy) {
            return addC2S(id, strategy);
        }

        public boolean addS2CStrategy(String id, S2CStrategy strategy) {
            return addS2C(id, strategy);
        }
    }

    private static final HashMap<String, C2SStrategy> c2sStrategies = new HashMap<>();
    private static final HashMap<String, S2CStrategy> s2cStrategies = new HashMap<>();

    @ZenRegister
    @ZenClass("pkttwkr.strategy.C2SStrategy")
    public interface C2SStrategy {
        void handle(IPlayer player, IServer server, IData data);
    }

    @ZenRegister
    @ZenClass("pkttwkr.strategy.S2CStrategy")
    public interface S2CStrategy {
        void handle(IPlayer player, IClient client, IData data);
    }

    private static boolean addC2S(String id, C2SStrategy strategy) {
        final Logger logger = PktTweaker.getLogger();
        if (id.isEmpty()) {
            logger.error("Empty id is not allowed!");
            return false;
        }
        final String newId = StringUtil.getFormatted(id);
        if (c2sStrategies.containsKey(newId)) {
            logger.error("Failed to register C2S strategy <" + newId + "> for duplicate id!");
            return false;
        }
        c2sStrategies.put(newId, strategy);
        logger.info("Successfully registered C2S strategy <" + newId + ">!");
        return true;
    }

    private static boolean addS2C(String id, S2CStrategy strategy) {
        final Logger logger = PktTweaker.getLogger();
        if (id.isEmpty()) {
            logger.error("Empty id is not allowed!");
            return false;
        }
        final String newId = StringUtil.getFormatted(id);
        if (s2cStrategies.containsKey(newId)) {
            logger.error("Failed to register S2C strategy <" + newId + "> for duplicate id!");
            return false;
        }
        s2cStrategies.put(newId, strategy);
        logger.info("Successfully registered S2C strategy <" + newId + ">!");
        return true;
    }

    public static Map<String, C2SStrategy> getC2SStrategies() {
        return ImmutableMap.copyOf(c2sStrategies);
    }

    public static Map<String, S2CStrategy> getS2CStrategies() {
        return ImmutableMap.copyOf(s2cStrategies);
    }
}
