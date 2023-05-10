package suncodes.es.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.stereotype.Service;
import suncodes.es.pojo.po.ProductPO;

@Service
public class IndexService {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public void deleteIndex(){
        //创建索引，系统初始化会自动创建索引
        boolean flg = elasticsearchRestTemplate.indexOps(ProductPO.class).delete();
        System.out.println("删除索引 = " + flg);
    }
}
