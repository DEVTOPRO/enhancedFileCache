package com.cps690.ehnacefilemethods.RepoServicesMetha;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import com.cps690.ehnacefilemethods.EntityModels.TestModel;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
@EnableJpaRepositories
public interface TestRepo extends JpaRepository<TestModel, Long>  {

}
