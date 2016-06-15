package me.kuye.spider.manager;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * @author xianyijun
 *
 */
public class RedisManager {
	private static Logger logger = LoggerFactory.getLogger(RedisManager.class);
	private static final JedisPool jedisPool;
	static {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(5);
		config.setMaxTotal(500);
		config.setMaxWaitMillis(1000 * 100);
		config.setTestOnBorrow(true);
		jedisPool = new JedisPool(config, "localhost", 6379, 100000);
	}

	/**
	 * 
	 * @Title: get
	 * 
	 * @Description: 根据key值获取对应的value
	 * 
	 * @param key
	 * 
	 * @return: 键对应的值
	 * 
	 */
	public String get(String key) {
		Jedis jedis = null;
		String value = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			value = jedis.get(key);
		} catch (JedisConnectionException e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return value;
	}

	/**
	 * 
	 * @Title: set
	 * 
	 * @Description: 向redis传入对应的key,value
	 * 
	 * @param key
	 * @param value
	 * @return: 如果成功的话，返回OK，否则返回"0"
	 * 
	 */
	public String set(String key, String value) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			return jedis.set(key, value);
		} catch (JedisConnectionException e) {
			e.printStackTrace();
			broken = false;
			return "0";
		} finally {
			closeConnection(jedis, broken);
		}
	}

	/**
	* @Title: delete
	* @Description: 删除指定的key,也可以传入一个包含key的数组
	* @param     一个key  也可以使 string 数组
	* @return Long     返回删除成功的个数 
	* @throws
	*/
	public Long delete(String... keys) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			return jedis.del(keys);
		} catch (JedisConnectionException e) {
			e.printStackTrace();
			broken = true;
			return 0L;
		} finally {
			closeConnection(jedis, broken);
		}
	}

	
	/**
	* @Title: append
	* @Description: 通过key向指定的value值追加值
	* @param     参数
	* @return    成功返回 添加后value的长度 失败 返回 添加的 value 的长度  异常返回
	* @throws
	*/
	public Long append(String key, String str) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			return jedis.append(key, str);
		} catch (JedisConnectionException e) {
			e.printStackTrace();
			broken = true;
			return 0L;
		} finally {
			closeConnection(jedis, broken);
		}
	}

	public Boolean exists(String key) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			return jedis.exists(key);
		} catch (JedisConnectionException e) {
			e.printStackTrace();
			broken = true;
			return false;
		} finally {
			closeConnection(jedis, broken);
		}
	}

	public Long setnx(String key, String value) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			return jedis.setnx(key, value);
		} catch (JedisConnectionException e) {
			e.printStackTrace();
			broken = true;
			return 0L;
		} finally {
			closeConnection(jedis, broken);
		}
	}

	public String setex(String key, String value, int seconds) {
		Jedis jedis = null;
		String res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.setex(key, seconds, value);
		} catch (JedisConnectionException e) {
			e.printStackTrace();
			broken = true;
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	public Long setrange(String key, String str, int offset) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			return jedis.setrange(key, offset, str);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
			return 0L;
		} finally {
			closeConnection(jedis, broken);
		}
	}

	public List<String> mget(String... keys) {
		Jedis jedis = null;
		List<String> values = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			values = jedis.mget(keys);
		} catch (Exception e) {
			e.printStackTrace();
			broken = false;
		} finally {
			closeConnection(jedis, broken);
		}
		return values;
	}

	public String mset(String... keysvalues) {
		Jedis jedis = null;
		String res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.mset(keysvalues);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	public Long msetnx(String... keysvalues) {
		Jedis jedis = null;
		Long res = 0L;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.msetnx(keysvalues);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	public String getset(String key, String value) {
		Jedis jedis = null;
		String res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.getSet(key, value);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过下标 和key 获取指定下标位置的 value
	 * </p>
	 * 
	 * @param key
	 * @param startOffset
	 *            开始位置 从0 开始 负数表示从右边开始截取
	 * @param endOffset
	 * @return 如果没有返回null
	 */
	public String getrange(String key, int startOffset, int endOffset) {
		Jedis jedis = null;
		String res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.getrange(key, startOffset, endOffset);
		} catch (Exception e) {
			e.printStackTrace();
			broken = true;
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	public Long incr(String key) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.incr(key);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	public Long incrBy(String key, Long integer) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.incrBy(key, integer);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	public Long decr(String key) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.decr(key);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	public Long decrBy(String key, Long integer) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.decrBy(key, integer);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	public Long serlen(String key) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.strlen(key);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	public Long hset(String key, String field, String value) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.hset(key, field, value);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	public Long hsetnx(String key, String field, String value) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.hsetnx(key, field, value);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	public String hmset(String key, Map<String, String> hash) {
		Jedis jedis = null;
		String res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.hmset(key, hash);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	public String hget(String key, String field) {
		Jedis jedis = null;
		String res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.hget(key, field);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	public List<String> hmget(String key, String... fields) {
		Jedis jedis = null;
		List<String> res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.hmget(key, fields);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	public Long hincrby(String key, String field, Long value) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.hincrBy(key, field, value);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	public Boolean hexists(String key, String field) {
		Jedis jedis = null;
		Boolean res = false;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.hexists(key, field);

		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	public Long hlen(String key) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.hlen(key);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;

	}

	public Long hdel(String key, String... fields) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.hdel(key, fields);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	public Set<String> hkeys(String key) {
		Jedis jedis = null;
		Set<String> res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.hkeys(key);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key返回所有和key有关的value
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public List<String> hvals(String key) {
		Jedis jedis = null;
		List<String> res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.hvals(key);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key获取所有的field和value
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public Map<String, String> hgetall(String key) {
		Jedis jedis = null;
		Map<String, String> res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.hgetAll(key);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key向list头部添加字符串
	 * </p>
	 * 
	 * @param key
	 * @param strs
	 *            可以使一个string 也可以使string数组
	 * @return 返回list的value个数
	 */
	public Long lpush(String key, String... strs) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.lpush(key, strs);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key向list尾部添加字符串
	 * </p>
	 * 
	 * @param key
	 * @param strs
	 *            可以使一个string 也可以使string数组
	 * @return 返回list的value个数
	 */
	public Long rpush(String key, String... strs) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.rpush(key, strs);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key在list指定的位置之前或者之后 添加字符串元素
	 * </p>
	 * 
	 * @param key
	 * @param where
	 *            LIST_POSITION枚举类型
	 * @param pivot
	 *            list里面的value
	 * @param value
	 *            添加的value
	 * @return
	 */
	public Long linsert(String key, LIST_POSITION where, String pivot, String value) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.linsert(key, where, pivot, value);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key设置list指定下标位置的value
	 * </p>
	 * <p>
	 * 如果下标超过list里面value的个数则报错
	 * </p>
	 * 
	 * @param key
	 * @param index
	 *            从0开始
	 * @param value
	 * @return 成功返回OK
	 */
	public String lset(String key, Long index, String value) {
		Jedis jedis = null;
		String res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.lset(key, index, value);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key从对应的list中删除指定的count个 和 value相同的元素
	 * </p>
	 * 
	 * @param key
	 * @param count
	 *            当count为0时删除全部
	 * @param value
	 * @return 返回被删除的个数
	 */
	public Long lrem(String key, long count, String value) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.lrem(key, count, value);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key保留list中从strat下标开始到end下标结束的value值
	 * </p>
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return 成功返回OK
	 */
	public String ltrim(String key, long start, long end) {
		Jedis jedis = null;
		String res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.ltrim(key, start, end);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key从list的头部删除一个value,并返回该value
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public String lpop(String key) {
		Jedis jedis = null;
		String res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.lpop(key);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key从list尾部删除一个value,并返回该元素
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public String rpop(String key) {
		Jedis jedis = null;
		String res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.rpop(key);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key从一个list的尾部删除一个value并添加到另一个list的头部,并返回该value
	 * </p>
	 * <p>
	 * 如果第一个list为空或者不存在则返回null
	 * </p>
	 * 
	 * @param srckey
	 * @param dstkey
	 * @return
	 */
	public String rpoplpush(String srckey, String dstkey) {
		Jedis jedis = null;
		String res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.rpoplpush(srckey, dstkey);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key获取list中指定下标位置的value
	 * </p>
	 * 
	 * @param key
	 * @param index
	 * @return 如果没有返回null
	 */
	public String lindex(String key, long index) {
		Jedis jedis = null;
		String res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.lindex(key, index);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key返回list的长度
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public Long llen(String key) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.llen(key);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key获取list指定下标位置的value
	 * </p>
	 * <p>
	 * 如果start 为 0 end 为 -1 则返回全部的list中的value
	 * </p>
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<String> lrange(String key, long start, long end) {
		Jedis jedis = null;
		List<String> res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.lrange(key, start, end);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key向指定的set中添加value
	 * </p>
	 * 
	 * @param key
	 * @param members
	 *            可以是一个String 也可以是一个String数组
	 * @return 添加成功的个数
	 */
	public Long sadd(String key, String... members) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.sadd(key, members);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key删除set中对应的value值
	 * </p>
	 * 
	 * @param key
	 * @param members
	 *            可以是一个String 也可以是一个String数组
	 * @return 删除的个数
	 */
	public Long srem(String key, String... members) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.srem(key, members);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key随机删除一个set中的value并返回该值
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public String spop(String key) {
		Jedis jedis = null;
		String res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.spop(key);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key获取set中的差集
	 * </p>
	 * <p>
	 * 以第一个set为标准
	 * </p>
	 * 
	 * @param keys
	 *            可以使一个string 则返回set中所有的value 也可以是string数组
	 * @return
	 */
	public Set<String> sdiff(String... keys) {
		Jedis jedis = null;
		Set<String> res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.sdiff(keys);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key获取set中的差集并存入到另一个key中
	 * </p>
	 * <p>
	 * 以第一个set为标准
	 * </p>
	 * 
	 * @param dstkey
	 *            差集存入的key
	 * @param keys
	 *            可以使一个string 则返回set中所有的value 也可以是string数组
	 * @return
	 */
	public Long sdiffstore(String dstkey, String... keys) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.sdiffstore(dstkey, keys);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	public Set<String> sinter(String... keys) {
		Jedis jedis = null;
		Set<String> res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.sinter(keys);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key获取指定set中的交集 并将结果存入新的set中
	 * </p>
	 * 
	 * @param dstkey
	 * @param keys
	 *            可以使一个string 也可以是一个string数组
	 * @return
	 */
	public Long sinterstore(String dstkey, String... keys) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.sinterstore(dstkey, keys);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key返回所有set的并集
	 * </p>
	 * 
	 * @param keys
	 *            可以使一个string 也可以是一个string数组
	 * @return
	 */
	public Set<String> sunion(String... keys) {
		Jedis jedis = null;
		Set<String> res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.sunion(keys);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key返回所有set的并集,并存入到新的set中
	 * </p>
	 * 
	 * @param dstkey
	 * @param keys
	 *            可以使一个string 也可以是一个string数组
	 * @return
	 */
	public Long sunionstore(String dstkey, String... keys) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.sunionstore(dstkey, keys);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key将set中的value移除并添加到第二个set中
	 * </p>
	 * 
	 * @param srckey
	 *            需要移除的
	 * @param dstkey
	 *            添加的
	 * @param member
	 *            set中的value
	 * @return
	 */
	public Long smove(String srckey, String dstkey, String member) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.smove(srckey, dstkey, member);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key获取set中value的个数
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public Long scard(String key) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.scard(key);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key判断value是否是set中的元素
	 * </p>
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Boolean sismember(String key, String member) {
		Jedis jedis = null;
		Boolean res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.sismember(key, member);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key获取set中随机的value,不删除元素
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public String srandmember(String key) {
		Jedis jedis = null;
		String res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.srandmember(key);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key获取set中所有的value
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public Set<String> smembers(String key) {
		Jedis jedis = null;
		Set<String> res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.smembers(key);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key向zset中添加value,score,其中score就是用来排序的
	 * </p>
	 * <p>
	 * 如果该value已经存在则根据score更新元素
	 * </p>
	 * 
	 * @param key
	 * @param scoreMembers
	 * @return
	 */
	public Long zadd(String key, Map<String, Double> scoreMembers) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.zadd(key, scoreMembers);
			jedis.zadd(key, scoreMembers);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key向zset中添加value,score,其中score就是用来排序的
	 * </p>
	 * <p>
	 * 如果该value已经存在则根据score更新元素
	 * </p>
	 * 
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public Long zadd(String key, double score, String member) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.zadd(key, score, member);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key删除在zset中指定的value
	 * </p>
	 * 
	 * @param key
	 * @param members
	 *            可以使一个string 也可以是一个string数组
	 * @return
	 */
	public Long zrem(String key, String... members) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.zrem(key, members);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key增加该zset中value的score的值
	 * </p>
	 * 
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public Double zincrby(String key, double score, String member) {
		Jedis jedis = null;
		Double res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.zincrby(key, score, member);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key返回zset中value的排名
	 * </p>
	 * <p>
	 * 下标从小到大排序
	 * </p>
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Long zrank(String key, String member) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.zrank(key, member);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key返回zset中value的排名
	 * </p>
	 * <p>
	 * 下标从大到小排序
	 * </p>
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Long zrevrank(String key, String member) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.zrevrank(key, member);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key将获取score从start到end中zset的value
	 * </p>
	 * <p>
	 * socre从大到小排序
	 * </p>
	 * <p>
	 * 当start为0 end为-1时返回全部
	 * </p>
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<String> zrevrange(String key, long start, long end) {
		Jedis jedis = null;
		Set<String> res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.zrevrange(key, start, end);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key返回指定score内zset中的value
	 * </p>
	 * 
	 * @param key
	 * @param max
	 * @param min
	 * @return
	 */
	public Set<String> zrangebyscore(String key, String max, String min) {
		Jedis jedis = null;
		Set<String> res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.zrevrangeByScore(key, max, min);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key返回指定score内zset中的value
	 * </p>
	 * 
	 * @param key
	 * @param max
	 * @param min
	 * @return
	 */
	public Set<String> zrangeByScore(String key, double max, double min) {
		Jedis jedis = null;
		Set<String> res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.zrevrangeByScore(key, max, min);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 返回指定区间内zset中value的数量
	 * </p>
	 * 
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Long zcount(String key, String min, String max) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.zcount(key, min, max);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key返回zset中的value个数
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public Long zcard(String key) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.zcard(key);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key获取zset中value的score值
	 * </p>
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Double zscore(String key, String member) {
		Jedis jedis = null;
		Double res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.zscore(key, member);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key删除给定区间内的元素
	 * </p>
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Long zremrangeByRank(String key, long start, long end) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.zremrangeByRank(key, start, end);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	* @Title: zremrangeByScore
	* @Description: 通过key删除指定score内的元素
	* @param     key start end 
	* @return Long    返回类型
	* @throws
	*/
	public Long zremrangeByScore(String key, double start, double end) {
		Jedis jedis = null;
		Long res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.zremrangeByScore(key, start, end);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	* @Title: keys
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param     参数
	* @return Set<String>    返回类型
	* @throws
	*/
	public Set<String> keys(String pattern) {
		Jedis jedis = null;
		Set<String> res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.keys(pattern);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	* @Title: type
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param     参数
	* @return String    返回类型
	* @throws
	*/
	public String type(String key) {
		Jedis jedis = null;
		String res = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			res = jedis.type(key);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			closeConnection(jedis, broken);
		}
		return res;
	}

	/**
	* @Title: closeConnection
	* @Description: 释放连接
	* @param     参数
	* @return void    
	* @throws
	*/
	@SuppressWarnings("deprecation")
	public void closeConnection(Jedis jedis, boolean broken) {
		if (jedis != null) {
			if (broken) {
				logger.error(" returnBrokenResource jedis = " + jedis);
				jedisPool.returnBrokenResource(jedis);
			} else {
				jedisPool.returnResource(jedis);
			}
		}
	}

}
