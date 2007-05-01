package net.sf.l2j.gameserver.characters.model;

// Generated 11 d�c. 2006 17:28:16 by Hibernate Tools 3.2.0.beta8

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import net.sf.l2j.gameserver.characters.model.recommendation.CharRecommendation;

/**
 * Characters generated by hbm2java
 * 
 * @deprecated not used for the moment, this class is only used for test purpose. 
 */
public class Characters implements java.io.Serializable
{

    // Fields    

    private static final long serialVersionUID = -7162570712501836925L;
    
    private int objId;
    private String accountName;
    private String charName;
    private Long level;
    private Long maxHp;
    private Long curHp;
    private Long maxCp;
    private Long curCp;
    private Long maxMp;
    private Long curMp;
    private Long acc;
    private Long crit;
    private Long evasion;
    private Long matk;
    private Long mdef;
    private Long mspd;
    private Long patk;
    private Long pdef;
    private Long pspd;
    private Long runSpd;
    private Long walkSpd;
    private Long str;
    private Long con;
    private Long dex;
    private Long int_;
    private Long men;
    private Long wit;
    private Long face;
    private Long hairStyle;
    private Long hairColor;
    private Long sex;
    private Long heading;
    private Long x;
    private Long y;
    private Long z;
    private BigDecimal movementMultiplier;
    private BigDecimal attackSpeedMultiplier;
    private BigDecimal colRad;
    private BigDecimal colHeight;
    private BigDecimal exp;
    private Long sp;
    private Long karma;
    private Long pvpkills;
    private Long pkkills;
    private Long clanid;
    private Long maxload;
    private Long race;
    private Long classid;
    private int baseClass;
    private BigDecimal deletetime;
    private Long cancraft;
    private String title;
    private Long allyId;
    private int recHave;
    private int recLeft;
    private Short accesslevel;
    private Boolean online;
    private Boolean charSlot;
    private Boolean newbie;
    private BigDecimal lastAccess;
    private Integer clanPrivs;
    private Boolean wantspeace;
    private BigDecimal deleteclan;
    private boolean isin7sdungeon;
    private BigDecimal onlinetime;
    private Boolean inJail;
    private BigDecimal jailTimer;
    private Byte nobless;
    private Integer varka;
    private Integer ketra;
    private int equipedWithZariche;
    private long zarichePk;
    private BigDecimal zaricheTime;
    private int pledgeClass;
    private int pledgeType;
    private int pledgeRank;
    private String apprentice;
    private int accademyLvl;
    private Set<CharRecommendation> characterRecommendations = new HashSet<CharRecommendation>(0);

    // Constructors

    /** default constructor */
    public Characters()
    {
    }

    /** minimal constructor */
    public Characters(int objId, String charName, int baseClass, int recHave, int recLeft,
                      boolean isin7sdungeon, int equipedWithZariche, long zarichePk,
                      BigDecimal zaricheTime, int pledgeClass, int pledgeType, int pledgeRank,
                      String apprentice, int accademyLvl)
    {
        this.objId = objId;
        this.charName = charName;
        this.baseClass = baseClass;
        this.recHave = recHave;
        this.recLeft = recLeft;
        this.isin7sdungeon = isin7sdungeon;
        this.equipedWithZariche = equipedWithZariche;
        this.zarichePk = zarichePk;
        this.zaricheTime = zaricheTime;
        this.pledgeClass = pledgeClass;
        this.pledgeType = pledgeType;
        this.pledgeRank = pledgeRank;
        this.apprentice = apprentice;
        this.accademyLvl = accademyLvl;
    }

