package cn.wode490390.nukkit.reversetext;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginIdentifiableCommand;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.TextFormat;

public class ReverseTextCommand extends Command implements PluginIdentifiableCommand {

    private final ReverseText plugin;

    public ReverseTextCommand(ReverseText plugin) {
        super("reverset", "Enable or disable text reversal", "/reverset <true|false>", new String[]{"rt", "reversetext"});
        this.setPermission("reversetext.use");
        this.getCommandParameters().clear();
        this.addCommandParameters("default", new CommandParameter[]{
                new CommandParameter("bool", false, new String[]{"true", "false"})
        });
        this.plugin = plugin;
    }

    @Override
    public Plugin getPlugin() {
        return this.plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!this.plugin.isEnabled() || !this.testPermission(sender)) {
            return false;
        }

        if (args.length < 1) {
            sender.sendMessage(this.usageMessage);
            return false;
        }

        if (sender instanceof Player) {
            long id = ((Player) sender).getId();
            boolean state;

            switch (args[0].toLowerCase()) {
                case "true":
                case "t":
                case "enable":
                case "1":
                    state = true;
                    this.plugin.users.add(id);
                    break;
                case "false":
                case "f":
                case "disable":
                case "0":
                    state = false;
                    this.plugin.users.remove(id);
                    break;
                default:
                    sender.sendMessage(this.usageMessage);
                    return false;
            }

            sender.sendMessage(TextFormat.YELLOW + "Text reversal is " + (state ? "enabled" : "disabled"));
        } else {
            sender.sendMessage(new TranslationContainer("%commands.generic.ingame"));
        }

        return true;
    }
}
