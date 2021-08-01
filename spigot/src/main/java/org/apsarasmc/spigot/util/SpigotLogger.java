package org.apsarasmc.spigot.util;

import org.slf4j.Marker;
import org.slf4j.helpers.MarkerIgnoringBase;

import java.util.logging.Level;

public class SpigotLogger extends MarkerIgnoringBase {
  // Sorry for it, I won't.
  private final java.util.logging.Logger parent;

  public SpigotLogger(java.util.logging.Logger parent) {
    this.parent = parent;
  }

  @Override
  public String getName() {
    return parent.getName();
  }

  @Override
  public boolean isTraceEnabled() {
    return true;
  }

  @Override
  public void trace(String msg) {
    parent.log(Level.FINEST, msg);
  }

  @Override
  public void trace(String format, Object arg) {
    parent.log(Level.FINEST, format, arg);
  }

  @Override
  public void trace(String format, Object arg1, Object arg2) {
    parent.log(Level.FINEST, format, new Object[] { arg1, arg2 });
  }

  @Override
  public void trace(String format, Object... arguments) {
    parent.log(Level.FINEST, format, arguments);
  }

  @Override
  public void trace(String msg, Throwable t) {
    parent.log(Level.FINEST, msg, t);
  }

  @Override
  public boolean isDebugEnabled() {
    return true;
  }

  @Override
  public void debug(String msg) {
    parent.log(Level.FINE, msg);
  }

  @Override
  public void debug(String format, Object arg) {
    parent.log(Level.FINE, format, arg);
  }

  @Override
  public void debug(String format, Object arg1, Object arg2) {
    parent.log(Level.FINE, format, new Object[] { arg1, arg2 });
  }

  @Override
  public void debug(String format, Object... arguments) {
    parent.log(Level.FINE, format, arguments);
  }

  @Override
  public void debug(String msg, Throwable t) {
    parent.log(Level.FINE, msg, t);
  }

  @Override
  public boolean isInfoEnabled() {
    return true;
  }

  @Override
  public void info(String msg) {
    parent.log(Level.INFO, msg);
  }

  @Override
  public void info(String format, Object arg) {
    parent.log(Level.INFO, format, arg);
  }

  @Override
  public void info(String format, Object arg1, Object arg2) {
    parent.log(Level.INFO, format, new Object[] { arg1, arg2 });
  }

  @Override
  public void info(String format, Object... arguments) {
    parent.log(Level.INFO, format, arguments);
  }

  @Override
  public void info(String msg, Throwable t) {
    parent.log(Level.INFO, msg, t);
  }

  @Override
  public boolean isWarnEnabled() {
    return true;
  }

  @Override
  public void warn(String msg) {
    parent.log(Level.WARNING, msg);
  }

  @Override
  public void warn(String format, Object arg) {
    parent.log(Level.WARNING, format, arg);
  }

  @Override
  public void warn(String format, Object... arguments) {
    parent.log(Level.WARNING, format, arguments);
  }

  @Override
  public void warn(String format, Object arg1, Object arg2) {
    parent.log(Level.WARNING, format, new Object[] { arg1, arg2 });
  }

  @Override
  public void warn(String msg, Throwable t) {
    parent.log(Level.WARNING, msg, t);
  }

  @Override
  public void warn(Marker marker, String msg, Throwable t) {
    parent.log(Level.WARNING, msg, t);
  }

  @Override
  public boolean isErrorEnabled() {
    return true;
  }

  @Override
  public void error(String msg) {
    parent.log(Level.SEVERE, msg);
  }

  @Override
  public void error(String format, Object arg) {
    parent.log(Level.SEVERE, format, arg);
  }

  @Override
  public void error(String format, Object arg1, Object arg2) {
    parent.log(Level.SEVERE, format, new Object[] { arg1, arg2 });
  }

  @Override
  public void error(String format, Object... arguments) {
    parent.log(Level.SEVERE, format, arguments);
  }

  @Override
  public void error(String msg, Throwable t) {
    parent.log(Level.SEVERE, msg, t);
  }
}
