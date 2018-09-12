package com.jt.common.factory;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.Resource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

public class JedisClusterFactory implements FactoryBean<JedisCluster> {

	private JedisPoolConfig poolConfig;
	private Resource addressConfig;
	private String addressKeyPrefix;
	@Override
	public JedisCluster getObject() throws Exception {
		Set<HostAndPort> nodes = getNodes();
		JedisCluster jedisCluster = new JedisCluster(nodes, poolConfig);
		return jedisCluster;
	}

	private Set<HostAndPort> getNodes() {
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		Properties properties = new Properties();
		try {
			properties.load(addressConfig.getInputStream());
			//遍历properties中的数据
			for (Object node : properties.keySet()) {
				String strNode = (String) node;
				if(strNode.startsWith(addressKeyPrefix)){
					String value = properties.getProperty(strNode);
					String [] args = value.split(":");
					HostAndPort hostAndPort = 
							new HostAndPort(args[0], Integer.parseInt(args[1]));
					nodes.add(hostAndPort);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nodes;
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return JedisCluster.class;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setPoolConfig(JedisPoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}
	public void setAddressConfig(Resource addressConfig) {
		this.addressConfig = addressConfig;
	}
	public void setAddressKeyPrefix(String addressKeyPrefix) {
		this.addressKeyPrefix = addressKeyPrefix;
	}
}
