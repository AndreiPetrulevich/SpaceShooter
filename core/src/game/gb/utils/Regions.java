package game.gb.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Regions {

    public static TextureRegion[] split (TextureRegion region, int rows, int cols, int frames) {
        if(region == null) throw new RuntimeException("Split null region");
        TextureRegion[] regions = new TextureRegion[frames];
        int width = region.getRegionWidth() / cols;
        int height = region.getRegionHeight() / rows;
        int frame = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                regions[frame] = new TextureRegion(region, width * j, height * i, width, height);
                if (frame == frames - 1) {
                    return regions;
                }
                frame++;

            }
        }
        return regions;
    }
}
