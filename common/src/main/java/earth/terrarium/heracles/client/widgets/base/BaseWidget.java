package earth.terrarium.heracles.client.widgets.base;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseWidget extends AbstractContainerEventHandler implements Renderable, NarratableEntry {

    private final List<GuiEventListener> children = new ArrayList<>();

    public <T extends GuiEventListener> T addChild(T child) {
        this.children.add(child);
        return child;
    }

    public void renderChildren(PoseStack pose, int mouseX, int mouseY, float partialTicks) {
        for (var child : this.children) {
            if (child instanceof Renderable renderable) {
                renderable.render(pose, mouseX, mouseY, partialTicks);
            }
        }
    }

    @Override
    public @NotNull List<? extends GuiEventListener> children() {
        return this.children;
    }

    @Override
    public @NotNull NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {

    }
}
