/*
 * Copyright 2016 HideoutMC // Creatiums LLC. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE AUTHOR ''AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and contributors and should not be interpreted as representing official policies,
 *  either expressed or implied, of anybody else.
 */

package net.omniblock.skywars.util;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.map.MinecraftFont;

public class TextUtil {
	
	private final static int CENTER_PX = 154;
	 
	public static String format(String s){
	    if(s == null) return null;
    	return s.replaceAll("&", "§");
    }

	public static String color(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public static String removeChatColor(String text){
		return ChatColor.stripColor(text);
	}

    public static String centerText(String text) {
    	return centerText(text, 65);
    }
    
    public static String centerText(String text, int size) {
    	int i = text.length();
		int j = size - i;
		if (j <= 0) {
			return text;
		}
    	return StringUtils.leftPad(text, i + j / 2, " ");
    }
    
    public static String getCenteredMessage(String message){
            if(message == null || message.equals(""))
                    message = format(message);
                   
                    int messagePxSize = 0;
                    boolean previousCode = false;
                    boolean isBold = false;
                   
                    for(char c : message.toCharArray()){
                            if(c == '§'){
                                    previousCode = true;
                                    continue;
                            }else if(previousCode == true){
                                    previousCode = false;
                                    if(c == 'l' || c == 'L'){
                                            isBold = true;
                                            continue;
                                    }else isBold = false;
                            }else{
                                    DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                                    messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                                    messagePxSize++;
                            }
                    }
                   
                    int halvedMessageSize = messagePxSize / 2;
                    int toCompensate = CENTER_PX - halvedMessageSize;
                    int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
                    int compensated = 0;
                    StringBuilder sb = new StringBuilder();
                    while(compensated < toCompensate){
                            sb.append(" ");
                            compensated += spaceLength;
                    }
                    return TextUtil.format(sb.toString() + message);
    }
    
    public static String replaceAllTextWith(String text, char replace) {
		char chArray[] = text.toCharArray();
		for(int i = 0; i < chArray.length; i++){
			chArray[i] = replace;
		}
		
		return String.valueOf(chArray);
	}
    
	public static String secToMin(int i)
	{
	 int ms = i / 60;
	 int ss = i % 60;
	 String m = (ms < 10 ? "0" : "") + ms;
	 String s = (ss < 10 ? "0" : "") + ss;
	 String f = m + ":" + s;
	 return f;
	}

	public static String centerAndFormat(String a) {
		
		String formattedString = TextUtil.format(a);
		
		int spaceWidth = MinecraftFont.Font.getWidth(" ");
		int width = MinecraftFont.Font.getWidth(TextUtil.removeChatColor(formattedString));
		int chatWidth = 180;
		
		if(width < chatWidth){
			int diff = chatWidth - width;
			int leftSpace = diff / 2;
			int spaces = (leftSpace / spaceWidth);
			
			String space = "";
			
			for(int i = 0; i < spaces; i++){
				space += " ";
			}
			
			formattedString = space + formattedString;
		}
		
		return formattedString;
	}
}