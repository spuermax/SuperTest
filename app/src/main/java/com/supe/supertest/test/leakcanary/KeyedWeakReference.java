package com.supe.supertest.test.leakcanary;

/**
 * @Author yinzh
 * @Date 2019/1/13 10:20
 * @Description
 */

import com.squareup.leakcanary.HeapDump;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

import static com.supe.supertest.test.leakcanary.Preconditions.checkNotNull;


/** @see {@link HeapDump#referenceKey}. */
final class KeyedWeakReference extends WeakReference<Object> {
    public final String key;
    public final String name;

    KeyedWeakReference(Object referent, String key, String name,
                       ReferenceQueue<Object> referenceQueue) {
        super(checkNotNull(referent, "referent"), checkNotNull(referenceQueue, "referenceQueue"));
        this.key = checkNotNull(key, "key");
        this.name = checkNotNull(name, "name");
    }
}
