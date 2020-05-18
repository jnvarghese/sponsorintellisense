package com.sposnor.intellisense.sponsorintellisense.data.model;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Student implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Student.class);
	
	private Long id;	
	private Long projectId;	
	private String projectCode;
	private String projectName;	
	private String projectAddress;	
	private Long agencyId;	
	private String agencyCode;
	private String agencyName;
	private String studentName;
	private String studentCode;
	private String gender;
	private String dateOfBirth;
	private String address;
	private String status;	
	private String hobbies;
	private String contributions;
	private String talent;
	private String recentAchivements;
	private byte[] profilePicture;
	private boolean softlocked;	
	private String maxOut;
	private String grade;
	private String favColor;
	private String favGame;
	private String nameOfGuardian;
	private String occupationOfGuardian;
	private String baseLanguage;
	private String imageLinkRef;
	private boolean hasImageUploaded;
	// 1 for present 0 for null.
	private int imagePresent;
	private Date createdDate;
	private Date updatedDate;
	private int expirationMonth;
	private int expirationYear;
	private Long createdBy;
	private Long updatedBy;	
	private String uploadstatus;
	private String studentUniqueCode;
	private String renewalDue;
	
	
	
	public String getRenewalDue() {
		return renewalDue;
	}
	public void setRenewalDue(String renewalDue) {
		this.renewalDue = renewalDue;
	}
	public String getProjectAddress() {
		return projectAddress;
	}
	public void setProjectAddress(String projectAddress) {
		this.projectAddress = projectAddress;
	}
	public String getStudentUniqueCode() {
		return studentUniqueCode;
	}
	public void setStudentUniqueCode(String studentUniqueCode) {
		this.studentUniqueCode = studentUniqueCode;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getAgencyCode() {
		return agencyCode;
	}
	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}
	public boolean isHasImageUploaded() {
		return hasImageUploaded;
	}
	public void setHasImageUploaded(boolean hasImageUploaded) {
		this.hasImageUploaded = hasImageUploaded;
	}
	public String getImageLinkRef() {
		return imageLinkRef;
	}
	public void setImageLinkRef(String imageLinkRef) {
		this.imageLinkRef = imageLinkRef;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Long getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getStudentCode() {
		return studentCode;
	}
	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Long getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
		
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getHobbies() {
		return hobbies;
	}
	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}
	public String getContributions() {
		return contributions;
	}
	public void setContributions(String contributions) {
		this.contributions = contributions;
	}
	public String getTalent() {
		return talent;
	}
	public void setTalent(String talent) {
		this.talent = talent;
	}
	public String getRecentAchivements() {
		return recentAchivements;
	}
	public void setRecentAchivements(String recentAchivements) {
		this.recentAchivements = recentAchivements;
	}
	
	public String getProfilePicture() throws IOException {
		String encodedImage;
		byte[] defaultPic = javax.xml.bind.DatatypeConverter.parseHexBinary("FFD8FFE000104A46494600010100000100010000FFDB00840009060708071015101012131615111512191513121515151817191615171716181317181D2820181A251B131721312125292B2E2E2E17203338332D37282D2E2B010A0A0A0E0C0B150A0C0E2B1915192B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2B2BFFC000110800E100E103012200021101031101FFC4001B00010002030101000000000000000000000005060103040207FFC4003E10000201030104050A0503030501000000000102030411051221314151536191B1061315162232717292C143528182D133A1A242C2E11462B2E2F023FFC40014010100000000000000000000000000000000FFC40014110100000000000000000000000000000000FFDA000C03010002110311003F00FB8800000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000003CCE71871697C5E0F3E7E8FE68F7A036187251E21C92E255F56D46AEA33F33472E39E5FEAFFD40999EB7A741E1D4EE8C9FF748C7A774DEB3FC67FC1C96FE4E5BA8ADB949CB9E1E17E86DF576CBA67F501BBD3BA6F59FE33FE02D734D7F89FE33FE0D3EAED974CFEA3C54F272D1A7B2E49F26DE7FB013109C2A2CA69A7C1A3D152B1BCB8D1AA3A7513D9CEF5FEE896AA7523512945E535B9A03D800000000000000000000000000000000000323750D62DEC5ECBCCA5F963CBE2F91DF564E116FA137DC55B40B4A77F5272A9ED6CE1E1F3726F8F700D575A85FD3D8D86B7A79CA7C3244D292A7252C7069F732F2AC2D3AB87D287FD05A7570FA5015CD4B5BA97D154E117152787BF2DF4244C68BA5C6C63B52F7E4B7BE85D08EDA7696D4DE6308A6B9A48DC80C835D6AF4A82CCA492E96F04655F282C21C1CA5F04FEE04B822297941633E3B51F8C7F824E857A55D66124D74A6071EB1A6C2FE1BB74E39D97F67D84069DAA56D2DBA738B697FA738717D9D85BCE0D4AA585BE255A2B7EE4DC769FF00640477ACF4FAA7F52FE07ACF4FAA7F52FE093A16D635E2A71A70C4B7AF652367FD05A7570FA501C961ADDBDE4B670E327C13C61FC1928557CA4B3A569284A9AD9DACEE5BB7AC61AEF2CB6D37521193E718BEF406D0000000000000000000000000001AEE3DC97CB2F02BFE48F1A9F0878C8B05C7B92F965E057FC91E353E10F190164000038356D469E9F1CF193F763D3DAFB0EE7B8A9C22F5ABA79F757FE31DD85F1FB80B6B1BCD625E72A49A8F4BF082E8ED2668E83634F8A72ED937F624A318C1612C25C12386FB58B5B27B326DCB9A8ACE3E206BADA0D854E1171ED8B7F721EEB4FBCD21F9CA726E2B8B5FEE5CD7693763ACDA5E3D94DA93E0A4B19F8723BE718C961A038749D4A17F1E4A51C6D2FBAEC36DFE9F46FD253CFB2DB5878EC2BB5E0F46B94E3EEB794BFED6FDA5FA7F05B22D359406BB6A11B78A82CE22B0B26D000AEF95DF87FBFEC4DD97F4E1F247C1109E577E1FEFFB13765FD387C91F0406F0000000000000000000000000001AEE3DC97CB2F02BFE48F1A9F0878C8B05C7B92F965E057FC91E353E10F1901640001CFA849C29546B9426FF00C590BE494162A4B9FB289DB9A7E76128FE68C977AC15DF25EB79AA93A4F736BFBC5E1AFF00EE802C17D55D0A539AE318B7DC8A1B6E4DB7C5E72FB799F40AB08D58B8BE0D34FF0052957FA6DC5949A69B5CA4936B1DBD0C0E3DE8BDE9D5A5714A137C65159F894FB1D3EE2F6588C5A5CE4D3C2FE4BAD0A51A11515C22924041F95B05B34DF4392EF5FF0004B699373A34DBE708F8109E55D6F3928535C565BFD7097DC9FB3A5E629C63F96297720370000AEF95DF87FBFEC4DD97F4E1F247C1109E577E1FEFFB13765FD387C91F0406F0000000000000000000000000001AEE3DC97CB2F02BFE48F1A9F0878C8B05C7B92F965E057FC91E353E10F1901640100055F5DB5AB635557A7B9369FC25DBD8CB41E2A538D54E3249A7C53E6072E9BA8D2D42395B9AF7A3CD7F28ECC22B779A057A12DBA0F87059C497C1F3354755D5AD774E39F9E0FC5600B4E11C9A8EA14AC6399717EEC79BFF008205EADAB5CEE8471F241E7BDE4D967A15C5C4B6EBB6BA567327F17C80F1A35B54D46B3AF53845E7E2F925D88B49E28D28514A31584B8247B000002BBE577E1FEFFB13765FD387C91F0442795DF87FBBEC4DD97F4E1F247C101BC00000000000000000000000000079A91DB4D74A6BBCAA68D751D2EACA153727B9BE8C3DCDF66F65B4E0D434BB6BEDF25897E65C7F5E90367A4EC7AD87D487A4EC7AD87D488CF56687592EE43D59A3D64BB90127E93B1EB61F521E93B1EB61F52233D59A3D64BB90F5668F592EE4049FA4EC7AD87D48C7A4AC3AD87D488DF5668F592EE43D59A3D64BB90125E92B1EB61F5233E93B1EB61F52233D59A3D64BB90F5668F592EE4049FA4EC7AD87D487A4EC7AD87D488CF5668F592EE43D59A3D64BB90127E93B1EB61F521E93B1EB61F52233D59A3D64BB90F56687592EE40716BF794EFE71852F6B1BB2B9B972459E843CD4631FCA92EE58386C347B5B27B4B32974CB97C17024800000000000000000000000600C80000000000000600C8000000000000000031909819061BC0C8190632640000000000000F336D27859787844368B7756E6599D6F6BDADAA2E2A38C7473DC4CCD49A78E3C9F69131B1BCB8A909D6D85E6D496D433996535BFA16F03AA96AD695A7B1196F6DA5B9E1B5C527CCC7A5ED3DADEFD8CED3D9961638EFC1C7474DBDC53A52D8F37466A4A4B3B52C3CA58E5DA6C8E9D5D50AB4F76D5494DADFBB12C633BBB00E98EA96D28EDE5ECB928A6E2F196B2BF4DFC4DF4EEA9549CA9A7ED431B5BB86786F39B5085285B4A35382825BBA5258C76E52396C215ED29C30B352BCD39B69EE4D65E7E0BC4093B9B9A76B1739BC457178CF3C723551D46DAB6D61B5B0B2F6938EEE9DFC8F3ABDAD4BCA32A70C6658C65EEE268BDD36A5D4EA3CA4AA538C53E69A9677AE803A6D352B6BB7B306F38CE1A6B2BA5678A1775142A535B6E3B4E588A5952DDC1BE4735B5A5E4EA427576179A8B8A506DE7384DBCADCB7703A2F2D6A56AB4A6B18A6E4DE7B563701AFD3564DE369F16B3B32C6572CE389EFD2B69E6FCE6D3C67671879CF4638E4E3A3A65C4234D7B3EC56737BF936DEEDDC4C3D2EE6399C76769579D48A6DE1A9727D0C0EC86AF6728CA5B4D2A78DACA6B19E0B7F3334754B5AD9C49AD88ED4B6A2E385DB922EF6D6BC2352AD5708B72A528E36A51CC77252C2CE3B446955D4AA568C9C1395282CC1ED24F69B49BE6C098B5BFA174F116F38CE249C72BA5678A31777F42D1A536F32E0A29C9FC70B91CBA6D8D6A53DA9C60B66384D4A726FA78BC25D86CBBB6BA55A35A96CB7B2E2E336D6ECE729AF801B28EA96B5B67664FFF00D1C945E1E1B5C564F33D5AD20B3B4FDE71584DB6D71D95CF0722D2EE1D0706D2A9B6E719478279CFE9CCF73D36B5BAA52A5B2E549493536D296D259795C1E7C40D95B50A1710DA85494529C62DA8BCE73EEE1AE66AB5BAA76B3B89CDE12A91E97C62B72478F46DE4E32DAD8DA9568CF7378C2E3CB899AFA5DC4DD492D9CBAB19C33C372C625D006750D4685DD0AAA0DED461969A716B2FB4DB4351B7B883845BDA54F3BD359F678ACF13454D3EFAE3CECA6A9A7529A8A516F761F378FEE7B8595ED69A954D85E6E9CA31D86F7B92C65E782033A7DFD1B6A14949B729433849C9E1716F049D1AB0AF1528BCA6B29908B48B9A7E6E49464E34D4251739456E6DA69AE3C7812F6141DB53516A29AE2A39C67B32F207400000000000000000000D7568D3ACB1249ADDB9ACF0E07BC1900000000000000799C233586934F93DE78A16F46DF742318AE88A4BC0DA000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000FFD9");
		if(null == profilePicture) {
			encodedImage = Base64.getEncoder().encodeToString(defaultPic);
			return encodedImage; 
		}
		encodedImage = Base64.getEncoder().encodeToString(this.profilePicture);
		return encodedImage; 
	}
	

	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}	
	
	public boolean isSoftlocked() {
		return softlocked;
	}
	public void setSoftlocked(boolean softlocked) {
		this.softlocked = softlocked;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) throws ParseException {
		java.util.Date dt = new java.util.Date();

		java.text.SimpleDateFormat sdf = 
		     new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String currentTime = sdf.format(dt);
		
		this.createdDate = sdf.parse(currentTime);
		
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public int getExpirationMonth() {
		return expirationMonth;
	}
	public void setExpirationMonth(int expirationMonth) {
		this.expirationMonth = expirationMonth;
	}
	public int getExpirationYear() {
		return expirationYear;
	}
	public void setExpirationYear(int expirationYear) {
		this.expirationYear = expirationYear;
	}
	
	public String getMaxOut() {
		return maxOut;
	}
	public void setMaxOut(String maxOut) {
		this.maxOut = maxOut;
	}
	
	
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getFavColor() {
		return favColor;
	}
	public void setFavColor(String favColor) {
		this.favColor = favColor;
	}
	public String getFavGame() {
		return favGame;
	}
	public void setFavGame(String favGame) {
		this.favGame = favGame;
	}
	public String getNameOfGuardian() {
		return nameOfGuardian;
	}
	public void setNameOfGuardian(String nameOfGuardian) {
		this.nameOfGuardian = nameOfGuardian;
	}
	public String getOccupationOfGuardian() {
		return occupationOfGuardian;
	}
	public void setOccupationOfGuardian(String occupationOfGuardian) {
		this.occupationOfGuardian = occupationOfGuardian;
	}
	public String getBaseLanguage() {
		return baseLanguage;
	}
	public void setBaseLanguage(String baseLanguage) {
		this.baseLanguage = baseLanguage;
	}
	
	
	public String getUploadstatus() {
		return uploadstatus;
	}
	public void setUploadstatus(String uploadstatus) {
		this.uploadstatus = uploadstatus;
	}
	public int getImagePresent() {
		return imagePresent;
	}
	public void setImagePresent(int imagePresent) {
		this.imagePresent = imagePresent;
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", projectId=" + projectId + ", projectName=" + projectName + ", agencyId="
				+ agencyId + ", agencyName=" + agencyName 
				+ ", studentName=" + studentName + ", gender=" + gender + ", dateOfBirth=" + dateOfBirth + ", address="
				+ address + ", status=" + status + ", hobbies=" + hobbies + ", contributions=" + contributions
				+ ", talent=" + talent + ", recentAchivements=" + recentAchivements + ", profilePicture="
				+ profilePicture + ", softlocked=" + softlocked + ", maxOut=" + maxOut + ", createdDate=" + createdDate
				+ ", updatedDate=" + updatedDate + ", expirationMonth=" + expirationMonth + ", expirationYear="
				+ expirationYear + "]";
	}
}
