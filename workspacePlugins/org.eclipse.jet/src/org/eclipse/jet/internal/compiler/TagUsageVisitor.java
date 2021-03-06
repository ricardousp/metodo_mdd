/**
 * <copyright>
 *
 * Copyright (c) 2007, 2010 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *   IBM - Initial API and implementation
 *
 * </copyright>
 *
 * $Id: TagUsageVisitor.java,v 1.2 2011/01/27 14:56:41 pelder Exp $
 */
package org.eclipse.jet.internal.compiler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jet.JET2Writer;
import org.eclipse.jet.core.parser.ast.JETASTVisitor;
import org.eclipse.jet.core.parser.ast.JETCompilationUnit;
import org.eclipse.jet.core.parser.ast.XMLBodyElement;
import org.eclipse.jet.core.parser.ast.XMLElement;
import org.eclipse.jet.core.parser.ast.XMLEmptyElement;
import org.eclipse.jet.taglib.TagLibraryReference;

/**
 * JET AST visitor that computes tag usage.
 */
public class TagUsageVisitor extends JETASTVisitor
{
  private boolean hasTags = false;
  
  private boolean hasAttributelessTags = false;
  
  private Set nsPrefixesUsed = new HashSet();
  
  private Map tagOrdinals = new IdentityHashMap();
  
  JET2Writer out;
  
  public TagUsageVisitor(JET2Writer out) {
    this.out = out;
  }
 
  private void handleTagElement(XMLElement xmlTagElement) {
    nsPrefixesUsed.add(xmlTagElement.getNSPrefix());
    hasTags = true; 
    if(!tagOrdinals.containsKey(xmlTagElement)) {
      tagOrdinals.put(xmlTagElement, new Integer(tagOrdinals.size()));
    }
    if(xmlTagElement.getAttributes().size() == 0) {
      hasAttributelessTags = true;
    }
  }
  
  public boolean hasAttributelessTags() {
    return hasAttributelessTags;
  }
  
  public boolean visit(XMLBodyElement xmlBodyElement)
  {
    handleTagElement(xmlBodyElement);
    return true;
  }
  
  public boolean visit(XMLEmptyElement xmlEmptyElement)
  {
    handleTagElement(xmlEmptyElement);
    return true;
  }

  /**
   * Test if the visited ast include JET tags
   * @return <code>true</code> if the compilation unit had tags
   */
  public boolean hasTags() {
    return hasTags;
  }
  
  /**
   * Return the tag library references used by the the visited
   * JET tags 
   * @param cu the compilation unit containing the visited elements
   * @return a possibily empty array of tag library references
   */
  public TagLibraryReference[] getUsedTagLibraryReferences(JETCompilationUnit cu) {
    final TagLibraryReference[] tlrefs = cu.getTagLibraryReferences();
    List usedRefs = new ArrayList();
    for (int i = 0; i < tlrefs.length; i++)
    {
      if(nsPrefixesUsed.contains(tlrefs[i].getPrefix())) {
        usedRefs.add(tlrefs[i]);
      }
    }
    return (TagLibraryReference[])usedRefs.toArray(new TagLibraryReference[usedRefs.size()]);
  }
  
  public int ordinal(XMLElement xmlTagElement) {
    Integer result = (Integer)tagOrdinals.get(xmlTagElement);
    if(result == null) {
      throw new IllegalStateException("Tag ordinals have not yet been computed"); //$NON-NLS-1$
    }
    return result.intValue();
  }
  
  public String ordinalExpr(XMLElement xmlTagElement) {
    return "[" + ordinal(xmlTagElement) + "]";  //$NON-NLS-1$//$NON-NLS-2$
  }
}
