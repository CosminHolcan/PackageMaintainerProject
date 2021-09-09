package ro.arc.packageManager.core.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Ignore;
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

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF/dbtest/db-data.xml")
public class MaintainerServiceTest {

    @Autowired
    private MaintainerService maintainerService;

    @Test
    public void findAll() throws Exception {
        List<Maintainer> maintainers = maintainerService.getAllMaintainers();
        assertEquals("there should be four maintainers", 4, maintainers.size());
    }

    @Test
    public void updateMaintainer() throws Exception {
        Maintainer m = maintainerService.getOneMaintainer(1L);
        m.setEmail("newemail@yahoo.com");
        maintainerService.updateMaintainer(m);
        assertEquals("the email should be the new one",  "newemail@yahoo.com",maintainerService.getOneMaintainer(1L).getEmail());
    }


    @Test
    public void deleteMaintainer() throws Exception {
        maintainerService.deleteMaintainer(1L);
        List<Maintainer> maintainers = maintainerService.getAllMaintainers();
        assertEquals("there should be 3 maintainers", 3, maintainers.size());

    }

    @Test
    public void getFilteredTest() throws Exception {
        List<Maintainer> maintainers = maintainerService.getFilteredMaintainers("email", "gmail");
        assertEquals("there should be 3 maintainers", 3, maintainers.size());
    }

}
