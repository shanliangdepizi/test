package com.jt.redis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

public class TestRedis {

	/**
	 * 连接单台redis
	 * 参数介绍：
	 *       redisip地址
	 *       redis:6379
	 */
	@Test
	public void test01(){
		Jedis jedis = new Jedis("192.168.161.138", 6379);
		jedis.set("redis", "redis入门案例");
		System.out.println("获取redis的数据："+jedis.get("redis"));
		System.out.println("获取redis的数据是："+jedis.get("1111"));
		jedis.del("redis");
		System.out.println("获取redis的数据："+jedis.get("redis"));
		//为数据设定超时时间 单位是秒
		jedis.setex("1804", 20, "1804班");
		
	}
	@Test
	public void test04(){
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(1000);
		//添加节点信息
		Set<HostAndPort> hostAndPool = new HashSet<HostAndPort>();
		hostAndPool.add(new HostAndPort("192.168.161.138", 7000));
		hostAndPool.add(new HostAndPort("192.168.161.138", 7001));
		hostAndPool.add(new HostAndPort("192.168.161.138", 7002));
		hostAndPool.add(new HostAndPort("192.168.161.138", 7003));
		hostAndPool.add(new HostAndPort("192.168.161.138", 7004));
		hostAndPool.add(new HostAndPort("192.168.161.138", 7005));
		hostAndPool.add(new HostAndPort("192.168.161.138", 7006));
		hostAndPool.add(new HostAndPort("192.168.161.138", 7007));
		hostAndPool.add(new HostAndPort("192.168.161.138", 7008));
		JedisCluster cluster = new JedisCluster(hostAndPool, poolConfig);
		cluster.set("110119", "集群搭建成功");
		System.out.println(cluster.get("110119"));
		
	}
}
