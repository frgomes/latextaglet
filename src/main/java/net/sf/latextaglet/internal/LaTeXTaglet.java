/*
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
*/
package net.sf.latextaglet.internal;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.UUID;

import com.sun.javadoc.Tag;
import com.sun.tools.doclets.internal.toolkit.Configuration;
import com.sun.tools.doclets.internal.toolkit.taglets.Taglet;

/**
 * Abstract taglet class for writing formulae
 * 
 * The LaTeX code can use commands of the (La)TeX math mode with the additional packages
 * <lu>
 * <li>amsfonts</li>
 * <li>latexsym</li>
 * <li>amsmath</li>
 * <li>amsthm</li>
 * <li>amscd</li>
 * <li>amssymb</li>
 * <li>eucal</li>
 * <li>exscale</li>
 * <li>dsfont</li>
 * <li>icomma</li>
 * <li>bm</li>
 * </lu>
 * 
 * <b>Requirements</b>:
 * The following applications must be installed and available in your PATH variable:
 * <lu>
 * <li>latex</li>
 * <li>dvipng</li>
 * </lu>
 *   
 * @author Stephan Dlugosz
 * @author Richard Gomes
 */
public abstract class LaTeXTaglet implements Taglet {
    /**
     * Will return true since <code>@latex</code>
     * can be used in field documentation.
     * @return true since <code>@latex</code>
     * can be used in field documentation and false
     * otherwise.
     */
    @Override
	public boolean inField() {
        return true;
    }
	
    /**
     * Will return true since <code>@latex</code>
     * can be used in constructor documentation.
     * @return true since <code>@latex</code>
     * can be used in constructor documentation and false
     * otherwise.
     */
    @Override
	public boolean inConstructor() {
        return true;
    }
    
    /**
     * Will return true since <code>@latex</code>
     * can be used in method documentation.
     * @return true since <code>@latex</code>
     * can be used in method documentation and false
     * otherwise.
     */
    @Override
	public boolean inMethod() {
        return true;
    }
    
    /**
     * Will return true since <code>@latex</code>
     * can be used in method documentation.
     * @return true since <code>@latex</code>
     * can be used in overview documentation and false
     * otherwise.
     */
    @Override
	public boolean inOverview() {
        return true;
    }

    /**
     * Will return true since <code>@latex</code>
     * can be used in package documentation.
     * @return true since <code>@latex</code>
     * can be used in package documentation and false
     * otherwise.
     */
    @Override
	public boolean inPackage() {
        return true;
    }

    /**
     * Will return true since <code>@latex</code>
     * can be used in type documentation (classes or interfaces).
     * @return true since <code>@latex</code>
     * can be used in type documentation and false
     * otherwise.
     */
    @Override
	public boolean inType() {
        return true;
    }
    
    /**
     * Will return false since <code>@latex</code>
     * is not an inline tag.
     * @return false since <code>@latex</code>
     * is not an inline tag.
     */
    
    @Override
	public boolean isInlineTag() {
        return true;
    }
    
    /**
     * @param tag
     * @param conf
     * @return String that is printed in the corresponding html file
     */
    public abstract String toString(Tag tag, Configuration conf);
    
    /**
     * @param tags
     * @param conf
     * @return String that is printed in the corresponding html file
     */
    public abstract String toString(Tag[] tags, Configuration conf);
    
    /**
     * @param LaTeXCode
     * @param conf 
     * @param ownLine true if Equation is set into its own line
     * @return String name of png file of the formula
     */
    protected String createPicture(final Tag LaTeXCode, final Configuration conf, final boolean ownLine) {
    	// Paket herausfinden
    	String paket = LaTeXCode.holder().position().file().getAbsolutePath().substring(conf.sourcepath.length()+1);
    	paket=paket.substring(0, paket.length()-LaTeXCode.holder().position().file().getName().length());
    	// System.out.println("package: "+paket); //$NON-NLS-1$
    	
    	// Namen festlegen
        final String path = conf.destDirName+paket;
        final String name = LaTeXCode.holder().name()+"."+UUID.randomUUID().toString();        
               
        //Code in Datei schreiben
        try{
            final Writer fw= new FileWriter(path+name+".tex"); //$NON-NLS-1$
        	fw.write("\\documentclass{article}\n"); //$NON-NLS-1$
        	//XXX fw.write("\\usepackage[html,pic-m]{tex4ht}\n"); //$NON-NLS-1$
        	fw.write("\\usepackage{amsfonts,latexsym,amsmath,amsthm,amscd,amssymb,eucal,exscale,dsfont,icomma,bm}\n"); //$NON-NLS-1$
        	fw.write("\\begin{document}\n"); //$NON-NLS-1$
        	if (ownLine) {
        		fw.write("\\begin{equation}\n"); //$NON-NLS-1$
        	} else {
        		fw.write("$"); //$NON-NLS-1$
        	}
        	fw.write(LaTeXCode.text());
        	if (ownLine) {
        		fw.write("\\end{equation}\n"); //$NON-NLS-1$
        	} else {
        		fw.write("$"); //$NON-NLS-1$
        	}
        	fw.write("\\end{document}\n"); //$NON-NLS-1$
            fw.close();
        }catch(final IOException ex) {
            ex.printStackTrace();
        }
        
		execute(path, name);

        return name+".png"; //$NON-NLS-1$
    }
    
    
    //
    // added by Richard Gomes
    //
    
	private final String[] latex  = { "latex" };
	private final String[] dvipng = { "dvipng", "-T", "tight", "-z", "9", "-bg", "transparent", "-x", "1200", "-o" };
	private static final String[] extensions = { ".log", ".dvi", ".aux" };
	
	private void execute(final String path, final String name) {
		execute(path, latex,  name+".tex");
		execute(path, dvipng, new String[] { name+".png", name+".dvi" } );
		for (int i=0; i<extensions.length; i++) {
			final File f = new File(path, name + extensions[i]);
			f.delete();
		}
	}
	
	
	private void execute(final String path, final String[] template, final String arg) {
		execute(path, template, new String[] { arg } );
	}
	
	
	private void execute(final String path, final String[] template, final String[] args) {
		final String[] command = new String[template.length+args.length];
		int count = 0;
		for (int i=0; i<template.length; i++) {
			command[count++] = template[i];
		}
		for (int i=0; i<args.length; i++) {
			command[count++] = args[i];
		}
		
		for (int i=0; i<command.length; i++) {
			System.out.print(command[i]); System.out.print(" ");
		}
		System.out.println();
		System.out.println("----------");
		
		try {
			final ProcessBuilder pb = new ProcessBuilder(command);
			pb.directory(new File(path));
			final Process p = pb.start();
			new StreamHandler(p.getInputStream(), System.out);
			new StreamHandler(p.getErrorStream(), System.err);
			p.waitFor();
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final InterruptedException e) {
			// ignore
		}
	}
    
}