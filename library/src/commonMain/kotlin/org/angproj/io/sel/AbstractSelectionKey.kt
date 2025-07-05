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
 * A fully functional abstract base for selection keys, using [SelectOperation] for operation sets.
 */
public abstract class AbstractSelectionKey(
    private val _selector: Selector,
    private val _channel: SelectableChannel
) : SelectionKey {

    private var _attachment: Any = nullAttachment

    private var _interestOps: Int = 0

    private var _readyOps: Int = 0

    private var _valid: Boolean = true

    override fun selector(): Selector = _selector

    override fun channel(): SelectableChannel = _channel

    override fun attach(obj: Any) {
        _attachment = obj
    }

    override fun attachment(): Any = _attachment

    override fun interestOps(): Int {
        ensureValid()
        return _interestOps
    }

    override fun interestOps(ops: Int): AbstractSelectionKey {
        ensureValid()
        require((ops and _channel.validOps().inv()) == 0) { "Invalid interest ops for channel" }
        _interestOps = ops
        return this
    }

    override fun readyOps(): Int {
        ensureValid()
        return _readyOps
    }

    /**
     * Sets the ready operations. Should be called by the selector implementation.
     */
    protected fun setReadyOps(ops: Int) {
        _readyOps = ops
    }

    override fun isAcceptable(): Boolean =
        (readyOps() and SelectOperation.OP_ACCEPT.toInt()) != 0

    override fun isConnectable(): Boolean =
        (readyOps() and SelectOperation.OP_CONNECT.toInt()) != 0

    override fun isReadable(): Boolean =
        (readyOps() and SelectOperation.OP_READ.toInt()) != 0

    override fun isWritable(): Boolean =
        (readyOps() and SelectOperation.OP_WRITE.toInt()) != 0

    override fun isValid(): Boolean = _valid

    override fun cancel() {
        if (_valid) {
            _valid = false
            _selector.deregister(this)
        }
    }

    private fun ensureValid() {
        if (!_valid) throw CancelledKeyException()
    }

    public companion object {
        public val nullAttachment: Any = object
    }
}