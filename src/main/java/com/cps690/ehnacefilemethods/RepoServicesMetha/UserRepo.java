package com.cps690.ehnacefilemethods.RepoServicesMetha;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cps690.ehnacefilemethods.EntityModels.UserCompInfo;
import com.cps690.ehnacefilemethods.EntityModels.UserInfo;

@Repository
@EnableJpaRepositories
public interface UserRepo extends JpaRepository<UserInfo, Long> {

	@Query(value="SELECT * FROM user_infor LIMIT :limtedNumber ", nativeQuery = true)
	List<UserInfo> findByLimitByValue(@Param("limtedNumber") int limtedNumber);
	

}
