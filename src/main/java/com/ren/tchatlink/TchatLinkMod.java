package com.ren.tchatlink;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod("tchatlink")
public class TchatLinkMod {

    public static final String MODID = "tchatlink";
    private static final Logger LOGGER = LogUtils.getLogger();

    public TchatLinkMod() {
        LOGGER.info("TchatLink chargé !");

        new Debug();

        MinecraftForge.EVENT_BUS.register(this);
    }
}
