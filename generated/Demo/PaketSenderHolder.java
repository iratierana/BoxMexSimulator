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

public final class PaketSenderHolder extends Ice.ObjectHolderBase<PaketSender>
{
    public
    PaketSenderHolder()
    {
    }

    public
    PaketSenderHolder(PaketSender value)
    {
        this.value = value;
    }

    public void
    patch(Ice.Object v)
    {
        if(v == null || v instanceof PaketSender)
        {
            value = (PaketSender)v;
        }
        else
        {
            IceInternal.Ex.throwUOE(type(), v);
        }
    }

    public String
    type()
    {
        return _PaketSenderDisp.ice_staticId();
    }
}