package suncodes.es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.Max;
import org.elasticsearch.search.aggregations.metrics.ParsedMax;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SearchRequestOps {
    public static void main(String[] args) throws IOException {
//        searchAll();
//        searchTermQuery();
//        searchPageQuery();
//        searchSortQuery();
//        searchFilterSourceQuery();
//        searchBoolQuery();
//        searchRangeQuery();
//        searchFuzzyQuery();
//        searchHighlightQuery();
//        searchAggQuery();
        searchGroupByQuery();
    }

    /**
     * 查询所有数据
     * @throws IOException
     */
    private static void searchAll() throws IOException {
        // 创建客户端对象
        HttpHost httpHost = new HttpHost("175.24.228.216", 9200, "http");
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(httpHost));

        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("student");
        // 构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 查询所有数据
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
        //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
        // 关闭客户端连接
        client.close();
    }

    /**
     * term 查询
     * @throws IOException
     */
    private static void searchTermQuery() throws IOException {
        // 创建客户端对象
        HttpHost httpHost = new HttpHost("175.24.228.216", 9200, "http");
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(httpHost));

        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("student");
        // 构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 查询所有数据
        sourceBuilder.query(QueryBuilders.termQuery("name", "zhangsan"));
        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
        // 关闭客户端连接
        client.close();
    }

    /**
     * 分页查询
     * @throws IOException
     */
    private static void searchPageQuery() throws IOException {
        // 创建客户端对象
        HttpHost httpHost = new HttpHost("175.24.228.216", 9200, "http");
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(httpHost));

        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("student");
        // 构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 查询所有数据
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        sourceBuilder.from(2);
        sourceBuilder.size(2);
        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
        // 关闭客户端连接
        client.close();
    }

    /**
     * 排序
     * @throws IOException
     */
    private static void searchSortQuery() throws IOException {
        // 创建客户端对象
        HttpHost httpHost = new HttpHost("175.24.228.216", 9200, "http");
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(httpHost));

        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("student");
        // 构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 查询所有数据
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        sourceBuilder.from(2);
        sourceBuilder.size(2);
        sourceBuilder.sort("age", SortOrder.DESC);
        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
        // 关闭客户端连接
        client.close();
    }

    /**
     * 过滤不必要的字段，只返回想要的字段
     * @throws IOException
     */
    private static void searchFilterSourceQuery() throws IOException {
        // 创建客户端对象
        HttpHost httpHost = new HttpHost("175.24.228.216", 9200, "http");
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(httpHost));

        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("student");
        // 构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 查询所有数据
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        sourceBuilder.from(2);
        sourceBuilder.size(2);
        sourceBuilder.sort("age", SortOrder.DESC);

        //查询字段过滤
        String[] excludes = {};
        String[] includes = {"name", "age"};
        sourceBuilder.fetchSource(includes, excludes);

        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
        // 关闭客户端连接
        client.close();
    }

    /**
     * bool 查询
     * @throws IOException
     */
    private static void searchBoolQuery() throws IOException {
        // 创建客户端对象
        HttpHost httpHost = new HttpHost("175.24.228.216", 9200, "http");
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(httpHost));

        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("student");
        // 构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 必须包含
//        boolQueryBuilder.must(QueryBuilders.matchQuery("age", "30"));
        // 一定不含
        boolQueryBuilder.mustNot(QueryBuilders.matchQuery("name", "zhangsan"));
        // 可能包含
//        boolQueryBuilder.should(QueryBuilders.matchQuery("sex", "男"));
        sourceBuilder.query(boolQueryBuilder);


        sourceBuilder.from(2);
        sourceBuilder.size(2);
        sourceBuilder.sort("age", SortOrder.DESC);

        //查询字段过滤
        String[] excludes = {};
        String[] includes = {"name", "age"};
        sourceBuilder.fetchSource(includes, excludes);

        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
        // 关闭客户端连接
        client.close();
    }

    /**
     * range 查询
     * @throws IOException
     */
    private static void searchRangeQuery() throws IOException {
        // 创建客户端对象
        HttpHost httpHost = new HttpHost("175.24.228.216", 9200, "http");
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(httpHost));

        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("student");
        // 构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("name");
        // 大于等于
        rangeQuery.gte("a");
        // 小于等于
        rangeQuery.lte("z");

        sourceBuilder.query(rangeQuery);

        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
        // 关闭客户端连接
        client.close();
    }

    /**
     * 模糊 查询
     * @throws IOException
     */
    private static void searchFuzzyQuery() throws IOException {
        // 创建客户端对象
        HttpHost httpHost = new HttpHost("175.24.228.216", 9200, "http");
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(httpHost));

        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("student");
        // 构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        sourceBuilder.query((QueryBuilders.fuzzyQuery("name","zhangsan").fuzziness(Fuzziness.ONE)));

        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
        // 关闭客户端连接
        client.close();
    }

    /**
     * 高亮 查询
     * @throws IOException
     */
    private static void searchHighlightQuery() throws IOException {
        // 创建客户端对象
        HttpHost httpHost = new HttpHost("175.24.228.216", 9200, "http");
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(httpHost));

        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("student");
        // 构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // 构建查询方式：高亮查询
        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("name","zhangsan");
        // 设置查询方式
        sourceBuilder.query(termsQueryBuilder);
        //构建高亮字段
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color='red'>");//设置标签前缀
        highlightBuilder.postTags("</font>");//设置标签后缀
        highlightBuilder.field("name");//设置高亮字段
        //设置高亮构建对象
        sourceBuilder.highlighter(highlightBuilder);
        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
            //打印高亮结果
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            System.out.println(highlightFields);
        }
        System.out.println("<<========");
        // 关闭客户端连接
        client.close();
    }

    private static void searchAggQuery() throws IOException {
        // 创建客户端对象
        HttpHost httpHost = new HttpHost("175.24.228.216", 9200, "http");
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(httpHost));

        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("student");
        // 构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        sourceBuilder.aggregation(AggregationBuilders.max("maxAge").field("age"));

        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        // 聚合信息
        Aggregations aggregations = response.getAggregations();
        for (Aggregation aggregation : aggregations) {
            System.out.println(aggregation.getName());
            String type = aggregation.getType();
            if (type.equals("max")) {
                System.out.println(((ParsedMax) aggregation).getValue());
            }
            System.out.println(aggregation.getClass());
        }
        System.out.println("<<========");
        // 关闭客户端连接
        client.close();
    }

    private static void searchGroupByQuery() throws IOException {
        // 创建客户端对象
        HttpHost httpHost = new HttpHost("175.24.228.216", 9200, "http");
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(httpHost));

        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("student");
        // 构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        sourceBuilder.aggregation(AggregationBuilders.terms("age_groupby").field("age"));

        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 查询匹配
        System.out.println(response);
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        // 聚合信息
        Aggregations aggregations = response.getAggregations();
        for (Aggregation aggregation : aggregations) {
            System.out.println(aggregation.getName());
            String type = aggregation.getType();
            System.out.println(type);
            if (type.equals("lterms")) {
                List<? extends Terms.Bucket> buckets = ((ParsedLongTerms) aggregation).getBuckets();
                for (Terms.Bucket bucket : buckets) {
                    System.out.println(bucket.getKeyAsString());
                }
                System.out.println(buckets);
            }
            System.out.println(aggregation.getClass());
        }
        System.out.println("<<========");
        // 关闭客户端连接
        client.close();
    }
}
