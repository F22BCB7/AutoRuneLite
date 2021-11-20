package com.github.joonasvali.naturalmouse.support;

import com.github.joonasvali.naturalmouse.api.MouseInfoAccessor;

import java.awt.*;

import org.osrs.util.Data;

public class DefaultMouseInfoAccessor implements MouseInfoAccessor {

  @Override
  public Point getMousePosition() {
	  int x=Data.inputManager.getMouseListener().getX();
	  int y=Data.inputManager.getMouseListener().getY();
	  
    return new Point(x, y);
  }
}
