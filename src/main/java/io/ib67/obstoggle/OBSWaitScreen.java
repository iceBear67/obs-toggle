package io.ib67.obstoggle;

import io.ib67.obstoggle.config.OBSConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.concurrent.atomic.AtomicBoolean;

public class OBSWaitScreen extends Screen {
    private final Screen parent;
    private final AtomicBoolean state;
    private final boolean delayScreen;
    private final long time = System.currentTimeMillis();
    private boolean failed;

    public OBSWaitScreen(Screen parent, AtomicBoolean state, boolean delayScreen) {
        super(Text.translatable("screen.obstoggle.waiting"));
        this.delayScreen = delayScreen;
        System.out.println("hello");
        this.parent = parent;
        this.state = state;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (delayScreen && System.currentTimeMillis() - time < 400) {
            textRenderer.draw(matrices, Text.translatable("screen.obstoggle.hint"), 0, 0, 0x5C5C5C5C);
        } else {
            this.renderBackground(matrices, 0);
            drawCenteredText(
                    matrices,
                    this.textRenderer,
                    failed ? Text.translatable("screen.obstoggle.fail") : Text.translatable("screen.obstoggle.waiting"),
                    this.width / 2, this.height / 2 - 50, 16777215);
        }
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void tick() {
        var timeout = System.currentTimeMillis() - time >= OBSConfig.timeout * 1000L;
        if (state.get()) {
            back();
        } else if (timeout && !failed) {
            failed = true;
            showBackButton();
        }
    }

    private void back() {
        this.client.setScreen(parent);
        if (parent == null) this.client.mouse.lockCursor();
    }

    private void showBackButton() {
        this.addDrawableChild(
                new ButtonWidget(
                        this.width / 2 - 100,
                        Math.min(this.height / 2 + 9, this.height - 30),
                        200,
                        20,
                        Text.translatable("screen.obstoggle.back"),
                        button -> back()
                )
        );
    }
}
