package com.joyfulgarden.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MembersRepository extends JpaRepository<Members, Integer> {

	// 進行模糊查詢

	// 根據會員ID
//	@Query("SELECT m FROM Members m WHERE m.memberId LIKE %:id%")
//	List<Members> findByMemberIdContaining(@Param("id") String id);

//	// 根據會員名稱
//    @Query("SELECT m FROM Members m WHERE m.nickName LIKE %:name%")
//    List<Members> findBynickNameContaining(@Param("name") String name);

	// 你可以根據需要添加其他模糊查詢方法，如根據其他屬性進行查詢
	// @Query("SELECT m FROM Members m WHERE m.xxx LIKE %:xxx%")
	// List<Members> findByXxxContaining(@Param("xxx") String xxx);

//    Members findByUsernameAndDeletedFalse(String username);
    
 // 根據會員ID查詢
	List<Members> findByMemberId(Integer memberId);
	
	
	public Optional<Members> findByUsername(String username);

	public Optional<Members> findByPassword(String password);

	public Members findByUsernameAndPassword(String username, String password);

	Boolean existsByUsername(String username);

	public boolean existsByPassword(String password);



}
