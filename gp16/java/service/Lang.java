/*
 * @Lang.java 1.0 28/04/2020
 *
 * Copyright (c) 2020 Aberystwyth University
 * All rights reserved
 */

package uk.ac.aber.cs221.gp16.java.service;

/**
 * Possible languages
 * <p>
 * EN = English
 * CY = Welsh
 *
 * @sas90
 * @version 1.0
 */
public enum Lang {
   EN, CY;

   @Override
   public String toString() {
      if (this == EN) {
         return "English";
      }
      return "Welsh";
   }
}