    /** full constructor */
    public Characters(int objId, String charName, Long level, Long maxHp, Long curHp,
                      Long maxCp, Long curCp, Long maxMp, Long curMp, Long acc, Long crit, Long evasion,
                      Long matk, Long mdef, Long mspd, Long patk, Long pdef, Long pspd, Long runSpd,
                      Long walkSpd, Long str, Long con, Long dex, Long int_, Long men, Long wit,
                      Long face, Long hairStyle, Long hairColor, Long sex, Long heading, Long x, Long y,
                      Long z, BigDecimal movementMultiplier, BigDecimal attackSpeedMultiplier,
                      BigDecimal colRad, BigDecimal colHeight, BigDecimal exp, Long sp, Long karma,
                      Long pvpkills, Long pkkills, Long clanid, Long maxload, Long race, Long classid,
                      int baseClass, BigDecimal deletetime, Long cancraft, String title, Long allyId,
                      int recHave, int recLeft, Short accesslevel, Boolean online, Boolean charSlot,
                      Boolean newbie, BigDecimal lastAccess, Integer clanPrivs, Boolean wantspeace,
                      BigDecimal deleteclan, boolean isin7sdungeon, BigDecimal onlinetime,
                      Boolean inJail, BigDecimal jailTimer, Byte nobless, Integer varka, Integer ketra,
                      int equipedWithZariche, long zarichePk, BigDecimal zaricheTime, int pledgeClass,
                      int pledgeType, int pledgeRank, String apprentice, int accademyLvl,
                      Set<CharRecommendation> characterRecommendations)
    {
        this.objId = objId;
        this.charName = charName;
        this.level = level;
        this.maxHp = maxHp;
        this.curHp = curHp;
        this.maxCp = maxCp;
        this.curCp = curCp;
        this.maxMp = maxMp;
        this.curMp = curMp;
        this.acc = acc;
        this.crit = crit;
        this.evasion = evasion;
        this.matk = matk;
        this.mdef = mdef;
        this.mspd = mspd;
        this.patk = patk;
        this.pdef = pdef;
        this.pspd = pspd;
        this.runSpd = runSpd;
        this.walkSpd = walkSpd;
        this.str = str;
        this.con = con;
        this.dex = dex;
        this.int_ = int_;
        this.men = men;
        this.wit = wit;
        this.face = face;
        this.hairStyle = hairStyle;
        this.hairColor = hairColor;
        this.sex = sex;
        this.heading = heading;
        this.x = x;
        this.y = y;
        this.z = z;
        this.movementMultiplier = movementMultiplier;
        this.attackSpeedMultiplier = attackSpeedMultiplier;
        this.colRad = colRad;
        this.colHeight = colHeight;
        this.exp = exp;
        this.sp = sp;
        this.karma = karma;
        this.pvpkills = pvpkills;
        this.pkkills = pkkills;
        this.clanid = clanid;
        this.maxload = maxload;
        this.race = race;
        this.classid = classid;
        this.baseClass = baseClass;
        this.deletetime = deletetime;
        this.cancraft = cancraft;
        this.title = title;
        this.allyId = allyId;
        this.recHave = recHave;
        this.recLeft = recLeft;
        this.accesslevel = accesslevel;
        this.online = online;
        this.charSlot = charSlot;
        this.newbie = newbie;
        this.lastAccess = lastAccess;
        this.clanPrivs = clanPrivs;
        this.wantspeace = wantspeace;
        this.deleteclan = deleteclan;
        this.isin7sdungeon = isin7sdungeon;
        this.onlinetime = onlinetime;
        this.inJail = inJail;
        this.jailTimer = jailTimer;
        this.nobless = nobless;
        this.varka = varka;
        this.ketra = ketra;
        this.equipedWithZariche = equipedWithZariche;
        this.zarichePk = zarichePk;
        this.zaricheTime = zaricheTime;
        this.pledgeClass = pledgeClass;
        this.pledgeType = pledgeType;
        this.pledgeRank = pledgeRank;
        this.apprentice = apprentice;
        this.accademyLvl = accademyLvl;
        this.characterRecommendations = characterRecommendations;
    }

    // Property accessors
    public int getObjId()
    {
        return this.objId;
    }

    public void setObjId(int objId)
    {
        this.objId = objId;
    }

    public String getCharName()
    {
        return this.charName;
    }

    public void setCharName(String charName)
    {
        this.charName = charName;
    }

    public Long getLevel()
    {
        return this.level;
    }

    public void setLevel(Long level)
    {
        this.level = level;
    }

