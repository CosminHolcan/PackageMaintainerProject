package ro.arc.packageManager.core.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import ro.arc.packageManager.core.ITConfig;
import ro.arc.packageManager.core.domain.Maintainer;
import ro.arc.packageManager.core.service.MaintainerService;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF/dbtest/db-data.xml")
public class MaintainerRepoTest {

    @Autowired
    private MaintainerRepository maintainerRepository;

    @Test
    public void findAll() throws Exception {
        List<Maintainer> maintainers = maintainerRepository.findAllWithPackageMaintainers();
        assertEquals("there should be four maintainers", 4, maintainers.size());
    }

    @Test
    public void findOneWithPackageMaintainers() throws Exception{
        Optional<Maintainer> maintainerOptional = maintainerRepository.findOneWithPackageMaintainersJPQL(1L);
        Maintainer m = maintainerOptional.get();
        assertEquals("there should be just two links",2, m.getPackageMaintainers().size());
    }

    @Test
    public void testExistAllByUserNameOrEmail() throws Exception{
        boolean result = maintainerRepository.existsAllByUserNameOrEmail("cosmin", "dsa@gmail.com");
        assertEquals("answer should be true",true, result);
    }

}