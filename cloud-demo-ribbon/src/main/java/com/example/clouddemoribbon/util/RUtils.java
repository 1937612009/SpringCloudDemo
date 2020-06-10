package com.example.clouddemoribbon.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * [redis的工具类] <br>
 *
 * @author [li.qiong]<br>
 * @version 1.0.0<br>
 * @CreateDate 2020年4月6日 <br>
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Slf4j
@Component
public class RUtils {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * [判断这个键是否存在] <br>
     *
     * @param redisKey
     * @return <br>
     * @author [li.qiong]<br>
     */
    public boolean isExistsKey(String redisKey) {
        try {
            return redisTemplate.hasKey(redisKey);
        } catch (Throwable e) {
            log.error("set key:" + redisKey + "  error:", e);
            return false;
        }
    }

    /**
     * [添加redis缓存,如果原来存在，则覆盖,时间为一天] <br>
     *
     * @param redisKey
     * @param object   <br>
     * @author [li.qiong]<br>
     */
    public boolean set(String redisKey, Object object) {
        boolean flag = set(redisKey, object, 24, TimeUnit.HOURS);
        return flag;
    }

    /**
     * [永久有效] <br>
     *
     * @param redisKey
     * @param object   <br>
     * @author [li.qiong]<br>
     */
    public void setNoExprise(String redisKey, Object object) {
        try {
            if (isNotEmpty(object)) {
                BoundValueOperations operations = redisTemplate.boundValueOps(redisKey);
                operations.set(JSON.toJSONString(object));
            }
        } catch (Throwable e) {
            log.error("set key:" + redisKey + "  error:", e);
        }
    }

    /**
     * [添加redis缓存,如果原来存在，则覆盖, 但是时间直接是你定的时间,而不会进行修改] <br>
     * TODO 注意: 集合对象必须是是集合,不能是字符串,这样就不会把集合进行转义
     *
     * @param redisKey
     * @param object
     * @param time
     * @param timeUnit <br>
     * @author [li.qiong]<br>
     */
    public boolean set(String redisKey, Object object, long time, TimeUnit timeUnit) {
        boolean flag = false;
        try {
            if (isNotEmpty(object)) {
                BoundValueOperations operations = redisTemplate.boundValueOps(redisKey);
                if (object instanceof Collection) {
                    JSONArray asJsonObject = JSONArray.fromObject(object);
                    operations.set(JSON.toJSONString(asJsonObject), time, timeUnit);
                } else {
                    operations.set(JSON.toJSONString(object), time, timeUnit);
                }
                flag = true;
            } else {
                log.error("set key:" + redisKey + "  对象信息为空:", object);
            }
        } catch (Throwable e) {
            log.error("set key:" + redisKey + "  error:", e);
        }
        return flag;
    }

    /**
     * [添加redis缓存,如果原来存在，则覆盖, 但是时间按照最新设置时间] <br>
     *
     * @param redisKey
     * @param object
     * @param time
     * @param timeUnit <br>
     * @author [li.qiong]<br>
     */
    public void setInfo(String redisKey, Object object, long time, TimeUnit timeUnit) {
        try {
            if (isNotEmpty(object)) {
//				RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//				redisStandaloneConfiguration.setDatabase(13);
                BoundValueOperations operations = redisTemplate.boundValueOps(redisKey);
                if (object instanceof Collection) {
                    JSONArray asJsonObject = JSONArray.fromObject(object);
                    operations.set(JSON.toJSONString(asJsonObject), time, timeUnit);
                } else {
                    operations.set(JSON.toJSONString(object), time, timeUnit);
                }
            } else {
                log.error("set key:" + redisKey + "  对象信息为空:", object);
            }
        } catch (Throwable e) {
            log.error("set key:" + redisKey + "  error:", e);
        }

    }

    /**
     * [添加redis缓存,如果原来存在，则覆盖, 但是时间按照最新设置时间] <br>
     *
     * @param redisKey
     * @param object
     * @param time
     * @param timeUnit <br>
     * @author [li.qiong]<br>
     */
    public void setInfoOneDay(String redisKey, Object object) {
        setInfo(redisKey, object, 24, TimeUnit.HOURS);
    }

    /**
     * [按照指定时间单位返回剩余时间] <br>
     *
     * @param redisKey
     * @param timeUnit
     * @return <br>
     * @author [li.qiong]<br>
     */
    public Long getTTLTime(String redisKey, TimeUnit timeUnit) {
        try {
            Long expire = redisTemplate.getExpire(redisKey, timeUnit);
            return expire;
        } catch (Throwable e) {
            log.error("set key:" + redisKey + "  error:", e);
        }
        return null;

    }

