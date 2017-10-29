package net.omniblock.skywars.patch.readers;

import net.omniblock.packets.network.Packets;
import net.omniblock.packets.network.structure.data.PacketSocketData;
import net.omniblock.packets.network.structure.data.PacketStructure;
import net.omniblock.packets.network.structure.data.PacketStructure.DataType;
import net.omniblock.packets.network.structure.packet.GameInitializerInfoPacket;
import net.omniblock.packets.network.tool.object.PacketReader;
import net.omniblock.packets.object.external.GamePreset;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.patch.types.SkywarsType;

public class GameReader {

	public static void start() {

		Packets.READER.registerReader(new PacketReader<GameInitializerInfoPacket>() {

			@Override
			public void readPacket(PacketSocketData<GameInitializerInfoPacket> packetsocketdata) {

				if (Skywars.currentMatchType != null) {
					if (Skywars.currentMatchType != SkywarsType.NONE) {
						return;
					}
				}

				PacketStructure structure = packetsocketdata.getStructure();

				GamePreset preset = GamePreset.valueOf(structure.get(DataType.STRINGS, "gamepreset"));
				SkywarsType type = SkywarsType.withPreset(preset);

				if (type != null)
					type.makeMatch();

				return;

			}

			@Override
			public Class<GameInitializerInfoPacket> getAttachedPacketClass() {
				return GameInitializerInfoPacket.class;
			}

		});

	}

}
