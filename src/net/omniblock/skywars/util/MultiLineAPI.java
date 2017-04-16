package net.omniblock.skywars.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListeningWhitelist;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.injector.GamePhase;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.omniblock.skywars.Skywars;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Class by iso2013 @ 2017.
 * <p>
 * Licensed under LGPLv3. See LICENSE.txt for more information.
 * You may copy, distribute and modify the software provided that modifications are described and licensed for free
 * under LGPL. Derivatives works (including modifications or anything statically linked to the library) can only be
 * redistributed under LGPL, but applications that use the library don't have to be.
 */

public final class MultiLineAPI {

	public static Plugin inst;
    //All player's Tag objects that correspond to their players.
    public static Map<UUID, Tag> tags;
    //The list of currently registered controllers.
    private static List<TagController> registeredControllers;
    //The packet handler for ProtocolLib. Used for controlling mount packets and despawn packets.
    private static PacketHandler pckt;
    //The event handler. Used for automatic enabling, entity relocation, and repairing on teleportation.
    private static EventListener evnt;
    private static Map<String, Integer> trackingRanges;

    /*
    I use a constructor for initializing non-Bukkit variables to new objects, and onEnable for setting their values
    or calling actual updates.
     */
    public static void start() {
        inst = Skywars.getInstance();
        tags = Maps.newHashMap();
        registeredControllers = Lists.newArrayList();
        trackingRanges = Maps.newHashMap();
    }

    /**
     * Checks if the event listener will automatically enable new players.
     *
     * @return whether or not new players will be automatically enabled
     */
    public static boolean isAutoEnable() {
        return evnt.isAutoEnable();
    }

    /**
     * Sets whether or not new players should be automatically enabled.
     *
     * @param val whether or not new players will be automatically enabled
     */
    public static void setAutoEnable(boolean val) {
        evnt.setAutoEnable(val);
    }

    /**
     * Enables the API for usage on the specified player.
     *
     * @param p the player to enable
     */
    public static void enable(Player p) {
        if (!tags.containsKey(p.getUniqueId())) {
            tags.put(p.getUniqueId(), new Tag(p, registeredControllers));
        }
    }

    /**
     * Disables the API for usage on the specified player.
     *
     * @param p the player to disable
     */
    public static void disable(Player p) {
        if (tags.containsKey(p.getUniqueId())) {
            tags.remove(p.getUniqueId()).remove();
        }
    }

    /**
     * Disables everyone on the API. Used in onDisable for server stopping.
     */
    public static void disable() {
        tags.values().forEach(Tag::remove);
        tags.clear();
    }

    /**
     * Checks whether a player is enabled.
     *
     * @param p the player to check the status of
     * @return whether or not the player is enabled
     */
    public static boolean isEnabled(Player p) {
        return tags.containsKey(p.getUniqueId());
    }

    /**
     * Gets the name which the main tag for a player is currently set to.
     *
     * @param p the player to get the name of
     * @return the player's name
     */
    public static TagLine getName(Player p) {
        Preconditions.checkArgument(tags.containsKey(p.getUniqueId()), "Player does not have API enabled!");
        return tags.get(p.getUniqueId()).getName();
    }

    /**
     * Gets a line by the specified index. Line numbers go from top to bottom, starting at zero and not including the
     * player's nametag. The line must exist in order to be retrieved.
     *
     * @param controller The controller to get a line for
     * @param p          The player to get a line of
     * @param lineIndex  The index of the line to get, starting at zero at the top and goes to the bottom
     * @return The line object that allows editing of the line
     */
    public static TagLine getLine(TagController controller, Player p, int lineIndex) {
        Preconditions.checkArgument(tags.containsKey(p.getUniqueId()), "Player does not have API enabled!");
        return tags.get(p.getUniqueId()).getLine(controller, lineIndex);
    }

    /**
     * Add a line to the specified player.
     *
     * @param controller The controller to add a line for
     * @param p          The player to add a line to
     * @return The line object that allows editing of the new line
     */
    public static TagLine addLine(TagController controller, Player p) {
        Preconditions.checkArgument(tags.containsKey(p.getUniqueId()), "Player does not have API enabled!");
        TagLine t = tags.get(p.getUniqueId()).addLine(controller);
        hide(p);
        return t;
    }

    /**
     * Remove a specified line of a player. Be sure the line you remove belongs to your plugin.
     *
     * @param controller The controller to remove a line from
     * @param p          The player to remove a line of
     * @param lineIndex  The index of the line to remove
     */
    public static void removeLine(TagController controller, Player p, int lineIndex) {
        Preconditions.checkArgument(tags.containsKey(p.getUniqueId()), "Player does not have API enabled!");
        tags.get(p.getUniqueId()).removeLine(controller, lineIndex);
    }

    /**
     * Remove a specified line of a player. Be sure the line you remove belongs to your plugin.
     *
     * @param controller The controller to remove a line from
     * @param p          The player to remove a line of
     * @param line       The line to remove
     */
    public static void removeLine(TagController controller, Player p, TagLine line) {
        Preconditions.checkArgument(tags.containsKey(p.getUniqueId()), "Player does not have API enabled!");
        tags.get(p.getUniqueId()).removeLine(controller, line);
    }

