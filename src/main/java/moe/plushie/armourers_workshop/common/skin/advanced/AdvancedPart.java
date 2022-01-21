package moe.plushie.armourers_workshop.common.skin.advanced;

import java.util.ArrayList;

import net.minecraft.util.math.vector.Vector3d;

public class AdvancedPart {

    private final ArrayList<AdvancedPart> children = new ArrayList<AdvancedPart>();
    public final int partIndex;
    public String name;
    public boolean isStatic = true;
    public boolean enabled = true;
    public float scale = 1F;
    public boolean mirror = false;

    public Vector3d pos = Vector3d.ZERO;
    public Vector3d posOffset = Vector3d.ZERO;

    public Vector3d rotationAngle = Vector3d.ZERO;
    public Vector3d rotationAngleOffset = Vector3d.ZERO;

    public Vector3d rotationPos = Vector3d.ZERO;
    public Vector3d rotationPosOffset = Vector3d.ZERO;

    public AdvancedPart(int partIndex, String name) {
        this.partIndex = partIndex;
        this.name = name;
    }

    public ArrayList<AdvancedPart> getChildren() {
        return children;
    }

    public void setRotationAngleOffset(double x, double y, double z) {
        this.rotationAngleOffset = new Vector3d(x, y, z);
    }
}
