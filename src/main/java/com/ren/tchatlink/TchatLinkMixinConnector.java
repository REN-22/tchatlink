package com.ren.tchatlink;

import org.spongepowered.asm.mixin.connect.IMixinConnector;
import org.spongepowered.asm.mixin.Mixins;

public class TchatLinkMixinConnector implements IMixinConnector {
    @Override
    public void connect() {
        System.out.println(">>> MIXIN CONNECTOR CHARGÉ <<<");
        Mixins.addConfiguration("mixins.tchatlink.json");
    }
}
