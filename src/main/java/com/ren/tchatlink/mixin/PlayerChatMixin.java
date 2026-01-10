package com.ren.tchatlink.mixin;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class PlayerChatMixin {
    private static final Set<String> processed = new HashSet<>(); 
    private static long lastCleanup = 0; 

    private void cleanup() { long now = System.currentTimeMillis(); 
        if (now - lastCleanup > 50) { 
            processed.clear(); lastCleanup = now; 
        } 
    }

    @Inject(method = "handlePlayerChat", at = @At("HEAD"))
    private void onPlayerChat(ClientboundPlayerChatPacket packet, CallbackInfo ci) {

        cleanup();

        // Identifiant unique du message (1.21.1 compatible) 
        String raw = packet.body().content(); 
        String sender = packet.sender().toString(); 
        String unique = sender + "|" + raw; 
        
        // Anti-doublon 
        if (processed.contains(unique)) return; 
        processed.add(unique);

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
            case "goal" -> {
                String[] args = msg.split(" ");
                switch (args.length) {
                    case 1:
                        try {
                            String baritoneCmd = "#goal";

                            var mc = net.minecraft.client.Minecraft.getInstance();
                            mc.player.connection.sendChat(baritoneCmd);

                            System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
                            mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                        } catch (Exception ignored) {}
                        break;
                    case 2:
                        if (args[1].equals("reset") || args[1].equals("clear") || args[1].equals("none")) {
                            try {
                                String baritoneCmd2 = "#goal " + args[1];

                                var mc2 = net.minecraft.client.Minecraft.getInstance();
                                mc2.player.connection.sendChat(baritoneCmd2);

                                System.out.println("commande--> " + baritoneCmd2 + "générée depuis --> " + raw);
                                mc2.player.sendSystemMessage(Component.literal(baritoneCmd2));
                            } catch (Exception ignored) {}
                            break;
                        } else if (args[1].matches("\\d+") || args[1].matches("\\d+\\.\\d+")) {
                            try {
                            double y = Double.parseDouble(args[1]);
                            String baritoneCmd2 = "#goal " + y;

                            var mc2 = net.minecraft.client.Minecraft.getInstance();
                            mc2.player.connection.sendChat(baritoneCmd2);

                            System.out.println("commande--> " + baritoneCmd2 + "générée depuis --> " + raw);
                            mc2.player.sendSystemMessage(Component.literal(baritoneCmd2));
                            } catch (Exception ignored) {}
                            break;
                        }
                        break;
                    case 3:
                        try {
                            double x = Double.parseDouble(args[1]);
                            double z = Double.parseDouble(args[2]);
                            String baritoneCmd3 = "#goal " + x + " " + z;

                            var mc3 = net.minecraft.client.Minecraft.getInstance();
                            mc3.player.connection.sendChat(baritoneCmd3);

                            System.out.println("commande--> " + baritoneCmd3 + "générée depuis --> " + raw);
                            mc3.player.sendSystemMessage(Component.literal(baritoneCmd3));
                        } catch (Exception ignored) {}
                        break;
                    case 4:
                        try {
                            double x = Double.parseDouble(args[1]);
                            double y = Double.parseDouble(args[2]);
                            double z = Double.parseDouble(args[3]);
                            String baritoneCmd4 = "#goal " + x + " " + y + " " + z;

                            var mc4 = net.minecraft.client.Minecraft.getInstance();
                            mc4.player.connection.sendChat(baritoneCmd4);

                            System.out.println("commande--> " + baritoneCmd4 + "générée depuis --> " + raw);
                            mc4.player.sendSystemMessage(Component.literal(baritoneCmd4));
                        } catch (Exception ignored) {}
                        break;
                    default:
                        break;
                }
                break;
            }

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
                            String entityName = args[2];
                            String baritoneCmd = "#follow entity " + entityName;

                            var mc = net.minecraft.client.Minecraft.getInstance();
                            mc.player.connection.sendChat(baritoneCmd);

                            System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
                            mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                        } catch (Exception ignored) {}

                    case "player":
                        try {
                            String playerName = args[2];
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

            case "pickup" -> {
                System.out.println("Commande pickup détectée");
                String[] args = msg.split(" ");

                if (args.length == 1) {
                    try {
                        String baritoneCmd = "#pickup";

                        var mc = net.minecraft.client.Minecraft.getInstance();
                        mc.player.connection.sendChat(baritoneCmd);

                        System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
                        mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                    } catch (Exception ignored) {}
                } else if (args.length > 1) {
                    String itemNames = args[1];
                    if (args.length > 2) {
                        for (int i = 2; i < args.length; i++) {
                            itemNames = itemNames + " " + args[i];
                        }
                    }
                    try {
                        String baritoneCmd = "#pickup " + itemNames;

                        var mc = net.minecraft.client.Minecraft.getInstance();
                        mc.player.connection.sendChat(baritoneCmd);

                        System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
                        mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                    } catch (Exception ignored) {}

                };
                break;
            }

            case "explore" -> {
                System.out.println("Commande explore détectée");
                String[] args = msg.split(" ");

                if (args.length == 1) {
                    try {
                        String baritoneCmd = "#explore";

                        var mc = net.minecraft.client.Minecraft.getInstance();
                        mc.player.connection.sendChat(baritoneCmd);

                        System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
                        mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                    } catch (Exception ignored) {}
                    break;
                } else if (args.length > 1) {
                    try {
                        Double x = Double.parseDouble(args[1]);
                        Double z = Double.parseDouble(args[2]);

                        String baritoneCmd = "#explore " + x + " " + z;

                        var mc = net.minecraft.client.Minecraft.getInstance();
                        mc.player.connection.sendChat(baritoneCmd);

                        System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
                        mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                    } catch (Exception ignored) {}
                }
                break;
            }

            case "find" -> {
                System.out.println("Commande find détectée");
                String[] args = msg.split(" ");

                if (args.length != 2) break;

                String blockName = args[1];
                try {
                    String baritoneCmd = "#find " + blockName;

                    var mc = net.minecraft.client.Minecraft.getInstance();
                    mc.player.connection.sendChat(baritoneCmd);

                    System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
                    mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                } catch (Exception ignored) {}
                break;
            }

            case "mine" -> {
                System.out.println("Commande mine détectée");
                String[] args = msg.split(" ");

                if (args.length != 2) break;

                String blockName = args[1];
                try {
                    String baritoneCmd = "#mine " + blockName;

                    var mc = net.minecraft.client.Minecraft.getInstance();
                    mc.player.connection.sendChat(baritoneCmd);

                    System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
                    mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                } catch (Exception ignored) {}
                break;
            }

            // case "click" -> {
            //     System.out.println("Commande click détectée");
            //     try {
            //         String baritoneCmd = "#click";

            //         var mc = net.minecraft.client.Minecraft.getInstance();
            //         mc.player.connection.sendChat(baritoneCmd);

            //         System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
            //         mc.player.sendSystemMessage(Component.literal(baritoneCmd));
            //     } catch (Exception ignored) {}
            //     break;
            // }

            case "surface" -> {
                System.out.println("Commande surface détectée");
                try {
                    String baritoneCmd = "#surface";

                    var mc = net.minecraft.client.Minecraft.getInstance();
                    mc.player.connection.sendChat(baritoneCmd);

                    System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
                    mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                } catch (Exception ignored) {}
                break;
            }

            case "thisway" -> {
                System.out.println("Commande thisway détectée");
                String[] args = msg.split(" ");

                try {
                    double distance = Double.parseDouble(args[1]);

                    String baritoneCmd = "#thisway " + distance;

                    var mc = net.minecraft.client.Minecraft.getInstance();
                    mc.player.connection.sendChat(baritoneCmd);

                    System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
                    mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                } catch (Exception ignored) {}
                break;
            }

            case "wp", "waypoint", "waypoints" -> {
                System.out.println("Commande waypoint détectée");
                String[] args = msg.split(" ");

                try {
                    var mc = net.minecraft.client.Minecraft.getInstance();
                    // Pas d’arguments → wp list
                    if (args.length == 1) {
                        String baritoneCmd = "#wp list";
                        mc.player.connection.sendChat(baritoneCmd);
                        mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                        break;
                    }

                    String sub = args[1];

                    switch (sub) {

                        // --- LIST / L ---
                        case "l", "list" -> {
                            String baritoneCmd = (args.length == 2)
                                    ? "#wp list"
                                    : "#wp list " + args[2];

                            mc.player.connection.sendChat(baritoneCmd);
                            mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                        }

                        // --- SAVE / S ---
                        case "s", "save" -> {
                            String baritoneCmd;

                            if (args.length == 2) {
                                baritoneCmd = "#wp save";
                            } else if (args.length == 3) {
                                baritoneCmd = "#wp save " + args[2];
                            } else if (args.length == 4) {
                                baritoneCmd = "#wp save " + args[2] + " " + args[3];
                            } else {
                                // wp save <tag> <name> <pos...>
                                String pos = String.join(" ", Arrays.copyOfRange(args, 4, args.length));
                                baritoneCmd = "#wp save " + args[2] + " " + args[3] + " " + pos;
                            }

                            mc.player.connection.sendChat(baritoneCmd);
                            mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                        }

                        // --- INFO / I / SHOW ---
                        case "i", "info", "show" -> {
                            if (args.length >= 3) {
                                String baritoneCmd = "#wp info " + args[2];
                                mc.player.connection.sendChat(baritoneCmd);
                                mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                            }
                        }

                        // --- DELETE / D ---
                        case "d", "delete" -> {
                            if (args.length >= 3) {
                                String baritoneCmd = "#wp delete " + args[2];
                                mc.player.connection.sendChat(baritoneCmd);
                                mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                            }
                        }

                        // --- RESTORE ---
                        case "restore" -> {
                            if (args.length >= 3) {
                                String baritoneCmd = "#wp restore " + args[2];
                                mc.player.connection.sendChat(baritoneCmd);
                                mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                            }
                        }

                        // --- CLEAR / C ---
                        case "c", "clear" -> {
                            if (args.length >= 3) {
                                String baritoneCmd = "#wp clear " + args[2];
                                mc.player.connection.sendChat(baritoneCmd);
                                mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                            }
                        }

                        // --- GOAL / G ---
                        case "g", "goal" -> {
                            if (args.length >= 3) {
                                String baritoneCmd = "#wp goal " + args[2];
                                mc.player.connection.sendChat(baritoneCmd);
                                mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                            }
                        }

                        // --- GOTO ---
                        case "goto" -> {
                            if (args.length >= 3) {
                                String baritoneCmd = "#wp goto " + args[2];
                                mc.player.connection.sendChat(baritoneCmd);
                                mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                            }
                        }

                        default -> {
                            mc.player.sendSystemMessage(Component.literal("§cUsage incorrect de wp"));
                        }
                    }

                } catch (Exception ignored) {}

                break;
            }

            case "sethome" -> {
                System.out.println("Commande sethome détectée");
                try {
                    String baritoneCmd = "#sethome";

                    var mc = net.minecraft.client.Minecraft.getInstance();
                    mc.player.connection.sendChat(baritoneCmd);

                    System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
                    mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                } catch (Exception ignored) {}
                break;
            }

            case "home" -> {
                System.out.println("Commande home détectée");
                try {
                    String baritoneCmd = "#home";

                    var mc = net.minecraft.client.Minecraft.getInstance();
                    mc.player.connection.sendChat(baritoneCmd);

                    System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
                    mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                } catch (Exception ignored) {}
                break;
            }

            case "elytra" -> {
                System.out.println("Commande elytra détectée");
                String[] args = msg.split(" ");

                if (args.length == 1) {
                    try {
                        String baritoneCmd = "#elytra";

                        var mc = net.minecraft.client.Minecraft.getInstance();
                        mc.player.connection.sendChat(baritoneCmd);

                        System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
                        mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                    } catch (Exception ignored) {}
                    break;
                } else if (args.length == 2) {
                    try {
                        String méthode = args[1];

                        String baritoneCmd = "#elytra " + méthode;

                        var mc = net.minecraft.client.Minecraft.getInstance();
                        mc.player.connection.sendChat(baritoneCmd);

                        System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
                        mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                    } catch (Exception ignored) {}
                    break;
                }
                break;
            }

            case "pause" -> {
                System.out.println("Commande pause détectée");
                try {
                    String baritoneCmd = "#pause";

                    var mc = net.minecraft.client.Minecraft.getInstance();
                    mc.player.connection.sendChat(baritoneCmd);

                    System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
                    mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                } catch (Exception ignored) {}
                break;
            }

            case "resume" -> {
                System.out.println("Commande resume détectée");
                try {
                    String baritoneCmd = "#resume";

                    var mc = net.minecraft.client.Minecraft.getInstance();
                    mc.player.connection.sendChat(baritoneCmd);

                    System.out.println("commande--> " + baritoneCmd + "générée depuis --> " + raw);
                    mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                } catch (Exception ignored) {}
                break;
            }

            case "cancel" -> {
                System.out.println("Commande cancel détectée");
                try {
                    String baritoneCmd = "#cancel";

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
