// **********************************************************************
//
// Copyright (c) 2003-2016 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.6.3
//
// <auto-generated>
//
// Generated from file `Server.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Demo;

public class Pakete extends Ice.ObjectImpl
{
    public Pakete()
    {
        estado = "";
    }

    public Pakete(int id, String estado, java.util.List<Producto> listaProductos)
    {
        this.id = id;
        this.estado = estado;
        this.listaProductos = listaProductos;
    }

    private static class __F implements Ice.ObjectFactory
    {
        public Ice.Object create(String type)
        {
            assert(type.equals(ice_staticId()));
            return new Pakete();
        }

        public void destroy()
        {
        }
    }
    private static Ice.ObjectFactory _factory = new __F();

    public static Ice.ObjectFactory
    ice_factory()
    {
        return _factory;
    }

    public static final String[] __ids =
    {
        "::Demo::Pakete",
        "::Ice::Object"
    };

    public boolean ice_isA(String s)
    {
        return java.util.Arrays.binarySearch(__ids, s) >= 0;
    }

    public boolean ice_isA(String s, Ice.Current __current)
    {
        return java.util.Arrays.binarySearch(__ids, s) >= 0;
    }

    public String[] ice_ids()
    {
        return __ids;
    }

    public String[] ice_ids(Ice.Current __current)
    {
        return __ids;
    }

    public String ice_id()
    {
        return __ids[0];
    }

    public String ice_id(Ice.Current __current)
    {
        return __ids[0];
    }

    public static String ice_staticId()
    {
        return __ids[0];
    }

    protected void __writeImpl(IceInternal.BasicStream __os)
    {
        __os.startWriteSlice(ice_staticId(), -1, true);
        __os.writeInt(id);
        __os.writeString(estado);
        ListaProductosHelper.write(__os, listaProductos);
        __os.endWriteSlice();
    }

    protected void __readImpl(IceInternal.BasicStream __is)
    {
        __is.startReadSlice();
        id = __is.readInt();
        estado = __is.readString();
        listaProductos = ListaProductosHelper.read(__is);
        __is.endReadSlice();
    }

    public int id;

    public String estado;

    public java.util.List<Producto> listaProductos;

    public Pakete
    clone()
    {
        return (Pakete)super.clone();
    }

    public static final long serialVersionUID = -1277510774L;
}