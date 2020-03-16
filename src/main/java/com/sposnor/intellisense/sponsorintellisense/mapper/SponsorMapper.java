package com.sposnor.intellisense.sponsorintellisense.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sposnor.intellisense.sponsorintellisense.data.model.Sequence;
import com.sposnor.intellisense.sponsorintellisense.data.model.Sponsor;
import com.sposnor.intellisense.sponsorintellisense.data.model.SponsorReport;

@Mapper
//@CacheNamespace(implementation=org.mybatis.caches.ehcache.EhcacheCache.class)
public interface SponsorMapper {

	public static final String SELECT_SPONSOR_BY_ID ="SELECT *,P.NAME PARISHNAME,P.CENTERID FROM SPONSOR S, PARISH P WHERE S.PARISHID = P.ID AND S.ID = #{id}";
	
	public static final String SELECT_ALL_ACTIVE_SPONSORS = "SELECT S.ID, FIRSTNAME, LASTNAME, MIDDLEINITIAL, NICKNAME, STATE,"
			+ "sponsorCode, P.NAME parishName, P.CITY PARISHCITY FROM SPONSOR S LEFT JOIN PARISH P ON S.PARISHID = P.ID WHERE S.SPONSORSTATUS = 0";
	
	public static final String SELECT_ALL_ACTIVE_SPONSORS_BY_FN_LN_PARISH_ID = "SELECT S.ID, FIRSTNAME, LASTNAME, MIDDLEINITIAL, STREET,S.CITY, STATE,POSTALCODE, SPONSORCODE,EMAILADDRESS,PHONE1,PHONE2, P.PROMOTEREMAIL promoterEmail " + 
			"FROM SPONSOR S, PARISH P WHERE S.PARISHID =P.ID AND S.SPONSORSTATUS = 0 AND PARISHID= #{id} "
			+ " AND FIRSTNAME LIKE CONCAT(#{firstName}, '%') AND LASTNAME LIKE CONCAT(#{lastName}, '%')";

	/*
	public static final String SELECT_ALL_ACTIVE_SPONSORS_BY_PARISHID = "SELECT S.ID, CONCAT(R.CODE,'-',C.CODE,'-',P.CODE,'-',S.SPONSORCODE) sponsorCode, "
			+ "FIRSTNAME, LASTNAME, MIDDLEINITIAL, NICKNAME,street, s.city, STATE,postalCode, parishId "
			+ "FROM SPONSOR S, PARISH P, CENTER C,REGION R WHERE S.PARISHID = P.ID "
			+ "AND P.CENTERID = C.ID AND C.REGIONID = R.ID "
			+ "AND S.PARISHID = #{id} AND S.SPONSORSTATUS = 0 order by sponsorCode";
	*/
	public static final String SELECT_ALL_ACTIVE_SPONSORS_BY_PARISHID = "SELECT S.ID,(CASE WHEN EN.ID IS NULL THEN RAND() ELSE EN.ID END) ENTID, EN.MISCAMOUNT, DATE_FORMAT(en.effectiveDate,'%m/%Y') effectiveDate, "
			+ "COUNT(SE.STUDENTID) NOOFSTUDENTS, SE.EXPIRATIONMONTH, SE.EXPIRATIONYEAR, "
			+ "CONCAT(R.CODE,'-',C.CODE,'-',P.CODE,'-',S.SPONSORCODE) SPONSORCODE, FIRSTNAME, LASTNAME, MIDDLEINITIAL, "
			+ "NICKNAME,STREET, S.CITY, STATE,POSTALCODE, PARISHID "
			+ "FROM SPONSOR S LEFT JOIN ENROLLMENT EN ON  EN.SPONSORID= S.ID AND EN.STATUS =0 LEFT JOIN SPONSEE SE ON EN.ID=SE.ENROLLMENTID, "
			+ "PARISH P, CENTER C,REGION R WHERE S.PARISHID = P.ID AND P.CENTERID = C.ID "
			+ "AND C.REGIONID = R.ID AND S.PARISHID = #{id} AND S.SPONSORSTATUS = 0 GROUP BY ENTID ORDER BY SPONSORCODE, EN.ID";
	
