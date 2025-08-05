package cn.kanyu.springai.config;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    ChatMemory chatMemory;
    //new SimpleLoggerAdvisor(),new ReReadingAdvisor() 自定义advisor
//    @Bean
//    ChatClient chatClient(ChatClient.Builder builder) {
//        return builder.defaultSystem(   "你是一位旅游专家" )
////                .defaultAdvisors(new SimpleLoggerAdvisor(),new SafeGuardAdvisor(List.of("张三")))
//                .defaultAdvisors(new SimpleLoggerAdvisor(),new ReReadingAdvisor())
//                .build();
//    }
        //
        @Bean
        ChatClient chatClient(ChatClient.Builder builder) {
            return builder.defaultSystem(   "你是一位旅游专家" )
                    .defaultAdvisors(PromptChatMemoryAdvisor.builder(chatMemory).build())
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
