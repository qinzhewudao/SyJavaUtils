package cache;

/**
 * @author sheyang
 * @description
 * @date 2019/9/14
 * @time 上午11:59
 */
public class LocalCacheUtilTest {

    public static void main(String[] args) {
        LocalCacheUtil.getInstance().put("sheYang", "test1");
        for (int i = 0; i < 100; i++) {
            System.out.println(LocalCacheUtil.getInstance().getIfPresent("sheYang"));
        }
        System.out.println(LocalCacheUtil.getInstance().getKeysSize());
        System.out.println(LocalCacheUtil.getInstance().getCacheStats());
        LocalCacheUtil.getInstance().rebuild(10, 10);
        System.out.println(LocalCacheUtil.getInstance().getIfPresent("sheYang"));
        System.out.println(LocalCacheUtil.getInstance().getKeysSize());
        System.out.println(LocalCacheUtil.getInstance().getCacheStats());
    }

}
