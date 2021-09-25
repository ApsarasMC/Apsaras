package org.apsarasmc.plugin.util;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class LineWriter extends Writer {
  private final Consumer< String> lineAction;

  public LineWriter(Consumer<String> lineAction){
    this.lineAction = lineAction;
  }
  private StringWriter writer = new StringWriter();
  @Override
  public void write(@Nonnull char[] cbuf, int off, int len) throws IOException {
    writer.write(cbuf,off,len);
    if (writer.toString().contains("\n")) {
      String[] split = writer.toString().split("\n");
      for (int i = 0; i < split.length; i++) {
        if(i == split.length - 1){
          writer = new StringWriter();
          writer.write(split[i]);
        }else {
          lineAction.accept(split[i]);
        }
      }
    }
  }

  @Override
  public void flush() throws IOException {
    String output = writer.toString();
    if (output.length() == 0) {
      return;
    }
    lineAction.accept(output);
    writer = new StringWriter();
  }

  @Override
  public void close() throws IOException {
    //
  }
}