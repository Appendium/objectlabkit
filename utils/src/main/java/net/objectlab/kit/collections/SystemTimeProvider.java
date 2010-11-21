/**
 * 
 */
package net.objectlab.kit.collections;

/**
 * @author xhensevalb
 *
 */
public class SystemTimeProvider implements TimeProvider {

    /* (non-Javadoc)
     * @see net.objectlab.kit.collections.TimeProvider#getCurrentMillis()
     */
    public long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

}
