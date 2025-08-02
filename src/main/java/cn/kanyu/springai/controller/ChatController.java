package cn.kanyu.springai.controller;
import cn.kanyu.springai.entity.MoreModelConfig;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
public class ChatController {
    //注入模型，配置文件中的模型，或者可以在方法中指定模型
//    @Resource
//    private OllamaChatModel model;

    //模拟数据库存储会话和消息
//    ChatMemory chatMemory = new InMemoryChatMemory();

    @Autowired
    private ChatClient chatClient;

    @Autowired(required=false)
    private VectorStore vectorStore;


    @GetMapping(value = "/chat", produces = "text/plain; charset=UTF-8")
    public String generation(String userInput) {
        // 发起聊天请求并处理响应
        return chatClient.prompt()
                .user(userInput)
                .advisors(new QuestionAnswerAdvisor(vectorStore))
                .call()
                .content();
    }
//    @RequestMapping("/chat")
//    public String chat(String prompt){
//        System.out.println(prompt);
//        return chatClient.prompt().user(prompt).call().content();
//       String call = model.call(message);
//        return call;
//
//    }


    @GetMapping(value = "/streamChat", produces = "text/event-stream;charset=UTF-8")
    public Flux<String> streamChat(@RequestParam("message")String message){
        //创建随机会话 ID
//        String sessionId = UUID.randomUUID().toString();
//        ChatClient chatClient = ChatClient.builder(this.model).defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory, sessionId, 10)).build();
        return chatClient
                .prompt()
                .user(message)
                .advisors(new QuestionAnswerAdvisor(vectorStore))
                .stream()
                .content();
    }

    @Autowired
    OllamaChatModel ollamaChatModel;
    //多模型选择
    @GetMapping(value = "/mulChatModel", produces = "text/event-stream;charset=UTF-8")
    public Flux<String> streamChatMul(@RequestParam("message")String message, MoreModelConfig moreModelConfig){

        ChatClient.Builder builder = ChatClient.builder(ollamaChatModel);
        builder.defaultOptions(ChatOptions.builder()
                .temperature(moreModelConfig.getTemperature())
                .model(moreModelConfig.getModel()).build()
        ).build();
        return chatClient
                .prompt()
                .user(message)
                .advisors(new QuestionAnswerAdvisor(vectorStore))
                .stream()
                .content();
    }

}