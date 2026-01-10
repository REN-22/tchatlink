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

        String[] morceaux = msg.split(" ");
        String commande = morceaux[0];
        System.out.println("commande parsé: " + commande);

        switch (commande) {
            case "goto" -> {
                System.out.println("Commande goto détectée");
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
                break;
            }

            case "path" -> {
                System.out.println("Commande path détectée");
                try {
                    String baritoneCmd = "#path";

                    var mc = net.minecraft.client.Minecraft.getInstance();
                    mc.player.connection.sendChat(baritoneCmd);

                    System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
                    mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                } catch (Exception ignored) {}
                break;
            }

            case "forcecancel" -> {
                System.out.println("Commande forcecancel détectée");
                try {
                    String baritoneCmd = "#forcecancel";

                    var mc = net.minecraft.client.Minecraft.getInstance();
                    mc.player.connection.sendChat(baritoneCmd);

                    System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
                    mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                } catch (Exception ignored) {}
                break;
            }

            case "invert" -> {
                System.out.println("Commande invert détectée");
                try {
                    String baritoneCmd = "#invert";

                    var mc = net.minecraft.client.Minecraft.getInstance();
                    mc.player.connection.sendChat(baritoneCmd);

                    System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
                    mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                } catch (Exception ignored) {}
                break;
            }

            case "tunnel" -> {
                System.out.println("Commande tunnel détectée");
                String[] args = msg.split(" ");

                if (args.length == 1) {
                    try {
                        String baritoneCmd = "#tunnel";

                        var mc = net.minecraft.client.Minecraft.getInstance();
                        mc.player.connection.sendChat(baritoneCmd);

                        System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
                        mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                    } catch (Exception ignored) {}
                } else if (args.length == 4) {
                    try {
                        int height = Integer.parseInt(args[1]);
                        int width = Integer.parseInt(args[2]);
                        int depth = Integer.parseInt(args[3]);

                        String baritoneCmd = "#tunnel " + height + " " + width + " " + depth;

                        var mc = net.minecraft.client.Minecraft.getInstance();
                        mc.player.connection.sendChat(baritoneCmd);

                        System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
                        mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                    } catch (Exception ignored) {}
                };
                break;
            }

            case "farm" -> {
                System.out.println("Commande farm détectée");
                String[] args = msg.split(" ");

                if (args.length == 1) {
                    try {
                        String baritoneCmd = "#farm";

                        var mc = net.minecraft.client.Minecraft.getInstance();
                        mc.player.connection.sendChat(baritoneCmd);

                        System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
                        mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                    } catch (Exception ignored) {}
                } else if (args.length == 2) {
                    try {
                        int range = Integer.parseInt(args[1]);

                        String baritoneCmd = "#farm " + range;

                        var mc = net.minecraft.client.Minecraft.getInstance();
                        mc.player.connection.sendChat(baritoneCmd);

                        System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
                        mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                    } catch (Exception ignored) {}
                } else if (args.length == 3) {
                    try {
                        int range = Integer.parseInt(args[1]);
                        String waypoint = args[2];

                        String baritoneCmd = "#farm " + range + " " + waypoint;

                        var mc = net.minecraft.client.Minecraft.getInstance();
                        mc.player.connection.sendChat(baritoneCmd);

                        System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
                        mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                    } catch (Exception ignored) {}
                };
                break;
            }

            case "follow" -> {
                System.out.println("Commande follow détectée");
                String[] args = msg.split(" ");

                System.out.println("mode détecté >" + args[1] +"<");

                if (args[1].equals("entities") || args[1].equals("entity") || args[1].equals("player")) {
                    System.out.println("args bien détectés: " + args[1]);
                } else {
                    System.out.println("args mal détectés");
                    break;
                }
                switch (args[1]) {
                    case "entities":
                        try {
                            String baritoneCmd = "#follow entities";

                            var mc = net.minecraft.client.Minecraft.getInstance();
                            mc.player.connection.sendChat(baritoneCmd);

                            System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
                            mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                        } catch (Exception ignored) {}
                        break;

                    case "entity":
                        try {
                            String entityName = args[3];
                            String baritoneCmd = "#follow entity " + entityName;

                            var mc = net.minecraft.client.Minecraft.getInstance();
                            mc.player.connection.sendChat(baritoneCmd);

                            System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
                            mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                        } catch (Exception ignored) {}

                    case "player":
                        try {
                            String playerName = args[3];
                            String baritoneCmd = "#follow player " + playerName;

                            var mc = net.minecraft.client.Minecraft.getInstance();
                            mc.player.connection.sendChat(baritoneCmd);

                            System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
                            mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                        } catch (Exception ignored) {}
                        break;
                    default:
                        break;
                }
                break;
            }

            case "stop" -> {
                System.out.println("Commande stop détectée");
                try {
                    String baritoneCmd = "#stop";

                    var mc = net.minecraft.client.Minecraft.getInstance();
                    mc.player.connection.sendChat(baritoneCmd);

                    System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
                    mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                } catch (Exception ignored) {}
                break;
            }



            default -> {
                break;
            }
        }
    }
}
