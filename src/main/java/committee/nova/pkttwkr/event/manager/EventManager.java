package committee.nova.pkttwkr.event.manager;

import committee.nova.pkttwkr.event.impl.StrategyRegisterEvent;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IEventHandle;
import crafttweaker.util.EventList;
import crafttweaker.util.IEventHandler;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("pkttwkr.event.EventManager")
public class EventManager {
    private static final EventManager INSTANCE = new EventManager();

    private final EventList<StrategyRegisterEvent> listStrategyRegister = new EventList<>();

    @ZenMethod
    public static EventManager getInstance() {
        return INSTANCE;
    }

    @ZenMethod
    public void clear() {
        listStrategyRegister.clear();
    }

    public boolean hasRegister() {
        return listStrategyRegister.hasHandlers();
    }

    public void publishRegister(StrategyRegisterEvent event) {
        listStrategyRegister.publish(event);
    }

    @ZenMethod
    public IEventHandle onStrategyRegister(IEventHandler<StrategyRegisterEvent> handler) {
        return listStrategyRegister.add(handler);
    }
}
