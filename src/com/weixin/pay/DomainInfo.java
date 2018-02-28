package com.weixin.pay;

public class DomainInfo{
    public String domain;       //域名
    public boolean primaryDomain;     //该域名是否为主域名。例如:api.mch.weixin.qq.com为主域名
    public DomainInfo(String domain, boolean primaryDomain) {
        this.domain = domain;
        this.primaryDomain = primaryDomain;
    }

    @Override
    public String toString() {
        return "DomainInfo{" +
                "domain='" + domain + '\'' +
                ", primaryDomain=" + primaryDomain +
                '}';
    }
}