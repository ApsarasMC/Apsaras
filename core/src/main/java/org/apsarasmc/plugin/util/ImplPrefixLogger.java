package org.apsarasmc.plugin.util;

import org.slf4j.Logger;
import org.slf4j.helpers.MarkerIgnoringBase;

public class ImplPrefixLogger extends MarkerIgnoringBase {
    private final Logger parent;
    private final String prefix;

    public ImplPrefixLogger(Logger parent, String prefix) {
        this.parent = parent;
        this.prefix = prefix;
    }

    @Override
    public String getName() {
        return parent.getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return parent.isTraceEnabled();
    }

    @Override
    public void trace(String msg) {
        parent.trace(prefix + msg);
    }

    @Override
    public void trace(String format, Object arg) {
        parent.trace(prefix + format, arg);
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        parent.trace(prefix + format, arg1, arg2);
    }

    @Override
    public void trace(String format, Object... arguments) {
        parent.trace(prefix + format, arguments);
    }

    @Override
    public void trace(String msg, Throwable t) {
        parent.trace(prefix + msg, t);
    }

    @Override
    public boolean isDebugEnabled() {
        return parent.isDebugEnabled();
    }

    @Override
    public void debug(String msg) {
        parent.debug(prefix + msg);
    }

    @Override
    public void debug(String format, Object arg) {
        parent.debug(prefix + format, arg);
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        parent.debug(prefix + format, arg1, arg2);
    }

    @Override
    public void debug(String format, Object... arguments) {
        parent.debug(prefix + format, arguments);
    }

    @Override
    public void debug(String msg, Throwable t) {
        parent.debug(prefix + msg, t);
    }

    @Override
    public boolean isInfoEnabled() {
        return parent.isInfoEnabled();
    }

    @Override
    public void info(String msg) {
        parent.info(prefix + msg);
    }

    @Override
    public void info(String format, Object arg) {
        parent.info(prefix + format, arg);
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        parent.info(prefix + format, arg1, arg2);
    }

    @Override
    public void info(String format, Object... arguments) {
        parent.info(prefix + format, arguments);
    }

    @Override
    public void info(String msg, Throwable t) {
        parent.info(prefix + msg, t);
    }

    @Override
    public boolean isWarnEnabled() {
        return parent.isWarnEnabled();
    }

    @Override
    public void warn(String msg) {
        parent.warn(prefix + msg);
    }

    @Override
    public void warn(String format, Object arg) {
        parent.warn(prefix + format, arg);
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        parent.warn(prefix + format, arg1, arg2);
    }

    @Override
    public void warn(String format, Object... arguments) {
        parent.warn(prefix + format, arguments);
    }

    @Override
    public void warn(String msg, Throwable t) {
        parent.warn(prefix + msg, t);
    }

    @Override
    public boolean isErrorEnabled() {
        return parent.isErrorEnabled();
    }

    @Override
    public void error(String msg) {
        parent.error(prefix + msg);
    }

    @Override
    public void error(String format, Object arg) {
        parent.error(prefix + format, arg);
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        parent.error(prefix + format, arg1, arg2);
    }

    @Override
    public void error(String format, Object... arguments) {
        parent.error(prefix + format, arguments);
    }

    @Override
    public void error(String msg, Throwable t) {
        parent.error(prefix + msg, t);
    }
}
