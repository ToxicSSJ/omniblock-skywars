/*
 *  Omniblock Developers Team - Copyright (C) 2016
 *
 *  This program is not a free software; you cannot redistribute it and/or modify it.
 *
 *  Only this enabled the editing and writing by the members of the team. 
 *  No third party is allowed to modification of the code.
 *
 */

package net.omniblock.skywars.patch.internal;

import java.util.ArrayList;
import java.util.List;

public class SkywarsResolver {

	private String serial = "Unknow";
	private String[] args = new String[] { "unranked" };

	public SkywarsResolver(String serial, String[] args) {
		this.serial = serial;
		this.args = args;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public List<String> getListArgs() {
		List<String> r = new ArrayList<String>();
		for (String s : getArgs()) {
			r.add(s);
		}
		return r;
	}

}
