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
package net.sf.latextaglet;
import java.util.Map;

import net.sf.latextaglet.internal.LaTeXTaglet;

import com.sun.javadoc.Doc;
import com.sun.javadoc.Tag;
import com.sun.tools.doclets.Taglet;
import com.sun.tools.doclets.internal.toolkit.Configuration;
import com.sun.tools.doclets.internal.toolkit.taglets.TagletOutput;
import com.sun.tools.doclets.internal.toolkit.taglets.TagletWriter;

/**
 * Taglet class for block equation style formulae<br>
 * 
 * Usage: {&#064;latex[ \sum_i x_i}<br>
 * 
 * This is an example (see below)
 * 
 * @author Stephan Dlugosz
 *
 * @latex[ \sum_i x_i
 */
public class LaTeXBlockEquationTaglet extends LaTeXTaglet {
	/**
     * Name of the tag
     */
    static String NAME="latex["; //$NON-NLS-1$
	
    /**
     * Return the name of this custom tag.
     * @return Name
     */
    @Override
	public String getName() {
        return NAME;
    }
    
    /* (non-Javadoc)
     * @see stephan.LaTeXTaglet#isInlineTag()
     */
    @Override
	public boolean isInlineTag() {
        return false;
    }
    
	/**
     * Register this Taglet.
     * @param tagletMap  the map to register this tag to.
     */
	public static void register(final Map<String, LaTeXBlockEquationTaglet> tagletMap) {
       final LaTeXBlockEquationTaglet tag = new LaTeXBlockEquationTaglet();
       final Taglet t = (Taglet) tagletMap.get(tag.getName());
       if (t != null) {
           tagletMap.remove(tag.getName());
       }
       tagletMap.put(tag.getName(), tag);
    }
	
    @Override
	public String toString(final Tag tag, final Configuration conf) {
        String name = null;
	
    	//erzeuge Datei und gebe Name zur�ck
    	name = createPicture(tag,conf,true);
        
        return "<DL><B>Formula:</B></DL><DD><img src=\""+name+"\" alt=\""+tag.text()+"\" class=\"math-display\"></DD>";  //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$
    }

    @Override
	public String toString(final Tag[] tags, final Configuration conf) {
        if (tags.length == 0) {
            return null;
        }
        return toString(tags[0], conf);
    }
    
    @Override
	public TagletOutput getTagletOutput(final Tag arg0, final TagletWriter arg1) throws IllegalArgumentException {
        final TagletOutput ret = arg1.getOutputInstance();
        ret.setOutput(toString(arg0, arg1.configuration()));
        return ret;
    }

    @Override
	public TagletOutput getTagletOutput(final Doc arg0, final TagletWriter arg1) throws IllegalArgumentException {
        final TagletOutput ret = arg1.getOutputInstance();
        ret.setOutput(toString(arg0.tags(getName()),arg1.configuration()));
        return ret;
    }
}
