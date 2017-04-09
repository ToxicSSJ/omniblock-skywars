package net.omniblock.skywars.games.solo.chest.item.z.type;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import net.omniblock.skywars.util.ItemBuilder;

public enum EItem{
	
	/* ━━━━━━━━━━━━━━━━━━━━━━━━━━━━[ Items Epicos ]━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ */
	
	HACHA_VIKINGA(
			"§8§k||§r §c§lHacha Vikinga §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Usada por los",
			"§7vikingos mas temidos",
			"§7se dice que se",
			"§7llamaban Vikingos Z.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.DAMAGE_ALL,
			Enchantment.DURABILITY
	}),
	OZ_INFERNAL(
			"§8§k||§r §c§lOz Infernal §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Proviene de la",
			"§7más temida Muerte, A",
			"§7cobrar Venganza!",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.FIRE_ASPECT,
			Enchantment.DAMAGE_ALL
	}),
	TRIDENTE(
			"§8§k||§r §c§lTridente §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Proveniente de el",
			"§7legendario Dios Poseidon!",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.KNOCKBACK,
			Enchantment.DAMAGE_ALL
	}),
	MATRIX(
			"§8§k||§r §c§lMatrix §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Cuenta la leyenda",
			"§7que los endermans",
			"§7engendraron esta piedra.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.DURABILITY
	}),
	CASCO_BRILLANTE(
			"§8§k||§r §c§lCasco Brillante §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Un casco no tan",
			"§7comun.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.DURABILITY,
			Enchantment.PROTECTION_ENVIRONMENTAL
	}),
	PETO_BRILLANTE(
			"§8§k||§r §c§lPeto Brillante §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Un peto un tanto",
			"§7peculiar.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.DURABILITY,
			Enchantment.PROTECTION_ENVIRONMENTAL
	}),
	PANTALONES_BRILLANTES(
			"§8§k||§r §c§lPantalones Brillantes §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Unos pantalones",
			"§7no tan extraños.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.DURABILITY,
			Enchantment.PROTECTION_ENVIRONMENTAL
	}),
	BOTAS_BRILLANTES(
			"§8§k||§r §c§lBotas Brillantes §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Unas Botas hechas",
			"§7en la cuna del Nether.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.DURABILITY,
			Enchantment.PROTECTION_ENVIRONMENTAL
	}),
	ARCO_PODEROSO(
			"§8§k||§r §c§lArco Poderoso §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Se dice que con",
			"§7sus flechas hace arder",
			"§7a sus enemigos.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.ARROW_DAMAGE,
			Enchantment.ARROW_FIRE
	}),
	TNT(
			"§8§k||§r §c§lTNT §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Explota el mundo",
			"§7entero.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.FIRE_ASPECT
	}),
	CASCO_BRILLANTE_II(
			"§8§k||§r §c§lCasco Brillante II §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Un casco no tan",
			"§7comun.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.DURABILITY,
			Enchantment.PROTECTION_ENVIRONMENTAL
	}),
	PETO_BRILLANTE_II(
			"§8§k||§r §c§lPeto Brillante II §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Un peto un tanto",
			"§7peculiar.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.DURABILITY,
			Enchantment.PROTECTION_ENVIRONMENTAL
	}),
	PANTALONES_BRILLANTES_II(
			"§8§k||§r §c§lPantalones Brillantes II §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Unos pantalones",
			"§7no tan extraños.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.DURABILITY,
			Enchantment.PROTECTION_ENVIRONMENTAL
	}),
	BOTAS_BRILLANTES_II(
			"§8§k||§r §c§lBotas Brillantes II §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Unas Botas hechas",
			"§7en la cuna del Nether.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.DURABILITY,
			Enchantment.PROTECTION_ENVIRONMENTAL
	}),
	ARCO_DE_LAS_TINIEBLAS(
			"§8§k||§r §c§lArco de las Tinieblas §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7No te caigas,",
			"§7porque el impacto te",
			"§7movera al vacio.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.ARROW_DAMAGE,
			Enchantment.ARROW_KNOCKBACK
	}),
	ESPADA_HECHIZADA(
			"§8§k||§r §c§lEspada Hechizada §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Esta espada",
			"§7no deja rastros del",
			"§7enemigo.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.DAMAGE_UNDEAD
	}),
	ESPADA_HECHIZADA_II(
			"§8§k||§r §c§lEspada Hechizada II §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Esta espada",
			"§7no deja rastros del",
			"§7enemigo.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.DAMAGE_UNDEAD
	}),
	HACHA_DEL_DRAGON(
			"§8§k||§r §c§lHacha del Dragon §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7El hacha forjada",
			"§7por las manos del dragon.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.DAMAGE_UNDEAD,
			Enchantment.FIRE_ASPECT
	}),
	ESPADA_DEL_DRAGON("§8§k||§r §c§lEspada del Dragon §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Una espada forjada",
			"§7por las manos del dragon.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.DAMAGE_UNDEAD,
			Enchantment.FIRE_ASPECT
	}),
	OZ_DEL_DRAGON(
			"§8§k||§r §c§lOz del Dragon §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Una Oz forjada",
			"§7por las manos del dragon.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.DAMAGE_UNDEAD,
			Enchantment.FIRE_ASPECT
	}),
	HACHA_DE_RAIZ(
			"§8§k||§r §c§lHacha de Raiz §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Desde la raiz de",
			"§7los arboles legendarios.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.DAMAGE_UNDEAD,
			Enchantment.FIRE_ASPECT
	}),
	PICO_DE_RAIZ(
			"§8§k||§r §c§lPico de Raiz §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Desde la raiz de",
			"§7los arboles legendarios.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.DIG_SPEED,
			Enchantment.FIRE_ASPECT
	}),
	ESPADA_DE_RAIZ(
			"§8§k||§r §c§lEspada de Raiz §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Desde la raiz de",
			"§7los arboles legendarios.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.KNOCKBACK,
			Enchantment.FIRE_ASPECT
	}),
	CASCO_MECANICO(
			"§8§k||§r §c§lCasco Mecanico §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Proviene de las,",
			"§7villas mecanicas.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.PROTECTION_ENVIRONMENTAL,
			Enchantment.THORNS
	}),
	PECHERA_MECANICA(
			"§8§k||§r §c§lPechera Mecanica §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Proviene de las,",
			"§7villas mecanicas.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.PROTECTION_ENVIRONMENTAL,
			Enchantment.THORNS
	}),
	PANTALONES_MECANICOS(
			"§8§k||§r §c§lPantalones Mecanicos §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Proviene de las,",
			"§7villas mecanicas.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.PROTECTION_ENVIRONMENTAL,
			Enchantment.THORNS
	}),
	BOTAS_MECANICOS(
			"§8§k||§r §c§lBotas Mecanicas §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Proviene de las,",
			"§7villas mecanicas.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.PROTECTION_ENVIRONMENTAL,
			Enchantment.THORNS
	}),
	CASCO_FUGAZ(
			"§8§k||§r §c§lCasco Fugaz §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Desde el Cosmos",
			"§7caen como meteoros.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.PROTECTION_ENVIRONMENTAL,
			Enchantment.PROTECTION_EXPLOSIONS,
			Enchantment.PROTECTION_FALL,
			Enchantment.PROTECTION_PROJECTILE
	}),
	PECHERA_FUGAZ(
			"§8§k||§r §c§lPechera Fugaz §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Desde el Cosmos",
			"§7caen como meteoros.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.PROTECTION_ENVIRONMENTAL,
			Enchantment.PROTECTION_EXPLOSIONS,
			Enchantment.PROTECTION_FALL,
			Enchantment.PROTECTION_PROJECTILE
	}),
	PANTALON_FUGAZ(
			"§8§k||§r §c§lPantalon Fugaz §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Desde el Cosmos",
			"§7caen como meteoros.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.PROTECTION_ENVIRONMENTAL,
			Enchantment.PROTECTION_EXPLOSIONS,
			Enchantment.PROTECTION_FALL,
			Enchantment.PROTECTION_PROJECTILE
	}),
	BOTAS_FUGAZ(
			"§8§k||§r §c§lBotas Fugaz §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Desde el Cosmos",
			"§7caen como meteoros.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.PROTECTION_ENVIRONMENTAL,
			Enchantment.PROTECTION_EXPLOSIONS,
			Enchantment.PROTECTION_FALL,
			Enchantment.PROTECTION_PROJECTILE
	}),
	CASCO_FENIX(
			"§8§k||§r §c§lCasco Fenix §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Creación de las",
			"§7poderosas llamas.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.PROTECTION_ENVIRONMENTAL,
			Enchantment.PROTECTION_EXPLOSIONS
	}),
	PECHERA_FENIX(
			"§8§k||§r §c§lPecheraFenix §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Creación de las",
			"§7poderosas llamas.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.PROTECTION_ENVIRONMENTAL,
			Enchantment.PROTECTION_EXPLOSIONS
	}),
	PANTALON_FENIX(
			"§8§k||§r §c§lPantalon Fenix §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Creación de las",
			"§7poderosas llamas.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.PROTECTION_ENVIRONMENTAL,
			Enchantment.PROTECTION_EXPLOSIONS
	}),
	BOTAS_FENIX(
			"§8§k||§r §c§lBotas Fenix §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Creación de las",
			"§7poderosas llamas.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.PROTECTION_ENVIRONMENTAL,
			Enchantment.PROTECTION_EXPLOSIONS
	}),
	ESPADA_X(
			"§8§k||§r §c§lEspada X §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7La nave que la",
			"§7trajo dejo entendido",
			"§7que provenia del futuro",
			"§7con un mensaje extraño:",
			"§701101001 01000011",
			"§701110010 01100001",
			"§701100110 01110100",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.DAMAGE_ALL,
			Enchantment.FIRE_ASPECT
	}),
	PALA_DEL_PODER(
			"§8§k||§r §c§lPala del Poder §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7En algún lugar del",
			"§7mundo se utilizo para",
			"§7combatir un wither gigante.",
			"§7¿Sabes en donde fue?",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.DAMAGE_ALL,
			Enchantment.DAMAGE_UNDEAD
	}),
	ESPADA_EXTRAÑA(
			"§8§k||§r §c§lEspada Extraña §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7No hay datos de",
			"§7donde o como se creo.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"	
	}, new Enchantment[]{
			Enchantment.DAMAGE_ALL
	}),
	PECHERA_ESTELAR(
			"§8§k||§r §c§lPechera Estelar §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Los Astros la",
			"§7denominaron superior.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.PROTECTION_ENVIRONMENTAL
	}),
	PANTALON_ESTELAR(
			"§8§k||§r §c§lPantalon Estelar §8§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Los Astros la",
			"§7denominaron superior.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §d§lEpico",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.PROTECTION_ENVIRONMENTAL
	}),
	
	/* ━━━━━━━━━━━━━━━━━━━━━━━━━━━━[ Items Legendarios ]━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ */
	
	CASCO_DE_OSCURIDAD(
			"§4§k||§r §e§lCasco de la Oscuridad §4§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Hecho por la",
			"§7maldad pura...",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §c§lLegendario",
			"§7§lTipo: §7Protección",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	},new Enchantment[]{
			Enchantment.DURABILITY
	}),
	PETO_DE_LA_OSCURIDAD(
			"§4§k||§r §e§lPeto de la Oscuridad §4§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Hecho por la",
			"§7maldad pura...",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §c§lLegendario",
			"§7§lTipo: §7Protección",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"	
	}, new Enchantment[]{
			Enchantment.DURABILITY,
			Enchantment.PROTECTION_ENVIRONMENTAL
	}),
	PANTALON_DE_LA_OSCURIDAD(
			"§4§k||§r §e§lPantalon de la Oscuridad §4§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Hecho por la",
			"§7maldad pura...",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §c§lLegendario",
			"§7§lTipo: §7Protección",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.DURABILITY,
			Enchantment.PROTECTION_ENVIRONMENTAL
	}),
	BOTAS_DE_LA_OSCURIDAD(
			"§4§k||§r §e§lBotas de la Oscuridad §4§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Hecho por la",
			"§7maldad pura...",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §c§lLegendario",
			"§7§lTipo: §7Protección",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.DURABILITY,
			Enchantment.PROTECTION_FALL
	}),
	CASCO_DE_TITAN(
			"§4§k||§r §e§lCasco Titan §4§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Hace miles de años",
			"§7los titanes forjaron este",
			"§7peculiar casco para",
			"§7proteger el Reino Titan!",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §c§lLegendario",
			"§7§lTipo: §7Protección",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.DURABILITY,
			Enchantment.PROTECTION_FIRE,
			Enchantment.PROTECTION_ENVIRONMENTAL
	}),
	PETO_TITAN(
			"§4§k||§r §e§lPeto Titan §4§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Hace miles de años",
			"§7los titanes forjaron este",
			"§7peculiar peto para",
			"§7proteger el Reino Titan!",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §c§lLegendario",
			"§7§lTipo: §7Protección",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.DURABILITY,
			Enchantment.PROTECTION_FIRE,
			Enchantment.PROTECTION_ENVIRONMENTAL
	}),
	PANTALON_TITAN(
			"§4§k||§r §e§lPantalon Titan §4§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Hace miles de años",
			"§7los titanes forjaron este",
			"§7peculiar pantalon para",
			"§7proteger el Reino Titan!",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §c§lLegendario",
			"§7§lTipo: §7Protección",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.DURABILITY,
			Enchantment.PROTECTION_FIRE,
			Enchantment.PROTECTION_ENVIRONMENTAL
	}),
	BOTAS_TITAN(
			"§4§k||§r §e§lBotas Titan §4§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Hace miles de años",
			"§7los titanes forjaron estas",
			"§7peculiares botas para",
			"§7proteger el Reino Titan!",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §c§lLegendario",
			"§7§lTipo: §7Protección",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.DURABILITY,
			Enchantment.PROTECTION_FIRE,
			Enchantment.PROTECTION_ENVIRONMENTAL,
			Enchantment.PROTECTION_FALL
	}),
	TORRETA_LASER(
			"§4§k||§r §e§lTorreta Laser§4§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7El destroza almas",
			"§7ha regresado!",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §c§lLegendario",
			"§7§lDaño: §70.5❤ x Disparo",
			"§7§lTipo: §7Laser",
			"§7§lTiempo: §7 3 seg",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.FIRE_ASPECT
	}),
	TORRETA_PORCINA(
			"§4§k||§r §e§lTorreta Porcina §4§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Oink, Oink, Oink",
			"§7Oink.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §c§lLegendario",
			"§7§lDaño: §73❤",
			"§7§lOtras: ",
		    "§7Ceguera II ; 3 seg",
		    "§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.FIRE_ASPECT
	}),
	COFRE_EXPLOSIVO(
			"§4§k||§r §e§lCofre Explosivo §4§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7Será como un regalo para",
			"§7tus enemigos.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §c§lLegendario",
			"§7§lDaño: §73❤",
			"§7§lOtras: ",
			"    §7Ceguera II ; 3 seg",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.FIRE_ASPECT
	}),
	TORRETA_SANADORA(
			"§4§k||§r §e§lTorreta Sanadora §4§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Te curara si estas",
			"§7cerca de ella.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §c§lLegendario",
			"§7§lCura: §71❤ x Rayo",
			"§7§lTipo: §7Sanadora",
			"§7§lTiempo: §7 4 seg",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.FIRE_ASPECT
	}),
	TORRETA_CONGELADORA(
			"§4§k||§r §e§lTorreta Congeladora §4§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Disparara bolas de",
			"§7nieve que congelaran a tus",
			"§7enemigos de una manera",
			"§7rapida!",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §c§lLegendario",
			"§7§lCongela: §7+1 x Disparo",
			"§7§lTipo: §7Congeladora",
			"§7§lTiempo: §7 1 seg",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.FIRE_ASPECT
	}),
	PUNTE_FUTURISTICO(
			"§4§k||§r §e§lPuente Futuristico §4§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Colocalo mirando",
			"§7hacia una dirección X",
			"§7crearas un puente donde",
			"§7solo tu podrás pasar!",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §c§lLegendario",
			"§7§lTipo: §7Construcción",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.DURABILITY
	}),
	BOLA_CONGELACEREBROS(
			"§4§k||§r §e§lBola Congelacerebros §4§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7A congelar cerebros!",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §c§lLegendario",
			"§7§lDaño: §75❤ x Disparo",
			"§7§lOtras: ",
			"    §7Lentitud X ; 5 seg",
			"    §7Fatiga II ; 20 seg",
			"    §7Ceguera I ; 2 seg",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.FIRE_ASPECT
	}),
	RAYO(
			"§4§k||§r §e§lRayo §4§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Lanza el rayo de",
			"§7tus sueños!",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §c§lLegendario",
			"§7§lDaño: §77❤",
			"§7§lOtras: ",
			"    §7Ceguera I ; 5 seg",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.FIRE_ASPECT
	}),
	VARITA_MAGICA(
			"§4§k||§r §e§lVarita Magica §4§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Engaña a tu",
			"§7adversario!",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §c§lLegendario",
			"§7§lGenera: §71 Clon",
			"§7§lTe da: ",
			"   §7Velocidad II ; 5 seg",
			"   §7Invisibilidad I ; 5 seg",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.FIRE_ASPECT
	}),
	KRAKEN(
			"§4§k||§r §e§lKraken §4§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Ha lanzar el",
			"§7kraken!",
			"§7§lOtras: ",
			"    §7Ceguera II ; 15 seg",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.FIRE_ASPECT
	}),
	BOMBARDERO(
			"§4§k||§r §e§lBombardero §4§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7Llamando a base, Lanzando",
			"§7misiles en 3, 2, 1...",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §c§lLegendario",
			"§7§lDaño: §76❤",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.FIRE_ASPECT
	}),
	POCION_PURIFICADORA(
			"§4§k||§r §e§lPoción Purificadora §4§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7Sal de ese mal momento",
			"§7solo tomate esto y dale",
			"§7un giro a la batalla.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §c§lLegendario",
			"§7§lOtras: ",
			"    §7Rellena el nivel de hambre",
			"    §7Rellena el nivel de vida",
			"    §7Remueve todas las pociones",
			"    §7Regeneracion II ; 0.5 seg",
			"    §7Vision Nocturna II ; 0.5 seg",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.FIRE_ASPECT
	}),
	RAYO_CONGELADO(
			"§4§k||§r §e§lRayo Congelado §4§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7Dale un toque de hielo a",
			"§7una zona.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §c§lLegendario",
			"§7§lDaño: §72.5❤",
			"§7§lOtras: ",
			"    §7Ceguera I ; 5 seg",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.FIRE_ASPECT
	}),
	PUÑO_DE_JHONCENA(
			"§4§k||§r §e§lPuño de JhonCena §4§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7Uno de los puños más epicos",
			"§7y mortales de la WWE.",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §c§lLegendario",
			"§7§lDaño: §74❤",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.FIRE_ASPECT
	}),
	METEORITO(
			"§4§k||§r §e§lMeteorito §4§k||", new String[]{
			"",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§9§l» §7Desde el cosmos",
			"§7podrás invocar un enorme",
			"§7meteorito a distancia!",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━",
			"§7§lRareza: §c§lLegendario",
			"§7§lTipo: §7Destructor",
			"§8━━━━━━━━━━━━━━━━━━━━━━━━━"
	}, new Enchantment[]{
			Enchantment.FIRE_ASPECT
	});
	
	private String name;
	private String[] string;
	private Enchantment[] enchantment;

		EItem(String name, String[] string, Enchantment[] enchantment){
			this.name = name;
			this.string = string;
			this.enchantment = enchantment;
			
	}
		
	public String[] getString() {
		return string;
	}

	public void setString(String[] string) {
		this.string = string;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Enchantment[] getEnchantment() {
		return enchantment;
	}

	public void setEnchantment(Enchantment[] enchantment) {
		this.enchantment = enchantment;
	}
	
	
	public static ItemStack ItemBuilder(Material m, EItem lore, int level){
		
		ItemBuilder item = new ItemBuilder(m);
		
		for(int i = 0; i < lore.getString().length; i++){
			item.lore(lore.getString()[i]);
	
		}
		
		for(int k = 0; k < lore.getEnchantment().length; k++){
			item.enchant(lore.getEnchantment()[k], level);
		
		}
		
		return item.material(m).name(lore.getName()).build();
	}
	
}
