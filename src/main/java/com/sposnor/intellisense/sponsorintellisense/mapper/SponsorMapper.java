package com.sposnor.intellisense.sponsorintellisense.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sposnor.intellisense.sponsorintellisense.data.model.Sponsor;

@Mapper
//@CacheNamespace(implementation=org.mybatis.caches.ehcache.EhcacheCache.class)
public interface SponsorMapper {

	public static final String SELECT_SPONSOR_BY_ID ="select *,p.name parishName from sponsor s, parish p where s.id =#{id}";
	
	public static final String SELECT_ALL_ACTIVE_SPONSORS = "SELECT S.ID, FIRSTNAME, LASTNAME, MIDDLEINITIAL, STATE, P.NAME "
			+ "parishName FROM SPONSOR S LEFT JOIN PARISH P ON S.PARISHID = P.ID WHERE S.SPONSORSTATUS = 0";
	
	public static final String INSERT_SPONSOR = "INSERT INTO SPONSOR	(parishId, firstName, lastName, middleInitial, dayOfBirth, "
			+ "monthOfBirth, sponsorStatus, emailAddress, appartmentNumber, street, city, state, postalCode, hasAnyCoSponser, coSponserName)"
			+ " values  (#{parishId},#{firstName},#{lastName},#{middleInitial},#{dayOfBirth},#{monthOfBirth},#{sponsorStatus},#{emailAddress},"
			+ "#{appartmentNumber},#{street},#{city},#{state},#{postalCode},#{hasAnyCoSponser},#{coSponserName})";
	
	public static final String UPDATE_SPONSOR = "UPDATE SPONSOR SET parishId= #{parishId} , firstName= #{firstName}, lastName= #{lastName}, "
			+ "middleInitial= #{middleInitial}, dayOfBirth= #{dayOfBirth}, monthOfBirth= #{monthOfBirth}, sponsorStatus= #{sponsorStatus},"
			+ " emailAddress= #{emailAddress}, appartmentNumber= #{appartmentNumber}, street= #{street}, city= #{city}, state= #{state},"
			+ " postalCode= #{postalCode}, hasAnyCoSponser= #{hasAnyCoSponser}, coSponserName= #{coSponserName} WHERE id=#{id}";
	
	public static final String SEARCH_BY_NAME ="SELECT ID, FIRSTNAME, LASTNAME, MIDDLEINITIAL FROM SPONSOR WHERE SPONSORSTATUS = 0"
			+ " AND FIRSTNAME LIKE #{name} ";
	
	@Select(SELECT_SPONSOR_BY_ID)
	Sponsor findById(@Param("id") Long id);
	
	@Select(SELECT_ALL_ACTIVE_SPONSORS)
	List<Sponsor> list();
	
	@Insert(INSERT_SPONSOR)
	@SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty= "id",
			before = false, resultType= Long.class)
	void insert(Sponsor sponsor);
	
	@Update(UPDATE_SPONSOR)	
	void update(Sponsor sponsor);
	
	@Select(SEARCH_BY_NAME)
	List<Sponsor> searchByName(@Param("name") String name);
}
//@Options(flushCache=true)
//@Options(useGeneratedKeys = true, keyProperty = "id", flushCache=true)