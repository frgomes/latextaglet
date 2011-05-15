/*
 * Copyright (C) Stephan Dlugosz, 2007. All Rights Reserved.
 * Copyright (C) Richard Gomes, 2011. All Rights Reserved.
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
*/
package net.sf.latextaglet.internal;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * Processing of Streams related to a call of Runtime.exec
 * 
 * @author sdlugosz
 * @author Richard Gomes
 */
public class StreamHandler extends Thread {
    private final InputStream is;
    private final PrintStream ps;
    
    /**
     * @param is Stream
     * @param type Type of Stream
     * @param print Should output be printed?
     */
    StreamHandler(final InputStream is, final PrintStream ps) {
        this.is = is;
        this.ps = ps;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Thread#run()
     */
    @Override
	public void run() {
        try {
            final InputStreamReader isr = new InputStreamReader(is);
            final BufferedReader br = new BufferedReader(isr);
            String line=null;
            
            while ( (line = br.readLine()) != null) {
            	ps.println(line);
            }
        } catch (final IOException ioe) {
            ioe.printStackTrace();  
        }
    }
}
