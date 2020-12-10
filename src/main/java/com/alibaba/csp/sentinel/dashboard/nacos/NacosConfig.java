package com.alibaba.csp.sentinel.dashboard.nacos;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigFactory;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 *
 * @Author:NCEPU_RuanCong
 *
 * @Time:2012-12-09:9:34
 *
 *
 *
 *
 */
@Configuration
public class NacosConfig {

    @Autowired
    private NacosConfigProperties nacosConfigProperties;

    /**
     * key method which is able to convert FlowRuleEntity to FLowRule,because only FlowRule can effect application
     *
     * From FlowRuleEntity -----> JSON String
     *
     * @return
     *
     */
    @Bean
    public Converter<List<FlowRuleEntity>,String> flowRuleEntityEncoder(){

//        String result = JSON.toJSONString(rules.stream().map(FlowRuleEntity::toRule).collect(Collectors.toList()),true);


        return rules -> JSON.toJSONString(rules.stream().map(FlowRuleEntity::toRule).collect(Collectors.toList()),true);
    }


    /**
     *
     * form JSON String -----> FlowRuleEntity
     *
     * @return
     */
    @Bean
    public Converter<String,List<FlowRuleEntity>> flowRuleEntityDecoder(){
        return s -> JSON.parseArray(s,FlowRuleEntity.class);
    }

    @Bean
    public ConfigService nacosConfigService() throws Exception{
        Properties properties = new Properties();
        System.out.println("addr:"+nacosConfigProperties.getServerAddr()+",namespace:"+nacosConfigProperties.getNamespace()+",groupId:"+nacosConfigProperties.getGroupId());
        properties.put(PropertyKeyConst.SERVER_ADDR,NacosConfigConstant.IP+":"+NacosConfigConstant.PORT);
        properties.put(PropertyKeyConst.NAMESPACE,NacosConfigConstant.NAMESPACE);

        ConfigService c =  ConfigFactory.createConfigService(properties);
        System.out.println(c.toString());

        return c;
    }

}
