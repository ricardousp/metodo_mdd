/*
 * *** IMPORTANT: Machine generated, DO NOT MODIFY ***
 * Generated by:
 * transform ID: org.eclipse.jet.transforms.tagfactory
 * on input: /org.eclipse.jet/plugin.xml
 *
 * The transformation may be found in the JET CVS repository
 */
/**
 *
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *   IBM - Initial API and implementation
 *
 */

package org.eclipse.jet.internal.taglib.format;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jet.taglib.CustomTag;
import org.eclipse.jet.taglib.JET2TagException;
import org.eclipse.jet.taglib.TagInstanceFactory;

/**
 * Tag Factory for formatTags
 */
public class TagFactory implements TagInstanceFactory
{

  private final Map tagOrdinalByName;
  
  /**
   * 
   */
  public TagFactory()
  {
    tagOrdinalByName = new HashMap(15);

    tagOrdinalByName.put("bundle",new Integer(1)); //$NON-NLS-1$
    tagOrdinalByName.put("formatNow",new Integer(2)); //$NON-NLS-1$
    tagOrdinalByName.put("indent",new Integer(3)); //$NON-NLS-1$
    tagOrdinalByName.put("lc",new Integer(4)); //$NON-NLS-1$
    tagOrdinalByName.put("message",new Integer(5)); //$NON-NLS-1$
    tagOrdinalByName.put("milliseconds",new Integer(6)); //$NON-NLS-1$
    tagOrdinalByName.put("param",new Integer(7)); //$NON-NLS-1$
    tagOrdinalByName.put("replaceAll",new Integer(8)); //$NON-NLS-1$
    tagOrdinalByName.put("setBundle",new Integer(9)); //$NON-NLS-1$
    tagOrdinalByName.put("setLocale",new Integer(10)); //$NON-NLS-1$
    tagOrdinalByName.put("strip",new Integer(11)); //$NON-NLS-1$
    tagOrdinalByName.put("uc",new Integer(12)); //$NON-NLS-1$
    tagOrdinalByName.put("unique",new Integer(13)); //$NON-NLS-1$
    tagOrdinalByName.put("uuid",new Integer(14)); //$NON-NLS-1$
    tagOrdinalByName.put("xpath",new Integer(15)); //$NON-NLS-1$
  }

  public CustomTag createCustomTag(String name)
  {
    Integer ordinal = (Integer)tagOrdinalByName.get(name);
    
    switch(ordinal == null ? 0 : ordinal.intValue()) {
      case 1: // bundle
        return new org.eclipse.jet.internal.taglib.format.BundleTag();
      case 2: // formatNow
        return new org.eclipse.jet.internal.taglib.format.FormatNowTag();
      case 3: // indent
        return new org.eclipse.jet.internal.taglib.format.IndentTag();
      case 4: // lc
        return new org.eclipse.jet.internal.taglib.format.LowerCaseTag();
      case 5: // message
        return new org.eclipse.jet.internal.taglib.format.MessageTag();
      case 6: // milliseconds
        return new org.eclipse.jet.internal.taglib.format.MillisecondsTag();
      case 7: // param
        return new org.eclipse.jet.internal.taglib.format.ParamTag();
      case 8: // replaceAll
        return new org.eclipse.jet.internal.taglib.format.ReplaceAllTag();
      case 9: // setBundle
        return new org.eclipse.jet.internal.taglib.format.SetBundleTag();
      case 10: // setLocale
        return new org.eclipse.jet.internal.taglib.format.SetLocaleTag();
      case 11: // strip
        return new org.eclipse.jet.internal.taglib.format.StripTag();
      case 12: // uc
        return new org.eclipse.jet.internal.taglib.format.UpperCaseTag();
      case 13: // unique
        return new org.eclipse.jet.internal.taglib.format.UniqueTag();
      case 14: // uuid
        return new org.eclipse.jet.internal.taglib.format.UuidTag();
      case 15: // xpath
        return new org.eclipse.jet.internal.taglib.format.XPathTag();
      default:
        throw new JET2TagException("Unknown Tag: " + name); //$NON-NLS-1$
    }
  }

}
