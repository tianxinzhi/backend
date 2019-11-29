package com.pccw.backend.bean;

/**
 * common table field value
 */
public class StaticVariable {

    /**
     * table => BaseLog
     */
    public static final String LOGTYPE_ORDER = "O";
    public static final String LOGTYPE_MANAGEMENT="M";
    public static final String LOGTYPE_REPL="R";

    public static final String LOGORDERNATURE_ASSIGN = "ASG";
    public static final String LOGORDERNATURE_RETURN = "RET";
    public static final String LOGORDERNATURE_EXCHANGE = "EXC";
    public static final String LOGORDERNATURE_ADVANCED_RESERVE = "ARS";
    public static final String LOGORDERNATURE_CANCEL_ADVANCE_RESERVE = "CARS";
    public static final String LOGORDERNATURE_ADVANCE_PICK_UP = "APU";
    public static final String LOGORDERNATURE_REPLENISHMENT_REQUEST = "RREQ";
    public static final String LOGORDERNATURE_REPLENISHMENT_RECEIVEN = "RREC";
    public static final String LOGORDERNATURE_STOCK_TAKE_ADJUSTMENT = "STA";
    public static final String LOGORDERNATURE_STOCK_TRANSFER_OUT = "TRO";
    public static final String LOGORDERNATURE_STOCK_OUT_STS = "SOTS";
    public static final String LOGORDERNATURE_STOCK_OUT_STW = "SOTW";
    public static final String LOGORDERNATURE_STOCK_TRANSFER_IN= "TRI";
    public static final String LOGORDERNATURE_STOCK_IN_STS = "SIFS";
    public static final String LOGORDERNATURE_STOCK_IN_FROM_WAREHOUSE = "SIFW";
    public static final String LOGORDERNATURE_STOCK_IN_WITHOUT_PO_STW = "SIWPO";
    public static final String LOGORDERNATURE_STOCK_RESERVE = "RES";
    public static final String LOGORDERNATURE_STOCK_CANCEL_RESERVE = "CRES";
    public static final String LOGORDERNATURE_TRANSFER_TO_WAREHOUSE = "TRW";
    
    public static final String STATUS_WAITING = "W";
    public static final String STATUS_DONE = "D";


    /**
     * table => BaseLogDtl
     */
    public static final String DTLACTION_ADD = "A";
    public static final String DTLACTION_DEDUCT="D";

    public static final String DTLSUBIN_GOOD = "Good";
    public static final String DTLSUBIN_AVAILABLE = "Available";
    public static final String DTLSUBIN_FAULTY = "Faulty";
    public static final String DTLSUBIN_INTRANSIT = "Intransit";
    public static final String DTLSUBIN_RESERVED = "Reserved";
    public static final String DTLSUBIN_RESERVED_WITH_AO = "Reserved With AO";
    public static final String DTLSUBIN_DEMO = "Demo";

    public static final String LISSTATUS_WAITING = "W";
    public static final String LISSTATUS_DONE = "D";

    public static final String STATUS_AVAILABLE = "AVL";
    public static final String STATUS_FAULTY = "FAU";
    public static final String STATUS_INTRANSIT = "DEL";
    public static final String STATUS_RESERVED = "RES";
    public static final String STATUS_RESERVED_WITH_AO = "RAO";
    public static final String STATUS_DEMO = "DEM";

    public static final String LOGIN_URL = "/system/login";
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
    public static final String ROLE_CLAIMS = "role";
    public static final long EXPIRATION = 60L * 60L;
    public static final String JWT_SECRET_KEY = "C*F-JaNdRgUkXn2r5u8x/A?D(G+KbPeShVmYq3s6v9y$B&E)H@McQfTjWnZr4u7w";


}
