package com.supe.supertest.test;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.haha.perflib.ArrayInstance;
import com.squareup.haha.perflib.ClassInstance;
import com.squareup.haha.perflib.ClassObj;
import com.squareup.haha.perflib.Heap;
import com.squareup.haha.perflib.HprofParser;
import com.squareup.haha.perflib.Instance;
import com.squareup.haha.perflib.Snapshot;
import com.squareup.haha.perflib.io.HprofBuffer;
import com.squareup.haha.perflib.io.MemoryMappedFileBuffer;
import com.supe.supertest.R;
import com.supe.supertest.test.leakcanary.HahaHelper;
import com.supe.supertest.test.leakcanary.HeapAnalyzer;
import com.supe.supertest.test.leakcanary.LeakTrace;
import com.supermax.base.common.permission.annotation.Permission;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.common.viewbind.annotation.OnClick;
import com.supermax.base.common.widget.toast.QsToast;
import com.supermax.base.mvp.QsActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.RequiresApi;

/**
 * @Author yinzh
 * @Date 2019/1/10 13:57
 * @Description
 */
public class ChapterBitmapActivity extends QsActivity {
    @Bind(R.id.tv_check)
    TextView tv_check;

    @Bind(R.id.iv_image1)
    ImageView iv_image1;
    @Bind(R.id.iv_image2)
    ImageView iv_image2;
    @Bind(R.id.iv_image3)
    ImageView iv_image3;
    public static final String SD_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/Android/data/";

    public static final String DUMP_PATH = SD_PATH + "com.supe.supertest/file/";


    @Override
    public int layoutId() {
        return R.layout.activity_chapter_bitmap;
    }

    @Permission({Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.READ_EXTERNAL_STORAGE})
    @Override
    public void initData(Bundle bundle) {
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_left_arrow_black);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_left_arrow_black);
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_left_arrow_black);


        iv_image1.setImageBitmap(bitmap1);
        iv_image2.setImageBitmap(bitmap2);
        iv_image3.setImageBitmap(bitmap3);

        Looper looper = Looper.getMainLooper();
        Looper.loop();
        ThreadLocal threadLocal = new ThreadLocal();
        threadLocal.get();
        threadLocal.set("");
        Looper.prepare();


    }

    @OnClick({R.id.tv_check})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_check:
                startAnalyze();

                QsToast.show("haha");
                break;
        }

    }


    private void startAnalyze() {
        Snapshot snapshot = createDump();

        //获得Bitmap Class
        ClassObj bitmapClass = snapshot.findClass(Bitmap.class.getName());
        Heap appHeap = snapshot.getHeap("app");
        Heap defaultHeap = snapshot.getHeap("default");

        analyzeHeap(bitmapClass, appHeap, snapshot);
        analyzeHeap(bitmapClass, defaultHeap, snapshot);



    }

    /**
     * 分析Heap
     *
     * @param heap
     */
    private void analyzeHeap(ClassObj bitmapClass, Heap heap, Snapshot snapshot) {
        Map<Integer, ArrayList<Instance>> instanceHeap = new HashMap<>();

        //从Heap中获取所有Bitmap的实例
        final List<Instance> bitmapInstance = bitmapClass.getHeapInstances(heap.getId());

        for (int i = 0; i < bitmapInstance.size(); i++) {
            Instance instance = bitmapInstance.get(i);
            ArrayInstance buffer = HahaHelper.fieldValue(((ClassInstance) instance).getValues(), "mBuffer");

            int hashCode = Arrays.hashCode(buffer.getValues());

            ArrayList<Instance> instanceList;
            if (instanceHeap.containsKey(hashCode)) {
                instanceList = instanceHeap.get(hashCode);
                instanceList.add(instance);
            } else {
                instanceList = new ArrayList<>();
                instanceList.add(instance);
            }

            instanceHeap.put(hashCode, instanceList);

        }

        HeapAnalyzer heapAnalyzer = new HeapAnalyzer();
        for (int key : instanceHeap.keySet()) {

            ArrayList<Instance> instanceList = instanceHeap.get(key);

            for(Instance instance : instanceList){
                long bufferSize = ((ClassInstance) instance).getTotalRetainedSize();
                long bitmapSize = instance.getTotalRetainedSize();

                Log.e(initTag(), "bufferSize==" + bufferSize);
                Log.e(initTag(), "bitmapSize==" + bitmapSize);
            }


            if (instanceList.size() > 1) {


                int height = HahaHelper.fieldValue(((ClassInstance) instanceList.get(0)).getValues(), "mHeight");
                int width = HahaHelper.fieldValue(((ClassInstance) instanceList.get(0)).getValues(), "mWidth");

                Log.e(initTag(), "图片重复个数 = " + instanceList.size());
                Log.e(initTag(), "hashcode: " + key);
                Log.e(initTag(), "height: " + height);
                Log.e(initTag(), "width: " + width);

                for (Instance instance : instanceList) {
                    LeakTrace leakTrace = heapAnalyzer.findLeakTrace(snapshot, instance);
                    Log.e(initTag(), "引用链: " + leakTrace.toString());

                }
            }

        }


    }


    /**
     * 生成堆转储文件
     */
    private Snapshot createDump() {
        File fileDir = new File(DUMP_PATH);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File file = new File(fileDir, "dump");
        Snapshot snapshot = null;

        try {
            if (!file.exists()) Debug.dumpHprofData(file.getAbsolutePath());
            HprofBuffer buffer = new MemoryMappedFileBuffer(file);
            HprofParser parser = new HprofParser(buffer);
            snapshot = parser.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return snapshot;
    }




    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                //
            }
        }
    };
}
