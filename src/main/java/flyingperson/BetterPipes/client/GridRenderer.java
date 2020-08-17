package flyingperson.BetterPipes.client;

import flyingperson.BetterPipes.BetterPipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

@SideOnly(Side.CLIENT)
public class GridRenderer {

    public static void render(EntityPlayer aPlayer, BlockPos pos, EnumFacing aSide, float aPartialTicks, ArrayList<EnumFacing> connections)
    {
        int aX = pos.getX();
        int aY = pos.getY();
        int aZ = pos.getZ();
        try {
            Class.forName("codechicken.lib.vec.Rotation");
        } catch (ClassNotFoundException e) {
            return;
        }

        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glPushMatrix();
        GL11.glTranslated(-(aPlayer.lastTickPosX + (aPlayer.posX - aPlayer.lastTickPosX) * aPartialTicks), -(aPlayer.lastTickPosY + (aPlayer.posY - aPlayer.lastTickPosY) * aPartialTicks), -(aPlayer.lastTickPosZ + (aPlayer.posZ - aPlayer.lastTickPosZ) * aPartialTicks));
        GL11.glTranslated(aX + 0.5, aY + 0.5, aZ + 0.5);
        codechicken.lib.vec.Rotation.sideRotations[aSide.getIndex()].glApply();
        GL11.glTranslated(0, -0.5025, 0);
        GL11.glLineWidth(2.0F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glBegin(GL11.GL_LINES);
        double animation = (double) BetterPipes.instance.counter / 10;
        double tColor = (animation % 42 < 21 ? 0.25 + ((animation % 21)/40.0) : 0.75 - ((animation % 21)/40.0));
        GL11.glColor4d(tColor, tColor, tColor, 1);


        GL11.glVertex3d(0.50, 0, -0.25);
        GL11.glVertex3d(-0.50, 0, -0.25);
        GL11.glVertex3d(0.50, 0, 0.25);
        GL11.glVertex3d(-0.50, 0, 0.25);
        GL11.glVertex3d(0.25, 0, -0.50);
        GL11.glVertex3d(0.25, 0, 0.50);
        GL11.glVertex3d(-0.25, 0, -0.50);
        GL11.glVertex3d(-0.25, 0, 0.50);
        switch (aSide) {
            case DOWN:
                if (connections.contains(EnumFacing.DOWN)) {
                    GL11.glVertex3d(-0.25, 0, -0.25);
                    GL11.glVertex3d(+0.25, 0, +0.25);
                    GL11.glVertex3d(-0.25, 0, +0.25);
                    GL11.glVertex3d(+0.25, 0, -0.25);
                }
                if (connections.contains(EnumFacing.UP)) {
                    GL11.glVertex3d(-0.50, 0, -0.50);
                    GL11.glVertex3d(-0.25, 0, -0.25);
                    GL11.glVertex3d(-0.50, 0, -0.25);
                    GL11.glVertex3d(-0.25, 0, -0.50);

                    GL11.glVertex3d(+0.50, 0, +0.50);
                    GL11.glVertex3d(+0.25, 0, +0.25);
                    GL11.glVertex3d(+0.50, 0, +0.25);
                    GL11.glVertex3d(+0.25, 0, +0.50);

                    GL11.glVertex3d(+0.50, 0, -0.50);
                    GL11.glVertex3d(+0.25, 0, -0.25);
                    GL11.glVertex3d(+0.50, 0, -0.25);
                    GL11.glVertex3d(+0.25, 0, -0.50);

                    GL11.glVertex3d(-0.50, 0, +0.50);
                    GL11.glVertex3d(-0.25, 0, +0.25);
                    GL11.glVertex3d(-0.50, 0, +0.25);
                    GL11.glVertex3d(-0.25, 0, +0.50);
                }
                if (connections.contains(EnumFacing.NORTH)) {
                    GL11.glVertex3d(-0.25, 0, -0.50);
                    GL11.glVertex3d(+0.25, 0, -0.25);
                    GL11.glVertex3d(-0.25, 0, -0.25);
                    GL11.glVertex3d(+0.25, 0, -0.50);
                }
                if (connections.contains(EnumFacing.SOUTH)) {
                    GL11.glVertex3d(-0.25, 0, +0.50);
                    GL11.glVertex3d(+0.25, 0, +0.25);
                    GL11.glVertex3d(-0.25, 0, +0.25);
                    GL11.glVertex3d(+0.25, 0, +0.50);
                }
                if (connections.contains(EnumFacing.WEST)) {
                    GL11.glVertex3d(-0.50, 0, -0.25);
                    GL11.glVertex3d(-0.25, 0, +0.25);
                    GL11.glVertex3d(-0.50, 0, +0.25);
                    GL11.glVertex3d(-0.25, 0, -0.25);
                }
                if (connections.contains(EnumFacing.EAST)) {
                    GL11.glVertex3d(+0.50, 0, -0.25);
                    GL11.glVertex3d(+0.25, 0, +0.25);
                    GL11.glVertex3d(+0.50, 0, +0.25);
                    GL11.glVertex3d(+0.25, 0, -0.25);
                }
                break;
            case UP:
                if (connections.contains(EnumFacing.DOWN)) {
                    GL11.glVertex3d(-0.50, 0, -0.50);
                    GL11.glVertex3d(-0.25, 0, -0.25);
                    GL11.glVertex3d(-0.50, 0, -0.25);
                    GL11.glVertex3d(-0.25, 0, -0.50);

                    GL11.glVertex3d(+0.50, 0, +0.50);
                    GL11.glVertex3d(+0.25, 0, +0.25);
                    GL11.glVertex3d(+0.50, 0, +0.25);
                    GL11.glVertex3d(+0.25, 0, +0.50);

                    GL11.glVertex3d(+0.50, 0, -0.50);
                    GL11.glVertex3d(+0.25, 0, -0.25);
                    GL11.glVertex3d(+0.50, 0, -0.25);
                    GL11.glVertex3d(+0.25, 0, -0.50);

                    GL11.glVertex3d(-0.50, 0, +0.50);
                    GL11.glVertex3d(-0.25, 0, +0.25);
                    GL11.glVertex3d(-0.50, 0, +0.25);
                    GL11.glVertex3d(-0.25, 0, +0.50);
                }
                if (connections.contains(EnumFacing.UP)) {
                    GL11.glVertex3d(-0.25, 0, -0.25);
                    GL11.glVertex3d(+0.25, 0, +0.25);
                    GL11.glVertex3d(-0.25, 0, +0.25);
                    GL11.glVertex3d(+0.25, 0, -0.25);
                }
                if (connections.contains(EnumFacing.NORTH)) {
                    GL11.glVertex3d(-0.25, 0, +0.50);
                    GL11.glVertex3d(+0.25, 0, +0.25);
                    GL11.glVertex3d(-0.25, 0, +0.25);
                    GL11.glVertex3d(+0.25, 0, +0.50);
                }
                if (connections.contains(EnumFacing.SOUTH)) {
                    GL11.glVertex3d(-0.25, 0, -0.50);
                    GL11.glVertex3d(+0.25, 0, -0.25);
                    GL11.glVertex3d(-0.25, 0, -0.25);
                    GL11.glVertex3d(+0.25, 0, -0.50);
                }
                if (connections.contains(EnumFacing.WEST)) {
                    GL11.glVertex3d(-0.50, 0, -0.25);
                    GL11.glVertex3d(-0.25, 0, +0.25);
                    GL11.glVertex3d(-0.50, 0, +0.25);
                    GL11.glVertex3d(-0.25, 0, -0.25);
                }
                if (connections.contains(EnumFacing.EAST)) {
                    GL11.glVertex3d(+0.50, 0, -0.25);
                    GL11.glVertex3d(+0.25, 0, +0.25);
                    GL11.glVertex3d(+0.50, 0, +0.25);
                    GL11.glVertex3d(+0.25, 0, -0.25);
                }
                break;
            case NORTH:
                if (connections.contains(EnumFacing.DOWN)) {
                    GL11.glVertex3d(-0.25, 0, +0.50);
                    GL11.glVertex3d(+0.25, 0, +0.25);
                    GL11.glVertex3d(-0.25, 0, +0.25);
                    GL11.glVertex3d(+0.25, 0, +0.50);
                }
                if (connections.contains(EnumFacing.UP)) {
                    GL11.glVertex3d(-0.25, 0, -0.50);
                    GL11.glVertex3d(+0.25, 0, -0.25);
                    GL11.glVertex3d(-0.25, 0, -0.25);
                    GL11.glVertex3d(+0.25, 0, -0.50);
                }
                if (connections.contains(EnumFacing.NORTH)) {
                    GL11.glVertex3d(-0.25, 0, -0.25);
                    GL11.glVertex3d(+0.25, 0, +0.25);
                    GL11.glVertex3d(-0.25, 0, +0.25);
                    GL11.glVertex3d(+0.25, 0, -0.25);
                }
                if (connections.contains(EnumFacing.SOUTH)) {
                    GL11.glVertex3d(-0.50, 0, -0.50);
                    GL11.glVertex3d(-0.25, 0, -0.25);
                    GL11.glVertex3d(-0.50, 0, -0.25);
                    GL11.glVertex3d(-0.25, 0, -0.50);

                    GL11.glVertex3d(+0.50, 0, +0.50);
                    GL11.glVertex3d(+0.25, 0, +0.25);
                    GL11.glVertex3d(+0.50, 0, +0.25);
                    GL11.glVertex3d(+0.25, 0, +0.50);

                    GL11.glVertex3d(+0.50, 0, -0.50);
                    GL11.glVertex3d(+0.25, 0, -0.25);
                    GL11.glVertex3d(+0.50, 0, -0.25);
                    GL11.glVertex3d(+0.25, 0, -0.50);

                    GL11.glVertex3d(-0.50, 0, +0.50);
                    GL11.glVertex3d(-0.25, 0, +0.25);
                    GL11.glVertex3d(-0.50, 0, +0.25);
                    GL11.glVertex3d(-0.25, 0, +0.50);
                }
                if (connections.contains(EnumFacing.WEST)) {
                    GL11.glVertex3d(-0.50, 0, -0.25);
                    GL11.glVertex3d(-0.25, 0, +0.25);
                    GL11.glVertex3d(-0.50, 0, +0.25);
                    GL11.glVertex3d(-0.25, 0, -0.25);
                }
                if (connections.contains(EnumFacing.EAST)) {
                    GL11.glVertex3d(+0.50, 0, -0.25);
                    GL11.glVertex3d(+0.25, 0, +0.25);
                    GL11.glVertex3d(+0.50, 0, +0.25);
                    GL11.glVertex3d(+0.25, 0, -0.25);
                }
                break;
            case SOUTH:
                if (connections.contains(EnumFacing.DOWN)) {
                    GL11.glVertex3d(-0.25, 0, -0.50);
                    GL11.glVertex3d(+0.25, 0, -0.25);
                    GL11.glVertex3d(-0.25, 0, -0.25);
                    GL11.glVertex3d(+0.25, 0, -0.50);
                }
                if (connections.contains(EnumFacing.UP)) {
                    GL11.glVertex3d(-0.25, 0, +0.50);
                    GL11.glVertex3d(+0.25, 0, +0.25);
                    GL11.glVertex3d(-0.25, 0, +0.25);
                    GL11.glVertex3d(+0.25, 0, +0.50);
                }
                if (connections.contains(EnumFacing.NORTH)) {
                    GL11.glVertex3d(-0.50, 0, -0.50);
                    GL11.glVertex3d(-0.25, 0, -0.25);
                    GL11.glVertex3d(-0.50, 0, -0.25);
                    GL11.glVertex3d(-0.25, 0, -0.50);

                    GL11.glVertex3d(+0.50, 0, +0.50);
                    GL11.glVertex3d(+0.25, 0, +0.25);
                    GL11.glVertex3d(+0.50, 0, +0.25);
                    GL11.glVertex3d(+0.25, 0, +0.50);

                    GL11.glVertex3d(+0.50, 0, -0.50);
                    GL11.glVertex3d(+0.25, 0, -0.25);
                    GL11.glVertex3d(+0.50, 0, -0.25);
                    GL11.glVertex3d(+0.25, 0, -0.50);

                    GL11.glVertex3d(-0.50, 0, +0.50);
                    GL11.glVertex3d(-0.25, 0, +0.25);
                    GL11.glVertex3d(-0.50, 0, +0.25);
                    GL11.glVertex3d(-0.25, 0, +0.50);
                }
                if (connections.contains(EnumFacing.SOUTH)) {
                    GL11.glVertex3d(-0.25, 0, -0.25);
                    GL11.glVertex3d(+0.25, 0, +0.25);
                    GL11.glVertex3d(-0.25, 0, +0.25);
                    GL11.glVertex3d(+0.25, 0, -0.25);
                }
                if (connections.contains(EnumFacing.WEST)) {
                    GL11.glVertex3d(-0.50, 0, -0.25);
                    GL11.glVertex3d(-0.25, 0, +0.25);
                    GL11.glVertex3d(-0.50, 0, +0.25);
                    GL11.glVertex3d(-0.25, 0, -0.25);
                }
                if (connections.contains(EnumFacing.EAST)) {
                    GL11.glVertex3d(+0.50, 0, -0.25);
                    GL11.glVertex3d(+0.25, 0, +0.25);
                    GL11.glVertex3d(+0.50, 0, +0.25);
                    GL11.glVertex3d(+0.25, 0, -0.25);
                }
                break;
            case WEST:
                if (connections.contains(EnumFacing.DOWN)) {
                    GL11.glVertex3d(+0.50, 0, -0.25);
                    GL11.glVertex3d(+0.25, 0, +0.25);
                    GL11.glVertex3d(+0.50, 0, +0.25);
                    GL11.glVertex3d(+0.25, 0, -0.25);
                }
                if (connections.contains(EnumFacing.UP)) {
                    GL11.glVertex3d(-0.50, 0, -0.25);
                    GL11.glVertex3d(-0.25, 0, +0.25);
                    GL11.glVertex3d(-0.50, 0, +0.25);
                    GL11.glVertex3d(-0.25, 0, -0.25);
                }
                if (connections.contains(EnumFacing.NORTH)) {
                    GL11.glVertex3d(-0.25, 0, -0.50);
                    GL11.glVertex3d(+0.25, 0, -0.25);
                    GL11.glVertex3d(-0.25, 0, -0.25);
                    GL11.glVertex3d(+0.25, 0, -0.50);
                }
                if (connections.contains(EnumFacing.SOUTH)) {
                    GL11.glVertex3d(-0.25, 0, +0.50);
                    GL11.glVertex3d(+0.25, 0, +0.25);
                    GL11.glVertex3d(-0.25, 0, +0.25);
                    GL11.glVertex3d(+0.25, 0, +0.50);
                }
                if (connections.contains(EnumFacing.WEST)) {
                    GL11.glVertex3d(-0.25, 0, -0.25);
                    GL11.glVertex3d(+0.25, 0, +0.25);
                    GL11.glVertex3d(-0.25, 0, +0.25);
                    GL11.glVertex3d(+0.25, 0, -0.25);
                }
                if (connections.contains(EnumFacing.EAST)) {
                    GL11.glVertex3d(-0.50, 0, -0.50);
                    GL11.glVertex3d(-0.25, 0, -0.25);
                    GL11.glVertex3d(-0.50, 0, -0.25);
                    GL11.glVertex3d(-0.25, 0, -0.50);

                    GL11.glVertex3d(+0.50, 0, +0.50);
                    GL11.glVertex3d(+0.25, 0, +0.25);
                    GL11.glVertex3d(+0.50, 0, +0.25);
                    GL11.glVertex3d(+0.25, 0, +0.50);

                    GL11.glVertex3d(+0.50, 0, -0.50);
                    GL11.glVertex3d(+0.25, 0, -0.25);
                    GL11.glVertex3d(+0.50, 0, -0.25);
                    GL11.glVertex3d(+0.25, 0, -0.50);

                    GL11.glVertex3d(-0.50, 0, +0.50);
                    GL11.glVertex3d(-0.25, 0, +0.25);
                    GL11.glVertex3d(-0.50, 0, +0.25);
                    GL11.glVertex3d(-0.25, 0, +0.50);
                }
                break;
            case EAST:
                if (connections.contains(EnumFacing.DOWN)) {
                    GL11.glVertex3d(-0.50, 0, -0.25);
                    GL11.glVertex3d(-0.25, 0, +0.25);
                    GL11.glVertex3d(-0.50, 0, +0.25);
                    GL11.glVertex3d(-0.25, 0, -0.25);
                }
                if (connections.contains(EnumFacing.UP)) {
                    GL11.glVertex3d(+0.50, 0, -0.25);
                    GL11.glVertex3d(+0.25, 0, +0.25);
                    GL11.glVertex3d(+0.50, 0, +0.25);
                    GL11.glVertex3d(+0.25, 0, -0.25);
                }
                if (connections.contains(EnumFacing.NORTH)) {
                    GL11.glVertex3d(-0.25, 0, -0.50);
                    GL11.glVertex3d(+0.25, 0, -0.25);
                    GL11.glVertex3d(-0.25, 0, -0.25);
                    GL11.glVertex3d(+0.25, 0, -0.50);
                }
                if (connections.contains(EnumFacing.SOUTH)) {
                    GL11.glVertex3d(-0.25, 0, +0.50);
                    GL11.glVertex3d(+0.25, 0, +0.25);
                    GL11.glVertex3d(-0.25, 0, +0.25);
                    GL11.glVertex3d(+0.25, 0, +0.50);
                }
                if (connections.contains(EnumFacing.WEST)) {
                    GL11.glVertex3d(-0.50, 0, -0.50);
                    GL11.glVertex3d(-0.25, 0, -0.25);
                    GL11.glVertex3d(-0.50, 0, -0.25);
                    GL11.glVertex3d(-0.25, 0, -0.50);

                    GL11.glVertex3d(+0.50, 0, +0.50);
                    GL11.glVertex3d(+0.25, 0, +0.25);
                    GL11.glVertex3d(+0.50, 0, +0.25);
                    GL11.glVertex3d(+0.25, 0, +0.50);

                    GL11.glVertex3d(+0.50, 0, -0.50);
                    GL11.glVertex3d(+0.25, 0, -0.25);
                    GL11.glVertex3d(+0.50, 0, -0.25);
                    GL11.glVertex3d(+0.25, 0, -0.50);

                    GL11.glVertex3d(-0.50, 0, +0.50);
                    GL11.glVertex3d(-0.25, 0, +0.25);
                    GL11.glVertex3d(-0.50, 0, +0.25);
                    GL11.glVertex3d(-0.25, 0, +0.50);
                }
                if (connections.contains(EnumFacing.EAST)) {
                    GL11.glVertex3d(-0.25, 0, -0.25);
                    GL11.glVertex3d(+0.25, 0, +0.25);
                    GL11.glVertex3d(-0.25, 0, +0.25);
                    GL11.glVertex3d(+0.25, 0, -0.25);
                }
                break;
        }
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }





    public static void renderSide(EntityPlayer aPlayer, BlockPos pos, EnumFacing aSide, float aPartialTicks)
    {
        int aX = pos.getX();
        int aY = pos.getY();
        int aZ = pos.getZ();
        try {
            Class.forName("codechicken.lib.vec.Rotation");
        } catch (ClassNotFoundException e) {
            return;
        }

        GL11.glDisable(GL11.GL_CULL_FACE);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);


        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glPushMatrix();
        GL11.glTranslated(-(aPlayer.lastTickPosX + (aPlayer.posX - aPlayer.lastTickPosX) * aPartialTicks), -(aPlayer.lastTickPosY + (aPlayer.posY - aPlayer.lastTickPosY) * aPartialTicks), -(aPlayer.lastTickPosZ + (aPlayer.posZ - aPlayer.lastTickPosZ) * aPartialTicks));
        GL11.glTranslated(aX + 0.5, aY + 0.5, aZ + 0.5);
        codechicken.lib.vec.Rotation.sideRotations[aSide.getIndex()].glApply();
        GL11.glTranslated(0, -0.5025, 0);
        GL11.glLineWidth(2.0F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glBegin(GL11.GL_QUADS);


        double animation = (double) BetterPipes.instance.counter / 10;
        double tColor = (animation % 42 < 21 ? 0.25 + ((animation % 21)/40.0) : 0.75 - ((animation % 21)/40.0));
        GL11.glColor4d(tColor, tColor, tColor, 0.3);

        GL11.glVertex3d(0.5, 0, 0.5);
        GL11.glVertex3d(-0.5, 0, 0.5);
        GL11.glVertex3d(-0.5, 0, -0.5);
        GL11.glVertex3d(0.5, 0, -0.5);

        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

}