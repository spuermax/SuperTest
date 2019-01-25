package com.supe.supertest.test.leakcanary;

import com.squareup.haha.perflib.Instance;
import com.squareup.leakcanary.Exclusion;

/**
 * @Author yinzh
 * @Date 2019/1/13 10:22
 * @Description
 */
final class LeakNode {
    /** May be null. */
    final Exclusion exclusion;
    final Instance instance;
    final LeakNode parent;
    final LeakReference leakReference;

    LeakNode(Exclusion exclusion, Instance instance, LeakNode parent, LeakReference leakReference) {
        this.exclusion = exclusion;
        this.instance = instance;
        this.parent = parent;
        this.leakReference = leakReference;
    }
}
