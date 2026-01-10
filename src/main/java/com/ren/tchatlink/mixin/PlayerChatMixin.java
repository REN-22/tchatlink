package com.ren.tchatlink.mixin;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class PlayerChatMixin {

    @Inject(method = "handlePlayerChat", at = @At("HEAD"))
    private void onPlayerChat(ClientboundPlayerChatPacket packet, CallbackInfo ci) {

        String raw = packet.body().content().toString();
        System.out.println("[Mixin PlayerChat] " + raw);

        // --- FILTRES ANTI-BOUCLE ---
        if (raw.startsWith("Baritone:")) return;
        if (raw.startsWith("Commande inconnue")) return;
        if (raw.startsWith("#")) return;
        if (raw.contains("Baritone")) return;
        if (raw.startsWith("#goto")) return;
        if (raw.contains("Baritone:")) return;
        // ----------------------------
        System.out.println(raw + "a passé les filtres");

        String msg = raw.substring(raw.indexOf("> ") + 1);
        System.out.println("extrait msg: " + msg);

        if (!msg.startsWith("goto ")) return;

        try {
            String[] args = msg.split(" ");
            if (args.length != 4) return;

            System.out.println("args détectés: " + args[1] + " " + args[2] + " " + args[3]);

            double x = Double.parseDouble(args[1]);
            double y = Double.parseDouble(args[2]);
            double z = Double.parseDouble(args[3]);

            String baritoneCmd = "#goto " + x + " " + y + " " + z;

            var mc = net.minecraft.client.Minecraft.getInstance();
            mc.player.connection.sendChat(baritoneCmd);

            System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
            mc.player.sendSystemMessage(Component.literal(baritoneCmd));

        } catch (Exception ignored) {}
    }
}
