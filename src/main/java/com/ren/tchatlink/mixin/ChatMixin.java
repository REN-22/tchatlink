package com.ren.tchatlink.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatComponent.class)
public class ChatMixin {

    @Inject(method = "addMessage", at = @At("HEAD"))
    private void onAddMessage(Component message, CallbackInfo ci) {

        String raw = message.getString();
        System.out.println("[Mixin Chat] Message reçu: " + raw);

        // Exemple : "<REN_22> goto 0 0 0"
        if (!raw.contains("> ")) return;
        String msg = raw.substring(raw.indexOf("> ") + 2);

        if (!msg.startsWith("goto ")) return;

        try {
            String[] args = msg.split(" ");
            if (args.length != 4) return;

            double x = Double.parseDouble(args[1]);
            double y = Double.parseDouble(args[2]);
            double z = Double.parseDouble(args[3]);

            String baritoneCmd = "#goto " + x + " " + y + " " + z;

            Minecraft.getInstance().player.connection.sendCommand(baritoneCmd);

            Minecraft.getInstance().player.sendSystemMessage(
                    Component.literal("Baritone: " + baritoneCmd)
            );

        } catch (Exception ignored) {}
    }
}
