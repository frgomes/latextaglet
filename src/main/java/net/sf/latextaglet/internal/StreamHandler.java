package net.sf.latextaglet.internal;
import java.io.*;

/**
 * Processing of Streams related to a call of Runtime.exec
 * 
 * <pre>
 * Copyright (C) Stephan Dlugosz, 2007. All Rights Reserved.
 *
 * LaTeXTaglet is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * LaTeXTaglet is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser Public License
 * along with LaTeXTaglet; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * </pre>
 * 
 * @author sdlugosz
 *
 */
public class StreamHandler extends Thread
{
    private InputStream is;
    private String type;
    private boolean print;
    
    
    /**
     * @param is Stream
     * @param type Type of Stream
     * @param print Should output be printed?
     */
    StreamHandler(InputStream is, String type, boolean print){
        this.is = is;
        this.type = type;
        this.print = print;
    }
    /**
     * @param is Stream
     * @param type Type of Stream
     */
    StreamHandler(InputStream is, String type){
        this(is,type,true);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Thread#run()
     */
    @SuppressWarnings("unqualified-field-access")
	public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line=null;
            
            while ( (line = br.readLine()) != null) {
            	if (print) System.out.println(type + ">" + line);   //$NON-NLS-1$
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();  
        }
    }
}


