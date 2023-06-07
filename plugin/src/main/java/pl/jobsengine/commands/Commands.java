package pl.jobsengine.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length > 0) {
            try {
                Class<?> c = Class.forName("pl.jobsengine.commands.subcommands." + args[0]);
                Constructor<?> constructor = c.getConstructor();
                Object o = constructor.newInstance();
                Method method = o.getClass().getMethod("begin");
                method.invoke(o);
            } catch(ClassNotFoundException
                    | NoSuchMethodException
                    | IllegalAccessException
                    | InstantiationException
                    | InvocationTargetException e) {
                sender.sendMessage("§cNot found subcommand!");
                return true;
            }
        }
        return true;
    }

}
