package com.mancosyt.rftb.manager;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

public class PortalManager{	
	
	public static void createPortal(Location center) {		
		Block a = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX(), center.getBlockY(), center.getBlockZ());
		
		Block b = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() + 1, center.getBlockY(), center.getBlockZ());
		
		Block c = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() - 1, center.getBlockY(), center.getBlockZ());
		
		Block d = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX(), center.getBlockY(), center.getBlockZ() - 1);
		
		Block e = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX(), center.getBlockY(), center.getBlockZ() + 1);
		
		Block f = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() - 1, center.getBlockY(), center.getBlockZ() + 1);
		
		Block g = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() - 1, center.getBlockY(), center.getBlockZ() - 1);
		
		Block h = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() + 1, center.getBlockY(), center.getBlockZ() + 1);
		
		Block i = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() + 1, center.getBlockY(), center.getBlockZ() - 1);
		
		Block end = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX(), center.getBlockY(), center.getBlockZ() - 2);
		
		Block enda = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX(), center.getBlockY(), center.getBlockZ() + 2);
		
		Block endb = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() + 2, center.getBlockY(), center.getBlockZ());
		
		Block endz = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() - 2, center.getBlockY(), center.getBlockZ());
						
		Block endc = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() - 2, center.getBlockY(), center.getBlockZ() - 1);
		
		Block ende = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() + 1, center.getBlockY(), center.getBlockZ() + 2);
		
		Block endf = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() + 1, center.getBlockY(), center.getBlockZ() - 2);
		
		Block endg = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() - 1, center.getBlockY(), center.getBlockZ() + 2);
		
		Block endh = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() - 2, center.getBlockY(), center.getBlockZ() + 1);
		
		Block endi = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() - 1, center.getBlockY(), center.getBlockZ() - 2);
		
		Block endj = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() + 2, center.getBlockY(), center.getBlockZ() + 1);
			
		Block endk = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() + 2, center.getBlockY(), center.getBlockZ() - 1);
		
		BlockState a1 = a.getState();
		a1.setType(Material.ENDER_PORTAL);
		a1.update(true);
		
		BlockState b1 = b.getState();
		b1.setType(Material.ENDER_PORTAL);
		b1.update(true);
		
		BlockState c1 = c.getState();
		c1.setType(Material.ENDER_PORTAL);
		c1.update(true);
		
		BlockState d1 = d.getState();
		d1.setType(Material.ENDER_PORTAL);
		d1.update(true);
		
		BlockState e1 = e.getState();
		e1.setType(Material.ENDER_PORTAL);
		e1.update(true);
		
		BlockState f1 = f.getState();
		f1.setType(Material.ENDER_PORTAL);
		f1.update(true);
		
		BlockState g1 = g.getState();
		g1.setType(Material.ENDER_PORTAL);
		g1.update(true);
		
		BlockState h1 = h.getState();
		h1.setType(Material.ENDER_PORTAL);
		h1.update(true);
		
		BlockState i1 = i.getState();
		i1.setType(Material.ENDER_PORTAL);
		i1.update(true);
				
		BlockState end1 = enda.getState();
		end1.setType(Material.ENDER_PORTAL_FRAME);
		end1.update(true);
		
		BlockState end2 = endb.getState();
		end2.setType(Material.ENDER_PORTAL_FRAME);
		end2.update(true);
		
		BlockState end3 = endc.getState();
		end3.setType(Material.ENDER_PORTAL_FRAME);
		end3.update(true);
		
		BlockState end5 = ende.getState();
		end5.setType(Material.ENDER_PORTAL_FRAME);
		end5.update(true);
		
		BlockState end6 = endf.getState();
		end6.setType(Material.ENDER_PORTAL_FRAME);
		end6.update(true);
		
		BlockState end7 = endg.getState();
		end7.setType(Material.ENDER_PORTAL_FRAME);
		end7.update(true);
		
		BlockState end8 = endh.getState();
		end8.setType(Material.ENDER_PORTAL_FRAME);
		end8.update(true);
		
		BlockState end9 = endi.getState();
		end9.setType(Material.ENDER_PORTAL_FRAME);
		end9.update(true);
		
		BlockState end10 = endj.getState();
		end10.setType(Material.ENDER_PORTAL_FRAME);
		end10.update(true);
		
		BlockState end11 = endk.getState();
		end11.setType(Material.ENDER_PORTAL_FRAME);
		end11.update(true);
		
		BlockState end12 = end.getState();
		end12.setType(Material.ENDER_PORTAL_FRAME);
		end12.update(true);
		
		BlockState end123 = endz.getState();
		end123.setType(Material.ENDER_PORTAL_FRAME);
		end123.update(true);	
	}
	
	public static void removePortal(Location center) {
		Block a = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX(), center.getBlockY(), center.getBlockZ());
		
		Block b = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() + 1, center.getBlockY(), center.getBlockZ());
		
		Block c = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() - 1, center.getBlockY(), center.getBlockZ());
		
		Block d = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX(), center.getBlockY(), center.getBlockZ() - 1);
		
		Block e = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX(), center.getBlockY(), center.getBlockZ() + 1);
		
		Block f = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() - 1, center.getBlockY(), center.getBlockZ() + 1);
		
		Block g = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() - 1, center.getBlockY(), center.getBlockZ() - 1);
		
		Block h = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() + 1, center.getBlockY(), center.getBlockZ() + 1);
		
		Block i = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() + 1, center.getBlockY(), center.getBlockZ() - 1);
		
		Block end = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX(), center.getBlockY(), center.getBlockZ() - 2);
		
		Block enda = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX(), center.getBlockY(), center.getBlockZ() + 2);
		
		Block endb = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() + 2, center.getBlockY(), center.getBlockZ());
		
		Block endz = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() - 2, center.getBlockY(), center.getBlockZ());
						
		Block endc = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() - 2, center.getBlockY(), center.getBlockZ() - 1);
		
		Block ende = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() + 1, center.getBlockY(), center.getBlockZ() + 2);
		
		Block endf = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() + 1, center.getBlockY(), center.getBlockZ() - 2);
		
		Block endg = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() - 1, center.getBlockY(), center.getBlockZ() + 2);
		
		Block endh = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() - 2, center.getBlockY(), center.getBlockZ() + 1);
		
		Block endi = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() - 1, center.getBlockY(), center.getBlockZ() - 2);
		
		Block endj = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() + 2, center.getBlockY(), center.getBlockZ() + 1);
				
		Block endk = Bukkit.getWorld(center.getWorld().getName()).getBlockAt(center.getBlockX() + 2, center.getBlockY(), center.getBlockZ() - 1);
		
		BlockState a1 = a.getState();
		a1.setType(Material.AIR);
		a1.update(true);
		
		BlockState b1 = b.getState();
		b1.setType(Material.AIR);
		b1.update(true);
		
		BlockState c1 = c.getState();
		c1.setType(Material.AIR);
		c1.update(true);
		
		BlockState d1 = d.getState();
		d1.setType(Material.AIR);
		d1.update(true);
		
		BlockState e1 = e.getState();
		e1.setType(Material.AIR);
		e1.update(true);
		
		BlockState f1 = f.getState();
		f1.setType(Material.AIR);
		f1.update(true);
		
		BlockState g1 = g.getState();
		g1.setType(Material.AIR);
		g1.update(true);
		
		BlockState h1 = h.getState();
		h1.setType(Material.AIR);
		h1.update(true);
		
		BlockState i1 = i.getState();
		i1.setType(Material.AIR);
		i1.update(true);
				
		BlockState end1 = enda.getState();
		end1.setType(Material.AIR);
		end1.update(true);
		
		BlockState end2 = endb.getState();
		end2.setType(Material.AIR);
		end2.update(true);
		
		BlockState end3 = endc.getState();
		end3.setType(Material.AIR);
		end3.update(true);
		
		BlockState end5 = ende.getState();
		end5.setType(Material.AIR);
		end5.update(true);
		
		BlockState end6 = endf.getState();
		end6.setType(Material.AIR);
		end6.update(true);
		
		BlockState end7 = endg.getState();
		end7.setType(Material.AIR);
		end7.update(true);
		
		BlockState end8 = endh.getState();
		end8.setType(Material.AIR);
		end8.update(true);
		
		BlockState end9 = endi.getState();
		end9.setType(Material.AIR);
		end9.update(true);
		
		BlockState end10 = endj.getState();
		end10.setType(Material.AIR);
		end10.update(true);
		
		BlockState end11 = endk.getState();
		end11.setType(Material.AIR);
		end11.update(true);
		
		BlockState end12 = end.getState();
		end12.setType(Material.AIR);
		end12.update(true);
		
		BlockState end123 = endz.getState();
		end123.setType(Material.AIR);
		end123.update(true);
	}
}