	public static final String INSERT_SPONSOR = "INSERT INTO SPONSOR(parishId, firstName, lastName, middleInitial, nickName, dayOfBirth, "
			+ "monthOfBirth, sponsorStatus, emailAddress, appartmentNumber, street, city, state, postalCode, hasAnyCoSponser, "
			+ "coSponserName, sponsorCode, phone1, phone2, createdBy, createdDate)"
			+ " values  (#{parishId},#{firstName},#{lastName},#{middleInitial},#{nickName}, #{dayOfBirth},#{monthOfBirth},#{sponsorStatus},#{emailAddress},"
			+ "#{appartmentNumber},#{street},#{city},#{state},#{postalCode},#{hasAnyCoSponser},#{coSponserName},"
			+ " #{sponsorCode}, #{phone1}, #{phone2}, #{createdBy}, #{createdDate})";
	
	public static final String UPDATE_SPONSOR = "UPDATE SPONSOR SET parishId= #{parishId} , firstName= #{firstName}, lastName= #{lastName}, phone1=#{phone1}, phone2=#{phone2}, "
			+ " middleInitial= #{middleInitial}, nickName=#{nickName}, dayOfBirth= #{dayOfBirth}, monthOfBirth= #{monthOfBirth}, sponsorStatus= #{sponsorStatus},"
			+ " emailAddress= #{emailAddress}, appartmentNumber= #{appartmentNumber}, street= #{street}, city= #{city}, state= #{state},"
			+ " postalCode= #{postalCode}, hasAnyCoSponser= #{hasAnyCoSponser}, "
			+ " sponsorCode= #{sponsorCode} , coSponserName= #{coSponserName}, updatedBy= #{updatedBy} WHERE id=#{id}";
	
	public static final String SEARCH_BY_NAME ="SELECT S.ID, FIRSTNAME, LASTNAME, MIDDLEINITIAL, NICKNAME, PARISHID, NAME parishName, P.CITY parishCity "
			+ "FROM SPONSOR S, PARISH P WHERE S.PARISHID = P.ID AND SPONSORSTATUS = 0 AND FIRSTNAME LIKE #{name} ";
	
	@Select(SELECT_SPONSOR_BY_ID)
	Sponsor findById(@Param("id") Long id);
	
	@Select("select (CASE WHEN max(seq_val) IS NULL THEN 1000 ELSE max(seq_val) END) sequence from sponsor_sequence where parishId=#{id}")
	Sequence getSequenceByParishId(@Param("id") Long id);
	
	@Select(SELECT_ALL_ACTIVE_SPONSORS)
	List<Sponsor> list();
	
	@Select(SELECT_ALL_ACTIVE_SPONSORS_BY_FN_LN_PARISH_ID)
	List<Sponsor> list2(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("id") Long id);
	
	@Select(SELECT_ALL_ACTIVE_SPONSORS_BY_PARISHID)
	List<Sponsor> listSponsorsByParishId(@Param("id") Long id);
	
	@Insert(INSERT_SPONSOR)
	@SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty= "id",
			before = false, resultType= Long.class)
	void insert(Sponsor sponsor);
	
	@Select("SELECT S.ID FROM SPONSOR S WHERE SPONSORSTATUS = 0 AND parishId=#{parishid} AND sponsorCode= #{code}")
	List<Sponsor> searchByCode(@Param("code") String code, @Param("parishid") Long parishId);
	
	@Update(UPDATE_SPONSOR)	
	void update(Sponsor sponsor);
	
	@Update("UPDATE SPONSOR SET contributionMaxOut= #{contributionMaxOut} WHERE ID= #{id}")
	void updateMaxOut(Sponsor sponsor);
	
	@Select("SELECT id,firstname, sponsorcode, middleInitial, lastName,street,city,state,postalCode,emailAddress, emailAddress2,phone1,phone2 FROM SPONSOR s WHERE s.parishid=#{parishid} AND s.sponsorCode = #{code}")
	Sponsor getSponsorByParishIdAndSponsorCode(@Param("parishid") Long parishId, @Param("code") String spondorCode);
	
	@Select(SEARCH_BY_NAME)
	List<Sponsor> searchByName(@Param("name") String name);
