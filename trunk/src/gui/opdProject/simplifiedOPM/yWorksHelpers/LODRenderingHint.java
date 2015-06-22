package gui.opdProject.simplifiedOPM.yWorksHelpers;

import java.awt.*;

public class LODRenderingHint {
    public static final Object VALUE_LOD_LOW = "LODRenderingHint#VALUE_LOD_LOW";
    public static final Object VALUE_LOD_MEDIUM = "LODRenderingHint#VALUE_LOD_MEDIUM";
    public static final Object VALUE_LOD_HIGH = "LODRenderingHint#VALUE_LOD_HIGH";
    public static final Object VALUE_LOD_OVERVIEW = "LODRenderingHint#VALUE_LOD_OVERVIEW";

    public static final RenderingHints.Key KEY_LOD = new RenderingHints.Key(0) {
        public boolean isCompatibleValue(Object val) {
            return true; //allow all kinds of values
        }
    };
}