    public Long getMaxHp()
    {
        return this.maxHp;
    }

    public void setMaxHp(Long maxHp)
    {
        this.maxHp = maxHp;
    }

    public Long getCurHp()
    {
        return this.curHp;
    }

    public void setCurHp(Long curHp)
    {
        this.curHp = curHp;
    }

    public Long getMaxCp()
    {
        return this.maxCp;
    }

    public void setMaxCp(Long maxCp)
    {
        this.maxCp = maxCp;
    }

    public Long getCurCp()
    {
        return this.curCp;
    }

    public void setCurCp(Long curCp)
    {
        this.curCp = curCp;
    }

    public Long getMaxMp()
    {
        return this.maxMp;
    }

    public void setMaxMp(Long maxMp)
    {
        this.maxMp = maxMp;
    }

    public Long getCurMp()
    {
        return this.curMp;
    }

    public void setCurMp(Long curMp)
    {
        this.curMp = curMp;
    }

    public Long getAcc()
    {
        return this.acc;
    }

    public void setAcc(Long acc)
    {
        this.acc = acc;
    }

    public Long getCrit()
    {
        return this.crit;
    }

    public void setCrit(Long crit)
    {
        this.crit = crit;
    }

    public Long getEvasion()
    {
        return this.evasion;
    }

    public void setEvasion(Long evasion)
    {
        this.evasion = evasion;
    }

    public Long getMatk()
    {
        return this.matk;
    }

    public void setMatk(Long matk)
    {
        this.matk = matk;
    }

    public Long getMdef()
    {
        return this.mdef;
    }

    public void setMdef(Long mdef)
    {
        this.mdef = mdef;
    }

    public Long getMspd()
    {
        return this.mspd;
    }

    public void setMspd(Long mspd)
    {
        this.mspd = mspd;
    }

    public Long getPatk()
    {
        return this.patk;
    }

    public void setPatk(Long patk)
    {
        this.patk = patk;
    }

    public Long getPdef()
    {
        return this.pdef;
    }

    public void setPdef(Long pdef)
    {
        this.pdef = pdef;
    }

    public Long getPspd()
    {
        return this.pspd;
    }

    public void setPspd(Long pspd)
    {
        this.pspd = pspd;
    }

    public Long getRunSpd()
    {
        return this.runSpd;
    }

    public void setRunSpd(Long runSpd)
    {
        this.runSpd = runSpd;
    }

    public Long getWalkSpd()
    {
        return this.walkSpd;
    }

    public void setWalkSpd(Long walkSpd)
    {
        this.walkSpd = walkSpd;
    }

    public Long getStr()
    {
        return this.str;
    }

    public void setStr(Long str)
    {
        this.str = str;
    }

    public Long getCon()
    {
        return this.con;
    }

    public void setCon(Long con)
    {
        this.con = con;
    }

    public Long getDex()
    {
        return this.dex;
    }

    public void setDex(Long dex)
    {
        this.dex = dex;
    }

    public Long getInt_()
    {
        return this.int_;
    }

    public void setInt_(Long int_)
    {
        this.int_ = int_;
    }

    public Long getMen()
    {
        return this.men;
    }

    public void setMen(Long men)
    {
        this.men = men;
    }

    public Long getWit()
    {
        return this.wit;
    }

    public void setWit(Long wit)
    {
        this.wit = wit;
    }

    public Long getFace()
    {
        return this.face;
    }

    public void setFace(Long face)
    {
        this.face = face;
    }

    public Long getHairStyle()
    {
        return this.hairStyle;
    }

    public void setHairStyle(Long hairStyle)
    {
        this.hairStyle = hairStyle;
    }

    public Long getHairColor()
    {
        return this.hairColor;
    }

    public void setHairColor(Long hairColor)
    {
        this.hairColor = hairColor;
    }

    public Long getSex()
    {
        return this.sex;
    }

    public void setSex(Long sex)
    {
        this.sex = sex;
    }

    public Long getHeading()
    {
        return this.heading;
    }

    public void setHeading(Long heading)
    {
        this.heading = heading;
    }