    /**
     * [获取redis非list缓存,单个对象] <br>
     *
     * @param redisKey
     * @param clazz
     * @return <br>
     * @author [li.qiong]<br>
     */
    public <T> T getObject(String redisKey, Class<T> clazz) {
        try {
            String objectJson = redisTemplate.opsForValue().get(redisKey);
            if (StringUtils.isBlank(objectJson)) {
                return null;
            }
            return JSON.parseObject(objectJson, clazz);
        } catch (Throwable e) {
            log.error("getvalue:" + redisKey + " error:", e);
        }
        return null;
    }

    /**
     * [通过键自增num] <br>
     *
     * @param redisKey
     * @param num
     * @return <br>
     * @author [li.qiong]<br>
     */
    public long incrby(String redisKey, long num) {
        try {
            BoundValueOperations boundHashOperations = redisTemplate.boundValueOps(redisKey);
            return boundHashOperations.increment(num);
        } catch (Throwable e) {
            log.error("hset key:" + redisKey + " error:", e);
            throw new RuntimeException("获取数据异常");
        }
    }

    /**
     * [设置指定的KEY 多少分钟后失效] <br>
     *
     * @param redisKey
     * @param minutes  <br>
     * @author [li.qiong]<br>
     */
    public void expire(String redisKey, int minutes) {
        try {
            BoundValueOperations opt = redisTemplate.boundValueOps(redisKey);
            opt.expire(minutes, TimeUnit.MINUTES);
        } catch (Throwable e) {
            log.error("expire key: " + redisKey + " error:", e);
        }
    }

    /**
     * [添加redis的map缓存] <br>
     *
     * @param redisKey
     * @param filedKey
     * @param object   <br>
     * @author [li.qiong]<br>
     */
    public void hsetNoExpire(String redisKey, String filedKey, Object object) {
        try {
            if (isNotEmpty(object)) {
                BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(redisKey);
                boundHashOperations.put(filedKey, JSON.toJSONString(object));
            }
        } catch (Throwable e) {
            log.error("hset key:" + redisKey + " fieldkey:" + filedKey + " error:", e);
        }
    }

    /**
     * [添加redis的map缓存] <br>
     *
     * @param redisKey
     * @param filedKey
     * @param object   <br>
     * @author [li.qiong]<br>
     */
    public void hset(String redisKey, String filedKey, Object object) {
        try {
            if (isNotEmpty(object)) {
                BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(redisKey);
                boundHashOperations.put(filedKey, JSON.toJSONString(object));
            }
        } catch (Throwable e) {
            log.error("hset key:" + redisKey + " fieldkey:" + filedKey + " error:", e);
        }
    }

    /**
     * [查看redeis的某一个键的的缓存数目] <br>
     *
     * @param redisKey
     * @return <br>
     * @author [li.qiong]<br>
     */
    public Long hlength(String redisKey) {
        try {
            BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(redisKey);
            return boundHashOperations.size();
        } catch (Throwable e) {
            log.error("hset key:" + redisKey);
            return null;
        }
    }

    /**
     * [添加redis的map缓存，设置超时时间.此超时时间会覆盖掉前一个超时时间] <br>
     *
     * @param redisKey
     * @param filedKey
     * @param object
     * @param timeout
     * @param unit     <br>
     * @author [li.qiong]<br>
     */
    public void hset(String redisKey, String filedKey, Object object, long timeout, TimeUnit unit) {
        try {
            if (isNotEmpty(object)) {
                BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(redisKey);
                boundHashOperations.put(filedKey, JSON.toJSONString(object));
                boundHashOperations.expire(timeout, unit);
            }
        } catch (Throwable e) {
            log.error("hset key:" + redisKey + " fieldkey:" + filedKey + " error:", e);
        }
    }

    /**
     * [获取redis的map缓存] <br>
     *
     * @param redisKey
     * @param filedKey
     * @param clazz
     * @return <br>
     * @author [li.qiong]<br>
     */
    public <T> T hget(String redisKey, String filedKey, Class<T> clazz) {
        try {
            String objectJson = (String) redisTemplate.opsForHash().get(redisKey, filedKey);
            if (StringUtils.isBlank(objectJson)) {
                return null;
            }
            return JSON.parseObject(objectJson, clazz);
        } catch (Throwable e) {
            log.error("hget key:" + redisKey + " fieldkey:" + filedKey + " error:", e);
        }
        return null;
    }

