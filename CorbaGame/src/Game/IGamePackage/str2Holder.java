package Game.IGamePackage;


/**
* Game/IGamePackage/str2Holder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Game.idl
* 20 ������ 2015 �. 22:37:06 MSK
*/

public final class str2Holder implements org.omg.CORBA.portable.Streamable
{
  public String value[] = null;

  public str2Holder ()
  {
  }

  public str2Holder (String[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = Game.IGamePackage.str2Helper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
        Game.IGamePackage.str2Helper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return Game.IGamePackage.str2Helper.type ();
  }

}
