package com.sposnor.intellisense.sponsorintellisense.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sposnor.intellisense.sponsorintellisense.data.model.SponseeReport;
import com.sposnor.intellisense.sponsorintellisense.data.model.Sponsor;

@Mapper
//@CacheNamespace(implementation=org.mybatis.caches.ehcache.EhcacheCache.class)
public interface SponsorMapper {

	public static final String SELECT_SPONSOR_BY_ID ="SELECT *,P.NAME PARISHNAME FROM SPONSOR S, PARISH P WHERE S.PARISHID = P.ID AND S.ID = #{id}";
	
	public static final String SELECT_ALL_ACTIVE_SPONSORS = "SELECT S.ID, FIRSTNAME, LASTNAME, MIDDLEINITIAL, NICKNAME, STATE, P.NAME "
			+ "parishName FROM SPONSOR S LEFT JOIN PARISH P ON S.PARISHID = P.ID WHERE S.SPONSORSTATUS = 0";
	
	public static final String SELECT_ALL_ACTIVE_SPONSORS_BY_PARISHID = "SELECT S.ID, FIRSTNAME, LASTNAME, MIDDLEINITIAL, NICKNAME, STATE "
			+ "FROM SPONSOR S, PARISH P WHERE S.PARISHID = P.ID AND S.PARISHID = #{id} AND S.SPONSORSTATUS = 0";
	
	public static final String INSERT_SPONSOR = "INSERT INTO SPONSOR	(parishId, firstName, lastName, middleInitial, nickName, dayOfBirth, "
			+ "monthOfBirth, sponsorStatus, emailAddress, appartmentNumber, street, city, state, postalCode, hasAnyCoSponser, coSponserName)"
			+ " values  (#{parishId},#{firstName},#{lastName},#{middleInitial},#{nickName}, #{dayOfBirth},#{monthOfBirth},#{sponsorStatus},#{emailAddress},"
			+ "#{appartmentNumber},#{street},#{city},#{state},#{postalCode},#{hasAnyCoSponser},#{coSponserName})";
	
	public static final String UPDATE_SPONSOR = "UPDATE SPONSOR SET parishId= #{parishId} , firstName= #{firstName}, lastName= #{lastName}, "
			+ "middleInitial= #{middleInitial}, nickName=#{nickName}, dayOfBirth= #{dayOfBirth}, monthOfBirth= #{monthOfBirth}, sponsorStatus= #{sponsorStatus},"
			+ " emailAddress= #{emailAddress}, appartmentNumber= #{appartmentNumber}, street= #{street}, city= #{city}, state= #{state},"
			+ " postalCode= #{postalCode}, hasAnyCoSponser= #{hasAnyCoSponser}, coSponserName= #{coSponserName} WHERE id=#{id}";
	
	public static final String SEARCH_BY_NAME ="SELECT S.ID, FIRSTNAME, LASTNAME, MIDDLEINITIAL, NICKNAME, PARISHID, NAME parishName, P.CITY parishCity "
			+ "FROM SPONSOR S, PARISH P WHERE S.PARISHID = P.ID AND SPONSORSTATUS = 0 AND FIRSTNAME LIKE #{name} ";
	
	@Select(SELECT_SPONSOR_BY_ID)
	Sponsor findById(@Param("id") Long id);
	
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
	
	@Select("SELECT CONCAT(A.CODE,'-',P.CODE,'-',ST.ID) UNIQUEID, CONCAT(ST.FIRSTNAME,' ',ST.LASTNAME) STUDENTNAME, "
			+ "DATEOFBIRTH, GENDER, HOBBIES, A.NAME AGENCYNAME, P.NAME PROJECTNAME FROM ENROLLMENT EN, SPONSEE SPE, "
			+ "STUDENT ST, PROJECT P, AGENCY A WHERE EN.ID = SPE.ENROLLMENTID AND SPE.STUDENTID = ST.ID "
			+ "AND ST.PROJECTID = P.ID AND P.AGENCYID = A.ID AND EN.ID = #{id} ")
	List<SponseeReport> listSponseesByEnrolmentId(@Param("id") Long id);
}
//@Options(flushCache=true)
//@Options(useGeneratedKeys = true, keyProperty = "id", flushCache=true)