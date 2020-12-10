package com.alibaba.csp.sentinel.dashboard.nacos;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @Author：NCEPU_RuanCong
 *
 * @Time:2020-12-09-9:50
 *
 *     pull FlowRule form configuration to dashboard
 *
 *     nacos--------->dashboard
 *
 */
@Component("flowRuleNacosProvider")
public class FlowRuleNacosProvider  implements DynamicRuleProvider<List<FlowRuleEntity>> {

    private static Logger logger = LoggerFactory.getLogger(FlowRuleNacosProvider.class);

    @Autowired
    private NacosConfigProperties nacosConfigProperties;

    @Autowired
    private ConfigService configService;

    @Autowired
    private Converter<String,List<FlowRuleEntity>> converter;

    @Override
    public List<FlowRuleEntity> getRules(String appName) throws Exception {

//        Properties properties = new Properties();
//        properties.put(PropertyKeyConst.NAMESPACE,NacosConfigConstant.NAMESPACE);
//        properties.put(PropertyKeyConst.SERVER_ADDR,NacosConfigConstant.IP);

        logger.info("-------------->nacos config is:{}",nacosConfigProperties.toString()+
                ",constant group id:"+NacosConfigConstant.GROUP_ID+",constant data id:"+NacosConfigConstant.DATA_ID);
        String rules = configService.getConfig(NacosConfigConstant.DATA_ID,NacosConfigConstant.GROUP_ID,3000);
//        String rules = configService.getConfig()
        logger.info("-------------->from nacos pull rules:{}",rules);
        return StringUtil.isEmpty(rules) ? new ArrayList<>() : converter.convert(rules);
    }
}