    /**
     * [通过键自增num] <br>
     *
     * @param redisKey
     * @param filedKey
     * @param num
     * @param redisValue
     * @return <br>
     * @author [li.qiong]<br>
     */
    public long hIncrby(String redisKey, String filedKey, long redisValue, long num) {
        try {
            BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(redisKey);
            boundHashOperations.put(filedKey, redisValue + "");
            return boundHashOperations.increment(filedKey, num);
        } catch (Throwable e) {
            log.error("hset key:" + redisKey + " error:", e);
            throw new RuntimeException("获取数据异常");
        }
    }

    /**
     * [获取redis中map的list缓存] <br>
     *
     * @param redisKey
     * @param filedKey
     * @param clazz
     * @return <br>
     * @author [li.qiong]<br>
     */
    public <T> List<T> hgetList(String redisKey, String filedKey, Class<T> clazz) {
        try {
            String objectJson = (String) redisTemplate.opsForHash().get(redisKey, filedKey);
            if (StringUtils.isBlank(objectJson)) {
                return new ArrayList<T>();
            }
            return JSON.parseArray(objectJson, clazz);
        } catch (Throwable e) {
            log.error("hgetList key:" + redisKey + " fieldkey:" + filedKey + " error:", e);
        }
        return new ArrayList<T>();
    }

    /**
     * [删除redis某个key的缓存] <br>
     *
     * @param redisKey
     * @param filedKey <br>
     * @author [li.qiong]<br>
     */
    public void hDelete(String redisKey, String filedKey) {
        try {
            BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(redisKey);
            boundHashOperations.delete(filedKey);
        } catch (Throwable e) {
            log.error("delete KEY:" + redisKey + " fileid key:" + filedKey + " error:", e);
        }
    }

    /**
     * [删除redis某个key的缓存] <br>
     *
     * @param redisKey <br>
     * @author [li.qiong]<br>
     */
    public void delete(String redisKey) {
        try {
            redisTemplate.delete(redisKey);
        } catch (Throwable e) {
            log.error("delete KEY:" + redisKey + " error:", e);
        }
    }

