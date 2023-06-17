package pl.jobsengine.commands;

import lombok.SneakyThrows;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class Commands implements CommandExecutor {

    private final HashMap<String, Object> cachedArgs = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        args = arrayToClassName(args);
        if(args.length > 0) {
            invokeMethod(sender, args);
        } else {
            if(sender instanceof Player) {
                invokeMethod(sender, new String[]{"ProfileCommand"});
            } else {
                invokeMethod(sender, new String[]{"HelpCommand"});
            }
        }
        return true;
    }

    @SneakyThrows
    private void invokeMethod(CommandSender sender, String[] args) {
        if(cachedArgs.containsKey(args[0])) {
            Method method = cachedArgs.get(args[0]).getClass().getMethod("begin", CommandSender.class, String[].class);
            method.invoke(cachedArgs.get(args[0]), sender, args);
        } else {
            getSubcommand(sender, args);
        }
    }

    private void getSubcommand(CommandSender sender, String[] args) {
        try {
            Class<?> c = Class.forName("pl.jobsengine.commands.subcommands." + args[0]);
            Constructor<?> constructor = c.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object o = constructor.newInstance();
            cachedArgs.put(args[0], o);
            invokeMethod(sender, args);
        } catch(ClassNotFoundException
                | NoSuchMethodException
                | IllegalAccessException
                | InstantiationException
                | InvocationTargetException e) {
            sender.sendMessage("§cNot found subcommand!");
        }
    }

    private String[] arrayToClassName(String[] array) {
        String[] arr = new String[array.length];
        for(int i = 0; i < array.length; i++) {
            arr[i] = array[i].substring(0, 1).toUpperCase() + array[i].substring(1).toLowerCase() + "Command";
        }
        return arr;
    }

}
