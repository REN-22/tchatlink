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
    private long lastCleanup = 0; 

    private void cleanup() { long now = System.currentTimeMillis(); 
        if (now - lastCleanup > 50) { 
            processed.clear(); 
            lastCleanup = now;
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

        String msg = raw.substring(raw.indexOf("> ") + 1);

        String[] morceaux = msg.split(" ");
        String commande = morceaux[0];

        switch (commande) {
            case "goal" -> {
                String[] args = msg.split(" ");
                switch (args.length) {
                    case 1:
                        try {
                            String baritoneCmd = "#goal";

                            var mc = net.minecraft.client.Minecraft.getInstance();
                            mc.player.connection.sendChat(baritoneCmd);

                            mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                        } catch (Exception ignored) {}
                        break;
                    case 2:
                        if (args[1].equals("reset") || args[1].equals("clear") || args[1].equals("none")) {
                            try {
                                String baritoneCmd2 = "#goal " + args[1];

                                var mc2 = net.minecraft.client.Minecraft.getInstance();
                                mc2.player.connection.sendChat(baritoneCmd2);

                                mc2.player.sendSystemMessage(Component.literal(baritoneCmd2));
                            } catch (Exception ignored) {}
                            break;
                        } else if (args[1].matches("\\d+") || args[1].matches("\\d+\\.\\d+")) {
                            try {
                            double y = Double.parseDouble(args[1]);
                            String baritoneCmd2 = "#goal " + y;

                            var mc2 = net.minecraft.client.Minecraft.getInstance();
                            mc2.player.connection.sendChat(baritoneCmd2);

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

                            mc4.player.sendSystemMessage(Component.literal(baritoneCmd4));
                        } catch (Exception ignored) {}
                        break;
                    default:
                        break;
                }
                break;
            }

            case "goto" -> {
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
                break;
            }

            case "path" -> {
                try {
                    String baritoneCmd = "#path";

                    var mc = net.minecraft.client.Minecraft.getInstance();
                    mc.player.connection.sendChat(baritoneCmd);

                    mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                } catch (Exception ignored) {}
                break;
            }

            case "forcecancel" -> {
                try {
                    String baritoneCmd = "#forcecancel";

                    var mc = net.minecraft.client.Minecraft.getInstance();
                    mc.player.connection.sendChat(baritoneCmd);

                    mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                } catch (Exception ignored) {}
                break;
            }

            case "invert" -> {
                try {
                    String baritoneCmd = "#invert";

                    var mc = net.minecraft.client.Minecraft.getInstance();
                    mc.player.connection.sendChat(baritoneCmd);

                    mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                } catch (Exception ignored) {}
                break;
            }

            case "tunnel" -> {
                String[] args = msg.split(" ");

                if (args.length == 1) {
                    try {
                        String baritoneCmd = "#tunnel";

                        var mc = net.minecraft.client.Minecraft.getInstance();
                        mc.player.connection.sendChat(baritoneCmd);

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

                        mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                    } catch (Exception ignored) {}
                };
                break;
            }

            case "farm" -> {
                String[] args = msg.split(" ");

                if (args.length == 1) {
                    try {
                        String baritoneCmd = "#farm";

                        var mc = net.minecraft.client.Minecraft.getInstance();
                        mc.player.connection.sendChat(baritoneCmd);

                        mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                    } catch (Exception ignored) {}
                } else if (args.length == 2) {
                    try {
                        int range = Integer.parseInt(args[1]);

                        String baritoneCmd = "#farm " + range;

                        var mc = net.minecraft.client.Minecraft.getInstance();
                        mc.player.connection.sendChat(baritoneCmd);

                        mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                    } catch (Exception ignored) {}
                } else if (args.length == 3) {
                    try {
                        int range = Integer.parseInt(args[1]);
                        String waypoint = args[2];

                        String baritoneCmd = "#farm " + range + " " + waypoint;

                        var mc = net.minecraft.client.Minecraft.getInstance();
                        mc.player.connection.sendChat(baritoneCmd);

                        mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                    } catch (Exception ignored) {}
                };
                break;
            }

            case "follow" -> {
                String[] args = msg.split(" ");
                switch (args[1]) {
                    case "entities":
                        try {
                            String baritoneCmd = "#follow entities";

                            var mc = net.minecraft.client.Minecraft.getInstance();
                            mc.player.connection.sendChat(baritoneCmd);

                            mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                        } catch (Exception ignored) {}
                        break;

                    case "entity":
                        try {
                            String entityName = args[2];
                            String baritoneCmd = "#follow entity " + entityName;

                            var mc = net.minecraft.client.Minecraft.getInstance();
                            mc.player.connection.sendChat(baritoneCmd);

                            mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                        } catch (Exception ignored) {}

                    case "player":
                        try {
                            String playerName = args[2];
                            String baritoneCmd = "#follow player " + playerName;

                            var mc = net.minecraft.client.Minecraft.getInstance();
                            mc.player.connection.sendChat(baritoneCmd);

                            mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                        } catch (Exception ignored) {}
                        break;
                    default:
                        break;
                }
                break;
            }

            case "stop" -> {
                try {
                    String baritoneCmd = "#stop";

                    var mc = net.minecraft.client.Minecraft.getInstance();
                    mc.player.connection.sendChat(baritoneCmd);

                    mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                } catch (Exception ignored) {}
                break;
            }

            case "pickup" -> {
                String[] args = msg.split(" ");

                if (args.length == 1) {
                    try {
                        String baritoneCmd = "#pickup";

                        var mc = net.minecraft.client.Minecraft.getInstance();
                        mc.player.connection.sendChat(baritoneCmd);

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

                        mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                    } catch (Exception ignored) {}

                };
                break;
            }

            case "explore" -> {
                String[] args = msg.split(" ");

                if (args.length == 1) {
                    try {
                        String baritoneCmd = "#explore";

                        var mc = net.minecraft.client.Minecraft.getInstance();
                        mc.player.connection.sendChat(baritoneCmd);

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

                        mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                    } catch (Exception ignored) {}
                }
                break;
            }

            case "find" -> {
                String[] args = msg.split(" ");

                if (args.length != 2) break;

                String blockName = args[1];
                try {
                    String baritoneCmd = "#find " + blockName;

                    var mc = net.minecraft.client.Minecraft.getInstance();
                    mc.player.connection.sendChat(baritoneCmd);

                    mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                } catch (Exception ignored) {}
                break;
            }

            case "mine" -> {
                String[] args = msg.split(" ");

                if (args.length != 2) break;

                String blockName = args[1];
                try {
                    String baritoneCmd = "#mine " + blockName;

                    var mc = net.minecraft.client.Minecraft.getInstance();
                    mc.player.connection.sendChat(baritoneCmd);

                    mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                } catch (Exception ignored) {}
                break;
            }

            // case "click" -> {
            //     try {
            //         String baritoneCmd = "#click";

            //         var mc = net.minecraft.client.Minecraft.getInstance();
            //         mc.player.connection.sendChat(baritoneCmd);

            //         mc.player.sendSystemMessage(Component.literal(baritoneCmd));
            //     } catch (Exception ignored) {}
            //     break;
            // }

            case "surface" -> {
                try {
                    String baritoneCmd = "#surface";

                    var mc = net.minecraft.client.Minecraft.getInstance();
                    mc.player.connection.sendChat(baritoneCmd);

                    mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                } catch (Exception ignored) {}
                break;
            }

            case "thisway" -> {
                String[] args = msg.split(" ");

                try {
                    double distance = Double.parseDouble(args[1]);

                    String baritoneCmd = "#thisway " + distance;

                    var mc = net.minecraft.client.Minecraft.getInstance();
                    mc.player.connection.sendChat(baritoneCmd);

                    mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                } catch (Exception ignored) {}
                break;
            }

            case "wp", "waypoint", "waypoints" -> {
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
                try {
                    String baritoneCmd = "#sethome";

                    var mc = net.minecraft.client.Minecraft.getInstance();
                    mc.player.connection.sendChat(baritoneCmd);

                    mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                } catch (Exception ignored) {}
                break;
            }

            case "home" -> {
                try {
                    String baritoneCmd = "#home";

                    var mc = net.minecraft.client.Minecraft.getInstance();
                    mc.player.connection.sendChat(baritoneCmd);

                    mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                } catch (Exception ignored) {}
                break;
            }

            case "elytra" -> {
                String[] args = msg.split(" ");

                if (args.length == 1) {
                    try {
                        String baritoneCmd = "#elytra";

                        var mc = net.minecraft.client.Minecraft.getInstance();
                        mc.player.connection.sendChat(baritoneCmd);

                        mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                    } catch (Exception ignored) {}
                    break;
                } else if (args.length == 2) {
                    try {
                        String méthode = args[1];

                        String baritoneCmd = "#elytra " + méthode;

                        var mc = net.minecraft.client.Minecraft.getInstance();
                        mc.player.connection.sendChat(baritoneCmd);

                        mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                    } catch (Exception ignored) {}
                    break;
                }
                break;
            }

            case "pause" -> {
                try {
                    String baritoneCmd = "#pause";

                    var mc = net.minecraft.client.Minecraft.getInstance();
                    mc.player.connection.sendChat(baritoneCmd);

                    mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                } catch (Exception ignored) {}
                break;
            }

            case "resume" -> {
                try {
                    String baritoneCmd = "#resume";

                    var mc = net.minecraft.client.Minecraft.getInstance();
                    mc.player.connection.sendChat(baritoneCmd);

                    mc.player.sendSystemMessage(Component.literal(baritoneCmd));
                } catch (Exception ignored) {}
                break;
            }

            case "cancel" -> {
                try {
                    String baritoneCmd = "#cancel";

                    var mc = net.minecraft.client.Minecraft.getInstance();
                    mc.player.connection.sendChat(baritoneCmd);

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