    /**
     * Get the number of lines a player's tag has. For appearance purposes, this is recommended to never be higher
     * than 3 or 4.
     *
     * @param p The player to get the line count of
     * @return The number of lines the player's tag has
     */
    public static int getLineCount(Player p) {
        Preconditions.checkArgument(tags.containsKey(p.getUniqueId()), "Player does not have API enabled!");
        return tags.get(p.getUniqueId()).getNumLines();
    }

    /**
     * Get the number of lines a player's tag has. For appearance purposes, this is recommended to never be higher
     * than 3 or 4.
     *
     * @param controller The controller to get the line count of
     * @param p          The player to get the line count of
     * @return The number of lines the player's tag has
     */
    public static int getLineCount(TagController controller, Player p) {
        Preconditions.checkArgument(tags.containsKey(p.getUniqueId()), "Player does not have API enabled!");
        return tags.get(p.getUniqueId()).getNumLines(controller);
    }

    /**
     * Refresh a player's tag. Call after any tag addition or removal to update the tag for all players.
     *
     * @param p The player to refresh
     */
    public static void refresh(Player p) {
        Preconditions.checkArgument(tags.containsKey(p.getUniqueId()), "Player does not have API enabled!");
        refreshForEveryone(p);
    }

    /**
     * Refresh all players in a player's view for the specified player. Used in onWorldChange and onTeleport to
     * repair broken tags.
     *
     * @param p The player whose view should be refreshed
     */
    public static void refreshOthers(Player p) {
    	
        refreshView(p);
        
        evnt = new EventListener();
        pckt = new PacketHandler();

        Skywars.getInstance().getServer().getPluginManager().registerEvents(evnt, Skywars.getInstance());

        YamlConfiguration configuration = Bukkit.spigot().getConfig();
        ConfigurationSection section = configuration.getConfigurationSection("world-settings");
        for (String s : section.getKeys(false)) {
            trackingRanges.put(s, section.getInt(s + ".entity-tracking-range.players"));
        }
        
    }

    /**
     * Clear all lines of a player. Be sure you are not removing other plugin's lines. Recommended to use removeLine
     * Skywars.getInstance()ead.
     *
     * @param p The player whose lines should be cleared
     */
    public static void clearLines(Player p) {
        tags.get(p.getUniqueId()).clear();
    }

    public static boolean hasLines(Player p) {
    	return tags.containsKey(p);
    }
    
    /**
     * Clear all lines of a player registered to a TagController.
     *
     * @param controller The controller to clear the lines of
     * @param p          The player whose lines should be cleared
     */
    public static void clearLines(TagController controller, Player p) {
        tags.get(p.getUniqueId()).clear(controller);
    }

    /**
     * Update the locations of the entities that correspond to a player. They are always at y=-10 below the player.
     * This is used in onMove, onTeleport, and onWorldChange to ensure the entities are still loaded by all clients
     * who can see the player they correspond to.
     *
     * @param p The player whose locations should be updated
     */
    public static void updateLocs(Player p) {
        tags.get(p.getUniqueId()).updateEntityLoc();
    }


    /**
     * Register a TagController class for use with MultiLineAPI.
     *
     * @param t The TagController to register
     */
    public static void register(TagController t) {
        if (!registeredControllers.contains(t)) {
            registeredControllers.add(t);
        }
    }

    /**
     * Check if a TagController is currently registered.
     *
     * @param t The TagController to check
     * @return Whether or not the TagController is registered.
     */
    public static boolean isRegistered(TagController t) {
        return registeredControllers.contains(t);
    }

    /*
    Method for refreshing the view of a specified player. Used internally by #refreshOthers(Player).
     */
    private static void refreshView(Player p) {
        tags.values().stream()
                .filter(s -> s.getOwner().getWorld() == p.getWorld())
                .forEach(s -> {
                            createPairs(s, p);
                        }
                );
    }

    /*
    Creates mount packets that pair the entities together to stack them client-side.
     */
    public static void createPairs(Tag t, Player p) {
        t.refreshPairings();
        int[][] pairings = t.getEntityPairings();
        int[] keys = pairings[0], values = pairings[1];
        for (int i = 0; i < keys.length; i++) {
            pckt.sendMountPacket(p, keys[i], values[i]);
        }
    }

    /*
    Refreshes a specified player for all viewers. Used internally by #refresh(Player).
     */
    private static void refreshForEveryone(Player p) {
        Bukkit.getOnlinePlayers().stream()
                .filter(o -> o.getWorld().getUID() == p.getWorld().getUID())
                .forEach(o -> {
                            createPairs(tags.get(p.getUniqueId()), o);
                        }
                );
    }

    /*
    Used to hide a player's tag from himself, preventing the player's view and interactions from being obstructed by
    the hitboxes.
     */
    public static void hide(Player p) {
        int[] entities = tags.get(p.getUniqueId()).getEntities();
        Integer dist = trackingRanges.get(p.getWorld().getName());
        if (dist == null) dist = trackingRanges.get("default");
        for (Entity e : p.getNearbyEntities(dist, dist, 250)) {
            if (e instanceof Player) {
                if (!VanishManager.canSee(p, (Player) e)) {
                    pckt.hide((Player) e, entities);
                }
            }
        }
        pckt.hide(p, entities);
    }

