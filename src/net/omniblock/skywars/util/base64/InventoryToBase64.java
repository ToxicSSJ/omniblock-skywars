package net.omniblock.skywars.util.base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

public class InventoryToBase64 {
	
    public static String toBase64(Inventory inventory) {
    	
        try {
        	
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
        
            dataOutput.writeInt(inventory.getSize());
        
            for (int i = 0; i < inventory.getSize(); i++) {
                dataOutput.writeObject(inventory.getItem(i));
            }
        
            dataOutput.close();
            
            return Base64Coder.encodeLines(outputStream.toByteArray());
            
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
        
    }

    public static Set<Map.Entry<Integer, ItemStack>> fromBase64(String data) throws IOException {
    	
        try {
        	
        	Map<Integer, ItemStack> map = new HashMap<Integer, ItemStack>();
        	
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

            // Read the serialized inventory
            for (int i = 0; i < dataInput.readInt(); i++) {
            	map.put(i, (ItemStack) dataInput.readObject());
            }
            
            dataInput.close();
            
            return map.entrySet();
            
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
        
    }
    
}