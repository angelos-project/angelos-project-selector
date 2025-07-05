/**
 * Copyright (c) 2025 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
 *
 * This software is available under the terms of the MIT license. Parts are licensed
 * under different terms if stated. The legal terms are attached to the LICENSE file
 * and are made available on:
 *
 *      https://opensource.org/licenses/MIT
 *
 * SPDX-License-Identifier: MIT
 *
 * Contributors:
 *      Kristoffer Paulsson - initial implementation
 */
package org.angproj.io.sel

/**
 * A token representing the registration of a [SelectableChannel] with a [Selector].
 *
 * A selection key is created each time a channel is registered with a selector.
 * It remains valid until it is cancelled, the channel is closed, or the selector is closed.
 *
 * A selection key contains:
 * - The selector with which it is registered
 * - The channel for which it was created
 * - The interest set, which determines the operations for which the channel will be tested
 * - The ready set, which identifies the operations for which the channel is ready
 * - An optional attachment object
 *
 * Selection keys are thread-safe and may be used by multiple threads.
 */
public abstract class AbstractSelectionKey : SelectionKey {

    /**
     * Returns the selector with which this key is registered.
     */
    abstract override fun selector(): Selector

    /**
     * Attaches the given object to this key.
     *
     * @param obj the object to attach; may be null
     */
    abstract override fun attach(obj: Any)

    /**
     * Retrieves the current attachment.
     *
     * @return the attached object, or null if none
     */
    abstract override fun attachment(): Any

    /**
     * Returns the channel for which this key was created.
     */
    abstract override fun channel(): SelectableChannel

    /**
     * Retrieves this key's interest set.
     *
     * @return the interest set
     */
    abstract override fun interestOps(): Int

    /**
     * Sets this key's interest set.
     *
     * @param ops the new interest set
     * @return this selection key
     */
    abstract override fun interestOps(ops: Int): AbstractSelectionKey

    /**
     * Retrieves this key's ready set.
     *
     * @return the ready set
     */
    abstract override fun readyOps(): Int

    /**
     * Tells whether this key's channel is ready to accept a new socket connection.
     *
     * @return true if acceptable
     */
    abstract override fun isAcceptable(): Boolean

    /**
     * Tells whether this key's channel is ready to complete its connection sequence.
     *
     * @return true if connectable
     */
    abstract override fun isConnectable(): Boolean

    /**
     * Tells whether this key's channel is ready for reading.
     *
     * @return true if readable
     */
    abstract override fun isReadable(): Boolean

    /**
     * Tells whether this key's channel is ready for writing.
     *
     * @return true if writable
     */
    abstract override fun isWritable(): Boolean

    /**
     * Tells whether this key is valid.
     *
     * @return true if valid
     */
    abstract override fun isValid(): Boolean

    /**
     * Cancels this key.
     */
    abstract override fun cancel()
}