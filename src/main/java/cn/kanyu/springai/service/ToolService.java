package cn.kanyu.springai.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToolService {


    @Autowired
    TicketService ticketService;

    //@tool 告诉大模型提供了哪些工具
    @Tool(description = "退票")
    public String cancel(
//            @ToolParam告诉大模型参数的描述
            @ToolParam(description = "预定号，格式任意") String ticketName,
            @ToolParam(description = "姓名") String name
    ){
        ticketService.cancel(ticketName, name);
        return "退票成功";
    }
}
