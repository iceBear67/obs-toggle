package io.ib67.obstoggle.mixin;

import io.ib67.obstoggle.OBSToggle;
import io.ib67.obstoggle.OBSWaitScreen;
import io.ib67.obstoggle.config.OBSConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {
    @Shadow
    public Screen currentScreen;

    @Shadow
    public abstract void setScreen(@Nullable Screen screen);

    @Inject(at = @At("HEAD"), method = "setScreen(Lnet/minecraft/client/gui/screen/Screen;)V", cancellable = true)
    public void onSetScreen(Screen incomingScreen, CallbackInfo info) {
        boolean open = false;
        boolean close = false;
        if (currentScreen == null && incomingScreen instanceof GameMenuScreen) {
            open = true;
        } else if (currentScreen instanceof GameMenuScreen && incomingScreen == null) {
            close = true;
        }
        if ((open || close) && OBSConfig.enabled) {
            var controller = OBSToggle.getInstance().getObsRemoteController();
            info.cancel();
            var state = new AtomicBoolean(false);
            if (open) {
                setScreen(new OBSWaitScreen(incomingScreen,state, true));
                controller.pauseRecord(any -> state.set(true));
            }else{
                setScreen(new OBSWaitScreen(null,state, false));
                controller.resumeRecord(any -> state.set(true));
            }
        }
    }
}
