package cn.kanyu.springai.config;

import org.springframework.ai.chat.client.advisor.api.*;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.rag.Query;
import reactor.core.publisher.Flux;

import java.util.Map;

public class ReReadingAdvisor implements BaseAdvisor {

    private static final  String DEFAULT_USER_TEXT_ADVISE= """
            {re2_input_query}
            Read the question again:{re2_input_query}
            """;
    @Override
    public AdvisedRequest before(AdvisedRequest request) {
        String contents = request.userText();
        Query originalQuery = Query.builder()
                .text(new PromptTemplate(DEFAULT_USER_TEXT_ADVISE, request.userParams()).render(Map.of("re2_input_query",contents)))
                .history(request.messages())
                .build();
        AdvisedRequest request1 = AdvisedRequest.from(request).userText(originalQuery.text()).build();
        return request1;
    }

    @Override
    public AdvisedResponse after(AdvisedResponse advisedResponse) {
        return advisedResponse;
    }

    /*存在多个拦截器的时候定义拦截器的优先级 数字越小优先级越高先执行*/
    @Override
    public int getOrder() {
        return 0;
    }
}