//	/profilePicture
	
	@Select("SELECT EN.ID, DATE_FORMAT(SM.maxOut, '%M %Y') renewalDue, CONCAT(R.CODE,'-',C.CODE,'-',P.CODE,'-',SP.SPONSORCODE) UNIQUEID, "
			+ "CASE hasAnyCoSponser WHEN '1' THEN CONCAT(FIRSTNAME,' ','&',' ',coSponserName ) ELSE CONCAT(FIRSTNAME,' ',COALESCE(MIDDLEINITIAL, ''),' ',LASTNAME ) END sponsorName, "
			+ "NICKNAME,  P.NAME parishName,P.CITY parishCity,C.NAME centerName, R.NAME regionName, "
			+ "APPARTMENTNUMBER,STREET, SP.CITY sponsorCity, STATE sponsorState,POSTALCODE, SP.EMAILADDRESS emailAddress, "
			+ "emailAddress2, phone1, phone2, DATE_FORMAT(paymentDate, \"%M 1 %Y\") paymentDate,  "
			+ "ROUND(CONTRIBUTIONAMOUNT,2) CONTRIBUTION, ROUND(MISCAMOUNT,2) MISCAMOUNT, ROUND(CONTRIBUTIONAMOUNT + MISCAMOUNT,2) TOTAL, "
			+ "ROUND(NETAMOUNT,2) NETCONTRIBUTION, ROUND(NETAMOUNT + MISCAMOUNT,2) NETTOTAL, EN.CREATEDDATE FROM SPONSOR SP, PARISH P, ENROLLMENT EN , CENTER C, "
			+ "REGION R, SPONSOR_MAXOUT SM  WHERE P.ID = SP.PARISHID AND EN.ID=SM.ENROLLMENTID AND EN.SPONSORID = SP.ID  AND P.CENTERID = C.ID AND C.REGIONID = R.ID "
			+ "AND EN.ID = #{id}  GROUP BY en.id")
	SponsorReport findSponsorByEnrolmentId(@Param("id") Long id);
	
	@Select({"<script>",
        "SELECT S.ID, PARISHID,FIRSTNAME, LASTNAME, SPONSORCODE, S.STREET, S.CITY, S.STATE, S.POSTALCODE, P.NAME parishName,P.CITY parishCity, "
        + "EMAILADDRESS,PHONE1,PHONE2, P.PROMOTEREMAIL promoterEmail FROM SPONSOR S, PARISH P "
        + "WHERE S.PARISHID=P.ID AND SPONSORCODE LIKE CONCAT(#{sponsorCode}, '%') AND FIRSTNAME LIKE CONCAT(#{firstName}, '%') "
        + "AND LASTNAME LIKE CONCAT(#{lastName}, '%') AND S.postalCode LIKE CONCAT(#{zipCode}, '%') AND S.PARISHID IN ", 
          "<foreach item='pid' index='index' collection='parishIds'",
            "open='(' separator=',' close=')'>",
            "#{pid}",
          "</foreach>",
        "</script>"})
	List<Sponsor> searchSponsor(
			@Param("parishIds") Long[] parishIds, 
			@Param("zipCode") String zipCode, 
			@Param("sponsorCode") String sponsorCode,
			@Param("firstName") String firstName,
			@Param("lastName") String lastName);
}
//@Options(flushCache=true)
//@Options(useGeneratedKeys = true, keyProperty = "id", flushCache=true)