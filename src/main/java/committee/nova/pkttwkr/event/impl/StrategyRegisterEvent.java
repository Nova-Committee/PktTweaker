package committee.nova.pkttwkr.event.impl;

import committee.nova.pkttwkr.storage.NetworkStrategies;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("pkttwkr.event.StrategyRegisterEvent")
public class StrategyRegisterEvent {
    private final NetworkStrategies.StrategyRegisterEventInternal internal;

    public StrategyRegisterEvent(NetworkStrategies.StrategyRegisterEventInternal internal) {
        this.internal = internal;
    }

    @ZenMethod
    public boolean addC2SStrategy(String id, NetworkStrategies.C2SStrategy strategy) {
        return internal.addC2SStrategy(id, strategy);
    }

    @ZenMethod
    public boolean addS2CStrategy(String id, NetworkStrategies.S2CStrategy strategy) {
        return internal.addS2CStrategy(id, strategy);
    }
}
