package com.ss.pojo.ctrip;

import org.simpleframework.xml.Root;

@Root
public enum OrderAction {
    Create,
    Modify,
    Pay,
    Delete,
    Cancel,
}
