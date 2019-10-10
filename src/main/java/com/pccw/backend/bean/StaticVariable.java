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

    public static final String STATUS_WAITING = "W";
    public static final String STATUS_DONE = "D";


    /**
     * table => BaseLogDtl
     */
    public static final String DTLACTION_ADD = "A";
    public static final String DTLACTION_DEDUCT="D";

    public static final String DTLSUBIN_GOOD = "Good";
    public static final String DTLSUBIN_FAULTY = "Faulty";
    public static final String DTLSUBIN_INTRAN = "Intran";

    public static final String LISSTATUS_WAITING = "W";
    public static final String LISSTATUS_DONE = "D";

    public static final String STATUS_AVAILABLE = "AVL";
    public static final String STATUS_DEMO = "DEM";
    public static final String STATUS_RESERVE = "RES";
    public static final String STATUS_AO_RESERVE = "ARE";
    public static final String STATUS_FAULTY = "FAU";
    public static final String STATUS_INTRANSIT = "INT";



}
