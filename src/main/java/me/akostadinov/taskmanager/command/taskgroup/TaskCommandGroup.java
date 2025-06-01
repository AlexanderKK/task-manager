package me.akostadinov.taskmanager.command.taskgroup;

import org.mineacademy.fo.Common;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.model.SimpleComponent;
import org.mineacademy.fo.plugin.SimplePlugin;

import java.util.Arrays;
import java.util.List;

@AutoRegister
public final class TaskCommandGroup extends SimpleCommandGroup {

    public TaskCommandGroup() {
        super("task|taskmanager");
    }

    @Override
    protected void registerSubcommands() {
        registerSubcommand(new TaskRegisterCommand(this));
        registerSubcommand(new TaskRemoveCommand(this));
        registerSubcommand(new TaskListCommand(this));
    }

    @Override
    protected String getCredits() {
        return "&7Visit &6&ogithub.com/AlexanderKK/task-manager&7.";
    }

    @Override
    protected List<SimpleComponent> getNoParamsHeader() {
        return Arrays.asList(
                SimpleComponent.of("&8" + Common.chatLineSmooth()),
                SimpleComponent.of("&7Welcome to the &6Task Manager&7. Type &6/task help&7 for more info."),
                SimpleComponent.of("&8"),
                SimpleComponent.of(this.getCredits()),
                SimpleComponent.of("&7Made by &6&oAKostadinov&r&7. &7This plugin is using &6&oFoundation&r&7.")
        );
    }

    @Override
    protected String[] getHelpHeader() {
        return new String[]{
                "&8" + Common.chatLineSmooth(),
                this.getHeaderPrefix() + "  " + SimplePlugin.getNamed() + " &7" + SimplePlugin.getVersion(),
                " ",
        };
    }

}