    public Long getX()
    {
        return this.x;
    }

    public void setX(Long x)
    {
        this.x = x;
    }

    public Long getY()
    {
        return this.y;
    }

    public void setY(Long y)
    {
        this.y = y;
    }

    public Long getZ()
    {
        return this.z;
    }

    public void setZ(Long z)
    {
        this.z = z;
    }

    public BigDecimal getMovementMultiplier()
    {
        return this.movementMultiplier;
    }

    public void setMovementMultiplier(BigDecimal movementMultiplier)
    {
        this.movementMultiplier = movementMultiplier;
    }

    public BigDecimal getAttackSpeedMultiplier()
    {
        return this.attackSpeedMultiplier;
    }

    public void setAttackSpeedMultiplier(BigDecimal attackSpeedMultiplier)
    {
        this.attackSpeedMultiplier = attackSpeedMultiplier;
    }

    public BigDecimal getColRad()
    {
        return this.colRad;
    }

    public void setColRad(BigDecimal colRad)
    {
        this.colRad = colRad;
    }

    public BigDecimal getColHeight()
    {
        return this.colHeight;
    }

    public void setColHeight(BigDecimal colHeight)
    {
        this.colHeight = colHeight;
    }

    public BigDecimal getExp()
    {
        return this.exp;
    }

    public void setExp(BigDecimal exp)
    {
        this.exp = exp;
    }

    public Long getSp()
    {
        return this.sp;
    }

    public void setSp(Long sp)
    {
        this.sp = sp;
    }

    public Long getKarma()
    {
        return this.karma;
    }

    public void setKarma(Long karma)
    {
        this.karma = karma;
    }

    public Long getPvpkills()
    {
        return this.pvpkills;
    }

    public void setPvpkills(Long pvpkills)
    {
        this.pvpkills = pvpkills;
    }

    public Long getPkkills()
    {
        return this.pkkills;
    }

    public void setPkkills(Long pkkills)
    {
        this.pkkills = pkkills;
    }

    public Long getClanid()
    {
        return this.clanid;
    }

    public void setClanid(Long clanid)
    {
        this.clanid = clanid;
    }

    public Long getMaxload()
    {
        return this.maxload;
    }

    public void setMaxload(Long maxload)
    {
        this.maxload = maxload;
    }

    public Long getRace()
    {
        return this.race;
    }

    public void setRace(Long race)
    {
        this.race = race;
    }

    public Long getClassid()
    {
        return this.classid;
    }

    public void setClassid(Long classid)
    {
        this.classid = classid;
    }

    public int getBaseClass()
    {
        return this.baseClass;
    }

    public void setBaseClass(int baseClass)
    {
        this.baseClass = baseClass;
    }

    public BigDecimal getDeletetime()
    {
        return this.deletetime;
    }

    public void setDeletetime(BigDecimal deletetime)
    {
        this.deletetime = deletetime;
    }

    public Long getCancraft()
    {
        return this.cancraft;
    }

