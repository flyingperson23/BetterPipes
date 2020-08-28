package flyingperson.BetterPipes.util;

import buildcraft.api.transport.pluggable.PluggableDefinition;
import buildcraft.lib.item.ItemPluggableSimple;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemPluggable extends ItemPluggableSimple {
    public ItemPluggable(String id, PluggableDefinition definition, PluggableDefinition.IPluggableCreator creator, @Nullable IPlacementPredicate canPlace) {
        super(id, definition, creator, canPlace);
    }

    public ItemPluggable(String id, PluggableDefinition definition) {
        this(id, definition, definition.creator, null);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("betterpipes.tooltip.bcpart1"));
        tooltip.add(I18n.format("betterpipes.tooltip.blocker2"));
    }
}
