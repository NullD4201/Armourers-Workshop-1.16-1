package moe.plushie.armourers_workshop.common.capability.paint;

import moe.plushie.armourers_workshop.common.painting.PaintType;
import net.minecraft.util.Direction;

public interface IPaintHolder {

    public void setPaintColour(byte[] colour, Direction facing);
    
    public byte[] getPaintColour(Direction facing);
    
    public void setPaintType(PaintType paintType, Direction facing);
    
    public PaintType getPaintType(Direction facing);
}
