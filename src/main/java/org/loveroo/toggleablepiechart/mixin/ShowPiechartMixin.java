package org.loveroo.toggleablepiechart.mixin;

import org.loveroo.toggleablepiechart.ToggleablepiechartClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.DebugHud;

@Mixin(DebugHud.class)
public abstract class ShowPiechartMixin {
    @Inject(method = "shouldShowRenderingChart()Z", at = @At(value = "HEAD", target = "Lnet/minecraft/client/MinecraftClient;run()V"), cancellable = true)
    public void enablePiechartLogging(CallbackInfoReturnable<Boolean> _info) {
        // we need the game to still track the debug info, this is needed
        _info.setReturnValue(ToggleablepiechartClient.piechartToggled);
        _info.cancel();
    }

    @Inject(method = "shouldShowRenderingChart()Z", at = @At("HEAD"), cancellable = true)
    public void makeChartVisible(CallbackInfoReturnable<Boolean> _info) {
        // because we render the piechart outselves manually, we just tell the game to "never render it"
        _info.setReturnValue(false);
    }
}
