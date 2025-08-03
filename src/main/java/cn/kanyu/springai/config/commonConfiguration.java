package cn.kanyu.springai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class commonConfiguration {
//    @Bean
//    ChatClient chatClient(ChatClient.Builder builder) {
//        return builder.defaultSystem("你将作为一名机器人产品的专家，对于用户的使用需求作出解答,当前服务的用户姓名：{name},年龄：{age}，性别：{sex}")
//                .build();
//    }

    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder.defaultSystem("# 角色\n" +
                        "你是一位专业的北京旅游导游\n" +
                        "##技能  \n" +
                        "### 技能一：理解客户需求  \n" +
                        "- 询问了解客户的旅行偏好，包括但不限于目的地、预算、出行日期和交通工具等\n" +
                        "- 根据用户的需求，个性化提供旅游攻略 \n" +
                        "\n" +
                        "### 技能二：规划旅游路线  \n" +
                        "- 结合客户的旅行偏好，设计一条详细的旅游路线，包括形成安排、交通方式、住宿和餐饮建议\n" +
                        "- 提供每个景点的详细介绍，包括历史背景、特色活动、最佳观看时间\n" +
                        "\n" +
                        "### 技能三：提供结合当地特色的实用建议  \n" +
                        "- 给出旅行中的实用建议，如必备物品清单、安全提示等\n" +
                        "- 回答用户关于旅行的任何问题\n" +
                        "- 若有不确定的问题，可以调用搜索工具来获取相关信息\n" +
                        "\n" +
                        "## 限制\n" +
                        "- 只讨论与旅游相关话题" )
                .build();
    }

    @Bean
    VectorStore vectorStore(EmbeddingModel embeddingModel) {
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(embeddingModel)
                .build();

        // 生成一个机器人产品说明书的文档
        List<Document> documents = List.of(
                new Document("产品说明书:产品名称：智能机器人\n" +
                        "产品描述：智能机器人是一个智能设备，能够自动完成各种任务。\n" +
                        "功能：\n" +
                        "1. 自动导航：机器人能够自动导航到指定位置。\n" +
                        "2. 自动抓取：机器人能够自动抓取物品。\n" +
                        "3. 自动放置：机器人能够自动放置物品。\n"));

        simpleVectorStore.add(documents);
        return simpleVectorStore;
    }
}
