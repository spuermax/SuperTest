package com.supe.supertest.homework.event;

/**
 * @Author yinzh
 * @Date 2019/8/14 11:27
 * @Description
 */
public class MessageEvent<T> {

    public static final int NO_CODE = -1;
    public static final int EXAM_CHANGE_ANSWER = 33; //免费题 选择答案

    public static final int EXAM_NEXT_QUESTION = 34; //免费题 下一题



    private T mMessage;
    private int mCode;

    public MessageEvent(T message) {
        mMessage = message;
        mCode = NO_CODE;
    }

    public MessageEvent(T message, int code) {
        mMessage = message;
        mCode = code;
    }

    public MessageEvent(int code) {
        mMessage = null;
        mCode = code;
    }

    public T getMessageBody() {
        return mMessage;
    }

    public int getType() {
        return mCode;
    }
}
