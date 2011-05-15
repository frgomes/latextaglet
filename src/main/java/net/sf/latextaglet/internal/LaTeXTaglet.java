package net.sf.latextaglet.internal;
import com.sun.tools.doclets.internal.toolkit.Configuration;
import com.sun.tools.doclets.internal.toolkit.taglets.Taglet;    // Taglet API
import com.sun.javadoc.*;               // Doclet API


import java.io.*;

/**
 * Abstract taglet class für writing formulae
 * 
 * The LaTeX code can use commands of the (La)TeX math mode with the additional packages
 * 
 * <p>amsfonts,latexsym,amsmath,amsthm,amscd,amssymb,eucal,exscale,dsfont,icomma,bm</p>
 * 
 * Requirements: (La)TeX Installation, ImageMagick, ghostscript<br>
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
 * @author Stephan Dlugosz
 */
public abstract class LaTeXTaglet implements Taglet {
    /**
     * Will return true since <code>@latex</code>
     * can be used in field documentation.
     * @return true since <code>@latex</code>
     * can be used in field documentation and false
     * otherwise.
     */
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
    public boolean inType() {
        return true;
    }
    
    /**
     * Will return false since <code>@latex</code>
     * is not an inline tag.
     * @return false since <code>@latex</code>
     * is not an inline tag.
     */
    
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
    protected String createPicture(Tag LaTeXCode, Configuration conf, boolean ownLine) {
    	// Paket herausfinden
    	String paket = LaTeXCode.holder().position().file().getAbsolutePath().substring(conf.sourcepath.length()+1);
    	paket=paket.substring(0, paket.length()-LaTeXCode.holder().position().file().getName().length());
    	// System.out.println("package: "+paket); //$NON-NLS-1$
    	
    	// Namen festlegen
        String path = conf.destDirName+paket;
        String name = LaTeXCode.holder().name()+LaTeXCode.text().hashCode();        
               
        //Code in Datei schreiben
        try{
            Writer fw= new FileWriter(path+name+".tex"); //$NON-NLS-1$
        	fw.write("\\documentclass{article}\n"); //$NON-NLS-1$
        	fw.write("\\usepackage[html,pic-m]{tex4ht}\n"); //$NON-NLS-1$
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
        }catch(IOException ex) {
            ex.printStackTrace();
        }
        
        //Umwandlung.bat ausführen
    	String batch="cmd /c LaTeXTagletUmwandlung.bat \""+path+"\" "+ name +" "+conf.destDirName.substring(0,2);   //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
            
        System.out.println("call: " + batch); //$NON-NLS-1$
        Runtime rt = Runtime.getRuntime();
        Process p=null;
        StreamHandler errorHandler=null;
        StreamHandler outputHandler=null;
        try{
        	p=rt.exec(batch);
        	
        	errorHandler = new StreamHandler(p.getErrorStream(), "ERROR",false); //$NON-NLS-1$
            outputHandler = new StreamHandler(p.getInputStream(), "OUTPUT",false); //$NON-NLS-1$
        } catch (IOException ex) {
        	ex.printStackTrace();
        }
        //System.out.println("starte Handler");
        errorHandler.start();
        outputHandler.start();
        
        try {
            int exitVal = p.waitFor();
            System.out.println("ExitValue: " + exitVal); //$NON-NLS-1$
        }catch(InterruptedException ex) {
        	ex.printStackTrace();
        }
        
        //System.out.println("...fertig");
        
        return name+".png"; //$NON-NLS-1$
    }
}