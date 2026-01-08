package com.ren.tchatlink;

import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

public class TchatLinkMixinConnector implements IMixinConnector {
    @Override
    public void connect() {
        System.out.println(">>> MIXIN CONNECTOR CHARGÉ <<<");
        Mixins.addConfiguration("mixins.tchatlink.json");
    }
}
