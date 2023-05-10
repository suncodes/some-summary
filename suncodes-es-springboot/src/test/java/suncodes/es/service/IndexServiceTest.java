package suncodes.es.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import suncodes.es.ElasticsearchApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ElasticsearchApplication.class)
public class IndexServiceTest {

    @Autowired
    private IndexService indexService;

    @Test
    public void f() {
        indexService.deleteIndex();
    }
}