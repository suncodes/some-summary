package suncodes.es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;

import java.io.IOException;

public class IndexRequestOps {
    public static void main(String[] args) throws IOException {
        IndexRequestOps indexRequestOps = new IndexRequestOps();
        indexRequestOps.createIndexIfNoExists("user");
        indexRequestOps.deleteIndex("user");
    }

    private void deleteIndex(String indexName) throws IOException {
        // 创建客户端对象
        HttpHost httpHost = new HttpHost("175.24.228.216", 9200, "http");
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(httpHost));
        // 删除索引 - 请求对象
        DeleteIndexRequest request = new DeleteIndexRequest("user");
        // 发送请求，获取响应
        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
        // 操作结果
        System.out.println("操作结果 ： " + response.isAcknowledged());
        // 关闭客户端连接
        client.close();
    }

    private void createIndexIfNoExists(String indexName) throws IOException {
        // 创建客户端对象
        HttpHost httpHost = new HttpHost("175.24.228.216", 9200, "http");
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(httpHost));

        GetIndexRequest getRequest = new GetIndexRequest(indexName);
        boolean exists = client.indices().exists(getRequest, RequestOptions.DEFAULT);
        if (exists) {
            System.out.println("索引已存在");
            GetIndexResponse getIndexResponse = client.indices().get(getRequest, RequestOptions.DEFAULT);
            System.out.println(getIndexResponse);
            // 关闭客户端连接
            client.close();
            return;
        }
        // 创建索引 - 请求对象
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        // 发送请求，获取响应
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        boolean acknowledged = response.isAcknowledged();
        // 响应状态
        System.out.println("操作状态 = " + acknowledged);

        // 关闭客户端连接
        client.close();
    }
}
