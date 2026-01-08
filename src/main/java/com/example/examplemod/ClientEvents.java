package com.example.examplemod;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ExampleMod.MODID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onClientChat(ClientChatReceivedEvent event) {
        String msg = event.getMessage().getString();

        if (!msg.startsWith("goto ")) return;

        try {
            String[] args = msg.split(" ");
            if (args.length != 4) return;

            double x = Double.parseDouble(args[1]);
            double y = Double.parseDouble(args[2]);
            double z = Double.parseDouble(args[3]);

            String baritoneCmd = "#goto " + x + " " + y + " " + z;

            // 🔥 Envoi de la commande Baritone côté client
            Minecraft.getInstance().player.connection.sendCommand(baritoneCmd);

            Minecraft.getInstance().player.sendSystemMessage(
                    Component.literal("Baritone: " + baritoneCmd)
            );

        } catch (Exception ignored) {}
    }
}
