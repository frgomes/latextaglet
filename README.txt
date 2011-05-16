What LaTeXtaglet is
===================

LaTeXtaglet allow mathamatical equations in your Javadocs.
These equations are generated as .PNG files and Javadocs are generated
so that these matematical equations show up as you would expect in the
places you determined in your documentation.


Brief History
=============

LatexTaglet was developed by Stephan Dlugosz.
Richard Gomes made small modifications in order to make it independent
of platform.



Requirements
============

Build Requirements: 

1. LaTeXtaglet requires JDK1.5 or higher.
   In particular, it is required a Sun JDK in order to compile, once
   certain classes are made available in tools.jar.

2. Maven2 or Maven3 are required to build and also to generate
   project files needed by Eclipse.
   

Runtime Requirements:

1. LaTeX must be installed in your system.
   In particular, the program "latex" must be available in your PATH.

2. The program "dvipng" must be available in your PATH.



Usage
=====

JQuantLib is an open source project which uses LaTeXtaglet extensively.
In particular, have a look at the pom.xml file in order to learn how
LaTeXtaglet is used in conjunction in order to produce Javadocs which
contain UML diagrams and mathematical formulas.


In your documentation, add tags like "latex(", "latex$" and "latex[" in 
order to embed mathematical formulas. Have a look at JQuantLib sources
in order to see how it works.

latex	inserts equations in a block

latex[	inserts equations in a block

latex$	inserts inline equations


links:
    http://www.jquantlib.org
    http://jquant.svn.sourceforge.net/svnroot/jquant/trunk/jquantlib-parent/pom.xml
    


Building LaTeXtaglet with Maven
===============================

You can build with both Maven2 and Maven3 using the command below:

    mvn clean package
    
Note: at the time of this writing, there's a know bug on Maven3 which
prevents that all profiles of being activated properly. In this case, you
have to force their activation, like this:

    mvn clean package -Pmaven3
    


Building LaTeXtaglet with Eclipse
=================================

You can generate Eclipse project files by typing:

    mvn clean generate-resources -Peclipse
    


Support
=======

We are sorry, but support is very limited.
If you have troubles, you can try to contact project administrators
via SourceForge, anyway.


Thanks

-- Richard Gomes