    public static class VanishManager {

        public static boolean canSee(Player who, Player forWho) {
            //Check VanishUtil
            if (VanishUtil.INVISIBLE_PLAYERS.contains(who)) return false;
            //Check Bukkit's vanish system
            if (!forWho.canSee(who)) return false;
            //Check potion effects
            if (who.hasPotionEffect(PotionEffectType.INVISIBILITY)) return false;
            //Disable tags for users in spectator
            if (forWho.getGameMode() == GameMode.SPECTATOR) return false;
            if (who.getGameMode() == GameMode.SPECTATOR) return false;
            return true;
        }
        
    }

    public static class Tag {
        //The list of entities that compose the base of the tag. Currently it's just the SILVERFISH entity to separate
        // the player's tag from the player's head.
        private final List<Entity> baseEntities;
        //The list of entities that make up the actual tag. This changes whenever the tag is refreshed, and is pulled
        // from the TagLine objects. See #refreshPairings()
        private final List<Entity> stack;
        //The HashMap that represents pairings of entities that should be mounted on each other. Key is the vehicle,
        // Value is the passenger. This is also updated in #refreshPairings()
        private final Map<Entity, Entity> pairings;

        //The TagLine object for the uppermost tag, - the player's name. It cannot be removed.
        private final TagLine name;
        //A list of lines to show underneath the player's name. This should only be changed through addLine(), getLine(),
        // clear() and removeLine() methods.
        private final Map<TagController, List<TagLine>> lines;

        //The player whom this tag belongs to.
        private final Player whoOwns;
        //The location entities should be spawned at when creating this tag.
        private Location entityLoc;

        /**
         * The default constructor for the tag object. This class should not be used in most scenarios, use the main
         * class: MultiLineAPI.
         *
         * @param owner The player who owns the tag.
         * @param owners The list of TagControllers that can modify this tag.
         */
        //Constructor just accepts the player who owns the tag, automatically updates the location, and generates the
        // base and pairings.
        public Tag(Player owner, List<TagController> owners) {
            //Initialize lists and maps to empty values.
            baseEntities = Lists.newArrayList();
            stack = Lists.newArrayList();
            pairings = Maps.newHashMap();
            lines = Maps.newHashMap();

            for (TagController r : owners) {
                lines.put(r, Lists.newArrayList());
            }
            //Set whoOwns to the player provided.
            whoOwns = owner;

            //Update the location entities should spawn at so spawning can be done.
            updateEntityLoc();
            //Create the new TagLine object that represents the player's first line of the name
            name = new TagLine(this, null);
            //Set it to the player's name
            name.setText(owner.getName());
            //Generate the base of the tag. Just a silverfish right now.
            genBase();
            //Refresh the pairings map so that pairings can be sent.
            refreshPairings();
        }

        /**
         * Retrieves the TagLine object that represents the first line of the nametag.
         *
         * @return The TagLine name object
         */
        public TagLine getName() {
            return name;
        }

        /**
         * Add a new line to the player's tag.
         *
         * @param owner The TagController to create a new line for
         * @return The new line that has been added
         */
        public TagLine addLine(TagController owner) {
            Preconditions.checkArgument(lines.containsKey(owner), "Controller is not registered for use with " +
                    "MultiLineAPI!");
            TagLine newLine = new TagLine(this, owner);
            lines.get(owner).add(newLine);
            refreshPairings();
            return newLine;
        }
        
        /**
         * Add a new line to the player's tag.
         *
         * @param owner The TagController to add a new line for
         * @param newLine The new line to add
         * @return The new line that has been added
         */
        public TagLine addLine(TagController owner, TagLine newLine) {
            Preconditions.checkArgument(lines.containsKey(owner), "Controller is not registered for use with " +
                    "MultiLineAPI!");
            Preconditions.checkArgument(lines.get(owner).contains(newLine), "Cannot add an Skywars.getInstance()ance of TagLine to a Tag " +
                    "more than once");
            lines.get(owner).add(newLine);
            refreshPairings();
        	return newLine;
        }

        /**
         * Get a line of the player's tag by a specified index.
         *
         * @param owner The TagController to get a line for
         * @param index The index of the tag to retrieve
         * @return The TagLine that has been retrieved
         */
        public TagLine getLine(TagController owner, int index) {
            Preconditions.checkArgument(lines.containsKey(owner), "Controller is not registered for use with " +
                    "MultiLineAPI!");
            Preconditions.checkArgument(index >= 0 && index < lines.get(owner).size(), "Index " + index + " was not found" +
                    " in list of size " + lines.get(owner).size());
            return lines.get(owner).get(index);
        }

        /**
         * Clear the player's lines. Removes all lines except the player's default name.
         */
        public void clear() {
            Map<TagController, List<TagLine>> tempMap = Maps.newHashMap();
            for (Map.Entry<TagController, List<TagLine>> entry : lines.entrySet()) {
                tempMap.put(entry.getKey(), Lists.newArrayList());
                clear(entry.getKey());
            }
            lines.clear();
            lines.putAll(tempMap);
            refreshPairings();
        }

