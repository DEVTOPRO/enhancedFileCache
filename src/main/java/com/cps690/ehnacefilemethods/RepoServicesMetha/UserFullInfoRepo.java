package com.cps690.ehnacefilemethods.RepoServicesMetha;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cps690.ehnacefilemethods.Dto.UserDto;
import com.cps690.ehnacefilemethods.EntityModels.UserCompInfo;

@Repository
@EnableJpaRepositories
public interface UserFullInfoRepo extends JpaRepository<UserCompInfo, Long> {
	
	@Query(value="SELECT * FROM user_comp_info LIMIT :limtedNumber", nativeQuery = true)
	List<UserCompInfo> findByLimitByValue(@Param("limtedNumber") int limtedNumber);
}
