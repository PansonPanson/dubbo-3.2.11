/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.demo.provider;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.demo.DemoService;

public class Application {

    private static final String REGISTRY_URL = "zookeeper://127.0.0.1:2181";

    public static void main(String[] args) {
        startWithBootstrap();
    }

    private static void startWithBootstrap() {
        // 服务配置信息封装
        ServiceConfig<DemoServiceImpl> service = new ServiceConfig<>();
        // 服务暴露接口
        service.setInterface(DemoService.class);
        // 服务暴露接口的实现
        service.setRef(new DemoServiceImpl());

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap
                // 服务名称
                .application(new ApplicationConfig("dubbo-demo-api-provider"))
                // 注册中心地址
                .registry(new RegistryConfig(REGISTRY_URL))
                // 使用 DUBBO 协议
                .protocol(new ProtocolConfig(CommonConstants.DUBBO, -1))
                .service(service)
                // 服务启动
                .start()
                .await();
    }
}