        /**
         * Clear the player's lines associated with a tag controller. Removes all lines the TagController has added.
         *
         * @param owner The TagController to clear the lines of
         */
        public void clear(TagController owner) {
            Preconditions.checkArgument(lines.containsKey(owner), "Controller is not registered for use with MultiLineAPI!");
            lines.get(owner).forEach(TagLine::remove);
            lines.get(owner).clear();
        }

        /**
         * Get the number of lines a player has.
         *
         * @return The number of lines a player has
         */
        public int getNumLines() {
            int num = 0;
            for (TagController c : lines.keySet()) {
                num += lines.get(c).size();
            }
            return num;
        }

        /**
         * Get the number of lines a player has.
         *
         * @param owner The TagController to get the line count of
         * @return The number of lines the TagController has for the player
         */
        public int getNumLines(TagController owner) {
            Preconditions.checkArgument(lines.containsKey(owner), "Controller is not registered for use with " +
                    "MultiLineAPI!");
            return lines.get(owner).size();
        }

        /**
         * Remove a line from this tag object.
         * @param owner The TagController to remove a line of
         * @param line The TagLine to remove from the tag
         */
        public void removeLine(TagController owner, TagLine line) {
            Preconditions.checkArgument(lines.containsKey(owner), "Controller is not registered for use with MultiLineAPI" +
                    "!");
            lines.get(owner).remove(line);
            refreshPairings();
        }

        /**
         * Remove a line from this tag object based on its index.
         * @param owner The TagController to remove a line of
         * @param index The index of the TagLine that should be removed
         */
        public void removeLine(TagController owner, int index) {
            Preconditions.checkArgument(lines.containsKey(owner), "Controller is not registered for use with " +
                    "MultiLineAPI!");
            Preconditions.checkArgument(index >= 0 && index < lines.get(owner).size(), "Index " + index + " was not found in list of size " + lines.get(owner).size());
            lines.get(owner).remove(index);
        }

        /**
         * Get an array of entity IDs that the stack is comprised of.
         * 
         * @return An array of entity IDs
         */
        public int[] getEntities() {
            List<Entity> stack = new ArrayList<>();
            stack.add(name.getLineEntity());
            stack.addAll(name.getSpaceEntities());
            stack.addAll(baseEntities);
            for (List<TagLine> entries : lines.values()) {
                for (TagLine line : entries) {
                    stack.add(line.getLineEntity());
                    stack.addAll(line.getSpaceEntities());
                }
            }
            int[] ints = new int[stack.size()];
            for (int i = 0; i < ints.length; i++) {
                ints[i] = stack.get(i).getEntityId();
            }
            return ints;
        }

        /** 
         * Get a 2D integer array that represents the pairings map. Only contains entity IDs
         * <br> Index 0 is the list of vehicles
         * <br> Index 1 is the list of passengers
         * 
         * @return the entity ID pairing maps
         */
        public int[][] getEntityPairings() {
            int[] keys = new int[pairings.size()];
            int[] values = new int[pairings.size()];
            List<Map.Entry<Entity, Entity>> entries = new ArrayList<>();
            entries.addAll(pairings.entrySet());
            for (int i = 0; i < keys.length; i++) {
                keys[i] = entries.get(i).getKey().getEntityId();
                values[i] = entries.get(i).getValue().getEntityId();
            }
            return new int[][]{keys, values};
        }

        //Generate the base of the tag. Currently is just a silverfish for spacing.
        private void genBase() {
            baseEntities.add(createGenericEntity(EntityType.SILVERFISH));
        }

        //Refresh the entity pairings so they can be resent
        public void refreshPairings() {
            //Clear the current pairings map
            pairings.clear();
            //Clear the current stack so it can be regenerated.
            stack.clear();
            //Add the player who owns the tag at the bottom of the stack.
            stack.add(whoOwns);
            //Add all of the baseEntities to the stack, after the Player.
            stack.addAll(baseEntities);
            //Reverse the order of the lines, so they are added in the correct order.
            List<Map.Entry<TagController, List<TagLine>>> sortedGroups = Lists.newArrayList(this.lines.entrySet());
            sortedGroups.sort((o1, o2) -> Integer.compare(o2.getKey().getPriority(), o1.getKey().getPriority()));

            List<TagLine> lines = Lists.newArrayList();
            for (Map.Entry<TagController, List<TagLine>> entry : sortedGroups) {
                lines.addAll(entry.getValue());
            }
            Collections.reverse(lines);
            //For each line the tag contains,
            for (TagLine line : lines) {
                if (line.getText() != null) {
                    //Add the text if the text message is not null.
                    stack.add(line.getLineEntity());
                }
                if (line.keepSpaceWhenNull() || line.getText() != null) {
                    //If the line is not null, or the line is set to always keep spacing, add the space entities.
                    stack.addAll(line.getSpaceEntities());
                }
            }
            //Add the line entity for the name.
            stack.add(name.getLineEntity());
            //For each entity in the stack; add it and the one following it to the pairings map.
            for (int i = 0; i < stack.size(); i++) {
                if (i + 1 < stack.size()) {
                    pairings.put(stack.get(i), stack.get(i + 1));
                }
            }
        }

