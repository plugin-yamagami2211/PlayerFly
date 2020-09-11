package me.yama2211.pf;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        if(getConfig().getBoolean("Update")){
            new UpdateChecker(this,"PlayerFly").getVersion(version -> {
                if (!this.getDescription().getVersion().equalsIgnoreCase(version)) {
                    getLogger().info("利用可能なアップデートがあります。配布フォーラムをご確認ください。");
                }
            });
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void FlyFlag(Player pl, Boolean flag){
        if(flag){
            pl.setAllowFlight(true);
            pl.setFlySpeed(0.1F);
            pl.sendMessage(ChatColor.translateAlternateColorCodes(
                    '&',getConfig().getString("MSG"+".flyOn")));
        } else {
            pl.setFlying(false);
            pl.setAllowFlight(false);
            pl.sendMessage(ChatColor.translateAlternateColorCodes(
                    '&',getConfig().getString("MSG"+".flyOff")));
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd,String commandLabel,String[] args){
        boolean FullFlag = false;
        Player p = ( sender instanceof Player ) ? ( Player )sender:( Player )null;

        if(cmd.getName().equalsIgnoreCase("fly")){
            if(!(sender.hasPermission("playerfly.use")))
            {
                sender.sendMessage(ChatColor.translateAlternateColorCodes(
                        '&',getConfig().getString("MSG"+".NoPex")));
            } else {
                if(p == null) return false;
                for (String arg:args){
                    switch (arg){
                        case "on":
                            FlyFlag(p,true);
                            break;
                        case "off":
                            FlyFlag(p,false);
                            break;
                        default:
                            p.sendMessage(ChatColor.GREEN + "/fly (on or off)" );
                    }
                }
            }
        }
        return true;
    }
}
