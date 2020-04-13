package com.supe.supertest.collapsing.demo.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.supe.supertest.R;
import com.supe.supertest.homework.QuestionActivity;
import com.supe.supertest.homework.adapter.ExamCardAdapter;
import com.supe.supertest.homework.module.HomeworkAnswerBean;
import com.supe.supertest.homework.module.HomeworkQuestionBean;
import com.supe.supertest.homework.event.MessageEvent;
import com.supe.supertest.homework.widget.FixHeightGridView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

/**
 * @Author yinzh
 * @Date 2020/3/18 15:09
 * @Description 题卡
 */
public class HomeworkCardFragment extends DialogFragment {

    private LinearLayout ll1;
    private LinearLayout ll2;
    private ImageView imgCardClose;
    private FixHeightGridView gridView;
    private RelativeLayout rlCardSubmit;
    private TextView tvCardSubmit;

    private QuestionActivity activity;

    private ArrayList<HomeworkQuestionBean> questionList;
    private ArrayList<HomeworkAnswerBean> answerList;

    private ExamCardAdapter mAdapter;

    //显示 交卷
    private boolean isShowSubmit = true;
    //是否展示答题结果
    private boolean isShowResult = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomDialog);
        activity = (QuestionActivity) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exam_card, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        if (getArguments() != null) {
            isShowSubmit = getArguments().getBoolean("isShowSubmit", true);
        }
        questionList = activity.mQuestionList;
        answerList = activity.answerList;
//        isShowResult = activity.mDoType == HOMEWORK_PARSE;

        ll1 = view.findViewById(R.id.ll_1);
        ll2 = view.findViewById(R.id.ll_2);
        imgCardClose = view.findViewById(R.id.img_card_close);
        gridView = view.findViewById(R.id.grid_view);
        rlCardSubmit = view.findViewById(R.id.rl_card_submit);
        tvCardSubmit = view.findViewById(R.id.tv_card_submit);

        if (isShowSubmit) {
            rlCardSubmit.setVisibility(View.VISIBLE);
            ll1.setVisibility(View.VISIBLE);
            ll2.setVisibility(View.GONE);
        } else {
            rlCardSubmit.setVisibility(View.GONE);
            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.VISIBLE);
        }

        imgCardClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        tvCardSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                activity.showFinishDialog();
            }
        });
    }

    private void initData() {
        mAdapter = new ExamCardAdapter(getContext(), questionList, answerList, isShowResult);
        gridView.setAdapter(mAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventBus.getDefault().post(new MessageEvent<>(position, MessageEvent.EXAM_CARD_JUMP));
                dismiss();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        //点击外部消失
        dialog.setCanceledOnTouchOutside(false);

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;

        Display display = window.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        params.height = (int) (height * 0.8f);

        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }
}