        private LivingEntity createGenericEntity(EntityType type) {
        	
        	NPC npc = CitizensAPI.getNPCRegistry().createNPC(type, "");
        	npc.spawn(entityLoc);
        	npc.setProtected(true);
        	npc.setFlyable(true);
        	
            LivingEntity e = (LivingEntity) npc.getEntity();
            
            e.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 1, true, false));
            
            e.setMetadata("STACK_ENTITY", new FixedMetadataValue(Bukkit.getPluginManager().getPlugin("MultiLineAPI"),
                    whoOwns.getUniqueId()));
            
            return e;
        }

        //Create a slime to go down in the entity stack. Used for generating spaces.
        private LivingEntity createSlime() {
            //Create a new slime through createGenericEntity and cast it to slime
            Slime s = (Slime) createGenericEntity(EntityType.SLIME);
            //Set slime size to -1
            s.setSize(-1);
            return s;
        }

        //Create an armor stand to show the text on.
        LivingEntity createArmorStand() {
            //Create a new armor stand through createGenericEntity and cast it to ArmorStand. Yes, for some reason armor
            // stands are LivingEntities and can hold potion effects. Why? I have no idea...
            ArmorStand as = (ArmorStand) createGenericEntity(EntityType.ARMOR_STAND);
            //Set the armor stand so it is a marker and does not have a hitbox.
            as.setMarker(true);
            //Make the armor stand invisible, since the invisibility potion effect doesn't apply to it.
            as.setVisible(false);
            //Make the custom name visible so it can be used to display text.
            as.setCustomNameVisible(true);
            return as;
        }

        //Create a space and return it. Used by TagLine objects to generate a space.
        List<Entity> createSpace() {
            //Create a new array list to store the entities in.
            List<Entity> space = new ArrayList<>();
            //Add a slime
            space.add(createSlime());
            //Add two silverfishes. This is the proper amount of spacing to create a decent-sized gap.
            space.add(createGenericEntity(EntityType.SILVERFISH));
            space.add(createGenericEntity(EntityType.SILVERFISH));
            return space;
        }

        //Update the location the entities should be at, so players within the view distance will still have them loaded.
        public void updateEntityLoc() {
            //Get the owning player's location
            Location l = whoOwns.getLocation();
            //Set y-level to -10.
            l.setY(-10.0D);
            //Update the variable of this class
            this.entityLoc = l;

            //For each base entity, teleport it to the new location.
            for (Entity e : baseEntities) {
                e.teleport(entityLoc);
            }
            //For each tag line, teleport it's entities to the new location.
            for (List<TagLine> t : lines.values()) {
                for (TagLine t2 : t) {
                    t2.teleport(entityLoc);
                }
            }
        }

        /**
         * Get the owner of the Tag
         * 
         * @return The owner of the Tag
         */
        public Player getOwner() {
            return whoOwns;
        }

        /**
         * Remove the tag from the player. This includes removal of the name, lines and
         * the base entities. Clears all localized data
         */
        public void remove() {
            name.remove();
            for (List<TagLine> t : lines.values()) {
                t.forEach(TagLine::remove);
            }
            baseEntities.forEach(Entity::remove);
        }

        public void tempDisable() {
            if (baseEntities.size() <= 0) return;
            name.tempDisable();
            for (List<TagLine> t : lines.values()) {
                t.forEach(TagLine::tempDisable);
            }
            baseEntities.forEach(Entity::remove);

            baseEntities.clear();
            stack.clear();
            pairings.clear();
        }

        public void reEnable() {
            if (baseEntities.size() > 0) return;
            updateEntityLoc();

            name.reEnable();
            for (List<TagLine> t : lines.values()) {
                t.forEach(TagLine::reEnable);
            }
            genBase();

            refreshPairings();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Tag tag = (Tag) o;

            if (name != null ? !name.equals(tag.name) : tag.name != null) return false;
            return whoOwns.equals(tag.whoOwns);

        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + whoOwns.hashCode();
            return result;
        }
    }
    
    public static class TagLine {

        private final Tag parent;
        private final TagController controller;
        private final List<Entity> spaceEntities;
        private Entity lineEntity;
        private boolean keepSpaceWhenNull;
        private String text;

        /**
         * The default constructor for a TagLine. This should only be called through MultiLineAPI.
         *
         * @param parent The Tag that this TagLine belongs to
         * @param controller The TagController responsible for this tag
         */
        public TagLine(Tag parent, TagController controller) {
            this.parent = parent;
            this.controller = controller;
            this.lineEntity = parent.createArmorStand();
            this.spaceEntities = parent.createSpace();
            this.keepSpaceWhenNull = false;
            this.text = lineEntity.getCustomName();
        }

        /**
         * The line entity is the ArmorStand with the text as it's name. Called by MultiLineAPI.
         *
         * @return The entity that is used to display text
         */
        public Entity getLineEntity() {
            return lineEntity;
        }

        /**
         * The spacing entities are used to separate the armor stands of each tag line. Without them, the text would all
         * show up in the same position and be impossible to read. Called by MultiLineAPI.
         *
         * @return The list of entities that represent a space
         */
        public List<Entity> getSpaceEntities() {
            return spaceEntities;
        }

        /**
         * Whether or not the line should keep the space between the lines before and after when it's text value is null.
         * This should almost always be false for appearance purposes.
         *
         * @param b Whether or not the space should be kept when null
         */
        public void setKeepSpaceWhenNull(boolean b) {
            this.keepSpaceWhenNull = b;
        }

        /**
         * Gets whether or not the line should keep the space when it's value is null.
         *
         * @return Whether or not the space will be kept
         */
        public boolean keepSpaceWhenNull() {
            return keepSpaceWhenNull;
        }

        /**
         * Get the text value of the line.
         *
         * @return The text currently being displayed on the line
         */
        public String getText() {
            return lineEntity.getCustomName();
        }

        /**
         * Set the text value of the line. Set to a String to display it, or null to hide the TagLine.
         *
         * @param s The string that should be displayed
         */
        public void setText(String s) {
            lineEntity.setCustomName(s);
            lineEntity.setCustomNameVisible(s != null);
            text = s;
        }

        /**
         * Teleport the entities for this line to the specified location. Should only be called by MultiLineAPI
         *
         * @param entityLoc The location to teleport the entities to.
         */
        public void teleport(Location entityLoc) {
            lineEntity.teleport(entityLoc);
            for (Entity e : spaceEntities) {
                e.teleport(entityLoc);
            }
        }

        /**
         * Remove the tag line. Used in onDisable and when disabling the API for a player.
         */
        public void remove() {
            lineEntity.remove();
            spaceEntities.forEach(Entity::remove);
        }

        /**
         * Gets the tag this line belongs to.
         *
         * @return The tag this line belongs to
         */
        public Tag getParent() {
            return parent;
        }

        /**
         * Get the TagController this line belongs to.
         *
         * @return The TagController this line belongs to
         */
        public TagController getController() {
            return controller;
        }



        public void tempDisable() {
            lineEntity.remove();
            spaceEntities.forEach(Entity::remove);
            spaceEntities.clear();
        }

        public void reEnable() {
            lineEntity = parent.createArmorStand();
            lineEntity.setCustomName(text);
            lineEntity.setCustomNameVisible(text != null);
            spaceEntities.addAll(parent.createSpace());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TagLine tagLine = (TagLine) o;

            if (keepSpaceWhenNull != tagLine.keepSpaceWhenNull) return false;
            if (!parent.equals(tagLine.parent)) return false;
            if (!controller.equals(tagLine.controller)) return false;
            return text != null ? text.equals(tagLine.text) : tagLine.text == null;

        }

        @Override
        public int hashCode() {
            int result = parent.hashCode();
            result = 31 * result + controller.hashCode();
            result = 31 * result + (keepSpaceWhenNull ? 1 : 0);
            result = 31 * result + (text != null ? text.hashCode() : 0);
            return result;
        }
    }
    
    public static class EventListener implements Listener {

        //Whether or not new players should be automatically enabled
        private boolean autoEnable;

        //Constructor just accepts the API to store in the Skywars.getInstance() variable
        public EventListener() {
        	
            autoEnable = true;
            
        }

        @EventHandler
        public void join(PlayerJoinEvent e) {
            if (!autoEnable) return;

            //If auto joining is enabled, then schedule a task
            Bukkit.getScheduler().runTaskLater(Skywars.getInstance(), () -> {
                //Enable the player with MultiLineAPI. This has to be done one tick later so all players receive the
                // entities.
                MultiLineAPI.enable(e.getPlayer());
                //Refresh the player to send the mount packets to all players.
                MultiLineAPI.refresh(e.getPlayer());
                //Hide the entities one tick later
                Bukkit.getScheduler().runTaskLater(Skywars.getInstance(), () -> {
                    //This has to be done one tick later to ensure the e.getPlayer() has received all the entities -
                    // otherwise some will hide and some won't.
                    hide(e.getPlayer());
                }, 2L);
            }, 1L);
        }

        @EventHandler
        public void onDeath(PlayerDeathEvent e) {
            if (MultiLineAPI.isEnabled(e.getEntity())) {
                Bukkit.getScheduler().runTaskLater(Skywars.getInstance(), () -> tags.get(e.getEntity().getUniqueId()).tempDisable(),
                        20L);
            }
        }

        @EventHandler
        public void onRespawn(PlayerRespawnEvent e) {
            if (MultiLineAPI.isEnabled(e.getPlayer())) {
                Bukkit.getScheduler().runTaskLater(Skywars.getInstance(), () -> {
                    //For some reason this must be done twice... Maybe it has to do with the entity spawning times?
                    tags.get(e.getPlayer().getUniqueId()).reEnable();
                    tags.get(e.getPlayer().getUniqueId()).tempDisable();
                    tags.get(e.getPlayer().getUniqueId()).reEnable();
                    //This number (2L) CANNOT be higher in my testing. Setting it to 10L resulted in no reset of the API.
                }, 2L);
            }
        }

        @EventHandler
        public void leave(PlayerQuitEvent e) {
            //If the player is enabled, then disable the player.
            if (MultiLineAPI.isEnabled(e.getPlayer())) {
                MultiLineAPI.disable(e.getPlayer());
            }
        }

        @EventHandler
        public void damage(EntityDamageEvent e) {
            //If the entity is a member of a player's stack, stop all damage to it.
            if (e.getEntity().hasMetadata("STACK_ENTITY")) e.setCancelled(true);
        }

        @EventHandler
        public void death(EntityDeathEvent e) {
            if (e.getEntity().hasMetadata("STACK_ENTITY")) {
                e.setDroppedExp(0);
                UUID u = (UUID) e.getEntity().getMetadata("STACK_ENTITY").get(0).value();
                Tag t = tags.get(u);
                if (t != null) {
                    t.tempDisable();
                    Bukkit.getScheduler().runTaskLater(Skywars.getInstance(), () -> {
                        t.reEnable();
                        MultiLineAPI.refresh(Bukkit.getPlayer(u));
                    }, 1L);
                }
            }
        }

        @EventHandler
        public void move(PlayerMoveEvent e) {
            //Update the player's entities locations so they follow the player around
            if (MultiLineAPI.isEnabled(e.getPlayer())) {
                MultiLineAPI.updateLocs(e.getPlayer());
            }
        }

        @EventHandler
        public void gamemode(PlayerGameModeChangeEvent e) {
            if (e.getNewGameMode() == GameMode.SPECTATOR) {
                Bukkit.getScheduler().runTaskLater(Skywars.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        MultiLineAPI.refreshOthers(e.getPlayer());
                    }
                }, 1L);
            }
        }

        @EventHandler
        public void teleport(PlayerTeleportEvent e) {
            Bukkit.getScheduler().runTaskLater(Skywars.getInstance(), () -> handle(e), 2L);
        }

        @EventHandler
        public void worldChange(PlayerChangedWorldEvent e) {
            handle(e);
        }

        public void handle(PlayerEvent e) {
            if (MultiLineAPI.isEnabled(e.getPlayer())) {
                MultiLineAPI.updateLocs(e.getPlayer());

                tags.get(e.getPlayer().getUniqueId()).tempDisable();
                tags.get(e.getPlayer().getUniqueId()).reEnable();

                hide(e.getPlayer());

                MultiLineAPI.refresh(e.getPlayer());
            }
            //Refresh other players for that player. This re-sends the mount packets, which break when they change worlds.
            MultiLineAPI.refreshOthers(e.getPlayer());
        }

        //Get whether or not players should automatically be enabled.
        public boolean isAutoEnable() {
            return autoEnable;
        }

        //Set whether or not players should automatically be enabled.
        public void setAutoEnable(boolean autoEnable) {
            this.autoEnable = autoEnable;
        }
    }
    
    public static class PacketHandler implements com.comphenix.protocol.events.PacketListener {
    	
        //The protocol manager for ProtocolLib
        private final ProtocolManager protocol;

        //Constructor accepts the API Skywars.getInstance()ance and retrieves the protocol manager from ProtocolLib.
        public PacketHandler() {
            protocol = ProtocolLibrary.getProtocolManager();
            protocol.addPacketListener(this);
        }

        @Override
        public void onPacketSending(PacketEvent packetEvent) {
            //Pull the PacketContainer object out of the event for easier
            PacketContainer packet = packetEvent.getPacket();
            if (packet.getType().equals(PacketType.Play.Server.NAMED_ENTITY_SPAWN)) {
                spawnPlayer(packet.getUUIDs().read(0), packetEvent.getPlayer());
            } else if (packet.getType().equals(PacketType.Play.Server.MOUNT)) {
                resendMount(packetEvent);
            } else if (packet.getType().equals(PacketType.Play.Server.ENTITY_EFFECT)) {
                handleAddEffect(packetEvent);
            } else if (packet.getType().equals(PacketType.Play.Server.REMOVE_ENTITY_EFFECT)) {
                handleEffect(packetEvent);
            }
        }

        @SuppressWarnings("deprecation")
		private void handleAddEffect(PacketEvent event) {
            PacketContainer p = event.getPacket();
            int entityId = p.getIntegers().read(0);
            int effectId = p.getBytes().read(0);
            if (effectId != PotionEffectType.INVISIBILITY.getId()) return;
            Optional<? extends Player> oP = Bukkit.getOnlinePlayers().stream()
                    .filter(ps -> ps.getEntityId() == entityId)
                    .filter(ps -> ps.getWorld().getUID().equals(event.getPlayer().getWorld().getUID())).findAny();
            if (!oP.isPresent()) return;
            Bukkit.getScheduler().runTask(Skywars.getInstance(), () -> tags.get(oP.get().getUniqueId()).tempDisable());
        }

        private void handleEffect(PacketEvent event) {
            PacketContainer p = event.getPacket();
            PotionEffectType effectId = p.getEffectTypes().read(0);
            if (!effectId.equals(PotionEffectType.INVISIBILITY)) return;
            tags.get(event.getPlayer().getUniqueId()).reEnable();
            MultiLineAPI.refresh(event.getPlayer());
        }

        private void spawnPlayer(UUID who, Player forWho) {
            //Check if the player can see the player that's supposed to spawn. (This if statement may not be necessary,
            // because this method is only fired in NAMED_ENTITY_SPAWN which doesn't get sent if the player is invisible,
            // but oh well.)
            if (forWho.canSee(Bukkit.getPlayer(who))) {
                //And if they can, create the entity pairings 2 ticks later to ensure the entities have spawned.
                Bukkit.getScheduler().runTaskLater(Skywars.getInstance(), () -> {
                    Tag t;
                    if ((t = tags.get(who)) != null) {
                        createPairs(t, forWho);
                    }
                }, 2L);
            }
        }

        @SuppressWarnings("unused")
		private void despawnPlayer(UUID who, Player forWho) {
            //TODO: Implement player de-spawning
        }

        private void resendMount(PacketEvent packetEvent) {
            //This code is designed to remount the entities if another plugin sends an empty mount packet. Due to a bug
            // in #setPassenger(), it has not been tested extensively.
            PacketContainer packet = packetEvent.getPacket();
            int[] passengers = packet.getIntegerArrays().read(0);
            int entity = packet.getIntegers().read(0);
            if (passengers.length == 0) {
                if (packetEvent.getPlayer().getEntityId() == entity) {
                    return;
                }
                Optional<? extends Player> p = Bukkit.getOnlinePlayers().stream()
                        .filter(ps -> ps.getEntityId() == entity)
                        .filter(ps -> ps.getWorld().getUID().equals(packetEvent.getPlayer().getWorld().getUID()))
                        .findAny();
                if (p.isPresent()) {
                    Tag t;
                    if ((t = tags.get(p.get().getUniqueId())) != null) {
                        packetEvent.setCancelled(true);
                        Bukkit.getScheduler().runTask(Skywars.getInstance(), () -> {
                            t.reEnable();
                            createPairs(t, packetEvent.getPlayer());
                        });
                    }
                }
            } else {
                Optional<? extends Player> p = Bukkit.getOnlinePlayers().stream()
                        .filter(ps -> ps.getEntityId() == entity)
                        .filter(ps -> ps.getWorld().getUID().equals(packetEvent.getPlayer().getWorld().getUID()))
                        .findAny();
                if (p.isPresent()) {
                    Tag t;
                    if ((t = tags.get(p.get().getUniqueId())) != null) {
                        List<Integer> entityList = Lists.newArrayList();
                        for (int i : t.getEntities()) entityList.add(i);
                        boolean containsOther = false;
                        for (int i : passengers) {
                            if (!entityList.contains(i)) {
                                containsOther = true;
                                break;
                            }
                        }
                        if (containsOther) {
                            t.tempDisable();
                        }
                    }
                }
            }
        }

        @Override
        public void onPacketReceiving(PacketEvent packetEvent) {
            //404: Method onPacketReceiving(PacketEvent packetEvent) not found.
        }

        @Override
        public ListeningWhitelist getSendingWhitelist() {
            //We only need to listen for the sending of player spawn packets and mount packets, so filter those using the
            // ListeningWhitelist.
            return ListeningWhitelist.newBuilder().normal().gamePhase(GamePhase.PLAYING).types(
                    PacketType.Play.Server.NAMED_ENTITY_SPAWN,
                    PacketType.Play.Server.MOUNT,
                    PacketType.Play.Server.ENTITY_EFFECT,
                    PacketType.Play.Server.REMOVE_ENTITY_EFFECT
            ).build();
        }

        @Override
        public ListeningWhitelist getReceivingWhitelist() {
            //We don't need to listen for anything from the client.
            return ListeningWhitelist.EMPTY_WHITELIST;
        }

        @Override
        public Plugin getPlugin() {
            //Return the API Skywars.getInstance()ance - the plugin this listener belongs to
            return Skywars.getInstance();
        }

        //This method is used to send entity mount packets in the createPairings method of MultiLineAPI.java.
        public void sendMountPacket(Player p, int key, int value) {
            //Create a new packet container with the MOUNT packet type.
            PacketContainer packet = protocol.createPacket(PacketType.Play.Server.MOUNT);
            //Write the vehicle entity ID to the first integer field.
            packet.getIntegers().write(0, key);
            //Write the passenger entity ID in a new empty array list to the first integer list field.
            packet.getIntegerArrays().write(0, new int[]{value});
            try {
                //Send the packet to the player.
                protocol.sendServerPacket(p, packet);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        //This method is used to hide a list of entities from a player by destroying them. This is used Skywars.getInstance()ead of
        // cancelling the entity spawn packets due to the dynamic nature of the list of entities in the Tag class.
        public void hide(Player p, int[] is) {
            //Create a new ENTITY_DESTROY packet.
            PacketContainer packet = protocol.createPacket(PacketType.Play.Server.ENTITY_DESTROY);
            //Write all the entity IDs that need to be hidden to the first integer list field.
            packet.getIntegerArrays().write(0, is);
            try {
                //Send the packet to the player.
                protocol.sendServerPacket(p, packet);
            } catch (InvocationTargetException e) {
            	e.printStackTrace();
            }
        }
    }
    
    public interface TagController {
        int getPriority();
    }
    
}