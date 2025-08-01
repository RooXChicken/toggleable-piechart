package org.loveroo.toggleablepiechart.mixin;

import net.minecraft.client.gui.render.state.special.ProfilerChartGuiElementRenderState;
import org.loveroo.toggleablepiechart.client.PieChartClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ProfilerChartGuiElementRenderState.class)
public abstract class PieChartScaleMixin {

    @Inject(method = "scale()F", at = @At("HEAD"), cancellable = true)
    private void modifyScale(CallbackInfoReturnable<Float> info) {
        info.setReturnValue((float)PieChartClient.transform.getScale());
    }
}
