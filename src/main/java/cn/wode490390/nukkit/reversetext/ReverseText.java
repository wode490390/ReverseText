package cn.wode490390.nukkit.reversetext;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.plugin.PluginBase;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import java.util.Set;

public class ReverseText extends PluginBase implements Listener {

    protected final Set<Long> users = new LongOpenHashSet();

    @Override
    public void onEnable() {
        this.getServer().getCommandMap().register("reversetext", new ReverseTextCommand(this));
        this.getServer().getPluginManager().registerEvents(this, this);

        try {
            new MetricsLite(this);
        } catch (Throwable ignore) {

        }
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        if (this.users.contains(event.getPlayer().getId())) {
            event.setMessage(new StringBuilder(event.getMessage()).reverse().toString());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.users.remove(event.getPlayer().getId());
    }
}
