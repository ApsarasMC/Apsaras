package org.apsarasmc.plugin.test.config;

import org.apsarasmc.apsaras.config.Comment;

import java.util.ArrayList;
import java.util.List;

public class ConfigBean {
  @Comment ("test Object")
  @Comment ("Just A TEST")
  public ConfigBean.Test test = new ConfigBean.Test();
  @Comment ("Hello, Jenkins!")
  public Just< Integer > integerJust = new Just<>();
  public Just< String > stringJust = new Just<>();

  {
    integerJust.value = 123;
    stringJust.value = "www";
  }

  public enum EnumConfig {
    A1, A2, A3, A4, A5;
  }

  public static class Just< T > {
    public T value;
  }

  public static class Test {
    public long[] longArray = new long[] { 1, 2, 3, 4, 5 };
    public int[] intArray = new int[] { 1, 2, 3, 4, 5 };
    public double[] doubleArray = new double[] { 1, 2, 3, 4, 5 };
    public float[] floatArray = new float[] { 1, 2, 3, 4, 5 };
    public short[] shortArray = new short[] { 1, 2, 3, 4, 5 };
    public byte[] byteArray = new byte[] { 1, 2, 3, 4, 5 };
    public boolean[] booleanArray = new boolean[] { true, false, true, false, true };

    public Long[] longArrayB = new Long[] { 1L, 2L, 3L, 4L, 5L };
    public Integer[] intArrayB = new Integer[] { 1, 2, 3, 4, 5 };
    public Double[] doubleArrayB = new Double[] { 1D, 2D, 3D, 4D, 5D };
    public Float[] floatArrayB = new Float[] { 1F, 2F, 3F, 4F, 5F };
    public Short[] shortArrayB = new Short[] { 1, 2, 3, 4, 5 };
    public Byte[] byteArrayB = new Byte[] { 1, 2, 3, 4, 5 };
    public Boolean[] booleanArrayB = new Boolean[] { true, false, true, false, true };

    public String[] stringArray = new String[] { "1", "2", "3", "4", "5" };
    public EnumConfig[] enumArray = new EnumConfig[] { EnumConfig.A1, EnumConfig.A2, EnumConfig.A3, EnumConfig.A4, EnumConfig.A5 };
    public Just< String >[] justArray;
    public long longA = 1;
    public int intA = 1;
    public double doubleA = 1;
    public float floatA = 1;
    public short shortA = 1;
    public byte byteA = 1;
    public boolean booleanA = true;
    public Long longB = 1L;
    public Integer intB = 1;
    public Double doubleB = 1D;
    public Float floatB = 1F;
    public Short shortB = 1;
    public Byte byteB = 1;
    public Boolean booleanB = true;
    public String string = "1";
    public EnumConfig enumA = EnumConfig.A1;

    {
      List< Just< String > > justList = new ArrayList<>();
      for (int i = 0; i < 5; i++) {
        Just< String > just = new Just<>();
        just.value = String.valueOf(i);
        justList.add(just);
      }
      justArray = justList.toArray(new Just[ 0 ]);
    }
  }
}
