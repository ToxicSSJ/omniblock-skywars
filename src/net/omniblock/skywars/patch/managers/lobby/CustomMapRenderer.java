package net.omniblock.skywars.patch.managers.lobby;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class CustomMapRenderer extends MapRenderer {
	
    @Override
    public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
    	
        BufferedImage img = null;
        
		try {
			
			System.out.println("LIGHT IT UP!");
			
			URL url = new URL("http://www.omniblock.net/gameserver/skwgs/generic/Musica.png");
			img = ImageIO.read(url);
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
        
        mapView.setScale(MapView.Scale.NORMAL);
        mapCanvas.drawImage(5, 5, img);
        
    }
 
}