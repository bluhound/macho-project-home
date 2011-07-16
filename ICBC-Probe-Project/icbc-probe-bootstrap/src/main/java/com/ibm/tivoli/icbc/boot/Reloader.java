package com.ibm.tivoli.icbc.boot;

/**
 * Internal interface that <code>ClassLoader</code> implementations may
 * optionally implement to support the auto-reload functionality of
 * the classloader associated with the context.
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.4 $ $Date: 2005/01/26 00:31:57 $
 */

public interface Reloader {


    /**
     * Add a new repository to the set of places this ClassLoader can look for
     * classes to be loaded.
     *
     * @param repository Name of a source of classes to be loaded, such as a
     *  directory pathname, a JAR file pathname, or a ZIP file pathname
     *
     * @exception IllegalArgumentException if the specified repository is
     *  invalid or does not exist
     */
    public void addRepository(String repository);


    /**
     * Return a String array of the current repositories for this class
     * loader.  If there are no repositories, a zero-length array is
     * returned.
     */
    public String[] findRepositories();


    /**
     * Have one or more classes or resources been modified so that a reload
     * is appropriate?
     */
    public boolean modified();


}