    public void setCancraft(Long cancraft)
    {
        this.cancraft = cancraft;
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Long getAllyId()
    {
        return this.allyId;
    }

    public void setAllyId(Long allyId)
    {
        this.allyId = allyId;
    }

    public int getRecHave()
    {
        return this.recHave;
    }

    public void setRecHave(int recHave)
    {
        this.recHave = recHave;
    }

    public int getRecLeft()
    {
        return this.recLeft;
    }

    public void setRecLeft(int recLeft)
    {
        this.recLeft = recLeft;
    }

    public Short getAccesslevel()
    {
        return this.accesslevel;
    }

    public void setAccesslevel(Short accesslevel)
    {
        this.accesslevel = accesslevel;
    }

    public Boolean getOnline()
    {
        return this.online;
    }

    public void setOnline(Boolean online)
    {
        this.online = online;
    }

    public Boolean getCharSlot()
    {
        return this.charSlot;
    }

    public void setCharSlot(Boolean charSlot)
    {
        this.charSlot = charSlot;
    }

    public Boolean getNewbie()
    {
        return this.newbie;
    }

    public void setNewbie(Boolean newbie)
    {
        this.newbie = newbie;
    }

    public BigDecimal getLastAccess()
    {
        return this.lastAccess;
    }

    public void setLastAccess(BigDecimal lastAccess)
    {
        this.lastAccess = lastAccess;
    }

    public Integer getClanPrivs()
    {
        return this.clanPrivs;
    }

    public void setClanPrivs(Integer clanPrivs)
    {
        this.clanPrivs = clanPrivs;
    }

    public Boolean getWantspeace()
    {
        return this.wantspeace;
    }

    public void setWantspeace(Boolean wantspeace)
    {
        this.wantspeace = wantspeace;
    }

    public BigDecimal getDeleteclan()
    {
        return this.deleteclan;
    }

    public void setDeleteclan(BigDecimal deleteclan)
    {
        this.deleteclan = deleteclan;
    }

    public boolean isIsin7sdungeon()
    {
        return this.isin7sdungeon;
    }

    public void setIsin7sdungeon(boolean isin7sdungeon)
    {
        this.isin7sdungeon = isin7sdungeon;
    }

    public BigDecimal getOnlinetime()
    {
        return this.onlinetime;
    }

    public void setOnlinetime(BigDecimal onlinetime)
    {
        this.onlinetime = onlinetime;
    }

    public Boolean getInJail()
    {
        return this.inJail;
    }

    public void setInJail(Boolean inJail)
    {
        this.inJail = inJail;
    }

    public BigDecimal getJailTimer()
    {
        return this.jailTimer;
    }

    public void setJailTimer(BigDecimal jailTimer)
    {
        this.jailTimer = jailTimer;
    }

    public Byte getNobless()
    {
        return this.nobless;
    }

    public void setNobless(Byte nobless)
    {
        this.nobless = nobless;
    }

    public Integer getVarka()
    {
        return this.varka;
    }

    public void setVarka(Integer varka)
    {
        this.varka = varka;
    }

    public Integer getKetra()
    {
        return this.ketra;
    }

    public void setKetra(Integer ketra)
    {
        this.ketra = ketra;
    }

    public int getEquipedWithZariche()
    {
        return this.equipedWithZariche;
    }

    public void setEquipedWithZariche(int equipedWithZariche)
    {
        this.equipedWithZariche = equipedWithZariche;
    }

    public long getZarichePk()
    {
        return this.zarichePk;
    }

    public void setZarichePk(long zarichePk)
    {
        this.zarichePk = zarichePk;
    }

    public BigDecimal getZaricheTime()
    {
        return this.zaricheTime;
    }

    public void setZaricheTime(BigDecimal zaricheTime)
    {
        this.zaricheTime = zaricheTime;
    }

    public int getPledgeClass()
    {
        return this.pledgeClass;
    }

    public void setPledgeClass(int pledgeClass)
    {
        this.pledgeClass = pledgeClass;
    }

    public int getPledgeType()
    {
        return this.pledgeType;
    }

    public void setPledgeType(int pledgeType)
    {
        this.pledgeType = pledgeType;
    }

    public int getPledgeRank()
    {
        return this.pledgeRank;
    }

    public void setPledgeRank(int pledgeRank)
    {
        this.pledgeRank = pledgeRank;
    }

    public String getApprentice()
    {
        return this.apprentice;
    }

    public void setApprentice(String apprentice)
    {
        this.apprentice = apprentice;
    }

    public int getAccademyLvl()
    {
        return this.accademyLvl;
    }

    public void setAccademyLvl(int accademyLvl)
    {
        this.accademyLvl = accademyLvl;
    }


    /**
     * @return the accountName
     */
    public String getAccountName()
    {
        return accountName;
    }

    /**
     * @param accountName the accountName to set
     */
    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    /**
     * @param characterRecommendations the characterRecommendations to set
     */
    public void setCharacterRecommendations(Set<CharRecommendation> characterRecommendations)
    {
        this.characterRecommendations = characterRecommendations;
    }

    public Set<CharRecommendation> getCharacterRecommendations()
    {
        return characterRecommendations;
    }
    
    
}
