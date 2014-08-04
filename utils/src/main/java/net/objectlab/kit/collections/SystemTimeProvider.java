/**
 *
 */
package net.objectlab.kit.collections;

/**
 * @author Benoit Xhenseval
 *
 */
public class SystemTimeProvider implements TimeProvider {

    /* (non-Javadoc)
     * @see net.objectlab.kit.collections.TimeProvider#getCurrentMillis()
     */
    @Override
    public long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

}
