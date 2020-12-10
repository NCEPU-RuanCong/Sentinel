package com.alibaba.csp.sentinel.dashboard.nacos;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @Author:NCEPU_RuanCong
 *
 *
 * @Time:2020-12-09-09:58
 *
 *  from dashboard push FlowRule to nacos
 *
 *  dashboard-------->nacos
 *
 */
@Component("flowRuleNacosPublisher")
public class FlowRuleNacosPublisher implements DynamicRulePublisher<List<FlowRuleEntity>> {

    private final Logger logger = LoggerFactory.getLogger(FlowRuleNacosPublisher.class);

    @Autowired
    private NacosConfigProperties nacosConfigProperties;

    @Autowired
    private ConfigService configService;

//    @Autowired
//    private Converter<List<FlowRuleEntity>,String> converter;
    private FlowRuleConvert converter = new FlowRuleConvert();

    @Override
    public void publish(String app, List<FlowRuleEntity> rules) throws Exception {
        logger.info("------------>app:{}",app);
        logger.info("------------>nacos config is:{}",nacosConfigProperties.toString()+
                ",constant group_id:"+NacosConfigConstant.GROUP_ID+",data_id:"+NacosConfigConstant.DATA_ID);
        AssertUtil.notEmpty(app,"app name cannot be empty!");
        if(rules == null) return;
        String temp = converter.convert(rules);
        configService.publishConfig(NacosConfigConstant.DATA_ID,NacosConfigConstant.GROUP_ID,temp);
    }
}