    /**
     * [判断对象是否为空 null -> true "" -> true " " -> true "null" -> true empty Collection
     * -> true empty Array -> true others -> false] <br>
     *
     * @param object
     * @return <br>
     * @author [li.qiong]<br>
     */
    private boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof String) {
            return ((String) object).trim().equals("") || ((String) object).trim().equals("null");
        } else if (object instanceof Collection<?>) {
            return ((Collection<?>) object).isEmpty();
        } else if (object instanceof Map<?, ?>) {
            return ((Map<?, ?>) object).isEmpty();
        } else if (object.getClass().isArray()) {
            return Array.getLength(object) == 0;
        } else {
            return false;
        }
    }

    private boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }

    public void expire(String redisKey, int expireTime, TimeUnit timeUnit) {
        redisTemplate.expire(redisKey, expireTime, timeUnit);
    }

    /**
     * [通过键获取某一个字符串的数据] <br>
     *
     * @param redisKey
     * @return <br>
     * @author [li.qiong]<br>
     */
    public String getString(String redisKey) {
        try {
            String objectJson = (String) redisTemplate.opsForValue().get(redisKey);
            if (StringUtils.isNotBlank(objectJson)) {
                if (objectJson.substring(0, 1).equals("\"")) {
                    objectJson = objectJson.substring(1, objectJson.length() - 1);
                }
                return objectJson;
            }
            return null;

        } catch (Throwable e) {
            log.error("getString:" + redisKey + " error:", e);
        }
        return null;
    }

    /**
     * [获取redis的list缓存] <br>
     *
     * @param redisKey
     * @param clazz
     * @return <br>
     * @author [li.qiong]<br>
     */
    public <T> List<T> getList(String redisKey, Class<T> clazz) {
        try {
            String objectJson = (String) redisTemplate.opsForValue().get(redisKey);
            if (StringUtils.isBlank(objectJson)) {
                return new ArrayList<T>();
            }
            return JSON.parseArray(objectJson, clazz);
        } catch (Throwable e) {
            log.error("getList:" + redisKey + " error:", e);
        }

        return new ArrayList<T>();
    }

    /**
     * [获取redis的list缓存, 如果KEY不存在 返回NULL] <br>
     *
     * @param redisKey
     * @param clazz
     * @return <br>
     * @author [li.qiong]<br>
     */
    public <T> List<T> getNewList(String redisKey, Class<T> clazz) {
        try {
            String objectJson = (String) redisTemplate.opsForValue().get(redisKey);
            if (StringUtils.isBlank(objectJson)) {
                return null;
            }
            return JSON.parseArray(objectJson, clazz);
        } catch (Throwable e) {
            log.error("getList:" + redisKey + " error:", e);
        }

        return null;
    }

    /**
     * SADD key member [member ...]
     * <p/>
     * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。
     * <p/>
     * 假如 key 不存在，则创建一个只包含 member 元素作成员的集合。
     * <p/>
     * 当 key 不是集合类型时，返回一个错误。 RedisSetCommands
     * <p/>
     * <p/>
     * redis的set数据结构的数据增加。
     *
     * @param key    redis key
     * @param values values
     * @return 增加的记录数
     */
    public Long sAdd(String key, Object[] values) {
        try {
            BoundSetOperations boundSetOperations = redisTemplate.boundSetOps(key);
            List<String> jsonList = new ArrayList<String>(values.length);
            String jsonStr = null;
            for (Object obj : values) {
                jsonStr = JSON.toJSONString(obj);
                jsonList.add(jsonStr);
            }
            Long result = boundSetOperations.add(jsonList.toArray());
            return result;
        } catch (Throwable e) {
            log.error("sAdd KEY:" + key + " error:", e);
        }
        return 0L;
    }

    /**
     * Get size of set at {@code key}. redis set的记录数。
     *
     * @param key redis key
     * @return set的记录数
     */
    public Long sCard(String key) {
        try {
            BoundSetOperations boundSetOperations = redisTemplate.boundSetOps(key);
            return boundSetOperations.size();
        } catch (Throwable e) {
            log.error("sCard KEY:" + key + "  error:", e);
        }
        return 0L;
    }

    /**
     * Removes and returns a random element from the set value stored at key.
     * 弹出redis set的一条记录。 http://redis.io/commands/spop
     *
     * @param redisKey redis key
     * @return set value
     */
    public <T> T spop(String redisKey, Class<T> clazz) {
        try {
            BoundSetOperations boundSetOperations = redisTemplate.boundSetOps(redisKey);
            Object objectJson = boundSetOperations.pop();
            if (objectJson == null || StringUtils.isBlank(objectJson.toString())) {
                return null;
            }
            return JSON.parseObject(objectJson.toString(), clazz);
        } catch (Throwable e) {
            log.error("sPop error:", e);
        }
        return null;
    }

    /**
     * Removes and returns a random element from the set value stored at key.
     * 弹出redis set的一条记录。 http://redis.io/commands/spop
     *
     * @param redisKey
     * @return set value
     */
    public <T> List<T> srandmember(String redisKey, Class<T> clazz, Long queryNum) {
        try {
            BoundSetOperations boundSetOperations = redisTemplate.boundSetOps(redisKey);
            List objectJson = boundSetOperations.randomMembers(queryNum);
            if (objectJson == null || StringUtils.isBlank(objectJson.toString())) {
                return null;
            }
            return JSON.parseArray(objectJson.toString(), clazz);
        } catch (Throwable e) {
            log.error("sPop error:", e);
        }
        return null;
    }

    public <T> List<T> distinctRandomMembers(String redisKey, Class<T> clazz, Long queryNum) {
        try {
            BoundSetOperations boundSetOperations = redisTemplate.boundSetOps(redisKey);
            Set objectJson = boundSetOperations.distinctRandomMembers(queryNum);
            if (objectJson == null || StringUtils.isBlank(objectJson.toString())) {
                return null;
            }
            return JSON.parseArray(objectJson.toString(), clazz);
        } catch (Throwable e) {
            log.error("sPop error:", e);
        }
        return null;
    }

    /**
     * Check if set at {@code key} contains {@code value}.
     *
     * @param key
     * @param value
     * @return
     */
    public boolean sIsMember(String key, String value) {
        try {
            return redisTemplate.boundSetOps(key).isMember(value);
        } catch (Throwable e) {
            log.error("sIsMember KEY:" + key + " member:" + value + "  error:", e);
        }
        return true;
    }

    /**
     * [按照key的前缀匹配对应的信息集合] <br>
     *
     * @param key
     * @return <br>
     * @author [li.qiong]<br>
     */
    public Set<String> patternKey(String key) {
        try {
            Set<String> keys = redisTemplate.keys(key + "*");
            return keys;
        } catch (Throwable e) {
            log.error("patternKey KEY" + key + "   error: " + e);
        }
        return null;
    }

}
