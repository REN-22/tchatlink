package com.ren.tchatlink.mixin;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ChatPacketMixin {

    @Inject(method = "handleSystemChat", at = @At("HEAD"))
    private void onSystemChat(ClientboundSystemChatPacket packet, CallbackInfo ci) {

        Component content = packet.content();
        if (content == null) return;

        String raw = content.getString();
        System.out.println("[Mixin Packet] Chat: " + raw);
        

        // --- FILTRES ANTI-BOUCLE ---
        if (raw.startsWith("Baritone:")) return;
        if (raw.startsWith("Commande inconnue")) return;
        if (raw.startsWith("#")) return;
        if (raw.contains("Baritone")) return;
        if (raw.startsWith("#goto")) return;
        if (raw.contains("Baritone:")) return;
        if (!raw.contains("> ")) return;
        // ----------------------------

        String msg = raw.substring(raw.indexOf("> ") + 2);

        if (!msg.startsWith("goto ")) return;

        try {
            String[] args = msg.split(" ");
            if (args.length != 4) return;

            double x = Double.parseDouble(args[1]);
            double y = Double.parseDouble(args[2]);
            double z = Double.parseDouble(args[3]);

            String baritoneCmd = "#goto " + x + " " + y + " " + z;

            var mc = net.minecraft.client.Minecraft.getInstance();
            mc.player.connection.sendChat(baritoneCmd);


            mc.player.sendSystemMessage(Component.literal(baritoneCmd));

        } catch (Exception ignored) {}
    }
}
