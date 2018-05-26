package com.sposnor.intellisense.sponsorintellisense.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sposnor.intellisense.sponsorintellisense.data.model.Sequence;
import com.sposnor.intellisense.sponsorintellisense.data.model.SponseeReport;
import com.sposnor.intellisense.sponsorintellisense.data.model.Sponsor;

@Mapper
//@CacheNamespace(implementation=org.mybatis.caches.ehcache.EhcacheCache.class)
public interface SponsorMapper {

	public static final String SELECT_SPONSOR_BY_ID ="SELECT *,P.NAME PARISHNAME,P.CENTERID FROM SPONSOR S, PARISH P WHERE S.PARISHID = P.ID AND S.ID = #{id}";
	
	public static final String SELECT_ALL_ACTIVE_SPONSORS = "SELECT S.ID, FIRSTNAME, LASTNAME, MIDDLEINITIAL, NICKNAME, STATE,"
			+ "sponsorCode, P.NAME parishName, P.CITY PARISHCITY FROM SPONSOR S LEFT JOIN PARISH P ON S.PARISHID = P.ID WHERE S.SPONSORSTATUS = 0";
	
	public static final String SELECT_ALL_ACTIVE_SPONSORS_BY_PARISHID = "SELECT S.ID, CONCAT(R.CODE,'-',C.CODE,'-',P.CODE,'-',S.SPONSORCODE) sponsorCode, "
			+ "FIRSTNAME, LASTNAME, MIDDLEINITIAL, NICKNAME,street, s.city, STATE,postalCode, parishId "
			+ "FROM SPONSOR S, PARISH P, CENTER C,REGION R WHERE S.PARISHID = P.ID "
			+ "AND P.CENTERID = C.ID AND C.REGIONID = R.ID "
			+ "AND S.PARISHID = #{id} AND S.SPONSORSTATUS = 0";
	
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
	
	@Select("select (CASE WHEN max(seq_val) IS NULL THEN 5000 ELSE max(seq_val) END) sequence from sponsor_sequence where parishId=#{id}")
	Sequence getSequenceByParishId(@Param("id") Long id);
	
	@Select(SELECT_ALL_ACTIVE_SPONSORS)
	List<Sponsor> list();
	
	@Select(SELECT_ALL_ACTIVE_SPONSORS_BY_PARISHID)
	List<Sponsor> listSponsorsByParishId(@Param("id") Long id);
	
	@Insert(INSERT_SPONSOR)
	@SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty= "id",
			before = false, resultType= Long.class)
	void insert(Sponsor sponsor);
	
	@Update(UPDATE_SPONSOR)	
	void update(Sponsor sponsor);
	
	@Update("UPDATE SPONSOR SET contributionMaxOut= #{contributionMaxOut} WHERE ID= #{id}")
	void updateMaxOut(Sponsor sponsor);
	
	
	@Select(SEARCH_BY_NAME)
	List<Sponsor> searchByName(@Param("name") String name);
	
	@Select("SELECT CONCAT(A.CODE,'-',P.CODE,'-',ST.STUDENTCODE) UNIQUEID, STUDENTNAME, "
			+ "DATE_FORMAT(str_to_date(DATEOFBIRTH, '%m/%d/%Y'), '%M %d %Y') DATEOFBIRTH, GENDER, GRADE,FAVCOLOR,FAVGAME,NAMEOFGUARDIAN,OCCUPATIONOFGUARDIAN,BASELANGUAGE,"
			+ " HOBBIES hobby, A.NAME AGENCYNAME, P.NAME PROJECTNAME, P.ADDRESS ADDRESS , profilePicture FROM ENROLLMENT EN, SPONSEE SPE, "
			+ "STUDENT ST, PROJECT P, AGENCY A WHERE EN.ID = SPE.ENROLLMENTID AND SPE.STUDENTID = ST.ID "
			+ "AND ST.PROJECTID = P.ID AND P.AGENCYID = A.ID AND EN.ID = #{id}  ORDER BY UNIQUEID ")
	List<SponseeReport> listSponseesByEnrolmentId(@Param("id") Long id);
}
//@Options(flushCache=true)
//@Options(useGeneratedKeys = true, keyProperty = "id", flushCache=true)